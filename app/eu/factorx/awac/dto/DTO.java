package eu.factorx.awac.dto;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.factorx.awac.dto.validation.Validator;
import eu.factorx.awac.util.MyrmexRunTimeException;
import play.mvc.Content;

import java.io.IOException;

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
			throw new MyrmexRunTimeException(e, e.getMessage());
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
		try {
			Validator.validate(this);
		} catch (Exception e) {
			throw new MyrmexRunTimeException("Validation failed for DTO: " + e.getMessage());
		}
	}


}
