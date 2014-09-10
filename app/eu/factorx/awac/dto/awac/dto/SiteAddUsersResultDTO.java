package eu.factorx.awac.dto.awac.dto;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.awac.get.AccountDTO;
import eu.factorx.awac.dto.myrmex.get.PersonDTO;

import java.util.ArrayList;
import java.util.List;

public class SiteAddUsersResultDTO extends DTO {

	private List<AccountDTO> users;

	public SiteAddUsersResultDTO() {
	}

	public SiteAddUsersResultDTO(List<AccountDTO> list) {
		users = new ArrayList<AccountDTO>();
		users.addAll(list);
	}

	public List<AccountDTO> getUsers() {
		return users;
	}

	public void setUsers(List<AccountDTO> users) {
		this.users = users;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (AccountDTO account : users) {
			sb.append(account.getIdentifier());
		}
		return "SiteAddUsersResultDTO{" + sb.toString() +
				'}';
	}
}
