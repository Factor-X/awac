package eu.factorx.awac.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;

import play.Logger;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.awac.get.CodeLabelDTO;
import eu.factorx.awac.dto.awac.get.CodeListDTO;
import eu.factorx.awac.dto.awac.get.FormDTO;
import eu.factorx.awac.dto.awac.get.ListPeriodsDTO;
import eu.factorx.awac.dto.awac.get.PeriodDTO;
import eu.factorx.awac.dto.awac.get.QuestionSetDTO;
import eu.factorx.awac.dto.awac.get.SaveAnswersResultDTO;
import eu.factorx.awac.dto.awac.get.UnitCategoryDTO;
import eu.factorx.awac.dto.awac.get.UnitDTO;
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
import eu.factorx.awac.service.CodeLabelService;
import eu.factorx.awac.service.FormService;
import eu.factorx.awac.service.PeriodService;
import eu.factorx.awac.service.QuestionAnswerService;
import eu.factorx.awac.service.QuestionService;
import eu.factorx.awac.service.QuestionSetAnswerService;
import eu.factorx.awac.service.ScopeService;
import eu.factorx.awac.service.StoredFileService;
import eu.factorx.awac.service.UnitCategoryService;
import eu.factorx.awac.service.UnitService;

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
		List<AnswerLineDTO> answerLineDTOs = toAnswerLineDTOs(questionSetAnswers);

		Logger.info("GET '{}' Data:", form.getIdentifier());
		for (AnswerLineDTO answerLineDTO : answerLineDTOs) {
			Logger.info("\t" + answerLineDTO);
		}

		QuestionAnswersDTO questionAnswersDTO = new QuestionAnswersDTO(form.getId(), scopeId, periodId, answerLineDTOs);

		Map<String, CodeListDTO> codeListDTOs = getNecessaryCodeLists(questionSets, lang);

		Map<Long, UnitCategoryDTO> unitCategoryDTOs = getAllUnitCategories();

		FormDTO formDTO = new FormDTO(unitCategoryDTOs, codeListDTOs, questionSetDTOs, questionAnswersDTO);
		return ok(formDTO);
	}

	private List<QuestionSetDTO> toQuestionSetDTOs(List<QuestionSet> questionSets) {
		List<QuestionSetDTO> questionSetDTOs = new ArrayList<>();
		for (QuestionSet questionSet : questionSets) {
			questionSetDTOs.add(conversionService.convert(questionSet, QuestionSetDTO.class));
		}
		return questionSetDTOs;
	}

	private List<AnswerLineDTO> toAnswerLineDTOs(List<QuestionSetAnswer> questionSetAnswers) {
		List<AnswerLineDTO> answerLineDTOs = new ArrayList<>();
		for (QuestionSetAnswer questionSetAnswer : questionSetAnswers) {
			List<QuestionAnswer> questionAnswers = questionSetAnswer.getQuestionAnswers();
			for (QuestionAnswer questionAnswer : questionAnswers) {
				answerLineDTOs.add(conversionService.convert(questionAnswer, AnswerLineDTO.class));
			}
			answerLineDTOs.addAll(toAnswerLineDTOs(questionSetAnswer.getChildren()));
		}
		return answerLineDTOs;
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
					if (!codeLists.containsKey(codeList)) {
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

	@Transactional(readOnly = false)
	@Security.Authenticated(SecuredController.class)
	public Result save() {
		Account currentUser = securedController.getCurrentUser();
		QuestionAnswersDTO answersDTO = extractDTOFromRequest(QuestionAnswersDTO.class);

		Form form = formService.findById(answersDTO.getFormId());
		Period period = periodService.findById(answersDTO.getPeriodId());
		Scope scope = scopeService.findById(answersDTO.getScopeId());

		if (form == null || period == null || scope == null) {
			throw new RuntimeException("Invalid request params");
		}

		Logger.info("POST '{}' Data:", form.getIdentifier());
		for (AnswerLineDTO answerLine : answersDTO.getListAnswers()) {
			Logger.info("\t" + answerLine);
		}

		saveAnswsersDTO(currentUser, form, period, scope, answersDTO.getListAnswers());

		SaveAnswersResultDTO dto = new SaveAnswersResultDTO();

		return ok(dto);
	}

	private void saveAnswsersDTO(Account currentUser, Form form, Period period, Scope scope, List<AnswerLineDTO> answerLineDTOs) {
		// delete all answers linked to form
		questionSetAnswerService.deleteAllFormAnswers(scope, period, form);

		// create answers
		Map<String, List<QuestionSetAnswer>> createdQSAnswers = new HashMap<>();
		for (AnswerLineDTO answerLineDTO : answerLineDTOs) {
			questionAnswerService.saveOrUpdate(createNewQuestionAnswer(currentUser, period, scope, answerLineDTO, createdQSAnswers));
		}
	}

	private QuestionAnswer createNewQuestionAnswer(Account currentUser, Period period, Scope scope, AnswerLineDTO answerLineDTO,
			Map<String, List<QuestionSetAnswer>> createdQuestionSetAnswers) {
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
			return null;
		}
		questionAnswer.getAnswerValues().addAll(answerValues);
		questionAnswerService.saveOrUpdate(questionAnswer);

		return questionAnswer;
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
					throw new RuntimeException("Invalid answerLineDTO (" + answerLineDTO + "): repetition map (" + repMap
							+ ") doesn not contain entry for parent questionSet (" + questionSetCode
							+ "), while repetion is allowed in this questionSet");
				}
				repMap.put(questionSetCode, 0);
			}
			questionSet = questionSet.getParent();
		}
		return repMap;
	}

	private QuestionSetAnswer getQuestionSetAnswer(Scope scope, Period period, Map<String, Integer> repMap, QuestionSet questionSet,
			Map<String, List<QuestionSetAnswer>> createdQuestionSetAnswers) {
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
				QuestionSetAnswer parentQuestionSetAnswer = getQuestionSetAnswer(scope, period, repMap, questionSet.getParent(),
						createdQuestionSetAnswers);
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
				throw new RuntimeException("the answer of the question " + question.getCode() + " cannot be saved : "
						+ rawAnswerValue.toString());
			}

			// add to the list
			answerValue.add(booleanAnswerValue);

			break;
		case STRING:

			// build the answerValue
			StringAnswerValue stringAnswerValue = new StringAnswerValue(questionAnswer, rawAnswerValue.toString());

			// test if the value is not null
			if (stringAnswerValue.getValue() == null) {
				throw new RuntimeException("the answer of the question " + question.getCode() + " cannot be saved : "
						+ rawAnswerValue.toString());
			}

			// add to the list
			answerValue.add(stringAnswerValue);
			break;
		case INTEGER:

			// build the answerValue
			UnitCategory unitCategoryInt = ((IntegerQuestion) question).getUnitCategory();
			Unit unitInt = getAndVerifyUnit(answerLine, unitCategoryInt, question.getCode().getKey());
			IntegerAnswerValue integerAnswerValue = new IntegerAnswerValue(questionAnswer, Integer.valueOf(rawAnswerValue.toString()),
					unitInt);

			// test if the value is not null
			if (integerAnswerValue.getValue() == null) {
				throw new RuntimeException("the answer of the question " + question.getCode() + " cannot be saved : "
						+ rawAnswerValue.toString());
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
				throw new RuntimeException("the answer of the question " + question.getCode() + " cannot be saved : "
						+ rawAnswerValue.toString());
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
				throw new RuntimeException("the answer of the question " + question.getCode() + " cannot be saved : "
						+ rawAnswerValue.toString());
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
				throw new RuntimeException("the answer of the question " + question.getCode() + " cannot be saved : "
						+ rawAnswerValue.toString());
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
					throw new RuntimeException("the answer of the question " + question.getCode() + " cannot be saved : "
							+ rawAnswerValue.toString());
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
			throw new RuntimeException(String.format(ERROR_ANSWER_UNIT_INVALID, questionKey, questionUnitCategory.getName(),
					answerUnit.getName(), answerUnitCategory.getName()));
		}
		return answerUnit;
	}

	@Transactional(readOnly = true)
	@Security.Authenticated(SecuredController.class)
	public Result getPeriodsForComparison(Long scopeId) {

		List<QuestionSetAnswer> listQuestionSetAnswer = questionSetAnswerService.findByScope(scopeService.findById(scopeId));

		List<PeriodDTO> periodList = new ArrayList<>();

		for (QuestionSetAnswer questionSetAnswer : listQuestionSetAnswer) {

			boolean toAdd = true;

			for (PeriodDTO periodDTO : periodList) {
				if (periodDTO.getId().equals(questionSetAnswer.getPeriod().getId())) {
					toAdd = false;
					break;
				}
			}
			if (toAdd) {
				periodList.add(conversionService.convert(questionSetAnswer.getPeriod(), PeriodDTO.class));
			}
		}

		return ok(new ListPeriodsDTO(periodList));
	}

	protected <T extends DTO> T extractDTOFromRequest(Class<T> DTOclass) {
		T dto = DTO.getDTO(request().body().asJson(), DTOclass);
		if (dto == null) {
			throw new RuntimeException("The request content cannot be converted to a '" + DTOclass.getName() + "'.");
		}
		return dto;
	}

}
