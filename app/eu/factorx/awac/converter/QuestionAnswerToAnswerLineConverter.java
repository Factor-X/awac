package eu.factorx.awac.converter;

import eu.factorx.awac.dto.awac.get.KeyValuePairDTO;
import eu.factorx.awac.dto.awac.post.AnswerLineDTO;
import eu.factorx.awac.dto.myrmex.get.PersonDTO;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.data.answer.AnswerType;
import eu.factorx.awac.models.data.answer.AnswerValue;
import eu.factorx.awac.models.data.answer.QuestionAnswer;
import eu.factorx.awac.models.data.answer.QuestionSetAnswer;
import eu.factorx.awac.models.data.answer.type.*;
import eu.factorx.awac.models.data.file.StoredFile;
import eu.factorx.awac.models.data.question.Question;
import eu.factorx.awac.models.data.question.QuestionSet;
import eu.factorx.awac.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class QuestionAnswerToAnswerLineConverter implements Converter<QuestionAnswer, AnswerLineDTO> {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountToPersonDTOConverter accountToPersonDTOConverter;

    @Override
    public AnswerLineDTO convert(QuestionAnswer questionAnswer) {
        Question question = questionAnswer.getQuestion();
        AnswerType answerType = question.getAnswerType();


        AnswerLineDTO answerLine = new AnswerLineDTO();

        if (questionAnswer.getAnswerValues().size() != 0) {

            AnswerValue answerValue = questionAnswer.getAnswerValues().get(0);

            Object rawAnswerValue = null;
            String unitCode = null;
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
                        unitCode = integerAnswerValue.getUnit().getUnitCode().getKey();
                    }
                    break;
                case DOUBLE:
                    DoubleAnswerValue doubleAnswerValue = (DoubleAnswerValue) answerValue;
                    rawAnswerValue = doubleAnswerValue.getValue();
                    if (doubleAnswerValue.getUnit() != null) {
                        unitCode = doubleAnswerValue.getUnit().getUnitCode().getKey();
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
                    rawAnswerValue = new KeyValuePairDTO<String, Long>(entityAnswerValue.getEntityName(),
                            entityAnswerValue.getEntityId());
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

            answerLine.setValue(rawAnswerValue);
            answerLine.setUnitCode(unitCode);
            answerLine.setComment(answerValue.getComment());
        }

        answerLine.setQuestionKey(question.getCode().getKey());
        answerLine.setMapRepetition(buildRepetitionMap(questionAnswer.getQuestionSetAnswer()));

        Account account = accountService.findByIdentifier(questionAnswer.getTechnicalSegment().getLastUpdateUser());

        if (account != null) {
            PersonDTO lastUpdatePerson = accountToPersonDTOConverter.convert(accountService.findByIdentifier(questionAnswer.getTechnicalSegment().getLastUpdateUser()));

            answerLine.setLastUpdateUser(lastUpdatePerson);
        }

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
