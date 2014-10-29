package eu.factorx.awac.dto.awac.get;

import java.util.List;

import eu.factorx.awac.dto.DTO;

public class SiteAddUsersResultDTO extends DTO {

	private List<AccountDTO> organizationUserList;

	private List<AccountDTO> siteSelectedUserList;


	public SiteAddUsersResultDTO() {
	}

	public SiteAddUsersResultDTO(List<AccountDTO> organizationUserlist,List<AccountDTO> siteSelectedUserList) {

		this.organizationUserList = organizationUserlist;
		this.siteSelectedUserList = siteSelectedUserList;

	}

	public List<AccountDTO> getOrganizationUserList() {
		return organizationUserList;
	}

	public void setOrganizationUserList(List<AccountDTO> organizationUserList) {
		this.organizationUserList = organizationUserList;
	}

	public List<AccountDTO> getSiteSelectedUserList() {
		return siteSelectedUserList;
	}

	public void setSiteSelectedUserList(List<AccountDTO> siteSelectedUserList) {
		this.siteSelectedUserList = siteSelectedUserList;
	}


	@Override
	public String toString() {

		StringBuffer sbAllUsers = new StringBuffer();
		for (AccountDTO account : this.organizationUserList) {
			sbAllUsers.append(account.getIdentifier());
		}

		StringBuffer sbSiteUsers = new StringBuffer();
		for (AccountDTO account : this.siteSelectedUserList) {
			sbAllUsers.append(account.getIdentifier());
		}

		return "SiteAddUsersResultDTO{" +
				"organizationUserList : " + sbAllUsers.toString() +
				"siteSelectedUserList : " + sbSiteUsers.toString() +
				'}';
	}
}
