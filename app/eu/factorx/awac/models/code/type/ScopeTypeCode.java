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

	public static final ScopeTypeCode ORG = new ScopeTypeCode("1");
	public static final ScopeTypeCode SITE = new ScopeTypeCode("2");
	public static final ScopeTypeCode PRODUCT = new ScopeTypeCode("3");
	private static final long serialVersionUID = 1L;

	protected ScopeTypeCode() {
		super(CodeList.SCOPE_TYPE);
	}

	public ScopeTypeCode(String key) {
		this();
		this.key = key;
	}

}
