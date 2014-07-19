package eu.factorx.awac.models;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import play.Logger;
import play.mvc.Http.Context;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class AbstractEntity implements Serializable {

	public static final String FIND_ALL = "Entity.findAll";

	public static final String TOTAL_RESULT = "Entity.totalResult";

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Long id;

	@Embedded
	protected TechnicalSegment technicalSegment;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TechnicalSegment getTechnicalSegment() {
		return technicalSegment;
	}

	public void setTechnicalSegment(TechnicalSegment technicalSegment) {
		this.technicalSegment = technicalSegment;
	}

	@PostPersist
	public void postPersist() {
		Logger.debug("===== Persisted " + getClass().getName() + " entity with ID = " + getId());
		String creationUser;
		if (Context.current.get() == null) {
			creationUser = "TECH";
		} else {
			creationUser = Context.current().session().get("identifier");
		}
		this.technicalSegment = new TechnicalSegment(creationUser);
	}

	@PostUpdate
	public void postUpdate() {
		Logger.debug("===== Updated " + getClass().getName() + " entity with ID = " + getId());
		String updateUser;
		if (Context.current.get() == null) {
			updateUser = "TECH";
		} else {
			updateUser = Context.current().session().get("identifier");
		}
		this.technicalSegment.update(updateUser);
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

}