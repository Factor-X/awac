package eu.factorx.awac.dto.awac.post;

import java.util.List;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.awac.shared.AnswerLine;
import eu.factorx.awac.dto.validation.annotations.NotNull;

public class AnswersSaveDTO extends DTO {

    @NotNull
    private Long scopeId;
    @NotNull
    private Long periodId;
    @NotNull
    private List<AnswerLine> listAnswers;

    public AnswersSaveDTO() {
    }

    public AnswersSaveDTO(Long scopeId, Long periodId, List<AnswerLine> listAnswers) {
        this.scopeId = scopeId;
        this.periodId = periodId;
        this.listAnswers = listAnswers;
    }

    public Long getScopeId() {
        return scopeId;
    }

    public void setScopeId(Long scopeId) {
        this.scopeId = scopeId;
    }

    public Long getPeriodId() {
        return periodId;
    }

    public void setPeriodId(Long periodId) {
        this.periodId = periodId;
    }

    public List<AnswerLine> getListAnswers() {
        return listAnswers;
    }

    public void setListAnswers(List<AnswerLine> listAnswers) {
        this.listAnswers = listAnswers;
    }
}
