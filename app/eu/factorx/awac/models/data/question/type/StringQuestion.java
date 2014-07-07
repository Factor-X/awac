package eu.factorx.awac.models.data.question.type;

import eu.factorx.awac.models.code.type.QuestionCode;
import eu.factorx.awac.models.data.answer.type.StringAnswerValue;
import eu.factorx.awac.models.data.question.Question;
import eu.factorx.awac.models.data.question.QuestionSet;

public class StringQuestion extends Question<StringAnswerValue> {

	private static final long serialVersionUID = 1L;

	protected StringQuestion() {
		super();
	}

	public StringQuestion(QuestionSet questionSet, int orderIndex, QuestionCode code) {
		super(questionSet, orderIndex, code);
	}

}
