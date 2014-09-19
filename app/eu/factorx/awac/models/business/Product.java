package eu.factorx.awac.models.business;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;

@Entity
@Table(name = "product")
public class Product extends Scope {

	private static final long serialVersionUID = 1L;

	@ManyToOne(optional = false)
	private Organization organization;

	private String name;

	protected Product() {
		super();
	}

	public Product(Organization organization, String name) {
		super();
		this.organization = organization;
		this.name = name;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof Product)) {
			return false;
		}
		Product rhs = (Product) obj;
		return new EqualsBuilder().append(this.organization, rhs.organization).append(this.name, rhs.name).isEquals();
	}

	@Override
	public String toString() {
		return "Site [id=" + id + ", name='" + name + "', organization=" + organization  + "]";
	}

}