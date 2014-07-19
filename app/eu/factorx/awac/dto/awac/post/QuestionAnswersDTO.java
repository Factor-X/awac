package eu.factorx.awac.dto.awac.post;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.validation.annotations.NotNull;

import java.util.List;

public class QuestionAnswersDTO extends DTO {

	@NotNull
	private Long scopeId;
	@NotNull
	private Long periodId;
	@NotNull
	private List<AnswerLineDTO> listAnswers;

	public QuestionAnswersDTO() {
	}

	public QuestionAnswersDTO(Long scopeId, Long periodId, List<AnswerLineDTO> listAnswers) {
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

	public List<AnswerLineDTO> getListAnswers() {
		return listAnswers;
	}

	public void setListAnswers(List<AnswerLineDTO> listAnswers) {
		this.listAnswers = listAnswers;
	}

	@Override
	public String toString() {
		return "QuestionAnswersDTO [scopeId=" + scopeId + ", periodId=" + periodId + ", listAnswers=" + listAnswers + "]";
	}

}
