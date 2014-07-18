package eu.factorx.awac.converter;

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

	@Override
	public QuestionSetAnswerDTO convert(QuestionSetAnswer questionSetAnswer) {
		QuestionSetAnswerDTO questionSetAnswerDTO = new QuestionSetAnswerDTO(questionSetAnswer.getId(),
				questionSetAnswer.getQuestionSet().getCode().getKey(), questionSetAnswer.getRepetitionIndex());
		for (QuestionAnswer questionAnswer : questionSetAnswer.getQuestionAnswers()) {
			questionSetAnswerDTO.getQuestionAnswers().add(convertQuestionAnswer(questionAnswer));
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
