package eu.factorx.awac.models.data.answer;

import java.io.Serializable;

import javax.persistence.Column;
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

    @Column(nullable = false, columnDefinition = "boolean default false")
	protected Boolean  verificationAsked = false;

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


    public Boolean getVerificationAsked() {
        return verificationAsked;
    }

    public void setVerificationAsked(Boolean verificationAsked) {
        this.verificationAsked = verificationAsked;
    }

    @Override
    public String toString() {
        return "AuditInfo{" +
                "dataValidator=" + dataValidator +
                ", dataLocker=" + dataLocker +
                ", dataVerifier=" + dataVerifier +
                ", verificationAsked=" + verificationAsked +
                '}';
    }
}