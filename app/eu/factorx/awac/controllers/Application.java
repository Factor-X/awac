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
import play.mvc.Controller;
import play.mvc.Result;

@org.springframework.stereotype.Controller
public class Application extends Controller {

	@Transactional
	public Result index() {
		return ok(eu.factorx.awac.views.html.index.render());
	}

	public Result enterprise() {
		return ok(eu.factorx.awac.views.html.enterprise.render());
	}

	public Result municipality() {
		return ok(eu.factorx.awac.views.html.municipality.render());
	}

	public Result javascriptRoutes() {
		response().setContentType("text/javascript");
		return ok(Routes.javascriptRouter("jsRoutes"
			// Routes
		));
	}
}
