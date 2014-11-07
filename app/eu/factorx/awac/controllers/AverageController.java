package eu.factorx.awac.controllers;

import eu.factorx.awac.dto.awac.get.CodeLabelDTO;
import eu.factorx.awac.dto.awac.get.CodeListDTO;
import eu.factorx.awac.dto.awac.get.ResultsDTO;
import eu.factorx.awac.dto.awac.post.ComputeAverageDTO;
import eu.factorx.awac.dto.awac.shared.ListDTO;
import eu.factorx.awac.dto.myrmex.get.ExceptionsDTO;
import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.business.Product;
import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.business.Site;
import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.code.label.CodeLabel;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.models.code.type.LanguageCode;
import eu.factorx.awac.models.code.type.PeriodCode;
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
import eu.factorx.awac.util.BusinessErrorType;
import eu.factorx.awac.util.MyrmexFatalException;
import eu.factorx.awac.util.MyrmexRuntimeException;
import eu.factorx.awac.util.email.messages.EmailMessage;
import eu.factorx.awac.util.email.service.EmailService;
import eu.factorx.awac.util.math.Vector2I;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.*;
import jxl.write.Number;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import play.db.jpa.Transactional;
import play.libs.F;
import play.mvc.Result;
import play.mvc.Security;

import java.io.*;
import java.util.*;
import static play.libs.F.Promise.promise;

import play.libs.F.*;
import play.mvc.*;
import java.util.concurrent.Callable;

import static play.libs.F.Promise.promise;

/**
 * Created by florian on 5/11/14.
 */
@org.springframework.stereotype.Controller
public class AverageController extends AbstractController {

	@Autowired
	private AwacCalculatorInstanceService awacCalculatorInstanceService;
	@Autowired
	private AwacCalculatorService awacCalculatorService;
	@Autowired
	private QuestionSetAnswerService questionSetAnswerService;
	@Autowired
	private QuestionAnswerService questionAnswerService;
	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private UnitConversionService unitConversionService;
	@Autowired
	private VelocityGeneratorService velocityGeneratorService;
	@Autowired
	private EmailService emailService;
	@Autowired
	private PeriodService periodService;
	@Autowired
	private QuestionSetService questionSetService;

	@Transactional(readOnly = true)
	@Security.Authenticated(SecuredController.class)
	public Result getNaceCodeList() throws IOException, WriteException {

		if (!securedController.getCurrentUser().getOrganization().getInterfaceCode().equals(InterfaceTypeCode.ADMIN)) {
			throw new MyrmexRuntimeException(BusinessErrorType.WRONG_INTERFACE_FOR_USER);
		}

		ListDTO<CodeListDTO> list = new ListDTO<>();

		list.add(toCodeListDTO(CodeList.SECTEURPRIMAIRE, securedController.getCurrentUser().getPerson().getDefaultLanguage()));
		list.add(toCodeListDTO(CodeList.SECTEURSECONDAIRE, securedController.getCurrentUser().getPerson().getDefaultLanguage()));
		list.add(toCodeListDTO(CodeList.SECTEURTERTIAIRE, securedController.getCurrentUser().getPerson().getDefaultLanguage()));

		return ok(list);
	}
/* TODO temp
	public Result computeAverage() {
		return async(
				promise(new Function0<Integer>() {
					public Integer apply() {
						return 2;
					}
				}).map(new Function<Integer,Result>() {
					public Result apply(Integer i) {
						//return ok("Got " + i);
						return ok(new ResultsDTO());
					}
				})
		);
	}
*/
	@Transactional(readOnly = true)
	@Security.Authenticated(SecuredController.class)
	public Result computeAverage() throws IOException, WriteException {


		if (!securedController.getCurrentUser().getOrganization().getInterfaceCode().equals(InterfaceTypeCode.ADMIN)) {
			throw new MyrmexRuntimeException(BusinessErrorType.WRONG_INTERFACE_FOR_USER);
		}

		ComputeAverageDTO dto = extractDTOFromRequest(ComputeAverageDTO.class);

		//convert data
		//TODO
		final AwacCalculator awacCalculator = awacCalculatorService.findByCode(new InterfaceTypeCode(dto.getInterfaceName()));
		final Period period = periodService.findByCode(new PeriodCode(dto.getPeriodKey()));


		//compute scopeAndPEriod list
		final List<ScopeAndPeriod> scopeAndPeriodList = new ArrayList<>();

		// load all organization
		List<Organization> organizationList = organizationService.findByInterfaceTypeCode(awacCalculator.getInterfaceTypeCode());

		Integer organizationComputed = 0;
		Integer scopeComputed = 0;

		//looking for scope
		for (Organization organization : organizationList) {

			//control organization
			if (organization.getStatisticsAllowed() != null && organization.getStatisticsAllowed()) {
				organizationComputed++;

				if (awacCalculator.getInterfaceTypeCode().equals(InterfaceTypeCode.ENTERPRISE)) {
					for (Site site : organization.getSites()) {
						for (Period periodToTest : site.getListPeriodAvailable()) {

							//control period
							if (periodToTest.equals(period)) {

								//for organization : found awaccalcultor and check if there is closed
								if (awacCalculatorInstanceService.findByCalculatorAndPeriodAndScope(awacCalculator, period, site) != null) {

									//control NACE code
									if (computeNace(dto.getNaceCodeListKey(), dto.getNaceCodeKey(), site, period)) {
										scopeComputed++;
										scopeAndPeriodList.add(new ScopeAndPeriod(site, periodToTest));
									}
								}
							}
						}
					}
				} else if (awacCalculator.getInterfaceTypeCode().equals(InterfaceTypeCode.MUNICIPALITY)) {

					for (Period periodToTest : periodService.findAll()) {

						//control period
						if (periodToTest.equals(period)) {

							//for municipality : found awaccalcultor and check if there is closed
							if (awacCalculatorInstanceService.findByCalculatorAndPeriodAndScope(awacCalculator, period, organization) != null) {

								//control
								scopeAndPeriodList.add(new ScopeAndPeriod(organization, periodToTest));
							}
						}
					}
				} else if (awacCalculator.getInterfaceTypeCode().equals(InterfaceTypeCode.EVENT)) {
					for (Product product : organization.getProducts()) {
						for (Period periodToTest : product.getListPeriodAvailable()) {

							scopeComputed++;

							//control period
							if (periodToTest.equals(period)) {

								//control
								scopeAndPeriodList.add(new ScopeAndPeriod(product, periodToTest));
							}
						}
					}
				} else {
					//for house and little
					for (Period periodToTest : periodService.findAll()) {

						//control period
						if (periodToTest.equals(period)) {

							//control
							scopeAndPeriodList.add(new ScopeAndPeriod(organization, periodToTest));
						}
					}
				}
			}
		}

		if(scopeAndPeriodList.size()==0){
			//TODO
			return ok(new ExceptionsDTO("Il n'y a aucun questionnaire qui correspond aux critères demandées"));
		}
		else{

			return async(
					promise(new Function0<Integer>() {
						public Integer apply() {

							try {
								computeAverage(awacCalculator,scopeAndPeriodList,period,1,1);//TODO,organizationComputed,scopeComputed);
							} catch (IOException e) {
								e.printStackTrace();
							} catch (WriteException e) {
								e.printStackTrace();
							}

							return 2;
						}
					}).map(new Function<Integer, Result>() {
						public Result apply(Integer i) {
							//TODO
							return ok(new ExceptionsDTO("Les moyennes sont en cours de calcul. Vous recevrez le résultat sur votre adresse e-mail dans quelques minutes"));
						}
					})
			);
		}


	}


