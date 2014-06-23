package eu.factorx.awac.models.code;

public class MaritalStatusCode extends Code {

	private static final String MARITAL_STATUS_TYPE = "MARITAL_STATUS";

	public static final MaritalStatusCode SINGLE = new MaritalStatusCode(1);
	public static final MaritalStatusCode MARRIED = new MaritalStatusCode(2);

	// required for hibernate
	@SuppressWarnings("unused")
	private MaritalStatusCode() {
	}

	public MaritalStatusCode(Integer value) {
		super();
		this.value = value;
	}

	@Override
	String getType() {
		return MARITAL_STATUS_TYPE;
	}

}
