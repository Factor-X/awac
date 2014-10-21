package eu.factorx.awac.service;

import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.business.Product;
import eu.factorx.awac.models.business.Scope;

import java.util.Collection;
import java.util.List;

public interface ProductService extends PersistenceService<Product> {

	public void remove(final Product product);
}
