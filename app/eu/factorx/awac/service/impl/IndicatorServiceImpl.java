package eu.factorx.awac.service.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;

import play.db.jpa.JPA;
import eu.factorx.awac.models.knowledge.Indicator;
import eu.factorx.awac.service.IndicatorService;

@Component
public class IndicatorServiceImpl extends AbstractJPAPersistenceServiceImpl<Indicator> implements IndicatorService {

	@Override
	public List<Indicator> findByParameters(IndicatorSearchParameter searchParameter) {
		TypedQuery<Indicator> query = JPA.em().createNamedQuery(Indicator.FIND_BY_PARAMETERS, Indicator.class)
				.setParameter("type", searchParameter.getType())
				.setParameter("scopeType", searchParameter.getScopeType())
				.setParameter("activityCategory", searchParameter.getActivityCategory())
				.setParameter("activitySubCategory", searchParameter.getActivitySubCategory())
				.setParameter("activityOwnership", searchParameter.getActivityOwnership())
				.setParameter("deleted", searchParameter.getDeleted());
		return query.getResultList();
	}

	@Override
	public List<String> findAllIndicatorNames() {
		List<String> resultList = JPA.em().createNamedQuery(Indicator.FIND_ALL_INDICATOR_NAMES, String.class)
				.getResultList();
		return resultList;
	}

}
