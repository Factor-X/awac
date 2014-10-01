package eu.factorx.awac.models.business;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import eu.factorx.awac.models.AuditedAbstractEntity;

@Entity
@Table(name = "scope")
@Inheritance(strategy = InheritanceType.JOINED)
// @DiscriminatorColumn(name = "type") // not used by hibernate - see hibernate.atlassian.net/browse/ANN-140
public abstract class Scope extends AuditedAbstractEntity {

	private static final long serialVersionUID = 1L;

	protected Scope() {
		super();
	}

	public Organization getOrganization() {
		if (this instanceof Organization) {
			return (Organization) this;
		}
		if (this instanceof Site) {
			return ((Site) this).getOrganization();
		}
		if (this instanceof Product) {
			return ((Product) this).getOrganization();
		}
		return null;
	}

    public abstract String getName();

    public abstract void setName(String name);
}