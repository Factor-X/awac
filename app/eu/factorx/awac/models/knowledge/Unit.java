package eu.factorx.awac.models.knowledge;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import play.db.ebean.Model;

@Entity
@Table(name = "unit")
public class Unit extends Model {

	private static final long serialVersionUID = 1L;

	public Unit() {
	}

	@Id
	private long id;
	private String name;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String param) {
		this.name = param;
	}

}