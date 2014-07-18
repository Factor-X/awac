package eu.factorx.awac.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import eu.factorx.awac.dto.awac.get.QuestionDTO;
import eu.factorx.awac.dto.awac.get.QuestionSetDTO;
import eu.factorx.awac.models.data.question.Question;
import eu.factorx.awac.models.data.question.QuestionSet;

@Component
public class QuestionSetToQuestionSetDTOConverter implements Converter<QuestionSet, QuestionSetDTO> {

	@Autowired
	private QuestionToQuestionDTOConverter questionToQuestionDTOConverter;

	@Override
	public QuestionSetDTO convert(QuestionSet questionSet) {
		QuestionSetDTO questionSetDTO = new QuestionSetDTO(questionSet.getCode().getKey(),
				questionSet.getRepetitionAllowed());
		for (Question question : questionSet.getQuestions()) {
			questionSetDTO.getQuestions().add(convertQuestion(question));
		}
		for (QuestionSet childQuestionSet : questionSet.getChildren()) {
			questionSetDTO.getChildren().add(convert(childQuestionSet));
		}
		return questionSetDTO;
	}

	private QuestionDTO convertQuestion(Question question) {
		return questionToQuestionDTOConverter.convert(question);
	}

}
