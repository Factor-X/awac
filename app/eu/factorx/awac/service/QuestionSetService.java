package eu.factorx.awac.service;

import eu.factorx.awac.models.code.type.QuestionCode;
import eu.factorx.awac.models.data.question.QuestionSet;

public interface QuestionSetService extends PersistenceService<QuestionSet> {

    QuestionSet findByCode(QuestionCode questionCode);
}
