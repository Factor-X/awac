package eu.factorx.awac.dto.myrmex.post;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.validation.annotations.NotNull;
import eu.factorx.awac.dto.validation.annotations.Pattern;

/**
 * Created by florian on 4/07/14.
 */
public class ConnectionFormDTO extends DTO {

	@NotNull
	@Pattern(regexp = "/^.{5,20}$/", message = "Login between 5 and 20 letters")
	private String login;

	@NotNull
	@Pattern(regexp = "/^.{5,20}$/", message = "Password between 5 and 20 letters")
	private String password;

	protected ConnectionFormDTO() {
	}

	public ConnectionFormDTO(String login, String password) {
		this.login = login;
		this.password = password;
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
}
