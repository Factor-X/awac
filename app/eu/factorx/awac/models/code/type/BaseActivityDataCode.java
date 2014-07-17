package eu.factorx.awac.models.code.type;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;

public class BaseActivityDataCode extends Code {

	private static final long serialVersionUID = 1L;

	protected BaseActivityDataCode() {
		super(CodeList.BASE_ACTIVITY_DATA);
	}

	public BaseActivityDataCode(String key) {
		this();
		this.key = key;
	}

}
