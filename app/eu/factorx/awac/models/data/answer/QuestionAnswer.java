package eu.factorx.awac.models.data.answer;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import eu.factorx.awac.models.AbstractEntity;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.data.question.Question;
import eu.factorx.awac.models.knowledge.Period;

@Entity
@Table(name = "question_answer")
public class QuestionAnswer<T extends AnswerValue> extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	public static final String FIND_BY_PARAMETERS = "QuestionAnswer.findByParameters";

	// ATTRIBUTES

	@ManyToOne(optional = false)
	private Period period;

	@ManyToOne(optional = false)
	private Scope scope;

	@ManyToOne(optional = false)
	private Account dataOwner;

	@ManyToOne(optional = false)
	private Question<T> question;

	private int repetitionIndex = 0;

	@Embedded
	private AuditInfo auditInfo;

	@OneToMany(mappedBy = "questionAnswer", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<T> answerValues;

	// CONSTRUCTORS

	protected QuestionAnswer() {
		super();
	}

	public QuestionAnswer(Period period, Scope scope, Account dataOwner, Question<T> question, int repetitionIndex) {
		super();
		this.period = period;
		this.scope = scope;
		this.dataOwner = dataOwner;
		this.question = question;
		this.repetitionIndex = repetitionIndex;
		this.answerValues = new ArrayList<T>();
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

	public Question<T> getQuestion() {
		return question;
	}

	public void setQuestion(Question<T> question) {
		this.question = question;
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

	public List<T> getAnswerValues() {
		return answerValues;
	}

	public void setAnswerValues(List<T> answerValues) {
		this.answerValues = answerValues;
	}

	public boolean addAnswerValue(T answerValue) {
		return this.getAnswerValues().add(answerValue);
	}

}