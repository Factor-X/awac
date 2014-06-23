package eu.factorx.awac.dto.awac;


import eu.factorx.awac.dto.DTO;


import java.util.*;


public class HouseholdDTO extends DTO {

    // general
    public Integer year;
    public Date lastSave;

    // list of values
    public Map<Integer, String> fuels;
    public Map<Integer, String> volumeUnits;
    public Map<Integer, String> households;

    // heat
    public Integer mainFuel;

    public Double consumption;
    public Integer consumptionUnit;

    public List<Integer> otherFuels;

    public Integer householdType;

    public Double surface;
    //public Integer year;


    public HouseholdDTO() {


    }


}