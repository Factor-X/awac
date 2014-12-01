package eu.factorx.awac.service;

import eu.factorx.awac.models.code.type.BaseIndicatorCode;
import eu.factorx.awac.models.knowledge.BaseIndicator;
import eu.factorx.awac.models.reporting.BaseActivityResult;

public interface BaseActivityResultService {

    Double getValueForYear(BaseActivityResult baseActivityResult);

}
