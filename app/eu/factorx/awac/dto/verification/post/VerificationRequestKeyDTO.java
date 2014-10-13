package eu.factorx.awac.dto.verification.post;

import eu.factorx.awac.dto.DTO;

/**
 * Created by florian on 9/10/14.
 */
public class VerificationRequestKeyDTO  extends DTO{

    private String key;

    public VerificationRequestKeyDTO() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "VerificationRequestKeyDTO{" +
                "key='" + key + '\'' +
                '}';
    }
}
