package eu.factorx.awac.models.data.answer;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import eu.factorx.awac.models.AbstractEntity;

@Entity
@Table(name = "answer_value")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "answer_type")
public abstract class AnswerValue extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@ManyToOne(optional = false)
	protected QuestionAnswer<? extends AnswerValue> questionAnswer;

	@Embedded
	protected AnswerRawData rawData;

	protected AnswerValue() {
		super();
	}

	public QuestionAnswer<? extends AnswerValue> getQuestionAnswer() {
		return questionAnswer;
	}

	public void setQuestionAnswer(QuestionAnswer<? extends AnswerValue> questionAnswer) {
		this.questionAnswer = questionAnswer;
	}

	protected abstract AnswerRawData getRawData();

	protected abstract void setRawData(AnswerRawData rawData);

}
