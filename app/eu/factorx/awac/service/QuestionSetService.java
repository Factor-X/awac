package eu.factorx.awac.service;

import java.util.List;

import eu.factorx.awac.models.data.question.QuestionSet;
import eu.factorx.awac.models.forms.Form;

public interface QuestionSetService extends PersistenceService<QuestionSet> {

	List<QuestionSet> findByForm(Form form);

}
