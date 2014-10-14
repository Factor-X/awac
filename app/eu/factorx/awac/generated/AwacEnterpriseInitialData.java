package eu.factorx.awac.generated;

import eu.factorx.awac.models.data.answer.QuestionSetAnswer;
import eu.factorx.awac.models.data.answer.QuestionAnswer;
import eu.factorx.awac.service.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import play.Logger;
import eu.factorx.awac.models.Notification;
import eu.factorx.awac.models.NotificationKind;
import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.code.type.PeriodCode;
import eu.factorx.awac.models.code.type.QuestionCode;
import eu.factorx.awac.models.code.type.UnitCategoryCode;
import eu.factorx.awac.models.data.question.Question;
import eu.factorx.awac.models.data.question.QuestionSet;
import eu.factorx.awac.models.data.question.type.*;
import eu.factorx.awac.models.forms.Form;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.models.knowledge.Unit;
import eu.factorx.awac.models.knowledge.UnitCategory;
import eu.factorx.awac.util.data.importer.*;
import play.db.jpa.JPA;

import java.util.*;

@Component
public class AwacEnterpriseInitialData {

    @Autowired
    private UnitCategoryService unitCategoryService;

    @Autowired
    private UnitService unitService;

    @Autowired
    private FormService formService;

    @Autowired
    private QuestionSetService questionSetService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionAnswerService questionAnswerService;

	@Autowired
    private QuestionSetAnswerService questionSetAnswerService;

	private void deleteQuestion(Question question, int indent) {
		System.out.println(StringUtils.repeat(' ', indent * 4) + "Deleting question " + question.getCode().getKey());

		List<QuestionAnswer> questionAnswers = questionAnswerService.findByCodes(Arrays.asList(question.getCode()));
		for (QuestionAnswer questionAnswer : questionAnswers) {
			System.out.println(StringUtils.repeat(' ', indent * 4) + "    deleting QuestionAnswer #" + questionAnswer.getId());
			JPA.em().remove(questionAnswer);
		}

		System.out.println(StringUtils.repeat(' ', indent * 4) + "    finally deleting question " + question.getCode().getKey());
		QuestionSet questionSet = question.getQuestionSet();
		questionSet.getQuestions().remove(question);
		JPA.em().persist(questionSet);
        JPA.em().remove(question);
	}

	private void deleteQuestionSet(QuestionSet qs, int indent) {
		QuestionSet secured = questionSetService.findByCode(qs.getCode());
		if (secured != null) {

			System.out.println(StringUtils.repeat(' ', indent * 4) + "Deleting QuestionSet " + secured.getCode().getKey());


			for (Question question : new ArrayList<>(secured.getQuestions())) {
				deleteQuestion(question, indent + 1);
			}

			for (QuestionSet questionSet : new ArrayList<>(secured.getChildren())) {
				deleteQuestionSet(questionSet, indent + 1);
			}

			List<QuestionSetAnswer> questionSetAnswers = questionSetAnswerService.findByCodes(Arrays.asList(secured.getCode()));
			for (QuestionSetAnswer questionSetAnswer : questionSetAnswers) {
				System.out.println(StringUtils.repeat(' ', indent * 4) + "    deleting QuestionSetAnswer #" + questionSetAnswer.getId());
                JPA.em().remove(questionSetAnswer);
			}

			if (secured.getParent() == null) {
				for (Form form : formService.findAll()) {
					List<QuestionSet> questionSets = form.getQuestionSets();
					for (QuestionSet questionSet : new ArrayList<>(questionSets)) {
						if (questionSet.getCode().equals(secured.getCode())) {
							System.out.println(StringUtils.repeat(' ', indent * 4) + "    removing from form " + form.getIdentifier());
							questionSets.remove(questionSet);
						}
					}
					JPA.em().persist(form);
				}
			}

			System.out.println(StringUtils.repeat(' ', indent * 4) + "    finally deleting QuestionSet " + secured.getCode().getKey());

			QuestionSet questionSet = secured.getParent();
			if (questionSet != null) {
				questionSet.getChildren().remove(secured);
				JPA.em().persist(questionSet);
			}

            JPA.em().remove(secured);
		} else {
			System.out.println(StringUtils.repeat(' ', indent * 4) + "Already deleted question_set " + qs.getCode().getKey());
		}
	}

    public void createOrUpdateSurvey() {

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


        // delete old questions
		{
			List<Question> allQuestions = questionService.findAll();
            List<String> codes = Arrays.asList("A2", "A3", "A4", "A5", "A6", "A7", "A9", "A10", "A11", "A12", "A14", "A16", "A17", "A1001", "A1002", "A1004", "A1005", "A21", "A23", "A24", "A26", "A27", "A28", "A32", "A33", "A35", "A36", "A38", "A39", "A43", "A44", "A46", "A48", "A49", "A51", "A403", "A404", "A405", "A408", "A409", "A410", "A411", "A414", "A415", "A416", "A417", "A503", "A504", "A505", "A508", "A509", "A510", "A511", "A514", "A515", "A516", "A517", "A603", "A604", "A605", "A608", "A609", "A610", "A611", "A614", "A615", "A616", "A617", "A95", "A96", "A97", "A98", "A99", "A100", "A101", "A102", "A103", "A104", "A105", "A106", "A107", "A108", "A110", "A111", "A112", "A116", "A117", "A118", "A119", "A120", "A122", "A123", "A124", "A125", "A126", "A127", "A206", "A210", "A211", "A212", "A213", "A214", "A215", "A216", "A217", "A218", "A219", "A220", "A221", "A222", "A225", "A226", "A227", "A228", "A129", "A133", "A134", "A135", "A136", "A137", "A138", "A139", "A500", "A143", "A145", "A146", "A147", "A148", "A149", "A150", "A151", "A152", "A153", "A154", "A155", "A156", "A158", "A159", "A160", "A161", "A162", "A165", "A167", "A168", "A1007", "A1008", "A1010", "A1011", "A169", "A171", "A172", "A174", "A5001", "A5002", "A5003", "A183", "A184", "A186", "A187", "A189", "A190", "A192", "A193", "A195", "A198", "A199", "A200", "A501", "A202", "A203", "A204", "A230", "A232", "A233", "A234", "A235", "A236", "A239", "A240", "A241", "A242", "A310", "A312", "A314", "A315", "A1013", "A1014", "A1016", "A1017", "A316", "A318", "A319", "A321", "A323", "A324", "A326", "A327", "A1019", "A1020", "A1022", "A1023", "A328", "A330", "A331", "A333", "A335", "A336", "A337", "A338", "A245", "A246", "A247", "A248", "A249", "A251", "A254", "A255", "A256", "A257", "A258", "A259", "A260", "A261", "A262", "A263", "A264", "A265", "A267", "A268", "A269", "A270", "A271", "A274", "A276", "A277", "A1025", "A1026", "A1028", "A1029", "A278", "A280", "A281", "A283", "A285", "A286", "A1031", "A1032", "A1034", "A1035", "A287", "A289", "A290", "A292", "A293", "A294", "A295", "A296", "A298", "A299", "A301", "A302", "A5011", "A5012", "A5013", "A5014");

			for (Question q : new ArrayList<>(allQuestions)) {
				if (codes.contains(q.getCode().getKey()) || !q.getCode().getKey().matches("A[0-9]+")) {
					allQuestions.remove(q);
				}
			}
			for (Question q : allQuestions) {
				deleteQuestion(q, 0);
			}
		}


		// delete old question_sets
		{
			List<QuestionSet> allQuestionSets = questionSetService.findAll();
            List<String> codes = Arrays.asList("A1", "A13", "A15", "A1000", "A1003", "A20", "A22", "A25", "A31", "A34", "A37", "A40", "A41", "A42", "A45", "A47", "A50", "A52", "A400", "A401", "A402", "A406", "A407", "A412", "A413", "A518", "A519", "A502", "A506", "A507", "A512", "A513", "A600", "A601", "A602", "A606", "A607", "A612", "A613", "A93", "A94", "A109", "A113", "A114", "A115", "A121", "A205", "A208", "A209", "A223", "A224", "A128", "A130", "A131", "A132", "A140", "A141", "A142", "A157", "A163", "A164", "A166", "A1006", "A1009", "A170", "A173", "A4999", "A5000", "A180", "A181", "A182", "A185", "A188", "A191", "A194", "A196", "A197", "A201", "A229", "A231", "A237", "A238", "A309", "A311", "A313", "A1012", "A1015", "A317", "A320", "A322", "A325", "A1018", "A1021", "A329", "A332", "A334", "A243", "A244", "A8000", "A250", "A252", "A253", "A266", "A272", "A273", "A275", "A1024", "A1027", "A279", "A282", "A284", "A1030", "A1033", "A288", "A291", "A297", "A300", "A5010");

			for (QuestionSet qs : new ArrayList<>(allQuestionSets)) {
				if (codes.contains(qs.getCode().getKey()) || !qs.getCode().getKey().matches("A[0-9]+")) {
					allQuestionSets.remove(qs);
				}
			}
			for (QuestionSet qs : allQuestionSets) {
				deleteQuestionSet(qs, 0);
			}
		}


    // =========================================================================
    // FORMS
    // =========================================================================

    // == TAB2
    // DESCRIPTION, CONSOMMATION & REJETS DU  SITE
    Form form2 = formService.findByIdentifier("TAB2");
    if (form2 == null) {
        form2 = new Form("TAB2");
        JPA.em().persist(form2);
    }
    // == TAB3
    // MOBILITE
    Form form3 = formService.findByIdentifier("TAB3");
    if (form3 == null) {
        form3 = new Form("TAB3");
        JPA.em().persist(form3);
    }
    // == TAB4
    // ACHATS, TRANSPORT ET DISTRIBUTION AMONT
    Form form4 = formService.findByIdentifier("TAB4");
    if (form4 == null) {
        form4 = new Form("TAB4");
        JPA.em().persist(form4);
    }
    // == TAB5
    // DECHETS
    Form form5 = formService.findByIdentifier("TAB5");
    if (form5 == null) {
        form5 = new Form("TAB5");
        JPA.em().persist(form5);
    }
    // == TAB6
    // BIENS D'EQUIPEMENT, ACTIFS LOUES EN AVAL, FRANCHISES, EMISSIONS FINANCEES
    Form form6 = formService.findByIdentifier("TAB6");
    if (form6 == null) {
        form6 = new Form("TAB6");
        JPA.em().persist(form6);
    }
    // == TAB7
    // PRODUITS VENDUS
    Form form7 = formService.findByIdentifier("TAB7");
    if (form7 == null) {
        form7 = new Form("TAB7");
        JPA.em().persist(form7);
    }


    // =========================================================================
    // QUESTION SETS
    // =========================================================================

    // == A1
    // Données générales
    QuestionSet a1 = questionSetService.findByCode(QuestionCode.A1);
    if( a1 == null ) {
        a1 = new QuestionSet(QuestionCode.A1, false, null);
        JPA.em().persist(a1);
    }
    form2.getQuestionSets().add(a1);
    JPA.em().persist(form2);
    // == A13
    // Consommation de combustibles
    QuestionSet a13 = questionSetService.findByCode(QuestionCode.A13);
    if( a13 == null ) {
        a13 = new QuestionSet(QuestionCode.A13, false, a1);
        JPA.em().persist(a13);
    }
    // == A15
    // Combustion de combustible par les sources statiques des sites de l'entreprise (mesurés en unités d'énergie)
    QuestionSet a15 = questionSetService.findByCode(QuestionCode.A15);
    if( a15 == null ) {
        a15 = new QuestionSet(QuestionCode.A15, true, a13);
        JPA.em().persist(a15);
    }
    // == A1000
    // Combustion de combustible par les sources statiques des sites de l'entreprise (mesurés en volume)
    QuestionSet a1000 = questionSetService.findByCode(QuestionCode.A1000);
    if( a1000 == null ) {
        a1000 = new QuestionSet(QuestionCode.A1000, true, a13);
        JPA.em().persist(a1000);
    }
    // == A1003
    // Combustion de combustible par les sources statiques des sites de l'entreprise (mesurés en poids)
    QuestionSet a1003 = questionSetService.findByCode(QuestionCode.A1003);
    if( a1003 == null ) {
        a1003 = new QuestionSet(QuestionCode.A1003, true, a13);
        JPA.em().persist(a1003);
    }
    // == A20
    // Electricité et vapeur achetées
    QuestionSet a20 = questionSetService.findByCode(QuestionCode.A20);
    if( a20 == null ) {
        a20 = new QuestionSet(QuestionCode.A20, false, null);
        JPA.em().persist(a20);
    }
    form2.getQuestionSets().add(a20);
    JPA.em().persist(form2);
    // == A22
    // Electricité
    QuestionSet a22 = questionSetService.findByCode(QuestionCode.A22);
    if( a22 == null ) {
        a22 = new QuestionSet(QuestionCode.A22, false, a20);
        JPA.em().persist(a22);
    }
    // == A25
    // Vapeur
    QuestionSet a25 = questionSetService.findByCode(QuestionCode.A25);
    if( a25 == null ) {
        a25 = new QuestionSet(QuestionCode.A25, true, a20);
        JPA.em().persist(a25);
    }
    // == A31
    // GES des processus de production
    QuestionSet a31 = questionSetService.findByCode(QuestionCode.A31);
    if( a31 == null ) {
        a31 = new QuestionSet(QuestionCode.A31, false, null);
        JPA.em().persist(a31);
    }
    form2.getQuestionSets().add(a31);
    JPA.em().persist(form2);
    // == A34
    // Type de GES émis par la production
    QuestionSet a34 = questionSetService.findByCode(QuestionCode.A34);
    if( a34 == null ) {
        a34 = new QuestionSet(QuestionCode.A34, true, a31);
        JPA.em().persist(a34);
    }
    // == A37
    // Systèmes de refroidissement
    QuestionSet a37 = questionSetService.findByCode(QuestionCode.A37);
    if( a37 == null ) {
        a37 = new QuestionSet(QuestionCode.A37, false, null);
        JPA.em().persist(a37);
    }
    form2.getQuestionSets().add(a37);
    JPA.em().persist(form2);
    // == A40
    // Méthodes au choix
    QuestionSet a40 = questionSetService.findByCode(QuestionCode.A40);
    if( a40 == null ) {
        a40 = new QuestionSet(QuestionCode.A40, false, a37);
        JPA.em().persist(a40);
    }
    // == A41
    // Estimation des émissions à partir des recharges de gaz
    QuestionSet a41 = questionSetService.findByCode(QuestionCode.A41);
    if( a41 == null ) {
        a41 = new QuestionSet(QuestionCode.A41, false, a40);
        JPA.em().persist(a41);
    }
    // == A42
    // Listes des types de gaz réfrigérants utilisés
    QuestionSet a42 = questionSetService.findByCode(QuestionCode.A42);
    if( a42 == null ) {
        a42 = new QuestionSet(QuestionCode.A42, true, a41);
        JPA.em().persist(a42);
    }
    // == A45
    // Estimation des émissions à partir de la puissance du groupe de froid
    QuestionSet a45 = questionSetService.findByCode(QuestionCode.A45);
    if( a45 == null ) {
        a45 = new QuestionSet(QuestionCode.A45, false, a40);
        JPA.em().persist(a45);
    }
    // == A47
    // Estimation des émissions à partir de la consommation électrique du site
    QuestionSet a47 = questionSetService.findByCode(QuestionCode.A47);
    if( a47 == null ) {
        a47 = new QuestionSet(QuestionCode.A47, false, a40);
        JPA.em().persist(a47);
    }
    // == A50
    // Mobilité
    QuestionSet a50 = questionSetService.findByCode(QuestionCode.A50);
    if( a50 == null ) {
        a50 = new QuestionSet(QuestionCode.A50, false, null);
        JPA.em().persist(a50);
    }
    form3.getQuestionSets().add(a50);
    JPA.em().persist(form3);
    // == A52
    // Transport routier
    QuestionSet a52 = questionSetService.findByCode(QuestionCode.A52);
    if( a52 == null ) {
        a52 = new QuestionSet(QuestionCode.A52, false, null);
        JPA.em().persist(a52);
    }
    form3.getQuestionSets().add(a52);
    JPA.em().persist(form3);
    // == A400
    // Véhicules de société ou détenus par l'entreprise
    QuestionSet a400 = questionSetService.findByCode(QuestionCode.A400);
    if( a400 == null ) {
        a400 = new QuestionSet(QuestionCode.A400, false, a52);
        JPA.em().persist(a400);
    }
    // == A401
    // Méthode au choix
    QuestionSet a401 = questionSetService.findByCode(QuestionCode.A401);
    if( a401 == null ) {
        a401 = new QuestionSet(QuestionCode.A401, false, a400);
        JPA.em().persist(a401);
    }
    // == A402
    // Calcul par les consommations
    QuestionSet a402 = questionSetService.findByCode(QuestionCode.A402);
    if( a402 == null ) {
        a402 = new QuestionSet(QuestionCode.A402, false, a401);
        JPA.em().persist(a402);
    }
    // == A406
    // Calcul par les kilomètres
    QuestionSet a406 = questionSetService.findByCode(QuestionCode.A406);
    if( a406 == null ) {
        a406 = new QuestionSet(QuestionCode.A406, false, a401);
        JPA.em().persist(a406);
    }
    // == A407
    // Créez autant de catégories de véhicules que souhaité
    QuestionSet a407 = questionSetService.findByCode(QuestionCode.A407);
    if( a407 == null ) {
        a407 = new QuestionSet(QuestionCode.A407, true, a406);
        JPA.em().persist(a407);
    }
    // == A412
    // Calcul par euros dépensés
    QuestionSet a412 = questionSetService.findByCode(QuestionCode.A412);
    if( a412 == null ) {
        a412 = new QuestionSet(QuestionCode.A412, false, a401);
        JPA.em().persist(a412);
    }
    // == A413
    // Créez autant de catégories de véhicules que souhaité
    QuestionSet a413 = questionSetService.findByCode(QuestionCode.A413);
    if( a413 == null ) {
        a413 = new QuestionSet(QuestionCode.A413, true, a412);
        JPA.em().persist(a413);
    }
    // == A518
    // Autres véhicules pour déplacements domicile-travail des employés
    QuestionSet a518 = questionSetService.findByCode(QuestionCode.A518);
    if( a518 == null ) {
        a518 = new QuestionSet(QuestionCode.A518, false, a52);
        JPA.em().persist(a518);
    }
    // == A519
    // Méthode au choix
    QuestionSet a519 = questionSetService.findByCode(QuestionCode.A519);
    if( a519 == null ) {
        a519 = new QuestionSet(QuestionCode.A519, false, a518);
        JPA.em().persist(a519);
    }
    // == A502
    // Calcul par les consommations
    QuestionSet a502 = questionSetService.findByCode(QuestionCode.A502);
    if( a502 == null ) {
        a502 = new QuestionSet(QuestionCode.A502, false, a519);
        JPA.em().persist(a502);
    }
    // == A506
    // Calcul par les kilomètres
    QuestionSet a506 = questionSetService.findByCode(QuestionCode.A506);
    if( a506 == null ) {
        a506 = new QuestionSet(QuestionCode.A506, false, a519);
        JPA.em().persist(a506);
    }
    // == A507
    // Créez autant de catégories de véhicules que souhaité
    QuestionSet a507 = questionSetService.findByCode(QuestionCode.A507);
    if( a507 == null ) {
        a507 = new QuestionSet(QuestionCode.A507, true, a506);
        JPA.em().persist(a507);
    }
    // == A512
    // Calcul par euros dépensés
    QuestionSet a512 = questionSetService.findByCode(QuestionCode.A512);
    if( a512 == null ) {
        a512 = new QuestionSet(QuestionCode.A512, false, a519);
        JPA.em().persist(a512);
    }
    // == A513
    // Créez autant de catégories de véhicules que souhaité
    QuestionSet a513 = questionSetService.findByCode(QuestionCode.A513);
    if( a513 == null ) {
        a513 = new QuestionSet(QuestionCode.A513, true, a512);
        JPA.em().persist(a513);
    }
    // == A600
    // Autres véhicules pour déplacements divers (véhicules loués, visiteurs, consultants, sous-traitants…)
    QuestionSet a600 = questionSetService.findByCode(QuestionCode.A600);
    if( a600 == null ) {
        a600 = new QuestionSet(QuestionCode.A600, false, a52);
        JPA.em().persist(a600);
    }
    // == A601
    // Méthode au choix
    QuestionSet a601 = questionSetService.findByCode(QuestionCode.A601);
    if( a601 == null ) {
        a601 = new QuestionSet(QuestionCode.A601, false, a600);
        JPA.em().persist(a601);
    }
    // == A602
    // Calcul par les consommations
    QuestionSet a602 = questionSetService.findByCode(QuestionCode.A602);
    if( a602 == null ) {
        a602 = new QuestionSet(QuestionCode.A602, false, a601);
        JPA.em().persist(a602);
    }
    // == A606
    // Calcul par les kilomètres
    QuestionSet a606 = questionSetService.findByCode(QuestionCode.A606);
    if( a606 == null ) {
        a606 = new QuestionSet(QuestionCode.A606, false, a601);
        JPA.em().persist(a606);
    }
    // == A607
    // Créez autant de catégories de véhicules que souhaité
    QuestionSet a607 = questionSetService.findByCode(QuestionCode.A607);
    if( a607 == null ) {
        a607 = new QuestionSet(QuestionCode.A607, true, a606);
        JPA.em().persist(a607);
    }
    // == A612
    // Calcul par euros dépensés
    QuestionSet a612 = questionSetService.findByCode(QuestionCode.A612);
    if( a612 == null ) {
        a612 = new QuestionSet(QuestionCode.A612, false, a601);
        JPA.em().persist(a612);
    }
    // == A613
    // Créez autant de catégories de véhicules que souhaité
    QuestionSet a613 = questionSetService.findByCode(QuestionCode.A613);
    if( a613 == null ) {
        a613 = new QuestionSet(QuestionCode.A613, true, a612);
        JPA.em().persist(a613);
    }
    // == A93
    // Transport en commun
    QuestionSet a93 = questionSetService.findByCode(QuestionCode.A93);
    if( a93 == null ) {
        a93 = new QuestionSet(QuestionCode.A93, false, null);
        JPA.em().persist(a93);
    }
    form3.getQuestionSets().add(a93);
    JPA.em().persist(form3);
    // == A94
    // Estimation par le détail des déplacements
    QuestionSet a94 = questionSetService.findByCode(QuestionCode.A94);
    if( a94 == null ) {
        a94 = new QuestionSet(QuestionCode.A94, false, a93);
        JPA.em().persist(a94);
    }
    // == A109
    // Estimation par nombre d'employés
    QuestionSet a109 = questionSetService.findByCode(QuestionCode.A109);
    if( a109 == null ) {
        a109 = new QuestionSet(QuestionCode.A109, false, a93);
        JPA.em().persist(a109);
    }
    // == A113
    // Transport en avion (déplacements professionnels ou des visiteurs)
    QuestionSet a113 = questionSetService.findByCode(QuestionCode.A113);
    if( a113 == null ) {
        a113 = new QuestionSet(QuestionCode.A113, false, null);
        JPA.em().persist(a113);
    }
    form3.getQuestionSets().add(a113);
    JPA.em().persist(form3);
    // == A114
    // Méthode par le détail des vols
    QuestionSet a114 = questionSetService.findByCode(QuestionCode.A114);
    if( a114 == null ) {
        a114 = new QuestionSet(QuestionCode.A114, false, a113);
        JPA.em().persist(a114);
    }
    // == A115
    // Créez autant de catégories de vol que nécessaire
    QuestionSet a115 = questionSetService.findByCode(QuestionCode.A115);
    if( a115 == null ) {
        a115 = new QuestionSet(QuestionCode.A115, true, a114);
        JPA.em().persist(a115);
    }
    // == A121
    // Méthode des moyennes
    QuestionSet a121 = questionSetService.findByCode(QuestionCode.A121);
    if( a121 == null ) {
        a121 = new QuestionSet(QuestionCode.A121, false, a113);
        JPA.em().persist(a121);
    }
    // == A205
    // Achat de biens et services
    QuestionSet a205 = questionSetService.findByCode(QuestionCode.A205);
    if( a205 == null ) {
        a205 = new QuestionSet(QuestionCode.A205, false, null);
        JPA.em().persist(a205);
    }
    form4.getQuestionSets().add(a205);
    JPA.em().persist(form4);
    // == A208
    // Méthode par détail des achats
    QuestionSet a208 = questionSetService.findByCode(QuestionCode.A208);
    if( a208 == null ) {
        a208 = new QuestionSet(QuestionCode.A208, false, null);
        JPA.em().persist(a208);
    }
    form4.getQuestionSets().add(a208);
    JPA.em().persist(form4);
    // == A209
    // Créez et nommez vos postes d'achats (et préciser la catégorie et le type de matériaux ensuite)
    QuestionSet a209 = questionSetService.findByCode(QuestionCode.A209);
    if( a209 == null ) {
        a209 = new QuestionSet(QuestionCode.A209, true, a208);
        JPA.em().persist(a209);
    }
    // == A223
    // Autres matériaux spécifiques pour lesquels l'entreprise dispose du facteur d'émissions cradle-to-gate
    QuestionSet a223 = questionSetService.findByCode(QuestionCode.A223);
    if( a223 == null ) {
        a223 = new QuestionSet(QuestionCode.A223, false, a208);
        JPA.em().persist(a223);
    }
    // == A224
    // Créez et nommez vos postes d'achats spécifiques (et précisez ensuite la catégorie, le type de matériaux et le facteur d'émission cradle-to-gate spécifique)
    QuestionSet a224 = questionSetService.findByCode(QuestionCode.A224);
    if( a224 == null ) {
        a224 = new QuestionSet(QuestionCode.A224, true, a223);
        JPA.em().persist(a224);
    }
    // == A128
    // Transport et distribution de marchandises amont
    QuestionSet a128 = questionSetService.findByCode(QuestionCode.A128);
    if( a128 == null ) {
        a128 = new QuestionSet(QuestionCode.A128, false, null);
        JPA.em().persist(a128);
    }
    form4.getQuestionSets().add(a128);
    JPA.em().persist(form4);
    // == A130
    // Transport amont
    QuestionSet a130 = questionSetService.findByCode(QuestionCode.A130);
    if( a130 == null ) {
        a130 = new QuestionSet(QuestionCode.A130, false, null);
        JPA.em().persist(a130);
    }
    form4.getQuestionSets().add(a130);
    JPA.em().persist(form4);
    // == A131
    // Transport avec des véhicules détenus par l'entreprise
    QuestionSet a131 = questionSetService.findByCode(QuestionCode.A131);
    if( a131 == null ) {
        a131 = new QuestionSet(QuestionCode.A131, false, a130);
        JPA.em().persist(a131);
    }
    // == A132
    // Méthode par consommation de carburants
    QuestionSet a132 = questionSetService.findByCode(QuestionCode.A132);
    if( a132 == null ) {
        a132 = new QuestionSet(QuestionCode.A132, false, a131);
        JPA.em().persist(a132);
    }
    // == A140
    // Transport effectué par des transporteurs
    QuestionSet a140 = questionSetService.findByCode(QuestionCode.A140);
    if( a140 == null ) {
        a140 = new QuestionSet(QuestionCode.A140, false, a130);
        JPA.em().persist(a140);
    }
    // == A141
    // Méthode des kilomètres
    QuestionSet a141 = questionSetService.findByCode(QuestionCode.A141);
    if( a141 == null ) {
        a141 = new QuestionSet(QuestionCode.A141, false, a140);
        JPA.em().persist(a141);
    }
    // == A142
    // Créez autant de schémas modaux que nécessaire
    QuestionSet a142 = questionSetService.findByCode(QuestionCode.A142);
    if( a142 == null ) {
        a142 = new QuestionSet(QuestionCode.A142, true, a141);
        JPA.em().persist(a142);
    }
    // == A157
    // Méthode des moyennes
    QuestionSet a157 = questionSetService.findByCode(QuestionCode.A157);
    if( a157 == null ) {
        a157 = new QuestionSet(QuestionCode.A157, false, a140);
        JPA.em().persist(a157);
    }
    // == A163
    // Distribution amont: Energie et froid des entrepôts de stockage
    QuestionSet a163 = questionSetService.findByCode(QuestionCode.A163);
    if( a163 == null ) {
        a163 = new QuestionSet(QuestionCode.A163, false, null);
        JPA.em().persist(a163);
    }
    form4.getQuestionSets().add(a163);
    JPA.em().persist(form4);
    // == A164
    // Créez autant d'entrepôts de stockage que nécessaire
    QuestionSet a164 = questionSetService.findByCode(QuestionCode.A164);
    if( a164 == null ) {
        a164 = new QuestionSet(QuestionCode.A164, true, a163);
        JPA.em().persist(a164);
    }
    // == A166
    // Listez les totaux de combustibles utilisés en amont (exprimés en unités d'énergie)
    QuestionSet a166 = questionSetService.findByCode(QuestionCode.A166);
    if( a166 == null ) {
        a166 = new QuestionSet(QuestionCode.A166, true, a164);
        JPA.em().persist(a166);
    }
    // == A1006
    // Listez les totaux de combustibles utilisés en amont (exprimés en volume)
    QuestionSet a1006 = questionSetService.findByCode(QuestionCode.A1006);
    if( a1006 == null ) {
        a1006 = new QuestionSet(QuestionCode.A1006, true, a164);
        JPA.em().persist(a1006);
    }
    // == A1009
    // Listez les totaux de combustibles utilisés en amont (exprimés en poids)
    QuestionSet a1009 = questionSetService.findByCode(QuestionCode.A1009);
    if( a1009 == null ) {
        a1009 = new QuestionSet(QuestionCode.A1009, true, a164);
        JPA.em().persist(a1009);
    }
    // == A170
    // Listez les gaz réfrigérants utilisés pour les marchandises amont
    QuestionSet a170 = questionSetService.findByCode(QuestionCode.A170);
    if( a170 == null ) {
        a170 = new QuestionSet(QuestionCode.A170, true, a164);
        JPA.em().persist(a170);
    }
    // == A173
    // Déchets générés par les opérations
    QuestionSet a173 = questionSetService.findByCode(QuestionCode.A173);
    if( a173 == null ) {
        a173 = new QuestionSet(QuestionCode.A173, false, null);
        JPA.em().persist(a173);
    }
    form5.getQuestionSets().add(a173);
    JPA.em().persist(form5);
    // == A4999
    // Postes de déchets
    QuestionSet a4999 = questionSetService.findByCode(QuestionCode.A4999);
    if( a4999 == null ) {
        a4999 = new QuestionSet(QuestionCode.A4999, false, null);
        JPA.em().persist(a4999);
    }
    form5.getQuestionSets().add(a4999);
    JPA.em().persist(form5);
    // == A5000
    // Listez vos différents postes de déchets
    QuestionSet a5000 = questionSetService.findByCode(QuestionCode.A5000);
    if( a5000 == null ) {
        a5000 = new QuestionSet(QuestionCode.A5000, true, a4999);
        JPA.em().persist(a5000);
    }
    // == A180
    // Eaux usées
    QuestionSet a180 = questionSetService.findByCode(QuestionCode.A180);
    if( a180 == null ) {
        a180 = new QuestionSet(QuestionCode.A180, false, null);
        JPA.em().persist(a180);
    }
    form5.getQuestionSets().add(a180);
    JPA.em().persist(form5);
    // == A181
    // Eaux usées domestiques par grand type de bâtiments
    QuestionSet a181 = questionSetService.findByCode(QuestionCode.A181);
    if( a181 == null ) {
        a181 = new QuestionSet(QuestionCode.A181, false, a180);
        JPA.em().persist(a181);
    }
    // == A182
    // Usine ou atelier
    QuestionSet a182 = questionSetService.findByCode(QuestionCode.A182);
    if( a182 == null ) {
        a182 = new QuestionSet(QuestionCode.A182, false, a181);
        JPA.em().persist(a182);
    }
    // == A185
    // Bureau
    QuestionSet a185 = questionSetService.findByCode(QuestionCode.A185);
    if( a185 == null ) {
        a185 = new QuestionSet(QuestionCode.A185, false, a181);
        JPA.em().persist(a185);
    }
    // == A188
    // Hôtel, pension, hôpitaux, prison
    QuestionSet a188 = questionSetService.findByCode(QuestionCode.A188);
    if( a188 == null ) {
        a188 = new QuestionSet(QuestionCode.A188, false, a181);
        JPA.em().persist(a188);
    }
    // == A191
    // Restaurant ou cantine
    QuestionSet a191 = questionSetService.findByCode(QuestionCode.A191);
    if( a191 == null ) {
        a191 = new QuestionSet(QuestionCode.A191, false, a181);
        JPA.em().persist(a191);
    }
    // == A194
    // Eaux usées industrielles
    QuestionSet a194 = questionSetService.findByCode(QuestionCode.A194);
    if( a194 == null ) {
        a194 = new QuestionSet(QuestionCode.A194, false, a180);
        JPA.em().persist(a194);
    }
    // == A196
    // Méthodes alternatives
    QuestionSet a196 = questionSetService.findByCode(QuestionCode.A196);
    if( a196 == null ) {
        a196 = new QuestionSet(QuestionCode.A196, false, a194);
        JPA.em().persist(a196);
    }
    // == A197
    // Méthode par la quantité de m³ rejetés
    QuestionSet a197 = questionSetService.findByCode(QuestionCode.A197);
    if( a197 == null ) {
        a197 = new QuestionSet(QuestionCode.A197, false, a196);
        JPA.em().persist(a197);
    }
    // == A201
    // Méthode par le poids de CO2 chimique des effluents rejetés
    QuestionSet a201 = questionSetService.findByCode(QuestionCode.A201);
    if( a201 == null ) {
        a201 = new QuestionSet(QuestionCode.A201, false, a196);
        JPA.em().persist(a201);
    }
    // == A229
    // Biens d'équipement (achetées durant l'année de déclaration)
    QuestionSet a229 = questionSetService.findByCode(QuestionCode.A229);
    if( a229 == null ) {
        a229 = new QuestionSet(QuestionCode.A229, false, null);
        JPA.em().persist(a229);
    }
    form6.getQuestionSets().add(a229);
    JPA.em().persist(form6);
    // == A231
    // Créez et nommez vos postes d'infrastructure
    QuestionSet a231 = questionSetService.findByCode(QuestionCode.A231);
    if( a231 == null ) {
        a231 = new QuestionSet(QuestionCode.A231, true, a229);
        JPA.em().persist(a231);
    }
    // == A237
    // Autres infrastructures spécifiques pour lesquels l'entreprise dispose du facteur d'émission cradle-to-gate
    QuestionSet a237 = questionSetService.findByCode(QuestionCode.A237);
    if( a237 == null ) {
        a237 = new QuestionSet(QuestionCode.A237, false, a229);
        JPA.em().persist(a237);
    }
    // == A238
    // Créez et nommez vos postes d'infrastructure spécifiques
    QuestionSet a238 = questionSetService.findByCode(QuestionCode.A238);
    if( a238 == null ) {
        a238 = new QuestionSet(QuestionCode.A238, true, a237);
        JPA.em().persist(a238);
    }
    // == A309
    // Actifs loués (aval)
    QuestionSet a309 = questionSetService.findByCode(QuestionCode.A309);
    if( a309 == null ) {
        a309 = new QuestionSet(QuestionCode.A309, false, null);
        JPA.em().persist(a309);
    }
    form6.getQuestionSets().add(a309);
    JPA.em().persist(form6);
    // == A311
    // Créez autant de catégories d'actifs loués que nécessaire
    QuestionSet a311 = questionSetService.findByCode(QuestionCode.A311);
    if( a311 == null ) {
        a311 = new QuestionSet(QuestionCode.A311, true, a309);
        JPA.em().persist(a311);
    }
    // == A313
    // Listez les totaux de combustibles utilisés pour les actifs loués (exprimés en unités d'énergie)
    QuestionSet a313 = questionSetService.findByCode(QuestionCode.A313);
    if( a313 == null ) {
        a313 = new QuestionSet(QuestionCode.A313, true, a311);
        JPA.em().persist(a313);
    }
    // == A1012
    // Listez les totaux de combustibles utilisés pour les actifs loués (exprimés en volume)
    QuestionSet a1012 = questionSetService.findByCode(QuestionCode.A1012);
    if( a1012 == null ) {
        a1012 = new QuestionSet(QuestionCode.A1012, true, a311);
        JPA.em().persist(a1012);
    }
    // == A1015
    // Listez les totaux de combustibles utilisés pour les actifs loués (exprimés en poids)
    QuestionSet a1015 = questionSetService.findByCode(QuestionCode.A1015);
    if( a1015 == null ) {
        a1015 = new QuestionSet(QuestionCode.A1015, true, a311);
        JPA.em().persist(a1015);
    }
    // == A317
    // Listez les gaz réfrigérants et autres nécessaires à l'opération des actifs loués
    QuestionSet a317 = questionSetService.findByCode(QuestionCode.A317);
    if( a317 == null ) {
        a317 = new QuestionSet(QuestionCode.A317, true, a311);
        JPA.em().persist(a317);
    }
    // == A320
    // Franchises
    QuestionSet a320 = questionSetService.findByCode(QuestionCode.A320);
    if( a320 == null ) {
        a320 = new QuestionSet(QuestionCode.A320, false, null);
        JPA.em().persist(a320);
    }
    form6.getQuestionSets().add(a320);
    JPA.em().persist(form6);
    // == A322
    // Créez autant de catégories de franchisés que nécessaire
    QuestionSet a322 = questionSetService.findByCode(QuestionCode.A322);
    if( a322 == null ) {
        a322 = new QuestionSet(QuestionCode.A322, true, a320);
        JPA.em().persist(a322);
    }
    // == A325
    // Listez les moyennes de combustibles utilisés par franchisé (exprimés en unités d'énergie)
    QuestionSet a325 = questionSetService.findByCode(QuestionCode.A325);
    if( a325 == null ) {
        a325 = new QuestionSet(QuestionCode.A325, true, a322);
        JPA.em().persist(a325);
    }
    // == A1018
    // Listez les moyennes de combustibles utilisés par franchisé (exprimés en volume)
    QuestionSet a1018 = questionSetService.findByCode(QuestionCode.A1018);
    if( a1018 == null ) {
        a1018 = new QuestionSet(QuestionCode.A1018, true, a322);
        JPA.em().persist(a1018);
    }
    // == A1021
    // Listez les moyennes de combustibles utilisés par franchisé (exprimés en poids)
    QuestionSet a1021 = questionSetService.findByCode(QuestionCode.A1021);
    if( a1021 == null ) {
        a1021 = new QuestionSet(QuestionCode.A1021, true, a322);
        JPA.em().persist(a1021);
    }
    // == A329
    // Listez les gaz réfrigérants et autres utilisés en moyenne par franchisé
    QuestionSet a329 = questionSetService.findByCode(QuestionCode.A329);
    if( a329 == null ) {
        a329 = new QuestionSet(QuestionCode.A329, true, a322);
        JPA.em().persist(a329);
    }
    // == A332
    // Emissions financées
    QuestionSet a332 = questionSetService.findByCode(QuestionCode.A332);
    if( a332 == null ) {
        a332 = new QuestionSet(QuestionCode.A332, false, null);
        JPA.em().persist(a332);
    }
    form6.getQuestionSets().add(a332);
    JPA.em().persist(form6);
    // == A334
    // Veuillez indiquer ici tous les projets dans lesquels votre entreprise investit
    QuestionSet a334 = questionSetService.findByCode(QuestionCode.A334);
    if( a334 == null ) {
        a334 = new QuestionSet(QuestionCode.A334, true, a332);
        JPA.em().persist(a334);
    }
    // == A243
    // Transport & Distribution, Traitement, Utilisation et Fin de vie des produits vendus
    QuestionSet a243 = questionSetService.findByCode(QuestionCode.A243);
    if( a243 == null ) {
        a243 = new QuestionSet(QuestionCode.A243, false, null);
        JPA.em().persist(a243);
    }
    form7.getQuestionSets().add(a243);
    JPA.em().persist(form7);
    // == A244
    // Lister les différents produits ou groupes de produits vendus par l'entreprise
    QuestionSet a244 = questionSetService.findByCode(QuestionCode.A244);
    if( a244 == null ) {
        a244 = new QuestionSet(QuestionCode.A244, true, a243);
        JPA.em().persist(a244);
    }
    // == A8000
    // Type de produit pour rubriques subséquentes
    QuestionSet a8000 = questionSetService.findByCode(QuestionCode.A8000);
    if( a8000 == null ) {
        a8000 = new QuestionSet(QuestionCode.A8000, false, a244);
        JPA.em().persist(a8000);
    }
    // == A250
    // Transport et distribution aval
    QuestionSet a250 = questionSetService.findByCode(QuestionCode.A250);
    if( a250 == null ) {
        a250 = new QuestionSet(QuestionCode.A250, false, a244);
        JPA.em().persist(a250);
    }
    // == A252
    // Transport aval: choix de méthodes
    QuestionSet a252 = questionSetService.findByCode(QuestionCode.A252);
    if( a252 == null ) {
        a252 = new QuestionSet(QuestionCode.A252, false, a250);
        JPA.em().persist(a252);
    }
    // == A253
    // Méthode par kilométrage
    QuestionSet a253 = questionSetService.findByCode(QuestionCode.A253);
    if( a253 == null ) {
        a253 = new QuestionSet(QuestionCode.A253, false, a252);
        JPA.em().persist(a253);
    }
    // == A266
    // Méthode des moyennes
    QuestionSet a266 = questionSetService.findByCode(QuestionCode.A266);
    if( a266 == null ) {
        a266 = new QuestionSet(QuestionCode.A266, false, a252);
        JPA.em().persist(a266);
    }
    // == A272
    // Distribution avale: Energie et Froid des entrepôts de stockage
    QuestionSet a272 = questionSetService.findByCode(QuestionCode.A272);
    if( a272 == null ) {
        a272 = new QuestionSet(QuestionCode.A272, false, a244);
        JPA.em().persist(a272);
    }
    // == A273
    // Créez autant d'entrepôts de stockage que nécessaire
    QuestionSet a273 = questionSetService.findByCode(QuestionCode.A273);
    if( a273 == null ) {
        a273 = new QuestionSet(QuestionCode.A273, true, a272);
        JPA.em().persist(a273);
    }
    // == A275
    // Listez les totaux de combustibles utilisés (exprimés en unités d'énergie)
    QuestionSet a275 = questionSetService.findByCode(QuestionCode.A275);
    if( a275 == null ) {
        a275 = new QuestionSet(QuestionCode.A275, true, a273);
        JPA.em().persist(a275);
    }
    // == A1024
    // Listez les totaux de combustibles utilisés (exprimés en volume)
    QuestionSet a1024 = questionSetService.findByCode(QuestionCode.A1024);
    if( a1024 == null ) {
        a1024 = new QuestionSet(QuestionCode.A1024, true, a273);
        JPA.em().persist(a1024);
    }
    // == A1027
    // Listez les totaux de combustibles utilisés (exprimés en poids)
    QuestionSet a1027 = questionSetService.findByCode(QuestionCode.A1027);
    if( a1027 == null ) {
        a1027 = new QuestionSet(QuestionCode.A1027, true, a273);
        JPA.em().persist(a1027);
    }
    // == A279
    // Listez les gaz réfrigérants
    QuestionSet a279 = questionSetService.findByCode(QuestionCode.A279);
    if( a279 == null ) {
        a279 = new QuestionSet(QuestionCode.A279, true, a273);
        JPA.em().persist(a279);
    }
    // == A282
    // Traitement
    QuestionSet a282 = questionSetService.findByCode(QuestionCode.A282);
    if( a282 == null ) {
        a282 = new QuestionSet(QuestionCode.A282, false, a244);
        JPA.em().persist(a282);
    }
    // == A284
    // Listez les totaux de combustibles (exprimés en unités d'énergie)
    QuestionSet a284 = questionSetService.findByCode(QuestionCode.A284);
    if( a284 == null ) {
        a284 = new QuestionSet(QuestionCode.A284, true, a282);
        JPA.em().persist(a284);
    }
    // == A1030
    // Listez les totaux de combustibles (exprimés en volume)
    QuestionSet a1030 = questionSetService.findByCode(QuestionCode.A1030);
    if( a1030 == null ) {
        a1030 = new QuestionSet(QuestionCode.A1030, true, a282);
        JPA.em().persist(a1030);
    }
    // == A1033
    // Listez les totaux de combustibles (exprimés en poids)
    QuestionSet a1033 = questionSetService.findByCode(QuestionCode.A1033);
    if( a1033 == null ) {
        a1033 = new QuestionSet(QuestionCode.A1033, true, a282);
        JPA.em().persist(a1033);
    }
    // == A288
    // Listez les gaz réfrigérants
    QuestionSet a288 = questionSetService.findByCode(QuestionCode.A288);
    if( a288 == null ) {
        a288 = new QuestionSet(QuestionCode.A288, true, a282);
        JPA.em().persist(a288);
    }
    // == A291
    // Utilisation
    QuestionSet a291 = questionSetService.findByCode(QuestionCode.A291);
    if( a291 == null ) {
        a291 = new QuestionSet(QuestionCode.A291, false, a244);
        JPA.em().persist(a291);
    }
    // == A297
    // Listez les gaz émis par utilisation de produit
    QuestionSet a297 = questionSetService.findByCode(QuestionCode.A297);
    if( a297 == null ) {
        a297 = new QuestionSet(QuestionCode.A297, true, a291);
        JPA.em().persist(a297);
    }
    // == A300
    // Traitement de fin de vie
    QuestionSet a300 = questionSetService.findByCode(QuestionCode.A300);
    if( a300 == null ) {
        a300 = new QuestionSet(QuestionCode.A300, false, a244);
        JPA.em().persist(a300);
    }
    // == A5010
    // Créez autant de postes de déchet que nécessaire
    QuestionSet a5010 = questionSetService.findByCode(QuestionCode.A5010);
    if( a5010 == null ) {
        a5010 = new QuestionSet(QuestionCode.A5010, true, a300);
        JPA.em().persist(a5010);
    }


    // =========================================================================
    // QUESTIONS
    // =========================================================================

    // == A2
    // Année de référence pour comparaison du présent bilan GES

    
IntegerQuestion a2 = (IntegerQuestion) questionService.findByCode(QuestionCode.A2);
if (a2 == null) {
    a2 = new IntegerQuestion(a1, 0, QuestionCode.A2, null, null);
    JPA.em().persist(a2);
} else {
    a2.setDefaultValue(null);
    if (!a2.getQuestionSet().equals(a1) && a1.getQuestions().contains(a2)) {
        a1.getQuestions().remove(a2);
        JPA.em().persist(a1);
    }
    if (a2.getQuestionSet().equals(a1) && !a1.getQuestions().contains(a2)) {
        a1.getQuestions().add(a2);
        JPA.em().persist(a1);
    }
    a2.setOrderIndex(0);
    a2.setUnitCategory(null);
    JPA.em().persist(a2);
}


    // == A3
    // A quel secteur principal appartient votre site?

    ValueSelectionQuestion a3 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A3);
if (a3 == null) {
    a3 = new ValueSelectionQuestion(a1, 0, QuestionCode.A3, CodeList.SECTEURPRINCIPAL);
    JPA.em().persist(a3);
} else {
    if (!a3.getQuestionSet().equals(a1) && a1.getQuestions().contains(a3)) {
        a1.getQuestions().remove(a3);
        JPA.em().persist(a1);
    }
    if (a3.getQuestionSet().equals(a1) && !a1.getQuestions().contains(a3)) {
        a1.getQuestions().add(a3);
        JPA.em().persist(a1);
    }
    a3.setOrderIndex(0);
    a3.setCodeList(CodeList.SECTEURPRINCIPAL);
    JPA.em().persist(a3);
}


