
package eu.factorx.awac.models.code.type;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;

public class ACHATAGROCode extends Code {

	private static final long serialVersionUID = 1L;

	protected ACHATAGROCode() {
		super(CodeList.ACHATAGRO);
	}

	public ACHATAGROCode(String key) {
		this();
		this.key = key;
	}

	public static final ACHATAGROCode BLE_PAR_TONNE_DE_MATIERE_SECHE = new ACHATAGROCode("1");
	public static final ACHATAGROCode FOURRAGES_PAR_TONNE_DE_MATIERES_SECHES = new ACHATAGROCode("2");
	public static final ACHATAGROCode ENSILAGE = new ACHATAGROCode("3");
	public static final ACHATAGROCode FARINE = new ACHATAGROCode("4");
	public static final ACHATAGROCode PAIN = new ACHATAGROCode("5");
	public static final ACHATAGROCode MAIS_ALIMENTAIRE = new ACHATAGROCode("6");
	public static final ACHATAGROCode POMMES_DE_TERRE = new ACHATAGROCode("7");
	public static final ACHATAGROCode SALADES_DE_SAISON = new ACHATAGROCode("8");
	public static final ACHATAGROCode SALADE_SOUS_SERRE = new ACHATAGROCode("9");
	public static final ACHATAGROCode TOMATES_SAISON = new ACHATAGROCode("10");
	public static final ACHATAGROCode TOMATES_SOUS_SERRE = new ACHATAGROCode("11");
	public static final ACHATAGROCode TOMATES_MOYENNE = new ACHATAGROCode("12");
	public static final ACHATAGROCode CONCOMBRES_SAISON = new ACHATAGROCode("13");
	public static final ACHATAGROCode CONCOMBRE_SOUS_SERRE = new ACHATAGROCode("14");
	public static final ACHATAGROCode AUTRES_FRUITS_LEGUMES_DE_SAISON = new ACHATAGROCode("15");
	public static final ACHATAGROCode FRUITS_IMPORTES = new ACHATAGROCode("16");
	public static final ACHATAGROCode MOYENNE_TOUS_FRUIS_ET_LEGUMES_HORS_PDT = new ACHATAGROCode("17");
	public static final ACHATAGROCode HUILE_DE_COLZA = new ACHATAGROCode("18");
	public static final ACHATAGROCode HUILE_DE_TOURNESOL = new ACHATAGROCode("19");
	public static final ACHATAGROCode POISSON_PECHE_METROPOLE = new ACHATAGROCode("20");
	public static final ACHATAGROCode THON_ET_AUTRES_POISSONS_TROPICAUX_PECHE = new ACHATAGROCode("21");
	public static final ACHATAGROCode CREVETTES_PECHE = new ACHATAGROCode("22");
	public static final ACHATAGROCode AGNEAU = new ACHATAGROCode("23");
	public static final ACHATAGROCode MOUTON = new ACHATAGROCode("24");
	public static final ACHATAGROCode PORC = new ACHATAGROCode("25");
	public static final ACHATAGROCode BOEUF = new ACHATAGROCode("26");
	public static final ACHATAGROCode VEAU = new ACHATAGROCode("27");
	public static final ACHATAGROCode LAIT_DE_VACHE = new ACHATAGROCode("28");
	public static final ACHATAGROCode LAIT_EN_POUDRE = new ACHATAGROCode("29");
	public static final ACHATAGROCode BEURRE = new ACHATAGROCode("30");
	public static final ACHATAGROCode FROMAGE_PATE_MOLLE = new ACHATAGROCode("31");
	public static final ACHATAGROCode FROMAGE_PATE_DURE = new ACHATAGROCode("32");
	public static final ACHATAGROCode YAOURTS = new ACHATAGROCode("33");
	public static final ACHATAGROCode VOLAILLE_INDUSTRIELLE = new ACHATAGROCode("34");
	public static final ACHATAGROCode CANARD_ORDINAIRE = new ACHATAGROCode("35");
	public static final ACHATAGROCode POULET_FERMIER = new ACHATAGROCode("36");
	public static final ACHATAGROCode PINTADE_FERMIERE = new ACHATAGROCode("37");
	public static final ACHATAGROCode OEUFS = new ACHATAGROCode("38");
	public static final ACHATAGROCode SUCRE = new ACHATAGROCode("39");
	public static final ACHATAGROCode VIN = new ACHATAGROCode("40");
	public static final ACHATAGROCode ALCOOL_PUR = new ACHATAGROCode("41");
}