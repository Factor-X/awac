package eu.factorx.awac.dto.verification.post;

import eu.factorx.awac.dto.awac.post.RegistrationDTO;
import eu.factorx.awac.dto.myrmex.get.PersonDTO;
import eu.factorx.awac.dto.validation.annotations.NotNull;

/**
 * Created by florian on 8/10/14.
 */
public class VerificationRegistrationDTO extends RegistrationDTO{

    @NotNull
    private String key;

    public VerificationRegistrationDTO(){
        super();
    }

    public VerificationRegistrationDTO(PersonDTO person, String password, String organizationName, Boolean organizationStatisticsAllowed, String key) {
        super(person, password, organizationName, organizationStatisticsAllowed);
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "VerificationRegistrationDTO{" +super.toString()+
                "key='" + key + '\'' +
                '}';
    }
}
