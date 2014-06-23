package eu.factorx.awac.models.forms;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import eu.factorx.awac.models.knowledge.Period;

@Entity
public class Campaign implements Serializable {

	private static final long serialVersionUID = 1L;

	public Campaign() {
	}

	@Id
	private long id;
	@OneToMany(mappedBy = "campaign")
	private List<Form> forms;
	@ManyToOne(optional = false)
	private Period period;

	public long getId() {
		return id;
	}

	public void setId(long id) {
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