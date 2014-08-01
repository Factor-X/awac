package eu.factorx.awac.models;

import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@MappedSuperclass
public abstract class AuditedAbstractEntity extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@Embedded
	protected TechnicalSegment technicalSegment;

	public TechnicalSegment getTechnicalSegment() {
		return technicalSegment;
	}

	public void setTechnicalSegment(TechnicalSegment technicalSegment) {
		this.technicalSegment = technicalSegment;
	}

	@PrePersist
	@Override
	public void prePersist() {
		super.prePersist();
		this.technicalSegment = new TechnicalSegment();
	}

	@PreUpdate
	@Override
	public void preUpdate() {
		super.preUpdate();
		this.technicalSegment.update();
	}

	@Override
	public String toString() {
		return super.toString() + " " + technicalSegment.toString();
	}
}
