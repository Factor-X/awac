package eu.factorx.awac.models.code.type;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;

public class ACHATROUTECode extends Code {

	public static final ACHATROUTECode BETON_BITUMINEUX = new ACHATROUTECode("1");
	public static final ACHATROUTECode GRAVE_BITUME_3 = new ACHATROUTECode("2");
	public static final ACHATROUTECode ENROBE_A_MODULE_ELEVE = new ACHATROUTECode("3");
	public static final ACHATROUTECode ENROBE_TIEDE = new ACHATROUTECode("4");
	public static final ACHATROUTECode GRAVE_EMULSION = new ACHATROUTECode("5");
	public static final ACHATROUTECode BETON_BITUMINEUX_A_FROID = new ACHATROUTECode("6");
	public static final ACHATROUTECode GRAVE_CIMENT = new ACHATROUTECode("7");
	public static final ACHATROUTECode GRAVE_CIMENT_PREFISSUREE = new ACHATROUTECode("8");
	public static final ACHATROUTECode GRAVE_LIANT_HYDRAULIQUE = new ACHATROUTECode("9");
	public static final ACHATROUTECode GRAVE_LIANT_ROUTIER_PREFISSUREE = new ACHATROUTECode("10");
	public static final ACHATROUTECode BETON_DE_CIMENT_ROUTIER = new ACHATROUTECode("11");
	public static final ACHATROUTECode BETON_ARME_CONTINU_ROUTIER = new ACHATROUTECode("12");
	public static final ACHATROUTECode GRAVE_NON_TRAITEE = new ACHATROUTECode("13");
	public static final ACHATROUTECode SOL_TRAITE_LIANT_ROUTIER = new ACHATROUTECode("14");
	public static final ACHATROUTECode RECYCLAGE_EN_PLACE_A_CHAUD_REC = new ACHATROUTECode("15");
	public static final ACHATROUTECode BETON_BITUMINEUX_AVEC_10_PCT_REC = new ACHATROUTECode("16");
	public static final ACHATROUTECode BETON_BITUMINEUX_AVEC_20_PCT_REC = new ACHATROUTECode("17");
	public static final ACHATROUTECode BETON_BITUMINEUX_AVEC_30_PCT_REC = new ACHATROUTECode("18");
	public static final ACHATROUTECode BETON_BITUMINEUX_AVEC_50_PCT_REC = new ACHATROUTECode("19");
	public static final ACHATROUTECode RECYCLAGE_EN_PLACE_A_L_EMULSION = new ACHATROUTECode("20");
	public static final ACHATROUTECode CIMENT_PORTLAND = new ACHATROUTECode("21");
	public static final ACHATROUTECode CHAUX_VIVE = new ACHATROUTECode("22");
	public static final ACHATROUTECode PIERRES_DE_CARRIERE = new ACHATROUTECode("23");
	public static final ACHATROUTECode BETON_C25_30CEM_II = new ACHATROUTECode("24");
	public static final ACHATROUTECode BETON_ARME = new ACHATROUTECode("25");
	public static final ACHATROUTECode BOIS_COURTE_DUREE_DE_VIE = new ACHATROUTECode("26");
	public static final ACHATROUTECode BOIS_D_OEUVRE = new ACHATROUTECode("27");
	private static final long serialVersionUID = 1L;
	protected ACHATROUTECode() {
		super(CodeList.ACHATROUTE);
	}
	public ACHATROUTECode(String key) {
		this();
		this.key = key;
	}
}