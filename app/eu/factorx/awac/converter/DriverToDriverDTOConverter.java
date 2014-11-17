package eu.factorx.awac.converter;

import eu.factorx.awac.dto.admin.get.DriverDTO;
import eu.factorx.awac.models.data.question.Driver;
import eu.factorx.awac.models.data.question.DriverValue;
import eu.factorx.awac.models.data.question.Question;
import eu.factorx.awac.models.data.question.QuestionSet;
import eu.factorx.awac.models.data.question.type.NumericQuestion;
import eu.factorx.awac.models.forms.Form;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class DriverToDriverDTOConverter implements Converter<Driver, DriverDTO> {


    @Autowired
    private DriverValueDriverValueDTOConverter driverValueDriverValueDTOConverter;

    @Override
    public DriverDTO convert(Driver driver) {
        DriverDTO driverDTO = new DriverDTO();

        driverDTO.setId(driver.getId());

        driverDTO.setName(driver.getName());

        for (DriverValue driverValue : driver.getDriverValueList()) {

            driverDTO.addDriverValue(driverValueDriverValueDTOConverter.convert(driverValue));
        }

        Set<String> awacCalculatorNames = new HashSet<>();

        String exceptedType = "";
        if(driver.getDoubleQuestionList().size()>0 || driver.getPercentageQuestionList().size()>0){
            exceptedType="double";
        }
        else{
            exceptedType="integer";
        }

        for (Question question : driver.getDoubleQuestionList()) {
            awacCalculatorNames.addAll(getCalculator(question.getQuestionSet()));
        }

        for (Question question : driver.getIntegerQuestionList()) {
            awacCalculatorNames.addAll(getCalculator(question.getQuestionSet()));
        }

        for (Question question : driver.getPercentageQuestionList()) {
            awacCalculatorNames.addAll(getCalculator(question.getQuestionSet()));
        }

        driverDTO.setExpectedValueType(exceptedType);
        driverDTO.setCalculatorNames(awacCalculatorNames);

    return driverDTO;
}

    private Set<String> getCalculator(QuestionSet questionSet) {

        if (questionSet.getParent() != null) {
            return getCalculator(questionSet.getParent());
        }
        Set<String> awacCalculators = new HashSet<>();
        for (Form form : questionSet.getFormList()) {
            awacCalculators.add(form.getAwacCalculator().getInterfaceTypeCode().getKey());
        }
        return awacCalculators;
    }

}
