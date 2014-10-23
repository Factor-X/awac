package eu.factorx.awac.dto.admin.get;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.awac.get.PeriodDTO;

/**
 * Created by florian on 21/10/14.
 */
public class DriverValueDTO extends DTO {

    private PeriodDTO  fromPeriod;

    private Double defaultValue;

    public DriverValueDTO() {
    }

    public DriverValueDTO(Double defaultValue, PeriodDTO fromPeriod) {
        this.defaultValue = defaultValue;
        this.fromPeriod = fromPeriod;
    }

    public PeriodDTO getFromPeriod() {
        return fromPeriod;
    }

    public void setFromPeriod(PeriodDTO fromPeriod) {
        this.fromPeriod = fromPeriod;
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
                "fromPeriod=" + fromPeriod +
                ", defaultValue=" + defaultValue +
                '}';
    }
}
