package eu.factorx.awac.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.business.Site;
import eu.factorx.awac.service.ScopeService;
import eu.factorx.awac.service.SiteService;

@Component
public class SiteServiceImpl extends AbstractJPAPersistenceServiceImpl<Site> implements SiteService {

	@Autowired
	private ScopeService scopeService;

	@Override
	public Site saveOrUpdate(final Site entity) {
		Site site = super.saveOrUpdate(entity);

		Scope scope = new Scope(site);
		scopeService.saveOrUpdate(scope);

		return site;
	}

}
