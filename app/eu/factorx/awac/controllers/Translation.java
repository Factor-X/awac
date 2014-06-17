package eu.factorx.awac.controllers;

import play.mvc.Controller;
import play.mvc.Result;

/**
 * Created by gaetan on 6/17/14.
 */

public class Translation extends Controller {

    public static Result fetch(String language) {
        return ok("{ \"ABCD\": \"Enorme bravo !\" }");
    }

}
