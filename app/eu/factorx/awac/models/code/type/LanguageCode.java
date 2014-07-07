package eu.factorx.awac.models.code.type;

import javax.persistence.Embeddable;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;

@Embeddable
public class LanguageCode implements Code {

	private static final long serialVersionUID = 1L;

	public static final CodeList CODE_TYPE = CodeList.LANGUAGE;

	public static final LanguageCode ENGLISH = new LanguageCode("1");
	public static final LanguageCode FRENCH = new LanguageCode("2");
	public static final LanguageCode DUTCH = new LanguageCode("3");

	protected CodeList codeList;

	protected String key;

	protected LanguageCode() {
		super();
	}

	public LanguageCode(String key) {
		super();
		this.key = key;
		this.codeList= CODE_TYPE;
	}

	public CodeList getCodeList() {
		return codeList;
	}

	public void setCodeList(CodeList codeList) {
		this.codeList = codeList;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
