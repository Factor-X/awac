package eu.factorx.awac.service;

import java.io.Serializable;

import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.forms.Form;
import eu.factorx.awac.models.knowledge.Period;

public class QuestionAnswerSearchParameter implements Serializable {

	private static final long serialVersionUID = 1L;

	private Form form;

	private Scope scope;

	private Period period;

	public QuestionAnswerSearchParameter() {
		super();
	}

	public Form getForm() {
		return form;
	}

	public void setForm(Form form) {
		this.form = form;
	}

	public Scope getScope() {
		return scope;
	}

	public void setScope(Scope scope) {
		this.scope = scope;
	}

	public Period getPeriod() {
		return period;
	}

	public void setPeriod(Period period) {
		this.period = period;
	}

	public QuestionAnswerSearchParameter appendForm(Form form) {
		setForm(form);
		return this;
	}

	public QuestionAnswerSearchParameter appendScope(Scope scope) {
		setScope(scope);
		return this;
	}

	public QuestionAnswerSearchParameter appendPeriod(Period period) {
		setPeriod(period);
		return this;
	}

}
