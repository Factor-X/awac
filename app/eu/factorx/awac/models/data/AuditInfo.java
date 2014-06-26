package eu.factorx.awac.models.data;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

import eu.factorx.awac.models.business.User;

@Embeddable
public class AuditInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	protected User dataValidator;
	@ManyToOne
	protected User dataLocker;
	@ManyToOne
	protected User dataVerifier;
	protected Integer verificationStatus;

	public User getDataValidator() {
		return dataValidator;
	}

	public void setDataValidator(User validator) {
		this.dataValidator = validator;
	}

	public User getDataLocker() {
		return dataLocker;
	}

	public void setDataLocker(User dataLocker) {
		this.dataLocker = dataLocker;
	}

	public User getDataVerifier() {
		return dataVerifier;
	}

	public void setDataVerifier(User dataVerifier) {
		this.dataVerifier = dataVerifier;
	}

	public Integer getVerificationStatus() {
		return verificationStatus;
	}

	public void setVerificationStatus(Integer verificationStatus) {
		this.verificationStatus = verificationStatus;
	}

}