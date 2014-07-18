package eu.factorx.awac;

import org.hibernate.Session;
import org.springframework.context.ApplicationContext;

import play.db.jpa.JPA;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.business.Site;
import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.code.type.ActivitySourceCode;
import eu.factorx.awac.models.code.type.QuestionCode;
import eu.factorx.awac.models.data.answer.QuestionAnswer;
import eu.factorx.awac.models.data.answer.QuestionSetAnswer;
import eu.factorx.awac.models.data.answer.type.CodeAnswerValue;
import eu.factorx.awac.models.data.answer.type.DoubleAnswerValue;
import eu.factorx.awac.models.data.question.Question;
import eu.factorx.awac.models.data.question.QuestionSet;
import eu.factorx.awac.models.data.question.type.BooleanQuestion;
import eu.factorx.awac.models.data.question.type.DoubleQuestion;
import eu.factorx.awac.models.data.question.type.IntegerQuestion;
import eu.factorx.awac.models.data.question.type.StringQuestion;
import eu.factorx.awac.models.data.question.type.ValueSelectionQuestion;
import eu.factorx.awac.models.forms.Form;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.models.knowledge.Unit;
import eu.factorx.awac.models.knowledge.UnitCategory;
import eu.factorx.awac.util.data.importer.AwacDataImporter;
import eu.factorx.awac.util.data.importer.CodeImporter;
import eu.factorx.awac.util.data.importer.MyrmexUnitsImporter;

public class AwacDummyDataCreator {

    public static void createAwacDummyData(ApplicationContext ctx, Session session) {

        // IMPORT MYRMEX UNITS
        new MyrmexUnitsImporter().run();

        // IMPORT AWAC DATA
        new AwacDataImporter().run();

        // IMPORT CODES
        new CodeImporter().run();

        // REFERENCES DATA

        UnitCategory surfaceUnits = getUnitCategoryByName("Area");
        UnitCategory energyUnits = getUnitCategoryByName("Energy");
        UnitCategory massUnits = getUnitCategoryByName("Mass");
        UnitCategory volumeUnits = getUnitCategoryByName("Volume");
        UnitCategory lengthUnits = getUnitCategoryByName("Length");
        UnitCategory powerUnits = getUnitCategoryByName("Power");
        UnitCategory moneyUnits = getUnitCategoryByName("Currency");
        UnitCategory timeUnits = getUnitCategoryByName("Time");


        Unit gj = getUnitBySymbol("GJ");
        Unit kcal = getUnitBySymbol("kcal");


        // ORGANIZATION AND ACCOUNT

        Organization factorx = new Organization("Factor-X");
        session.saveOrUpdate(factorx);

        Site p3 = new Site(factorx, "P3");
        session.saveOrUpdate(p3);

        Scope scope = new Scope(p3);
        session.saveOrUpdate(scope);

        Account user1 = new Account(factorx, "user1", "password", "user1_lastname", "user1_firstname");
        user1.setAge(25);
        session.saveOrUpdate(user1);

        // CAMPAIGN AND FORM

        Period period1 = new Period("2013");
        session.saveOrUpdate(period1);

        //Form form1 = new Form("Formulaire Entreprise");
        //session.saveOrUpdate(form1);


        createAll(session, lengthUnits, surfaceUnits, volumeUnits, massUnits, energyUnits, powerUnits, moneyUnits, timeUnits);

        // ANSWERS

        // -- FORM
        Form tab1Form = new Form("TAB1");
        session.saveOrUpdate(tab1Form);

        Form tab2Form = new Form("TAB2");
        session.saveOrUpdate(tab2Form);

        Form tab4Form = new Form("TAB4");
        session.saveOrUpdate(tab4Form);


        // -- QUESTION_SETS
        QuestionSet a1 = new QuestionSet(QuestionCode.A1, false);
        session.saveOrUpdate(a1);
        tab1Form.getQuestionSets().add(a1);
        session.saveOrUpdate(tab1Form);


        QuestionSet a13 = new QuestionSet(QuestionCode.A13, false);
        session.saveOrUpdate(a13);
        tab2Form.getQuestionSets().add(a13);
        session.saveOrUpdate(tab2Form);

        QuestionSet a15 = new QuestionSet(QuestionCode.A15, true);
        session.saveOrUpdate(a15);
        tab2Form.getQuestionSets().add(a15);
        session.saveOrUpdate(tab2Form);

        QuestionSet a20 = new QuestionSet(QuestionCode.A20, false);
        session.saveOrUpdate(a20);
        tab2Form.getQuestionSets().add(a20);
        session.saveOrUpdate(tab2Form);

        QuestionSet a22 = new QuestionSet(QuestionCode.A22, false);
        session.saveOrUpdate(a22);
        tab2Form.getQuestionSets().add(a22);
        session.saveOrUpdate(tab2Form);

        QuestionSet a25 = new QuestionSet(QuestionCode.A25, false);
        session.saveOrUpdate(a25);
        tab2Form.getQuestionSets().add(a25);
        session.saveOrUpdate(tab2Form);

        QuestionSet a31 = new QuestionSet(QuestionCode.A31, false);
        session.saveOrUpdate(a31);
        tab2Form.getQuestionSets().add(a31);
        session.saveOrUpdate(tab2Form);

        QuestionSet a41 = new QuestionSet(QuestionCode.A41, false);
        session.saveOrUpdate(a41);
        tab2Form.getQuestionSets().add(a41);
        session.saveOrUpdate(tab2Form);

        QuestionSet a42 = new QuestionSet(QuestionCode.A42, false);
        session.saveOrUpdate(a42);
        tab2Form.getQuestionSets().add(a42);
        session.saveOrUpdate(tab2Form);

        QuestionSet a45 = new QuestionSet(QuestionCode.A45, false);
        session.saveOrUpdate(a45);
        tab2Form.getQuestionSets().add(a45);
        session.saveOrUpdate(tab2Form);

        QuestionSet a47 = new QuestionSet(QuestionCode.A47, false);
        session.saveOrUpdate(a47);
        tab2Form.getQuestionSets().add(a47);
        session.saveOrUpdate(tab2Form);


        // -- QUESTIONS

        session.saveOrUpdate(new IntegerQuestion(a1, 0, QuestionCode.A2, null));
        session.saveOrUpdate(new ValueSelectionQuestion(a1, 100, QuestionCode.A3, CodeList.SITE_SECTORS));
        session.saveOrUpdate(new ValueSelectionQuestion(a1, 200, QuestionCode.A4, CodeList.NACE_CODES_1));
        session.saveOrUpdate(new ValueSelectionQuestion(a1, 300, QuestionCode.A5, CodeList.NACE_CODES_2));
        session.saveOrUpdate(new ValueSelectionQuestion(a1, 400, QuestionCode.A6, CodeList.NACE_CODES_3));
        session.saveOrUpdate(new BooleanQuestion(a1, 500, QuestionCode.A7));
        session.saveOrUpdate(new ValueSelectionQuestion(a1, 600, QuestionCode.A8, CodeList.PUBLIC_PRIVATE));
        session.saveOrUpdate(new DoubleQuestion(a1, 700, QuestionCode.A9, surfaceUnits));
        session.saveOrUpdate(new DoubleQuestion(a1, 800, QuestionCode.A10, surfaceUnits));
        session.saveOrUpdate(new BooleanQuestion(a1, 900, QuestionCode.A11));
        session.saveOrUpdate(new IntegerQuestion(a1, 1000, QuestionCode.A12, null));

        // TAB 2

        session.saveOrUpdate(new IntegerQuestion(a13, 0, QuestionCode.A14, null));
        session.saveOrUpdate(new IntegerQuestion(a13, 100, QuestionCode.A15, null));
        Question a16 = new ValueSelectionQuestion(a15, 200, QuestionCode.A16, CodeList.ACTIVITY_SOURCE);
        session.saveOrUpdate(a16);
        Question a17 = new DoubleQuestion(a15, 300, QuestionCode.A17, energyUnits);
        session.saveOrUpdate(a17);
        // 18
        // 19
        session.saveOrUpdate(new IntegerQuestion(a13, 400, QuestionCode.A21, null));
        session.saveOrUpdate(new IntegerQuestion(a13, 500, QuestionCode.A23, null));
        session.saveOrUpdate(new IntegerQuestion(a13, 600, QuestionCode.A24, null));


        // First set of answers
        int repetitionIndex = 0;
        QuestionSetAnswer questionSet15Answer0 = new QuestionSetAnswer(scope, period1, a15, repetitionIndex, null);
        session.saveOrUpdate(questionSet15Answer0);

        QuestionAnswer a16Answer0 = new QuestionAnswer(user1, null, questionSet15Answer0, a16);
        session.saveOrUpdate(a16Answer0);
        CodeAnswerValue a16Answer0Value = new CodeAnswerValue(a16Answer0, ActivitySourceCode.DIESEL_GASOIL_OU_FUEL_LEGER);
        session.saveOrUpdate(a16Answer0Value);

        QuestionAnswer a17Answer0 = new QuestionAnswer(user1, null, questionSet15Answer0, a17);
        session.saveOrUpdate(a17Answer0);
        DoubleAnswerValue a17Answer0Value = new DoubleAnswerValue(a17Answer0, 20.6, gj);
        session.saveOrUpdate(a17Answer0Value);

        // Second set of answers
        repetitionIndex = 1;
        QuestionSetAnswer questionSet15Answer1 = new QuestionSetAnswer(scope, period1, a15, repetitionIndex, null);
        session.saveOrUpdate(questionSet15Answer1);

        QuestionAnswer a16Answer1 = new QuestionAnswer(user1, null, questionSet15Answer1, a16);
        session.saveOrUpdate(a16Answer1);
        CodeAnswerValue a16Answer1Value = new CodeAnswerValue(a16Answer1, ActivitySourceCode.BOIS_BUCHE);
        session.saveOrUpdate(a16Answer1Value);

        QuestionAnswer a17Answer1 = new QuestionAnswer(user1, null, questionSet15Answer1, a17);
        session.saveOrUpdate(a17Answer1);
        DoubleAnswerValue a17Answer1Value = new DoubleAnswerValue(a17Answer1, 60523.0, kcal);
        session.saveOrUpdate(a17Answer1Value);


        // Compound question : "Distribution amont: Energie et froid des entrepôts de stockage" (TAB 4)

        // -- Question sets

        // Question Set "entrepôts de stockage"
        QuestionSet a164 = new QuestionSet(QuestionCode.A164, true, null);
        session.saveOrUpdate(a164);
        tab4Form.getQuestionSets().add(a164);
        session.saveOrUpdate(tab4Form);

        // -- Question "nom"
        Question a165 = new StringQuestion(a164, 0, QuestionCode.A165);
        session.saveOrUpdate(a165);

        // -- Question Set "combustibles"
        QuestionSet a166 = new QuestionSet(QuestionCode.A166, true, a164);
        session.saveOrUpdate(a166);
        // -- -- Question "type"
        Question a167 = new ValueSelectionQuestion(a166, 0, QuestionCode.A167, CodeList.FUEL);
        session.saveOrUpdate(a167);
        // -- -- Question "quantité"
        Question a168 = new DoubleQuestion(a166, 1, QuestionCode.A168, energyUnits);
        session.saveOrUpdate(a168);

        // -- Question "électricité"
        Question a169 = new DoubleQuestion(a164, 1, QuestionCode.A169, energyUnits);
        session.saveOrUpdate(a169);

        // -- Question Set "gaz réfrigérants"
        QuestionSet a170 = new QuestionSet(QuestionCode.A170, true, a164);
        session.saveOrUpdate(a170);
        // -- -- Question "type"
        Question a171 = new ValueSelectionQuestion(a170, 0, QuestionCode.A171, CodeList.REFRIGERANT_GAS);
        session.saveOrUpdate(a171);
        // -- -- Question "quantité"
        Question a172 = new DoubleQuestion(a170, 1, QuestionCode.A172, massUnits);
        session.saveOrUpdate(a172);

        // -- Answers

        // 1er entrepôt
        QuestionSetAnswer qs164_r0 = new QuestionSetAnswer(scope, period1, a164, 0, null);

        // -- -- 1er combustible
        QuestionSetAnswer qs164_r0_qs166_r0 = new QuestionSetAnswer(scope, period1, a166, 0, qs164_r0);
        // -- -- -- type
        // -- -- -- quantité
        // -- -- -- électricité

        // -- -- 2ème combustible 
        QuestionSetAnswer qs164_r0_qs166_r1 = new QuestionSetAnswer(scope, period1, a166, 1, qs164_r0);
        // -- -- -- type
        // -- -- -- quantité
        // -- -- -- électricité

        // -- -- 1er gaz réfrigérant
        QuestionSetAnswer qs164_r0_qs170_r0 = new QuestionSetAnswer(scope, period1, a170, 0, qs164_r0);
        // -- -- -- type
        // -- -- -- quantité

        // 2ème entrepôt
        QuestionSetAnswer qs164_r1 = new QuestionSetAnswer(scope, period1, a164, 1, null);

        // -- -- 1er combustible
        QuestionSetAnswer qs164_r1_qs166_r0 = new QuestionSetAnswer(scope, period1, a166, 0, qs164_r1);
        // -- -- -- type
        // -- -- -- quantité
        // -- -- -- électricité

        // -- -- 1er gaz réfrigérant
        QuestionSetAnswer qs164_r1_qs170_r0 = new QuestionSetAnswer(scope, period1, a170, 0, qs164_r1);
        // -- -- -- type
        // -- -- -- quantité

        // -- -- 2ème gaz réfrigérant 
        QuestionSetAnswer qs164_r1_qs170_r1 = new QuestionSetAnswer(scope, period1, a170, 1, qs164_r1);
        // -- -- -- type
        // -- -- -- quantité

    }


