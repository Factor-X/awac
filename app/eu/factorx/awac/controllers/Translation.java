package eu.factorx.awac.controllers;

import eu.factorx.awac.InMemoryData;
import play.mvc.Controller;
import play.mvc.Result;

public class Translation extends Controller {

	public Result fetch(String language) {

		return ok(InMemoryData.translations.get(language.toUpperCase()));

	}

}
