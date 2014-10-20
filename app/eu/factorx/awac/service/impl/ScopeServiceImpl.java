package eu.factorx.awac.service.impl;

import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.service.ProductService;
import eu.factorx.awac.service.ScopeService;
import eu.factorx.awac.service.SiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ScopeServiceImpl extends AbstractJPAPersistenceServiceImpl<Scope> implements ScopeService {

    @Autowired
    private SiteService siteService;

    @Autowired
    private ProductService productService;

    public List<Scope> findByOrganization(Organization organization) {

        List<Scope> result = new ArrayList<>();

        if (organization.getInterfaceCode().equals(InterfaceTypeCode.ENTERPRISE)) {
            //load site
            result.addAll(siteService.findByOrganization(organization));
        } else if (organization.getInterfaceCode().equals(InterfaceTypeCode.EVENT)) {
            //load product
            result.addAll(productService.findByOrganization(organization));

        } else if (organization.getInterfaceCode().equals(InterfaceTypeCode.HOUSEHOLD)) {
            //organization
        } else if (organization.getInterfaceCode().equals(InterfaceTypeCode.LITTLE_EMITTER)) {
            //organization
        } else { //municipality
            //organization
        }


        return result;

    }

}
