package eu.factorx.awac.models;

import play.mvc.Http.Context;

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
		this.technicalSegment = new TechnicalSegment(getCurrentUser());
	}

	@PreUpdate
	@Override
	public void preUpdate() {
		super.preUpdate();
		this.technicalSegment.update(getCurrentUser());
	}

	private String getCurrentUser() {
		if (Context.current.get() == null) {
			return "TECH";
		}
		return Context.current().session().get("identifier");
	}

}
