package eu.factorx.awac.models.knowledge;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import play.db.ebean.Model;

@Entity
@Table(name = "period")
public class Period extends Model {

	private static final long serialVersionUID = 1L;

	public Period() {
	}

	@Id
	private Long id;
	private String label;

	public Period(String label) {
		super();
		this.label = label;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String param) {
		this.label = param;
	}

}