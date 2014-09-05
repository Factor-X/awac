package eu.factorx.awac.service;

import eu.factorx.awac.models.business.Organization;

public interface OrganizationService extends PersistenceService<Organization> {

	public Organization findByName(String name);

	public void remove(final Organization organization);

	public Organization saveOrUpdate(final Organization entity);
}
