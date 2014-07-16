package eu.factorx.awac.dto.awac.get;

import eu.factorx.awac.dto.DTO;

public class QuestionDTO extends DTO {

    public String questionKey;

    public Long unitCategoryId;

    public String codeListName;

    protected QuestionDTO() {
        super();
    }

    public QuestionDTO(String questionKey, Long unitCategoryId) {
        super();
        this.questionKey = questionKey;
        this.unitCategoryId = unitCategoryId;
    }

    public QuestionDTO(String questionKey, String codeListName) {
        super();
        this.questionKey = questionKey;
        this.codeListName = codeListName;
    }

    public String getQuestionKey() {
        return questionKey;
    }

    public void setQuestionKey(String questionKey) {
        this.questionKey = questionKey;
    }

    public Long getUnitCategoryId() {
        return unitCategoryId;
    }

    public void setUnitCategoryId(Long unitCategoryId) {
        this.unitCategoryId = unitCategoryId;
    }

    public String getCodeListName() {
        return codeListName;
    }

    public void setCodeListName(String codeListName) {
        this.codeListName = codeListName;
    }
}
