package eu.factorx.awac.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import eu.factorx.awac.dto.awac.get.QuestionDTO;
import eu.factorx.awac.models.data.question.Question;
import eu.factorx.awac.models.data.question.type.NumericQuestion;
import eu.factorx.awac.models.data.question.type.ValueSelectionQuestion;
import eu.factorx.awac.models.knowledge.UnitCategory;

@Component
public class QuestionToQuestionDTOConverter implements Converter<Question, QuestionDTO> {

	@Override
	public QuestionDTO convert(Question question) {
		Long unitCategoryId = null;
		String codeListName = null;
		if (question instanceof NumericQuestion) {
			UnitCategory unitCategory = ((NumericQuestion) question).getUnitCategory();
			if (unitCategory != null) {
				unitCategoryId = unitCategory.getId();
			}
		} else if (question instanceof ValueSelectionQuestion) {
			codeListName = ((ValueSelectionQuestion) question).getCodeList().name();
		}
		return new QuestionDTO(question.getCode().getKey(), question.getAnswerType(), codeListName, unitCategoryId);
	}

}
