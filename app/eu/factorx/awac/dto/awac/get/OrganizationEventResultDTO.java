package eu.factorx.awac.dto.awac.get;

import java.util.List;

import eu.factorx.awac.dto.DTO;

public class OrganizationEventResultDTO extends DTO {


	List<OrganizationEventDTO> organizationEventList;

	public OrganizationEventResultDTO() {
	}

	public OrganizationEventResultDTO(List<OrganizationEventDTO> organizationEventlist) {
		this.organizationEventList = organizationEventlist;
	}

	public List<OrganizationEventDTO> getOrganizationEventList() {
		return organizationEventList;
	}

	public void setOrganizationEventList(List<OrganizationEventDTO> organizationEventList) {
		this.organizationEventList = organizationEventList;
	}

	@Override
	public String toString() {

		return "SiteAddUsersResultDTO{" +
				"organizationEventList : " + organizationEventList.toString() +
				'}';
	}
}
