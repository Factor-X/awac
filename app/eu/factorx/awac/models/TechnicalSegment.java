package eu.factorx.awac.models;

import javax.persistence.Embeddable;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@Embeddable
public class TechnicalSegment {

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime creationDate;

	private String creationUser;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime lastUpdateDate;

	private String lastUpdateUser;

	protected TechnicalSegment() {
		super();
	}

	public TechnicalSegment(DateTime creationDate, String creationUser) {
		super();
		this.creationDate = creationDate;
		this.creationUser = creationUser;
		this.lastUpdateDate = creationDate;
		this.lastUpdateUser = creationUser;
	}

	public DateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(DateTime creationDate) {
		this.creationDate = creationDate;
	}

	public String getCreationUser() {
		return creationUser;
	}

	public void setCreationUser(String creationUser) {
		this.creationUser = creationUser;
	}

	public DateTime getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(DateTime lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getLastUpdateUser() {
		return lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	@Override
	public String toString() {
		return "TechnicalSegment [creationDate=" + creationDate + ", creationUser=" + creationUser + ", lastUpdateDate=" + lastUpdateDate + ", lastUpdateUser=" + lastUpdateUser + "]";
	}

}
