package eu.factorx.awac.models;

import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import play.Play;
import play.mvc.Http.Context;
import play.mvc.Http.Session;

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
		String currentUser = getCurrentUser();
		if (StringUtils.isBlank(currentUser)) {
			currentUser = "TECHNICAL";
		}
		this.technicalSegment = new TechnicalSegment(DateTime.now(), currentUser);
	}

	@PreUpdate
	@Override
	public void preUpdate() {
		super.preUpdate();
		this.technicalSegment.setLastUpdateDate(DateTime.now());
		this.technicalSegment.setLastUpdateUser(getCurrentUser());
	}

	private static String getCurrentUser() {
		if (Play.application().isTest()) {
			return "TEST_USER";
		}
		if (Context.current.get() == null) {
			return "TECHNICAL";
		}

		Session session = Context.current().session();
		return session.get("identifier");
	}

	/**
	 * Default implementation: override this.
	 */
	@Override
	public String toString() {
		return "AuditedAbstractEntity [id=" + id + ", technicalSegment=" + technicalSegment + "]";
	}

}
