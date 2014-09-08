package eu.factorx.awac.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.business.Site;
import eu.factorx.awac.service.ScopeService;
import eu.factorx.awac.service.SiteService;
import play.db.jpa.JPA;

@Component
public class SiteServiceImpl extends AbstractJPAPersistenceServiceImpl<Site> implements SiteService {

	@Autowired
	private ScopeService scopeService;

	@Override
	public Site saveOrUpdate(final Site entity) {

		boolean createScope = false;
		if (entity.getId() == null) {
			createScope = true;
		}

		Site site = super.saveOrUpdate(entity);

		if (createScope) {
			Scope scope = new Scope(site);
			scopeService.saveOrUpdate(scope);
		}

		return site;
	}

	@Override
	public void remove (final Site entity) {
		Scope scope = scopeService.findBySite(entity);
		//play.Logger.info("remove Site:" + entity.toString());
		scopeService.remove(scope);
		super.remove(entity);
	}

}
