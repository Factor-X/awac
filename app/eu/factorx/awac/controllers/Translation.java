package eu.factorx.awac.controllers;

import eu.factorx.awac.InMemoryData;
import eu.factorx.awac.dto.myrmex.get.TranslationsDTO;
import play.mvc.Controller;
import play.mvc.Result;

public class Translation extends Controller {

    public Result fetch(String language) {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }


        return ok(InMemoryData.translations.get(language.toUpperCase()));

    }

}
