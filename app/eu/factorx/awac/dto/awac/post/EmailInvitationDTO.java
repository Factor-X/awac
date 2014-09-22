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

	//private String organizationName;

	//private OrganizationDTO organization;

	public EmailInvitationDTO() {
	}

//	public EmailInvitationDTO(String email, String organizationName, OrganizationDTO organization) {
//
//		this.invitationEmail = email;
//		this.organizationName = organizationName;
//		//this.organization = organization;
//	}

	public EmailInvitationDTO(String email) {

		this.invitationEmail = email;
//		this.organizationName = organizationName;
	}

//	public String getOrganizationName() {
//		return organizationName;
//	}
//
//	public void setOrganizationName(String organizationName) {
//		this.organizationName = organizationName;
//	}

	public String getInvitationEmail() {
		return invitationEmail;
	}
	public void setInvitationEmail(String email) {
		this.invitationEmail = email;
	}

//	public OrganizationDTO getOrganization() {
//		return organization;
//	}
//
//	public void setOrganization(OrganizationDTO organization) {
//		this.organization = organization;
//	}

	@Override
	public String toString() {
		return "EmailInvitationDTO{" +
				"invitationEmail='" + invitationEmail + '\'' +
				'}';
	}

}
