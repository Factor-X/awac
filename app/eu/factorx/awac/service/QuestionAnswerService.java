package eu.factorx.awac.service;

import eu.factorx.awac.models.code.type.QuestionCode;
import eu.factorx.awac.models.data.answer.QuestionAnswer;

import java.util.List;

public interface QuestionAnswerService extends PersistenceService<QuestionAnswer> {

	List<QuestionAnswer> findByCodes(List<QuestionCode> codes);

}
