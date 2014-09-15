package eu.factorx.awac.models.data.answer;

import javax.persistence.*;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import eu.factorx.awac.models.AuditedAbstractEntity;

@Entity
@Table(name = "answer_value")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "answertype")
@Access(AccessType.PROPERTY)
public abstract class AnswerValue extends AuditedAbstractEntity {

	private static final long serialVersionUID = 1L;

	protected AnswerRawData rawData;

	protected AnswerType answerType;

	protected QuestionAnswer questionAnswer;

    protected String comment;

	protected AnswerValue() {
		super();
	}

	@Embedded
	protected abstract AnswerRawData getRawData();

	protected abstract void setRawData(AnswerRawData rawData);

	@Enumerated(EnumType.STRING)
	@Column(name = "answertype", insertable = false, updatable = false)
	public AnswerType getAnswerType() {
		return answerType;
	}

	public void setAnswerType(AnswerType answerType) {
		this.answerType = answerType;
	}

	@ManyToOne(optional = false)
	public QuestionAnswer getQuestionAnswer() {
		return questionAnswer;
	}

	public void setQuestionAnswer(QuestionAnswer param) {
		this.questionAnswer = param;
	}

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
	public abstract String toString();

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof AnswerValue)) {
			return false;
		}
		AnswerValue rhs = (AnswerValue) obj;
		return new EqualsBuilder().append(this.questionAnswer, rhs.questionAnswer).append(this.rawData, rhs.rawData).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(this.questionAnswer).append(this.rawData).toHashCode();
	}

}
