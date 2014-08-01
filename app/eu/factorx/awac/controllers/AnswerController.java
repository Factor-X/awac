package eu.factorx.awac.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import eu.factorx.awac.dto.awac.get.*;
import eu.factorx.awac.dto.awac.post.FormProgressDTO;
import eu.factorx.awac.models.data.FormProgress;
import eu.factorx.awac.service.*;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;

import play.Logger;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.awac.post.AnswerLineDTO;
import eu.factorx.awac.dto.awac.post.QuestionAnswersDTO;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.code.label.CodeLabel;
import eu.factorx.awac.models.code.type.LanguageCode;
import eu.factorx.awac.models.code.type.QuestionCode;
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
<<<<<<< HEAD

=======
>>>>>>> 5bf6ef1e98c0ee9fcd66f432165a187f4eecc431

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

		// TODO user language should be a request parameter (else, we should get from session or cookie)
		LanguageCode lang = LanguageCode.ENGLISH;

		if (form == null || period == null || scope == null) {
			throw new RuntimeException("Invalid request params");
		}

		List<QuestionSet> questionSets = form.getQuestionSets();
		List<QuestionSetDTO> questionSetDTOs = toQuestionSetDTOs(form.getQuestionSets());

		List<QuestionSetAnswer> questionSetAnswers = questionSetAnswerService.findByScopeAndPeriodAndForm(scope, period, form);
		List<QuestionAnswer> allQuestionAnswers = getAllQuestionAnswers(questionSetAnswers);

		QuestionAnswersDTO questionAnswersDTO = createQuestionAnswersDTO(form.getId(), periodId, scopeId, allQuestionAnswers);

		Logger.info("GET '{}' Data:", form.getIdentifier());
		for (AnswerLineDTO answerLineDTO : questionAnswersDTO.getListAnswers()) {
			Logger.info("\t" + answerLineDTO);
		}

		Map<String, CodeListDTO> codeListDTOs = getNecessaryCodeLists(questionSets, lang);

		Map<Long, UnitCategoryDTO> unitCategoryDTOs = getAllUnitCategories();

		FormDTO formDTO = new FormDTO(unitCategoryDTOs, codeListDTOs, questionSetDTOs, questionAnswersDTO);
		return ok(formDTO);
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

		Form form = formService.findById(answersDTO.getFormId());
		Period period = periodService.findById(answersDTO.getPeriodId());
		Scope scope = scopeService.findById(answersDTO.getScopeId());

		if (form == null || period == null || scope == null) {
			throw new RuntimeException("Invalid request parameters : " + answersDTO);
		}

		// security check
		if (!currentUser.getOrganization().equals(scope.getOrganization())) {
			throw new RuntimeException("The user '" + currentUser.getIdentifier() + "' is not allowed to update data of organization '" + scope.getOrganization() + "'");
		}

		Logger.info("POST '{}' Data:", form.getIdentifier());
		for (AnswerLineDTO answerLine : answersDTO.getListAnswers()) {
			Logger.info("\t" + answerLine);
		}

		saveAnswsersDTO(currentUser, form, period, scope, answersDTO.getListAnswers());

		SaveAnswersResultDTO dto = new SaveAnswersResultDTO();

		return ok(dto);
	}

	private QuestionAnswersDTO createQuestionAnswersDTO(Long formId, Long periodId, Long scopeId, List<QuestionAnswer> allQuestionAnswers) {
		LocalDateTime formAnswersLastUpdateDate = null;
		if (!allQuestionAnswers.isEmpty()) {
			formAnswersLastUpdateDate = allQuestionAnswers.get(0).getTechnicalSegment().getLastUpdateDate();
		}
		List<AnswerLineDTO> answerLineDTOs = new ArrayList<>();
		for (QuestionAnswer questionAnswer : allQuestionAnswers) {
			LocalDateTime lastUpdateDate = questionAnswer.getTechnicalSegment().getLastUpdateDate();
			if (lastUpdateDate.isAfter(formAnswersLastUpdateDate)) {
				formAnswersLastUpdateDate = lastUpdateDate;
			}
			answerLineDTOs.add(conversionService.convert(questionAnswer, AnswerLineDTO.class));
		}
		return new QuestionAnswersDTO(formId, scopeId, periodId, formAnswersLastUpdateDate, answerLineDTOs);
	}

	private List<QuestionSetDTO> toQuestionSetDTOs(List<QuestionSet> questionSets) {
		List<QuestionSetDTO> questionSetDTOs = new ArrayList<>();
		for (QuestionSet questionSet : questionSets) {
			questionSetDTOs.add(conversionService.convert(questionSet, QuestionSetDTO.class));
		}
		return questionSetDTOs;
	}

	private List<QuestionAnswer> getAllQuestionAnswers(List<QuestionSetAnswer> questionSetAnswers) {
		List<QuestionAnswer> questionAnswers = new ArrayList<QuestionAnswer>();
		for (QuestionSetAnswer questionSetAnswer : questionSetAnswers) {
			questionAnswers.addAll(questionSetAnswer.getQuestionAnswers());
			questionAnswers.addAll(getAllQuestionAnswers(questionSetAnswer.getChildren()));
		}
		return questionAnswers;
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
			codeLists.putAll(getNecessaryCodeLists(questionSet.getChildren(), lang));
		}
		return codeLists;
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
		Map<String, List<AnswerLineDTO>> answerLinesDTOsMap = asMap(answerLineDTOs);

		// updated or deleted question answers
		List<AnswerLineDTO> updatedAndDeletedQuestionAnswers = new ArrayList<>();
		List<QuestionSetAnswer> questionSetAnswers = questionSetAnswerService.findByScopeAndPeriodAndForm(scope, period, form);
		updateOrDeleteQuestionAnswers(questionSetAnswers, answerLinesDTOsMap, updatedAndDeletedQuestionAnswers);

		// new question answers
		Map<String, List<QuestionSetAnswer>> createdQSAnswers = new HashMap<>();
		for (AnswerLineDTO answerLineDTO : answerLineDTOs) {
			if (!updatedAndDeletedQuestionAnswers.contains(answerLineDTO)) {
				createQuestionAnswer(currentUser, period, scope, answerLineDTO, createdQSAnswers);
			}
		}

		// cleaning: delete empty QuestionSetAnswers (without QuestionAnswers)
		deleteEmptyQuestionSetAnswers(questionSetAnswers);
	}

	private void updateOrDeleteQuestionAnswers(List<QuestionSetAnswer> questionSetAnswers, Map<String, List<AnswerLineDTO>> answerLinesDTOs,
			List<AnswerLineDTO> updatedAndDeletedQuestionAnswers) {
		for (QuestionSetAnswer questionSetAnswer : questionSetAnswers) {
			for (QuestionAnswer questionAnswer : questionSetAnswer.getQuestionAnswers()) {
				AnswerLineDTO answerLineDTO = getAnswerLineDTO(questionAnswer, answerLinesDTOs);
				if (answerLineDTO != null) {
					updateOrDeleteQuestionAnswer(questionAnswer, answerLineDTO);
					updatedAndDeletedQuestionAnswers.add(answerLineDTO);
				}
			}
			updateOrDeleteQuestionAnswers(questionSetAnswer.getChildren(), answerLinesDTOs, updatedAndDeletedQuestionAnswers);
		}
	}

	private void deleteEmptyQuestionSetAnswers(List<QuestionSetAnswer> questionSetAnswers) {
		for (QuestionSetAnswer questionSetAnswer : questionSetAnswers) {
			if (questionSetAnswer.getQuestionAnswers().isEmpty() && questionSetAnswer.getChildren().isEmpty()) {
				Logger.info("DELETING QuestionSetAnswer [ID={}]", questionSetAnswer.getId());
				questionSetAnswerService.remove(questionSetAnswer);
			} else {
				deleteEmptyQuestionSetAnswers(questionSetAnswer.getChildren());
			}
		}
	}

	private void updateOrDeleteQuestionAnswer(QuestionAnswer questionAnswer, AnswerLineDTO answerLineDTO) {
		Object value = answerLineDTO.getValue();
		if ((value == null) || (StringUtils.trimToNull(value.toString()) == null)) {
			Logger.info("DELETING QuestionAnswer [ID={}] ({})", questionAnswer.getId(), answerLineDTO);
			questionAnswer = questionAnswerService.findById(questionAnswer.getId());
			questionAnswerService.remove(questionAnswer);
		} else {
			List<AnswerValue> oldAnswerValues = questionAnswer.getAnswerValues();
			List<AnswerValue> newAnswerValues = getAnswerValue(answerLineDTO, questionAnswer);
			if (!oldAnswerValues.equals(newAnswerValues)) {
				Logger.info("UPDATING QuestionAnswer [ID={}] ({})", questionAnswer.getId(), answerLineDTO);
				questionAnswer.getAnswerValues().clear();
				questionAnswer.getAnswerValues().addAll(newAnswerValues);
				questionAnswerService.saveOrUpdate(questionAnswer);
			}
		}
	}

	private void createQuestionAnswer(Account currentUser, Period period, Scope scope, AnswerLineDTO answerLineDTO, Map<String, List<QuestionSetAnswer>> createdQuestionSetAnswers) {
		Object answerValue = answerLineDTO.getValue();
		if ((answerValue == null) || StringUtils.isBlank(answerValue.toString())) {			
			Logger.warn("Cannot create a new QuestionAnswer from answer line {}: value is null", answerLineDTO);
			return;
		}
		
		Question question = getAndVerifyQuestion(answerLineDTO);
		QuestionSet questionSet = question.getQuestionSet();

		// get (and fix) repetition map
		Map<String, Integer> repetitionMap = getRepetitionMap(questionSet, answerLineDTO);

		// get QuestionSetAnswer
		QuestionSetAnswer questionSetAnswer = getQuestionSetAnswer(scope, period, repetitionMap, questionSet, createdQuestionSetAnswers);

		// create and save QuestionAnswer
		QuestionAnswer questionAnswer = new QuestionAnswer(currentUser, null, questionSetAnswer, question);
		List<AnswerValue> answerValues = getAnswerValue(answerLineDTO, questionAnswer);
		if (answerValues == null) {
			return;
		}
		questionAnswer.getAnswerValues().addAll(answerValues);
		questionAnswerService.saveOrUpdate(questionAnswer);
		Logger.info("CREATED QuestionAnswer [ID={}] ({})", questionAnswer.getId(), answerLineDTO);
	}

	private Map<String, Integer> getRepetitionMap(QuestionSet questionSet, AnswerLineDTO answerLineDTO) {
		Map<String, Integer> repMap = answerLineDTO.getMapRepetition();
		if (repMap == null) {
			repMap = new HashMap<String, Integer>();
		}
		while (questionSet != null) {
			String questionSetCode = questionSet.getCode().getKey();
			// get repetition index
			Integer repetitionIndex = repMap.get(questionSetCode);
			if (repetitionIndex == null) {
				if (questionSet.getRepetitionAllowed()) {
					throw new RuntimeException("Invalid answerLineDTO (" + answerLineDTO + "): repetition map (" + repMap + ") doesn not contain entry for parent questionSet (" + questionSetCode
							+ "), while repetion is allowed in this questionSet");
				}
				repMap.put(questionSetCode, 0);
			}
			questionSet = questionSet.getParent();
		}
		return repMap;
	}

	private QuestionSetAnswer getQuestionSetAnswer(Scope scope, Period period, Map<String, Integer> repMap, QuestionSet questionSet, Map<String, List<QuestionSetAnswer>> createdQuestionSetAnswers) {
		String questionSetCode = questionSet.getCode().getKey();

		// attempt to find the QuestionSetAnswer in already created map
		QuestionSetAnswer questionSetAnswer = null;
		if (!createdQuestionSetAnswers.containsKey(questionSetCode)) {
			createdQuestionSetAnswers.put(questionSetCode, new ArrayList<QuestionSetAnswer>());
		} else {
			questionSetAnswer = fromCreatedQuestionSetAnswers(repMap, createdQuestionSetAnswers.get(questionSetCode));
		}

		// else create new QuestionSetAnswer
		if (questionSetAnswer == null) {
			questionSetAnswer = new QuestionSetAnswer(scope, period, questionSet, repMap.get(questionSetCode), null);
			if (questionSet.getParent() != null) {
				QuestionSetAnswer parentQuestionSetAnswer = getQuestionSetAnswer(scope, period, repMap, questionSet.getParent(), createdQuestionSetAnswers);
				questionSetAnswer.setParent(parentQuestionSetAnswer);
			}
			questionSetAnswerService.saveOrUpdate(questionSetAnswer);
			createdQuestionSetAnswers.get(questionSetCode).add(questionSetAnswer);
		}
		return questionSetAnswer;
	}

	private QuestionSetAnswer fromCreatedQuestionSetAnswers(Map<String, Integer> repMap, List<QuestionSetAnswer> createdQuestionSetAnswers) {
		loop1: for (QuestionSetAnswer questionSetAnswer : createdQuestionSetAnswers) {
			String key = questionSetAnswer.getQuestionSet().getCode().getKey();
			if (repMap.containsKey(key) && repMap.get(key).equals(questionSetAnswer.getRepetitionIndex())) {
				// check parents
				QuestionSetAnswer parentQuestionSetAnswer = questionSetAnswer.getParent();
				while (parentQuestionSetAnswer != null) {
					String parentKey = parentQuestionSetAnswer.getQuestionSet().getCode().getKey();
					if (repMap.containsKey(parentKey) && repMap.get(parentKey).equals(parentQuestionSetAnswer.getRepetitionIndex())) {
						parentQuestionSetAnswer = parentQuestionSetAnswer.getParent();
					} else {
						continue loop1;
					}
				}
				return questionSetAnswer;
			}
		}
		return null;
	}

	private List<AnswerValue> getAnswerValue(AnswerLineDTO answerLine, QuestionAnswer questionAnswer) {
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

	protected <T extends DTO> T extractDTOFromRequest(Class<T> DTOclass) {
		T dto = DTO.getDTO(request().body().asJson(), DTOclass);
		if (dto == null) {
			throw new RuntimeException("The request content cannot be converted to a '" + DTOclass.getName() + "'.");
		}
		return dto;
	}

	private static Map<String, List<AnswerLineDTO>> asMap(List<AnswerLineDTO> answerLineDTOs) {
		Map<String, List<AnswerLineDTO>> answerLinesDTOsMap = new HashMap<>();
		for (AnswerLineDTO answerLineDTO : answerLineDTOs) {
			String questionKey = answerLineDTO.getQuestionKey();
			if (!answerLinesDTOsMap.containsKey(questionKey)) {
				answerLinesDTOsMap.put(questionKey, new ArrayList<AnswerLineDTO>());
			}
			answerLinesDTOsMap.get(questionKey).add(answerLineDTO);
		}
		return answerLinesDTOsMap;
	}

	private static AnswerLineDTO getAnswerLineDTO(QuestionAnswer questionAnswer, Map<String, List<AnswerLineDTO>> answerLineDTOs) {
		Map<String, Integer> questionAnswerRepetitionMap = buildRepetitionMap(questionAnswer);
		String questionKey = questionAnswer.getQuestion().getCode().getKey();
		List<AnswerLineDTO> questionSetAnswerLineDTOs = answerLineDTOs.get(questionKey);
		if (questionSetAnswerLineDTOs != null) {
			for (AnswerLineDTO answerLineDTO : questionSetAnswerLineDTOs) {
				if (answerLineDTO.getMapRepetition() != null && answerLineDTO.getMapRepetition().isEmpty()) {
					answerLineDTO.setMapRepetition(null);
				}
				if ((questionAnswerRepetitionMap == null && answerLineDTO.getMapRepetition() == null) || questionAnswerRepetitionMap.equals(answerLineDTO.getMapRepetition())) {
					return answerLineDTO;
				}
			}
		}
		return null;
	}

	private static Map<String, Integer> buildRepetitionMap(QuestionAnswer questionAnswer) {
		Map<String, Integer> res = new HashMap<>();
		QuestionSetAnswer questionSetAnswer = questionAnswer.getQuestionSetAnswer();
		while (questionSetAnswer != null) {
			QuestionSet questionSet = questionSetAnswer.getQuestionSet();
			if (questionSet.getRepetitionAllowed()) {
				res.put(questionSet.getCode().getKey(), questionSetAnswer.getRepetitionIndex());
			}
			questionSetAnswer = questionSetAnswer.getParent();
		}
		if (res.isEmpty()) {
			res = null;
		}
		return res;
	}

	@Transactional(readOnly = true)
	@Security.Authenticated(SecuredController.class)
	public Result getFormProgress(Long periodId, Long scopeId){

		//1. control the owner of the scope
		Scope scope = scopeService.findById(scopeId);

		if(!scope.getOrganization().equals(securedController.getCurrentUser().getOrganization())){
			throw new RuntimeException("This scope doesn't owned by your organization");
		}

		//2. load period
		Period period = periodService.findById(periodId);

		//3. load formProgress
		List<FormProgress> formProgressList = formProgressService.findByPeriodAndByScope(period, scope);

		return ok(conversionService.convert(formProgressList, FormProgressListDTO.class));

	}

<<<<<<< HEAD
	@Transactional(readOnly = false)
	@Security.Authenticated(SecuredController.class)
	public Result setFormProgress(){

		Logger.info("public Result setFormProgress(){");

		FormProgressDTO formProgressDTO = extractDTOFromRequest(FormProgressDTO.class);

		Period period = periodService.findById(formProgressDTO.getPeriod());
		Scope scope = scopeService.findById(formProgressDTO.getScope());
		Form form = formService.findByIdentifier(formProgressDTO.getForm());


		//control percentage
		Integer percentage = formProgressDTO.getPercentage();
		if(percentage<0 || percentage>100){
			throw new RuntimeException("Percentage must be more than 0 and less than 100. Currently : "+percentage);
=======
	protected <T extends DTO> T extractDTOFromRequest(Class<T> DTOclass) {

		if(request().body().asJson() == null){
			throw new RuntimeException("The request doesn't contain any body");
		}

		T dto = DTO.getDTO(request().body().asJson(), DTOclass);
		if (dto == null) {
			throw new RuntimeException("The request content cannot be converted to a '" + DTOclass.getName() + "'.");
>>>>>>> 5bf6ef1e98c0ee9fcd66f432165a187f4eecc431
		}

		//control scope
		if(!scope.getOrganization().equals(securedController.getCurrentUser().getOrganization())){
			throw new RuntimeException("This scope doesn't owned by your organization. Scope asked : "+scope.toString());
		}

		//try to load
		FormProgress formProgress = formProgressService.findByPeriodAndByScopeAndForm(period, scope, form);

		//update...
		if(formProgress != null){
			formProgress.setPercentage(percentage);
		}
		//...or create a new formProgress
		else{
			formProgress = new FormProgress(period,form,scope,percentage);
		}

		formProgressService.saveOrUpdate(formProgress);

		return ok();
	}

	@Transactional(readOnly = true)
	@Security.Authenticated(SecuredController.class)
	public Result getFormProgress(Long periodId, Long scopeId){

		//1. control the owner of the scope
		Scope scope = scopeService.findById(scopeId);

		if(!scope.getOrganization().equals(securedController.getCurrentUser().getOrganization())){
			throw new RuntimeException("This scope doesn't owned by your organization");
		}

		//2. load period
		Period period = periodService.findById(periodId);

		//3. load formProgress
		List<FormProgress> formProgressList = formProgressService.findByPeriodAndByScope(period, scope);

		return ok(conversionService.convert(formProgressList, FormProgressListDTO.class));

	}

	@Transactional(readOnly = false)
	@Security.Authenticated(SecuredController.class)
	public Result setFormProgress(){

		Logger.info("public Result setFormProgress(){");

		FormProgressDTO formProgressDTO = extractDTOFromRequest(FormProgressDTO.class);

		Period period = periodService.findById(formProgressDTO.getPeriod());
		Scope scope = scopeService.findById(formProgressDTO.getScope());
		Form form = formService.findByIdentifier(formProgressDTO.getForm());


		//control percentage
		Integer percentage = formProgressDTO.getPercentage();
		if(percentage<0 || percentage>100){
			throw new RuntimeException("Percentage must be more than 0 and less than 100. Currently : "+percentage);
		}

		//control scope
		if(!scope.getOrganization().equals(securedController.getCurrentUser().getOrganization())){
			throw new RuntimeException("This scope doesn't owned by your organization. Scope asked : "+scope.toString());
		}

		//try to load
		FormProgress formProgress = formProgressService.findByPeriodAndByScopeAndForm(period, scope, form);

		//update...
		if(formProgress != null){
			formProgress.setPercentage(percentage);
		}
		//...or create a new formProgress
		else{
			formProgress = new FormProgress(period,form,scope,percentage);
		}

		formProgressService.saveOrUpdate(formProgress);

		return ok();
	}

}
