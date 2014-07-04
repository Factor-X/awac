package eu.factorx.awac.models.data;


//@Entity
public class StringAnswerValue extends AnswerValue {

	private static final long serialVersionUID = 1L;

	private String value;

	protected StringAnswerValue() {
		super();
	}

	public StringAnswerValue(String value) {
		super();
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String param) {
		this.value = param;
	}

}