package eu.factorx.awac.dto.myrmex.post;


import eu.factorx.awac.dto.DTO;

public class ForgotPasswordDTO extends DTO{

	private String identifier;

	private String interfaceCode;

	public ForgotPasswordDTO() {
	}

	public ForgotPasswordDTO(String identifier, String interfaceCode) {
		this.identifier = identifier;
		this.interfaceCode = interfaceCode;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getInterfaceCode() {
		return interfaceCode;
	}

	public void setInterfaceCode(String interfaceCode) {
		this.interfaceCode = interfaceCode;
	}

	@Override
	public String toString() {
		return "ForgotPasswordDTO{" +
				"interfaceCode='" + interfaceCode + '\'' +
				", identifier='" + identifier + '\'' +
				'}';
	}
}
