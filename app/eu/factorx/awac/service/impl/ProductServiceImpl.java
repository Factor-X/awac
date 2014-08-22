package eu.factorx.awac.service.impl;

import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.business.Product;
import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.service.ProductService;
import eu.factorx.awac.service.ScopeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ProductServiceImpl extends AbstractJPAPersistenceServiceImpl<Product> implements ProductService {

	@Autowired
	private ScopeService scopeService;

	@Override
	public Product saveOrUpdate(final Product entity) {
		Product product = super.saveOrUpdate(entity);

		Scope scope = new Scope(product);
		scopeService.saveOrUpdate(scope);

		return product;
	}

}
