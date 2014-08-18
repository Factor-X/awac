package eu.factorx.awac.dto.myrmex.get;

import eu.factorx.awac.dto.DTO;

/**
 * Created by florian on 4/07/14.
 */
public class ExceptionsDTO extends DTO {

	private final String message;

	// add default constructor for Json Parser
	public ExceptionsDTO() { this.message = null; }

	public ExceptionsDTO(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
