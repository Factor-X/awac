package eu.factorx.awac.service;

import java.util.List;

import eu.factorx.awac.controllers.AverageController;
import eu.factorx.awac.models.code.type.QuestionCode;
import eu.factorx.awac.models.data.answer.QuestionAnswer;
import eu.factorx.awac.models.data.question.Question;
import eu.factorx.awac.models.forms.AwacCalculatorInstance;

public interface QuestionAnswerService extends PersistenceService<QuestionAnswer> {

	List<QuestionAnswer> findByParameters(QuestionAnswerSearchParameter searchParameter);

	List<QuestionAnswer> findByCodes(List<QuestionCode> codes);

    public List<QuestionAnswer> findByQuestionAndCalculatorInstance(Question question, List<AverageController.ScopeAndPeriod> scopeAndPeriodList);
}
