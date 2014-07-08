package eu.factorx.awac.dto.myrmex.post;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.validation.annotations.NotNull;

import javax.validation.constraints.Size;


/**
 * Created by root on 6/07/14.
 */
public class ProductCreateFormDTO extends DTO {

    @NotNull
    @Size(min = 2,max = 20,message = "name : between 2 and 20 letters")
    private String name;

    public ProductCreateFormDTO() {
    }

    public ProductCreateFormDTO(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
