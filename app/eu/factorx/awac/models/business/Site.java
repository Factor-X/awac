package eu.factorx.awac.models.business;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "site")
public class Site extends Scope {

	private static final long serialVersionUID = 1L;

	public Site() {
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