package eu.factorx.awac.dto.myrmex.post;

import eu.factorx.awac.dto.DTO;
import play.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by florian on 4/07/14.
 */
public class ConnectionFormDTO extends DTO {

    private String login;
    private String password;

    protected ConnectionFormDTO() {
    }

    public ConnectionFormDTO(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public boolean testLogin() {

        if (login != null) {

            Pattern patternLogin = Pattern.compile("^.*$");

            Matcher matcherLogin = patternLogin.matcher(login);

            if (matcherLogin.find()) {
                Logger.info("LOGIN OK !!");
                return true;
            }
        }
        Logger.info("LOGIN WRONG !!");

        return false;
    }

    public boolean testPassword() {

        if (password != null) {

            Pattern patternPassword = Pattern.compile("^.*$");

            Matcher matcherPassword = patternPassword.matcher(password);

            if (matcherPassword.find()) {
                Logger.info("PASSWORD OK !!");
                return true;
            }
        }
        Logger.info("PASSWORD WRONG !!");

        return false;
    }

    public boolean controlForm() {

        if (testLogin() && testPassword()) {
            return true;
        }
        return false;
    }
}
