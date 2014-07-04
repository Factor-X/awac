package eu.factorx.awac.models.data;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import eu.factorx.awac.models.AbstractEntity;
import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.knowledge.Period;

@Entity
@Table(name = "question_set_answer")
public class QuestionSetAnswer extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@ManyToOne(optional = false)
	private QuestionSet questionSet;

	@ManyToOne(optional = false)
	private Period period;

	@ManyToOne(optional = false)
	private Scope scope;

	@OneToMany(mappedBy = "questionSetAnswer")
	private List<QuestionAnswer> questionAnswers;

	protected QuestionSetAnswer() {
		super();
	}

	public QuestionSetAnswer(QuestionSet questionSet, Period period, Scope scope) {
		super();
		this.questionSet = questionSet;
		this.period = period;
		this.scope = scope;
	}

	public QuestionSet getQuestionSet() {
		return questionSet;
	}

	public void setQuestionSet(QuestionSet questionSet) {
		this.questionSet = questionSet;
	}

	public Period getPeriod() {
		return period;
	}

	public void setPeriod(Period period) {
		this.period = period;
	}

	public Scope getScope() {
		return scope;
	}

	public void setScope(Scope scope) {
		this.scope = scope;
	}

	public List<QuestionAnswer> getQuestionAnswers() {
		return questionAnswers;
	}

	public void setQuestionAnswers(List<QuestionAnswer> questionAnswers) {
		this.questionAnswers = questionAnswers;
	}

}