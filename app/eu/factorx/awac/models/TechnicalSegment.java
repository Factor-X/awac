package eu.factorx.awac.models;

import javax.persistence.Embeddable;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import play.Play;
import play.mvc.Http.Context;
import play.mvc.Http.Session;

@Embeddable
public class TechnicalSegment {

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime creationDate;

	private String creationUser;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime lastUpdateDate;

	private String lastUpdateUser;

	public TechnicalSegment() {
		super();
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

	public void update() {
		this.lastUpdateDate = DateTime.now();
		this.lastUpdateUser = getCurrentUser();
	}

	private static String getCurrentUser() {
		if (Play.application().isTest()) {
			return "TEST";
		}
		if (Context.current.get() == null) {
			return "TECH";
		}

		Session session = Context.current().session();
		return session.get("identifier");
	}

	public static TechnicalSegment newInstance() {
		TechnicalSegment res = new TechnicalSegment();
		DateTime now = DateTime.now();
		String creationUser = getCurrentUser();
		res.creationDate = now;
		res.creationUser = creationUser;
		res.lastUpdateDate = now;
		res.lastUpdateUser = creationUser;
		return res;
	}

	@Override
	public String toString() {
		return "TechnicalSegment [creationDate=" + creationDate + ", creationUser=" + creationUser + ", lastUpdateDate=" + lastUpdateDate + ", lastUpdateUser=" + lastUpdateUser + "]";
	}

}
