package eu.factorx.awac.models.forms;

import eu.factorx.awac.models.AuditedAbstractEntity;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.code.type.VerificationRequestStatus;
import eu.factorx.awac.models.data.file.StoredFile;
import eu.factorx.awac.models.email.EmailVerificationContent;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by florian on 8/10/14.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = VerificationRequestCanceled.FIND_BY_ORGANIZATION_VERIFIER, query = "select p from VerificationRequestCanceled p where p.organizationVerifier = :organizationVerifier" ),
})
public class VerificationRequestCanceled extends AuditedAbstractEntity {

    private static final long serialVersionUID = 1L;
    public static final String FIND_BY_ORGANIZATION_VERIFIER = "VerificationRequestCanceled_FIND_BY_ORGANIZATION_VERIFIER";

    @ManyToOne(optional = false)
    private AwacCalculatorInstance  awacCalculatorInstance;

    @ManyToOne(optional = false)
    protected Organization organizationVerifier;

    @ManyToOne
    protected Account contact;

    @Embedded
    protected EmailVerificationContent emailVerificationContent;

    public VerificationRequestCanceled() {
    }

    public VerificationRequestCanceled(AwacCalculatorInstance awacCalculatorInstance, Organization organizationVerifier, Account contact, EmailVerificationContent emailVerificationContent) {
        this.awacCalculatorInstance = awacCalculatorInstance;
        this.organizationVerifier = organizationVerifier;
        this.contact = contact;
        this.emailVerificationContent = emailVerificationContent;
    }

    public Account getContact() {
        return contact;
    }

    public void setContact(Account contact) {
        this.contact = contact;
    }

    public AwacCalculatorInstance getAwacCalculatorInstance() {
        return awacCalculatorInstance;
    }

    public void setAwacCalculatorInstance(AwacCalculatorInstance awacCalculatorInstance) {
        this.awacCalculatorInstance = awacCalculatorInstance;
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

    @Override
    public String toString() {
        return "VerificationRequest{" +
                ", organizationVerifier=" + organizationVerifier +
                ", emailVerificationContent=" + emailVerificationContent +
                '}';
    }

}
