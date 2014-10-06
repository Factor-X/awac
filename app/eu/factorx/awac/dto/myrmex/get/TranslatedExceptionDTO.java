package eu.factorx.awac.dto.myrmex.get;

import eu.factorx.awac.dto.DTO;

import java.util.ArrayList;
import java.util.List;

public class TranslatedExceptionDTO extends DTO {

	private final String       code;
	private       List<Object> parameters;

	// add default constructor for Json Parser
	public TranslatedExceptionDTO() {
		this.code = null;
		this.parameters = new ArrayList<>();
	}

	public TranslatedExceptionDTO(String code) {
		this.code = code;
		this.parameters = new ArrayList<>();
	}

	public TranslatedExceptionDTO(String message, List<Object> parameters) {
		this.code = message;
		this.parameters = parameters;
	}

	public String getCode() {
		return code;
	}

	public List<Object> getParameters() {
		return parameters;
	}
}
