package eu.factorx.awac.dto;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import play.Logger;
import play.mvc.Content;

import java.io.IOException;

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

    public static <T> T getDTO( JsonNode data, Class<T> type ){

        ObjectMapper mapper = new ObjectMapper();

        JsonParser jp = data.traverse();

        try {
            return mapper.readValue(jp, type);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
