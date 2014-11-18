package eu.factorx.awac.service;

import eu.factorx.awac.models.knowledge.Indicator;
import eu.factorx.awac.models.knowledge.ReportIndicator;

public interface IndicatorService extends PersistenceService<Indicator> {
    Indicator findByCode(String key);

}
