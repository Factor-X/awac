package eu.factorx.awac.dto.myrmex.get;


import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.util.BusinessErrorType;

public class MustChangePasswordExceptionsDTO extends DTO{

	private final String message;

	// add default constructor for Json Parser
	public MustChangePasswordExceptionsDTO() { this.message = null; }

	public MustChangePasswordExceptionsDTO(String message) {
		this.message = message;
	}

	public MustChangePasswordExceptionsDTO(BusinessErrorType businessError) {
		this.message = businessError.toString();
	}

	public String getMessage() {
		return message;
	}
}
