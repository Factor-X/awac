package uml.data;

import play.db.ebean.Model;
import uml.business.Scope;
import uml.business.User;
import uml.knowledge.Period;
import uml.knowledge.Unit;

import javax.persistence.Embedded;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class QuestionAnswer extends Model {

    private static final long serialVersionUID = 1L;

    @Id
    protected long id;
    @ManyToOne(optional = false)
    protected User dataOwner;
    @ManyToOne(optional = false)
    protected Period period;
    @ManyToOne(optional = false)
    protected Question question;
    @Embedded
    protected AuditInfo auditInfo;
    @ManyToOne(optional = false)
    protected Scope scope;
    @ManyToOne(optional = false)
    protected Unit unit;

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