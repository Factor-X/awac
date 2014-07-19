package eu.factorx.awac.models.data.question.type;

import eu.factorx.awac.models.code.type.QuestionCode;
import eu.factorx.awac.models.data.answer.AnswerType;
import eu.factorx.awac.models.data.question.Question;
import eu.factorx.awac.models.data.question.QuestionSet;

import javax.persistence.Entity;

@Entity
public class EntitySelectionQuestion extends Question {

	private static final long serialVersionUID = 1L;

	private String entityName;

	protected EntitySelectionQuestion() {
		super();
	}

	public EntitySelectionQuestion(QuestionSet questionSet, int orderIndex, QuestionCode code, String entityName) {
		super(questionSet, orderIndex, code);
		this.entityName = entityName;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	@Override
	public AnswerType getAnswerType() {
		return AnswerType.ENTITY_SELECTION;
	}

}
