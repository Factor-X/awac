package eu.factorx.awac.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;

import play.Logger;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import eu.factorx.awac.converter.QuestionAnswerToAnswerLineConverter;
import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.awac.get.*;
import eu.factorx.awac.dto.awac.post.AnswerLineDTO;
import eu.factorx.awac.dto.awac.post.FormProgressDTO;
import eu.factorx.awac.dto.awac.post.QuestionAnswersDTO;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.code.label.CodeLabel;
import eu.factorx.awac.models.code.type.LanguageCode;
import eu.factorx.awac.models.code.type.QuestionCode;
import eu.factorx.awac.models.code.type.ScopeTypeCode;
import eu.factorx.awac.models.data.FormProgress;
import eu.factorx.awac.models.data.answer.AnswerValue;
import eu.factorx.awac.models.data.answer.QuestionAnswer;
import eu.factorx.awac.models.data.answer.QuestionSetAnswer;
import eu.factorx.awac.models.data.answer.type.BooleanAnswerValue;
import eu.factorx.awac.models.data.answer.type.CodeAnswerValue;
import eu.factorx.awac.models.data.answer.type.DocumentAnswerValue;
import eu.factorx.awac.models.data.answer.type.DoubleAnswerValue;
import eu.factorx.awac.models.data.answer.type.EntityAnswerValue;
import eu.factorx.awac.models.data.answer.type.IntegerAnswerValue;
import eu.factorx.awac.models.data.answer.type.StringAnswerValue;
import eu.factorx.awac.models.data.file.StoredFile;
import eu.factorx.awac.models.data.question.Question;
import eu.factorx.awac.models.data.question.QuestionSet;
import eu.factorx.awac.models.data.question.type.DoubleQuestion;
import eu.factorx.awac.models.data.question.type.EntitySelectionQuestion;
import eu.factorx.awac.models.data.question.type.IntegerQuestion;
import eu.factorx.awac.models.data.question.type.ValueSelectionQuestion;
import eu.factorx.awac.models.forms.Form;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.models.knowledge.Unit;
import eu.factorx.awac.models.knowledge.UnitCategory;
import eu.factorx.awac.service.*;

@org.springframework.stereotype.Controller
public class AnswerController extends Controller {

	private static final String ERROR_ANSWER_UNIT_NOT_AUTHORIZED = "The question identified by key '%s' does not accept unit, since a unit (id = %s) is present in client answer";
	private static final String ERROR_ANSWER_UNIT_REQUIRED = "The question identified by key '%s' requires a unit of the category '%s', but no unit is present in client answer";
	private static final String ERROR_ANSWER_UNIT_INVALID = "The question identified by key '%s' requires a unit of the category '%s', since the unit of the client answer is '%s' (part of the category '%s')";

	@Autowired
	private PeriodService periodService;
	@Autowired
	private ScopeService scopeService;
	@Autowired
	private QuestionSetAnswerService questionSetAnswerService;
	@Autowired
	private QuestionService questionService;
	@Autowired
	private QuestionAnswerService questionAnswerService;
	@Autowired
	private UnitCategoryService unitCategoryService;
	@Autowired
	private UnitService unitService;
	@Autowired
	private FormService formService;
	@Autowired
	private CodeLabelService codeLabelService;
	@Autowired
	private ConversionService conversionService;
	@Autowired
	private SecuredController securedController;
	@Autowired
	private StoredFileService storedFileService;
	@Autowired
	private FormProgressService formProgressService;

