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
public class IntegerAnswerValue extends AnswerValue {

	private static final long serialVersionUID = 1L;

	@Transient
	private Integer value;

	@Transient
	private Unit unit;

	protected IntegerAnswerValue() {
		super();
	}

	public IntegerAnswerValue(QuestionAnswer questionAnswer, Integer value, Unit unit) {
		super();
		this.questionAnswer = questionAnswer;
		this.value = value;
		this.unit = unit;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
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
		if (unit != null) {
			rawData.setLongData(unit.getId());
		}
		rawData.setDoubleData(value.doubleValue());
		return rawData;
	}

	@Override
	protected void setRawData(AnswerRawData rawData) {
		Long unitId = rawData.getLongData();
		if (unitId != null) {
			this.unit = JPA.em().find(Unit.class, unitId);
		}
		this.value = rawData.getDoubleData().intValue();
	}

}