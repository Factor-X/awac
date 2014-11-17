package eu.factorx.awac.dto.awac.post;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.validation.annotations.NotNull;
import eu.factorx.awac.dto.validation.annotations.Pattern;

public class EmailChangeDTO extends DTO {

	@NotNull
	private String password;

	@NotNull
	@Pattern(regexp = Pattern.EMAIL)
	private String newEmail;

	public EmailChangeDTO() {
	}

	public EmailChangeDTO(String password, String newEmail) {
		this.password = password;
		this.newEmail = newEmail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNewEmail() {
		return newEmail;
	}

	public void setNewEmail(String newEmail) {
		this.newEmail = newEmail;
	}

	@Override
	public String toString() {
		return "EmailChangeDTO [newEmail=" + newEmail + "]";
	}

}
