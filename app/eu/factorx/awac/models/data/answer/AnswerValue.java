package eu.factorx.awac.models.data.answer;

import eu.factorx.awac.models.AbstractEntity;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "answer_value")
@Access(AccessType.PROPERTY)
public abstract class AnswerValue extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	protected AnswerRawData rawData;

	protected QuestionAnswer questionAnswer;

	protected AnswerValue() {
		super();
	}

	@Embedded
	protected abstract AnswerRawData getRawData();

	protected abstract void setRawData(AnswerRawData rawData);

	@ManyToOne(optional = false)
	public QuestionAnswer getQuestionAnswer() {
		return questionAnswer;
	}

	public void setQuestionAnswer(QuestionAnswer param) {
		this.questionAnswer = param;
	}

}
