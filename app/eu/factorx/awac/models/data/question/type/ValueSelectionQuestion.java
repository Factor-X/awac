package eu.factorx.awac.models.data.question.type;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.NamedQuery;

import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.code.type.QuestionCode;
import eu.factorx.awac.models.data.answer.AnswerType;
import eu.factorx.awac.models.data.question.Question;
import eu.factorx.awac.models.data.question.QuestionSet;

@Entity
@NamedQuery(name = ValueSelectionQuestion.FIND_ALL_USED_CODE_LISTS, query = "select distinct q.codeList from ValueSelectionQuestion q")
public class ValueSelectionQuestion extends Question {

	private static final long serialVersionUID = 1L;

	public static final String FIND_ALL_USED_CODE_LISTS = "ValueSelectionQuestion.findAllUsedCodeLists";

	@Enumerated(EnumType.STRING)
	private CodeList codeList;

	protected ValueSelectionQuestion() {
		super();
	}

	public ValueSelectionQuestion(QuestionSet questionSet, int orderIndex, QuestionCode code, CodeList codeList) {
		super(questionSet, orderIndex, code);
		this.codeList = codeList;
	}

	public CodeList getCodeList() {
		return codeList;
	}

	public void setCodeList(CodeList codeList) {
		this.codeList = codeList;
	}

	@Override
	public AnswerType getAnswerType() {
		return AnswerType.VALUE_SELECTION;
	}

}
