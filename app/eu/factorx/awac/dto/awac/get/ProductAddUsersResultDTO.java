package eu.factorx.awac.dto.awac.get;

import eu.factorx.awac.dto.DTO;

import java.util.List;

/**
 * Created by florian on 26/11/14.
 */
public class ProductAddUsersResultDTO extends DTO{

	private List<AccountDTO> organizationUserList;

	private List<AccountDTO> productSelectedUserList;


	public ProductAddUsersResultDTO() {
	}

	public ProductAddUsersResultDTO(List<AccountDTO> organizationUserlist,List<AccountDTO> productSelectedUserList) {

		this.organizationUserList = organizationUserlist;
		this.productSelectedUserList = productSelectedUserList;

	}

	public List<AccountDTO> getOrganizationUserList() {
		return organizationUserList;
	}

	public void setOrganizationUserList(List<AccountDTO> organizationUserList) {
		this.organizationUserList = organizationUserList;
	}

	public List<AccountDTO> getProductSelectedUserList() {
		return productSelectedUserList;
	}

	public void setProductSelectedUserList(List<AccountDTO> productSelectedUserList) {
		this.productSelectedUserList = productSelectedUserList;
	}


	@Override
	public String toString() {

		StringBuffer sbAllUsers = new StringBuffer();
		for (AccountDTO account : this.organizationUserList) {
			sbAllUsers.append(account.getIdentifier());
		}

		StringBuffer sbProductUsers = new StringBuffer();
		for (AccountDTO account : this.productSelectedUserList) {
			sbAllUsers.append(account.getIdentifier());
		}

		return "ProductAddUsersResultDTO{" +
				"organizationUserList : " + sbAllUsers.toString() +
				"productSelectedUserList : " + sbProductUsers.toString() +
				'}';
	}
}
