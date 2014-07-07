package eu.factorx.awac.models.code.type;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;

public class GenderCode extends Code {

	private static final long serialVersionUID = 1L;

	public static final CodeList CODE_TYPE = CodeList.GENDER;

	public static final GenderCode MAN = new GenderCode(1);
	public static final GenderCode WOMAN = new GenderCode(2);

	protected GenderCode() {
		super();
	}

	public GenderCode(Integer i) {
		super(CODE_TYPE, Integer.toString(i));
	}

}
