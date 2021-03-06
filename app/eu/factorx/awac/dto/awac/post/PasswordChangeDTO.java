package eu.factorx.awac.dto.awac.post;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.validation.annotations.Pattern;

public class PasswordChangeDTO extends DTO {

	@Pattern(regexp = Pattern.PASSWORD)
	private String oldPassword;

	@Pattern(regexp = Pattern.PASSWORD)
	private String newPassword;

	public PasswordChangeDTO() {
	}

	public PasswordChangeDTO(String oldPassword, String newPassword) {
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
}
