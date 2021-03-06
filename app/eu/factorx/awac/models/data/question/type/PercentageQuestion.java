package eu.factorx.awac.models.data.question.type;

import javax.persistence.Entity;

import eu.factorx.awac.models.code.type.QuestionCode;
import eu.factorx.awac.models.data.answer.AnswerType;
import eu.factorx.awac.models.data.question.QuestionSet;

@Entity
public class PercentageQuestion extends NumericQuestion {

	private static final long serialVersionUID = 1L;

	protected PercentageQuestion() {
		super();
	}

	public PercentageQuestion(QuestionSet questionSet, int orderIndex, QuestionCode code) {
		super(questionSet, orderIndex, code,null);
	}

	@Override
	public AnswerType getAnswerType() {
		return AnswerType.PERCENTAGE;
	}

}
