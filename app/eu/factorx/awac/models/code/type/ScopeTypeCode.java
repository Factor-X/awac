package eu.factorx.awac.models.code.type;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@AttributeOverrides({@AttributeOverride(name = "key", column = @Column(name = "scopetype"))})
public class ScopeTypeCode extends Code {

	private static final long serialVersionUID = 1L;

	public static final String ORG_KEY = "1";
	public static final String SITE_KEY = "2";
	public static final String PRODUCT_KEY = "3";

	public static final ScopeTypeCode ORG = new ScopeTypeCode(ORG_KEY);
	public static final ScopeTypeCode SITE = new ScopeTypeCode(SITE_KEY);
	public static final ScopeTypeCode PRODUCT = new ScopeTypeCode(PRODUCT_KEY);

	protected ScopeTypeCode() {
		super(CodeList.SCOPE_TYPE);
	}

	public ScopeTypeCode(String key) {
		this();
		this.key = key;
	}

}
