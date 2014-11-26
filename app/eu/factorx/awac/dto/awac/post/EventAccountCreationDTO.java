package eu.factorx.awac.dto.awac.post;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.myrmex.get.PersonDTO;
import eu.factorx.awac.dto.validation.annotations.Pattern;
import eu.factorx.awac.dto.validation.annotations.Size;

public class EventAccountCreationDTO extends DTO{

	//account data
	private PersonDTO person;

	//password
	@Pattern(regexp = Pattern.PASSWORD)
	private String password;

	@Size(min = 1, max = 255)
	private String organizationName;

	private Boolean organizationStatisticsAllowed;

	private String firstProductName;

	public EventAccountCreationDTO() {
	}

	public EventAccountCreationDTO(PersonDTO person, String password, String organizationName, Boolean organizationStatisticsAllowed, String firstSiteName) {
		this.person = person;
		this.password = password;
		this.organizationName = organizationName;
		this.organizationStatisticsAllowed = organizationStatisticsAllowed;
		this.firstProductName = firstSiteName;
	}

	public PersonDTO getPerson() {
		return person;
	}

	public void setPerson(PersonDTO person) {
		this.person = person;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public Boolean getOrganizationStatisticsAllowed() {
		return organizationStatisticsAllowed;
	}

	public void setOrganizationStatisticsAllowed(Boolean organizationStatisticsAllowed) {
		this.organizationStatisticsAllowed = organizationStatisticsAllowed;
	}

	public String getFirstProductName() {
		return firstProductName;
	}

	public void setFirstProductName(String firstProductName) {
		this.firstProductName = firstProductName;
	}

	@Override
	public String toString() {
		return "EventAccountCreationDTO{" +
				"person=" + person +
				", password='" + password + '\'' +
				", organizationName='" + organizationName + '\'' +
				", organizationStatisticsAllowed=" + organizationStatisticsAllowed +
				", firstProductName='" + firstProductName + '\'' +
				'}';
	}
}
