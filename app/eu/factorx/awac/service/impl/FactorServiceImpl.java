package eu.factorx.awac.service.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import play.db.jpa.JPA;
import eu.factorx.awac.models.knowledge.Factor;
import eu.factorx.awac.service.FactorService;

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
		if (resultList.size() > 1) {
			throw new RuntimeException("More than one factor for given parameters");
		}
		if (resultList.size() == 0) {
			return null;
		}

		return resultList.get(0);
	}
}
