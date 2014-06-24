package eu.factorx.awac.models.business;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import play.db.ebean.Model;

@Entity
@Table(name = "organization")
public class Organization extends Model {

	private static final long serialVersionUID = 1L;

	public Organization() {
	}

	@Id
	private Long id;
	private String name;
	@OneToMany(mappedBy = "organization")
	private List<Site> sites;

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

	public List<Site> getSites() {
		return sites;
	}

	public void setSites(List<Site> param) {
		this.sites = param;
	}

}