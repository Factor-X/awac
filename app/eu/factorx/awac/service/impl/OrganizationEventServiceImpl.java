package eu.factorx.awac.service.impl;

import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.business.OrganizationEvent;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.service.OrganizationEventService;
import org.springframework.stereotype.Component;
import play.db.jpa.JPA;

import javax.persistence.Query;
import java.util.List;

@Component
public class OrganizationEventServiceImpl extends AbstractJPAPersistenceServiceImpl<OrganizationEvent> implements OrganizationEventService {

	@Override
	public List<OrganizationEvent> findByOrganizationAndPeriod(Organization organization, Period period) {


		Query query = JPA.em().createQuery("" +
			"select oe " +
			"from OrganizationEvent oe " +
			"where oe.organization = :org " +
			"and oe.period = :period " +
			"order by oe.id");
		query.setParameter("org", organization);
		query.setParameter("period", period);

		return (List<OrganizationEvent>) query.getResultList();

		/*
		Session session = JPA.em().unwrap(Session.class);

		Criteria criteria = session.createCriteria(OrganizationEvent.class);
		criteria.add(Restrictions.eq(OrganizationEvent.ORGANIZATION_PROPERTY_NAME, organization));
		criteria.add(Restrictions.eq(OrganizationEvent.PERIOD_PROPERTY_NAME, period));
		criteria.addOrder(Order.asc(OrganizationEvent.ID_PROPERTY_NAME));
		criteria.setCacheable(true);

		@SuppressWarnings("unchecked")
		List<OrganizationEvent> result = criteria.list();
		return result;
		*/
	}

	@Override
	public List<OrganizationEvent> findByOrganization(Organization organization) {


		Query query = JPA.em().createQuery("" +
			"select oe " +
			"from OrganizationEvent oe " +
			"where oe.organization = :org " +
			"order by oe.id");
		query.setParameter("org", organization);

		return (List<OrganizationEvent>) query.getResultList();

/*
		Session session = JPA.em().unwrap(Session.class);

		Criteria criteria = session.createCriteria(OrganizationEvent.class);
		criteria.add(Restrictions.eq(OrganizationEvent.ORGANIZATION_PROPERTY_NAME, organization));
		criteria.addOrder(Order.asc(OrganizationEvent.ID_PROPERTY_NAME));
		criteria.setCacheable(true);

		@SuppressWarnings("unchecked")
		List<OrganizationEvent> result = criteria.list();

		return result;
		*/
	}


}
