package eu.factorx.awac.dto.awac.post;

import eu.factorx.awac.dto.DTO;

public class LockQuestionSetDTO extends DTO {

    private String questionSetKey;
    private String periodCode;
    private Long scopeId;
    private Boolean lock;

    public LockQuestionSetDTO() {
    }

    public String getQuestionSetKey() {
        return questionSetKey;
    }

    public void setQuestionSetKey(String questionSetKey) {
        this.questionSetKey = questionSetKey;
    }

    public String getPeriodCode() {
        return periodCode;
    }

    public void setPeriodCode(String periodCode) {
        this.periodCode = periodCode;
    }

    public Long getScopeId() {
        return scopeId;
    }

    public void setScopeId(Long scopeId) {
        this.scopeId = scopeId;
    }

    public Boolean getLock() {
        return lock;
    }

    public void setLock(Boolean lock) {
        this.lock = lock;
    }

    @Override
    public String toString() {
        return "LockQuestionSetDTO{" +
                "questionSetKey='" + questionSetKey + '\'' +
                ", periodCode='" + periodCode + '\'' +
                ", scopeId=" + scopeId +
                ", lock=" + lock +
                '}';
    }
}
