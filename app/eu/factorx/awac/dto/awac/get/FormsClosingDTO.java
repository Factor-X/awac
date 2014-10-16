package eu.factorx.awac.dto.awac.get;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.verification.get.VerificationRequestDTO;

/**
 * Created by florian on 29/09/14.
 */
public class FormsClosingDTO extends DTO {

    private Boolean closed;

    private Boolean closeable;

    private VerificationRequestDTO verificationRequest;

    public FormsClosingDTO() {
    }

    public VerificationRequestDTO getVerificationRequest() {
        return verificationRequest;
    }

    public void setVerificationRequest(VerificationRequestDTO verificationRequest) {
        this.verificationRequest = verificationRequest;
    }

    public Boolean getClosed() {
        return closed;
    }

    public void setClosed(Boolean closed) {
        this.closed = closed;
    }

    public Boolean getCloseable() {
        return closeable;
    }

    public void setCloseable(Boolean closeable) {
        this.closeable = closeable;
    }

    @Override
    public String toString() {
        return "FormsClosedDTO{" +
                "closed=" + closed +
                ", closeable=" + closeable +
                '}';
    }
}
