package eu.factorx.awac.service.impl;

import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.forms.AwacCalculatorInstance;
import org.springframework.stereotype.Component;

import eu.factorx.awac.models.business.Site;
import eu.factorx.awac.service.SiteService;
import play.db.jpa.JPA;

import java.util.List;

@Component
public class SiteServiceImpl extends AbstractJPAPersistenceServiceImpl<Site> implements SiteService {

    @Override
    public List<Site> findByOrganization(Organization organization) {
        return JPA.em().createNamedQuery(Site.FIND_BY_ORGANIZATION, Site.class)
                .setParameter("organization", organization)
                .getResultList();

    }
}
