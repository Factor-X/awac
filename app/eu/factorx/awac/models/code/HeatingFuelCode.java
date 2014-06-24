package eu.factorx.awac.models.code;

import java.util.Arrays;
import java.util.List;

//@Entity
//@DiscriminatorValue("HEATING_FUEL")
public class HeatingFuelCode extends Code {

	private static final long serialVersionUID = 1L;

	public static final HeatingFuelCode OIL = new HeatingFuelCode(1, "OIL");
	public static final HeatingFuelCode GAS = new HeatingFuelCode(2, "GAS");

	private static final HeatingFuelCode[] ALL = new HeatingFuelCode[] { OIL,
			GAS };

	public HeatingFuelCode(Integer value, String code) {
		this.value = value;
		this.code = code;
	}

	public static List<HeatingFuelCode> getAll() {
		return Arrays.asList(ALL);
	}
}
