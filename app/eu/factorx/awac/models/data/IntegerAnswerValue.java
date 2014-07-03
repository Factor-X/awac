package eu.factorx.awac.models.data;

import eu.factorx.awac.models.knowledge.Unit;

public class IntegerAnswerValue extends NumericAnswerValue {

	private static final long serialVersionUID = 1L;

	private Integer value;

	protected IntegerAnswerValue() {
		super();
	}

	protected IntegerAnswerValue(Integer value, Unit unit) {
		super();
		this.value = value;
		this.unit = unit;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

}
