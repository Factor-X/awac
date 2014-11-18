package eu.factorx.awac.dto.myrmex.get;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.validation.annotations.Pattern;
import eu.factorx.awac.dto.validation.annotations.Size;

public class AnonymousPersonDTO extends DTO {

	@Pattern(regexp=Pattern.IDENTIFIER)
	private String identifier;

	@Size(min=1,max=255)
	private String firstName;

	@Size(min=1,max=255)
	private String lastName;

	@Pattern(regexp=Pattern.EMAIL)
	private String email;

	//password
	@Pattern(regexp = Pattern.PASSWORD)
	private String password;

	private Boolean isAdmin;

	private Boolean isActive;

	private String defaultLanguage;

	public AnonymousPersonDTO() {

	}

	public AnonymousPersonDTO(String identifier, String firstName, String lastName, String email, Boolean isAdmin, Boolean isActive, String defaultLanguage, String password) {
		this.identifier = identifier;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.isAdmin = isAdmin;
		this.isActive = isActive;
		this.defaultLanguage = defaultLanguage;
		this.password = password;
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

	public String getDefaultLanguage() {
		return defaultLanguage;
	}

	public void setDefaultLanguage(String defaultLanguage) {
		this.defaultLanguage = defaultLanguage;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "AnonymousPersonDTO{" +
				"identifier='" + identifier + '\'' +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", email='" + email + '\'' +
				", password='" + password + '\'' +
				", isAdmin=" + isAdmin +
				", isActive=" + isActive +
				", defaultLanguage='" + defaultLanguage + '\'' +
				'}';
	}
}
