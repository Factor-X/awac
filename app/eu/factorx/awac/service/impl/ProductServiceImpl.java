package eu.factorx.awac.service.impl;

import eu.factorx.awac.models.business.Product;
import eu.factorx.awac.service.ProductService;
import org.springframework.stereotype.Repository;

/**
 * Created by root on 6/07/14.
 */
@Repository
public class ProductServiceImpl extends AbstractJPAPersistenceServiceImpl<Product> implements ProductService{
}
