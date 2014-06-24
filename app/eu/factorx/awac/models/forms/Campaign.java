package eu.factorx.awac.models.forms;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import play.db.ebean.Model;
import eu.factorx.awac.models.knowledge.Period;

@Entity
@Table(name = "campaign")
public class Campaign extends Model {

	private static final long serialVersionUID = 1L;

	public Campaign() {
	}

	@Id
	private Long id;
	@OneToMany(mappedBy = "campaign")
	private List<Form> forms;
	@ManyToOne(optional = false)
	private Period period;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Form> getForms() {
		return forms;
	}

	public void setForms(List<Form> param) {
		this.forms = param;
	}

	public Period getPeriod() {
		return period;
	}

	public void setPeriod(Period param) {
		this.period = param;
	}

}