package eu.factorx.awac.service.impl;

import eu.factorx.awac.models.business.Product;
import eu.factorx.awac.service.ProductService;
import org.springframework.stereotype.Repository;

@Repository
public class ProductServiceImpl extends AbstractJPAPersistenceServiceImpl<Product> implements ProductService {
}
