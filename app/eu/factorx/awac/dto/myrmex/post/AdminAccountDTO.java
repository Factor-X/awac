package eu.factorx.awac.dto.myrmex.post;

import eu.factorx.awac.dto.DTO;


public class AdminAccountDTO extends DTO{

	private String email;

	private String interfaceName;

	private Boolean isAdmin;

	public AdminAccountDTO() {
	}


	public AdminAccountDTO(String email, String interfaceName, Boolean isAdmin) {
		this.email = email;
		this.interfaceName = interfaceName;
		this.isAdmin = isAdmin;
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

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	@Override
	public String toString() {
		return "AdminAccountDTO{" +
				"email='" + email + '\'' +
				", interfaceName='" + interfaceName + '\'' +
				", isAdmin=" + isAdmin +
				'}';
	}
}
