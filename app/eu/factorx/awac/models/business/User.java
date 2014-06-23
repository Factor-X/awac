package eu.factorx.awac.models.business;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import play.db.ebean.Model;

@Entity
@Table(name = "user")
public class User extends Model {

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