/*
 *
 * Instant Play Framework
 * AWAC
 *                       
 *
 * Copyright (c) 2014 Factor-X.
 * Author Gaston Hollands
 *
 */

package eu.factorx.awac.controllers;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.awac.get.LoginResultDTO;
import eu.factorx.awac.dto.awac.shared.ReturnDTO;
import eu.factorx.awac.dto.myrmex.get.ExceptionsDTO;
import eu.factorx.awac.dto.myrmex.get.MustChangePasswordExceptionsDTO;
import eu.factorx.awac.dto.myrmex.get.TranslatedExceptionDTO;
import eu.factorx.awac.dto.myrmex.post.ConnectionFormDTO;
import eu.factorx.awac.dto.myrmex.post.ForgotPasswordDTO;
import eu.factorx.awac.dto.myrmex.post.TestAuthenticateDTO;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.code.label.CodeLabel;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.service.AccountService;
import eu.factorx.awac.service.CodeLabelService;
import eu.factorx.awac.service.PersonService;
import eu.factorx.awac.service.VelocityGeneratorService;
import eu.factorx.awac.util.BusinessErrorType;
import eu.factorx.awac.util.KeyGenerator;
import eu.factorx.awac.util.email.messages.EmailMessage;
import eu.factorx.awac.util.email.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import play.Logger;
import play.db.jpa.Transactional;
import play.mvc.Result;

import java.util.HashMap;
import java.util.Map;

@org.springframework.stereotype.Controller
public class AuthenticationController extends AbstractController {

	@Autowired
	private PersonService personService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private ConversionService conversionService;

	@Autowired
	private EmailService emailService;

	@Autowired
	private CodeLabelService codeLabelService;

	@Autowired
	private VelocityGeneratorService velocityGeneratorService;

	@Transactional(readOnly = true)
	public Result testAuthentication() {

		TestAuthenticateDTO dto = extractDTOFromRequest(TestAuthenticateDTO.class);

		if (securedController.isAuthenticated()) {

			Account account = securedController.getCurrentUser();

			if (account.getOrganization().getInterfaceCode().equals(new InterfaceTypeCode(dto.getInterfaceName()))) {
				return ok(conversionService.convert(securedController.getCurrentUser(), LoginResultDTO.class));
			}

		}
		return unauthorized();
	}


	// authenticate action cf routes
	@Transactional(readOnly = false)
	public Result authenticate() {

		ConnectionFormDTO connectionFormDTO = DTO.getDTO(request().body().asJson(), ConnectionFormDTO.class);

		if (connectionFormDTO == null) {
			throw new RuntimeException("The request cannot be convert");
		}

		//test if the login exist
		Account account = accountService.findByIdentifier(connectionFormDTO.getLogin());

		//control account
		if (account == null) {
			//use the same message for both login and password error
			// "The couple login / password was not found"
			return unauthorized(new TranslatedExceptionDTO("LOGIN_PASSWORD_PAIR_NOT_FOUND"));
		}

		//test password
		if (!accountService.controlPassword(connectionFormDTO.getPassword(), account)) {
			//use the same message for both login and password error
			return unauthorized(new TranslatedExceptionDTO("LOGIN_PASSWORD_PAIR_NOT_FOUND"));
		}

		//control interface
		InterfaceTypeCode interfaceTypeCode = new InterfaceTypeCode(connectionFormDTO.getInterfaceName());

		if (interfaceTypeCode == null) {
			return unauthorized(new ExceptionsDTO(account.getOrganization().getInterfaceCode().getKey() + " is not a valid interface"));
		} else if (!interfaceTypeCode.equals(account.getOrganization().getInterfaceCode())) {
			//use the same message for both login and password error
			Logger.info(interfaceTypeCode + "");
			Logger.info(account.getOrganization() + "");
			// return unauthorized(new ExceptionsDTO("This account is not for " + interfaceTypeCode.getKey() + " but for " + account.getOrganization().getInterfaceCode().getKey() + ". Please switch calculator and retry."));
			return unauthorized(new TranslatedExceptionDTO("WRONG_INTERFACE_FOR_USER", interfaceTypeCode.getKey(), account.getOrganization().getInterfaceCode().getKey()));
		}

		//control acitf
		if (!account.getActive()) {
			// "Votre compte est actuellement suspendue. Contactez votre administrateur."
			return unauthorized(new TranslatedExceptionDTO("SUSPENDED_ACCOUNT"));
		}

		//control change password
		if (account.getNeedChangePassword()) {

			if (connectionFormDTO.getNewPassword() != null) {
				account.setPassword(connectionFormDTO.getNewPassword());
				account.setNeedChangePassword(false);
				accountService.saveOrUpdate(account);
			} else {
				//use the same message for both login and password error
				return unauthorized(new MustChangePasswordExceptionsDTO());
			}
		}

		//if the login and the password are ok, refresh the session
		securedController.storeIdentifier(account);

		//convert and send it
		LoginResultDTO dto = conversionService.convert(account, LoginResultDTO.class);

		return ok(dto);

	} // end of authenticate action

	// logout action cf routes
	@Transactional(readOnly = true)
	public Result logout() {

		session().clear();

		return ok(new ReturnDTO());
	}


	@Transactional(readOnly = false)
	public Result forgotPassword() {

		ForgotPasswordDTO dto = extractDTOFromRequest(ForgotPasswordDTO.class);

		InterfaceTypeCode interfaceTypeCode = new InterfaceTypeCode(dto.getInterfaceName());

		Account account = accountService.findByIdentifier(dto.getIdentifier());

		if (account == null) {
			return notFound(new ExceptionsDTO(BusinessErrorType.INVALID_IDENTIFIER));
		}

		//generate password
		String password = KeyGenerator.generateRandomPassword(10);


		// retrieve traductions
		HashMap<String, CodeLabel> traductions = codeLabelService.findCodeLabelsByList(CodeList.TRANSLATIONS_EMAIL_MESSAGE);
		String title = traductions.get("RESET_PASSWORD_EMAIL_SUBJECT").getLabel(account.getPerson().getDefaultLanguage());

		Map values = new HashMap<String, Object>();
		values.put("title", title);
		values.put("password", password);


		String velocityContent = velocityGeneratorService.generate(velocityGeneratorService.getTemplateNameByMethodName(), values);

		// send email for invitation
		EmailMessage email = new EmailMessage(account.getPerson().getEmail(), title, velocityContent);
		emailService.send(email);

		//save new password
		account.setPassword(password);
		account.setNeedChangePassword(true);
		accountService.saveOrUpdate(account);

		return ok(new ReturnDTO());
	}

} // end of controller class

