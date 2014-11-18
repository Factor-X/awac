package eu.factorx.awac.service.impl;

import org.springframework.stereotype.Repository;

import eu.factorx.awac.models.business.Product;
import eu.factorx.awac.service.ProductService;

@Repository
public class ProductServiceImpl extends AbstractJPAPersistenceServiceImpl<Product> implements ProductService {

}
