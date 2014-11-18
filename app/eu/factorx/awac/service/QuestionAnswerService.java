package eu.factorx.awac.service;

import eu.factorx.awac.controllers.AverageController;
import eu.factorx.awac.models.code.type.QuestionCode;
import eu.factorx.awac.models.data.answer.QuestionAnswer;
import eu.factorx.awac.models.data.answer.QuestionSetAnswer;
import eu.factorx.awac.models.data.question.Question;

import java.util.List;

public interface QuestionAnswerService extends PersistenceService<QuestionAnswer> {

	List<QuestionAnswer> findByParameters(QuestionAnswerSearchParameter searchParameter);

	List<QuestionAnswer> findByCodes(List<QuestionCode> codes);

	public List<QuestionAnswer> findByQuestionAndCalculatorInstance(Question question, List<AverageController.ScopeAndPeriod> scopeAndPeriodList);

	public List<QuestionAnswer> findByQuestionAndCalculatorInstance(Question question, AverageController.ScopeAndPeriod scopeAndPeriod);

	public List<QuestionAnswer> findByQuestionAndQuestionSetAnswers(Question question, List<QuestionSetAnswer> questionSetAnswers);
}
