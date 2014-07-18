package eu.factorx.awac.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import eu.factorx.awac.dto.awac.get.KeyValuePairDTO;
import eu.factorx.awac.dto.awac.shared.AnswerLine;
import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.data.answer.AnswerType;
import eu.factorx.awac.models.data.answer.AnswerValue;
import eu.factorx.awac.models.data.answer.QuestionAnswer;
import eu.factorx.awac.models.data.answer.type.BooleanAnswerValue;
import eu.factorx.awac.models.data.answer.type.CodeAnswerValue;
import eu.factorx.awac.models.data.answer.type.DoubleAnswerValue;
import eu.factorx.awac.models.data.answer.type.EntityAnswerValue;
import eu.factorx.awac.models.data.answer.type.IntegerAnswerValue;
import eu.factorx.awac.models.data.answer.type.StringAnswerValue;
import eu.factorx.awac.models.data.question.Question;

@Component
public class QuestionAnswerToAnswerLineConverter implements Converter<QuestionAnswer, AnswerLine> {

	@Override
	public AnswerLine convert(QuestionAnswer questionAnswer) {
		Question question = questionAnswer.getQuestion();
		AnswerType answerType = question.getAnswerType();

		// TODO A single QuestionAnswer may be linked to several answer values => not yet implemented
		AnswerValue answerValue = questionAnswer.getAnswerValues().get(0);
		Object rawAnswerValue = null;
		Integer unitId = null;
		switch (answerType) {
		case BOOLEAN:
			rawAnswerValue = ((BooleanAnswerValue) answerValue).getValue();
			break;
		case STRING:
			rawAnswerValue = ((StringAnswerValue) answerValue).getValue();
			break;
		case INTEGER:
			IntegerAnswerValue integerAnswerValue = (IntegerAnswerValue) answerValue;
			rawAnswerValue = integerAnswerValue.getValue();
			if (integerAnswerValue.getUnit() != null) {
				unitId = integerAnswerValue.getUnit().getId().intValue();
			}
			break;
		case DOUBLE:
			DoubleAnswerValue doubleAnswerValue = (DoubleAnswerValue) answerValue;
			rawAnswerValue = doubleAnswerValue.getValue();
			if (doubleAnswerValue.getUnit() != null) {
				unitId = doubleAnswerValue.getUnit().getId().intValue();
			}
			break;
		case VALUE_SELECTION:
			Code value = ((CodeAnswerValue) answerValue).getValue();
			if (value != null)
				rawAnswerValue = value.getKey();
			else
				rawAnswerValue = null;
			break;
		case ENTITY_SELECTION:
			EntityAnswerValue entityAnswerValue = (EntityAnswerValue) answerValue;
			rawAnswerValue = new KeyValuePairDTO<String, Long>(entityAnswerValue.getEntityName(),
					entityAnswerValue.getEntityId());
			break;
		}
		return new AnswerLine(questionAnswer.getId(), question.getCode().getKey(), rawAnswerValue, questionAnswer
				.getQuestionSetAnswer().getRepetitionIndex(), unitId);
	}

}
