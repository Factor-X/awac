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

import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import eu.factorx.awac.compilers.AngularCompiler;
import eu.factorx.awac.service.PersonService;

@org.springframework.stereotype.Controller
public class Application extends Controller {

	@Autowired
	private PersonService personService;

	@Transactional
    public Result index() {
        return ok(eu.factorx.awac.views.html.index.render());
    }

    public static Result app() {
        AngularCompiler compiler = new AngularCompiler();
        String code = compiler.compile("app/eu/factorx/awac/angular");
        return ok(code);
    }

}
