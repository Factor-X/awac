
package eu.factorx.awac.models.code.type;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@AttributeOverrides({@AttributeOverride(name = "key", column = @Column(name = "achatverre"))})
public class ACHATVERRECode extends Code {

    private static final long serialVersionUID = 1L;

    protected ACHATVERRECode() {
        super(CodeList.ACHATVERRE);
    }

    public ACHATVERRECode(String key) {
        this();
        this.key = key;
    }
public static final ACHATVERRECode VERRE_PLAT = new ACHATVERRECode("1");
public static final ACHATVERRECode VERRE_BOUTEILLE = new ACHATVERRECode("2");
public static final ACHATVERRECode VERRE_FLACONS_MOYENNE = new ACHATVERRECode("3");
public static final ACHATVERRECode VERRE_TECHNIQUE_MOYENNE = new ACHATVERRECode("4");
public static final ACHATVERRECode FIBRE_DE_VERRE_MOYENNE = new ACHATVERRECode("5");}