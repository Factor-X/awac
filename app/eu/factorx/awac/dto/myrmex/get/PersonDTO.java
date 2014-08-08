package eu.factorx.awac.dto.myrmex.get;

import eu.factorx.awac.dto.DTO;

/**
 * Created by florian on 4/07/14.
 */
public class PersonDTO extends DTO {
	private String identifier;
	private String firstName;
	private String lastName;
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
