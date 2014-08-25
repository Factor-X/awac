package eu.factorx.awac.dto.myrmex.post;

import eu.factorx.awac.dto.DTO;


public class ActiveAccountDTO extends DTO {


	private String email;

	private String interfaceName;

	private Boolean isActive;

	public ActiveAccountDTO() {
	}


	public ActiveAccountDTO(String email, String interfaceName, Boolean isActive) {
		this.email = email;
		this.interfaceName = interfaceName;
		this.isActive = isActive;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	@Override
	public String toString() {
		return "ActiveAccountDTO{" +
				"email='" + email + '\'' +
				", interfaceName='" + interfaceName + '\'' +
				", isActive=" + isActive +
				'}';
	}
}
