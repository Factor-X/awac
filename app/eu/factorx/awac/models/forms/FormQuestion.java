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
	private QuestionSet questionSet;

	public FormQuestion() {
		super();
	}

	public FormQuestion(Form form, QuestionSet questionSet) {
		super();
		this.form = form;
		this.questionSet = questionSet;
	}

	public Form getForm() {
		return form;
	}

	public void setForm(Form param) {
		this.form = param;
	}

	public QuestionSet getQuestionSet() {
		return questionSet;
	}

	public void setQuestionSet(QuestionSet questionSet) {
		this.questionSet = questionSet;
	}

}