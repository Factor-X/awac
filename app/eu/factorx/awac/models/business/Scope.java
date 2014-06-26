package eu.factorx.awac.models.business;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.OneToOne;

@Embeddable
public class Scope implements Serializable {

	private static final long serialVersionUID = 1L;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "scope_type")), })
	private ScopeType scopeType;
	@OneToOne
	private Organization organization;
	@OneToOne
	private Site site;
	@OneToOne
	private Product product;

	public Scope() {

	}

	public Scope(Organization organization) {
		this.scopeType = ScopeType.ORG;
		this.organization = organization;
	}

	public ScopeType getScopeType() {
		return scopeType;
	}

	public void setScopeType(ScopeType scopeType) {
		this.scopeType = scopeType;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}