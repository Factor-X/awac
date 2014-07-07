package eu.factorx.awac.models.data.answer.type;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Transient;

import play.db.jpa.JPA;
import eu.factorx.awac.models.data.answer.AnswerRawData;
import eu.factorx.awac.models.data.answer.AnswerValue;
import eu.factorx.awac.models.data.answer.QuestionAnswer;
import eu.factorx.awac.models.knowledge.Unit;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class NumericAnswerValue<T extends Number> extends AnswerValue {

	private static final long serialVersionUID = 1L;

	@Transient
	private T value;

	@Transient
	private Unit unit;

	protected NumericAnswerValue() {
		super();
	}

	public NumericAnswerValue(QuestionAnswer questionAnswer, T value, Unit unit) {
		super();
		this.questionAnswer = questionAnswer;
		this.value = value;
		this.unit = unit;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	@Override
	protected AnswerRawData getRawData() {
		AnswerRawData rawData = new AnswerRawData();
		rawData.setLongData(unit.getId());
		rawData.setDoubleData(value.doubleValue());
		return rawData;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void setRawData(AnswerRawData rawData) {
		this.unit = JPA.em().find(Unit.class, rawData.getLongData());
		this.value = (T) rawData.getDoubleData();
	}

	
}