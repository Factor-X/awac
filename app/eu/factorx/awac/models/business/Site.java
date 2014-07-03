package eu.factorx.awac.models.business;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import eu.factorx.awac.models.AbstractEntity;

@Entity
@Table(name = "site")
public class Site extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	private String name;

	@ManyToOne(optional = false)
	private Organization organization;

	public Site() {
		super();
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization param) {
		this.organization = param;
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

}