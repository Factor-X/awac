package eu.factorx.awac.models.data.question.type;

import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import eu.factorx.awac.models.code.type.QuestionCode;
import eu.factorx.awac.models.data.question.Question;
import eu.factorx.awac.models.data.question.QuestionSet;
import eu.factorx.awac.models.knowledge.UnitCategory;

@MappedSuperclass
public abstract class NumericQuestion extends Question {

	private static final long serialVersionUID = 1L;

	@ManyToOne(optional = true)
	protected UnitCategory unitCategory;

	protected NumericQuestion() {
		super();
	}

	protected NumericQuestion(QuestionSet questionSet, int orderIndex, QuestionCode code, UnitCategory unitCategory) {
		super(questionSet, orderIndex, code);
		this.unitCategory = unitCategory;
	}

	public UnitCategory getUnitCategory() {
		return unitCategory;
	}

	public void setUnitCategory(UnitCategory unitCategory) {
		this.unitCategory = unitCategory;
	}

}
