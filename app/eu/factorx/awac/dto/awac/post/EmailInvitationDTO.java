package eu.factorx.awac.dto.awac.post;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.validation.annotations.NotNull;
import eu.factorx.awac.dto.validation.annotations.Pattern;

public class EmailInvitationDTO extends DTO {


	@NotNull
	@Pattern(regexp = Pattern.EMAIL)
	private String invitationEmail;

	public EmailInvitationDTO() {
	}

	public EmailInvitationDTO(String email) {
		this.invitationEmail = email;
	}


	public String getInvitationEmail() {
		return invitationEmail;
	}

	public void setInvitationEmail(String email) {
		this.invitationEmail = email;
	}

	@Override
	public String toString() {
		return "EmailInvitationDTO [email=" + invitationEmail + "]";
	}

}
