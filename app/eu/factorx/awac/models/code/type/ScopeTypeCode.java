package eu.factorx.awac.models.code.type;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;

@Embeddable
@AttributeOverrides({@AttributeOverride(name = "key", column = @Column(name = "scopetype"))})
public class ScopeTypeCode extends Code {

	private static final long serialVersionUID = 1L;

	public static final ScopeTypeCode ORG = new ScopeTypeCode("1");
	public static final ScopeTypeCode SITE = new ScopeTypeCode("2");
	public static final ScopeTypeCode PRODUCT = new ScopeTypeCode("3");

	protected ScopeTypeCode() {
		super(CodeList.SCOPE_TYPE);
	}

	public ScopeTypeCode(String key) {
		this();
		this.key = key;
	}

}
