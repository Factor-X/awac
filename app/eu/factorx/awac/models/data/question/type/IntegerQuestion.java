package eu.factorx.awac.models.data.question.type;

import eu.factorx.awac.models.code.type.QuestionCode;
import eu.factorx.awac.models.data.answer.AnswerType;
import eu.factorx.awac.models.data.question.QuestionSet;
import eu.factorx.awac.models.knowledge.UnitCategory;

import javax.persistence.Entity;

@Entity
public class IntegerQuestion extends NumericQuestion {

	private static final long serialVersionUID = 1L;

	protected IntegerQuestion() {
		super();
	}

	public IntegerQuestion(QuestionSet questionSet, int orderIndex, QuestionCode code, UnitCategory unitCategory) {
		super(questionSet, orderIndex, code, unitCategory);
	}

	@Override
	public AnswerType getAnswerType() {
		return AnswerType.INTEGER;
	}

}