	private void computeAverage(AwacCalculator awacCalculator, List<ScopeAndPeriod> scopeAndPeriodList, Period period, int organizationComputed, int scopeComputed) throws IOException, WriteException {


		//for each question, compute average
		//compute form by form
		List<AverageValue> averageValueList = new ArrayList<>();
		for (Form form : awacCalculator.getForms()) {
			for (QuestionSet questionSet : form.getQuestionSets()) {
				averageValueList.addAll(computeAverage(questionSet, scopeAndPeriodList, securedController.getCurrentUser().getPerson().getDefaultLanguage()));
			}
		}

		//
		// EXCEL FILE
		//
		//compute criteria
		HashMap<String, String> criteria = new HashMap<>();
		criteria.put("Calculateur", awacCalculator.getInterfaceTypeCode().

						getKey()

		);
		criteria.put("Periode", period.getLabel());
		criteria.put("Nomber de formulaire pris en compte", scopeAndPeriodList.size() + "");
		criteria.put("Organisations prises en compte", organizationComputed + "");

		if (awacCalculator.getInterfaceTypeCode().equals(InterfaceTypeCode.ENTERPRISE)) {
			criteria.put("Sites pris en compte", scopeComputed + "");
		} else if (awacCalculator.getInterfaceTypeCode().equals(InterfaceTypeCode.EVENT)) {
			criteria.put("Evenements pris en compte", scopeComputed + "");
		}

		ByteArrayOutputStream output = generateExcel(awacCalculator, averageValueList, securedController.getCurrentUser().getPerson().getDefaultLanguage(), criteria);


		//send email
		Map<String, Object> values = new HashMap<>();
		/*
		values.put("request", calculatorInstance.getVerificationRequest());
	    values.put("user", securedController.getCurrentUser());
	    */
		String velocityContent = velocityGeneratorService.generate("verification/average.vm", values);

		EmailMessage email = new EmailMessage(securedController.getCurrentUser().getPerson().getEmail(), "Awac - moyenne", velocityContent);
		//


		FileOutputStream outputs = new FileOutputStream(new File("/home/florian/temp/result.xls"));
		IOUtils.write(output.toByteArray(), outputs);

		//send email
		HashMap<String, ByteArrayOutputStream> listAttachment = new HashMap<>();
		listAttachment.put("average.xls", output);
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
		WritableSheet sheet = wb.createSheet("Moyenne", wb.getNumberOfSheets());

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
				cell = writePartBorder(sheet, cell, questionSet, averageValueList, 0, lang);
			}

