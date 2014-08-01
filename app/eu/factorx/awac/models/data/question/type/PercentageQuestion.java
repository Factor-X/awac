package eu.factorx.awac.models.data.question.type;

import eu.factorx.awac.models.code.type.QuestionCode;
import eu.factorx.awac.models.data.answer.AnswerType;
import eu.factorx.awac.models.data.question.QuestionSet;

import javax.persistence.Entity;

@Entity
public class PercentageQuestion extends NumericQuestion {

	private static final long serialVersionUID = 1L;

	protected PercentageQuestion() {
		super();
	}

	public PercentageQuestion(QuestionSet questionSet, int orderIndex, QuestionCode code) {
		super(questionSet, orderIndex, code,null);
	}

	public PercentageQuestion(QuestionSet questionSet, int orderIndex, QuestionCode code,Double defaultValue) {
		super(questionSet, orderIndex, code, null,defaultValue);
	}

	@Override
	public AnswerType getAnswerType() {
		return AnswerType.PERCENTAGE;
	}

}