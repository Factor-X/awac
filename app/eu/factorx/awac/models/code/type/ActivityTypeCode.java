
package eu.factorx.awac.models.code.type;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@AttributeOverrides({@AttributeOverride(name = "key", column = @Column(name = "activitytype"))})
public class ActivityTypeCode extends Code {

    private static final long serialVersionUID = 1L;

    protected ActivityTypeCode() {
        super(CodeList.ActivityType);
    }

    public ActivityTypeCode(String key) {
        this();
        this.key = key;
    }
public static final ActivityTypeCode COMBUSTION_FOSSILE = new ActivityTypeCode("1");
public static final ActivityTypeCode COMBUSTION_FOSSILE_VIA_SURFACE = new ActivityTypeCode("2");
public static final ActivityTypeCode ELEC_PAYS_GRISE = new ActivityTypeCode("3");
public static final ActivityTypeCode ELEC_PAYS_VERTE = new ActivityTypeCode("4");
public static final ActivityTypeCode ELEC_PAYS_GRISE_VIA_SURFACE = new ActivityTypeCode("5");
public static final ActivityTypeCode ELEC_PAYS_VERTE_VIA_SURFACE = new ActivityTypeCode("6");
public static final ActivityTypeCode RESEAU_DE_CHALEUR = new ActivityTypeCode("7");
public static final ActivityTypeCode GAZ = new ActivityTypeCode("8");
public static final ActivityTypeCode AUTRES_COMPOSES_PERFLUORES = new ActivityTypeCode("9");
public static final ActivityTypeCode PUISSANCE_DES_BLOCS_FRIGO = new ActivityTypeCode("10");
public static final ActivityTypeCode TC_BUS = new ActivityTypeCode("11");
public static final ActivityTypeCode TC_METRO = new ActivityTypeCode("12");
public static final ActivityTypeCode TC_TRAIN_NATIONAL = new ActivityTypeCode("13");
public static final ActivityTypeCode TC_TRAIN_INTERNATIONAL = new ActivityTypeCode("14");
public static final ActivityTypeCode TC_TRAM = new ActivityTypeCode("15");
public static final ActivityTypeCode TC_TAXI = new ActivityTypeCode("16");
public static final ActivityTypeCode DEPLACEMENT_MOYENNE = new ActivityTypeCode("17");
public static final ActivityTypeCode AVION_CLASSE_ECO = new ActivityTypeCode("18");
public static final ActivityTypeCode AVION_CLASSE_BUSINESS = new ActivityTypeCode("19");
public static final ActivityTypeCode AVION_CLASSE_PREMIERE = new ActivityTypeCode("20");
public static final ActivityTypeCode AVION_SANS_CLASSE = new ActivityTypeCode("21");
public static final ActivityTypeCode CAMION_LOCAL = new ActivityTypeCode("22");
public static final ActivityTypeCode CAMIONNETTE_LOCAL = new ActivityTypeCode("23");
public static final ActivityTypeCode CAMION_INTERNATIONAL = new ActivityTypeCode("24");
public static final ActivityTypeCode TRAIN = new ActivityTypeCode("25");
public static final ActivityTypeCode BATEAU = new ActivityTypeCode("26");
public static final ActivityTypeCode BARGE_PENICHE = new ActivityTypeCode("27");
public static final ActivityTypeCode AVION_COURT_COURRIER_1000_KM = new ActivityTypeCode("28");
public static final ActivityTypeCode AVION_MOYEN_COURRIER_1000_A_4000_KM = new ActivityTypeCode("29");
public static final ActivityTypeCode AVION_LONG_COURRIER_4000_KM = new ActivityTypeCode("30");
public static final ActivityTypeCode TRANSPORT_ROUTIER_CAMION_LOCAL = new ActivityTypeCode("31");
public static final ActivityTypeCode TRANSPORT_ROUTIER_CAMIONNETTE_LOCAL = new ActivityTypeCode("32");
public static final ActivityTypeCode TRANSPORT_CAMION_INTERNATIONAL = new ActivityTypeCode("33");
public static final ActivityTypeCode CAMION_RAIL_TRAIN_AVION_BATEAU_BELGIQUE = new ActivityTypeCode("34");
public static final ActivityTypeCode CAMION_RAIL_TRAIN_AVION_BATEAU_HORS_BELGIQUE_AMONT = new ActivityTypeCode("35");
public static final ActivityTypeCode CAMION_RAIL_TRAIN_AVION_BATEAU_HORS_BELGIQUE_AVAL = new ActivityTypeCode("36");
public static final ActivityTypeCode INCINERATION = new ActivityTypeCode("37");
public static final ActivityTypeCode CET = new ActivityTypeCode("38");
public static final ActivityTypeCode INCONNU = new ActivityTypeCode("39");
public static final ActivityTypeCode BIOMETHANISATION = new ActivityTypeCode("40");
public static final ActivityTypeCode COMPOSTAGE = new ActivityTypeCode("41");
public static final ActivityTypeCode RECYLCAGE = new ActivityTypeCode("42");
public static final ActivityTypeCode INCINERATION_EN_AMONT_DU_TRAITEMENT = new ActivityTypeCode("43");
public static final ActivityTypeCode INCINERATION_PENDANT_LE_TRAITEMENT = new ActivityTypeCode("44");
public static final ActivityTypeCode CET_EN_AMONT_DU_TRAITEMENT = new ActivityTypeCode("45");
public static final ActivityTypeCode CET_PENDANT_LE_TRAITEMENT = new ActivityTypeCode("46");
public static final ActivityTypeCode RECYCLAGE_EN_AMONT_DU_TRAITEMENT = new ActivityTypeCode("47");
public static final ActivityTypeCode RECYCLAGE_PENDANT_LE_TRAITEMENT = new ActivityTypeCode("48");
public static final ActivityTypeCode COMPOSTAGE_EN_AMONT_DU_TRAITEMENT = new ActivityTypeCode("49");
public static final ActivityTypeCode COMPOSTAGE_PENDANT_LE_TRAITEMENT = new ActivityTypeCode("50");
public static final ActivityTypeCode BIOMETHANISATION_EN_AMONT_DU_TRAITEMENT = new ActivityTypeCode("51");
public static final ActivityTypeCode BIOMETHANISATION_PENDANT_LE_TRAITEMENT = new ActivityTypeCode("52");
public static final ActivityTypeCode INCONNU_EN_AMONT_DU_TRAITEMENT = new ActivityTypeCode("53");
public static final ActivityTypeCode INCONNU_PENDANT_LE_TRAITEMENT = new ActivityTypeCode("54");
public static final ActivityTypeCode REJET_DANS_LE_RESEAU_D_EGOUTTAGE = new ActivityTypeCode("55");
public static final ActivityTypeCode REJET_EN_MER_RIVIERE_OU_LAC = new ActivityTypeCode("56");
public static final ActivityTypeCode REJET_EN_STATION_D_EPURATION_NON_SATUREE = new ActivityTypeCode("57");
public static final ActivityTypeCode REJET_EN_STATION_D_EPURATION_SATUREE = new ActivityTypeCode("58");
public static final ActivityTypeCode METHANISEUR_DE_BOUES = new ActivityTypeCode("59");
public static final ActivityTypeCode LAGUNAGE_ANAEROBIE = new ActivityTypeCode("60");
public static final ActivityTypeCode METAUX = new ActivityTypeCode("61");
public static final ActivityTypeCode PLASTIQUES = new ActivityTypeCode("62");
public static final ActivityTypeCode PAPIER_OU_CARTON = new ActivityTypeCode("63");
public static final ActivityTypeCode VERRE = new ActivityTypeCode("64");
public static final ActivityTypeCode PRODUITS_CHIMIQUES = new ActivityTypeCode("65");
public static final ActivityTypeCode MATERIAUX_ROUTIERS = new ActivityTypeCode("66");
public static final ActivityTypeCode AGRO_ALIMENTAIRE = new ActivityTypeCode("67");
public static final ActivityTypeCode SERVICES_ET_INFORMATIQUES = new ActivityTypeCode("68");
public static final ActivityTypeCode DIRECT_CO2E = new ActivityTypeCode("69");
public static final ActivityTypeCode LOGEMENTS = new ActivityTypeCode("70");
public static final ActivityTypeCode BATIMENTS_A_USAGE_AGRICOLE = new ActivityTypeCode("71");
public static final ActivityTypeCode BATIMENTS_A_USAGE_INDUSTRIEL = new ActivityTypeCode("72");
public static final ActivityTypeCode BATIMENTS_A_USAGE_COMMERCIAL = new ActivityTypeCode("73");
public static final ActivityTypeCode BATIMENTS_DE_BUREAUX = new ActivityTypeCode("74");
public static final ActivityTypeCode BATIMENTS_A_USAGE_DE_PARKINGS = new ActivityTypeCode("75");
public static final ActivityTypeCode BATIMENTS_A_USAGE_D_ENSEIGNEMENT = new ActivityTypeCode("76");
public static final ActivityTypeCode BATIMENTS_A_USAGE_DE_LOISIRS = new ActivityTypeCode("77");
public static final ActivityTypeCode BATIMENTS_A_USAGE_DE_SANTE = new ActivityTypeCode("78");
public static final ActivityTypeCode PARKINGS_ROUTIERS_DE_PLEIN_AIR = new ActivityTypeCode("79");
public static final ActivityTypeCode VOIRIE = new ActivityTypeCode("80");
public static final ActivityTypeCode INFORMATIQUE = new ActivityTypeCode("81");
public static final ActivityTypeCode BUREAUTIQUE = new ActivityTypeCode("82");
public static final ActivityTypeCode VEHICULE_ROULANT = new ActivityTypeCode("83");
public static final ActivityTypeCode MACHINES_TYPE = new ActivityTypeCode("84");
public static final ActivityTypeCode MOBILIER_TYPE = new ActivityTypeCode("85");}