    // == A4
    // Quel est le code NACE principal de votre site?

    ValueSelectionQuestion a4 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A4);
if (a4 == null) {
    a4 = new ValueSelectionQuestion(a1, 0, QuestionCode.A4, CodeList.SECTEURPRIMAIRE);
    JPA.em().persist(a4);
} else {
    if (!a4.getQuestionSet().equals(a1) && a1.getQuestions().contains(a4)) {
        a1.getQuestions().remove(a4);
        JPA.em().persist(a1);
    }
    if (a4.getQuestionSet().equals(a1) && !a1.getQuestions().contains(a4)) {
        a1.getQuestions().add(a4);
        JPA.em().persist(a1);
    }
    a4.setOrderIndex(0);
    a4.setCodeList(CodeList.SECTEURPRIMAIRE);
    JPA.em().persist(a4);
}


    // == A5
    // Quel est le code NACE principal de votre site?

    ValueSelectionQuestion a5 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A5);
if (a5 == null) {
    a5 = new ValueSelectionQuestion(a1, 0, QuestionCode.A5, CodeList.SECTEURSECONDAIRE);
    JPA.em().persist(a5);
} else {
    if (!a5.getQuestionSet().equals(a1) && a1.getQuestions().contains(a5)) {
        a1.getQuestions().remove(a5);
        JPA.em().persist(a1);
    }
    if (a5.getQuestionSet().equals(a1) && !a1.getQuestions().contains(a5)) {
        a1.getQuestions().add(a5);
        JPA.em().persist(a1);
    }
    a5.setOrderIndex(0);
    a5.setCodeList(CodeList.SECTEURSECONDAIRE);
    JPA.em().persist(a5);
}


    // == A6
    // Quel est le code NACE principal de votre site?

    ValueSelectionQuestion a6 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A6);
if (a6 == null) {
    a6 = new ValueSelectionQuestion(a1, 0, QuestionCode.A6, CodeList.SECTEURTERTIAIRE);
    JPA.em().persist(a6);
} else {
    if (!a6.getQuestionSet().equals(a1) && a1.getQuestions().contains(a6)) {
        a1.getQuestions().remove(a6);
        JPA.em().persist(a1);
    }
    if (a6.getQuestionSet().equals(a1) && !a1.getQuestions().contains(a6)) {
        a1.getQuestions().add(a6);
        JPA.em().persist(a1);
    }
    a6.setOrderIndex(0);
    a6.setCodeList(CodeList.SECTEURTERTIAIRE);
    JPA.em().persist(a6);
}


    // == A7
    // Est-ce que votre activité est purement ou principalement de bureaux?

    BooleanQuestion a7 = (BooleanQuestion) questionService.findByCode(QuestionCode.A7);
if (a7 == null) {
    a7 = new BooleanQuestion(a1, 0, QuestionCode.A7, null);
    JPA.em().persist(a7);
} else {
    a7.setDefaultValue(null);
    if (!a7.getQuestionSet().equals(a1) && a1.getQuestions().contains(a7)) {
        a1.getQuestions().remove(a7);
        JPA.em().persist(a1);
    }
    if (a7.getQuestionSet().equals(a1) && !a1.getQuestions().contains(a7)) {
        a1.getQuestions().add(a7);
        JPA.em().persist(a1);
    }
    a7.setOrderIndex(0);
    JPA.em().persist(a7);
}


    // == A9
    // Indiquez la surface totale du site:

    
DoubleQuestion a9 = (DoubleQuestion) questionService.findByCode(QuestionCode.A9);
if (a9 == null) {
    a9 = new DoubleQuestion( a1, 0, QuestionCode.A9, areaUnits, null, areaUnits.getMainUnit() );
    JPA.em().persist(a9);
} else {
    a9.setDefaultValue(null);
    if (!a9.getQuestionSet().equals(a1) && a1.getQuestions().contains(a9)) {
        a1.getQuestions().remove(a9);
        JPA.em().persist(a1);
    }
    if (a9.getQuestionSet().equals(a1) && !a1.getQuestions().contains(a9)) {
        a1.getQuestions().add(a9);
        JPA.em().persist(a1);
    }
    a9.setUnitCategory(areaUnits);
    a9.setOrderIndex(0);
    a9.setDefaultUnit(areaUnits.getMainUnit());
    JPA.em().persist(a9);
}




    // == A10
    // Quelle est la surface des bureaux?

    
DoubleQuestion a10 = (DoubleQuestion) questionService.findByCode(QuestionCode.A10);
if (a10 == null) {
    a10 = new DoubleQuestion( a1, 0, QuestionCode.A10, areaUnits, null, areaUnits.getMainUnit() );
    JPA.em().persist(a10);
} else {
    a10.setDefaultValue(null);
    if (!a10.getQuestionSet().equals(a1) && a1.getQuestions().contains(a10)) {
        a1.getQuestions().remove(a10);
        JPA.em().persist(a1);
    }
    if (a10.getQuestionSet().equals(a1) && !a1.getQuestions().contains(a10)) {
        a1.getQuestions().add(a10);
        JPA.em().persist(a1);
    }
    a10.setUnitCategory(areaUnits);
    a10.setOrderIndex(0);
    a10.setDefaultUnit(areaUnits.getMainUnit());
    JPA.em().persist(a10);
}




    // == A11
    // Etes-vous participant aux accords de branche de 2ème génération?

    BooleanQuestion a11 = (BooleanQuestion) questionService.findByCode(QuestionCode.A11);
if (a11 == null) {
    a11 = new BooleanQuestion(a1, 0, QuestionCode.A11, null);
    JPA.em().persist(a11);
} else {
    a11.setDefaultValue(null);
    if (!a11.getQuestionSet().equals(a1) && a1.getQuestions().contains(a11)) {
        a1.getQuestions().remove(a11);
        JPA.em().persist(a1);
    }
    if (a11.getQuestionSet().equals(a1) && !a1.getQuestions().contains(a11)) {
        a1.getQuestions().add(a11);
        JPA.em().persist(a1);
    }
    a11.setOrderIndex(0);
    JPA.em().persist(a11);
}


    // == A12
    // Quel est le nombre d'employés sur l'année du bilan?

    
IntegerQuestion a12 = (IntegerQuestion) questionService.findByCode(QuestionCode.A12);
if (a12 == null) {
    a12 = new IntegerQuestion(a1, 0, QuestionCode.A12, null, null);
    JPA.em().persist(a12);
} else {
    a12.setDefaultValue(null);
    if (!a12.getQuestionSet().equals(a1) && a1.getQuestions().contains(a12)) {
        a1.getQuestions().remove(a12);
        JPA.em().persist(a1);
    }
    if (a12.getQuestionSet().equals(a1) && !a1.getQuestions().contains(a12)) {
        a1.getQuestions().add(a12);
        JPA.em().persist(a1);
    }
    a12.setOrderIndex(0);
    a12.setUnitCategory(null);
    JPA.em().persist(a12);
}


    // == A14
    // Pièces documentaires liées aux consommations de combustible

    DocumentQuestion a14 = (DocumentQuestion) questionService.findByCode(QuestionCode.A14);
if (a14 == null) {
    a14 = new DocumentQuestion(a13, 0, QuestionCode.A14);
    JPA.em().persist(a14);
} else {
    if (!a14.getQuestionSet().equals(a13) && a13.getQuestions().contains(a14)) {
        a13.getQuestions().remove(a14);
        JPA.em().persist(a13);
    }
    if (a14.getQuestionSet().equals(a13) && !a13.getQuestions().contains(a14)) {
        a13.getQuestions().add(a14);
        JPA.em().persist(a13);
    }
    a14.setOrderIndex(0);
    JPA.em().persist(a14);
}


    // == A16
    // Combustible

    ValueSelectionQuestion a16 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A16);
if (a16 == null) {
    a16 = new ValueSelectionQuestion(a15, 0, QuestionCode.A16, CodeList.COMBUSTIBLE);
    JPA.em().persist(a16);
} else {
    if (!a16.getQuestionSet().equals(a15) && a15.getQuestions().contains(a16)) {
        a15.getQuestions().remove(a16);
        JPA.em().persist(a15);
    }
    if (a16.getQuestionSet().equals(a15) && !a15.getQuestions().contains(a16)) {
        a15.getQuestions().add(a16);
        JPA.em().persist(a15);
    }
    a16.setOrderIndex(0);
    a16.setCodeList(CodeList.COMBUSTIBLE);
    JPA.em().persist(a16);
}


    // == A17
    // Quantité

    
DoubleQuestion a17 = (DoubleQuestion) questionService.findByCode(QuestionCode.A17);
if (a17 == null) {
    a17 = new DoubleQuestion( a15, 0, QuestionCode.A17, energyUnits, null, getUnitBySymbol("kW.h") );
    JPA.em().persist(a17);
} else {
    a17.setDefaultValue(null);
    if (!a17.getQuestionSet().equals(a15) && a15.getQuestions().contains(a17)) {
        a15.getQuestions().remove(a17);
        JPA.em().persist(a15);
    }
    if (a17.getQuestionSet().equals(a15) && !a15.getQuestions().contains(a17)) {
        a15.getQuestions().add(a17);
        JPA.em().persist(a15);
    }
    a17.setUnitCategory(energyUnits);
    a17.setOrderIndex(0);
    a17.setDefaultUnit(getUnitBySymbol("kW.h"));
    JPA.em().persist(a17);
}




    // == A1001
    // Combustible

    ValueSelectionQuestion a1001 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A1001);
if (a1001 == null) {
    a1001 = new ValueSelectionQuestion(a1000, 0, QuestionCode.A1001, CodeList.COMBUSTIBLEVOLUME);
    JPA.em().persist(a1001);
} else {
    if (!a1001.getQuestionSet().equals(a1000) && a1000.getQuestions().contains(a1001)) {
        a1000.getQuestions().remove(a1001);
        JPA.em().persist(a1000);
    }
    if (a1001.getQuestionSet().equals(a1000) && !a1000.getQuestions().contains(a1001)) {
        a1000.getQuestions().add(a1001);
        JPA.em().persist(a1000);
    }
    a1001.setOrderIndex(0);
    a1001.setCodeList(CodeList.COMBUSTIBLEVOLUME);
    JPA.em().persist(a1001);
}


    // == A1002
    // Quantité

    
DoubleQuestion a1002 = (DoubleQuestion) questionService.findByCode(QuestionCode.A1002);
if (a1002 == null) {
    a1002 = new DoubleQuestion( a1000, 0, QuestionCode.A1002, volumeUnits, null, volumeUnits.getMainUnit() );
    JPA.em().persist(a1002);
} else {
    a1002.setDefaultValue(null);
    if (!a1002.getQuestionSet().equals(a1000) && a1000.getQuestions().contains(a1002)) {
        a1000.getQuestions().remove(a1002);
        JPA.em().persist(a1000);
    }
    if (a1002.getQuestionSet().equals(a1000) && !a1000.getQuestions().contains(a1002)) {
        a1000.getQuestions().add(a1002);
        JPA.em().persist(a1000);
    }
    a1002.setUnitCategory(volumeUnits);
    a1002.setOrderIndex(0);
    a1002.setDefaultUnit(volumeUnits.getMainUnit());
    JPA.em().persist(a1002);
}




    // == A1004
    // Combustible

    ValueSelectionQuestion a1004 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A1004);
if (a1004 == null) {
    a1004 = new ValueSelectionQuestion(a1003, 0, QuestionCode.A1004, CodeList.COMBUSTIBLEPOIDS);
    JPA.em().persist(a1004);
} else {
    if (!a1004.getQuestionSet().equals(a1003) && a1003.getQuestions().contains(a1004)) {
        a1003.getQuestions().remove(a1004);
        JPA.em().persist(a1003);
    }
    if (a1004.getQuestionSet().equals(a1003) && !a1003.getQuestions().contains(a1004)) {
        a1003.getQuestions().add(a1004);
        JPA.em().persist(a1003);
    }
    a1004.setOrderIndex(0);
    a1004.setCodeList(CodeList.COMBUSTIBLEPOIDS);
    JPA.em().persist(a1004);
}


    // == A1005
    // Quantité

    
DoubleQuestion a1005 = (DoubleQuestion) questionService.findByCode(QuestionCode.A1005);
if (a1005 == null) {
    a1005 = new DoubleQuestion( a1003, 0, QuestionCode.A1005, massUnits, null, massUnits.getMainUnit() );
    JPA.em().persist(a1005);
} else {
    a1005.setDefaultValue(null);
    if (!a1005.getQuestionSet().equals(a1003) && a1003.getQuestions().contains(a1005)) {
        a1003.getQuestions().remove(a1005);
        JPA.em().persist(a1003);
    }
    if (a1005.getQuestionSet().equals(a1003) && !a1003.getQuestions().contains(a1005)) {
        a1003.getQuestions().add(a1005);
        JPA.em().persist(a1003);
    }
    a1005.setUnitCategory(massUnits);
    a1005.setOrderIndex(0);
    a1005.setDefaultUnit(massUnits.getMainUnit());
    JPA.em().persist(a1005);
}




    // == A21
    // Pièces documentaires liées aux achats d'électricité et de vapeur

    DocumentQuestion a21 = (DocumentQuestion) questionService.findByCode(QuestionCode.A21);
if (a21 == null) {
    a21 = new DocumentQuestion(a20, 0, QuestionCode.A21);
    JPA.em().persist(a21);
} else {
    if (!a21.getQuestionSet().equals(a20) && a20.getQuestions().contains(a21)) {
        a20.getQuestions().remove(a21);
        JPA.em().persist(a20);
    }
    if (a21.getQuestionSet().equals(a20) && !a20.getQuestions().contains(a21)) {
        a20.getQuestions().add(a21);
        JPA.em().persist(a20);
    }
    a21.setOrderIndex(0);
    JPA.em().persist(a21);
}


    // == A23
    // Consommation d'électricité verte

    
DoubleQuestion a23 = (DoubleQuestion) questionService.findByCode(QuestionCode.A23);
if (a23 == null) {
    a23 = new DoubleQuestion( a22, 0, QuestionCode.A23, energyUnits, null, getUnitBySymbol("kW.h") );
    JPA.em().persist(a23);
} else {
    a23.setDefaultValue(null);
    if (!a23.getQuestionSet().equals(a22) && a22.getQuestions().contains(a23)) {
        a22.getQuestions().remove(a23);
        JPA.em().persist(a22);
    }
    if (a23.getQuestionSet().equals(a22) && !a22.getQuestions().contains(a23)) {
        a22.getQuestions().add(a23);
        JPA.em().persist(a22);
    }
    a23.setUnitCategory(energyUnits);
    a23.setOrderIndex(0);
    a23.setDefaultUnit(getUnitBySymbol("kW.h"));
    JPA.em().persist(a23);
}




    // == A24
    // Consommation d'électricité grise

    
DoubleQuestion a24 = (DoubleQuestion) questionService.findByCode(QuestionCode.A24);
if (a24 == null) {
    a24 = new DoubleQuestion( a22, 0, QuestionCode.A24, energyUnits, null, getUnitBySymbol("kW.h") );
    JPA.em().persist(a24);
} else {
    a24.setDefaultValue(null);
    if (!a24.getQuestionSet().equals(a22) && a22.getQuestions().contains(a24)) {
        a22.getQuestions().remove(a24);
        JPA.em().persist(a22);
    }
    if (a24.getQuestionSet().equals(a22) && !a22.getQuestions().contains(a24)) {
        a22.getQuestions().add(a24);
        JPA.em().persist(a22);
    }
    a24.setUnitCategory(energyUnits);
    a24.setOrderIndex(0);
    a24.setDefaultUnit(getUnitBySymbol("kW.h"));
    JPA.em().persist(a24);
}




    // == A26
    // Energie primaire utilisée pour produire la vapeur:

    ValueSelectionQuestion a26 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A26);
if (a26 == null) {
    a26 = new ValueSelectionQuestion(a25, 0, QuestionCode.A26, CodeList.ENERGIEVAPEUR);
    JPA.em().persist(a26);
} else {
    if (!a26.getQuestionSet().equals(a25) && a25.getQuestions().contains(a26)) {
        a25.getQuestions().remove(a26);
        JPA.em().persist(a25);
    }
    if (a26.getQuestionSet().equals(a25) && !a25.getQuestions().contains(a26)) {
        a25.getQuestions().add(a26);
        JPA.em().persist(a25);
    }
    a26.setOrderIndex(0);
    a26.setCodeList(CodeList.ENERGIEVAPEUR);
    JPA.em().persist(a26);
}


    // == A27
    // Efficacité de la chaudière

    PercentageQuestion a27 = (PercentageQuestion) questionService.findByCode(QuestionCode.A27);
if (a27 == null) {
    a27 = new PercentageQuestion(a25, 0, QuestionCode.A27, null);
    JPA.em().persist(a27);
} else {
    a27.setDefaultValue(null);
    if (!a27.getQuestionSet().equals(a25) && a25.getQuestions().contains(a27)) {
        a25.getQuestions().remove(a27);
        JPA.em().persist(a25);
    }
    if (a27.getQuestionSet().equals(a25) && !a25.getQuestions().contains(a27)) {
        a25.getQuestions().add(a27);
        JPA.em().persist(a25);
    }
    a27.setOrderIndex(0);
    JPA.em().persist(a27);
}


    // == A28
    // Quantité achetée

    
DoubleQuestion a28 = (DoubleQuestion) questionService.findByCode(QuestionCode.A28);
if (a28 == null) {
    a28 = new DoubleQuestion( a25, 0, QuestionCode.A28, energyUnits, null, getUnitBySymbol("kW.h") );
    JPA.em().persist(a28);
} else {
    a28.setDefaultValue(null);
    if (!a28.getQuestionSet().equals(a25) && a25.getQuestions().contains(a28)) {
        a25.getQuestions().remove(a28);
        JPA.em().persist(a25);
    }
    if (a28.getQuestionSet().equals(a25) && !a25.getQuestions().contains(a28)) {
        a25.getQuestions().add(a28);
        JPA.em().persist(a25);
    }
    a28.setUnitCategory(energyUnits);
    a28.setOrderIndex(0);
    a28.setDefaultUnit(getUnitBySymbol("kW.h"));
    JPA.em().persist(a28);
}




    // == A32
    // Est-ce que vos activités impliquent des procédés chimiques et physiques émetteurs directs de gaz à effet de serre ?

    BooleanQuestion a32 = (BooleanQuestion) questionService.findByCode(QuestionCode.A32);
if (a32 == null) {
    a32 = new BooleanQuestion(a31, 0, QuestionCode.A32, null);
    JPA.em().persist(a32);
} else {
    a32.setDefaultValue(null);
    if (!a32.getQuestionSet().equals(a31) && a31.getQuestions().contains(a32)) {
        a31.getQuestions().remove(a32);
        JPA.em().persist(a31);
    }
    if (a32.getQuestionSet().equals(a31) && !a31.getQuestions().contains(a32)) {
        a31.getQuestions().add(a32);
        JPA.em().persist(a31);
    }
    a32.setOrderIndex(0);
    JPA.em().persist(a32);
}


    // == A33
    // Pièces documentaires liées aux GES des processus de production

    DocumentQuestion a33 = (DocumentQuestion) questionService.findByCode(QuestionCode.A33);
if (a33 == null) {
    a33 = new DocumentQuestion(a31, 0, QuestionCode.A33);
    JPA.em().persist(a33);
} else {
    if (!a33.getQuestionSet().equals(a31) && a31.getQuestions().contains(a33)) {
        a31.getQuestions().remove(a33);
        JPA.em().persist(a31);
    }
    if (a33.getQuestionSet().equals(a31) && !a31.getQuestions().contains(a33)) {
        a31.getQuestions().add(a33);
        JPA.em().persist(a31);
    }
    a33.setOrderIndex(0);
    JPA.em().persist(a33);
}


    // == A35
    // Type de GES

    ValueSelectionQuestion a35 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A35);
if (a35 == null) {
    a35 = new ValueSelectionQuestion(a34, 0, QuestionCode.A35, CodeList.GES);
    JPA.em().persist(a35);
} else {
    if (!a35.getQuestionSet().equals(a34) && a34.getQuestions().contains(a35)) {
        a34.getQuestions().remove(a35);
        JPA.em().persist(a34);
    }
    if (a35.getQuestionSet().equals(a34) && !a34.getQuestions().contains(a35)) {
        a34.getQuestions().add(a35);
        JPA.em().persist(a34);
    }
    a35.setOrderIndex(0);
    a35.setCodeList(CodeList.GES);
    JPA.em().persist(a35);
}


    // == A36
    // Quantité

    
