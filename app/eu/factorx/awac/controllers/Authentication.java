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
import eu.factorx.awac.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import play.Logger;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import eu.factorx.awac.common.AccountStatusType;
import eu.factorx.awac.models.account.Person;
@org.springframework.stereotype.Controller
public class Authentication extends Controller {

    @Autowired
    private AccountService accountService;

    @Autowired
    private ConversionService conversionService;

    // inner class for login information
    public static class Login {

       public String identifier;
       public String password;

        public String getIdentifier() {
            return identifier;
        }

        public void setIdentifier(String identifier) {
            this.identifier = identifier;
        }

        public String getPassword() {
            return password;
        }
    } // end of inner class


    // login action cf routes
    public Result login() {
    	return ok(
        	eu.factorx.awac.views.html.login.form.render(form(Login.class))
    	);
    } // end of login controller

    // authenticate action cf routes
    public Result authenticate() {

        Logger.info("Json content : "+request().body().asJson());

        ConnectionFormDTO connectionFormDTO = DTO.getDTO(request().body().asJson(), ConnectionFormDTO.class);

        if(connectionFormDTO==null){
            throw new RuntimeException("The request cannot be convert");
        }

        Logger.info("Login : "+connectionFormDTO.getLogin());
        Logger.info("Password : "+connectionFormDTO.getPassword());

        //control the form
        if(!connectionFormDTO.controlForm()){
            //generic error message => the content of the form was controlled in the client side
            return this.forbidden(new ExceptionsDTO("The form is not valid"));
        }

        //test if the login exist
        Logger.info("accountService : "+accountService);
        Logger.info("connectionFormDTO : "+connectionFormDTO);
        Account account =  accountService.findByIdentifier(connectionFormDTO.getLogin());

        if(account==null){
            //use the same message for both login and password error
            return this.notFound(new ExceptionsDTO("The couple login / password was not found"));
        }

        //test password
        if(account.getPassword().equals(connectionFormDTO.getPassword())){
            //use the same message for both login and password error
            return this.notFound(new ExceptionsDTO("The couple login / password was not found"));
        }

        //if the login and the password are ok, refresh the session
        session().clear();
	    session("identifier",account.getIdentifier());

        //convert and send it
	    return ok(conversionService.convert(account, MyselfDTO.class));

    } // end of authenticate action

    // logout action cf routes
    @Security.Authenticated(Secured.class)
    public Result logout() {
        /*
      session().clear();
       flash("success", "You've been logged out");
       return redirect(
        eu.factorx.awac.controllers.routes.Authentication.login()
       );*/
        return null;
    } // end of logout action
        
} // end of controller class

