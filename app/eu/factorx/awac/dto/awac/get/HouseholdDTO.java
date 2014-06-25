package eu.factorx.awac.dto.awac.get;


import eu.factorx.awac.dto.DTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class HouseholdDTO extends DTO {

    // general
    public Integer year;
    public Date lastSave;

    // list of values
    public ListOfValuesDTO fuels = new ListOfValuesDTO();
    public ListOfValuesDTO volumeUnits = new ListOfValuesDTO();
    public ListOfValuesDTO households = new ListOfValuesDTO();

    // heat
    public QuestionDTO<Long> mainFuel = new QuestionDTO<>();

    public QuestionWithUnitDTO<Double> consumption = new QuestionWithUnitDTO<>();

    public List<QuestionWithUnitDTO<Double>> otherFuels = new ArrayList<>();

    public QuestionDTO<Long> householdType = new QuestionDTO<>();


    public QuestionDTO<Double> surface = new QuestionDTO<>();

    public QuestionDTO<Integer> inhabitants = new QuestionDTO<>();
    public QuestionDTO<Integer> heatedRooms = new QuestionDTO<>();
    public QuestionDTO<Boolean> builtBefore1975 = new QuestionDTO<>();

    public QuestionDTO<Boolean> last10YearsRoof = new QuestionDTO<>();
    public QuestionDTO<Boolean> last10YearsExternalWalls = new QuestionDTO<>();
    public QuestionDTO<Boolean> last10YearsInsulatedGlazing = new QuestionDTO<>();

    // cook
    public QuestionWithUnitDTO<Double> butane = new QuestionWithUnitDTO<>();

}