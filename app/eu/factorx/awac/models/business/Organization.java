package eu.factorx.awac.models.business;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import eu.factorx.awac.models.AbstractEntity;

@Entity
@Table(name = "organization")
public class Organization extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	private String name;

	@OneToMany(mappedBy = "organization")
	private List<Site> sites;

	public Organization() {
		super();
	}

	public Organization(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Site> getSites() {
		return sites;
	}

	public void setSites(List<Site> param) {
		this.sites = param;
	}

}