
package eu.factorx.awac.models.code.type;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;

public class FRIGORIGENEBASECode extends Code {

	private static final long serialVersionUID = 1L;

	protected FRIGORIGENEBASECode() {
		super(CodeList.FRIGORIGENEBASE);
	}

	public FRIGORIGENEBASECode(String key) {
		this();
		this.key = key;
	}

	public static final FRIGORIGENEBASECode R404A = new FRIGORIGENEBASECode("1");
	public static final FRIGORIGENEBASECode R134A = new FRIGORIGENEBASECode("2");
	public static final FRIGORIGENEBASECode R410A = new FRIGORIGENEBASECode("3");
}