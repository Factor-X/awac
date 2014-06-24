package eu.factorx.awac.models.data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import play.db.ebean.Model;

@Entity
@Table(name = "question")
public class Question extends Model {

	private static final long serialVersionUID = 1L;

	public Question() {
	}

	@Id
	private Long id;
	private String code;
	private String unitCategory;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUnitCategory() {
		return unitCategory;
	}

	public void setUnitCategory(String param) {
		this.unitCategory = param;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String param) {
		this.code = param;
	}

}