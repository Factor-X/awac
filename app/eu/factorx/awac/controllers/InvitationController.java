package eu.factorx.awac.controllers;

import eu.factorx.awac.common.actions.SecurityAnnotation;
import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.awac.get.InvitationResultDTO;
import eu.factorx.awac.dto.awac.get.LoginResultDTO;
import eu.factorx.awac.dto.awac.post.EmailInvitationDTO;
import eu.factorx.awac.dto.awac.post.EnterpriseAccountCreationDTO;
import eu.factorx.awac.dto.awac.post.MunicipalityAccountCreationDTO;
import eu.factorx.awac.dto.myrmex.get.ExceptionsDTO;
import eu.factorx.awac.dto.myrmex.get.PersonDTO;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.account.Person;
import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.business.Site;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.service.AccountService;
import eu.factorx.awac.service.OrganizationService;
import eu.factorx.awac.service.PersonService;
import eu.factorx.awac.service.SiteService;
import eu.factorx.awac.util.BusinessErrorType;
import eu.factorx.awac.util.MyrmexException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import play.Logger;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;


@Transactional(readOnly = true)
@Security.Authenticated(SecuredController.class)
@SecurityAnnotation(isAdmin = true, isSystemAdmin = false)
@org.springframework.stereotype.Controller
public class InvitationController extends Controller {

	@Autowired
	private PersonService personService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private OrganizationService organizationService;

	@Autowired
	private ConversionService conversionService;

	@Autowired
	private SiteService siteService;

	@Autowired
	private SecuredController securedController;

	@Transactional(readOnly = false)
	public Result launchInvitation () {

		EmailInvitationDTO dto = extractDTOFromRequest(EmailInvitationDTO.class);
		Logger.info("Email Invitation : " + dto.getInvitationEmail());

		//create InvitationResultDTO
		InvitationResultDTO resultDto = new InvitationResultDTO ();
		return ok(resultDto);
	}


	private static <T extends DTO> T extractDTOFromRequest(Class<T> DTOclass) {
		T dto = DTO.getDTO(request().body().asJson(), DTOclass);
		if (dto == null) {
			throw new RuntimeException("The request content cannot be converted to a '" + DTOclass.getName() + "'.");
		}
		return dto;
	}



}