DoubleQuestion a36 = (DoubleQuestion) questionService.findByCode(QuestionCode.A36);
if (a36 == null) {
    a36 = new DoubleQuestion( a34, 0, QuestionCode.A36, massUnits, null, getUnitBySymbol("t") );
    JPA.em().persist(a36);
} else {
    a36.setDefaultValue(null);
    if (!a36.getQuestionSet().equals(a34) && a34.getQuestions().contains(a36)) {
        a34.getQuestions().remove(a36);
        JPA.em().persist(a34);
    }
    if (a36.getQuestionSet().equals(a34) && !a34.getQuestions().contains(a36)) {
        a34.getQuestions().add(a36);
        JPA.em().persist(a34);
    }
    a36.setUnitCategory(massUnits);
    a36.setOrderIndex(0);
    a36.setDefaultUnit(getUnitBySymbol("t"));
    JPA.em().persist(a36);
}




    // == A38
    // Disposez-vous d’un système de froid nécessitant un apport ponctuel d’agent réfrigérant (p.e. les chillers, les climatiseurs à air et à eau glacée, les réfrigérateurs, bacs à surgelés, etc.)?

    BooleanQuestion a38 = (BooleanQuestion) questionService.findByCode(QuestionCode.A38);
if (a38 == null) {
    a38 = new BooleanQuestion(a37, 0, QuestionCode.A38, null);
    JPA.em().persist(a38);
} else {
    a38.setDefaultValue(null);
    if (!a38.getQuestionSet().equals(a37) && a37.getQuestions().contains(a38)) {
        a37.getQuestions().remove(a38);
        JPA.em().persist(a37);
    }
    if (a38.getQuestionSet().equals(a37) && !a37.getQuestions().contains(a38)) {
        a37.getQuestions().add(a38);
        JPA.em().persist(a37);
    }
    a38.setOrderIndex(0);
    JPA.em().persist(a38);
}


    // == A39
    // Pièces documentaires liées aux systèmes de refroidissement

    DocumentQuestion a39 = (DocumentQuestion) questionService.findByCode(QuestionCode.A39);
if (a39 == null) {
    a39 = new DocumentQuestion(a37, 0, QuestionCode.A39);
    JPA.em().persist(a39);
} else {
    if (!a39.getQuestionSet().equals(a37) && a37.getQuestions().contains(a39)) {
        a37.getQuestions().remove(a39);
        JPA.em().persist(a37);
    }
    if (a39.getQuestionSet().equals(a37) && !a37.getQuestions().contains(a39)) {
        a37.getQuestions().add(a39);
        JPA.em().persist(a37);
    }
    a39.setOrderIndex(0);
    JPA.em().persist(a39);
}


    // == A43
    // Type de gaz

    ValueSelectionQuestion a43 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A43);
if (a43 == null) {
    a43 = new ValueSelectionQuestion(a42, 0, QuestionCode.A43, CodeList.FRIGORIGENE);
    JPA.em().persist(a43);
} else {
    if (!a43.getQuestionSet().equals(a42) && a42.getQuestions().contains(a43)) {
        a42.getQuestions().remove(a43);
        JPA.em().persist(a42);
    }
    if (a43.getQuestionSet().equals(a42) && !a42.getQuestions().contains(a43)) {
        a42.getQuestions().add(a43);
        JPA.em().persist(a42);
    }
    a43.setOrderIndex(0);
    a43.setCodeList(CodeList.FRIGORIGENE);
    JPA.em().persist(a43);
}


    // == A44
    // Quantité de recharge nécessaire pour l'année

    
DoubleQuestion a44 = (DoubleQuestion) questionService.findByCode(QuestionCode.A44);
if (a44 == null) {
    a44 = new DoubleQuestion( a42, 0, QuestionCode.A44, massUnits, null, massUnits.getMainUnit() );
    JPA.em().persist(a44);
} else {
    a44.setDefaultValue(null);
    if (!a44.getQuestionSet().equals(a42) && a42.getQuestions().contains(a44)) {
        a42.getQuestions().remove(a44);
        JPA.em().persist(a42);
    }
    if (a44.getQuestionSet().equals(a42) && !a42.getQuestions().contains(a44)) {
        a42.getQuestions().add(a44);
        JPA.em().persist(a42);
    }
    a44.setUnitCategory(massUnits);
    a44.setOrderIndex(0);
    a44.setDefaultUnit(massUnits.getMainUnit());
    JPA.em().persist(a44);
}




    // == A46
    // Quel est la puissance frigorifique des groupes froid?

    
DoubleQuestion a46 = (DoubleQuestion) questionService.findByCode(QuestionCode.A46);
if (a46 == null) {
    a46 = new DoubleQuestion( a45, 0, QuestionCode.A46, powerUnits, null, powerUnits.getMainUnit() );
    JPA.em().persist(a46);
} else {
    a46.setDefaultValue(null);
    if (!a46.getQuestionSet().equals(a45) && a45.getQuestions().contains(a46)) {
        a45.getQuestions().remove(a46);
        JPA.em().persist(a45);
    }
    if (a46.getQuestionSet().equals(a45) && !a45.getQuestions().contains(a46)) {
        a45.getQuestions().add(a46);
        JPA.em().persist(a45);
    }
    a46.setUnitCategory(powerUnits);
    a46.setOrderIndex(0);
    a46.setDefaultUnit(powerUnits.getMainUnit());
    JPA.em().persist(a46);
}




    // == A48
    // Est-ce que votre entreprise produit du sucre ou des pâtes sèches?

    BooleanQuestion a48 = (BooleanQuestion) questionService.findByCode(QuestionCode.A48);
if (a48 == null) {
    a48 = new BooleanQuestion(a47, 0, QuestionCode.A48, null);
    JPA.em().persist(a48);
} else {
    a48.setDefaultValue(null);
    if (!a48.getQuestionSet().equals(a47) && a47.getQuestions().contains(a48)) {
        a47.getQuestions().remove(a48);
        JPA.em().persist(a47);
    }
    if (a48.getQuestionSet().equals(a47) && !a47.getQuestions().contains(a48)) {
        a47.getQuestions().add(a48);
        JPA.em().persist(a47);
    }
    a48.setOrderIndex(0);
    JPA.em().persist(a48);
}


    // == A49
    // Quel est le nombre d'heures de fonctionnement annuel du site?

    
DoubleQuestion a49 = (DoubleQuestion) questionService.findByCode(QuestionCode.A49);
if (a49 == null) {
    a49 = new DoubleQuestion( a47, 0, QuestionCode.A49, timeUnits, null, getUnitBySymbol("h") );
    JPA.em().persist(a49);
} else {
    a49.setDefaultValue(null);
    if (!a49.getQuestionSet().equals(a47) && a47.getQuestions().contains(a49)) {
        a47.getQuestions().remove(a49);
        JPA.em().persist(a47);
    }
    if (a49.getQuestionSet().equals(a47) && !a47.getQuestions().contains(a49)) {
        a47.getQuestions().add(a49);
        JPA.em().persist(a47);
    }
    a49.setUnitCategory(timeUnits);
    a49.setOrderIndex(0);
    a49.setDefaultUnit(getUnitBySymbol("h"));
    JPA.em().persist(a49);
}




    // == A51
    // Pièces documentaires liées à la mobilité

    DocumentQuestion a51 = (DocumentQuestion) questionService.findByCode(QuestionCode.A51);
if (a51 == null) {
    a51 = new DocumentQuestion(a50, 0, QuestionCode.A51);
    JPA.em().persist(a51);
} else {
    if (!a51.getQuestionSet().equals(a50) && a50.getQuestions().contains(a51)) {
        a50.getQuestions().remove(a51);
        JPA.em().persist(a50);
    }
    if (a51.getQuestionSet().equals(a50) && !a50.getQuestions().contains(a51)) {
        a50.getQuestions().add(a51);
        JPA.em().persist(a50);
    }
    a51.setOrderIndex(0);
    JPA.em().persist(a51);
}


    // == A403
    // Consommation d'essence

    
DoubleQuestion a403 = (DoubleQuestion) questionService.findByCode(QuestionCode.A403);
if (a403 == null) {
    a403 = new DoubleQuestion( a402, 0, QuestionCode.A403, volumeUnits, null, volumeUnits.getMainUnit() );
    JPA.em().persist(a403);
} else {
    a403.setDefaultValue(null);
    if (!a403.getQuestionSet().equals(a402) && a402.getQuestions().contains(a403)) {
        a402.getQuestions().remove(a403);
        JPA.em().persist(a402);
    }
    if (a403.getQuestionSet().equals(a402) && !a402.getQuestions().contains(a403)) {
        a402.getQuestions().add(a403);
        JPA.em().persist(a402);
    }
    a403.setUnitCategory(volumeUnits);
    a403.setOrderIndex(0);
    a403.setDefaultUnit(volumeUnits.getMainUnit());
    JPA.em().persist(a403);
}




    // == A404
    // Consommation de diesel

    
DoubleQuestion a404 = (DoubleQuestion) questionService.findByCode(QuestionCode.A404);
if (a404 == null) {
    a404 = new DoubleQuestion( a402, 0, QuestionCode.A404, volumeUnits, null, volumeUnits.getMainUnit() );
    JPA.em().persist(a404);
} else {
    a404.setDefaultValue(null);
    if (!a404.getQuestionSet().equals(a402) && a402.getQuestions().contains(a404)) {
        a402.getQuestions().remove(a404);
        JPA.em().persist(a402);
    }
    if (a404.getQuestionSet().equals(a402) && !a402.getQuestions().contains(a404)) {
        a402.getQuestions().add(a404);
        JPA.em().persist(a402);
    }
    a404.setUnitCategory(volumeUnits);
    a404.setOrderIndex(0);
    a404.setDefaultUnit(volumeUnits.getMainUnit());
    JPA.em().persist(a404);
}




    // == A405
    // Consommation de gaz de pétrole liquéfié (GPL)

    
DoubleQuestion a405 = (DoubleQuestion) questionService.findByCode(QuestionCode.A405);
if (a405 == null) {
    a405 = new DoubleQuestion( a402, 0, QuestionCode.A405, volumeUnits, null, volumeUnits.getMainUnit() );
    JPA.em().persist(a405);
} else {
    a405.setDefaultValue(null);
    if (!a405.getQuestionSet().equals(a402) && a402.getQuestions().contains(a405)) {
        a402.getQuestions().remove(a405);
        JPA.em().persist(a402);
    }
    if (a405.getQuestionSet().equals(a402) && !a402.getQuestions().contains(a405)) {
        a402.getQuestions().add(a405);
        JPA.em().persist(a402);
    }
    a405.setUnitCategory(volumeUnits);
    a405.setOrderIndex(0);
    a405.setDefaultUnit(volumeUnits.getMainUnit());
    JPA.em().persist(a405);
}




    // == A408
    // Catégorie de véhicule

    StringQuestion a408 = (StringQuestion) questionService.findByCode(QuestionCode.A408);
if (a408 == null) {
    a408 = new StringQuestion(a407, 0, QuestionCode.A408, null);
    JPA.em().persist(a408);
} else {
    a408.setDefaultValue(null);
    if (!a408.getQuestionSet().equals(a407) && a407.getQuestions().contains(a408)) {
        a407.getQuestions().remove(a408);
        JPA.em().persist(a407);
    }
    if (a408.getQuestionSet().equals(a407) && !a407.getQuestions().contains(a408)) {
        a407.getQuestions().add(a408);
        JPA.em().persist(a407);
    }
    a408.setOrderIndex(0);
    JPA.em().persist(a408);
}


    // == A409
    // Quel type de carburant utilise-t-il ?

    ValueSelectionQuestion a409 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A409);
if (a409 == null) {
    a409 = new ValueSelectionQuestion(a407, 0, QuestionCode.A409, CodeList.CARBURANT);
    JPA.em().persist(a409);
} else {
    if (!a409.getQuestionSet().equals(a407) && a407.getQuestions().contains(a409)) {
        a407.getQuestions().remove(a409);
        JPA.em().persist(a407);
    }
    if (a409.getQuestionSet().equals(a407) && !a407.getQuestions().contains(a409)) {
        a407.getQuestions().add(a409);
        JPA.em().persist(a407);
    }
    a409.setOrderIndex(0);
    a409.setCodeList(CodeList.CARBURANT);
    JPA.em().persist(a409);
}


    // == A410
    // Consommation moyenne (L/100km)

    
IntegerQuestion a410 = (IntegerQuestion) questionService.findByCode(QuestionCode.A410);
if (a410 == null) {
    a410 = new IntegerQuestion(a407, 0, QuestionCode.A410, null, null);
    JPA.em().persist(a410);
} else {
    a410.setDefaultValue(null);
    if (!a410.getQuestionSet().equals(a407) && a407.getQuestions().contains(a410)) {
        a407.getQuestions().remove(a410);
        JPA.em().persist(a407);
    }
    if (a410.getQuestionSet().equals(a407) && !a407.getQuestions().contains(a410)) {
        a407.getQuestions().add(a410);
        JPA.em().persist(a407);
    }
    a410.setOrderIndex(0);
    a410.setUnitCategory(null);
    JPA.em().persist(a410);
}


    // == A411
    // Quelle est le nombre de kilomètres parcourus par an?

    
IntegerQuestion a411 = (IntegerQuestion) questionService.findByCode(QuestionCode.A411);
if (a411 == null) {
    a411 = new IntegerQuestion(a407, 0, QuestionCode.A411, null, null);
    JPA.em().persist(a411);
} else {
    a411.setDefaultValue(null);
    if (!a411.getQuestionSet().equals(a407) && a407.getQuestions().contains(a411)) {
        a407.getQuestions().remove(a411);
        JPA.em().persist(a407);
    }
    if (a411.getQuestionSet().equals(a407) && !a407.getQuestions().contains(a411)) {
        a407.getQuestions().add(a411);
        JPA.em().persist(a407);
    }
    a411.setOrderIndex(0);
    a411.setUnitCategory(null);
    JPA.em().persist(a411);
}


    // == A414
    // Catégorie de véhicule

    StringQuestion a414 = (StringQuestion) questionService.findByCode(QuestionCode.A414);
if (a414 == null) {
    a414 = new StringQuestion(a413, 0, QuestionCode.A414, null);
    JPA.em().persist(a414);
} else {
    a414.setDefaultValue(null);
    if (!a414.getQuestionSet().equals(a413) && a413.getQuestions().contains(a414)) {
        a413.getQuestions().remove(a414);
        JPA.em().persist(a413);
    }
    if (a414.getQuestionSet().equals(a413) && !a413.getQuestions().contains(a414)) {
        a413.getQuestions().add(a414);
        JPA.em().persist(a413);
    }
    a414.setOrderIndex(0);
    JPA.em().persist(a414);
}


    // == A415
    // Quel type de carburant utilise-t-il ?

    ValueSelectionQuestion a415 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A415);
if (a415 == null) {
    a415 = new ValueSelectionQuestion(a413, 0, QuestionCode.A415, CodeList.CARBURANT);
    JPA.em().persist(a415);
} else {
    if (!a415.getQuestionSet().equals(a413) && a413.getQuestions().contains(a415)) {
        a413.getQuestions().remove(a415);
        JPA.em().persist(a413);
    }
    if (a415.getQuestionSet().equals(a413) && !a413.getQuestions().contains(a415)) {
        a413.getQuestions().add(a415);
        JPA.em().persist(a413);
    }
    a415.setOrderIndex(0);
    a415.setCodeList(CodeList.CARBURANT);
    JPA.em().persist(a415);
}


    // == A416
    // Prix moyen du litre de ce carburant

    
DoubleQuestion a416 = (DoubleQuestion) questionService.findByCode(QuestionCode.A416);
if (a416 == null) {
    a416 = new DoubleQuestion( a413, 0, QuestionCode.A416, moneyUnits, null, getUnitBySymbol("EUR") );
    JPA.em().persist(a416);
} else {
    a416.setDefaultValue(null);
    if (!a416.getQuestionSet().equals(a413) && a413.getQuestions().contains(a416)) {
        a413.getQuestions().remove(a416);
        JPA.em().persist(a413);
    }
    if (a416.getQuestionSet().equals(a413) && !a413.getQuestions().contains(a416)) {
        a413.getQuestions().add(a416);
        JPA.em().persist(a413);
    }
    a416.setUnitCategory(moneyUnits);
    a416.setOrderIndex(0);
    a416.setDefaultUnit(getUnitBySymbol("EUR"));
    JPA.em().persist(a416);
}




    // == A417
    // Quel est le montant annuel de dépenses en carburant?

    
DoubleQuestion a417 = (DoubleQuestion) questionService.findByCode(QuestionCode.A417);
if (a417 == null) {
    a417 = new DoubleQuestion( a413, 0, QuestionCode.A417, moneyUnits, null, getUnitBySymbol("EUR") );
    JPA.em().persist(a417);
} else {
    a417.setDefaultValue(null);
    if (!a417.getQuestionSet().equals(a413) && a413.getQuestions().contains(a417)) {
        a413.getQuestions().remove(a417);
        JPA.em().persist(a413);
    }
    if (a417.getQuestionSet().equals(a413) && !a413.getQuestions().contains(a417)) {
        a413.getQuestions().add(a417);
        JPA.em().persist(a413);
    }
    a417.setUnitCategory(moneyUnits);
    a417.setOrderIndex(0);
    a417.setDefaultUnit(getUnitBySymbol("EUR"));
    JPA.em().persist(a417);
}




    // == A503
    // Consommation d'essence

    
DoubleQuestion a503 = (DoubleQuestion) questionService.findByCode(QuestionCode.A503);
if (a503 == null) {
    a503 = new DoubleQuestion( a502, 0, QuestionCode.A503, volumeUnits, null, volumeUnits.getMainUnit() );
    JPA.em().persist(a503);
} else {
    a503.setDefaultValue(null);
    if (!a503.getQuestionSet().equals(a502) && a502.getQuestions().contains(a503)) {
        a502.getQuestions().remove(a503);
        JPA.em().persist(a502);
    }
    if (a503.getQuestionSet().equals(a502) && !a502.getQuestions().contains(a503)) {
        a502.getQuestions().add(a503);
        JPA.em().persist(a502);
    }
    a503.setUnitCategory(volumeUnits);
    a503.setOrderIndex(0);
    a503.setDefaultUnit(volumeUnits.getMainUnit());
    JPA.em().persist(a503);
}




    // == A504
    // Consommation de diesel

    
DoubleQuestion a504 = (DoubleQuestion) questionService.findByCode(QuestionCode.A504);
if (a504 == null) {
    a504 = new DoubleQuestion( a502, 0, QuestionCode.A504, volumeUnits, null, volumeUnits.getMainUnit() );
    JPA.em().persist(a504);
} else {
    a504.setDefaultValue(null);
    if (!a504.getQuestionSet().equals(a502) && a502.getQuestions().contains(a504)) {
        a502.getQuestions().remove(a504);
        JPA.em().persist(a502);
    }
    if (a504.getQuestionSet().equals(a502) && !a502.getQuestions().contains(a504)) {
        a502.getQuestions().add(a504);
        JPA.em().persist(a502);
    }
    a504.setUnitCategory(volumeUnits);
    a504.setOrderIndex(0);
    a504.setDefaultUnit(volumeUnits.getMainUnit());
    JPA.em().persist(a504);
}




    // == A505
    // Consommation de gaz de pétrole liquéfié (GPL)

    
DoubleQuestion a505 = (DoubleQuestion) questionService.findByCode(QuestionCode.A505);
if (a505 == null) {
    a505 = new DoubleQuestion( a502, 0, QuestionCode.A505, volumeUnits, null, volumeUnits.getMainUnit() );
    JPA.em().persist(a505);
} else {
    a505.setDefaultValue(null);
    if (!a505.getQuestionSet().equals(a502) && a502.getQuestions().contains(a505)) {
        a502.getQuestions().remove(a505);
        JPA.em().persist(a502);
    }
    if (a505.getQuestionSet().equals(a502) && !a502.getQuestions().contains(a505)) {
        a502.getQuestions().add(a505);
        JPA.em().persist(a502);
    }
    a505.setUnitCategory(volumeUnits);
    a505.setOrderIndex(0);
    a505.setDefaultUnit(volumeUnits.getMainUnit());
    JPA.em().persist(a505);
}




    // == A508
    // Catégorie de véhicule

    StringQuestion a508 = (StringQuestion) questionService.findByCode(QuestionCode.A508);
if (a508 == null) {
    a508 = new StringQuestion(a507, 0, QuestionCode.A508, null);
    JPA.em().persist(a508);
} else {
    a508.setDefaultValue(null);
    if (!a508.getQuestionSet().equals(a507) && a507.getQuestions().contains(a508)) {
        a507.getQuestions().remove(a508);
        JPA.em().persist(a507);
    }
    if (a508.getQuestionSet().equals(a507) && !a507.getQuestions().contains(a508)) {
        a507.getQuestions().add(a508);
        JPA.em().persist(a507);
    }
    a508.setOrderIndex(0);
    JPA.em().persist(a508);
}


    // == A509
    // Quel type de carburant utilise-t-il ?

    ValueSelectionQuestion a509 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A509);
if (a509 == null) {
    a509 = new ValueSelectionQuestion(a507, 0, QuestionCode.A509, CodeList.CARBURANT);
    JPA.em().persist(a509);
} else {
    if (!a509.getQuestionSet().equals(a507) && a507.getQuestions().contains(a509)) {
        a507.getQuestions().remove(a509);
        JPA.em().persist(a507);
    }
    if (a509.getQuestionSet().equals(a507) && !a507.getQuestions().contains(a509)) {
        a507.getQuestions().add(a509);
        JPA.em().persist(a507);
    }
    a509.setOrderIndex(0);
    a509.setCodeList(CodeList.CARBURANT);
    JPA.em().persist(a509);
}


    // == A510
    // Consommation moyenne (L/100km)

    
IntegerQuestion a510 = (IntegerQuestion) questionService.findByCode(QuestionCode.A510);
if (a510 == null) {
    a510 = new IntegerQuestion(a507, 0, QuestionCode.A510, null, null);
    JPA.em().persist(a510);
} else {
    a510.setDefaultValue(null);
    if (!a510.getQuestionSet().equals(a507) && a507.getQuestions().contains(a510)) {
        a507.getQuestions().remove(a510);
        JPA.em().persist(a507);
    }
    if (a510.getQuestionSet().equals(a507) && !a507.getQuestions().contains(a510)) {
        a507.getQuestions().add(a510);
        JPA.em().persist(a507);
    }
    a510.setOrderIndex(0);
    a510.setUnitCategory(null);
    JPA.em().persist(a510);
}


    // == A511
    // Quelle est le nombre de kilomètres parcourus par an?

    
IntegerQuestion a511 = (IntegerQuestion) questionService.findByCode(QuestionCode.A511);
if (a511 == null) {
    a511 = new IntegerQuestion(a507, 0, QuestionCode.A511, null, null);
    JPA.em().persist(a511);
} else {
    a511.setDefaultValue(null);
    if (!a511.getQuestionSet().equals(a507) && a507.getQuestions().contains(a511)) {
        a507.getQuestions().remove(a511);
        JPA.em().persist(a507);
    }
    if (a511.getQuestionSet().equals(a507) && !a507.getQuestions().contains(a511)) {
        a507.getQuestions().add(a511);
        JPA.em().persist(a507);
    }
    a511.setOrderIndex(0);
    a511.setUnitCategory(null);
    JPA.em().persist(a511);
}


    // == A514
    // Catégorie de véhicule

    StringQuestion a514 = (StringQuestion) questionService.findByCode(QuestionCode.A514);
if (a514 == null) {
    a514 = new StringQuestion(a513, 0, QuestionCode.A514, null);
    JPA.em().persist(a514);
} else {
    a514.setDefaultValue(null);
    if (!a514.getQuestionSet().equals(a513) && a513.getQuestions().contains(a514)) {
        a513.getQuestions().remove(a514);
        JPA.em().persist(a513);
    }
    if (a514.getQuestionSet().equals(a513) && !a513.getQuestions().contains(a514)) {
        a513.getQuestions().add(a514);
        JPA.em().persist(a513);
    }
    a514.setOrderIndex(0);
    JPA.em().persist(a514);
}


    // == A515
    // Quel type de carburant utilise-t-il ?

    ValueSelectionQuestion a515 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A515);
if (a515 == null) {
    a515 = new ValueSelectionQuestion(a513, 0, QuestionCode.A515, CodeList.CARBURANT);
    JPA.em().persist(a515);
} else {
    if (!a515.getQuestionSet().equals(a513) && a513.getQuestions().contains(a515)) {
        a513.getQuestions().remove(a515);
        JPA.em().persist(a513);
    }
    if (a515.getQuestionSet().equals(a513) && !a513.getQuestions().contains(a515)) {
        a513.getQuestions().add(a515);
        JPA.em().persist(a513);
    }
    a515.setOrderIndex(0);
    a515.setCodeList(CodeList.CARBURANT);
    JPA.em().persist(a515);
}


    // == A516
    // Prix moyen du litre de ce carburant

    
DoubleQuestion a516 = (DoubleQuestion) questionService.findByCode(QuestionCode.A516);
if (a516 == null) {
    a516 = new DoubleQuestion( a513, 0, QuestionCode.A516, moneyUnits, null, getUnitBySymbol("EUR") );
    JPA.em().persist(a516);
} else {
    a516.setDefaultValue(null);
    if (!a516.getQuestionSet().equals(a513) && a513.getQuestions().contains(a516)) {
        a513.getQuestions().remove(a516);
        JPA.em().persist(a513);
    }
    if (a516.getQuestionSet().equals(a513) && !a513.getQuestions().contains(a516)) {
        a513.getQuestions().add(a516);
        JPA.em().persist(a513);
    }
    a516.setUnitCategory(moneyUnits);
    a516.setOrderIndex(0);
    a516.setDefaultUnit(getUnitBySymbol("EUR"));
    JPA.em().persist(a516);
}




    // == A517
    // Quel est le montant annuel de dépenses en carburant?

    
DoubleQuestion a517 = (DoubleQuestion) questionService.findByCode(QuestionCode.A517);
if (a517 == null) {
    a517 = new DoubleQuestion( a513, 0, QuestionCode.A517, moneyUnits, null, getUnitBySymbol("EUR") );
    JPA.em().persist(a517);
} else {
    a517.setDefaultValue(null);
    if (!a517.getQuestionSet().equals(a513) && a513.getQuestions().contains(a517)) {
        a513.getQuestions().remove(a517);
        JPA.em().persist(a513);
    }
    if (a517.getQuestionSet().equals(a513) && !a513.getQuestions().contains(a517)) {
        a513.getQuestions().add(a517);
        JPA.em().persist(a513);
    }
    a517.setUnitCategory(moneyUnits);
    a517.setOrderIndex(0);
    a517.setDefaultUnit(getUnitBySymbol("EUR"));
    JPA.em().persist(a517);
}




    // == A603
    // Consommation d'essence

    
DoubleQuestion a603 = (DoubleQuestion) questionService.findByCode(QuestionCode.A603);
if (a603 == null) {
    a603 = new DoubleQuestion( a602, 0, QuestionCode.A603, volumeUnits, null, volumeUnits.getMainUnit() );
    JPA.em().persist(a603);
} else {
    a603.setDefaultValue(null);
    if (!a603.getQuestionSet().equals(a602) && a602.getQuestions().contains(a603)) {
        a602.getQuestions().remove(a603);
        JPA.em().persist(a602);
    }
    if (a603.getQuestionSet().equals(a602) && !a602.getQuestions().contains(a603)) {
        a602.getQuestions().add(a603);
        JPA.em().persist(a602);
    }
    a603.setUnitCategory(volumeUnits);
    a603.setOrderIndex(0);
    a603.setDefaultUnit(volumeUnits.getMainUnit());
    JPA.em().persist(a603);
}




    // == A604
    // Consommation de diesel

    
DoubleQuestion a604 = (DoubleQuestion) questionService.findByCode(QuestionCode.A604);
if (a604 == null) {
    a604 = new DoubleQuestion( a602, 0, QuestionCode.A604, volumeUnits, null, volumeUnits.getMainUnit() );
    JPA.em().persist(a604);
} else {
    a604.setDefaultValue(null);
    if (!a604.getQuestionSet().equals(a602) && a602.getQuestions().contains(a604)) {
        a602.getQuestions().remove(a604);
        JPA.em().persist(a602);
    }
    if (a604.getQuestionSet().equals(a602) && !a602.getQuestions().contains(a604)) {
        a602.getQuestions().add(a604);
        JPA.em().persist(a602);
    }
    a604.setUnitCategory(volumeUnits);
    a604.setOrderIndex(0);
    a604.setDefaultUnit(volumeUnits.getMainUnit());
    JPA.em().persist(a604);
}




    // == A605
    // Consommation de gaz de pétrole liquéfié (GPL)

    
DoubleQuestion a605 = (DoubleQuestion) questionService.findByCode(QuestionCode.A605);
if (a605 == null) {
    a605 = new DoubleQuestion( a602, 0, QuestionCode.A605, volumeUnits, null, volumeUnits.getMainUnit() );
    JPA.em().persist(a605);
} else {
    a605.setDefaultValue(null);
    if (!a605.getQuestionSet().equals(a602) && a602.getQuestions().contains(a605)) {
        a602.getQuestions().remove(a605);
        JPA.em().persist(a602);
    }
    if (a605.getQuestionSet().equals(a602) && !a602.getQuestions().contains(a605)) {
        a602.getQuestions().add(a605);
        JPA.em().persist(a602);
    }
    a605.setUnitCategory(volumeUnits);
    a605.setOrderIndex(0);
    a605.setDefaultUnit(volumeUnits.getMainUnit());
    JPA.em().persist(a605);
}




    // == A608
    // Catégorie de véhicule

    StringQuestion a608 = (StringQuestion) questionService.findByCode(QuestionCode.A608);
if (a608 == null) {
    a608 = new StringQuestion(a607, 0, QuestionCode.A608, null);
    JPA.em().persist(a608);
} else {
    a608.setDefaultValue(null);
    if (!a608.getQuestionSet().equals(a607) && a607.getQuestions().contains(a608)) {
        a607.getQuestions().remove(a608);
        JPA.em().persist(a607);
    }
    if (a608.getQuestionSet().equals(a607) && !a607.getQuestions().contains(a608)) {
        a607.getQuestions().add(a608);
        JPA.em().persist(a607);
    }
    a608.setOrderIndex(0);
    JPA.em().persist(a608);
}


    // == A609
    // Quel type de carburant utilise-t-il ?

    ValueSelectionQuestion a609 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A609);
if (a609 == null) {
    a609 = new ValueSelectionQuestion(a607, 0, QuestionCode.A609, CodeList.CARBURANT);
    JPA.em().persist(a609);
} else {
    if (!a609.getQuestionSet().equals(a607) && a607.getQuestions().contains(a609)) {
        a607.getQuestions().remove(a609);
        JPA.em().persist(a607);
    }
    if (a609.getQuestionSet().equals(a607) && !a607.getQuestions().contains(a609)) {
        a607.getQuestions().add(a609);
        JPA.em().persist(a607);
    }
    a609.setOrderIndex(0);
    a609.setCodeList(CodeList.CARBURANT);
    JPA.em().persist(a609);
}


    // == A610
    // Consommation moyenne (L/100km)

    
IntegerQuestion a610 = (IntegerQuestion) questionService.findByCode(QuestionCode.A610);
if (a610 == null) {
    a610 = new IntegerQuestion(a607, 0, QuestionCode.A610, null, null);
    JPA.em().persist(a610);
} else {
    a610.setDefaultValue(null);
    if (!a610.getQuestionSet().equals(a607) && a607.getQuestions().contains(a610)) {
        a607.getQuestions().remove(a610);
        JPA.em().persist(a607);
    }
    if (a610.getQuestionSet().equals(a607) && !a607.getQuestions().contains(a610)) {
        a607.getQuestions().add(a610);
        JPA.em().persist(a607);
    }
    a610.setOrderIndex(0);
    a610.setUnitCategory(null);
    JPA.em().persist(a610);
}


    // == A611
    // Quelle est le nombre de kilomètres parcourus par an?

    
IntegerQuestion a611 = (IntegerQuestion) questionService.findByCode(QuestionCode.A611);
if (a611 == null) {
    a611 = new IntegerQuestion(a607, 0, QuestionCode.A611, null, null);
    JPA.em().persist(a611);
} else {
    a611.setDefaultValue(null);
    if (!a611.getQuestionSet().equals(a607) && a607.getQuestions().contains(a611)) {
        a607.getQuestions().remove(a611);
        JPA.em().persist(a607);
    }
    if (a611.getQuestionSet().equals(a607) && !a607.getQuestions().contains(a611)) {
        a607.getQuestions().add(a611);
        JPA.em().persist(a607);
    }
    a611.setOrderIndex(0);
    a611.setUnitCategory(null);
    JPA.em().persist(a611);
}


    // == A614
    // Catégorie de véhicule

    StringQuestion a614 = (StringQuestion) questionService.findByCode(QuestionCode.A614);
if (a614 == null) {
    a614 = new StringQuestion(a613, 0, QuestionCode.A614, null);
    JPA.em().persist(a614);
} else {
    a614.setDefaultValue(null);
    if (!a614.getQuestionSet().equals(a613) && a613.getQuestions().contains(a614)) {
        a613.getQuestions().remove(a614);
        JPA.em().persist(a613);
    }
    if (a614.getQuestionSet().equals(a613) && !a613.getQuestions().contains(a614)) {
        a613.getQuestions().add(a614);
        JPA.em().persist(a613);
    }
    a614.setOrderIndex(0);
    JPA.em().persist(a614);
}


    // == A615
    // Quel type de carburant utilise-t-il ?

    ValueSelectionQuestion a615 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A615);
