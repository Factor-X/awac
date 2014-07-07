package eu.factorx.awac.models.data.question.type;

import javax.persistence.Entity;

import eu.factorx.awac.models.AbstractEntity;
import eu.factorx.awac.models.code.type.QuestionCode;
import eu.factorx.awac.models.data.answer.type.EntityAnswerValue;
import eu.factorx.awac.models.data.question.Question;
import eu.factorx.awac.models.data.question.QuestionSet;

@Entity
public class EntitySelectionQuestion<T extends AbstractEntity> extends Question<EntityAnswerValue<T>> {

	private static final long serialVersionUID = 1L;

	protected EntitySelectionQuestion() {
		super();
	}

	public EntitySelectionQuestion(QuestionSet questionSet, int orderIndex, QuestionCode code) {
		super(questionSet, orderIndex, code);
	}

}
