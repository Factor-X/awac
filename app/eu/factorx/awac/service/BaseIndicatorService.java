package eu.factorx.awac.service;

import eu.factorx.awac.models.code.type.BaseIndicatorCode;
import eu.factorx.awac.models.knowledge.BaseIndicator;

public interface BaseIndicatorService extends PersistenceService<BaseIndicator> {

	// List<BaseIndicator> findAllCarbonIndicatorsForSites(InterfaceTypeCode interfaceType);

	void removeAll();

	BaseIndicator getByCode(BaseIndicatorCode code);
}
