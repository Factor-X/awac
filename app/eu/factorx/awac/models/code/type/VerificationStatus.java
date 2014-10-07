package eu.factorx.awac.models.code.type;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@AttributeOverrides({ @AttributeOverride(name = "key", column = @Column(name = "verificationstatus")) })
public class VerificationStatus extends Code{

    private static final long serialVersionUID = 1L;

    public static final VerificationStatus VALID = new VerificationStatus("valid");
    public static final VerificationStatus UNVALID = new VerificationStatus("unvalid");


    public VerificationStatus() {
        super(CodeList.VERIFICATION_STATUS);
    }

    public VerificationStatus(String key) {
        this();
        this.key = key;
    }
}
