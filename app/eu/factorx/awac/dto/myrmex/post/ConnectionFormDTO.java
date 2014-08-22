package eu.factorx.awac.dto.myrmex.post;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.validation.annotations.NotNull;
import eu.factorx.awac.dto.validation.annotations.Pattern;

/**
 * Created by florian on 4/07/14.
 */
public class ConnectionFormDTO extends DTO {

	@NotNull
	@Pattern(regexp = Pattern.IDENTIFIER, message = "Login between 5 and 20 letters")
	private String login;

	@NotNull
	@Pattern(regexp = Pattern.PASSWORD, message = "Password between 5 and 20 letters")
	private String password;

	private String interfaceName;

	@Pattern(regexp = Pattern.PASSWORD_OPTIONAL, message = "Password between 5 and 20 letters")
	private String newPassword = null;

	protected ConnectionFormDTO() {
	}

	public ConnectionFormDTO(String login, String password, String interfaceName, String newPassword) {
		this.login = login;
		this.password = password;
		this.interfaceName = interfaceName;
		this.newPassword = newPassword;
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

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	@Override
	public String toString() {
		return "ConnectionFormDTO{" +
				"login='" + login + '\'' +
				", password='" + password + '\'' +
				", interfaceName='" + interfaceName + '\'' +
				", newPassword='" + newPassword + '\'' +
				'}';
	}
}