	@Transactional(readOnly = true)
	@Security.Authenticated(SecuredController.class)
	public Result getByForm(String formIdentifier, Long periodId, Long scopeId) {

		Form form = formService.findByIdentifier(formIdentifier);
		Period period = periodService.findById(periodId);
		Scope scope = scopeService.findById(scopeId);

		// TODO The user language should be saved in a session attribute, and it should be possible to update it with a request parameter (change language request).
		// Without connection, the user language should be obtained from a cookie or from browser "accepted languages" request header.
		LanguageCode lang = LanguageCode.ENGLISH;

		if (form == null || period == null || scope == null) {
			throw new RuntimeException("Invalid request params");
		}

		Map<Long, UnitCategoryDTO> unitCategoryDTOs = getAllUnitCategories();

		Map<String, CodeListDTO> codeListDTOs = getNecessaryCodeLists(form.getAllQuestionSets(), lang);

		List<QuestionSetDTO> questionSetDTOs = toQuestionSetDTOs(form.getQuestionSets());

		List<QuestionAnswer> questionAnswers = questionAnswerService.findByParameters(new QuestionAnswerSearchParameter().appendForm(form).appendPeriod(period).appendScope(scope));
		List<AnswerLineDTO> answerLineDTOs = toAnswerLineDTOs(questionAnswers);

		Logger.info("GET '{}' Data:", form.getIdentifier());
		for (AnswerLineDTO answerLineDTO : answerLineDTOs) {
			Logger.info("\t" + answerLineDTO);
		}

		QuestionAnswersDTO questionAnswersDTO = new QuestionAnswersDTO(form.getId(), scopeId, periodId, getMaxLastUpdateDate(questionAnswers), answerLineDTOs);

		return ok(new FormDTO(unitCategoryDTOs, codeListDTOs, questionSetDTOs, questionAnswersDTO));
	}

	@Transactional(readOnly = true)
	@Security.Authenticated(SecuredController.class)
	public Result getPeriodsForComparison(Long scopeId) {
		List<PeriodDTO> periodDTOs = new ArrayList<>();
		List<Period> periods = questionSetAnswerService.getAllQuestionSetAnswersPeriodsByScope(scopeId);
		for (Period period : periods) {
			periodDTOs.add(conversionService.convert(period, PeriodDTO.class));
		}
		return ok(new ListPeriodsDTO(periodDTOs));
	}

	@Transactional(readOnly = false)
	@Security.Authenticated(SecuredController.class)
	public Result save() {
		Account currentUser = securedController.getCurrentUser();
		QuestionAnswersDTO answersDTO = extractDTOFromRequest(QuestionAnswersDTO.class);

		// validate context
		Form form = formService.findById(answersDTO.getFormId());
		Period period = periodService.findById(answersDTO.getPeriodId());
		Scope scope = scopeService.findById(answersDTO.getScopeId());
		if (form == null || period == null || scope == null) {
			throw new RuntimeException("Invalid request parameters : " + answersDTO);
		}

		// validate user organization
		validateUserRightsForScope(currentUser, scope);

		// log posted data (...and create an empty mapRepetition if the property is null; easier for comparisons)
		Logger.info("POST '{}' Data:", form.getIdentifier());
		for (AnswerLineDTO answerLine : answersDTO.getListAnswers()) {
			Logger.info("\t" + answerLine);
			if (answerLine.getMapRepetition() == null) {
				answerLine.setMapRepetition(new HashMap<String, Integer>());
			}
		}

		// create, update or delete QuestionAnswers and QuestionSetAnswers
		saveAnswsersDTO(currentUser, form, period, scope, answersDTO.getListAnswers());

		return ok(new SaveAnswersResultDTO());
	}

	@Transactional(readOnly = true)
	@Security.Authenticated(SecuredController.class)
	public Result getFormProgress(Long periodId, Long scopeId) {

		// 1. control the owner of the scope
		Scope scope = scopeService.findById(scopeId);

		if (!scope.getOrganization().equals(securedController.getCurrentUser().getOrganization())) {
			throw new RuntimeException("This scope doesn't owned by your organization");
		}

		// 2. load period
		Period period = periodService.findById(periodId);

		// 3. load formProgress
		List<FormProgress> formProgressList = formProgressService.findByPeriodAndByScope(period, scope);

		return ok(conversionService.convert(formProgressList, FormProgressListDTO.class));

	}

	@Transactional(readOnly = false)
	@Security.Authenticated(SecuredController.class)
	public Result setFormProgress() {

		Logger.info("public Result setFormProgress(){");

		FormProgressDTO formProgressDTO = extractDTOFromRequest(FormProgressDTO.class);

		Period period = periodService.findById(formProgressDTO.getPeriod());
		Scope scope = scopeService.findById(formProgressDTO.getScope());
		Form form = formService.findByIdentifier(formProgressDTO.getForm());

		// control percentage
		Integer percentage = formProgressDTO.getPercentage();
		if (percentage < 0 || percentage > 100) {
			throw new RuntimeException("Percentage must be more than 0 and less than 100. Currently : " + percentage);
		}

		// control scope
		if (!scope.getOrganization().equals(securedController.getCurrentUser().getOrganization())) {
			throw new RuntimeException("This scope doesn't owned by your organization. Scope asked : " + scope.toString());
		}

		// try to load
		FormProgress formProgress = formProgressService.findByPeriodAndByScopeAndForm(period, scope, form);

		// update...
		if (formProgress != null) {
			formProgress.setPercentage(percentage);
		}
		// ...or create a new formProgress
		else {
			formProgress = new FormProgress(period, form, scope, percentage);
		}

		formProgressService.saveOrUpdate(formProgress);

		return ok();
	}

