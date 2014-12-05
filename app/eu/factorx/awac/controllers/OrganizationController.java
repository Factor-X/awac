package eu.factorx.awac.controllers;

import java.util.ArrayList;
import java.util.List;

import eu.factorx.awac.dto.awac.get.*;
import eu.factorx.awac.dto.myrmex.get.ProductDTO;
import eu.factorx.awac.models.association.AccountProductAssociation;
import eu.factorx.awac.models.business.Product;
import eu.factorx.awac.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;

import play.Logger;
import play.db.jpa.Transactional;
import play.mvc.Result;
import play.mvc.Security;
import eu.factorx.awac.common.actions.SecurityAnnotation;
import eu.factorx.awac.dto.awac.post.SiteAddUsersDTO;
import eu.factorx.awac.dto.awac.shared.ReturnDTO;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.association.AccountSiteAssociation;
import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.business.Site;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.util.MyrmexFatalException;

//annotate as Spring Component
@Transactional(readOnly = false)
@org.springframework.stereotype.Controller
public class OrganizationController extends AbstractController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private ConversionService conversionService;

    @Autowired
    private AccountSiteAssociationService accountSiteAssociationService;


	@Autowired
	private AccountProductAssociationService accountProductAssociationService;

    @Autowired
    private SiteService siteService;

	@Autowired
	private ProductService productService;

    @Autowired
    private SecuredController securedController;

	@Transactional
	@Security.Authenticated(SecuredController.class)
	@SecurityAnnotation(isAdmin = true, isSystemAdmin = false)
	public  Result loadAssociatedAccountsForProduct() {

		ProductAddUsersDTO dto = extractDTOFromRequest(ProductAddUsersDTO.class);

		// get all users for specified organization
		List<AccountDTO> organizationAccountDtoList = getOrganizationUserList(securedController.getCurrentUser().getOrganization());
		// get all selected/associated users for specified site
		Product product = productService.findById(dto.getProduct().getId());
		//control site
		if(!product.getOrganization().equals(securedController.getCurrentUser().getOrganization())) {
			throw new MyrmexFatalException("this is not your site");
		}
		List<AccountDTO> siteAccountDtoList = getProductSelectedUserList(product);

		// create return DTO
		ProductAddUsersResultDTO resultDto = new ProductAddUsersResultDTO(organizationAccountDtoList, siteAccountDtoList);

		// return list of associated
		return ok(resultDto);
	}

	/**
     * get all accounts for specified organization
     */
    @Transactional
    @Security.Authenticated(SecuredController.class)
    @SecurityAnnotation(isAdmin = true, isSystemAdmin = false)
    public Result loadAssociatedAccounts() {

        SiteAddUsersDTO dto = extractDTOFromRequest(SiteAddUsersDTO.class);

        // get all users for specified organization
        List<AccountDTO> organizationAccountDtoList = getOrganizationUserList(securedController.getCurrentUser().getOrganization());
        // get all selected/associated users for specified site
        Site site = siteService.findById(dto.getSite().getId());
        //control site
        if(!site.getOrganization().equals(securedController.getCurrentUser().getOrganization())) {
            throw new MyrmexFatalException("this is not your site");
        }
        List<AccountDTO> siteAccountDtoList = getSiteSelectedUserList(site);

        // create return DTO
        SiteAddUsersResultDTO resultDto = new SiteAddUsersResultDTO(organizationAccountDtoList, siteAccountDtoList);

        // return list of associated
        return ok(resultDto);
    }

    /**
     * save all site associated accounts for specified site/organisation uple
     */
    @Transactional
    @Security.Authenticated(SecuredController.class)
    @SecurityAnnotation(isAdmin = true, isSystemAdmin = false)
    public Result saveAssociatedAccounts() {

        SiteAddUsersDTO dto = extractDTOFromRequest(SiteAddUsersDTO.class);

        saveSiteSelectedUserList(dto.getSite(), dto.getSelectedAccounts());

        // create return DTO
        Site site = siteService.findById(dto.getSite().getId());
        if(!site.getOrganization().equals(securedController.getCurrentUser().getOrganization())) {
            throw new MyrmexFatalException("this is not your site");
        }
        SiteAddUsersResultDTO resultDto = new SiteAddUsersResultDTO(getOrganizationUserList(securedController.getCurrentUser().getOrganization()), getSiteSelectedUserList(site));

        // return list of associated
        return ok(resultDto);
    }

	@Transactional
	@Security.Authenticated(SecuredController.class)
	@SecurityAnnotation(isAdmin = true, isSystemAdmin = false)
	public Result saveAssociatedAccountsForProduct() {

		ProductAddUsersDTO dto = extractDTOFromRequest(ProductAddUsersDTO.class);

		saveProductSelectedUserList(dto.getProduct(), dto.getSelectedAccounts());

		// create return DTO
		Product product = productService.findById(dto.getProduct().getId());
		if(!product.getOrganization().equals(securedController.getCurrentUser().getOrganization())) {
			throw new MyrmexFatalException("this is not your site");
		}
		ProductAddUsersResultDTO resultDto = new ProductAddUsersResultDTO(getOrganizationUserList(securedController.getCurrentUser().getOrganization()), getProductSelectedUserList(product));

		// return list of associated
		return ok(resultDto);
	}

    @Transactional
    @Security.Authenticated(SecuredController.class)
    @SecurityAnnotation(isAdmin = true, isSystemAdmin = false)
    public Result getMyOrganization() {

        return ok(conversionService.convert(securedController.getCurrentUser().getOrganization(), OrganizationDTO.class));
    }

    @Transactional(readOnly = false)
    @Security.Authenticated(SecuredController.class)
    @SecurityAnnotation(isAdmin = true, isSystemAdmin = false)
    public Result updateOrganization() {

        OrganizationDTO organizationDTO = extractDTOFromRequest(OrganizationDTO.class);

        Organization organization = securedController.getCurrentUser().getOrganization();
        organization.setName(organizationDTO.getName());
        if(!securedController.getCurrentUser().getOrganization().getInterfaceCode().equals(InterfaceTypeCode.VERIFICATION) &&
            !securedController.getCurrentUser().getOrganization().getInterfaceCode().equals(InterfaceTypeCode.ADMIN)) {
            organization.setStatisticsAllowed(organizationDTO.getStatisticsAllowed());
        }
        return ok(new ReturnDTO());
    }

    /**
     * *********** Private methods ************************
     */

    // get all users for specified organization
    private List<AccountDTO> getOrganizationUserList(Organization org) {

        // get associated accounts
        List<AccountDTO> organizationAccountDtoList = new ArrayList<AccountDTO>();
        List<Account> organizationAccountList = org.getAccounts();

        for (Account account : organizationAccountList) {
            AccountDTO adto = conversionService.convert(account, AccountDTO.class);
            organizationAccountDtoList.add(adto);
        }

        return (organizationAccountDtoList);
    } // end of getOrganizationUserList

    // get all selected users for specified site
    private List<AccountDTO> getSiteSelectedUserList(Site site) {
        // get associated accounts
        List<AccountDTO> associatedAccountDtoList = new ArrayList<>();
        List<AccountSiteAssociation> accountSiteAssociationList = accountSiteAssociationService.findBySite(site);

        for (AccountSiteAssociation asa : accountSiteAssociationList) {
            AccountDTO accountDTO = conversionService.convert(asa.getAccount(), AccountDTO.class);
            associatedAccountDtoList.add(accountDTO);
        }

        return (associatedAccountDtoList);
    } // end of getSiteSelectedUserList

	// get all selected users for specified site
	private List<AccountDTO> getProductSelectedUserList(Product product) {
		// get associated accounts
		List<AccountDTO> associatedAccountDtoList = new ArrayList<>();
		List<AccountProductAssociation> accountProductAssociationList = accountProductAssociationService.findByProduct(product);

		for (AccountProductAssociation apa : accountProductAssociationList) {
			AccountDTO accountDTO = conversionService.convert(apa.getAccount(), AccountDTO.class);
			associatedAccountDtoList.add(accountDTO);
		}

		return (associatedAccountDtoList);
	} // end of getSiteSelectedUserList

    // save all selected users for specified site
    private void saveSiteSelectedUserList(SiteDTO siteDto, List<AccountDTO> associatedAccountDtoList) {

        Site site = siteService.findById(siteDto.getId());
        List<AccountSiteAssociation> accountSiteAssociationList = accountSiteAssociationService.findBySite(site);

        if (accountSiteAssociationList != null) {
            Logger.info("remove previous list");
            for (AccountSiteAssociation asa : accountSiteAssociationList) {
                Logger.info("removing asa : " + asa.getId());
                accountSiteAssociationService.remove(asa);
            }
        }

        Logger.info("Account List");
        for (AccountDTO accountDto : associatedAccountDtoList) {
            Account account = accountService.findByIdentifier(accountDto.getIdentifier());
            Logger.info("Account : " + account.getIdentifier());
            AccountSiteAssociation accountSiteAssociation = new AccountSiteAssociation(site, account);
            accountSiteAssociationService.saveOrUpdate(accountSiteAssociation);
        }
    } // end of saveSiteSelectedUserList

	private void saveProductSelectedUserList(ProductDTO productDTO, List<AccountDTO> associatedAccountDtoList) {

		Product product = productService.findById(productDTO.getId());
		List<AccountProductAssociation> accountProductAssociations = accountProductAssociationService.findByProduct(product);

		if (accountProductAssociations!= null) {
			for (AccountProductAssociation apa : accountProductAssociations) {
				accountProductAssociationService.remove(apa);
			}
		}

		for (AccountDTO accountDto : associatedAccountDtoList) {
			Account account = accountService.findByIdentifier(accountDto.getIdentifier());
			AccountProductAssociation accountProductAssociation = new AccountProductAssociation(product, account);
			accountProductAssociationService.saveOrUpdate(accountProductAssociation);
		}
	}

} // end of class