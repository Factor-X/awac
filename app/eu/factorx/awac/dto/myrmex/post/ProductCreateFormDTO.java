package eu.factorx.awac.dto.myrmex.post;

import eu.factorx.awac.dto.DTO;

import javax.validation.constraints.NotNull;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by root on 6/07/14.
 */
public class ProductCreateFormDTO extends DTO {

    @NotNull
    @javax.validation.constraints.Pattern(regexp = "^.{2,20}$")
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
