package eu.factorx.awac.service;

import eu.factorx.awac.models.business.Site;

public interface SiteService extends PersistenceService<Site> {

	public Site saveOrUpdate(final Site entity);
	public void remove(final Site entity);
}
