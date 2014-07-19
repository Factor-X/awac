package eu.factorx.awac.models.code.type;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;

public class ACHATPAPIERCode extends Code {

	public static final ACHATPAPIERCode PAPIER = new ACHATPAPIERCode("1");
	public static final ACHATPAPIERCode CARTON = new ACHATPAPIERCode("2");
	private static final long serialVersionUID = 1L;

	protected ACHATPAPIERCode() {
		super(CodeList.ACHATPAPIER);
	}
	public ACHATPAPIERCode(String key) {
		this();
		this.key = key;
	}
}