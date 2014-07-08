package eu.factorx.awac.models.data.question.type;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import eu.factorx.awac.models.code.type.QuestionCode;
import eu.factorx.awac.models.data.answer.AnswerType;
import eu.factorx.awac.models.data.question.Question;
import eu.factorx.awac.models.data.question.QuestionSet;
import eu.factorx.awac.models.knowledge.UnitCategory;

@Entity
public class DoubleQuestion extends Question {

	private static final long serialVersionUID = 1L;

	@ManyToOne(optional = false)
	protected UnitCategory unitCategory;

	protected DoubleQuestion() {
		super();
	}

	public DoubleQuestion(QuestionSet questionSet, int orderIndex, QuestionCode code, UnitCategory unitCategory) {
		super(questionSet, orderIndex, code);
		this.unitCategory = unitCategory;
	}

	public UnitCategory getUnitCategory() {
		return unitCategory;
	}

	public void setUnitCategory(UnitCategory unitCategory) {
		this.unitCategory = unitCategory;
	}

	@Override
	public AnswerType getAnswerType() {
		return AnswerType.DOUBLE;
	}

}
