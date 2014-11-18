package eu.factorx.awac.dto.awac.get;

import eu.factorx.awac.dto.DTO;

/**
 * Created by florian on 13/10/14.
 */
public class VerificationFinalizationDTO extends DTO{

    private boolean finalized;

    private boolean success;

    public VerificationFinalizationDTO() {
    }

    public VerificationFinalizationDTO(boolean finalized, boolean success) {
        this.finalized = finalized;
        this.success = success;
    }

    public boolean isFinalized() {
        return finalized;
    }

    public void setFinalized(boolean finalized) {
        this.finalized = finalized;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "VerificationFinalizationDTO{" +
                "finalized=" + finalized +
                ", success=" + success +
                '}';
    }
}
