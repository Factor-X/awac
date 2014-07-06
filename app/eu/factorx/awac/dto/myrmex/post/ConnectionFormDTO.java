package eu.factorx.awac.dto.myrmex.post;

import eu.factorx.awac.dto.DTO;

/**
 * Created by florian on 4/07/14.
 */
public class ConnectionFormDTO extends DTO{

    private final String login;
    private final String password;

    public ConnectionFormDTO(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
