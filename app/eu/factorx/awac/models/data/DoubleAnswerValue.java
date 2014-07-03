package eu.factorx.awac.models.data;

import eu.factorx.awac.models.knowledge.Unit;

public class DoubleAnswerValue extends NumericAnswerValue {

	private static final long serialVersionUID = 1L;

	private Double value;

	protected DoubleAnswerValue() {
		super();
	}

	public DoubleAnswerValue(Double value, Unit unit) {
		super();
		this.value = value;
		this.unit = unit;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double param) {
		this.value = param;
	}

}