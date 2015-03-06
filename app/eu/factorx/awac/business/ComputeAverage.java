package eu.factorx.awac.business;

import eu.factorx.awac.controllers.AverageController.ScopeAndPeriod;
import eu.factorx.awac.dto.awac.get.CodeLabelDTO;
import eu.factorx.awac.dto.awac.get.CodeListDTO;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.code.label.CodeLabel;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.models.code.type.LanguageCode;
import eu.factorx.awac.models.code.type.QuestionCode;
import eu.factorx.awac.models.data.answer.AnswerValue;
import eu.factorx.awac.models.data.answer.QuestionAnswer;
import eu.factorx.awac.models.data.answer.QuestionSetAnswer;
import eu.factorx.awac.models.data.answer.type.*;
import eu.factorx.awac.models.data.question.Question;
import eu.factorx.awac.models.data.question.QuestionSet;
import eu.factorx.awac.models.data.question.type.*;
import eu.factorx.awac.models.forms.AwacCalculator;
import eu.factorx.awac.models.forms.Form;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.models.knowledge.Unit;
import eu.factorx.awac.service.*;
import eu.factorx.awac.util.MyrmexFatalException;
import eu.factorx.awac.util.MyrmexRuntimeException;
import eu.factorx.awac.util.email.messages.EmailMessage;
import eu.factorx.awac.util.email.service.EmailService;
import eu.factorx.awac.util.math.Vector2I;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Colour;
import jxl.write.*;
import jxl.write.Number;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import play.Logger;
import play.db.jpa.JPA;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.Boolean;
import java.util.*;
import java.util.concurrent.Future;


//@Named("ComputeAverageService")
//@Component
@Service
public class ComputeAverage {

	@Autowired
	private QuestionAnswerService questionAnswerService;
	@Autowired
	private UnitConversionService unitConversionService;
	@Autowired
	private VelocityGeneratorService velocityGeneratorService;
	@Autowired
	private EmailService emailService;
	@Autowired
	private CodeLabelService codeLabelService;
	@Autowired
	private QuestionSetAnswerService questionSetAnswerService;
	@Autowired
	private QuestionSetService questionSetService;

	private HashMap<QuestionSet, List<RepetitionMap>> repetitionMapList = new HashMap<>();
	private static final String indentString = "        ";
	private List<QuestionSet> listQuestionSetWithRepetition = new ArrayList<>();

	//list of question to ignore to determine the list of referencing questions
	private String[] questionToIgnore = {"A248"};

	// for asynchronous running purposes
	@play.db.jpa.Transactional(readOnly = true)
	@Async
	public Future<Boolean> computeAverageAsync(final Account account, final AwacCalculator awacCalculator,
											   final List<eu.factorx.awac.controllers.AverageController.ScopeAndPeriod> scopeAndPeriodList,
											   final Period period, final int organizationComputed,
											   final int scopeComputed) throws IOException, WriteException {

		try {
			JPA.withTransaction("default", false, new play.libs.F.Function0<Void>() {
				public Void apply() throws Throwable {


					play.Logger.info("Running in Asynch mode ");
					try {
						computeAverage(
							account,
							awacCalculator,
							scopeAndPeriodList,
							period,
							organizationComputed,
							scopeComputed
						);
					} catch (Exception e) {
						e.printStackTrace();
					}
					return null;
				}
			});
		} catch (Throwable e) {
			e.printStackTrace();
		}

		return new AsyncResult<Boolean>(new Boolean(true));
	}

