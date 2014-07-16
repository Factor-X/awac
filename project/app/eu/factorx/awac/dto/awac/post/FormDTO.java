package eu.factorx.awac.dto.awac.post;

/**
 * Created by root on 7/07/14.
 */
public class FormDTO {

    private Integer formId;
    private Integer periodId;
    private Integer scopeId;

    public FormDTO() {
    }

    public FormDTO(Integer formId, Integer periodId, Integer scopeId) {
        this.formId = formId;
        this.periodId = periodId;
        this.scopeId = scopeId;
    }

    public Integer getFormId() {
        return formId;
    }

    public void setFormId(Integer formId) {
        this.formId = formId;
    }

    public Integer getPeriodId() {
        return periodId;
    }

    public void setPeriodId(Integer periodId) {
        this.periodId = periodId;
    }

    public Integer getScopeId() {
        return scopeId;
    }

    public void setScopeId(Integer scopeId) {
        this.scopeId = scopeId;
    }
}
