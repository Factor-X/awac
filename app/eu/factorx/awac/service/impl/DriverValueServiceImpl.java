package eu.factorx.awac.service.impl;

import eu.factorx.awac.models.data.question.DriverValue;
import eu.factorx.awac.models.data.question.type.NumericQuestion;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.service.DriverValueService;
import eu.factorx.awac.util.MyrmexFatalException;
import org.springframework.stereotype.Component;

@Component
public class DriverValueServiceImpl extends AbstractJPAPersistenceServiceImpl<DriverValue> implements DriverValueService {

    @Override
    public DriverValue findLastedValid(NumericQuestion numericQuestion, Period period) {
        DriverValue lastSelected = null;
        if (numericQuestion.getDriver() != null) {
            for (DriverValue driverValue : numericQuestion.getDriver().getDriverValueList()) {
                int periodYear = Integer.parseInt(driverValue.getFromPeriod().getPeriodCode().getKey());
                if (periodYear <= Integer.parseInt(period.getPeriodCode().getKey())) {
                    if (lastSelected == null) {
                        lastSelected = driverValue;
                    } else if (periodYear > Integer.parseInt(lastSelected.getFromPeriod().getPeriodCode().getKey())) {
                        lastSelected = driverValue;
                    }
                }
            }

            if (lastSelected == null) {
                throw new MyrmexFatalException("There is no adapted driverValue for driver "+numericQuestion.getDriver().getId()+" and period "+period.getLabel());
            }
        }
        return lastSelected;
    }

}
