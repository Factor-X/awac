
package eu.factorx.awac.models.code.type;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@AttributeOverrides({@AttributeOverride(name = "key", column = @Column(name = "typeachat"))})
public class TYPEACHATCode extends Code {

    private static final long serialVersionUID = 1L;

    protected TYPEACHATCode() {
        super(CodeList.TYPEACHAT);
    }

    public TYPEACHATCode(String key) {
        this();
        this.key = key;
    }
public static final TYPEACHATCode METAUX = new TYPEACHATCode("1");
public static final TYPEACHATCode PLASTIQUES = new TYPEACHATCode("2");
public static final TYPEACHATCode PAPIER_OU_CARTON = new TYPEACHATCode("3");
public static final TYPEACHATCode VERRE = new TYPEACHATCode("4");
public static final TYPEACHATCode PRODUITS_CHIMIQUES = new TYPEACHATCode("5");
public static final TYPEACHATCode ROAD_MATERIALS = new TYPEACHATCode("6");
public static final TYPEACHATCode SERVICES_ET_INFORMATIQUE = new TYPEACHATCode("7");
public static final TYPEACHATCode AGRO_ALIMENTAIRE = new TYPEACHATCode("8");}