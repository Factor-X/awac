package eu.factorx.awac.models.code.type;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;

public class TRAITEUREAUCode extends Code {

	public static final TRAITEUREAUCode PAR_L_ENTREPRISE = new TRAITEUREAUCode("1");
	private static final long serialVersionUID = 1L;

	protected TRAITEUREAUCode() {
		super(CodeList.TRAITEUREAU);
	}

	public TRAITEUREAUCode(String key) {
		this();
		this.key = key;
	}
}