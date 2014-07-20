package eu.factorx.awac.models.code.type;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;

public class ACHATMETALCode extends Code {

	public static final ACHATMETALCode ACIER_OU_FER_BLANC = new ACHATMETALCode("1");
	public static final ACHATMETALCode ALUMINIUM = new ACHATMETALCode("2");
	public static final ACHATMETALCode CUIVRE_MOYENNE = new ACHATMETALCode("3");
	public static final ACHATMETALCode ZINC_MOYENNE = new ACHATMETALCode("4");
	public static final ACHATMETALCode NICKEL_MOYENNE = new ACHATMETALCode("5");
	public static final ACHATMETALCode PLOMB_MOYENNE = new ACHATMETALCode("6");
	public static final ACHATMETALCode AUTRES_METAUX_COURANTS_MOYENNE = new ACHATMETALCode("7");
	private static final long serialVersionUID = 1L;
	protected ACHATMETALCode() {
		super(CodeList.ACHATMETAL);
	}
	public ACHATMETALCode(String key) {
		this();
		this.key = key;
	}
}