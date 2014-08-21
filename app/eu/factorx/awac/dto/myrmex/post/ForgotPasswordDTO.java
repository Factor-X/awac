package eu.factorx.awac.dto.myrmex.post;


import eu.factorx.awac.dto.DTO;

public class ForgotPasswordDTO extends DTO{

	private String identifier;

	private String interfaceName;

	public ForgotPasswordDTO() {
	}

	public ForgotPasswordDTO(String identifier, String interfaceName) {
		this.identifier = identifier;
		this.interfaceName = interfaceName;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	@Override
	public String toString() {
		return "ForgotPasswordDTO{" +
				"identifier='" + identifier + '\'' +
				", interfaceName='" + interfaceName + '\'' +
				'}';
	}
}
