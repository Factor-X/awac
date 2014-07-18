package eu.factorx.awac.dto.awac.post;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.awac.get.QuestionDTO;
import eu.factorx.awac.dto.awac.get.QuestionSetDTO;
import eu.factorx.awac.dto.awac.shared.AnswerLine;
import eu.factorx.awac.dto.validation.annotations.NotNull;

public class QuestionAnswerDTO extends DTO {

	@NotNull
	private QuestionDTO questionDTO;

	@NotNull
	private AnswerLine answerLine;

	public QuestionAnswerDTO() {
		super();
	}


    public QuestionAnswerDTO(AnswerLine answerLine, QuestionDTO questionDTO) {
        this.answerLine = answerLine;
        this.questionDTO = questionDTO;
    }

    public QuestionDTO getQuestionDTO() {
        return questionDTO;
    }

    public void setQuestionDTO(QuestionDTO questionDTO) {
        this.questionDTO = questionDTO;
    }

    public AnswerLine getAnswerLine() {
        return answerLine;
    }

    public void setAnswerLine(AnswerLine answerLine) {
        this.answerLine = answerLine;
    }
}
