package eu.factorx.awac.controllers;

import eu.factorx.awac.common.actions.SecurityAnnotation;
import eu.factorx.awac.dto.awac.get.OrganizationDTO;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.service.OrganizationService;
import eu.factorx.awac.util.email.messages.EmailMessage;
import eu.factorx.awac.util.email.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import play.Logger;
import play.db.jpa.Transactional;
import play.mvc.Result;
import play.mvc.Security;

import java.util.List;

//annotate as Spring Component
@Component
@Transactional(readOnly = true)
@Security.Authenticated(SecuredController.class)
@SecurityAnnotation(isAdmin = false, isSystemAdmin = false)
public class OrganizationController extends AbstractController {

	@Autowired
	private OrganizationService organizationService;

	/**
	 * get all accounts for specified organization
	 */
	public Result retrieveAssociatedAccounts() {

		OrganizationDTO dto = extractDTOFromRequest(OrganizationDTO.class);
		Logger.info("Host Organization Invitation Name: " + dto.getName());

		// get organization
		Organization org = organizationService.findByName(dto.getName());
		// get associated accounts
		List<Account> associatedAccountList = org.getAccounts();
		Logger.info("Account List");
		for (Account account : associatedAccountList) {
			Logger.info("Account ID : " + account.getIdentifier());
		}

		// create return DTO

		// return list of associated

		return ok();
	}
}