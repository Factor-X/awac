package eu.factorx.awac.models.data.question;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import eu.factorx.awac.models.AbstractEntity;
import eu.factorx.awac.models.code.type.QuestionCode;

@Entity
@Table(name = "question")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "question_type")
public class Question extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@ManyToOne(optional = false)
	protected QuestionSet questionSet;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "key", column = @Column(name = "code")) })
	protected QuestionCode code;

	protected int orderIndex = 0;

	protected Question() {
		super();
	}

	protected Question(QuestionSet questionSet, int orderIndex, QuestionCode code) {
		super();
		this.questionSet = questionSet;
		this.orderIndex = orderIndex;
		this.code = code;
	}

	public QuestionSet getQuestionSet() {
		return questionSet;
	}

	public void setQuestionSet(QuestionSet questionSet) {
		this.questionSet = questionSet;
	}

	public QuestionCode getCode() {
		return code;
	}

	public void setCode(QuestionCode code) {
		this.code = code;
	}

	public int getOrderIndex() {
		return orderIndex;
	}

	public void setOrderIndex(int orderIndex) {
		this.orderIndex = orderIndex;
	}

}
