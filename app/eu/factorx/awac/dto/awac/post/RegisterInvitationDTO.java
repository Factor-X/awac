package eu.factorx.awac.dto.awac.post;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.validation.annotations.NotNull;
import eu.factorx.awac.dto.validation.annotations.Pattern;

public class RegisterInvitationDTO extends DTO {


//	@NotNull
//	@Pattern(regexp = Pattern.EMAIL)

	private String login;
	private String password;
	private String lastName;
	private String firstName;
	private String interfaceName;
	private String email;
	private String key;

	public RegisterInvitationDTO(String login,
								 String password,
								 String lastName,
								 String firstName,
								 String interfaceName,
								 String email,
								 String key)	{

		this.login = login;
		this.password = password;
		this.lastName = lastName;
		this.firstName = firstName;
		this.interfaceName = interfaceName;
		this.key = key;
		this.email = email;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getEmail() {

		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public RegisterInvitationDTO() {
	}


	@Override
	public String toString() {
		return "RegisterInvitationDTO [" +
		"login=" + login +
		"password=" + password +
		"lastName=" + lastName +
		"firstName=" + firstName +
		"interfaceName=" + interfaceName +
		"key=" + key +
		"email=" + email +
				"]";
	}

}
