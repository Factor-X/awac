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


import eu.factorx.awac.compilers.AngularCompiler;
import play.Routes;
import play.mvc.Controller;
import play.mvc.Result;

import java.io.File;

public class Application extends Controller {

    public static Result index() {
        return ok(eu.factorx.awac.views.html.index.render());
    }

    public static Result javascriptRoutes() {

        response().setContentType("text/javascript");
        return ok(
                Routes.javascriptRouter("jsRoutes",
                        eu.factorx.awac.controllers.vies.routes.javascript.VatViesService.checkVat()
                        // Routes
                )
        );
    }


    public static Result app() {
        AngularCompiler compiler = new AngularCompiler();
        String code = compiler.compile("app/eu/factorx/awac/angular");
        return ok(code);
    }

}
