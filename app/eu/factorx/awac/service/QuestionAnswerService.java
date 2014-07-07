package eu.factorx.awac.service;

import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.data.answer.QuestionAnswer;
import eu.factorx.awac.models.data.question.Question;
import eu.factorx.awac.models.knowledge.Period;

public interface QuestionAnswerService extends PersistenceService<QuestionAnswer> {

	QuestionAnswer findByScopeAndPeriod(Question question, Scope scope, Period period);

}
