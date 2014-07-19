package eu.factorx.awac.models.code.type;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@AttributeOverrides({@AttributeOverride(name = "key", column = @Column(name = "heatingfueltype"))})
public class HeatingFuelTypeCode extends Code {

	public static final HeatingFuelTypeCode OIL = new HeatingFuelTypeCode("1");
	public static final HeatingFuelTypeCode GAS = new HeatingFuelTypeCode("2");
	public static final HeatingFuelTypeCode COAL = new HeatingFuelTypeCode("3");
	private static final long serialVersionUID = 1L;

	protected HeatingFuelTypeCode() {
		super(CodeList.HEATING_FUEL_TYPE);
	}

	public HeatingFuelTypeCode(String key) {
		this();
		this.key = key;
	}

}
