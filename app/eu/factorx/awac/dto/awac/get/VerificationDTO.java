package eu.factorx.awac.dto.awac.get;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.myrmex.get.PersonDTO;

/**
 * Created by florian on 10/10/14.
 */
public class VerificationDTO extends DTO {

    private String status;

    private String comment;

    private PersonDTO verifier;

    public VerificationDTO() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public PersonDTO getVerifier() {
        return verifier;
    }

    public void setVerifier(PersonDTO verifier) {
        this.verifier = verifier;
    }

    @Override
    public String toString() {
        return "VerificationDTO{" +
                "status='" + status + '\'' +
                ", comment='" + comment + '\'' +
                ", verifier=" + verifier +
                '}';
    }
}
