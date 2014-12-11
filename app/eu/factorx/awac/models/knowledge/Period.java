package eu.factorx.awac.models.knowledge;

import javax.persistence.*;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import eu.factorx.awac.models.AuditedAbstractEntity;
import eu.factorx.awac.models.code.type.PeriodCode;

@Entity
@Table(name = "period")
public class Period extends AuditedAbstractEntity implements Comparable<Period> {

    private static final long serialVersionUID = 1L;

    @Embedded
    @AttributeOverrides({@AttributeOverride(name = "key", column = @Column(name = "code"))})
    @Column(unique = true, nullable = false)
    private PeriodCode periodCode;

    private String label;

    public Period() {
    }

    public Period(PeriodCode periodCode, String label) {
        super();
        this.periodCode = periodCode;
        this.label = label;
    }

    public PeriodCode getPeriodCode() {
        return periodCode;
    }

    public void setPeriodCode(PeriodCode periodCode) {
        this.periodCode = periodCode;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String param) {
        this.label = param;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Period)) {
            return false;
        }
        Period rhs = (Period) obj;
        return new EqualsBuilder().append(this.periodCode, rhs.periodCode).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(19, 67).append(this.label).toHashCode();
    }

    public String toString() {
        return "Period[periodCode : " + periodCode.toString() + ", label: " + label + "]";
    }

    @Override
    public int compareTo(Period o) {
        return new Integer(Integer.parseInt(o.periodCode.getKey())).compareTo(Integer.parseInt(this.periodCode.getKey()));
    }
}