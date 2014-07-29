package eu.factorx.awac.converter;

import com.amazonaws.services.simpleworkflow.model.Run;
import eu.factorx.awac.dto.awac.get.KeyValuePairDTO;
import eu.factorx.awac.dto.awac.post.AnswerLineDTO;
import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.data.answer.AnswerType;
import eu.factorx.awac.models.data.answer.AnswerValue;
import eu.factorx.awac.models.data.answer.QuestionAnswer;
import eu.factorx.awac.models.data.answer.QuestionSetAnswer;
import eu.factorx.awac.models.data.answer.type.*;
import eu.factorx.awac.models.data.file.StoredFile;
import eu.factorx.awac.models.data.question.Question;
import eu.factorx.awac.models.data.question.QuestionSet;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class QuestionAnswerToAnswerLineConverter implements Converter<QuestionAnswer, AnswerLineDTO> {

	@Override
	public AnswerLineDTO convert(QuestionAnswer questionAnswer) {
		Question question = questionAnswer.getQuestion();
		AnswerType answerType = question.getAnswerType();

        if(questionAnswer.getAnswerValues().size()==0){
            throw new RuntimeException("Error converter : "+questionAnswer);
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
				rawAnswerValue = booleanValue;
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
            case DOCUMENT:

                for(AnswerValue answerValueEl : questionAnswer.getAnswerValues()){

                    if(rawAnswerValue==null){
                        rawAnswerValue = new HashMap<Long,String>();
                    }

                    DocumentAnswerValue documentAnswerValue = (DocumentAnswerValue) answerValueEl;
                    StoredFile storedFile = documentAnswerValue.getStoredFile();
                    ((HashMap<Long,String>)rawAnswerValue).put(storedFile.getId(),storedFile.getOriginalName());
                }

                break;
		}

		AnswerLineDTO answerLine = new AnswerLineDTO();
		answerLine.setValue(rawAnswerValue);
		answerLine.setQuestionKey(question.getCode().getKey());
		answerLine.setUnitId(unitId);
		answerLine.setMapRepetition(getRepetitionMap(questionAnswer));

		return answerLine;
	}

	private Map<String, Integer> getRepetitionMap(QuestionAnswer questionAnswer) {
		Map<String, Integer> repetitionMap = new HashMap<>();
		putRepetitionIndex(repetitionMap, questionAnswer.getQuestionSetAnswer());
		return repetitionMap;
	}

	private void putRepetitionIndex(Map<String, Integer> repetitionMap, QuestionSetAnswer questionSetAnswer) {
		QuestionSet questionSet = questionSetAnswer.getQuestionSet();
		if (questionSet.getRepetitionAllowed()) {
			String code = questionSet.getCode().getKey();
			Integer repetitionIndex = questionSetAnswer.getRepetitionIndex();
			repetitionMap.put(code, repetitionIndex);
		}

		QuestionSetAnswer parent = questionSetAnswer.getParent();
		if (parent != null) {
			putRepetitionIndex(repetitionMap, parent);
		}
	}

}
