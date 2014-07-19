package eu.factorx.awac.converter;

import eu.factorx.awac.dto.awac.get.QuestionDTO;
import eu.factorx.awac.dto.awac.get.QuestionSetDTO;
import eu.factorx.awac.models.data.question.Question;
import eu.factorx.awac.models.data.question.QuestionSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class QuestionSetToQuestionSetDTOConverter implements Converter<QuestionSet, QuestionSetDTO> {

	@Autowired
	private QuestionToQuestionDTOConverter questionToQuestionDTOConverter;

	@Override
	public QuestionSetDTO convert(QuestionSet questionSet) {
		String code = questionSet.getCode().getKey();
		Boolean repetitionAllowed = questionSet.getRepetitionAllowed();
		List<QuestionDTO> questions = new ArrayList<>();
		for (Question question : questionSet.getQuestions()) {
			questions.add(convertQuestion(question));
		}
		List<QuestionSetDTO> children = new ArrayList<>();
		for (QuestionSet childQuestionSet : questionSet.getChildren()) {
			children.add(convert(childQuestionSet));
		}
		return new QuestionSetDTO(code, repetitionAllowed, children, questions);
	}

	private QuestionDTO convertQuestion(Question question) {
		return questionToQuestionDTOConverter.convert(question);
	}

}
