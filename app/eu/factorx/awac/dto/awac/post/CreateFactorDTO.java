package eu.factorx.awac.dto.awac.post;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.validation.annotations.NotNull;

public class CreateFactorDTO extends DTO {

    @NotNull
    private String unitCategory;
    @NotNull
    private String indicatorCategory;
    @NotNull
    private String activityType;
    @NotNull
    private String activitySource;
    private String origin;
    @NotNull
    private Double valueSince2000;

    public CreateFactorDTO() {

    }

    public String getUnitCategory() {
        return unitCategory;
    }

    public void setUnitCategory(String unitCategory) {
        this.unitCategory = unitCategory;
    }

    public String getIndicatorCategory() {
        return indicatorCategory;
    }

    public void setIndicatorCategory(String indicatorCategory) {
        this.indicatorCategory = indicatorCategory;
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

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public Double getValueSince2000() {
        return valueSince2000;
    }

    public void setValueSince2000(Double valueSince2000) {
        this.valueSince2000 = valueSince2000;
    }
}
