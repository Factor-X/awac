package eu.factorx.awac.models.code;

public class HeatingFuelCode extends Code {

	private static final long serialVersionUID = 1L;

	public static final CodeType CODE_TYPE = CodeType.HEATING_FUEL_TYPE;

	public static final HeatingFuelCode OIL = new HeatingFuelCode(1);
	public static final HeatingFuelCode GAS = new HeatingFuelCode(2);
	public static final HeatingFuelCode COAL = new HeatingFuelCode(3);
	

	public static final HeatingFuelCode[] ALL = new HeatingFuelCode[] { OIL, GAS, COAL };

	private HeatingFuelCode(Integer value) {
		this.type = CODE_TYPE;
		this.value = value;
	}

	public static HeatingFuelCode getCode(Integer value) {
		for (HeatingFuelCode code : ALL) {
			if (value.equals(code.getValue())) {
				return code;
			}
		}
		return null;
	}

}
