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
    private Object defaultValue;

    private UnitDTO defaultUnit;


    public QuestionDTO() {
    }

    public QuestionDTO(String code, AnswerType answerType, String codeListName, Long unitCategoryId) {
        this.code = code;
        this.answerType = answerType;
        this.codeListName = codeListName;
        this.unitCategoryId = unitCategoryId;
    }

    public QuestionDTO(String code, AnswerType answerType, String codeListName, Long unitCategoryId, Object defaultValue) {
        this.code = code;
        this.answerType = answerType;
        this.codeListName = codeListName;
        this.unitCategoryId = unitCategoryId;
        this.defaultValue = defaultValue;
    }

    public QuestionDTO(String code, AnswerType answerType, String codeListName, Long unitCategoryId, Object defaultValue, UnitDTO defaultUnit) {
        this.code = code;
        this.answerType = answerType;
        this.codeListName = codeListName;
        this.unitCategoryId = unitCategoryId;
        this.defaultValue = defaultValue;
        this.defaultUnit = defaultUnit;
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

    public Object getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
    }

    public UnitDTO getDefaultUnit() {
        return defaultUnit;
    }

    public void setDefaultUnit(UnitDTO defaultUnit) {
        this.defaultUnit = defaultUnit;
    }

    @Override
    public String toString() {
        return "QuestionDTO{" +
                "defaultUnit=" + defaultUnit +
                ", defaultValue=" + defaultValue +
                ", unitCategoryId=" + unitCategoryId +
                ", codeListName='" + codeListName + '\'' +
                ", answerType=" + answerType +
                ", code='" + code + '\'' +
                '}';
    }
}
