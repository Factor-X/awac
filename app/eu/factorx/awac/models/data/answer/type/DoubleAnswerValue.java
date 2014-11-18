package eu.factorx.awac.models.data.answer.type;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

import play.db.jpa.JPA;
import eu.factorx.awac.models.data.answer.AnswerRawData;
import eu.factorx.awac.models.data.answer.QuestionAnswer;
import eu.factorx.awac.models.knowledge.Unit;

@Entity
@DiscriminatorValue("DOUBLE")
public class DoubleAnswerValue extends NumericAnswerValue {

	private static final long serialVersionUID = 1L;

	@Transient
	private Double value;

	protected DoubleAnswerValue() {
		super();
	}

	public DoubleAnswerValue(QuestionAnswer questionAnswer, Double value, Unit unit) {
		super();
		this.questionAnswer = questionAnswer;
		this.value = value;
		this.unit = unit;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	@Override
	protected AnswerRawData getRawData() {
		AnswerRawData rawData = new AnswerRawData();
		if (unit != null) {
			rawData.setLongData(unit.getId());
		}
		rawData.setDoubleData(value);
		return rawData;
	}

	@Override
	protected void setRawData(AnswerRawData rawData) {
		Long unitId = rawData.getLongData();
		if (unitId != null) {
			this.unit = JPA.em().find(Unit.class, unitId);
		}
		this.value = rawData.getDoubleData();
	}

	@Override
	public Double doubleValue() {
		return value;
	}

	@Override
	public String toString() {
		return "DoubleAnswerValue [value=" + value + ", unit=" + unit + "]";
	}

}