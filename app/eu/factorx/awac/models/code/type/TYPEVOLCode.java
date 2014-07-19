
package eu.factorx.awac.models.code.type;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;

public class TYPEVOLCode extends Code {

	private static final long serialVersionUID = 1L;

	protected TYPEVOLCode() {
		super(CodeList.TYPEVOL);
	}

	public TYPEVOLCode(String key) {
		this();
		this.key = key;
	}

	public static final TYPEVOLCode VOLS_EN_EUROPE_4_000KM_A_R = new TYPEVOLCode("1");
	public static final TYPEVOLCode VOLS_INTERCONTINENTAUX_4_000KM_A_R = new TYPEVOLCode("2");
}