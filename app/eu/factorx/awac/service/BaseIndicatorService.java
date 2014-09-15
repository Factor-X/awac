package eu.factorx.awac.service;

import java.util.List;

import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.models.knowledge.BaseIndicator;
import eu.factorx.awac.service.impl.IndicatorSearchParameter;

public interface BaseIndicatorService extends PersistenceService<BaseIndicator> {

	List<BaseIndicator> findByParameters(IndicatorSearchParameter searchParameter);

	List<BaseIndicator> findAllCarbonIndicatorsForSites(InterfaceTypeCode interfaceType);

	List<String> findAllIndicatorNames();

	void removeAll();


}
