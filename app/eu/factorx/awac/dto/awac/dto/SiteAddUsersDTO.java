package eu.factorx.awac.dto.awac.dto;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.awac.get.OrganizationDTO;
import eu.factorx.awac.dto.validation.annotations.NotNull;
import eu.factorx.awac.dto.validation.annotations.Pattern;

public class SiteAddUsersDTO extends DTO {


	private OrganizationDTO organization;

	public SiteAddUsersDTO() {
	}

	public SiteAddUsersDTO(OrganizationDTO organization) {
		this.organization = organization;
	}

	public OrganizationDTO getOrganization() {
		return organization;
	}

	public void setOrganization(OrganizationDTO organization) {
		this.organization = organization;
	}

	@Override
	public String toString() {
		return "SiteAddUsersDTO [organization=" + organization.toString() + "]";
	}

}
