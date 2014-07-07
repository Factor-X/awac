package eu.factorx.awac.dto.awac.get;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.awac.shared.AnswerLine;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by root on 7/07/14.
 */
public class AnswersDTO extends DTO {

    @NotNull
    private Integer ScopeId;
    @NotNull
    private Integer periodId;
    @NotNull
    private List<AnswerLine> listQuestionValueDTO;

    public AnswersDTO() {
    }

    public AnswersDTO(Integer scopeId, Integer periodId, List<AnswerLine> listQuestionValueDTO) {
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
