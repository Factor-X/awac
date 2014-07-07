package eu.factorx.awac.models.data.answer;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import eu.factorx.awac.models.AbstractEntity;

@Entity
@Table(name = "answer_value")
public abstract class AnswerValue extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@Embedded
	protected AnswerRawData rawData;

	@ManyToOne
	protected QuestionAnswer questionAnswer;

	protected AnswerValue() {
		super();
	}

	protected abstract AnswerRawData getRawData();

	protected abstract void setRawData(AnswerRawData rawData);

	public QuestionAnswer getQuestionAnswer() {
	    return questionAnswer;
	}

	public void setQuestionAnswer(QuestionAnswer param) {
	    this.questionAnswer = param;
	}

}
