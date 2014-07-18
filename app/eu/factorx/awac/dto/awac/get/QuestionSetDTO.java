package eu.factorx.awac.dto.awac.get;

import java.util.List;

import eu.factorx.awac.dto.DTO;

public class QuestionSetDTO extends DTO {

	private String code;

	private Boolean repetitionAllowed;

    private List<QuestionSetDTO> children;

    private List<QuestionDTO> questions;

    public QuestionSetDTO() {
    }

    public QuestionSetDTO(String code, Boolean repetitionAllowed, List<QuestionSetDTO> children, List<QuestionDTO> questions) {
        this.code = code;
        this.repetitionAllowed = repetitionAllowed;
        this.children = children;
        this.questions = questions;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getRepetitionAllowed() {
        return repetitionAllowed;
    }

    public void setRepetitionAllowed(Boolean repetitionAllowed) {
        this.repetitionAllowed = repetitionAllowed;
    }

    public List<QuestionSetDTO> getChildren() {
        return children;
    }

    public void setChildren(List<QuestionSetDTO> children) {
        this.children = children;
    }

    public List<QuestionDTO> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionDTO> questions) {
        this.questions = questions;
    }
}
