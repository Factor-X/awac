package eu.factorx.awac.service.impl;

import eu.factorx.awac.models.knowledge.Factor;
import eu.factorx.awac.models.knowledge.FactorValue;
import eu.factorx.awac.service.FactorValueService;
import org.springframework.stereotype.Component;
import play.db.jpa.JPA;

import java.util.List;

@Component
public class FactorValueServiceImpl extends AbstractJPAPersistenceServiceImpl<FactorValue> implements FactorValueService {

    @Override
    public FactorValue findByFactorAndYear(Factor factor, int year) {
        List<FactorValue> candidate = JPA.em().createQuery("select fv from FactorValue fv where fv.factor = :f and fv.dateIn <= :year and fv.dateOut is not null and fv.dateOut >= :year ", FactorValue.class)
            .setParameter("f", factor)
            .setParameter("year", year)
            .getResultList();

        if (candidate.size() > 0) {
            return candidate.get(0);
        }

        return JPA.em().createQuery("select fv from FactorValue fv where fv.factor = :f and fv.dateIn <= :year and fv.dateOut is null ", FactorValue.class)
            .setParameter("f", factor)
            .setParameter("year", year)
            .getSingleResult();

    }

}
