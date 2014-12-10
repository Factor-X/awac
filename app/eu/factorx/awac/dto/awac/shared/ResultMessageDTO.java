package eu.factorx.awac.dto.awac.shared;

import eu.factorx.awac.dto.DTO;

public class ResultMessageDTO extends DTO {

	private String message;

	public ResultMessageDTO() {
	}

	public ResultMessageDTO(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
