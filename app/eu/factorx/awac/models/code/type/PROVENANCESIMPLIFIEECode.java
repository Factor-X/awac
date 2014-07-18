
package eu.factorx.awac.models.code.type;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;

public class PROVENANCESIMPLIFIEECode extends Code {

    private static final long serialVersionUID = 1L;

    protected PROVENANCESIMPLIFIEECode() {
        super(CodeList.PROVENANCESIMPLIFIEE);
    }

    public PROVENANCESIMPLIFIEECode(String key) {
        this();
        this.key = key;
    }

    public static final PROVENANCESIMPLIFIEECode BELGIQUE = new PROVENANCESIMPLIFIEECode("1");
    public static final PROVENANCESIMPLIFIEECode EUROPE = new PROVENANCESIMPLIFIEECode("2");
    public static final PROVENANCESIMPLIFIEECode MONDE = new PROVENANCESIMPLIFIEECode("3");
}