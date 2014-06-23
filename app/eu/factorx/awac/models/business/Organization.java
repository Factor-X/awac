package eu.factorx.awac.models.business;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Organization")
public class Organization extends Scope implements Serializable {

	private static final long serialVersionUID = 1L;

	public Organization() {
	}

	@OneToMany(mappedBy = "organization")
	private List<Site> sites;

	public List<Site> getSites() {
	    return sites;
	}

	public void setSites(List<Site> param) {
	    this.sites = param;
	}

}