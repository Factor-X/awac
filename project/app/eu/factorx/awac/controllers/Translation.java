package eu.factorx.awac.controllers;

import play.mvc.Controller;
import play.mvc.Result;
import eu.factorx.awac.InMemoryData;

public class Translation extends Controller {

    public Result fetch(String language) {

        return ok(InMemoryData.translations.get(language.toUpperCase()));

    }

}
