package eu.factorx.awac.converter;

import eu.factorx.awac.dto.myrmex.get.PersonDTO;
import eu.factorx.awac.dto.myrmex.get.ProductDTO;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.business.Product;
import org.springframework.core.convert.converter.Converter;

/**
 * Created by root on 6/07/14.
 */
public class ProductToProductDTOConverter implements Converter<Product , ProductDTO> {

    @Override
    public ProductDTO convert(Product product) {

        ProductDTO productDTO = new ProductDTO();

        productDTO.setName(product.getName());
        
        return productDTO;
    }
}
