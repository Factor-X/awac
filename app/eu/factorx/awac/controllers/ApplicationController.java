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

import play.Routes;
import play.db.jpa.Transactional;
import play.mvc.Result;
import eu.factorx.awac.GlobalVariables;

@org.springframework.stereotype.Controller
public class ApplicationController extends AbstractController {


    public Result admin() {
        return ok(eu.factorx.awac.views.html.admin.render(GlobalVariables.STARTUP_DATE_IDENTIFIER));
    }

    @Transactional
	public Result index() {
		return ok(eu.factorx.awac.views.html.index.render(GlobalVariables.STARTUP_DATE_IDENTIFIER));
	}


    public Result verification() {
        return ok(eu.factorx.awac.views.html.verification.render(GlobalVariables.STARTUP_DATE_IDENTIFIER));
    }

	public Result calculator(String appName) {
		return ok(eu.factorx.awac.views.html.calculator.render(GlobalVariables.STARTUP_DATE_IDENTIFIER,appName));
	}

	public Result javascriptRoutes() {
		response().setContentType("text/javascript");
		return ok(Routes.javascriptRouter("jsRoutes"
			// Routes
		));
	}
}
