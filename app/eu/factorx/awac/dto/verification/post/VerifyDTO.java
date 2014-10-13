package eu.factorx.awac.dto.verification.post;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.awac.get.VerificationDTO;

/**
 * Created by florian on 10/10/14.
 */
public class VerifyDTO extends DTO {
    private VerificationDTO verification;

    private Long scopeId;

    private String periodKey;

    private String questionSetKey;

    public VerifyDTO() {
    }

    public String getQuestionSetKey() {
        return questionSetKey;
    }

    public void setQuestionSetKey(String questionSetKey) {
        this.questionSetKey = questionSetKey;
    }

    public VerificationDTO getVerification() {
        return verification;
    }

    public void setVerification(VerificationDTO verification) {
        this.verification = verification;
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

    @Override
    public String toString() {
        return "VerifyDTO{" +
                "verification=" + verification +
                ", scopeId=" + scopeId +
                ", periodKey='" + periodKey + '\'' +
                '}';
    }
}
