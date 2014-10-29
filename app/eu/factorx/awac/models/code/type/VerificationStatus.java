package eu.factorx.awac.models.code.type;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;

@Embeddable
@AttributeOverrides({ @AttributeOverride(name = "key", column = @Column(name = "verification_status")) })
public class VerificationStatus extends Code{

    private static final long serialVersionUID = 1L;

    public static final VerificationStatus APPROVED = new VerificationStatus("approved");
    public static final VerificationStatus REJECTED = new VerificationStatus("rejected");


    public VerificationStatus() {
        super(CodeList.VERIFICATION_STATUS);
    }

    public VerificationStatus(String key) {
        this();
        this.key = key;
    }
}
