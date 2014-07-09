package eu.factorx.awac.dto.awac.shared;

import javax.validation.constraints.NotNull;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.models.data.answer.AnswerType;

public class AnswerLine extends DTO {

	@NotNull
	private String questionKey;

	@NotNull
	private Object value;

	private Integer unitId;

	public AnswerLine() {
	}

	public AnswerLine(String questionKey, Object value) {
		this.questionKey = questionKey;
		this.value = value;
	}

	public AnswerLine(String questionKey, Object value, Integer unitId) {
		this.questionKey = questionKey;
		this.value = value;
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

	public Integer getUnitId() {
		return unitId;
	}

	public void setUnitId(Integer unitId) {
		this.unitId = unitId;
	}

}
