package eu.factorx.awac.models.data;

import java.io.Serializable;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.business.User;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.models.knowledge.Unit;

@Entity
@Table(name = "question_answer")
public class QuestionAnswer implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	protected Long id;
	@ManyToOne(optional = false)
	protected User dataOwner;
	@ManyToOne(optional = false)
	protected Period period;
	@ManyToOne(optional = false)
	protected Question question;
	@Embedded
	protected AuditInfo auditInfo;
	@Embedded
	protected Scope scope;
	@ManyToOne(optional = false)
	protected Unit unit;

	// for test purpose, answerValue should be an abstract type, with concrete
	// classes for each type of answer
	protected String answerValue;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getDataOwner() {
		return dataOwner;
	}

	public void setDataOwner(User param) {
		this.dataOwner = param;
	}

	public Period getPeriod() {
		return period;
	}

	public void setPeriod(Period param) {
		this.period = param;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question param) {
		this.question = param;
	}

	public AuditInfo getAuditInfo() {
		return auditInfo;
	}

	public void setAuditInfo(AuditInfo auditInfo) {
		this.auditInfo = auditInfo;
	}

	public Scope getScope() {
		return scope;
	}

	public void setScope(Scope param) {
		this.scope = param;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit param) {
		this.unit = param;
	}

	public String getAnswerValue() {
		return answerValue;
	}

	public void setAnswerValue(String answerValue) {
		this.answerValue = answerValue;
	}

}