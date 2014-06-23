package eu.factorx.awac.models.code;

public abstract class Code {

	protected Integer value;

	abstract String getType();
	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}


}
