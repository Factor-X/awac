package eu.factorx.awac.dto.awac.get;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.myrmex.get.PersonDTO;

import java.util.List;

public class LoginResultDTO extends DTO {

	private PersonDTO person;

	private String defaultPeriod;

	private String organizationName;

    private Long defaultSiteId;

    private List<SiteDTO> mySites;

    private List<PeriodDTO> availablePeriods;

    public LoginResultDTO() {
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

    public List<SiteDTO> getMySites() {
        return mySites;
    }

    public void setMySites(List<SiteDTO> mySites) {
        this.mySites = mySites;
    }

    @Override
    public String toString() {
        return "LoginResultDTO{" +
                "person=" + person +
                ", defaultPeriod='" + defaultPeriod + '\'' +
                ", organizationName='" + organizationName + '\'' +
                ", mySites=" + mySites +
                '}';
    }

    public void setAvailablePeriods(List<PeriodDTO> availablePeriods) {
        this.availablePeriods = availablePeriods;
    }

    public List<PeriodDTO> getAvailablePeriods() {
        return availablePeriods;
    }
}
