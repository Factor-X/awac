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

import eu.factorx.awac.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import eu.factorx.awac.common.AccountStatusType;
import eu.factorx.awac.models.account.Person;

public class Authentication extends Controller {

    @Autowired
    private PersonService personService;

    // inner class for login information
    public static class Login {

       public String identifier;
       public String password;

       // validate is called automatically by the framework on submit.
       public String validate() {
    	if (Login.authenticate(identifier, password) == null) {
      		return "Invalid user or password";
    	}
    	return null;
       }
       
   	   /*
   		* Account authentication
   		*/
   	
      	public static Person authenticate(String id, String password) {
      		return null;/*pers.find.where().eq("identifier", id)
      				.eq("accountStatus",AccountStatusType.ACTIVE)
      	        	.eq("password", password).findUnique();
      	        	*/
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
	    Form<Login> loginForm = form(Login.class).bindFromRequest(
    	);
	    if (loginForm.hasErrors()) {
	      return badRequest(eu.factorx.awac.views.html.login.form.render(loginForm));
	    }
	    else {
	      session().clear();
	      session("identifier",loginForm.get().identifier);	      
	      return (redirect (eu.factorx.awac.controllers.routes.Application.index()));
	    }
    } // end of authenticate action

    // logout action cf routes
    @Security.Authenticated(Secured.class)
    public Result logout() {
      session().clear();
       flash("success", "You've been logged out");
       return redirect(
        eu.factorx.awac.controllers.routes.Authentication.login()
       );
    } // end of logout action
        
} // end of controller class

