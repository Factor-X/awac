package eu.factorx.awac.service.impl;

import java.util.List;

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
import eu.factorx.awac.models.knowledge.BaseIndicator;
import eu.factorx.awac.service.BaseIndicatorService;

@Component
public class BaseIndicatorServiceImpl extends AbstractJPAPersistenceServiceImpl<BaseIndicator> implements BaseIndicatorService {

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

	@Override
	public void removeAll() {
		int nbDeleted = JPA.em().createNamedQuery(BaseIndicator.REMOVE_ALL).executeUpdate();
		Logger.info("Deleted {} baseIndicators", nbDeleted);
	}

}
