package eu.factorx.awac.dto.awac.post;

import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.validation.annotations.NotNull;

public class QuestionAnswersDTO extends DTO {

	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormat.forPattern("yyyy/MM/dd HH:mm");

	@NotNull
	private Long formId;
	@NotNull
	private Long scopeId;
	@NotNull
	private Long periodId;
	private String lastUpdateDate;
	@NotNull
	private List<AnswerLineDTO> listAnswers;

	public QuestionAnswersDTO() {
	}

	public QuestionAnswersDTO(Long formId, Long scopeId, Long periodId, DateTime lastUpdateDate, List<AnswerLineDTO> listAnswers) {
		this.formId = formId;
		this.scopeId = scopeId;
		this.periodId = periodId;
		if (lastUpdateDate != null) {
			this.lastUpdateDate = DATE_TIME_FORMATTER.print(lastUpdateDate);
		}
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

	public String getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(String lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public List<AnswerLineDTO> getListAnswers() {
		return listAnswers;
	}

	public void setListAnswers(List<AnswerLineDTO> listAnswers) {
		this.listAnswers = listAnswers;
	}

	@Override
	public String toString() {
		return "QuestionAnswersDTO [formId=" + formId + ", scopeId=" + scopeId + ", periodId=" + periodId + ", lastUpdateDate="
				+ lastUpdateDate + ", listAnswers=" + listAnswers + "]";
	}
}
