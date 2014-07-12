package eu.factorx.awac.models.code.type;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;

@Embeddable
@AttributeOverrides({ @AttributeOverride(name = "key", column = @Column(name = "activitysource")) })
public class ActivitySourceCode extends Code {

	private static final long serialVersionUID = 1L;

	public static final ActivitySourceCode DIESEL_GASOIL_OU_FUEL_LEGER = new ActivitySourceCode("1");
	public static final ActivitySourceCode GAZ_DE_PETROLE_LIQUEFIE_GPL = new ActivitySourceCode("2");
	public static final ActivitySourceCode GAZ_NATUREL_PAUVRE = new ActivitySourceCode("3");
	public static final ActivitySourceCode ESSENCE = new ActivitySourceCode("4");
	public static final ActivitySourceCode BIODIESEL = new ActivitySourceCode("5");
	public static final ActivitySourceCode BIOETHANOL = new ActivitySourceCode("6");
	public static final ActivitySourceCode BIOGAZ = new ActivitySourceCode("7");
	public static final ActivitySourceCode GAZ_DE_DECHARGE = new ActivitySourceCode("8");
	public static final ActivitySourceCode CHARBON = new ActivitySourceCode("9");
	public static final ActivitySourceCode BOIS_BUCHE = new ActivitySourceCode("10");
	public static final ActivitySourceCode BOIS_COPEAUX = new ActivitySourceCode("11");
	public static final ActivitySourceCode BOIS_PELLETS = new ActivitySourceCode("12");
	public static final ActivitySourceCode HERBE_PAILLE = new ActivitySourceCode("13");
	public static final ActivitySourceCode PROPANE = new ActivitySourceCode("14");
	public static final ActivitySourceCode BUTANE = new ActivitySourceCode("15");
	public static final ActivitySourceCode AGGLOMERE_DE_CHARBON_OU_DE_LIGNITE = new ActivitySourceCode("16");
	public static final ActivitySourceCode ANTHRACITE = new ActivitySourceCode("17");
	public static final ActivitySourceCode AUTRES_PRODUITS_PETROLIERS_PARAFFINES_CIRES = new ActivitySourceCode("18");
	public static final ActivitySourceCode BITUMES = new ActivitySourceCode("19");
	public static final ActivitySourceCode BRIQUETTE_DE_LIGNITE = new ActivitySourceCode("20");
	public static final ActivitySourceCode COKE = new ActivitySourceCode("21");
	public static final ActivitySourceCode COKE_DE_PETROLE = new ActivitySourceCode("22");
	public static final ActivitySourceCode FUEL_LOURD = new ActivitySourceCode("23");
	public static final ActivitySourceCode GAZ_DE_COKERIE = new ActivitySourceCode("24");
	public static final ActivitySourceCode GAZ_DE_HAUT_FOURNEAU = new ActivitySourceCode("25");
	public static final ActivitySourceCode GAZ_NATUREL_RICHE = new ActivitySourceCode("26");
	public static final ActivitySourceCode GOUDRON = new ActivitySourceCode("27");
	public static final ActivitySourceCode GRAISSES = new ActivitySourceCode("28");
	public static final ActivitySourceCode HUILES = new ActivitySourceCode("29");
	public static final ActivitySourceCode KEROSENE = new ActivitySourceCode("30");
	public static final ActivitySourceCode LIGNITE = new ActivitySourceCode("31");
	public static final ActivitySourceCode METHANE = new ActivitySourceCode("32");
	public static final ActivitySourceCode NAPHTA = new ActivitySourceCode("33");
	public static final ActivitySourceCode PETROLE_BRUT = new ActivitySourceCode("34");
	public static final ActivitySourceCode PETROLE_LAMPANT = new ActivitySourceCode("35");
	public static final ActivitySourceCode TERRIL = new ActivitySourceCode("36");
	public static final ActivitySourceCode TOURBE = new ActivitySourceCode("37");
	public static final ActivitySourceCode NATURAL_GAS = new ActivitySourceCode("38");
	public static final ActivitySourceCode GAS_DIESEL_OIL = new ActivitySourceCode("39");
	public static final ActivitySourceCode WOOD_WOOD_WASTE = new ActivitySourceCode("40");
	public static final ActivitySourceCode BELGIQUE = new ActivitySourceCode("41");
	public static final ActivitySourceCode MOYENNE_UE27 = new ActivitySourceCode("42");
	public static final ActivitySourceCode BOIS_COPEAUX_DE_BOIS = new ActivitySourceCode("43");
	public static final ActivitySourceCode DIESEL_GASOIL_FUEL_LEGER = new ActivitySourceCode("44");
	public static final ActivitySourceCode _403B = new ActivitySourceCode("45");
	public static final ActivitySourceCode _404A = new ActivitySourceCode("46");
	public static final ActivitySourceCode _407C = new ActivitySourceCode("47");
	public static final ActivitySourceCode _409A = new ActivitySourceCode("48");
	public static final ActivitySourceCode _410A = new ActivitySourceCode("49");
	public static final ActivitySourceCode _413A = new ActivitySourceCode("50");
	public static final ActivitySourceCode CARBON_TETRACHLORIDE_CCL4 = new ActivitySourceCode("51");
	public static final ActivitySourceCode CFC_11_R11_TRICHLOROFLUOROMETHANE_CCL3F = new ActivitySourceCode("52");
	public static final ActivitySourceCode CFC_113_CCL2FCCLF2 = new ActivitySourceCode("53");
	public static final ActivitySourceCode CFC_114_CCLF2CCLF2 = new ActivitySourceCode("54");
	public static final ActivitySourceCode CFC_115_CCLF2CF3 = new ActivitySourceCode("55");
	public static final ActivitySourceCode CFC_12_R12_DICHLORODIFLUOROMETHANE_CCL2F2 = new ActivitySourceCode("56");
	public static final ActivitySourceCode CFC_13_CCLF3 = new ActivitySourceCode("57");
	public static final ActivitySourceCode DIMETHYLETHER_CH3OCH3 = new ActivitySourceCode("58");
	public static final ActivitySourceCode DIOXYDE_DE_CARBONE_CO2 = new ActivitySourceCode("59");
	public static final ActivitySourceCode HALON_1211_CBRCLF2 = new ActivitySourceCode("61");
	public static final ActivitySourceCode HALON_1301_CBRF3 = new ActivitySourceCode("62");
	public static final ActivitySourceCode HALON_2402_CBRF2CBRF2 = new ActivitySourceCode("63");
	public static final ActivitySourceCode HCFC_123_CHCL2CF3 = new ActivitySourceCode("64");
	public static final ActivitySourceCode HCFC_124_CHCLFCF3 = new ActivitySourceCode("65");
	public static final ActivitySourceCode HCFC_141B_CH3CCL2F = new ActivitySourceCode("66");
	public static final ActivitySourceCode HCFC_142B_CH3CCLF2 = new ActivitySourceCode("67");
	public static final ActivitySourceCode HCFC_22_R22_CHLORODIFLUOROMETHANE_CHCLF2 = new ActivitySourceCode("68");
	public static final ActivitySourceCode HCFC_225CA_CHCL2CF2CF3 = new ActivitySourceCode("69");
	public static final ActivitySourceCode HCFC_225CB_CHCLFCF2CCLF2 = new ActivitySourceCode("70");
	public static final ActivitySourceCode HCFE_235DA2_CHF2OCHCLCF3 = new ActivitySourceCode("71");
	public static final ActivitySourceCode HEXAFLUORURE_DE_SOUFFRE_SF6 = new ActivitySourceCode("72");
	public static final ActivitySourceCode HFC_125 = new ActivitySourceCode("74");
	public static final ActivitySourceCode HFC_125_CHF2CF3 = new ActivitySourceCode("75");
	public static final ActivitySourceCode HFC_134 = new ActivitySourceCode("76");
	public static final ActivitySourceCode HFC_134_CHF2CHF2 = new ActivitySourceCode("77");
	public static final ActivitySourceCode HFC_134A_CH2FCF3 = new ActivitySourceCode("78");
	public static final ActivitySourceCode HFC_143 = new ActivitySourceCode("79");
	public static final ActivitySourceCode HFC_143_CH3CF3 = new ActivitySourceCode("80");
	public static final ActivitySourceCode HFC_143A = new ActivitySourceCode("81");
	public static final ActivitySourceCode HFC_143A_CH3CHF2 = new ActivitySourceCode("82");
	public static final ActivitySourceCode HFC_152A = new ActivitySourceCode("83");
	public static final ActivitySourceCode HFC_152A_CF3CHFCF3 = new ActivitySourceCode("84");
	public static final ActivitySourceCode HFC_227EA = new ActivitySourceCode("85");
	public static final ActivitySourceCode HFC_227EA_CF3CH2CF3 = new ActivitySourceCode("86");
	public static final ActivitySourceCode HFC_23 = new ActivitySourceCode("87");
	public static final ActivitySourceCode HFC_23_CHF3 = new ActivitySourceCode("88");
	public static final ActivitySourceCode HFC_236FA = new ActivitySourceCode("89");
	public static final ActivitySourceCode HFC_236FA_CHF2CH2CF3 = new ActivitySourceCode("90");
	public static final ActivitySourceCode HFC_245FA = new ActivitySourceCode("91");
	public static final ActivitySourceCode HFC_245FA_CH3CF2CH2CF3 = new ActivitySourceCode("92");
	public static final ActivitySourceCode HFC_32 = new ActivitySourceCode("93");
	public static final ActivitySourceCode HFC_32_CH2F2 = new ActivitySourceCode("94");
	public static final ActivitySourceCode HFC_41 = new ActivitySourceCode("95");
	public static final ActivitySourceCode HFC_41_CH3F = new ActivitySourceCode("96");
	public static final ActivitySourceCode HFC_43_I0MEE = new ActivitySourceCode("97");
	public static final ActivitySourceCode HFC_43_I0MEE_CF3CHFCHFCF2CF3 = new ActivitySourceCode("98");
	public static final ActivitySourceCode HFE_125_CHF2OCF3 = new ActivitySourceCode("99");
	public static final ActivitySourceCode HFE_134_CHF2OCHF2 = new ActivitySourceCode("100");
	public static final ActivitySourceCode HFE_143A_CH3OCF3 = new ActivitySourceCode("101");
	public static final ActivitySourceCode HFE_236CA12_HG_10_CHF2OCF2OCHF2 = new ActivitySourceCode("102");
	public static final ActivitySourceCode HFE_245CB2_CH3OCF2CHF2 = new ActivitySourceCode("103");
	public static final ActivitySourceCode HFE_245FA2_CHF2OCH2CF3 = new ActivitySourceCode("104");
	public static final ActivitySourceCode HFE_254CB2_CH3OCF2CHF2 = new ActivitySourceCode("105");
	public static final ActivitySourceCode HFE_338PCC13_HG_01_CHF2OCF2CF2OCHF2 = new ActivitySourceCode("106");
	public static final ActivitySourceCode HFE_347MCC3_CH3OCF2CF2CF3 = new ActivitySourceCode("107");
	public static final ActivitySourceCode HFE_347PCF2_CHF2CF2OCH2CF3 = new ActivitySourceCode("108");
	public static final ActivitySourceCode HFE_356PCC3_CH3OCF2CF2CHF2 = new ActivitySourceCode("109");
	public static final ActivitySourceCode HFE_43_10PCCC124_H_GALDEN1040X_CHF2OCF2OC2F4OCHF2 = new ActivitySourceCode(
			"110");
	public static final ActivitySourceCode HFE_449SL_HFE_7100_C4F9OCH3 = new ActivitySourceCode("111");
	public static final ActivitySourceCode HFE_569SF2_HFE_7200_C4F9OC2H5 = new ActivitySourceCode("112");
	public static final ActivitySourceCode ISCEON89 = new ActivitySourceCode("113");
	public static final ActivitySourceCode METHANE_NH4 = new ActivitySourceCode("114");
	public static final ActivitySourceCode METHANE_CH4 = new ActivitySourceCode("115");
	public static final ActivitySourceCode METHYL_BROMIDE_CH3BR = new ActivitySourceCode("116");
	public static final ActivitySourceCode METHYL_CHLORIDE_CH3CL = new ActivitySourceCode("117");
	public static final ActivitySourceCode METHYL_CHLOROFORM_CH3CCL3 = new ActivitySourceCode("118");
	public static final ActivitySourceCode METHYLENE_CHLORIDE_CH2CL2 = new ActivitySourceCode("119");
	public static final ActivitySourceCode NITROGEN_TRIFLUORIDE_NF3 = new ActivitySourceCode("120");
	public static final ActivitySourceCode OXIDE_NITREUX_N2O = new ActivitySourceCode("121");
	public static final ActivitySourceCode PERFLUOROBUTANE_PFC_3_1_10 = new ActivitySourceCode("122");
	public static final ActivitySourceCode PERFLUOROBUTANE_PFC_3_1_10_C4F10 = new ActivitySourceCode("123");
	public static final ActivitySourceCode PERFLUOROCYCLOBUTANE_PFC_318 = new ActivitySourceCode("124");
	public static final ActivitySourceCode PERFLUOROCYCLOBUTANE_PFC_318_C_C4F8 = new ActivitySourceCode("125");
	public static final ActivitySourceCode PERFLUOROETHANE_PFC_116 = new ActivitySourceCode("126");
	public static final ActivitySourceCode PERFLUOROETHANE_PFC_116_C2F6 = new ActivitySourceCode("127");
	public static final ActivitySourceCode PERFLUOROHEXANE_PFC_5_1_14 = new ActivitySourceCode("128");
	public static final ActivitySourceCode PERFLUOROHEXANE_PFC_5_1_14_C6F14 = new ActivitySourceCode("129");
	public static final ActivitySourceCode PERFLUOROMETHANE_PFC_14 = new ActivitySourceCode("130");
	public static final ActivitySourceCode PERFLUOROMETHANE_PFC_14_CF4 = new ActivitySourceCode("131");
	public static final ActivitySourceCode PERFLUOROPENTANE_PFC_4_1_12 = new ActivitySourceCode("132");
	public static final ActivitySourceCode PERFLUOROPENTANE_PFC_4_1_12_C5F12 = new ActivitySourceCode("133");
	public static final ActivitySourceCode PERFLUOROPROPANE_PFC_218 = new ActivitySourceCode("134");
	public static final ActivitySourceCode PERFLUOROPROPANE_PFC_218_C3F8 = new ActivitySourceCode("135");
	public static final ActivitySourceCode PFC_4_1_12_C5F12 = new ActivitySourceCode("136");
	public static final ActivitySourceCode PFC_9_1_18_C10F18 = new ActivitySourceCode("137");
	public static final ActivitySourceCode PFPMIE_CF3OCF_CF3_CF2OCF2OCF3 = new ActivitySourceCode("138");
	public static final ActivitySourceCode PROTOXYDE_D_AZOTE_N2O = new ActivitySourceCode("139");
	public static final ActivitySourceCode R134A = new ActivitySourceCode("140");
	public static final ActivitySourceCode R22 = new ActivitySourceCode("141");
	public static final ActivitySourceCode R290_PROPANE_C3H8 = new ActivitySourceCode("142");
	public static final ActivitySourceCode R401A = new ActivitySourceCode("143");
	public static final ActivitySourceCode R402A = new ActivitySourceCode("144");
	public static final ActivitySourceCode R404A_52_44_4_MIX_DE_HFC_143A_125_ET_134A = new ActivitySourceCode("145");
	public static final ActivitySourceCode R406A_55_41_4_MIX_DE_HCFC_22_HCFC_142B_ET_R600A = new ActivitySourceCode(
			"146");
	public static final ActivitySourceCode R407C_23_25_52_MIX_DE_HFC_32_125_ET_134A = new ActivitySourceCode("147");
	public static final ActivitySourceCode R408A = new ActivitySourceCode("148");
	public static final ActivitySourceCode R408A_47_7_46_MIX_DE_HCFC_22_HFC_125_ET_HFC_143A = new ActivitySourceCode(
			"149");
	public static final ActivitySourceCode R409A_60_25_15_MIX_DE_HCFC_22_HCFC_124_ET_HCFC_142B = new ActivitySourceCode(
			"150");
	public static final ActivitySourceCode R410A_50_50_MIX_DE_HFC_32_ET_125 = new ActivitySourceCode("151");
	public static final ActivitySourceCode R417A = new ActivitySourceCode("152");
	public static final ActivitySourceCode R422D = new ActivitySourceCode("153");
	public static final ActivitySourceCode R427A = new ActivitySourceCode("154");
	public static final ActivitySourceCode R502_48_8_51_2_MIX_DE_OF_HCFC_22_ET_CFC_115 = new ActivitySourceCode("155");
	public static final ActivitySourceCode R507 = new ActivitySourceCode("156");
	public static final ActivitySourceCode R507_50_50_MIX_DE_HFC_125_ET_HFC_143A = new ActivitySourceCode("157");
	public static final ActivitySourceCode R508B = new ActivitySourceCode("158");
	public static final ActivitySourceCode R508B_46_54_MIX_DE_HFC_23_ET_PFC_116 = new ActivitySourceCode("159");
	public static final ActivitySourceCode R600A_ISOBUTANE_C4H10 = new ActivitySourceCode("160");
	public static final ActivitySourceCode TRIFLUOROMETHYL_PENTAFLUORURE_DE_SOUFFRE_SF5CF3 = new ActivitySourceCode(
			"161");
	public static final ActivitySourceCode GENERIQUE = new ActivitySourceCode("162");
	public static final ActivitySourceCode DIESEL = new ActivitySourceCode("163");
	public static final ActivitySourceCode GPL = new ActivitySourceCode("164");
	public static final ActivitySourceCode WALLONIE_SANS_GARE_NI_BUS_HORS_AGGLO = new ActivitySourceCode("165");
	public static final ActivitySourceCode WALLONIE_SANS_GARE_NI_BUS_EN_AGGLO = new ActivitySourceCode("166");
	public static final ActivitySourceCode WALLONIE_SANS_GARE_MAIS_BUS_HORS_AGGLO = new ActivitySourceCode("167");
	public static final ActivitySourceCode WALLONIE_SANS_GARE_MAIS_BUS_EN_AGGLO = new ActivitySourceCode("168");
	public static final ActivitySourceCode WALLONIE_GARE_PAS_DE_BUS_HORS_AGGLO = new ActivitySourceCode("169");
	public static final ActivitySourceCode WALLONIE_GARE_PAS_DE_BUS_EN_AGGLO = new ActivitySourceCode("170");
	public static final ActivitySourceCode WALLONIE_GARE_ET_BUS_HORS_AGGLO = new ActivitySourceCode("171");
	public static final ActivitySourceCode WALLONIE_GARE_ET_BUS_EN_AGGLO = new ActivitySourceCode("172");
	public static final ActivitySourceCode VOLS_EUROPE_4000KM_A_R = new ActivitySourceCode("173");
	public static final ActivitySourceCode VOLS_INTERCONTINENTAUX_4000_KM_A_R = new ActivitySourceCode("174");
	public static final ActivitySourceCode MOYENNE = new ActivitySourceCode("175");
	public static final ActivitySourceCode ESTIMATION_MIXTE = new ActivitySourceCode("176");
	public static final ActivitySourceCode ESTIMATION_MOYENNE = new ActivitySourceCode("177");
	public static final ActivitySourceCode METAUX = new ActivitySourceCode("178");
	public static final ActivitySourceCode PLASTIQUES = new ActivitySourceCode("179");
	public static final ActivitySourceCode VERRE = new ActivitySourceCode("180");
	public static final ActivitySourceCode PAPIER_CARTON = new ActivitySourceCode("181");
	public static final ActivitySourceCode MATIERE_ORGANIQUE = new ActivitySourceCode("182");
	public static final ActivitySourceCode DECHETS_DANGEREUX = new ActivitySourceCode("183");
	public static final ActivitySourceCode EAU_USEE = new ActivitySourceCode("184");
	public static final ActivitySourceCode EAUX_DE_RAFFINAGE_D_ALCOOL = new ActivitySourceCode("185");
	public static final ActivitySourceCode EAUX_DE_BIERE_ET_MALT = new ActivitySourceCode("186");
	public static final ActivitySourceCode EAUX_DE_CAFE = new ActivitySourceCode("187");
	public static final ActivitySourceCode EAUX_DE_PRODUITS_LAITIERS = new ActivitySourceCode("188");
	public static final ActivitySourceCode EAUX_DE_PREPARATION_DU_POISSON = new ActivitySourceCode("189");
	public static final ActivitySourceCode EAUX_DE_VIANDES_ET_VOLAILLES = new ActivitySourceCode("190");
	public static final ActivitySourceCode EAUX_DE_SUBSTANCES_CHIMIQUES_ORGANIQUES = new ActivitySourceCode("191");
	public static final ActivitySourceCode EAUX_DE_RAFFINERIES_DE_PETROLE = new ActivitySourceCode("192");
	public static final ActivitySourceCode EAUX_DE_PAPIER_ET_PATE_ENSEMBLE = new ActivitySourceCode("193");
	public static final ActivitySourceCode EAUX_DE_PRODUCTION_D_AMIDON = new ActivitySourceCode("194");
	public static final ActivitySourceCode EAUX_DE_RAFFINAGE_DU_SUCRE = new ActivitySourceCode("195");
	public static final ActivitySourceCode EAUX_DE_LEGUMES_ET_FRUITS_JUS = new ActivitySourceCode("196");
	public static final ActivitySourceCode EAUX_DE_VINS_ET_VINAIGRES = new ActivitySourceCode("197");
	public static final ActivitySourceCode DCO = new ActivitySourceCode("198");
	public static final ActivitySourceCode AZOTE = new ActivitySourceCode("199");
	public static final ActivitySourceCode ACIER_OU_FER_BLANC = new ActivitySourceCode("200");
	public static final ActivitySourceCode ALUMINIUM = new ActivitySourceCode("201");
	public static final ActivitySourceCode CUIVRE_MOYENNE = new ActivitySourceCode("202");
	public static final ActivitySourceCode ZINC_MOYENNE = new ActivitySourceCode("203");
	public static final ActivitySourceCode NICKEL_MOYENNE = new ActivitySourceCode("204");
	public static final ActivitySourceCode PLOMB_MOYENNE = new ActivitySourceCode("205");
	public static final ActivitySourceCode AUTRES_METAUX_COURANTS_MOYENNE = new ActivitySourceCode("206");
	public static final ActivitySourceCode ACIER_OU_FER_BLANC_100_RECYCLE = new ActivitySourceCode("207");
	public static final ActivitySourceCode ALUMINIUM_100_RECYCLE = new ActivitySourceCode("208");
	public static final ActivitySourceCode CUIVRE_MOYENNE_100_RECYCLE = new ActivitySourceCode("209");
	public static final ActivitySourceCode ZINC_MOYENNE_100_RECYCLE = new ActivitySourceCode("210");
	public static final ActivitySourceCode NICKEL_MOYENNE_100_RECYCLE = new ActivitySourceCode("211");
	public static final ActivitySourceCode PLOMB_MOYENNE_100_RECYCLE = new ActivitySourceCode("212");
	public static final ActivitySourceCode PLASTIQUE_MOYENNE = new ActivitySourceCode("213");
	public static final ActivitySourceCode PEHD = new ActivitySourceCode("214");
	public static final ActivitySourceCode PET = new ActivitySourceCode("215");
	public static final ActivitySourceCode POLYSTYRENE_MOYENNE = new ActivitySourceCode("216");
	public static final ActivitySourceCode PVC = new ActivitySourceCode("217");
	public static final ActivitySourceCode COMPOSITES_ET_POLYURETHANE_MOYENNE = new ActivitySourceCode("218");
	public static final ActivitySourceCode FILMS_PLASTIQUES_PET_PAS_RECYCLABLE = new ActivitySourceCode("219");
	public static final ActivitySourceCode PLASTIQUE_MOYENNE_100_RECYCLE = new ActivitySourceCode("220");
	public static final ActivitySourceCode PEHD_100_RECYCLE = new ActivitySourceCode("221");
	public static final ActivitySourceCode PET_100_RECYCLE = new ActivitySourceCode("222");
	public static final ActivitySourceCode POLYSTYRENE_MOYENNE_100_RECYCLE = new ActivitySourceCode("223");
	public static final ActivitySourceCode PVC_100_RECYCLE = new ActivitySourceCode("224");
	public static final ActivitySourceCode COMPOSITES_ET_POLYURETHANE_MOYENNE_100_RECYCLE = new ActivitySourceCode(
			"225");
	public static final ActivitySourceCode FILMS_PLASTIQUES_PET_PAS_RECYCLABLE_100_RECYCLE = new ActivitySourceCode(
			"226");
	public static final ActivitySourceCode PAPIER = new ActivitySourceCode("227");
	public static final ActivitySourceCode PAPIER_100_RECYCLE = new ActivitySourceCode("228");
	public static final ActivitySourceCode CARTON = new ActivitySourceCode("229");
	public static final ActivitySourceCode CARTON_100_RECYCLE = new ActivitySourceCode("230");
	public static final ActivitySourceCode VERRE_PLAT = new ActivitySourceCode("231");
	public static final ActivitySourceCode VERRE_BOUTEILLE = new ActivitySourceCode("232");
	public static final ActivitySourceCode VERRE_FLACONS_MOYENNE = new ActivitySourceCode("233");
	public static final ActivitySourceCode VERRE_TECHNIQUE_MOYENNE = new ActivitySourceCode("234");
	public static final ActivitySourceCode FIBRE_DE_VERRE_MOYENNE = new ActivitySourceCode("235");
	public static final ActivitySourceCode VERRE_PLAT_100_RECYCLE = new ActivitySourceCode("236");
	public static final ActivitySourceCode VERRE_BOUTEILLE_100_RECYCLE = new ActivitySourceCode("237");
	public static final ActivitySourceCode VERRE_FLACONS_MOYENNE_100_RECYCLE = new ActivitySourceCode("238");
	public static final ActivitySourceCode VERRE_TECHNIQUE_MOYENNE_100_RECYCLE = new ActivitySourceCode("239");
	public static final ActivitySourceCode FIBRE_DE_VERRE_MOYENNE_100_RECYCLE = new ActivitySourceCode("240");
	public static final ActivitySourceCode AMMONITRATE_PAR_TONNE_D_AZOTE_AN = new ActivitySourceCode("241");
	public static final ActivitySourceCode AMMONITRATE_CALCAIRE_PAR_TONNE_D_AZOTE_CAN = new ActivitySourceCode("242");
	public static final ActivitySourceCode UREE_PAR_TONNE_D_AZOTE = new ActivitySourceCode("243");
	public static final ActivitySourceCode ENGRAIS_AZOTE_MOYEN_PAR_TONNE_D_AZOTE = new ActivitySourceCode("244");
	public static final ActivitySourceCode TSP_PAR_TONNE_DE_P2O5 = new ActivitySourceCode("245");
	public static final ActivitySourceCode ENGRAIS_PHOSPHATE_MOYEN_PAR_TONNE_DE_P2O5 = new ActivitySourceCode("246");
	public static final ActivitySourceCode POTASSE_PAR_TONNE_DE_K2O = new ActivitySourceCode("247");
	public static final ActivitySourceCode ENGRAIS_POTASSIQUE_MOYEN_PAR_TONNE_DE_K2O = new ActivitySourceCode("248");
	public static final ActivitySourceCode HERBICIDES_PAR_TONNE_DE_MATIERE_ACTIVE = new ActivitySourceCode("249");
	public static final ActivitySourceCode FONGICIDES_PAR_TONNE_DE_MATIERE_ACTIVE = new ActivitySourceCode("250");
	public static final ActivitySourceCode INSECTICIDES_PAR_TONNE_DE_MATIERE_ACTIVE = new ActivitySourceCode("251");
	public static final ActivitySourceCode AUTRES_PPP_PAR_TONNE_DE_MATIERE_ACTIVE = new ActivitySourceCode("252");
	public static final ActivitySourceCode REGULATEUR_DE_CROISSANCE_PAR_TONNE_DE_MATIERE_ACTIVE = new ActivitySourceCode(
			"253");
	public static final ActivitySourceCode ASP_PAR_TONNE_DE_P = new ActivitySourceCode("254");
	public static final ActivitySourceCode SCORIES_THOMAS_PAR_TONNE_DE_P = new ActivitySourceCode("255");
	public static final ActivitySourceCode HEXANE = new ActivitySourceCode("256");
	public static final ActivitySourceCode SOUDE_50 = new ActivitySourceCode("257");
	public static final ActivitySourceCode ACIDE_PHOSPHORIQUE = new ActivitySourceCode("258");
	public static final ActivitySourceCode METHANOL = new ActivitySourceCode("259");
	public static final ActivitySourceCode ACIDE_SULFURIQUE = new ActivitySourceCode("260");
	public static final ActivitySourceCode ACIDE_CHLORHYDRIQUE = new ActivitySourceCode("261");
	public static final ActivitySourceCode NYLON = new ActivitySourceCode("262");
	public static final ActivitySourceCode ALCOOL = new ActivitySourceCode("263");
	public static final ActivitySourceCode BETON_C25_30CEM_II = new ActivitySourceCode("264");
	public static final ActivitySourceCode BETON_ARME = new ActivitySourceCode("265");
	public static final ActivitySourceCode BETON_ARME_CONTINU_ROUTIER = new ActivitySourceCode("266");
	public static final ActivitySourceCode BETON_BITUMINEUX = new ActivitySourceCode("267");
	public static final ActivitySourceCode BETON_BITUMINEUX_A_FROID = new ActivitySourceCode("268");
	public static final ActivitySourceCode BETON_BITUMINEUX_AVEC_10_REC = new ActivitySourceCode("269");
	public static final ActivitySourceCode BETON_BITUMINEUX_AVEC_20_REC = new ActivitySourceCode("270");
	public static final ActivitySourceCode BETON_BITUMINEUX_AVEC_30_REC = new ActivitySourceCode("271");
	public static final ActivitySourceCode BETON_BITUMINEUX_AVEC_50_REC = new ActivitySourceCode("272");
	public static final ActivitySourceCode BETON_DE_CIMENT_ROUTIER = new ActivitySourceCode("273");
	public static final ActivitySourceCode BOIS_COURTE_DUREE_DE_VIE = new ActivitySourceCode("274");
	public static final ActivitySourceCode BOIS_D_OEUVRE = new ActivitySourceCode("275");
	public static final ActivitySourceCode CHAUX_VIVE = new ActivitySourceCode("276");
	public static final ActivitySourceCode CIMENT_PORTLAND = new ActivitySourceCode("277");
	public static final ActivitySourceCode ENROBE_A_MODULE_ELEVE = new ActivitySourceCode("278");
	public static final ActivitySourceCode ENROBE_TIEDE = new ActivitySourceCode("279");
	public static final ActivitySourceCode GRAVE_BITUME_3 = new ActivitySourceCode("280");
	public static final ActivitySourceCode GRAVE_CIMENT = new ActivitySourceCode("281");
	public static final ActivitySourceCode GRAVE_CIMENT_PREFISSUREE = new ActivitySourceCode("282");
	public static final ActivitySourceCode GRAVE_EMULSION = new ActivitySourceCode("283");
	public static final ActivitySourceCode GRAVE_LIANT_HYDRAULIQUE = new ActivitySourceCode("284");
	public static final ActivitySourceCode GRAVE_LIANT_ROUTIER_PREFISSUREE = new ActivitySourceCode("285");
	public static final ActivitySourceCode GRAVE_NON_TRAITEE = new ActivitySourceCode("286");
	public static final ActivitySourceCode PIERRES_DE_CARRIERE = new ActivitySourceCode("287");
	public static final ActivitySourceCode RECYCLAGE_EN_PLACE_A_CHAUD_REC = new ActivitySourceCode("288");
	public static final ActivitySourceCode RECYCLAGE_EN_PLACE_A_L_EMULSION = new ActivitySourceCode("289");
	public static final ActivitySourceCode SOL_TRAITE_LIANT_ROUTIER = new ActivitySourceCode("290");
	public static final ActivitySourceCode BLE_PAR_TONNE_DE_MATIERE_SECHE = new ActivitySourceCode("291");
	public static final ActivitySourceCode FOURRAGES_PAR_TONNE_DE_MATIERES_SECHES = new ActivitySourceCode("292");
	public static final ActivitySourceCode ENSILAGE = new ActivitySourceCode("293");
	public static final ActivitySourceCode AGNEAU = new ActivitySourceCode("294");
	public static final ActivitySourceCode ALCOOL_PUR = new ActivitySourceCode("295");
	public static final ActivitySourceCode AUTRES_FRUITS_LEGUMES_DE_SAISON = new ActivitySourceCode("296");
	public static final ActivitySourceCode BEURRE = new ActivitySourceCode("297");
	public static final ActivitySourceCode BOEUF = new ActivitySourceCode("298");
	public static final ActivitySourceCode CANARD_ORDINAIRE = new ActivitySourceCode("299");
	public static final ActivitySourceCode CONCOMBRE_SOUS_SERRE = new ActivitySourceCode("300");
	public static final ActivitySourceCode CONCOMBRES_SAISON = new ActivitySourceCode("301");
	public static final ActivitySourceCode CREVETTES_PECHE = new ActivitySourceCode("302");
	public static final ActivitySourceCode FARINE = new ActivitySourceCode("303");
	public static final ActivitySourceCode FROMAGE_PATE_DURE = new ActivitySourceCode("304");
	public static final ActivitySourceCode FROMAGE_PATE_MOLLE = new ActivitySourceCode("305");
	public static final ActivitySourceCode FRUITS_IMPORTES = new ActivitySourceCode("306");
	public static final ActivitySourceCode HUILE_DE_COLZA = new ActivitySourceCode("307");
	public static final ActivitySourceCode HUILE_DE_TOURNESOL = new ActivitySourceCode("308");
	public static final ActivitySourceCode LAIT_DE_VACHE = new ActivitySourceCode("309");
	public static final ActivitySourceCode LAIT_EN_POUDRE = new ActivitySourceCode("310");
	public static final ActivitySourceCode MAIS_ALIMENTAIRE = new ActivitySourceCode("311");
	public static final ActivitySourceCode MOUTON = new ActivitySourceCode("312");
	public static final ActivitySourceCode MOYENNE_TOUS_FRUIS_ET_LEGUMES_HORS_PDT = new ActivitySourceCode("313");
	public static final ActivitySourceCode OEUFS = new ActivitySourceCode("314");
	public static final ActivitySourceCode PAIN = new ActivitySourceCode("315");
	public static final ActivitySourceCode PINTADE_FERMIERE = new ActivitySourceCode("316");
	public static final ActivitySourceCode POISSON_PECHE_METROPOLE = new ActivitySourceCode("317");
	public static final ActivitySourceCode POMMES_DE_TERRE = new ActivitySourceCode("318");
	public static final ActivitySourceCode PORC = new ActivitySourceCode("319");
	public static final ActivitySourceCode POULET_FERMIER = new ActivitySourceCode("320");
	public static final ActivitySourceCode SALADE_SOUS_SERRE = new ActivitySourceCode("321");
	public static final ActivitySourceCode SALADES_DE_SAISON = new ActivitySourceCode("322");
	public static final ActivitySourceCode SUCRE = new ActivitySourceCode("323");
	public static final ActivitySourceCode THON_ET_AUTRES_POISSONS_TROPICAUX_PECHE = new ActivitySourceCode("324");
	public static final ActivitySourceCode TOMATES_MOYENNE = new ActivitySourceCode("325");
	public static final ActivitySourceCode TOMATES_SAISON = new ActivitySourceCode("326");
	public static final ActivitySourceCode TOMATES_SOUS_SERRE = new ActivitySourceCode("327");
	public static final ActivitySourceCode VEAU = new ActivitySourceCode("328");
	public static final ActivitySourceCode VIN = new ActivitySourceCode("329");
	public static final ActivitySourceCode VOLAILLE_INDUSTRIELLE = new ActivitySourceCode("330");
	public static final ActivitySourceCode YAOURTS = new ActivitySourceCode("331");
	public static final ActivitySourceCode FAIBLEMENT_MATERIEL = new ActivitySourceCode("332");
	public static final ActivitySourceCode FORTEMENT_MATERIEL = new ActivitySourceCode("333");
	public static final ActivitySourceCode INFORMATIQUE_ET_CONSOMMABLES = new ActivitySourceCode("334");
	public static final ActivitySourceCode DIRECT_CO2E = new ActivitySourceCode("335");
	public static final ActivitySourceCode BETON = new ActivitySourceCode("336");
	public static final ActivitySourceCode METAL = new ActivitySourceCode("337");
	public static final ActivitySourceCode NORMAL = new ActivitySourceCode("338");
	public static final ActivitySourceCode INTENSIF = new ActivitySourceCode("339");
	public static final ActivitySourceCode NORMALE = new ActivitySourceCode("340");
	public static final ActivitySourceCode PC_ECRAN_PLAT = new ActivitySourceCode("341");
	public static final ActivitySourceCode GROSSE_IMPRIMANTE = new ActivitySourceCode("342");
	public static final ActivitySourceCode PHOTOCOPIEUR = new ActivitySourceCode("343");
	public static final ActivitySourceCode VOITURE = new ActivitySourceCode("344");
	public static final ActivitySourceCode CAMION = new ActivitySourceCode("345");
	public static final ActivitySourceCode CAMIONNETTE = new ActivitySourceCode("346");
	public static final ActivitySourceCode TRANS_PALETTES = new ActivitySourceCode("347");
	public static final ActivitySourceCode AUTRE = new ActivitySourceCode("348");
	public static final ActivitySourceCode MACHINES = new ActivitySourceCode("349");
	public static final ActivitySourceCode MOBILIER = new ActivitySourceCode("350");
	public static final ActivitySourceCode VOITURE_GROSSE = new ActivitySourceCode("351");
	public static final ActivitySourceCode VOITURE_MOYENNE = new ActivitySourceCode("352");
	public static final ActivitySourceCode VOITURE_PETITE = new ActivitySourceCode("353");
	public static final ActivitySourceCode REPAS_MOYEN = new ActivitySourceCode("354");
	public static final ActivitySourceCode REPAS_VEGETARIEN = new ActivitySourceCode("355");
	public static final ActivitySourceCode REPAS_A_DOMINANTE_VEGETALE_AVEC_POULET = new ActivitySourceCode("356");
	public static final ActivitySourceCode REPAS_A_DOMINANTE_VEGETALE_AVEC_BOEUF = new ActivitySourceCode("357");
	public static final ActivitySourceCode REPAS_CLASSIQUE_AVEC_POULET = new ActivitySourceCode("358");
	public static final ActivitySourceCode REPAS_CLASSIQUE_AVEC_BOEUF = new ActivitySourceCode("359");
	public static final ActivitySourceCode REPAS_A_DOMINANTE_ANIMALE_AVEC_POULET = new ActivitySourceCode("360");
	public static final ActivitySourceCode REPAS_A_DOMINANTE_ANIMALE_AVEC_BOEUF = new ActivitySourceCode("361");
	public static final ActivitySourceCode REPAS_POULET_MANIOC_BANANES = new ActivitySourceCode("362");
	public static final ActivitySourceCode REPAS_POISSON_RIZ_TOMATES = new ActivitySourceCode("363");

	protected ActivitySourceCode() {
		super(CodeList.ACTIVITY_SOURCE);
	}

	public ActivitySourceCode(String key) {
		this();
		this.key = key;
	}

}
