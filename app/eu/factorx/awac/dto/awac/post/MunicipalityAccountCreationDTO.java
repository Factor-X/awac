package eu.factorx.awac.dto.awac.post;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.myrmex.get.PersonDTO;
import eu.factorx.awac.dto.validation.annotations.Pattern;
import eu.factorx.awac.dto.validation.annotations.Size;

public class MunicipalityAccountCreationDTO extends DTO{

	//account data
	private PersonDTO person;

	//password
	@Pattern(regexp = Pattern.PASSWORD)
	private String password;

	@Size(min = 1, max = 255)
	private String municipalityName;

	public MunicipalityAccountCreationDTO() {
	}

	public MunicipalityAccountCreationDTO(PersonDTO person, String password, String municipalityName) {
		this.person = person;
		this.password = password;
		this.municipalityName = municipalityName;
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

	public String getMunicipalityName() {
		return municipalityName;
	}

	public void setMunicipalityName(String municipalityName) {
		this.municipalityName = municipalityName;
	}

	@Override
	public String toString() {
		return "MunicipalityAccountCreationDTO{" +
				"person=" + person +
				", password='" + password + '\'' +
				", municipalityName='" + municipalityName + '\'' +
				'}';
	}
}
