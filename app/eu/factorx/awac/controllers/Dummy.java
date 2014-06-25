
package eu.factorx.awac.controllers;

import eu.factorx.awac.dto.awac.get.HouseholdDTO;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.Date;


public class Dummy extends Controller {

    public static Result getHousehold(Integer year) {
        HouseholdDTO dto = new HouseholdDTO();

        // general
        dto.year = year;
        dto.lastSave = new Date();

        // list of values
        dto.fuels.add(1, "GAZ");
        dto.fuels.add(2, "WOOD");
        dto.fuels.add(3, "COAL");
        dto.fuels.add(4, "MAZOUT");

        dto.volumeUnits.add(1, "LITRE");
        dto.volumeUnits.add(2, "CUBIC-METER");

        dto.households.add(1, "TYPE1");
        dto.households.add(2, "TYPE2");

        // heat
        dto.mainFuel.value = 1L;

        dto.consumption.value = 123.0;
        dto.consumption.unit = 2L;

        // dto.otherFuels

        dto.householdType.value = 1L;

        dto.surface.value = 120.0;
        dto.inhabitants.value = 4;
        dto.heatedRooms.value = 2;
        dto.builtBefore1975.value = true;

        dto.last10YearsRoof.value = true;
        dto.last10YearsExternalWalls.value = false;
        dto.last10YearsInsulatedGlazing.value = false;

        // cook
        dto.butane.value = 1000.0;
        dto.butane.unit = 2L;

        return ok(dto);
    }

}