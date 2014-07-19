package eu.factorx.awac.service;

import eu.factorx.awac.models.knowledge.Indicator;
import eu.factorx.awac.models.reporting.BaseActivityData;

import java.util.List;

public interface IndicatorService extends PersistenceService<Indicator> {

	List<Indicator> findCarbonIndicatorsForSitesByActivity(BaseActivityData activityData);

	List<String> findAllIndicatorNames();

}
