
package eu.factorx.awac.models.code.type;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;

public class MOTIFDEPLACEMENTCode extends Code {

    private static final long serialVersionUID = 1L;

    protected MOTIFDEPLACEMENTCode() {
        super(CodeList.MOTIFDEPLACEMENT);
    }

    public MOTIFDEPLACEMENTCode(String key) {
        this();
        this.key = key;
    }

    public static final MOTIFDEPLACEMENTCode CHOICE = new MOTIFDEPLACEMENTCode("1");
    public static final MOTIFDEPLACEMENTCode DEPLACEMENTS_DOMICILE_TRAVAIL = new MOTIFDEPLACEMENTCode("2");
    public static final MOTIFDEPLACEMENTCode DEPLACEMENTS_PROFESSIONNELS_ET_DES_VISITEURS = new MOTIFDEPLACEMENTCode("3");
}