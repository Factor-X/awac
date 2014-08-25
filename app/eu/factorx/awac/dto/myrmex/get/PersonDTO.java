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

	private Boolean isAdmin;

	private Boolean isActive;

	public PersonDTO() {

	}

	public PersonDTO(String identifier, String firstName, String lastName, String email, Boolean isAdmin, Boolean isActive) {
		this.identifier = identifier;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.isAdmin = isAdmin;
		this.isActive = isActive;
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

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	@Override
	public String toString() {
		return "PersonDTO{" +
				"identifier='" + identifier + '\'' +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", email='" + email + '\'' +
				", isAdmin=" + isAdmin +
				", isActive=" + isActive +
				'}';
	}
}
