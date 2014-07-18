package eu.factorx.awac.dto.awac.get;

import java.util.ArrayList;
import java.util.List;

public class QuestionSetDTO {

	private String code;

	private Boolean repetitionAllowed;

	private List<QuestionDTO> questions = new ArrayList<>();

	private List<QuestionSetDTO> children = new ArrayList<>();

	public QuestionSetDTO() {
		super();
	}

	public QuestionSetDTO(String code, Boolean repetitionAllowed) {
		super();
		this.code = code;
		this.repetitionAllowed = repetitionAllowed;
		this.questions = new ArrayList<>();
		this.children = new ArrayList<>();
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

	public List<QuestionDTO> getQuestions() {
		return questions;
	}

	public void setQuestions(List<QuestionDTO> questions) {
		this.questions = questions;
	}

	public List<QuestionSetDTO> getChildren() {
		return children;
	}

	public void setChildren(List<QuestionSetDTO> children) {
		this.children = children;
	}
}
