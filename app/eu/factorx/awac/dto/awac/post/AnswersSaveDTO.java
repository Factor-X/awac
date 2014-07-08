package eu.factorx.awac.dto.awac.post;

import java.util.List;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.awac.shared.AnswerLine;
import eu.factorx.awac.dto.validation.annotations.NotNull;

public class AnswersSaveDTO extends DTO {

    @NotNull
    private Integer ScopeId;
    @NotNull
    private Integer periodId;
    @NotNull
    private List<AnswerLine> listQuestionValueDTO;

    public AnswersSaveDTO() {
    }

    public AnswersSaveDTO(Integer scopeId, Integer periodId, List<AnswerLine> listQuestionValueDTO) {
        ScopeId = scopeId;
        this.periodId = periodId;
        this.listQuestionValueDTO = listQuestionValueDTO;
    }

    public Integer getScopeId() {
        return ScopeId;
    }

    public void setScopeId(Integer scopeId) {
        ScopeId = scopeId;
    }

    public Integer getPeriodId() {
        return periodId;
    }

    public void setPeriodId(Integer periodId) {
        this.periodId = periodId;
    }

    public List<AnswerLine> getListQuestionValueDTO() {
        return listQuestionValueDTO;
    }

    public void setListQuestionValueDTO(List<AnswerLine> listQuestionValueDTO) {
        this.listQuestionValueDTO = listQuestionValueDTO;
    }
}