if (a615 == null) {
    a615 = new ValueSelectionQuestion(a613, 0, QuestionCode.A615, CodeList.CARBURANT);
    JPA.em().persist(a615);
} else {
    if (!a615.getQuestionSet().equals(a613) && a613.getQuestions().contains(a615)) {
        a613.getQuestions().remove(a615);
        JPA.em().persist(a613);
    }
    if (a615.getQuestionSet().equals(a613) && !a613.getQuestions().contains(a615)) {
        a613.getQuestions().add(a615);
        JPA.em().persist(a613);
    }
    a615.setOrderIndex(0);
    a615.setCodeList(CodeList.CARBURANT);
    JPA.em().persist(a615);
}


    // == A616
    // Prix moyen du litre de ce carburant

    
DoubleQuestion a616 = (DoubleQuestion) questionService.findByCode(QuestionCode.A616);
if (a616 == null) {
    a616 = new DoubleQuestion( a613, 0, QuestionCode.A616, moneyUnits, null, getUnitBySymbol("EUR") );
    JPA.em().persist(a616);
} else {
    a616.setDefaultValue(null);
    if (!a616.getQuestionSet().equals(a613) && a613.getQuestions().contains(a616)) {
        a613.getQuestions().remove(a616);
        JPA.em().persist(a613);
    }
    if (a616.getQuestionSet().equals(a613) && !a613.getQuestions().contains(a616)) {
        a613.getQuestions().add(a616);
        JPA.em().persist(a613);
    }
    a616.setUnitCategory(moneyUnits);
    a616.setOrderIndex(0);
    a616.setDefaultUnit(getUnitBySymbol("EUR"));
    JPA.em().persist(a616);
}




    // == A617
    // Quel est le montant annuel de dépenses en carburant?

    
DoubleQuestion a617 = (DoubleQuestion) questionService.findByCode(QuestionCode.A617);
if (a617 == null) {
    a617 = new DoubleQuestion( a613, 0, QuestionCode.A617, moneyUnits, null, getUnitBySymbol("EUR") );
    JPA.em().persist(a617);
} else {
    a617.setDefaultValue(null);
    if (!a617.getQuestionSet().equals(a613) && a613.getQuestions().contains(a617)) {
        a613.getQuestions().remove(a617);
        JPA.em().persist(a613);
    }
    if (a617.getQuestionSet().equals(a613) && !a613.getQuestions().contains(a617)) {
        a613.getQuestions().add(a617);
        JPA.em().persist(a613);
    }
    a617.setUnitCategory(moneyUnits);
    a617.setOrderIndex(0);
    a617.setDefaultUnit(getUnitBySymbol("EUR"));
    JPA.em().persist(a617);
}




    // == A95
    // Bus TEC pour déplacement domicile-travail des employés (en km.passagers)

    
IntegerQuestion a95 = (IntegerQuestion) questionService.findByCode(QuestionCode.A95);
if (a95 == null) {
    a95 = new IntegerQuestion(a94, 0, QuestionCode.A95, null, null);
    JPA.em().persist(a95);
} else {
    a95.setDefaultValue(null);
    if (!a95.getQuestionSet().equals(a94) && a94.getQuestions().contains(a95)) {
        a94.getQuestions().remove(a95);
        JPA.em().persist(a94);
    }
    if (a95.getQuestionSet().equals(a94) && !a94.getQuestions().contains(a95)) {
        a94.getQuestions().add(a95);
        JPA.em().persist(a94);
    }
    a95.setOrderIndex(0);
    a95.setUnitCategory(null);
    JPA.em().persist(a95);
}


    // == A96
    // Bus TEC pour déplacements professionnels & des visiteurs (en km.passagers)

    
IntegerQuestion a96 = (IntegerQuestion) questionService.findByCode(QuestionCode.A96);
if (a96 == null) {
    a96 = new IntegerQuestion(a94, 0, QuestionCode.A96, null, null);
    JPA.em().persist(a96);
} else {
    a96.setDefaultValue(null);
    if (!a96.getQuestionSet().equals(a94) && a94.getQuestions().contains(a96)) {
        a94.getQuestions().remove(a96);
        JPA.em().persist(a94);
    }
    if (a96.getQuestionSet().equals(a94) && !a94.getQuestions().contains(a96)) {
        a94.getQuestions().add(a96);
        JPA.em().persist(a94);
    }
    a96.setOrderIndex(0);
    a96.setUnitCategory(null);
    JPA.em().persist(a96);
}


    // == A97
    // Métro pour déplacement domicile-travail des employés (en km.passagers)

    
IntegerQuestion a97 = (IntegerQuestion) questionService.findByCode(QuestionCode.A97);
if (a97 == null) {
    a97 = new IntegerQuestion(a94, 0, QuestionCode.A97, null, null);
    JPA.em().persist(a97);
} else {
    a97.setDefaultValue(null);
    if (!a97.getQuestionSet().equals(a94) && a94.getQuestions().contains(a97)) {
        a94.getQuestions().remove(a97);
        JPA.em().persist(a94);
    }
    if (a97.getQuestionSet().equals(a94) && !a94.getQuestions().contains(a97)) {
        a94.getQuestions().add(a97);
        JPA.em().persist(a94);
    }
    a97.setOrderIndex(0);
    a97.setUnitCategory(null);
    JPA.em().persist(a97);
}


    // == A98
    // Métro pour déplacements professionnels & des visiteurs (en km.passagers)

    
IntegerQuestion a98 = (IntegerQuestion) questionService.findByCode(QuestionCode.A98);
if (a98 == null) {
    a98 = new IntegerQuestion(a94, 0, QuestionCode.A98, null, null);
    JPA.em().persist(a98);
} else {
    a98.setDefaultValue(null);
    if (!a98.getQuestionSet().equals(a94) && a94.getQuestions().contains(a98)) {
        a94.getQuestions().remove(a98);
        JPA.em().persist(a94);
    }
    if (a98.getQuestionSet().equals(a94) && !a94.getQuestions().contains(a98)) {
        a94.getQuestions().add(a98);
        JPA.em().persist(a94);
    }
    a98.setOrderIndex(0);
    a98.setUnitCategory(null);
    JPA.em().persist(a98);
}


    // == A99
    // Train national SNCB pour déplacement domicile-travail des employés (en km.passagers)

    
IntegerQuestion a99 = (IntegerQuestion) questionService.findByCode(QuestionCode.A99);
if (a99 == null) {
    a99 = new IntegerQuestion(a94, 0, QuestionCode.A99, null, null);
    JPA.em().persist(a99);
} else {
    a99.setDefaultValue(null);
    if (!a99.getQuestionSet().equals(a94) && a94.getQuestions().contains(a99)) {
        a94.getQuestions().remove(a99);
        JPA.em().persist(a94);
    }
    if (a99.getQuestionSet().equals(a94) && !a94.getQuestions().contains(a99)) {
        a94.getQuestions().add(a99);
        JPA.em().persist(a94);
    }
    a99.setOrderIndex(0);
    a99.setUnitCategory(null);
    JPA.em().persist(a99);
}


    // == A100
    // Train national SNCB pour déplacements professionnels & des visiteurs (en km.passagers)

    
IntegerQuestion a100 = (IntegerQuestion) questionService.findByCode(QuestionCode.A100);
if (a100 == null) {
    a100 = new IntegerQuestion(a94, 0, QuestionCode.A100, null, null);
    JPA.em().persist(a100);
} else {
    a100.setDefaultValue(null);
    if (!a100.getQuestionSet().equals(a94) && a94.getQuestions().contains(a100)) {
        a94.getQuestions().remove(a100);
        JPA.em().persist(a94);
    }
    if (a100.getQuestionSet().equals(a94) && !a94.getQuestions().contains(a100)) {
        a94.getQuestions().add(a100);
        JPA.em().persist(a94);
    }
    a100.setOrderIndex(0);
    a100.setUnitCategory(null);
    JPA.em().persist(a100);
}


    // == A101
    // Train international (TGV) pour déplacement domicile-travail des employés (en km.passagers)

    
IntegerQuestion a101 = (IntegerQuestion) questionService.findByCode(QuestionCode.A101);
if (a101 == null) {
    a101 = new IntegerQuestion(a94, 0, QuestionCode.A101, null, null);
    JPA.em().persist(a101);
} else {
    a101.setDefaultValue(null);
    if (!a101.getQuestionSet().equals(a94) && a94.getQuestions().contains(a101)) {
        a94.getQuestions().remove(a101);
        JPA.em().persist(a94);
    }
    if (a101.getQuestionSet().equals(a94) && !a94.getQuestions().contains(a101)) {
        a94.getQuestions().add(a101);
        JPA.em().persist(a94);
    }
    a101.setOrderIndex(0);
    a101.setUnitCategory(null);
    JPA.em().persist(a101);
}


    // == A102
    // Train international (TGV) pour déplacements professionnels & des visiteurs (en km.passagers)

    
IntegerQuestion a102 = (IntegerQuestion) questionService.findByCode(QuestionCode.A102);
if (a102 == null) {
    a102 = new IntegerQuestion(a94, 0, QuestionCode.A102, null, null);
    JPA.em().persist(a102);
} else {
    a102.setDefaultValue(null);
    if (!a102.getQuestionSet().equals(a94) && a94.getQuestions().contains(a102)) {
        a94.getQuestions().remove(a102);
        JPA.em().persist(a94);
    }
    if (a102.getQuestionSet().equals(a94) && !a94.getQuestions().contains(a102)) {
        a94.getQuestions().add(a102);
        JPA.em().persist(a94);
    }
    a102.setOrderIndex(0);
    a102.setUnitCategory(null);
    JPA.em().persist(a102);
}


    // == A103
    // Tram pour déplacement domicile-travail des employés (en km.passagers)

    
IntegerQuestion a103 = (IntegerQuestion) questionService.findByCode(QuestionCode.A103);
if (a103 == null) {
    a103 = new IntegerQuestion(a94, 0, QuestionCode.A103, null, null);
    JPA.em().persist(a103);
} else {
    a103.setDefaultValue(null);
    if (!a103.getQuestionSet().equals(a94) && a94.getQuestions().contains(a103)) {
        a94.getQuestions().remove(a103);
        JPA.em().persist(a94);
    }
    if (a103.getQuestionSet().equals(a94) && !a94.getQuestions().contains(a103)) {
        a94.getQuestions().add(a103);
        JPA.em().persist(a94);
    }
    a103.setOrderIndex(0);
    a103.setUnitCategory(null);
    JPA.em().persist(a103);
}


    // == A104
    // Tram pour déplacements professionnels & des visiteurs (en km.passagers)

    
IntegerQuestion a104 = (IntegerQuestion) questionService.findByCode(QuestionCode.A104);
if (a104 == null) {
    a104 = new IntegerQuestion(a94, 0, QuestionCode.A104, null, null);
    JPA.em().persist(a104);
} else {
    a104.setDefaultValue(null);
    if (!a104.getQuestionSet().equals(a94) && a94.getQuestions().contains(a104)) {
        a94.getQuestions().remove(a104);
        JPA.em().persist(a94);
    }
    if (a104.getQuestionSet().equals(a94) && !a94.getQuestions().contains(a104)) {
        a94.getQuestions().add(a104);
        JPA.em().persist(a94);
    }
    a104.setOrderIndex(0);
    a104.setUnitCategory(null);
    JPA.em().persist(a104);
}


    // == A105
    // Taxi pour déplacement domicile-travail des employés (en véhicules.km)

    
IntegerQuestion a105 = (IntegerQuestion) questionService.findByCode(QuestionCode.A105);
if (a105 == null) {
    a105 = new IntegerQuestion(a94, 0, QuestionCode.A105, null, null);
    JPA.em().persist(a105);
} else {
    a105.setDefaultValue(null);
    if (!a105.getQuestionSet().equals(a94) && a94.getQuestions().contains(a105)) {
        a94.getQuestions().remove(a105);
        JPA.em().persist(a94);
    }
    if (a105.getQuestionSet().equals(a94) && !a94.getQuestions().contains(a105)) {
        a94.getQuestions().add(a105);
        JPA.em().persist(a94);
    }
    a105.setOrderIndex(0);
    a105.setUnitCategory(null);
    JPA.em().persist(a105);
}


    // == A106
    // Taxi pour déplacements professionnels & des visiteurs (en véhicules.km)

    
IntegerQuestion a106 = (IntegerQuestion) questionService.findByCode(QuestionCode.A106);
if (a106 == null) {
    a106 = new IntegerQuestion(a94, 0, QuestionCode.A106, null, null);
    JPA.em().persist(a106);
} else {
    a106.setDefaultValue(null);
    if (!a106.getQuestionSet().equals(a94) && a94.getQuestions().contains(a106)) {
        a94.getQuestions().remove(a106);
        JPA.em().persist(a94);
    }
    if (a106.getQuestionSet().equals(a94) && !a94.getQuestions().contains(a106)) {
        a94.getQuestions().add(a106);
        JPA.em().persist(a94);
    }
    a106.setOrderIndex(0);
    a106.setUnitCategory(null);
    JPA.em().persist(a106);
}


    // == A107
    // Taxi pour déplacement domicile-travail des employés (en valeur)

    
DoubleQuestion a107 = (DoubleQuestion) questionService.findByCode(QuestionCode.A107);
if (a107 == null) {
    a107 = new DoubleQuestion( a94, 0, QuestionCode.A107, moneyUnits, null, getUnitBySymbol("EUR") );
    JPA.em().persist(a107);
} else {
    a107.setDefaultValue(null);
    if (!a107.getQuestionSet().equals(a94) && a94.getQuestions().contains(a107)) {
        a94.getQuestions().remove(a107);
        JPA.em().persist(a94);
    }
    if (a107.getQuestionSet().equals(a94) && !a94.getQuestions().contains(a107)) {
        a94.getQuestions().add(a107);
        JPA.em().persist(a94);
    }
    a107.setUnitCategory(moneyUnits);
    a107.setOrderIndex(0);
    a107.setDefaultUnit(getUnitBySymbol("EUR"));
    JPA.em().persist(a107);
}




    // == A108
    // Taxi pour déplacements professionnels & des visiteurs (en valeur)

    
DoubleQuestion a108 = (DoubleQuestion) questionService.findByCode(QuestionCode.A108);
if (a108 == null) {
    a108 = new DoubleQuestion( a94, 0, QuestionCode.A108, moneyUnits, null, getUnitBySymbol("EUR") );
    JPA.em().persist(a108);
} else {
    a108.setDefaultValue(null);
    if (!a108.getQuestionSet().equals(a94) && a94.getQuestions().contains(a108)) {
        a94.getQuestions().remove(a108);
        JPA.em().persist(a94);
    }
    if (a108.getQuestionSet().equals(a94) && !a94.getQuestions().contains(a108)) {
        a94.getQuestions().add(a108);
        JPA.em().persist(a94);
    }
    a108.setUnitCategory(moneyUnits);
    a108.setOrderIndex(0);
    a108.setDefaultUnit(getUnitBySymbol("EUR"));
    JPA.em().persist(a108);
}




    // == A110
    // Etes-vous situés à proximité d'une gare (< 1 km)?

    BooleanQuestion a110 = (BooleanQuestion) questionService.findByCode(QuestionCode.A110);
if (a110 == null) {
    a110 = new BooleanQuestion(a109, 0, QuestionCode.A110, null);
    JPA.em().persist(a110);
} else {
    a110.setDefaultValue(null);
    if (!a110.getQuestionSet().equals(a109) && a109.getQuestions().contains(a110)) {
        a109.getQuestions().remove(a110);
        JPA.em().persist(a109);
    }
    if (a110.getQuestionSet().equals(a109) && !a109.getQuestions().contains(a110)) {
        a109.getQuestions().add(a110);
        JPA.em().persist(a109);
    }
    a110.setOrderIndex(0);
    JPA.em().persist(a110);
}


    // == A111
    // Etes-vous situés à proximité d'un arrêt de transport en commun (< 500 m)?

    BooleanQuestion a111 = (BooleanQuestion) questionService.findByCode(QuestionCode.A111);
if (a111 == null) {
    a111 = new BooleanQuestion(a109, 0, QuestionCode.A111, null);
    JPA.em().persist(a111);
} else {
    a111.setDefaultValue(null);
    if (!a111.getQuestionSet().equals(a109) && a109.getQuestions().contains(a111)) {
        a109.getQuestions().remove(a111);
        JPA.em().persist(a109);
    }
    if (a111.getQuestionSet().equals(a109) && !a109.getQuestions().contains(a111)) {
        a109.getQuestions().add(a111);
        JPA.em().persist(a109);
    }
    a111.setOrderIndex(0);
    JPA.em().persist(a111);
}


    // == A112
    // Etes-vous situés en Agglomération ?

    BooleanQuestion a112 = (BooleanQuestion) questionService.findByCode(QuestionCode.A112);
if (a112 == null) {
    a112 = new BooleanQuestion(a109, 0, QuestionCode.A112, null);
    JPA.em().persist(a112);
} else {
    a112.setDefaultValue(null);
    if (!a112.getQuestionSet().equals(a109) && a109.getQuestions().contains(a112)) {
        a109.getQuestions().remove(a112);
        JPA.em().persist(a109);
    }
    if (a112.getQuestionSet().equals(a109) && !a109.getQuestions().contains(a112)) {
        a109.getQuestions().add(a112);
        JPA.em().persist(a109);
    }
    a112.setOrderIndex(0);
    JPA.em().persist(a112);
}


    // == A116
    // Catégorie de vol

    StringQuestion a116 = (StringQuestion) questionService.findByCode(QuestionCode.A116);
if (a116 == null) {
    a116 = new StringQuestion(a115, 0, QuestionCode.A116, null);
    JPA.em().persist(a116);
} else {
    a116.setDefaultValue(null);
    if (!a116.getQuestionSet().equals(a115) && a115.getQuestions().contains(a116)) {
        a115.getQuestions().remove(a116);
        JPA.em().persist(a115);
    }
    if (a116.getQuestionSet().equals(a115) && !a115.getQuestions().contains(a116)) {
        a115.getQuestions().add(a116);
        JPA.em().persist(a115);
    }
    a116.setOrderIndex(0);
    JPA.em().persist(a116);
}


    // == A117
    // Type de vol

    ValueSelectionQuestion a117 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A117);
if (a117 == null) {
    a117 = new ValueSelectionQuestion(a115, 0, QuestionCode.A117, CodeList.TYPEVOL);
    JPA.em().persist(a117);
} else {
    if (!a117.getQuestionSet().equals(a115) && a115.getQuestions().contains(a117)) {
        a115.getQuestions().remove(a117);
        JPA.em().persist(a115);
    }
    if (a117.getQuestionSet().equals(a115) && !a115.getQuestions().contains(a117)) {
        a115.getQuestions().add(a117);
        JPA.em().persist(a115);
    }
    a117.setOrderIndex(0);
    a117.setCodeList(CodeList.TYPEVOL);
    JPA.em().persist(a117);
}


    // == A118
    // Classe du vol

    ValueSelectionQuestion a118 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A118);
if (a118 == null) {
    a118 = new ValueSelectionQuestion(a115, 0, QuestionCode.A118, CodeList.CATEGORIEVOL);
    JPA.em().persist(a118);
} else {
    if (!a118.getQuestionSet().equals(a115) && a115.getQuestions().contains(a118)) {
        a115.getQuestions().remove(a118);
        JPA.em().persist(a115);
    }
    if (a118.getQuestionSet().equals(a115) && !a115.getQuestions().contains(a118)) {
        a115.getQuestions().add(a118);
        JPA.em().persist(a115);
    }
    a118.setOrderIndex(0);
    a118.setCodeList(CodeList.CATEGORIEVOL);
    JPA.em().persist(a118);
}


    // == A119
    // Nombre de vols/an

    
IntegerQuestion a119 = (IntegerQuestion) questionService.findByCode(QuestionCode.A119);
if (a119 == null) {
    a119 = new IntegerQuestion(a115, 0, QuestionCode.A119, null, null);
    JPA.em().persist(a119);
} else {
    a119.setDefaultValue(null);
    if (!a119.getQuestionSet().equals(a115) && a115.getQuestions().contains(a119)) {
        a115.getQuestions().remove(a119);
        JPA.em().persist(a115);
    }
    if (a119.getQuestionSet().equals(a115) && !a115.getQuestions().contains(a119)) {
        a115.getQuestions().add(a119);
        JPA.em().persist(a115);
    }
    a119.setOrderIndex(0);
    a119.setUnitCategory(null);
    JPA.em().persist(a119);
}


    // == A120
    // Distance moyenne A/R (km)

    
DoubleQuestion a120 = (DoubleQuestion) questionService.findByCode(QuestionCode.A120);
if (a120 == null) {
    a120 = new DoubleQuestion( a115, 0, QuestionCode.A120, lengthUnits, null, getUnitBySymbol("km") );
    JPA.em().persist(a120);
} else {
    a120.setDefaultValue(null);
    if (!a120.getQuestionSet().equals(a115) && a115.getQuestions().contains(a120)) {
        a115.getQuestions().remove(a120);
        JPA.em().persist(a115);
    }
    if (a120.getQuestionSet().equals(a115) && !a115.getQuestions().contains(a120)) {
        a115.getQuestions().add(a120);
        JPA.em().persist(a115);
    }
    a120.setUnitCategory(lengthUnits);
    a120.setOrderIndex(0);
    a120.setDefaultUnit(getUnitBySymbol("km"));
    JPA.em().persist(a120);
}




    // == A122
    // % des employés qui réalisent des déplacements en avion

    PercentageQuestion a122 = (PercentageQuestion) questionService.findByCode(QuestionCode.A122);
if (a122 == null) {
    a122 = new PercentageQuestion(a121, 0, QuestionCode.A122, null);
    JPA.em().persist(a122);
} else {
    a122.setDefaultValue(null);
    if (!a122.getQuestionSet().equals(a121) && a121.getQuestions().contains(a122)) {
        a121.getQuestions().remove(a122);
        JPA.em().persist(a121);
    }
    if (a122.getQuestionSet().equals(a121) && !a121.getQuestions().contains(a122)) {
        a121.getQuestions().add(a122);
        JPA.em().persist(a121);
    }
    a122.setOrderIndex(0);
    JPA.em().persist(a122);
}


    // == A123
    // Connaissez-vous le nombre de km parcourus en avion?

    BooleanQuestion a123 = (BooleanQuestion) questionService.findByCode(QuestionCode.A123);
if (a123 == null) {
    a123 = new BooleanQuestion(a121, 0, QuestionCode.A123, null);
    JPA.em().persist(a123);
} else {
    a123.setDefaultValue(null);
    if (!a123.getQuestionSet().equals(a121) && a121.getQuestions().contains(a123)) {
        a121.getQuestions().remove(a123);
        JPA.em().persist(a121);
    }
    if (a123.getQuestionSet().equals(a121) && !a121.getQuestions().contains(a123)) {
        a121.getQuestions().add(a123);
        JPA.em().persist(a121);
    }
    a123.setOrderIndex(0);
    JPA.em().persist(a123);
}


    // == A124
    // Les voyages ont-ils lieu en Europe?

    BooleanQuestion a124 = (BooleanQuestion) questionService.findByCode(QuestionCode.A124);
if (a124 == null) {
    a124 = new BooleanQuestion(a121, 0, QuestionCode.A124, null);
    JPA.em().persist(a124);
} else {
    a124.setDefaultValue(null);
    if (!a124.getQuestionSet().equals(a121) && a121.getQuestions().contains(a124)) {
        a121.getQuestions().remove(a124);
        JPA.em().persist(a121);
    }
    if (a124.getQuestionSet().equals(a121) && !a121.getQuestions().contains(a124)) {
        a121.getQuestions().add(a124);
        JPA.em().persist(a121);
    }
    a124.setOrderIndex(0);
    JPA.em().persist(a124);
}


    // == A125
    // Km moyen assignés par employé voyageant

    
DoubleQuestion a125 = (DoubleQuestion) questionService.findByCode(QuestionCode.A125);
if (a125 == null) {
    a125 = new DoubleQuestion( a121, 0, QuestionCode.A125, lengthUnits, null, getUnitBySymbol("km") );
    JPA.em().persist(a125);
} else {
    a125.setDefaultValue(null);
    if (!a125.getQuestionSet().equals(a121) && a121.getQuestions().contains(a125)) {
        a121.getQuestions().remove(a125);
        JPA.em().persist(a121);
    }
    if (a125.getQuestionSet().equals(a121) && !a121.getQuestions().contains(a125)) {
        a121.getQuestions().add(a125);
        JPA.em().persist(a121);
    }
    a125.setUnitCategory(lengthUnits);
    a125.setOrderIndex(0);
    a125.setDefaultUnit(getUnitBySymbol("km"));
    JPA.em().persist(a125);
}




    // == A126
    // Km moyen assignés par employé voyageant

    
DoubleQuestion a126 = (DoubleQuestion) questionService.findByCode(QuestionCode.A126);
if (a126 == null) {
    a126 = new DoubleQuestion( a121, 0, QuestionCode.A126, lengthUnits, null, getUnitBySymbol("km") );
    JPA.em().persist(a126);
} else {
    a126.setDefaultValue(null);
    if (!a126.getQuestionSet().equals(a121) && a121.getQuestions().contains(a126)) {
        a121.getQuestions().remove(a126);
        JPA.em().persist(a121);
    }
    if (a126.getQuestionSet().equals(a121) && !a121.getQuestions().contains(a126)) {
        a121.getQuestions().add(a126);
        JPA.em().persist(a121);
    }
    a126.setUnitCategory(lengthUnits);
    a126.setOrderIndex(0);
    a126.setDefaultUnit(getUnitBySymbol("km"));
    JPA.em().persist(a126);
}




    // == A127
    // km moyen parcourus sur l'année:

    
DoubleQuestion a127 = (DoubleQuestion) questionService.findByCode(QuestionCode.A127);
if (a127 == null) {
    a127 = new DoubleQuestion( a121, 0, QuestionCode.A127, lengthUnits, null, getUnitBySymbol("km") );
    JPA.em().persist(a127);
} else {
    a127.setDefaultValue(null);
    if (!a127.getQuestionSet().equals(a121) && a121.getQuestions().contains(a127)) {
        a121.getQuestions().remove(a127);
        JPA.em().persist(a121);
    }
    if (a127.getQuestionSet().equals(a121) && !a121.getQuestions().contains(a127)) {
        a121.getQuestions().add(a127);
        JPA.em().persist(a121);
    }
    a127.setUnitCategory(lengthUnits);
    a127.setOrderIndex(0);
    a127.setDefaultUnit(getUnitBySymbol("km"));
    JPA.em().persist(a127);
}




    // == A206
    // Pièces documentaires liées aux achats

    DocumentQuestion a206 = (DocumentQuestion) questionService.findByCode(QuestionCode.A206);
if (a206 == null) {
    a206 = new DocumentQuestion(a205, 0, QuestionCode.A206);
    JPA.em().persist(a206);
} else {
    if (!a206.getQuestionSet().equals(a205) && a205.getQuestions().contains(a206)) {
        a205.getQuestions().remove(a206);
        JPA.em().persist(a205);
    }
    if (a206.getQuestionSet().equals(a205) && !a205.getQuestions().contains(a206)) {
        a205.getQuestions().add(a206);
        JPA.em().persist(a205);
    }
    a206.setOrderIndex(0);
    JPA.em().persist(a206);
}


    // == A210
    // Poste d'achat

    StringQuestion a210 = (StringQuestion) questionService.findByCode(QuestionCode.A210);
if (a210 == null) {
    a210 = new StringQuestion(a209, 0, QuestionCode.A210, null);
    JPA.em().persist(a210);
} else {
    a210.setDefaultValue(null);
    if (!a210.getQuestionSet().equals(a209) && a209.getQuestions().contains(a210)) {
        a209.getQuestions().remove(a210);
        JPA.em().persist(a209);
    }
    if (a210.getQuestionSet().equals(a209) && !a209.getQuestions().contains(a210)) {
        a209.getQuestions().add(a210);
        JPA.em().persist(a209);
    }
    a210.setOrderIndex(0);
    JPA.em().persist(a210);
}


    // == A211
    // Catégorie

    ValueSelectionQuestion a211 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A211);
if (a211 == null) {
    a211 = new ValueSelectionQuestion(a209, 0, QuestionCode.A211, CodeList.TYPEACHAT);
    JPA.em().persist(a211);
} else {
    if (!a211.getQuestionSet().equals(a209) && a209.getQuestions().contains(a211)) {
        a209.getQuestions().remove(a211);
        JPA.em().persist(a209);
    }
    if (a211.getQuestionSet().equals(a209) && !a209.getQuestions().contains(a211)) {
        a209.getQuestions().add(a211);
        JPA.em().persist(a209);
    }
    a211.setOrderIndex(0);
    a211.setCodeList(CodeList.TYPEACHAT);
    JPA.em().persist(a211);
}


    // == A212
    // Type

    ValueSelectionQuestion a212 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A212);
if (a212 == null) {
    a212 = new ValueSelectionQuestion(a209, 0, QuestionCode.A212, CodeList.ACHATMETAL);
    JPA.em().persist(a212);
} else {
    if (!a212.getQuestionSet().equals(a209) && a209.getQuestions().contains(a212)) {
        a209.getQuestions().remove(a212);
        JPA.em().persist(a209);
    }
    if (a212.getQuestionSet().equals(a209) && !a209.getQuestions().contains(a212)) {
        a209.getQuestions().add(a212);
        JPA.em().persist(a209);
    }
    a212.setOrderIndex(0);
    a212.setCodeList(CodeList.ACHATMETAL);
    JPA.em().persist(a212);
}


    // == A213
    // Type

    ValueSelectionQuestion a213 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A213);
if (a213 == null) {
    a213 = new ValueSelectionQuestion(a209, 0, QuestionCode.A213, CodeList.ACHATPLASTIQUE);
    JPA.em().persist(a213);
} else {
    if (!a213.getQuestionSet().equals(a209) && a209.getQuestions().contains(a213)) {
        a209.getQuestions().remove(a213);
        JPA.em().persist(a209);
    }
    if (a213.getQuestionSet().equals(a209) && !a209.getQuestions().contains(a213)) {
        a209.getQuestions().add(a213);
        JPA.em().persist(a209);
    }
    a213.setOrderIndex(0);
    a213.setCodeList(CodeList.ACHATPLASTIQUE);
    JPA.em().persist(a213);
}


    // == A214
    // Type

    ValueSelectionQuestion a214 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A214);
if (a214 == null) {
    a214 = new ValueSelectionQuestion(a209, 0, QuestionCode.A214, CodeList.ACHATPAPIER);
    JPA.em().persist(a214);
} else {
    if (!a214.getQuestionSet().equals(a209) && a209.getQuestions().contains(a214)) {
        a209.getQuestions().remove(a214);
        JPA.em().persist(a209);
    }
    if (a214.getQuestionSet().equals(a209) && !a209.getQuestions().contains(a214)) {
        a209.getQuestions().add(a214);
        JPA.em().persist(a209);
    }
    a214.setOrderIndex(0);
    a214.setCodeList(CodeList.ACHATPAPIER);
    JPA.em().persist(a214);
}


    // == A215
    // Type

    ValueSelectionQuestion a215 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A215);
if (a215 == null) {
    a215 = new ValueSelectionQuestion(a209, 0, QuestionCode.A215, CodeList.ACHATVERRE);
    JPA.em().persist(a215);
} else {
    if (!a215.getQuestionSet().equals(a209) && a209.getQuestions().contains(a215)) {
        a209.getQuestions().remove(a215);
        JPA.em().persist(a209);
    }
    if (a215.getQuestionSet().equals(a209) && !a209.getQuestions().contains(a215)) {
        a209.getQuestions().add(a215);
        JPA.em().persist(a209);
    }
    a215.setOrderIndex(0);
    a215.setCodeList(CodeList.ACHATVERRE);
    JPA.em().persist(a215);
}


    // == A216
    // Type

    ValueSelectionQuestion a216 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A216);
if (a216 == null) {
    a216 = new ValueSelectionQuestion(a209, 0, QuestionCode.A216, CodeList.ACHATCHIMIQUE);
    JPA.em().persist(a216);
} else {
    if (!a216.getQuestionSet().equals(a209) && a209.getQuestions().contains(a216)) {
        a209.getQuestions().remove(a216);
        JPA.em().persist(a209);
    }
    if (a216.getQuestionSet().equals(a209) && !a209.getQuestions().contains(a216)) {
        a209.getQuestions().add(a216);
        JPA.em().persist(a209);
    }
    a216.setOrderIndex(0);
    a216.setCodeList(CodeList.ACHATCHIMIQUE);
    JPA.em().persist(a216);
}


    // == A217
    // Type

    ValueSelectionQuestion a217 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A217);
if (a217 == null) {
    a217 = new ValueSelectionQuestion(a209, 0, QuestionCode.A217, CodeList.ACHATROUTE);
    JPA.em().persist(a217);
} else {
    if (!a217.getQuestionSet().equals(a209) && a209.getQuestions().contains(a217)) {
        a209.getQuestions().remove(a217);
        JPA.em().persist(a209);
    }
    if (a217.getQuestionSet().equals(a209) && !a209.getQuestions().contains(a217)) {
        a209.getQuestions().add(a217);
        JPA.em().persist(a209);
    }
    a217.setOrderIndex(0);
    a217.setCodeList(CodeList.ACHATROUTE);
    JPA.em().persist(a217);
}


    // == A218
    // Type

    ValueSelectionQuestion a218 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A218);
if (a218 == null) {
    a218 = new ValueSelectionQuestion(a209, 0, QuestionCode.A218, CodeList.ACHATAGRO);
    JPA.em().persist(a218);
} else {
    if (!a218.getQuestionSet().equals(a209) && a209.getQuestions().contains(a218)) {
        a209.getQuestions().remove(a218);
        JPA.em().persist(a209);
    }
    if (a218.getQuestionSet().equals(a209) && !a209.getQuestions().contains(a218)) {
        a209.getQuestions().add(a218);
        JPA.em().persist(a209);
    }
    a218.setOrderIndex(0);
    a218.setCodeList(CodeList.ACHATAGRO);
    JPA.em().persist(a218);
}


    // == A219
    // Type

    ValueSelectionQuestion a219 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A219);
