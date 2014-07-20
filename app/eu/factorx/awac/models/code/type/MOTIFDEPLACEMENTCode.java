package eu.factorx.awac.models.code.type;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;

public class MOTIFDEPLACEMENTCode extends Code {

	public static final MOTIFDEPLACEMENTCode CHOICE = new MOTIFDEPLACEMENTCode("1");
	public static final MOTIFDEPLACEMENTCode DEPLACEMENTS_DOMICILE_TRAVAIL = new MOTIFDEPLACEMENTCode("2");
	private static final long serialVersionUID = 1L;

	protected MOTIFDEPLACEMENTCode() {
		super(CodeList.MOTIFDEPLACEMENT);
	}
	public MOTIFDEPLACEMENTCode(String key) {
		this();
		this.key = key;
	}
}