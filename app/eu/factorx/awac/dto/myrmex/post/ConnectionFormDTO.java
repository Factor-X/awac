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

	protected ConnectionFormDTO() {
	}

	public ConnectionFormDTO(String login, String password, String interfaceName) {
		this.login = login;
		this.password = password;
		this.interfaceName = interfaceName;
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

	@Override
	public String toString() {
		return "ConnectionFormDTO{" +
				"interfaceName='" + interfaceName + '\'' +
				", password='" + password + '\'' +
				", login='" + login + '\'' +
				'}';
	}
}