if (a219 == null) {
    a219 = new ValueSelectionQuestion(a209, 0, QuestionCode.A219, CodeList.ACHATSERVICE);
    JPA.em().persist(a219);
} else {
    if (!a219.getQuestionSet().equals(a209) && a209.getQuestions().contains(a219)) {
        a209.getQuestions().remove(a219);
        JPA.em().persist(a209);
    }
    if (a219.getQuestionSet().equals(a209) && !a209.getQuestions().contains(a219)) {
        a209.getQuestions().add(a219);
        JPA.em().persist(a209);
    }
    a219.setOrderIndex(0);
    a219.setCodeList(CodeList.ACHATSERVICE);
    JPA.em().persist(a219);
}


    // == A220
    // Taux de recyclé

    PercentageQuestion a220 = (PercentageQuestion) questionService.findByCode(QuestionCode.A220);
if (a220 == null) {
    a220 = new PercentageQuestion(a209, 0, QuestionCode.A220, null);
    JPA.em().persist(a220);
} else {
    a220.setDefaultValue(null);
    if (!a220.getQuestionSet().equals(a209) && a209.getQuestions().contains(a220)) {
        a209.getQuestions().remove(a220);
        JPA.em().persist(a209);
    }
    if (a220.getQuestionSet().equals(a209) && !a209.getQuestions().contains(a220)) {
        a209.getQuestions().add(a220);
        JPA.em().persist(a209);
    }
    a220.setOrderIndex(0);
    JPA.em().persist(a220);
}


    // == A221
    // Quantité

    
DoubleQuestion a221 = (DoubleQuestion) questionService.findByCode(QuestionCode.A221);
if (a221 == null) {
    a221 = new DoubleQuestion( a209, 0, QuestionCode.A221, massUnits, null, getUnitBySymbol("t") );
    JPA.em().persist(a221);
} else {
    a221.setDefaultValue(null);
    if (!a221.getQuestionSet().equals(a209) && a209.getQuestions().contains(a221)) {
        a209.getQuestions().remove(a221);
        JPA.em().persist(a209);
    }
    if (a221.getQuestionSet().equals(a209) && !a209.getQuestions().contains(a221)) {
        a209.getQuestions().add(a221);
        JPA.em().persist(a209);
    }
    a221.setUnitCategory(massUnits);
    a221.setOrderIndex(0);
    a221.setDefaultUnit(getUnitBySymbol("t"));
    JPA.em().persist(a221);
}




    // == A222
    // Quantité

    
DoubleQuestion a222 = (DoubleQuestion) questionService.findByCode(QuestionCode.A222);
if (a222 == null) {
    a222 = new DoubleQuestion( a209, 0, QuestionCode.A222, moneyUnits, null, getUnitBySymbol("EUR") );
    JPA.em().persist(a222);
} else {
    a222.setDefaultValue(null);
    if (!a222.getQuestionSet().equals(a209) && a209.getQuestions().contains(a222)) {
        a209.getQuestions().remove(a222);
        JPA.em().persist(a209);
    }
    if (a222.getQuestionSet().equals(a209) && !a209.getQuestions().contains(a222)) {
        a209.getQuestions().add(a222);
        JPA.em().persist(a209);
    }
    a222.setUnitCategory(moneyUnits);
    a222.setOrderIndex(0);
    a222.setDefaultUnit(getUnitBySymbol("EUR"));
    JPA.em().persist(a222);
}




    // == A225
    // Poste d'achat

    StringQuestion a225 = (StringQuestion) questionService.findByCode(QuestionCode.A225);
if (a225 == null) {
    a225 = new StringQuestion(a224, 0, QuestionCode.A225, null);
    JPA.em().persist(a225);
} else {
    a225.setDefaultValue(null);
    if (!a225.getQuestionSet().equals(a224) && a224.getQuestions().contains(a225)) {
        a224.getQuestions().remove(a225);
        JPA.em().persist(a224);
    }
    if (a225.getQuestionSet().equals(a224) && !a224.getQuestions().contains(a225)) {
        a224.getQuestions().add(a225);
        JPA.em().persist(a224);
    }
    a225.setOrderIndex(0);
    JPA.em().persist(a225);
}


    // == A226
    // Quantité

    
IntegerQuestion a226 = (IntegerQuestion) questionService.findByCode(QuestionCode.A226);
if (a226 == null) {
    a226 = new IntegerQuestion(a224, 0, QuestionCode.A226, null, null);
    JPA.em().persist(a226);
} else {
    a226.setDefaultValue(null);
    if (!a226.getQuestionSet().equals(a224) && a224.getQuestions().contains(a226)) {
        a224.getQuestions().remove(a226);
        JPA.em().persist(a224);
    }
    if (a226.getQuestionSet().equals(a224) && !a224.getQuestions().contains(a226)) {
        a224.getQuestions().add(a226);
        JPA.em().persist(a224);
    }
    a226.setOrderIndex(0);
    a226.setUnitCategory(null);
    JPA.em().persist(a226);
}


    // == A227
    // Unité dans laquelle s'exprime cette quantité

    StringQuestion a227 = (StringQuestion) questionService.findByCode(QuestionCode.A227);
if (a227 == null) {
    a227 = new StringQuestion(a224, 0, QuestionCode.A227, null);
    JPA.em().persist(a227);
} else {
    a227.setDefaultValue(null);
    if (!a227.getQuestionSet().equals(a224) && a224.getQuestions().contains(a227)) {
        a224.getQuestions().remove(a227);
        JPA.em().persist(a224);
    }
    if (a227.getQuestionSet().equals(a224) && !a224.getQuestions().contains(a227)) {
        a224.getQuestions().add(a227);
        JPA.em().persist(a224);
    }
    a227.setOrderIndex(0);
    JPA.em().persist(a227);
}


    // == A228
    // Facteur d'émission en tCO2e par unité ci-dessus

    
IntegerQuestion a228 = (IntegerQuestion) questionService.findByCode(QuestionCode.A228);
if (a228 == null) {
    a228 = new IntegerQuestion(a224, 0, QuestionCode.A228, null, null);
    JPA.em().persist(a228);
} else {
    a228.setDefaultValue(null);
    if (!a228.getQuestionSet().equals(a224) && a224.getQuestions().contains(a228)) {
        a224.getQuestions().remove(a228);
        JPA.em().persist(a224);
    }
    if (a228.getQuestionSet().equals(a224) && !a224.getQuestions().contains(a228)) {
        a224.getQuestions().add(a228);
        JPA.em().persist(a224);
    }
    a228.setOrderIndex(0);
    a228.setUnitCategory(null);
    JPA.em().persist(a228);
}


    // == A129
    // Pièces documentaires liées au transport et stockage amont

    DocumentQuestion a129 = (DocumentQuestion) questionService.findByCode(QuestionCode.A129);
if (a129 == null) {
    a129 = new DocumentQuestion(a128, 0, QuestionCode.A129);
    JPA.em().persist(a129);
} else {
    if (!a129.getQuestionSet().equals(a128) && a128.getQuestions().contains(a129)) {
        a128.getQuestions().remove(a129);
        JPA.em().persist(a128);
    }
    if (a129.getQuestionSet().equals(a128) && !a128.getQuestions().contains(a129)) {
        a128.getQuestions().add(a129);
        JPA.em().persist(a128);
    }
    a129.setOrderIndex(0);
    JPA.em().persist(a129);
}


    // == A133
    // Consommation d'essence

    
DoubleQuestion a133 = (DoubleQuestion) questionService.findByCode(QuestionCode.A133);
if (a133 == null) {
    a133 = new DoubleQuestion( a132, 0, QuestionCode.A133, volumeUnits, null, getUnitBySymbol("l") );
    JPA.em().persist(a133);
} else {
    a133.setDefaultValue(null);
    if (!a133.getQuestionSet().equals(a132) && a132.getQuestions().contains(a133)) {
        a132.getQuestions().remove(a133);
        JPA.em().persist(a132);
    }
    if (a133.getQuestionSet().equals(a132) && !a132.getQuestions().contains(a133)) {
        a132.getQuestions().add(a133);
        JPA.em().persist(a132);
    }
    a133.setUnitCategory(volumeUnits);
    a133.setOrderIndex(0);
    a133.setDefaultUnit(getUnitBySymbol("l"));
    JPA.em().persist(a133);
}




    // == A134
    // Consommation de diesel

    
DoubleQuestion a134 = (DoubleQuestion) questionService.findByCode(QuestionCode.A134);
if (a134 == null) {
    a134 = new DoubleQuestion( a132, 0, QuestionCode.A134, volumeUnits, null, getUnitBySymbol("l") );
    JPA.em().persist(a134);
} else {
    a134.setDefaultValue(null);
    if (!a134.getQuestionSet().equals(a132) && a132.getQuestions().contains(a134)) {
        a132.getQuestions().remove(a134);
        JPA.em().persist(a132);
    }
    if (a134.getQuestionSet().equals(a132) && !a132.getQuestions().contains(a134)) {
        a132.getQuestions().add(a134);
        JPA.em().persist(a132);
    }
    a134.setUnitCategory(volumeUnits);
    a134.setOrderIndex(0);
    a134.setDefaultUnit(getUnitBySymbol("l"));
    JPA.em().persist(a134);
}




    // == A135
    // Consommation de gaz de pétrole liquéfié (GPL)

    
DoubleQuestion a135 = (DoubleQuestion) questionService.findByCode(QuestionCode.A135);
if (a135 == null) {
    a135 = new DoubleQuestion( a132, 0, QuestionCode.A135, volumeUnits, null, getUnitBySymbol("l") );
    JPA.em().persist(a135);
} else {
    a135.setDefaultValue(null);
    if (!a135.getQuestionSet().equals(a132) && a132.getQuestions().contains(a135)) {
        a132.getQuestions().remove(a135);
        JPA.em().persist(a132);
    }
    if (a135.getQuestionSet().equals(a132) && !a132.getQuestions().contains(a135)) {
        a132.getQuestions().add(a135);
        JPA.em().persist(a132);
    }
    a135.setUnitCategory(volumeUnits);
    a135.setOrderIndex(0);
    a135.setDefaultUnit(getUnitBySymbol("l"));
    JPA.em().persist(a135);
}




    // == A136
    // Est-ce les marchandises sont refrigérées durant le transport?

    BooleanQuestion a136 = (BooleanQuestion) questionService.findByCode(QuestionCode.A136);
if (a136 == null) {
    a136 = new BooleanQuestion(a132, 0, QuestionCode.A136, null);
    JPA.em().persist(a136);
} else {
    a136.setDefaultValue(null);
    if (!a136.getQuestionSet().equals(a132) && a132.getQuestions().contains(a136)) {
        a132.getQuestions().remove(a136);
        JPA.em().persist(a132);
    }
    if (a136.getQuestionSet().equals(a132) && !a132.getQuestions().contains(a136)) {
        a132.getQuestions().add(a136);
        JPA.em().persist(a132);
    }
    a136.setOrderIndex(0);
    JPA.em().persist(a136);
}


    // == A137
    // Type de Gaz

    ValueSelectionQuestion a137 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A137);
if (a137 == null) {
    a137 = new ValueSelectionQuestion(a132, 0, QuestionCode.A137, CodeList.FRIGORIGENEBASE);
    JPA.em().persist(a137);
} else {
    if (!a137.getQuestionSet().equals(a132) && a132.getQuestions().contains(a137)) {
        a132.getQuestions().remove(a137);
        JPA.em().persist(a132);
    }
    if (a137.getQuestionSet().equals(a132) && !a132.getQuestions().contains(a137)) {
        a132.getQuestions().add(a137);
        JPA.em().persist(a132);
    }
    a137.setOrderIndex(0);
    a137.setCodeList(CodeList.FRIGORIGENEBASE);
    JPA.em().persist(a137);
}


    // == A138
    // Connaissez-vous la quantité annuelle de recharge de ce gaz?

    BooleanQuestion a138 = (BooleanQuestion) questionService.findByCode(QuestionCode.A138);
if (a138 == null) {
    a138 = new BooleanQuestion(a132, 0, QuestionCode.A138, null);
    JPA.em().persist(a138);
} else {
    a138.setDefaultValue(null);
    if (!a138.getQuestionSet().equals(a132) && a132.getQuestions().contains(a138)) {
        a132.getQuestions().remove(a138);
        JPA.em().persist(a132);
    }
    if (a138.getQuestionSet().equals(a132) && !a132.getQuestions().contains(a138)) {
        a132.getQuestions().add(a138);
        JPA.em().persist(a132);
    }
    a138.setOrderIndex(0);
    JPA.em().persist(a138);
}


    // == A139
    // Quantité de recharge annuelle

    
DoubleQuestion a139 = (DoubleQuestion) questionService.findByCode(QuestionCode.A139);
if (a139 == null) {
    a139 = new DoubleQuestion( a132, 0, QuestionCode.A139, massUnits, null, getUnitBySymbol("kg") );
    JPA.em().persist(a139);
} else {
    a139.setDefaultValue(null);
    if (!a139.getQuestionSet().equals(a132) && a132.getQuestions().contains(a139)) {
        a132.getQuestions().remove(a139);
        JPA.em().persist(a132);
    }
    if (a139.getQuestionSet().equals(a132) && !a132.getQuestions().contains(a139)) {
        a132.getQuestions().add(a139);
        JPA.em().persist(a132);
    }
    a139.setUnitCategory(massUnits);
    a139.setOrderIndex(0);
    a139.setDefaultUnit(getUnitBySymbol("kg"));
    JPA.em().persist(a139);
}




    // == A500
    // Quantité de recharge annuelle

    
DoubleQuestion a500 = (DoubleQuestion) questionService.findByCode(QuestionCode.A500);
if (a500 == null) {
    a500 = new DoubleQuestion( a132, 0, QuestionCode.A500, massUnits, null, getUnitBySymbol("kg") );
    JPA.em().persist(a500);
} else {
    a500.setDefaultValue(null);
    if (!a500.getQuestionSet().equals(a132) && a132.getQuestions().contains(a500)) {
        a132.getQuestions().remove(a500);
        JPA.em().persist(a132);
    }
    if (a500.getQuestionSet().equals(a132) && !a132.getQuestions().contains(a500)) {
        a132.getQuestions().add(a500);
        JPA.em().persist(a132);
    }
    a500.setUnitCategory(massUnits);
    a500.setOrderIndex(0);
    a500.setDefaultUnit(getUnitBySymbol("kg"));
    JPA.em().persist(a500);
}




    // == A143
    // Nom du produit transporté

    StringQuestion a143 = (StringQuestion) questionService.findByCode(QuestionCode.A143);
if (a143 == null) {
    a143 = new StringQuestion(a142, 0, QuestionCode.A143, null);
    JPA.em().persist(a143);
} else {
    a143.setDefaultValue(null);
    if (!a143.getQuestionSet().equals(a142) && a142.getQuestions().contains(a143)) {
        a142.getQuestions().remove(a143);
        JPA.em().persist(a142);
    }
    if (a143.getQuestionSet().equals(a142) && !a142.getQuestions().contains(a143)) {
        a142.getQuestions().add(a143);
        JPA.em().persist(a142);
    }
    a143.setOrderIndex(0);
    JPA.em().persist(a143);
}


    // == A145
    // Poids total transporté sur l'année de bilan

    
DoubleQuestion a145 = (DoubleQuestion) questionService.findByCode(QuestionCode.A145);
if (a145 == null) {
    a145 = new DoubleQuestion( a142, 0, QuestionCode.A145, massUnits, null, getUnitBySymbol("t") );
    JPA.em().persist(a145);
} else {
    a145.setDefaultValue(null);
    if (!a145.getQuestionSet().equals(a142) && a142.getQuestions().contains(a145)) {
        a142.getQuestions().remove(a145);
        JPA.em().persist(a142);
    }
    if (a145.getQuestionSet().equals(a142) && !a142.getQuestions().contains(a145)) {
        a142.getQuestions().add(a145);
        JPA.em().persist(a142);
    }
    a145.setUnitCategory(massUnits);
    a145.setOrderIndex(0);
    a145.setDefaultUnit(getUnitBySymbol("t"));
    JPA.em().persist(a145);
}




    // == A146
    // Distance entre le point d'enlèvement et de livraison du produit

    
DoubleQuestion a146 = (DoubleQuestion) questionService.findByCode(QuestionCode.A146);
if (a146 == null) {
    a146 = new DoubleQuestion( a142, 0, QuestionCode.A146, lengthUnits, null, getUnitBySymbol("km") );
    JPA.em().persist(a146);
} else {
    a146.setDefaultValue(null);
    if (!a146.getQuestionSet().equals(a142) && a142.getQuestions().contains(a146)) {
        a142.getQuestions().remove(a146);
        JPA.em().persist(a142);
    }
    if (a146.getQuestionSet().equals(a142) && !a142.getQuestions().contains(a146)) {
        a142.getQuestions().add(a146);
        JPA.em().persist(a142);
    }
    a146.setUnitCategory(lengthUnits);
    a146.setOrderIndex(0);
    a146.setDefaultUnit(getUnitBySymbol("km"));
    JPA.em().persist(a146);
}




    // == A147
    // % de distance effectuée par transport routier local par camion

    PercentageQuestion a147 = (PercentageQuestion) questionService.findByCode(QuestionCode.A147);
if (a147 == null) {
    a147 = new PercentageQuestion(a142, 0, QuestionCode.A147, null);
    JPA.em().persist(a147);
} else {
    a147.setDefaultValue(null);
    if (!a147.getQuestionSet().equals(a142) && a142.getQuestions().contains(a147)) {
        a142.getQuestions().remove(a147);
        JPA.em().persist(a142);
    }
    if (a147.getQuestionSet().equals(a142) && !a142.getQuestions().contains(a147)) {
        a142.getQuestions().add(a147);
        JPA.em().persist(a142);
    }
    a147.setOrderIndex(0);
    JPA.em().persist(a147);
}


    // == A148
    // % de distance effectuée par transport routier local par camionnette

    PercentageQuestion a148 = (PercentageQuestion) questionService.findByCode(QuestionCode.A148);
if (a148 == null) {
    a148 = new PercentageQuestion(a142, 0, QuestionCode.A148, null);
    JPA.em().persist(a148);
} else {
    a148.setDefaultValue(null);
    if (!a148.getQuestionSet().equals(a142) && a142.getQuestions().contains(a148)) {
        a142.getQuestions().remove(a148);
        JPA.em().persist(a142);
    }
    if (a148.getQuestionSet().equals(a142) && !a142.getQuestions().contains(a148)) {
        a142.getQuestions().add(a148);
        JPA.em().persist(a142);
    }
    a148.setOrderIndex(0);
    JPA.em().persist(a148);
}


    // == A149
    // % de distance effectuée par transport routier international

    PercentageQuestion a149 = (PercentageQuestion) questionService.findByCode(QuestionCode.A149);
if (a149 == null) {
    a149 = new PercentageQuestion(a142, 0, QuestionCode.A149, null);
    JPA.em().persist(a149);
} else {
    a149.setDefaultValue(null);
    if (!a149.getQuestionSet().equals(a142) && a142.getQuestions().contains(a149)) {
        a142.getQuestions().remove(a149);
        JPA.em().persist(a142);
    }
    if (a149.getQuestionSet().equals(a142) && !a142.getQuestions().contains(a149)) {
        a142.getQuestions().add(a149);
        JPA.em().persist(a142);
    }
    a149.setOrderIndex(0);
    JPA.em().persist(a149);
}


    // == A150
    // % de distance effectuée par voie ferroviaire

    PercentageQuestion a150 = (PercentageQuestion) questionService.findByCode(QuestionCode.A150);
if (a150 == null) {
    a150 = new PercentageQuestion(a142, 0, QuestionCode.A150, null);
    JPA.em().persist(a150);
} else {
    a150.setDefaultValue(null);
    if (!a150.getQuestionSet().equals(a142) && a142.getQuestions().contains(a150)) {
        a142.getQuestions().remove(a150);
        JPA.em().persist(a142);
    }
    if (a150.getQuestionSet().equals(a142) && !a142.getQuestions().contains(a150)) {
        a142.getQuestions().add(a150);
        JPA.em().persist(a142);
    }
    a150.setOrderIndex(0);
    JPA.em().persist(a150);
}


    // == A151
    // % de distance effectuée par voie maritime

    PercentageQuestion a151 = (PercentageQuestion) questionService.findByCode(QuestionCode.A151);
if (a151 == null) {
    a151 = new PercentageQuestion(a142, 0, QuestionCode.A151, null);
    JPA.em().persist(a151);
} else {
    a151.setDefaultValue(null);
    if (!a151.getQuestionSet().equals(a142) && a142.getQuestions().contains(a151)) {
        a142.getQuestions().remove(a151);
        JPA.em().persist(a142);
    }
    if (a151.getQuestionSet().equals(a142) && !a142.getQuestions().contains(a151)) {
        a142.getQuestions().add(a151);
        JPA.em().persist(a142);
    }
    a151.setOrderIndex(0);
    JPA.em().persist(a151);
}


    // == A152
    // % de distance effectuée par voie fluviale

    PercentageQuestion a152 = (PercentageQuestion) questionService.findByCode(QuestionCode.A152);
if (a152 == null) {
    a152 = new PercentageQuestion(a142, 0, QuestionCode.A152, null);
    JPA.em().persist(a152);
} else {
    a152.setDefaultValue(null);
    if (!a152.getQuestionSet().equals(a142) && a142.getQuestions().contains(a152)) {
        a142.getQuestions().remove(a152);
        JPA.em().persist(a142);
    }
    if (a152.getQuestionSet().equals(a142) && !a142.getQuestions().contains(a152)) {
        a142.getQuestions().add(a152);
        JPA.em().persist(a142);
    }
    a152.setOrderIndex(0);
    JPA.em().persist(a152);
}


    // == A153
    // % de distance effectuée par transport aérien court courrier (<1000 km)

    PercentageQuestion a153 = (PercentageQuestion) questionService.findByCode(QuestionCode.A153);
if (a153 == null) {
    a153 = new PercentageQuestion(a142, 0, QuestionCode.A153, null);
    JPA.em().persist(a153);
} else {
    a153.setDefaultValue(null);
    if (!a153.getQuestionSet().equals(a142) && a142.getQuestions().contains(a153)) {
        a142.getQuestions().remove(a153);
        JPA.em().persist(a142);
    }
    if (a153.getQuestionSet().equals(a142) && !a142.getQuestions().contains(a153)) {
        a142.getQuestions().add(a153);
        JPA.em().persist(a142);
    }
    a153.setOrderIndex(0);
    JPA.em().persist(a153);
}


    // == A154
    // % de distance effectuée par transport aérien moyen courrier (1000 à 4000 km)

    PercentageQuestion a154 = (PercentageQuestion) questionService.findByCode(QuestionCode.A154);
if (a154 == null) {
    a154 = new PercentageQuestion(a142, 0, QuestionCode.A154, null);
    JPA.em().persist(a154);
} else {
    a154.setDefaultValue(null);
    if (!a154.getQuestionSet().equals(a142) && a142.getQuestions().contains(a154)) {
        a142.getQuestions().remove(a154);
        JPA.em().persist(a142);
    }
    if (a154.getQuestionSet().equals(a142) && !a142.getQuestions().contains(a154)) {
        a142.getQuestions().add(a154);
        JPA.em().persist(a142);
    }
    a154.setOrderIndex(0);
    JPA.em().persist(a154);
}


    // == A155
    // % de distance effectuée par transport aérien long courrier (> 4000 km)

    PercentageQuestion a155 = (PercentageQuestion) questionService.findByCode(QuestionCode.A155);
if (a155 == null) {
    a155 = new PercentageQuestion(a142, 0, QuestionCode.A155, null);
    JPA.em().persist(a155);
} else {
    a155.setDefaultValue(null);
    if (!a155.getQuestionSet().equals(a142) && a142.getQuestions().contains(a155)) {
        a142.getQuestions().remove(a155);
        JPA.em().persist(a142);
    }
    if (a155.getQuestionSet().equals(a142) && !a142.getQuestions().contains(a155)) {
        a142.getQuestions().add(a155);
        JPA.em().persist(a142);
    }
    a155.setOrderIndex(0);
    JPA.em().persist(a155);
}


    // == A156
    // Total (supposé être égal à 100%)

    PercentageQuestion a156 = (PercentageQuestion) questionService.findByCode(QuestionCode.A156);
if (a156 == null) {
    a156 = new PercentageQuestion(a142, 0, QuestionCode.A156, null);
    JPA.em().persist(a156);
} else {
    a156.setDefaultValue(null);
    if (!a156.getQuestionSet().equals(a142) && a142.getQuestions().contains(a156)) {
        a142.getQuestions().remove(a156);
        JPA.em().persist(a142);
    }
    if (a156.getQuestionSet().equals(a142) && !a142.getQuestions().contains(a156)) {
        a142.getQuestions().add(a156);
        JPA.em().persist(a142);
    }
    a156.setOrderIndex(0);
    JPA.em().persist(a156);
}


    // == A158
    // Quel est le poids total transporté sur toute l'année du bilan (tous produits confondus)?

    
DoubleQuestion a158 = (DoubleQuestion) questionService.findByCode(QuestionCode.A158);
if (a158 == null) {
    a158 = new DoubleQuestion( a157, 0, QuestionCode.A158, massUnits, null, getUnitBySymbol("t") );
    JPA.em().persist(a158);
} else {
    a158.setDefaultValue(null);
    if (!a158.getQuestionSet().equals(a157) && a157.getQuestions().contains(a158)) {
        a157.getQuestions().remove(a158);
        JPA.em().persist(a157);
    }
    if (a158.getQuestionSet().equals(a157) && !a157.getQuestions().contains(a158)) {
        a157.getQuestions().add(a158);
        JPA.em().persist(a157);
    }
    a158.setUnitCategory(massUnits);
    a158.setOrderIndex(0);
    a158.setDefaultUnit(getUnitBySymbol("t"));
    JPA.em().persist(a158);
}




    // == A159
    // Quelle est la provenance des produits?

    ValueSelectionQuestion a159 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A159);
if (a159 == null) {
    a159 = new ValueSelectionQuestion(a157, 0, QuestionCode.A159, CodeList.PROVENANCESIMPLIFIEE);
    JPA.em().persist(a159);
} else {
    if (!a159.getQuestionSet().equals(a157) && a157.getQuestions().contains(a159)) {
        a157.getQuestions().remove(a159);
        JPA.em().persist(a157);
    }
    if (a159.getQuestionSet().equals(a157) && !a157.getQuestions().contains(a159)) {
        a157.getQuestions().add(a159);
        JPA.em().persist(a157);
    }
    a159.setOrderIndex(0);
    a159.setCodeList(CodeList.PROVENANCESIMPLIFIEE);
    JPA.em().persist(a159);
}


    // == A160
    // Distance moyenne assignée entre le point d'enlèvement et le point de livraison

    
DoubleQuestion a160 = (DoubleQuestion) questionService.findByCode(QuestionCode.A160);
if (a160 == null) {
    a160 = new DoubleQuestion( a157, 0, QuestionCode.A160, lengthUnits, null, getUnitBySymbol("km") );
    JPA.em().persist(a160);
} else {
    a160.setDefaultValue(null);
    if (!a160.getQuestionSet().equals(a157) && a157.getQuestions().contains(a160)) {
        a157.getQuestions().remove(a160);
        JPA.em().persist(a157);
    }
    if (a160.getQuestionSet().equals(a157) && !a157.getQuestions().contains(a160)) {
        a157.getQuestions().add(a160);
        JPA.em().persist(a157);
    }
    a160.setUnitCategory(lengthUnits);
    a160.setOrderIndex(0);
    a160.setDefaultUnit(getUnitBySymbol("km"));
    JPA.em().persist(a160);
}




    // == A161
    // Distance moyenne assignée entre le point d'enlèvement et le point de livraison

    
DoubleQuestion a161 = (DoubleQuestion) questionService.findByCode(QuestionCode.A161);
if (a161 == null) {
    a161 = new DoubleQuestion( a157, 0, QuestionCode.A161, lengthUnits, null, getUnitBySymbol("km") );
    JPA.em().persist(a161);
} else {
    a161.setDefaultValue(null);
    if (!a161.getQuestionSet().equals(a157) && a157.getQuestions().contains(a161)) {
        a157.getQuestions().remove(a161);
        JPA.em().persist(a157);
    }
    if (a161.getQuestionSet().equals(a157) && !a157.getQuestions().contains(a161)) {
        a157.getQuestions().add(a161);
        JPA.em().persist(a157);
    }
    a161.setUnitCategory(lengthUnits);
    a161.setOrderIndex(0);
    a161.setDefaultUnit(getUnitBySymbol("km"));
    JPA.em().persist(a161);
}




    // == A162
    // Distance moyenne assignée entre le point d'enlèvement et le point de livraison

    
DoubleQuestion a162 = (DoubleQuestion) questionService.findByCode(QuestionCode.A162);
if (a162 == null) {
    a162 = new DoubleQuestion( a157, 0, QuestionCode.A162, lengthUnits, null, getUnitBySymbol("km") );
    JPA.em().persist(a162);
} else {
    a162.setDefaultValue(null);
    if (!a162.getQuestionSet().equals(a157) && a157.getQuestions().contains(a162)) {
        a157.getQuestions().remove(a162);
        JPA.em().persist(a157);
    }
    if (a162.getQuestionSet().equals(a157) && !a157.getQuestions().contains(a162)) {
        a157.getQuestions().add(a162);
        JPA.em().persist(a157);
    }
    a162.setUnitCategory(lengthUnits);
    a162.setOrderIndex(0);
    a162.setDefaultUnit(getUnitBySymbol("km"));
    JPA.em().persist(a162);
}




    // == A165
    // Entrepôt

    StringQuestion a165 = (StringQuestion) questionService.findByCode(QuestionCode.A165);
if (a165 == null) {
    a165 = new StringQuestion(a164, 0, QuestionCode.A165, null);
    JPA.em().persist(a165);
} else {
    a165.setDefaultValue(null);
    if (!a165.getQuestionSet().equals(a164) && a164.getQuestions().contains(a165)) {
        a164.getQuestions().remove(a165);
        JPA.em().persist(a164);
    }
    if (a165.getQuestionSet().equals(a164) && !a164.getQuestions().contains(a165)) {
        a164.getQuestions().add(a165);
        JPA.em().persist(a164);
    }
    a165.setOrderIndex(0);
    JPA.em().persist(a165);
}


    // == A167
    // Combustible utilisé en amont

    ValueSelectionQuestion a167 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A167);
if (a167 == null) {
    a167 = new ValueSelectionQuestion(a166, 0, QuestionCode.A167, CodeList.COMBUSTIBLE);
    JPA.em().persist(a167);
} else {
    if (!a167.getQuestionSet().equals(a166) && a166.getQuestions().contains(a167)) {
        a166.getQuestions().remove(a167);
        JPA.em().persist(a166);
    }
    if (a167.getQuestionSet().equals(a166) && !a166.getQuestions().contains(a167)) {
        a166.getQuestions().add(a167);
        JPA.em().persist(a166);
    }
    a167.setOrderIndex(0);
    a167.setCodeList(CodeList.COMBUSTIBLE);
    JPA.em().persist(a167);
}


    // == A168
    // Quantité

    
DoubleQuestion a168 = (DoubleQuestion) questionService.findByCode(QuestionCode.A168);
if (a168 == null) {
    a168 = new DoubleQuestion( a166, 0, QuestionCode.A168, energyUnits, null, getUnitBySymbol("kW.h") );
    JPA.em().persist(a168);
} else {
    a168.setDefaultValue(null);
    if (!a168.getQuestionSet().equals(a166) && a166.getQuestions().contains(a168)) {
        a166.getQuestions().remove(a168);
        JPA.em().persist(a166);
    }
    if (a168.getQuestionSet().equals(a166) && !a166.getQuestions().contains(a168)) {
        a166.getQuestions().add(a168);
        JPA.em().persist(a166);
    }
    a168.setUnitCategory(energyUnits);
    a168.setOrderIndex(0);
    a168.setDefaultUnit(getUnitBySymbol("kW.h"));
    JPA.em().persist(a168);
}




    // == A1007
    // Combustible utilisé en amont

    ValueSelectionQuestion a1007 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A1007);
if (a1007 == null) {
    a1007 = new ValueSelectionQuestion(a1006, 0, QuestionCode.A1007, CodeList.COMBUSTIBLEVOLUME);
    JPA.em().persist(a1007);
} else {
    if (!a1007.getQuestionSet().equals(a1006) && a1006.getQuestions().contains(a1007)) {
        a1006.getQuestions().remove(a1007);
        JPA.em().persist(a1006);
    }
    if (a1007.getQuestionSet().equals(a1006) && !a1006.getQuestions().contains(a1007)) {
        a1006.getQuestions().add(a1007);
        JPA.em().persist(a1006);
    }
    a1007.setOrderIndex(0);
    a1007.setCodeList(CodeList.COMBUSTIBLEVOLUME);
    JPA.em().persist(a1007);
}


    // == A1008
    // Quantité

    
DoubleQuestion a1008 = (DoubleQuestion) questionService.findByCode(QuestionCode.A1008);
if (a1008 == null) {
    a1008 = new DoubleQuestion( a1006, 0, QuestionCode.A1008, volumeUnits, null, volumeUnits.getMainUnit() );
    JPA.em().persist(a1008);
} else {
    a1008.setDefaultValue(null);
    if (!a1008.getQuestionSet().equals(a1006) && a1006.getQuestions().contains(a1008)) {
        a1006.getQuestions().remove(a1008);
        JPA.em().persist(a1006);
    }
    if (a1008.getQuestionSet().equals(a1006) && !a1006.getQuestions().contains(a1008)) {
        a1006.getQuestions().add(a1008);
        JPA.em().persist(a1006);
    }
    a1008.setUnitCategory(volumeUnits);
    a1008.setOrderIndex(0);
    a1008.setDefaultUnit(volumeUnits.getMainUnit());
    JPA.em().persist(a1008);
}




    // == A1010
    // Combustible utilisé en amont

    ValueSelectionQuestion a1010 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A1010);
