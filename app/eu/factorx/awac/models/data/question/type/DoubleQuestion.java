package eu.factorx.awac.models.data.question.type;

import javax.persistence.Entity;

import eu.factorx.awac.models.code.type.QuestionCode;
import eu.factorx.awac.models.data.answer.AnswerType;
import eu.factorx.awac.models.data.question.QuestionSet;
import eu.factorx.awac.models.knowledge.Unit;
import eu.factorx.awac.models.knowledge.UnitCategory;

@Entity
public class DoubleQuestion extends NumericQuestion {

	private static final long serialVersionUID = 1L;

	protected DoubleQuestion() {
		super();
	}

	public DoubleQuestion(QuestionSet questionSet, int orderIndex, QuestionCode code, UnitCategory unitCategory) {
		super(questionSet, orderIndex, code, unitCategory);
	}

    public DoubleQuestion(QuestionSet questionSet, int orderIndex, QuestionCode code, UnitCategory unitCategory, Unit defaultUnit) {
        super(questionSet, orderIndex, code, unitCategory,defaultUnit);
    }

    @Override
	public AnswerType getAnswerType() {
		return AnswerType.DOUBLE;
	}

}
