package eu.factorx.awac.models.forms;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import play.db.ebean.Model;
import eu.factorx.awac.models.data.Question;

@Entity
@Table(name = "form_question")
public class FormQuestion extends Model {

	private static final long serialVersionUID = 1L;

	public FormQuestion() {
	}

	@Id
	private long id;
	@ManyToOne(optional = false)
	private Form form;
	@ManyToOne(optional = false)
	private Question question;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Form getForm() {
		return form;
	}

	public void setForm(Form param) {
		this.form = param;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question param) {
		this.question = param;
	}

}