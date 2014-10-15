package eu.factorx.awac.dto.verification.get;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.awac.get.OrganizationDTO;
import eu.factorx.awac.dto.awac.get.PeriodDTO;
import eu.factorx.awac.dto.awac.get.ScopeDTO;
import eu.factorx.awac.dto.myrmex.get.PersonDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by florian on 9/10/14.
 */
public class VerificationRequestDTO extends DTO{

    private Long id;

    private OrganizationDTO  organizationCustomer;

    private OrganizationDTO organizationVerifier;

    private ScopeDTO scope;

    private PeriodDTO period;

    private PersonDTO contact;

    private String contactPhoneNumber;

    private String comment;

    private String status;

    private List<PersonDTO> verifierList;

    private String verificationRejectedComment;

    private Long verificationSuccessFileId;

    public VerificationRequestDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<PersonDTO> getVerifierList() {
        return verifierList;
    }

    public void addVerifier(PersonDTO person){
        if(verifierList==null){
            verifierList = new ArrayList<>();
        }
        verifierList.add(person);
    }

    public OrganizationDTO getOrganizationVerifier() {
        return organizationVerifier;
    }

    public void setOrganizationVerifier(OrganizationDTO organizationVerifier) {
        this.organizationVerifier = organizationVerifier;
    }

    public String getVerificationRejectedComment() {
        return verificationRejectedComment;
    }

    public void setVerificationRejectedComment(String verificationRejectedComment) {
        this.verificationRejectedComment = verificationRejectedComment;
    }

    public Long getVerificationSuccessFileId() {
        return verificationSuccessFileId;
    }

    public void setVerificationSuccessFileId(Long verificationSuccessFileId) {
        this.verificationSuccessFileId = verificationSuccessFileId;
    }

    public void setVerifierList(List<PersonDTO> verifierList) {
        this.verifierList = verifierList;
    }

    public OrganizationDTO getOrganizationCustomer() {
        return organizationCustomer;
    }

    public void setOrganizationCustomer(OrganizationDTO organizationCustomer) {
        this.organizationCustomer = organizationCustomer;
    }

    public ScopeDTO getScope() {
        return scope;
    }

    public void setScope(ScopeDTO scope) {
        this.scope = scope;
    }

    public PeriodDTO getPeriod() {
        return period;
    }

    public void setPeriod(PeriodDTO period) {
        this.period = period;
    }

    public PersonDTO getContact() {
        return contact;
    }

    public void setContact(PersonDTO contact) {
        this.contact = contact;
    }

    public String getContactPhoneNumber() {
        return contactPhoneNumber;
    }

    public void setContactPhoneNumber(String contactPhoneNumber) {
        this.contactPhoneNumber = contactPhoneNumber;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "VerificationRequestDTO{" +
                "id=" + id +
                ", organizationCustomer=" + organizationCustomer +
                ", scope=" + scope +
                ", period=" + period +
                ", contact=" + contact +
                ", contactPhoneNumber='" + contactPhoneNumber + '\'' +
                ", comment='" + comment + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
