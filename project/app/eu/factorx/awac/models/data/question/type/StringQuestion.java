package eu.factorx.awac.models.data.question.type;

import javax.persistence.Entity;

import eu.factorx.awac.models.code.type.QuestionCode;
import eu.factorx.awac.models.data.answer.AnswerType;
import eu.factorx.awac.models.data.question.Question;
import eu.factorx.awac.models.data.question.QuestionSet;

@Entity
public class StringQuestion extends Question {

	private static final long serialVersionUID = 1L;

	protected StringQuestion() {
		super();
	}

	public StringQuestion(QuestionSet questionSet, int orderIndex, QuestionCode code) {
		super(questionSet, orderIndex, code);
	}

	@Override
	public AnswerType getAnswerType() {
		return AnswerType.STRING;
	}

}
