package eu.factorx.awac.dto.awac.post;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.myrmex.get.PersonDTO;
import eu.factorx.awac.dto.validation.annotations.NotNull;
import eu.factorx.awac.dto.validation.annotations.Pattern;

public class RegisterInvitationDTO extends DTO {

    @NotNull
    @Pattern(regexp = Pattern.PASSWORD, message = "Password between 5 and 20 letters")
	private String password;

    @NotNull
    private PersonDTO person;

    @NotNull
	private String key;

    public RegisterInvitationDTO() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public PersonDTO getPerson() {
        return person;
    }

    public void setPerson(PersonDTO person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return "RegisterInvitationDTO{" +
                "password='" + password + '\'' +
                ", person=" + person +
                ", key='" + key + '\'' +
                '}';
    }
}
