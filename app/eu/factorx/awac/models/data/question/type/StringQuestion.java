package eu.factorx.awac.models.data.question.type;

import eu.factorx.awac.models.code.type.QuestionCode;
import eu.factorx.awac.models.data.answer.AnswerType;
import eu.factorx.awac.models.data.question.Question;
import eu.factorx.awac.models.data.question.QuestionSet;

import javax.persistence.Entity;

@Entity
public class StringQuestion extends Question {

	private static final long serialVersionUID = 1L;

	protected String defaultValue = null;

	protected StringQuestion() {
		super();
	}

	public StringQuestion(QuestionSet questionSet, int orderIndex, QuestionCode code) {
		super(questionSet, orderIndex, code);
	}
	public StringQuestion(QuestionSet questionSet, int orderIndex, QuestionCode code, String defaultValue) {
		super(questionSet, orderIndex, code);

		this.defaultValue = defaultValue;
	}



	@Override
	public AnswerType getAnswerType() {
		return AnswerType.STRING;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
}
