package eu.factorx.awac.service.impl;

import eu.factorx.awac.models.business.Organization;
import org.springframework.stereotype.Repository;

import eu.factorx.awac.models.business.Product;
import eu.factorx.awac.service.ProductService;

import java.util.List;

@Repository
public class ProductServiceImpl extends AbstractJPAPersistenceServiceImpl<Product> implements ProductService {

    @Override
    public List<Product> findByOrganization(Organization organization) {




    }
}
