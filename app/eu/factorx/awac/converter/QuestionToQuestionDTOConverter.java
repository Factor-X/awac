package eu.factorx.awac.converter;

import eu.factorx.awac.dto.awac.get.UnitDTO;
import eu.factorx.awac.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private UnitService unitService;

    @Override
    public QuestionDTO convert(Question question) {

        Long unitCategoryId = null;
        String codeListName = null;
        Object defaultValue = null;
        UnitDTO defaultUnit = null;

        if (question instanceof NumericQuestion) {
            UnitCategory unitCategory = ((NumericQuestion) question).getUnitCategory();
            if (unitCategory != null) {
                unitCategoryId = unitCategory.getId();
            }

        } else if (question instanceof ValueSelectionQuestion) {
            codeListName = ((ValueSelectionQuestion) question).getCodeList().name();
        }

        //add default value
        if (question instanceof DoubleQuestion) {
            defaultValue = ((DoubleQuestion) question).getDefaultValue();
        } else if (question instanceof IntegerQuestion) {
            Double doubleValue = ((IntegerQuestion) question).getDefaultValue();
            if (doubleValue != null) {
                defaultValue = doubleValue.intValue();
            }
        }

        //add default unit
        if(question instanceof NumericQuestion && ((NumericQuestion)question).getDefaultUnit()!=null){
            defaultUnit = new UnitDTO(((NumericQuestion)question).getDefaultUnit().getId(), ((NumericQuestion)question).getDefaultUnit().getSymbol());
        }

        return new QuestionDTO(question.getCode().getKey(), question.getAnswerType(), codeListName, unitCategoryId, defaultValue,defaultUnit);
    }

}
