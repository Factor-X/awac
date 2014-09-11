package eu.factorx.awac.dto.awac.dto;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.awac.get.AccountDTO;
import eu.factorx.awac.dto.awac.get.OrganizationDTO;
import eu.factorx.awac.dto.validation.annotations.NotNull;
import eu.factorx.awac.dto.validation.annotations.Pattern;

import java.util.List;

public class SiteAddUsersDTO extends DTO {


	private OrganizationDTO organization;

	private List<AccountDTO> selectedAccounts;

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

	public List<AccountDTO> getSelectedAccounts() {
		return selectedAccounts;
	}

	public void setSelectedAccounts(List<AccountDTO> selectedAccounts) {
		this.selectedAccounts = selectedAccounts;
	}

	@Override
	public String toString() {
		return "SiteAddUsersDTO [organization=" + organization.toString() + "]";
	}

}
