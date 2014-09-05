package eu.factorx.awac.controllers;

import eu.factorx.awac.common.actions.SecurityAnnotation;
import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.awac.get.InvitationResultDTO;
import eu.factorx.awac.dto.awac.get.LoginResultDTO;
import eu.factorx.awac.dto.awac.post.EmailInvitationDTO;
import eu.factorx.awac.dto.awac.post.EnterpriseAccountCreationDTO;
import eu.factorx.awac.dto.awac.post.MunicipalityAccountCreationDTO;
import eu.factorx.awac.dto.awac.post.RegisterInvitationDTO;
import eu.factorx.awac.dto.myrmex.get.ExceptionsDTO;
import eu.factorx.awac.dto.myrmex.get.PersonDTO;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.account.Person;
import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.business.Site;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.models.invitation.Invitation;
import eu.factorx.awac.service.*;
import eu.factorx.awac.util.BusinessErrorType;
import eu.factorx.awac.util.KeyGenerator;
import eu.factorx.awac.util.MyrmexException;
import eu.factorx.awac.util.email.messages.EmailMessage;
import eu.factorx.awac.util.email.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;
import play.Logger;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

@Transactional(readOnly = false)
@org.springframework.stereotype.Controller
public class InvitationController extends AbstractController {


	@Autowired
	private EmailService emailService;

	@Autowired
	private InvitationService invitationService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private PersonService personService;

	@Autowired
	private ConversionService conversionService;




	@Transactional
	@Security.Authenticated(SecuredController.class)
	@SecurityAnnotation(isAdmin = true, isSystemAdmin = false)
	public Result launchInvitation () {

		// get InvitationDTO from request
		EmailInvitationDTO dto = extractDTOFromRequest(EmailInvitationDTO.class);
		Logger.info("Host Organization Invitation Name: " + dto.getOrganization().getName());
		Logger.info("Guest Email Invitation : " + dto.getInvitationEmail());

		Organization org = new Organization (dto.getOrganization().getName());
		org.setId(dto.getOrganization().getId());

		// compute key
		String key = KeyGenerator.generateRandomKey(dto.getInvitationEmail().length());
		Logger.info("Email Invitation generated key : " + key);

		// store key and user
		Invitation invitation = new Invitation(dto.getInvitationEmail(),key,org);
		invitationService.saveOrUpdate(invitation);

		// send email for invitation
		// send mail
		EmailMessage email = new EmailMessage(dto.getInvitationEmail(),"AWAC - invitation from " + dto.getOrganization().getName() + ".",
																	   "http://localhost:9000/enterprise#/registration/" + key);
		emailService.send(email);

		//create InvitationResultDTO
		InvitationResultDTO resultDto = new InvitationResultDTO ();
		return ok(resultDto);
	}

	@Transactional (readOnly = false)
	public Result registerInvitation () {

		Logger.info("request body:" + request().body().asJson());
		// get InvitationDTO from request
		RegisterInvitationDTO dto = extractDTOFromRequest(RegisterInvitationDTO.class);
		Logger.info("Registering Invitation : " + dto.getEmail());
		Logger.info("dump: " + dto.toString());

		// check if invitation exist
		Invitation invitation=invitationService.findByGenkey(dto.getKey());
		if (invitation==null) {
			return unauthorized(new ExceptionsDTO("This invitation is not longer valid. Please verify invitation with host organization or verify your account is already set up."));
		}


		// create person
		Person person = new Person (dto.getLastName(),dto.getFirstName(),dto.getEmail());
		personService.saveOrUpdate(person);

		// create account
		Account account = new Account(invitation.getOrganization(), person, dto.getLogin(), dto.getPassword(), new InterfaceTypeCode(dto.getInterfaceName()));
		accountService.saveOrUpdate(account);

		// delete invitation
		invitationService.remove(invitation);

		// send confirmation email
		EmailMessage email = new EmailMessage(dto.getEmail(),"AWAC - registering confirmation ", "Your user " + dto.getLogin() + " is created");
		emailService.send(email);


		//create InvitationResultDTO
		InvitationResultDTO resultDto = new InvitationResultDTO ();
		return ok(resultDto);
	}

//	@Override
//	private static <T extends DTO> T extractDTOFromRequest(Class<T> DTOclass) {
//		T dto = DTO.getDTO(request().body().asJson(), DTOclass);
//		if (dto == null) {
//			throw new RuntimeException("The request content cannot be converted to a '" + DTOclass.getName() + "'.");
//		}
//		return dto;
//	}


}
