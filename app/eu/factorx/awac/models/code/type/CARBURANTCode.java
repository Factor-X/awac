
package eu.factorx.awac.models.code.type;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;

public class CARBURANTCode extends Code {

	private static final long serialVersionUID = 1L;

	protected CARBURANTCode() {
		super(CodeList.CARBURANT);
	}

	public CARBURANTCode(String key) {
		this();
		this.key = key;
	}

	public static final CARBURANTCode ESSENCE = new CARBURANTCode("1");
	public static final CARBURANTCode DIESEL = new CARBURANTCode("2");
	public static final CARBURANTCode BIODIESEL = new CARBURANTCode("3");
	public static final CARBURANTCode GAZ_DE_PRETROLE_LIQUEFIE_GPL = new CARBURANTCode("4");
}