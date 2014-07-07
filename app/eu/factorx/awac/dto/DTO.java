package eu.factorx.awac.dto;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import play.Logger;
import play.mvc.Content;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.IOException;
import java.util.Set;

public class DTO implements Content {

    private String __type;
    public String get__type() {
        return this.getClass().getCanonicalName();
    }
    public void set__type(String __type) {
        if (!get__type().equals(__type)) {
            throw new RuntimeException("Wrong type of DTO received");
        }
    }

    @Override
    public String body() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String contentType() {
        return "application/json; charset=utf-8";
    }

    public static <T> T getDTO(JsonNode data, Class<T> type) {
        ObjectMapper mapper = new ObjectMapper();
        JsonParser jp = data.traverse();
        try {
            return mapper.readValue(jp, type);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void validate() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<DTO>> violations = validator.validate(this);
        if (violations.size() > 0) {
            throw new RuntimeException("Validation of DTO failed");
        }
    }


}
