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

import eu.factorx.awac.controllers.jade.JadeBaseController;
import play.Routes;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

@org.springframework.stereotype.Controller
public class JadeController extends JadeBaseController {

	public static Result index() {
		return ok(render("index", "message", "Hello world!"));
	}

	public static Result login () {
		return ok(render("login", "message", "Hello world!"));
	}

	public static Result angular () {
			return ok(eu.factorx.awac.views.html.index.render());
	}


}
