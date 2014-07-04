package eu.factorx.awac.models.code;

import javax.persistence.Embeddable;

@Embeddable
public class ScopeType extends Code {

	private static final long serialVersionUID = 1L;

	private static final CodeType CODE_TYPE = CodeType.SCOPE;

	public static final ScopeType ORG = new ScopeType(1);
	public static final ScopeType SITE = new ScopeType(2);
	public static final ScopeType PRODUCT = new ScopeType(3);

	public ScopeType(Integer value) {
		super(CODE_TYPE, value);
	}

}
