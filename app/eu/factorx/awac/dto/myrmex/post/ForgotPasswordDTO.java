package eu.factorx.awac.dto.myrmex.post;


import eu.factorx.awac.dto.DTO;

public class ForgotPasswordDTO extends DTO{

	private String identifier;

	public ForgotPasswordDTO() {
	}

	public ForgotPasswordDTO(String identifier) {
		this.identifier = identifier;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	@Override
	public String toString() {
		return "ForgotPasswordDTO{" +
				"identifier='" + identifier + '\'' +
				'}';
	}
}
