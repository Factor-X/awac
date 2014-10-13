package eu.factorx.awac.models.business;

import eu.factorx.awac.models.AuditedAbstractEntity;
import eu.factorx.awac.models.knowledge.Period;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"organization_id", "period_id", "name"})
})
@NamedQueries({
        @NamedQuery(name = OrganizationEvent.FIND_BY_ORGANIZATION_AND_PERIOD_AND_NAME, query = "select p from OrganizationEvent p where p.organization =  :organization and p.period = :period and p.name = :name"),
        @NamedQuery(name = OrganizationEvent.FIND_BY_ORGANIZATION_AND_PERIOD, query = "select p from OrganizationEvent p where p.organization =  :organization and p.period = :period"),
        @NamedQuery(name = OrganizationEvent.FIND_BY_ORGANIZATION, query = "select p from OrganizationEvent p where p.organization =  :organization"),
})
public class OrganizationEvent extends AuditedAbstractEntity {

    public static final String ORGANIZATION_PROPERTY_NAME = "organization";
    public static final String PERIOD_PROPERTY_NAME = "period";

    public static final String FIND_BY_ORGANIZATION_AND_PERIOD_AND_NAME = "OrganizationEvent_FIND_BY_ORGANIZATION_AND_PERIOD_AND_NAME";
    public static final String FIND_BY_ORGANIZATION_AND_PERIOD = "OrganizationEvent_FIND_BY_ORGANIZATION_AND_PERIOD";
    public static final String FIND_BY_ORGANIZATION = "OrganizationEvent_FIND_BY_ORGANIZATION";

    private static final long serialVersionUID = 1L;

    @ManyToOne(optional = false)
    private Organization organization;

    @ManyToOne(optional = false)
    private Period period;

    @Column(nullable = false)
    private String name;

    @Column(nullable = true, length = 1000)
    private String description;

    public OrganizationEvent() {
        super();
    }

    /**
     * @param organization
     * @param period
     * @param name
     * @param description
     */
    public OrganizationEvent(Organization organization, Period period, String name, String description) {
        super();
        this.organization = organization;
        this.period = period;
        this.name = name;
        this.description = description;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof OrganizationEvent)) {
            return false;
        }
        OrganizationEvent rhs = (OrganizationEvent) obj;
        return new EqualsBuilder().append(this.organization, rhs.organization).append(this.period, rhs.period).append(this.name, rhs.name).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(23, 61).append(this.organization).append(this.period).append(this.name).toHashCode();
    }

    @Override
    public String toString() {
        return "OrganizationEvent [id=" + id + ", organization=" + organization + ", period=" + period + ", name='" + name + "', description='" + description + "']";
    }

}
