package eu.factorx.awac.dto.myrmex.post;


import eu.factorx.awac.dto.DTO;

public class TestAuthenticateDTO extends DTO{

	private String interfaceName;

	public TestAuthenticateDTO() {
	}

	public TestAuthenticateDTO(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	@Override
	public String toString() {
		return "TestAuthenticateDTO{" +
				"interfaceName='" + interfaceName + '\'' +
				'}';
	}
}
