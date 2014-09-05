package eu.factorx.awac.dto.awac.post;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.awac.get.OrganizationDTO;
import eu.factorx.awac.dto.validation.annotations.NotNull;
import eu.factorx.awac.dto.validation.annotations.Pattern;
import eu.factorx.awac.models.business.Organization;

public class EmailInvitationDTO extends DTO {


	@NotNull
	@Pattern(regexp = Pattern.EMAIL)
	private String invitationEmail;

	private OrganizationDTO organization;

	public EmailInvitationDTO() {
	}

	public EmailInvitationDTO(String email, OrganizationDTO organization) {

		this.invitationEmail = email;
		this.organization = organization;
	}

	public String getInvitationEmail() {
		return invitationEmail;
	}
	public void setInvitationEmail(String email) {
		this.invitationEmail = email;
	}

	public OrganizationDTO getOrganization() {
		return organization;
	}

	public void setOrganization(OrganizationDTO organization) {
		this.organization = organization;
	}

	@Override
	public String toString() {
		return "EmailInvitationDTO [email=" + invitationEmail + "organization=" + organization.toString() + "]";
	}

}
