package eu.factorx.awac.dto.admin.get;

import eu.factorx.awac.dto.DTO;

import java.util.ArrayList;
import java.util.List;

public class FactorDTO extends DTO {

    private String               key;
    private String               origin;
    private List<FactorValueDTO> factorValues;
    private String               unitIn;
    private String               indicatorCategory;
    private String               activitySource;
    private String               activityType;
    private String               unitCategoryIn;

    public FactorDTO() {
        factorValues = new ArrayList<FactorValueDTO>();
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getOrigin() {
        return origin;
    }

    public List<FactorValueDTO> getFactorValues() {
        return factorValues;
    }

    public void setUnitIn(String unitIn) {
        this.unitIn = unitIn;
    }

    public String getUnitIn() {
        return unitIn;
    }

    public void setIndicatorCategory(String indicatorCategory) {
        this.indicatorCategory = indicatorCategory;
    }

    public String getIndicatorCategory() {
        return indicatorCategory;
    }

    public void setActivitySource(String activitySource) {
        this.activitySource = activitySource;
    }

    public String getActivitySource() {
        return activitySource;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setUnitCategoryIn(String unitCategoryIn) {
        this.unitCategoryIn = unitCategoryIn;
    }

    public String getUnitCategoryIn() {
        return unitCategoryIn;
    }

}
