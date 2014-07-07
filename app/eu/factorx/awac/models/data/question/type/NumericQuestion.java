package eu.factorx.awac.models.data.question.type;

import javax.persistence.Entity;
import javax.persistence.Enumerated;

import eu.factorx.awac.models.code.type.QuestionCode;
import eu.factorx.awac.models.data.answer.type.NumericAnswerValue;
import eu.factorx.awac.models.data.question.Question;
import eu.factorx.awac.models.data.question.QuestionSet;
import eu.factorx.awac.models.knowledge.UnitCategory;

@Entity
public class NumericQuestion<T extends Number> extends Question<NumericAnswerValue<T>> {

	private static final long serialVersionUID = 1L;

	@Enumerated
	protected UnitCategory unitCategory;

	protected NumericQuestion() {
		super();
	}

	public NumericQuestion(QuestionSet questionSet, int orderIndex, QuestionCode code, UnitCategory unitCategory) {
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
