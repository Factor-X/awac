package eu.factorx.awac.service.impl;


import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import play.db.jpa.JPA;
import eu.factorx.awac.models.code.type.PeriodCode;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.service.PeriodService;

import java.text.DateFormat;
import java.util.Date;

@Component
public class PeriodServiceImpl extends AbstractJPAPersistenceServiceImpl<Period> implements PeriodService {

	@Override
	public Period findByCode(PeriodCode periodCode) {
		Session session = JPA.em().unwrap(Session.class);
		Criteria criteria = session.createCriteria(Period.class);
		criteria.add(Restrictions.eq("periodCode", periodCode));
		return (Period) criteria.uniqueResult();
	}

    @Override
    public Period findLastYear(){
        PeriodCode periodCode = new PeriodCode(String.valueOf(new DateTime().minusYears(1).getYear()));
        return findByCode(periodCode);
    }
}
