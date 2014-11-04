package eu.factorx.awac.models.forms;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import eu.factorx.awac.models.AuditedAbstractEntity;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.code.type.VerificationRequestStatus;
import eu.factorx.awac.models.data.answer.Verification;
import eu.factorx.awac.models.data.file.StoredFile;
import eu.factorx.awac.models.email.EmailVerificationContent;

/**
 * Created by florian on 8/10/14.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = VerificationRequest.FIND_BY_KEY, query = "select p from VerificationRequest p where p.key = :verificationRequestKey" ),
        @NamedQuery(name = VerificationRequest.FIND_BY_ORGANIZATION_VERIFIER, query = "select p from VerificationRequest p where p.organizationVerifier = :organizationVerifier" ),
        @NamedQuery(name = VerificationRequest.FIND_BY_ORGANIZATION_VERIFIER_AND_SCOPE, query = "select p from VerificationRequest p where p.organizationVerifier = :organizationVerifier and p.awacCalculatorInstance.scope = :scope" ),
        @NamedQuery(name = VerificationRequest.FIND_BY_ORGANIZATION_VERIFIER_AND_SCOPE_AND_PERIOD, query = "select p from VerificationRequest p where p.organizationVerifier = :organizationVerifier and p.awacCalculatorInstance.scope = :scope and p.awacCalculatorInstance.period = :period" ),
        @NamedQuery(name = VerificationRequest.FIND_BY_SCOPE_AND_PERIOD, query = "select p from VerificationRequest p where p.awacCalculatorInstance.scope = :scope and p.awacCalculatorInstance.period = :period" ),
        @NamedQuery(name = VerificationRequest.FIND_BY_ORGANIZATION_VERIFIER_AND_VERIFICATION_REQUEST_STATUS, query = "select p from VerificationRequest p where p.organizationVerifier = :organizationVerifier and p.verificationRequestStatus = :verificationRequestStatus" )
})
public class VerificationRequest extends AuditedAbstractEntity {

    private static final long serialVersionUID = 1L;
    public static final String FIND_BY_KEY = "VerificationRequest_FIND_BY_KEY";
    public static final String FIND_BY_ORGANIZATION_VERIFIER = "VerificationRequest_FIND_BY_ORGANIZATION_VERIFIER";
	public static final String FIND_BY_ORGANIZATION_VERIFIER_AND_SCOPE = "VerificationRequest.FIND_BY_ORGANIZATION_VERIFIER_AND_SCOPE";
    public static final String FIND_BY_ORGANIZATION_VERIFIER_AND_SCOPE_AND_PERIOD= "VerificationRequest_FIND_BY_ORGANIZATION_VERIFIER_AND_SCOPE_AND_PERIOD";
    public static final String FIND_BY_SCOPE_AND_PERIOD = "VerificationRequest_FIND_BY_SCOPE_AND_PERIOD";
    public static final java.lang.String FIND_BY_ORGANIZATION_VERIFIER_AND_VERIFICATION_REQUEST_STATUS = "VerificationRequest_FIND_BY_ORGANIZATION_VERIFIER_AND_VERIFICATION_REQUEST_STATUS";

    @OneToOne(optional = false)
    private AwacCalculatorInstance  awacCalculatorInstance;

    @Embedded
    protected VerificationRequestStatus verificationRequestStatus;

    protected String key;

    @ManyToOne
    protected Organization organizationVerifier;

    @ManyToOne
    protected Account contact;

    @OneToMany(mappedBy="verificationRequest")
    private List<Verification> verificationList;

    @Embedded
    protected EmailVerificationContent emailVerificationContent;

    @ManyToMany
    @JoinTable(name = "mm_verifierrequest_account",
            joinColumns = @JoinColumn(name = "verifier_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "account_id", referencedColumnName = "id"),
            uniqueConstraints = {@UniqueConstraint(columnNames={"verifier_id", "account_id"}),
            })
    private Set<Account> verifierList;

    @ManyToOne
    private StoredFile verificationResultDocument;
    private String verificationRejectedComment;


    public VerificationRequest() {
    }


    public List<Verification> getVerificationList() {
        return verificationList;
    }

    public void setVerificationList(List<Verification> verificationList) {
        this.verificationList = verificationList;
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

    public Set<Account> getVerifierList() {
        return verifierList;
    }

    public void setVerifierList(Set<Account> verifierList) {
        this.verifierList = verifierList;
    }

    public void addVerifier(Account verifier){
        if(this.verifierList==null){
            this.verifierList = new HashSet<>();
        }
        this.verifierList.add(verifier);
    }

    @Override
    public String toString() {
        return "VerificationRequest{" +
                "id="+getId()+
                ", awacCalculatorInstance=" + awacCalculatorInstance.getId() +
                ", verificationRequestStatus=" + verificationRequestStatus.getKey() +
                ", key='" + key + '\'' +
                ", organizationVerifier=" + organizationVerifier.getId() +
                ", contact=" + contact.getId() +
                ", verifierList=" + verifierList +
                ", verificationResultDocument=" + verificationResultDocument +
                ", verificationRejectedComment='" + verificationRejectedComment + '\'' +
                '}';
    }

    public String getVerificationRejectedComment() {
        return verificationRejectedComment;
    }

    public void setVerificationRejectedComment(String verificationRejectedComment) {
        this.verificationRejectedComment = verificationRejectedComment;
    }
}
