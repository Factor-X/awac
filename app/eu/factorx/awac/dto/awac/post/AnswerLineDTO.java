package eu.factorx.awac.dto.awac.post;

import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.validation.annotations.NotNull;
import eu.factorx.awac.models.data.question.QuestionSet;

public class AnswerLineDTO extends DTO {

	@NotNull
	private String questionKey;

	@NotNull
	private Object value;

	private String lastUpdateUser;

	private String unitCode;

	/**
	 * K: code of a parent {@link QuestionSet questionSet}<br>
	 * V: repetition index of this answer inside this {@link QuestionSet questionSet}
	 */
	private Map<String, Integer> mapRepetition;

    private String comment;

	public AnswerLineDTO() {
	}

    public AnswerLineDTO(String questionKey, Object value, String lastUpdateUser) {
        this.questionKey = questionKey;
        this.value = value;
        this.lastUpdateUser = lastUpdateUser;
    }

    public AnswerLineDTO(String questionKey, Object value, String lastUpdateUser, String unitCode) {
        this.questionKey = questionKey;
        this.value = value;
        this.lastUpdateUser = lastUpdateUser;
        this.unitCode = unitCode;
    }

    public AnswerLineDTO(String questionKey, Object value, String lastUpdateUser, Map<String, Integer> mapRepetition) {
        this.questionKey = questionKey;
        this.value = value;
        this.lastUpdateUser = lastUpdateUser;
        this.mapRepetition = mapRepetition;
    }

    public AnswerLineDTO(String questionKey, Object value, String lastUpdateUser, Map<String, Integer> mapRepetition,String unitCode) {
        this.questionKey = questionKey;
        this.value = value;
        this.lastUpdateUser = lastUpdateUser;
        this.unitCode = unitCode;
        this.mapRepetition = mapRepetition;
    }

    public AnswerLineDTO(String questionKey, Object value, String lastUpdateUser, String unitCode, Map<String, Integer> mapRepetition, String comment) {
        this.questionKey = questionKey;
        this.value = value;
        this.lastUpdateUser = lastUpdateUser;
        this.unitCode = unitCode;
        this.mapRepetition = mapRepetition;
        this.comment = comment;
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

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public Map<String, Integer> getMapRepetition() {
        return mapRepetition;
    }

    public void setMapRepetition(Map<String, Integer> mapRepetition) {
        this.mapRepetition = mapRepetition;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof AnswerLineDTO)) {
			return false;
		}
		AnswerLineDTO rhs = (AnswerLineDTO) obj;
		return new EqualsBuilder().append(this.questionKey, rhs.questionKey).append(this.mapRepetition, rhs.mapRepetition).isEquals();
	}

	/**
	 * Default implementation: override this.
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(7, 29).append(this.questionKey).append(this.mapRepetition).toHashCode();
	}

    @Override
    public String toString() {
        return "AnswerLineDTO{" +
                "questionKey='" + questionKey + '\'' +
                ", value=" + value +
                ", lastUpdateUser='" + lastUpdateUser + '\'' +
                ", unitCode='" + unitCode + '\'' +
                ", mapRepetition=" + mapRepetition +
                ", comment='" + comment + '\'' +
                '}';
    }
}
