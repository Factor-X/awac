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
import eu.factorx.awac.dto.awac.post.EmailInvitationDTO;
import eu.factorx.awac.dto.myrmex.get.ExceptionsDTO;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.service.OrganizationService;
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
	private ConversionService conversionService;

	/**
	 * get all accounts for specified organization
	 */
	@Transactional
	@Security.Authenticated(SecuredController.class)
	@SecurityAnnotation(isAdmin = true, isSystemAdmin = false)
	public Result retrieveAssociatedAccounts() {


		SiteAddUsersDTO dto = extractDTOFromRequest(SiteAddUsersDTO.class);
		Logger.info("Organization Name: " + dto.getOrganization().getName());

		// get organization
		Organization org = organizationService.findByName(dto.getOrganization().getName());
		// get associated accounts
		List<AccountDTO> associatedAccountDtoList = new ArrayList<AccountDTO>();
		List<Account> associatedAccountList = org.getAccounts();

		//Logger.info("Account List");
		for (Account account : associatedAccountList) {
			AccountDTO adto = conversionService.convert(account, AccountDTO.class);
			associatedAccountDtoList.add(adto);
		}

		// create return DTO
		SiteAddUsersResultDTO resultDto = new SiteAddUsersResultDTO(associatedAccountDtoList);

		// return list of associated
		return ok(resultDto);
	}

	/**
	 * save all associated accounts for specified organization
	 */
	@Transactional
	@Security.Authenticated(SecuredController.class)
	@SecurityAnnotation(isAdmin = true, isSystemAdmin = false)
	public Result saveAssociatedAccounts() {

		SiteAddUsersDTO dto = extractDTOFromRequest(SiteAddUsersDTO.class);
		Logger.info("Organization Name: " + dto.getOrganization().getName());

		// get associated accounts
		List<AccountDTO> associatedAccountDtoList = dto.getSelectedAccounts();

		Logger.info("Account List");
		for (AccountDTO account : associatedAccountDtoList) {
			Logger.info("Account List");
			Logger.info("Account : " + account.getIdentifier());
		}

		// create return DTO
		SiteAddUsersResultDTO resultDto = new SiteAddUsersResultDTO(associatedAccountDtoList);

		// return list of associated
		return ok(resultDto);
	}
}