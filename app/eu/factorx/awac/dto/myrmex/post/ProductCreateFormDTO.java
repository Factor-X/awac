package eu.factorx.awac.dto.myrmex.post;

import eu.factorx.awac.dto.FormDTO;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by root on 6/07/14.
 */
public class ProductCreateFormDTO extends FormDTO {

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


    public boolean testName() {

        if (name != null) {
            Pattern patternName = Pattern.compile("^.{2,20}$");

            Matcher matcherName = patternName.matcher(name);

            if (matcherName.find()) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean controlForm() {
        if (testName()) {
            return true;
        }
        return false;
    }
}
