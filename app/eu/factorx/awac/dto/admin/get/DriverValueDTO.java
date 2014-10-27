package eu.factorx.awac.dto.admin.get;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.awac.get.PeriodDTO;

/**
 * Created by florian on 21/10/14.
 */
public class DriverValueDTO extends DTO {

    private String  fromPeriodKey;

    private Double defaultValue;

    public DriverValueDTO() {
    }

    public DriverValueDTO(String fromPeriodKey, Double defaultValue) {
        this.fromPeriodKey = fromPeriodKey;
        this.defaultValue = defaultValue;
    }

    public String getFromPeriodKey() {
        return fromPeriodKey;
    }

    public void setFromPeriodKey(String fromPeriodKey) {
        this.fromPeriodKey = fromPeriodKey;
    }

    public Double getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(Double defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public String toString() {
        return "DriverValueDTO{" +
                "fromPeriodKey='" + fromPeriodKey + '\'' +
                ", defaultValue=" + defaultValue +
                '}';
    }
}
