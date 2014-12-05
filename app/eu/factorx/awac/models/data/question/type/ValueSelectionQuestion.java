package eu.factorx.awac.models.data.question.type;

import javax.persistence.*;

import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.code.type.QuestionCode;
import eu.factorx.awac.models.data.answer.AnswerType;
import eu.factorx.awac.models.data.question.Question;
import eu.factorx.awac.models.data.question.QuestionSet;

@Entity
@NamedQueries({
		@NamedQuery(name = ValueSelectionQuestion.FIND_ALL_USED_CODE_LISTS, query = "select distinct q.codeList from ValueSelectionQuestion q"),
		@NamedQuery(name = ValueSelectionQuestion.FIND_CODE_LISTS_BY_QUESTION_SETS, query = "select distinct q.codeList from ValueSelectionQuestion q where q.questionSet in :questionSets"),

})

public class ValueSelectionQuestion extends Question {

	private static final long serialVersionUID = 1L;

	public static final String FIND_ALL_USED_CODE_LISTS = "ValueSelectionQuestion.findAllUsedCodeLists";

	public static final String FIND_CODE_LISTS_BY_QUESTION_SETS = "ValueSelectionQuestion.findCodeListsByQuestionSets";

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
