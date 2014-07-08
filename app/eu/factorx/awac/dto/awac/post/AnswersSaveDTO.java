package eu.factorx.awac.dto.awac.post;

import java.util.List;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.awac.shared.AnswerLine;
import eu.factorx.awac.dto.validation.annotations.NotNull;

public class AnswersSaveDTO extends DTO {

    @NotNull
    private Integer scopeId;
    @NotNull
    private Integer periodId;
    @NotNull
    private List<AnswerLine> listAnswers;

    public AnswersSaveDTO() {
    }

    public AnswersSaveDTO(Integer scopeId, Integer periodId, List<AnswerLine> listAnswers) {
        this.scopeId = scopeId;
        this.periodId = periodId;
        this.listAnswers = listAnswers;
    }

    public Integer getScopeId() {
        return scopeId;
    }

    public void setScopeId(Integer scopeId) {
        this.scopeId = scopeId;
    }

    public Integer getPeriodId() {
        return periodId;
    }

    public void setPeriodId(Integer periodId) {
        this.periodId = periodId;
    }

    public List<AnswerLine> getListAnswers() {
        return listAnswers;
    }

    public void setListAnswers(List<AnswerLine> listAnswers) {
        this.listAnswers = listAnswers;
    }
}
