package eu.factorx.awac.service.impl;

import org.springframework.stereotype.Component;

import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.service.ScopeService;
import play.db.jpa.JPA;

@Component
public class ScopeServiceImpl extends AbstractJPAPersistenceServiceImpl<Scope> implements ScopeService {
    @Override
    public Scope findByType(String scopeType) {
        return (Scope) JPA.em().createQuery("select e from Scope e where e.scopeType = :scopeType").setParameter("scopeType", scopeType).getSingleResult();
    }
}
