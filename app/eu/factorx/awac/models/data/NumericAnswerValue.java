package eu.factorx.awac.models.data;

import javax.persistence.ManyToOne;

import eu.factorx.awac.models.knowledge.Unit;

public abstract class NumericAnswerValue extends AnswerValue {

	private static final long serialVersionUID = 1L;
	@ManyToOne(optional = false)
	protected Unit unit;

	protected NumericAnswerValue() {
		super();
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit param) {
		this.unit = param;
	}

}