package eu.factorx.awac.service.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import play.db.jpa.JPA;
import eu.factorx.awac.models.knowledge.Factor;
import eu.factorx.awac.models.knowledge.Indicator;
import eu.factorx.awac.models.reporting.BaseActivityData;
import eu.factorx.awac.service.FactorService;

@Component
public class FactorServiceImpl extends AbstractJPAPersistenceServiceImpl<Factor> implements FactorService {

	@Override
	public Factor findByIndicatorAndActivity(Indicator indicator, BaseActivityData baseActivityData) {
		List<Factor> resultList = JPA.em().createNamedQuery(Factor.FIND_BY_PARAMETERS, Factor.class)
				.setParameter("indicatorCategory", indicator.getIndicatorCategory())
				.setParameter("activitySource", baseActivityData.getActivitySource())
				.setParameter("activityType", baseActivityData.getActivityType())
				.setParameter("unitIn", baseActivityData.getUnit())
				.setParameter("unitOut", indicator.getUnit())
				.getResultList();
		if (resultList.size() > 1) {
			throw new RuntimeException("More than one factor for given parameters");
		}
		if (resultList.size() == 0) {
			return null;
		}

		return resultList.get(0);
	}
}
