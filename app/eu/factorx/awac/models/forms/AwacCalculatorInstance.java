package eu.factorx.awac.models.forms;

import javax.persistence.*;

import eu.factorx.awac.models.AuditedAbstractEntity;
import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.knowledge.Period;


@Entity
@Table(uniqueConstraints =@UniqueConstraint(columnNames = {"awaccalculator_id", "period_id", "scope_id"}))
@NamedQueries({
        @NamedQuery(name = AwacCalculatorInstance.FIND_BY_CALCULATOR_AND_PERIOD_AND_SCOPE, query = "select p from AwacCalculatorInstance p where p.awacCalculator = :calculator and p.period = :period and p.scope=:scope" ),
        @NamedQuery(name = AwacCalculatorInstance.FIND_BY_PERIOD_AND_SCOPE, query = "select p from AwacCalculatorInstance p where p.period = :period and p.scope=:scope" ),
        @NamedQuery(name = AwacCalculatorInstance.FIND_BY_PERIOD_AND_ORGANIZATION, query = "select p from AwacCalculatorInstance p where p.period = :period and p.scope=:scope" ),
        @NamedQuery(name = AwacCalculatorInstance.FIND_BY_PERIOD_AND_SCOPES, query = "select p from AwacCalculatorInstance p where p.period = :period and p.scope in :scopes" ),

})
public class AwacCalculatorInstance extends AuditedAbstractEntity{

    public static final String FIND_BY_CALCULATOR_AND_PERIOD_AND_SCOPE = "AwacCalculatorInstance_FIND_BY_CALCULATOR_AND_PERIOD_AND_SCOPE";
    public static final String FIND_BY_PERIOD_AND_SCOPE = "AwacCalculatorInstance_FIND_BY_PERIOD_AND_SCOPE";
    public static final java.lang.String FIND_BY_PERIOD_AND_ORGANIZATION = "AwacCalculatorInstance_FIND_BY_PERIOD_AND_ORGANIZATION";
    public static final java.lang.String FIND_BY_PERIOD_AND_SCOPES = "AwacCalculatorInstance_FIND_BY_PERIOD_AND_SCOPES";

    @ManyToOne(cascade = {CascadeType.MERGE}, optional = false)
    private AwacCalculator awacCalculator;

    @ManyToOne(cascade = {CascadeType.MERGE}, optional = false)
    private Period period;

    @ManyToOne(cascade = {CascadeType.MERGE}, optional = false)
    private Scope scope;

    @Column(columnDefinition = "boolean not null default false", nullable = false)
    private Boolean closed = false;

    @OneToOne(mappedBy="awacCalculatorInstance")
    private VerificationRequest verificationRequest;

    public AwacCalculatorInstance() {
        super();
    }

    public VerificationRequest getVerificationRequest() {
        return verificationRequest;
    }

    public void setVerificationRequest(VerificationRequest verificationRequest) {
        this.verificationRequest = verificationRequest;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }


    public AwacCalculator getAwacCalculator() {
        return awacCalculator;
    }

    public void setAwacCalculator(AwacCalculator awacCalculator) {
        this.awacCalculator = awacCalculator;
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

    @Override
    public String toString() {
        return "AwacCalculatorClosed{" +
                "awacCalculator=" + awacCalculator +
                ", period=" + period +
                ", scope=" + scope +
                '}';
    }
}