	public void computeAverage(final Account account, final AwacCalculator awacCalculator,
							   final List<eu.factorx.awac.controllers.AverageController.ScopeAndPeriod> scopeAndPeriodList,
							   final Period period, final int organizationComputed, final int scopeComputed) throws IOException, WriteException {

		//for each question, compute average
		//compute form by form
		List<AverageValue> averageValueList = new ArrayList<>();
		for (Form form : awacCalculator.getForms()) {
			for (QuestionSet questionSet : form.getQuestionSets()) {
				averageValueList.addAll(computeAverage(questionSet, scopeAndPeriodList, account.getDefaultLanguage(), null, period));
			}
		}

		//
		// EXCEL FILE
		//
		//compute criteria
		HashMap<String, String> criteria = new HashMap<>();
		criteria.put("Calculateur", awacCalculator.getInterfaceTypeCode().getKey());
		criteria.put("Période", period.getLabel());
		criteria.put("Nombre de formulaires pris en compte", scopeAndPeriodList.size() + "");
		criteria.put("Nombre d'organisations prises en compte", organizationComputed + "");

		if (awacCalculator.getInterfaceTypeCode().equals(InterfaceTypeCode.ENTERPRISE)) {
			criteria.put("Nombre de sites pris en compte", scopeComputed + "");
		} else if (awacCalculator.getInterfaceTypeCode().equals(InterfaceTypeCode.EVENT)) {
			criteria.put("Nombre d'évènements pris en compte", scopeComputed + "");
		}

		ByteArrayOutputStream output = generateExcel(awacCalculator, averageValueList, account.getDefaultLanguage(), criteria);


		//send email
		Map<String, Object> values = new HashMap<>();
		/*
		values.put("request", calculatorInstance.getVerificationRequest());
	    values.put("user", securedController.getCurrentUser());
	    */

		/*** Add CELDL-405 ***/
		String subject;
		String content;
		HashMap<String, CodeLabel> traductions = codeLabelService.findCodeLabelsByList(CodeList.TRANSLATIONS_EMAIL_MESSAGE);

		subject = traductions.get("AVERAGE_SUBJECT").getLabel(account.getDefaultLanguage());
		content = traductions.get("AVERAGE_CONTENT").getLabel(account.getDefaultLanguage());

		values.put("content", content);

		String velocityContent = velocityGeneratorService.generate("verification/average.vm", values);

		EmailMessage email = new EmailMessage(account.getEmail(), subject, velocityContent);
		//

		// Local write for test purposes
		//	FileOutputStream outputs = new FileOutputStream(new File("/home/florian/temp/result.xls"));
		//	IOUtils.write(output.toByteArray(), outputs);

		//send email
		HashMap<String, ByteArrayOutputStream> listAttachment = new HashMap<>();
		listAttachment.put("statistiques_AWAC.xls", output);
		email.setAttachmentFilenameList(listAttachment);
		emailService.send(email);

	}

	private boolean computeNace(String naceCodeList, String naceCode, Scope site, Period period) {
		if (naceCodeList != null) {
			//load questionSet
			QuestionSet questionSeta1 = questionSetService.findByCode(new QuestionCode("A1"));
			if (CodeList.SECTEURPRIMAIRE.toString().equals(naceCodeList)) {

				//load code
				if (naceCode != null) {
					Code code = new Code(CodeList.SECTEURPRIMAIRE, naceCode);

					for (QuestionSetAnswer questionSetAnswer : questionSetAnswerService.findByScopeAndPeriodAndQuestionSet(site, period, questionSeta1)) {
						for (QuestionAnswer questionAnswer : questionSetAnswer.getQuestionAnswers()) {
							if (questionAnswer.getQuestion().getCode().getKey().equals("A4")) {
								for (AnswerValue answerValue : questionAnswer.getAnswerValues()) {
									if (((CodeAnswerValue) answerValue).getValue().equals(code)) {
										return true;
									}
								}


							}
						}
					}
				} else {
					Code code = new Code(CodeList.SECTEURPRINCIPAL, "1");
					for (QuestionSetAnswer questionSetAnswer : questionSetAnswerService.findByScopeAndPeriodAndQuestionSet(site, period, questionSeta1)) {
						for (QuestionAnswer questionAnswer : questionSetAnswer.getQuestionAnswers()) {
							if (questionAnswer.getQuestion().getCode().getKey().equals("A3")) {
								for (AnswerValue answerValue : questionAnswer.getAnswerValues()) {
									if (((CodeAnswerValue) answerValue).getValue().equals(code)) {
										return true;
									}
								}


							}
						}
					}
				}
			} else if (CodeList.SECTEURSECONDAIRE.toString().equals(naceCodeList)) {

				//load code
				if (naceCode != null) {
					Code code = new Code(CodeList.SECTEURSECONDAIRE, naceCode);

					for (QuestionSetAnswer questionSetAnswer : questionSetAnswerService.findByScopeAndPeriodAndQuestionSet(site, period, questionSeta1)) {
						for (QuestionAnswer questionAnswer : questionSetAnswer.getQuestionAnswers()) {
							if (questionAnswer.getQuestion().getCode().getKey().equals("A5")) {
								for (AnswerValue answerValue : questionAnswer.getAnswerValues()) {
									if (((CodeAnswerValue) answerValue).getValue().equals(code)) {
										return true;
									}
								}


							}
						}
					}
				} else {
					Code code = new Code(CodeList.SECTEURPRINCIPAL, "2");
					Code code2 = new Code(CodeList.SECTEURPRINCIPAL, "3");

					for (QuestionSetAnswer questionSetAnswer : questionSetAnswerService.findByScopeAndPeriodAndQuestionSet(site, period, questionSeta1)) {
						for (QuestionAnswer questionAnswer : questionSetAnswer.getQuestionAnswers()) {
							if (questionAnswer.getQuestion().getCode().getKey().equals("A3")) {
								for (AnswerValue answerValue : questionAnswer.getAnswerValues()) {
									if (((CodeAnswerValue) answerValue).getValue().equals(code) ||
										((CodeAnswerValue) answerValue).getValue().equals(code2)) {
										return true;
									}
								}


							}
						}
					}
				}
			} else if (CodeList.SECTEURTERTIAIRE.toString().equals(naceCodeList)) {
				//load code

				if (naceCode != null) {
					Code code = new Code(CodeList.SECTEURTERTIAIRE, naceCode);

					for (QuestionSetAnswer questionSetAnswer : questionSetAnswerService.findByScopeAndPeriodAndQuestionSet(site, period, questionSeta1)) {
						for (QuestionAnswer questionAnswer : questionSetAnswer.getQuestionAnswers()) {
							if (questionAnswer.getQuestion().getCode().getKey().equals("A6")) {
								for (AnswerValue answerValue : questionAnswer.getAnswerValues()) {
									if (((CodeAnswerValue) answerValue).getValue().equals(code)) {
										return true;
									}
								}


							}
						}
					}
				} else {
					Code code = new Code(CodeList.SECTEURPRINCIPAL, "4");

					for (QuestionSetAnswer questionSetAnswer : questionSetAnswerService.findByScopeAndPeriodAndQuestionSet(site, period, questionSeta1)) {
						for (QuestionAnswer questionAnswer : questionSetAnswer.getQuestionAnswers()) {
							if (questionAnswer.getQuestion().getCode().getKey().equals("A3")) {
								for (AnswerValue answerValue : questionAnswer.getAnswerValues()) {
									if (((CodeAnswerValue) answerValue).getValue().equals(code)) {
										return true;
									}
								}


							}
						}
					}
				}
			}
		} else

		{
			return true;
		}

		return false;
	}