	private List<AnswerLineDTO> toAnswerLineDTOs(List<QuestionAnswer> allQuestionAnswers) {
		List<AnswerLineDTO> answerLineDTOs = new ArrayList<>();
		for (QuestionAnswer questionAnswer : allQuestionAnswers) {
			answerLineDTOs.add(conversionService.convert(questionAnswer, AnswerLineDTO.class));
		}
		return answerLineDTOs;
	}

	private List<QuestionSetDTO> toQuestionSetDTOs(List<QuestionSet> questionSets) {
		List<QuestionSetDTO> questionSetDTOs = new ArrayList<>();
		for (QuestionSet questionSet : questionSets) {
			questionSetDTOs.add(conversionService.convert(questionSet, QuestionSetDTO.class));
		}
		return questionSetDTOs;
	}

	private Map<String, CodeListDTO> getNecessaryCodeLists(List<QuestionSet> questionSets, LanguageCode lang) {
		Map<String, CodeListDTO> codeLists = new HashMap<>();
		for (QuestionSet questionSet : questionSets) {
			for (Question question : questionSet.getQuestions()) {
				if (question instanceof ValueSelectionQuestion) {
					CodeList codeList = ((ValueSelectionQuestion) question).getCodeList();
					String codeListName = codeList.name();
					if (!codeLists.containsKey(codeListName)) {
						codeLists.put(codeListName, toCodeListDTO(codeList, lang));
					}
				}
			}
		}
		return codeLists;
	}

	private CodeListDTO toCodeListDTO(CodeList codeList, LanguageCode lang) {
		List<CodeLabel> codeLabels = codeLabelService.findCodeLabelsByList(codeList);
		if (codeLabels == null) {
			throw new RuntimeException("No code labels for the code list: " + codeList);
		}
		List<CodeLabelDTO> codeLabelDTOs = new ArrayList<>();
		for (CodeLabel codeLabel : codeLabels) {
			codeLabelDTOs.add(new CodeLabelDTO(codeLabel.getKey(), codeLabel.getLabel(lang)));
		}
		return new CodeListDTO(codeList.name(), codeLabelDTOs);
	}

	private Map<Long, UnitCategoryDTO> getAllUnitCategories() {
		Map<Long, UnitCategoryDTO> res = new HashMap<>();
		for (UnitCategory unitCategory : unitCategoryService.findAll()) {
			UnitCategoryDTO unitCategoryDTO = new UnitCategoryDTO(unitCategory.getId());
			unitCategoryDTO.setMainUnitId(unitCategory.getMainUnit().getId());
			for (Unit unit : unitCategory.getUnits()) {
				unitCategoryDTO.addUnit(new UnitDTO(unit.getId(), unit.getSymbol()));
			}
			res.put(unitCategoryDTO.getId(), unitCategoryDTO);
		}
		return res;
	}

	private void saveAnswsersDTO(Account currentUser, Form form, Period period, Scope scope, List<AnswerLineDTO> answerLineDTOs) {

		// existing question answers to update or delete
		Logger.info("saveAnswsersDTO() - (1) - Update or delete existing QuestionAnswers...");
		List<QuestionSetAnswer> questionSetAnswers = questionSetAnswerService.findByParameters(new QuestionSetAnswerSearchParameter(true).appendForm(form).appendPeriod(period).appendScope(scope));
		updateOrDeleteQuestionAnswers(questionSetAnswers, answerLineDTOs);

		// new question answers
		Logger.info("saveAnswsersDTO() - (2) - Save new QuestionAnswers...");
		for (AnswerLineDTO answerLineDTO : answerLineDTOs) {
			createQuestionAnswer(currentUser, period, scope, answerLineDTO, questionSetAnswers);
		}

		// cleaning: delete empty QuestionSetAnswers (without QuestionAnswers)
		Logger.info("saveAnswsersDTO() - (3) - Find and delete empty QuestionSetAnswers...");
		JPA.em().flush();
		questionSetAnswerService.deleteEmptyQuestionSetAnswers(scope, period, form);
	}

