package eu.factorx.awac.service.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import play.Logger;
import play.db.jpa.JPA;
import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.business.OrganizationEvent;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.service.OrganizationEventService;

@Component
public class OrganizationEventServiceImpl extends AbstractJPAPersistenceServiceImpl<OrganizationEvent> implements OrganizationEventService {

    @Override
    public List<OrganizationEvent> findByOrganizationAndPeriod(Organization organization, Period period) {

        return JPA.em().createNamedQuery(OrganizationEvent.FIND_BY_ORGANIZATION_AND_PERIOD, OrganizationEvent.class)
                .setParameter("organization", organization)
                .setParameter("period", period)
                .getResultList();
    }

    @Override
    public List<OrganizationEvent> findByOrganization(Organization organization) {

        return JPA.em().createNamedQuery(OrganizationEvent.FIND_BY_ORGANIZATION, OrganizationEvent.class)
                .setParameter("organization", organization)
                .getResultList();
    }

    public OrganizationEvent findByOrganizationAndPeriodAndName(Organization organization, Period period, String name) {

        List<OrganizationEvent> resultList = JPA.em().createNamedQuery(OrganizationEvent.FIND_BY_ORGANIZATION_AND_PERIOD_AND_NAME, OrganizationEvent.class)
                .setParameter("organization", organization)
                .setParameter("period", period)
                .setParameter("name", name)
                .getResultList();
        if (resultList.size() > 1) {
            String errorMsg = "More than one account with organization = '" + organization + ",period:" + period + ",name:" + name;
            Logger.error(errorMsg);
            throw new RuntimeException(errorMsg);
        }
        if (resultList.size() == 0) {
            return null;
        }
        return resultList.get(0);


    }

}
