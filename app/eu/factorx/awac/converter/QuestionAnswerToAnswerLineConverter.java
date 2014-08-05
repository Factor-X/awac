package eu.factorx.awac.converter;

import java.util.HashMap;
import java.util.Map;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import eu.factorx.awac.dto.awac.get.KeyValuePairDTO;
import eu.factorx.awac.dto.awac.post.AnswerLineDTO;
import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.data.answer.AnswerType;
import eu.factorx.awac.models.data.answer.AnswerValue;
import eu.factorx.awac.models.data.answer.QuestionAnswer;
import eu.factorx.awac.models.data.answer.QuestionSetAnswer;
import eu.factorx.awac.models.data.answer.type.BooleanAnswerValue;
import eu.factorx.awac.models.data.answer.type.CodeAnswerValue;
import eu.factorx.awac.models.data.answer.type.DocumentAnswerValue;
import eu.factorx.awac.models.data.answer.type.DoubleAnswerValue;
import eu.factorx.awac.models.data.answer.type.EntityAnswerValue;
import eu.factorx.awac.models.data.answer.type.IntegerAnswerValue;
import eu.factorx.awac.models.data.answer.type.StringAnswerValue;
import eu.factorx.awac.models.data.file.StoredFile;
import eu.factorx.awac.models.data.question.Question;
import eu.factorx.awac.models.data.question.QuestionSet;

@Component
public class QuestionAnswerToAnswerLineConverter implements Converter<QuestionAnswer, AnswerLineDTO> {

	@Override
	public AnswerLineDTO convert(QuestionAnswer questionAnswer) {
		Question question = questionAnswer.getQuestion();
		AnswerType answerType = question.getAnswerType();

		if (questionAnswer.getAnswerValues().size() == 0) {
			throw new RuntimeException("Error converter : " + questionAnswer);
		}

		AnswerValue answerValue = questionAnswer.getAnswerValues().get(0);

		Object rawAnswerValue = null;
		Integer unitId = null;
		switch (answerType) {
		case BOOLEAN:
			Boolean booleanValue = ((BooleanAnswerValue) answerValue).getValue();
			if (booleanValue == Boolean.TRUE) {
				rawAnswerValue = "1";
			} else if (booleanValue == Boolean.FALSE) {
				rawAnswerValue = "0";
			}
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
		case PERCENTAGE:
			DoubleAnswerValue doubleAnswerValueForPercent = (DoubleAnswerValue) answerValue;
			rawAnswerValue = doubleAnswerValueForPercent.getValue();
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
			rawAnswerValue = new KeyValuePairDTO<String, Long>(entityAnswerValue.getEntityName(), entityAnswerValue.getEntityId());
			break;
		case DOCUMENT:

			for (AnswerValue answerValueEl : questionAnswer.getAnswerValues()) {

				if (rawAnswerValue == null) {
					rawAnswerValue = new HashMap<Long, String>();
				}

				DocumentAnswerValue documentAnswerValue = (DocumentAnswerValue) answerValueEl;
				StoredFile storedFile = documentAnswerValue.getStoredFile();
				((HashMap<Long, String>) rawAnswerValue).put(storedFile.getId(), storedFile.getOriginalName());
			}

			break;
		}

		AnswerLineDTO answerLine = new AnswerLineDTO();
		answerLine.setValue(rawAnswerValue);
		answerLine.setQuestionKey(question.getCode().getKey());
		answerLine.setUnitId(unitId);
		answerLine.setMapRepetition(buildRepetitionMap(questionAnswer.getQuestionSetAnswer()));
		answerLine.setLastUpdateUser(questionAnswer.getTechnicalSegment().getLastUpdateUser());

		return answerLine;
	}

	public static Map<String, Integer> buildRepetitionMap(QuestionSetAnswer questionSetAnswer) {
		Map<String, Integer> res = new HashMap<>();
		while (questionSetAnswer != null) {
			QuestionSet questionSet = questionSetAnswer.getQuestionSet();
			if (questionSet.getRepetitionAllowed()) {
				res.put(questionSet.getCode().getKey(), questionSetAnswer.getRepetitionIndex());
			}
			questionSetAnswer = questionSetAnswer.getParent();
		}
		return res;
	}

}
