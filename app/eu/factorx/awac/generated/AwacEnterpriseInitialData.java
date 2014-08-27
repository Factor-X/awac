package eu.factorx.awac.generated;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import play.Logger;
import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.code.type.QuestionCode;
import eu.factorx.awac.models.code.type.UnitCategoryCode;
import eu.factorx.awac.models.data.question.QuestionSet;
import eu.factorx.awac.models.data.question.type.*;
import eu.factorx.awac.models.forms.Form;
import eu.factorx.awac.models.knowledge.Unit;
import eu.factorx.awac.models.knowledge.UnitCategory;
import eu.factorx.awac.service.UnitCategoryService;
import eu.factorx.awac.service.UnitService;

@Component
public class AwacEnterpriseInitialData {

    @Autowired
    private UnitCategoryService unitCategoryService;

    @Autowired
    private UnitService unitService;

    public void createSurvey(Session session) {
        Logger.info("===> CREATE AWAC Enterprise INITIAL DATA -- START");
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

    // == TAB2
    // DESCRIPTION, CONSOMMATION & REJETS DU  SITE
    Form form2 = new Form("TAB2");
    session.saveOrUpdate(form2);

    // == TAB3
    // MOBILITE
    Form form3 = new Form("TAB3");
    session.saveOrUpdate(form3);

    // == TAB4
    // TRANSPORT ET DISTRIBUTION AMONT
    Form form4 = new Form("TAB4");
    session.saveOrUpdate(form4);

    // == TAB5
    // DECHETS
    Form form5 = new Form("TAB5");
    session.saveOrUpdate(form5);

    // == TAB6
    // ACHATS ET INVESTISSEMENTS
    Form form6 = new Form("TAB6");
    session.saveOrUpdate(form6);

    // == TAB7
    // PRODUITS VENDUS
    Form form7 = new Form("TAB7");
    session.saveOrUpdate(form7);



    // =========================================================================
    // QUESTION SETS
    // =========================================================================

    // == A1
    // Données générales
    QuestionSet a1 = new QuestionSet(QuestionCode.A1, false, null);
    session.saveOrUpdate(a1);
        form2.getQuestionSets().add(a1);
        session.saveOrUpdate(form2);

    // == A13
    // Consommation de combustibles
    QuestionSet a13 = new QuestionSet(QuestionCode.A13, false, null);
    session.saveOrUpdate(a13);
        form2.getQuestionSets().add(a13);
        session.saveOrUpdate(form2);

    // == A15
    // Combustion de combustible par les sources statiques des sites de l'entreprise
    QuestionSet a15 = new QuestionSet(QuestionCode.A15, true, a13);
    session.saveOrUpdate(a15);

    // == A20
    // Electricité et vapeur achetées
    QuestionSet a20 = new QuestionSet(QuestionCode.A20, false, null);
    session.saveOrUpdate(a20);
        form2.getQuestionSets().add(a20);
        session.saveOrUpdate(form2);

    // == A22
    // Electricité
    QuestionSet a22 = new QuestionSet(QuestionCode.A22, false, a20);
    session.saveOrUpdate(a22);

    // == A25
    // Vapeur
    QuestionSet a25 = new QuestionSet(QuestionCode.A25, true, a20);
    session.saveOrUpdate(a25);

    // == A31
    // GES des processus de production
    QuestionSet a31 = new QuestionSet(QuestionCode.A31, false, null);
    session.saveOrUpdate(a31);
        form2.getQuestionSets().add(a31);
        session.saveOrUpdate(form2);

    // == A34
    // Type de GES émis par la production
    QuestionSet a34 = new QuestionSet(QuestionCode.A34, true, a31);
    session.saveOrUpdate(a34);

    // == A37
    // Systèmes de refroidissement
    QuestionSet a37 = new QuestionSet(QuestionCode.A37, false, null);
    session.saveOrUpdate(a37);
        form2.getQuestionSets().add(a37);
        session.saveOrUpdate(form2);

    // == A40
    // Méthodes au choix
    QuestionSet a40 = new QuestionSet(QuestionCode.A40, false, a37);
    session.saveOrUpdate(a40);

    // == A41
    // Estimation des émissions à partir des recharges de gaz
    QuestionSet a41 = new QuestionSet(QuestionCode.A41, false, a40);
    session.saveOrUpdate(a41);

    // == A42
    // Listes des types de gaz réfrigérants utilisés
    QuestionSet a42 = new QuestionSet(QuestionCode.A42, true, a41);
    session.saveOrUpdate(a42);

    // == A45
    // Estimation des émissions à partir de la puissance du groupe de froid
    QuestionSet a45 = new QuestionSet(QuestionCode.A45, false, a40);
    session.saveOrUpdate(a45);

    // == A47
    // Estimation des émissions à partir de la consommation électrique du site
    QuestionSet a47 = new QuestionSet(QuestionCode.A47, false, a40);
    session.saveOrUpdate(a47);

    // == A50
    // Mobilité
    QuestionSet a50 = new QuestionSet(QuestionCode.A50, false, null);
    session.saveOrUpdate(a50);
        form3.getQuestionSets().add(a50);
        session.saveOrUpdate(form3);

    // == A52
    // Transport routier (méthode au choix)
    QuestionSet a52 = new QuestionSet(QuestionCode.A52, false, a50);
    session.saveOrUpdate(a52);

    // == A53
    // Calcul par les consommations
    QuestionSet a53 = new QuestionSet(QuestionCode.A53, false, a52);
    session.saveOrUpdate(a53);

    // == A54
    // Véhicules de société ou détenus par l'entreprise
    QuestionSet a54 = new QuestionSet(QuestionCode.A54, false, a53);
    session.saveOrUpdate(a54);

    // == A58
    // Autres véhicules: déplacements domicile-travail des employés
    QuestionSet a58 = new QuestionSet(QuestionCode.A58, false, a53);
    session.saveOrUpdate(a58);

    // == A62
    // Autres véhicules: Déplacements professionnels & visiteurs
    QuestionSet a62 = new QuestionSet(QuestionCode.A62, false, a53);
    session.saveOrUpdate(a62);

    // == A66
    // Calcul par les kilomètres
    QuestionSet a66 = new QuestionSet(QuestionCode.A66, false, a52);
    session.saveOrUpdate(a66);

    // == A67
    // Créez autant de catégories de véhicules que souhaité
    QuestionSet a67 = new QuestionSet(QuestionCode.A67, true, a66);
    session.saveOrUpdate(a67);

    // == A77
    // Calcul par euros dépensés
    QuestionSet a77 = new QuestionSet(QuestionCode.A77, false, a52);
    session.saveOrUpdate(a77);

    // == A78
    // Créez autant de catégories de véhicules que souhaité
    QuestionSet a78 = new QuestionSet(QuestionCode.A78, true, a77);
    session.saveOrUpdate(a78);

    // == A93
    // Transport en commun
    QuestionSet a93 = new QuestionSet(QuestionCode.A93, false, a50);
    session.saveOrUpdate(a93);

    // == A94
    // Estimation par le détail des déplacements
    QuestionSet a94 = new QuestionSet(QuestionCode.A94, false, a93);
    session.saveOrUpdate(a94);

    // == A109
    // Estimation par nombre d'employés
    QuestionSet a109 = new QuestionSet(QuestionCode.A109, false, a93);
    session.saveOrUpdate(a109);

    // == A113
    // Transport en avion (déplacements professionnels ou des visiteurs)
    QuestionSet a113 = new QuestionSet(QuestionCode.A113, false, a50);
    session.saveOrUpdate(a113);

    // == A114
    // Méthode par le détail des vols
    QuestionSet a114 = new QuestionSet(QuestionCode.A114, false, a113);
    session.saveOrUpdate(a114);

    // == A115
    // Créez autant de catégories de vol que nécessaire
    QuestionSet a115 = new QuestionSet(QuestionCode.A115, true, a114);
    session.saveOrUpdate(a115);

    // == A121
    // Méthode des moyennes
    QuestionSet a121 = new QuestionSet(QuestionCode.A121, false, a113);
    session.saveOrUpdate(a121);

    // == A128
    // Transport et distribution de marchandises amont
    QuestionSet a128 = new QuestionSet(QuestionCode.A128, false, null);
    session.saveOrUpdate(a128);
        form4.getQuestionSets().add(a128);
        session.saveOrUpdate(form4);

    // == A130
    // Transport amont
    QuestionSet a130 = new QuestionSet(QuestionCode.A130, false, a128);
    session.saveOrUpdate(a130);

    // == A131
    // Transport avec des véhicules détenus par l'entreprise
    QuestionSet a131 = new QuestionSet(QuestionCode.A131, false, a130);
    session.saveOrUpdate(a131);

    // == A132
    // Méthode par consommation de carburants
    QuestionSet a132 = new QuestionSet(QuestionCode.A132, false, a131);
    session.saveOrUpdate(a132);

    // == A140
    // Transport effectué par des transporteurs
    QuestionSet a140 = new QuestionSet(QuestionCode.A140, false, a130);
    session.saveOrUpdate(a140);

    // == A141
    // Méthode des kilomètres
    QuestionSet a141 = new QuestionSet(QuestionCode.A141, false, a140);
    session.saveOrUpdate(a141);

    // == A142
    // Créez autant de marchandises que nécessaire
    QuestionSet a142 = new QuestionSet(QuestionCode.A142, true, a141);
    session.saveOrUpdate(a142);

    // == A157
    // Méthode des moyennes
    QuestionSet a157 = new QuestionSet(QuestionCode.A157, false, a140);
    session.saveOrUpdate(a157);

    // == A163
    // Distribution amont: Energie et froid des entrepôts de stockage
    QuestionSet a163 = new QuestionSet(QuestionCode.A163, false, a128);
    session.saveOrUpdate(a163);

    // == A164
    // Créez autant d'entrepôts de stockage que nécessaire
    QuestionSet a164 = new QuestionSet(QuestionCode.A164, true, a163);
    session.saveOrUpdate(a164);

    // == A166
    // Listez les totaux de combustibles utilisés en amont
    QuestionSet a166 = new QuestionSet(QuestionCode.A166, true, a164);
    session.saveOrUpdate(a166);

    // == A170
    // Listez les gaz réfrigérants utilisés pour les marchandises amont
    QuestionSet a170 = new QuestionSet(QuestionCode.A170, true, a164);
    session.saveOrUpdate(a170);

    // == A173
    // Déchets générés par les opérations
    QuestionSet a173 = new QuestionSet(QuestionCode.A173, false, null);
    session.saveOrUpdate(a173);
        form5.getQuestionSets().add(a173);
        session.saveOrUpdate(form5);

    // == A175
    // Listez vos différents postes de déchets
    QuestionSet a175 = new QuestionSet(QuestionCode.A175, true, a173);
    session.saveOrUpdate(a175);

    // == A180
    // Eaux usées
    QuestionSet a180 = new QuestionSet(QuestionCode.A180, false, a173);
    session.saveOrUpdate(a180);

    // == A181
    // Eaux usées domestiques par grand type de bâtiments
    QuestionSet a181 = new QuestionSet(QuestionCode.A181, false, a180);
    session.saveOrUpdate(a181);

    // == A182
    // Usine ou atelier
    QuestionSet a182 = new QuestionSet(QuestionCode.A182, false, a181);
    session.saveOrUpdate(a182);

    // == A185
    // Bureau
    QuestionSet a185 = new QuestionSet(QuestionCode.A185, false, a181);
    session.saveOrUpdate(a185);

    // == A188
    // Hôtel, pension, hôpitaux, prison
    QuestionSet a188 = new QuestionSet(QuestionCode.A188, false, a181);
    session.saveOrUpdate(a188);

    // == A191
    // Restaurant ou cantine
    QuestionSet a191 = new QuestionSet(QuestionCode.A191, false, a181);
    session.saveOrUpdate(a191);

    // == A194
    // Eaux usées industrielles
    QuestionSet a194 = new QuestionSet(QuestionCode.A194, false, a180);
    session.saveOrUpdate(a194);

    // == A196
    // Méthodes alternatives
    QuestionSet a196 = new QuestionSet(QuestionCode.A196, false, a194);
    session.saveOrUpdate(a196);

    // == A197
    // Méthode par la quantité de m³ rejetés
    QuestionSet a197 = new QuestionSet(QuestionCode.A197, false, a196);
    session.saveOrUpdate(a197);

    // == A201
    // Méthode par le poids de CO2 chimique des effluents rejetés
    QuestionSet a201 = new QuestionSet(QuestionCode.A201, false, a196);
    session.saveOrUpdate(a201);

    // == A205
    // Achat de biens et services
    QuestionSet a205 = new QuestionSet(QuestionCode.A205, false, null);
    session.saveOrUpdate(a205);
        form6.getQuestionSets().add(a205);
        session.saveOrUpdate(form6);

    // == A208
    // Méthode par détail des achats
    QuestionSet a208 = new QuestionSet(QuestionCode.A208, false, null);
    session.saveOrUpdate(a208);
        form6.getQuestionSets().add(a208);
        session.saveOrUpdate(form6);

    // == A209
    // Créez et nommez vos postes d'achats (et préciser la catégorie et le type de matériaux ensuite)
    QuestionSet a209 = new QuestionSet(QuestionCode.A209, true, a208);
    session.saveOrUpdate(a209);

    // == A223
    // Autres matériaux spécifiques pour lesquels l'entreprise dispose du facteur d'émissions cradle-to-gate
    QuestionSet a223 = new QuestionSet(QuestionCode.A223, false, a208);
    session.saveOrUpdate(a223);

    // == A224
    // Créez et nommez vos postes d'achats spécifiques (et précisez ensuite la catégorie, le type de matériaux et le facteur d'émission cradle-to-gate spécifique)
    QuestionSet a224 = new QuestionSet(QuestionCode.A224, true, a223);
    session.saveOrUpdate(a224);

    // == A229
    // Infrastructures (achetées durant l'année de déclaration)
    QuestionSet a229 = new QuestionSet(QuestionCode.A229, false, null);
    session.saveOrUpdate(a229);
        form6.getQuestionSets().add(a229);
        session.saveOrUpdate(form6);

    // == A231
    // Créez et nommez vos postes d'infrastructure
    QuestionSet a231 = new QuestionSet(QuestionCode.A231, true, a229);
    session.saveOrUpdate(a231);

    // == A237
    // Autres infrastructures spécifiques pour lesquels l'entreprise dispose du facteur d'émission cradle-to-gate
    QuestionSet a237 = new QuestionSet(QuestionCode.A237, false, a229);
    session.saveOrUpdate(a237);

    // == A238
    // Créez et nommez vos postes d'infrastructure spécifiques
    QuestionSet a238 = new QuestionSet(QuestionCode.A238, true, a237);
    session.saveOrUpdate(a238);

    // == A309
    // Actifs loués (aval)
    QuestionSet a309 = new QuestionSet(QuestionCode.A309, false, null);
    session.saveOrUpdate(a309);
        form6.getQuestionSets().add(a309);
        session.saveOrUpdate(form6);

    // == A311
    // Créez autant de catégories d'actifs loués que nécessaire
    QuestionSet a311 = new QuestionSet(QuestionCode.A311, true, a309);
    session.saveOrUpdate(a311);

    // == A313
    // Listez les totaux de combustibles utilisés pour les actifs loués
    QuestionSet a313 = new QuestionSet(QuestionCode.A313, true, a311);
    session.saveOrUpdate(a313);

    // == A317
    // Listez les gaz réfrigérants et autres nécessaires à l'opération des actifs loués
    QuestionSet a317 = new QuestionSet(QuestionCode.A317, true, a311);
    session.saveOrUpdate(a317);

    // == A320
    // Franchises
    QuestionSet a320 = new QuestionSet(QuestionCode.A320, false, null);
    session.saveOrUpdate(a320);
        form6.getQuestionSets().add(a320);
        session.saveOrUpdate(form6);

    // == A322
    // Créez autant de catégories de franchisés que nécessaire
    QuestionSet a322 = new QuestionSet(QuestionCode.A322, true, a320);
    session.saveOrUpdate(a322);

    // == A325
    // Listez les moyennes de combustibles utilisés par franchisé
    QuestionSet a325 = new QuestionSet(QuestionCode.A325, true, a322);
    session.saveOrUpdate(a325);

    // == A329
    // Listez les gaz réfrigérants et autres utilisés en moyenne par franchisé
    QuestionSet a329 = new QuestionSet(QuestionCode.A329, true, a322);
    session.saveOrUpdate(a329);

    // == A332
    // Activités d'investissement
    QuestionSet a332 = new QuestionSet(QuestionCode.A332, false, null);
    session.saveOrUpdate(a332);
        form6.getQuestionSets().add(a332);
        session.saveOrUpdate(form6);

    // == A334
    // Veuillez indiquer ici tous les projets dans lesquels votre entreprise investit
    QuestionSet a334 = new QuestionSet(QuestionCode.A334, true, a332);
    session.saveOrUpdate(a334);

    // == A243
    // Transport & Distribution, Traitement, Utilisation et Fin de vie des produits vendus
    QuestionSet a243 = new QuestionSet(QuestionCode.A243, false, null);
    session.saveOrUpdate(a243);
        form7.getQuestionSets().add(a243);
        session.saveOrUpdate(form7);

    // == A244
    // Lister les différents produits ou groupes de produits vendus par l'entreprise
    QuestionSet a244 = new QuestionSet(QuestionCode.A244, true, a243);
    session.saveOrUpdate(a244);

    // == A250
    // Transport et distribution aval
    QuestionSet a250 = new QuestionSet(QuestionCode.A250, false, a244);
    session.saveOrUpdate(a250);

    // == A252
    // Transport aval: choix de méthodes
    QuestionSet a252 = new QuestionSet(QuestionCode.A252, false, a250);
    session.saveOrUpdate(a252);

    // == A253
    // Méthode par kilométrage
    QuestionSet a253 = new QuestionSet(QuestionCode.A253, false, a252);
    session.saveOrUpdate(a253);

    // == A266
    // Méthode des moyennes
    QuestionSet a266 = new QuestionSet(QuestionCode.A266, false, a252);
    session.saveOrUpdate(a266);

    // == A272
    // Distribution avale: Energie et Froid des entrepôts de stockage
    QuestionSet a272 = new QuestionSet(QuestionCode.A272, false, a244);
    session.saveOrUpdate(a272);

    // == A273
    // Créez autant d'entrepôts de stockage que nécessaire
    QuestionSet a273 = new QuestionSet(QuestionCode.A273, true, a272);
    session.saveOrUpdate(a273);

    // == A275
    // Listez les totaux de combustibles utilisés
    QuestionSet a275 = new QuestionSet(QuestionCode.A275, true, a273);
    session.saveOrUpdate(a275);

    // == A279
    // Listez les gaz réfrigérants
    QuestionSet a279 = new QuestionSet(QuestionCode.A279, true, a273);
    session.saveOrUpdate(a279);

    // == A282
    // Traitement
    QuestionSet a282 = new QuestionSet(QuestionCode.A282, false, a244);
    session.saveOrUpdate(a282);

    // == A284
    // Listez les totaux de combustibles
    QuestionSet a284 = new QuestionSet(QuestionCode.A284, true, a282);
    session.saveOrUpdate(a284);

    // == A288
    // Listez les gaz réfrigérants
    QuestionSet a288 = new QuestionSet(QuestionCode.A288, true, a282);
    session.saveOrUpdate(a288);

    // == A291
    // Utilisation
    QuestionSet a291 = new QuestionSet(QuestionCode.A291, false, a244);
    session.saveOrUpdate(a291);

    // == A297
    // Listez les gaz émis par utilisation de produit
    QuestionSet a297 = new QuestionSet(QuestionCode.A297, true, a291);
    session.saveOrUpdate(a297);

    // == A300
    // Traitement de fin de vie
    QuestionSet a300 = new QuestionSet(QuestionCode.A300, false, a244);
    session.saveOrUpdate(a300);

    // == A303
    // Créez autant de catégories de déchet que nécessaire
    QuestionSet a303 = new QuestionSet(QuestionCode.A303, true, a300);
    session.saveOrUpdate(a303);



    // =========================================================================
    // QUESTIONS
    // =========================================================================

    // == A2
    // Année de référence pour comparaison du présent bilan GES
    session.saveOrUpdate(new IntegerQuestion( a1, 0, QuestionCode.A2, null, null ));

    // == A3
    // A quel secteur principal appartient votre site?
    session.saveOrUpdate(new ValueSelectionQuestion( a1, 0, QuestionCode.A3, CodeList.SECTEURPRINCIPAL ));

    // == A4
    // Quel est le code NACE principal de votre site?
    session.saveOrUpdate(new ValueSelectionQuestion( a1, 0, QuestionCode.A4, CodeList.SECTEURPRIMAIRE ));

    // == A5
    // Quel est le code NACE principal de votre site?
    session.saveOrUpdate(new ValueSelectionQuestion( a1, 0, QuestionCode.A5, CodeList.SECTEURSECONDAIRE ));

    // == A6
    // Quel est le code NACE principal de votre site?
    session.saveOrUpdate(new ValueSelectionQuestion( a1, 0, QuestionCode.A6, CodeList.SECTEURTERTIAIRE ));

    // == A7
    // Est-ce que votre activité est purement ou principalement de bureaux?
    session.saveOrUpdate(new BooleanQuestion( a1, 0, QuestionCode.A7, null ));

    // == A8
    // Etes-vous dans le secteur public ou privé?
    session.saveOrUpdate(new ValueSelectionQuestion( a1, 0, QuestionCode.A8, CodeList.SECTEURTYPE ));

    // == A9
    // Indiquez la surface totale du site:
    session.saveOrUpdate(new DoubleQuestion( a1, 0, QuestionCode.A9, areaUnits, null, getUnitBySymbol("") ));

    // == A10
    // Quelle est la surface des bureaux?
    session.saveOrUpdate(new DoubleQuestion( a1, 0, QuestionCode.A10, areaUnits, null, getUnitBySymbol("") ));

    // == A11
    // Etes-vous participant aux accords de branche de 2ème génération?
    session.saveOrUpdate(new BooleanQuestion( a1, 0, QuestionCode.A11, null ));

    // == A12
    // Quel est le nombre d'employés sur l'année du bilan?
    session.saveOrUpdate(new IntegerQuestion( a1, 0, QuestionCode.A12, null, null ));

    // == A14
    // Pièces documentaires liées aux consommations de combustible
    session.saveOrUpdate(new DocumentQuestion( a13, 0, QuestionCode.A14));

    // == A16
    // Combustible
    session.saveOrUpdate(new ValueSelectionQuestion( a15, 0, QuestionCode.A16, CodeList.COMBUSTIBLE ));

    // == A17
    // Quantité
    session.saveOrUpdate(new DoubleQuestion( a15, 0, QuestionCode.A17, energyUnits, null, getUnitBySymbol("kW.h") ));

    // == A21
    // Pièces documentaires liées aux achats d'électricité et de vapeur
    session.saveOrUpdate(new DocumentQuestion( a20, 0, QuestionCode.A21));

    // == A23
    // Consommation d'électricité verte
    session.saveOrUpdate(new DoubleQuestion( a22, 0, QuestionCode.A23, energyUnits, null, getUnitBySymbol("kW.h") ));

    // == A24
    // Consommation d'électricité grise
    session.saveOrUpdate(new DoubleQuestion( a22, 0, QuestionCode.A24, energyUnits, null, getUnitBySymbol("kW.h") ));

    // == A26
    // Energie primaire utilisée pour produire la vapeur:
    session.saveOrUpdate(new ValueSelectionQuestion( a25, 0, QuestionCode.A26, CodeList.ENERGIEVAPEUR ));

    // == A27
    // Efficacité de la chaudière
    session.saveOrUpdate(new PercentageQuestion( a25, 0, QuestionCode.A27, null ));

    // == A28
    // Quantité achetée
    session.saveOrUpdate(new DoubleQuestion( a25, 0, QuestionCode.A28, energyUnits, null, getUnitBySymbol("kW.h") ));

    // == A32
    // Est-ce que vos activités impliquent des procédés chimiques et physiques émetteurs directs de gaz à effet de serre ?
    session.saveOrUpdate(new BooleanQuestion( a31, 0, QuestionCode.A32, null ));

    // == A33
    // Pièces documentaires liées aux GES des processus de production
    session.saveOrUpdate(new DocumentQuestion( a31, 0, QuestionCode.A33));

    // == A35
    // Type de GES
    session.saveOrUpdate(new ValueSelectionQuestion( a34, 0, QuestionCode.A35, CodeList.GES ));

    // == A36
    // Quantité
    session.saveOrUpdate(new DoubleQuestion( a34, 0, QuestionCode.A36, massUnits, null, getUnitBySymbol("t") ));

    // == A38
    // Disposez-vous d’un système de froid nécessitant un apport ponctuel d’agent réfrigérant (p.e. les chillers, les climatiseurs à air et à eau glacée, les réfrigérateurs, bacs à surgelés, etc.)?
    session.saveOrUpdate(new BooleanQuestion( a37, 0, QuestionCode.A38, null ));

    // == A39
    // Pièces documentaires liées aux systèmes de refroidissement
    session.saveOrUpdate(new DocumentQuestion( a37, 0, QuestionCode.A39));

    // == A43
    // Type de gaz
    session.saveOrUpdate(new ValueSelectionQuestion( a42, 0, QuestionCode.A43, CodeList.FRIGORIGENE ));

    // == A44
    // Quantité de recharge nécessaire pour l'année
    session.saveOrUpdate(new DoubleQuestion( a42, 0, QuestionCode.A44, massUnits, null, getUnitBySymbol("") ));

    // == A46
    // Quel est la puissance frigorifique des groupes froid?
    session.saveOrUpdate(new DoubleQuestion( a45, 0, QuestionCode.A46, powerUnits, null, getUnitBySymbol("") ));

    // == A48
    // Est-ce que votre entreprise produit du sucre ou des pâtes sèches?
    session.saveOrUpdate(new BooleanQuestion( a47, 0, QuestionCode.A48, null ));

    // == A49
    // Quel est le nombre d'heures de fonctionnement annuel du site?
    session.saveOrUpdate(new DoubleQuestion( a47, 0, QuestionCode.A49, timeUnits, null, getUnitBySymbol("h") ));

    // == A51
    // Pièces documentaires liées à la mobilité
    session.saveOrUpdate(new DocumentQuestion( a50, 0, QuestionCode.A51));

    // == A55
    // Consommation d'essence
    session.saveOrUpdate(new DoubleQuestion( a54, 0, QuestionCode.A55, volumeUnits, null, getUnitBySymbol("") ));

    // == A56
    // Consommation de diesel
    session.saveOrUpdate(new DoubleQuestion( a54, 0, QuestionCode.A56, volumeUnits, null, getUnitBySymbol("") ));

    // == A57
    // Consommation de gaz de pétrole liquéfié (GPL)
    session.saveOrUpdate(new DoubleQuestion( a54, 0, QuestionCode.A57, volumeUnits, null, getUnitBySymbol("") ));

    // == A59
    // Consommation d'essence
    session.saveOrUpdate(new DoubleQuestion( a58, 0, QuestionCode.A59, volumeUnits, null, getUnitBySymbol("") ));

    // == A60
    // Consommation de diesel
    session.saveOrUpdate(new DoubleQuestion( a58, 0, QuestionCode.A60, volumeUnits, null, getUnitBySymbol("") ));

    // == A61
    // Consommation de gaz de pétrole liquéfié (GPL)
    session.saveOrUpdate(new DoubleQuestion( a58, 0, QuestionCode.A61, volumeUnits, null, getUnitBySymbol("") ));

    // == A63
    // Consommation d'essence
    session.saveOrUpdate(new DoubleQuestion( a62, 0, QuestionCode.A63, volumeUnits, null, getUnitBySymbol("") ));

    // == A64
    // Consommation de diesel
    session.saveOrUpdate(new DoubleQuestion( a62, 0, QuestionCode.A64, volumeUnits, null, getUnitBySymbol("") ));

    // == A65
    // Consommation de gaz de pétrole liquéfié (GPL)
    session.saveOrUpdate(new DoubleQuestion( a62, 0, QuestionCode.A65, volumeUnits, null, getUnitBySymbol("") ));

    // == A68
    // Catégorie de véhicule
    session.saveOrUpdate(new StringQuestion( a67, 0, QuestionCode.A68, null ));

    // == A69
    // S'agit-il d'une catégorie de véhicules appartenant ou sous le contrôle à la société ou pas?
    session.saveOrUpdate(new BooleanQuestion( a67, 0, QuestionCode.A69, null ));

    // == A70
    // Motif de déplacement
    session.saveOrUpdate(new ValueSelectionQuestion( a67, 0, QuestionCode.A70, CodeList.MOTIFDEPLACEMENT ));

    // == A71
    // Quel type de carburant utilise-t-il ?
    session.saveOrUpdate(new ValueSelectionQuestion( a67, 0, QuestionCode.A71, CodeList.CARBURANT ));

    // == A72
    // De quel type de véhicule s'agit-il?
    session.saveOrUpdate(new ValueSelectionQuestion( a67, 0, QuestionCode.A72, CodeList.TYPEVEHICULE ));

    // == A73
    // Consommation moyenne (L/100km)
    session.saveOrUpdate(new IntegerQuestion( a67, 0, QuestionCode.A73, null, 7 ));

    // == A74
    // Consommation moyenne (L/100km)
    session.saveOrUpdate(new IntegerQuestion( a67, 0, QuestionCode.A74, null, 12 ));

    // == A75
    // Consommation moyenne (L/100km)
    session.saveOrUpdate(new IntegerQuestion( a67, 0, QuestionCode.A75, null, 6 ));

    // == A76
    // Quelle est le nombre de kilomètres parcourus par an?
    session.saveOrUpdate(new IntegerQuestion( a67, 0, QuestionCode.A76, null, null ));

    // == A79
    // Catégorie de véhicule
    session.saveOrUpdate(new StringQuestion( a78, 0, QuestionCode.A79, null ));

    // == A80
    // S'agit-il d'une catégorie de véhicules appartenant ou sous le contrôle à la société ou pas?
    session.saveOrUpdate(new BooleanQuestion( a78, 0, QuestionCode.A80, null ));

    // == A81
    // Motif de déplacement
    session.saveOrUpdate(new ValueSelectionQuestion( a78, 0, QuestionCode.A81, CodeList.MOTIFDEPLACEMENT ));

    // == A83
    // Quel type de carburant utilise-t-il ?
    session.saveOrUpdate(new ValueSelectionQuestion( a78, 0, QuestionCode.A83, CodeList.CARBURANT ));

    // == A88
    // Quel est le montant annuel de dépenses en carburant?
    session.saveOrUpdate(new DoubleQuestion( a78, 0, QuestionCode.A88, moneyUnits, null, getUnitBySymbol("EUR") ));

    // == A89
    // Prix moyen du litre d'essence
    session.saveOrUpdate(new DoubleQuestion( a78, 0, QuestionCode.A89, moneyUnits, 1.3, getUnitBySymbol("EUR") ));

    // == A90
    // Prix moyen du litre de diesel
    session.saveOrUpdate(new DoubleQuestion( a78, 0, QuestionCode.A90, moneyUnits, 1.45, getUnitBySymbol("EUR") ));

    // == A91
    // Prix moyen du litre de biodiesel
    session.saveOrUpdate(new DoubleQuestion( a78, 0, QuestionCode.A91, moneyUnits, 1.44, getUnitBySymbol("EUR") ));

    // == A92
    // Prix moyen du litre de Gaz de Prétrole Liquéfié (GPL)
    session.saveOrUpdate(new DoubleQuestion( a78, 0, QuestionCode.A92, moneyUnits, 1.1, getUnitBySymbol("EUR") ));

    // == A95
    // Bus TEC pour déplacement domicile-travail des employés (en km.passagers)
    session.saveOrUpdate(new IntegerQuestion( a94, 0, QuestionCode.A95, null, null ));

    // == A96
    // Bus TEC pour déplacements professionnels & des visiteurs (en km.passagers)
    session.saveOrUpdate(new IntegerQuestion( a94, 0, QuestionCode.A96, null, null ));

    // == A97
    // Métro pour déplacement domicile-travail des employés (en km.passagers)
    session.saveOrUpdate(new IntegerQuestion( a94, 0, QuestionCode.A97, null, null ));

    // == A98
    // Métro pour déplacements professionnels & des visiteurs (en km.passagers)
    session.saveOrUpdate(new IntegerQuestion( a94, 0, QuestionCode.A98, null, null ));

    // == A99
    // Train national SNCB pour déplacement domicile-travail des employés (en km.passagers)
    session.saveOrUpdate(new IntegerQuestion( a94, 0, QuestionCode.A99, null, null ));

    // == A100
    // Train national SNCB pour déplacements professionnels & des visiteurs (en km.passagers)
    session.saveOrUpdate(new IntegerQuestion( a94, 0, QuestionCode.A100, null, null ));

    // == A101
    // Train international (TGV) pour déplacement domicile-travail des employés (en km.passagers)
    session.saveOrUpdate(new IntegerQuestion( a94, 0, QuestionCode.A101, null, null ));

    // == A102
    // Train international (TGV) pour déplacements professionnels & des visiteurs (en km.passagers)
    session.saveOrUpdate(new IntegerQuestion( a94, 0, QuestionCode.A102, null, null ));

    // == A103
    // Tram pour déplacement domicile-travail des employés (en km.passagers)
    session.saveOrUpdate(new IntegerQuestion( a94, 0, QuestionCode.A103, null, null ));

    // == A104
    // Tram pour déplacements professionnels & des visiteurs (en km.passagers)
    session.saveOrUpdate(new IntegerQuestion( a94, 0, QuestionCode.A104, null, null ));

    // == A105
    // Taxi pour déplacement domicile-travail des employés (en véhicules.km)
    session.saveOrUpdate(new IntegerQuestion( a94, 0, QuestionCode.A105, null, null ));

    // == A106
    // Taxi pour déplacements professionnels & des visiteurs (en véhicules.km)
    session.saveOrUpdate(new IntegerQuestion( a94, 0, QuestionCode.A106, null, null ));

    // == A107
    // Taxi pour déplacement domicile-travail des employés (en valeur)
    session.saveOrUpdate(new DoubleQuestion( a94, 0, QuestionCode.A107, moneyUnits, null, getUnitBySymbol("EUR") ));

    // == A108
    // Taxi pour déplacements professionnels & des visiteurs (en valeur)
    session.saveOrUpdate(new DoubleQuestion( a94, 0, QuestionCode.A108, moneyUnits, null, getUnitBySymbol("EUR") ));

    // == A110
    // Etes-vous situés à proximité d'une gare (< 1 km)?
    session.saveOrUpdate(new BooleanQuestion( a109, 0, QuestionCode.A110, null ));

    // == A111
    // Etes-vous situés à proximité d'un arrêt de transport en commun (< 500 m)?
    session.saveOrUpdate(new BooleanQuestion( a109, 0, QuestionCode.A111, null ));

    // == A112
    // Etes-vous situés en Agglomération ?
    session.saveOrUpdate(new BooleanQuestion( a109, 0, QuestionCode.A112, null ));

    // == A116
    // Catégorie de vol
    session.saveOrUpdate(new StringQuestion( a115, 0, QuestionCode.A116, null ));

    // == A117
    // Type de vol
    session.saveOrUpdate(new ValueSelectionQuestion( a115, 0, QuestionCode.A117, CodeList.TYPEVOL ));

    // == A118
    // Classe du vol
    session.saveOrUpdate(new ValueSelectionQuestion( a115, 0, QuestionCode.A118, CodeList.CATEGORIEVOL ));

    // == A119
    // Nombre de vols/an
    session.saveOrUpdate(new IntegerQuestion( a115, 0, QuestionCode.A119, null, null ));

    // == A120
    // Distance moyenne A/R (km)
    session.saveOrUpdate(new DoubleQuestion( a115, 0, QuestionCode.A120, lengthUnits, null, getUnitBySymbol("km") ));

    // == A122
    // % des employés qui réalisent des déplacements en avion
    session.saveOrUpdate(new PercentageQuestion( a121, 0, QuestionCode.A122, null ));

    // == A123
    // Connaissez-vous le nombre de km parcourus en avion?
    session.saveOrUpdate(new BooleanQuestion( a121, 0, QuestionCode.A123, null ));

    // == A124
    // Les voyages ont-ils lieu en Europe?
    session.saveOrUpdate(new BooleanQuestion( a121, 0, QuestionCode.A124, null ));

    // == A125
    // Km moyen assignés par employé voyageant
    session.saveOrUpdate(new DoubleQuestion( a121, 0, QuestionCode.A125, lengthUnits, null, getUnitBySymbol("km") ));

    // == A126
    // Km moyen assignés par employé voyageant
    session.saveOrUpdate(new DoubleQuestion( a121, 0, QuestionCode.A126, lengthUnits, null, getUnitBySymbol("km") ));

    // == A127
    // km moyen parcourus sur l'année:
    session.saveOrUpdate(new DoubleQuestion( a121, 0, QuestionCode.A127, lengthUnits, null, getUnitBySymbol("km") ));

    // == A129
    // Pièces documentaires liées au transport et stockage amont
    session.saveOrUpdate(new DocumentQuestion( a128, 0, QuestionCode.A129));

    // == A133
    // Consommation d'essence
    session.saveOrUpdate(new DoubleQuestion( a132, 0, QuestionCode.A133, volumeUnits, null, getUnitBySymbol("l") ));

    // == A134
    // Consommation de diesel
    session.saveOrUpdate(new DoubleQuestion( a132, 0, QuestionCode.A134, volumeUnits, null, getUnitBySymbol("l") ));

    // == A135
    // Consommation de gaz de pétrole liquéfié (GPL)
    session.saveOrUpdate(new DoubleQuestion( a132, 0, QuestionCode.A135, volumeUnits, null, getUnitBySymbol("l") ));

    // == A136
    // Est-ce les marchandises sont refrigérées durant le transport?
    session.saveOrUpdate(new BooleanQuestion( a132, 0, QuestionCode.A136, null ));

    // == A137
    // Type de Gaz
    session.saveOrUpdate(new ValueSelectionQuestion( a132, 0, QuestionCode.A137, CodeList.FRIGORIGENEBASE ));

    // == A138
    // Connaissez-vous la quantité annuelle de recharge de ce gaz?
    session.saveOrUpdate(new BooleanQuestion( a132, 0, QuestionCode.A138, null ));

    // == A139
    // Quantité de recharge annuelle
    session.saveOrUpdate(new DoubleQuestion( a132, 0, QuestionCode.A139, massUnits, null, getUnitBySymbol("kg") ));

    // == A500
    // Quantité de recharge annuelle
    session.saveOrUpdate(new DoubleQuestion( a132, 0, QuestionCode.A500, massUnits, null, getUnitBySymbol("kg") ));

    // == A143
    // Marchandise
    session.saveOrUpdate(new StringQuestion( a142, 0, QuestionCode.A143, null ));

    // == A145
    // Poids total transporté:
    session.saveOrUpdate(new DoubleQuestion( a142, 0, QuestionCode.A145, massUnits, null, getUnitBySymbol("t") ));

    // == A146
    // Distance totale entre le point de départ et le point d'arrivée de la marchandise:
    session.saveOrUpdate(new DoubleQuestion( a142, 0, QuestionCode.A146, lengthUnits, null, getUnitBySymbol("km") ));

    // == A147
    // % de distance effectuée par transport routier local par camion
    session.saveOrUpdate(new PercentageQuestion( a142, 0, QuestionCode.A147, null ));

    // == A148
    // % de distance effectuée par transport routier local par camionnette
    session.saveOrUpdate(new PercentageQuestion( a142, 0, QuestionCode.A148, null ));

    // == A149
    // % de distance effectuée par transport routier international
    session.saveOrUpdate(new PercentageQuestion( a142, 0, QuestionCode.A149, null ));

    // == A150
    // % de distance effectuée par voie ferroviaire
    session.saveOrUpdate(new PercentageQuestion( a142, 0, QuestionCode.A150, null ));

    // == A151
    // % de distance effectuée par voie maritime
    session.saveOrUpdate(new PercentageQuestion( a142, 0, QuestionCode.A151, null ));

    // == A152
    // % de distance effectuée par voie fluviale
    session.saveOrUpdate(new PercentageQuestion( a142, 0, QuestionCode.A152, null ));

    // == A153
    // % de distance effectuée par transport aérien court courrier (<1000 km)
    session.saveOrUpdate(new PercentageQuestion( a142, 0, QuestionCode.A153, null ));

    // == A154
    // % de distance effectuée par transport aérien moyen courrier (1000 à 4000 km)
    session.saveOrUpdate(new PercentageQuestion( a142, 0, QuestionCode.A154, null ));

    // == A155
    // % de distance effectuée par transport aérien long courrier (> 4000 km)
    session.saveOrUpdate(new PercentageQuestion( a142, 0, QuestionCode.A155, null ));

    // == A156
    // Total (supposé être égal à 100%)
    session.saveOrUpdate(new PercentageQuestion( a142, 0, QuestionCode.A156, null ));

    // == A158
    // Quel est le poids total des marchandises?
    session.saveOrUpdate(new DoubleQuestion( a157, 0, QuestionCode.A158, massUnits, null, getUnitBySymbol("t") ));

    // == A159
    // Quelle est la provenance ou destination des marchandises?
    session.saveOrUpdate(new ValueSelectionQuestion( a157, 0, QuestionCode.A159, CodeList.PROVENANCESIMPLIFIEE ));

    // == A160
    // Km assignés en moyenne aux marchandises
    session.saveOrUpdate(new DoubleQuestion( a157, 0, QuestionCode.A160, lengthUnits, null, getUnitBySymbol("km") ));

    // == A161
    // Km assignés en moyenne aux marchandises
    session.saveOrUpdate(new DoubleQuestion( a157, 0, QuestionCode.A161, lengthUnits, null, getUnitBySymbol("km") ));

    // == A162
    // Km assignés en moyenne aux marchandises
    session.saveOrUpdate(new DoubleQuestion( a157, 0, QuestionCode.A162, lengthUnits, null, getUnitBySymbol("km") ));

    // == A165
    // Entrepôt
    session.saveOrUpdate(new StringQuestion( a164, 0, QuestionCode.A165, null ));

    // == A167
    // Combustible utilisé en amont
    session.saveOrUpdate(new ValueSelectionQuestion( a166, 0, QuestionCode.A167, CodeList.COMBUSTIBLE ));

    // == A168
    // Quantité
    session.saveOrUpdate(new DoubleQuestion( a166, 0, QuestionCode.A168, energyUnits, null, getUnitBySymbol("kW.h") ));

    // == A169
    // Electricité
    session.saveOrUpdate(new DoubleQuestion( a164, 0, QuestionCode.A169, energyUnits, null, getUnitBySymbol("kW.h") ));

    // == A171
    // Type de gaz
    session.saveOrUpdate(new ValueSelectionQuestion( a170, 0, QuestionCode.A171, CodeList.FRIGORIGENE ));

    // == A172
    // Quantité de recharge nécessaire pour l'année
    session.saveOrUpdate(new DoubleQuestion( a170, 0, QuestionCode.A172, massUnits, null, getUnitBySymbol("") ));

    // == A174
    // Pièces documentaires liées aux déchets
    session.saveOrUpdate(new DocumentQuestion( a173, 0, QuestionCode.A174));

    // == A176
    // Poste de déchet
    session.saveOrUpdate(new StringQuestion( a175, 0, QuestionCode.A176, null ));

    // == A177
    // Type de déchet
    session.saveOrUpdate(new ValueSelectionQuestion( a175, 0, QuestionCode.A177, CodeList.TYPEDECHET ));

    // == A178
    // Type de traitement
    session.saveOrUpdate(new ValueSelectionQuestion( a175, 0, QuestionCode.A178, CodeList.TRAITEMENTDECHET ));

    // == A179
    // Quantité
    session.saveOrUpdate(new DoubleQuestion( a175, 0, QuestionCode.A179, massUnits, null, getUnitBySymbol("t") ));

    // == A183
    // Nombre d'ouvriers
    session.saveOrUpdate(new IntegerQuestion( a182, 0, QuestionCode.A183, null, null ));

    // == A184
    // Nombre de jours de travail/an
    session.saveOrUpdate(new IntegerQuestion( a182, 0, QuestionCode.A184, null, 220 ));

    // == A186
    // Nombre d'employés
    session.saveOrUpdate(new IntegerQuestion( a185, 0, QuestionCode.A186, null, null ));

    // == A187
    // Nombre de jours de travail/an
    session.saveOrUpdate(new IntegerQuestion( a185, 0, QuestionCode.A187, null, 220 ));

    // == A189
    // Nombre de lits
    session.saveOrUpdate(new IntegerQuestion( a188, 0, QuestionCode.A189, null, null ));

    // == A190
    // Nombre de jours d'ouverture/an
    session.saveOrUpdate(new IntegerQuestion( a188, 0, QuestionCode.A190, null, 365 ));

    // == A192
    // Nombre de couverts/jour
    session.saveOrUpdate(new IntegerQuestion( a191, 0, QuestionCode.A192, null, null ));

    // == A193
    // Nombre de jours d'ouverture/an
    session.saveOrUpdate(new IntegerQuestion( a191, 0, QuestionCode.A193, null, 220 ));

    // == A195
    // Est-ce l'entreprise qui réalise le traitement ou est-il effectué par des tiers?
    session.saveOrUpdate(new ValueSelectionQuestion( a197, 0, QuestionCode.A195, CodeList.TRAITEUREAU ));

    // == A198
    // Source de rejet
    session.saveOrUpdate(new ValueSelectionQuestion( a197, 0, QuestionCode.A198, CodeList.ORIGINEEAUUSEE ));

    // == A199
    // Quantités de m³ rejetés
    session.saveOrUpdate(new DoubleQuestion( a197, 0, QuestionCode.A199, volumeUnits, null, getUnitBySymbol("") ));

    // == A200
    // Méthode de traitement des eaux usées
    session.saveOrUpdate(new ValueSelectionQuestion( a197, 0, QuestionCode.A200, CodeList.TRAITEMENTEAU ));

    // == A501
    // Est-ce l'entreprise qui réalise le traitement ou est-il effectué par des tiers?
    session.saveOrUpdate(new ValueSelectionQuestion( a201, 0, QuestionCode.A501, CodeList.TRAITEUREAU ));

    // == A202
    // Quantités de DCO rejetés
    session.saveOrUpdate(new DoubleQuestion( a201, 0, QuestionCode.A202, massUnits, null, getUnitBySymbol("") ));

    // == A203
    // Quantités d'azote rejetés
    session.saveOrUpdate(new DoubleQuestion( a201, 0, QuestionCode.A203, massUnits, null, getUnitBySymbol("") ));

    // == A204
    // Méthode de traitement des eaux usées
    session.saveOrUpdate(new ValueSelectionQuestion( a201, 0, QuestionCode.A204, CodeList.TRAITEMENTEAU ));

    // == A206
    // Pièces documentaires liées aux achats
    session.saveOrUpdate(new DocumentQuestion( a205, 0, QuestionCode.A206));

    // == A210
    // Poste d'achat
    session.saveOrUpdate(new StringQuestion( a209, 0, QuestionCode.A210, null ));

    // == A211
    // Catégorie
    session.saveOrUpdate(new ValueSelectionQuestion( a209, 0, QuestionCode.A211, CodeList.TYPEACHAT ));

    // == A212
    // Type
    session.saveOrUpdate(new ValueSelectionQuestion( a209, 0, QuestionCode.A212, CodeList.ACHATMETAL ));

    // == A213
    // Type
    session.saveOrUpdate(new ValueSelectionQuestion( a209, 0, QuestionCode.A213, CodeList.ACHATPLASTIQUE ));

    // == A214
    // Type
    session.saveOrUpdate(new ValueSelectionQuestion( a209, 0, QuestionCode.A214, CodeList.ACHATPAPIER ));

    // == A215
    // Type
    session.saveOrUpdate(new ValueSelectionQuestion( a209, 0, QuestionCode.A215, CodeList.ACHATVERRE ));

    // == A216
    // Type
    session.saveOrUpdate(new ValueSelectionQuestion( a209, 0, QuestionCode.A216, CodeList.ACHATCHIMIQUE ));

    // == A217
    // Type
    session.saveOrUpdate(new ValueSelectionQuestion( a209, 0, QuestionCode.A217, CodeList.ACHATROUTE ));

    // == A218
    // Type
    session.saveOrUpdate(new ValueSelectionQuestion( a209, 0, QuestionCode.A218, CodeList.ACHATAGRO ));

    // == A219
    // Type
    session.saveOrUpdate(new ValueSelectionQuestion( a209, 0, QuestionCode.A219, CodeList.ACHATSERVICE ));

    // == A220
    // Taux de recyclé
    session.saveOrUpdate(new PercentageQuestion( a209, 0, QuestionCode.A220, null ));

    // == A221
    // Quantité
    session.saveOrUpdate(new DoubleQuestion( a209, 0, QuestionCode.A221, massUnits, null, getUnitBySymbol("t") ));

    // == A222
    // Quantité
    session.saveOrUpdate(new DoubleQuestion( a209, 0, QuestionCode.A222, moneyUnits, null, getUnitBySymbol("EUR") ));

    // == A225
    // Poste d'achat
    session.saveOrUpdate(new StringQuestion( a224, 0, QuestionCode.A225, null ));

    // == A226
    // Quantité
    session.saveOrUpdate(new IntegerQuestion( a224, 0, QuestionCode.A226, null, null ));

    // == A227
    // Unité dans laquelle s'exprime cette quantité
    session.saveOrUpdate(new StringQuestion( a224, 0, QuestionCode.A227, null ));

    // == A228
    // Facteur d'émission en tCO2e par unité ci-dessus
    session.saveOrUpdate(new IntegerQuestion( a224, 0, QuestionCode.A228, null, null ));

    // == A230
    // Pièces documentaires liées aux infrastructures
    session.saveOrUpdate(new DocumentQuestion( a229, 0, QuestionCode.A230));

    // == A232
    // Poste d'infrastructure
    session.saveOrUpdate(new StringQuestion( a231, 0, QuestionCode.A232, null ));

    // == A233
    // Type d'infrastructure
    session.saveOrUpdate(new ValueSelectionQuestion( a231, 0, QuestionCode.A233, CodeList.INFRASTRUCTURE ));

    // == A234
    // Quantité
    session.saveOrUpdate(new DoubleQuestion( a231, 0, QuestionCode.A234, areaUnits, null, getUnitBySymbol("") ));

    // == A235
    // Quantité
    session.saveOrUpdate(new DoubleQuestion( a231, 0, QuestionCode.A235, massUnits, null, getUnitBySymbol("t") ));

    // == A236
    // Quantité
    session.saveOrUpdate(new IntegerQuestion( a231, 0, QuestionCode.A236, null, null ));

    // == A239
    // Poste d'infrastructure
    session.saveOrUpdate(new StringQuestion( a238, 0, QuestionCode.A239, null ));

    // == A240
    // Quantité
    session.saveOrUpdate(new IntegerQuestion( a238, 0, QuestionCode.A240, null, null ));

    // == A241
    // Unité dans laquelle s'exprime cette quantité
    session.saveOrUpdate(new StringQuestion( a238, 0, QuestionCode.A241, null ));

    // == A242
    // Facteur d'émission en tCO2e par unité ci-dessus
    session.saveOrUpdate(new IntegerQuestion( a238, 0, QuestionCode.A242, null, null ));

    // == A310
    // Fournir ici les documents éventuels justifiant les données suivantes
    session.saveOrUpdate(new DocumentQuestion( a309, 0, QuestionCode.A310));

    // == A312
    // Catégorie d'actif loué
    session.saveOrUpdate(new StringQuestion( a311, 0, QuestionCode.A312, null ));

    // == A314
    // Combustible utilisé
    session.saveOrUpdate(new ValueSelectionQuestion( a313, 0, QuestionCode.A314, CodeList.COMBUSTIBLE ));

    // == A315
    // Quantité
    session.saveOrUpdate(new DoubleQuestion( a313, 0, QuestionCode.A315, energyUnits, null, getUnitBySymbol("kW.h") ));

    // == A316
    // Electricité
    session.saveOrUpdate(new DoubleQuestion( a311, 0, QuestionCode.A316, energyUnits, null, getUnitBySymbol("kW.h") ));

    // == A318
    // Type de gaz
    session.saveOrUpdate(new ValueSelectionQuestion( a317, 0, QuestionCode.A318, CodeList.FRIGORIGENE ));

    // == A319
    // Quantité de recharge nécessaire pour l'année
    session.saveOrUpdate(new DoubleQuestion( a317, 0, QuestionCode.A319, massUnits, null, getUnitBySymbol("") ));

    // == A321
    // Fournir ici les documents éventuels justifiant les données suivantes
    session.saveOrUpdate(new DocumentQuestion( a320, 0, QuestionCode.A321));

    // == A323
    // Catégorie de franchisé
    session.saveOrUpdate(new StringQuestion( a322, 0, QuestionCode.A323, null ));

    // == A324
    // Nombre de franchisés
    session.saveOrUpdate(new IntegerQuestion( a322, 0, QuestionCode.A324, null, null ));

    // == A326
    // Combustible utilisé
    session.saveOrUpdate(new ValueSelectionQuestion( a325, 0, QuestionCode.A326, CodeList.COMBUSTIBLE ));

    // == A327
    // Quantité
    session.saveOrUpdate(new DoubleQuestion( a325, 0, QuestionCode.A327, energyUnits, null, getUnitBySymbol("kW.h") ));

    // == A328
    // Electricité (moyenne par franchisé)
    session.saveOrUpdate(new DoubleQuestion( a322, 0, QuestionCode.A328, energyUnits, null, getUnitBySymbol("kW.h") ));

    // == A330
    // Type de gaz
    session.saveOrUpdate(new ValueSelectionQuestion( a329, 0, QuestionCode.A330, CodeList.FRIGORIGENE ));

    // == A331
    // Quantité de recharge nécessaire pour l'année
    session.saveOrUpdate(new DoubleQuestion( a329, 0, QuestionCode.A331, massUnits, null, getUnitBySymbol("") ));

    // == A333
    // Fournir ici les documents éventuels justifiant les données suivantes
    session.saveOrUpdate(new DocumentQuestion( a332, 0, QuestionCode.A333));

    // == A335
    // Nom du projet
    session.saveOrUpdate(new StringQuestion( a334, 0, QuestionCode.A335, null ));

    // == A336
    // Part d'investissements dans le projet
    session.saveOrUpdate(new PercentageQuestion( a334, 0, QuestionCode.A336, null ));

    // == A337
    // Emissions directes totales (tCO2e)
    session.saveOrUpdate(new IntegerQuestion( a334, 0, QuestionCode.A337, null, null ));

    // == A338
    // Emissions indirectes totales (tCO2e)
    session.saveOrUpdate(new IntegerQuestion( a334, 0, QuestionCode.A338, null, null ));

    // == A245
    // Nom du produit ou groupe de produits
    session.saveOrUpdate(new StringQuestion( a244, 0, QuestionCode.A245, null ));

    // == A246
    // Quantité vendue de ce produit
    session.saveOrUpdate(new IntegerQuestion( a244, 0, QuestionCode.A246, null, null ));

    // == A247
    // Unité dans laquelle s'exprime cette quantité
    session.saveOrUpdate(new StringQuestion( a244, 0, QuestionCode.A247, null ));

    // == A248
    // S'agit-il d'un produit (ou groupe de produits) final ou intermédiaire?
    session.saveOrUpdate(new ValueSelectionQuestion( a244, 0, QuestionCode.A248, CodeList.TYPEPRODUIT ));

    // == A249
    // Connaissez-vous la ou les applications ultérieures?
    session.saveOrUpdate(new BooleanQuestion( a244, 0, QuestionCode.A249, null ));

    // == A251
    // Pièces documentaires liées au transport et stockage aval
    session.saveOrUpdate(new DocumentQuestion( a250, 0, QuestionCode.A251));

    // == A254
    // Poids total transporté:
    session.saveOrUpdate(new DoubleQuestion( a253, 0, QuestionCode.A254, massUnits, null, getUnitBySymbol("t") ));

    // == A255
    // Distance totale entre le point de vente et le client particulier ou entre le point de vente et le client professionnel
    session.saveOrUpdate(new DoubleQuestion( a253, 0, QuestionCode.A255, lengthUnits, null, getUnitBySymbol("km") ));

    // == A256
    // % de distance effectuée par transport routier local par camion
    session.saveOrUpdate(new PercentageQuestion( a253, 0, QuestionCode.A256, null ));

    // == A257
    // % de distance effectuée par transport routier local par camionnette
    session.saveOrUpdate(new PercentageQuestion( a253, 0, QuestionCode.A257, null ));

    // == A258
    // % de distance effectuée par transport routier international
    session.saveOrUpdate(new PercentageQuestion( a253, 0, QuestionCode.A258, null ));

    // == A259
    // % de distance effectuée par voie ferroviaire
    session.saveOrUpdate(new PercentageQuestion( a253, 0, QuestionCode.A259, null ));

    // == A260
    // % de distance effectuée par voie maritime
    session.saveOrUpdate(new PercentageQuestion( a253, 0, QuestionCode.A260, null ));

    // == A261
    // % de distance effectuée par voie fluviale
    session.saveOrUpdate(new PercentageQuestion( a253, 0, QuestionCode.A261, null ));

    // == A262
    // % de distance effectuée par transport aérien court courrier (<1000 km)
    session.saveOrUpdate(new PercentageQuestion( a253, 0, QuestionCode.A262, null ));

    // == A263
    // % de distance effectuée par transport aérien moyen courrier (1000 à 4000 km)
    session.saveOrUpdate(new PercentageQuestion( a253, 0, QuestionCode.A263, null ));

    // == A264
    // % de distance effectuée par transport aérien long courrier (> 4000 km)
    session.saveOrUpdate(new PercentageQuestion( a253, 0, QuestionCode.A264, null ));

    // == A265
    // Total (supposé être égal à 100%)
    session.saveOrUpdate(new PercentageQuestion( a253, 0, QuestionCode.A265, null ));

    // == A267
    // Poids total transporté:
    session.saveOrUpdate(new DoubleQuestion( a266, 0, QuestionCode.A267, massUnits, null, getUnitBySymbol("t") ));

    // == A268
    // Quelle est la destination géographique des produits vendus?
    session.saveOrUpdate(new ValueSelectionQuestion( a266, 0, QuestionCode.A268, CodeList.PROVENANCESIMPLIFIEE ));

    // == A269
    // Km assignés en moyenne aux marchandises
    session.saveOrUpdate(new DoubleQuestion( a266, 0, QuestionCode.A269, lengthUnits, null, getUnitBySymbol("km") ));

    // == A270
    // Km assignés en moyenne aux marchandises
    session.saveOrUpdate(new DoubleQuestion( a266, 0, QuestionCode.A270, lengthUnits, null, getUnitBySymbol("km") ));

    // == A271
    // Km assignés en moyenne aux marchandises
    session.saveOrUpdate(new DoubleQuestion( a266, 0, QuestionCode.A271, lengthUnits, null, getUnitBySymbol("km") ));

    // == A274
    // Entrepôt
    session.saveOrUpdate(new StringQuestion( a273, 0, QuestionCode.A274, null ));

    // == A276
    // Combustible utilisé
    session.saveOrUpdate(new ValueSelectionQuestion( a275, 0, QuestionCode.A276, CodeList.COMBUSTIBLE ));

    // == A277
    // Quantité
    session.saveOrUpdate(new DoubleQuestion( a275, 0, QuestionCode.A277, energyUnits, null, getUnitBySymbol("kW.h") ));

    // == A278
    // Electricité
    session.saveOrUpdate(new DoubleQuestion( a273, 0, QuestionCode.A278, energyUnits, null, getUnitBySymbol("kW.h") ));

    // == A280
    // Type de gaz
    session.saveOrUpdate(new ValueSelectionQuestion( a279, 0, QuestionCode.A280, CodeList.FRIGORIGENE ));

    // == A281
    // Quantité de recharge nécessaire pour l'année
    session.saveOrUpdate(new DoubleQuestion( a279, 0, QuestionCode.A281, massUnits, null, getUnitBySymbol("") ));

    // == A283
    // Fournir ici les documents éventuels justifiant les données suivantes
    session.saveOrUpdate(new DocumentQuestion( a282, 0, QuestionCode.A283));

    // == A285
    // Combustible utilisé
    session.saveOrUpdate(new ValueSelectionQuestion( a284, 0, QuestionCode.A285, CodeList.COMBUSTIBLE ));

    // == A286
    // Quantité
    session.saveOrUpdate(new DoubleQuestion( a284, 0, QuestionCode.A286, energyUnits, null, getUnitBySymbol("kW.h") ));

    // == A287
    // Electricité
    session.saveOrUpdate(new DoubleQuestion( a282, 0, QuestionCode.A287, energyUnits, null, getUnitBySymbol("kW.h") ));

    // == A289
    // Type de gaz
    session.saveOrUpdate(new ValueSelectionQuestion( a288, 0, QuestionCode.A289, CodeList.FRIGORIGENE ));

    // == A290
    // Quantité de recharge nécessaire pour l'année
    session.saveOrUpdate(new DoubleQuestion( a288, 0, QuestionCode.A290, massUnits, null, getUnitBySymbol("") ));

    // == A292
    // Fournir ici les documents éventuels justifiant les données suivantes
    session.saveOrUpdate(new DocumentQuestion( a291, 0, QuestionCode.A292));

    // == A293
    // Nombre total d'utilisations du produit ou groupe de produits sur toute sa durée de vie
    session.saveOrUpdate(new IntegerQuestion( a291, 0, QuestionCode.A293, null, null ));

    // == A294
    // Consommation de diesel par utilisation de produit
    session.saveOrUpdate(new DoubleQuestion( a291, 0, QuestionCode.A294, volumeUnits, null, getUnitBySymbol("l") ));

    // == A295
    // Consommation d'essence par utilisation de produit
    session.saveOrUpdate(new DoubleQuestion( a291, 0, QuestionCode.A295, volumeUnits, null, getUnitBySymbol("l") ));

    // == A296
    // Consommation d'électricité par utilisation de produit
    session.saveOrUpdate(new DoubleQuestion( a291, 0, QuestionCode.A296, energyUnits, null, getUnitBySymbol("kW.h") ));

    // == A298
    // Gaz émis
    session.saveOrUpdate(new ValueSelectionQuestion( a297, 0, QuestionCode.A298, CodeList.GESSIMPLIFIE ));

    // == A299
    // Quantité
    session.saveOrUpdate(new DoubleQuestion( a297, 0, QuestionCode.A299, massUnits, null, getUnitBySymbol("") ));

    // == A301
    // Fournir ici les documents éventuels justifiant les données suivantes
    session.saveOrUpdate(new DocumentQuestion( a300, 0, QuestionCode.A301));

    // == A302
    // Poids total de produit vendu
    session.saveOrUpdate(new DoubleQuestion( a300, 0, QuestionCode.A302, massUnits, null, getUnitBySymbol("t") ));

    // == A304
    // Catégorie de déchet
    session.saveOrUpdate(new StringQuestion( a303, 0, QuestionCode.A304, null ));

    // == A305
    // Poids total de cette catégorie de déchet issu des produits vendus
    session.saveOrUpdate(new DoubleQuestion( a303, 0, QuestionCode.A305, massUnits, null, getUnitBySymbol("t") ));

    // == A306
    // Type principal de ce déchet:
    session.saveOrUpdate(new ValueSelectionQuestion( a303, 0, QuestionCode.A306, CodeList.TYPEDECHET ));

    // == A307
    // Type de traitement du déchet
    session.saveOrUpdate(new ValueSelectionQuestion( a303, 0, QuestionCode.A307, CodeList.TRAITEMENTDECHET ));

    // == A308
    // Proportion du déchet issu du produit, traité par la méthode précédemment renseignée
    session.saveOrUpdate(new ValueSelectionQuestion( a303, 0, QuestionCode.A308, CodeList.POURCENTSIMPLIFIE ));




        Logger.info("===> CREATE AWAC Enterprise INITIAL DATA -- END (Took {} milliseconds)", (System.currentTimeMillis() - startTime));
    }

    private UnitCategory getUnitCategoryByCode(UnitCategoryCode unitCategoryCode) {
        return unitCategoryService.findByCode(unitCategoryCode);
    }

    private Unit getUnitBySymbol(String symbol) {
        return unitService.findBySymbol(symbol);
    }

}



