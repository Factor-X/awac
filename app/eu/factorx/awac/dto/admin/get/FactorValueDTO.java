package eu.factorx.awac.dto.admin.get;

import eu.factorx.awac.dto.DTO;

public class FactorValueDTO extends DTO {

    private Double value;
    private String dateIn;
    private String dateOut;

    public void setValue(Double value) {
        this.value = value;
    }

    public Double getValue() {
        return value;
    }

    public void setDateIn(String dateIn) {
        this.dateIn = dateIn;
    }

    public String getDateIn() {
        return dateIn;
    }

    public void setDateOut(String dateOut) {
        this.dateOut = dateOut;
    }

    public String getDateOut() {
        return dateOut;
    }
}
