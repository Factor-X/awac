package eu.factorx.awac.models.code.type;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;

@Embeddable
@AttributeOverrides({ @AttributeOverride(name = "key", column = @Column(name = "verification_request_status")) })
public class VerificationRequestStatus  extends Code {

    private static final long serialVersionUID = 1L;

    public static final VerificationRequestStatus WAIT_VERIFIER_REGISTRATION = new VerificationRequestStatus("VERIFICATION_STATUS_WAIT_VERIFIER_REGISTRATION");
    public static final VerificationRequestStatus WAIT_VERIFIER_CONFIRMATION = new VerificationRequestStatus("VERIFICATION_STATUS_WAIT_VERIFIER_CONFIRMATION");
    public static final VerificationRequestStatus WAIT_CUSTOMER_CONFIRMATION = new VerificationRequestStatus("VERIFICATION_STATUS_WAIT_CUSTOMER_CONFIRMATION");
    public static final VerificationRequestStatus WAIT_ASSIGNATION = new VerificationRequestStatus("VERIFICATION_STATUS_WAIT_ASSIGNATION");
    public static final VerificationRequestStatus VERIFICATION = new VerificationRequestStatus("VERIFICATION_STATUS_VERIFICATION");
    public static final VerificationRequestStatus WAIT_VERIFICATION_CONFIRMATION_SUCCESS = new VerificationRequestStatus("VERIFICATION_STATUS_WAIT_VERIFICATION_CONFIRMATION_SUCCESS");
    public static final VerificationRequestStatus WAIT_VERIFICATION_CONFIRMATION_REJECT = new VerificationRequestStatus("VERIFICATION_STATUS_WAIT_VERIFICATION_CONFIRMATION_REJECT");
    public static final VerificationRequestStatus CORRECTION = new VerificationRequestStatus("VERIFICATION_STATUS_CORRECTION");
    public static final VerificationRequestStatus WAIT_CUSTOMER_VERIFIED_CONFIRMATION = new VerificationRequestStatus("VERIFICATION_STATUS_WAIT_CUSTOMER_VERIFIED_CONFIRMATION");
    public static final VerificationRequestStatus VERIFIED = new VerificationRequestStatus("VERIFICATION_STATUS_VERIFIED");
    public static final VerificationRequestStatus REJECTED = new VerificationRequestStatus("VERIFICATION_STATUS_REJECTED");


    public VerificationRequestStatus() {
        super(CodeList.VERIFICATION_REQUEST_STATUS);
    }

    public VerificationRequestStatus(String key) {
        this();
        this.key = key;
    }
}





