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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;

import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.myrmex.get.ExceptionsDTO;
import eu.factorx.awac.dto.myrmex.get.MyselfDTO;
import eu.factorx.awac.dto.myrmex.post.ConnectionFormDTO;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.service.AccountService;

@org.springframework.stereotype.Controller
public class AuthenticationController extends Controller {

    @Autowired
    private AccountService accountService;

    @Autowired
    private ConversionService conversionService;

    // authenticate action cf routes
    @Transactional(readOnly = true)
    public Result authenticate() {

        ConnectionFormDTO connectionFormDTO = DTO.getDTO(request().body().asJson(), ConnectionFormDTO.class);

        if (connectionFormDTO == null) {
            throw new RuntimeException("The request cannot be convert");
        }
/*
        //control the form
        if (!connectionFormDTO.controlForm()) {
            //generic error message => the content of the form was controlled in the client side
            return this.forbidden(new ExceptionsDTO("The form is not valid"));
        }
*/
        //test if the login exist
        Account account = accountService.findByIdentifier(connectionFormDTO.getLogin());

        if (account == null) {
            //use the same message for both login and password error
            return this.notFound(new ExceptionsDTO("The couple login / password was not found"));
        }

        //test password
        if (!account.getPassword().equals(connectionFormDTO.getPassword())) {
            //use the same message for both login and password error
            return this.notFound(new ExceptionsDTO("The couple login / password was not found"));
        }

        //if the login and the password are ok, refresh the session
        session().clear();
        session(Secured.SESSION_IDENTIFIER_STORE, account.getIdentifier());

        //convert and send it
        return ok(conversionService.convert(account, MyselfDTO.class));

    } // end of authenticate action

    // logout action cf routes
    @Security.Authenticated(Secured.class)
    public Result logout() {
        session().clear();
        flash("success", "You've been logged out");
        return null;
    } // end of logout action

} // end of controller class

