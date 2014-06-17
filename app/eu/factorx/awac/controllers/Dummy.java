
package eu.factorx.awac.controllers;

import play.mvc.Controller;
import play.mvc.Result;

public class Dummy extends Controller {

    /**
     * Handle default path requests, redirect to account list
     */
    public static Result index() {
        return ok(eu.factorx.awac.views.html.dummy.index.render());
    }

}