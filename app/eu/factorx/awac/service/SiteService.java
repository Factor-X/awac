package eu.factorx.awac.service;

import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.business.Site;

import java.util.List;

public interface SiteService extends PersistenceService<Site> {

	public Site saveOrUpdate(final Site entity);
	public void remove(final Site entity);

    public List<Site> findByOrganization(Organization organization);
}
