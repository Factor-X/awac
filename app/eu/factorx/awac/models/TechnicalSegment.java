package eu.factorx.awac.models;

import javax.persistence.Embeddable;

import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

import eu.factorx.awac.models.account.Account;

//@Embeddable
public class TechnicalSegment {

	@DateTimeFormat
	private DateTime creationDate;
	
	private Account creationUser;
	
	@DateTimeFormat
	private DateTime lastUpdateDate;
	
	private Account lastUpdateUser;

	protected TechnicalSegment() {
		super();
	}

	public DateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(DateTime creationDate) {
		this.creationDate = creationDate;
	}

	public Account getCreationUser() {
		return creationUser;
	}

	public void setCreationUser(Account creationUser) {
		this.creationUser = creationUser;
	}

	public DateTime getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(DateTime lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public Account getLastUpdateUser() {
		return lastUpdateUser;
	}

	public void setLastUpdateUser(Account lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}
	
	
}