	private ByteArrayOutputStream generateExcel(AwacCalculator awacCalculator, List<AverageValue> averageValueList, LanguageCode lang, HashMap<String, String> criteria) throws WriteException, IOException {

		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

		// Create cell font and format
		WritableFont cellFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
		WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
		cellFormat.setAlignment(jxl.format.Alignment.LEFT);
		cellFormat.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);

		WorkbookSettings wbSettings = new WorkbookSettings();
		wbSettings.setLocale(new Locale(lang.getKey()));
		WritableWorkbook wb = Workbook.createWorkbook(byteArrayOutputStream, wbSettings);

		//create the excel file
		WritableSheet sheet = wb.createSheet("Statistiques", wb.getNumberOfSheets());

		int row = 0;
		for (Map.Entry<String, String> entry : criteria.entrySet()) {
			sheet.addCell(new Label(
				0,
				row,
				entry.getKey()
			));

			sheet.addCell(new Label(
				1,
				row,
				entry.getValue()
			));
			row++;
		}
		row++;


		insertHeader(sheet, cellFormat, row);

		Vector2I cell = new Vector2I(0, row);
		for (Form form : awacCalculator.getForms()) {
			sheet.addCell(new Label(
				cell.getX(),
				cell.getY(),
				translate(form.getIdentifier(), CodeList.TRANSLATIONS_SURVEY, lang),
				cellFormat));

			cell.setY(cell.getY() + 1);

			for (QuestionSet questionSet : form.getQuestionSets()) {
				//TODO ??
				cell = computeQuestionSet(sheet, cell, questionSet, averageValueList, 0, lang);
			}

			cell.setY(cell.getY() + 1);
		}

		sheet.setColumnView(0, 600);
		sheet.setColumnView(1, 15);
		sheet.setColumnView(2, 30);
		sheet.setColumnView(3, 15);
		sheet.setColumnView(4, 15);
		sheet.setColumnView(5, 15);

		//create email an joint excel file
		wb.write();
		wb.close();

