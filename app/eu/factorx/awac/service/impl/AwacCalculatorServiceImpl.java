package eu.factorx.awac.service.impl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import play.db.jpa.JPA;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.models.forms.AwacCalculator;
import eu.factorx.awac.service.AwacCalculatorService;

@Component
public class AwacCalculatorServiceImpl extends AbstractJPAPersistenceServiceImpl<AwacCalculator> implements AwacCalculatorService {

	@Override
	public AwacCalculator findByCode(InterfaceTypeCode interfaceTypeCode) {
		Session session = JPA.em().unwrap(Session.class);
		Criteria criteria = session.createCriteria(AwacCalculator.class);
		criteria.add(Restrictions.eq(AwacCalculator.INTERFACE_TYPE_CODE_PROPERTY, interfaceTypeCode));
		return (AwacCalculator) criteria.uniqueResult();
	}

}
