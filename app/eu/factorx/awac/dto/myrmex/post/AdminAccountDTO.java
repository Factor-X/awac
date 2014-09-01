package eu.factorx.awac.dto.myrmex.post;

import eu.factorx.awac.dto.DTO;


public class AdminAccountDTO extends DTO{

	private String identifier;

	private Boolean isAdmin;

	public AdminAccountDTO() {
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
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
				"isAdmin=" + isAdmin +
				", identifier='" + identifier + '\'' +
				'}';
	}
}
