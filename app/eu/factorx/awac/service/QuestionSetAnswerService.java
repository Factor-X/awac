package eu.factorx.awac.service;

import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.code.type.QuestionCode;
import eu.factorx.awac.models.data.answer.QuestionSetAnswer;
import eu.factorx.awac.models.forms.Form;
import eu.factorx.awac.models.knowledge.Period;

import java.util.List;

public interface QuestionSetAnswerService extends PersistenceService<QuestionSetAnswer> {

	List<QuestionSetAnswer> findByScopeAndPeriod(Scope scope, Period period);

	List<QuestionSetAnswer> findByScopeAndPeriodAndForm(Scope scope, Period period, Form form);

	List<QuestionSetAnswer> findByCodes(List<QuestionCode> codes);

}
