package eu.factorx.awac.models.business;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import eu.factorx.awac.models.AuditedAbstractEntity;
import eu.factorx.awac.models.code.type.ScopeTypeCode;

@Entity
@Table(name = "scope")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Scope extends AuditedAbstractEntity {

	private static final long serialVersionUID = -3633516662163632222L;

	protected Scope() {
		super();
	}

	public abstract ScopeTypeCode getScopeType();

	public abstract Organization getOrganization();

	public abstract String getName();

}