	/**
	 * For each {@link QuestionSetAnswer} in given list, iterate over all questionAnswer, and check if there is a matching {@link AnswerLineDTO} in answerLinesDTOs. If there is a
	 * matching, update or delete the {@link QuestionAnswer}.
	 * 
	 * @param questionSetAnswers
	 * @param answerLineDTOs
	 */
	private void updateOrDeleteQuestionAnswers(List<QuestionSetAnswer> questionSetAnswers, List<AnswerLineDTO> answerLineDTOs) {
		for (QuestionSetAnswer questionSetAnswer : questionSetAnswers) {
			List<QuestionAnswer> questionAnswers = questionSetAnswer.getQuestionAnswers();
			for (int i = questionAnswers.size() - 1; i >= 0; i--) {
				QuestionAnswer questionAnswer = questionAnswers.get(i);
				AnswerLineDTO answerLineDTO = getMatchingAnswerLineDTO(questionAnswer, answerLineDTOs);
				if (answerLineDTO != null) {
					updateOrDeleteQuestionAnswer(questionAnswer, answerLineDTO);
					answerLineDTOs.remove(answerLineDTO);
				}
			}
		}
	}

	/**
	 * Update or delete the given {@link QuestionAnswer questionAnswer} according to the value of the given {@link AnswerLineDTO answerLineDTO}.
	 * 
	 * @param questionAnswer
	 * @param answerLineDTO
	 */
	private void updateOrDeleteQuestionAnswer(QuestionAnswer questionAnswer, AnswerLineDTO answerLineDTO) {
		Object value = answerLineDTO.getValue();
		QuestionSetAnswer questionSetAnswer = questionAnswer.getQuestionSetAnswer();
		if ((value == null) || (StringUtils.trimToNull(value.toString()) == null)) {
			Logger.info("DELETING {}", questionAnswer);
			questionSetAnswer.getQuestionAnswers().remove(questionAnswer);
			questionSetAnswerService.saveOrUpdate(questionSetAnswer);
		} else {
			List<AnswerValue> oldAnswerValues = questionAnswer.getAnswerValues();
			List<AnswerValue> newAnswerValues = getAnswerValues(answerLineDTO, questionAnswer);
			if (!oldAnswerValues.equals(newAnswerValues)) {
				questionAnswer.updateAnswerValues(newAnswerValues);
				questionSetAnswerService.saveOrUpdate(questionSetAnswer);
				Logger.info("UPDATED {}", questionAnswer);
			}
		}
	}

	private void createQuestionAnswer(Account currentUser, Period period, Scope scope, AnswerLineDTO answerLineDTO, List<QuestionSetAnswer> createdQuestionSetAnswers) {
		Object answerValue = answerLineDTO.getValue();
		if ((answerValue == null) || StringUtils.isBlank(answerValue.toString())) {
			Logger.warn("Cannot create a new QuestionAnswer from answer line {}: value is null", answerLineDTO);
			return;
		}

		Question question = getAndVerifyQuestion(answerLineDTO);
		QuestionSet questionSet = question.getQuestionSet();

		// get (and fix) AnswerLineDTO repetition map
		Map<String, Integer> repetitionMap = getFullRepetitionMap(questionSet, answerLineDTO);

		// find QuestionSetAnswer matching QuestionSet key and (if existing)
		QuestionSetAnswer questionSetAnswer = getQuestionSetAnswer(period, scope, questionSet, repetitionMap, createdQuestionSetAnswers);

		// create and save QuestionAnswer
		QuestionAnswer questionAnswer = new QuestionAnswer(currentUser, null, questionSetAnswer, question);
		List<AnswerValue> answerValues = getAnswerValues(answerLineDTO, questionAnswer);
		if (answerValues == null) {
			return;
		}
		questionAnswer.getAnswerValues().addAll(answerValues);
		questionSetAnswer.getQuestionAnswers().add(questionAnswer);
		questionSetAnswerService.saveOrUpdate(questionSetAnswer);
		Logger.info("CREATED {}", questionAnswer);
	}

