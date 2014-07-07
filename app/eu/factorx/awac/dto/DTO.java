package eu.factorx.awac.dto;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.factorx.awac.util.MyrmexRunTimeException;
import play.Logger;
import play.mvc.Content;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DTO implements Content {

    private String __type;

    public String get__type() {
        return this.getClass().getCanonicalName();
    }

    public void set__type(String __type) {
        if (!get__type().equals(__type)) {
            throw new MyrmexRunTimeException("Wrong type of DTO received");
        }
    }

    @Override
    public String body() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new MyrmexRunTimeException(e,e.getMessage());
        }
    }

    @Override
    public String contentType() {
        return "application/json; charset=utf-8";
    }

    public static <T extends DTO> T getDTO(JsonNode data, Class<T> type) {
        ObjectMapper mapper = new ObjectMapper();
        JsonParser jp = data.traverse();
        try {
            T dto = mapper.readValue(jp, type);
            if (dto == null) {
                throw new MyrmexRunTimeException("Validation of DTO creation");
            }
            dto.validate();
            return dto;

        } catch (IOException e) {
            throw new MyrmexRunTimeException("Validation of DTO creation");
        }
    }

    public void validate() {

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<DTO>> violations = validator.validate(this);

        if (violations.size() > 0) {

            String errors = "";

            for(ConstraintViolation<DTO> constraintViolation : violations){
                errors=errors.concat("\n");
                errors=errors.concat(constraintViolation.getMessage());
            }

            throw new RuntimeException("Validation of DTO failed : "+errors);
        }
    }


}
