package eu.factorx.awac.service;

import java.util.List;

import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.business.OrganizationEvent;
import eu.factorx.awac.models.knowledge.Period;

public interface OrganizationEventService extends PersistenceService<OrganizationEvent> {

	public List<OrganizationEvent> findByOrganizationAndPeriod(Organization organization, Period period);

	public List<OrganizationEvent> findByOrganization(Organization organization);

}
