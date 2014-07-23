package eu.factorx.awac.dto.awac.get;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.models.data.answer.AnswerType;

public class QuestionDTO extends DTO {

	private String code;

	private AnswerType answerType;

	// if AnswerType = VALUE_SELECTION
	private String codeListName;

	// if AnswerType = INTEGER or DOUBLE
	private Long unitCategoryId;

    //a default value for the answer
    private Double defaultValue;


    public QuestionDTO() {
    }

    public QuestionDTO(String code, AnswerType answerType, String codeListName, Long unitCategoryId) {
        this.code = code;
        this.answerType = answerType;
        this.codeListName = codeListName;
        this.unitCategoryId = unitCategoryId;
    }

    public QuestionDTO(String code, AnswerType answerType, String codeListName, Long unitCategoryId, Double defaultValue) {
        this.code = code;
        this.answerType = answerType;
        this.codeListName = codeListName;
        this.unitCategoryId = unitCategoryId;
        this.defaultValue = defaultValue;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public AnswerType getAnswerType() {
        return answerType;
    }

    public void setAnswerType(AnswerType answerType) {
        this.answerType = answerType;
    }

    public String getCodeListName() {
        return codeListName;
    }

    public void setCodeListName(String codeListName) {
        this.codeListName = codeListName;
    }

    public Long getUnitCategoryId() {
        return unitCategoryId;
    }

    public void setUnitCategoryId(Long unitCategoryId) {
        this.unitCategoryId = unitCategoryId;
    }

    public Double getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(Double defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public String toString() {
        return "QuestionDTO{" +
                "code='" + code + '\'' +
                ", answerType=" + answerType +
                ", codeListName='" + codeListName + '\'' +
                ", unitCategoryId=" + unitCategoryId +
                ", defaultValue=" + defaultValue +
                '}';
    }
}
