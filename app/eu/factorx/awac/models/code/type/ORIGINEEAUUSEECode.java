
package eu.factorx.awac.models.code.type;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;

public class ORIGINEEAUUSEECode extends Code {

    private static final long serialVersionUID = 1L;

    protected ORIGINEEAUUSEECode() {
        super(CodeList.ORIGINEEAUUSEE);
    }

    public ORIGINEEAUUSEECode(String key) {
        this();
        this.key = key;
    }

    public static final ORIGINEEAUUSEECode EAUX_DE_RAFFINAGE_D_ALCOOL = new ORIGINEEAUUSEECode("1");
    public static final ORIGINEEAUUSEECode EAUX_DE_BIERE_ET_MALT = new ORIGINEEAUUSEECode("2");
    public static final ORIGINEEAUUSEECode EAUX_DE_CAFE = new ORIGINEEAUUSEECode("3");
    public static final ORIGINEEAUUSEECode EAUX_DE_PRODUITS_LAITIERS = new ORIGINEEAUUSEECode("4");
    public static final ORIGINEEAUUSEECode EAUX_DE_PREPARATION_DU_POISSON = new ORIGINEEAUUSEECode("5");
    public static final ORIGINEEAUUSEECode EAUX_DE_VIANDES_ET_VOLAILLES = new ORIGINEEAUUSEECode("6");
    public static final ORIGINEEAUUSEECode EAUX_DE_SUBSTANCES_CHIMIQUES_ORGANIQUES = new ORIGINEEAUUSEECode("7");
    public static final ORIGINEEAUUSEECode EAUX_DE_RAFFINERIES_DE_PETROLE = new ORIGINEEAUUSEECode("8");
    public static final ORIGINEEAUUSEECode EAUX_DE_PLASTIQUES_ET_RESINES = new ORIGINEEAUUSEECode("9");
    public static final ORIGINEEAUUSEECode EAUX_DE_PAPIER_ET_PATE_ENSEMBLE = new ORIGINEEAUUSEECode("10");
    public static final ORIGINEEAUUSEECode EAUX_DE_PRODUCTION_D_AMIDON = new ORIGINEEAUUSEECode("11");
    public static final ORIGINEEAUUSEECode EAUX_DE_RAFFINAGE_DU_SUCRE = new ORIGINEEAUUSEECode("12");
    public static final ORIGINEEAUUSEECode EAUX_DE_LEGUMES_ET_FRUITS_JUS = new ORIGINEEAUUSEECode("13");
    public static final ORIGINEEAUUSEECode EAUX_DE_VINS_ET_VINAIGRES = new ORIGINEEAUUSEECode("14");
}