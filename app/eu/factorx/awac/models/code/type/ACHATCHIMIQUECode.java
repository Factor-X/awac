
package eu.factorx.awac.models.code.type;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;

public class ACHATCHIMIQUECode extends Code {

	private static final long serialVersionUID = 1L;

	protected ACHATCHIMIQUECode() {
		super(CodeList.ACHATCHIMIQUE);
	}

	public ACHATCHIMIQUECode(String key) {
		this();
		this.key = key;
	}

	public static final ACHATCHIMIQUECode AMMONITRATE_PAR_TONNE_D_AZOTE_AN = new ACHATCHIMIQUECode("1");
	public static final ACHATCHIMIQUECode AMMONITRATE_CALCAIRE_PAR_TONNE_D_AZOTE_CAN = new ACHATCHIMIQUECode("2");
	public static final ACHATCHIMIQUECode UREE_PAR_TONNE_D_AZOTE = new ACHATCHIMIQUECode("3");
	public static final ACHATCHIMIQUECode ENGRAIS_AZOTE_MOYEN_PAR_TONNE_D_AZOTE = new ACHATCHIMIQUECode("4");
	public static final ACHATCHIMIQUECode TSP_PAR_TONNE_DE_P2O5 = new ACHATCHIMIQUECode("5");
	public static final ACHATCHIMIQUECode ENGRAIS_PHOSPHATE_MOYEN_PAR_TONNE_DE_P2O5 = new ACHATCHIMIQUECode("6");
	public static final ACHATCHIMIQUECode POTASSE_PAR_TONNE_DE_K2O = new ACHATCHIMIQUECode("7");
	public static final ACHATCHIMIQUECode ENGRAIS_POTASSIQUE_MOYEN_PAR_TONNE_DE_K2O = new ACHATCHIMIQUECode("8");
	public static final ACHATCHIMIQUECode HERBICIDES_PAR_TONNE_DE_MATIERE_ACTIVE = new ACHATCHIMIQUECode("9");
	public static final ACHATCHIMIQUECode FONGICIDES_PAR_TONNE_DE_MATIERE_ACTIVE = new ACHATCHIMIQUECode("10");
	public static final ACHATCHIMIQUECode INSECTICIDES_PAR_TONNE_DE_MATIERE_ACTIVE = new ACHATCHIMIQUECode("11");
	public static final ACHATCHIMIQUECode AUTRES_PPP_PAR_TONNE_DE_MATIERE_ACTIVE = new ACHATCHIMIQUECode("12");
	public static final ACHATCHIMIQUECode REGULATEUR_DE_CROISSANCE_PAR_TONNE_DE_MATIERE_ACTIVE = new ACHATCHIMIQUECode("13");
	public static final ACHATCHIMIQUECode ASP_PAR_TONNE_DE_P = new ACHATCHIMIQUECode("14");
	public static final ACHATCHIMIQUECode SCORIES_THOMAS_PAR_TONNE_DE_P = new ACHATCHIMIQUECode("15");
	public static final ACHATCHIMIQUECode HEXANE = new ACHATCHIMIQUECode("16");
	public static final ACHATCHIMIQUECode SOUDE_50_PCT = new ACHATCHIMIQUECode("17");
	public static final ACHATCHIMIQUECode ACIDE_PHOSPHORIQUE = new ACHATCHIMIQUECode("18");
	public static final ACHATCHIMIQUECode METHANOL = new ACHATCHIMIQUECode("19");
	public static final ACHATCHIMIQUECode ACIDE_SULFURIQUE = new ACHATCHIMIQUECode("20");
	public static final ACHATCHIMIQUECode ACIDE_CHLORHYDRIQUE = new ACHATCHIMIQUECode("21");
	public static final ACHATCHIMIQUECode NYLON = new ACHATCHIMIQUECode("22");
	public static final ACHATCHIMIQUECode ALCOOL = new ACHATCHIMIQUECode("23");
}