if (a1010 == null) {
    a1010 = new ValueSelectionQuestion(a1009, 0, QuestionCode.A1010, CodeList.COMBUSTIBLEPOIDS);
    JPA.em().persist(a1010);
} else {
    if (!a1010.getQuestionSet().equals(a1009) && a1009.getQuestions().contains(a1010)) {
        a1009.getQuestions().remove(a1010);
        JPA.em().persist(a1009);
    }
    if (a1010.getQuestionSet().equals(a1009) && !a1009.getQuestions().contains(a1010)) {
        a1009.getQuestions().add(a1010);
        JPA.em().persist(a1009);
    }
    a1010.setOrderIndex(0);
    a1010.setCodeList(CodeList.COMBUSTIBLEPOIDS);
    JPA.em().persist(a1010);
}


    // == A1011
    // Quantité

    
DoubleQuestion a1011 = (DoubleQuestion) questionService.findByCode(QuestionCode.A1011);
if (a1011 == null) {
    a1011 = new DoubleQuestion( a1009, 0, QuestionCode.A1011, massUnits, null, massUnits.getMainUnit() );
    JPA.em().persist(a1011);
} else {
    a1011.setDefaultValue(null);
    if (!a1011.getQuestionSet().equals(a1009) && a1009.getQuestions().contains(a1011)) {
        a1009.getQuestions().remove(a1011);
        JPA.em().persist(a1009);
    }
    if (a1011.getQuestionSet().equals(a1009) && !a1009.getQuestions().contains(a1011)) {
        a1009.getQuestions().add(a1011);
        JPA.em().persist(a1009);
    }
    a1011.setUnitCategory(massUnits);
    a1011.setOrderIndex(0);
    a1011.setDefaultUnit(massUnits.getMainUnit());
    JPA.em().persist(a1011);
}




    // == A169
    // Electricité

    
DoubleQuestion a169 = (DoubleQuestion) questionService.findByCode(QuestionCode.A169);
if (a169 == null) {
    a169 = new DoubleQuestion( a164, 0, QuestionCode.A169, energyUnits, null, getUnitBySymbol("kW.h") );
    JPA.em().persist(a169);
} else {
    a169.setDefaultValue(null);
    if (!a169.getQuestionSet().equals(a164) && a164.getQuestions().contains(a169)) {
        a164.getQuestions().remove(a169);
        JPA.em().persist(a164);
    }
    if (a169.getQuestionSet().equals(a164) && !a164.getQuestions().contains(a169)) {
        a164.getQuestions().add(a169);
        JPA.em().persist(a164);
    }
    a169.setUnitCategory(energyUnits);
    a169.setOrderIndex(0);
    a169.setDefaultUnit(getUnitBySymbol("kW.h"));
    JPA.em().persist(a169);
}




    // == A171
    // Type de gaz

    ValueSelectionQuestion a171 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A171);
if (a171 == null) {
    a171 = new ValueSelectionQuestion(a170, 0, QuestionCode.A171, CodeList.FRIGORIGENE);
    JPA.em().persist(a171);
} else {
    if (!a171.getQuestionSet().equals(a170) && a170.getQuestions().contains(a171)) {
        a170.getQuestions().remove(a171);
        JPA.em().persist(a170);
    }
    if (a171.getQuestionSet().equals(a170) && !a170.getQuestions().contains(a171)) {
        a170.getQuestions().add(a171);
        JPA.em().persist(a170);
    }
    a171.setOrderIndex(0);
    a171.setCodeList(CodeList.FRIGORIGENE);
    JPA.em().persist(a171);
}


    // == A172
    // Quantité de recharge nécessaire pour l'année

    
DoubleQuestion a172 = (DoubleQuestion) questionService.findByCode(QuestionCode.A172);
if (a172 == null) {
    a172 = new DoubleQuestion( a170, 0, QuestionCode.A172, massUnits, null, massUnits.getMainUnit() );
    JPA.em().persist(a172);
} else {
    a172.setDefaultValue(null);
    if (!a172.getQuestionSet().equals(a170) && a170.getQuestions().contains(a172)) {
        a170.getQuestions().remove(a172);
        JPA.em().persist(a170);
    }
    if (a172.getQuestionSet().equals(a170) && !a170.getQuestions().contains(a172)) {
        a170.getQuestions().add(a172);
        JPA.em().persist(a170);
    }
    a172.setUnitCategory(massUnits);
    a172.setOrderIndex(0);
    a172.setDefaultUnit(massUnits.getMainUnit());
    JPA.em().persist(a172);
}




    // == A174
    // Pièces documentaires liées aux déchets

    DocumentQuestion a174 = (DocumentQuestion) questionService.findByCode(QuestionCode.A174);
if (a174 == null) {
    a174 = new DocumentQuestion(a173, 0, QuestionCode.A174);
    JPA.em().persist(a174);
} else {
    if (!a174.getQuestionSet().equals(a173) && a173.getQuestions().contains(a174)) {
        a173.getQuestions().remove(a174);
        JPA.em().persist(a173);
    }
    if (a174.getQuestionSet().equals(a173) && !a173.getQuestions().contains(a174)) {
        a173.getQuestions().add(a174);
        JPA.em().persist(a173);
    }
    a174.setOrderIndex(0);
    JPA.em().persist(a174);
}


    // == A5001
    // Poste de déchet

    StringQuestion a5001 = (StringQuestion) questionService.findByCode(QuestionCode.A5001);
if (a5001 == null) {
    a5001 = new StringQuestion(a5000, 0, QuestionCode.A5001, null);
    JPA.em().persist(a5001);
} else {
    a5001.setDefaultValue(null);
    if (!a5001.getQuestionSet().equals(a5000) && a5000.getQuestions().contains(a5001)) {
        a5000.getQuestions().remove(a5001);
        JPA.em().persist(a5000);
    }
    if (a5001.getQuestionSet().equals(a5000) && !a5000.getQuestions().contains(a5001)) {
        a5000.getQuestions().add(a5001);
        JPA.em().persist(a5000);
    }
    a5001.setOrderIndex(0);
    JPA.em().persist(a5001);
}


    // == A5002
    // Type de déchet

    ValueSelectionQuestion a5002 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A5002);
if (a5002 == null) {
    a5002 = new ValueSelectionQuestion(a5000, 0, QuestionCode.A5002, CodeList.GESTIONDECHETS);
    JPA.em().persist(a5002);
} else {
    if (!a5002.getQuestionSet().equals(a5000) && a5000.getQuestions().contains(a5002)) {
        a5000.getQuestions().remove(a5002);
        JPA.em().persist(a5000);
    }
    if (a5002.getQuestionSet().equals(a5000) && !a5000.getQuestions().contains(a5002)) {
        a5000.getQuestions().add(a5002);
        JPA.em().persist(a5000);
    }
    a5002.setOrderIndex(0);
    a5002.setCodeList(CodeList.GESTIONDECHETS);
    JPA.em().persist(a5002);
}


    // == A5003
    // Quantité

    
DoubleQuestion a5003 = (DoubleQuestion) questionService.findByCode(QuestionCode.A5003);
if (a5003 == null) {
    a5003 = new DoubleQuestion( a5000, 0, QuestionCode.A5003, massUnits, null, getUnitBySymbol("t") );
    JPA.em().persist(a5003);
} else {
    a5003.setDefaultValue(null);
    if (!a5003.getQuestionSet().equals(a5000) && a5000.getQuestions().contains(a5003)) {
        a5000.getQuestions().remove(a5003);
        JPA.em().persist(a5000);
    }
    if (a5003.getQuestionSet().equals(a5000) && !a5000.getQuestions().contains(a5003)) {
        a5000.getQuestions().add(a5003);
        JPA.em().persist(a5000);
    }
    a5003.setUnitCategory(massUnits);
    a5003.setOrderIndex(0);
    a5003.setDefaultUnit(getUnitBySymbol("t"));
    JPA.em().persist(a5003);
}




    // == A183
    // Nombre d'ouvriers

    
IntegerQuestion a183 = (IntegerQuestion) questionService.findByCode(QuestionCode.A183);
if (a183 == null) {
    a183 = new IntegerQuestion(a182, 0, QuestionCode.A183, null, null);
    JPA.em().persist(a183);
} else {
    a183.setDefaultValue(null);
    if (!a183.getQuestionSet().equals(a182) && a182.getQuestions().contains(a183)) {
        a182.getQuestions().remove(a183);
        JPA.em().persist(a182);
    }
    if (a183.getQuestionSet().equals(a182) && !a182.getQuestions().contains(a183)) {
        a182.getQuestions().add(a183);
        JPA.em().persist(a182);
    }
    a183.setOrderIndex(0);
    a183.setUnitCategory(null);
    JPA.em().persist(a183);
}


    // == A184
    // Nombre de jours de travail/an

    
IntegerQuestion a184 = (IntegerQuestion) questionService.findByCode(QuestionCode.A184);
if (a184 == null) {
    a184 = new IntegerQuestion(a182, 0, QuestionCode.A184, null, 220);
    JPA.em().persist(a184);
} else {
    a184.setDefaultValue((double) 220);
    if (!a184.getQuestionSet().equals(a182) && a182.getQuestions().contains(a184)) {
        a182.getQuestions().remove(a184);
        JPA.em().persist(a182);
    }
    if (a184.getQuestionSet().equals(a182) && !a182.getQuestions().contains(a184)) {
        a182.getQuestions().add(a184);
        JPA.em().persist(a182);
    }
    a184.setOrderIndex(0);
    a184.setUnitCategory(null);
    JPA.em().persist(a184);
}


    // == A186
    // Nombre d'employés

    
IntegerQuestion a186 = (IntegerQuestion) questionService.findByCode(QuestionCode.A186);
if (a186 == null) {
    a186 = new IntegerQuestion(a185, 0, QuestionCode.A186, null, null);
    JPA.em().persist(a186);
} else {
    a186.setDefaultValue(null);
    if (!a186.getQuestionSet().equals(a185) && a185.getQuestions().contains(a186)) {
        a185.getQuestions().remove(a186);
        JPA.em().persist(a185);
    }
    if (a186.getQuestionSet().equals(a185) && !a185.getQuestions().contains(a186)) {
        a185.getQuestions().add(a186);
        JPA.em().persist(a185);
    }
    a186.setOrderIndex(0);
    a186.setUnitCategory(null);
    JPA.em().persist(a186);
}


    // == A187
    // Nombre de jours de travail/an

    
IntegerQuestion a187 = (IntegerQuestion) questionService.findByCode(QuestionCode.A187);
if (a187 == null) {
    a187 = new IntegerQuestion(a185, 0, QuestionCode.A187, null, 220);
    JPA.em().persist(a187);
} else {
    a187.setDefaultValue((double) 220);
    if (!a187.getQuestionSet().equals(a185) && a185.getQuestions().contains(a187)) {
        a185.getQuestions().remove(a187);
        JPA.em().persist(a185);
    }
    if (a187.getQuestionSet().equals(a185) && !a185.getQuestions().contains(a187)) {
        a185.getQuestions().add(a187);
        JPA.em().persist(a185);
    }
    a187.setOrderIndex(0);
    a187.setUnitCategory(null);
    JPA.em().persist(a187);
}


    // == A189
    // Nombre de lits

    
IntegerQuestion a189 = (IntegerQuestion) questionService.findByCode(QuestionCode.A189);
if (a189 == null) {
    a189 = new IntegerQuestion(a188, 0, QuestionCode.A189, null, null);
    JPA.em().persist(a189);
} else {
    a189.setDefaultValue(null);
    if (!a189.getQuestionSet().equals(a188) && a188.getQuestions().contains(a189)) {
        a188.getQuestions().remove(a189);
        JPA.em().persist(a188);
    }
    if (a189.getQuestionSet().equals(a188) && !a188.getQuestions().contains(a189)) {
        a188.getQuestions().add(a189);
        JPA.em().persist(a188);
    }
    a189.setOrderIndex(0);
    a189.setUnitCategory(null);
    JPA.em().persist(a189);
}


    // == A190
    // Nombre de jours d'ouverture/an

    
IntegerQuestion a190 = (IntegerQuestion) questionService.findByCode(QuestionCode.A190);
if (a190 == null) {
    a190 = new IntegerQuestion(a188, 0, QuestionCode.A190, null, 365);
    JPA.em().persist(a190);
} else {
    a190.setDefaultValue((double) 365);
    if (!a190.getQuestionSet().equals(a188) && a188.getQuestions().contains(a190)) {
        a188.getQuestions().remove(a190);
        JPA.em().persist(a188);
    }
    if (a190.getQuestionSet().equals(a188) && !a188.getQuestions().contains(a190)) {
        a188.getQuestions().add(a190);
        JPA.em().persist(a188);
    }
    a190.setOrderIndex(0);
    a190.setUnitCategory(null);
    JPA.em().persist(a190);
}


    // == A192
    // Nombre de couverts/jour

    
IntegerQuestion a192 = (IntegerQuestion) questionService.findByCode(QuestionCode.A192);
if (a192 == null) {
    a192 = new IntegerQuestion(a191, 0, QuestionCode.A192, null, null);
    JPA.em().persist(a192);
} else {
    a192.setDefaultValue(null);
    if (!a192.getQuestionSet().equals(a191) && a191.getQuestions().contains(a192)) {
        a191.getQuestions().remove(a192);
        JPA.em().persist(a191);
    }
    if (a192.getQuestionSet().equals(a191) && !a191.getQuestions().contains(a192)) {
        a191.getQuestions().add(a192);
        JPA.em().persist(a191);
    }
    a192.setOrderIndex(0);
    a192.setUnitCategory(null);
    JPA.em().persist(a192);
}


    // == A193
    // Nombre de jours d'ouverture/an

    
IntegerQuestion a193 = (IntegerQuestion) questionService.findByCode(QuestionCode.A193);
if (a193 == null) {
    a193 = new IntegerQuestion(a191, 0, QuestionCode.A193, null, 220);
    JPA.em().persist(a193);
} else {
    a193.setDefaultValue((double) 220);
    if (!a193.getQuestionSet().equals(a191) && a191.getQuestions().contains(a193)) {
        a191.getQuestions().remove(a193);
        JPA.em().persist(a191);
    }
    if (a193.getQuestionSet().equals(a191) && !a191.getQuestions().contains(a193)) {
        a191.getQuestions().add(a193);
        JPA.em().persist(a191);
    }
    a193.setOrderIndex(0);
    a193.setUnitCategory(null);
    JPA.em().persist(a193);
}


    // == A195
    // Est-ce l'entreprise qui réalise le traitement ou est-il effectué par des tiers?

    ValueSelectionQuestion a195 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A195);
if (a195 == null) {
    a195 = new ValueSelectionQuestion(a197, 0, QuestionCode.A195, CodeList.TRAITEUREAU);
    JPA.em().persist(a195);
} else {
    if (!a195.getQuestionSet().equals(a197) && a197.getQuestions().contains(a195)) {
        a197.getQuestions().remove(a195);
        JPA.em().persist(a197);
    }
    if (a195.getQuestionSet().equals(a197) && !a197.getQuestions().contains(a195)) {
        a197.getQuestions().add(a195);
        JPA.em().persist(a197);
    }
    a195.setOrderIndex(0);
    a195.setCodeList(CodeList.TRAITEUREAU);
    JPA.em().persist(a195);
}


    // == A198
    // Source de rejet

    ValueSelectionQuestion a198 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A198);
if (a198 == null) {
    a198 = new ValueSelectionQuestion(a197, 0, QuestionCode.A198, CodeList.ORIGINEEAUUSEE);
    JPA.em().persist(a198);
} else {
    if (!a198.getQuestionSet().equals(a197) && a197.getQuestions().contains(a198)) {
        a197.getQuestions().remove(a198);
        JPA.em().persist(a197);
    }
    if (a198.getQuestionSet().equals(a197) && !a197.getQuestions().contains(a198)) {
        a197.getQuestions().add(a198);
        JPA.em().persist(a197);
    }
    a198.setOrderIndex(0);
    a198.setCodeList(CodeList.ORIGINEEAUUSEE);
    JPA.em().persist(a198);
}


    // == A199
    // Quantités de m³ rejetés

    
DoubleQuestion a199 = (DoubleQuestion) questionService.findByCode(QuestionCode.A199);
if (a199 == null) {
    a199 = new DoubleQuestion( a197, 0, QuestionCode.A199, volumeUnits, null, volumeUnits.getMainUnit() );
    JPA.em().persist(a199);
} else {
    a199.setDefaultValue(null);
    if (!a199.getQuestionSet().equals(a197) && a197.getQuestions().contains(a199)) {
        a197.getQuestions().remove(a199);
        JPA.em().persist(a197);
    }
    if (a199.getQuestionSet().equals(a197) && !a197.getQuestions().contains(a199)) {
        a197.getQuestions().add(a199);
        JPA.em().persist(a197);
    }
    a199.setUnitCategory(volumeUnits);
    a199.setOrderIndex(0);
    a199.setDefaultUnit(volumeUnits.getMainUnit());
    JPA.em().persist(a199);
}




    // == A200
    // Méthode de traitement des eaux usées

    ValueSelectionQuestion a200 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A200);
if (a200 == null) {
    a200 = new ValueSelectionQuestion(a197, 0, QuestionCode.A200, CodeList.TRAITEMENTEAU);
    JPA.em().persist(a200);
} else {
    if (!a200.getQuestionSet().equals(a197) && a197.getQuestions().contains(a200)) {
        a197.getQuestions().remove(a200);
        JPA.em().persist(a197);
    }
    if (a200.getQuestionSet().equals(a197) && !a197.getQuestions().contains(a200)) {
        a197.getQuestions().add(a200);
        JPA.em().persist(a197);
    }
    a200.setOrderIndex(0);
    a200.setCodeList(CodeList.TRAITEMENTEAU);
    JPA.em().persist(a200);
}


    // == A501
    // Est-ce l'entreprise qui réalise le traitement ou est-il effectué par des tiers?

    ValueSelectionQuestion a501 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A501);
if (a501 == null) {
    a501 = new ValueSelectionQuestion(a201, 0, QuestionCode.A501, CodeList.TRAITEUREAU);
    JPA.em().persist(a501);
} else {
    if (!a501.getQuestionSet().equals(a201) && a201.getQuestions().contains(a501)) {
        a201.getQuestions().remove(a501);
        JPA.em().persist(a201);
    }
    if (a501.getQuestionSet().equals(a201) && !a201.getQuestions().contains(a501)) {
        a201.getQuestions().add(a501);
        JPA.em().persist(a201);
    }
    a501.setOrderIndex(0);
    a501.setCodeList(CodeList.TRAITEUREAU);
    JPA.em().persist(a501);
}


    // == A202
    // Quantités de DCO rejetés

    
DoubleQuestion a202 = (DoubleQuestion) questionService.findByCode(QuestionCode.A202);
if (a202 == null) {
    a202 = new DoubleQuestion( a201, 0, QuestionCode.A202, massUnits, null, massUnits.getMainUnit() );
    JPA.em().persist(a202);
} else {
    a202.setDefaultValue(null);
    if (!a202.getQuestionSet().equals(a201) && a201.getQuestions().contains(a202)) {
        a201.getQuestions().remove(a202);
        JPA.em().persist(a201);
    }
    if (a202.getQuestionSet().equals(a201) && !a201.getQuestions().contains(a202)) {
        a201.getQuestions().add(a202);
        JPA.em().persist(a201);
    }
    a202.setUnitCategory(massUnits);
    a202.setOrderIndex(0);
    a202.setDefaultUnit(massUnits.getMainUnit());
    JPA.em().persist(a202);
}




    // == A203
    // Quantités d'azote rejetés

    
DoubleQuestion a203 = (DoubleQuestion) questionService.findByCode(QuestionCode.A203);
if (a203 == null) {
    a203 = new DoubleQuestion( a201, 0, QuestionCode.A203, massUnits, null, massUnits.getMainUnit() );
    JPA.em().persist(a203);
} else {
    a203.setDefaultValue(null);
    if (!a203.getQuestionSet().equals(a201) && a201.getQuestions().contains(a203)) {
        a201.getQuestions().remove(a203);
        JPA.em().persist(a201);
    }
    if (a203.getQuestionSet().equals(a201) && !a201.getQuestions().contains(a203)) {
        a201.getQuestions().add(a203);
        JPA.em().persist(a201);
    }
    a203.setUnitCategory(massUnits);
    a203.setOrderIndex(0);
    a203.setDefaultUnit(massUnits.getMainUnit());
    JPA.em().persist(a203);
}




    // == A204
    // Méthode de traitement des eaux usées

    ValueSelectionQuestion a204 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A204);
if (a204 == null) {
    a204 = new ValueSelectionQuestion(a201, 0, QuestionCode.A204, CodeList.TRAITEMENTEAU);
    JPA.em().persist(a204);
} else {
    if (!a204.getQuestionSet().equals(a201) && a201.getQuestions().contains(a204)) {
        a201.getQuestions().remove(a204);
        JPA.em().persist(a201);
    }
    if (a204.getQuestionSet().equals(a201) && !a201.getQuestions().contains(a204)) {
        a201.getQuestions().add(a204);
        JPA.em().persist(a201);
    }
    a204.setOrderIndex(0);
    a204.setCodeList(CodeList.TRAITEMENTEAU);
    JPA.em().persist(a204);
}


    // == A230
    // Pièces documentaires liées aux biens d'équipements

    DocumentQuestion a230 = (DocumentQuestion) questionService.findByCode(QuestionCode.A230);
if (a230 == null) {
    a230 = new DocumentQuestion(a229, 0, QuestionCode.A230);
    JPA.em().persist(a230);
} else {
    if (!a230.getQuestionSet().equals(a229) && a229.getQuestions().contains(a230)) {
        a229.getQuestions().remove(a230);
        JPA.em().persist(a229);
    }
    if (a230.getQuestionSet().equals(a229) && !a229.getQuestions().contains(a230)) {
        a229.getQuestions().add(a230);
        JPA.em().persist(a229);
    }
    a230.setOrderIndex(0);
    JPA.em().persist(a230);
}


    // == A232
    // Poste d'infrastructure

    StringQuestion a232 = (StringQuestion) questionService.findByCode(QuestionCode.A232);
if (a232 == null) {
    a232 = new StringQuestion(a231, 0, QuestionCode.A232, null);
    JPA.em().persist(a232);
} else {
    a232.setDefaultValue(null);
    if (!a232.getQuestionSet().equals(a231) && a231.getQuestions().contains(a232)) {
        a231.getQuestions().remove(a232);
        JPA.em().persist(a231);
    }
    if (a232.getQuestionSet().equals(a231) && !a231.getQuestions().contains(a232)) {
        a231.getQuestions().add(a232);
        JPA.em().persist(a231);
    }
    a232.setOrderIndex(0);
    JPA.em().persist(a232);
}


    // == A233
    // Type d'infrastructure

    ValueSelectionQuestion a233 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A233);
if (a233 == null) {
    a233 = new ValueSelectionQuestion(a231, 0, QuestionCode.A233, CodeList.INFRASTRUCTURE);
    JPA.em().persist(a233);
} else {
    if (!a233.getQuestionSet().equals(a231) && a231.getQuestions().contains(a233)) {
        a231.getQuestions().remove(a233);
        JPA.em().persist(a231);
    }
    if (a233.getQuestionSet().equals(a231) && !a231.getQuestions().contains(a233)) {
        a231.getQuestions().add(a233);
        JPA.em().persist(a231);
    }
    a233.setOrderIndex(0);
    a233.setCodeList(CodeList.INFRASTRUCTURE);
    JPA.em().persist(a233);
}


    // == A234
    // Quantité

    
DoubleQuestion a234 = (DoubleQuestion) questionService.findByCode(QuestionCode.A234);
if (a234 == null) {
    a234 = new DoubleQuestion( a231, 0, QuestionCode.A234, areaUnits, null, areaUnits.getMainUnit() );
    JPA.em().persist(a234);
} else {
    a234.setDefaultValue(null);
    if (!a234.getQuestionSet().equals(a231) && a231.getQuestions().contains(a234)) {
        a231.getQuestions().remove(a234);
        JPA.em().persist(a231);
    }
    if (a234.getQuestionSet().equals(a231) && !a231.getQuestions().contains(a234)) {
        a231.getQuestions().add(a234);
        JPA.em().persist(a231);
    }
    a234.setUnitCategory(areaUnits);
    a234.setOrderIndex(0);
    a234.setDefaultUnit(areaUnits.getMainUnit());
    JPA.em().persist(a234);
}




    // == A235
    // Quantité

    
DoubleQuestion a235 = (DoubleQuestion) questionService.findByCode(QuestionCode.A235);
if (a235 == null) {
    a235 = new DoubleQuestion( a231, 0, QuestionCode.A235, massUnits, null, getUnitBySymbol("t") );
    JPA.em().persist(a235);
} else {
    a235.setDefaultValue(null);
    if (!a235.getQuestionSet().equals(a231) && a231.getQuestions().contains(a235)) {
        a231.getQuestions().remove(a235);
        JPA.em().persist(a231);
    }
    if (a235.getQuestionSet().equals(a231) && !a231.getQuestions().contains(a235)) {
        a231.getQuestions().add(a235);
        JPA.em().persist(a231);
    }
    a235.setUnitCategory(massUnits);
    a235.setOrderIndex(0);
    a235.setDefaultUnit(getUnitBySymbol("t"));
    JPA.em().persist(a235);
}




    // == A236
    // Quantité

    
IntegerQuestion a236 = (IntegerQuestion) questionService.findByCode(QuestionCode.A236);
if (a236 == null) {
    a236 = new IntegerQuestion(a231, 0, QuestionCode.A236, null, null);
    JPA.em().persist(a236);
} else {
    a236.setDefaultValue(null);
    if (!a236.getQuestionSet().equals(a231) && a231.getQuestions().contains(a236)) {
        a231.getQuestions().remove(a236);
        JPA.em().persist(a231);
    }
    if (a236.getQuestionSet().equals(a231) && !a231.getQuestions().contains(a236)) {
        a231.getQuestions().add(a236);
        JPA.em().persist(a231);
    }
    a236.setOrderIndex(0);
    a236.setUnitCategory(null);
    JPA.em().persist(a236);
}


    // == A239
    // Poste d'infrastructure

    StringQuestion a239 = (StringQuestion) questionService.findByCode(QuestionCode.A239);
if (a239 == null) {
    a239 = new StringQuestion(a238, 0, QuestionCode.A239, null);
    JPA.em().persist(a239);
} else {
    a239.setDefaultValue(null);
    if (!a239.getQuestionSet().equals(a238) && a238.getQuestions().contains(a239)) {
        a238.getQuestions().remove(a239);
        JPA.em().persist(a238);
    }
    if (a239.getQuestionSet().equals(a238) && !a238.getQuestions().contains(a239)) {
        a238.getQuestions().add(a239);
        JPA.em().persist(a238);
    }
    a239.setOrderIndex(0);
    JPA.em().persist(a239);
}


    // == A240
    // Quantité

    
IntegerQuestion a240 = (IntegerQuestion) questionService.findByCode(QuestionCode.A240);
if (a240 == null) {
    a240 = new IntegerQuestion(a238, 0, QuestionCode.A240, null, null);
    JPA.em().persist(a240);
} else {
    a240.setDefaultValue(null);
    if (!a240.getQuestionSet().equals(a238) && a238.getQuestions().contains(a240)) {
        a238.getQuestions().remove(a240);
        JPA.em().persist(a238);
    }
    if (a240.getQuestionSet().equals(a238) && !a238.getQuestions().contains(a240)) {
        a238.getQuestions().add(a240);
        JPA.em().persist(a238);
    }
    a240.setOrderIndex(0);
    a240.setUnitCategory(null);
    JPA.em().persist(a240);
}


    // == A241
    // Unité dans laquelle s'exprime cette quantité

    StringQuestion a241 = (StringQuestion) questionService.findByCode(QuestionCode.A241);
if (a241 == null) {
    a241 = new StringQuestion(a238, 0, QuestionCode.A241, null);
    JPA.em().persist(a241);
} else {
    a241.setDefaultValue(null);
    if (!a241.getQuestionSet().equals(a238) && a238.getQuestions().contains(a241)) {
        a238.getQuestions().remove(a241);
        JPA.em().persist(a238);
    }
    if (a241.getQuestionSet().equals(a238) && !a238.getQuestions().contains(a241)) {
        a238.getQuestions().add(a241);
        JPA.em().persist(a238);
    }
    a241.setOrderIndex(0);
    JPA.em().persist(a241);
}


    // == A242
    // Facteur d'émission en tCO2e par unité ci-dessus

    
IntegerQuestion a242 = (IntegerQuestion) questionService.findByCode(QuestionCode.A242);
if (a242 == null) {
    a242 = new IntegerQuestion(a238, 0, QuestionCode.A242, null, null);
    JPA.em().persist(a242);
} else {
    a242.setDefaultValue(null);
    if (!a242.getQuestionSet().equals(a238) && a238.getQuestions().contains(a242)) {
        a238.getQuestions().remove(a242);
        JPA.em().persist(a238);
    }
    if (a242.getQuestionSet().equals(a238) && !a238.getQuestions().contains(a242)) {
        a238.getQuestions().add(a242);
        JPA.em().persist(a238);
    }
    a242.setOrderIndex(0);
    a242.setUnitCategory(null);
    JPA.em().persist(a242);
}


    // == A310
    // Fournir ici les documents éventuels justifiant les données suivantes

    DocumentQuestion a310 = (DocumentQuestion) questionService.findByCode(QuestionCode.A310);
if (a310 == null) {
    a310 = new DocumentQuestion(a309, 0, QuestionCode.A310);
    JPA.em().persist(a310);
} else {
    if (!a310.getQuestionSet().equals(a309) && a309.getQuestions().contains(a310)) {
        a309.getQuestions().remove(a310);
        JPA.em().persist(a309);
    }
    if (a310.getQuestionSet().equals(a309) && !a309.getQuestions().contains(a310)) {
        a309.getQuestions().add(a310);
        JPA.em().persist(a309);
    }
    a310.setOrderIndex(0);
    JPA.em().persist(a310);
}


    // == A312
    // Catégorie d'actif loué

    StringQuestion a312 = (StringQuestion) questionService.findByCode(QuestionCode.A312);
if (a312 == null) {
    a312 = new StringQuestion(a311, 0, QuestionCode.A312, null);
    JPA.em().persist(a312);
} else {
    a312.setDefaultValue(null);
    if (!a312.getQuestionSet().equals(a311) && a311.getQuestions().contains(a312)) {
        a311.getQuestions().remove(a312);
        JPA.em().persist(a311);
    }
    if (a312.getQuestionSet().equals(a311) && !a311.getQuestions().contains(a312)) {
        a311.getQuestions().add(a312);
        JPA.em().persist(a311);
    }
    a312.setOrderIndex(0);
    JPA.em().persist(a312);
}


    // == A314
    // Combustible utilisé

    ValueSelectionQuestion a314 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A314);
if (a314 == null) {
    a314 = new ValueSelectionQuestion(a313, 0, QuestionCode.A314, CodeList.COMBUSTIBLE);
    JPA.em().persist(a314);
} else {
    if (!a314.getQuestionSet().equals(a313) && a313.getQuestions().contains(a314)) {
        a313.getQuestions().remove(a314);
        JPA.em().persist(a313);
    }
    if (a314.getQuestionSet().equals(a313) && !a313.getQuestions().contains(a314)) {
        a313.getQuestions().add(a314);
        JPA.em().persist(a313);
    }
    a314.setOrderIndex(0);
    a314.setCodeList(CodeList.COMBUSTIBLE);
    JPA.em().persist(a314);
}


    // == A315
    // Quantité

    
DoubleQuestion a315 = (DoubleQuestion) questionService.findByCode(QuestionCode.A315);
if (a315 == null) {
    a315 = new DoubleQuestion( a313, 0, QuestionCode.A315, energyUnits, null, getUnitBySymbol("kW.h") );
    JPA.em().persist(a315);
} else {
    a315.setDefaultValue(null);
    if (!a315.getQuestionSet().equals(a313) && a313.getQuestions().contains(a315)) {
        a313.getQuestions().remove(a315);
        JPA.em().persist(a313);
    }
    if (a315.getQuestionSet().equals(a313) && !a313.getQuestions().contains(a315)) {
        a313.getQuestions().add(a315);
        JPA.em().persist(a313);
    }
    a315.setUnitCategory(energyUnits);
    a315.setOrderIndex(0);
    a315.setDefaultUnit(getUnitBySymbol("kW.h"));
    JPA.em().persist(a315);
}




    // == A1013
    // Combustible utilisé

    ValueSelectionQuestion a1013 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A1013);
if (a1013 == null) {
    a1013 = new ValueSelectionQuestion(a1012, 0, QuestionCode.A1013, CodeList.COMBUSTIBLEVOLUME);
    JPA.em().persist(a1013);
} else {
    if (!a1013.getQuestionSet().equals(a1012) && a1012.getQuestions().contains(a1013)) {
        a1012.getQuestions().remove(a1013);
        JPA.em().persist(a1012);
    }
    if (a1013.getQuestionSet().equals(a1012) && !a1012.getQuestions().contains(a1013)) {
        a1012.getQuestions().add(a1013);
        JPA.em().persist(a1012);
    }
    a1013.setOrderIndex(0);
    a1013.setCodeList(CodeList.COMBUSTIBLEVOLUME);
    JPA.em().persist(a1013);
}


    // == A1014
    // Quantité

    