	private QuestionSetAnswer getQuestionSetAnswer(Period period, Scope scope, QuestionSet questionSet, Map<String, Integer> repetitionMap, List<QuestionSetAnswer> createdQuestionSetAnswers) {
		String questionSetKey = questionSet.getCode().getKey();
		if (!repetitionMap.containsKey(questionSetKey)) {
			// assume that the questionSet is not repeatable (this validation should have already occurred), and save 0 in DB
			repetitionMap.put(questionSetKey, 0);
		}
		QuestionSetAnswer questionSetAnswer = findQuestionSetAnswer(questionSetKey, repetitionMap, createdQuestionSetAnswers);
		if (questionSetAnswer == null) {			
			questionSetAnswer = new QuestionSetAnswer(scope, period, questionSet, repetitionMap.get(questionSetKey), null);
			if (questionSet.getParent() != null) {
				QuestionSetAnswer parentQuestionSetAnswer = getQuestionSetAnswer(period, scope, questionSet.getParent(), repetitionMap, createdQuestionSetAnswers);
				questionSetAnswer.setParent(parentQuestionSetAnswer);
			}
		}
		questionSetAnswerService.saveOrUpdate(questionSetAnswer);
		Logger.info("CREATED {}", questionSetAnswer);
		createdQuestionSetAnswers.add(questionSetAnswer);
		return questionSetAnswer;
	}

	private Map<String, Integer> getFullRepetitionMap(QuestionSet questionSet, AnswerLineDTO answerLineDTO) {
		Map<String, Integer> repMap = answerLineDTO.getMapRepetition();
		if (repMap == null) {
			repMap = new HashMap<String, Integer>();
		}
		while (questionSet != null) {
			Integer repetitionIndex = repMap.get(questionSet.getCode().getKey());
			if (repetitionIndex == null) {
				if (questionSet.getRepetitionAllowed()) {
					throw new RuntimeException("Invalid answerLineDTO (" + answerLineDTO + "): repetition map (" + repMap + ") doesn not contain entry for parent questionSet ("
							+ questionSet.getCode().getKey() + "), while repetion is allowed in this questionSet");
				}
			}
			questionSet = questionSet.getParent();
		}
		return repMap;
	}

