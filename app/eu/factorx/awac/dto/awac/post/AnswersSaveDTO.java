package eu.factorx.awac.dto.awac.post;

import java.util.List;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.validation.annotations.NotNull;

public class AnswersSaveDTO extends DTO {

	@NotNull
	private Long scopeId;
	@NotNull
	private Long periodId;
	@NotNull
	private List<QuestionAnswerDTO> listAnswers;

	public AnswersSaveDTO() {
		super();
	}

	/**
	 * @param scopeId
	 * @param periodId
	 * @param listAnswers
	 */
	public AnswersSaveDTO(Long scopeId, Long periodId, List<QuestionAnswerDTO> listAnswers) {
		super();
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

	public List<QuestionAnswerDTO> getListAnswers() {
		return listAnswers;
	}

	public void setListAnswers(List<QuestionAnswerDTO> listAnswers) {
		this.listAnswers = listAnswers;
	}

}
