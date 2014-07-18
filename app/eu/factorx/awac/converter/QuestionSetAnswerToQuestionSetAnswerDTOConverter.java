package eu.factorx.awac.converter;

import eu.factorx.awac.dto.awac.post.QuestionAnswerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import eu.factorx.awac.dto.awac.get.QuestionSetAnswerDTO;
import eu.factorx.awac.dto.awac.shared.AnswerLine;
import eu.factorx.awac.models.data.answer.QuestionAnswer;
import eu.factorx.awac.models.data.answer.QuestionSetAnswer;

@Component
public class QuestionSetAnswerToQuestionSetAnswerDTOConverter implements
		Converter<QuestionSetAnswer, QuestionSetAnswerDTO> {

	@Autowired
	QuestionAnswerToAnswerLineConverter questionAnswerToAnswerLineConverter;

    @Autowired
    QuestionToQuestionDTOConverter questionToQuestionDTOConverter;

	@Override
	public QuestionSetAnswerDTO convert(QuestionSetAnswer questionSetAnswer) {
		QuestionSetAnswerDTO questionSetAnswerDTO = new QuestionSetAnswerDTO(questionSetAnswer.getId(),
				questionSetAnswer.getQuestionSet().getCode().getKey(), questionSetAnswer.getRepetitionIndex());
		for (QuestionAnswer questionAnswer : questionSetAnswer.getQuestionAnswers()) {

            QuestionAnswerDTO questionAnswerDTO = new QuestionAnswerDTO();
            questionAnswerDTO.setAnswerLine(convertQuestionAnswer(questionAnswer));
            questionAnswerDTO.setQuestionDTO(questionToQuestionDTOConverter.convert(questionAnswer.getQuestion()));

			questionSetAnswerDTO.getQuestionAnswers().add(questionAnswerDTO);
		}
		for (QuestionSetAnswer childQuestionSetAnswer : questionSetAnswer.getChildren()) {
			questionSetAnswerDTO.getChildren().add(convert(childQuestionSetAnswer));
		}
		return questionSetAnswerDTO;
	}

	private AnswerLine convertQuestionAnswer(QuestionAnswer questionAnswer) {
		return questionAnswerToAnswerLineConverter.convert(questionAnswer);
	}

}
