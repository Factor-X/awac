package eu.factorx.awac.service;

import java.util.List;

import eu.factorx.awac.models.code.type.QuestionCode;
import eu.factorx.awac.models.data.question.Question;
import eu.factorx.awac.models.forms.Form;

public interface QuestionService extends PersistenceService<Question> {

	public List<Question> findByCodes(List<QuestionCode> codes);

	public Question findByCode(QuestionCode questionCode);

	public List<Question> findByForm(Form form);
	
}
