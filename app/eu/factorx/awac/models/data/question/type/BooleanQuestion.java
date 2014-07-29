package eu.factorx.awac.models.data.question.type;

import eu.factorx.awac.models.code.type.QuestionCode;
import eu.factorx.awac.models.data.answer.AnswerType;
import eu.factorx.awac.models.data.question.Question;
import eu.factorx.awac.models.data.question.QuestionSet;

import javax.persistence.Entity;

@Entity
public class BooleanQuestion extends Question {

	private static final long serialVersionUID = 1L;

	protected BooleanQuestion() {
		super();
	}

	protected Boolean defaultValue;

	public BooleanQuestion(QuestionSet questionSet, int orderIndex, QuestionCode code) {
		super(questionSet, orderIndex, code);
	}

	public BooleanQuestion(QuestionSet questionSet, int orderIndex, QuestionCode code, Boolean defaultValue) {
		super(questionSet, orderIndex, code);
		this.defaultValue = defaultValue;
	}

	@Override
	public AnswerType getAnswerType() {
		return AnswerType.BOOLEAN;
	}

	public Boolean getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(Boolean defaultValue) {
		this.defaultValue = defaultValue;
	}
}
