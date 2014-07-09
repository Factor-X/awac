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

import static play.data.Form.form;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.myrmex.get.ExceptionsDTO;
import eu.factorx.awac.dto.myrmex.get.MyselfDTO;
import eu.factorx.awac.dto.myrmex.post.ConnectionFormDTO;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import play.Logger;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

@org.springframework.stereotype.Controller
public class AuthenticationController extends Controller {

    @Autowired
    private AccountService accountService;

    @Autowired
    private ConversionService conversionService;

    @Autowired
    private SecuredController securedController;

    @Transactional(readOnly = true)
    public Result testAuthentication(){

        if(securedController.isAuthenticated()){
            return ok(conversionService.convert(securedController.getCurrentUser(),MyselfDTO.class));
        }
        return this.unauthorized();
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
            return this.unauthorized(new ExceptionsDTO("The couple login / password was not found"));
        }

        //test password
        if (!account.getPassword().equals(connectionFormDTO.getPassword())) {
            //use the same message for both login and password error
            return this.unauthorized(new ExceptionsDTO("The couple login / password was not found"));
        }

        //if the login and the password are ok, refresh the session
        session().clear();
        session(SecuredController.SESSION_IDENTIFIER_STORE, account.getIdentifier());

        //convert and send it
        return ok(conversionService.convert(account, MyselfDTO.class));

    } // end of authenticate action

    // logout action cf routes
    public Result logout() {

        session().clear();
        Logger.debug(SecuredController.SESSION_IDENTIFIER_STORE+":"+session().get(SecuredController.SESSION_IDENTIFIER_STORE));

        return ok("Stop");
    }

} // end of controller class

