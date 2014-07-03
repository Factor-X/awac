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
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import eu.factorx.awac.common.AccountStatusType;
import eu.factorx.awac.models.account.Person;

public class Authentication extends Controller {

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
      		return Person.find.where().eq("identifier", id)
      				.eq("accountStatus",AccountStatusType.ACTIVE)
      	        	.eq("password", password).findUnique();
      	}	
    } // end of inner class


    // login action cf routes
    public static Result login() {
    	return ok(
        	form.render(form(Login.class))
    	);
    } // end of login controller

    // authenticate action cf routes
    public static Result authenticate() {
	    Form<Login> loginForm = form(Login.class).bindFromRequest(
    	);
	    if (loginForm.hasErrors()) {
	      return badRequest(form.render(loginForm));
	    }
	    else {
	      session().clear();
	      session("identifier",loginForm.get().identifier);	      
	      return (redirect (routes.Application.index()));
	    }
    } // end of authenticate action

    // logout action cf routes
    @Security.Authenticated(Secured.class)
    public static Result logout() {
      session().clear();
       flash("success", "You've been logged out");
       return redirect(
        eu.factorx.awac.controllers.routes.Authentication.login()
       );
    } // end of logout action
        
} // end of controller class

