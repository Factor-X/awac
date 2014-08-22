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

import eu.factorx.awac.dto.myrmex.get.ExceptionsDTO;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.account.Administrator;
import eu.factorx.awac.models.account.Person;
import eu.factorx.awac.models.account.SystemAdministrator;
import eu.factorx.awac.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import play.db.jpa.Transactional;
import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Security;

@Controller
public class SecuredController extends Security.Authenticator {

	public static final String SESSION_IDENTIFIER_STORE = "identifier";
	@Autowired
	private AccountService accountService;

	@Override
	public String getUsername(Context ctx) {
		return ctx.session().get(SESSION_IDENTIFIER_STORE);
	}

	@Override
	public Result onUnauthorized(Context ctx) {
		return unauthorized(new ExceptionsDTO("Not connected"));
	}

	public boolean isAuthenticated() {
		return (Context.current().session().get(SESSION_IDENTIFIER_STORE) == null) ? false : true;
	}

	@Transactional(readOnly = true)
	public Account getCurrentUser() {

		return accountService.findByIdentifier(Context.current().session().get("identifier"));
	}

	public boolean isAdministrator() {
		return (((Account)getCurrentUser()) instanceof Administrator);
	}

	public boolean isSystemAdministrator() {
		return (((Account)getCurrentUser()) instanceof SystemAdministrator);
	}

	public void storeIdentifier(String identifier){

		//if the login and the password are ok, refresh the session
		Context.current().session().clear();
		Context.current().session().put(SecuredController.SESSION_IDENTIFIER_STORE, identifier);
	}
}

