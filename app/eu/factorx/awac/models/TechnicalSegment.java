package eu.factorx.awac.models;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import javax.persistence.Embeddable;

@Embeddable
public class TechnicalSegment {

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime creationDate;

	private String creationUser;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime lastUpdateDate;

	private String lastUpdateUser;

	protected TechnicalSegment() {
		super();
	}

	public TechnicalSegment(String creationUser) {
		super();
		LocalDateTime now = LocalDateTime.now();
		this.creationDate = now;
		this.creationUser = creationUser;
		this.lastUpdateDate = now;
		this.lastUpdateUser = creationUser;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public String getCreationUser() {
		return creationUser;
	}

	public void setCreationUser(String creationUser) {
		this.creationUser = creationUser;
	}

	public LocalDateTime getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(LocalDateTime lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getLastUpdateUser() {
		return lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	public void update(String updateUser) {
		this.lastUpdateDate = LocalDateTime.now();
		this.lastUpdateUser = updateUser;
	}

	@Override
	public String toString() {
		return "TechnicalSegment [creationDate=" + creationDate + ", creationUser=" + creationUser + ", lastUpdateDate=" + lastUpdateDate
				+ ", lastUpdateUser=" + lastUpdateUser + "]";
	}

}
