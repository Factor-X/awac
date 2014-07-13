package eu.factorx.awac.service;

import eu.factorx.awac.models.knowledge.Factor;
import eu.factorx.awac.models.knowledge.Indicator;
import eu.factorx.awac.models.reporting.BaseActivityData;

public interface FactorService extends PersistenceService<Factor> {

	Factor findByIndicatorAndActivity(Indicator indicator, BaseActivityData baseActivityData);

}
