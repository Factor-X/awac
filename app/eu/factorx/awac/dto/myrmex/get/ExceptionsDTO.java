package eu.factorx.awac.dto.myrmex.get;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.util.BusinessErrorType;

public class ExceptionsDTO extends DTO {

	private final String message;

	// add default constructor for Json Parser
	public ExceptionsDTO() { this.message = null; }

	public ExceptionsDTO(String message) {
		this.message = message;
	}

	public ExceptionsDTO(BusinessErrorType businessError) {
		this.message = businessError.toString();
	}

	public String getMessage() {
		return message;
	}
}
