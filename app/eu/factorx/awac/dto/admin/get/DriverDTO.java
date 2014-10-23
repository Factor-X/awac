package eu.factorx.awac.dto.admin.get;

import eu.factorx.awac.dto.DTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by florian on 21/10/14.
 */
public class DriverDTO extends DTO {

    private String name;

    private List<DriverValueDTO> driverValues;

    public DriverDTO() {
    }

    public DriverDTO(String name, List<DriverValueDTO> driverValues) {
        this.name = name;
        this.driverValues = driverValues;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DriverValueDTO> getDriverValues() {
        return driverValues;
    }

    public void setDriverValues(List<DriverValueDTO> driverValues) {
        this.driverValues = driverValues;
    }

    public void addDriverValue(DriverValueDTO driverValueDTO){
        if(this.driverValues==null){
            this.driverValues = new ArrayList<>();
        }

        this.driverValues.add(driverValueDTO);
    }

    @Override
    public String toString() {
        return "DriverDTO{" +
                "driverValues=" + driverValues +
                ", name='" + name + '\'' +
                '}';
    }
}
