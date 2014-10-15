package eu.factorx.awac.dto.verification.post;

import eu.factorx.awac.dto.DTO;

import java.util.List;

/**
 * Created by florian on 9/10/14.
 */
public class VerificationRequestChangeStatusDTO extends DTO {

    private Long scopeId;

    private String periodKey;

    private String newStatus;
    private List<String> verifierIdentifier;

    private Long verificationFinalizationFileId;
    private String verificationRejectedComment;

    private String password;

    public VerificationRequestChangeStatusDTO() {
    }

    public Long getVerificationFinalizationFileId() {
        return verificationFinalizationFileId;
    }

    public void setVerificationFinalizationFileId(Long verificationFinalizationFileId) {
        this.verificationFinalizationFileId = verificationFinalizationFileId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getScopeId() {
        return scopeId;
    }

    public void setScopeId(Long scopeId) {
        this.scopeId = scopeId;
    }

    public String getPeriodKey() {
        return periodKey;
    }

    public void setPeriodKey(String periodKey) {
        this.periodKey = periodKey;
    }

    public String getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(String newStatus) {
        this.newStatus = newStatus;
    }

    public List<String> getVerifierIdentifier() {
        return verifierIdentifier;
    }

    public void setVerifierIdentifier(List<String> verifierIdentifier) {
        this.verifierIdentifier = verifierIdentifier;
    }

    @Override
    public String toString() {
        return "VerificationRequestChangeStatusDTO{" +
                "scopeId=" + scopeId +
                ", periodKey='" + periodKey + '\'' +
                ", newStatus='" + newStatus + '\'' +
                '}';
    }

    public String getVerificationRejectedComment() {
        return verificationRejectedComment;
    }

    public void setVerificationRejectedComment(String verificationRejectedComment) {
        this.verificationRejectedComment = verificationRejectedComment;
    }
}
