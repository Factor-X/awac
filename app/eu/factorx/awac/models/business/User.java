package eu.factorx.awac.models.business;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import eu.factorx.awac.models.business.Organization;

import javax.persistence.ManyToOne;

@Entity
@Table(name = "User")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	public User() {
	}

	@Id
	private long id;
	@ManyToOne(optional = false)
	private Organization organization;
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Organization getOrganization() {
	    return organization;
	}

	public void setOrganization(Organization param) {
	    this.organization = param;
	}

}