DoubleQuestion a1014 = (DoubleQuestion) questionService.findByCode(QuestionCode.A1014);
if (a1014 == null) {
    a1014 = new DoubleQuestion( a1012, 0, QuestionCode.A1014, volumeUnits, null, volumeUnits.getMainUnit() );
    JPA.em().persist(a1014);
} else {
    a1014.setDefaultValue(null);
    if (!a1014.getQuestionSet().equals(a1012) && a1012.getQuestions().contains(a1014)) {
        a1012.getQuestions().remove(a1014);
        JPA.em().persist(a1012);
    }
    if (a1014.getQuestionSet().equals(a1012) && !a1012.getQuestions().contains(a1014)) {
        a1012.getQuestions().add(a1014);
        JPA.em().persist(a1012);
    }
    a1014.setUnitCategory(volumeUnits);
    a1014.setOrderIndex(0);
    a1014.setDefaultUnit(volumeUnits.getMainUnit());
    JPA.em().persist(a1014);
}




    // == A1016
    // Combustible utilisé

    ValueSelectionQuestion a1016 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A1016);
if (a1016 == null) {
    a1016 = new ValueSelectionQuestion(a1015, 0, QuestionCode.A1016, CodeList.COMBUSTIBLEPOIDS);
    JPA.em().persist(a1016);
} else {
    if (!a1016.getQuestionSet().equals(a1015) && a1015.getQuestions().contains(a1016)) {
        a1015.getQuestions().remove(a1016);
        JPA.em().persist(a1015);
    }
    if (a1016.getQuestionSet().equals(a1015) && !a1015.getQuestions().contains(a1016)) {
        a1015.getQuestions().add(a1016);
        JPA.em().persist(a1015);
    }
    a1016.setOrderIndex(0);
    a1016.setCodeList(CodeList.COMBUSTIBLEPOIDS);
    JPA.em().persist(a1016);
}


    // == A1017
    // Quantité

    
DoubleQuestion a1017 = (DoubleQuestion) questionService.findByCode(QuestionCode.A1017);
if (a1017 == null) {
    a1017 = new DoubleQuestion( a1015, 0, QuestionCode.A1017, massUnits, null, massUnits.getMainUnit() );
    JPA.em().persist(a1017);
} else {
    a1017.setDefaultValue(null);
    if (!a1017.getQuestionSet().equals(a1015) && a1015.getQuestions().contains(a1017)) {
        a1015.getQuestions().remove(a1017);
        JPA.em().persist(a1015);
    }
    if (a1017.getQuestionSet().equals(a1015) && !a1015.getQuestions().contains(a1017)) {
        a1015.getQuestions().add(a1017);
        JPA.em().persist(a1015);
    }
    a1017.setUnitCategory(massUnits);
    a1017.setOrderIndex(0);
    a1017.setDefaultUnit(massUnits.getMainUnit());
    JPA.em().persist(a1017);
}




    // == A316
    // Electricité

    
DoubleQuestion a316 = (DoubleQuestion) questionService.findByCode(QuestionCode.A316);
if (a316 == null) {
    a316 = new DoubleQuestion( a311, 0, QuestionCode.A316, energyUnits, null, getUnitBySymbol("kW.h") );
    JPA.em().persist(a316);
} else {
    a316.setDefaultValue(null);
    if (!a316.getQuestionSet().equals(a311) && a311.getQuestions().contains(a316)) {
        a311.getQuestions().remove(a316);
        JPA.em().persist(a311);
    }
    if (a316.getQuestionSet().equals(a311) && !a311.getQuestions().contains(a316)) {
        a311.getQuestions().add(a316);
        JPA.em().persist(a311);
    }
    a316.setUnitCategory(energyUnits);
    a316.setOrderIndex(0);
    a316.setDefaultUnit(getUnitBySymbol("kW.h"));
    JPA.em().persist(a316);
}




    // == A318
    // Type de gaz

    ValueSelectionQuestion a318 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A318);
if (a318 == null) {
    a318 = new ValueSelectionQuestion(a317, 0, QuestionCode.A318, CodeList.FRIGORIGENE);
    JPA.em().persist(a318);
} else {
    if (!a318.getQuestionSet().equals(a317) && a317.getQuestions().contains(a318)) {
        a317.getQuestions().remove(a318);
        JPA.em().persist(a317);
    }
    if (a318.getQuestionSet().equals(a317) && !a317.getQuestions().contains(a318)) {
        a317.getQuestions().add(a318);
        JPA.em().persist(a317);
    }
    a318.setOrderIndex(0);
    a318.setCodeList(CodeList.FRIGORIGENE);
    JPA.em().persist(a318);
}


    // == A319
    // Quantité de recharge nécessaire pour l'année

    
DoubleQuestion a319 = (DoubleQuestion) questionService.findByCode(QuestionCode.A319);
if (a319 == null) {
    a319 = new DoubleQuestion( a317, 0, QuestionCode.A319, massUnits, null, massUnits.getMainUnit() );
    JPA.em().persist(a319);
} else {
    a319.setDefaultValue(null);
    if (!a319.getQuestionSet().equals(a317) && a317.getQuestions().contains(a319)) {
        a317.getQuestions().remove(a319);
        JPA.em().persist(a317);
    }
    if (a319.getQuestionSet().equals(a317) && !a317.getQuestions().contains(a319)) {
        a317.getQuestions().add(a319);
        JPA.em().persist(a317);
    }
    a319.setUnitCategory(massUnits);
    a319.setOrderIndex(0);
    a319.setDefaultUnit(massUnits.getMainUnit());
    JPA.em().persist(a319);
}




    // == A321
    // Fournir ici les documents éventuels justifiant les données suivantes

    DocumentQuestion a321 = (DocumentQuestion) questionService.findByCode(QuestionCode.A321);
if (a321 == null) {
    a321 = new DocumentQuestion(a320, 0, QuestionCode.A321);
    JPA.em().persist(a321);
} else {
    if (!a321.getQuestionSet().equals(a320) && a320.getQuestions().contains(a321)) {
        a320.getQuestions().remove(a321);
        JPA.em().persist(a320);
    }
    if (a321.getQuestionSet().equals(a320) && !a320.getQuestions().contains(a321)) {
        a320.getQuestions().add(a321);
        JPA.em().persist(a320);
    }
    a321.setOrderIndex(0);
    JPA.em().persist(a321);
}


    // == A323
    // Catégorie de franchisé

    StringQuestion a323 = (StringQuestion) questionService.findByCode(QuestionCode.A323);
if (a323 == null) {
    a323 = new StringQuestion(a322, 0, QuestionCode.A323, null);
    JPA.em().persist(a323);
} else {
    a323.setDefaultValue(null);
    if (!a323.getQuestionSet().equals(a322) && a322.getQuestions().contains(a323)) {
        a322.getQuestions().remove(a323);
        JPA.em().persist(a322);
    }
    if (a323.getQuestionSet().equals(a322) && !a322.getQuestions().contains(a323)) {
        a322.getQuestions().add(a323);
        JPA.em().persist(a322);
    }
    a323.setOrderIndex(0);
    JPA.em().persist(a323);
}


    // == A324
    // Nombre de franchisés

    
IntegerQuestion a324 = (IntegerQuestion) questionService.findByCode(QuestionCode.A324);
if (a324 == null) {
    a324 = new IntegerQuestion(a322, 0, QuestionCode.A324, null, null);
    JPA.em().persist(a324);
} else {
    a324.setDefaultValue(null);
    if (!a324.getQuestionSet().equals(a322) && a322.getQuestions().contains(a324)) {
        a322.getQuestions().remove(a324);
        JPA.em().persist(a322);
    }
    if (a324.getQuestionSet().equals(a322) && !a322.getQuestions().contains(a324)) {
        a322.getQuestions().add(a324);
        JPA.em().persist(a322);
    }
    a324.setOrderIndex(0);
    a324.setUnitCategory(null);
    JPA.em().persist(a324);
}


    // == A326
    // Combustible utilisé

    ValueSelectionQuestion a326 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A326);
if (a326 == null) {
    a326 = new ValueSelectionQuestion(a325, 0, QuestionCode.A326, CodeList.COMBUSTIBLE);
    JPA.em().persist(a326);
} else {
    if (!a326.getQuestionSet().equals(a325) && a325.getQuestions().contains(a326)) {
        a325.getQuestions().remove(a326);
        JPA.em().persist(a325);
    }
    if (a326.getQuestionSet().equals(a325) && !a325.getQuestions().contains(a326)) {
        a325.getQuestions().add(a326);
        JPA.em().persist(a325);
    }
    a326.setOrderIndex(0);
    a326.setCodeList(CodeList.COMBUSTIBLE);
    JPA.em().persist(a326);
}


    // == A327
    // Quantité

    
DoubleQuestion a327 = (DoubleQuestion) questionService.findByCode(QuestionCode.A327);
if (a327 == null) {
    a327 = new DoubleQuestion( a325, 0, QuestionCode.A327, energyUnits, null, getUnitBySymbol("kW.h") );
    JPA.em().persist(a327);
} else {
    a327.setDefaultValue(null);
    if (!a327.getQuestionSet().equals(a325) && a325.getQuestions().contains(a327)) {
        a325.getQuestions().remove(a327);
        JPA.em().persist(a325);
    }
    if (a327.getQuestionSet().equals(a325) && !a325.getQuestions().contains(a327)) {
        a325.getQuestions().add(a327);
        JPA.em().persist(a325);
    }
    a327.setUnitCategory(energyUnits);
    a327.setOrderIndex(0);
    a327.setDefaultUnit(getUnitBySymbol("kW.h"));
    JPA.em().persist(a327);
}




    // == A1019
    // Combustible utilisé

    ValueSelectionQuestion a1019 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A1019);
if (a1019 == null) {
    a1019 = new ValueSelectionQuestion(a1018, 0, QuestionCode.A1019, CodeList.COMBUSTIBLEVOLUME);
    JPA.em().persist(a1019);
} else {
    if (!a1019.getQuestionSet().equals(a1018) && a1018.getQuestions().contains(a1019)) {
        a1018.getQuestions().remove(a1019);
        JPA.em().persist(a1018);
    }
    if (a1019.getQuestionSet().equals(a1018) && !a1018.getQuestions().contains(a1019)) {
        a1018.getQuestions().add(a1019);
        JPA.em().persist(a1018);
    }
    a1019.setOrderIndex(0);
    a1019.setCodeList(CodeList.COMBUSTIBLEVOLUME);
    JPA.em().persist(a1019);
}


    // == A1020
    // Quantité

    
DoubleQuestion a1020 = (DoubleQuestion) questionService.findByCode(QuestionCode.A1020);
if (a1020 == null) {
    a1020 = new DoubleQuestion( a1018, 0, QuestionCode.A1020, volumeUnits, null, volumeUnits.getMainUnit() );
    JPA.em().persist(a1020);
} else {
    a1020.setDefaultValue(null);
    if (!a1020.getQuestionSet().equals(a1018) && a1018.getQuestions().contains(a1020)) {
        a1018.getQuestions().remove(a1020);
        JPA.em().persist(a1018);
    }
    if (a1020.getQuestionSet().equals(a1018) && !a1018.getQuestions().contains(a1020)) {
        a1018.getQuestions().add(a1020);
        JPA.em().persist(a1018);
    }
    a1020.setUnitCategory(volumeUnits);
    a1020.setOrderIndex(0);
    a1020.setDefaultUnit(volumeUnits.getMainUnit());
    JPA.em().persist(a1020);
}




    // == A1022
    // Combustible utilisé

    ValueSelectionQuestion a1022 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A1022);
if (a1022 == null) {
    a1022 = new ValueSelectionQuestion(a1021, 0, QuestionCode.A1022, CodeList.COMBUSTIBLEPOIDS);
    JPA.em().persist(a1022);
} else {
    if (!a1022.getQuestionSet().equals(a1021) && a1021.getQuestions().contains(a1022)) {
        a1021.getQuestions().remove(a1022);
        JPA.em().persist(a1021);
    }
    if (a1022.getQuestionSet().equals(a1021) && !a1021.getQuestions().contains(a1022)) {
        a1021.getQuestions().add(a1022);
        JPA.em().persist(a1021);
    }
    a1022.setOrderIndex(0);
    a1022.setCodeList(CodeList.COMBUSTIBLEPOIDS);
    JPA.em().persist(a1022);
}


    // == A1023
    // Quantité

    
DoubleQuestion a1023 = (DoubleQuestion) questionService.findByCode(QuestionCode.A1023);
if (a1023 == null) {
    a1023 = new DoubleQuestion( a1021, 0, QuestionCode.A1023, massUnits, null, massUnits.getMainUnit() );
    JPA.em().persist(a1023);
} else {
    a1023.setDefaultValue(null);
    if (!a1023.getQuestionSet().equals(a1021) && a1021.getQuestions().contains(a1023)) {
        a1021.getQuestions().remove(a1023);
        JPA.em().persist(a1021);
    }
    if (a1023.getQuestionSet().equals(a1021) && !a1021.getQuestions().contains(a1023)) {
        a1021.getQuestions().add(a1023);
        JPA.em().persist(a1021);
    }
    a1023.setUnitCategory(massUnits);
    a1023.setOrderIndex(0);
    a1023.setDefaultUnit(massUnits.getMainUnit());
    JPA.em().persist(a1023);
}




    // == A328
    // Electricité (moyenne par franchisé)

    
DoubleQuestion a328 = (DoubleQuestion) questionService.findByCode(QuestionCode.A328);
if (a328 == null) {
    a328 = new DoubleQuestion( a322, 0, QuestionCode.A328, energyUnits, null, getUnitBySymbol("kW.h") );
    JPA.em().persist(a328);
} else {
    a328.setDefaultValue(null);
    if (!a328.getQuestionSet().equals(a322) && a322.getQuestions().contains(a328)) {
        a322.getQuestions().remove(a328);
        JPA.em().persist(a322);
    }
    if (a328.getQuestionSet().equals(a322) && !a322.getQuestions().contains(a328)) {
        a322.getQuestions().add(a328);
        JPA.em().persist(a322);
    }
    a328.setUnitCategory(energyUnits);
    a328.setOrderIndex(0);
    a328.setDefaultUnit(getUnitBySymbol("kW.h"));
    JPA.em().persist(a328);
}




    // == A330
    // Type de gaz

    ValueSelectionQuestion a330 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A330);
if (a330 == null) {
    a330 = new ValueSelectionQuestion(a329, 0, QuestionCode.A330, CodeList.FRIGORIGENE);
    JPA.em().persist(a330);
} else {
    if (!a330.getQuestionSet().equals(a329) && a329.getQuestions().contains(a330)) {
        a329.getQuestions().remove(a330);
        JPA.em().persist(a329);
    }
    if (a330.getQuestionSet().equals(a329) && !a329.getQuestions().contains(a330)) {
        a329.getQuestions().add(a330);
        JPA.em().persist(a329);
    }
    a330.setOrderIndex(0);
    a330.setCodeList(CodeList.FRIGORIGENE);
    JPA.em().persist(a330);
}


    // == A331
    // Quantité de recharge nécessaire pour l'année

    
DoubleQuestion a331 = (DoubleQuestion) questionService.findByCode(QuestionCode.A331);
if (a331 == null) {
    a331 = new DoubleQuestion( a329, 0, QuestionCode.A331, massUnits, null, massUnits.getMainUnit() );
    JPA.em().persist(a331);
} else {
    a331.setDefaultValue(null);
    if (!a331.getQuestionSet().equals(a329) && a329.getQuestions().contains(a331)) {
        a329.getQuestions().remove(a331);
        JPA.em().persist(a329);
    }
    if (a331.getQuestionSet().equals(a329) && !a329.getQuestions().contains(a331)) {
        a329.getQuestions().add(a331);
        JPA.em().persist(a329);
    }
    a331.setUnitCategory(massUnits);
    a331.setOrderIndex(0);
    a331.setDefaultUnit(massUnits.getMainUnit());
    JPA.em().persist(a331);
}




    // == A333
    // Fournir ici les documents éventuels justifiant les données suivantes

    DocumentQuestion a333 = (DocumentQuestion) questionService.findByCode(QuestionCode.A333);
if (a333 == null) {
    a333 = new DocumentQuestion(a332, 0, QuestionCode.A333);
    JPA.em().persist(a333);
} else {
    if (!a333.getQuestionSet().equals(a332) && a332.getQuestions().contains(a333)) {
        a332.getQuestions().remove(a333);
        JPA.em().persist(a332);
    }
    if (a333.getQuestionSet().equals(a332) && !a332.getQuestions().contains(a333)) {
        a332.getQuestions().add(a333);
        JPA.em().persist(a332);
    }
    a333.setOrderIndex(0);
    JPA.em().persist(a333);
}


    // == A335
    // Nom du projet

    StringQuestion a335 = (StringQuestion) questionService.findByCode(QuestionCode.A335);
if (a335 == null) {
    a335 = new StringQuestion(a334, 0, QuestionCode.A335, null);
    JPA.em().persist(a335);
} else {
    a335.setDefaultValue(null);
    if (!a335.getQuestionSet().equals(a334) && a334.getQuestions().contains(a335)) {
        a334.getQuestions().remove(a335);
        JPA.em().persist(a334);
    }
    if (a335.getQuestionSet().equals(a334) && !a334.getQuestions().contains(a335)) {
        a334.getQuestions().add(a335);
        JPA.em().persist(a334);
    }
    a335.setOrderIndex(0);
    JPA.em().persist(a335);
}


    // == A336
    // Part d'investissements dans le projet

    PercentageQuestion a336 = (PercentageQuestion) questionService.findByCode(QuestionCode.A336);
if (a336 == null) {
    a336 = new PercentageQuestion(a334, 0, QuestionCode.A336, null);
    JPA.em().persist(a336);
} else {
    a336.setDefaultValue(null);
    if (!a336.getQuestionSet().equals(a334) && a334.getQuestions().contains(a336)) {
        a334.getQuestions().remove(a336);
        JPA.em().persist(a334);
    }
    if (a336.getQuestionSet().equals(a334) && !a334.getQuestions().contains(a336)) {
        a334.getQuestions().add(a336);
        JPA.em().persist(a334);
    }
    a336.setOrderIndex(0);
    JPA.em().persist(a336);
}


    // == A337
    // Emissions directes totales (tCO2e)

    
IntegerQuestion a337 = (IntegerQuestion) questionService.findByCode(QuestionCode.A337);
if (a337 == null) {
    a337 = new IntegerQuestion(a334, 0, QuestionCode.A337, null, null);
    JPA.em().persist(a337);
} else {
    a337.setDefaultValue(null);
    if (!a337.getQuestionSet().equals(a334) && a334.getQuestions().contains(a337)) {
        a334.getQuestions().remove(a337);
        JPA.em().persist(a334);
    }
    if (a337.getQuestionSet().equals(a334) && !a334.getQuestions().contains(a337)) {
        a334.getQuestions().add(a337);
        JPA.em().persist(a334);
    }
    a337.setOrderIndex(0);
    a337.setUnitCategory(null);
    JPA.em().persist(a337);
}


    // == A338
    // Emissions indirectes totales (tCO2e)

    
IntegerQuestion a338 = (IntegerQuestion) questionService.findByCode(QuestionCode.A338);
if (a338 == null) {
    a338 = new IntegerQuestion(a334, 0, QuestionCode.A338, null, null);
    JPA.em().persist(a338);
} else {
    a338.setDefaultValue(null);
    if (!a338.getQuestionSet().equals(a334) && a334.getQuestions().contains(a338)) {
        a334.getQuestions().remove(a338);
        JPA.em().persist(a334);
    }
    if (a338.getQuestionSet().equals(a334) && !a334.getQuestions().contains(a338)) {
        a334.getQuestions().add(a338);
        JPA.em().persist(a334);
    }
    a338.setOrderIndex(0);
    a338.setUnitCategory(null);
    JPA.em().persist(a338);
}


    // == A245
    // Nom du produit ou groupe de produits

    StringQuestion a245 = (StringQuestion) questionService.findByCode(QuestionCode.A245);
if (a245 == null) {
    a245 = new StringQuestion(a244, 0, QuestionCode.A245, null);
    JPA.em().persist(a245);
} else {
    a245.setDefaultValue(null);
    if (!a245.getQuestionSet().equals(a244) && a244.getQuestions().contains(a245)) {
        a244.getQuestions().remove(a245);
        JPA.em().persist(a244);
    }
    if (a245.getQuestionSet().equals(a244) && !a244.getQuestions().contains(a245)) {
        a244.getQuestions().add(a245);
        JPA.em().persist(a244);
    }
    a245.setOrderIndex(0);
    JPA.em().persist(a245);
}


    // == A246
    // Quantité vendue de ce produit

    
IntegerQuestion a246 = (IntegerQuestion) questionService.findByCode(QuestionCode.A246);
if (a246 == null) {
    a246 = new IntegerQuestion(a244, 0, QuestionCode.A246, null, null);
    JPA.em().persist(a246);
} else {
    a246.setDefaultValue(null);
    if (!a246.getQuestionSet().equals(a244) && a244.getQuestions().contains(a246)) {
        a244.getQuestions().remove(a246);
        JPA.em().persist(a244);
    }
    if (a246.getQuestionSet().equals(a244) && !a244.getQuestions().contains(a246)) {
        a244.getQuestions().add(a246);
        JPA.em().persist(a244);
    }
    a246.setOrderIndex(0);
    a246.setUnitCategory(null);
    JPA.em().persist(a246);
}


    // == A247
    // Unité dans laquelle s'exprime cette quantité

    StringQuestion a247 = (StringQuestion) questionService.findByCode(QuestionCode.A247);
if (a247 == null) {
    a247 = new StringQuestion(a244, 0, QuestionCode.A247, null);
    JPA.em().persist(a247);
} else {
    a247.setDefaultValue(null);
    if (!a247.getQuestionSet().equals(a244) && a244.getQuestions().contains(a247)) {
        a244.getQuestions().remove(a247);
        JPA.em().persist(a244);
    }
    if (a247.getQuestionSet().equals(a244) && !a244.getQuestions().contains(a247)) {
        a244.getQuestions().add(a247);
        JPA.em().persist(a244);
    }
    a247.setOrderIndex(0);
    JPA.em().persist(a247);
}


    // == A248
    // S'agit-il d'un produit (ou groupe de produits) final ou intermédiaire?

    ValueSelectionQuestion a248 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A248);
if (a248 == null) {
    a248 = new ValueSelectionQuestion(a244, 0, QuestionCode.A248, CodeList.TYPEPRODUIT);
    JPA.em().persist(a248);
} else {
    if (!a248.getQuestionSet().equals(a244) && a244.getQuestions().contains(a248)) {
        a244.getQuestions().remove(a248);
        JPA.em().persist(a244);
    }
    if (a248.getQuestionSet().equals(a244) && !a244.getQuestions().contains(a248)) {
        a244.getQuestions().add(a248);
        JPA.em().persist(a244);
    }
    a248.setOrderIndex(0);
    a248.setCodeList(CodeList.TYPEPRODUIT);
    JPA.em().persist(a248);
}


    // == A249
    // Connaissez-vous la ou les applications ultérieures?

    BooleanQuestion a249 = (BooleanQuestion) questionService.findByCode(QuestionCode.A249);
if (a249 == null) {
    a249 = new BooleanQuestion(a244, 0, QuestionCode.A249, null);
    JPA.em().persist(a249);
} else {
    a249.setDefaultValue(null);
    if (!a249.getQuestionSet().equals(a244) && a244.getQuestions().contains(a249)) {
        a244.getQuestions().remove(a249);
        JPA.em().persist(a244);
    }
    if (a249.getQuestionSet().equals(a244) && !a244.getQuestions().contains(a249)) {
        a244.getQuestions().add(a249);
        JPA.em().persist(a244);
    }
    a249.setOrderIndex(0);
    JPA.em().persist(a249);
}


    // == A251
    // Pièces documentaires liées au transport et stockage aval

    DocumentQuestion a251 = (DocumentQuestion) questionService.findByCode(QuestionCode.A251);
if (a251 == null) {
    a251 = new DocumentQuestion(a250, 0, QuestionCode.A251);
    JPA.em().persist(a251);
} else {
    if (!a251.getQuestionSet().equals(a250) && a250.getQuestions().contains(a251)) {
        a250.getQuestions().remove(a251);
        JPA.em().persist(a250);
    }
    if (a251.getQuestionSet().equals(a250) && !a250.getQuestions().contains(a251)) {
        a250.getQuestions().add(a251);
        JPA.em().persist(a250);
    }
    a251.setOrderIndex(0);
    JPA.em().persist(a251);
}


    // == A254
    // Poids total transporté sur l'année de bilan:

    
DoubleQuestion a254 = (DoubleQuestion) questionService.findByCode(QuestionCode.A254);
if (a254 == null) {
    a254 = new DoubleQuestion( a253, 0, QuestionCode.A254, massUnits, null, getUnitBySymbol("t") );
    JPA.em().persist(a254);
} else {
    a254.setDefaultValue(null);
    if (!a254.getQuestionSet().equals(a253) && a253.getQuestions().contains(a254)) {
        a253.getQuestions().remove(a254);
        JPA.em().persist(a253);
    }
    if (a254.getQuestionSet().equals(a253) && !a253.getQuestions().contains(a254)) {
        a253.getQuestions().add(a254);
        JPA.em().persist(a253);
    }
    a254.setUnitCategory(massUnits);
    a254.setOrderIndex(0);
    a254.setDefaultUnit(getUnitBySymbol("t"));
    JPA.em().persist(a254);
}




    // == A255
    // Distance entre le point d'enlèvement et de livraison du produit

    
DoubleQuestion a255 = (DoubleQuestion) questionService.findByCode(QuestionCode.A255);
if (a255 == null) {
    a255 = new DoubleQuestion( a253, 0, QuestionCode.A255, lengthUnits, null, getUnitBySymbol("km") );
    JPA.em().persist(a255);
} else {
    a255.setDefaultValue(null);
    if (!a255.getQuestionSet().equals(a253) && a253.getQuestions().contains(a255)) {
        a253.getQuestions().remove(a255);
        JPA.em().persist(a253);
    }
    if (a255.getQuestionSet().equals(a253) && !a253.getQuestions().contains(a255)) {
        a253.getQuestions().add(a255);
        JPA.em().persist(a253);
    }
    a255.setUnitCategory(lengthUnits);
    a255.setOrderIndex(0);
    a255.setDefaultUnit(getUnitBySymbol("km"));
    JPA.em().persist(a255);
}




    // == A256
    // % de distance effectuée par transport routier local par camion

    PercentageQuestion a256 = (PercentageQuestion) questionService.findByCode(QuestionCode.A256);
if (a256 == null) {
    a256 = new PercentageQuestion(a253, 0, QuestionCode.A256, null);
    JPA.em().persist(a256);
} else {
    a256.setDefaultValue(null);
    if (!a256.getQuestionSet().equals(a253) && a253.getQuestions().contains(a256)) {
        a253.getQuestions().remove(a256);
        JPA.em().persist(a253);
    }
    if (a256.getQuestionSet().equals(a253) && !a253.getQuestions().contains(a256)) {
        a253.getQuestions().add(a256);
        JPA.em().persist(a253);
    }
    a256.setOrderIndex(0);
    JPA.em().persist(a256);
}


    // == A257
    // % de distance effectuée par transport routier local par camionnette

    PercentageQuestion a257 = (PercentageQuestion) questionService.findByCode(QuestionCode.A257);
if (a257 == null) {
    a257 = new PercentageQuestion(a253, 0, QuestionCode.A257, null);
    JPA.em().persist(a257);
} else {
    a257.setDefaultValue(null);
    if (!a257.getQuestionSet().equals(a253) && a253.getQuestions().contains(a257)) {
        a253.getQuestions().remove(a257);
        JPA.em().persist(a253);
    }
    if (a257.getQuestionSet().equals(a253) && !a253.getQuestions().contains(a257)) {
        a253.getQuestions().add(a257);
        JPA.em().persist(a253);
    }
    a257.setOrderIndex(0);
    JPA.em().persist(a257);
}


    // == A258
    // % de distance effectuée par transport routier international

    PercentageQuestion a258 = (PercentageQuestion) questionService.findByCode(QuestionCode.A258);
if (a258 == null) {
    a258 = new PercentageQuestion(a253, 0, QuestionCode.A258, null);
    JPA.em().persist(a258);
} else {
    a258.setDefaultValue(null);
    if (!a258.getQuestionSet().equals(a253) && a253.getQuestions().contains(a258)) {
        a253.getQuestions().remove(a258);
        JPA.em().persist(a253);
    }
    if (a258.getQuestionSet().equals(a253) && !a253.getQuestions().contains(a258)) {
        a253.getQuestions().add(a258);
        JPA.em().persist(a253);
    }
    a258.setOrderIndex(0);
    JPA.em().persist(a258);
}


    // == A259
    // % de distance effectuée par voie ferroviaire

    PercentageQuestion a259 = (PercentageQuestion) questionService.findByCode(QuestionCode.A259);
if (a259 == null) {
    a259 = new PercentageQuestion(a253, 0, QuestionCode.A259, null);
    JPA.em().persist(a259);
} else {
    a259.setDefaultValue(null);
    if (!a259.getQuestionSet().equals(a253) && a253.getQuestions().contains(a259)) {
        a253.getQuestions().remove(a259);
        JPA.em().persist(a253);
    }
    if (a259.getQuestionSet().equals(a253) && !a253.getQuestions().contains(a259)) {
        a253.getQuestions().add(a259);
        JPA.em().persist(a253);
    }
    a259.setOrderIndex(0);
    JPA.em().persist(a259);
}


    // == A260
    // % de distance effectuée par voie maritime

    PercentageQuestion a260 = (PercentageQuestion) questionService.findByCode(QuestionCode.A260);
if (a260 == null) {
    a260 = new PercentageQuestion(a253, 0, QuestionCode.A260, null);
    JPA.em().persist(a260);
} else {
    a260.setDefaultValue(null);
    if (!a260.getQuestionSet().equals(a253) && a253.getQuestions().contains(a260)) {
        a253.getQuestions().remove(a260);
        JPA.em().persist(a253);
    }
    if (a260.getQuestionSet().equals(a253) && !a253.getQuestions().contains(a260)) {
        a253.getQuestions().add(a260);
        JPA.em().persist(a253);
    }
    a260.setOrderIndex(0);
    JPA.em().persist(a260);
}


    // == A261
    // % de distance effectuée par voie fluviale

    PercentageQuestion a261 = (PercentageQuestion) questionService.findByCode(QuestionCode.A261);
if (a261 == null) {
    a261 = new PercentageQuestion(a253, 0, QuestionCode.A261, null);
    JPA.em().persist(a261);
} else {
    a261.setDefaultValue(null);
    if (!a261.getQuestionSet().equals(a253) && a253.getQuestions().contains(a261)) {
        a253.getQuestions().remove(a261);
        JPA.em().persist(a253);
    }
    if (a261.getQuestionSet().equals(a253) && !a253.getQuestions().contains(a261)) {
        a253.getQuestions().add(a261);
        JPA.em().persist(a253);
    }
    a261.setOrderIndex(0);
    JPA.em().persist(a261);
}


    // == A262
    // % de distance effectuée par transport aérien court courrier (<1000 km)

    PercentageQuestion a262 = (PercentageQuestion) questionService.findByCode(QuestionCode.A262);
if (a262 == null) {
    a262 = new PercentageQuestion(a253, 0, QuestionCode.A262, null);
    JPA.em().persist(a262);
} else {
    a262.setDefaultValue(null);
    if (!a262.getQuestionSet().equals(a253) && a253.getQuestions().contains(a262)) {
        a253.getQuestions().remove(a262);
        JPA.em().persist(a253);
    }
    if (a262.getQuestionSet().equals(a253) && !a253.getQuestions().contains(a262)) {
        a253.getQuestions().add(a262);
        JPA.em().persist(a253);
    }
    a262.setOrderIndex(0);
    JPA.em().persist(a262);
}


    // == A263
    // % de distance effectuée par transport aérien moyen courrier (1000 à 4000 km)

    PercentageQuestion a263 = (PercentageQuestion) questionService.findByCode(QuestionCode.A263);
if (a263 == null) {
    a263 = new PercentageQuestion(a253, 0, QuestionCode.A263, null);
    JPA.em().persist(a263);
} else {
    a263.setDefaultValue(null);
    if (!a263.getQuestionSet().equals(a253) && a253.getQuestions().contains(a263)) {
        a253.getQuestions().remove(a263);
        JPA.em().persist(a253);
    }
    if (a263.getQuestionSet().equals(a253) && !a253.getQuestions().contains(a263)) {
        a253.getQuestions().add(a263);
        JPA.em().persist(a253);
    }
    a263.setOrderIndex(0);
    JPA.em().persist(a263);
}


    // == A264
    // % de distance effectuée par transport aérien long courrier (> 4000 km)

    PercentageQuestion a264 = (PercentageQuestion) questionService.findByCode(QuestionCode.A264);
if (a264 == null) {
    a264 = new PercentageQuestion(a253, 0, QuestionCode.A264, null);
    JPA.em().persist(a264);
} else {
    a264.setDefaultValue(null);
    if (!a264.getQuestionSet().equals(a253) && a253.getQuestions().contains(a264)) {
        a253.getQuestions().remove(a264);
        JPA.em().persist(a253);
    }
    if (a264.getQuestionSet().equals(a253) && !a253.getQuestions().contains(a264)) {
        a253.getQuestions().add(a264);
        JPA.em().persist(a253);
    }
    a264.setOrderIndex(0);
    JPA.em().persist(a264);
}


    // == A265
    // Total (supposé être égal à 100%)

    PercentageQuestion a265 = (PercentageQuestion) questionService.findByCode(QuestionCode.A265);
if (a265 == null) {
    a265 = new PercentageQuestion(a253, 0, QuestionCode.A265, null);
    JPA.em().persist(a265);
} else {
    a265.setDefaultValue(null);
    if (!a265.getQuestionSet().equals(a253) && a253.getQuestions().contains(a265)) {
        a253.getQuestions().remove(a265);
        JPA.em().persist(a253);
    }
    if (a265.getQuestionSet().equals(a253) && !a253.getQuestions().contains(a265)) {
        a253.getQuestions().add(a265);
        JPA.em().persist(a253);
    }
    a265.setOrderIndex(0);
    JPA.em().persist(a265);
}


    // == A267
    // Poids total transporté:

    
