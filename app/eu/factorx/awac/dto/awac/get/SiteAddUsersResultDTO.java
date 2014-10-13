package eu.factorx.awac.dto.awac.get;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.awac.get.AccountDTO;
import eu.factorx.awac.dto.myrmex.get.PersonDTO;

import java.util.ArrayList;
import java.util.List;

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
