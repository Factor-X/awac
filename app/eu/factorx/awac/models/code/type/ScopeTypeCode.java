package eu.factorx.awac.models.code.type;

import javax.persistence.Embeddable;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;

@Embeddable
public class ScopeTypeCode extends Code {

	private static final long serialVersionUID = 1L;

	public static final CodeList CODE_TYPE = CodeList.SCOPE_TYPE;

	public static final ScopeTypeCode ORG = new ScopeTypeCode("1");
	public static final ScopeTypeCode SITE = new ScopeTypeCode("2");
	public static final ScopeTypeCode PRODUCT = new ScopeTypeCode("3");

	protected ScopeTypeCode() {
		super();
	}

	public ScopeTypeCode(String key) {
		super(CODE_TYPE, key);
	}

}
