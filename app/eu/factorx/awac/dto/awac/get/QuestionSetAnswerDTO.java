package eu.factorx.awac.dto.awac.get;

import java.util.ArrayList;
import java.util.List;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.awac.shared.AnswerLine;
import eu.factorx.awac.dto.validation.annotations.NotNull;

public class QuestionSetAnswerDTO extends DTO {

	@NotNull
	private Long id;

	@NotNull
	private String questionSetCode;

	private Integer repetitionIndex;

	private List<AnswerLine> questionAnswers;

	private List<QuestionSetAnswerDTO> children;

	public QuestionSetAnswerDTO() {
		super();
	}

	public QuestionSetAnswerDTO(Long id, String questionSetCode, Integer repetitionIndex) {
		super();
		this.id = id;
		this.questionSetCode = questionSetCode;
		this.repetitionIndex = repetitionIndex;
		this.questionAnswers = new ArrayList<>();
		this.children = new ArrayList<>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public List<AnswerLine> getQuestionAnswers() {
		return questionAnswers;
	}

	public void setQuestionAnswers(List<AnswerLine> questionAnswers) {
		this.questionAnswers = questionAnswers;
	}

	public List<QuestionSetAnswerDTO> getChildren() {
		return children;
	}

	public void setChildren(List<QuestionSetAnswerDTO> children) {
		this.children = children;
	}

}
