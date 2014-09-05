package eu.factorx.awac.service;

import java.util.List;

import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.models.knowledge.Indicator;
import eu.factorx.awac.service.impl.IndicatorSearchParameter;

public interface IndicatorService extends PersistenceService<Indicator> {

	List<Indicator> findByParameters(IndicatorSearchParameter searchParameter);

	List<Indicator> findAllCarbonIndicatorsForSites(InterfaceTypeCode interfaceType);

	List<String> findAllIndicatorNames();

	void removeAll();


}