			cell.setY(cell.getY() + 1);
		}

		sheet.setColumnView(0, 600);

		//create email an joint excel file
		wb.write();
		wb.close();

		return byteArrayOutputStream;


	}


	private List<AverageValue> computeAverage(QuestionSet questionSet, List<ScopeAndPeriod> scopeAndPeriodList, LanguageCode lang) {

		List<AverageValue> averageValueList = new ArrayList<>();

		for (QuestionSet child : questionSet.getChildren()) {
			averageValueList.addAll(computeAverage(child, scopeAndPeriodList, lang));
		}

		for (Question question : questionSet.getQuestions()) {

			//load answer
			List<QuestionAnswer> questionAnswerList = questionAnswerService.findByQuestionAndCalculatorInstance(question, scopeAndPeriodList);

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
				averageValueList.add(new AverageValue(question, totalYes, "Oui"));
				averageValueList.add(new AverageValue(question, totalNo, "non"));
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
					averageValueList.add(new AverageValue(question, entry.getValue(), translate(entry.getKey().getKey(), entry.getKey().getCodeList(), lang)));
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
								value = convert(((DoubleAnswerValue) answerValue), unit);
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

				averageValueList.add(new AverageValue(question, total, average, unit, standardDeviation));

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
								value = convert(((IntegerAnswerValue) answerValue), unit);
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

				averageValueList.add(new AverageValue(question, total, average, unit, standardDeviation));

			} else {
				// String => useless ??
				// ENTITY => useless ??
				// DOCUMENT => useless ??
				int total = 0;
				for (QuestionAnswer questionAnswer : questionAnswerList) {
					for (AnswerValue answerValue : questionAnswer.getAnswerValues()) {

						if ((answerValue instanceof StringAnswerValue && ((StringAnswerValue) answerValue).getValue() != null) ||
								(answerValue instanceof DocumentAnswerValue && ((DocumentAnswerValue) answerValue).getStoredFile() != null) ||
								(answerValue instanceof EntityAnswerValue && ((EntityAnswerValue) answerValue).getEntityId() != null)) {

							total++;

						}
					}
				}
				averageValueList.add(new AverageValue(question, total));
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

	private static class AverageValue {

		private final Question question;
		private final int nbAnswer;
		private String type = null;
		private Double average = null;
		private Unit unit = null;
		private Double standardDeviation = null;

		private AverageValue(Question question, int nbAnswer) {
			this.question = question;
			this.nbAnswer = nbAnswer;
		}

		private AverageValue(Question question, int nbAnswer, String type) {
			this.question = question;
			this.nbAnswer = nbAnswer;
			this.type = type;
		}


		private AverageValue(Question question, int nbAnswer, Double average, Unit unit, Double standardDeviation) {
			this.question = question;
			this.nbAnswer = nbAnswer;
			this.average = average;
			this.unit = unit;
			this.standardDeviation = standardDeviation;
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
					"question=" + question +
					", nbAnswer=" + nbAnswer +
					", type='" + type + '\'' +
					", average=" + average +
					", unit=" + unit +
					", standardDeviation=" + standardDeviation +
					'}';
		}
	}

	private String translate(String code, CodeList cl, LanguageCode lang) {
		CodeLabel codeLabel = codeLabelService.findCodeLabelByCode(new Code(cl, code));
		if (codeLabel == null) {
			return code;
		} else {
			return codeLabel.getLabel(lang);
		}
	}


	private Vector2I writePartBorder(WritableSheet sheet, Vector2I cell, QuestionSet questionSet, List<AverageValue> averageValuesList, int indent, LanguageCode lang) throws WriteException {

		final String indentString = "        ";

		sheet.addCell(new Label(
				cell.getX(),
				cell.getY(),
				StringUtils.repeat(indentString, indent) + translate(questionSet.getCode().getKey(), CodeList.QUESTION, lang)
		));

		cell.setY(cell.getY() + 1);

		for (QuestionSet qs : questionSet.getChildren()) {
			cell = writePartBorder(sheet, cell, qs, averageValuesList, indent + 1, lang);
		}

		//found all average

		for (Question question : questionSet.getQuestions()) {

			sheet.addCell(new Label(
					cell.getX(),
					cell.getY(),
					StringUtils.repeat(indentString, indent + 1) + translate(question.getCode().getKey(), CodeList.QUESTION, lang)
			));

			boolean first = true;
			for (AverageValue averageValue : averageValuesList) {
				if (averageValue.getQuestion().equals(question)) {


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

	public static class ScopeAndPeriod {

		private final Scope scope;

		private final Period period;

		private ScopeAndPeriod(Scope scope, Period period) {
			this.scope = scope;
			this.period = period;
		}

		public Scope getScope() {
			return scope;
		}

		public Period getPeriod() {
			return period;
		}

		@Override
		public String toString() {
			return "ScopeAndPeriod{" +
					"scope=" + scope +
					", period=" + period +
					'}';
		}

	}


	private Double convert(NumericAnswerValue answerValue, Unit toUnit) {

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
		return unitConversionService.convert(answerValue.doubleValue(), answerValue.getUnit(), toUnit, null);
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
}
