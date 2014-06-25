package eu.factorx.awac.models.business;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import play.db.ebean.Model;

@Entity
// @DiscriminatorColumn(name = "scope_type", discriminatorType =
// DiscriminatorType.INTEGER)
// @Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "scope")
public class Scope extends Model {

	private static final long serialVersionUID = 1L;

	@Id
	private Long id;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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