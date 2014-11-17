package eu.factorx.awac.models.code.type;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;

@Embeddable
@AttributeOverrides({ @AttributeOverride(name = "key", column = @Column(name = "lang")) })
public class LanguageCode extends Code {

	private static final long serialVersionUID = 1L;

	public static final LanguageCode ENGLISH = new LanguageCode("EN");
	public static final LanguageCode FRENCH = new LanguageCode("FR");
	public static final LanguageCode DUTCH = new LanguageCode("NL");

	public static final LanguageCode[] ALL = { ENGLISH, FRENCH, DUTCH };

	protected LanguageCode() {
		super(CodeList.LANGUAGE);
	}

	public LanguageCode(String key) {
		this();
		this.key = key;
	}

	public static LanguageCode fromString(String language) {
		for (LanguageCode languageCode : ALL) {
			if (languageCode.getKey().equals(language)) {
				return languageCode;
			}
		}
		return null;
	}

}
