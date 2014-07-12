package eu.factorx.awac.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import eu.factorx.awac.dto.awac.get.*;
import eu.factorx.awac.models.code.label.CodeLabel;
import eu.factorx.awac.service.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import play.Logger;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.awac.post.AnswersSaveDTO;
import eu.factorx.awac.dto.awac.shared.AnswerLine;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.code.type.QuestionCode;
import eu.factorx.awac.models.data.answer.AnswerType;
import eu.factorx.awac.models.data.answer.AnswerValue;
import eu.factorx.awac.models.data.answer.QuestionAnswer;
import eu.factorx.awac.models.data.answer.type.BooleanAnswerValue;
import eu.factorx.awac.models.data.answer.type.CodeAnswerValue;
import eu.factorx.awac.models.data.answer.type.DoubleAnswerValue;
import eu.factorx.awac.models.data.answer.type.EntityAnswerValue;
import eu.factorx.awac.models.data.answer.type.IntegerAnswerValue;
import eu.factorx.awac.models.data.answer.type.StringAnswerValue;
import eu.factorx.awac.models.data.question.Question;
import eu.factorx.awac.models.data.question.type.DoubleQuestion;
import eu.factorx.awac.models.data.question.type.EntitySelectionQuestion;
import eu.factorx.awac.models.data.question.type.IntegerQuestion;
import eu.factorx.awac.models.data.question.type.NumericQuestion;
import eu.factorx.awac.models.data.question.type.ValueSelectionQuestion;
import eu.factorx.awac.models.forms.Form;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.models.knowledge.Unit;
import eu.factorx.awac.models.knowledge.UnitCategory;

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
    private SecuredController securedController;

    @Transactional(readOnly = true)
    @Security.Authenticated(SecuredController.class)
    public Result getByForm(String formIdentifier, Long periodId, Long scopeId) {

        Form form = formService.findByIdentifier(formIdentifier);
        Period period = periodService.findById(periodId);
        Scope scope = scopeService.findById(scopeId);

        List<Question> questions = questionService.findByForm(form);
        Map<String, QuestionAnswer> questionAnswersByKey = getQuestionAnswersByKey(period, scope);
        List<AnswerLine> listQuestionValueDTO = new ArrayList<>();
        List<QuestionDTO> questionDTOs = new ArrayList<>();
        for (Question question : questions) {
            String questionKey = question.getCode().getKey();
            AnswerType questionAnswerType = question.getAnswerType();
            Long unitCategoryId = null;
            if (questionAnswerType == AnswerType.INTEGER || questionAnswerType == AnswerType.DOUBLE) {
                UnitCategory unitCategory = ((NumericQuestion) question).getUnitCategory();
                if (unitCategory != null) {
                    unitCategoryId = unitCategory.getId();
                }
            }
            QuestionDTO questionDTO = new QuestionDTO(questionKey, unitCategoryId);

            if (question instanceof ValueSelectionQuestion) {
                ValueSelectionQuestion valueSelectionQuestion = (ValueSelectionQuestion) question;
                CodeList codeList = valueSelectionQuestion.getCodeList();
                String codeListName = codeList.name();
                questionDTO.setCodeListName(codeListName);
            }


            questionDTOs.add(questionDTO);
            listQuestionValueDTO.add(toAnswerLine(question, questionAnswersByKey.get(questionKey)));
        }

        List<CodeListDTO> codeLists = new ArrayList<>();
        List<String> addedCodes = new ArrayList<>();
        for (Question question : questions) {
            if (question instanceof ValueSelectionQuestion) {

                ValueSelectionQuestion valueSelectionQuestion = (ValueSelectionQuestion) question;
                CodeList codeList = valueSelectionQuestion.getCodeList();
                String codeListName = codeList.name();

                if (!addedCodes.contains(codeListName)) {

                    CodeListDTO codeListDTO = new CodeListDTO();
                    codeListDTO.setCodeLabels(new ArrayList<CodeLabelDTO>());
                    codeListDTO.setCode(codeListName);
                    List<CodeLabel> codeLabels = codeLabelService.findCodeLabelsByType(codeList);
                    for (CodeLabel codeLabel : codeLabels) {
                        codeListDTO.getCodeLabels().add(new CodeLabelDTO(codeLabel.getKey(), codeLabel.getLabelEn(), codeLabel.getLabelFr(), codeLabel.getLabelNl()));
                    }

                    codeLists.add(codeListDTO);
                    addedCodes.add(codeListName);
                }
            }
        }

        AnswersSaveDTO answersSaveDTO = new AnswersSaveDTO(scopeId, periodId, listQuestionValueDTO);
        FormDTO formDTO = new FormDTO(questionDTOs, getAllUnitCategories(), codeLists, answersSaveDTO);

        return ok(formDTO);
    }

    private List<UnitCategoryDTO> getAllUnitCategories() {
        List<UnitCategoryDTO> res = new ArrayList<>();
        for (UnitCategory unitCategory : unitCategoryService.findAll()) {
            UnitCategoryDTO unitCategoryDTO = new UnitCategoryDTO(unitCategory.getId());
            for (Unit unit : unitCategory.getUnits()) {
                unitCategoryDTO.addUnit(new UnitDTO(unit.getId(), unit.getName()));
            }
            res.add(unitCategoryDTO);
        }
        return res;
    }

    private Map<String, QuestionAnswer> getQuestionAnswersByKey(Period period, Scope scope) {
        List<QuestionAnswer> questionAnswers = questionAnswerService.findByScopeAndPeriod(scope, period);
        Map<String, QuestionAnswer> questionAnswersByKey = new HashMap<>();
        for (QuestionAnswer questionAnswer : questionAnswers) {
            questionAnswersByKey.put(questionAnswer.getQuestion().getCode().getKey(), questionAnswer);
        }
        return questionAnswersByKey;
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
        return ok();
    }

    public void saveAnswsersDTO(Account currentUser, AnswersSaveDTO answersDTO) {
        Period period = periodService.findById(answersDTO.getPeriodId().longValue());
        Scope scope = scopeService.findById(answersDTO.getScopeId().longValue());

        for (AnswerLine answerLine : answersDTO.getListAnswers()) {
            Question question = getAndVerifyQuestion(answerLine);
            // TODO The concept of "repetition" (== answers groups linked to a questions set) is not yet implemented in the client...
            // => set repetitionIndex = 0
            QuestionAnswer questionAnswer = new QuestionAnswer(period, scope, currentUser, question, 0);
            // TODO A single QuestionAnswer may be linked to several answer values (all of the same type); this is not yet implemented in DTOs (only one Object returned)
            // => add only one AnswerValue in answerValues list
            AnswerValue answerValue = getAnswerValue(answerLine, question, questionAnswer);
            if (answerValue == null) continue;
            questionAnswer.getAnswerValues().add(answerValue);
            questionAnswerService.saveOrUpdate(questionAnswer);
        }
    }

    private AnswerLine toAnswerLine(Question question, QuestionAnswer questionAnswer) {
        AnswerType answerType = question.getAnswerType();

        if (questionAnswer == null) {
            return new AnswerLine(question.getCode().getKey(), null);
        }
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
        return new AnswerLine(question.getCode().getKey(), rawAnswerValue, unitId);
    }

    private AnswerValue getAnswerValue(AnswerLine answerLine, Question question, QuestionAnswer questionAnswer) {
        if (answerLine.getValue() == null) {
            return null;
        }
        String rawAnswerValue = answerLine.getValue().toString();
        AnswerValue answerValue = null;

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
                    questionUnitCategory.getCode()));
        }
        Unit answerUnit = unitService.findById(answerUnitId.longValue());

        // check unit category => throw an Exception if client provided an invalid unit (not part of the question's unit category)
        UnitCategory answerUnitCategory = answerUnit.getCategory();
        if (!questionUnitCategory.equals(answerUnitCategory)) {
            throw new RuntimeException(String.format(ERROR_ANSWER_UNIT_INVALID, questionKey,
                    questionUnitCategory.getCode(), answerUnit.getName(), answerUnitCategory.getCode()));
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
