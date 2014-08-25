package eu.factorx.awac.dto.awac.get;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.myrmex.get.PersonDTO;

import java.util.List;

public class LoginResultDTO extends DTO {

	private PersonDTO person;

	private String defaultPeriod;

	private List<PeriodDTO> availablePeriods;
	private OrganizationDTO organization;

	public LoginResultDTO() {
	}

	public LoginResultDTO(PersonDTO person, String defaultPeriod, List<PeriodDTO> availablePeriods, OrganizationDTO organization) {
		this.person = person;
		this.defaultPeriod = defaultPeriod;
		this.availablePeriods = availablePeriods;
		this.organization = organization;
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

	public List<PeriodDTO> getAvailablePeriods() {
		return availablePeriods;
	}

	public void setAvailablePeriods(List<PeriodDTO> availablePeriods) {
		this.availablePeriods = availablePeriods;
	}

	public OrganizationDTO getOrganization() {
		return organization;
	}

	public void setOrganization(OrganizationDTO organization) {
		this.organization = organization;
	}

	@Override
	public String toString() {
		return "LoginResultDTO{" +
				"person=" + person +
				", defaultPeriod='" + defaultPeriod + '\'' +
				", availablePeriods=" + availablePeriods +
				", organization=" + organization +
				'}';
	}
}