		return byteArrayOutputStream;


	}


	private List<AverageValue> computeAverage(QuestionSet questionSet, List<ScopeAndPeriod> scopeAndPeriodList, LanguageCode lang, RepetitionMap repetitionMap, Period period) {

		List<AverageValue> averageValueList = new ArrayList<>();

		//List<RepetitionMap> m = null;

		//test if this averages need to be computed by iteration
		//the response is yes if :
		// 1. the question if repeatable
		// 2. there is at least one question of selectQuestiontype
		// this question(s) will be used like referencing question(s)
		List<ValueSelectionQuestion> referencingQuestions = new ArrayList<>();

		if (questionSet.getRepetitionAllowed()) {
			for (Question question : questionSet.getQuestions()) {
				//test if this is  question to ignore
				boolean ignore = false;
				for (String s : questionToIgnore) {
					if (question.getCode().getKey().equals(s)) {
						ignore = true;
						break;
					}
				}

				if (!ignore &&
					questionSet.getRepetitionAllowed() &&
					question instanceof ValueSelectionQuestion) {
					referencingQuestions.add(((ValueSelectionQuestion) question));
				}
			}
		}

		//if the referencingQuestionList are more than one, compute average by iteration
		if (referencingQuestions.size() == 0) {

			//continue the tree if the questionSet is not repetable
			for (QuestionSet child : questionSet.getChildren()) {
				averageValueList.addAll(computeAverage(child, scopeAndPeriodList, lang, repetitionMap, period));
			}
			averageValueList.addAll(computeResponse(questionSet, scopeAndPeriodList, lang, null, period));
		} else {

			listQuestionSetWithRepetition.add(questionSet);

			//if there if a repetition but repetitionMap is not null, repetition to more than 1 level => not implemented
			if (repetitionMap != null) {
				throw new MyrmexRuntimeException("more than one repetition level => not implemented for average : " + questionSet.getCode().getKey());
			}

			for (ScopeAndPeriod scopeAndPeriod : scopeAndPeriodList) {


				//looking for questionSetAnswer
				List<QuestionSetAnswer> questionSetAnswerList = questionSetAnswerService.findByQuestionSetAndCalculatorInstance(questionSet, scopeAndPeriod);

				//for each, test answers from defined question
				for (QuestionSetAnswer questionSetAnswer : questionSetAnswerList) {


					boolean foundForAll = true;

					repetitionMap = new RepetitionMap();
					repetitionMap.addQuestionSetAnswer(questionSetAnswer);


					//for each defined question...
					for (ValueSelectionQuestion definedQuestion : referencingQuestions) {

						boolean founded = false;

						for (QuestionAnswer questionAnswer : questionSetAnswer.getQuestionAnswers()) {
							if (questionAnswer.getQuestion().getCode().equals(definedQuestion.getCode()) &&
								questionAnswer.getAnswerValues() != null &&
								questionAnswer.getAnswerValues().size() > 0) {

								CodeAnswerValue codeAnswerValue = ((CodeAnswerValue) questionAnswer.getAnswerValues().get(0));

								if (codeAnswerValue.getValue() != null) {
									repetitionMap.addValue(definedQuestion, codeAnswerValue.getValue());
									founded = true;
									break;
								}

							}
						}
						if (!founded) {
							foundForAll = false;
							break;
						}
					}

					if (foundForAll) {
						if (!repetitionMapList.containsKey(questionSet)) {
							repetitionMapList.put(questionSet, new ArrayList<RepetitionMap>());
						}
						if (repetitionMapList.get(questionSet).contains(repetitionMap)) {
							for (RepetitionMap repetitionMap1 : repetitionMapList.get(questionSet)) {
								if (repetitionMap1.equals(repetitionMap)) {
									repetitionMap1.addAllQuestionSet(repetitionMap.getQuestionSetAnswers());
								}
							}

						} else {
							repetitionMapList.get(questionSet).add(repetitionMap);
						}
					}


					//add t list

					//repetitionMapList.get(questionSet).add(repetitionMap);

				}
			}

			//continue the tree if the questionSet is not repetable
			for (QuestionSet child : questionSet.getChildren()) {
				if (repetitionMapList.get(questionSet) != null) {
					for (RepetitionMap repetitionMapToTest : repetitionMapList.get(questionSet)) {
						averageValueList.addAll(computeAverage(child, scopeAndPeriodList, lang, repetitionMapToTest, period));
					}
				}
			}

			//print by repetitionMap
			if (repetitionMapList.get(questionSet) != null) {
				for (RepetitionMap repetitionMapToTest : repetitionMapList.get(questionSet)) {
					averageValueList.addAll(computeResponse(questionSet, null, lang, repetitionMapToTest, period));
				}
			}
		}
		return averageValueList;
	}

	private List<AverageValue> computeResponse(QuestionSet questionSet, List<ScopeAndPeriod> scopeAndPeriodList, LanguageCode lang, RepetitionMap repetitionMap, Period period) {


		List<AverageValue> averageValueList = new ArrayList<>();


		for (Question question : questionSet.getQuestions()) {


			//load answer
			final List<QuestionAnswer> questionAnswerList;
			if (scopeAndPeriodList != null) {
				questionAnswerList = questionAnswerService.findByQuestionAndCalculatorInstance(question, scopeAndPeriodList);
			} else {
				if (repetitionMap.getQuestionSetAnswers() == null || repetitionMap.getQuestionSetAnswers().size() == 0) {
					return averageValueList;
				}
				questionAnswerList = questionAnswerService.findByQuestionAndQuestionSetAnswers(question, repetitionMap.getQuestionSetAnswers());
			}

			//compute response
			//YES / NO
			if (question instanceof BooleanQuestion) {
				int totalNo = 0;
				int totalYes = 0;
				for (QuestionAnswer questionAnswer : questionAnswerList) {
					for (AnswerValue answerValue : questionAnswer.getAnswerValues()) {
						if (((BooleanAnswerValue) answerValue).getValue() != null) {
							if (((BooleanAnswerValue) answerValue).getValue()) {
								totalYes++;
							} else {
								totalNo++;
							}
						}
					}
				}
				averageValueList.add(new AverageValue(question, totalYes, "Oui", repetitionMap));
				averageValueList.add(new AverageValue(question, totalNo, "non", repetitionMap));
			}

			// CODE
			else if (question instanceof ValueSelectionQuestion) {
				HashMap<Code, Integer> codeMap = new HashMap<>();
				for (QuestionAnswer questionAnswer : questionAnswerList) {
					for (AnswerValue answerValue : questionAnswer.getAnswerValues()) {
						if (((CodeAnswerValue) answerValue).getValue() != null) {
							if (codeMap.containsKey(((CodeAnswerValue) answerValue).getValue())) {
								codeMap.put(((CodeAnswerValue) answerValue).getValue(), (codeMap.get(((CodeAnswerValue) answerValue).getValue()) + 1));
							} else {
								codeMap.put(((CodeAnswerValue) answerValue).getValue(), 1);
							}
						}
					}
				}
				for (Map.Entry<Code, Integer> entry : codeMap.entrySet()) {
					averageValueList.add(new AverageValue(question, entry.getValue(), translate(entry.getKey().getKey(), entry.getKey().getCodeList(), lang), repetitionMap));
				}
			}

			// Double && percentage
			else if (question instanceof DoubleQuestion || question instanceof PercentageQuestion) {

				double addedValue = 0.0;
				int total = 0;
				double derivationSum = 0.0;
				List<Double> listValues = new ArrayList<>();
				Unit unit = null;

				if (question instanceof DoubleQuestion && ((DoubleQuestion) question).getUnitCategory() != null) {
					if (((DoubleQuestion) question).getDefaultUnit() != null) {
						unit = ((DoubleQuestion) question).getDefaultUnit();
					} else {
						unit = ((DoubleQuestion) question).getUnitCategory().getMainUnit();
					}
				}

				for (QuestionAnswer questionAnswer : questionAnswerList) {
					for (AnswerValue answerValue : questionAnswer.getAnswerValues()) {
						if (((DoubleAnswerValue) answerValue).getValue() != null) {

							double value = ((DoubleAnswerValue) answerValue).getValue();

							//convert
							if (unit != null) {
								value = convert(((DoubleAnswerValue) answerValue), unit, period);
							}

							listValues.add(value);
							total++;
							addedValue += value;
						}

					}
				}
				double average;
				double standardDeviation;
				if (total != 0) {
					average = addedValue / total;
					standardDeviation = standardDeviation(listValues, average, total);
				} else {
					average = 0.0;
					standardDeviation = 0.0;
				}

				averageValueList.add(new AverageValue(question, total, average, unit, standardDeviation, repetitionMap));

			}

			// Integer
			else if (question instanceof IntegerQuestion) {

				double addedValue = 0.0;
				int total = 0;
				double derivationSum = 0.0;
				List<Double> listValues = new ArrayList<>();
				Unit unit = null;


				if (question instanceof DoubleQuestion && ((DoubleQuestion) question).getUnitCategory() != null) {
					if (((DoubleQuestion) question).getDefaultUnit() != null) {
						unit = ((DoubleQuestion) question).getDefaultUnit();
					} else {
						unit = ((DoubleQuestion) question).getUnitCategory().getMainUnit();
					}
				}

				for (QuestionAnswer questionAnswer : questionAnswerList) {
					for (AnswerValue answerValue : questionAnswer.getAnswerValues()) {

						if (((IntegerAnswerValue) answerValue).getValue() != null) {

							double value = ((IntegerAnswerValue) answerValue).getValue();

							//convert
							if (unit != null) {
								value = convert(((IntegerAnswerValue) answerValue), unit, period);
							}
							listValues.add(value);
							total++;
							addedValue += value;

						}
					}
				}


				double average;
				double standardDeviation;
				if (total != 0) {
					average = addedValue / total;
					standardDeviation = standardDeviation(listValues, average, total);
				} else {
					average = 0.0;
					standardDeviation = 0.0;
				}

				averageValueList.add(new AverageValue(question, total, average, unit, standardDeviation, repetitionMap));

			} else {
				// String => useless ??
				// ENTITY => useless ??
				// DOCUMENT => useless ??
				int total = 0;
				for (QuestionAnswer questionAnswer : questionAnswerList) {
					for (AnswerValue answerValue : questionAnswer.getAnswerValues()) {

						if ((answerValue instanceof StringAnswerValue && ((StringAnswerValue) answerValue).getValue() != null) ||
							(answerValue instanceof DocumentAnswerValue && ((DocumentAnswerValue) answerValue).getStoredFile() != null) ||
							(answerValue instanceof EntityAnswerValue && ((EntityAnswerValue) answerValue).getEntityId() != null) ||
							(answerValue instanceof DateTimeAnswerValue && ((DateTimeAnswerValue) answerValue).getDateTime() != null)) {

							total++;

						}
					}
				}
				averageValueList.add(new AverageValue(question, total, repetitionMap));
			}
		}

		if (scopeAndPeriodList == null) {
			Logger.info("==>AVERAGE GENERATED : " + averageValueList.size());
			for (AverageValue averageValue : averageValueList) {
				Logger.info("==>" + averageValue);
			}
		}
		return averageValueList;
	}


	private void insertHeader(WritableSheet sheet, WritableCellFormat cellFormat, int x) throws WriteException {

		sheet.addCell(new Label(1, x, "# réponses", cellFormat));
		sheet.addCell(new Label(2, x, "Type", cellFormat));
		sheet.addCell(new Label(3, x, "Moyenne", cellFormat));
		sheet.addCell(new Label(4, x, "Unité", cellFormat));
		sheet.addCell(new Label(5, x, "Ecart-type", cellFormat));

	}

	private String translate(String code, CodeList cl, LanguageCode lang) {
		CodeLabel codeLabel = codeLabelService.findCodeLabelByCode(new Code(cl, code));
		if (codeLabel == null) {
			return code;
		} else {
			return codeLabel.getLabel(lang);
		}
	}


	private Vector2I computeQuestionSet(WritableSheet sheet, Vector2I cell, QuestionSet questionSet, List<AverageValue> averageValuesList, int indent, LanguageCode lang) throws WriteException {


		Logger.info("CELL : " + cell);
		Logger.info("indent : " + indentString + " " + indent);
		Logger.info("questionSet : " + questionSet);
		Logger.info("questionSet : " + questionSet);

		Label questionSetLabel = new Label(
			cell.getX(),
			cell.getY(),
			StringUtils.repeat(indentString, indent) + translate(questionSet.getCode().getKey(), CodeList.QUESTION, lang)
		);

		if (listQuestionSetWithRepetition.contains(questionSet)) {
			WritableCellFormat questionSetRepetableFormat = new WritableCellFormat();
			questionSetRepetableFormat.setBackground(Colour.LIGHT_BLUE);
			questionSetLabel.setCellFormat(questionSetRepetableFormat);
		}

		sheet.addCell(questionSetLabel);

		cell.setY(cell.getY() + 1);


		//if the questionSet is repetable, looking for repetitionMap
		if (listQuestionSetWithRepetition.contains(questionSet)) {
			if (repetitionMapList.get(questionSet) != null) {
				for (RepetitionMap repetitionMap : repetitionMapList.get(questionSet)) {
					cell = writeAnswer(questionSet, cell, sheet, averageValuesList, indent, lang, repetitionMap);
				}
			}
		} else {
			cell = writeAnswer(questionSet, cell, sheet, averageValuesList, indent, lang, null);
		}
		return cell;
	}

	private Vector2I writeAnswer(QuestionSet questionSet, Vector2I cell, WritableSheet sheet, List<AverageValue> averageValuesList, int indent, LanguageCode lang, RepetitionMap repetitionMap) throws WriteException {


		for (QuestionSet qs : questionSet.getChildren()) {
			cell = computeQuestionSet(sheet, cell, qs, averageValuesList, indent + 1, lang);
		}

		//found all average

		for (Question question : questionSet.getQuestions()) {

			Logger.info("CELL : " + cell);
			Logger.info("indent : " + indentString + " " + indent);
			Logger.info("questionSet : " + questionSet);
			Logger.info("questionSet : " + questionSet);

			sheet.addCell(new Label(
				cell.getX(),
				cell.getY(),
				StringUtils.repeat(indentString, indent + 1) + translate(question.getCode().getKey(), CodeList.QUESTION, lang)
			));

			boolean first = true;
			for (AverageValue averageValue : averageValuesList) {

				//if this is writeAnswer repetition, test the correspondance to the repetition map
				if ((repetitionMap == null || repetitionMap.equals(averageValue.repetitionMap)) &&
					averageValue.getQuestion().equals(question)) {


					WritableFont cellFont = new WritableFont(WritableFont.ARIAL, 10);
					cellFont.setItalic(true);
					WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
					cellFormat.setAlignment(jxl.format.Alignment.LEFT);
					cellFormat.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);

					if (first) {
						first = false;
					} else {
						cell.setY(cell.getY() + 1);
					}

					//nb response
					sheet.addCell(new Number(
						cell.getX() + 1,
						cell.getY(),
						averageValue.getNbAnswer()
					));

					//type
					if (averageValue.getType() != null) {
						sheet.addCell(new jxl.write.Label(
							cell.getX() + 2,
							cell.getY(),
							averageValue.getType()
						));
					}

					//average
					if (averageValue.getAverage() != null) {
						sheet.addCell(new Number(
							cell.getX() + 3,
							cell.getY(),
							averageValue.getAverage()
						));
					}
					//unit
					if (averageValue.getUnit() != null) {
						sheet.addCell(new jxl.write.Label(
							cell.getX() + 4,
							cell.getY(),
							averageValue.getUnit().getSymbol()
						));
					}
					//standard deviation
					if (averageValue.getStandardDeviation() != null) {
						sheet.addCell(new Number(
							cell.getX() + 5,
							cell.getY(),
							averageValue.getStandardDeviation()
						));
					}
				}
			}

			cell.setY(cell.getY() + 1);
		}

		return cell;
	}


	private Double convert(NumericAnswerValue answerValue, Unit toUnit, Period period) {

		if (toUnit == null && answerValue.getUnit() == null) {
			return answerValue.doubleValue();
		}
		if (toUnit == null || answerValue.getUnit() == null) {
			throw new MyrmexFatalException("cannot convert ");
		}
		if (answerValue.getUnit().equals(toUnit)) {
			return answerValue.doubleValue();
		}
		if (answerValue.getUnit().getCategory() == null || toUnit.getCategory() == null) {
			throw new MyrmexFatalException("Question " + answerValue.getQuestionAnswer().getQuestion().getCode().getKey() + ", answer:" + answerValue.getId() + ": not cat for unit :  " + answerValue.getUnit().getSymbol() + " or " + toUnit.getSymbol());

		}
		//convert
		return unitConversionService.convert(answerValue.doubleValue(), answerValue.getUnit(), toUnit, Integer.valueOf(period.getPeriodCode().getKey()));
	}

	private Double standardDeviation(List<Double> values, double average, int total) {

		double value = 0.0;

		for (Double d : values) {
			value += (d - average) * (d - average);
		}
		value = value / total;

		return Math.sqrt(value);
	}


	protected CodeListDTO toCodeListDTO(CodeList codeList, LanguageCode lang) {
		List<CodeLabel> codeLabels = new ArrayList<>(codeLabelService.findCodeLabelsByList(codeList).values());
		List<CodeLabelDTO> codeLabelDTOs = new ArrayList<>();
		for (CodeLabel codeLabel : codeLabels) {
			codeLabelDTOs.add(new CodeLabelDTO(codeLabel.getKey(), codeLabel.getLabel(lang)));
		}
		return new CodeListDTO(codeList.name(), codeLabelDTOs);
	}


	private static class RepetitionMap {

		/**
		 * list of questions referenced for the repetition + response
		 */
		private HashMap<ValueSelectionQuestion, Code> values = new HashMap<>();
		/*
		 * questionSetAnswer linked for this repetition
		 */
		private List<QuestionSetAnswer> questionSetAnswers = new ArrayList<>();

		public void addValue(ValueSelectionQuestion valueSelectionQuestion, Code code) {
			values.put(valueSelectionQuestion, code);
		}

		public HashMap<ValueSelectionQuestion, Code> getValues() {
			return values;
		}

		public void setValues(HashMap<ValueSelectionQuestion, Code> values) {
			this.values = values;
		}


		public void addQuestionSetAnswer(QuestionSetAnswer questionSetAnswer) {
			if (questionSetAnswers == null) {
				questionSetAnswers = new ArrayList<>();
			}
			questionSetAnswers.add(questionSetAnswer);
		}

		public void addAllQuestionSet(List<QuestionSetAnswer> questionSetAnswers) {
			if (this.questionSetAnswers == null) {
				this.questionSetAnswers = new ArrayList<>();
			}
			this.questionSetAnswers.addAll(questionSetAnswers);

		}

		public List<QuestionSetAnswer> getQuestionSetAnswers() {
			return questionSetAnswers;
		}

		@Override
		public boolean equals(Object o) {
			if (o instanceof RepetitionMap) {
				for (Map.Entry<ValueSelectionQuestion, Code> entry : values.entrySet()) {
					boolean founded = false;
					for (Map.Entry<ValueSelectionQuestion, Code> entry2 : ((RepetitionMap) o).getValues().entrySet()) {
						if (entry.getKey().equals(entry2.getKey()) && entry.getValue().equals(entry2.getValue())) {
							founded = true;
							break;
						}
					}
					if (!founded) {
						return false;
					}
				}
			}
			return true;
		}


		@Override
		public String toString() {
			String value = "";
			for (Map.Entry<ValueSelectionQuestion, Code> entry : values.entrySet()) {
				value += "{" + entry.getKey().getCode().getKey() + "," + entry.getValue().getKey() + "}";
			}
			String setAnswer = "";
			for (QuestionSetAnswer questionSetAnswer : questionSetAnswers) {
				setAnswer += "/" + questionSetAnswer.getId();
			}


			return "Com{" +
				"values=" + value +
				",questionSetAnswers=" + setAnswer +
				'}';
		}

	}


	private static class AverageValue {

		private final Question question;
		private final int nbAnswer;
		private String type = null;
		private Double average = null;
		private Unit unit = null;
		private Double standardDeviation = null;
		private RepetitionMap repetitionMap = null;

		private AverageValue(Question question, int nbAnswer, RepetitionMap repetitionMap) {
			this.question = question;
			this.nbAnswer = nbAnswer;
			this.repetitionMap = repetitionMap;
		}


		private AverageValue(Question question, int nbAnswer, String type, RepetitionMap repetitionMap) {
			this.question = question;
			this.nbAnswer = nbAnswer;
			this.type = type;
			this.repetitionMap = repetitionMap;
		}


		private AverageValue(Question question, int nbAnswer, Double average, Unit unit, Double standardDeviation, RepetitionMap repetitionMap) {
			this.question = question;
			this.nbAnswer = nbAnswer;
			this.average = average;
			this.unit = unit;
			this.standardDeviation = standardDeviation;
			this.repetitionMap = repetitionMap;
		}

		public Question getQuestion() {
			return question;
		}

		public int getNbAnswer() {
			return nbAnswer;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public Double getAverage() {
			return average;
		}

		public void setAverage(Double average) {
			this.average = average;
		}

		public Unit getUnit() {
			return unit;
		}

		public void setUnit(Unit unit) {
			this.unit = unit;
		}

		public Double getStandardDeviation() {
			return standardDeviation;
		}

		public void setStandardDeviation(Double standardDeviation) {
			this.standardDeviation = standardDeviation;
		}

		@Override
		public String toString() {
			return "AverageValue{" +
				"question=" + question.getCode().getKey() +
				", nbAnswer=" + nbAnswer +
				", type='" + type + '\'' +
				", average=" + average +
				", unit=" + ((unit != null) ? unit.getSymbol() : "") +
				", standardDeviation=" + standardDeviation +
				", com=" + repetitionMap +
				'}';
		}
	}

} // end of class

