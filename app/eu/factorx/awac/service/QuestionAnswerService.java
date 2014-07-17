package eu.factorx.awac.service;

import java.util.List;

import eu.factorx.awac.models.code.type.QuestionCode;
import eu.factorx.awac.models.data.answer.QuestionAnswer;

public interface QuestionAnswerService extends PersistenceService<QuestionAnswer> {

	List<QuestionAnswer> findByCodes(List<QuestionCode> codes);

}
