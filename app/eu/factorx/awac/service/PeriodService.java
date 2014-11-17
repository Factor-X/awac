package eu.factorx.awac.service;

import eu.factorx.awac.models.code.type.PeriodCode;
import eu.factorx.awac.models.knowledge.Period;

public interface PeriodService extends PersistenceService<Period> {
	
	Period findByCode(PeriodCode periodCode);

    Period findLastYear();

}
