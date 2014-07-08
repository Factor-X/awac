package eu.factorx.awac.models.data.question.type;

import javax.persistence.Entity;
import javax.persistence.Enumerated;

import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.code.type.QuestionCode;
import eu.factorx.awac.models.data.question.Question;
import eu.factorx.awac.models.data.question.QuestionSet;

@Entity
public class ValueSelectionQuestion extends Question {

	private static final long serialVersionUID = 1L;

	@Enumerated
	private CodeList codeList;

	protected ValueSelectionQuestion() {
		super();
	}

	public ValueSelectionQuestion(QuestionSet questionSet, int orderIndex,
			QuestionCode code, CodeList codeList) {
		super(questionSet, orderIndex, code);
		this.codeList = codeList;
	}

	public CodeList getCodeList() {
		return codeList;
	}

	public void setCodeList(CodeList codeList) {
		this.codeList = codeList;
	}

}
