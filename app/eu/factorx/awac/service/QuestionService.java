package eu.factorx.awac.service;

import java.util.List;

import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.code.type.QuestionCode;
import eu.factorx.awac.models.data.question.Question;
import eu.factorx.awac.models.forms.Form;

public interface QuestionService extends PersistenceService<Question> {

	List<Question> findByCodes(List<QuestionCode> codes);

	Question findByCode(QuestionCode questionCode);

	List<Question> findByForm(Form form);

	List<CodeList> findAllCodeListsUsed();

}
