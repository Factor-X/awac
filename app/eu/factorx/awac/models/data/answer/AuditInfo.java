package eu.factorx.awac.models.data.answer;

import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.forms.VerificationRequest;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
public class AuditInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    protected Account dataValidator;

    @ManyToOne
    protected Account dataLocker;

    @ManyToOne
    protected Account dataVerifier;

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

    @Override
    public String toString() {
        return "AuditInfo{" +
                "dataValidator=" + dataValidator +
                ", dataLocker=" + dataLocker +
                ", dataVerifier=" + dataVerifier +
                '}';
    }
}