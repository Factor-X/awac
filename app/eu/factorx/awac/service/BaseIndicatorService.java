package eu.factorx.awac.service;

import java.util.List;

import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.models.knowledge.BaseIndicator;

public interface BaseIndicatorService extends PersistenceService<BaseIndicator> {

	List<BaseIndicator> findAllCarbonIndicatorsForSites(InterfaceTypeCode interfaceType);

	void removeAll();

}
