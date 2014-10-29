package eu.factorx.awac.service;

import eu.factorx.awac.models.business.Product;

public interface ProductService extends PersistenceService<Product> {

	public void remove(final Product product);
}
