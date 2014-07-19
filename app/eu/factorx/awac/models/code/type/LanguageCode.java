package eu.factorx.awac.models.code.type;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@AttributeOverrides({@AttributeOverride(name = "key", column = @Column(name = "lang"))})
public class LanguageCode extends Code {

	public static final LanguageCode ENGLISH = new LanguageCode("1");
	public static final LanguageCode FRENCH = new LanguageCode("2");
	public static final LanguageCode DUTCH = new LanguageCode("3");
	private static final long serialVersionUID = 1L;

	protected LanguageCode() {
		super(CodeList.LANGUAGE);
	}

	public LanguageCode(String key) {
		this();
		this.key = key;
	}

}
