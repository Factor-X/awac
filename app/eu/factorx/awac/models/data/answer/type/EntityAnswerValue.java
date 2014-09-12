package eu.factorx.awac.models.data.answer.type;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;

import eu.factorx.awac.models.data.answer.AnswerRawData;
import eu.factorx.awac.models.data.answer.AnswerValue;
import eu.factorx.awac.models.data.answer.QuestionAnswer;

@Entity
@DiscriminatorValue("ENTITY_SELECTION")
public class EntityAnswerValue extends AnswerValue {

	private static final long serialVersionUID = 1L;

	@Transient
	private String entityName;

	@Transient
	private Long entityId;

	protected EntityAnswerValue() {
		super();
	}

	public EntityAnswerValue(QuestionAnswer questionAnswer, String entityName, Long entityId) {
		super();
		this.questionAnswer = questionAnswer;
		this.entityName = entityName;
		this.entityId = entityId;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public Long getEntityId() {
		return entityId;
	}

	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	@Override
	protected AnswerRawData getRawData() {
		AnswerRawData rawData = new AnswerRawData();
		rawData.setStringData1(entityName);
		rawData.setLongData(entityId);
		return rawData;
	}

	@Override
	protected void setRawData(AnswerRawData rawData) {
		this.entityName = StringUtils.trim(rawData.getStringData1());
		this.entityId = rawData.getLongData();
	}

	@Override
	public String toString() {
		return "EntityAnswerValue [entityName=" + entityName + ", entityId=" + entityId + "]";
	}

}
