package eu.factorx.awac.models.data.answer.type;

import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;

import play.db.jpa.JPA;
import eu.factorx.awac.models.AbstractEntity;
import eu.factorx.awac.models.data.answer.AnswerRawData;
import eu.factorx.awac.models.data.answer.AnswerValue;
import eu.factorx.awac.models.data.answer.QuestionAnswer;

public class EntityAnswerValue<T extends AbstractEntity> extends AnswerValue {

	private static final long serialVersionUID = 1L;

	@Transient
	private T value;

	protected EntityAnswerValue() {
		super();
	}

	public EntityAnswerValue(QuestionAnswer<EntityAnswerValue<T>> questionAnswer, T value) {
		super();
		this.questionAnswer = questionAnswer;
		this.value = value;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	@Override
	protected AnswerRawData getRawData() {
		AnswerRawData rawData = new AnswerRawData();
		rawData.setStringData1(value.getClass().getName());
		rawData.setLongData(value.getId());
		return rawData;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void setRawData(AnswerRawData rawData) {
		String entityName = StringUtils.trim(rawData.getStringData1());
		Long entityId = rawData.getLongData();
		this.value = (T) JPA.em().unwrap(Session.class).get(entityName, entityId);
	}

}
