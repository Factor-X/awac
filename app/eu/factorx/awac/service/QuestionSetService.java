package eu.factorx.awac.service;

import java.util.Collection;
import java.util.List;

import eu.factorx.awac.models.data.question.QuestionSet;

public interface QuestionSetService extends PersistenceService<QuestionSet> {

	List<QuestionSet> findQuestionSetsWithoutParentByCodes(Collection<String> codes);

}
