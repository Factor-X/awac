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

import play.*;
import play.mvc.*;
import play.mvc.Http.*;

import eu.factorx.awac.models.*;

public class Secured extends Security.Authenticator {

    @Override
    public String getUsername(Context ctx) {
        return ctx.session().get("identifier");
    }

    @Override
    public Result onUnauthorized(Context ctx) {
        return redirect(eu.factorx.awac.controllers.routes.Authentication.login());
    }

    public static boolean isAuthenticated() {
        return (Context.current().session().get("identifier") == null) ? false : true;
    }

    public static Person getCurrentUser() {
        return (eu.factorx.awac.models.Person.findByIdentifier(Context.current().session().get("identifier")));
    }

    public static boolean isAdministrator() {
        return (getCurrentUser() instanceof eu.factorx.awac.models.Administrator);
    } // end of check administrator

    public static boolean isAccount() {
        return (getCurrentUser() instanceof eu.factorx.awac.models.Account);
    } // end of check administrator

}

