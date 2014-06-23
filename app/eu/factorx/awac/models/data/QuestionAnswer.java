package eu.factorx.awac.models.data;

import java.io.Serializable;

import javax.persistence.Embedded;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.business.User;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.models.knowledge.Unit;

@MappedSuperclass
public class QuestionAnswer implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	protected long id;
	@ManyToOne(optional = false)
	private User dataOwner;
	@ManyToOne(optional = false)
	private Period period;
	@ManyToOne(optional = false)
	private Question question;
	@Embedded
	private AuditInfo auditInfo;
	@ManyToOne(optional = false)
	private Scope scope;
	@ManyToOne(optional = false)
	private Unit unit;

	public long getId() {
		return id;
	}

	public void setId(long id) {
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

}