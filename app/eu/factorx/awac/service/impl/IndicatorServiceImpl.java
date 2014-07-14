package eu.factorx.awac.service.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;

import play.db.jpa.JPA;
import eu.factorx.awac.models.code.type.IndicatorTypeCode;
import eu.factorx.awac.models.code.type.ScopeTypeCode;
import eu.factorx.awac.models.knowledge.Indicator;
import eu.factorx.awac.models.reporting.BaseActivityData;
import eu.factorx.awac.service.IndicatorService;

@Component
public class IndicatorServiceImpl extends AbstractJPAPersistenceServiceImpl<Indicator> implements IndicatorService {

	@Override
	public List<Indicator> findCarbonIndicatorsForSitesByActivity(BaseActivityData activityData) {
		TypedQuery<Indicator> query = JPA.em().createNamedQuery(Indicator.FIND_BY_PARAMETERS, Indicator.class)
					.setParameter("type", IndicatorTypeCode.CARBON)
					.setParameter("scopeType", ScopeTypeCode.SITE)
					.setParameter("activityCategory", activityData.getActivityCategory())
					.setParameter("activitySubCategory", activityData.getActivitySubCategory())
					.setParameter("activityOwnership", activityData.getActivityOwnership());
		return query.getResultList();
	}

	@Override
	public List<String> findAllIndicatorNames() {
		List<String> resultList = JPA.em().createNamedQuery(Indicator.FIND_ALL_INDICATOR_NAMES, String.class)
				.getResultList();
		return resultList;
	}

}
