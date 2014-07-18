package eu.factorx.awac.dto.awac.post;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.awac.shared.AnswerLine;
import eu.factorx.awac.dto.validation.annotations.NotNull;

public class QuestionAnswerDTO extends DTO {

	@NotNull
	private String questionKey;

	@NotNull
	private AnswerLine answerLine;

	public QuestionAnswerDTO() {
		super();
	}

	/**
	 * @param questionKey
	 * @param answerLine
	 */
	public QuestionAnswerDTO(String questionKey, AnswerLine answerLine) {
		super();
		this.questionKey = questionKey;
		this.answerLine = answerLine;
	}

	public String getQuestionKey() {
		return questionKey;
	}

	public void setQuestionKey(String questionKey) {
		this.questionKey = questionKey;
	}

	public AnswerLine getAnswerLine() {
		return answerLine;
	}

	public void setAnswerLine(AnswerLine answerLine) {
		this.answerLine = answerLine;
	}

}
