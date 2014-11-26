package eu.factorx.awac.controllers;

import eu.factorx.awac.common.actions.SecurityAnnotation;
import eu.factorx.awac.dto.awac.post.AssignPeriodToProductDTO;
import eu.factorx.awac.dto.awac.post.ListPeriodsDTO;
import eu.factorx.awac.models.association.AccountProductAssociation;
import eu.factorx.awac.models.code.type.PeriodCode;
import eu.factorx.awac.models.code.type.ScopeTypeCode;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.service.*;
import eu.factorx.awac.util.BusinessErrorType;
import eu.factorx.awac.util.MyrmexRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;

import play.Logger;
import play.db.jpa.Transactional;
import play.mvc.Result;
import play.mvc.Security;
import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.myrmex.get.ProductDTO;
import eu.factorx.awac.dto.myrmex.post.ProductCreateFormDTO;
import eu.factorx.awac.models.business.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 6/07/14.
 */
@org.springframework.stereotype.Controller
public class ProductController extends AbstractController {

	@Autowired
	private ProductService productService;

	@Autowired
	private ConversionService conversionService;

	@Autowired
	private PeriodService periodService;

	@Autowired
	private AccountProductAssociationService accountProductAssociationService;


	@Transactional(readOnly = false)
	@Security.Authenticated(SecuredController.class)
	@SecurityAnnotation(isAdmin = true, isSystemAdmin = false)
	public Result edit(){

		//control
		if(!securedController.getCurrentUser().getOrganization().getInterfaceCode().getScopeTypeCode().equals(ScopeTypeCode.PRODUCT)){
			throw new MyrmexRuntimeException(BusinessErrorType.WRONG_RIGHT);
		}

		ProductDTO dto = extractDTOFromRequest(ProductDTO.class);

		//control id
		if(dto.getId()== null){
			throw new MyrmexRuntimeException(BusinessErrorType.DATA_NOT_FOUND);
		}

		//load product
		Product product = productService.findById(dto.getId());

		// if the product is null
		if(product == null){
			throw new MyrmexRuntimeException(BusinessErrorType.DATA_NOT_FOUND);
		}

		//edit
		product.setName(dto.getName());
		product.setDescription(dto.getDescription());

		productService.saveOrUpdate(product);

		return ok(conversionService.convert(product,ProductDTO.class));
	}

	@Transactional(readOnly = false)
	@Security.Authenticated(SecuredController.class)
	@SecurityAnnotation(isAdmin = true, isSystemAdmin = false)
	public Result create(){

		//control
		if(!securedController.getCurrentUser().getOrganization().getInterfaceCode().getScopeTypeCode().equals(ScopeTypeCode.PRODUCT)){
			throw new MyrmexRuntimeException(BusinessErrorType.WRONG_RIGHT);
		}

		ProductDTO dto = extractDTOFromRequest(ProductDTO.class);

		//create
		Product product= new Product(securedController.getCurrentUser().getOrganization(), dto.getName());

		product.setDescription(dto.getDescription());

		//assign the last year
		Period lastYear = periodService.findLastYear();
		List<Period> periodList = new ArrayList<>();
		periodList.add(lastYear);
		product.setListPeriodAvailable(periodList);

		productService.saveOrUpdate(product);

		//assign the creator to the product
		AccountProductAssociation accountProductAssociation= new AccountProductAssociation(product,securedController.getCurrentUser());
		accountProductAssociationService.saveOrUpdate(accountProductAssociation);

		// return the new product because some new information are added, like id and scope
		return ok(conversionService.convert(product,ProductDTO.class));
	}

	@Transactional(readOnly = false)
	@Security.Authenticated(SecuredController.class)
	@SecurityAnnotation(isAdmin = true, isSystemAdmin = false)
	public Result assignPeriodToProduct(){

		//control
		if(!securedController.getCurrentUser().getOrganization().getInterfaceCode().getScopeTypeCode().equals(ScopeTypeCode.PRODUCT)){
			throw new MyrmexRuntimeException(BusinessErrorType.WRONG_RIGHT);
		}

		AssignPeriodToProductDTO dto = extractDTOFromRequest(AssignPeriodToProductDTO.class);


		//load period
		Period period = periodService.findByCode(new PeriodCode(dto.getPeriodKeyCode()));

		if(period==null){
			throw new MyrmexRuntimeException("");
		}

		//load product
		Product product = productService.findById(dto.getProductId());

		if(product == null || !product.getOrganization().equals(securedController.getCurrentUser().getOrganization())){
			throw new MyrmexRuntimeException("");
			//TODO error
		}

		//assign period to product
		boolean toAdd = dto.getAssign();

		//control is the period is already into product
		if(product.getListPeriodAvailable()!=null){
			for(Period periodToTest : product.getListPeriodAvailable()){

				if(periodToTest.equals(period)){
					//founded and to assign => useless to add
					if(dto.getAssign()){
						toAdd = false;
					}
					//founded and to remove => remove
					else{
						product.getListPeriodAvailable().remove(periodToTest);
					}
					break;
				}
			}
		}

		//add ig it's needed
		if(toAdd){
			product.getListPeriodAvailable().add(period);
		}

		//save
		productService.saveOrUpdate(product);

		ListPeriodsDTO listPeriodsDTO = conversionService.convert(product, ListPeriodsDTO.class);

		return ok(listPeriodsDTO);
	}

	@Transactional(readOnly = false)
	@Security.Authenticated(SecuredController.class)
	@SecurityAnnotation(isAdmin = false, isSystemAdmin = false)
	public Result getProduct(long productId){

		//control
		if(!securedController.getCurrentUser().getOrganization().getInterfaceCode().getScopeTypeCode().equals(ScopeTypeCode.PRODUCT)){
			throw new MyrmexRuntimeException(BusinessErrorType.WRONG_RIGHT);
		}

		//load the product
		Product product = productService.findById(productId);

		if(product == null){
			throw new MyrmexRuntimeException("");
			//TODO error
		}

		//test owner
		if(!securedController.getCurrentUser().getOrganization().equals(product.getOrganization())){
			throw new MyrmexRuntimeException("");
			//TODO error
		}

		//convert
		return  ok(conversionService.convert(product,ProductDTO.class));
	}
}
