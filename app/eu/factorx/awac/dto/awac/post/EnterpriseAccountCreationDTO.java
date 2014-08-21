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

	private String interfaceCode;

	public EnterpriseAccountCreationDTO() {
	}

	public EnterpriseAccountCreationDTO(PersonDTO personDTO, String password, String organizationName, String interfaceCode) {
		this.personDTO = personDTO;
		this.password = password;
		this.organizationName = organizationName;
		this.interfaceCode = interfaceCode;
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

	public String getInterfaceCode() {
		return interfaceCode;
	}

	public void setInterfaceCode(String interfaceCode) {
		this.interfaceCode = interfaceCode;
	}

	@Override
	public String toString() {
		return "EnterpriseAccountCreationDTO{" +
				"interfaceCode='" + interfaceCode + '\'' +
				", organizationName='" + organizationName + '\'' +
				", password='" + password + '\'' +
				", personDTO=" + personDTO +
				'}';
	}
}
