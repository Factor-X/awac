package eu.factorx.awac.dto.awac.post;

import java.util.Map;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.validation.annotations.NotNull;
import eu.factorx.awac.models.data.question.QuestionSet;

public class AnswerLineDTO extends DTO {

	@NotNull
	private String questionKey;

	@NotNull
	private Object value;

	private Integer unitId;

	/**
	 * K: code of a parent {@link QuestionSet questionSet}<br>
	 * V: repetition index of this answer inside this {@link QuestionSet questionSet}
	 */
    private Map<String,Integer> mapRepetition;


    public AnswerLineDTO() {
    }

    public AnswerLineDTO(String questionKey, Object value, Integer unitId, Map<String, Integer> mapRepetition) {
        this.questionKey = questionKey;
        this.value = value;
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
}
