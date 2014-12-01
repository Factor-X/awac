package eu.factorx.awac.service.impl;

import eu.factorx.awac.models.business.Site;
import eu.factorx.awac.models.knowledge.Factor;
import eu.factorx.awac.models.reporting.BaseActivityData;
import eu.factorx.awac.models.reporting.BaseActivityResult;
import eu.factorx.awac.service.BaseActivityResultService;
import eu.factorx.awac.service.FactorValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BaseActivityResultServiceImpl implements BaseActivityResultService {

    @Autowired
    private FactorValueService factorValueService;

    @Override
    public Double getValueForYear(BaseActivityResult baseActivityResult) {
        BaseActivityData activityData = baseActivityResult.getActivityData();
        Factor factor = baseActivityResult.getFactor();
        Site site = baseActivityResult.getSite();
        if (activityData == null || factor == null) {
            return null;
        }
        Double activityDataValue = activityData.getValue();
        Double factorValue = factorValueService.findByFactorAndYear(factor, baseActivityResult.getYear()).getValue();
        if (site != null) {
            return (activityDataValue * factorValue * site.getPercentOwned() / 100.0);
        } else {
            return (activityDataValue * factorValue);
        }
    }
}
