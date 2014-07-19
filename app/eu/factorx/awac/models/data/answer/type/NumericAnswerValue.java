package eu.factorx.awac.models.data.answer.type;

import eu.factorx.awac.models.data.answer.AnswerValue;
import eu.factorx.awac.models.knowledge.Unit;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@MappedSuperclass
public abstract class NumericAnswerValue extends AnswerValue {

	private static final long serialVersionUID = 1L;

	@Transient
	protected Unit unit;

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public abstract Double doubleValue();

}
