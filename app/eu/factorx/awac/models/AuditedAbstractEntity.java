package eu.factorx.awac.models;

import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.joda.time.DateTime;

import play.Play;
import play.mvc.Http.Context;

@MappedSuperclass
public abstract class AuditedAbstractEntity extends AbstractEntity {

	private static final long serialVersionUID = -1711298010541869994L;

	public static final String TECHNICAL_USER = "TECHNICAL";
	public static final String TEST_USER = "TEST_USER";
	public static final String IDENTIFIER_HTTP_SESSION_ATTR_NAME = "identifier";

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
		this.technicalSegment = new TechnicalSegment(DateTime.now(), getCurrentUser());
	}

	@PreUpdate
	@Override
	public void preUpdate() {
		super.preUpdate();
		if (this.technicalSegment == null) {
			this.technicalSegment = new TechnicalSegment(DateTime.now(), getCurrentUser());
		} else {			
			this.technicalSegment.setLastUpdateDate(DateTime.now());
			this.technicalSegment.setLastUpdateUser(getCurrentUser());
		}
	}

	private void createTechnicalSegment() {
	}

	private void updateTechnicalSegment() {
		if (this.technicalSegment == null) {
			createTechnicalSegment();
		} else {			
			this.technicalSegment.setLastUpdateDate(DateTime.now());
			this.technicalSegment.setLastUpdateUser(getCurrentUser());
		}
	}

	private static String getCurrentUser() {
		if (Play.application().isTest()) {
			return TEST_USER;
		}
		if (Context.current.get() == null) { // == if no http context
			return TECHNICAL_USER;
		}

		return Context.current().session().get(IDENTIFIER_HTTP_SESSION_ATTR_NAME);
	}

	/**
	 * Default implementation: override this.
	 */
	@Override
	public String toString() {
		return "AuditedAbstractEntity [id=" + id + ", technicalSegment=" + technicalSegment + "]";
	}

}
