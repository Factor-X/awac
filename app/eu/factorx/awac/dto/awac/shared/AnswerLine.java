package eu.factorx.awac.dto.awac.shared;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.validation.annotations.NotNull;

public class AnswerLine extends DTO {

	@NotNull
	private String questionKey;

	@NotNull
	private Integer repetitionIndex;

	@NotNull
	private Object value;

	private Integer unitId;

	public AnswerLine() {
	}

	public AnswerLine(String questionKey, Object value, Integer repetitionIndex, Integer unitId) {
		this.questionKey = questionKey;
		this.value = value;
		this.repetitionIndex = repetitionIndex;
		this.unitId = unitId;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getQuestionKey() {
		return questionKey;
	}

	public void setQuestionKey(String questionKey) {
		this.questionKey = questionKey;
	}

	public Integer getRepetitionIndex() {
		return repetitionIndex;
	}

	public void setRepetitionIndex(Integer repetitionIndex) {
		this.repetitionIndex = repetitionIndex;
	}

	public Integer getUnitId() {
		return unitId;
	}

	public void setUnitId(Integer unitId) {
		this.unitId = unitId;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("questionKey", questionKey)
				.append("repetitionIndex", repetitionIndex).append("value", value).append("unitId", unitId).toString();
	}
}
