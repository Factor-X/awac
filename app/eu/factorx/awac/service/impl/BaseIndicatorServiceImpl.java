package eu.factorx.awac.service.impl;

import eu.factorx.awac.models.knowledge.BaseIndicator;
import eu.factorx.awac.service.BaseIndicatorService;
import org.springframework.stereotype.Component;
import play.db.jpa.JPA;

@Component
public class BaseIndicatorServiceImpl extends AbstractJPAPersistenceServiceImpl<BaseIndicator> implements BaseIndicatorService {

/*

	@Override
	public List<BaseIndicator> findAllCarbonIndicatorsForSites(InterfaceTypeCode interfaceTypeCode) {

		Session session = JPA.em().unwrap(Session.class);
		Criteria criteria = session.createCriteria(BaseIndicator.class);

		DetachedCriteria dc = DetachedCriteria.forClass(AwacCalculator.class, "ac");
		dc.createAlias("ac.baseIndicators", "baseIndicator");
		dc.add(Restrictions.eq("ac.interfaceTypeCode", interfaceTypeCode));
		dc.setProjection(Projections.property("baseIndicator.id"));

		criteria.add(Subqueries.propertyIn("id", dc));
		criteria.add(Restrictions.eq("deleted", Boolean.FALSE));
		criteria.add(Restrictions.eq("type", IndicatorTypeCode.CARBON));
		criteria.add(Restrictions.eq("scopeType", ScopeTypeCode.SITE));

		criteria.setCacheable(true);
		@SuppressWarnings("unchecked")
		List<BaseIndicator> result = criteria.list();

		Logger.info("Found {} baseIndicators", result.size());
		return result;
	}

*/

	@Override
	public void removeAll() {
		JPA.em().createNamedQuery(BaseIndicator.REMOVE_ALL).executeUpdate();
	}

}
