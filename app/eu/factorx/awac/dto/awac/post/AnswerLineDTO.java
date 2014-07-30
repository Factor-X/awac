package eu.factorx.awac.dto.awac.post;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.validation.annotations.NotNull;
import eu.factorx.awac.models.data.question.QuestionSet;

import java.util.Map;

public class AnswerLineDTO extends DTO {

	@NotNull
	private String questionKey;

	@NotNull
	private Object value;

	private String lastUpdateUser;

	private Integer unitId;

	/**
	 * K: code of a parent {@link QuestionSet questionSet}<br>
	 * V: repetition index of this answer inside this {@link QuestionSet questionSet}
	 */
	private Map<String, Integer> mapRepetition;

	public AnswerLineDTO() {
	}

	public AnswerLineDTO(String questionKey, Object value, String lastUpdateUser, Integer unitId, Map<String, Integer> mapRepetition) {
		this.questionKey = questionKey;
		this.value = value;
		this.lastUpdateUser = lastUpdateUser;
		this.unitId = unitId;
		this.mapRepetition = mapRepetition;
	}

	public String getQuestionKey() {
		return questionKey;
	}

	public void setQuestionKey(String questionKey) {
		this.questionKey = questionKey;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getLastUpdateUser() {
		return lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	public Integer getUnitId() {
		return unitId;
	}

	public void setUnitId(Integer unitId) {
		this.unitId = unitId;
	}

	public Map<String, Integer> getMapRepetition() {
		return mapRepetition;
	}

	public void setMapRepetition(Map<String, Integer> mapRepetition) {
		this.mapRepetition = mapRepetition;
	}

	@Override 
	public String toString() {
		return "AnswerLineDTO [questionKey=" + questionKey + ", value=" + value + ", lastUpdateUser=" + lastUpdateUser + ", unitId="
				+ unitId + ", mapRepetition=" + mapRepetition + "]";
	}

}
