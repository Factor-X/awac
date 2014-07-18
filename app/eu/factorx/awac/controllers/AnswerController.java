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
import eu.factorx.awac.dto.awac.get.KeyValuePairDTO;
import eu.factorx.awac.dto.awac.get.QuestionSetDTO;
import eu.factorx.awac.dto.awac.get.SaveAnswersResultDTO;
import eu.factorx.awac.dto.awac.get.UnitCategoryDTO;
import eu.factorx.awac.dto.awac.get.UnitDTO;
import eu.factorx.awac.dto.awac.post.AnswersSaveDTO;
import eu.factorx.awac.dto.awac.shared.AnswerLine;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.code.label.CodeLabel;
import eu.factorx.awac.models.code.type.LanguageCode;
import eu.factorx.awac.models.code.type.QuestionCode;
import eu.factorx.awac.models.data.answer.AnswerType;
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

		Logger.info("getByForm 1");

		Form form = formService.findByIdentifier(formIdentifier);
		Logger.info("getByForm 1.1");
		Period period = periodService.findById(periodId);
		Logger.info("getByForm 1.2");
		Scope scope = scopeService.findById(scopeId);
		Logger.info("getByForm 1.3");

		// TODO should be in request param
		LanguageCode lang = LanguageCode.ENGLISH;
		Logger.info("getByForm 1.4");

		if (form == null || period == null || scope == null) {
			throw new RuntimeException("Invalid request params");
		}

		Logger.info("getByForm 2");

		List<QuestionSet> questionSets = form.getQuestionSets();
		List<QuestionSetDTO> questionSetDTOs = toQuestionSetDTOs(questionSets);

		Logger.info("getByForm 3");

		Map<String, CodeListDTO> codeListDTOs = new HashMap<>();
		putNecessaryCodeLists(codeListDTOs, questionSets, lang);

		Logger.info("getByForm 4");

		Map<Long, UnitCategoryDTO> unitCategoryDTOs = getAllUnitCategories();

		Logger.info("getByForm 5");

		List<QuestionSetAnswer> questionSetAnswers = questionSetAnswerService.findByScopeAndPeriodAndForm(scope,
				period, form);
		List<AnswerLine> listAnswers = toAnswerLineDTOs(questionSetAnswers);
		AnswersSaveDTO questionAnswerDTO = new AnswersSaveDTO(scopeId, periodId, listAnswers);

		Logger.info("getByForm 6");

		FormDTO formDTO = new FormDTO(scopeId, periodId, unitCategoryDTOs, codeListDTOs, questionSetDTOs, questionAnswerDTO);		
		return ok(formDTO);
	}

	private List<AnswerLine> toAnswerLineDTOs(List<QuestionSetAnswer> questionSetAnswers) {
		List<AnswerLine> answers = new ArrayList<>();
		for (QuestionSetAnswer questionSetAnswer : questionSetAnswers) {
			List<QuestionAnswer> questionAnswers = questionSetAnswer.getQuestionAnswers();
			for (QuestionAnswer questionAnswer : questionAnswers) {
				answers.add(conversionService.convert(questionAnswer, AnswerLine.class));
			}
			answers.addAll(toAnswerLineDTOs(questionSetAnswer.getChildren()));
		}
		return answers;
	}

	private Map<String, CodeListDTO> putNecessaryCodeLists(Map<String, CodeListDTO> codeLists,
			List<QuestionSet> questionSets, LanguageCode lang) {
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
			putNecessaryCodeLists(codeLists, questionSet.getChildren(), lang);
		}
		return codeLists;
	}

	private CodeListDTO toCodeListDTO(CodeList codeList, LanguageCode lang) {
		List<CodeLabel> codeLabels = codeLabelService.findCodeLabelsByType(codeList);
		CodeListDTO codeListDTO = new CodeListDTO(codeList.name());
		for (CodeLabel codeLabel : codeLabels) {
			codeListDTO.getCodeLabels().add(new CodeLabelDTO(codeLabel.getKey(), codeLabel.getLabel(lang)));
		}
		return codeListDTO;
	}

	private List<QuestionSetDTO> toQuestionSetDTOs(List<QuestionSet> questionSets) {
		List<QuestionSetDTO> questionSetDTOs = new ArrayList<>();
		for (QuestionSet questionSet : questionSets) {
			questionSetDTOs.add(conversionService.convert(questionSet, QuestionSetDTO.class));
		}
		return questionSetDTOs;
	}

	/*
	 * TODO private List<QuestionSetAnswerDTO> toQuestionSetAnswerDTOs(List<QuestionSetAnswer> questionSetAnswers) { List<QuestionSetAnswerDTO> questionSetAnswerDTOs = new
	 * ArrayList<>(); for (QuestionSetAnswer questionSetAnswer : questionSetAnswers) { questionSetAnswerDTOs.add(conversionService.convert(questionSetAnswer,
	 * QuestionSetAnswerDTO.class));
	 * 
	 * } return questionSetAnswerDTOs; }
	 */
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
		AnswersSaveDTO answersDTO = extractDTOFromRequest(AnswersSaveDTO.class);
		Logger.info("save() 3");
		saveAnswsersDTO(currentUser, answersDTO);
		Logger.info("save() 4");

		SaveAnswersResultDTO dto = new SaveAnswersResultDTO();

		return ok(dto);
	}

	public void saveAnswsersDTO(Account currentUser, AnswersSaveDTO answersDTO) {
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

	public AnswerLine convert(QuestionAnswer questionAnswer) {
		Question question = questionAnswer.getQuestion();
		AnswerType answerType = question.getAnswerType();

		// TODO A single QuestionAnswer may be linked to several answer values => not yet implemented
		AnswerValue answerValue = questionAnswer.getAnswerValues().get(0);
		Object rawAnswerValue = null;
		Integer unitId = null;
		switch (answerType) {
		case BOOLEAN:
			rawAnswerValue = ((BooleanAnswerValue) answerValue).getValue();
			break;
		case STRING:
			rawAnswerValue = ((StringAnswerValue) answerValue).getValue();
			break;
		case INTEGER:
			IntegerAnswerValue integerAnswerValue = (IntegerAnswerValue) answerValue;
			rawAnswerValue = integerAnswerValue.getValue();
			if (integerAnswerValue.getUnit() != null) {
				unitId = integerAnswerValue.getUnit().getId().intValue();
			}
			break;
		case DOUBLE:
			DoubleAnswerValue doubleAnswerValue = (DoubleAnswerValue) answerValue;
			rawAnswerValue = doubleAnswerValue.getValue();
			if (doubleAnswerValue.getUnit() != null) {
				unitId = doubleAnswerValue.getUnit().getId().intValue();
			}
			break;
		case VALUE_SELECTION:
			Code value = ((CodeAnswerValue) answerValue).getValue();
			if (value != null)
				rawAnswerValue = value.getKey();
			else
				rawAnswerValue = null;
			break;
		case ENTITY_SELECTION:
			EntityAnswerValue entityAnswerValue = (EntityAnswerValue) answerValue;
			rawAnswerValue = new KeyValuePairDTO<String, Long>(entityAnswerValue.getEntityName(),
					entityAnswerValue.getEntityId());
			break;
		}

        AnswerLine answerLine = new AnswerLine();
        answerLine.setValue(rawAnswerValue);
        answerLine.setQuestionKey(questionAnswer.getQuestion().getCode().getKey());
        answerLine.setUnitId(unitId);


		return answerLine;
	}

	private AnswerValue getAnswerValue(AnswerLine answerLine, QuestionAnswer questionAnswer) {
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

	private Question getAndVerifyQuestion(AnswerLine answerLine) {
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

	private Unit getAndVerifyUnit(AnswerLine answerLine, UnitCategory questionUnitCategory, String questionKey) {
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
			throw new RuntimeException(String.format(ERROR_ANSWER_UNIT_REQUIRED, questionKey,
					questionUnitCategory.getName()));
		}
		Unit answerUnit = unitService.findById(answerUnitId.longValue());

		// check unit category => throw an Exception if client provided an invalid unit (not part of the question's unit category)
		UnitCategory answerUnitCategory = answerUnit.getCategory();
		if (!questionUnitCategory.equals(answerUnitCategory)) {
			throw new RuntimeException(String.format(ERROR_ANSWER_UNIT_INVALID, questionKey,
					questionUnitCategory.getName(), answerUnit.getName(), answerUnitCategory.getName()));
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
