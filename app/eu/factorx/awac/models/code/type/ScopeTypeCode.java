package eu.factorx.awac.models.code.type;

import javax.persistence.Embeddable;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;

@Embeddable
public class ScopeTypeCode implements Code {

	private static final long serialVersionUID = 1L;

	public static final CodeList CODE_TYPE = CodeList.SCOPE_TYPE;

	public static final ScopeTypeCode ORG = new ScopeTypeCode("1");
	public static final ScopeTypeCode SITE = new ScopeTypeCode("2");
	public static final ScopeTypeCode PRODUCT = new ScopeTypeCode("3");

	protected CodeList codeList;

	protected String key;

	protected ScopeTypeCode() {
		super();
	}

	public ScopeTypeCode(String key) {
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
