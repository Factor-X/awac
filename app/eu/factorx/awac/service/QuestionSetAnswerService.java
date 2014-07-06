package eu.factorx.awac.service;

import java.util.List;

import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.data.QuestionSetAnswer;
import eu.factorx.awac.models.knowledge.Period;

public interface QuestionSetAnswerService extends PersistenceService<QuestionSetAnswer> {

	List<QuestionSetAnswer> findByScopeAndPeriod(Scope scope, Period period);
	
}
