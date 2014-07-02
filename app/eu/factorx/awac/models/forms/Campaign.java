package eu.factorx.awac.models.forms;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import eu.factorx.awac.models.knowledge.Period;

@Entity
@Table(name = "campaign")
public class Campaign implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	@ManyToOne(optional = false)
	private Period period;
	@OneToMany(mappedBy = "campaign")
	private List<Form> forms;

	public Campaign() {
	}

	public Campaign(String name, Period period) {
		super();
		this.name = name;
		this.period = period;
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