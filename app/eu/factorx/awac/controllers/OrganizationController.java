package eu.factorx.awac.controllers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.factorx.awac.common.actions.SecurityAnnotation;
import eu.factorx.awac.dto.awac.dto.SiteAddUsersDTO;
import eu.factorx.awac.dto.awac.dto.SiteAddUsersResultDTO;
import eu.factorx.awac.dto.awac.get.AccountDTO;
import eu.factorx.awac.dto.awac.get.LoginResultDTO;
import eu.factorx.awac.dto.awac.get.OrganizationDTO;
import eu.factorx.awac.dto.awac.get.SiteDTO;
import eu.factorx.awac.dto.awac.post.EmailInvitationDTO;
import eu.factorx.awac.dto.myrmex.get.ExceptionsDTO;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.association.AccountSiteAssociation;
import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.business.Site;
import eu.factorx.awac.service.AccountService;
import eu.factorx.awac.service.AccountSiteAssociationService;
import eu.factorx.awac.service.OrganizationService;
import eu.factorx.awac.service.SiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;
import play.Logger;
import play.db.jpa.Transactional;
import play.mvc.Result;
import play.mvc.Security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//annotate as Spring Component
@Transactional(readOnly = false)
@org.springframework.stereotype.Controller
public class OrganizationController extends AbstractController {

	@Autowired
	private OrganizationService organizationService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private ConversionService conversionService;

	@Autowired
	private AccountSiteAssociationService accountSiteAssociationService;

	@Autowired
	private SiteService siteService;

	/**
	 * get all accounts for specified organization
	 */
	@Transactional
	@Security.Authenticated(SecuredController.class)
	@SecurityAnnotation(isAdmin = true, isSystemAdmin = false)
	public Result loadAssociatedAccounts() {


		SiteAddUsersDTO dto = extractDTOFromRequest(SiteAddUsersDTO.class);
		Logger.info("Load Site Accounts Association - Organization Name: " + dto.getOrganization().getName() +  " Site name:" + dto.getSite().getName());

		List<AccountDTO> organizationAccountDtoList = new ArrayList<AccountDTO>();
		List<AccountDTO> siteAccountDtoList = new ArrayList<AccountDTO>();

		// get all users for specified organization
		organizationAccountDtoList = getOrganizationUserList(dto.getOrganization());
		// get all selected/associated users for specified site
		siteAccountDtoList = getSiteSelectedUserList(dto.getSite());

		// create return DTO
		SiteAddUsersResultDTO resultDto = new SiteAddUsersResultDTO(organizationAccountDtoList,siteAccountDtoList);

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
		Logger.info("Save Site Accounts Association - Organization Name: " + dto.getOrganization().getName() +  " Site name:" + dto.getSite().getName());

		saveSiteSelectedUserList(dto.getSite(),dto.getSelectedAccounts());

		// create return DTO
		SiteAddUsersResultDTO resultDto = new SiteAddUsersResultDTO(getOrganizationUserList(dto.getOrganization()),getSiteSelectedUserList(dto.getSite()));

		// return list of associated
		return ok(resultDto);
	}

	/************** Private methods *************************/

	// get all users for specified organization
	private List<AccountDTO> getOrganizationUserList (OrganizationDTO dto) {

		// get organization
		Organization org = organizationService.findByName(dto.getName());
		// get associated accounts
		List<AccountDTO> organizationAccountDtoList = new ArrayList<AccountDTO>();
		List<Account> organizationAccountList = org.getAccounts();

		if (organizationAccountDtoList!=null) {
			//Logger.info("Account List");
			Logger.info("add into list");
			for (Account account : organizationAccountList) {
				Logger.info("add account : " + account.getId());
				AccountDTO adto = conversionService.convert(account, AccountDTO.class);
				organizationAccountDtoList.add(adto);
			}
		}

		for (AccountDTO accountDTO : organizationAccountDtoList) {
			Logger.info("Organization account: " + accountDTO.getIdentifier());
		}


		return (organizationAccountDtoList);
	} // end of getOrganizationUserList

	// get all selected users for specified site
	private List<AccountDTO> getSiteSelectedUserList (SiteDTO dto) {
		// get associated accounts
		List<AccountDTO> associatedAccountDtoList = new ArrayList<>();
		Site site = siteService.findById(dto.getId());
		List<AccountSiteAssociation> accountSiteAssociationList = accountSiteAssociationService.findBySite(site);

		if (accountSiteAssociationList!=null) {
			Logger.info("add into list");
			for (AccountSiteAssociation asa : accountSiteAssociationList) {
				Logger.info("add asa : " + asa.getId());
				AccountDTO accountDTO = conversionService.convert(asa.getAccount(),AccountDTO.class);
				associatedAccountDtoList.add(accountDTO);
			}
		}

		for (AccountDTO accountDTO : associatedAccountDtoList) {
			Logger.info("Associated account: " + accountDTO.getIdentifier());
		}

		return (associatedAccountDtoList);
	} // end of getSiteSelectedUserList

	// save all selected users for specified site
	private void saveSiteSelectedUserList (SiteDTO siteDto, List<AccountDTO> associatedAccountDtoList) {

		Site site = siteService.findById(siteDto.getId());
		List<AccountSiteAssociation> accountSiteAssociationList = accountSiteAssociationService.findBySite(site);

		if (accountSiteAssociationList!=null) {
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
			AccountSiteAssociation accountSiteAssociation = new AccountSiteAssociation(site,account);
			accountSiteAssociationService.saveOrUpdate(accountSiteAssociation);
		}
	} // end of saveSiteSelectedUserList

} // end of class