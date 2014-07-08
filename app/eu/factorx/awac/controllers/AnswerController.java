package eu.factorx.awac.controllers;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.awac.post.AnswersSaveDTO;
import eu.factorx.awac.dto.awac.shared.AnswerLine;
import eu.factorx.awac.dto.myrmex.post.ProductCreateFormDTO;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.code.type.QuestionCode;
import eu.factorx.awac.models.data.answer.AnswerValue;
import eu.factorx.awac.models.data.answer.QuestionAnswer;
import eu.factorx.awac.models.data.answer.type.BooleanAnswerValue;
import eu.factorx.awac.models.data.answer.type.CodeAnswerValue;
import eu.factorx.awac.models.data.answer.type.DoubleAnswerValue;
import eu.factorx.awac.models.data.answer.type.EntityAnswerValue;
import eu.factorx.awac.models.data.answer.type.IntegerAnswerValue;
import eu.factorx.awac.models.data.answer.type.StringAnswerValue;
import eu.factorx.awac.models.data.question.Question;
import eu.factorx.awac.models.data.question.type.BooleanQuestion;
import eu.factorx.awac.models.data.question.type.DoubleQuestion;
import eu.factorx.awac.models.data.question.type.EntitySelectionQuestion;
import eu.factorx.awac.models.data.question.type.IntegerQuestion;
import eu.factorx.awac.models.data.question.type.StringQuestion;
import eu.factorx.awac.models.data.question.type.ValueSelectionQuestion;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.models.knowledge.Unit;
import eu.factorx.awac.models.knowledge.UnitCategory;
import eu.factorx.awac.service.PeriodService;
import eu.factorx.awac.service.QuestionAnswerService;
import eu.factorx.awac.service.QuestionService;
import eu.factorx.awac.service.ScopeService;
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
    private QuestionService questionService;
    @Autowired
    private QuestionAnswerService questionAnswerService;
    @Autowired
    private UnitService unitService;

    @Autowired
    private SecuredController securedController;

    @Transactional(readOnly = true)
    @Security.Authenticated(SecuredController.class)
    public Result getByForm(Integer formId, Integer periodId, Integer scopeId) {

        ProductCreateFormDTO productCreateFormDTO = DTO.getDTO(request().body().asJson(), ProductCreateFormDTO.class);

        return null;
    }

    @Transactional(readOnly = false)
    @Security.Authenticated(SecuredController.class)
    public Result save() {
        Account currentUser = securedController.getCurrentUser();
        AnswersSaveDTO answersDTO = extractDTOFromRequest(AnswersSaveDTO.class);
        Period period = periodService.findById(answersDTO.getPeriodId().longValue());
        Scope scope = scopeService.findById(answersDTO.getScopeId().longValue());

        for (AnswerLine answerLine : answersDTO.getListQuestionValueDTO()) {
            Question question = getAndVerifyQuestion(answerLine);
            // TODO The concept of "repetition" (== answers groups linked to a questions set) is not yet implemented in the client...
            // => set repetitionIndex = 0
            QuestionAnswer questionAnswer = new QuestionAnswer(period, scope, currentUser, question, 0);
            // TODO A single QuestionAnswer may be linked to several answer values (all of the same type); this is not yet implemented in DTOs (only one Object returned)
            // => add only one AnswerValue in answerValues list
            AnswerValue answerValue = getAnswerValue(answerLine, question, questionAnswer);
            if (answerValue == null) {
                throw new RuntimeException("The question of type '" + question.getClass() + "' cannot be treated");
            }
            questionAnswer.getAnswerValues().add(answerValue);
            questionAnswerService.saveOrUpdate(questionAnswer);
        }
        return ok();
    }

    private AnswerValue getAnswerValue(AnswerLine answerLine, Question question, QuestionAnswer questionAnswer) {
        Object rawAnswerValue = answerLine.getValue();
        AnswerValue answerValue = null;

        if (question instanceof BooleanQuestion) {
            answerValue = new BooleanAnswerValue(questionAnswer, (Boolean) rawAnswerValue);

        } else if (question instanceof StringQuestion) {
            answerValue = new StringAnswerValue(questionAnswer, (String) rawAnswerValue);

        } else if (question instanceof IntegerQuestion) {
            UnitCategory unitCategory = ((IntegerQuestion) question).getUnitCategory();
            Unit unit = getAndVerifyUnit(answerLine, unitCategory, question.getCode().getKey());
            answerValue = new IntegerAnswerValue(questionAnswer, (Integer) rawAnswerValue, unit);

        } else if (question instanceof DoubleQuestion) {
            UnitCategory unitCategory = ((DoubleQuestion) question).getUnitCategory();
            Unit unit = getAndVerifyUnit(answerLine, unitCategory, question.getCode().getKey());
            answerValue = new DoubleAnswerValue(questionAnswer, (Double) rawAnswerValue, unit);

        } else if (question instanceof ValueSelectionQuestion) {
            CodeList codeList = ((ValueSelectionQuestion) question).getCodeList();
            answerValue = new CodeAnswerValue(questionAnswer, new Code(codeList, (String) rawAnswerValue));

        } else if (question instanceof EntitySelectionQuestion) {
            String entityName = ((EntitySelectionQuestion) question).getEntityName();
            answerValue = new EntityAnswerValue(questionAnswer, entityName, (Long) rawAnswerValue);
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
            throw new RuntimeException(String.format(ERROR_ANSWER_UNIT_REQUIRED, questionKey, questionUnitCategory.getCode()));
        }
        Unit answerUnit = unitService.findById(answerUnitId.longValue());

        // check unit category => throw an Exception if client provided an invalid unit (not part of the question's unit category)
        UnitCategory answerUnitCategory = answerUnit.getCategory();
        if (!questionUnitCategory.equals(answerUnitCategory)) {
            throw new RuntimeException(String.format(ERROR_ANSWER_UNIT_INVALID, questionKey, questionUnitCategory.getCode(), answerUnit.getName(), answerUnitCategory.getCode()));
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
