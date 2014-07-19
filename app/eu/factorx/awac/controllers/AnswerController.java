package eu.factorx.awac.controllers;

import java.util.ArrayList;
import java.util.HashMap;
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
import eu.factorx.awac.models.data.answer.type.DoubleAnswerValue;
import eu.factorx.awac.models.data.answer.type.EntityAnswerValue;
import eu.factorx.awac.models.data.answer.type.IntegerAnswerValue;
import eu.factorx.awac.models.data.answer.type.StringAnswerValue;
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
		QuestionAnswersDTO questionAnswersDTO = new QuestionAnswersDTO(form.getId(), scopeId, periodId, answerLineDTOs);

		Map<String, CodeListDTO> codeListDTOs = getNecessaryCodeLists(questionSets, lang);

		Map<Long, UnitCategoryDTO> unitCategoryDTOs = getAllUnitCategories();

        Logger.debug("questionSetDTOs : "+questionSetDTOs);

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
		List<CodeLabel> codeLabels = codeLabelService.findCodeLabelsByType(codeList);
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
		Logger.info("save() 1");
		Account currentUser = securedController.getCurrentUser();
		Logger.info("save() 2");
		QuestionAnswersDTO answersDTO = extractDTOFromRequest(QuestionAnswersDTO.class);
		Logger.info("save() 3");
		saveAnswsersDTO(currentUser, answersDTO);
		Logger.info("save() 4");

		SaveAnswersResultDTO dto = new SaveAnswersResultDTO();

		return ok(dto);
	}

	public void saveAnswsersDTO(Account currentUser, QuestionAnswersDTO answersDTO) {
		/*
		 * List<QuestionSetAnswerDTO> listAnswers = answersDTO.getListAnswers(); for (QuestionSetAnswerDTO questionSetAnswerDTO : listAnswers) { QuestionSetAnswer questionSetAnswer
		 * = questionSetAnswerService.findById(questionSetAnswerDTO.getId()); List<AnswerLine> answerLines = questionSetAnswerDTO.getQuestionAnswers(); for (AnswerLine answerLine :
		 * answerLines) { QuestionAnswer questionAnswer; if (answerLine.getQuestionAnswerId() != null) { // updating questionAnswer =
		 * questionAnswerService.findById(answerLine.getQuestionAnswerId()); questionAnswer.getAnswerValues().clear(); } else { // creating Question question =
		 * getAndVerifyQuestion(answerLine); questionAnswer = new QuestionAnswer(currentUser, null, questionSetAnswer, question); }
		 * 
		 * // TODO A single QuestionAnswer may be linked to several answer values (all of the same type); this is not yet implemented in DTOs (only one Object returned) // => add
		 * only one AnswerValue in answerValues list AnswerValue answerValue = getAnswerValue(answerLine, questionAnswer); if (answerValue == null) continue;
		 * questionAnswer.getAnswerValues().add(answerValue);
		 * 
		 * questionAnswerService.saveOrUpdate(questionAnswer); } }
		 */
	}

	private AnswerValue getAnswerValue(AnswerLineDTO answerLine, QuestionAnswer questionAnswer) {
		if (answerLine.getValue() == null) {
			return null;
		}
		String rawAnswerValue = answerLine.getValue().toString();
		AnswerValue answerValue = null;

		Question question = questionAnswer.getQuestion();
		switch (question.getAnswerType()) {
		case BOOLEAN:
			answerValue = new BooleanAnswerValue(questionAnswer, Boolean.valueOf(rawAnswerValue));
			break;
		case STRING:
			answerValue = new StringAnswerValue(questionAnswer, rawAnswerValue);
			break;
		case INTEGER:
			UnitCategory unitCategoryInt = ((IntegerQuestion) question).getUnitCategory();
			Unit unitInt = getAndVerifyUnit(answerLine, unitCategoryInt, question.getCode().getKey());
			answerValue = new IntegerAnswerValue(questionAnswer, Integer.valueOf(rawAnswerValue), unitInt);
			break;
		case DOUBLE:
			UnitCategory unitCategoryDbl = ((DoubleQuestion) question).getUnitCategory();
			Unit unitDbl = getAndVerifyUnit(answerLine, unitCategoryDbl, question.getCode().getKey());
			answerValue = new DoubleAnswerValue(questionAnswer, Double.valueOf(rawAnswerValue), unitDbl);
			break;
		case VALUE_SELECTION:
			CodeList codeList = ((ValueSelectionQuestion) question).getCodeList();
			answerValue = new CodeAnswerValue(questionAnswer, new Code(codeList, rawAnswerValue));
			break;
		case ENTITY_SELECTION:
			String entityName = ((EntitySelectionQuestion) question).getEntityName();
			answerValue = new EntityAnswerValue(questionAnswer, entityName, Long.valueOf(rawAnswerValue));
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

	protected <T extends DTO> T extractDTOFromRequest(Class<T> DTOclass) {
		T dto = DTO.getDTO(request().body().asJson(), DTOclass);
		if (dto == null) {
			throw new RuntimeException("The request content cannot be converted to a '" + DTOclass.getName() + "'.");
		}
		return dto;
	}

}