DoubleQuestion a267 = (DoubleQuestion) questionService.findByCode(QuestionCode.A267);
if (a267 == null) {
    a267 = new DoubleQuestion( a266, 0, QuestionCode.A267, massUnits, null, getUnitBySymbol("t") );
    JPA.em().persist(a267);
} else {
    a267.setDefaultValue(null);
    if (!a267.getQuestionSet().equals(a266) && a266.getQuestions().contains(a267)) {
        a266.getQuestions().remove(a267);
        JPA.em().persist(a266);
    }
    if (a267.getQuestionSet().equals(a266) && !a266.getQuestions().contains(a267)) {
        a266.getQuestions().add(a267);
        JPA.em().persist(a266);
    }
    a267.setUnitCategory(massUnits);
    a267.setOrderIndex(0);
    a267.setDefaultUnit(getUnitBySymbol("t"));
    JPA.em().persist(a267);
}




    // == A268
    // Quelle est la destination géographique des produits vendus?

    ValueSelectionQuestion a268 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A268);
if (a268 == null) {
    a268 = new ValueSelectionQuestion(a266, 0, QuestionCode.A268, CodeList.PROVENANCESIMPLIFIEE);
    JPA.em().persist(a268);
} else {
    if (!a268.getQuestionSet().equals(a266) && a266.getQuestions().contains(a268)) {
        a266.getQuestions().remove(a268);
        JPA.em().persist(a266);
    }
    if (a268.getQuestionSet().equals(a266) && !a266.getQuestions().contains(a268)) {
        a266.getQuestions().add(a268);
        JPA.em().persist(a266);
    }
    a268.setOrderIndex(0);
    a268.setCodeList(CodeList.PROVENANCESIMPLIFIEE);
    JPA.em().persist(a268);
}


    // == A269
    // Km assignés en moyenne aux marchandises

    
DoubleQuestion a269 = (DoubleQuestion) questionService.findByCode(QuestionCode.A269);
if (a269 == null) {
    a269 = new DoubleQuestion( a266, 0, QuestionCode.A269, lengthUnits, null, getUnitBySymbol("km") );
    JPA.em().persist(a269);
} else {
    a269.setDefaultValue(null);
    if (!a269.getQuestionSet().equals(a266) && a266.getQuestions().contains(a269)) {
        a266.getQuestions().remove(a269);
        JPA.em().persist(a266);
    }
    if (a269.getQuestionSet().equals(a266) && !a266.getQuestions().contains(a269)) {
        a266.getQuestions().add(a269);
        JPA.em().persist(a266);
    }
    a269.setUnitCategory(lengthUnits);
    a269.setOrderIndex(0);
    a269.setDefaultUnit(getUnitBySymbol("km"));
    JPA.em().persist(a269);
}




    // == A270
    // Km assignés en moyenne aux marchandises

    
DoubleQuestion a270 = (DoubleQuestion) questionService.findByCode(QuestionCode.A270);
if (a270 == null) {
    a270 = new DoubleQuestion( a266, 0, QuestionCode.A270, lengthUnits, null, getUnitBySymbol("km") );
    JPA.em().persist(a270);
} else {
    a270.setDefaultValue(null);
    if (!a270.getQuestionSet().equals(a266) && a266.getQuestions().contains(a270)) {
        a266.getQuestions().remove(a270);
        JPA.em().persist(a266);
    }
    if (a270.getQuestionSet().equals(a266) && !a266.getQuestions().contains(a270)) {
        a266.getQuestions().add(a270);
        JPA.em().persist(a266);
    }
    a270.setUnitCategory(lengthUnits);
    a270.setOrderIndex(0);
    a270.setDefaultUnit(getUnitBySymbol("km"));
    JPA.em().persist(a270);
}




    // == A271
    // Km assignés en moyenne aux marchandises

    
DoubleQuestion a271 = (DoubleQuestion) questionService.findByCode(QuestionCode.A271);
if (a271 == null) {
    a271 = new DoubleQuestion( a266, 0, QuestionCode.A271, lengthUnits, null, getUnitBySymbol("km") );
    JPA.em().persist(a271);
} else {
    a271.setDefaultValue(null);
    if (!a271.getQuestionSet().equals(a266) && a266.getQuestions().contains(a271)) {
        a266.getQuestions().remove(a271);
        JPA.em().persist(a266);
    }
    if (a271.getQuestionSet().equals(a266) && !a266.getQuestions().contains(a271)) {
        a266.getQuestions().add(a271);
        JPA.em().persist(a266);
    }
    a271.setUnitCategory(lengthUnits);
    a271.setOrderIndex(0);
    a271.setDefaultUnit(getUnitBySymbol("km"));
    JPA.em().persist(a271);
}




    // == A274
    // Entrepôt

    StringQuestion a274 = (StringQuestion) questionService.findByCode(QuestionCode.A274);
if (a274 == null) {
    a274 = new StringQuestion(a273, 0, QuestionCode.A274, null);
    JPA.em().persist(a274);
} else {
    a274.setDefaultValue(null);
    if (!a274.getQuestionSet().equals(a273) && a273.getQuestions().contains(a274)) {
        a273.getQuestions().remove(a274);
        JPA.em().persist(a273);
    }
    if (a274.getQuestionSet().equals(a273) && !a273.getQuestions().contains(a274)) {
        a273.getQuestions().add(a274);
        JPA.em().persist(a273);
    }
    a274.setOrderIndex(0);
    JPA.em().persist(a274);
}


    // == A276
    // Combustible utilisé

    ValueSelectionQuestion a276 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A276);
if (a276 == null) {
    a276 = new ValueSelectionQuestion(a275, 0, QuestionCode.A276, CodeList.COMBUSTIBLE);
    JPA.em().persist(a276);
} else {
    if (!a276.getQuestionSet().equals(a275) && a275.getQuestions().contains(a276)) {
        a275.getQuestions().remove(a276);
        JPA.em().persist(a275);
    }
    if (a276.getQuestionSet().equals(a275) && !a275.getQuestions().contains(a276)) {
        a275.getQuestions().add(a276);
        JPA.em().persist(a275);
    }
    a276.setOrderIndex(0);
    a276.setCodeList(CodeList.COMBUSTIBLE);
    JPA.em().persist(a276);
}


    // == A277
    // Quantité

    
DoubleQuestion a277 = (DoubleQuestion) questionService.findByCode(QuestionCode.A277);
if (a277 == null) {
    a277 = new DoubleQuestion( a275, 0, QuestionCode.A277, energyUnits, null, getUnitBySymbol("kW.h") );
    JPA.em().persist(a277);
} else {
    a277.setDefaultValue(null);
    if (!a277.getQuestionSet().equals(a275) && a275.getQuestions().contains(a277)) {
        a275.getQuestions().remove(a277);
        JPA.em().persist(a275);
    }
    if (a277.getQuestionSet().equals(a275) && !a275.getQuestions().contains(a277)) {
        a275.getQuestions().add(a277);
        JPA.em().persist(a275);
    }
    a277.setUnitCategory(energyUnits);
    a277.setOrderIndex(0);
    a277.setDefaultUnit(getUnitBySymbol("kW.h"));
    JPA.em().persist(a277);
}




    // == A1025
    // Combustible utilisé

    ValueSelectionQuestion a1025 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A1025);
if (a1025 == null) {
    a1025 = new ValueSelectionQuestion(a1024, 0, QuestionCode.A1025, CodeList.COMBUSTIBLEVOLUME);
    JPA.em().persist(a1025);
} else {
    if (!a1025.getQuestionSet().equals(a1024) && a1024.getQuestions().contains(a1025)) {
        a1024.getQuestions().remove(a1025);
        JPA.em().persist(a1024);
    }
    if (a1025.getQuestionSet().equals(a1024) && !a1024.getQuestions().contains(a1025)) {
        a1024.getQuestions().add(a1025);
        JPA.em().persist(a1024);
    }
    a1025.setOrderIndex(0);
    a1025.setCodeList(CodeList.COMBUSTIBLEVOLUME);
    JPA.em().persist(a1025);
}


    // == A1026
    // Quantité

    
DoubleQuestion a1026 = (DoubleQuestion) questionService.findByCode(QuestionCode.A1026);
if (a1026 == null) {
    a1026 = new DoubleQuestion( a1024, 0, QuestionCode.A1026, volumeUnits, null, volumeUnits.getMainUnit() );
    JPA.em().persist(a1026);
} else {
    a1026.setDefaultValue(null);
    if (!a1026.getQuestionSet().equals(a1024) && a1024.getQuestions().contains(a1026)) {
        a1024.getQuestions().remove(a1026);
        JPA.em().persist(a1024);
    }
    if (a1026.getQuestionSet().equals(a1024) && !a1024.getQuestions().contains(a1026)) {
        a1024.getQuestions().add(a1026);
        JPA.em().persist(a1024);
    }
    a1026.setUnitCategory(volumeUnits);
    a1026.setOrderIndex(0);
    a1026.setDefaultUnit(volumeUnits.getMainUnit());
    JPA.em().persist(a1026);
}




    // == A1028
    // Combustible utilisé

    ValueSelectionQuestion a1028 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A1028);
if (a1028 == null) {
    a1028 = new ValueSelectionQuestion(a1027, 0, QuestionCode.A1028, CodeList.COMBUSTIBLEPOIDS);
    JPA.em().persist(a1028);
} else {
    if (!a1028.getQuestionSet().equals(a1027) && a1027.getQuestions().contains(a1028)) {
        a1027.getQuestions().remove(a1028);
        JPA.em().persist(a1027);
    }
    if (a1028.getQuestionSet().equals(a1027) && !a1027.getQuestions().contains(a1028)) {
        a1027.getQuestions().add(a1028);
        JPA.em().persist(a1027);
    }
    a1028.setOrderIndex(0);
    a1028.setCodeList(CodeList.COMBUSTIBLEPOIDS);
    JPA.em().persist(a1028);
}


    // == A1029
    // Quantité

    
DoubleQuestion a1029 = (DoubleQuestion) questionService.findByCode(QuestionCode.A1029);
if (a1029 == null) {
    a1029 = new DoubleQuestion( a1027, 0, QuestionCode.A1029, massUnits, null, massUnits.getMainUnit() );
    JPA.em().persist(a1029);
} else {
    a1029.setDefaultValue(null);
    if (!a1029.getQuestionSet().equals(a1027) && a1027.getQuestions().contains(a1029)) {
        a1027.getQuestions().remove(a1029);
        JPA.em().persist(a1027);
    }
    if (a1029.getQuestionSet().equals(a1027) && !a1027.getQuestions().contains(a1029)) {
        a1027.getQuestions().add(a1029);
        JPA.em().persist(a1027);
    }
    a1029.setUnitCategory(massUnits);
    a1029.setOrderIndex(0);
    a1029.setDefaultUnit(massUnits.getMainUnit());
    JPA.em().persist(a1029);
}




    // == A278
    // Electricité

    
DoubleQuestion a278 = (DoubleQuestion) questionService.findByCode(QuestionCode.A278);
if (a278 == null) {
    a278 = new DoubleQuestion( a273, 0, QuestionCode.A278, energyUnits, null, getUnitBySymbol("kW.h") );
    JPA.em().persist(a278);
} else {
    a278.setDefaultValue(null);
    if (!a278.getQuestionSet().equals(a273) && a273.getQuestions().contains(a278)) {
        a273.getQuestions().remove(a278);
        JPA.em().persist(a273);
    }
    if (a278.getQuestionSet().equals(a273) && !a273.getQuestions().contains(a278)) {
        a273.getQuestions().add(a278);
        JPA.em().persist(a273);
    }
    a278.setUnitCategory(energyUnits);
    a278.setOrderIndex(0);
    a278.setDefaultUnit(getUnitBySymbol("kW.h"));
    JPA.em().persist(a278);
}




    // == A280
    // Type de gaz

    ValueSelectionQuestion a280 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A280);
if (a280 == null) {
    a280 = new ValueSelectionQuestion(a279, 0, QuestionCode.A280, CodeList.FRIGORIGENE);
    JPA.em().persist(a280);
} else {
    if (!a280.getQuestionSet().equals(a279) && a279.getQuestions().contains(a280)) {
        a279.getQuestions().remove(a280);
        JPA.em().persist(a279);
    }
    if (a280.getQuestionSet().equals(a279) && !a279.getQuestions().contains(a280)) {
        a279.getQuestions().add(a280);
        JPA.em().persist(a279);
    }
    a280.setOrderIndex(0);
    a280.setCodeList(CodeList.FRIGORIGENE);
    JPA.em().persist(a280);
}


    // == A281
    // Quantité de recharge nécessaire pour l'année

    
DoubleQuestion a281 = (DoubleQuestion) questionService.findByCode(QuestionCode.A281);
if (a281 == null) {
    a281 = new DoubleQuestion( a279, 0, QuestionCode.A281, massUnits, null, massUnits.getMainUnit() );
    JPA.em().persist(a281);
} else {
    a281.setDefaultValue(null);
    if (!a281.getQuestionSet().equals(a279) && a279.getQuestions().contains(a281)) {
        a279.getQuestions().remove(a281);
        JPA.em().persist(a279);
    }
    if (a281.getQuestionSet().equals(a279) && !a279.getQuestions().contains(a281)) {
        a279.getQuestions().add(a281);
        JPA.em().persist(a279);
    }
    a281.setUnitCategory(massUnits);
    a281.setOrderIndex(0);
    a281.setDefaultUnit(massUnits.getMainUnit());
    JPA.em().persist(a281);
}




    // == A283
    // Fournir ici les documents éventuels justifiant les données suivantes

    DocumentQuestion a283 = (DocumentQuestion) questionService.findByCode(QuestionCode.A283);
if (a283 == null) {
    a283 = new DocumentQuestion(a282, 0, QuestionCode.A283);
    JPA.em().persist(a283);
} else {
    if (!a283.getQuestionSet().equals(a282) && a282.getQuestions().contains(a283)) {
        a282.getQuestions().remove(a283);
        JPA.em().persist(a282);
    }
    if (a283.getQuestionSet().equals(a282) && !a282.getQuestions().contains(a283)) {
        a282.getQuestions().add(a283);
        JPA.em().persist(a282);
    }
    a283.setOrderIndex(0);
    JPA.em().persist(a283);
}


    // == A285
    // Combustible utilisé

    ValueSelectionQuestion a285 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A285);
if (a285 == null) {
    a285 = new ValueSelectionQuestion(a284, 0, QuestionCode.A285, CodeList.COMBUSTIBLE);
    JPA.em().persist(a285);
} else {
    if (!a285.getQuestionSet().equals(a284) && a284.getQuestions().contains(a285)) {
        a284.getQuestions().remove(a285);
        JPA.em().persist(a284);
    }
    if (a285.getQuestionSet().equals(a284) && !a284.getQuestions().contains(a285)) {
        a284.getQuestions().add(a285);
        JPA.em().persist(a284);
    }
    a285.setOrderIndex(0);
    a285.setCodeList(CodeList.COMBUSTIBLE);
    JPA.em().persist(a285);
}


    // == A286
    // Quantité

    
DoubleQuestion a286 = (DoubleQuestion) questionService.findByCode(QuestionCode.A286);
if (a286 == null) {
    a286 = new DoubleQuestion( a284, 0, QuestionCode.A286, energyUnits, null, getUnitBySymbol("kW.h") );
    JPA.em().persist(a286);
} else {
    a286.setDefaultValue(null);
    if (!a286.getQuestionSet().equals(a284) && a284.getQuestions().contains(a286)) {
        a284.getQuestions().remove(a286);
        JPA.em().persist(a284);
    }
    if (a286.getQuestionSet().equals(a284) && !a284.getQuestions().contains(a286)) {
        a284.getQuestions().add(a286);
        JPA.em().persist(a284);
    }
    a286.setUnitCategory(energyUnits);
    a286.setOrderIndex(0);
    a286.setDefaultUnit(getUnitBySymbol("kW.h"));
    JPA.em().persist(a286);
}




    // == A1031
    // Combustible utilisé

    ValueSelectionQuestion a1031 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A1031);
if (a1031 == null) {
    a1031 = new ValueSelectionQuestion(a1030, 0, QuestionCode.A1031, CodeList.COMBUSTIBLEVOLUME);
    JPA.em().persist(a1031);
} else {
    if (!a1031.getQuestionSet().equals(a1030) && a1030.getQuestions().contains(a1031)) {
        a1030.getQuestions().remove(a1031);
        JPA.em().persist(a1030);
    }
    if (a1031.getQuestionSet().equals(a1030) && !a1030.getQuestions().contains(a1031)) {
        a1030.getQuestions().add(a1031);
        JPA.em().persist(a1030);
    }
    a1031.setOrderIndex(0);
    a1031.setCodeList(CodeList.COMBUSTIBLEVOLUME);
    JPA.em().persist(a1031);
}


    // == A1032
    // Quantité

    
DoubleQuestion a1032 = (DoubleQuestion) questionService.findByCode(QuestionCode.A1032);
if (a1032 == null) {
    a1032 = new DoubleQuestion( a1030, 0, QuestionCode.A1032, volumeUnits, null, volumeUnits.getMainUnit() );
    JPA.em().persist(a1032);
} else {
    a1032.setDefaultValue(null);
    if (!a1032.getQuestionSet().equals(a1030) && a1030.getQuestions().contains(a1032)) {
        a1030.getQuestions().remove(a1032);
        JPA.em().persist(a1030);
    }
    if (a1032.getQuestionSet().equals(a1030) && !a1030.getQuestions().contains(a1032)) {
        a1030.getQuestions().add(a1032);
        JPA.em().persist(a1030);
    }
    a1032.setUnitCategory(volumeUnits);
    a1032.setOrderIndex(0);
    a1032.setDefaultUnit(volumeUnits.getMainUnit());
    JPA.em().persist(a1032);
}




    // == A1034
    // Combustible utilisé

    ValueSelectionQuestion a1034 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A1034);
if (a1034 == null) {
    a1034 = new ValueSelectionQuestion(a1033, 0, QuestionCode.A1034, CodeList.COMBUSTIBLEPOIDS);
    JPA.em().persist(a1034);
} else {
    if (!a1034.getQuestionSet().equals(a1033) && a1033.getQuestions().contains(a1034)) {
        a1033.getQuestions().remove(a1034);
        JPA.em().persist(a1033);
    }
    if (a1034.getQuestionSet().equals(a1033) && !a1033.getQuestions().contains(a1034)) {
        a1033.getQuestions().add(a1034);
        JPA.em().persist(a1033);
    }
    a1034.setOrderIndex(0);
    a1034.setCodeList(CodeList.COMBUSTIBLEPOIDS);
    JPA.em().persist(a1034);
}


    // == A1035
    // Quantité

    
DoubleQuestion a1035 = (DoubleQuestion) questionService.findByCode(QuestionCode.A1035);
if (a1035 == null) {
    a1035 = new DoubleQuestion( a1033, 0, QuestionCode.A1035, massUnits, null, massUnits.getMainUnit() );
    JPA.em().persist(a1035);
} else {
    a1035.setDefaultValue(null);
    if (!a1035.getQuestionSet().equals(a1033) && a1033.getQuestions().contains(a1035)) {
        a1033.getQuestions().remove(a1035);
        JPA.em().persist(a1033);
    }
    if (a1035.getQuestionSet().equals(a1033) && !a1033.getQuestions().contains(a1035)) {
        a1033.getQuestions().add(a1035);
        JPA.em().persist(a1033);
    }
    a1035.setUnitCategory(massUnits);
    a1035.setOrderIndex(0);
    a1035.setDefaultUnit(massUnits.getMainUnit());
    JPA.em().persist(a1035);
}




    // == A287
    // Electricité

    
DoubleQuestion a287 = (DoubleQuestion) questionService.findByCode(QuestionCode.A287);
if (a287 == null) {
    a287 = new DoubleQuestion( a282, 0, QuestionCode.A287, energyUnits, null, getUnitBySymbol("kW.h") );
    JPA.em().persist(a287);
} else {
    a287.setDefaultValue(null);
    if (!a287.getQuestionSet().equals(a282) && a282.getQuestions().contains(a287)) {
        a282.getQuestions().remove(a287);
        JPA.em().persist(a282);
    }
    if (a287.getQuestionSet().equals(a282) && !a282.getQuestions().contains(a287)) {
        a282.getQuestions().add(a287);
        JPA.em().persist(a282);
    }
    a287.setUnitCategory(energyUnits);
    a287.setOrderIndex(0);
    a287.setDefaultUnit(getUnitBySymbol("kW.h"));
    JPA.em().persist(a287);
}




    // == A289
    // Type de gaz

    ValueSelectionQuestion a289 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A289);
if (a289 == null) {
    a289 = new ValueSelectionQuestion(a288, 0, QuestionCode.A289, CodeList.FRIGORIGENE);
    JPA.em().persist(a289);
} else {
    if (!a289.getQuestionSet().equals(a288) && a288.getQuestions().contains(a289)) {
        a288.getQuestions().remove(a289);
        JPA.em().persist(a288);
    }
    if (a289.getQuestionSet().equals(a288) && !a288.getQuestions().contains(a289)) {
        a288.getQuestions().add(a289);
        JPA.em().persist(a288);
    }
    a289.setOrderIndex(0);
    a289.setCodeList(CodeList.FRIGORIGENE);
    JPA.em().persist(a289);
}


    // == A290
    // Quantité de recharge nécessaire pour l'année

    
DoubleQuestion a290 = (DoubleQuestion) questionService.findByCode(QuestionCode.A290);
if (a290 == null) {
    a290 = new DoubleQuestion( a288, 0, QuestionCode.A290, massUnits, null, massUnits.getMainUnit() );
    JPA.em().persist(a290);
} else {
    a290.setDefaultValue(null);
    if (!a290.getQuestionSet().equals(a288) && a288.getQuestions().contains(a290)) {
        a288.getQuestions().remove(a290);
        JPA.em().persist(a288);
    }
    if (a290.getQuestionSet().equals(a288) && !a288.getQuestions().contains(a290)) {
        a288.getQuestions().add(a290);
        JPA.em().persist(a288);
    }
    a290.setUnitCategory(massUnits);
    a290.setOrderIndex(0);
    a290.setDefaultUnit(massUnits.getMainUnit());
    JPA.em().persist(a290);
}




    // == A292
    // Fournir ici les documents éventuels justifiant les données suivantes

    DocumentQuestion a292 = (DocumentQuestion) questionService.findByCode(QuestionCode.A292);
if (a292 == null) {
    a292 = new DocumentQuestion(a291, 0, QuestionCode.A292);
    JPA.em().persist(a292);
} else {
    if (!a292.getQuestionSet().equals(a291) && a291.getQuestions().contains(a292)) {
        a291.getQuestions().remove(a292);
        JPA.em().persist(a291);
    }
    if (a292.getQuestionSet().equals(a291) && !a291.getQuestions().contains(a292)) {
        a291.getQuestions().add(a292);
        JPA.em().persist(a291);
    }
    a292.setOrderIndex(0);
    JPA.em().persist(a292);
}


    // == A293
    // Nombre total d'utilisations du produit ou groupe de produits sur toute sa durée de vie

    
IntegerQuestion a293 = (IntegerQuestion) questionService.findByCode(QuestionCode.A293);
if (a293 == null) {
    a293 = new IntegerQuestion(a291, 0, QuestionCode.A293, null, null);
    JPA.em().persist(a293);
} else {
    a293.setDefaultValue(null);
    if (!a293.getQuestionSet().equals(a291) && a291.getQuestions().contains(a293)) {
        a291.getQuestions().remove(a293);
        JPA.em().persist(a291);
    }
    if (a293.getQuestionSet().equals(a291) && !a291.getQuestions().contains(a293)) {
        a291.getQuestions().add(a293);
        JPA.em().persist(a291);
    }
    a293.setOrderIndex(0);
    a293.setUnitCategory(null);
    JPA.em().persist(a293);
}


    // == A294
    // Consommation de diesel par utilisation de produit

    
DoubleQuestion a294 = (DoubleQuestion) questionService.findByCode(QuestionCode.A294);
if (a294 == null) {
    a294 = new DoubleQuestion( a291, 0, QuestionCode.A294, volumeUnits, null, getUnitBySymbol("l") );
    JPA.em().persist(a294);
} else {
    a294.setDefaultValue(null);
    if (!a294.getQuestionSet().equals(a291) && a291.getQuestions().contains(a294)) {
        a291.getQuestions().remove(a294);
        JPA.em().persist(a291);
    }
    if (a294.getQuestionSet().equals(a291) && !a291.getQuestions().contains(a294)) {
        a291.getQuestions().add(a294);
        JPA.em().persist(a291);
    }
    a294.setUnitCategory(volumeUnits);
    a294.setOrderIndex(0);
    a294.setDefaultUnit(getUnitBySymbol("l"));
    JPA.em().persist(a294);
}




    // == A295
    // Consommation d'essence par utilisation de produit

    
DoubleQuestion a295 = (DoubleQuestion) questionService.findByCode(QuestionCode.A295);
if (a295 == null) {
    a295 = new DoubleQuestion( a291, 0, QuestionCode.A295, volumeUnits, null, getUnitBySymbol("l") );
    JPA.em().persist(a295);
} else {
    a295.setDefaultValue(null);
    if (!a295.getQuestionSet().equals(a291) && a291.getQuestions().contains(a295)) {
        a291.getQuestions().remove(a295);
        JPA.em().persist(a291);
    }
    if (a295.getQuestionSet().equals(a291) && !a291.getQuestions().contains(a295)) {
        a291.getQuestions().add(a295);
        JPA.em().persist(a291);
    }
    a295.setUnitCategory(volumeUnits);
    a295.setOrderIndex(0);
    a295.setDefaultUnit(getUnitBySymbol("l"));
    JPA.em().persist(a295);
}




    // == A296
    // Consommation d'électricité par utilisation de produit

    
DoubleQuestion a296 = (DoubleQuestion) questionService.findByCode(QuestionCode.A296);
if (a296 == null) {
    a296 = new DoubleQuestion( a291, 0, QuestionCode.A296, energyUnits, null, getUnitBySymbol("kW.h") );
    JPA.em().persist(a296);
} else {
    a296.setDefaultValue(null);
    if (!a296.getQuestionSet().equals(a291) && a291.getQuestions().contains(a296)) {
        a291.getQuestions().remove(a296);
        JPA.em().persist(a291);
    }
    if (a296.getQuestionSet().equals(a291) && !a291.getQuestions().contains(a296)) {
        a291.getQuestions().add(a296);
        JPA.em().persist(a291);
    }
    a296.setUnitCategory(energyUnits);
    a296.setOrderIndex(0);
    a296.setDefaultUnit(getUnitBySymbol("kW.h"));
    JPA.em().persist(a296);
}




    // == A298
    // Gaz émis

    ValueSelectionQuestion a298 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A298);
if (a298 == null) {
    a298 = new ValueSelectionQuestion(a297, 0, QuestionCode.A298, CodeList.GESSIMPLIFIE);
    JPA.em().persist(a298);
} else {
    if (!a298.getQuestionSet().equals(a297) && a297.getQuestions().contains(a298)) {
        a297.getQuestions().remove(a298);
        JPA.em().persist(a297);
    }
    if (a298.getQuestionSet().equals(a297) && !a297.getQuestions().contains(a298)) {
        a297.getQuestions().add(a298);
        JPA.em().persist(a297);
    }
    a298.setOrderIndex(0);
    a298.setCodeList(CodeList.GESSIMPLIFIE);
    JPA.em().persist(a298);
}


    // == A299
    // Quantité

    
DoubleQuestion a299 = (DoubleQuestion) questionService.findByCode(QuestionCode.A299);
if (a299 == null) {
    a299 = new DoubleQuestion( a297, 0, QuestionCode.A299, massUnits, null, massUnits.getMainUnit() );
    JPA.em().persist(a299);
} else {
    a299.setDefaultValue(null);
    if (!a299.getQuestionSet().equals(a297) && a297.getQuestions().contains(a299)) {
        a297.getQuestions().remove(a299);
        JPA.em().persist(a297);
    }
    if (a299.getQuestionSet().equals(a297) && !a297.getQuestions().contains(a299)) {
        a297.getQuestions().add(a299);
        JPA.em().persist(a297);
    }
    a299.setUnitCategory(massUnits);
    a299.setOrderIndex(0);
    a299.setDefaultUnit(massUnits.getMainUnit());
    JPA.em().persist(a299);
}




    // == A301
    // Fournir ici les documents éventuels justifiant les données suivantes

    DocumentQuestion a301 = (DocumentQuestion) questionService.findByCode(QuestionCode.A301);
if (a301 == null) {
    a301 = new DocumentQuestion(a300, 0, QuestionCode.A301);
    JPA.em().persist(a301);
} else {
    if (!a301.getQuestionSet().equals(a300) && a300.getQuestions().contains(a301)) {
        a300.getQuestions().remove(a301);
        JPA.em().persist(a300);
    }
    if (a301.getQuestionSet().equals(a300) && !a300.getQuestions().contains(a301)) {
        a300.getQuestions().add(a301);
        JPA.em().persist(a300);
    }
    a301.setOrderIndex(0);
    JPA.em().persist(a301);
}


    // == A302
    // Poids total de produit vendu

    
DoubleQuestion a302 = (DoubleQuestion) questionService.findByCode(QuestionCode.A302);
if (a302 == null) {
    a302 = new DoubleQuestion( a300, 0, QuestionCode.A302, massUnits, null, getUnitBySymbol("t") );
    JPA.em().persist(a302);
} else {
    a302.setDefaultValue(null);
    if (!a302.getQuestionSet().equals(a300) && a300.getQuestions().contains(a302)) {
        a300.getQuestions().remove(a302);
        JPA.em().persist(a300);
    }
    if (a302.getQuestionSet().equals(a300) && !a300.getQuestions().contains(a302)) {
        a300.getQuestions().add(a302);
        JPA.em().persist(a300);
    }
    a302.setUnitCategory(massUnits);
    a302.setOrderIndex(0);
    a302.setDefaultUnit(getUnitBySymbol("t"));
    JPA.em().persist(a302);
}




    // == A5011
    // Poste de déchet

    StringQuestion a5011 = (StringQuestion) questionService.findByCode(QuestionCode.A5011);
if (a5011 == null) {
    a5011 = new StringQuestion(a5010, 0, QuestionCode.A5011, null);
    JPA.em().persist(a5011);
} else {
    a5011.setDefaultValue(null);
    if (!a5011.getQuestionSet().equals(a5010) && a5010.getQuestions().contains(a5011)) {
        a5010.getQuestions().remove(a5011);
        JPA.em().persist(a5010);
    }
    if (a5011.getQuestionSet().equals(a5010) && !a5010.getQuestions().contains(a5011)) {
        a5010.getQuestions().add(a5011);
        JPA.em().persist(a5010);
    }
    a5011.setOrderIndex(0);
    JPA.em().persist(a5011);
}


    // == A5012
    // Poids total de ce poste de déchet issu des produits vendus

    
DoubleQuestion a5012 = (DoubleQuestion) questionService.findByCode(QuestionCode.A5012);
if (a5012 == null) {
    a5012 = new DoubleQuestion( a5010, 0, QuestionCode.A5012, massUnits, null, getUnitBySymbol("t") );
    JPA.em().persist(a5012);
} else {
    a5012.setDefaultValue(null);
    if (!a5012.getQuestionSet().equals(a5010) && a5010.getQuestions().contains(a5012)) {
        a5010.getQuestions().remove(a5012);
        JPA.em().persist(a5010);
    }
    if (a5012.getQuestionSet().equals(a5010) && !a5010.getQuestions().contains(a5012)) {
        a5010.getQuestions().add(a5012);
        JPA.em().persist(a5010);
    }
    a5012.setUnitCategory(massUnits);
    a5012.setOrderIndex(0);
    a5012.setDefaultUnit(getUnitBySymbol("t"));
    JPA.em().persist(a5012);
}




    // == A5013
    // Catégorie principale de ce poste de déchet:

    ValueSelectionQuestion a5013 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A5013);
if (a5013 == null) {
    a5013 = new ValueSelectionQuestion(a5010, 0, QuestionCode.A5013, CodeList.GESTIONDECHETS);
    JPA.em().persist(a5013);
} else {
    if (!a5013.getQuestionSet().equals(a5010) && a5010.getQuestions().contains(a5013)) {
        a5010.getQuestions().remove(a5013);
        JPA.em().persist(a5010);
    }
    if (a5013.getQuestionSet().equals(a5010) && !a5010.getQuestions().contains(a5013)) {
        a5010.getQuestions().add(a5013);
        JPA.em().persist(a5010);
    }
    a5013.setOrderIndex(0);
    a5013.setCodeList(CodeList.GESTIONDECHETS);
    JPA.em().persist(a5013);
}


    // == A5014
    // Proportion du déchet issu du produit, traité par la méthode précédemment renseignée

    PercentageQuestion a5014 = (PercentageQuestion) questionService.findByCode(QuestionCode.A5014);
if (a5014 == null) {
    a5014 = new PercentageQuestion(a5010, 0, QuestionCode.A5014, null);
    JPA.em().persist(a5014);
} else {
    a5014.setDefaultValue(null);
    if (!a5014.getQuestionSet().equals(a5010) && a5010.getQuestions().contains(a5014)) {
        a5010.getQuestions().remove(a5014);
        JPA.em().persist(a5010);
    }
    if (a5014.getQuestionSet().equals(a5010) && !a5010.getQuestions().contains(a5014)) {
        a5010.getQuestions().add(a5014);
        JPA.em().persist(a5010);
    }
    a5014.setOrderIndex(0);
    JPA.em().persist(a5014);
}




        Logger.info("===> CREATE AWAC Enterprise INITIAL DATA -- END (Took {} milliseconds)", (System.currentTimeMillis() - startTime));
    }

    private UnitCategory getUnitCategoryByCode(UnitCategoryCode unitCategoryCode) {
        return unitCategoryService.findByCode(unitCategoryCode);
    }

    private Unit getUnitBySymbol(String symbol) {
        return unitService.findBySymbol(symbol);
    }

}



