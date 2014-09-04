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
import play.Logger;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;


@Transactional(readOnly = true)
@org.springframework.stereotype.Controller
public class InvitationController extends AbstractController {


	@Autowired
	private EmailService emailService;

	@Autowired
	private InvitationService invitationService;


	@Security.Authenticated(SecuredController.class)
	@SecurityAnnotation(isAdmin = true, isSystemAdmin = false)
	public Result launchInvitation () {

		// get InvitationDTO from request
		EmailInvitationDTO dto = extractDTOFromRequest(EmailInvitationDTO.class);
		Logger.info("Email Invitation : " + dto.getInvitationEmail());

		// compute key
		String key = KeyGenerator.generateRandomKey(dto.getInvitationEmail().length());
		Logger.info("Email Invitation generated key : " + key);

		// store key and user
		Invitation invitation = new Invitation(dto.getInvitationEmail(),key);
		invitationService.saveOrUpdate(invitation);

		// send email for invitation
		// send mail
		EmailMessage email = new EmailMessage(dto.getInvitationEmail(),"AWAC - invitation", "http://localhost:9000/enterprise#/registration/" + key);
		emailService.send(email);

		//create InvitationResultDTO
		InvitationResultDTO resultDto = new InvitationResultDTO ();
		return ok(resultDto);
	}

	public Result registerInvitation () {

		Logger.info("request body:" + request().body().asJson());
		// get InvitationDTO from request
		RegisterInvitationDTO dto = extractDTOFromRequest(RegisterInvitationDTO.class);
		Logger.info("Registering Invitation : " + dto.getEmail());
		Logger.info("dump" + dto.toString());

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
