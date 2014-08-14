package eu.factorx.awac.dto.awac.post;

import eu.factorx.awac.dto.DTO;

public class PasswordChangeDTO extends DTO {

	private String olPassword;

	private String newPassword;

	public PasswordChangeDTO(String olPassword, String newPassword) {
		this.olPassword = olPassword;
		this.newPassword = newPassword;
	}

	public String getOlPassword() {
		return olPassword;
	}

	public void setOlPassword(String olPassword) {
		this.olPassword = olPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
}
