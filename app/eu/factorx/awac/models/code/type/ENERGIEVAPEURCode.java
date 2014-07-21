
package eu.factorx.awac.models.code.type;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@AttributeOverrides({@AttributeOverride(name = "key", column = @Column(name = "energievapeur"))})
public class ENERGIEVAPEURCode extends Code {

    private static final long serialVersionUID = 1L;

    protected ENERGIEVAPEURCode() {
        super(CodeList.ENERGIEVAPEUR);
    }

    public ENERGIEVAPEURCode(String key) {
        this();
        this.key = key;
    }
public static final ENERGIEVAPEURCode BIOGAZ = new ENERGIEVAPEURCode("1");
public static final ENERGIEVAPEURCode AUTRES_PRODUITS_PETROLIERS_PARAFFINES_CIRES = new ENERGIEVAPEURCode("2");
public static final ENERGIEVAPEURCode BOIS_COPEAUX_DE_BOIS = new ENERGIEVAPEURCode("3");
public static final ENERGIEVAPEURCode BUTANE = new ENERGIEVAPEURCode("4");
public static final ENERGIEVAPEURCode CHARBON = new ENERGIEVAPEURCode("5");
public static final ENERGIEVAPEURCode COKE = new ENERGIEVAPEURCode("6");
public static final ENERGIEVAPEURCode ESSENCE = new ENERGIEVAPEURCode("7");
public static final ENERGIEVAPEURCode DIESEL_GASOIL_FUEL_LEGER = new ENERGIEVAPEURCode("8");
public static final ENERGIEVAPEURCode FUEL_LOURD = new ENERGIEVAPEURCode("9");
public static final ENERGIEVAPEURCode GAZ_NATUREL_RICHE = new ENERGIEVAPEURCode("10");
public static final ENERGIEVAPEURCode GRAISSES = new ENERGIEVAPEURCode("11");
public static final ENERGIEVAPEURCode HUILES = new ENERGIEVAPEURCode("12");
public static final ENERGIEVAPEURCode GAZ_DE_PETROLE_LIQUEFIE_GPL = new ENERGIEVAPEURCode("13");
public static final ENERGIEVAPEURCode METHANE = new ENERGIEVAPEURCode("14");
public static final ENERGIEVAPEURCode PROPANE = new ENERGIEVAPEURCode("15");}