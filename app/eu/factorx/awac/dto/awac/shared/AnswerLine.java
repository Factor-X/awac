package eu.factorx.awac.dto.awac.shared;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.validation.annotations.NotNull;

import java.util.Map;

public class AnswerLine extends DTO {

	@NotNull
	private String questionKey;

	@NotNull
	private Object value;

	private Integer unitId;

    private Map<String,Integer> mapRepetition;


    public AnswerLine() {
    }

    public AnswerLine(String questionKey, Object value, Integer unitId, Map<String, Integer> mapRepetition) {
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
