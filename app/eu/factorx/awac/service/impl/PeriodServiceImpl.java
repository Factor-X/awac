package eu.factorx.awac.service.impl;


import eu.factorx.awac.models.code.type.PeriodCode;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.service.PeriodService;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;
import play.db.jpa.JPA;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class PeriodServiceImpl extends AbstractJPAPersistenceServiceImpl<Period> implements PeriodService {

    @Override
    public Period findByCode(PeriodCode periodCode) {
        Query query = JPA.em().createQuery("select e from Period e where e.periodCode = :pc");
        query.setParameter("pc", periodCode);
        return (Period) query.getSingleResult();
    }

    @Override
    public Period findLastYear() {
        PeriodCode periodCode = new PeriodCode(String.valueOf(new DateTime().minusYears(1).getYear()));
        return findByCode(periodCode);
    }

    @Override
    public List<Period> findAll() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int currentYear = calendar.get(Calendar.YEAR);
        List<Period> finalList = new ArrayList<>();
        for (Period period : super.findAll()) {
            if (Integer.parseInt(period.getPeriodCode().getKey()) <= currentYear) {
                finalList.add(period);
            }

        }
        return finalList;
    }
}
