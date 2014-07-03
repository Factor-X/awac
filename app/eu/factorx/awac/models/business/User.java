package eu.factorx.awac.models.business;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import eu.factorx.awac.models.AbstractEntity;

@Entity
@Table(name = "user")
public class User extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	private String name;

	@ManyToOne(optional = false)
	private Organization organization;

	protected User() {
		super();
	}

	public User(String name, Organization organization) {
		super();
		this.name = name;
		this.organization = organization;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization param) {
		this.organization = param;
	}

}