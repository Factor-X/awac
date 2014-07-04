package eu.factorx.awac.models.business;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import eu.factorx.awac.models.AbstractEntity;
import eu.factorx.awac.models.code.ScopeType;

@Entity
@Table(name = "scope")
public class Scope extends AbstractEntity {

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

	protected Scope() {
		super();
	}

	public Scope(ScopeType scopeType, Organization organization, Site site, Product product) {
		super();
		this.scopeType = scopeType;
		this.organization = organization;
		this.site = site;
		this.product = product;
	}

	public Scope(Organization organization) {
		this(ScopeType.ORG, organization, null, null);
	}

	public Scope(Site site) {
		this(ScopeType.SITE, site.getOrganization(), site, null);
	}

	public Scope(Product product) {
		this(ScopeType.PRODUCT, product.getOrganization(), null, product);
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

	@Override
	public int compareTo(AbstractEntity o) {
		// TODO Auto-generated method stub
		return 0;
	}

}