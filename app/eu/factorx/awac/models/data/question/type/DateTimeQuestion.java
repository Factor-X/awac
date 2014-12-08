package eu.factorx.awac.models.data.question.type;

import eu.factorx.awac.models.code.type.QuestionCode;
import eu.factorx.awac.models.data.answer.AnswerType;
import eu.factorx.awac.models.data.question.Question;
import eu.factorx.awac.models.data.question.QuestionSet;

import javax.persistence.Entity;

@Entity
public class DateTimeQuestion extends Question {

	private static final long serialVersionUID = 1L;

	protected DateTimeQuestion() {
		super();
	}

	public DateTimeQuestion(QuestionSet questionSet, int orderIndex, QuestionCode code) {
		super(questionSet, orderIndex, code);
	}

	@Override
	public AnswerType getAnswerType() {
		return AnswerType.DATE_TIME;
	}

}
