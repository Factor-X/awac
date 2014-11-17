package eu.factorx.awac.service;

import eu.factorx.awac.models.knowledge.Factor;
import eu.factorx.awac.service.impl.FactorSearchParameter;

public interface FactorService extends PersistenceService<Factor> {

	Factor findByParameters(FactorSearchParameter searchParameter);

	void removeAll();

}
