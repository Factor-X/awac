package eu.factorx.awac.controllers;

import eu.factorx.awac.common.actions.SecurityAnnotation;
import eu.factorx.awac.dto.awac.get.AccountDTO;
import eu.factorx.awac.dto.awac.get.OrganizationDTO;
import eu.factorx.awac.dto.awac.get.SiteAddUsersResultDTO;
import eu.factorx.awac.dto.awac.get.SiteDTO;
import eu.factorx.awac.dto.awac.post.SiteAddUsersDTO;
import eu.factorx.awac.dto.awac.shared.ReturnDTO;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.association.AccountSiteAssociation;
import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.business.Site;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.service.AccountService;
import eu.factorx.awac.service.AccountSiteAssociationService;
import eu.factorx.awac.service.OrganizationService;
import eu.factorx.awac.service.SiteService;
import eu.factorx.awac.util.BusinessErrorType;
import eu.factorx.awac.util.MyrmexFatalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import play.Logger;
import play.db.jpa.Transactional;
import play.mvc.Result;
import play.mvc.Security;

import java.util.ArrayList;
import java.util.List;

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
    private SiteService siteService;

    @Autowired
    private SecuredController securedController;

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

} // end of class