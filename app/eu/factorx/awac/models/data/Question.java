package eu.factorx.awac.models.data;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import eu.factorx.awac.models.AbstractEntity;
import eu.factorx.awac.models.code.QuestionCode;
import eu.factorx.awac.models.knowledge.UnitCategory;

@Entity
@Table(name = "question")
public class Question extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@ManyToOne(optional = false)
	private QuestionSet questionSet;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "code")) })
	private QuestionCode code;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "type", column = @Column(name = "type")),
			@AttributeOverride(name = "valuesList.key", column = @Column(name = "code_type")) })
	private AnswerType answerType;

	@ManyToOne
	private UnitCategory unitCategory;

	private Short orderIndex;

	public Question() {
		super();
	}

	public Question(QuestionSet questionSet, QuestionCode code, AnswerType answerType, UnitCategory unitCategory,
			short orderIndex) {
		super();
		this.questionSet = questionSet;
		this.code = code;
		this.answerType = answerType;
		this.unitCategory = unitCategory;
		this.orderIndex = orderIndex;
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

	public AnswerType getAnswerType() {
		return answerType;
	}

	public void setAnswerType(AnswerType answerType) {
		this.answerType = answerType;
	}

	public UnitCategory getUnitCategory() {
		return unitCategory;
	}

	public void setUnitCategory(UnitCategory unitCategory) {
		this.unitCategory = unitCategory;
	}

	public Short getOrderIndex() {
		return orderIndex;
	}

	public void setOrderIndex(Short orderIndex) {
		this.orderIndex = orderIndex;
	}

}
