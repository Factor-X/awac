package eu.factorx.awac.models.business;

import eu.factorx.awac.models.AbstractEntity;
import eu.factorx.awac.models.code.type.ScopeTypeCode;

import javax.persistence.*;

@Entity
@Table(name = "scope")
@NamedQueries({
		@NamedQuery(name = Scope.FIND_BY_SITE, query = "select s from Scope s where s.site = :site"),
		@NamedQuery(name = Scope.FIND_BY_ORGANIZATION, query = "select s from Scope s where s.organization = :organization"),
		@NamedQuery(name = Scope.FIND_BY_PRODUCT, query = "select s from Scope s where s.product = :product"),})
public class Scope extends AbstractEntity {

	public static final String FIND_BY_SITE = "Scope.findBySite";
	public static final String FIND_BY_ORGANIZATION = "Scope.findByOrganization";
	public static final String FIND_BY_PRODUCT = "Scope.findByProduct";
	private static final long serialVersionUID = 1L;
	@Embedded
	@AttributeOverrides({@AttributeOverride(name = "key", column = @Column(name = "type"))})
	private ScopeTypeCode scopeType;

	@OneToOne
	private Organization organization;

	@OneToOne
	private Site site;

	@OneToOne
	private Product product;

	protected Scope() {
		super();
	}

	public Scope(ScopeTypeCode scopeType, Organization organization, Site site, Product product) {
		super();
		this.scopeType = scopeType;
		this.organization = organization;
		this.site = site;
		this.product = product;
	}

	public Scope(Organization organization) {
		this(ScopeTypeCode.ORG, organization, null, null);
	}

	public Scope(Site site) {
		this(ScopeTypeCode.SITE, site.getOrganization(), site, null);
	}

	public Scope(Product product) {
		this(ScopeTypeCode.PRODUCT, product.getOrganization(), null, product);
	}

	public ScopeTypeCode getScopeType() {
		return scopeType;
	}

	public void setScopeType(ScopeTypeCode scopeType) {
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