	private List<AnswerValue> getAnswerValues(AnswerLineDTO answerLine, QuestionAnswer questionAnswer) {
		if (answerLine.getValue() == null) {
			return null;
		}
		Object rawAnswerValue = answerLine.getValue();
		List<AnswerValue> answerValue = new ArrayList<>();

		Question question = questionAnswer.getQuestion();
		switch (question.getAnswerType()) {
		case BOOLEAN:

			// build the answer value
			String strValue = StringUtils.trim(rawAnswerValue.toString());
			Boolean booleanValue = null;
			if ("1".equals(strValue)) {
				booleanValue = Boolean.TRUE;
			} else if ("0".equals(strValue)) {
				booleanValue = Boolean.FALSE;
			}

			BooleanAnswerValue booleanAnswerValue = new BooleanAnswerValue(questionAnswer, booleanValue);

			// test if the value is not null
			if (booleanAnswerValue.getValue() == null) {
				throw new RuntimeException("the answer of the question " + question.getCode() + " cannot be saved : " + rawAnswerValue.toString());
			}

			// add to the list
			answerValue.add(booleanAnswerValue);

			break;
		case STRING:

			// build the answerValue
			StringAnswerValue stringAnswerValue = new StringAnswerValue(questionAnswer, rawAnswerValue.toString());

			// test if the value is not null
			if (stringAnswerValue.getValue() == null) {
				throw new RuntimeException("the answer of the question " + question.getCode() + " cannot be saved : " + rawAnswerValue.toString());
			}

			// add to the list
			answerValue.add(stringAnswerValue);
			break;
		case INTEGER:

			// build the answerValue
			UnitCategory unitCategoryInt = ((IntegerQuestion) question).getUnitCategory();
			Unit unitInt = getAndVerifyUnit(answerLine, unitCategoryInt, question.getCode().getKey());
			IntegerAnswerValue integerAnswerValue = new IntegerAnswerValue(questionAnswer, Integer.valueOf(rawAnswerValue.toString()), unitInt);

			// test if the value is not null
			if (integerAnswerValue.getValue() == null) {
				throw new RuntimeException("the answer of the question " + question.getCode() + " cannot be saved : " + rawAnswerValue.toString());
			}

			// add to the list
			answerValue.add(integerAnswerValue);
			break;
		case DOUBLE:

			// build the answerValue
			UnitCategory unitCategoryDbl = ((DoubleQuestion) question).getUnitCategory();
			Unit unitDbl = getAndVerifyUnit(answerLine, unitCategoryDbl, question.getCode().getKey());

			DoubleAnswerValue doubleAnswerValue = new DoubleAnswerValue(questionAnswer, Double.valueOf(rawAnswerValue.toString()), unitDbl);

			// test if the value is not null
			if (doubleAnswerValue.getValue() == null) {
				throw new RuntimeException("the answer of the question " + question.getCode() + " cannot be saved : " + rawAnswerValue.toString());
			}

			// add to the list
			answerValue.add(doubleAnswerValue);
			break;
		case PERCENTAGE:

			DoubleAnswerValue percentageAnswerValue = new DoubleAnswerValue(questionAnswer, Double.valueOf(rawAnswerValue.toString()), null);

			// test if the value is not null
			if (percentageAnswerValue.getValue() == null) {
				throw new RuntimeException("the answer of the question " + question.getCode() + " cannot be saved : " + rawAnswerValue.toString());
			}

			// add to the list
			answerValue.add(percentageAnswerValue);
			break;
		case VALUE_SELECTION:

			// build the answerValue
			CodeList codeList = ((ValueSelectionQuestion) question).getCodeList();

			CodeAnswerValue codeAnswerValue = new CodeAnswerValue(questionAnswer, new Code(codeList, rawAnswerValue.toString()));

			// test if the value is not null
			if (codeAnswerValue.getValue() == null) {
				throw new RuntimeException("the answer of the question " + question.getCode() + " cannot be saved : " + rawAnswerValue.toString());
			}

			// add to the list
			answerValue.add(codeAnswerValue);
			break;
		case ENTITY_SELECTION:

			// build the answerValue
			String entityName = ((EntitySelectionQuestion) question).getEntityName();

			EntityAnswerValue entityAnswerValue = new EntityAnswerValue(questionAnswer, entityName, Long.valueOf(rawAnswerValue.toString()));

			// test if the value is not null
			if (entityAnswerValue.getEntityId() == null) {
				throw new RuntimeException("the answer of the question " + question.getCode() + " cannot be saved : " + rawAnswerValue.toString());
			}

			// add to the list
			answerValue.add(entityAnswerValue);
			break;
		case DOCUMENT:

			Logger.info(rawAnswerValue.toString());
			Logger.info(rawAnswerValue.getClass().toString());

			for (Map.Entry<String, String> entry : ((LinkedHashMap<String, String>) rawAnswerValue).entrySet()) {
				StoredFile storedFile = storedFileService.findById(Long.parseLong(entry.getKey()));
				if (storedFile == null) {
					throw new RuntimeException("the answer of the question " + question.getCode() + " cannot be saved : " + rawAnswerValue.toString());
				}
				answerValue.add(new DocumentAnswerValue(questionAnswer, storedFile));
			}

			break;
		}

		return answerValue;
	}

	private Question getAndVerifyQuestion(AnswerLineDTO answerLine) {
		String questionKey = StringUtils.trimToNull(answerLine.getQuestionKey());
		if (questionKey == null) {
			throw new RuntimeException("The answer [" + answerLine + "] is not valid : question key is null.");
		}
		Question question = questionService.findByCode(new QuestionCode(questionKey));
		if (question == null) {
			throw new RuntimeException("The question key [" + questionKey + "] is not valid.");
		}
		return question;
	}

	private Unit getAndVerifyUnit(AnswerLineDTO answerLine, UnitCategory questionUnitCategory, String questionKey) {
		Integer answerUnitId = answerLine.getUnitId();

		// no unit category linked to the question => return null, or throw an Exception if client provided a unit
		if (questionUnitCategory == null) {
			if (answerUnitId != null) {
				// TODO this event should not throw a RuntimeException => to improve
				throw new RuntimeException(String.format(ERROR_ANSWER_UNIT_NOT_AUTHORIZED, questionKey, answerUnitId));
			} else {
				return null;
			}
		}

		// the question is linked to a unit category => get unit from client answer, or throw an Exception if client provided no unit
		if (answerUnitId == null) {
			throw new RuntimeException(String.format(ERROR_ANSWER_UNIT_REQUIRED, questionKey, questionUnitCategory.getName()));
		}
		Unit answerUnit = unitService.findById(answerUnitId.longValue());

		// check unit category => throw an Exception if client provided an invalid unit (not part of the question's unit category)
		UnitCategory answerUnitCategory = answerUnit.getCategory();
		if (!questionUnitCategory.equals(answerUnitCategory)) {
			throw new RuntimeException(String.format(ERROR_ANSWER_UNIT_INVALID, questionKey, questionUnitCategory.getName(), answerUnit.getName(), answerUnitCategory.getName()));
		}
		return answerUnit;
	}

