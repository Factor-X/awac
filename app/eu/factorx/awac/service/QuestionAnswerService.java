package eu.factorx.awac.service;

import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.data.answer.AnswerValue;
import eu.factorx.awac.models.data.answer.QuestionAnswer;
import eu.factorx.awac.models.data.question.Question;
import eu.factorx.awac.models.knowledge.Period;

public interface QuestionAnswerService extends PersistenceService<QuestionAnswer<?>> {

	<T extends AnswerValue> QuestionAnswer<T> findByScopeAndPeriod(Question<T> question, Scope scope, Period period);

}
