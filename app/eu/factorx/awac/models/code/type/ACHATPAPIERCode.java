
package eu.factorx.awac.models.code.type;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@AttributeOverrides({@AttributeOverride(name = "key", column = @Column(name = "achatpapier"))})
public class ACHATPAPIERCode extends Code {

    private static final long serialVersionUID = 1L;

    protected ACHATPAPIERCode() {
        super(CodeList.ACHATPAPIER);
    }

    public ACHATPAPIERCode(String key) {
        this();
        this.key = key;
    }
public static final ACHATPAPIERCode PAPIER = new ACHATPAPIERCode("1");
public static final ACHATPAPIERCode CARTON = new ACHATPAPIERCode("2");}