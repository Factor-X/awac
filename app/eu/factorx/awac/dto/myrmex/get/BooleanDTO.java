package eu.factorx.awac.dto.myrmex.get;

import eu.factorx.awac.dto.DTO;

/**
 * Created by florian on 18/09/14.
 */
public class BooleanDTO extends DTO {

    private Boolean value;

    public BooleanDTO() {
    }

    public BooleanDTO(Boolean value) {
        this.value = value;
    }

    public Boolean getValue() {
        return value;
    }

    public void setValue(Boolean value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "BooleanDTO{" +
                "value=" + value +
                '}';
    }
}
