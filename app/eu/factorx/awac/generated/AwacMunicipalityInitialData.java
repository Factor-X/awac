package eu.factorx.awac.generated;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import play.Logger;
import eu.factorx.awac.models.Notification;
import eu.factorx.awac.models.NotificationKind;
import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.code.type.PeriodCode;
import eu.factorx.awac.models.code.type.QuestionCode;
import eu.factorx.awac.models.code.type.UnitCategoryCode;
import eu.factorx.awac.models.data.question.QuestionSet;
import eu.factorx.awac.models.data.question.type.*;
import eu.factorx.awac.models.forms.Form;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.models.knowledge.Unit;
import eu.factorx.awac.models.knowledge.UnitCategory;
import eu.factorx.awac.service.UnitCategoryService;
import eu.factorx.awac.service.UnitService;
import eu.factorx.awac.util.data.importer.*;

@Component
public class AwacMunicipalityInitialData {

    @Autowired
    private UnitCategoryService unitCategoryService;

    @Autowired
    private UnitService unitService;

    public void createSurvey(Session session) {
        Logger.info("===> CREATE AWAC Municipality INITIAL DATA -- START");
        long startTime = System.currentTimeMillis();

        UnitCategory energyUnits  = getUnitCategoryByCode(UnitCategoryCode.ENERGY);
        UnitCategory massUnits    = getUnitCategoryByCode(UnitCategoryCode.MASS);
        UnitCategory volumeUnits  = getUnitCategoryByCode(UnitCategoryCode.VOLUME);
        UnitCategory lengthUnits  = getUnitCategoryByCode(UnitCategoryCode.LENGTH);
        UnitCategory areaUnits    = getUnitCategoryByCode(UnitCategoryCode.AREA);
        UnitCategory powerUnits   = getUnitCategoryByCode(UnitCategoryCode.POWER);
        UnitCategory moneyUnits   = getUnitCategoryByCode(UnitCategoryCode.CURRENCY);
        UnitCategory timeUnits    = getUnitCategoryByCode(UnitCategoryCode.DURATION);

    // =========================================================================
    // FORMS
    // =========================================================================

    // == TAB_C1
    // DONNEES GENERALES
    Form form1 = new Form("TAB_C1");
    session.saveOrUpdate(form1);

    // == TAB_C2
    // ENERGIE, FROID ET DECHETS
    Form form2 = new Form("TAB_C2");
    session.saveOrUpdate(form2);

    // == TAB_C3
    // MOBILITE
    Form form3 = new Form("TAB_C3");
    session.saveOrUpdate(form3);

    // == TAB_C4
    // ACHATS DE BIENS ET SERVICES
    Form form4 = new Form("TAB_C4");
    session.saveOrUpdate(form4);

    // == TAB_C5
    // INFRASTRUCTURES ET INVESTISSEMENTS
    Form form5 = new Form("TAB_C5");
    session.saveOrUpdate(form5);



    // =========================================================================
    // QUESTION SETS
    // =========================================================================

    // == AC1
    // AWAC - Communes
    QuestionSet ac1 = new QuestionSet(QuestionCode.AC1, false, null);
    session.saveOrUpdate(ac1);
        form1.getQuestionSets().add(ac1);
        session.saveOrUpdate(form1);

    // == AC2
    // Introduction - Paramètres de votre commune
    QuestionSet ac2 = new QuestionSet(QuestionCode.AC2, false, ac1);
    session.saveOrUpdate(ac2);

    // == AC9
    // Energie, froid et déchets
    QuestionSet ac9 = new QuestionSet(QuestionCode.AC9, false, null);
    session.saveOrUpdate(ac9);
        form2.getQuestionSets().add(ac9);
        session.saveOrUpdate(form2);

    // == AC10
    // Lister les différents bâtiments ou groupes de bâtiments gérés par la commune
    QuestionSet ac10 = new QuestionSet(QuestionCode.AC10, true, ac9);
    session.saveOrUpdate(ac10);

    // == AC24
    // Consommation de combustible (chauffage de bâtiments et machines statiques p.e. groupe éléctrogène)
    QuestionSet ac24 = new QuestionSet(QuestionCode.AC24, false, ac10);
    session.saveOrUpdate(ac24);

    // == AC25
    // Indiquez ici vos différentes consommations de combustibles pour l'ensemble du  bâtiment ou groupe de bâtiments (chauffage et machines statiques)
    QuestionSet ac25 = new QuestionSet(QuestionCode.AC25, true, ac24);
    session.saveOrUpdate(ac25);

    // == AC28
    // Consommation d'électricité (achetée sur le réseau ou à des tiers)
    QuestionSet ac28 = new QuestionSet(QuestionCode.AC28, false, ac10);
    session.saveOrUpdate(ac28);

    // == AC29
    // Indiquez ici vos différentes consommations éléctriques pour ce bâtiment ou groupe de bâtiments (hors éclairage public)
    QuestionSet ac29 = new QuestionSet(QuestionCode.AC29, false, ac28);
    session.saveOrUpdate(ac29);

    // == AC32
    // Consommation de vapeur (achetée à des tiers)
    QuestionSet ac32 = new QuestionSet(QuestionCode.AC32, false, ac10);
    session.saveOrUpdate(ac32);

    // == AC33
    // Indiquez ici vos différentes quantités de vapeur achetées pour ce bâtiment ou groupe de bâtiments
    QuestionSet ac33 = new QuestionSet(QuestionCode.AC33, true, ac32);
    session.saveOrUpdate(ac33);

    // == AC37
    // Utilisation d'un système de refroidissement
    QuestionSet ac37 = new QuestionSet(QuestionCode.AC37, false, ac10);
    session.saveOrUpdate(ac37);

    // == AC39
    // Ajoutez ici les différents gaz réfrigérant utilisés dans les systèmes de refroidissement  situés dans ce bâtiment ou groupe de bâtiments
    QuestionSet ac39 = new QuestionSet(QuestionCode.AC39, true, ac37);
    session.saveOrUpdate(ac39);

    // == AC42
    // Production de déchets
    QuestionSet ac42 = new QuestionSet(QuestionCode.AC42, false, ac10);
    session.saveOrUpdate(ac42);

    // == AC44
    // Indiquez ici les différentes quantités de déchets produits dans votre bâtiment ou groupe de bâtiments
    QuestionSet ac44 = new QuestionSet(QuestionCode.AC44, true, ac42);
    session.saveOrUpdate(ac44);

    // == AC52
    // Eclairage public et coffrets de voirie
    QuestionSet ac52 = new QuestionSet(QuestionCode.AC52, false, null);
    session.saveOrUpdate(ac52);
        form2.getQuestionSets().add(ac52);
        session.saveOrUpdate(form2);

    // == AC56
    // Créez autant de coffrets de voirie que souhaité
    QuestionSet ac56 = new QuestionSet(QuestionCode.AC56, true, ac52);
    session.saveOrUpdate(ac56);

    // == AC60
    // Mobilité
    QuestionSet ac60 = new QuestionSet(QuestionCode.AC60, false, null);
    session.saveOrUpdate(ac60);
        form3.getQuestionSets().add(ac60);
        session.saveOrUpdate(form3);

    // == AC62
    // Transport routier
    QuestionSet ac62 = new QuestionSet(QuestionCode.AC62, false, ac60);
    session.saveOrUpdate(ac62);

    // == AC64
    // Déplacements en véhicule routier
    QuestionSet ac64 = new QuestionSet(QuestionCode.AC64, false, ac62);
    session.saveOrUpdate(ac64);

    // == AC65
    // Méthode de calcul par les consommations (recommandée)
    QuestionSet ac65 = new QuestionSet(QuestionCode.AC65, false, ac64);
    session.saveOrUpdate(ac65);

    // == AC66
    // Veuillez entrer ici tous les véhicules
    QuestionSet ac66 = new QuestionSet(QuestionCode.AC66, true, ac65);
    session.saveOrUpdate(ac66);

    // == AC72
    // Méthode de calcul par le kilométrage (méthode alternative)
    QuestionSet ac72 = new QuestionSet(QuestionCode.AC72, false, ac64);
    session.saveOrUpdate(ac72);

    // == AC73
    // Veuillez entrer ici tous les (groupes de) véhicules
    QuestionSet ac73 = new QuestionSet(QuestionCode.AC73, true, ac72);
    session.saveOrUpdate(ac73);

    // == AC84
    // Méthode de calcul par les dépenses (méthode alternative)
    QuestionSet ac84 = new QuestionSet(QuestionCode.AC84, false, ac64);
    session.saveOrUpdate(ac84);

    // == AC85
    // Veuillez entrer ici les dépenses en carburant
    QuestionSet ac85 = new QuestionSet(QuestionCode.AC85, true, ac84);
    session.saveOrUpdate(ac85);

    // == AC92
    // Déplacement via les transports publics
    QuestionSet ac92 = new QuestionSet(QuestionCode.AC92, false, ac60);
    session.saveOrUpdate(ac92);

    // == AC93
    // Déplacements domicile-travail des employés communaux
    QuestionSet ac93 = new QuestionSet(QuestionCode.AC93, false, ac92);
    session.saveOrUpdate(ac93);

    // == AC98
    // Déplacements professionnels des employés communaux ainsi que des visiteurs de la commune
    QuestionSet ac98 = new QuestionSet(QuestionCode.AC98, false, ac92);
    session.saveOrUpdate(ac98);

    // == AC106
    // Déplacement et voyages en avion
    QuestionSet ac106 = new QuestionSet(QuestionCode.AC106, false, ac60);
    session.saveOrUpdate(ac106);

    // == AC107
    // Créez autant de catégories de vol que nécessaire
    QuestionSet ac107 = new QuestionSet(QuestionCode.AC107, true, ac106);
    session.saveOrUpdate(ac107);

    // == AC114
    // Achat de biens et services
    QuestionSet ac114 = new QuestionSet(QuestionCode.AC114, false, null);
    session.saveOrUpdate(ac114);
        form4.getQuestionSets().add(ac114);
        session.saveOrUpdate(form4);

    // == AC116
    // Veuillez  indiquez ici l'ensemble des biens et services que votre commune achète.
    QuestionSet ac116 = new QuestionSet(QuestionCode.AC116, true, ac114);
    session.saveOrUpdate(ac116);

    // == AC130
    // Infrastructures (achetées durant l'année de déclaration)
    QuestionSet ac130 = new QuestionSet(QuestionCode.AC130, false, null);
    session.saveOrUpdate(ac130);
        form5.getQuestionSets().add(ac130);
        session.saveOrUpdate(form5);

    // == AC132
    // Veuillez indiquer ici les différentes infrastructures achetées.
    QuestionSet ac132 = new QuestionSet(QuestionCode.AC132, true, ac130);
    session.saveOrUpdate(ac132);

    // == AC137
    // Activités d'investissement
    QuestionSet ac137 = new QuestionSet(QuestionCode.AC137, false, null);
    session.saveOrUpdate(ac137);
        form5.getQuestionSets().add(ac137);
        session.saveOrUpdate(form5);

    // == AC139
    // Veuillez indiquer ici tous les projets dans lesquels votre commune investit
    QuestionSet ac139 = new QuestionSet(QuestionCode.AC139, true, ac137);
    session.saveOrUpdate(ac139);



    // =========================================================================
    // QUESTIONS
    // =========================================================================

    // == AC3
    // Année de référence du calcul
    session.saveOrUpdate(new IntegerQuestion( ac2, 0, QuestionCode.AC3, null, null ));

    // == AC4
    // Souhaitez-vous que le bilan soit conforme à la convention des maires?
    session.saveOrUpdate(new BooleanQuestion( ac2, 0, QuestionCode.AC4, null ));

    // == AC5
    // Nombre d'employés en début d'année de bilan
    session.saveOrUpdate(new IntegerQuestion( ac2, 0, QuestionCode.AC5, null, null ));

    // == AC6
    // Nombre d'habitants dans la commune en début d'année de bilan
    session.saveOrUpdate(new IntegerQuestion( ac2, 0, QuestionCode.AC6, null, null ));

    // == AC7
    // Nombre total de bâtiments communaux pris en compte dans le bilan
    session.saveOrUpdate(new IntegerQuestion( ac2, 0, QuestionCode.AC7, null, null ));

    // == AC8
    // Superficie du territoire
        session.saveOrUpdate(new DoubleQuestion( ac2, 0, QuestionCode.AC8, areaUnits, null, getUnitBySymbol("ha") ));


    // == AC11
    // Nom du bâtiment ou groupe de bâtiments
    session.saveOrUpdate(new StringQuestion( ac10, 0, QuestionCode.AC11, null ));

    // == AC12
    // Catégorie du bâtiment
    session.saveOrUpdate(new ValueSelectionQuestion( ac10, 0, QuestionCode.AC12, CodeList.SERVICECOMMUNAL ));

    // == AC13
    // Autre, veuillez préciser
    session.saveOrUpdate(new StringQuestion( ac10, 0, QuestionCode.AC13, null ));

    // == AC14
    // Adresse (rue et numéro):
    session.saveOrUpdate(new StringQuestion( ac10, 0, QuestionCode.AC14, null ));

    // == AC15
    // Code Postal:
    session.saveOrUpdate(new StringQuestion( ac10, 0, QuestionCode.AC15, null ));

    // == AC16
    // Nom et prénom de la personne de contact:
    session.saveOrUpdate(new StringQuestion( ac10, 0, QuestionCode.AC16, null ));

    // == AC17
    // Email de la personne de contact:
    session.saveOrUpdate(new StringQuestion( ac10, 0, QuestionCode.AC17, null ));

    // == AC18
    // Téléphone de la personne de contact:
    session.saveOrUpdate(new StringQuestion( ac10, 0, QuestionCode.AC18, null ));

    // == AC19
    // Est-ce un bâtiment dont la commune est propriétaire ou locataire?
    session.saveOrUpdate(new ValueSelectionQuestion( ac10, 0, QuestionCode.AC19, CodeList.GESTIONBATIMENT ));

    // == AC20
    // Quelle est la superficie totale du (groupe de) bâtiment?
        session.saveOrUpdate(new DoubleQuestion( ac10, 0, QuestionCode.AC20, areaUnits, null, getUnitBySymbol("m2") ));


    // == AC21
    // Quelle est la superficie chauffée sur tout le (groupe de) bâtiment?
        session.saveOrUpdate(new DoubleQuestion( ac10, 0, QuestionCode.AC21, areaUnits, null, getUnitBySymbol("m2") ));


    // == AC22
    // Quelle en est le % de la partie chauffée occupé par la commune?
    session.saveOrUpdate(new PercentageQuestion( ac10, 0, QuestionCode.AC22, null ));

    // == AC23
    // Fournir ici les documents éventuels justifiant les données de consommation (combustibles, électricité, vapeur)
    session.saveOrUpdate(new DocumentQuestion( ac10, 0, QuestionCode.AC23));

    // == AC26
    // Combustible
    session.saveOrUpdate(new ValueSelectionQuestion( ac25, 0, QuestionCode.AC26, CodeList.COMBUSTIBLESIMPLECOMMUNE ));

    // == AC27
    // Quantité
        session.saveOrUpdate(new DoubleQuestion( ac25, 0, QuestionCode.AC27, energyUnits, null, energyUnits.getMainUnit() ));


    // == AC30
    // Electricité grise
        session.saveOrUpdate(new DoubleQuestion( ac29, 0, QuestionCode.AC30, energyUnits, null, getUnitBySymbol("kW.h") ));


    // == AC31
    // Electricité verte
        session.saveOrUpdate(new DoubleQuestion( ac29, 0, QuestionCode.AC31, energyUnits, null, getUnitBySymbol("kW.h") ));


    // == AC34
    // Quel est le type d'énergie primaire utilisé pour produire la vapeur? 
    session.saveOrUpdate(new ValueSelectionQuestion( ac33, 0, QuestionCode.AC34, CodeList.ENERGIEVAPEUR ));

    // == AC35
    // Efficacité de la chaudière
    session.saveOrUpdate(new PercentageQuestion( ac33, 0, QuestionCode.AC35, null ));

    // == AC36
    // Consommation annuelle de vapeur
        session.saveOrUpdate(new DoubleQuestion( ac33, 0, QuestionCode.AC36, energyUnits, null, energyUnits.getMainUnit() ));


    // == AC38
    // Fournir ici les documents éventuels justifiant les données suivantes
    session.saveOrUpdate(new DocumentQuestion( ac37, 0, QuestionCode.AC38));

    // == AC40
    // Type de gaz réfrigérant
    session.saveOrUpdate(new ValueSelectionQuestion( ac39, 0, QuestionCode.AC40, CodeList.FRIGORIGENE ));

    // == AC41
    // Quantité de recharge nécessaire (pour l'année d'utilisation rapportée)
        session.saveOrUpdate(new DoubleQuestion( ac39, 0, QuestionCode.AC41, massUnits, null, massUnits.getMainUnit() ));


    // == AC43
    // Fournir ici les documents éventuels justifiant les données suivantes
    session.saveOrUpdate(new DocumentQuestion( ac42, 0, QuestionCode.AC43));

    // == AC45
    // Type de déchet
    session.saveOrUpdate(new ValueSelectionQuestion( ac44, 0, QuestionCode.AC45, CodeList.TYPEDECHET ));

    // == AC46
    // Quantité de déchets Incinérée
        session.saveOrUpdate(new DoubleQuestion( ac44, 0, QuestionCode.AC46, massUnits, null, getUnitBySymbol("t") ));


    // == AC47
    // Quantité de déchets Recyclée
        session.saveOrUpdate(new DoubleQuestion( ac44, 0, QuestionCode.AC47, massUnits, null, getUnitBySymbol("t") ));


    // == AC48
    // Quantité de déchets en CET
        session.saveOrUpdate(new DoubleQuestion( ac44, 0, QuestionCode.AC48, massUnits, null, getUnitBySymbol("t") ));


    // == AC49
    // Quantité de déchets utilisés en biométhanisation
        session.saveOrUpdate(new DoubleQuestion( ac44, 0, QuestionCode.AC49, massUnits, null, getUnitBySymbol("t") ));


    // == AC50
    // Quantité de déchets mis en compostage
        session.saveOrUpdate(new DoubleQuestion( ac44, 0, QuestionCode.AC50, massUnits, null, getUnitBySymbol("t") ));


    // == AC51
    // Quantité de déchets traités autrement
        session.saveOrUpdate(new DoubleQuestion( ac44, 0, QuestionCode.AC51, massUnits, null, getUnitBySymbol("t") ));


    // == AC53
    // Fournir ici les documents éventuels justifiant les données suivantes
    session.saveOrUpdate(new DocumentQuestion( ac52, 0, QuestionCode.AC53));

    // == AC54
    // Eclairage public: consommation d'électricité verte
        session.saveOrUpdate(new DoubleQuestion( ac52, 0, QuestionCode.AC54, energyUnits, null, energyUnits.getMainUnit() ));


    // == AC55
    // Eclairage public: consommation d'électricité grise
        session.saveOrUpdate(new DoubleQuestion( ac52, 0, QuestionCode.AC55, energyUnits, null, energyUnits.getMainUnit() ));


    // == AC57
    // Désignation du coffret
    session.saveOrUpdate(new StringQuestion( ac56, 0, QuestionCode.AC57, null ));

    // == AC58
    // Consommation d'électricité verte du coffret
        session.saveOrUpdate(new DoubleQuestion( ac56, 0, QuestionCode.AC58, energyUnits, null, energyUnits.getMainUnit() ));


    // == AC59
    // Consommation d'électricité grise du coffret
        session.saveOrUpdate(new DoubleQuestion( ac56, 0, QuestionCode.AC59, energyUnits, null, energyUnits.getMainUnit() ));


    // == AC61
    // Fournir ici les documents éventuels justifiant les données suivantes
    session.saveOrUpdate(new DocumentQuestion( ac60, 0, QuestionCode.AC61));

    // == AC63
    // Quelle est la part des déplacement communaux sur le territoire communal ?
    session.saveOrUpdate(new PercentageQuestion( ac62, 0, QuestionCode.AC63, null ));

    // == AC67
    // Nom du (groupe de) véhicule
    session.saveOrUpdate(new StringQuestion( ac66, 0, QuestionCode.AC67, null ));

    // == AC68
    // Véhicule communal ou autre?
    session.saveOrUpdate(new ValueSelectionQuestion( ac66, 0, QuestionCode.AC68, CodeList.POSSESSIONVEHICULECOMMUNAL ));

    // == AC69
    // Motif de déplacement
    session.saveOrUpdate(new ValueSelectionQuestion( ac66, 0, QuestionCode.AC69, CodeList.MOTIFDEPLACEMENT ));

    // == AC70
    // Quel type de carburant utilise-t-il ?
    session.saveOrUpdate(new ValueSelectionQuestion( ac66, 0, QuestionCode.AC70, CodeList.CARBURANT ));

    // == AC71
    // Quelle est la quantité totale de carburant consommée par an ?
        session.saveOrUpdate(new DoubleQuestion( ac66, 0, QuestionCode.AC71, volumeUnits, null, getUnitBySymbol("l") ));


    // == AC74
    // Nom du (groupe de) véhicule
    session.saveOrUpdate(new StringQuestion( ac73, 0, QuestionCode.AC74, null ));

    // == AC75
    // Véhicule communal ou autre?
    session.saveOrUpdate(new ValueSelectionQuestion( ac73, 0, QuestionCode.AC75, CodeList.POSSESSIONVEHICULECOMMUNAL ));

    // == AC76
    // Motif de déplacement
    session.saveOrUpdate(new ValueSelectionQuestion( ac73, 0, QuestionCode.AC76, CodeList.MOTIFDEPLACEMENT ));

    // == AC77
    // Quel type de carburant utilise-t-il ?
    session.saveOrUpdate(new ValueSelectionQuestion( ac73, 0, QuestionCode.AC77, CodeList.CARBURANT ));

    // == AC78
    // De quel type de véhicule s'agit-il?
    session.saveOrUpdate(new ValueSelectionQuestion( ac73, 0, QuestionCode.AC78, CodeList.TYPEVEHICULECOMMUNE ));

    // == AC79
    // Consommation moyenne (L/100km)
    session.saveOrUpdate(new IntegerQuestion( ac73, 0, QuestionCode.AC79, null, 7 ));

    // == AC80
    // Consommation moyenne (L/100km)
    session.saveOrUpdate(new IntegerQuestion( ac73, 0, QuestionCode.AC80, null, 12 ));

    // == AC81
    // Consommation moyenne (L/100km)
    session.saveOrUpdate(new IntegerQuestion( ac73, 0, QuestionCode.AC81, null, 6 ));

    // == AC82
    // Consommation moyenne (L/100km)
    session.saveOrUpdate(new IntegerQuestion( ac73, 0, QuestionCode.AC82, null, 10 ));

    // == AC83
    // Quelle est la distance parcourue par an?
        session.saveOrUpdate(new DoubleQuestion( ac73, 0, QuestionCode.AC83, lengthUnits, null, getUnitBySymbol("km") ));


    // == AC86
    // Dépense
    session.saveOrUpdate(new StringQuestion( ac85, 0, QuestionCode.AC86, null ));

    // == AC87
    // De quel type de carburant s'agit-il ?
    session.saveOrUpdate(new ValueSelectionQuestion( ac85, 0, QuestionCode.AC87, CodeList.CARBURANT ));

    // == AC88
    // Quel est le montant annuel de dépenses en carburant?
        session.saveOrUpdate(new DoubleQuestion( ac85, 0, QuestionCode.AC88, moneyUnits, null, getUnitBySymbol("EUR") ));


    // == AC89
    // Prix unitaire de carburant
        session.saveOrUpdate(new DoubleQuestion( ac85, 0, QuestionCode.AC89, moneyUnits, 1.3, getUnitBySymbol("EUR") ));


    // == AC90
    // Dépenses pour des véhicules communaux ou autres?
    session.saveOrUpdate(new ValueSelectionQuestion( ac85, 0, QuestionCode.AC90, CodeList.POSSESSIONVEHICULECOMMUNAL ));

    // == AC91
    // Motif de déplacement
    session.saveOrUpdate(new ValueSelectionQuestion( ac85, 0, QuestionCode.AC91, CodeList.MOTIFDEPLACEMENT ));

    // == AC94
    // Bus TEC (en km.passagers)
    session.saveOrUpdate(new IntegerQuestion( ac93, 0, QuestionCode.AC94, null, null ));

    // == AC95
    // Métro (en km.passagers)
    session.saveOrUpdate(new IntegerQuestion( ac93, 0, QuestionCode.AC95, null, null ));

    // == AC96
    // Train national SNCB (en km.passagers)
    session.saveOrUpdate(new IntegerQuestion( ac93, 0, QuestionCode.AC96, null, null ));

    // == AC97
    // Tram  (en km.passagers)
    session.saveOrUpdate(new IntegerQuestion( ac93, 0, QuestionCode.AC97, null, null ));

    // == AC99
    // Bus TEC (en km.passagers)
    session.saveOrUpdate(new IntegerQuestion( ac98, 0, QuestionCode.AC99, null, null ));

    // == AC100
    // Métro (en km.passagers)
    session.saveOrUpdate(new IntegerQuestion( ac98, 0, QuestionCode.AC100, null, null ));

    // == AC101
    // Train national SNCB (en km.passagers)
    session.saveOrUpdate(new IntegerQuestion( ac98, 0, QuestionCode.AC101, null, null ));

    // == AC102
    // Tram (en km.passagers)
    session.saveOrUpdate(new IntegerQuestion( ac98, 0, QuestionCode.AC102, null, null ));

    // == AC103
    // Taxi (en véhicule.km)
    session.saveOrUpdate(new IntegerQuestion( ac98, 0, QuestionCode.AC103, null, null ));

    // == AC104
    // Taxi (en montant dépensé)
        session.saveOrUpdate(new DoubleQuestion( ac98, 0, QuestionCode.AC104, moneyUnits, null, getUnitBySymbol("EUR") ));


    // == AC105
    // TGV (en km.passagers)
    session.saveOrUpdate(new IntegerQuestion( ac98, 0, QuestionCode.AC105, null, null ));

    // == AC108
    // Catégorie de vol
    session.saveOrUpdate(new StringQuestion( ac107, 0, QuestionCode.AC108, null ));

    // == AC109
    // Type de vol
    session.saveOrUpdate(new ValueSelectionQuestion( ac107, 0, QuestionCode.AC109, CodeList.TYPEVOL ));

    // == AC110
    // Classe du vol
    session.saveOrUpdate(new ValueSelectionQuestion( ac107, 0, QuestionCode.AC110, CodeList.CATEGORIEVOL ));

    // == AC111
    // Nombre de vols/an
    session.saveOrUpdate(new IntegerQuestion( ac107, 0, QuestionCode.AC111, null, null ));

    // == AC112
    // Distance moyenne A/R (km)
        session.saveOrUpdate(new DoubleQuestion( ac107, 0, QuestionCode.AC112, lengthUnits, null, getUnitBySymbol("km") ));


    // == AC113
    // Motif de déplacement
    session.saveOrUpdate(new ValueSelectionQuestion( ac107, 0, QuestionCode.AC113, CodeList.MOTIFDEPLACEMENTHORSDDT ));

    // == AC115
    // Fournir ici les documents éventuels justifiant les données suivantes
    session.saveOrUpdate(new DocumentQuestion( ac114, 0, QuestionCode.AC115));

    // == AC117
    // Poste d'achat
    session.saveOrUpdate(new StringQuestion( ac116, 0, QuestionCode.AC117, null ));

    // == AC118
    // Catégorie
    session.saveOrUpdate(new ValueSelectionQuestion( ac116, 0, QuestionCode.AC118, CodeList.TYPEACHAT ));

    // == AC119
    // Type
    session.saveOrUpdate(new ValueSelectionQuestion( ac116, 0, QuestionCode.AC119, CodeList.ACHATMETAL ));

    // == AC120
    // Type
    session.saveOrUpdate(new ValueSelectionQuestion( ac116, 0, QuestionCode.AC120, CodeList.ACHATPLASTIQUE ));

    // == AC121
    // Type
    session.saveOrUpdate(new ValueSelectionQuestion( ac116, 0, QuestionCode.AC121, CodeList.ACHATPAPIER ));

    // == AC122
    // Type
    session.saveOrUpdate(new ValueSelectionQuestion( ac116, 0, QuestionCode.AC122, CodeList.ACHATVERRE ));

    // == AC123
    // Type
    session.saveOrUpdate(new ValueSelectionQuestion( ac116, 0, QuestionCode.AC123, CodeList.ACHATCHIMIQUE ));

    // == AC124
    // Type
    session.saveOrUpdate(new ValueSelectionQuestion( ac116, 0, QuestionCode.AC124, CodeList.ACHATROUTE ));

    // == AC125
    // Type
    session.saveOrUpdate(new ValueSelectionQuestion( ac116, 0, QuestionCode.AC125, CodeList.ACHATAGRO ));

    // == AC126
    // Type
    session.saveOrUpdate(new ValueSelectionQuestion( ac116, 0, QuestionCode.AC126, CodeList.ACHATSERVICE ));

    // == AC127
    // Taux de recyclé
    session.saveOrUpdate(new PercentageQuestion( ac116, 0, QuestionCode.AC127, 0.9 ));

    // == AC128
    // Quantité
        session.saveOrUpdate(new DoubleQuestion( ac116, 0, QuestionCode.AC128, massUnits, null, getUnitBySymbol("t") ));


    // == AC129
    // Quantité
        session.saveOrUpdate(new DoubleQuestion( ac116, 0, QuestionCode.AC129, moneyUnits, null, getUnitBySymbol("EUR") ));


    // == AC131
    // Fournir ici les documents éventuels justifiant les données suivantes
    session.saveOrUpdate(new DocumentQuestion( ac130, 0, QuestionCode.AC131));

    // == AC133
    // Type d'infrastructure
    session.saveOrUpdate(new ValueSelectionQuestion( ac132, 0, QuestionCode.AC133, CodeList.INFRASTRUCTURE ));

    // == AC134
    // Quantité
        session.saveOrUpdate(new DoubleQuestion( ac132, 0, QuestionCode.AC134, areaUnits, null, getUnitBySymbol("m2") ));


    // == AC135
    // Quantité
        session.saveOrUpdate(new DoubleQuestion( ac132, 0, QuestionCode.AC135, massUnits, null, getUnitBySymbol("t") ));


    // == AC136
    // Nombre d'unités achetées
    session.saveOrUpdate(new IntegerQuestion( ac132, 0, QuestionCode.AC136, null, null ));

    // == AC138
    // Fournir ici les documents éventuels justifiant les données suivantes
    session.saveOrUpdate(new DocumentQuestion( ac137, 0, QuestionCode.AC138));

    // == AC140
    // Nom du projet
    session.saveOrUpdate(new StringQuestion( ac139, 0, QuestionCode.AC140, null ));

    // == AC141
    // Part d'investissements dans le projet
    session.saveOrUpdate(new PercentageQuestion( ac139, 0, QuestionCode.AC141, null ));

    // == AC142
    // Emissions directes totales du projet (tCO²e)
    session.saveOrUpdate(new IntegerQuestion( ac139, 0, QuestionCode.AC142, null, null ));

    // == AC143
    // Emissions indirectes totales du projet (tCO²e)
    session.saveOrUpdate(new IntegerQuestion( ac139, 0, QuestionCode.AC143, null, null ));




        Logger.info("===> CREATE AWAC Municipality INITIAL DATA -- END (Took {} milliseconds)", (System.currentTimeMillis() - startTime));
    }

    private UnitCategory getUnitCategoryByCode(UnitCategoryCode unitCategoryCode) {
        return unitCategoryService.findByCode(unitCategoryCode);
    }

    private Unit getUnitBySymbol(String symbol) {
        return unitService.findBySymbol(symbol);
    }

}



