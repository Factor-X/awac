package eu.factorx.awac.models.code;

public class GenderCode extends Code {

	private static final long serialVersionUID = 1L;

	private static final CodeType CODE_TYPE = CodeType.GENDER;

	public static final GenderCode MAN = new GenderCode(1);
	public static final GenderCode WOMAN = new GenderCode(2);

	private static final GenderCode[] ALL = new GenderCode[] { MAN, WOMAN };

	private GenderCode(Integer value) {
		super(CODE_TYPE, value);
	}

	public static GenderCode getCode(Integer value) {
		for (GenderCode code : ALL) {
			if (value.equals(code.getValue())) {
				return code;
			}
		}
		return null;
	}

}
