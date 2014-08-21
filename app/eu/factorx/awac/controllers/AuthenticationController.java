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
import eu.factorx.awac.dto.myrmex.post.ConnectionFormDTO;
import eu.factorx.awac.dto.myrmex.post.ForgotPasswordDTO;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.service.AccountService;
import eu.factorx.awac.util.BusinessErrorType;
import eu.factorx.awac.util.KeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import play.Logger;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

@org.springframework.stereotype.Controller
public class AuthenticationController extends Controller {

	@Autowired
	private AccountService accountService;

	@Autowired
	private ConversionService conversionService;

	@Autowired
	private SecuredController securedController;

	@Transactional(readOnly = true)
	public Result testAuthentication() {

		if (securedController.isAuthenticated()) {
			return ok(conversionService.convert(securedController.getCurrentUser(), LoginResultDTO.class));

		}
		return unauthorized();
	}


	// authenticate action cf routes
	@Transactional(readOnly = true)
	public Result authenticate() {

		ConnectionFormDTO connectionFormDTO = DTO.getDTO(request().body().asJson(), ConnectionFormDTO.class);

		if (connectionFormDTO == null) {
			throw new RuntimeException("The request cannot be convert");
		}

		//test if the login exist
		Account account = accountService.findByIdentifier(connectionFormDTO.getLogin());

		if (account == null) {
			//use the same message for both login and password error
			return unauthorized(new ExceptionsDTO("The couple login / password was not found"));
		}

		//test password
		if (!account.getPassword().equals(connectionFormDTO.getPassword())) {
			//use the same message for both login and password error
			return unauthorized(new ExceptionsDTO("The couple login / password was not found"));
		}

		//if the login and the password are ok, refresh the session
		session().clear();
		session(SecuredController.SESSION_IDENTIFIER_STORE, account.getIdentifier());

		//convert and send it
		LoginResultDTO dto = conversionService.convert(account, LoginResultDTO.class);

		return ok(dto);

	} // end of authenticate action

	// logout action cf routes
	public Result logout() {

		session().clear();
		Logger.debug(SecuredController.SESSION_IDENTIFIER_STORE + ":" + session().get(SecuredController.SESSION_IDENTIFIER_STORE));

		return ok("Stop");
	}


	@Transactional(readOnly = false)
	public Result forgotPassword(){

		ForgotPasswordDTO dto = extractDTOFromRequest(ForgotPasswordDTO.class);

		InterfaceTypeCode interfaceTypeCode = new InterfaceTypeCode(dto.getInterfaceCode());

		Account account;

		if(dto.getIdentifier().contains("@")){
			account = accountService.findByEmailAndInterfaceCode(dto.getIdentifier().toLowerCase(),interfaceTypeCode);
		}
		else{
			account = accountService.findByIdentifier(dto.getIdentifier());
		}


		if(account == null){
			return notFound(new ExceptionsDTO(BusinessErrorType.INVALID_IDENTIFIER));
		}

		//generate password
		String password = KeyGenerator.generateRandomPassword(10);

		//TODO generate email
		play.Logger.info("SEND email : " + password);
		//TODO send email

		//save new password
		//TODO encode password !!
		account.setPassword(password);
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

