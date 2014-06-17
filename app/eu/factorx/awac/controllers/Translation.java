package eu.factorx.awac.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaetan on 6/17/14.
 */

public class Translation extends Controller {

    public static Result fetch(String language) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }

        Map<String, String> map = new HashMap<>();

        map.put("ABCD", "Deux poules discutent:<br/>- Comment vas-tu ma cocotte?<br/>- Pas très bien. Je crois que je couve quelque chose !");

        return ok(Json.toJson(map));
    }

}
