package eu.factorx.awac.controllers;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.myrmex.get.ExceptionsDTO;
import eu.factorx.awac.dto.myrmex.get.MyselfDTO;
import eu.factorx.awac.dto.myrmex.get.ProductDTO;
import eu.factorx.awac.dto.myrmex.post.ProductCreateFormDTO;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.business.Product;
import eu.factorx.awac.service.AccountService;
import eu.factorx.awac.service.OrganizationService;
import eu.factorx.awac.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

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
    public Result createProduct() {

        //1. control the authentification
        Account account = secured.getCurrentUser();

        if (account == null) {
            return notFound("You are not connected");
            //manage not connected user
        }

        //2. create the DTO
        ProductCreateFormDTO productCreateFormDTO = DTO.getDTO(request().body().asJson(), ProductCreateFormDTO.class);

        if(productCreateFormDTO==null){
            throw new RuntimeException("The request cannot be convert");
        }

        //2.2 control DTO
        if(!productCreateFormDTO.controlForm()){
            return this.forbidden(new ExceptionsDTO("The form is not valid"));
        }

        //3. create the product
        Product product = new Product(account.getOrganization(), productCreateFormDTO.getName());

        //3.1 add
        productService.save(product);

        //4. return product DTO
        return ok(conversionService.convert(product, ProductDTO.class));

    }
}
