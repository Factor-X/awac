package uml.data;

import play.db.ebean.Model;
import uml.business.User;

import javax.persistence.Embeddable;

@Embeddable
public class AuditInfo extends Model {

    private static final long serialVersionUID = 1L;

    //@ManyToOne
    private User dataValidator;
    //@ManyToOne
    private User dataLocker;
    //@ManyToOne
    private User dataVerifier;
    private Integer verificationStatus;

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