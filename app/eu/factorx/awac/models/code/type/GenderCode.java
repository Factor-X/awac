package eu.factorx.awac.models.code.type;

import javax.persistence.Embeddable;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;

@Embeddable
public class GenderCode implements Code {

	private static final long serialVersionUID = 1L;

	public static final CodeList CODE_TYPE = CodeList.GENDER;

	public static final GenderCode MAN = new GenderCode("1");
	public static final GenderCode WOMAN = new GenderCode("2");

	protected CodeList codeList;

	protected String key;

	protected GenderCode() {
		super();
	}

	public GenderCode(String key) {
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
