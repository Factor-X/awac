package eu.factorx.awac.service;

import java.util.List;

import eu.factorx.awac.models.knowledge.Indicator;
import eu.factorx.awac.models.reporting.BaseActivityData;

public interface IndicatorService extends PersistenceService<Indicator> {

	List<Indicator> findCarbonIndicatorsForSitesByActivity(BaseActivityData activityData);

}
