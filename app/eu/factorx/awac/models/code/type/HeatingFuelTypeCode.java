package eu.factorx.awac.models.code.type;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;

public class HeatingFuelTypeCode extends Code {

	private static final long serialVersionUID = 1L;

	public static final CodeList CODE_TYPE = CodeList.HEATING_FUEL_TYPE;

	public static final HeatingFuelTypeCode OIL = new HeatingFuelTypeCode(1);
	public static final HeatingFuelTypeCode GAS = new HeatingFuelTypeCode(2);
	public static final HeatingFuelTypeCode COAL = new HeatingFuelTypeCode(3);

	protected HeatingFuelTypeCode() {
		super();
	}

	public HeatingFuelTypeCode(Integer key) {
		super(CODE_TYPE, Integer.toString(key));
	}

}
