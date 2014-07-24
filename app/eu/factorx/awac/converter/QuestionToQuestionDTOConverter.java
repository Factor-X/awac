package eu.factorx.awac.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import eu.factorx.awac.dto.awac.get.QuestionDTO;
import eu.factorx.awac.models.data.question.Question;
import eu.factorx.awac.models.data.question.type.DoubleQuestion;
import eu.factorx.awac.models.data.question.type.IntegerQuestion;
import eu.factorx.awac.models.data.question.type.NumericQuestion;
import eu.factorx.awac.models.data.question.type.ValueSelectionQuestion;
import eu.factorx.awac.models.knowledge.UnitCategory;

@Component
public class QuestionToQuestionDTOConverter implements Converter<Question, QuestionDTO> {

	@Override
	public QuestionDTO convert(Question question) {
		Long unitCategoryId = null;
		String codeListName = null;
		Object defaultValue = null;
		if (question instanceof NumericQuestion) {
			UnitCategory unitCategory = ((NumericQuestion) question).getUnitCategory();
			if (unitCategory != null) {
				unitCategoryId = unitCategory.getId();
			}
		} else if (question instanceof ValueSelectionQuestion) {
			codeListName = ((ValueSelectionQuestion) question).getCodeList().name();
		} else if (question instanceof DoubleQuestion) {
			defaultValue = ((DoubleQuestion) question).getDefaultValue();
		} else if (question instanceof IntegerQuestion) {
			Double doubleValue =  ((IntegerQuestion) question).getDefaultValue();
			if (doubleValue != null) {
				defaultValue = doubleValue.intValue();
			}
		}
		return new QuestionDTO(question.getCode().getKey(), question.getAnswerType(), codeListName, unitCategoryId, defaultValue);
	}

}
