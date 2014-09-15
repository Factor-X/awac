package eu.factorx.awac.util.data.importer.badImporter;

import eu.factorx.awac.models.code.type.ActivityCategoryCode;
import eu.factorx.awac.models.code.type.BaseActivityDataCode;
import eu.factorx.awac.models.knowledge.Unit;
import eu.factorx.awac.models.reporting.BaseActivityData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by florian on 29/08/14.
 */
public class BAD {

    private String baseActivityDataCode;
    private String name;
    private int rank;
    private String SpecificPurpose;

    private String condition;

    //it's always an activityCategoryCode
    private String activityCategoryCode;

    private String activitySubCategory;

    private String activityType;

    private String activitySource;

    private String activityOwnership;

    private Unit unit;

    private String value;

    private boolean canBeGenerated = true;

    private List<String> listQuestion= new ArrayList<>();
    private int line;

    public BAD() {
    }

    public String getBaseActivityDataCode() {
        return baseActivityDataCode;
    }

    public void setBaseActivityDataCode(String baseActivityDataCode) {
        this.baseActivityDataCode = baseActivityDataCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getSpecificPurpose() {
        return SpecificPurpose;
    }

    public void setSpecificPurpose(String specificPurpose) {
        SpecificPurpose = specificPurpose;
    }

    public String getActivityCategoryCode() {
        return activityCategoryCode;
    }

    public void setActivityCategoryCode(String activityCategoryCode) {
        this.activityCategoryCode = activityCategoryCode;
    }

    public String getActivitySubCategory() {
        return activitySubCategory;
    }

    public void setActivitySubCategory(String activitySubCategory) {
        this.activitySubCategory = activitySubCategory;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getActivitySource() {
        return activitySource;
    }

    public void setActivitySource(String activitySource) {
        this.activitySource = activitySource;
    }

    public String getActivityOwnership() {
        return activityOwnership;
    }

    public void setActivityOwnership(String activityOwnership) {
        this.activityOwnership = activityOwnership;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    @Override
    public String toString() {
        return "BAD{" +
                "baseActivityDataCode='" + baseActivityDataCode + '\'' +
                ", name='" + name + '\'' +
                ", rank=" + rank +
                ", SpecificPurpose='" + SpecificPurpose + '\'' +
                ", condition='" + condition + '\'' +
                ", activityCategoryCode='" + activityCategoryCode + '\'' +
                ", activitySubCategory='" + activitySubCategory + '\'' +
                ", activityType='" + activityType + '\'' +
                ", activitySource='" + activitySource + '\'' +
                ", activityOwnership='" + activityOwnership + '\'' +
                ", unit=" + unit +
                ", value='" + value + '\'' +
                ", canBeGenerated=" + canBeGenerated +
                ", listQuestion=" + listQuestion +
                ", line=" + line +
                '}';
    }

    public void setCanBeGenerated(boolean canBeGenerated) {
        this.canBeGenerated = canBeGenerated;
    }

    public boolean isCanBeGenerated() {
        return canBeGenerated;
    }

    public void addQuestion(String questionCodeKey) {

        boolean founded = false;
        for (String unitToTest : listQuestion) {
            if (unitToTest.equals(questionCodeKey)) {
                founded = true;
            }
        }
        if (!founded) {
            this.listQuestion.add(questionCodeKey);
        }


    }

    public List<String> getListQuestions() {
        return listQuestion;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }
}
