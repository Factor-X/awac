package eu.factorx.awac.dto.awac.get;

import java.util.List;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.myrmex.get.PersonDTO;

public class LoginResultDTO extends DTO {

	private PersonDTO person;

	private Long defaultPeriod;

	private List<PeriodDTO> availablePeriods;
	private OrganizationDTO organization;

	public LoginResultDTO() {
	}

	public PersonDTO getPerson() {
		return person;
	}

	public void setPerson(PersonDTO person) {
		this.person = person;
	}

	public Long getDefaultPeriod() {
		return defaultPeriod;
	}

	public void setDefaultPeriod(Long defaultPeriod) {
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
}
