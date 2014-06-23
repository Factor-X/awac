package eu.factorx.awac.models.business;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Product extends Scope implements Serializable {

	private static final long serialVersionUID = 1L;

	public Product() {
	}

	@ManyToOne(optional = false)
	private Organization organization;

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization param) {
		this.organization = param;
	}

}