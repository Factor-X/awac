package eu.factorx.awac.models;

import java.io.Serializable;

import javax.persistence.*;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import play.Logger;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class AbstractEntity implements Serializable {

	private static final long serialVersionUID = 1139875066271860505L;

	private static final String PERSISTING_ENTITY = "JPA Event - Persisting new {} entity";
	private static final String UPDATING_ENTITY = "JPA Event - Updating {} entity with ID = {}";
	private static final String REMOVING_ENTITY = "JPA Event - Removing {} entity with ID = {}";
	private static final String LOADED_ENTITY = "JPA Event - Loaded {} entity with ID = {}";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@PrePersist
	public void prePersist() {
		Logger.debug(PERSISTING_ENTITY, getClass().getName());
	}

	@PreUpdate
	public void preUpdate() {
		Logger.debug(UPDATING_ENTITY, getClass().getName(), getId());
	}

	@PreRemove
	public void preRemove() {
		Logger.debug(REMOVING_ENTITY, getClass().getName(), getId());
	}

	@PostLoad
	public void postLoad() {
		Logger.trace(LOADED_ENTITY, getClass().getName(), getId());
	}

	/**
	 * Default implementation: override this.
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (obj.getClass() != getClass()) {
			return false;
		}
		AbstractEntity rhs = (AbstractEntity) obj;
		return new EqualsBuilder().append(this.id, rhs.id).isEquals();
	}

	/**
	 * Default implementation: override this.
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(this.id).toHashCode();
	}

    @Override
    public String toString() {
        return "AbstractEntity.id=" + id;
    }
}