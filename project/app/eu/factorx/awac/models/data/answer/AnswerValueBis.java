package eu.factorx.awac.models.data.answer;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import eu.factorx.awac.models.AbstractEntity;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "answer_value")
@Access(AccessType.PROPERTY)
public abstract class AnswerValueBis extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	protected AnswerRawData rawData;

	protected QuestionAnswer questionAnswer;

	protected AnswerValueBis() {
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
