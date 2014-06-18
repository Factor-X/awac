package eu.factorx.awac.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import play.mvc.Content;

public class DTO implements Content {
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
}
