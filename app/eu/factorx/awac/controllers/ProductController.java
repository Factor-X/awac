package eu.factorx.awac.controllers;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.myrmex.get.ProductDTO;
import eu.factorx.awac.dto.myrmex.post.ProductCreateFormDTO;
import eu.factorx.awac.models.business.Product;
import eu.factorx.awac.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import play.Logger;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

/**
 * Created by root on 6/07/14.
 */
@org.springframework.stereotype.Controller
public class ProductController extends AbstractController {


	@Autowired
	private ProductService productService;

	@Autowired
	private ConversionService conversionService;


	@Transactional(readOnly = false)
	@Security.Authenticated(SecuredController.class)
	public Result createProduct() {




		Logger.debug("USER : " + securedController.getCurrentUser());
		Logger.debug(SecuredController.SESSION_IDENTIFIER_STORE + ":" + session().get(SecuredController.SESSION_IDENTIFIER_STORE));

		//1. create the DTO
		ProductCreateFormDTO productCreateFormDTO = DTO.getDTO(request().body().asJson(), ProductCreateFormDTO.class);

		//2. create the product
		Product product = new Product(securedController.getCurrentUser().getOrganization(), productCreateFormDTO.getName());

		//3.1 add
		productService.saveOrUpdate(product);

		//4. return product DTO
		return ok(conversionService.convert(product, ProductDTO.class));

	}

    public Integer getA(){
        return 5;
    }
}
