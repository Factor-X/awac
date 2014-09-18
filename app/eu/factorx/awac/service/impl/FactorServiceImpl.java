package eu.factorx.awac.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import play.Logger;
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

}
