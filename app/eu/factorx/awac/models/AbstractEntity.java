package eu.factorx.awac.models;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import play.Logger;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class AbstractEntity implements Serializable {

	public static final String FIND_ALL = "Entity.findAll";

	public static final String TOTAL_RESULT = "Entity.totalResult";

	private static final long serialVersionUID = 1L;

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
		Logger.debug("===== Persisting " + getClass().getName() + " entity");
	}

	@PreUpdate
	public void preUpdate() {
		Logger.debug("===== Updating " + getClass().getName() + " entity with ID = " + getId());
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