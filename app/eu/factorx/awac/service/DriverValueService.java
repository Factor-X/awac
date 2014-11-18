package eu.factorx.awac.service;

import eu.factorx.awac.models.data.question.DriverValue;
import eu.factorx.awac.models.data.question.type.NumericQuestion;
import eu.factorx.awac.models.knowledge.Period;


public interface DriverValueService extends PersistenceService<DriverValue> {

    public DriverValue findLastedValid(NumericQuestion numericQuestion, Period period) ;
}
