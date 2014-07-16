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
import eu.factorx.awac.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import play.Logger;
import play.db.jpa.Transactional;
import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Security;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.account.Administrator;
import eu.factorx.awac.models.account.Person;
import eu.factorx.awac.service.PersonService;

@Controller
public class SecuredController extends Security.Authenticator {

    @Autowired
    private AccountService accountService;

    public static final String SESSION_IDENTIFIER_STORE = "identifier";

    @Override
    public String getUsername(Context ctx) {
        return ctx.session().get(SESSION_IDENTIFIER_STORE);
    }

    @Override
    public Result onUnauthorized(Context ctx) {
        return unauthorized(new ExceptionsDTO("Not connected"));
    }

    public boolean isAuthenticated() {
        return (Context.current().session().get(SESSION_IDENTIFIER_STORE)==null)?false:true;
    }

    @Transactional(readOnly = true)
    public Account getCurrentUser (){

        return accountService.findByIdentifier(Context.current().session().get("identifier"));
    }

    public boolean isAdministrator () {
        return false;//(((Person)getCurrentUser()) instanceof Administrator);
    } // end of check administrator
/*
	
    @Override
    public String getUsername(Context ctx) {
        return ctx.session().get("identifier");
    }

    @Override
    public Result onUnauthorized(Context ctx) {
        return null;//redirect(eu.factorx.awac.controllers.routes.Authentication.login());
    }

	public boolean isAuthenticated() {
		return (Context.current().session().get("identifier")==null)?false:true;
	}


    public String getCurrentIdentifier () {

        return Context.current().session().get("identifier");
    }



*/
}

