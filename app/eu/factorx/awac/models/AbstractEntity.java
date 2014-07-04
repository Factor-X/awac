package eu.factorx.awac.models;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class AbstractEntity implements Serializable, Comparable<AbstractEntity> {

	public static final String FIND_ALL = "Entity.findAll";

	public static final String TOTAL_RESULT = "Entity.totalResult";

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Long id;

	// @Embedded
	// protected TechnicalSegment technicalSegment;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	// public TechnicalSegment getTechnicalSegment() {
	// return technicalSegment;
	// }
	//
	// public void setTechnicalSegment(TechnicalSegment technicalSegment) {
	// this.technicalSegment = technicalSegment;
	// }

	/**
	 * Default implementation: override this.
	 * 
	 */
	@Override
	public int compareTo(AbstractEntity obj) {
		return new CompareToBuilder().append(this.id, obj.id).toComparison();
	}

	/**
	 * Default implementation: override this.
	 * 
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
	 * 
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(this.id).toHashCode();
	}

}