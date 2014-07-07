package eu.factorx.awac.models.data.question.type;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.type.QuestionCode;
import eu.factorx.awac.models.data.question.Question;
import eu.factorx.awac.models.data.question.QuestionSet;

@Entity
@DiscriminatorValue("VALUE_SELECTION")
public class ValueSelectionQuestion<T extends Code> extends Question {

	private static final long serialVersionUID = 1L;

	protected ValueSelectionQuestion() {
		super();
	}

	public ValueSelectionQuestion(QuestionSet questionSet, int orderIndex, QuestionCode code) {
		super(questionSet, orderIndex, code);
	}

}
