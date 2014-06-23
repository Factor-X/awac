package eu.factorx.awac.controllers;

import eu.factorx.awac.dto.myrmex.TranslationsDTO;
import play.mvc.Controller;
import play.mvc.Result;

public class Translation extends Controller {

    public static Result fetch(String language) {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }

        TranslationsDTO dto = new TranslationsDTO(language);

        dto.put("ABCD", "Deux poules discutent:<br/>- Comment vas-tu ma cocotte?<br/>- Pas très bien. Je crois que je couve quelque chose !");
        dto.put("DOUBLE-CODE", "Un double: ");
        dto.put("INTEGER-CODE", "Un entier: ");
        dto.put("TEXT-CODE", "Un texte: ");

        dto.put("DOUBLE-UNIT-CODE", "Un double avec unité: ");

        dto.put("UNITS-KILOGRAM", "kg");
        dto.put("UNITS-GRAM", "g");
        dto.put("UNITS-TON", "ton");

        return ok(dto);

    }

}
