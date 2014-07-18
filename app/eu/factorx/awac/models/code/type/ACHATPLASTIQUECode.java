
package eu.factorx.awac.models.code.type;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;

public class ACHATPLASTIQUECode extends Code {

    private static final long serialVersionUID = 1L;

    protected ACHATPLASTIQUECode() {
        super(CodeList.ACHATPLASTIQUE);
    }

    public ACHATPLASTIQUECode(String key) {
        this();
        this.key = key;
    }

    public static final ACHATPLASTIQUECode PLASTIQUE_MOYENNE = new ACHATPLASTIQUECode("1");
    public static final ACHATPLASTIQUECode POLYETHYLENE_BASSE_DENSITE = new ACHATPLASTIQUECode("2");
    public static final ACHATPLASTIQUECode PET = new ACHATPLASTIQUECode("3");
    public static final ACHATPLASTIQUECode POLYSTYRENE_MOYENNE = new ACHATPLASTIQUECode("4");
    public static final ACHATPLASTIQUECode PVC = new ACHATPLASTIQUECode("5");
    public static final ACHATPLASTIQUECode COMPOSITES_ET_POLYURETHANE_MOYENNE = new ACHATPLASTIQUECode("6");
    public static final ACHATPLASTIQUECode FILMS_PLASTIQUES_PET_PAS_RECYCLABLE = new ACHATPLASTIQUECode("7");
    public static final ACHATPLASTIQUECode PLASTIQUE_MOYENNE = new ACHATPLASTIQUECode("8");
}