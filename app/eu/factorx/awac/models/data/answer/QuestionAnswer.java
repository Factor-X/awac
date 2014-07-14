package eu.factorx.awac.models.data.answer;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import eu.factorx.awac.models.AbstractEntity;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.data.question.Question;
import eu.factorx.awac.models.knowledge.Period;

@Entity
@Table(name = "question_answer")
@NamedQueries({
		@NamedQuery(name = QuestionAnswer.FIND_BY_SCOPE_AND_PERIOD, query = "select qa from QuestionAnswer qa where qa.scope = :scope and qa.period = :period"),
		@NamedQuery(name = QuestionAnswer.FIND_BY_CODES, query = "select qa from QuestionAnswer qa where qa.question.code in :codes"),
		@NamedQuery(name = QuestionAnswer.FIND_BY_SCOPE_AND_PERIOD_AND_QUESTION_SETS, query = "select qa from QuestionAnswer qa where qa.scope = :scope and qa.period = :period and qa.question.questionSet in :questionSets"), })
public class QuestionAnswer extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	public static final String FIND_BY_SCOPE_AND_PERIOD = "QuestionAnswer.findByScopeAndPeriod";

	public static final String FIND_BY_CODES = "QuestionAnswer.findByCodes";

	/**
	 * :scope
	 * :period
	 * :questionSets
	 */
	public static final String FIND_BY_SCOPE_AND_PERIOD_AND_QUESTION_SETS = "QuestionAnswer.findByScopeAndPeriodAndQuestionSets";

	// ATTRIBUTES

	@ManyToOne(optional = false)
	private Period period;

	@ManyToOne(optional = false)
	private Scope scope;

	@ManyToOne(optional = false)
	private Account dataOwner;

	private int repetitionIndex = 0;

	@Embedded
	private AuditInfo auditInfo;

	@OneToMany(mappedBy = "questionAnswer", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<AnswerValue> answerValues = new ArrayList<>();

	@ManyToOne(targetEntity = Question.class)
	private Question question;

	// CONSTRUCTORS

	protected QuestionAnswer() {
		super();
	}

	public QuestionAnswer(Period period, Scope scope, Account dataOwner, Question question, int repetitionIndex) {
		super();
		this.period = period;
		this.scope = scope;
		this.dataOwner = dataOwner;
		this.question = question;
		this.repetitionIndex = repetitionIndex;
		this.answerValues = new ArrayList<>();
	}

	// ACCESSORS

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

	public Account getDataOwner() {
		return dataOwner;
	}

	public void setDataOwner(Account dataOwner) {
		this.dataOwner = dataOwner;
	}

	public int getRepetitionIndex() {
		return repetitionIndex;
	}

	public void setRepetitionIndex(int repetitionIndex) {
		this.repetitionIndex = repetitionIndex;
	}

	public AuditInfo getAuditInfo() {
		return auditInfo;
	}

	public void setAuditInfo(AuditInfo auditInfo) {
		this.auditInfo = auditInfo;
	}

	public List<AnswerValue> getAnswerValues() {
		return answerValues;
	}

	public void setAnswerValues(List<AnswerValue> answerValues) {
		this.answerValues = answerValues;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

}