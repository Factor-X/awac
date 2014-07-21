package eu.factorx.awac.models.code.type;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@AttributeOverrides({@AttributeOverride(name = "key", column = @Column(name = "traitementeau"))})
public class TRAITEMENTEAUCode extends Code {

	public static final TRAITEMENTEAUCode REJET_EN_MER_RIVIERE_OU_LAC = new TRAITEMENTEAUCode("1");
	public static final TRAITEMENTEAUCode REJET_EN_STATION_D_EPURATION_NON_SATUREE = new TRAITEMENTEAUCode("2");
	public static final TRAITEMENTEAUCode REJET_EN_STATION_D_EPURATION_SATUREE = new TRAITEMENTEAUCode("3");
	public static final TRAITEMENTEAUCode METHANISEUR_DE_BOUES = new TRAITEMENTEAUCode("4");
	public static final TRAITEMENTEAUCode LAGUNAGE_ANAEROBIE = new TRAITEMENTEAUCode("5");
	private static final long serialVersionUID = 1L;
	protected TRAITEMENTEAUCode() {
		super(CodeList.TRAITEMENTEAU);
	}
	public TRAITEMENTEAUCode(String key) {
		this();
		this.key = key;
	}
}