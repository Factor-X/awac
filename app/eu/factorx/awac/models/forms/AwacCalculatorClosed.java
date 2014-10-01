package eu.factorx.awac.models.forms;

import eu.factorx.awac.models.AuditedAbstractEntity;
import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.knowledge.Period;

import javax.persistence.*;

/**
 * Created by florian on 26/09/14.
 */
@Entity
@Table(uniqueConstraints =@UniqueConstraint(columnNames = {"awaccalculator_id", "period_id", "scope_id"}))
@NamedQueries({
        @NamedQuery(name = AwacCalculatorClosed.FIND_BY_CALCULATOR_AND_PERIOD_AND_SCOPE, query = "select p from AwacCalculatorClosed p where p.awacCalculator = :calculator and p.period = :period and p.scope=:scope" ),
})
public class AwacCalculatorClosed extends AuditedAbstractEntity{

    public static final String FIND_BY_CALCULATOR_AND_PERIOD_AND_SCOPE = "AwacCalculatorClosed_FIND_BY_CALCULATOR_AND_PERIOD_AND_SCOPE";

    @ManyToOne(cascade = {CascadeType.MERGE}, optional = false)
    private AwacCalculator awacCalculator;

    @ManyToOne(cascade = {CascadeType.MERGE}, optional = false)
    private Period period;

    @ManyToOne(cascade = {CascadeType.MERGE}, optional = false)
    private Scope scope;

    public AwacCalculatorClosed() {
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
