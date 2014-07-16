package eu.factorx.awac.service;

import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.business.Product;
import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.business.Site;

public interface ScopeService extends PersistenceService<Scope> {

	Scope findBySite(Site site);

	Scope findByOrganization(Organization organization);

	Scope findByProduct(Product product);

}
