package eu.factorx.awac.models.code.type;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;

public class INFRASTRUCTURECode extends Code {

	public static final INFRASTRUCTURECode INFRASTRUCTURE = new INFRASTRUCTURECode("1");
	public static final INFRASTRUCTURECode LOGEMENTS_BETON = new INFRASTRUCTURECode("2");
	public static final INFRASTRUCTURECode BATIMENTS_AGRICOLES_BETON = new INFRASTRUCTURECode("3");
	public static final INFRASTRUCTURECode BATIMENTS_AGRICOLES_METAL = new INFRASTRUCTURECode("4");
	public static final INFRASTRUCTURECode BAT_INDUSTRIELS_BETON = new INFRASTRUCTURECode("5");
	public static final INFRASTRUCTURECode BAT_INDUSTRIELS_METAL = new INFRASTRUCTURECode("6");
	public static final INFRASTRUCTURECode GARAGES_BETON = new INFRASTRUCTURECode("7");
	public static final INFRASTRUCTURECode GARAGES_METAL = new INFRASTRUCTURECode("8");
	public static final INFRASTRUCTURECode COMMERCES_BETON = new INFRASTRUCTURECode("9");
	public static final INFRASTRUCTURECode COMMERCES_METAL = new INFRASTRUCTURECode("10");
	public static final INFRASTRUCTURECode BUREAUX_BETON = new INFRASTRUCTURECode("11");
	public static final INFRASTRUCTURECode BUREAUX_METAL = new INFRASTRUCTURECode("12");
	public static final INFRASTRUCTURECode ENSEIGNEMENT_BETON = new INFRASTRUCTURECode("13");
	public static final INFRASTRUCTURECode SANTE_BETON = new INFRASTRUCTURECode("14");
	public static final INFRASTRUCTURECode LOISIRS_BETON = new INFRASTRUCTURECode("15");
	public static final INFRASTRUCTURECode LOISIRS_METAL = new INFRASTRUCTURECode("16");
	public static final INFRASTRUCTURECode PARKINGS_NORMAUX = new INFRASTRUCTURECode("17");
	public static final INFRASTRUCTURECode PARKINGS_ROUTIERS_INTENSIFS = new INFRASTRUCTURECode("18");
	public static final INFRASTRUCTURECode PC_AVEC_ECRAN_PLAT = new INFRASTRUCTURECode("19");
	public static final INFRASTRUCTURECode PHOTOCOPIEURS_GROSSE_IMPRIMANTE = new INFRASTRUCTURECode("20");
	public static final INFRASTRUCTURECode VEHICULES = new INFRASTRUCTURECode("21");
	public static final INFRASTRUCTURECode MACHINES = new INFRASTRUCTURECode("22");
	public static final INFRASTRUCTURECode MOBILIER = new INFRASTRUCTURECode("23");
	public static final INFRASTRUCTURECode VOIRIE = new INFRASTRUCTURECode("24");
	private static final long serialVersionUID = 1L;
	protected INFRASTRUCTURECode() {
		super(CodeList.INFRASTRUCTURE);
	}
	public INFRASTRUCTURECode(String key) {
		this();
		this.key = key;
	}
}