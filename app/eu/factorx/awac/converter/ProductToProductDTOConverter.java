package eu.factorx.awac.converter;

import eu.factorx.awac.models.association.AccountProductAssociation;
import eu.factorx.awac.models.association.AccountSiteAssociation;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.service.AccountProductAssociationService;
import eu.factorx.awac.service.AccountSiteAssociationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import eu.factorx.awac.dto.myrmex.get.ProductDTO;
import eu.factorx.awac.models.business.Product;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by root on 6/07/14.
 */
@Component
public class ProductToProductDTOConverter implements Converter<Product, ProductDTO> {


    @Autowired
    private PeriodToPeriodDTOConverter periodToPeriodDTOConverter;

    @Autowired
    private AccountProductAssociationService accountProductAssociationService;

    @Autowired
    private AccountToPersonDTOConverter accountToPersonDTOConverter;

	@Override
	public ProductDTO convert(Product product) {

		ProductDTO dto = new ProductDTO();

		dto.setId(product.getId());
		dto.setDescription(product.getDescription());
        dto.setName(product.getName());

        if (product.getListPeriodAvailable() != null) {
            for (Period period : product.getListPeriodAvailable()) {
                dto.addPeriodAvailable(periodToPeriodDTOConverter.convert(period));
            }
        }

        List<AccountProductAssociation> associations = accountProductAssociationService.findByProduct(product);

        for (AccountProductAssociation accountProductAssociation : associations) {
            dto.addPerson(accountToPersonDTOConverter.convert(accountProductAssociation.getAccount()));
        }

		return dto;
	}
}
