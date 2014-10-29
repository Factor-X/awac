package eu.factorx.awac.dto.awac.post;

import java.util.List;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.awac.get.AccountDTO;
import eu.factorx.awac.dto.awac.get.SiteDTO;

public class SiteAddUsersDTO extends DTO {

	private SiteDTO site;

	private List<AccountDTO> selectedAccounts;

	public SiteAddUsersDTO() {
	}

	public SiteDTO getSite() {
		return site;
	}

	public void setSite(SiteDTO site) {
		this.site = site;
	}

	public List<AccountDTO> getSelectedAccounts() {
		return selectedAccounts;
	}

	public void setSelectedAccounts(List<AccountDTO> selectedAccounts) {
		this.selectedAccounts = selectedAccounts;
	}


    @Override
    public String toString() {
        return "SiteAddUsersDTO{" +
                "site=" + site +
                ", selectedAccounts=" + selectedAccounts +
                '}';
    }
}
