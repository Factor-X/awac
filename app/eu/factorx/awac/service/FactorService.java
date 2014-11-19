package eu.factorx.awac.service;

import eu.factorx.awac.models.knowledge.Factor;
import eu.factorx.awac.models.knowledge.UnitCategory;
import eu.factorx.awac.service.impl.FactorSearchParameter;

public interface FactorService extends PersistenceService<Factor> {

	Factor findByParameters(FactorSearchParameter searchParameter);

    Factor findByCode(String key);

    Factor findByIndicatorCategoryActivityTypeActivitySourceAndUnitCategory(String indicatorCategory, String activityType, String activitySource, UnitCategory unitCategory);

	void removeAll();

    Integer getNextKey();
}
