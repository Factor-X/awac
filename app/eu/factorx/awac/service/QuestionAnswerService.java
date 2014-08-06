package eu.factorx.awac.service;

import eu.factorx.awac.models.code.type.QuestionCode;
import eu.factorx.awac.models.data.answer.QuestionAnswer;
import eu.factorx.awac.models.data.answer.QuestionSetAnswer;

import java.util.List;

public interface QuestionAnswerService extends PersistenceService<QuestionAnswer> {

	List<QuestionAnswer> findByParameters(QuestionAnswerSearchParameter searchParameter);

	List<QuestionAnswer> findByCodes(List<QuestionCode> codes);

}