    private static void createAll(Session session, UnitCategory lengthUnits, UnitCategory surfaceUnits, UnitCategory volumeUnits, UnitCategory massUnits, UnitCategory energyUnits, UnitCategory powerUnits, UnitCategory moneyUnits, UnitCategory timeUnits) {





        // == TAB1 ========================================================================

        Form tab1Form = new Form("TAB1");
        session.saveOrUpdate(tab1Form);


        // == TAB2 ========================================================================

        Form tab2Form = new Form("TAB2");
        session.saveOrUpdate(tab2Form);


        // == TAB3 ========================================================================

        Form tab3Form = new Form("TAB3");
        session.saveOrUpdate(tab3Form);


        // == TAB4 ========================================================================

        Form tab4Form = new Form("TAB4");
        session.saveOrUpdate(tab4Form);


        // == TAB5 ========================================================================

        Form tab5Form = new Form("TAB5");
        session.saveOrUpdate(tab5Form);


        // == TAB6 ========================================================================

        Form tab6Form = new Form("TAB6");
        session.saveOrUpdate(tab6Form);


        // == TAB7 ========================================================================

        Form tab7Form = new Form("TAB7");
        session.saveOrUpdate(tab7Form);


        // == A1 ==========================================================================
        // AWAC - Entreprises
        // A1 (AWAC - Entreprises)

        QuestionSet a1 = new QuestionSet(QuestionCode.A1, false);
        session.saveOrUpdate(a1);
        tab1Form.getQuestionSets().add(a1);
        session.saveOrUpdate(tab1Form);


        // == A13 =========================================================================
        // Consommation de combustibles
        // A13 (Consommation de combustibles)

        QuestionSet a13 = new QuestionSet(QuestionCode.A13, false);
        session.saveOrUpdate(a13);
        tab2Form.getQuestionSets().add(a13);
        session.saveOrUpdate(tab2Form);


        // == A15 =========================================================================
        // Combustion de combustible par les sources statiques des sites de l'entreprise
        // A13(Consommation de combustibles) > A15 (Combustion de combustible par les sources statiques des sites de l'entreprise)

        QuestionSet a15 = new QuestionSet(QuestionCode.A15, false);
        session.saveOrUpdate(a15);
        tab2Form.getQuestionSets().add(a15);
        session.saveOrUpdate(tab2Form);


        // == A20 =========================================================================
        // Electricité et vapeur achetées
        // A20 (Electricité et vapeur achetées)

        QuestionSet a20 = new QuestionSet(QuestionCode.A20, false);
        session.saveOrUpdate(a20);
        tab2Form.getQuestionSets().add(a20);
        session.saveOrUpdate(tab2Form);


        // == A22 =========================================================================
        // Electricité
        // A20(Electricité et vapeur achetées) > A22 (Electricité)

        QuestionSet a22 = new QuestionSet(QuestionCode.A22, false);
        session.saveOrUpdate(a22);
        tab2Form.getQuestionSets().add(a22);
        session.saveOrUpdate(tab2Form);


        // == A25 =========================================================================
        // Vapeur
        // A20(Electricité et vapeur achetées) > A25 (Vapeur)

        QuestionSet a25 = new QuestionSet(QuestionCode.A25, false);
        session.saveOrUpdate(a25);
        tab2Form.getQuestionSets().add(a25);
        session.saveOrUpdate(tab2Form);


        // == A31 =========================================================================
        // GES des processus de production
        // A31 (GES des processus de production)

        QuestionSet a31 = new QuestionSet(QuestionCode.A31, false);
        session.saveOrUpdate(a31);
        tab2Form.getQuestionSets().add(a31);
        session.saveOrUpdate(tab2Form);


        // == A34 =========================================================================
        // Type de GES émis par la production
        // A31(GES des processus de production) > A34 (Type de GES émis par la production)

        QuestionSet a34 = new QuestionSet(QuestionCode.A34, false);
        session.saveOrUpdate(a34);
        tab2Form.getQuestionSets().add(a34);
        session.saveOrUpdate(tab2Form);


        // == A37 =========================================================================
        // Systèmes de refroidissement
        // A37 (Systèmes de refroidissement)

        QuestionSet a37 = new QuestionSet(QuestionCode.A37, false);
        session.saveOrUpdate(a37);
        tab2Form.getQuestionSets().add(a37);
        session.saveOrUpdate(tab2Form);


        // == A40 =========================================================================
        // Méthodes au choix
        // A37(Systèmes de refroidissement) > A40 (Méthodes au choix)

        QuestionSet a40 = new QuestionSet(QuestionCode.A40, false);
        session.saveOrUpdate(a40);
        tab2Form.getQuestionSets().add(a40);
        session.saveOrUpdate(tab2Form);


        // == A41 =========================================================================
        // Estimation des émissions à partir des recharges de gaz
        // A37(Systèmes de refroidissement) > A40(Méthodes au choix) > A41 (Estimation des émissions à partir des recharges de gaz)

        QuestionSet a41 = new QuestionSet(QuestionCode.A41, false);
        session.saveOrUpdate(a41);
        tab2Form.getQuestionSets().add(a41);
        session.saveOrUpdate(tab2Form);


        // == A42 =========================================================================
        // Listes des types de gaz réfrigérants utilisés
        // A37(Systèmes de refroidissement) > A40(Méthodes au choix) > A41(Estimation des émissions à partir des recharges de gaz) > A42 (Listes des types de gaz réfrigérants utilisés)

        QuestionSet a42 = new QuestionSet(QuestionCode.A42, false);
        session.saveOrUpdate(a42);
        tab2Form.getQuestionSets().add(a42);
        session.saveOrUpdate(tab2Form);


        // == A45 =========================================================================
        // Estimation des émissions à partir de la puissance du groupe de froid
        // A37(Systèmes de refroidissement) > A40(Méthodes au choix) > A45 (Estimation des émissions à partir de la puissance du groupe de froid)

        QuestionSet a45 = new QuestionSet(QuestionCode.A45, false);
        session.saveOrUpdate(a45);
        tab2Form.getQuestionSets().add(a45);
        session.saveOrUpdate(tab2Form);


        // == A47 =========================================================================
        // Estimation des émissions à partir de la consommation électrique du site
        // A37(Systèmes de refroidissement) > A40(Méthodes au choix) > A47 (Estimation des émissions à partir de la consommation électrique du site)

        QuestionSet a47 = new QuestionSet(QuestionCode.A47, false);
        session.saveOrUpdate(a47);
        tab2Form.getQuestionSets().add(a47);
        session.saveOrUpdate(tab2Form);


        // == A50 =========================================================================
        // Mobilité
        // A50 (Mobilité)

        QuestionSet a50 = new QuestionSet(QuestionCode.A50, false);
        session.saveOrUpdate(a50);
        tab3Form.getQuestionSets().add(a50);
        session.saveOrUpdate(tab3Form);


        // == A52 =========================================================================
        // Transport routier (méthode au choix)
        // A50(Mobilité) > A52 (Transport routier (méthode au choix))

        QuestionSet a52 = new QuestionSet(QuestionCode.A52, false);
        session.saveOrUpdate(a52);
        tab3Form.getQuestionSets().add(a52);
        session.saveOrUpdate(tab3Form);


        // == A53 =========================================================================
        // Calcul par les consommations
        // A50(Mobilité) > A52(Transport routier (méthode au choix)) > A53 (Calcul par les consommations)

        QuestionSet a53 = new QuestionSet(QuestionCode.A53, false);
        session.saveOrUpdate(a53);
        tab3Form.getQuestionSets().add(a53);
        session.saveOrUpdate(tab3Form);


        // == A54 =========================================================================
        // Véhicules de société ou détenus par l'entreprise
        // A50(Mobilité) > A52(Transport routier (méthode au choix)) > A53(Calcul par les consommations) > A54 (Véhicules de société ou détenus par l'entreprise)

        QuestionSet a54 = new QuestionSet(QuestionCode.A54, false);
        session.saveOrUpdate(a54);
        tab3Form.getQuestionSets().add(a54);
        session.saveOrUpdate(tab3Form);


        // == A58 =========================================================================
        // Autres véhicules: déplacements domicile-travail des employés
        // A50(Mobilité) > A52(Transport routier (méthode au choix)) > A53(Calcul par les consommations) > A58 (Autres véhicules: déplacements domicile-travail des employés)

        QuestionSet a58 = new QuestionSet(QuestionCode.A58, false);
        session.saveOrUpdate(a58);
        tab3Form.getQuestionSets().add(a58);
        session.saveOrUpdate(tab3Form);


        // == A62 =========================================================================
        // Autres véhicules: Déplacements professionnels & visiteurs
        // A50(Mobilité) > A52(Transport routier (méthode au choix)) > A53(Calcul par les consommations) > A62 (Autres véhicules: Déplacements professionnels & visiteurs)

        QuestionSet a62 = new QuestionSet(QuestionCode.A62, false);
        session.saveOrUpdate(a62);
        tab3Form.getQuestionSets().add(a62);
        session.saveOrUpdate(tab3Form);


        // == A66 =========================================================================
        // Calcul par les kilomètres
        // A50(Mobilité) > A52(Transport routier (méthode au choix)) > A66 (Calcul par les kilomètres)

        QuestionSet a66 = new QuestionSet(QuestionCode.A66, false);
        session.saveOrUpdate(a66);
        tab3Form.getQuestionSets().add(a66);
        session.saveOrUpdate(tab3Form);


        // == A67 =========================================================================
        // Créez autant de catégories de véhicules que souhaité
        // A50(Mobilité) > A52(Transport routier (méthode au choix)) > A66(Calcul par les kilomètres) > A67 (Créez autant de catégories de véhicules que souhaité)

        QuestionSet a67 = new QuestionSet(QuestionCode.A67, false);
        session.saveOrUpdate(a67);
        tab3Form.getQuestionSets().add(a67);
        session.saveOrUpdate(tab3Form);


        // == A77 =========================================================================
        // Calcul par euros dépensés
        // A50(Mobilité) > A52(Transport routier (méthode au choix)) > A77 (Calcul par euros dépensés)

        QuestionSet a77 = new QuestionSet(QuestionCode.A77, false);
        session.saveOrUpdate(a77);
        tab3Form.getQuestionSets().add(a77);
        session.saveOrUpdate(tab3Form);


        // == A78 =========================================================================
        // Créez autant de catégories de véhicules que souhaité
        // A50(Mobilité) > A52(Transport routier (méthode au choix)) > A77(Calcul par euros dépensés) > A78 (Créez autant de catégories de véhicules que souhaité)

        QuestionSet a78 = new QuestionSet(QuestionCode.A78, false);
        session.saveOrUpdate(a78);
        tab3Form.getQuestionSets().add(a78);
        session.saveOrUpdate(tab3Form);


        // == A93 =========================================================================
        // Transport en commun
        // A50(Mobilité) > A93 (Transport en commun)

        QuestionSet a93 = new QuestionSet(QuestionCode.A93, false);
        session.saveOrUpdate(a93);
        tab3Form.getQuestionSets().add(a93);
        session.saveOrUpdate(tab3Form);


        // == A94 =========================================================================
        // Estimation par le détail des déplacements
        // A50(Mobilité) > A93(Transport en commun) > A94 (Estimation par le détail des déplacements)

        QuestionSet a94 = new QuestionSet(QuestionCode.A94, false);
        session.saveOrUpdate(a94);
        tab3Form.getQuestionSets().add(a94);
        session.saveOrUpdate(tab3Form);


        // == A109 ========================================================================
        // Estimation par nombre d'employés
        // A50(Mobilité) > A93(Transport en commun) > A109 (Estimation par nombre d'employés)

        QuestionSet a109 = new QuestionSet(QuestionCode.A109, false);
        session.saveOrUpdate(a109);
        tab3Form.getQuestionSets().add(a109);
        session.saveOrUpdate(tab3Form);


        // == A113 ========================================================================
        // Transport en avion (déplacements professionnels ou des visiteurs)
        // A50(Mobilité) > A113 (Transport en avion (déplacements professionnels ou des visiteurs))

        QuestionSet a113 = new QuestionSet(QuestionCode.A113, false);
        session.saveOrUpdate(a113);
        tab3Form.getQuestionSets().add(a113);
        session.saveOrUpdate(tab3Form);


        // == A114 ========================================================================
        // Méthode par le détail des vols
        // A50(Mobilité) > A113(Transport en avion (déplacements professionnels ou des visiteurs)) > A114 (Méthode par le détail des vols)

        QuestionSet a114 = new QuestionSet(QuestionCode.A114, false);
        session.saveOrUpdate(a114);
        tab3Form.getQuestionSets().add(a114);
        session.saveOrUpdate(tab3Form);


        // == A115 ========================================================================
        // Créez autant de catégories de vol que nécessaire
        // A50(Mobilité) > A113(Transport en avion (déplacements professionnels ou des visiteurs)) > A114(Méthode par le détail des vols) > A115 (Créez autant de catégories de vol que nécessaire)

        QuestionSet a115 = new QuestionSet(QuestionCode.A115, false);
        session.saveOrUpdate(a115);
        tab3Form.getQuestionSets().add(a115);
        session.saveOrUpdate(tab3Form);


        // == A121 ========================================================================
        // Méthode des moyennes
        // A50(Mobilité) > A113(Transport en avion (déplacements professionnels ou des visiteurs)) > A121 (Méthode des moyennes)

        QuestionSet a121 = new QuestionSet(QuestionCode.A121, false);
        session.saveOrUpdate(a121);
        tab3Form.getQuestionSets().add(a121);
        session.saveOrUpdate(tab3Form);


        // == A128 ========================================================================
        // Transport et distribution de marchandises amont
        // A128 (Transport et distribution de marchandises amont)

        QuestionSet a128 = new QuestionSet(QuestionCode.A128, false);
        session.saveOrUpdate(a128);
        tab4Form.getQuestionSets().add(a128);
        session.saveOrUpdate(tab4Form);


        // == A130 ========================================================================
        // Transport amont
        // A128(Transport et distribution de marchandises amont) > A130 (Transport amont)

        QuestionSet a130 = new QuestionSet(QuestionCode.A130, false);
        session.saveOrUpdate(a130);
        tab4Form.getQuestionSets().add(a130);
        session.saveOrUpdate(tab4Form);


        // == A131 ========================================================================
        // Transport avec des véhicules détenus par l'entreprise
        // A128(Transport et distribution de marchandises amont) > A130(Transport amont) > A131 (Transport avec des véhicules détenus par l'entreprise)

        QuestionSet a131 = new QuestionSet(QuestionCode.A131, false);
        session.saveOrUpdate(a131);
        tab4Form.getQuestionSets().add(a131);
        session.saveOrUpdate(tab4Form);


        // == A132 ========================================================================
        // Méthode par consommation de carburants
        // A128(Transport et distribution de marchandises amont) > A130(Transport amont) > A131(Transport avec des véhicules détenus par l'entreprise) > A132 (Méthode par consommation de carburants)

        QuestionSet a132 = new QuestionSet(QuestionCode.A132, false);
        session.saveOrUpdate(a132);
        tab4Form.getQuestionSets().add(a132);
        session.saveOrUpdate(tab4Form);


        // == A140 ========================================================================
        // Transport effectué par des transporteurs
        // A128(Transport et distribution de marchandises amont) > A130(Transport amont) > A140 (Transport effectué par des transporteurs)

        QuestionSet a140 = new QuestionSet(QuestionCode.A140, false);
        session.saveOrUpdate(a140);
        tab4Form.getQuestionSets().add(a140);
        session.saveOrUpdate(tab4Form);


        // == A141 ========================================================================
        // Méthode des kilomètres
        // A128(Transport et distribution de marchandises amont) > A130(Transport amont) > A140(Transport effectué par des transporteurs) > A141 (Méthode des kilomètres)

        QuestionSet a141 = new QuestionSet(QuestionCode.A141, false);
        session.saveOrUpdate(a141);
        tab4Form.getQuestionSets().add(a141);
        session.saveOrUpdate(tab4Form);


        // == A142 ========================================================================
        // Créez autant de marchandises que nécessaire
        // A128(Transport et distribution de marchandises amont) > A130(Transport amont) > A140(Transport effectué par des transporteurs) > A141(Méthode des kilomètres) > A142 (Créez autant de marchandises que nécessaire)

        QuestionSet a142 = new QuestionSet(QuestionCode.A142, false);
        session.saveOrUpdate(a142);
        tab4Form.getQuestionSets().add(a142);
        session.saveOrUpdate(tab4Form);


        // == A157 ========================================================================
        // Méthode des moyennes
        // A128(Transport et distribution de marchandises amont) > A130(Transport amont) > A140(Transport effectué par des transporteurs) > A157 (Méthode des moyennes)

        QuestionSet a157 = new QuestionSet(QuestionCode.A157, false);
        session.saveOrUpdate(a157);
        tab4Form.getQuestionSets().add(a157);
        session.saveOrUpdate(tab4Form);


        // == A163 ========================================================================
        // Distribution amont: Energie et froid des entrepôts de stockage
        // A128(Transport et distribution de marchandises amont) > A163 (Distribution amont: Energie et froid des entrepôts de stockage)

        QuestionSet a163 = new QuestionSet(QuestionCode.A163, false);
        session.saveOrUpdate(a163);
        tab4Form.getQuestionSets().add(a163);
        session.saveOrUpdate(tab4Form);


        // == A164 ========================================================================
        // Créez autant d'entrepôts de stockage que nécessaire
        // A128(Transport et distribution de marchandises amont) > A163(Distribution amont: Energie et froid des entrepôts de stockage) > A164 (Créez autant d'entrepôts de stockage que nécessaire)

        QuestionSet a164 = new QuestionSet(QuestionCode.A164, false);
        session.saveOrUpdate(a164);
        tab4Form.getQuestionSets().add(a164);
        session.saveOrUpdate(tab4Form);


        // == A166 ========================================================================
        // Listez les totaux de combustibles utilisés en amont
        // A128(Transport et distribution de marchandises amont) > A163(Distribution amont: Energie et froid des entrepôts de stockage) > A164(Créez autant d'entrepôts de stockage que nécessaire) > A166 (Listez les totaux de combustibles utilisés en amont)

        QuestionSet a166 = new QuestionSet(QuestionCode.A166, false);
        session.saveOrUpdate(a166);
        tab4Form.getQuestionSets().add(a166);
        session.saveOrUpdate(tab4Form);


        // == A170 ========================================================================
        // Listez les gaz réfrigérants utilisés pour les marchandises amont
        // A128(Transport et distribution de marchandises amont) > A163(Distribution amont: Energie et froid des entrepôts de stockage) > A164(Créez autant d'entrepôts de stockage que nécessaire) > A170 (Listez les gaz réfrigérants utilisés pour les marchandises amont)

        QuestionSet a170 = new QuestionSet(QuestionCode.A170, false);
        session.saveOrUpdate(a170);
        tab4Form.getQuestionSets().add(a170);
        session.saveOrUpdate(tab4Form);


        // == A173 ========================================================================
        // Déchets générés par les opérations
        // A173 (Déchets générés par les opérations)

        QuestionSet a173 = new QuestionSet(QuestionCode.A173, false);
        session.saveOrUpdate(a173);
        tab5Form.getQuestionSets().add(a173);
        session.saveOrUpdate(tab5Form);


        // == A175 ========================================================================
        // Listez vos différents postes de déchets
        // A173(Déchets générés par les opérations) > A175 (Listez vos différents postes de déchets)

        QuestionSet a175 = new QuestionSet(QuestionCode.A175, false);
        session.saveOrUpdate(a175);
        tab5Form.getQuestionSets().add(a175);
        session.saveOrUpdate(tab5Form);


        // == A180 ========================================================================
        // Eaux usées
        // A173(Déchets générés par les opérations) > A180 (Eaux usées)

        QuestionSet a180 = new QuestionSet(QuestionCode.A180, false);
        session.saveOrUpdate(a180);
        tab5Form.getQuestionSets().add(a180);
        session.saveOrUpdate(tab5Form);


        // == A181 ========================================================================
        // Eaux usées domestiques par grand type de bâtiments
        // A173(Déchets générés par les opérations) > A180(Eaux usées) > A181 (Eaux usées domestiques par grand type de bâtiments)

        QuestionSet a181 = new QuestionSet(QuestionCode.A181, false);
        session.saveOrUpdate(a181);
        tab5Form.getQuestionSets().add(a181);
        session.saveOrUpdate(tab5Form);


        // == A182 ========================================================================
        // Usine ou atelier
        // A173(Déchets générés par les opérations) > A180(Eaux usées) > A181(Eaux usées domestiques par grand type de bâtiments) > A182 (Usine ou atelier)

        QuestionSet a182 = new QuestionSet(QuestionCode.A182, false);
        session.saveOrUpdate(a182);
        tab5Form.getQuestionSets().add(a182);
        session.saveOrUpdate(tab5Form);


        // == A185 ========================================================================
        // Bureau
        // A173(Déchets générés par les opérations) > A180(Eaux usées) > A181(Eaux usées domestiques par grand type de bâtiments) > A185 (Bureau)

        QuestionSet a185 = new QuestionSet(QuestionCode.A185, false);
        session.saveOrUpdate(a185);
        tab5Form.getQuestionSets().add(a185);
        session.saveOrUpdate(tab5Form);


        // == A188 ========================================================================
        // Hôtel, pension, hôpitaux, prison
        // A173(Déchets générés par les opérations) > A180(Eaux usées) > A181(Eaux usées domestiques par grand type de bâtiments) > A188 (Hôtel, pension, hôpitaux, prison)

        QuestionSet a188 = new QuestionSet(QuestionCode.A188, false);
        session.saveOrUpdate(a188);
        tab5Form.getQuestionSets().add(a188);
        session.saveOrUpdate(tab5Form);


        // == A191 ========================================================================
        // Restaurant ou cantine
        // A173(Déchets générés par les opérations) > A180(Eaux usées) > A181(Eaux usées domestiques par grand type de bâtiments) > A191 (Restaurant ou cantine)

        QuestionSet a191 = new QuestionSet(QuestionCode.A191, false);
        session.saveOrUpdate(a191);
        tab5Form.getQuestionSets().add(a191);
        session.saveOrUpdate(tab5Form);


        // == A194 ========================================================================
        // Eaux usées industrielles
        // A173(Déchets générés par les opérations) > A180(Eaux usées) > A194 (Eaux usées industrielles)

        QuestionSet a194 = new QuestionSet(QuestionCode.A194, false);
        session.saveOrUpdate(a194);
        tab5Form.getQuestionSets().add(a194);
        session.saveOrUpdate(tab5Form);


        // == A196 ========================================================================
        // Méthodes alternatives
        // A173(Déchets générés par les opérations) > A180(Eaux usées) > A194(Eaux usées industrielles) > A196 (Méthodes alternatives)

        QuestionSet a196 = new QuestionSet(QuestionCode.A196, false);
        session.saveOrUpdate(a196);
        tab5Form.getQuestionSets().add(a196);
        session.saveOrUpdate(tab5Form);


        // == A197 ========================================================================
        // Méthode par la quantité de m³ rejetés
        // A173(Déchets générés par les opérations) > A180(Eaux usées) > A194(Eaux usées industrielles) > A196(Méthodes alternatives) > A197 (Méthode par la quantité de m³ rejetés)

        QuestionSet a197 = new QuestionSet(QuestionCode.A197, false);
        session.saveOrUpdate(a197);
        tab5Form.getQuestionSets().add(a197);
        session.saveOrUpdate(tab5Form);


        // == A201 ========================================================================
        // Méthode par le poids de CO2 chimique des effluents rejetés
        // A173(Déchets générés par les opérations) > A180(Eaux usées) > A194(Eaux usées industrielles) > A196(Méthodes alternatives) > A201 (Méthode par le poids de CO2 chimique des effluents rejetés)

        QuestionSet a201 = new QuestionSet(QuestionCode.A201, false);
        session.saveOrUpdate(a201);
        tab5Form.getQuestionSets().add(a201);
        session.saveOrUpdate(tab5Form);


        // == A205 ========================================================================
        // Achat de biens et services
        // A205 (Achat de biens et services)

        QuestionSet a205 = new QuestionSet(QuestionCode.A205, false);
        session.saveOrUpdate(a205);
        tab6Form.getQuestionSets().add(a205);
        session.saveOrUpdate(tab6Form);


        // == A208 ========================================================================
        // Méthode par détail des achats
        // A208 (Méthode par détail des achats)

        QuestionSet a208 = new QuestionSet(QuestionCode.A208, false);
        session.saveOrUpdate(a208);
        tab6Form.getQuestionSets().add(a208);
        session.saveOrUpdate(tab6Form);


        // == A209 ========================================================================
        // Créez et nommez vos postes d'achats (et préciser la catégorie et le type de matériaux ensuite)
        // A208(Méthode par détail des achats) > A209 (Créez et nommez vos postes d'achats (et préciser la catégorie et le type de matériaux ensuite))

        QuestionSet a209 = new QuestionSet(QuestionCode.A209, false);
        session.saveOrUpdate(a209);
        tab6Form.getQuestionSets().add(a209);
        session.saveOrUpdate(tab6Form);


        // == A223 ========================================================================
        // Autres matériaux spécifiques pour lesquels l'entreprise dispose du facteur d'émissions cradle-to-gate
        // A208(Méthode par détail des achats) > A223 (Autres matériaux spécifiques pour lesquels l'entreprise dispose du facteur d'émissions cradle-to-gate)

        QuestionSet a223 = new QuestionSet(QuestionCode.A223, false);
        session.saveOrUpdate(a223);
        tab6Form.getQuestionSets().add(a223);
        session.saveOrUpdate(tab6Form);


        // == A224 ========================================================================
        // Créez et nommez vos postes d'achats spécifiques (et précisez ensuite la catégorie, le type de matériaux et le facteur d'émission cradle-to-gate spécifique)
        // A208(Méthode par détail des achats) > A223(Autres matériaux spécifiques pour lesquels l'entreprise dispose du facteur d'émissions cradle-to-gate) > A224 (Créez et nommez vos postes d'achats spécifiques (et précisez ensuite la catégorie, le type de matériaux et le facteur d'émission cradle-to-gate spécifique))

        QuestionSet a224 = new QuestionSet(QuestionCode.A224, false);
        session.saveOrUpdate(a224);
        tab6Form.getQuestionSets().add(a224);
        session.saveOrUpdate(tab6Form);


        // == A229 ========================================================================
        // Infrastructures (achetées durant l'année de déclaration)
        // A229 (Infrastructures (achetées durant l'année de déclaration))

        QuestionSet a229 = new QuestionSet(QuestionCode.A229, false);
        session.saveOrUpdate(a229);
        tab6Form.getQuestionSets().add(a229);
        session.saveOrUpdate(tab6Form);


        // == A231 ========================================================================
        // Créez et nommez vos postes d'infrastructure
        // A229(Infrastructures (achetées durant l'année de déclaration)) > A231 (Créez et nommez vos postes d'infrastructure)

        QuestionSet a231 = new QuestionSet(QuestionCode.A231, false);
        session.saveOrUpdate(a231);
        tab6Form.getQuestionSets().add(a231);
        session.saveOrUpdate(tab6Form);


        // == A237 ========================================================================
        // Autres infrastructures spécifiques pour lesquels l'entreprise dispose du facteur d'émission cradle-to-gate
        // A229(Infrastructures (achetées durant l'année de déclaration)) > A237 (Autres infrastructures spécifiques pour lesquels l'entreprise dispose du facteur d'émission cradle-to-gate)

        QuestionSet a237 = new QuestionSet(QuestionCode.A237, false);
        session.saveOrUpdate(a237);
        tab6Form.getQuestionSets().add(a237);
        session.saveOrUpdate(tab6Form);


        // == A238 ========================================================================
        // Créez et nommez vos postes d'infrastructure spécifiques
        // A229(Infrastructures (achetées durant l'année de déclaration)) > A237(Autres infrastructures spécifiques pour lesquels l'entreprise dispose du facteur d'émission cradle-to-gate) > A238 (Créez et nommez vos postes d'infrastructure spécifiques)

        QuestionSet a238 = new QuestionSet(QuestionCode.A238, false);
        session.saveOrUpdate(a238);
        tab6Form.getQuestionSets().add(a238);
        session.saveOrUpdate(tab6Form);


        // == A243 ========================================================================
        // Transport & Distribution, Traitement, Utilisation et Fin de vie des produits vendus
        // A243 (Transport & Distribution, Traitement, Utilisation et Fin de vie des produits vendus)

        QuestionSet a243 = new QuestionSet(QuestionCode.A243, false);
        session.saveOrUpdate(a243);
        tab7Form.getQuestionSets().add(a243);
        session.saveOrUpdate(tab7Form);


        // == A244 ========================================================================
        // Lister les différents produits ou groupes de produits vendus par l'entreprise
        // A243(Transport & Distribution, Traitement, Utilisation et Fin de vie des produits vendus) > A244 (Lister les différents produits ou groupes de produits vendus par l'entreprise)

        QuestionSet a244 = new QuestionSet(QuestionCode.A244, false);
        session.saveOrUpdate(a244);
        tab7Form.getQuestionSets().add(a244);
        session.saveOrUpdate(tab7Form);


        // == A250 ========================================================================
        // Transport et distribution aval
        // A243(Transport & Distribution, Traitement, Utilisation et Fin de vie des produits vendus) > A244(Lister les différents produits ou groupes de produits vendus par l'entreprise) > A250 (Transport et distribution aval)

        QuestionSet a250 = new QuestionSet(QuestionCode.A250, false);
        session.saveOrUpdate(a250);
        tab7Form.getQuestionSets().add(a250);
        session.saveOrUpdate(tab7Form);


        // == A252 ========================================================================
        // Transport aval: choix de méthodes
        // A243(Transport & Distribution, Traitement, Utilisation et Fin de vie des produits vendus) > A244(Lister les différents produits ou groupes de produits vendus par l'entreprise) > A250(Transport et distribution aval) > A252 (Transport aval: choix de méthodes)

        QuestionSet a252 = new QuestionSet(QuestionCode.A252, false);
        session.saveOrUpdate(a252);
        tab7Form.getQuestionSets().add(a252);
        session.saveOrUpdate(tab7Form);


        // == A253 ========================================================================
        // Méthode par kilométrage
        // A243(Transport & Distribution, Traitement, Utilisation et Fin de vie des produits vendus) > A244(Lister les différents produits ou groupes de produits vendus par l'entreprise) > A250(Transport et distribution aval) > A252(Transport aval: choix de méthodes) > A253 (Méthode par kilométrage)

        QuestionSet a253 = new QuestionSet(QuestionCode.A253, false);
        session.saveOrUpdate(a253);
        tab7Form.getQuestionSets().add(a253);
        session.saveOrUpdate(tab7Form);


        // == A266 ========================================================================
        // Méthode des moyennes
        // A243(Transport & Distribution, Traitement, Utilisation et Fin de vie des produits vendus) > A244(Lister les différents produits ou groupes de produits vendus par l'entreprise) > A250(Transport et distribution aval) > A252(Transport aval: choix de méthodes) > A266 (Méthode des moyennes)

        QuestionSet a266 = new QuestionSet(QuestionCode.A266, false);
        session.saveOrUpdate(a266);
        tab7Form.getQuestionSets().add(a266);
        session.saveOrUpdate(tab7Form);


        // == A272 ========================================================================
        // Distribution avale: Energie et Froid des entrepôts de stockage
        // A243(Transport & Distribution, Traitement, Utilisation et Fin de vie des produits vendus) > A244(Lister les différents produits ou groupes de produits vendus par l'entreprise) > A272 (Distribution avale: Energie et Froid des entrepôts de stockage)

        QuestionSet a272 = new QuestionSet(QuestionCode.A272, false);
        session.saveOrUpdate(a272);
        tab7Form.getQuestionSets().add(a272);
        session.saveOrUpdate(tab7Form);


        // == A273 ========================================================================
        // Créez autant d'entrepôts de stockage que nécessaire
        // A243(Transport & Distribution, Traitement, Utilisation et Fin de vie des produits vendus) > A244(Lister les différents produits ou groupes de produits vendus par l'entreprise) > A272(Distribution avale: Energie et Froid des entrepôts de stockage) > A273 (Créez autant d'entrepôts de stockage que nécessaire)

        QuestionSet a273 = new QuestionSet(QuestionCode.A273, false);
        session.saveOrUpdate(a273);
        tab7Form.getQuestionSets().add(a273);
        session.saveOrUpdate(tab7Form);


        // == A275 ========================================================================
        // Listez les totaux de combustibles utilisés
        // A31(GES des processus de production) > A34(Type de GES émis par la production) > A275 (Listez les totaux de combustibles utilisés)

        QuestionSet a275 = new QuestionSet(QuestionCode.A275, false);
        session.saveOrUpdate(a275);
        tab7Form.getQuestionSets().add(a275);
        session.saveOrUpdate(tab7Form);


        // == A279 ========================================================================
        // Listez les gaz réfrigérants
        // A31(GES des processus de production) > A34(Type de GES émis par la production) > A279 (Listez les gaz réfrigérants)

        QuestionSet a279 = new QuestionSet(QuestionCode.A279, false);
        session.saveOrUpdate(a279);
        tab7Form.getQuestionSets().add(a279);
        session.saveOrUpdate(tab7Form);


        // == A282 ========================================================================
        // Traitement
        // A243(Transport & Distribution, Traitement, Utilisation et Fin de vie des produits vendus) > A244(Lister les différents produits ou groupes de produits vendus par l'entreprise) > A282 (Traitement)

        QuestionSet a282 = new QuestionSet(QuestionCode.A282, false);
        session.saveOrUpdate(a282);
        tab7Form.getQuestionSets().add(a282);
        session.saveOrUpdate(tab7Form);


        // == A284 ========================================================================
        // Listez les totaux de combustibles
        // A243(Transport & Distribution, Traitement, Utilisation et Fin de vie des produits vendus) > A244(Lister les différents produits ou groupes de produits vendus par l'entreprise) > A282(Traitement) > A284 (Listez les totaux de combustibles)

        QuestionSet a284 = new QuestionSet(QuestionCode.A284, false);
        session.saveOrUpdate(a284);
        tab7Form.getQuestionSets().add(a284);
        session.saveOrUpdate(tab7Form);


        // == A288 ========================================================================
        // Listez les gaz réfrigérants
        // A243(Transport & Distribution, Traitement, Utilisation et Fin de vie des produits vendus) > A244(Lister les différents produits ou groupes de produits vendus par l'entreprise) > A282(Traitement) > A288 (Listez les gaz réfrigérants)

        QuestionSet a288 = new QuestionSet(QuestionCode.A288, false);
        session.saveOrUpdate(a288);
        tab7Form.getQuestionSets().add(a288);
        session.saveOrUpdate(tab7Form);


        // == A291 ========================================================================
        // Utilisation
        // A243(Transport & Distribution, Traitement, Utilisation et Fin de vie des produits vendus) > A244(Lister les différents produits ou groupes de produits vendus par l'entreprise) > A291 (Utilisation)

        QuestionSet a291 = new QuestionSet(QuestionCode.A291, false);
        session.saveOrUpdate(a291);
        tab7Form.getQuestionSets().add(a291);
        session.saveOrUpdate(tab7Form);


        // == A297 ========================================================================
        // Listez les gaz émis par utilisation de produit
        // A243(Transport & Distribution, Traitement, Utilisation et Fin de vie des produits vendus) > A244(Lister les différents produits ou groupes de produits vendus par l'entreprise) > A291(Utilisation) > A297 (Listez les gaz émis par utilisation de produit)

        QuestionSet a297 = new QuestionSet(QuestionCode.A297, false);
        session.saveOrUpdate(a297);
        tab7Form.getQuestionSets().add(a297);
        session.saveOrUpdate(tab7Form);


        // == A300 ========================================================================
        // Traitement de fin de vie
        // A243(Transport & Distribution, Traitement, Utilisation et Fin de vie des produits vendus) > A244(Lister les différents produits ou groupes de produits vendus par l'entreprise) > A300 (Traitement de fin de vie)

        QuestionSet a300 = new QuestionSet(QuestionCode.A300, false);
        session.saveOrUpdate(a300);
        tab7Form.getQuestionSets().add(a300);
        session.saveOrUpdate(tab7Form);


        // == A303 ========================================================================
        // Créez autant de catégories de déchet que nécessaire
        // A243(Transport & Distribution, Traitement, Utilisation et Fin de vie des produits vendus) > A244(Lister les différents produits ou groupes de produits vendus par l'entreprise) > A300(Traitement de fin de vie) > A303 (Créez autant de catégories de déchet que nécessaire)

        QuestionSet a303 = new QuestionSet(QuestionCode.A303, false);
        session.saveOrUpdate(a303);
        tab7Form.getQuestionSets().add(a303);
        session.saveOrUpdate(tab7Form);


        // == A309 ========================================================================
        // Actifs loués (aval)
        // A309 (Actifs loués (aval))

        QuestionSet a309 = new QuestionSet(QuestionCode.A309, false);
        session.saveOrUpdate(a309);
        tab7Form.getQuestionSets().add(a309);
        session.saveOrUpdate(tab7Form);


        // == A311 ========================================================================
        // Créez autant de catégories d'actifs loués que nécessaire
        // A309(Actifs loués (aval)) > A311 (Créez autant de catégories d'actifs loués que nécessaire)

        QuestionSet a311 = new QuestionSet(QuestionCode.A311, false);
        session.saveOrUpdate(a311);
        tab7Form.getQuestionSets().add(a311);
        session.saveOrUpdate(tab7Form);


        // == A313 ========================================================================
        // Listez les totaux de combustibles utilisés pour les actifs loués
        // A309(Actifs loués (aval)) > A311(Créez autant de catégories d'actifs loués que nécessaire) > A313 (Listez les totaux de combustibles utilisés pour les actifs loués)

        QuestionSet a313 = new QuestionSet(QuestionCode.A313, false);
        session.saveOrUpdate(a313);
        tab7Form.getQuestionSets().add(a313);
        session.saveOrUpdate(tab7Form);


        // == A317 ========================================================================
        // Listez les gaz réfrigérants et autres nécessaires à l'opération des actifs loués
        // A309(Actifs loués (aval)) > A311(Créez autant de catégories d'actifs loués que nécessaire) > A317 (Listez les gaz réfrigérants et autres nécessaires à l'opération des actifs loués)

        QuestionSet a317 = new QuestionSet(QuestionCode.A317, false);
        session.saveOrUpdate(a317);
        tab7Form.getQuestionSets().add(a317);
        session.saveOrUpdate(tab7Form);


        // == A320 ========================================================================
        // Franchises
        // A320 (Franchises)

        QuestionSet a320 = new QuestionSet(QuestionCode.A320, false);
        session.saveOrUpdate(a320);
        tab7Form.getQuestionSets().add(a320);
        session.saveOrUpdate(tab7Form);


        // == A322 ========================================================================
        // Créez autant de catégories de franchisés que nécessaire
        // A320(Franchises) > A322 (Créez autant de catégories de franchisés que nécessaire)

        QuestionSet a322 = new QuestionSet(QuestionCode.A322, false);
        session.saveOrUpdate(a322);
        tab7Form.getQuestionSets().add(a322);
        session.saveOrUpdate(tab7Form);


        // == A325 ========================================================================
        // Listez les moyennes de combustibles utilisés par franchisé
        // A320(Franchises) > A322(Créez autant de catégories de franchisés que nécessaire) > A325 (Listez les moyennes de combustibles utilisés par franchisé)

        QuestionSet a325 = new QuestionSet(QuestionCode.A325, false);
        session.saveOrUpdate(a325);
        tab7Form.getQuestionSets().add(a325);
        session.saveOrUpdate(tab7Form);


        // == A329 ========================================================================
        // Listez les gaz réfrigérants et autres utilisés en moyenne par franchisé
        // A320(Franchises) > A322(Créez autant de catégories de franchisés que nécessaire) > A329 (Listez les gaz réfrigérants et autres utilisés en moyenne par franchisé)

        QuestionSet a329 = new QuestionSet(QuestionCode.A329, false);
        session.saveOrUpdate(a329);
        tab7Form.getQuestionSets().add(a329);
        session.saveOrUpdate(tab7Form);


        // == A332 ========================================================================
        // Activités d'investissement
        // A332 (Activités d'investissement)

        QuestionSet a332 = new QuestionSet(QuestionCode.A332, false);
        session.saveOrUpdate(a332);
        tab7Form.getQuestionSets().add(a332);
        session.saveOrUpdate(tab7Form);


        // == A334 ========================================================================
        // Veuillez indiquer ici tous les projets dans lesquels votre entreprise investit
        // A332(Activités d'investissement) > A334 (Veuillez indiquer ici tous les projets dans lesquels votre entreprise investit)

        QuestionSet a334 = new QuestionSet(QuestionCode.A334, false);
        session.saveOrUpdate(a334);
        tab7Form.getQuestionSets().add(a334);
        session.saveOrUpdate(tab7Form);


        // == A2 ==========================================================================
        // Année de référence pour comparaison du présent bilan GES
        // A1(AWAC - Entreprises) > A2 (Année de référence pour comparaison du présent bilan GES)
        session.saveOrUpdate(new IntegerQuestion(a1, 0, QuestionCode.A2, null));

        // == A3 ==========================================================================
        // A quel secteur principal appartient votre site?
        // A1(AWAC - Entreprises) > A3 (A quel secteur principal appartient votre site?)
        session.saveOrUpdate(new ValueSelectionQuestion(a1, 0, QuestionCode.A3, CodeList.SITE_SECTORS));

        // == A4 ==========================================================================
        // Quel est le code NACE principal de votre site?
        // A1(AWAC - Entreprises) > A4 (Quel est le code NACE principal de votre site?)
        session.saveOrUpdate(new ValueSelectionQuestion(a1, 0, QuestionCode.A4, CodeList.NACE_CODES_1));

        // == A5 ==========================================================================
        // Quel est le code NACE principal de votre site?
        // A1(AWAC - Entreprises) > A5 (Quel est le code NACE principal de votre site?)
        session.saveOrUpdate(new ValueSelectionQuestion(a1, 0, QuestionCode.A5, CodeList.NACE_CODES_2));

        // == A6 ==========================================================================
        // Quel est le code NACE principal de votre site?
        // A1(AWAC - Entreprises) > A6 (Quel est le code NACE principal de votre site?)
        session.saveOrUpdate(new ValueSelectionQuestion(a1, 0, QuestionCode.A6, CodeList.NACE_CODES_3));

        // == A7 ==========================================================================
        // Est-ce que votre activité est purement ou principalement de bureaux?
        // A1(AWAC - Entreprises) > A7 (Est-ce que votre activité est purement ou principalement de bureaux?)
        session.saveOrUpdate(new BooleanQuestion(a1, 0, QuestionCode.A7));

        // == A8 ==========================================================================
        // Etes-vous dans le secteur public ou privé?
        // A1(AWAC - Entreprises) > A8 (Etes-vous dans le secteur public ou privé?)
        session.saveOrUpdate(new ValueSelectionQuestion(a1, 0, QuestionCode.A8, CodeList.PUBLIC_PRIVATE));

        // == A9 ==========================================================================
        // Indiquez la surface totale du site:
        // A1(AWAC - Entreprises) > A9 (Indiquez la surface totale du site:)
        session.saveOrUpdate(new DoubleQuestion(a1, 0, QuestionCode.A9, surfaceUnits));

        // == A10 =========================================================================
        // Quelle est la surface des bureaux?
        // A1(AWAC - Entreprises) > A10 (Quelle est la surface des bureaux?)
        session.saveOrUpdate(new DoubleQuestion(a1, 0, QuestionCode.A10, surfaceUnits));

        // == A11 =========================================================================
        // Etes-vous participant aux accords de branche de 2ème génération?
        // A1(AWAC - Entreprises) > A11 (Etes-vous participant aux accords de branche de 2ème génération?)
        session.saveOrUpdate(new BooleanQuestion(a1, 0, QuestionCode.A11));

        // == A12 =========================================================================
        // Quel est le nombre d'employés sur l'année du bilan?
        // A1(AWAC - Entreprises) > A12 (Quel est le nombre d'employés sur l'année du bilan?)
        session.saveOrUpdate(new IntegerQuestion(a1, 0, QuestionCode.A12, null));

        // == A14 =========================================================================
        // Pièces documentaires liées aux consommations de combustible
        // A13(Consommation de combustibles) > A14 (Pièces documentaires liées aux consommations de combustible)
        session.saveOrUpdate(new StringQuestion(a13, 0, QuestionCode.A14));

        // == A16 =========================================================================
        // Combustible
        // A13(Consommation de combustibles) > A15(Combustion de combustible par les sources statiques des sites de l'entreprise) > A16 (Combustible)
        session.saveOrUpdate(new ValueSelectionQuestion(a15, 0, QuestionCode.A16, CodeList.FUEL));

        // == A17 =========================================================================
        // Quantité
        // A13(Consommation de combustibles) > A15(Combustion de combustible par les sources statiques des sites de l'entreprise) > A17 (Quantité)
        session.saveOrUpdate(new DoubleQuestion(a15, 0, QuestionCode.A17, energyUnits));

        // == A21 =========================================================================
        // Pièces documentaires liées aux achats d'électricité et de vapeur
        // A20(Electricité et vapeur achetées) > A21 (Pièces documentaires liées aux achats d'électricité et de vapeur)
        session.saveOrUpdate(new StringQuestion(a20, 0, QuestionCode.A21));

        // == A23 =========================================================================
        // Consommation d'électricité verte
        // A20(Electricité et vapeur achetées) > A22(Electricité) > A23 (Consommation d'électricité verte)
        session.saveOrUpdate(new DoubleQuestion(a22, 0, QuestionCode.A23, energyUnits));

        // == A24 =========================================================================
        // Consommation d'électricité grise
        // A20(Electricité et vapeur achetées) > A22(Electricité) > A24 (Consommation d'électricité grise)
        session.saveOrUpdate(new DoubleQuestion(a22, 0, QuestionCode.A24, energyUnits));

        // == A26 =========================================================================
        // Energie primaire utilisée pour produire la vapeur:
        // A20(Electricité et vapeur achetées) > A25(Vapeur) > A26 (Energie primaire utilisée pour produire la vapeur:)
        session.saveOrUpdate(new ValueSelectionQuestion(a25, 0, QuestionCode.A26, CodeList.ENERGIEVAPEUR));

        // == A27 =========================================================================
        // Efficacité de la chaudière
        // A20(Electricité et vapeur achetées) > A25(Vapeur) > A27 (Efficacité de la chaudière)
        session.saveOrUpdate(new DoubleQuestion(a25, 0, QuestionCode.A27, null));

        // == A28 =========================================================================
        // Quantité achetée
        // A20(Electricité et vapeur achetées) > A25(Vapeur) > A28 (Quantité achetée)
        session.saveOrUpdate(new DoubleQuestion(a25, 0, QuestionCode.A28, energyUnits));

        // == A32 =========================================================================
        // Est-ce que vos activités impliquent des procédés chimiques et physiques émetteurs directs de gaz à effet de serre ?
        // A31(GES des processus de production) > A32 (Est-ce que vos activités impliquent des procédés chimiques et physiques émetteurs directs de gaz à effet de serre ?)
        session.saveOrUpdate(new BooleanQuestion(a31, 0, QuestionCode.A32));

        // == A33 =========================================================================
        // Pièces documentaires liées aux GES des processus de production
        // A31(GES des processus de production) > A33 (Pièces documentaires liées aux GES des processus de production)
        session.saveOrUpdate(new StringQuestion(a31, 0, QuestionCode.A33));

        // == A35 =========================================================================
        // Type de GES
        // A31(GES des processus de production) > A34(Type de GES émis par la production) > A35 (Type de GES)
        session.saveOrUpdate(new ValueSelectionQuestion(a34, 0, QuestionCode.A35, CodeList.GES));

        // == A36 =========================================================================
        // Quantité
        // A31(GES des processus de production) > A34(Type de GES émis par la production) > A36 (Quantité)
        session.saveOrUpdate(new DoubleQuestion(a34, 0, QuestionCode.A36, massUnits));

        // == A38 =========================================================================
        // Disposez-vous d’un système de froid nécessitant un apport ponctuel d’agent réfrigérant (p.e. les chillers, les climatiseurs à air et à eau glacée, les réfrigérateurs, bacs à surgelés, etc.)?
        // A37(Systèmes de refroidissement) > A38 (Disposez-vous d’un système de froid nécessitant un apport ponctuel d’agent réfrigérant (p.e. les chillers, les climatiseurs à air et à eau glacée, les réfrigérateurs, bacs à surgelés, etc.)?)
        session.saveOrUpdate(new BooleanQuestion(a37, 0, QuestionCode.A38));

        // == A39 =========================================================================
        // Pièces documentaires liées aux systèmes de refroidissement
        // A37(Systèmes de refroidissement) > A39 (Pièces documentaires liées aux systèmes de refroidissement)
        session.saveOrUpdate(new StringQuestion(a37, 0, QuestionCode.A39));

        // == A43 =========================================================================
        // Type de gaz
        // A37(Systèmes de refroidissement) > A40(Méthodes au choix) > A41(Estimation des émissions à partir des recharges de gaz) > A42(Listes des types de gaz réfrigérants utilisés) > A43 (Type de gaz)
        session.saveOrUpdate(new ValueSelectionQuestion(a42, 0, QuestionCode.A43, CodeList.FRIGORIGENE));

        // == A44 =========================================================================
        // Quantité de recharge nécessaire pour l'année
        // A37(Systèmes de refroidissement) > A40(Méthodes au choix) > A41(Estimation des émissions à partir des recharges de gaz) > A42(Listes des types de gaz réfrigérants utilisés) > A44 (Quantité de recharge nécessaire pour l'année)
        session.saveOrUpdate(new DoubleQuestion(a42, 0, QuestionCode.A44, massUnits));

        // == A46 =========================================================================
        // Quel est la puissance frigorifique des groupes froid?
        // A37(Systèmes de refroidissement) > A40(Méthodes au choix) > A45(Estimation des émissions à partir de la puissance du groupe de froid) > A46 (Quel est la puissance frigorifique des groupes froid?)
        session.saveOrUpdate(new DoubleQuestion(a45, 0, QuestionCode.A46, powerUnits));

        // == A48 =========================================================================
        // Est-ce que votre entreprise produit du sucre ou des pâtes sèches?
        // A37(Systèmes de refroidissement) > A40(Méthodes au choix) > A47(Estimation des émissions à partir de la consommation électrique du site) > A48 (Est-ce que votre entreprise produit du sucre ou des pâtes sèches?)
        session.saveOrUpdate(new BooleanQuestion(a47, 0, QuestionCode.A48));

        // == A49 =========================================================================
        // Quel est le nombre d'heures de fonctionnement annuel du site?
        // A37(Systèmes de refroidissement) > A40(Méthodes au choix) > A47(Estimation des émissions à partir de la consommation électrique du site) > A49 (Quel est le nombre d'heures de fonctionnement annuel du site?)
        session.saveOrUpdate(new DoubleQuestion(a47, 0, QuestionCode.A49, timeUnits));

        // == A51 =========================================================================
        // Pièces documentaires liées à la mobilité
        // A50(Mobilité) > A51 (Pièces documentaires liées à la mobilité)
        session.saveOrUpdate(new StringQuestion(a50, 0, QuestionCode.A51));

        // == A55 =========================================================================
        // Consommation d'essence
        // A50(Mobilité) > A52(Transport routier (méthode au choix)) > A53(Calcul par les consommations) > A54(Véhicules de société ou détenus par l'entreprise) > A55 (Consommation d'essence)
        session.saveOrUpdate(new DoubleQuestion(a54, 0, QuestionCode.A55, volumeUnits));

        // == A56 =========================================================================
        // Consommation de diesel
        // A50(Mobilité) > A52(Transport routier (méthode au choix)) > A53(Calcul par les consommations) > A54(Véhicules de société ou détenus par l'entreprise) > A56 (Consommation de diesel)
        session.saveOrUpdate(new DoubleQuestion(a54, 0, QuestionCode.A56, volumeUnits));

        // == A57 =========================================================================
        // Consommation de gaz de pétrole liquéfié (GPL)
        // A50(Mobilité) > A52(Transport routier (méthode au choix)) > A53(Calcul par les consommations) > A54(Véhicules de société ou détenus par l'entreprise) > A57 (Consommation de gaz de pétrole liquéfié (GPL))
        session.saveOrUpdate(new DoubleQuestion(a54, 0, QuestionCode.A57, volumeUnits));

        // == A59 =========================================================================
        // Consommation d'essence
        // A50(Mobilité) > A52(Transport routier (méthode au choix)) > A53(Calcul par les consommations) > A58(Autres véhicules: déplacements domicile-travail des employés) > A59 (Consommation d'essence)
        session.saveOrUpdate(new DoubleQuestion(a58, 0, QuestionCode.A59, volumeUnits));

        // == A60 =========================================================================
        // Consommation de diesel
        // A50(Mobilité) > A52(Transport routier (méthode au choix)) > A53(Calcul par les consommations) > A58(Autres véhicules: déplacements domicile-travail des employés) > A60 (Consommation de diesel)
        session.saveOrUpdate(new DoubleQuestion(a58, 0, QuestionCode.A60, volumeUnits));

        // == A61 =========================================================================
        // Consommation de gaz de pétrole liquéfié (GPL)
        // A50(Mobilité) > A52(Transport routier (méthode au choix)) > A53(Calcul par les consommations) > A58(Autres véhicules: déplacements domicile-travail des employés) > A61 (Consommation de gaz de pétrole liquéfié (GPL))
        session.saveOrUpdate(new DoubleQuestion(a58, 0, QuestionCode.A61, volumeUnits));

        // == A63 =========================================================================
        // Consommation d'essence
        // A50(Mobilité) > A52(Transport routier (méthode au choix)) > A53(Calcul par les consommations) > A62(Autres véhicules: Déplacements professionnels & visiteurs) > A63 (Consommation d'essence)
        session.saveOrUpdate(new DoubleQuestion(a62, 0, QuestionCode.A63, volumeUnits));

        // == A64 =========================================================================
        // Consommation de diesel
        // A50(Mobilité) > A52(Transport routier (méthode au choix)) > A53(Calcul par les consommations) > A62(Autres véhicules: Déplacements professionnels & visiteurs) > A64 (Consommation de diesel)
        session.saveOrUpdate(new DoubleQuestion(a62, 0, QuestionCode.A64, volumeUnits));

        // == A65 =========================================================================
        // Consommation de gaz de pétrole liquéfié (GPL)
        // A50(Mobilité) > A52(Transport routier (méthode au choix)) > A53(Calcul par les consommations) > A62(Autres véhicules: Déplacements professionnels & visiteurs) > A65 (Consommation de gaz de pétrole liquéfié (GPL))
        session.saveOrUpdate(new DoubleQuestion(a62, 0, QuestionCode.A65, volumeUnits));

        // == A68 =========================================================================
        // Catégorie de véhicule
        // A50(Mobilité) > A52(Transport routier (méthode au choix)) > A66(Calcul par les kilomètres) > A67(Créez autant de catégories de véhicules que souhaité) > A68 (Catégorie de véhicule)
        session.saveOrUpdate(new StringQuestion(a67, 0, QuestionCode.A68));

        // == A69 =========================================================================
        // S'agit-il d'une catégorie de véhicules appartenant ou sous le contrôle à la société ou pas?
        // A50(Mobilité) > A52(Transport routier (méthode au choix)) > A66(Calcul par les kilomètres) > A67(Créez autant de catégories de véhicules que souhaité) > A69 (S'agit-il d'une catégorie de véhicules appartenant ou sous le contrôle à la société ou pas?)
        session.saveOrUpdate(new BooleanQuestion(a67, 0, QuestionCode.A69));

        // == A70 =========================================================================
        // Motif de déplacement
        // A50(Mobilité) > A52(Transport routier (méthode au choix)) > A66(Calcul par les kilomètres) > A67(Créez autant de catégories de véhicules que souhaité) > A70 (Motif de déplacement)
        session.saveOrUpdate(new ValueSelectionQuestion(a67, 0, QuestionCode.A70, CodeList.MOTIFDEPLACEMENT));

        // == A71 =========================================================================
        // Quel type de carburant utilise-t-il ?
        // A50(Mobilité) > A52(Transport routier (méthode au choix)) > A66(Calcul par les kilomètres) > A67(Créez autant de catégories de véhicules que souhaité) > A71 (Quel type de carburant utilise-t-il ?)
        session.saveOrUpdate(new ValueSelectionQuestion(a67, 0, QuestionCode.A71, CodeList.CARBURANT));

        // == A72 =========================================================================
        // De quel type de véhicule s'agit-il?
        // A50(Mobilité) > A52(Transport routier (méthode au choix)) > A66(Calcul par les kilomètres) > A67(Créez autant de catégories de véhicules que souhaité) > A72 (De quel type de véhicule s'agit-il?)
        session.saveOrUpdate(new ValueSelectionQuestion(a67, 0, QuestionCode.A72, CodeList.TYPEVEHICULE));

        // == A73 =========================================================================
        // Consommation moyenne (L/100km)
        // A50(Mobilité) > A52(Transport routier (méthode au choix)) > A66(Calcul par les kilomètres) > A67(Créez autant de catégories de véhicules que souhaité) > A73 (Consommation moyenne (L/100km))
        session.saveOrUpdate(new IntegerQuestion(a67, 0, QuestionCode.A73, null));

        // == A74 =========================================================================
        // Consommation moyenne (L/100km)
        // A50(Mobilité) > A52(Transport routier (méthode au choix)) > A66(Calcul par les kilomètres) > A67(Créez autant de catégories de véhicules que souhaité) > A74 (Consommation moyenne (L/100km))
        session.saveOrUpdate(new IntegerQuestion(a67, 0, QuestionCode.A74, null));

        // == A75 =========================================================================
        // Consommation moyenne (L/100km)
        // A50(Mobilité) > A52(Transport routier (méthode au choix)) > A66(Calcul par les kilomètres) > A67(Créez autant de catégories de véhicules que souhaité) > A75 (Consommation moyenne (L/100km))
        session.saveOrUpdate(new IntegerQuestion(a67, 0, QuestionCode.A75, null));

        // == A76 =========================================================================
        // Quelle est le nombre de kilomètres parcourus par an?
        // A50(Mobilité) > A52(Transport routier (méthode au choix)) > A66(Calcul par les kilomètres) > A67(Créez autant de catégories de véhicules que souhaité) > A76 (Quelle est le nombre de kilomètres parcourus par an?)
        session.saveOrUpdate(new IntegerQuestion(a67, 0, QuestionCode.A76, null));

        // == A79 =========================================================================
        // Catégorie de véhicule
        // A13(Consommation de combustibles) > A79 (Catégorie de véhicule)
        session.saveOrUpdate(new StringQuestion(a13, 0, QuestionCode.A79));

        // == A80 =========================================================================
        // S'agit-il d'une catégorie de véhicules appartenant ou sous le contrôle à la société ou pas?
        // A13(Consommation de combustibles) > A80 (S'agit-il d'une catégorie de véhicules appartenant ou sous le contrôle à la société ou pas?)
        session.saveOrUpdate(new BooleanQuestion(a13, 0, QuestionCode.A80));

        // == A81 =========================================================================
        // Motif de déplacement
        // A13(Consommation de combustibles) > A81 (Motif de déplacement)
        session.saveOrUpdate(new ValueSelectionQuestion(a13, 0, QuestionCode.A81, CodeList.MOTIFDEPLACEMENT));

        // == A83 =========================================================================
        // Quel type de carburant utilise-t-il ?
        // A13(Consommation de combustibles) > A83 (Quel type de carburant utilise-t-il ?)
        session.saveOrUpdate(new ValueSelectionQuestion(a13, 0, QuestionCode.A83, CodeList.CARBURANT));

        // == A88 =========================================================================
        // Quel est le montant annuel de dépenses en carburant?
        // A13(Consommation de combustibles) > A88 (Quel est le montant annuel de dépenses en carburant?)
        session.saveOrUpdate(new DoubleQuestion(a13, 0, QuestionCode.A88, moneyUnits));

        // == A89 =========================================================================
        // Prix moyen du litre d'essence
        // A13(Consommation de combustibles) > A89 (Prix moyen du litre d'essence)
        session.saveOrUpdate(new DoubleQuestion(a13, 0, QuestionCode.A89, moneyUnits));

        // == A90 =========================================================================
        // Prix moyen du litre de diesel
        // A13(Consommation de combustibles) > A90 (Prix moyen du litre de diesel)
        session.saveOrUpdate(new DoubleQuestion(a13, 0, QuestionCode.A90, moneyUnits));

        // == A91 =========================================================================
        // Prix moyen du litre de biodiesel
        // A13(Consommation de combustibles) > A91 (Prix moyen du litre de biodiesel)
        session.saveOrUpdate(new DoubleQuestion(a13, 0, QuestionCode.A91, moneyUnits));

        // == A92 =========================================================================
        // Prix moyen du litre de Gaz de Prétrole Liquéfié (GPL)
        // A13(Consommation de combustibles) > A92 (Prix moyen du litre de Gaz de Prétrole Liquéfié (GPL))
        session.saveOrUpdate(new DoubleQuestion(a13, 0, QuestionCode.A92, moneyUnits));

        // == A95 =========================================================================
        // Bus TEC pour déplacement domicile-travail des employés (en km.passagers)
        // A50(Mobilité) > A93(Transport en commun) > A94(Estimation par le détail des déplacements) > A95 (Bus TEC pour déplacement domicile-travail des employés (en km.passagers))
        session.saveOrUpdate(new IntegerQuestion(a94, 0, QuestionCode.A95, null));

        // == A96 =========================================================================
        // Bus TEC pour déplacements professionnels & des visiteurs (en km.passagers)
        // A50(Mobilité) > A93(Transport en commun) > A94(Estimation par le détail des déplacements) > A96 (Bus TEC pour déplacements professionnels & des visiteurs (en km.passagers))
        session.saveOrUpdate(new IntegerQuestion(a94, 0, QuestionCode.A96, null));

        // == A97 =========================================================================
        // Métro pour déplacement domicile-travail des employés (en km.passagers)
        // A50(Mobilité) > A93(Transport en commun) > A94(Estimation par le détail des déplacements) > A97 (Métro pour déplacement domicile-travail des employés (en km.passagers))
        session.saveOrUpdate(new IntegerQuestion(a94, 0, QuestionCode.A97, null));

        // == A98 =========================================================================
        // Métro pour déplacements professionnels & des visiteurs (en km.passagers)
        // A50(Mobilité) > A93(Transport en commun) > A94(Estimation par le détail des déplacements) > A98 (Métro pour déplacements professionnels & des visiteurs (en km.passagers))
        session.saveOrUpdate(new IntegerQuestion(a94, 0, QuestionCode.A98, null));

        // == A99 =========================================================================
        // Train national SNCB pour déplacement domicile-travail des employés (en km.passagers)
        // A50(Mobilité) > A93(Transport en commun) > A94(Estimation par le détail des déplacements) > A99 (Train national SNCB pour déplacement domicile-travail des employés (en km.passagers))
        session.saveOrUpdate(new IntegerQuestion(a94, 0, QuestionCode.A99, null));

        // == A100 ========================================================================
        // Train national SNCB pour déplacements professionnels & des visiteurs (en km.passagers)
        // A50(Mobilité) > A93(Transport en commun) > A94(Estimation par le détail des déplacements) > A100 (Train national SNCB pour déplacements professionnels & des visiteurs (en km.passagers))
        session.saveOrUpdate(new IntegerQuestion(a94, 0, QuestionCode.A100, null));

        // == A101 ========================================================================
        // Train international (TGV) pour déplacement domicile-travail des employés (en km.passagers)
        // A50(Mobilité) > A93(Transport en commun) > A94(Estimation par le détail des déplacements) > A101 (Train international (TGV) pour déplacement domicile-travail des employés (en km.passagers))
        session.saveOrUpdate(new IntegerQuestion(a94, 0, QuestionCode.A101, null));

        // == A102 ========================================================================
        // Train international (TGV) pour déplacements professionnels & des visiteurs (en km.passagers)
        // A50(Mobilité) > A93(Transport en commun) > A94(Estimation par le détail des déplacements) > A102 (Train international (TGV) pour déplacements professionnels & des visiteurs (en km.passagers))
        session.saveOrUpdate(new IntegerQuestion(a94, 0, QuestionCode.A102, null));

        // == A103 ========================================================================
        // Tram pour déplacement domicile-travail des employés (en km.passagers)
        // A50(Mobilité) > A93(Transport en commun) > A94(Estimation par le détail des déplacements) > A103 (Tram pour déplacement domicile-travail des employés (en km.passagers))
        session.saveOrUpdate(new IntegerQuestion(a94, 0, QuestionCode.A103, null));

        // == A104 ========================================================================
        // Tram pour déplacements professionnels & des visiteurs (en km.passagers)
        // A50(Mobilité) > A93(Transport en commun) > A94(Estimation par le détail des déplacements) > A104 (Tram pour déplacements professionnels & des visiteurs (en km.passagers))
        session.saveOrUpdate(new IntegerQuestion(a94, 0, QuestionCode.A104, null));

        // == A105 ========================================================================
        // Taxi pour déplacement domicile-travail des employés (en véhicules.km)
        // A50(Mobilité) > A93(Transport en commun) > A94(Estimation par le détail des déplacements) > A105 (Taxi pour déplacement domicile-travail des employés (en véhicules.km))
        session.saveOrUpdate(new IntegerQuestion(a94, 0, QuestionCode.A105, null));

        // == A106 ========================================================================
        // Taxi pour déplacements professionnels & des visiteurs (en véhicules.km)
        // A50(Mobilité) > A93(Transport en commun) > A94(Estimation par le détail des déplacements) > A106 (Taxi pour déplacements professionnels & des visiteurs (en véhicules.km))
        session.saveOrUpdate(new IntegerQuestion(a94, 0, QuestionCode.A106, null));

        // == A107 ========================================================================
        // Taxi pour déplacement domicile-travail des employés (en valeur)
        // A50(Mobilité) > A93(Transport en commun) > A94(Estimation par le détail des déplacements) > A107 (Taxi pour déplacement domicile-travail des employés (en valeur))
        session.saveOrUpdate(new DoubleQuestion(a94, 0, QuestionCode.A107, moneyUnits));

        // == A108 ========================================================================
        // Taxi pour déplacements professionnels & des visiteurs (en valeur)
        // A50(Mobilité) > A93(Transport en commun) > A94(Estimation par le détail des déplacements) > A108 (Taxi pour déplacements professionnels & des visiteurs (en valeur))
        session.saveOrUpdate(new DoubleQuestion(a94, 0, QuestionCode.A108, moneyUnits));

        // == A110 ========================================================================
        // Etes-vous situés à proximité d'une gare (< 1 km)?
        // A50(Mobilité) > A93(Transport en commun) > A109(Estimation par nombre d'employés) > A110 (Etes-vous situés à proximité d'une gare (< 1 km)?)
        session.saveOrUpdate(new BooleanQuestion(a109, 0, QuestionCode.A110));

        // == A111 ========================================================================
        // Etes-vous situés à proximité d'un arrêt de transport en commun (< 500 m)?
        // A50(Mobilité) > A93(Transport en commun) > A109(Estimation par nombre d'employés) > A111 (Etes-vous situés à proximité d'un arrêt de transport en commun (< 500 m)?)
        session.saveOrUpdate(new BooleanQuestion(a109, 0, QuestionCode.A111));

        // == A112 ========================================================================
        // Etes-vous situés en Agglomération ?
        // A50(Mobilité) > A93(Transport en commun) > A109(Estimation par nombre d'employés) > A112 (Etes-vous situés en Agglomération ?)
        session.saveOrUpdate(new BooleanQuestion(a109, 0, QuestionCode.A112));

        // == A116 ========================================================================
        // Catégorie de vol
        // A50(Mobilité) > A113(Transport en avion (déplacements professionnels ou des visiteurs)) > A114(Méthode par le détail des vols) > A115(Créez autant de catégories de vol que nécessaire) > A116 (Catégorie de vol)
        session.saveOrUpdate(new StringQuestion(a115, 0, QuestionCode.A116));

        // == A117 ========================================================================
        // Type de vol
        // A50(Mobilité) > A113(Transport en avion (déplacements professionnels ou des visiteurs)) > A114(Méthode par le détail des vols) > A115(Créez autant de catégories de vol que nécessaire) > A117 (Type de vol)
        session.saveOrUpdate(new ValueSelectionQuestion(a115, 0, QuestionCode.A117, CodeList.TYPEVOL));

        // == A118 ========================================================================
        // Classe du vol
        // A50(Mobilité) > A113(Transport en avion (déplacements professionnels ou des visiteurs)) > A114(Méthode par le détail des vols) > A115(Créez autant de catégories de vol que nécessaire) > A118 (Classe du vol)
        session.saveOrUpdate(new ValueSelectionQuestion(a115, 0, QuestionCode.A118, CodeList.CATEGORIEVOL));

        // == A119 ========================================================================
        // Nombre de vols/an
        // A50(Mobilité) > A113(Transport en avion (déplacements professionnels ou des visiteurs)) > A114(Méthode par le détail des vols) > A115(Créez autant de catégories de vol que nécessaire) > A119 (Nombre de vols/an)
        session.saveOrUpdate(new IntegerQuestion(a115, 0, QuestionCode.A119, null));

        // == A120 ========================================================================
        // Distance moyenne A/R (km)
        // A50(Mobilité) > A113(Transport en avion (déplacements professionnels ou des visiteurs)) > A114(Méthode par le détail des vols) > A115(Créez autant de catégories de vol que nécessaire) > A120 (Distance moyenne A/R (km))
        session.saveOrUpdate(new DoubleQuestion(a115, 0, QuestionCode.A120, lengthUnits));

        // == A122 ========================================================================
        // % des employés qui réalisent des déplacements en avion
        // A50(Mobilité) > A113(Transport en avion (déplacements professionnels ou des visiteurs)) > A121(Méthode des moyennes) > A122 (% des employés qui réalisent des déplacements en avion)
        session.saveOrUpdate(new DoubleQuestion(a121, 0, QuestionCode.A122, null));

        // == A123 ========================================================================
        // Connaissez-vous le nombre de km parcourus en avion?
        // A50(Mobilité) > A113(Transport en avion (déplacements professionnels ou des visiteurs)) > A121(Méthode des moyennes) > A123 (Connaissez-vous le nombre de km parcourus en avion?)
        session.saveOrUpdate(new BooleanQuestion(a121, 0, QuestionCode.A123));

        // == A124 ========================================================================
        // Les voyages ont-ils lieu en Europe?
        // A50(Mobilité) > A113(Transport en avion (déplacements professionnels ou des visiteurs)) > A121(Méthode des moyennes) > A124 (Les voyages ont-ils lieu en Europe?)
        session.saveOrUpdate(new BooleanQuestion(a121, 0, QuestionCode.A124));

        // == A125 ========================================================================
        // Km moyen assignés par employé voyageant
        // A50(Mobilité) > A113(Transport en avion (déplacements professionnels ou des visiteurs)) > A121(Méthode des moyennes) > A125 (Km moyen assignés par employé voyageant)
        session.saveOrUpdate(new DoubleQuestion(a121, 0, QuestionCode.A125, lengthUnits));

        // == A126 ========================================================================
        // Km moyen assignés par employé voyageant
        // A50(Mobilité) > A113(Transport en avion (déplacements professionnels ou des visiteurs)) > A121(Méthode des moyennes) > A126 (Km moyen assignés par employé voyageant)
        session.saveOrUpdate(new DoubleQuestion(a121, 0, QuestionCode.A126, lengthUnits));

        // == A127 ========================================================================
        // km moyen parcourus sur l'année:
        // A50(Mobilité) > A113(Transport en avion (déplacements professionnels ou des visiteurs)) > A121(Méthode des moyennes) > A127 (km moyen parcourus sur l'année:)
        session.saveOrUpdate(new DoubleQuestion(a121, 0, QuestionCode.A127, lengthUnits));

        // == A129 ========================================================================
        // Pièces documentaires liées au transport et stockage amont
        // A128(Transport et distribution de marchandises amont) > A129 (Pièces documentaires liées au transport et stockage amont)
        session.saveOrUpdate(new StringQuestion(a128, 0, QuestionCode.A129));

        // == A133 ========================================================================
        // Consommation d'essence
        // A128(Transport et distribution de marchandises amont) > A130(Transport amont) > A131(Transport avec des véhicules détenus par l'entreprise) > A132(Méthode par consommation de carburants) > A133 (Consommation d'essence)
        session.saveOrUpdate(new DoubleQuestion(a132, 0, QuestionCode.A133, volumeUnits));

        // == A134 ========================================================================
        // Consommation de diesel
        // A128(Transport et distribution de marchandises amont) > A130(Transport amont) > A131(Transport avec des véhicules détenus par l'entreprise) > A132(Méthode par consommation de carburants) > A134 (Consommation de diesel)
        session.saveOrUpdate(new DoubleQuestion(a132, 0, QuestionCode.A134, volumeUnits));

        // == A135 ========================================================================
        // Consommation de gaz de pétrole liquéfié (GPL)
        // A128(Transport et distribution de marchandises amont) > A130(Transport amont) > A131(Transport avec des véhicules détenus par l'entreprise) > A132(Méthode par consommation de carburants) > A135 (Consommation de gaz de pétrole liquéfié (GPL))
        session.saveOrUpdate(new DoubleQuestion(a132, 0, QuestionCode.A135, volumeUnits));

        // == A136 ========================================================================
        // Est-ce les marchandises sont refrigérées durant le transport?
        // A128(Transport et distribution de marchandises amont) > A130(Transport amont) > A131(Transport avec des véhicules détenus par l'entreprise) > A132(Méthode par consommation de carburants) > A136 (Est-ce les marchandises sont refrigérées durant le transport?)
        session.saveOrUpdate(new BooleanQuestion(a132, 0, QuestionCode.A136));

        // == A137 ========================================================================
        // Type de Gaz
        // A128(Transport et distribution de marchandises amont) > A130(Transport amont) > A131(Transport avec des véhicules détenus par l'entreprise) > A132(Méthode par consommation de carburants) > A137 (Type de Gaz)
        session.saveOrUpdate(new ValueSelectionQuestion(a132, 0, QuestionCode.A137, CodeList.FRIGORIGENEBASE));

        // == A138 ========================================================================
        // Connaissez-vous la quantité annuelle de recharge de ce gaz?
        // A128(Transport et distribution de marchandises amont) > A130(Transport amont) > A131(Transport avec des véhicules détenus par l'entreprise) > A132(Méthode par consommation de carburants) > A138 (Connaissez-vous la quantité annuelle de recharge de ce gaz?)
        session.saveOrUpdate(new BooleanQuestion(a132, 0, QuestionCode.A138));

        // == A139 ========================================================================
        // Quantité de recharge annuelle
        // A128(Transport et distribution de marchandises amont) > A130(Transport amont) > A131(Transport avec des véhicules détenus par l'entreprise) > A132(Méthode par consommation de carburants) > A139 (Quantité de recharge annuelle)
        session.saveOrUpdate(new DoubleQuestion(a132, 0, QuestionCode.A139, massUnits));

        // == A500 ========================================================================
        // Quantité de recharge annuelle
        // A128(Transport et distribution de marchandises amont) > A130(Transport amont) > A131(Transport avec des véhicules détenus par l'entreprise) > A132(Méthode par consommation de carburants) > A500 (Quantité de recharge annuelle)
        session.saveOrUpdate(new DoubleQuestion(a132, 0, QuestionCode.A500, massUnits));

        // == A143 ========================================================================
        // Marchandise
        // A128(Transport et distribution de marchandises amont) > A130(Transport amont) > A140(Transport effectué par des transporteurs) > A141(Méthode des kilomètres) > A142(Créez autant de marchandises que nécessaire) > A143 (Marchandise)
        session.saveOrUpdate(new StringQuestion(a142, 0, QuestionCode.A143));

        // == A145 ========================================================================
        // Poids total transporté:
        // A128(Transport et distribution de marchandises amont) > A130(Transport amont) > A140(Transport effectué par des transporteurs) > A141(Méthode des kilomètres) > A142(Créez autant de marchandises que nécessaire) > A145 (Poids total transporté:)
        session.saveOrUpdate(new DoubleQuestion(a142, 0, QuestionCode.A145, massUnits));

        // == A146 ========================================================================
        // Distance totale entre le point de départ et le point d'arrivée de la marchandise:
        // A128(Transport et distribution de marchandises amont) > A130(Transport amont) > A140(Transport effectué par des transporteurs) > A141(Méthode des kilomètres) > A142(Créez autant de marchandises que nécessaire) > A146 (Distance totale entre le point de départ et le point d'arrivée de la marchandise:)
        session.saveOrUpdate(new DoubleQuestion(a142, 0, QuestionCode.A146, lengthUnits));

        // == A147 ========================================================================
        // % de distance effectuée par transport routier local par camion
        // A128(Transport et distribution de marchandises amont) > A130(Transport amont) > A140(Transport effectué par des transporteurs) > A141(Méthode des kilomètres) > A142(Créez autant de marchandises que nécessaire) > A147 (% de distance effectuée par transport routier local par camion)
        session.saveOrUpdate(new DoubleQuestion(a142, 0, QuestionCode.A147, null));

        // == A148 ========================================================================
        // % de distance effectuée par transport routier local par camionnette
        // A128(Transport et distribution de marchandises amont) > A130(Transport amont) > A140(Transport effectué par des transporteurs) > A141(Méthode des kilomètres) > A142(Créez autant de marchandises que nécessaire) > A148 (% de distance effectuée par transport routier local par camionnette)
        session.saveOrUpdate(new DoubleQuestion(a142, 0, QuestionCode.A148, null));

        // == A149 ========================================================================
        // % de distance effectuée par transport routier international
        // A128(Transport et distribution de marchandises amont) > A130(Transport amont) > A140(Transport effectué par des transporteurs) > A141(Méthode des kilomètres) > A142(Créez autant de marchandises que nécessaire) > A149 (% de distance effectuée par transport routier international)
        session.saveOrUpdate(new DoubleQuestion(a142, 0, QuestionCode.A149, null));

        // == A150 ========================================================================
        // % de distance effectuée par voie ferroviaire
        // A128(Transport et distribution de marchandises amont) > A130(Transport amont) > A140(Transport effectué par des transporteurs) > A141(Méthode des kilomètres) > A142(Créez autant de marchandises que nécessaire) > A150 (% de distance effectuée par voie ferroviaire)
        session.saveOrUpdate(new DoubleQuestion(a142, 0, QuestionCode.A150, null));

        // == A151 ========================================================================
        // % de distance effectuée par voie maritime
        // A128(Transport et distribution de marchandises amont) > A130(Transport amont) > A140(Transport effectué par des transporteurs) > A141(Méthode des kilomètres) > A142(Créez autant de marchandises que nécessaire) > A151 (% de distance effectuée par voie maritime)
        session.saveOrUpdate(new DoubleQuestion(a142, 0, QuestionCode.A151, null));

        // == A152 ========================================================================
        // % de distance effectuée par voie fluviale
        // A128(Transport et distribution de marchandises amont) > A130(Transport amont) > A140(Transport effectué par des transporteurs) > A141(Méthode des kilomètres) > A142(Créez autant de marchandises que nécessaire) > A152 (% de distance effectuée par voie fluviale)
        session.saveOrUpdate(new DoubleQuestion(a142, 0, QuestionCode.A152, null));

        // == A153 ========================================================================
        // % de distance effectuée par transport aérien court courrier (<1000 km)
        // A128(Transport et distribution de marchandises amont) > A130(Transport amont) > A140(Transport effectué par des transporteurs) > A141(Méthode des kilomètres) > A142(Créez autant de marchandises que nécessaire) > A153 (% de distance effectuée par transport aérien court courrier (<1000 km))
        session.saveOrUpdate(new DoubleQuestion(a142, 0, QuestionCode.A153, null));

        // == A154 ========================================================================
        // % de distance effectuée par transport aérien moyen courrier (1000 à 4000 km)
        // A128(Transport et distribution de marchandises amont) > A130(Transport amont) > A140(Transport effectué par des transporteurs) > A141(Méthode des kilomètres) > A142(Créez autant de marchandises que nécessaire) > A154 (% de distance effectuée par transport aérien moyen courrier (1000 à 4000 km))
        session.saveOrUpdate(new DoubleQuestion(a142, 0, QuestionCode.A154, null));

        // == A155 ========================================================================
        // % de distance effectuée par transport aérien long courrier (> 4000 km)
        // A128(Transport et distribution de marchandises amont) > A130(Transport amont) > A140(Transport effectué par des transporteurs) > A141(Méthode des kilomètres) > A142(Créez autant de marchandises que nécessaire) > A155 (% de distance effectuée par transport aérien long courrier (> 4000 km))
        session.saveOrUpdate(new DoubleQuestion(a142, 0, QuestionCode.A155, null));

        // == A156 ========================================================================
        // Total (supposé être égal à 100%)
        // A128(Transport et distribution de marchandises amont) > A130(Transport amont) > A140(Transport effectué par des transporteurs) > A141(Méthode des kilomètres) > A142(Créez autant de marchandises que nécessaire) > A156 (Total (supposé être égal à 100%))
        session.saveOrUpdate(new DoubleQuestion(a142, 0, QuestionCode.A156, null));

        // == A158 ========================================================================
        // Quel est le poids total des marchandises?
        // A128(Transport et distribution de marchandises amont) > A130(Transport amont) > A140(Transport effectué par des transporteurs) > A157(Méthode des moyennes) > A158 (Quel est le poids total des marchandises?)
        session.saveOrUpdate(new DoubleQuestion(a157, 0, QuestionCode.A158, massUnits));

        // == A159 ========================================================================
        // Quelle est la provenance ou destination des marchandises?
        // A128(Transport et distribution de marchandises amont) > A130(Transport amont) > A140(Transport effectué par des transporteurs) > A157(Méthode des moyennes) > A159 (Quelle est la provenance ou destination des marchandises?)
        session.saveOrUpdate(new ValueSelectionQuestion(a157, 0, QuestionCode.A159, CodeList.PROVENANCESIMPLIFIEE));

        // == A160 ========================================================================
        // Km assignés en moyenne aux marchandises
        // A128(Transport et distribution de marchandises amont) > A130(Transport amont) > A140(Transport effectué par des transporteurs) > A157(Méthode des moyennes) > A160 (Km assignés en moyenne aux marchandises)
        session.saveOrUpdate(new DoubleQuestion(a157, 0, QuestionCode.A160, lengthUnits));

        // == A161 ========================================================================
        // Km assignés en moyenne aux marchandises
        // A128(Transport et distribution de marchandises amont) > A130(Transport amont) > A140(Transport effectué par des transporteurs) > A157(Méthode des moyennes) > A161 (Km assignés en moyenne aux marchandises)
        session.saveOrUpdate(new DoubleQuestion(a157, 0, QuestionCode.A161, lengthUnits));

        // == A162 ========================================================================
        // Km assignés en moyenne aux marchandises
        // A128(Transport et distribution de marchandises amont) > A130(Transport amont) > A140(Transport effectué par des transporteurs) > A157(Méthode des moyennes) > A162 (Km assignés en moyenne aux marchandises)
        session.saveOrUpdate(new DoubleQuestion(a157, 0, QuestionCode.A162, lengthUnits));

        // == A165 ========================================================================
        // Entrepôt
        // A128(Transport et distribution de marchandises amont) > A163(Distribution amont: Energie et froid des entrepôts de stockage) > A164(Créez autant d'entrepôts de stockage que nécessaire) > A165 (Entrepôt)
        session.saveOrUpdate(new StringQuestion(a164, 0, QuestionCode.A165));

        // == A167 ========================================================================
        // Combustible utilisé en amont
        // A128(Transport et distribution de marchandises amont) > A163(Distribution amont: Energie et froid des entrepôts de stockage) > A164(Créez autant d'entrepôts de stockage que nécessaire) > A166(Listez les totaux de combustibles utilisés en amont) > A167 (Combustible utilisé en amont)
        session.saveOrUpdate(new ValueSelectionQuestion(a166, 0, QuestionCode.A167, CodeList.FUEL));

        // == A168 ========================================================================
        // Quantité
        // A128(Transport et distribution de marchandises amont) > A163(Distribution amont: Energie et froid des entrepôts de stockage) > A164(Créez autant d'entrepôts de stockage que nécessaire) > A166(Listez les totaux de combustibles utilisés en amont) > A168 (Quantité)
        session.saveOrUpdate(new DoubleQuestion(a166, 0, QuestionCode.A168, energyUnits));

        // == A169 ========================================================================
        // Electricité
        // A128(Transport et distribution de marchandises amont) > A163(Distribution amont: Energie et froid des entrepôts de stockage) > A164(Créez autant d'entrepôts de stockage que nécessaire) > A169 (Electricité)
        session.saveOrUpdate(new DoubleQuestion(a164, 0, QuestionCode.A169, energyUnits));

        // == A171 ========================================================================
        // Type de gaz
        // A128(Transport et distribution de marchandises amont) > A163(Distribution amont: Energie et froid des entrepôts de stockage) > A164(Créez autant d'entrepôts de stockage que nécessaire) > A170(Listez les gaz réfrigérants utilisés pour les marchandises amont) > A171 (Type de gaz)
        session.saveOrUpdate(new ValueSelectionQuestion(a170, 0, QuestionCode.A171, CodeList.REFRIGERANT_GAS));

        // == A172 ========================================================================
        // Quantité de recharge nécessaire pour l'année
        // A128(Transport et distribution de marchandises amont) > A163(Distribution amont: Energie et froid des entrepôts de stockage) > A164(Créez autant d'entrepôts de stockage que nécessaire) > A170(Listez les gaz réfrigérants utilisés pour les marchandises amont) > A172 (Quantité de recharge nécessaire pour l'année)
        session.saveOrUpdate(new DoubleQuestion(a170, 0, QuestionCode.A172, massUnits));

        // == A174 ========================================================================
        // Pièces documentaires liées aux déchets
        // A173(Déchets générés par les opérations) > A174 (Pièces documentaires liées aux déchets)
        session.saveOrUpdate(new StringQuestion(a173, 0, QuestionCode.A174));

        // == A176 ========================================================================
        // Poste de déchet
        // A173(Déchets générés par les opérations) > A175(Listez vos différents postes de déchets) > A176 (Poste de déchet)
        session.saveOrUpdate(new StringQuestion(a175, 0, QuestionCode.A176));

        // == A177 ========================================================================
        // Type de déchet
        // A173(Déchets générés par les opérations) > A175(Listez vos différents postes de déchets) > A177 (Type de déchet)
        session.saveOrUpdate(new ValueSelectionQuestion(a175, 0, QuestionCode.A177, CodeList.TYPEDECHET));

        // == A178 ========================================================================
        // Type de traitement
        // A173(Déchets générés par les opérations) > A175(Listez vos différents postes de déchets) > A178 (Type de traitement)
        session.saveOrUpdate(new ValueSelectionQuestion(a175, 0, QuestionCode.A178, CodeList.TRAITEMENTDECHET));

        // == A179 ========================================================================
        // Quantité
        // A173(Déchets générés par les opérations) > A175(Listez vos différents postes de déchets) > A179 (Quantité)
        session.saveOrUpdate(new DoubleQuestion(a175, 0, QuestionCode.A179, massUnits));

        // == A183 ========================================================================
        // Nombre d'ouvriers
        // A173(Déchets générés par les opérations) > A180(Eaux usées) > A181(Eaux usées domestiques par grand type de bâtiments) > A182(Usine ou atelier) > A183 (Nombre d'ouvriers)
        session.saveOrUpdate(new IntegerQuestion(a182, 0, QuestionCode.A183, null));

        // == A184 ========================================================================
        // Nombre de jours de travail/an
        // A173(Déchets générés par les opérations) > A180(Eaux usées) > A181(Eaux usées domestiques par grand type de bâtiments) > A182(Usine ou atelier) > A184 (Nombre de jours de travail/an)
        session.saveOrUpdate(new IntegerQuestion(a182, 0, QuestionCode.A184, null));

        // == A186 ========================================================================
        // Nombre d'employés
        // A173(Déchets générés par les opérations) > A180(Eaux usées) > A181(Eaux usées domestiques par grand type de bâtiments) > A185(Bureau) > A186 (Nombre d'employés)
        session.saveOrUpdate(new IntegerQuestion(a185, 0, QuestionCode.A186, null));

        // == A187 ========================================================================
        // Nombre de jours de travail/an
        // A173(Déchets générés par les opérations) > A180(Eaux usées) > A181(Eaux usées domestiques par grand type de bâtiments) > A185(Bureau) > A187 (Nombre de jours de travail/an)
        session.saveOrUpdate(new IntegerQuestion(a185, 0, QuestionCode.A187, null));

        // == A189 ========================================================================
        // Nombre de lits
        // A173(Déchets générés par les opérations) > A180(Eaux usées) > A181(Eaux usées domestiques par grand type de bâtiments) > A188(Hôtel, pension, hôpitaux, prison) > A189 (Nombre de lits)
        session.saveOrUpdate(new IntegerQuestion(a188, 0, QuestionCode.A189, null));

        // == A190 ========================================================================
        // Nombre de jours d'ouverture/an
        // A173(Déchets générés par les opérations) > A180(Eaux usées) > A181(Eaux usées domestiques par grand type de bâtiments) > A188(Hôtel, pension, hôpitaux, prison) > A190 (Nombre de jours d'ouverture/an)
        session.saveOrUpdate(new IntegerQuestion(a188, 0, QuestionCode.A190, null));

        // == A192 ========================================================================
        // Nombre de couverts/jour
        // A173(Déchets générés par les opérations) > A180(Eaux usées) > A181(Eaux usées domestiques par grand type de bâtiments) > A191(Restaurant ou cantine) > A192 (Nombre de couverts/jour)
        session.saveOrUpdate(new IntegerQuestion(a191, 0, QuestionCode.A192, null));

        // == A193 ========================================================================
        // Nombre de jours d'ouverture/an
        // A173(Déchets générés par les opérations) > A180(Eaux usées) > A181(Eaux usées domestiques par grand type de bâtiments) > A191(Restaurant ou cantine) > A193 (Nombre de jours d'ouverture/an)
        session.saveOrUpdate(new IntegerQuestion(a191, 0, QuestionCode.A193, null));

        // == A195 ========================================================================
        // Est-ce l'entreprise qui réalise le traitement ou est-il effectué par des tiers?
        // A173(Déchets générés par les opérations) > A180(Eaux usées) > A194(Eaux usées industrielles) > A195 (Est-ce l'entreprise qui réalise le traitement ou est-il effectué par des tiers?)
        session.saveOrUpdate(new ValueSelectionQuestion(a194, 0, QuestionCode.A195, CodeList.TRAITEUREAU));

        // == A198 ========================================================================
        // Source de rejetnull
        // A173(Déchets générés par les opérations) > A180(Eaux usées) > A194(Eaux usées industrielles) > A196(Méthodes alternatives) > A197(Méthode par la quantité de m³ rejetés) > A198 (Source de rejetnull)
        session.saveOrUpdate(new ValueSelectionQuestion(a197, 0, QuestionCode.A198, CodeList.ORIGINEEAUUSEE));

        // == A199 ========================================================================
        // Quantités de m³ rejetés
        // A173(Déchets générés par les opérations) > A180(Eaux usées) > A194(Eaux usées industrielles) > A196(Méthodes alternatives) > A197(Méthode par la quantité de m³ rejetés) > A199 (Quantités de m³ rejetés)
        session.saveOrUpdate(new DoubleQuestion(a197, 0, QuestionCode.A199, volumeUnits));

        // == A200 ========================================================================
        // Méthode de traitement des eaux usées
        // A173(Déchets générés par les opérations) > A180(Eaux usées) > A194(Eaux usées industrielles) > A196(Méthodes alternatives) > A197(Méthode par la quantité de m³ rejetés) > A200 (Méthode de traitement des eaux usées)
        session.saveOrUpdate(new ValueSelectionQuestion(a197, 0, QuestionCode.A200, CodeList.TRAITEMENTEAU));

        // == A501 ========================================================================
        // Est-ce l'entreprise qui réalise le traitement ou est-il effectué par des tiers?
        // A173(Déchets générés par les opérations) > A180(Eaux usées) > A194(Eaux usées industrielles) > A501 (Est-ce l'entreprise qui réalise le traitement ou est-il effectué par des tiers?)
        session.saveOrUpdate(new ValueSelectionQuestion(a194, 0, QuestionCode.A501, CodeList.TRAITEUREAU));

        // == A202 ========================================================================
        // Quantités de DCO rejetés
        // A173(Déchets générés par les opérations) > A180(Eaux usées) > A194(Eaux usées industrielles) > A196(Méthodes alternatives) > A201(Méthode par le poids de CO2 chimique des effluents rejetés) > A202 (Quantités de DCO rejetés)
        session.saveOrUpdate(new DoubleQuestion(a201, 0, QuestionCode.A202, massUnits));

        // == A203 ========================================================================
        // Quantités d'azote rejetés
        // A173(Déchets générés par les opérations) > A180(Eaux usées) > A194(Eaux usées industrielles) > A196(Méthodes alternatives) > A201(Méthode par le poids de CO2 chimique des effluents rejetés) > A203 (Quantités d'azote rejetés)
        session.saveOrUpdate(new DoubleQuestion(a201, 0, QuestionCode.A203, massUnits));

        // == A204 ========================================================================
        // Méthode de traitement des eaux usées
        // A173(Déchets générés par les opérations) > A180(Eaux usées) > A194(Eaux usées industrielles) > A196(Méthodes alternatives) > A201(Méthode par le poids de CO2 chimique des effluents rejetés) > A204 (Méthode de traitement des eaux usées)
        session.saveOrUpdate(new ValueSelectionQuestion(a201, 0, QuestionCode.A204, CodeList.TRAITEMENTEAU));

        // == A206 ========================================================================
        // Pièces documentaires liées aux achats
        // A205(Achat de biens et services) > A206 (Pièces documentaires liées aux achats)
        session.saveOrUpdate(new StringQuestion(a205, 0, QuestionCode.A206));

        // == A210 ========================================================================
        // Poste d'achat
        // A208(Méthode par détail des achats) > A209(Créez et nommez vos postes d'achats (et préciser la catégorie et le type de matériaux ensuite)) > A210 (Poste d'achat)
        session.saveOrUpdate(new StringQuestion(a209, 0, QuestionCode.A210));

        // == A211 ========================================================================
        // Catégorie
        // A208(Méthode par détail des achats) > A209(Créez et nommez vos postes d'achats (et préciser la catégorie et le type de matériaux ensuite)) > A211 (Catégorie)
        session.saveOrUpdate(new ValueSelectionQuestion(a209, 0, QuestionCode.A211, CodeList.TYPEACHAT));

        // == A212 ========================================================================
        // Type
        // A208(Méthode par détail des achats) > A209(Créez et nommez vos postes d'achats (et préciser la catégorie et le type de matériaux ensuite)) > A212 (Type)
        session.saveOrUpdate(new ValueSelectionQuestion(a209, 0, QuestionCode.A212, CodeList.ACHATMETAL));

        // == A213 ========================================================================
        // Type
        // A208(Méthode par détail des achats) > A209(Créez et nommez vos postes d'achats (et préciser la catégorie et le type de matériaux ensuite)) > A213 (Type)
        session.saveOrUpdate(new ValueSelectionQuestion(a209, 0, QuestionCode.A213, CodeList.ACHATPLASTIQUE));

        // == A214 ========================================================================
        // Type
        // A208(Méthode par détail des achats) > A209(Créez et nommez vos postes d'achats (et préciser la catégorie et le type de matériaux ensuite)) > A214 (Type)
        session.saveOrUpdate(new ValueSelectionQuestion(a209, 0, QuestionCode.A214, CodeList.ACHATPAPIER));

        // == A215 ========================================================================
        // Type
        // A208(Méthode par détail des achats) > A209(Créez et nommez vos postes d'achats (et préciser la catégorie et le type de matériaux ensuite)) > A215 (Type)
        session.saveOrUpdate(new ValueSelectionQuestion(a209, 0, QuestionCode.A215, CodeList.ACHATVERRE));

        // == A216 ========================================================================
        // Type
        // A208(Méthode par détail des achats) > A209(Créez et nommez vos postes d'achats (et préciser la catégorie et le type de matériaux ensuite)) > A216 (Type)
        session.saveOrUpdate(new ValueSelectionQuestion(a209, 0, QuestionCode.A216, CodeList.ACHATCHIMIQUE));

        // == A217 ========================================================================
        // Type
        // A208(Méthode par détail des achats) > A209(Créez et nommez vos postes d'achats (et préciser la catégorie et le type de matériaux ensuite)) > A217 (Type)
        session.saveOrUpdate(new ValueSelectionQuestion(a209, 0, QuestionCode.A217, CodeList.ACHATROUTE));

        // == A218 ========================================================================
        // Type
        // A208(Méthode par détail des achats) > A209(Créez et nommez vos postes d'achats (et préciser la catégorie et le type de matériaux ensuite)) > A218 (Type)
        session.saveOrUpdate(new ValueSelectionQuestion(a209, 0, QuestionCode.A218, CodeList.ACHATAGRO));

        // == A219 ========================================================================
        // Type
        // A208(Méthode par détail des achats) > A209(Créez et nommez vos postes d'achats (et préciser la catégorie et le type de matériaux ensuite)) > A219 (Type)
        session.saveOrUpdate(new ValueSelectionQuestion(a209, 0, QuestionCode.A219, CodeList.ACHATSERVICE));

        // == A220 ========================================================================
        // Taux de recyclé
        // A208(Méthode par détail des achats) > A209(Créez et nommez vos postes d'achats (et préciser la catégorie et le type de matériaux ensuite)) > A220 (Taux de recyclé)
        session.saveOrUpdate(new DoubleQuestion(a209, 0, QuestionCode.A220, null));

        // == A221 ========================================================================
        // Quantité
        // A208(Méthode par détail des achats) > A209(Créez et nommez vos postes d'achats (et préciser la catégorie et le type de matériaux ensuite)) > A221 (Quantité)
        session.saveOrUpdate(new DoubleQuestion(a209, 0, QuestionCode.A221, massUnits));

        // == A222 ========================================================================
        // Quantité
        // A208(Méthode par détail des achats) > A209(Créez et nommez vos postes d'achats (et préciser la catégorie et le type de matériaux ensuite)) > A222 (Quantité)
        session.saveOrUpdate(new DoubleQuestion(a209, 0, QuestionCode.A222, moneyUnits));

        // == A225 ========================================================================
        // Poste d'achat
        // A208(Méthode par détail des achats) > A223(Autres matériaux spécifiques pour lesquels l'entreprise dispose du facteur d'émissions cradle-to-gate) > A224(Créez et nommez vos postes d'achats spécifiques (et précisez ensuite la catégorie, le type de matériaux et le facteur d'émission cradle-to-gate spécifique)) > A225 (Poste d'achat)
        session.saveOrUpdate(new StringQuestion(a224, 0, QuestionCode.A225));

        // == A226 ========================================================================
        // Quantité
        // A208(Méthode par détail des achats) > A223(Autres matériaux spécifiques pour lesquels l'entreprise dispose du facteur d'émissions cradle-to-gate) > A224(Créez et nommez vos postes d'achats spécifiques (et précisez ensuite la catégorie, le type de matériaux et le facteur d'émission cradle-to-gate spécifique)) > A226 (Quantité)
        session.saveOrUpdate(new IntegerQuestion(a224, 0, QuestionCode.A226, null));

        // == A227 ========================================================================
        // Unité dans laquelle s'exprime cette quantité
        // A208(Méthode par détail des achats) > A223(Autres matériaux spécifiques pour lesquels l'entreprise dispose du facteur d'émissions cradle-to-gate) > A224(Créez et nommez vos postes d'achats spécifiques (et précisez ensuite la catégorie, le type de matériaux et le facteur d'émission cradle-to-gate spécifique)) > A227 (Unité dans laquelle s'exprime cette quantité)
        session.saveOrUpdate(new IntegerQuestion(a224, 0, QuestionCode.A227, null));

        // == A228 ========================================================================
        // Facteur d'émission en tCO2e par unité ci-dessus
        // A208(Méthode par détail des achats) > A223(Autres matériaux spécifiques pour lesquels l'entreprise dispose du facteur d'émissions cradle-to-gate) > A224(Créez et nommez vos postes d'achats spécifiques (et précisez ensuite la catégorie, le type de matériaux et le facteur d'émission cradle-to-gate spécifique)) > A228 (Facteur d'émission en tCO2e par unité ci-dessus)
        session.saveOrUpdate(new IntegerQuestion(a224, 0, QuestionCode.A228, null));

        // == A230 ========================================================================
        // Pièces documentaires liées aux infrastructures
        // A229(Infrastructures (achetées durant l'année de déclaration)) > A230 (Pièces documentaires liées aux infrastructures)
        session.saveOrUpdate(new StringQuestion(a229, 0, QuestionCode.A230));

        // == A232 ========================================================================
        // Poste d'infrastructure
        // A229(Infrastructures (achetées durant l'année de déclaration)) > A231(Créez et nommez vos postes d'infrastructure) > A232 (Poste d'infrastructure)
        session.saveOrUpdate(new StringQuestion(a231, 0, QuestionCode.A232));

        // == A233 ========================================================================
        // Type d'infrastructure
        // A229(Infrastructures (achetées durant l'année de déclaration)) > A231(Créez et nommez vos postes d'infrastructure) > A233 (Type d'infrastructure)
        session.saveOrUpdate(new ValueSelectionQuestion(a231, 0, QuestionCode.A233, CodeList.INFRASTRUCTURE));

        // == A234 ========================================================================
        // Quantité
        // A229(Infrastructures (achetées durant l'année de déclaration)) > A231(Créez et nommez vos postes d'infrastructure) > A234 (Quantité)
        session.saveOrUpdate(new DoubleQuestion(a231, 0, QuestionCode.A234, surfaceUnits));

        // == A235 ========================================================================
        // Quantité
        // A229(Infrastructures (achetées durant l'année de déclaration)) > A231(Créez et nommez vos postes d'infrastructure) > A235 (Quantité)
        session.saveOrUpdate(new DoubleQuestion(a231, 0, QuestionCode.A235, massUnits));

        // == A236 ========================================================================
        // Quantité
        // A229(Infrastructures (achetées durant l'année de déclaration)) > A231(Créez et nommez vos postes d'infrastructure) > A236 (Quantité)
        session.saveOrUpdate(new IntegerQuestion(a231, 0, QuestionCode.A236, null));

        // == A239 ========================================================================
        // Poste d'infrastructure
        // A229(Infrastructures (achetées durant l'année de déclaration)) > A237(Autres infrastructures spécifiques pour lesquels l'entreprise dispose du facteur d'émission cradle-to-gate) > A238(Créez et nommez vos postes d'infrastructure spécifiques) > A239 (Poste d'infrastructure)
        session.saveOrUpdate(new StringQuestion(a238, 0, QuestionCode.A239));

        // == A240 ========================================================================
        // Quantité
        // A229(Infrastructures (achetées durant l'année de déclaration)) > A237(Autres infrastructures spécifiques pour lesquels l'entreprise dispose du facteur d'émission cradle-to-gate) > A238(Créez et nommez vos postes d'infrastructure spécifiques) > A240 (Quantité)
        session.saveOrUpdate(new IntegerQuestion(a238, 0, QuestionCode.A240, null));

        // == A241 ========================================================================
        // Unité dans laquelle s'exprime cette quantité
        // A229(Infrastructures (achetées durant l'année de déclaration)) > A237(Autres infrastructures spécifiques pour lesquels l'entreprise dispose du facteur d'émission cradle-to-gate) > A238(Créez et nommez vos postes d'infrastructure spécifiques) > A241 (Unité dans laquelle s'exprime cette quantité)
        session.saveOrUpdate(new IntegerQuestion(a238, 0, QuestionCode.A241, null));

        // == A242 ========================================================================
        // Facteur d'émission en tCO2e par unité ci-dessus
        // A229(Infrastructures (achetées durant l'année de déclaration)) > A237(Autres infrastructures spécifiques pour lesquels l'entreprise dispose du facteur d'émission cradle-to-gate) > A238(Créez et nommez vos postes d'infrastructure spécifiques) > A242 (Facteur d'émission en tCO2e par unité ci-dessus)
        session.saveOrUpdate(new IntegerQuestion(a238, 0, QuestionCode.A242, null));

        // == A245 ========================================================================
        // Nom du produit ou groupe de produits
        // A243(Transport & Distribution, Traitement, Utilisation et Fin de vie des produits vendus) > A244(Lister les différents produits ou groupes de produits vendus par l'entreprise) > A245 (Nom du produit ou groupe de produits)
        session.saveOrUpdate(new StringQuestion(a244, 0, QuestionCode.A245));

        // == A246 ========================================================================
        // Quantité vendue de ce produit
        // A243(Transport & Distribution, Traitement, Utilisation et Fin de vie des produits vendus) > A244(Lister les différents produits ou groupes de produits vendus par l'entreprise) > A246 (Quantité vendue de ce produit)
        session.saveOrUpdate(new IntegerQuestion(a244, 0, QuestionCode.A246, null));

        // == A247 ========================================================================
        // Unité dans laquelle s'exprime cette quantité
        // A243(Transport & Distribution, Traitement, Utilisation et Fin de vie des produits vendus) > A244(Lister les différents produits ou groupes de produits vendus par l'entreprise) > A247 (Unité dans laquelle s'exprime cette quantité)
        session.saveOrUpdate(new StringQuestion(a244, 0, QuestionCode.A247));

        // == A248 ========================================================================
        // S'agit-il d'un produit (ou groupe de produits) final ou intermédiaire?
        // A243(Transport & Distribution, Traitement, Utilisation et Fin de vie des produits vendus) > A244(Lister les différents produits ou groupes de produits vendus par l'entreprise) > A248 (S'agit-il d'un produit (ou groupe de produits) final ou intermédiaire?)
        session.saveOrUpdate(new ValueSelectionQuestion(a244, 0, QuestionCode.A248, CodeList.TYPEPRODUIT));

        // == A249 ========================================================================
        // Connaissez-vous la ou les applications ultérieures?
        // A243(Transport & Distribution, Traitement, Utilisation et Fin de vie des produits vendus) > A244(Lister les différents produits ou groupes de produits vendus par l'entreprise) > A249 (Connaissez-vous la ou les applications ultérieures?)
        session.saveOrUpdate(new BooleanQuestion(a244, 0, QuestionCode.A249));

        // == A251 ========================================================================
        // Pièces documentaires liées au transport et stockage aval
        // A243(Transport & Distribution, Traitement, Utilisation et Fin de vie des produits vendus) > A244(Lister les différents produits ou groupes de produits vendus par l'entreprise) > A250(Transport et distribution aval) > A251 (Pièces documentaires liées au transport et stockage aval)
        session.saveOrUpdate(new StringQuestion(a250, 0, QuestionCode.A251));

        // == A254 ========================================================================
        // Poids total transporté:
        // A243(Transport & Distribution, Traitement, Utilisation et Fin de vie des produits vendus) > A244(Lister les différents produits ou groupes de produits vendus par l'entreprise) > A250(Transport et distribution aval) > A252(Transport aval: choix de méthodes) > A253(Méthode par kilométrage) > A254 (Poids total transporté:)
        session.saveOrUpdate(new DoubleQuestion(a253, 0, QuestionCode.A254, massUnits));

        // == A255 ========================================================================
        // Distance totale entre le point de vente et le client particulier ou entre le point de vente et le client professionnel
        // A243(Transport & Distribution, Traitement, Utilisation et Fin de vie des produits vendus) > A244(Lister les différents produits ou groupes de produits vendus par l'entreprise) > A250(Transport et distribution aval) > A252(Transport aval: choix de méthodes) > A253(Méthode par kilométrage) > A255 (Distance totale entre le point de vente et le client particulier ou entre le point de vente et le client professionnel)
        session.saveOrUpdate(new DoubleQuestion(a253, 0, QuestionCode.A255, lengthUnits));

        // == A256 ========================================================================
        // % de distance effectuée par transport routier local par camion
        // A243(Transport & Distribution, Traitement, Utilisation et Fin de vie des produits vendus) > A244(Lister les différents produits ou groupes de produits vendus par l'entreprise) > A250(Transport et distribution aval) > A252(Transport aval: choix de méthodes) > A253(Méthode par kilométrage) > A256 (% de distance effectuée par transport routier local par camion)
        session.saveOrUpdate(new DoubleQuestion(a253, 0, QuestionCode.A256, null));

        // == A257 ========================================================================
        // % de distance effectuée par transport routier local par camionnette
        // A243(Transport & Distribution, Traitement, Utilisation et Fin de vie des produits vendus) > A244(Lister les différents produits ou groupes de produits vendus par l'entreprise) > A250(Transport et distribution aval) > A252(Transport aval: choix de méthodes) > A253(Méthode par kilométrage) > A257 (% de distance effectuée par transport routier local par camionnette)
        session.saveOrUpdate(new DoubleQuestion(a253, 0, QuestionCode.A257, null));

        // == A258 ========================================================================
        // % de distance effectuée par transport routier international
        // A243(Transport & Distribution, Traitement, Utilisation et Fin de vie des produits vendus) > A244(Lister les différents produits ou groupes de produits vendus par l'entreprise) > A250(Transport et distribution aval) > A252(Transport aval: choix de méthodes) > A253(Méthode par kilométrage) > A258 (% de distance effectuée par transport routier international)
        session.saveOrUpdate(new DoubleQuestion(a253, 0, QuestionCode.A258, null));

        // == A259 ========================================================================
        // % de distance effectuée par voie ferroviaire
        // A243(Transport & Distribution, Traitement, Utilisation et Fin de vie des produits vendus) > A244(Lister les différents produits ou groupes de produits vendus par l'entreprise) > A250(Transport et distribution aval) > A252(Transport aval: choix de méthodes) > A253(Méthode par kilométrage) > A259 (% de distance effectuée par voie ferroviaire)
        session.saveOrUpdate(new DoubleQuestion(a253, 0, QuestionCode.A259, null));

        // == A260 ========================================================================
        // % de distance effectuée par voie maritime
        // A243(Transport & Distribution, Traitement, Utilisation et Fin de vie des produits vendus) > A244(Lister les différents produits ou groupes de produits vendus par l'entreprise) > A250(Transport et distribution aval) > A252(Transport aval: choix de méthodes) > A253(Méthode par kilométrage) > A260 (% de distance effectuée par voie maritime)
        session.saveOrUpdate(new DoubleQuestion(a253, 0, QuestionCode.A260, null));

        // == A261 ========================================================================
        // % de distance effectuée par voie fluviale
        // A243(Transport & Distribution, Traitement, Utilisation et Fin de vie des produits vendus) > A244(Lister les différents produits ou groupes de produits vendus par l'entreprise) > A250(Transport et distribution aval) > A252(Transport aval: choix de méthodes) > A253(Méthode par kilométrage) > A261 (% de distance effectuée par voie fluviale)
        session.saveOrUpdate(new DoubleQuestion(a253, 0, QuestionCode.A261, null));

        // == A262 ========================================================================
        // % de distance effectuée par transport aérien court courrier (<1000 km)
        // A243(Transport & Distribution, Traitement, Utilisation et Fin de vie des produits vendus) > A244(Lister les différents produits ou groupes de produits vendus par l'entreprise) > A250(Transport et distribution aval) > A252(Transport aval: choix de méthodes) > A253(Méthode par kilométrage) > A262 (% de distance effectuée par transport aérien court courrier (<1000 km))
        session.saveOrUpdate(new DoubleQuestion(a253, 0, QuestionCode.A262, null));

        // == A263 ========================================================================
        // % de distance effectuée par transport aérien moyen courrier (1000 à 4000 km)
        // A243(Transport & Distribution, Traitement, Utilisation et Fin de vie des produits vendus) > A244(Lister les différents produits ou groupes de produits vendus par l'entreprise) > A250(Transport et distribution aval) > A252(Transport aval: choix de méthodes) > A253(Méthode par kilométrage) > A263 (% de distance effectuée par transport aérien moyen courrier (1000 à 4000 km))
        session.saveOrUpdate(new DoubleQuestion(a253, 0, QuestionCode.A263, null));

        // == A264 ========================================================================
        // % de distance effectuée par transport aérien long courrier (> 4000 km)
        // A243(Transport & Distribution, Traitement, Utilisation et Fin de vie des produits vendus) > A244(Lister les différents produits ou groupes de produits vendus par l'entreprise) > A250(Transport et distribution aval) > A252(Transport aval: choix de méthodes) > A253(Méthode par kilométrage) > A264 (% de distance effectuée par transport aérien long courrier (> 4000 km))
        session.saveOrUpdate(new DoubleQuestion(a253, 0, QuestionCode.A264, null));

        // == A265 ========================================================================
        // Total (supposé être égal à 100%)
        // A243(Transport & Distribution, Traitement, Utilisation et Fin de vie des produits vendus) > A244(Lister les différents produits ou groupes de produits vendus par l'entreprise) > A250(Transport et distribution aval) > A252(Transport aval: choix de méthodes) > A253(Méthode par kilométrage) > A265 (Total (supposé être égal à 100%))
        session.saveOrUpdate(new DoubleQuestion(a253, 0, QuestionCode.A265, null));

        // == A267 ========================================================================
        // Poids total transporté:
        // A243(Transport & Distribution, Traitement, Utilisation et Fin de vie des produits vendus) > A244(Lister les différents produits ou groupes de produits vendus par l'entreprise) > A250(Transport et distribution aval) > A252(Transport aval: choix de méthodes) > A266(Méthode des moyennes) > A267 (Poids total transporté:)
        session.saveOrUpdate(new DoubleQuestion(a266, 0, QuestionCode.A267, massUnits));

        // == A268 ========================================================================
        // Quelle est la destination géographique des produits vendus?
        // A243(Transport & Distribution, Traitement, Utilisation et Fin de vie des produits vendus) > A244(Lister les différents produits ou groupes de produits vendus par l'entreprise) > A250(Transport et distribution aval) > A252(Transport aval: choix de méthodes) > A266(Méthode des moyennes) > A268 (Quelle est la destination géographique des produits vendus?)
        session.saveOrUpdate(new ValueSelectionQuestion(a266, 0, QuestionCode.A268, CodeList.PROVENANCESIMPLIFIEE));

        // == A269 ========================================================================
        // Km assignés en moyenne aux marchandises
        // A243(Transport & Distribution, Traitement, Utilisation et Fin de vie des produits vendus) > A244(Lister les différents produits ou groupes de produits vendus par l'entreprise) > A250(Transport et distribution aval) > A252(Transport aval: choix de méthodes) > A266(Méthode des moyennes) > A269 (Km assignés en moyenne aux marchandises)
        session.saveOrUpdate(new DoubleQuestion(a266, 0, QuestionCode.A269, lengthUnits));

        // == A270 ========================================================================
        // Km assignés en moyenne aux marchandises
        // A243(Transport & Distribution, Traitement, Utilisation et Fin de vie des produits vendus) > A244(Lister les différents produits ou groupes de produits vendus par l'entreprise) > A250(Transport et distribution aval) > A252(Transport aval: choix de méthodes) > A266(Méthode des moyennes) > A270 (Km assignés en moyenne aux marchandises)
        session.saveOrUpdate(new DoubleQuestion(a266, 0, QuestionCode.A270, lengthUnits));

        // == A271 ========================================================================
        // Km assignés en moyenne aux marchandises
        // A243(Transport & Distribution, Traitement, Utilisation et Fin de vie des produits vendus) > A244(Lister les différents produits ou groupes de produits vendus par l'entreprise) > A250(Transport et distribution aval) > A252(Transport aval: choix de méthodes) > A266(Méthode des moyennes) > A271 (Km assignés en moyenne aux marchandises)
        session.saveOrUpdate(new DoubleQuestion(a266, 0, QuestionCode.A271, lengthUnits));

        // == A274 ========================================================================
        // Entrepôt
        // A31(GES des processus de production) > A34(Type de GES émis par la production) > A274 (Entrepôt)
        session.saveOrUpdate(new StringQuestion(a34, 0, QuestionCode.A274));

        // == A276 ========================================================================
        // Combustible utilisé
        // A31(GES des processus de production) > A34(Type de GES émis par la production) > A275(Listez les totaux de combustibles utilisés) > A276 (Combustible utilisé)
        session.saveOrUpdate(new ValueSelectionQuestion(a275, 0, QuestionCode.A276, CodeList.COMBUSTIBLE));

        // == A277 ========================================================================
        // Quantité
        // A31(GES des processus de production) > A34(Type de GES émis par la production) > A275(Listez les totaux de combustibles utilisés) > A277 (Quantité)
        session.saveOrUpdate(new DoubleQuestion(a275, 0, QuestionCode.A277, energyUnits));

        // == A278 ========================================================================
        // Electricité
        // A31(GES des processus de production) > A34(Type de GES émis par la production) > A278 (Electricité)
        session.saveOrUpdate(new DoubleQuestion(a34, 0, QuestionCode.A278, energyUnits));

        // == A280 ========================================================================
        // Type de gaz
        // A31(GES des processus de production) > A34(Type de GES émis par la production) > A279(Listez les gaz réfrigérants) > A280 (Type de gaz)
        session.saveOrUpdate(new ValueSelectionQuestion(a279, 0, QuestionCode.A280, CodeList.FRIGORIGENE));

        // == A281 ========================================================================
        // Quantité de recharge nécessaire pour l'année
        // A31(GES des processus de production) > A34(Type de GES émis par la production) > A279(Listez les gaz réfrigérants) > A281 (Quantité de recharge nécessaire pour l'année)
        session.saveOrUpdate(new DoubleQuestion(a279, 0, QuestionCode.A281, massUnits));

        // == A283 ========================================================================
        // Fournir ici les documents éventuels justifiant les données suivantes
        // A243(Transport & Distribution, Traitement, Utilisation et Fin de vie des produits vendus) > A244(Lister les différents produits ou groupes de produits vendus par l'entreprise) > A282(Traitement) > A283 (Fournir ici les documents éventuels justifiant les données suivantes)
        session.saveOrUpdate(new StringQuestion(a282, 0, QuestionCode.A283));

        // == A285 ========================================================================
        // Combustible utilisé
        // A243(Transport & Distribution, Traitement, Utilisation et Fin de vie des produits vendus) > A244(Lister les différents produits ou groupes de produits vendus par l'entreprise) > A282(Traitement) > A284(Listez les totaux de combustibles) > A285 (Combustible utilisé)
        session.saveOrUpdate(new ValueSelectionQuestion(a284, 0, QuestionCode.A285, CodeList.COMBUSTIBLE));

        // == A286 ========================================================================
        // Quantité
        // A243(Transport & Distribution, Traitement, Utilisation et Fin de vie des produits vendus) > A244(Lister les différents produits ou groupes de produits vendus par l'entreprise) > A282(Traitement) > A284(Listez les totaux de combustibles) > A286 (Quantité)
        session.saveOrUpdate(new DoubleQuestion(a284, 0, QuestionCode.A286, energyUnits));

        // == A287 ========================================================================
        // Electricité
        // A243(Transport & Distribution, Traitement, Utilisation et Fin de vie des produits vendus) > A244(Lister les différents produits ou groupes de produits vendus par l'entreprise) > A282(Traitement) > A287 (Electricité)
        session.saveOrUpdate(new DoubleQuestion(a282, 0, QuestionCode.A287, energyUnits));

        // == A289 ========================================================================
        // Type de gaz
        // A243(Transport & Distribution, Traitement, Utilisation et Fin de vie des produits vendus) > A244(Lister les différents produits ou groupes de produits vendus par l'entreprise) > A282(Traitement) > A288(Listez les gaz réfrigérants) > A289 (Type de gaz)
        session.saveOrUpdate(new ValueSelectionQuestion(a288, 0, QuestionCode.A289, CodeList.FRIGORIGENE));

        // == A290 ========================================================================
        // Quantité de recharge nécessaire pour l'année
        // A243(Transport & Distribution, Traitement, Utilisation et Fin de vie des produits vendus) > A244(Lister les différents produits ou groupes de produits vendus par l'entreprise) > A282(Traitement) > A288(Listez les gaz réfrigérants) > A290 (Quantité de recharge nécessaire pour l'année)
        session.saveOrUpdate(new DoubleQuestion(a288, 0, QuestionCode.A290, massUnits));

        // == A292 ========================================================================
        // Fournir ici les documents éventuels justifiant les données suivantes
        // A243(Transport & Distribution, Traitement, Utilisation et Fin de vie des produits vendus) > A244(Lister les différents produits ou groupes de produits vendus par l'entreprise) > A291(Utilisation) > A292 (Fournir ici les documents éventuels justifiant les données suivantes)
        session.saveOrUpdate(new StringQuestion(a291, 0, QuestionCode.A292));

        // == A293 ========================================================================
        // Nombre total d'utilisations du produit ou groupe de produits sur toute sa durée de vie
        // A243(Transport & Distribution, Traitement, Utilisation et Fin de vie des produits vendus) > A244(Lister les différents produits ou groupes de produits vendus par l'entreprise) > A291(Utilisation) > A293 (Nombre total d'utilisations du produit ou groupe de produits sur toute sa durée de vie)
        session.saveOrUpdate(new IntegerQuestion(a291, 0, QuestionCode.A293, null));

        // == A294 ========================================================================
        // Consommation de diesel par utilisation de produit
        // A243(Transport & Distribution, Traitement, Utilisation et Fin de vie des produits vendus) > A244(Lister les différents produits ou groupes de produits vendus par l'entreprise) > A291(Utilisation) > A294 (Consommation de diesel par utilisation de produit)
        session.saveOrUpdate(new DoubleQuestion(a291, 0, QuestionCode.A294, volumeUnits));

        // == A295 ========================================================================
        // Consommation d'essence par utilisation de produit
        // A243(Transport & Distribution, Traitement, Utilisation et Fin de vie des produits vendus) > A244(Lister les différents produits ou groupes de produits vendus par l'entreprise) > A291(Utilisation) > A295 (Consommation d'essence par utilisation de produit)
        session.saveOrUpdate(new DoubleQuestion(a291, 0, QuestionCode.A295, volumeUnits));

        // == A296 ========================================================================
        // Consommation d'électricité par utilisation de produit
        // A243(Transport & Distribution, Traitement, Utilisation et Fin de vie des produits vendus) > A244(Lister les différents produits ou groupes de produits vendus par l'entreprise) > A291(Utilisation) > A296 (Consommation d'électricité par utilisation de produit)
        session.saveOrUpdate(new DoubleQuestion(a291, 0, QuestionCode.A296, energyUnits));

        // == A298 ========================================================================
        // Gaz émis
        // A243(Transport & Distribution, Traitement, Utilisation et Fin de vie des produits vendus) > A244(Lister les différents produits ou groupes de produits vendus par l'entreprise) > A291(Utilisation) > A297(Listez les gaz émis par utilisation de produit) > A298 (Gaz émis)
        session.saveOrUpdate(new ValueSelectionQuestion(a297, 0, QuestionCode.A298, CodeList.GESSIMPLIFIE));

        // == A299 ========================================================================
        // Quantité
        // A243(Transport & Distribution, Traitement, Utilisation et Fin de vie des produits vendus) > A244(Lister les différents produits ou groupes de produits vendus par l'entreprise) > A291(Utilisation) > A297(Listez les gaz émis par utilisation de produit) > A299 (Quantité)
        session.saveOrUpdate(new DoubleQuestion(a297, 0, QuestionCode.A299, massUnits));

        // == A301 ========================================================================
        // Fournir ici les documents éventuels justifiant les données suivantes
        // A243(Transport & Distribution, Traitement, Utilisation et Fin de vie des produits vendus) > A244(Lister les différents produits ou groupes de produits vendus par l'entreprise) > A300(Traitement de fin de vie) > A301 (Fournir ici les documents éventuels justifiant les données suivantes)
        session.saveOrUpdate(new StringQuestion(a300, 0, QuestionCode.A301));

        // == A302 ========================================================================
        // Poids total de produit vendu
        // A243(Transport & Distribution, Traitement, Utilisation et Fin de vie des produits vendus) > A244(Lister les différents produits ou groupes de produits vendus par l'entreprise) > A300(Traitement de fin de vie) > A302 (Poids total de produit vendu)
        session.saveOrUpdate(new DoubleQuestion(a300, 0, QuestionCode.A302, massUnits));

        // == A304 ========================================================================
        // Catégorie de déchet
        // A243(Transport & Distribution, Traitement, Utilisation et Fin de vie des produits vendus) > A244(Lister les différents produits ou groupes de produits vendus par l'entreprise) > A300(Traitement de fin de vie) > A303(Créez autant de catégories de déchet que nécessaire) > A304 (Catégorie de déchet)
        session.saveOrUpdate(new StringQuestion(a303, 0, QuestionCode.A304));

        // == A305 ========================================================================
        // Poids total de cette catégorie de déchet issu des produits vendus
        // A243(Transport & Distribution, Traitement, Utilisation et Fin de vie des produits vendus) > A244(Lister les différents produits ou groupes de produits vendus par l'entreprise) > A300(Traitement de fin de vie) > A303(Créez autant de catégories de déchet que nécessaire) > A305 (Poids total de cette catégorie de déchet issu des produits vendus)
        session.saveOrUpdate(new DoubleQuestion(a303, 0, QuestionCode.A305, massUnits));

        // == A306 ========================================================================
        // Type principal de ce déchet:
        // A243(Transport & Distribution, Traitement, Utilisation et Fin de vie des produits vendus) > A244(Lister les différents produits ou groupes de produits vendus par l'entreprise) > A300(Traitement de fin de vie) > A303(Créez autant de catégories de déchet que nécessaire) > A306 (Type principal de ce déchet:)
        session.saveOrUpdate(new ValueSelectionQuestion(a303, 0, QuestionCode.A306, CodeList.TYPEDECHET));

        // == A307 ========================================================================
        // Type de traitement du déchet
        // A243(Transport & Distribution, Traitement, Utilisation et Fin de vie des produits vendus) > A244(Lister les différents produits ou groupes de produits vendus par l'entreprise) > A300(Traitement de fin de vie) > A303(Créez autant de catégories de déchet que nécessaire) > A307 (Type de traitement du déchet)
        session.saveOrUpdate(new ValueSelectionQuestion(a303, 0, QuestionCode.A307, CodeList.TRAITEMENTDECHET));

        // == A308 ========================================================================
        // Proportion du déchet issu du produit, traité par la méthode précédemment renseignée
        // A243(Transport & Distribution, Traitement, Utilisation et Fin de vie des produits vendus) > A244(Lister les différents produits ou groupes de produits vendus par l'entreprise) > A300(Traitement de fin de vie) > A303(Créez autant de catégories de déchet que nécessaire) > A308 (Proportion du déchet issu du produit, traité par la méthode précédemment renseignée)
        session.saveOrUpdate(new ValueSelectionQuestion(a303, 0, QuestionCode.A308, CodeList.POURCENTSIMPLIFIE));

        // == A310 ========================================================================
        // Fournir ici les documents éventuels justifiant les données suivantes
        // A309(Actifs loués (aval)) > A310 (Fournir ici les documents éventuels justifiant les données suivantes)
        session.saveOrUpdate(new StringQuestion(a309, 0, QuestionCode.A310));

        // == A312 ========================================================================
        // Catégorie d'actif loué
        // A309(Actifs loués (aval)) > A311(Créez autant de catégories d'actifs loués que nécessaire) > A312 (Catégorie d'actif loué)
        session.saveOrUpdate(new StringQuestion(a311, 0, QuestionCode.A312));

        // == A314 ========================================================================
        // Combustible utilisé
        // A309(Actifs loués (aval)) > A311(Créez autant de catégories d'actifs loués que nécessaire) > A313(Listez les totaux de combustibles utilisés pour les actifs loués) > A314 (Combustible utilisé)
        session.saveOrUpdate(new ValueSelectionQuestion(a313, 0, QuestionCode.A314, CodeList.COMBUSTIBLE));

        // == A315 ========================================================================
        // Quantité
        // A309(Actifs loués (aval)) > A311(Créez autant de catégories d'actifs loués que nécessaire) > A313(Listez les totaux de combustibles utilisés pour les actifs loués) > A315 (Quantité)
        session.saveOrUpdate(new DoubleQuestion(a313, 0, QuestionCode.A315, energyUnits));

        // == A316 ========================================================================
        // Electricité
        // A309(Actifs loués (aval)) > A311(Créez autant de catégories d'actifs loués que nécessaire) > A316 (Electricité)
        session.saveOrUpdate(new DoubleQuestion(a311, 0, QuestionCode.A316, energyUnits));

        // == A318 ========================================================================
        // Type de gaz
        // A309(Actifs loués (aval)) > A311(Créez autant de catégories d'actifs loués que nécessaire) > A317(Listez les gaz réfrigérants et autres nécessaires à l'opération des actifs loués) > A318 (Type de gaz)
        session.saveOrUpdate(new ValueSelectionQuestion(a317, 0, QuestionCode.A318, CodeList.FRIGORIGENE));

        // == A319 ========================================================================
        // Quantité de recharge nécessaire pour l'année
        // A309(Actifs loués (aval)) > A311(Créez autant de catégories d'actifs loués que nécessaire) > A317(Listez les gaz réfrigérants et autres nécessaires à l'opération des actifs loués) > A319 (Quantité de recharge nécessaire pour l'année)
        session.saveOrUpdate(new DoubleQuestion(a317, 0, QuestionCode.A319, massUnits));

        // == A321 ========================================================================
        // Fournir ici les documents éventuels justifiant les données suivantes
        // A320(Franchises) > A321 (Fournir ici les documents éventuels justifiant les données suivantes)
        session.saveOrUpdate(new StringQuestion(a320, 0, QuestionCode.A321));

        // == A323 ========================================================================
        // Catégorie de franchisé
        // A320(Franchises) > A322(Créez autant de catégories de franchisés que nécessaire) > A323 (Catégorie de franchisé)
        session.saveOrUpdate(new StringQuestion(a322, 0, QuestionCode.A323));

        // == A324 ========================================================================
        // Nombre de franchisés
        // A320(Franchises) > A322(Créez autant de catégories de franchisés que nécessaire) > A324 (Nombre de franchisés)
        session.saveOrUpdate(new IntegerQuestion(a322, 0, QuestionCode.A324, null));

        // == A326 ========================================================================
        // Combustible utilisé
        // A320(Franchises) > A322(Créez autant de catégories de franchisés que nécessaire) > A325(Listez les moyennes de combustibles utilisés par franchisé) > A326 (Combustible utilisé)
        session.saveOrUpdate(new ValueSelectionQuestion(a325, 0, QuestionCode.A326, CodeList.COMBUSTIBLE));

        // == A327 ========================================================================
        // Quantité
        // A320(Franchises) > A322(Créez autant de catégories de franchisés que nécessaire) > A325(Listez les moyennes de combustibles utilisés par franchisé) > A327 (Quantité)
        session.saveOrUpdate(new DoubleQuestion(a325, 0, QuestionCode.A327, energyUnits));

        // == A328 ========================================================================
        // Electricité (moyenne par franchisé)
        // A320(Franchises) > A322(Créez autant de catégories de franchisés que nécessaire) > A328 (Electricité (moyenne par franchisé))
        session.saveOrUpdate(new DoubleQuestion(a322, 0, QuestionCode.A328, energyUnits));

        // == A330 ========================================================================
        // Type de gaz
        // A320(Franchises) > A322(Créez autant de catégories de franchisés que nécessaire) > A329(Listez les gaz réfrigérants et autres utilisés en moyenne par franchisé) > A330 (Type de gaz)
        session.saveOrUpdate(new ValueSelectionQuestion(a329, 0, QuestionCode.A330, CodeList.FRIGORIGENE));

        // == A331 ========================================================================
        // Quantité de recharge nécessaire pour l'année
        // A320(Franchises) > A322(Créez autant de catégories de franchisés que nécessaire) > A329(Listez les gaz réfrigérants et autres utilisés en moyenne par franchisé) > A331 (Quantité de recharge nécessaire pour l'année)
        session.saveOrUpdate(new DoubleQuestion(a329, 0, QuestionCode.A331, massUnits));

        // == A333 ========================================================================
        // Fournir ici les documents éventuels justifiant les données suivantes
        // A332(Activités d'investissement) > A333 (Fournir ici les documents éventuels justifiant les données suivantes)
        session.saveOrUpdate(new StringQuestion(a332, 0, QuestionCode.A333));

        // == A335 ========================================================================
        // Nom du projet
        // A332(Activités d'investissement) > A334(Veuillez indiquer ici tous les projets dans lesquels votre entreprise investit) > A335 (Nom du projet)
        session.saveOrUpdate(new StringQuestion(a334, 0, QuestionCode.A335));

        // == A336 ========================================================================
        // Part d'investissements dans le projet
        // A332(Activités d'investissement) > A334(Veuillez indiquer ici tous les projets dans lesquels votre entreprise investit) > A336 (Part d'investissements dans le projet)
        session.saveOrUpdate(new DoubleQuestion(a334, 0, QuestionCode.A336, null));

        // == A337 ========================================================================
        // Emissions directes totales (tCO2e)
        // A332(Activités d'investissement) > A334(Veuillez indiquer ici tous les projets dans lesquels votre entreprise investit) > A337 (Emissions directes totales (tCO2e))
        session.saveOrUpdate(new IntegerQuestion(a334, 0, QuestionCode.A337, null));

        // == A338 ========================================================================
        // Emissions indirectes totales (tCO2e)
        // A332(Activités d'investissement) > A334(Veuillez indiquer ici tous les projets dans lesquels votre entreprise investit) > A338 (Emissions indirectes totales (tCO2e))
        session.saveOrUpdate(new IntegerQuestion(a334, 0, QuestionCode.A338, null));











    }


    private static Unit getUnitBySymbol(String symbol) {
        return JPA.em().createNamedQuery(Unit.FIND_BY_SYMBOL, Unit.class).setParameter("symbol", symbol).getSingleResult();
    }

    private static UnitCategory getUnitCategoryByName(String name) {
        return JPA.em().createNamedQuery(UnitCategory.FIND_BY_NAME, UnitCategory.class).setParameter("name", name).getSingleResult();
    }

}
