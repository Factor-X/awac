package eu.factorx.awac.dto.myrmex.get;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.validation.annotations.Pattern;
import eu.factorx.awac.dto.validation.annotations.Size;

public class PersonDTO extends DTO {

	@Pattern(regexp=Pattern.IDENTIFIER)
	private String identifier;

	@Size(min=1,max=255)
	private String firstName;

	@Size(min=1,max=255)
	private String lastName;

	@Pattern(regexp=Pattern.EMAIL)
	private String email;

	public PersonDTO() {
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
