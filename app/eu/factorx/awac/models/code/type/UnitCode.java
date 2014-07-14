package eu.factorx.awac.models.code.type;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;

public class UnitCode extends Code {

	private static final long serialVersionUID = 1L;

	public static final UnitCode GJ = new UnitCode("GJ");

	protected UnitCode() {
		super(CodeList.UNIT);
	}

	public UnitCode(String key) {
		this();
		this.key = key;
	}

}
