package eu.factorx.awac.service;

import eu.factorx.awac.models.knowledge.Factor;
import eu.factorx.awac.models.knowledge.FactorValue;
import org.springframework.stereotype.Component;

public interface FactorValueService extends PersistenceService<FactorValue> {

    FactorValue findByFactorAndYear(Factor factor, int year);
}
