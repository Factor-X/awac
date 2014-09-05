package eu.factorx.awac.service.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.stereotype.Component;

import play.Logger;
import play.db.jpa.JPA;
import eu.factorx.awac.models.code.type.IndicatorTypeCode;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.models.code.type.ScopeTypeCode;
import eu.factorx.awac.models.forms.AwacCalculator;
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
	public List<Indicator> findAllCarbonIndicatorsForSites(InterfaceTypeCode interfaceTypeCode) {
		Session session = JPA.em().unwrap(Session.class);
		Criteria criteria = session.createCriteria(Indicator.class);
		
		DetachedCriteria dc = DetachedCriteria.forClass(AwacCalculator.class, "ac");
		dc.createAlias("ac.indicators", "indicator");
		dc.add(Restrictions.eq("ac.interfaceTypeCode", interfaceTypeCode));
		dc.setProjection(Projections.property("indicator.id"));

		criteria.add(Subqueries.propertyIn("id", dc));
		criteria.add(Restrictions.eq("deleted", Boolean.FALSE));
		criteria.add(Restrictions.eq("type", IndicatorTypeCode.CARBON));
		criteria.add(Restrictions.eq("scopeType", ScopeTypeCode.SITE));

		criteria.setCacheable(true);
		@SuppressWarnings("unchecked")
		List<Indicator> result = criteria.list();

		Logger.info("Found {} indicators", result.size());
		return result;
	}

	@Override
	public List<String> findAllIndicatorNames() {
		List<String> resultList = JPA.em().createNamedQuery(Indicator.FIND_ALL_INDICATOR_NAMES, String.class)
				.getResultList();
		return resultList;
	}

	@Override
	public void removeAll() {
		int nbDeleted = JPA.em().createNamedQuery(Indicator.REMOVE_ALL).executeUpdate();
		Logger.info("Deleted {} indicators", nbDeleted);
	}

}
