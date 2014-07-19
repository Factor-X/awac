
package eu.factorx.awac.models.code.type;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;

public class TRAITEMENTDECHETCode extends Code {

	private static final long serialVersionUID = 1L;

	protected TRAITEMENTDECHETCode() {
		super(CodeList.TRAITEMENTDECHET);
	}

	public TRAITEMENTDECHETCode(String key) {
		this();
		this.key = key;
	}

	public static final TRAITEMENTDECHETCode INCINERATION = new TRAITEMENTDECHETCode("1");
	public static final TRAITEMENTDECHETCode CET_AVEC_VALORISATION = new TRAITEMENTDECHETCode("2");
	public static final TRAITEMENTDECHETCode RECYCLAGE = new TRAITEMENTDECHETCode("3");
	public static final TRAITEMENTDECHETCode INCONNU = new TRAITEMENTDECHETCode("4");
}