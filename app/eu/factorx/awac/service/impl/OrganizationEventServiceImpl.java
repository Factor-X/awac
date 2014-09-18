package eu.factorx.awac.service.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import play.db.jpa.JPA;
import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.business.OrganizationEvent;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.service.OrganizationEventService;

@Component
public class OrganizationEventServiceImpl extends AbstractJPAPersistenceServiceImpl<OrganizationEvent> implements OrganizationEventService {

	@Override
	public List<OrganizationEvent> findByOrganizationAndPeriod(Organization organization, Period period) {
		Session session = JPA.em().unwrap(Session.class);

		Criteria criteria = session.createCriteria(OrganizationEvent.class);
		criteria.add(Restrictions.eq(OrganizationEvent.ORGANIZATION_PROPERTY_NAME, organization));
		criteria.add(Restrictions.eq(OrganizationEvent.PERIOD_PROPERTY_NAME, period));
		criteria.addOrder(Order.asc(OrganizationEvent.ID_PROPERTY_NAME));
		criteria.setCacheable(true);

		@SuppressWarnings("unchecked")
		List<OrganizationEvent> result = criteria.list();
		return result;
	}

	@Override
	public List<OrganizationEvent> findByOrganization(Organization organization) {
		Session session = JPA.em().unwrap(Session.class);

		Criteria criteria = session.createCriteria(OrganizationEvent.class);
		criteria.add(Restrictions.eq(OrganizationEvent.ORGANIZATION_PROPERTY_NAME, organization));
		criteria.addOrder(Order.asc(OrganizationEvent.ID_PROPERTY_NAME));
		criteria.setCacheable(true);

		@SuppressWarnings("unchecked")
		List<OrganizationEvent> result = criteria.list();
		return result;
	}


}
