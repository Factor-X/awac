package eu.factorx.awac.service.impl;

import eu.factorx.awac.models.knowledge.Factor;
import eu.factorx.awac.models.knowledge.FactorValue;
import eu.factorx.awac.models.knowledge.UnitCategory;
import eu.factorx.awac.service.FactorService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import play.Logger;
import play.db.jpa.JPA;

import java.util.ArrayList;
import java.util.List;

@Component
public class FactorServiceImpl extends AbstractJPAPersistenceServiceImpl<Factor> implements FactorService {

    @Override
    public Factor findByParameters(FactorSearchParameter searchParameter) {
        List<Factor> resultList = JPA.em().createNamedQuery(Factor.FIND_BY_PARAMETERS, Factor.class)
            .setParameter("indicatorCategory", searchParameter.getIndicatorCategory())
            .setParameter("activitySource", searchParameter.getActivitySource())
            .setParameter("activityType", searchParameter.getActivityType())
            .setParameter("unitIn", searchParameter.getUnitIn())
            .setParameter("unitOut", searchParameter.getUnitOut())
            .getResultList();

        if (resultList.isEmpty()) {
            return null;
        }

        Factor factor = resultList.get(0);

        if (resultList.size() > 1) {
            Logger.error("Found more than one factor ({}) matching given parameters: {}", resultList.size(), searchParameter);
            Logger.error("---> Keeping first factor: {}", factor.getKey());
            List<String> skippedFactors = new ArrayList<>();
            for (int i = 1; i < resultList.size(); i++) {
                skippedFactors.add(resultList.get(i).getKey());
            }
            Logger.error("---> Skipping factor(s): {}", StringUtils.join(skippedFactors, ", "));
        }

        return factor;
    }

    @Override
    public Factor findByCode(String key) {
        return JPA.em()
            .createQuery("select e from Factor e where e.key = :key", Factor.class)
            .setParameter("key", key)
            .getSingleResult();
    }

    @Override
    public Factor findByIndicatorCategoryActivityTypeActivitySourceAndUnitCategory(String indicatorCategory, String activityType, String activitySource, UnitCategory unitCategory) {

        return JPA.em()
            .createQuery("select e from Factor e where e.indicatorCategory.key = :ic and e.activityType.key = :t and e.activitySource.key = :s and e.unitIn.category = :uc", Factor.class)
            .setParameter("ic", indicatorCategory)
            .setParameter("t", activityType)
            .setParameter("s", activitySource)
            .setParameter("uc", unitCategory)
            .getSingleResult();

    }

    @Override
    public void removeAll() {
        JPA.em().createNamedQuery(FactorValue.REMOVE_ALL).executeUpdate();
        JPA.em().createNamedQuery(Factor.REMOVE_ALL).executeUpdate();
    }

    @Override
    public Integer getNextKey() {
        return 1 + JPA.em().createQuery("select max(cast(SUBSTRING(e.key, 10) as integer)) from Factor e", Integer.class).getSingleResult();
    }

}
