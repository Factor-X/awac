package eu.factorx.awac.service;

import java.io.Serializable;

import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.forms.Form;
import eu.factorx.awac.models.knowledge.Period;

public class QuestionSetAnswerSearchParameter implements Serializable {

	private static final long serialVersionUID = 1L;

	private Form form;

	private Scope scope;

	private Period period;

	private Boolean withChildren;

	public QuestionSetAnswerSearchParameter(Boolean withChildren) {
		super();
		this.withChildren = withChildren;
	}

	public Boolean getWithChildren() {
		return withChildren;
	}

	public void setWithChildren(Boolean withSubItems) {
		this.withChildren = withSubItems;
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

	public QuestionSetAnswerSearchParameter appendForm(Form form) {
		setForm(form);
		return this;
	}

	public QuestionSetAnswerSearchParameter appendScope(Scope scope) {
		setScope(scope);
		return this;
	}

	public QuestionSetAnswerSearchParameter appendPeriod(Period period) {
		setPeriod(period);
		return this;
	}

}
