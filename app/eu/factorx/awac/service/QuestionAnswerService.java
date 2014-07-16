package eu.factorx.awac.service;

import java.util.List;

import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.code.type.QuestionCode;
import eu.factorx.awac.models.data.answer.QuestionAnswer;
import eu.factorx.awac.models.knowledge.Period;

public interface QuestionAnswerService extends PersistenceService<QuestionAnswer> {

	List<QuestionAnswer> findByScopeAndPeriod(Scope scope, Period period);

	List<QuestionAnswer> findByScopeAndPeriodAndQuestionSet(Scope scope, Period period, QuestionCode questionSetCode);

	List<QuestionAnswer> findByCodes(List<QuestionCode> codes);

}
