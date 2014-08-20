package eu.factorx.awac.dto.awac.post;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.myrmex.get.PersonDTO;
import eu.factorx.awac.dto.validation.annotations.Pattern;
import eu.factorx.awac.dto.validation.annotations.Size;

public class EnterpriseAccountCreationDTO extends DTO{

	//account data
	private PersonDTO personDTO;

	//password
	@Pattern(regexp = Pattern.PASSWORD)
	private String password;

	@Size(min = 1, max = 255)
	private String organizationName;

	public EnterpriseAccountCreationDTO() {
	}

	public EnterpriseAccountCreationDTO(PersonDTO personDTO, String password, String organizationName) {
		this.personDTO = personDTO;
		this.password = password;
		this.organizationName = organizationName;
	}

	public PersonDTO getPersonDTO() {
		return personDTO;
	}

	public void setPersonDTO(PersonDTO personDTO) {
		this.personDTO = personDTO;
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

	@Override
	public String toString() {
		return "EnterpriseAccountCreationDTO{" +
				"organizationName='" + organizationName + '\'' +
				", password='" + password + '\'' +
				", personDTO=" + personDTO +
				'}';
	}
}
