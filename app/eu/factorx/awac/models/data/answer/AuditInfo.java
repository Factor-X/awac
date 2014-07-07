package eu.factorx.awac.models.data.answer;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

import eu.factorx.awac.models.account.Account;

@Embeddable
public class AuditInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	protected Account dataValidator;

	@ManyToOne
	protected Account dataLocker;

	@ManyToOne
	protected Account dataVerifier;

	protected Integer verificationStatus;

	public Account getDataValidator() {
		return dataValidator;
	}

	public void setDataValidator(Account validator) {
		this.dataValidator = validator;
	}

	public Account getDataLocker() {
		return dataLocker;
	}

	public void setDataLocker(Account dataLocker) {
		this.dataLocker = dataLocker;
	}

	public Account getDataVerifier() {
		return dataVerifier;
	}

	public void setDataVerifier(Account dataVerifier) {
		this.dataVerifier = dataVerifier;
	}

	public Integer getVerificationStatus() {
		return verificationStatus;
	}

	public void setVerificationStatus(Integer verificationStatus) {
		this.verificationStatus = verificationStatus;
	}

}