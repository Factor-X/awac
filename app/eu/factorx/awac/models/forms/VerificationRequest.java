package eu.factorx.awac.models.forms;

import eu.factorx.awac.models.AuditedAbstractEntity;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.code.type.VerificationRequestStatus;
import eu.factorx.awac.models.data.file.StoredFile;
import eu.factorx.awac.models.email.EmailVerificationContent;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by florian on 8/10/14.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = VerificationRequest.FIND_BY_KEY, query = "select p from VerificationRequest p where p.key = :verificationRequestKey" ),
        @NamedQuery(name = VerificationRequest.FIND_BY_ORGANIZATION_VERIFIER, query = "select p from VerificationRequest p where p.organizationVerifier = :organizationVerifier" ),
        @NamedQuery(name = VerificationRequest.FIND_BY_ORGANIZATION_VERIFIER_AND_SCOPE_AND_PERIOD, query = "select p from VerificationRequest p where p.organizationVerifier = :organizationVerifier and p.awacCalculatorInstance.scope = :scope and p.awacCalculatorInstance.period = :period" ),
        @NamedQuery(name = VerificationRequest.FIND_BY_SCOPE_AND_PERIOD, query = "select p from VerificationRequest p where p.awacCalculatorInstance.scope = :scope and p.awacCalculatorInstance.period = :period" ),
})
public class VerificationRequest extends AuditedAbstractEntity {

    private static final long serialVersionUID = 1L;
    public static final String FIND_BY_KEY = "VerificationRequest_FIND_BY_KEY";
    public static final String FIND_BY_ORGANIZATION_VERIFIER = "VerificationRequest_FIND_BY_ORGANIZATION_VERIFIER";
    public static final String FIND_BY_ORGANIZATION_VERIFIER_AND_SCOPE_AND_PERIOD= "VerificationRequest_FIND_BY_ORGANIZATION_VERIFIER_AND_SCOPE_AND_PERIOD";
    public static final String FIND_BY_SCOPE_AND_PERIOD = "VerificationRequest_FIND_BY_SCOPE_AND_PERIOD";

    @OneToOne(optional = false)
    private AwacCalculatorInstance  awacCalculatorInstance;

    @Embedded
    protected VerificationRequestStatus verificationRequestStatus;

    protected String key;

    @ManyToOne
    protected Organization organizationVerifier;

    @ManyToOne
    protected Account contact;

    @Embedded
    protected EmailVerificationContent emailVerificationContent;

    @ManyToMany
    @JoinTable(name = "mm_verifierrequest_account",
            joinColumns = @JoinColumn(name = "verifier_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "account_id", referencedColumnName = "id"),
            uniqueConstraints = {@UniqueConstraint(columnNames={"verifier_id", "account_id"}),
            })
    private List<Account> verifierList;

    @ManyToOne
    private StoredFile verificationResultDocument;
    private String verificationRejectedComment;


    public VerificationRequest() {
    }

    public AwacCalculatorInstance getAwacCalculatorInstance() {
        return awacCalculatorInstance;
    }

    public void setAwacCalculatorInstance(AwacCalculatorInstance awacCalculatorInstance) {
        this.awacCalculatorInstance = awacCalculatorInstance;
    }

    public VerificationRequestStatus getVerificationRequestStatus() {
        return verificationRequestStatus;
    }

    public void setVerificationRequestStatus(VerificationRequestStatus verificationRequestStatus) {
        this.verificationRequestStatus = verificationRequestStatus;
    }

    public StoredFile getVerificationResultDocument() {
        return verificationResultDocument;
    }

    public void setVerificationResultDocument(StoredFile verificationResultDocument) {
        this.verificationResultDocument = verificationResultDocument;
    }

    public Account getContact() {
        return contact;
    }

    public void setContact(Account contact) {
        this.contact = contact;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Organization getOrganizationVerifier() {
        return organizationVerifier;
    }

    public void setOrganizationVerifier(Organization organizationVerifier) {
        this.organizationVerifier = organizationVerifier;
    }

    public EmailVerificationContent getEmailVerificationContent() {
        return emailVerificationContent;
    }

    public void setEmailVerificationContent(EmailVerificationContent EmailVerificationContent) {
        this.emailVerificationContent = EmailVerificationContent;
    }

    public List<Account> getVerifierList() {
        return verifierList;
    }

    public void setVerifierList(List<Account> verifierList) {
        this.verifierList = verifierList;
    }

    public void addVerifier(Account verifier){
        if(this.verifierList==null){
            this.verifierList = new ArrayList<>();
        }
        this.verifierList.add(verifier);
    }

    @Override
    public String toString() {
        return "VerificationRequest{" +
                "verificationRequestStatus=" + verificationRequestStatus +
                ", key='" + key + '\'' +
                ", organizationVerifier=" + organizationVerifier +
                ", contact=" + contact +
                ", emailVerificationContent=" + emailVerificationContent +
                '}';
    }


    public String getVerificationRejectedComment() {
        return verificationRejectedComment;
    }

    public void setVerificationRejectedComment(String verificationRejectedComment) {
        this.verificationRejectedComment = verificationRejectedComment;
    }
}
