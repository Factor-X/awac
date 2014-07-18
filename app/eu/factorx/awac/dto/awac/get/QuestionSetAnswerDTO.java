package eu.factorx.awac.dto.awac.get;

import java.util.ArrayList;
import java.util.List;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.awac.post.QuestionAnswerDTO;
import eu.factorx.awac.dto.validation.annotations.NotNull;

public class QuestionSetAnswerDTO extends DTO {

	@NotNull
    /**
     * the questionSet code
     */
	private String questionSetCode;

    /**
     * the repetition index for this questionSetAnswer.
     * Can be null (if this questionSetAnswer is not repetable)
     */
	private Integer repetitionIndex;

    /**
     *
     */
	private List<QuestionAnswerDTO> questionAnswers;

    /**
     * contains the questionSetAnswerDTO children
     */
	private List<QuestionSetAnswerDTO> children;

	public QuestionSetAnswerDTO() {
		super();
	}

	public QuestionSetAnswerDTO(Long id, String questionSetCode, Integer repetitionIndex) {
		super();
		this.questionSetCode = questionSetCode;
		this.repetitionIndex = repetitionIndex;
		this.questionAnswers = new ArrayList<>();
		this.children = new ArrayList<>();
	}

	public String getQuestionSetCode() {
		return questionSetCode;
	}

	public void setQuestionSetCode(String questionSetCode) {
		this.questionSetCode = questionSetCode;
	}

	public Integer getRepetitionIndex() {
		return repetitionIndex;
	}

	public void setRepetitionIndex(Integer repetitionIndex) {
		this.repetitionIndex = repetitionIndex;
	}

	public List<QuestionAnswerDTO> getQuestionAnswers() {
		return questionAnswers;
	}

	public void setQuestionAnswers(List<QuestionAnswerDTO> questionAnswers) {
		this.questionAnswers = questionAnswers;
	}

	public List<QuestionSetAnswerDTO> getChildren() {
		return children;
	}

	public void setChildren(List<QuestionSetAnswerDTO> children) {
		this.children = children;
	}

}
