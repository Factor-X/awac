package eu.factorx.awac.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import eu.factorx.awac.models.business.Product;
import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.service.ProductService;
import eu.factorx.awac.service.ScopeService;

@Repository
public class ProductServiceImpl extends AbstractJPAPersistenceServiceImpl<Product> implements ProductService {

	@Autowired
	private ScopeService scopeService;

	@Override
	public Product saveOrUpdate(final Product entity) {

		boolean createScope = false;
		if (entity.getId() == null) {
			createScope = true;
		}

		Product product = super.saveOrUpdate(entity);

		if (createScope) {
			Scope scope = new Scope(product);
			scopeService.saveOrUpdate(scope);
		}
		return product;
	}

	@Override
	public void remove (final Product entity) {
		Scope scope = scopeService.findByProduct(entity);
		if (scope != null) {
			scopeService.remove(scope);
		}
		super.remove(entity);
	}

}
