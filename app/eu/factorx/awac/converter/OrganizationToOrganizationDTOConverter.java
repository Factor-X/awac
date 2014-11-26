package eu.factorx.awac.converter;

import java.util.Collections;
import java.util.List;

import eu.factorx.awac.models.business.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import eu.factorx.awac.dto.awac.get.OrganizationDTO;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.business.Site;

@Component
public class OrganizationToOrganizationDTOConverter implements Converter<Organization, OrganizationDTO> {

    @Autowired
    private SiteToSiteDTOConverter siteToSiteDTOConverter;

    @Autowired
    private AccountToPersonDTOConverter accountToPersonDTOConverter;

	@Autowired
	private ProductToProductDTOConverter productToProductDTOConverter;

    @Override
    public OrganizationDTO convert(Organization org) {

        OrganizationDTO dto = new OrganizationDTO();
        dto.setId(org.getId());
        dto.setName(org.getName());
        dto.setStatisticsAllowed(org.getStatisticsAllowed());
        dto.setInterfaceName(org.getInterfaceCode().getKey());

        //order site list
        List<Site> siteList = org.getSites();

        Collections.sort(siteList);

        for (Site site : siteList) {
            dto.getSites().add(siteToSiteDTOConverter.convert(site));
        }

	    //order product list
	    List<Product> productList = org.getProducts();

	    Collections.sort(productList);

	    for (Product product: productList) {
		    dto.getProducts().add(productToProductDTOConverter.convert(product));
	    }

        for (Account account : org.getAccounts()) {
            dto.getUsers().add(accountToPersonDTOConverter.convert(account));
        }
        return dto;
    }
}
