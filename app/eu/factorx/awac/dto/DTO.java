package eu.factorx.awac.dto;

import java.io.IOException;

import org.apache.commons.lang3.exception.ExceptionUtils;

import play.Logger;
import play.mvc.Content;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import eu.factorx.awac.dto.validation.Validator;
import eu.factorx.awac.util.MyrmexRuntimeException;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DTO implements Content {

	private String __type;

	public static <T extends DTO> T getDTO(JsonNode data, Class<T> type) {
		if(data!=null) {
			ObjectMapper mapper = new ObjectMapper();
			JsonParser jp = data.traverse();
			try {
				T dto = mapper.readValue(jp, type);
				if (dto == null) {
					throw new MyrmexRuntimeException("Validation of DTO creation");
				}
				dto.validate();
				return dto;

			} catch (IOException e) {
				throw new MyrmexRuntimeException(e, "Validation of DTO creation");
			}
		}
		throw new MyrmexRuntimeException("Validation of DTO : data is null");
	}

	public String get__type() {
		return this.getClass().getCanonicalName();
	}

	public void set__type(String __type) {
		if (!get__type().equals(__type)) {
			throw new MyrmexRuntimeException("Wrong type of DTO received. Expected : "+get__type()+", receive : "+__type);
		}
	}

	@Override
	public String body() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			throw new MyrmexRuntimeException(e, e.getMessage());
		}
	}

	@Override
	public String contentType() {
		return "application/json; charset=utf-8";
	}

	public void validate() {
		try {
			Logger.info("Validating DTO: " + this);
			Validator.validate(this);
		} catch (Exception e) {
			throw new MyrmexRuntimeException("Validation failed for DTO: " + e.getMessage() + "\n" + ExceptionUtils.getStackTrace(e));
		}
	}


}
