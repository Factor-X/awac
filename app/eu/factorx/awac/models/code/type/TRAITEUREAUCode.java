
package eu.factorx.awac.models.code.type;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@AttributeOverrides({@AttributeOverride(name = "key", column = @Column(name = "traiteureau"))})
public class TRAITEUREAUCode extends Code {

    private static final long serialVersionUID = 1L;

    protected TRAITEUREAUCode() {
        super(CodeList.TRAITEUREAU);
    }

    public TRAITEUREAUCode(String key) {
        this();
        this.key = key;
    }
public static final TRAITEUREAUCode PAR_L_ENTREPRISE = new TRAITEUREAUCode("1");
public static final TRAITEUREAUCode PAR_DES_TIERS = new TRAITEUREAUCode("2");}