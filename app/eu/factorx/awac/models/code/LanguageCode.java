package eu.factorx.awac.models.code;

public class LanguageCode extends Code {

	private static final long serialVersionUID = 1L;

	public static final CodeType CODE_TYPE = CodeType.LANGUAGE;

	public static final LanguageCode ENGLISH = new LanguageCode(1);
	public static final LanguageCode FRENCH = new LanguageCode(2);
	public static final LanguageCode DUTCH = new LanguageCode(3);

	private static final LanguageCode[] ALL = new LanguageCode[] { ENGLISH, FRENCH, DUTCH };

	private LanguageCode(Integer value) {
		this.type = CODE_TYPE;
		this.value = value;
	}

	public static LanguageCode getCode(Integer value) {
		for (LanguageCode code : ALL) {
			if (value.equals(code.getValue())) {
				return code;
			}
		}
		return null;
	}

}
