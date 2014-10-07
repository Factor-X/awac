package eu.factorx.awac.dto.awac.post;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.myrmex.get.PersonDTO;
import eu.factorx.awac.dto.validation.annotations.Pattern;
import eu.factorx.awac.dto.validation.annotations.Size;

public class RegistrationDTO extends DTO{

	//account data
	private PersonDTO person;

	//password
	@Pattern(regexp = Pattern.PASSWORD)
	private String password;

	@Size(min = 1, max = 255)
	private String organizationName;

	public RegistrationDTO() {
	}

	public RegistrationDTO(PersonDTO person, String password, String organizationName) {
		this.person = person;
		this.password = password;
		this.organizationName = organizationName;
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

    @Override
	public String toString() {
		return "MunicipalityAccountCreationDTO{" +
				"person=" + person +
				", password='" + password + '\'' +
				", organizationName='" + organizationName + '\'' +
				'}';
	}
}
