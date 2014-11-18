package eu.factorx.awac.service;

import java.io.Serializable;

import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.data.question.QuestionSet;
import eu.factorx.awac.models.forms.Form;
import eu.factorx.awac.models.knowledge.Period;

public class QuestionSetAnswerSearchParameter implements Serializable {

	private static final long serialVersionUID = 1L;

	private Form form;

	private Scope scope;

	private Period period;

	private Boolean withChildren;

    private QuestionSet questionSet;

    public QuestionSetAnswerSearchParameter(Boolean withChildren) {
		super();
		this.withChildren = withChildren;
	}

	public Form getForm() {
		return form;
	}

	public Scope getScope() {
		return scope;
	}

	public Period getPeriod() {
		return period;
	}

    public QuestionSet getQuestionSet() {
        return questionSet;
    }

    public Boolean getWithChildren() {
		return withChildren;
	}

	public QuestionSetAnswerSearchParameter appendForm(Form form) {
		this.form = form;
		return this;
	}

	public QuestionSetAnswerSearchParameter appendScope(Scope scope) {
		this.scope = scope;
		return this;
	}

	public QuestionSetAnswerSearchParameter appendPeriod(Period period) {
		this.period = period;
		return this;
	}

    public QuestionSetAnswerSearchParameter appendQuestionSet(QuestionSet questionSet) {
        this.questionSet = questionSet;
        return this;
    }


}
