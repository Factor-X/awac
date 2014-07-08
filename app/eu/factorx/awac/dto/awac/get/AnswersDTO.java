package eu.factorx.awac.dto.awac.get;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.awac.post.AnswersSaveDTO;
import eu.factorx.awac.dto.awac.shared.AnswerLine;

import javax.validation.constraints.NotNull;
import java.util.List;

public class AnswersDTO extends DTO {

    private AnswersSaveDTO answersSaveDTO;

    private Object valueContent;

    public AnswersDTO() {
    }

    public AnswersDTO(AnswersSaveDTO answersSaveDTO, Object valueContent) {
        this.answersSaveDTO = answersSaveDTO;
        this.valueContent = valueContent;
    }

    public AnswersSaveDTO getAnswersSaveDTO() {
        return answersSaveDTO;
    }

    public void setAnswersSaveDTO(AnswersSaveDTO answersSaveDTO) {
        this.answersSaveDTO = answersSaveDTO;
    }

    public Object getValueContent() {
        return valueContent;
    }

    public void setValueContent(Object valueContent) {
        this.valueContent = valueContent;
    }
}
