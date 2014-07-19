package eu.factorx.awac.dto.awac.post;

import java.util.List;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.validation.annotations.NotNull;

public class QuestionAnswersDTO extends DTO {

	@NotNull
	private Long formId;
	@NotNull
	private Long scopeId;
	@NotNull
	private Long periodId;
	@NotNull
	private List<AnswerLineDTO> listAnswers;

	public QuestionAnswersDTO() {
	}

	public QuestionAnswersDTO(Long formId, Long scopeId, Long periodId, List<AnswerLineDTO> listAnswers) {
		this.formId = formId;
		this.scopeId = scopeId;
		this.periodId = periodId;
		this.listAnswers = listAnswers;
	}

	public Long getFormId() {
		return formId;
	}

	public void setFormId(Long formId) {
		this.formId = formId;
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

	public List<AnswerLineDTO> getListAnswers() {
		return listAnswers;
	}

	public void setListAnswers(List<AnswerLineDTO> listAnswers) {
		this.listAnswers = listAnswers;
	}

	@Override
	public String toString() {
		return "QuestionAnswersDTO [formId=" + formId + ", scopeId=" + scopeId + ", periodId=" + periodId + ", listAnswers=" + listAnswers + "]";
	}

}
