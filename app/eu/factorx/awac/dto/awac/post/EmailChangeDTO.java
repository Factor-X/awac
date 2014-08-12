package eu.factorx.awac.dto.awac.post;

import eu.factorx.awac.dto.DTO;

public class EmailChangeDTO extends DTO {

	private String password;

	private String oldEmail;

	private String newEmail;

	public EmailChangeDTO() {
	}

	public EmailChangeDTO(String password, String oldEmail, String newEmail) {
		this.password = password;
		this.oldEmail = oldEmail;
		this.newEmail = newEmail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getOldEmail() {
		return oldEmail;
	}

	public void setOldEmail(String oldEmail) {
		this.oldEmail = oldEmail;
	}

	public String getNewEmail() {
		return newEmail;
	}

	public void setNewEmail(String newEmail) {
		this.newEmail = newEmail;
	}
}
