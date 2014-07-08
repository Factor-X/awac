package eu.factorx.awac.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;

import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.myrmex.get.ProductDTO;
import eu.factorx.awac.dto.myrmex.post.ProductCreateFormDTO;
import eu.factorx.awac.models.business.Product;
import eu.factorx.awac.service.ProductService;

/**
 * Created by root on 6/07/14.
 */
@org.springframework.stereotype.Controller
public class ProductController extends Controller {

    @Autowired
    private Secured secured;

    @Autowired
    private ProductService productService;

    @Autowired
    private ConversionService conversionService;


    @Transactional(readOnly = false)
    @Security.Authenticated(Secured.class)
    public Result createProduct() {

        //2. create the DTO
        ProductCreateFormDTO productCreateFormDTO = DTO.getDTO(request().body().asJson(), ProductCreateFormDTO.class);

        if (productCreateFormDTO == null) {
            throw new RuntimeException("The request cannot be convert");
        }

        //3. create the product
        Product product = new Product(secured.getCurrentUser().getOrganization(), productCreateFormDTO.getName());

        //3.1 add
        productService.saveOrUpdate(product);

        //4. return product DTO
        return ok(conversionService.convert(product, ProductDTO.class));

    }
}
