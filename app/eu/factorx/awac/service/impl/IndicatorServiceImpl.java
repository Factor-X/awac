package eu.factorx.awac.service.impl;

import eu.factorx.awac.models.knowledge.Indicator;
import eu.factorx.awac.service.IndicatorService;
import org.springframework.stereotype.Component;
import play.db.jpa.JPA;

@Component
public class IndicatorServiceImpl extends AbstractJPAPersistenceServiceImpl<Indicator> implements IndicatorService {

    @Override
    public Indicator findByCode(String key) {
        return JPA.em().createQuery("select e from Indicator e where e.code = :code", Indicator.class).
            setParameter("code", key).
            getSingleResult();
    }
}