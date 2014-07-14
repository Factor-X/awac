package eu.factorx.awac;

import eu.factorx.awac.models.business.Site;
import eu.factorx.awac.models.data.question.type.*;
import org.hibernate.Session;

import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.code.label.CodeLabel;
import eu.factorx.awac.models.code.type.HeatingFuelTypeCode;
import eu.factorx.awac.models.code.type.LanguageCode;
import eu.factorx.awac.models.code.type.QuestionCode;
import eu.factorx.awac.models.data.answer.QuestionAnswer;
import eu.factorx.awac.models.data.answer.type.CodeAnswerValue;
import eu.factorx.awac.models.data.answer.type.DoubleAnswerValue;
import eu.factorx.awac.models.data.answer.type.IntegerAnswerValue;
import eu.factorx.awac.models.data.question.Question;
import eu.factorx.awac.models.data.question.QuestionSet;
import eu.factorx.awac.models.forms.Form;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.models.knowledge.Unit;
import eu.factorx.awac.models.knowledge.UnitCategory;

public class AwacDummyDataCreator {

    public static void createAwacDummyData(Session session) {

        // REFERENCES DATA

        session.saveOrUpdate(new CodeLabel(HeatingFuelTypeCode.GAS, "Gas", "Gaz", "Gas"));
        session.saveOrUpdate(new CodeLabel(HeatingFuelTypeCode.OIL, "Oil", "Mazout", "Brandstof"));
        session.saveOrUpdate(new CodeLabel(HeatingFuelTypeCode.COAL, "Coal", "Charbon", "Steenkool"));

        session.saveOrUpdate(new CodeLabel(LanguageCode.ENGLISH, "English", "Anglais", "Engels"));
        session.saveOrUpdate(new CodeLabel(LanguageCode.FRENCH, "French", "Français", "Frans"));
        session.saveOrUpdate(new CodeLabel(LanguageCode.DUTCH, "Dutch", "Néerlandais", "Nederlands"));

        UnitCategory volumeUnits = new UnitCategory("Volume");
        session.saveOrUpdate(volumeUnits);
        Unit litter = new Unit("Liter", volumeUnits);
        session.saveOrUpdate(litter);
        Unit gallon = new Unit("Gallon", volumeUnits);
        session.saveOrUpdate(gallon);
        Unit cubicMeter = new Unit("Cubic meter", volumeUnits);
        session.saveOrUpdate(cubicMeter);

        UnitCategory massUnits = new UnitCategory("Mass");
        session.saveOrUpdate(massUnits);
        Unit kg = new Unit("Kg", massUnits);
        session.saveOrUpdate(kg);
        Unit ton = new Unit("Ton", massUnits);
        session.saveOrUpdate(ton);

        UnitCategory surfaceUnits = new UnitCategory("Surface");
        session.saveOrUpdate(surfaceUnits);
        Unit m2 = new Unit("m2", surfaceUnits);
        session.saveOrUpdate(m2);

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

        Form form1 = new Form("Formulaire Entreprise");
        session.saveOrUpdate(form1);

        // QUESTIONS

        // (1) Heating Fuel Question Set (repetition allowed)
        QuestionSet hfqs = new QuestionSet(QuestionCode.HFQS, Boolean.TRUE);
        session.saveOrUpdate(hfqs);

        // (1.1) Heating fuel type
        Question hfqType = new ValueSelectionQuestion(hfqs, 0, QuestionCode.HFQ_HFTYPE, CodeList.HEATING_FUEL_TYPE);
        hfqs.addQuestion(hfqType);

        // (1.2a) Heating fuel consumption (in volume units)
        Question hfqVol = new IntegerQuestion(hfqs, 1, QuestionCode.HFQ_HFCONSO_VOL, volumeUnits);
        hfqs.addQuestion(hfqVol);

        // (1.2b) Heating fuel consumption (in mass units)
        Question hfqMass = new DoubleQuestion(hfqs, 1, QuestionCode.HFQ_HFCONSO_MASS, massUnits);
        hfqs.addQuestion(hfqMass);

        // link question set to form1
        form1.getQuestionSet().add(hfqs);
        session.saveOrUpdate(form1);

        // (2) Air Travel Questions Set (repetition allowed)
        // TODO !

        // ANSWERS

        Scope scope1 = new Scope(factorx);
        session.saveOrUpdate(scope1);

        // first set of responses
        short repetitionIndex = 1;

        QuestionAnswer hfqTypeAnswer1 = new QuestionAnswer(period1, scope1, user1, hfqType, repetitionIndex);
        session.saveOrUpdate(hfqTypeAnswer1);
        CodeAnswerValue hfqTypeAnswer1Value = new CodeAnswerValue(hfqTypeAnswer1, HeatingFuelTypeCode.GAS);
        session.saveOrUpdate(hfqTypeAnswer1Value);

        QuestionAnswer hfqVolAnswer1 = new QuestionAnswer(period1, scope1, user1, hfqVol, repetitionIndex);
        session.saveOrUpdate(hfqVolAnswer1);
        IntegerAnswerValue hfqVolAnswer1Value = new IntegerAnswerValue(hfqVolAnswer1, 120, cubicMeter);
        session.saveOrUpdate(hfqVolAnswer1Value);

        // second set of responses
        repetitionIndex = 2;

        QuestionAnswer question1_answer2 = new QuestionAnswer(period1, scope1, user1, hfqType, repetitionIndex);
        session.saveOrUpdate(question1_answer2);
        CodeAnswerValue question1_answer2Value = new CodeAnswerValue(question1_answer2, HeatingFuelTypeCode.COAL);
        session.saveOrUpdate(question1_answer2Value);

        QuestionAnswer question2b_answer2 = new QuestionAnswer(period1, scope1, user1, hfqMass, repetitionIndex);
        session.saveOrUpdate(question2b_answer2);
        DoubleAnswerValue question2b_answer2Value = new DoubleAnswerValue(question2b_answer2, 3.2, ton);
        session.saveOrUpdate(question2b_answer2Value);

        // TEST FORM

        Form testForm = new Form("TEST_FORM");
        session.saveOrUpdate(testForm);

        // (Q1)
        QuestionSet questionSet1 = new QuestionSet(new QuestionCode("QS1"), Boolean.TRUE);
        session.saveOrUpdate(questionSet1);
        Question question1 = new StringQuestion(questionSet1, 0, new QuestionCode("Q1"));
        session.saveOrUpdate(question1);

        // (Q2)
        QuestionSet questionSet2 = new QuestionSet(new QuestionCode("QS2"), Boolean.TRUE);
        session.saveOrUpdate(questionSet2);
        Question question2 = new IntegerQuestion(questionSet2, 0, new QuestionCode("Q2"), volumeUnits);
        session.saveOrUpdate(question2);

        // (Q3)
        QuestionSet questionSet3 = new QuestionSet(new QuestionCode("QS3"), Boolean.TRUE);
        session.saveOrUpdate(questionSet3);
        Question question3 = new DoubleQuestion(questionSet3, 0, new QuestionCode("Q3"), null);
        session.saveOrUpdate(question3);

        // (Q4)
        QuestionSet questionSet4 = new QuestionSet(new QuestionCode("QS4"), Boolean.TRUE);
        session.saveOrUpdate(questionSet4);
        Question question4 = new IntegerQuestion(questionSet4, 0, new QuestionCode("Q4"), massUnits);
        session.saveOrUpdate(question4);

        // Add question sets to test form
        testForm.getQuestionSet().add(questionSet1);
        testForm.getQuestionSet().add(questionSet2);
        testForm.getQuestionSet().add(questionSet3);
        testForm.getQuestionSet().add(questionSet4);
        session.saveOrUpdate(testForm);

        // (Answer Q4)
        QuestionAnswer question4Answer = new QuestionAnswer(period1, scope1, user1, question4, 0);
        session.saveOrUpdate(question4Answer);
        IntegerAnswerValue question4AnswerValue = new IntegerAnswerValue(question4Answer, 23, ton);
        session.saveOrUpdate(question4AnswerValue);

        System.out.println(" ==== TEST ANSWERS CONTROLLER - REQUEST PARAMS:");
        System.out.println(" ===== Form ID = " + testForm.getId());
        System.out.println(" ===== Period ID = " + period1.getId());
        System.out.println(" ===== Scope ID = " + scope1.getId());

        // -- FORM
        Form tab1Form = new Form("TAB1");
        session.saveOrUpdate(tab1Form);

        Form tab2Form = new Form("TAB2");
        session.saveOrUpdate(tab2Form);

        // -- CODE LISTS
        session.saveOrUpdate(new CodeLabel(CodeList.SITE_SECTORS, "1", "Industrie primaire, hormis le secteur agricole", "Industrie primaire, hormis le secteur agricole", "Industrie primaire, hormis le secteur agricole"));
        session.saveOrUpdate(new CodeLabel(CodeList.SITE_SECTORS, "2", "Production de biens intermédiaires", "Production de biens intermédiaires", "Production de biens intermédiaires"));
        session.saveOrUpdate(new CodeLabel(CodeList.SITE_SECTORS, "3", "Production de biens de consommation", "Production de biens de consommation", "Production de biens de consommation"));
        session.saveOrUpdate(new CodeLabel(CodeList.SITE_SECTORS, "4", "Tertiaire", "Tertiaire", "Tertiaire"));

        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES_1, "1", "05 Extraction de houille et de lignite", "05 Extraction de houille et de lignite", "05 Extraction de houille et de lignite"));
        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES_1, "2", "06 Extraction d'hydrocarbures", "06 Extraction d'hydrocarbures", "06 Extraction d'hydrocarbures"));
        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES_1, "3", "07 Extraction de minerais métalliques", "07 Extraction de minerais métalliques", "07 Extraction de minerais métalliques"));
        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES_1, "4", "08 Autres industries extractives", "08 Autres industries extractives", "08 Autres industries extractives"));
        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES_1, "5", "09 Services de soutien aux industries extractives", "09 Services de soutien aux industries extractives", "09 Services de soutien aux industries extractives"));

        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES_2, "1", "10 Industries alimentaires", "10 Industries alimentaires", "10 Industries alimentaires"));
        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES_2, "1", "11 Fabrication de boissons", "11 Fabrication de boissons", "11 Fabrication de boissons"));
        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES_2, "1", "12 Fabrication de produits à base de tabac", "12 Fabrication de produits à base de tabac", "12 Fabrication de produits à base de tabac"));
        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES_2, "1", "13 Fabrication de textiles", "13 Fabrication de textiles", "13 Fabrication de textiles"));
        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES_2, "1", "14 Industrie de l'habillement", "14 Industrie de l'habillement", "14 Industrie de l'habillement"));
        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES_2, "1", "15 Industrie du cuir et de la chaussure", "15 Industrie du cuir et de la chaussure", "15 Industrie du cuir et de la chaussure"));
        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES_2, "1", "16 Travail du bois et fabrication d'articles en bois et en liège, à l'exception des meubles, fabrication d'articles en vannerie et sparterie", "16 Travail du bois et fabrication d'articles en bois et en liège, à l'exception des meubles, fabrication d'articles en vannerie et sparterie", "16 Travail du bois et fabrication d'articles en bois et en liège, à l'exception des meubles, fabrication d'articles en vannerie et sparterie"));
        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES_2, "1", "17 Industrie du papier et du carton", "17 Industrie du papier et du carton", "17 Industrie du papier et du carton"));
        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES_2, "1", "18 Imprimerie et reproduction d'enregistrements", "18 Imprimerie et reproduction d'enregistrements", "18 Imprimerie et reproduction d'enregistrements"));
        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES_2, "1", "19 Cokéfaction et raffinage", "19 Cokéfaction et raffinage", "19 Cokéfaction et raffinage"));
        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES_2, "1", "20 Industrie chimique", "20 Industrie chimique", "20 Industrie chimique"));
        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES_2, "1", "21 Industrie pharmaceutique", "21 Industrie pharmaceutique", "21 Industrie pharmaceutique"));
        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES_2, "1", "22 Fabrication de produits en caoutchouc et en plastique", "22 Fabrication de produits en caoutchouc et en plastique", "22 Fabrication de produits en caoutchouc et en plastique"));
        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES_2, "1", "23 Fabrication d'autres produits minéraux non métalliques", "23 Fabrication d'autres produits minéraux non métalliques", "23 Fabrication d'autres produits minéraux non métalliques"));
        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES_2, "1", "24 Métallurgie", "24 Métallurgie", "24 Métallurgie"));
        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES_2, "1", "25 Fabrication de produits métalliques, à l'exception des machines et des équipements", "25 Fabrication de produits métalliques, à l'exception des machines et des équipements", "25 Fabrication de produits métalliques, à l'exception des machines et des équipements"));
        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES_2, "1", "26 Fabrication de produits informatiques, électroniques et optiques", "26 Fabrication de produits informatiques, électroniques et optiques", "26 Fabrication de produits informatiques, électroniques et optiques"));
        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES_2, "1", "27 Fabrication d'équipements électriques", "27 Fabrication d'équipements électriques", "27 Fabrication d'équipements électriques"));
        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES_2, "1", "28 Fabrication de machines et d'équipements n.c.a.", "28 Fabrication de machines et d'équipements n.c.a.", "28 Fabrication de machines et d'équipements n.c.a."));
        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES_2, "1", "29 Construction et assemblage de véhicules automobiles, de remorques et de semi remorques", "29 Construction et assemblage de véhicules automobiles, de remorques et de semi remorques", "29 Construction et assemblage de véhicules automobiles, de remorques et de semi remorques"));
        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES_2, "1", "30 Fabrication d'autres matériels de transport", "30 Fabrication d'autres matériels de transport", "30 Fabrication d'autres matériels de transport"));
        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES_2, "1", "31 Fabrication de meubles", "31 Fabrication de meubles", "31 Fabrication de meubles"));
        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES_2, "1", "32 Autres industries manufacturières", "32 Autres industries manufacturières", "32 Autres industries manufacturières"));
        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES_2, "1", "33 Réparation et installation de machines et d'équipements", "33 Réparation et installation de machines et d'équipements", "33 Réparation et installation de machines et d'équipements"));
        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES_2, "1", "35 Production et distribution d'électricité, de gaz, de vapeur et d'air conditionné", "35 Production et distribution d'électricité, de gaz, de vapeur et d'air conditionné", "35 Production et distribution d'électricité, de gaz, de vapeur et d'air conditionné"));


        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES_3, "1",
                "36 Captage, traitement et distribution d'eau",
                "36 Captage, traitement et distribution d'eau",
                "36 Captage, traitement et distribution d'eau"));
        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES_3, "1",
                "37 Collecte et traitement des eaux usées",
                "37 Collecte et traitement des eaux usées",
                "37 Collecte et traitement des eaux usées"));
        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES_3, "1",
                "38 Collecte, traitement et élimination des déchets, récupération",
                "38 Collecte, traitement et élimination des déchets, récupération",
                "38 Collecte, traitement et élimination des déchets, récupération"));
        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES_3, "1",
                "39 Dépollution et autres services de gestion des déchets",
                "39 Dépollution et autres services de gestion des déchets",
                "39 Dépollution et autres services de gestion des déchets"));
        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES_3, "1",
                "41 Construction de bâtiments, promotion immobilière",
                "41 Construction de bâtiments, promotion immobilière",
                "41 Construction de bâtiments, promotion immobilière"));
        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES_3, "1",
                "42 Génie civil",
                "42 Génie civil",
                "42 Génie civil"));
        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES_3, "1",
                "43 Travaux de construction spécialisés",
                "43 Travaux de construction spécialisés",
                "43 Travaux de construction spécialisés"));
        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES_3, "1",
                "45 Commerce de gros et de détail et réparation véhicules automobiles et de motocycles",
                "45 Commerce de gros et de détail et réparation véhicules automobiles et de motocycles",
                "45 Commerce de gros et de détail et réparation véhicules automobiles et de motocycles"));
        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES_3, "1",
                "46 Commerce de gros, à l'exception des véhicules automobiles et des motocycles",
                "46 Commerce de gros, à l'exception des véhicules automobiles et des motocycles",
                "46 Commerce de gros, à l'exception des véhicules automobiles et des motocycles"));
        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES_3, "1",
                "47 Commerce de détail, à l'exception des véhicules automobiles et des motocycles",
                "47 Commerce de détail, à l'exception des véhicules automobiles et des motocycles",
                "47 Commerce de détail, à l'exception des véhicules automobiles et des motocycles"));
        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES_3, "1",
                "49 Transports terrestres et transport par conduites",
                "49 Transports terrestres et transport par conduites",
                "49 Transports terrestres et transport par conduites"));
        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES_3, "1",
                "50 Transports par eau",
                "50 Transports par eau",
                "50 Transports par eau"));
        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES_3, "1",
                "51 Transports aériens",
                "51 Transports aériens",
                "51 Transports aériens"));
        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES_3, "1",
                "52 Entreposage et services auxiliaires des transports",
                "52 Entreposage et services auxiliaires des transports",
                "52 Entreposage et services auxiliaires des transports"));
        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES_3, "1",
                "53 Activités de poste et de courrier",
                "53 Activités de poste et de courrier",
                "53 Activités de poste et de courrier"));
        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES_3, "1",
                "55 Hébergement",
                "55 Hébergement",
                "55 Hébergement"));
        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES_3, "1",
                "56 Restauration",
                "56 Restauration",
                "56 Restauration"));
        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES_3, "1",
                "58 Édition",
                "58 Édition",
                "58 Édition"));
        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES_3, "1",
                "59 Production de films cinématographiques, de vidéo et de programmes de télévision, enregistrement sonore et édition musicale",
                "59 Production de films cinématographiques, de vidéo et de programmes de télévision, enregistrement sonore et édition musicale",
                "59 Production de films cinématographiques, de vidéo et de programmes de télévision, enregistrement sonore et édition musicale"));
        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES_3, "1",
                "60 Programmation et diffusion de programmes de radio et de télévision",
                "60 Programmation et diffusion de programmes de radio et de télévision",
                "60 Programmation et diffusion de programmes de radio et de télévision"));
        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES_3, "1",
                "61 Télécommunications",
                "61 Télécommunications",
                "61 Télécommunications"));
        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES_3, "1",
                "62 Programmation, conseil et autres activités informatiques",
                "62 Programmation, conseil et autres activités informatiques",
                "62 Programmation, conseil et autres activités informatiques"));
        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES_3, "1",
                "63 Services d'information",
                "63 Services d'information",
                "63 Services d'information"));
        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES_3, "1",
                "64 Activités des services financiers, hors assurance et caisses de retraite",
                "64 Activités des services financiers, hors assurance et caisses de retraite",
                "64 Activités des services financiers, hors assurance et caisses de retraite"));
        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES_3, "1",
                "65 Assurance, réassurance et caisses de retraite, à l'exclusion des assurances sociales obligatoires",
                "65 Assurance, réassurance et caisses de retraite, à l'exclusion des assurances sociales obligatoires",
                "65 Assurance, réassurance et caisses de retraite, à l'exclusion des assurances sociales obligatoires"));
        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES_3, "1",
                "66 Activités auxiliaires de services financiers et d'assurance",
                "66 Activités auxiliaires de services financiers et d'assurance",
                "66 Activités auxiliaires de services financiers et d'assurance"));
        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES_3, "1",
                "68 Activités immobilières",
                "68 Activités immobilières",
                "68 Activités immobilières"));
        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES_3, "1",
                "69 Activités juridiques et comptables",
                "69 Activités juridiques et comptables",
                "69 Activités juridiques et comptables"));
        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES_3, "1",
                "70 Activités des sièges sociaux, conseil de gestion",
                "70 Activités des sièges sociaux, conseil de gestion",
                "70 Activités des sièges sociaux, conseil de gestion"));
        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES_3, "1",
                "71 Activités d'architecture et d'ingénierie, activités de contrôle et analyses techniques",
                "71 Activités d'architecture et d'ingénierie, activités de contrôle et analyses techniques",
                "71 Activités d'architecture et d'ingénierie, activités de contrôle et analyses techniques"));
        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES_3, "1",
                "72 Recherche développement scientifique",
                "72 Recherche développement scientifique",
                "72 Recherche développement scientifique"));
        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES_3, "1",
                "73 Publicité et études de marché",
                "73 Publicité et études de marché",
                "73 Publicité et études de marché"));
        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES_3, "1",
                "74 Autres activités spécialisées, scientifiques et techniques",
                "74 Autres activités spécialisées, scientifiques et techniques",
                "74 Autres activités spécialisées, scientifiques et techniques"));
        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES_3, "1",
                "75 Activités vétérinaires",
                "75 Activités vétérinaires",
                "75 Activités vétérinaires"));
        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES_3, "1",
                "77 Activités de location et location bail",
                "77 Activités de location et location bail",
                "77 Activités de location et location bail"));
        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES_3, "1",
                "78 Activités liées à l'emploi",
                "78 Activités liées à l'emploi",
                "78 Activités liées à l'emploi"));
        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES_3, "1",
                "79 Activités des agences de voyage, voyagistes, services de réservation et activités connexes",
                "79 Activités des agences de voyage, voyagistes, services de réservation et activités connexes",
                "79 Activités des agences de voyage, voyagistes, services de réservation et activités connexes"));
        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES_3, "1",
                "80 Enquêtes et sécurité",
                "80 Enquêtes et sécurité",
                "80 Enquêtes et sécurité"));
        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES_3, "1",
                "81 Services relatifs aux bâtiments, aménagement paysager",
                "81 Services relatifs aux bâtiments, aménagement paysager",
                "81 Services relatifs aux bâtiments, aménagement paysager"));
        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES_3, "1",
                "82 Services administratifs de bureau et autres activités de soutien aux entreprises",
                "82 Services administratifs de bureau et autres activités de soutien aux entreprises",
                "82 Services administratifs de bureau et autres activités de soutien aux entreprises"));
        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES_3, "1",
                "84 Administration publique et défense, M5sécurité sociale obligatoire",
                "84 Administration publique et défense, M5sécurité sociale obligatoire",
                "84 Administration publique et défense, M5sécurité sociale obligatoire"));
        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES_3, "1",
                "85 Enseignement",
                "85 Enseignement",
                "85 Enseignement"));
        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES_3, "1",
                "86 Activités pour la santé humaine",
                "86 Activités pour la santé humaine",
                "86 Activités pour la santé humaine"));
        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES_3, "1",
                "87 Activités médico sociales et sociales avec hébergement",
                "87 Activités médico sociales et sociales avec hébergement",
                "87 Activités médico sociales et sociales avec hébergement"));
        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES_3, "1",
                "88 Action sociale sans hébergement",
                "88 Action sociale sans hébergement",
                "88 Action sociale sans hébergement"));
        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES_3, "1",
                "90 Activités créatives, artistiques et de spectacle",
                "90 Activités créatives, artistiques et de spectacle",
                "90 Activités créatives, artistiques et de spectacle"));
        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES_3, "1",
                "91 Bibliothèques, archives, musées et autres activités culturelles",
                "91 Bibliothèques, archives, musées et autres activités culturelles",
                "91 Bibliothèques, archives, musées et autres activités culturelles"));
        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES_3, "1",
                "92 Organisation de jeux de hasard et d'argent",
                "92 Organisation de jeux de hasard et d'argent",
                "92 Organisation de jeux de hasard et d'argent"));
        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES_3, "1",
                "93 Activités sportives, récréatives et de loisirs",
                "93 Activités sportives, récréatives et de loisirs",
                "93 Activités sportives, récréatives et de loisirs"));
        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES_3, "1",
                "94 Activités des organisations associatives",
                "94 Activités des organisations associatives",
                "94 Activités des organisations associatives"));
        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES_3, "1",
                "95 Réparation d'ordinateurs et de biens personnels et domestiques",
                "95 Réparation d'ordinateurs et de biens personnels et domestiques",
                "95 Réparation d'ordinateurs et de biens personnels et domestiques"));
        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES_3, "1",
                "96 Autres services personnels",
                "96 Autres services personnels",
                "96 Autres services personnels"));
        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES_3, "1",
                "97 Activités des ménages en tant qu'employeurs de personnel domestique",
                "97 Activités des ménages en tant qu'employeurs de personnel domestique",
                "97 Activités des ménages en tant qu'employeurs de personnel domestique"));
        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES_3, "1",
                "98 Activités indifférenciées des ménages en tant que producteurs de biens et services pour usage propre",
                "98 Activités indifférenciées des ménages en tant que producteurs de biens et services pour usage propre",
                "98 Activités indifférenciées des ménages en tant que producteurs de biens et services pour usage propre"));
        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES_3, "1",
                "99 Activités des organisations et organismes extraterritoriaux",
                "99 Activités des organisations et organismes extraterritoriaux",
                "99 Activités des organisations et organismes extraterritoriaux"));

        session.saveOrUpdate(new CodeLabel(CodeList.PUBLIC_OR_PRIVATE, "1", "Public", "Public", "Public"));
        session.saveOrUpdate(new CodeLabel(CodeList.PUBLIC_OR_PRIVATE, "2", "Privé", "Privé", "Privé"));

        CodeList codeList = CodeList.FUEL;
        session.saveOrUpdate(new CodeLabel(codeList, "1", "Diesel, gasoil ou fuel léger", "Diesel, gasoil ou fuel léger", "Diesel, gasoil ou fuel léger"));
        session.saveOrUpdate(new CodeLabel(codeList, "2", "Gaz de Pétrole Liquefié (GPL)", "Gaz de Pétrole Liquefié (GPL)", "Gaz de Pétrole Liquefié (GPL)"));
        session.saveOrUpdate(new CodeLabel(codeList, "3", "Gaz naturel (pauvre)", "Gaz naturel (pauvre)", "Gaz naturel (pauvre)"));
        session.saveOrUpdate(new CodeLabel(codeList, "4", "Essence", "Essence", "Essence"));
        session.saveOrUpdate(new CodeLabel(codeList, "5", "Biodiesel", "Biodiesel", "Biodiesel"));
        session.saveOrUpdate(new CodeLabel(codeList, "6", "Bioethanol", "Bioethanol", "Bioethanol"));
        session.saveOrUpdate(new CodeLabel(codeList, "7", "Biogaz", "Biogaz", "Biogaz"));
        session.saveOrUpdate(new CodeLabel(codeList, "8", "Gaz de décharge", "Gaz de décharge", "Gaz de décharge"));
        session.saveOrUpdate(new CodeLabel(codeList, "9", "Charbon", "Charbon", "Charbon"));
        session.saveOrUpdate(new CodeLabel(codeList, "10", "Bois (bûche)", "Bois (bûche)", "Bois (bûche)"));
        session.saveOrUpdate(new CodeLabel(codeList, "11", "Bois (copeaux)", "Bois (copeaux)", "Bois (copeaux)"));
        session.saveOrUpdate(new CodeLabel(codeList, "12", "Bois (pellets)", "Bois (pellets)", "Bois (pellets)"));
        session.saveOrUpdate(new CodeLabel(codeList, "13", "Herbe/paille", "Herbe/paille", "Herbe/paille"));
        session.saveOrUpdate(new CodeLabel(codeList, "14", "Propane", "Propane", "Propane"));
        session.saveOrUpdate(new CodeLabel(codeList, "15", "Butane", "Butane", "Butane"));
        session.saveOrUpdate(new CodeLabel(codeList, "16", "Aggloméré de charbon ou de lignite", "Aggloméré de charbon ou de lignite", "Aggloméré de charbon ou de lignite"));
        session.saveOrUpdate(new CodeLabel(codeList, "17", "Anthracite", "Anthracite", "Anthracite"));
        session.saveOrUpdate(new CodeLabel(codeList, "18", "Biogaz", "Biogaz", "Biogaz"));
        session.saveOrUpdate(new CodeLabel(codeList, "19", "Autres produits pétroliers (paraffines, cires, …)", "Autres produits pétroliers (paraffines, cires, …)", "Autres produits pétroliers (paraffines, cires, …)"));
        session.saveOrUpdate(new CodeLabel(codeList, "20", "Bitumes", "Bitumes", "Bitumes"));
        session.saveOrUpdate(new CodeLabel(codeList, "21", "Briquette de lignite", "Briquette de lignite", "Briquette de lignite"));
        session.saveOrUpdate(new CodeLabel(codeList, "22", "Coke", "Coke", "Coke"));
        session.saveOrUpdate(new CodeLabel(codeList, "23", "Coke de pétrole", "Coke de pétrole", "Coke de pétrole"));
        session.saveOrUpdate(new CodeLabel(codeList, "24", "Fuel lourd", "Fuel lourd", "Fuel lourd"));
        session.saveOrUpdate(new CodeLabel(codeList, "25", "Gaz de cokerie", "Gaz de cokerie", "Gaz de cokerie"));
        session.saveOrUpdate(new CodeLabel(codeList, "26", "Gaz de haut fourneau", "Gaz de haut fourneau", "Gaz de haut fourneau"));
        session.saveOrUpdate(new CodeLabel(codeList, "27", "Gaz naturel (riche)", "Gaz naturel (riche)", "Gaz naturel (riche)"));
        session.saveOrUpdate(new CodeLabel(codeList, "28", "Goudron", "Goudron", "Goudron"));
        session.saveOrUpdate(new CodeLabel(codeList, "29", "Graisses", "Graisses", "Graisses"));
        session.saveOrUpdate(new CodeLabel(codeList, "30", "Huiles", "Huiles", "Huiles"));
        session.saveOrUpdate(new CodeLabel(codeList, "31", "Kérosène", "Kérosène", "Kérosène"));
        session.saveOrUpdate(new CodeLabel(codeList, "32", "Lignite", "Lignite", "Lignite"));
        session.saveOrUpdate(new CodeLabel(codeList, "33", "Méthane", "Méthane", "Méthane"));
        session.saveOrUpdate(new CodeLabel(codeList, "34", "Naphta", "Naphta", "Naphta"));
        session.saveOrUpdate(new CodeLabel(codeList, "35", "Pétrole brut", "Pétrole brut", "Pétrole brut"));
        session.saveOrUpdate(new CodeLabel(codeList, "36", "Pétrole lampant", "Pétrole lampant", "Pétrole lampant"));
        session.saveOrUpdate(new CodeLabel(codeList, "37", "Terril", "Terril", "Terril"));
        session.saveOrUpdate(new CodeLabel(codeList, "38", "Tourbe", "Tourbe", "Tourbe"));


        // -- QUESTION_SETS
        QuestionSet a1 = new QuestionSet(QuestionCode.A1, false);
        session.saveOrUpdate(a1);
        tab1Form.getQuestionSet().add(a1);
        session.saveOrUpdate(tab1Form);


        QuestionSet a13 = new QuestionSet(QuestionCode.A13, false);
        session.saveOrUpdate(a13);
        tab2Form.getQuestionSet().add(a13);
        session.saveOrUpdate(tab2Form);

        QuestionSet a15 = new QuestionSet(QuestionCode.A15, true);
        session.saveOrUpdate(a15);
        tab2Form.getQuestionSet().add(a15);
        session.saveOrUpdate(tab2Form);

        QuestionSet a20 = new QuestionSet(QuestionCode.A20, false);
        session.saveOrUpdate(a20);
        tab2Form.getQuestionSet().add(a20);
        session.saveOrUpdate(tab2Form);

        QuestionSet a22 = new QuestionSet(QuestionCode.A22, false);
        session.saveOrUpdate(a22);
        tab2Form.getQuestionSet().add(a22);
        session.saveOrUpdate(tab2Form);

        QuestionSet a25 = new QuestionSet(QuestionCode.A25, false);
        session.saveOrUpdate(a25);
        tab2Form.getQuestionSet().add(a25);
        session.saveOrUpdate(tab2Form);

        QuestionSet a31 = new QuestionSet(QuestionCode.A31, false);
        session.saveOrUpdate(a31);
        tab2Form.getQuestionSet().add(a31);
        session.saveOrUpdate(tab2Form);

        QuestionSet a41 = new QuestionSet(QuestionCode.A41, false);
        session.saveOrUpdate(a41);
        tab2Form.getQuestionSet().add(a41);
        session.saveOrUpdate(tab2Form);

        QuestionSet a42 = new QuestionSet(QuestionCode.A42, false);
        session.saveOrUpdate(a42);
        tab2Form.getQuestionSet().add(a42);
        session.saveOrUpdate(tab2Form);

        QuestionSet a45 = new QuestionSet(QuestionCode.A45, false);
        session.saveOrUpdate(a45);
        tab2Form.getQuestionSet().add(a45);
        session.saveOrUpdate(tab2Form);

        QuestionSet a47 = new QuestionSet(QuestionCode.A47, false);
        session.saveOrUpdate(a47);
        tab2Form.getQuestionSet().add(a47);
        session.saveOrUpdate(tab2Form);
        
        
        // -- QUESTIONS

        session.saveOrUpdate(new IntegerQuestion(a1, 0, QuestionCode.A2, null));
        session.saveOrUpdate(new ValueSelectionQuestion(a1, 100, QuestionCode.A3, CodeList.SITE_SECTORS));
        session.saveOrUpdate(new ValueSelectionQuestion(a1, 200, QuestionCode.A4, CodeList.NACE_CODES_1));
        session.saveOrUpdate(new ValueSelectionQuestion(a1, 300, QuestionCode.A5, CodeList.NACE_CODES_2));
        session.saveOrUpdate(new ValueSelectionQuestion(a1, 400, QuestionCode.A6, CodeList.NACE_CODES_3));
        session.saveOrUpdate(new BooleanQuestion(a1, 500, QuestionCode.A7));
        session.saveOrUpdate(new ValueSelectionQuestion(a1, 600, QuestionCode.A8, CodeList.PUBLIC_OR_PRIVATE));
        session.saveOrUpdate(new DoubleQuestion(a1, 700, QuestionCode.A9, surfaceUnits));
        session.saveOrUpdate(new DoubleQuestion(a1, 800, QuestionCode.A10, surfaceUnits));
        session.saveOrUpdate(new BooleanQuestion(a1, 900, QuestionCode.A11));
        session.saveOrUpdate(new IntegerQuestion(a1, 1000, QuestionCode.A12, null));

        // TAB 2

        session.saveOrUpdate(new IntegerQuestion(a13, 0, QuestionCode.A14, null));
        session.saveOrUpdate(new IntegerQuestion(a13, 100, QuestionCode.A15, null));
        session.saveOrUpdate(new IntegerQuestion(a13, 200, QuestionCode.A16, null));
        session.saveOrUpdate(new IntegerQuestion(a13, 300, QuestionCode.A17, null));
        // 18
        // 19
        session.saveOrUpdate(new IntegerQuestion(a13, 300, QuestionCode.A21, null));
        session.saveOrUpdate(new IntegerQuestion(a13, 300, QuestionCode.A23, null));
        session.saveOrUpdate(new IntegerQuestion(a13, 300, QuestionCode.A24, null));



    }
}













