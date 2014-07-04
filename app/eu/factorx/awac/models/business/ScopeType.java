package eu.factorx.awac.models.business;

import javax.persistence.Embeddable;

@Embeddable
public class ScopeType {

	public static final ScopeType ORG = new ScopeType(1);
	public static final ScopeType SITE = new ScopeType(2);
	public static final ScopeType PRODUCT = new ScopeType(3);

	private Integer value;

	protected ScopeType() {
		super();
	}

	public ScopeType(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

}
