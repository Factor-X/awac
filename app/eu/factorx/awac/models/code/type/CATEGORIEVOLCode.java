
package eu.factorx.awac.models.code.type;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;

public class CATEGORIEVOLCode extends Code {

    private static final long serialVersionUID = 1L;

    protected CATEGORIEVOLCode() {
        super(CodeList.CATEGORIEVOL);
    }

    public CATEGORIEVOLCode(String key) {
        this();
        this.key = key;
    }

    public static final CATEGORIEVOLCode CLASSE_ECO = new CATEGORIEVOLCode("1");
    public static final CATEGORIEVOLCode CLASSE_BUSINESS = new CATEGORIEVOLCode("2");
    public static final CATEGORIEVOLCode PREMIERE_CLASSE = new CATEGORIEVOLCode("3");
}