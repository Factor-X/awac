package eu.factorx.awac.models.data.answer;

import eu.factorx.awac.models.AuditedAbstractEntity;
import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.data.question.QuestionSet;
import eu.factorx.awac.models.knowledge.Period;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({@NamedQuery(name = QuestionSetAnswer.FIND_DISTINCT_PERIODS, query = "select distinct qsa.period from QuestionSetAnswer qsa where qsa.scope.id = :scopeId"),})
public class QuestionSetAnswer extends AuditedAbstractEntity implements Comparable<QuestionSetAnswer> {

    /**
     * @param scopeId
     * : a {@link Long}
     */
    public static final String FIND_DISTINCT_PERIODS = "QuestionSetAnswer.findAllDistinctPeriodsByScopeId";

    private static final long serialVersionUID = 1L;

    @ManyToOne(optional = false)
    private QuestionSet questionSet;

    @ManyToOne(optional = false)
    private Period period;

    @ManyToOne(optional = false)
    private Scope scope;

    @ManyToOne()
    private QuestionSetAnswer parent = null;

    private Integer repetitionIndex = 0;

    @OneToMany(mappedBy = "questionSetAnswer", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<QuestionAnswer> questionAnswers;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<QuestionSetAnswer> children;

    @Embedded
    private AuditInfo auditInfo;


    public QuestionSetAnswer() {
        super();
        auditInfo = new AuditInfo();
    }

    /**
     * @param questionSet
     * @param period
     * @param scope
     * @param parent
     * @param repetitionIndex
     */
    public QuestionSetAnswer(Scope scope, Period period, QuestionSet questionSet, Integer repetitionIndex, QuestionSetAnswer parent) {
        super();
        this.scope = scope;
        this.period = period;
        this.questionSet = questionSet;
        this.repetitionIndex = repetitionIndex;
        this.parent = parent;
        this.questionAnswers = new ArrayList<QuestionAnswer>();
        this.children = new ArrayList<QuestionSetAnswer>();
        auditInfo = new AuditInfo();
    }

    public AuditInfo getAuditInfo() {
        return auditInfo;
    }

    public void setAuditInfo(AuditInfo auditInfo) {
        this.auditInfo = auditInfo;
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

    public QuestionSetAnswer getParent() {
        return parent;
    }

    public void setParent(QuestionSetAnswer parent) {
        this.parent = parent;
    }

    public Integer getRepetitionIndex() {
        return repetitionIndex;
    }

    public void setRepetitionIndex(Integer repetitionIndex) {
        this.repetitionIndex = repetitionIndex;
    }

    public List<QuestionAnswer> getQuestionAnswers() {
        return questionAnswers;
    }

    public void setQuestionAnswers(List<QuestionAnswer> questionAnswers) {
        this.questionAnswers = questionAnswers;
    }

    public List<QuestionSetAnswer> getChildren() {
        return children;
    }

    public void setChildren(List<QuestionSetAnswer> children) {
        this.children = children;
    }

    public QuestionSetAnswer appendChild(QuestionSetAnswer subQuestionSetAnswer) {
        this.getChildren().add(subQuestionSetAnswer);
        return this;
    }

    @Override
    public String toString() {
        return "QuestionSetAnswer{" +
                "id="+id+
                "questionSet=" + questionSet +
                ", period=" + period +
                ", scope=" + scope +
                ", parent=" + parent +
                ", repetitionIndex=" + repetitionIndex +
                ", questionAnswers=" + questionAnswers +
                ", auditInfo=" + auditInfo +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof QuestionSetAnswer)) {
            return false;
        }
        QuestionSetAnswer rhs = (QuestionSetAnswer) obj;
        return new EqualsBuilder().append(this.questionSet, rhs.questionSet).append(this.period, rhs.period).append(this.scope, rhs.scope).append(this.parent, rhs.parent)
                .append(this.repetitionIndex, rhs.repetitionIndex).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(47, 89).append(this.questionSet).append(this.period).append(this.scope).append(this.parent).append(this.repetitionIndex).toHashCode();
    }

    @Override
    public int compareTo(QuestionSetAnswer o) {
        if (this.questionSet.getCode().equals(o.questionSet.getCode()) && this.period.equals(o.period) && this.scope.equals(o.scope) && this.parent.equals(o.parent)) {
            return new CompareToBuilder().append(this.repetitionIndex, o.repetitionIndex).toComparison();
        }
        return 0;
    }

}
