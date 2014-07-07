package eu.factorx.awac.models.code.type;

import javax.persistence.Embeddable;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;

@Embeddable
public class HeatingFuelTypeCode implements Code {

	private static final long serialVersionUID = 1L;

	public static final CodeList CODE_TYPE = CodeList.HEATING_FUEL_TYPE;

	public static final HeatingFuelTypeCode OIL = new HeatingFuelTypeCode("1");
	public static final HeatingFuelTypeCode GAS = new HeatingFuelTypeCode("2");
	public static final HeatingFuelTypeCode COAL = new HeatingFuelTypeCode("3");

	protected CodeList codeList;

	protected String key;

	protected HeatingFuelTypeCode() {
		super();
	}

	public HeatingFuelTypeCode(String key) {
		super();
		this.key = key;
		this.codeList= CODE_TYPE;
	}

	public CodeList getCodeList() {
		return codeList;
	}

	public void setCodeList(CodeList codeList) {
		this.codeList = codeList;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
