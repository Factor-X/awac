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
import eu.factorx.awac.dto.myrmex.post.ConnectionFormDTO;
import eu.factorx.awac.dto.myrmex.post.ForgotPasswordDTO;
import eu.factorx.awac.dto.myrmex.post.TestAuthenticateDTO;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.account.Person;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.service.AccountService;
import eu.factorx.awac.service.PersonService;
import eu.factorx.awac.util.BusinessErrorType;
import eu.factorx.awac.util.KeyGenerator;
import eu.factorx.awac.util.email.messages.EmailMessage;
import eu.factorx.awac.util.email.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import play.Logger;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

@org.springframework.stereotype.Controller
public class AuthenticationController extends Controller {

	@Autowired
	private PersonService personService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private ConversionService conversionService;

	@Autowired
	private SecuredController securedController;

	@Autowired
	private EmailService emailService;

	@Transactional(readOnly = true)
	public Result testAuthentication() {

		TestAuthenticateDTO dto = extractDTOFromRequest(TestAuthenticateDTO.class);

		if (securedController.isAuthenticated()) {

			Account account = securedController.getCurrentUser();

			if (account.getInterfaceCode().equals(new InterfaceTypeCode(dto.getInterfaceName()))) {
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
			return unauthorized(new ExceptionsDTO("The couple login / password was not found"));
		}

		//test password
		if (!accountService.controlPassword(connectionFormDTO.getPassword(), account)) {
			//use the same message for both login and password error
			return unauthorized(new ExceptionsDTO("The couple login / password was not found"));
		}

		//control interface
		InterfaceTypeCode interfaceTypeCode = new InterfaceTypeCode(connectionFormDTO.getInterfaceName());

		if (interfaceTypeCode == null || !interfaceTypeCode.equals(account.getInterfaceCode())) {
			//use the same message for both login and password error
			//TODO translate
			return unauthorized(new ExceptionsDTO("This account is not for " + interfaceTypeCode.getKey() + " but for " + account.getInterfaceCode().getKey() + ". Please switch calculator and retry."));
		}

		//control acitf
		if (!account.getActive()) {
			//TODO translate
			return unauthorized(new ExceptionsDTO("Votre compte est actuellement suspendue. Contactez votre administrateur."));
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

		//generate email
		/* TODO waiting email refactoring
		EmailMessage emailMessage = new EmailMessage(account.getPerson().getEmail(),
				"New password", "blabla your new password : " + password,
				account.getInterfaceCode(),
				account.getPerson().getDefaultLanguage());

		emailService.send(emailMessage);
		*/
		//save new password
		account.setPassword(password);
		account.setNeedChangePassword(true);
		accountService.saveOrUpdate(account);

		return ok(new ReturnDTO());
	}

	private static <T extends DTO> T extractDTOFromRequest(Class<T> DTOclass) {
		T dto = DTO.getDTO(request().body().asJson(), DTOclass);
		if (dto == null) {
			throw new RuntimeException("The request content cannot be converted to a '" + DTOclass.getName() + "'.");
		}
		return dto;
	}

} // end of controller class

