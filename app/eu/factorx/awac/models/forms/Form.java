package eu.factorx.awac.models.forms;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import play.db.ebean.Model;

@Entity
@Table(name = "form")
public class Form extends Model {

	private static final long serialVersionUID = 1L;

	public Form() {
	}

	@Id
	private long id;
	@Transient
	private Integer progress;
	@OneToMany(mappedBy = "form")
	private List<FormQuestion> questions = new ArrayList<FormQuestion>();
	@ManyToOne(optional = false)
	private Campaign campaign;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Integer getProgress() {
		return progress;
	}

	public void setProgress(Integer progress) {
		this.progress = progress;
	}

	public List<FormQuestion> getQuestions() {
		return questions;
	}

	public void setQuestions(List<FormQuestion> param) {
		this.questions = param;
	}

	public Campaign getCampaign() {
	    return campaign;
	}

	public void setCampaign(Campaign param) {
	    this.campaign = param;
	}

}