	/**
	 * Check if there is an {@link AnswerLineDTO} among <b>answerLineDTOs</b> matching given {@link QuestionAnswer questionAnswer}.
	 * 
	 * @param questionAnswer
	 * @param answerLineDTOs
	 * @return the matching {@link AnswerLineDTO} or <code>null</code> if there is no match.
	 */
	private static AnswerLineDTO getMatchingAnswerLineDTO(QuestionAnswer questionAnswer, List<AnswerLineDTO> answerLineDTOs) {
		String questionKey = questionAnswer.getQuestion().getCode().getKey();
		Map<String, Integer> mapRepetition = QuestionAnswerToAnswerLineConverter.buildRepetitionMap(questionAnswer.getQuestionSetAnswer());
		for (AnswerLineDTO answerLineDTO : answerLineDTOs) {
			if (questionKey.equals(answerLineDTO.getQuestionKey()) && mapRepetition.equals(answerLineDTO.getMapRepetition())) {
				return answerLineDTO;
			}
		}
		return null;
	}

	private static QuestionSetAnswer findQuestionSetAnswer(String questionSetKey, Map<String, Integer> repetitionMap, List<QuestionSetAnswer> createdQuestionSetAnswers) {
		for (QuestionSetAnswer questionSetAnswer : createdQuestionSetAnswers) {
			if (questionSetKey.equals(questionSetAnswer.getQuestionSet().getCode().getKey()) && repetitionMap.equals(QuestionAnswerToAnswerLineConverter.buildRepetitionMap(questionSetAnswer))) {
				return questionSetAnswer;
			}		
			QuestionSetAnswer childQuestionSetAnswer = findQuestionSetAnswer(questionSetKey, repetitionMap, questionSetAnswer.getChildren());
			if (childQuestionSetAnswer != null) {
				return childQuestionSetAnswer;
			}
		}
		return null;
	}

	private static LocalDateTime getMaxLastUpdateDate(List<QuestionAnswer> allQuestionAnswers) {
		if ((allQuestionAnswers == null) || allQuestionAnswers.isEmpty()) {
			return null;
		}
		LocalDateTime maxLastUpdateDate = allQuestionAnswers.get(0).getTechnicalSegment().getLastUpdateDate();
		for (int i = 1; i < allQuestionAnswers.size(); i++) {
			LocalDateTime lastUpdateDate = allQuestionAnswers.get(i).getTechnicalSegment().getLastUpdateDate();
			if (lastUpdateDate.isAfter(maxLastUpdateDate)) {
				maxLastUpdateDate = lastUpdateDate;
			}
		}
		return maxLastUpdateDate;
	}

	private static void validateUserRightsForScope(Account currentUser, Scope scope) {
		Organization scopeOrganization = null;
		ScopeTypeCode scopeType = scope.getScopeType();
		if (ScopeTypeCode.ORG.equals(scopeType)) {
			scopeOrganization = scope.getOrganization();
		} else if (ScopeTypeCode.SITE.equals(scopeType)) {
			scopeOrganization = scope.getSite().getOrganization();
		} else if (ScopeTypeCode.PRODUCT.equals(scopeType)) {
			scopeOrganization = scope.getProduct().getOrganization();
		}
		if (!scopeOrganization.equals(currentUser.getOrganization())) {
			throw new RuntimeException("The user '" + currentUser.getIdentifier() + "' is not allowed to update data of organization '" + scopeOrganization + "'");
		}
	}

	private static <T extends DTO> T extractDTOFromRequest(Class<T> DTOclass) {
		T dto = DTO.getDTO(request().body().asJson(), DTOclass);
		if (dto == null) {
			throw new RuntimeException("The request content cannot be converted to a '" + DTOclass.getName() + "'.");
		}
		return dto;
	}

}
