package eu.factorx.awac.dto.awac.post;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.myrmex.get.PersonDTO;
import eu.factorx.awac.dto.validation.annotations.Pattern;

/**
 * Created by florian on 25/11/14.
 */
public class HouseholdAccountCreationDTO extends DTO {


	//account data
	private PersonDTO person;

	//password
	@Pattern(regexp = Pattern.PASSWORD)
	private String password;

	private Boolean organizationStatisticsAllowed;

	public HouseholdAccountCreationDTO() {
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

	public Boolean getOrganizationStatisticsAllowed() {
		return organizationStatisticsAllowed;
	}

	public void setOrganizationStatisticsAllowed(Boolean organizationStatisticsAllowed) {
		this.organizationStatisticsAllowed = organizationStatisticsAllowed;
	}

	@Override
	public String toString() {
		return "HouseholdAccountCreationDTO{" +
				"person=" + person +
				", password='" + password + '\'' +
				", organizationStatisticsAllowed=" + organizationStatisticsAllowed +
				'}';
	}
}
