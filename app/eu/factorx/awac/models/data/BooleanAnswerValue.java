package eu.factorx.awac.models.data;


public class BooleanAnswerValue extends AnswerValue {

	private static final long serialVersionUID = 1L;
	private Boolean value;

	protected BooleanAnswerValue() {
		super();
	}

	protected BooleanAnswerValue(Boolean value) {
		super();
		this.value = value;
	}

	public Boolean getValue() {
		return value;
	}

	public void setValue(Boolean param) {
		this.value = param;
	}

}