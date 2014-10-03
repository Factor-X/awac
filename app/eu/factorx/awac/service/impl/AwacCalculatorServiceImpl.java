package eu.factorx.awac.service.impl;

import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.models.forms.AwacCalculator;
import eu.factorx.awac.service.AwacCalculatorService;
import org.springframework.stereotype.Component;
import play.db.jpa.JPA;

import javax.persistence.TypedQuery;

@Component
public class AwacCalculatorServiceImpl extends AbstractJPAPersistenceServiceImpl<AwacCalculator> implements AwacCalculatorService {

	@Override
	public AwacCalculator findByCode(InterfaceTypeCode interfaceTypeCode) {

		TypedQuery<AwacCalculator> query = JPA.em().createQuery("" +
				"select e " +
				"from AwacCalculator e " +
				"where e.interfaceTypeCode = :code",
			AwacCalculator.class);
		query.setParameter("code", interfaceTypeCode);
		return query.getSingleResult();

		/*
		Session session = JPA.em().unwrap(Session.class);
		Criteria criteria = session.createCriteria(AwacCalculator.class);
		criteria.add(Restrictions.eq(AwacCalculator.INTERFACE_TYPE_CODE_PROPERTY, interfaceTypeCode));
		return (AwacCalculator) criteria.uniqueResult();
		*/
	}

}
