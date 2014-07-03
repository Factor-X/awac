package eu.factorx.awac.models.forms;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import eu.factorx.awac.models.AbstractEntity;
import eu.factorx.awac.models.data.QuestionSet;

@Entity
@Table(name = "form_question")
public class FormQuestion extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@ManyToOne(optional = false)
	private Form form;

	@ManyToOne(optional = false)
	private QuestionSet question;

	public FormQuestion() {
	}

	public FormQuestion(Form form, QuestionSet question) {
		super();
		this.form = form;
		this.question = question;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Form getForm() {
		return form;
	}

	public void setForm(Form param) {
		this.form = param;
	}

	public QuestionSet getQuestion() {
		return question;
	}

	public void setQuestion(QuestionSet param) {
		this.question = param;
	}

}