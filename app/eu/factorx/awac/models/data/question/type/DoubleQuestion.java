package eu.factorx.awac.models.data.question.type;

import eu.factorx.awac.models.code.type.QuestionCode;
import eu.factorx.awac.models.data.answer.AnswerType;
import eu.factorx.awac.models.data.question.QuestionSet;
import eu.factorx.awac.models.knowledge.Unit;
import eu.factorx.awac.models.knowledge.UnitCategory;

import javax.persistence.Entity;

@Entity
public class DoubleQuestion extends NumericQuestion {

	private static final long serialVersionUID = 1L;

	protected DoubleQuestion() {
		super();
	}

	public DoubleQuestion(QuestionSet questionSet, int orderIndex, QuestionCode code, UnitCategory unitCategory) {
		super(questionSet, orderIndex, code, unitCategory);
	}

	public DoubleQuestion(QuestionSet questionSet, int orderIndex, QuestionCode code, UnitCategory unitCategory, Double defaultValue) {
		super(questionSet, orderIndex, code, unitCategory, defaultValue);
	}

    public DoubleQuestion(QuestionSet questionSet, int orderIndex, QuestionCode code, UnitCategory unitCategory, Double defaultValue, Unit defaultUnit) {
        super(questionSet, orderIndex, code, unitCategory, defaultValue,defaultUnit);
    }

    @Override
	public AnswerType getAnswerType() {
		return AnswerType.DOUBLE;
	}

}
