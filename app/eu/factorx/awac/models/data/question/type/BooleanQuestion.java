package eu.factorx.awac.models.data.question.type;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import eu.factorx.awac.models.code.type.QuestionCode;
import eu.factorx.awac.models.data.answer.type.BooleanAnswerValue;
import eu.factorx.awac.models.data.question.Question;
import eu.factorx.awac.models.data.question.QuestionSet;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("BOOLEAN")
public class BooleanQuestion extends Question<BooleanAnswerValue> {

	private static final long serialVersionUID = 1L;

	protected BooleanQuestion() {
		super();
	}

	public BooleanQuestion(QuestionSet questionSet, int orderIndex, QuestionCode code) {
		super(questionSet, orderIndex, code);
	}

}
