package eu.factorx.awac.dto.awac.get;

import java.util.List;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.myrmex.get.PersonDTO;

public class LoginResultDTO extends DTO {

	private PersonDTO person;

	private String defaultPeriod;

	private Long organizationId;

	private String organizationName;

    private Long defaultSiteId;

    private List<ScopeDTO> myScopes;

    private List<PeriodDTO> availablePeriods;

    public LoginResultDTO() {
	}

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	public Long getDefaultSiteId() {
        return defaultSiteId;
    }

    public void setDefaultSiteId(Long defaultSiteId) {
        this.defaultSiteId = defaultSiteId;
    }

    public PersonDTO getPerson() {
        return person;
    }

    public void setPerson(PersonDTO person) {
        this.person = person;
    }

    public String getDefaultPeriod() {
        return defaultPeriod;
    }

    public void setDefaultPeriod(String defaultPeriod) {
        this.defaultPeriod = defaultPeriod;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public List<ScopeDTO> getMyScopes() {
        return myScopes;
    }

    public void setMyScopes(List<ScopeDTO> myScopes) {
        this.myScopes = myScopes;
    }

    @Override
    public String toString() {
        return "LoginResultDTO{" +
                "person=" + person +
                ", defaultPeriod='" + defaultPeriod + '\'' +
                ", organizationName='" + organizationName + '\'' +
                ", defaultSiteId=" + defaultSiteId +
                ", myScopes=" + myScopes +
                ", availablePeriods=" + availablePeriods +
                '}';
    }

    public void setAvailablePeriods(List<PeriodDTO> availablePeriods) {
        this.availablePeriods = availablePeriods;
    }

    public List<PeriodDTO> getAvailablePeriods() {
        return availablePeriods;
    }
}
