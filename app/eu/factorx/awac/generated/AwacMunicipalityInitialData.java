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
public class AwacMunicipalityInitialData {

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


        // delete old questions
		{
			List<Question> allQuestions = questionService.findAll();
            List<String> codes = Arrays.asList("AC3", "AC5", "AC6", "AC7", "AC8", "AC11", "AC12", "AC13", "AC14", "AC15", "AC16", "AC17", "AC18", "AC19", "AC20", "AC21", "AC22", "AC23", "AC26", "AC27", "AC901", "AC902", "AC904", "AC905", "AC30", "AC31", "AC34", "AC35", "AC36", "AC38", "AC40", "AC41", "AC43", "AC5001", "AC5002", "AC53", "AC54", "AC55", "AC57", "AC58", "AC59", "AC61", "AC403", "AC404", "AC405", "AC408", "AC409", "AC410", "AC411", "AC414", "AC415", "AC416", "AC417", "AC503", "AC504", "AC505", "AC508", "AC509", "AC510", "AC511", "AC514", "AC515", "AC516", "AC517", "AC603", "AC604", "AC605", "AC608", "AC609", "AC610", "AC611", "AC614", "AC615", "AC616", "AC617", "AC94", "AC95", "AC96", "AC97", "AC99", "AC100", "AC101", "AC102", "AC103", "AC104", "AC105", "AC108", "AC109", "AC110", "AC111", "AC112", "AC113", "AC115", "AC117", "AC118", "AC119", "AC120", "AC121", "AC122", "AC123", "AC124", "AC125", "AC126", "AC127", "AC128", "AC129", "AC131", "AC133", "AC134", "AC135", "AC136", "AC138", "AC140", "AC141", "AC142", "AC143");

			for (Question q : new ArrayList<>(allQuestions)) {
				if (codes.contains(q.getCode().getKey()) || !q.getCode().getKey().matches("AC[0-9]+")) {
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
            List<String> codes = Arrays.asList("AC1", "AC2", "AC9", "AC10", "AC24", "AC25", "AC900", "AC903", "AC28", "AC29", "AC32", "AC33", "AC37", "AC39", "AC42", "AC5000", "AC52", "AC56", "AC60", "AC62", "AC400", "AC401", "AC402", "AC406", "AC407", "AC412", "AC413", "AC500", "AC501", "AC502", "AC506", "AC507", "AC512", "AC513", "AC600", "AC601", "AC602", "AC606", "AC607", "AC612", "AC613", "AC92", "AC93", "AC98", "AC106", "AC107", "AC114", "AC116", "AC130", "AC132", "AC137", "AC139");

			for (QuestionSet qs : new ArrayList<>(allQuestionSets)) {
				if (codes.contains(qs.getCode().getKey()) || !qs.getCode().getKey().matches("AC[0-9]+")) {
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

    // == TAB_C1
    // DONNEES GENERALES
    Form form1 = formService.findByIdentifier("TAB_C1");
    if (form1 == null) {
        form1 = new Form("TAB_C1");
        JPA.em().persist(form1);
    }
    // == TAB_C2
    // ENERGIE, FROID ET DECHETS
    Form form2 = formService.findByIdentifier("TAB_C2");
    if (form2 == null) {
        form2 = new Form("TAB_C2");
        JPA.em().persist(form2);
    }
    // == TAB_C3
    // MOBILITE
    Form form3 = formService.findByIdentifier("TAB_C3");
    if (form3 == null) {
        form3 = new Form("TAB_C3");
        JPA.em().persist(form3);
    }
    // == TAB_C4
    // ACHATS DE BIENS ET SERVICES
    Form form4 = formService.findByIdentifier("TAB_C4");
    if (form4 == null) {
        form4 = new Form("TAB_C4");
        JPA.em().persist(form4);
    }
    // == TAB_C5
    // INFRASTRUCTURES ET INVESTISSEMENTS
    Form form5 = formService.findByIdentifier("TAB_C5");
    if (form5 == null) {
        form5 = new Form("TAB_C5");
        JPA.em().persist(form5);
    }


    // =========================================================================
    // QUESTION SETS
    // =========================================================================

    // == AC1
    // AWAC - Communes
    QuestionSet ac1 = questionSetService.findByCode(QuestionCode.AC1);
    if( ac1 == null ) {
        ac1 = new QuestionSet(QuestionCode.AC1, false, null);
        JPA.em().persist(ac1);
    }
    form1.getQuestionSets().add(ac1);
    JPA.em().persist(form1);
    // == AC2
    // Introduction - Paramètres de votre commune
    QuestionSet ac2 = questionSetService.findByCode(QuestionCode.AC2);
    if( ac2 == null ) {
        ac2 = new QuestionSet(QuestionCode.AC2, false, ac1);
        JPA.em().persist(ac2);
    }
    // == AC9
    // Energie, froid et déchets
    QuestionSet ac9 = questionSetService.findByCode(QuestionCode.AC9);
    if( ac9 == null ) {
        ac9 = new QuestionSet(QuestionCode.AC9, false, null);
        JPA.em().persist(ac9);
    }
    form2.getQuestionSets().add(ac9);
    JPA.em().persist(form2);
    // == AC10
    // Lister les différents bâtiments ou groupes de bâtiments gérés par la commune
    QuestionSet ac10 = questionSetService.findByCode(QuestionCode.AC10);
    if( ac10 == null ) {
        ac10 = new QuestionSet(QuestionCode.AC10, true, ac9);
        JPA.em().persist(ac10);
    }
    // == AC24
    // Consommation de combustible (chauffage de bâtiments et machines statiques p.e. groupe éléctrogène), mesurés soit en énergie, en volume ou en poids
    QuestionSet ac24 = questionSetService.findByCode(QuestionCode.AC24);
    if( ac24 == null ) {
        ac24 = new QuestionSet(QuestionCode.AC24, false, ac10);
        JPA.em().persist(ac24);
    }
    // == AC25
    // Indiquez ici vos différentes consommations de combustibles exprimés en énergie pour l'ensemble du  bâtiment ou groupe de bâtiments (chauffage et machines statiques)
    QuestionSet ac25 = questionSetService.findByCode(QuestionCode.AC25);
    if( ac25 == null ) {
        ac25 = new QuestionSet(QuestionCode.AC25, true, ac24);
        JPA.em().persist(ac25);
    }
    // == AC900
    // Indiquez ici vos différentes consommations de combustibles exprimés en volume pour l'ensemble du  bâtiment ou groupe de bâtiments (chauffage et machines statiques)
    QuestionSet ac900 = questionSetService.findByCode(QuestionCode.AC900);
    if( ac900 == null ) {
        ac900 = new QuestionSet(QuestionCode.AC900, true, ac24);
        JPA.em().persist(ac900);
    }
    // == AC903
    // Indiquez ici vos différentes consommations de combustibles exprimés en poids pour l'ensemble du  bâtiment ou groupe de bâtiments (chauffage et machines statiques)
    QuestionSet ac903 = questionSetService.findByCode(QuestionCode.AC903);
    if( ac903 == null ) {
        ac903 = new QuestionSet(QuestionCode.AC903, true, ac24);
        JPA.em().persist(ac903);
    }
    // == AC28
    // Consommation d'électricité (achetée sur le réseau ou à des tiers)
    QuestionSet ac28 = questionSetService.findByCode(QuestionCode.AC28);
    if( ac28 == null ) {
        ac28 = new QuestionSet(QuestionCode.AC28, false, ac10);
        JPA.em().persist(ac28);
    }
    // == AC29
    // Indiquez ici vos différentes consommations éléctriques pour ce bâtiment ou groupe de bâtiments (hors éclairage public)
    QuestionSet ac29 = questionSetService.findByCode(QuestionCode.AC29);
    if( ac29 == null ) {
        ac29 = new QuestionSet(QuestionCode.AC29, false, ac28);
        JPA.em().persist(ac29);
    }
    // == AC32
    // Consommation de vapeur (achetée à des tiers)
    QuestionSet ac32 = questionSetService.findByCode(QuestionCode.AC32);
    if( ac32 == null ) {
        ac32 = new QuestionSet(QuestionCode.AC32, false, ac10);
        JPA.em().persist(ac32);
    }
    // == AC33
    // Indiquez ici vos différentes quantités de vapeur achetées pour ce bâtiment ou groupe de bâtiments
    QuestionSet ac33 = questionSetService.findByCode(QuestionCode.AC33);
    if( ac33 == null ) {
        ac33 = new QuestionSet(QuestionCode.AC33, true, ac32);
        JPA.em().persist(ac33);
    }
    // == AC37
    // Utilisation d'un système de refroidissement
    QuestionSet ac37 = questionSetService.findByCode(QuestionCode.AC37);
    if( ac37 == null ) {
        ac37 = new QuestionSet(QuestionCode.AC37, false, ac10);
        JPA.em().persist(ac37);
    }
    // == AC39
    // Ajoutez ici les différents gaz réfrigérant utilisés dans les systèmes de refroidissement  situés dans ce bâtiment ou groupe de bâtiments
    QuestionSet ac39 = questionSetService.findByCode(QuestionCode.AC39);
    if( ac39 == null ) {
        ac39 = new QuestionSet(QuestionCode.AC39, true, ac37);
        JPA.em().persist(ac39);
    }
    // == AC42
    // Production de déchets
    QuestionSet ac42 = questionSetService.findByCode(QuestionCode.AC42);
    if( ac42 == null ) {
        ac42 = new QuestionSet(QuestionCode.AC42, false, ac10);
        JPA.em().persist(ac42);
    }
    // == AC5000
    // Indiquez ici les différentes quantités de déchets produits dans votre bâtiment ou groupe de bâtiments
    QuestionSet ac5000 = questionSetService.findByCode(QuestionCode.AC5000);
    if( ac5000 == null ) {
        ac5000 = new QuestionSet(QuestionCode.AC5000, true, ac42);
        JPA.em().persist(ac5000);
    }
    // == AC52
    // Eclairage public et coffrets de voirie
    QuestionSet ac52 = questionSetService.findByCode(QuestionCode.AC52);
    if( ac52 == null ) {
        ac52 = new QuestionSet(QuestionCode.AC52, false, null);
        JPA.em().persist(ac52);
    }
    form2.getQuestionSets().add(ac52);
    JPA.em().persist(form2);
    // == AC56
    // Créez autant de coffrets de voirie que souhaité
    QuestionSet ac56 = questionSetService.findByCode(QuestionCode.AC56);
    if( ac56 == null ) {
        ac56 = new QuestionSet(QuestionCode.AC56, true, ac52);
        JPA.em().persist(ac56);
    }
    // == AC60
    // Mobilité
    QuestionSet ac60 = questionSetService.findByCode(QuestionCode.AC60);
    if( ac60 == null ) {
        ac60 = new QuestionSet(QuestionCode.AC60, false, null);
        JPA.em().persist(ac60);
    }
    form3.getQuestionSets().add(ac60);
    JPA.em().persist(form3);
    // == AC62
    // Transport routier
    QuestionSet ac62 = questionSetService.findByCode(QuestionCode.AC62);
    if( ac62 == null ) {
        ac62 = new QuestionSet(QuestionCode.AC62, false, ac60);
        JPA.em().persist(ac62);
    }
    // == AC400
    // Véhicules communaux ou détenus par la commune
    QuestionSet ac400 = questionSetService.findByCode(QuestionCode.AC400);
    if( ac400 == null ) {
        ac400 = new QuestionSet(QuestionCode.AC400, false, ac62);
        JPA.em().persist(ac400);
    }
    // == AC401
    // Méthode au choix
    QuestionSet ac401 = questionSetService.findByCode(QuestionCode.AC401);
    if( ac401 == null ) {
        ac401 = new QuestionSet(QuestionCode.AC401, false, ac400);
        JPA.em().persist(ac401);
    }
    // == AC402
    // Calcul par les consommations
    QuestionSet ac402 = questionSetService.findByCode(QuestionCode.AC402);
    if( ac402 == null ) {
        ac402 = new QuestionSet(QuestionCode.AC402, false, ac401);
        JPA.em().persist(ac402);
    }
    // == AC406
    // Calcul par les kilomètres
    QuestionSet ac406 = questionSetService.findByCode(QuestionCode.AC406);
    if( ac406 == null ) {
        ac406 = new QuestionSet(QuestionCode.AC406, false, ac401);
        JPA.em().persist(ac406);
    }
    // == AC407
    // Créez autant de catégories de véhicules que souhaité
    QuestionSet ac407 = questionSetService.findByCode(QuestionCode.AC407);
    if( ac407 == null ) {
        ac407 = new QuestionSet(QuestionCode.AC407, true, ac406);
        JPA.em().persist(ac407);
    }
    // == AC412
    // Calcul par euros dépensés
    QuestionSet ac412 = questionSetService.findByCode(QuestionCode.AC412);
    if( ac412 == null ) {
        ac412 = new QuestionSet(QuestionCode.AC412, false, ac401);
        JPA.em().persist(ac412);
    }
    // == AC413
    // Créez autant de catégories de véhicules que souhaité
    QuestionSet ac413 = questionSetService.findByCode(QuestionCode.AC413);
    if( ac413 == null ) {
        ac413 = new QuestionSet(QuestionCode.AC413, true, ac412);
        JPA.em().persist(ac413);
    }
    // == AC500
    // Autres véhicules pour déplacements domicile-travail des employés
    QuestionSet ac500 = questionSetService.findByCode(QuestionCode.AC500);
    if( ac500 == null ) {
        ac500 = new QuestionSet(QuestionCode.AC500, false, ac62);
        JPA.em().persist(ac500);
    }
    // == AC501
    // Méthode au choix
    QuestionSet ac501 = questionSetService.findByCode(QuestionCode.AC501);
    if( ac501 == null ) {
        ac501 = new QuestionSet(QuestionCode.AC501, false, ac500);
        JPA.em().persist(ac501);
    }
    // == AC502
    // Calcul par les consommations
    QuestionSet ac502 = questionSetService.findByCode(QuestionCode.AC502);
    if( ac502 == null ) {
        ac502 = new QuestionSet(QuestionCode.AC502, false, ac501);
        JPA.em().persist(ac502);
    }
    // == AC506
    // Calcul par les kilomètres
    QuestionSet ac506 = questionSetService.findByCode(QuestionCode.AC506);
    if( ac506 == null ) {
        ac506 = new QuestionSet(QuestionCode.AC506, false, ac501);
        JPA.em().persist(ac506);
    }
    // == AC507
    // Créez autant de catégories de véhicules que souhaité
    QuestionSet ac507 = questionSetService.findByCode(QuestionCode.AC507);
    if( ac507 == null ) {
        ac507 = new QuestionSet(QuestionCode.AC507, true, ac506);
        JPA.em().persist(ac507);
    }
    // == AC512
    // Calcul par euros dépensés
    QuestionSet ac512 = questionSetService.findByCode(QuestionCode.AC512);
    if( ac512 == null ) {
        ac512 = new QuestionSet(QuestionCode.AC512, false, ac501);
        JPA.em().persist(ac512);
    }
    // == AC513
    // Créez autant de catégories de véhicules que souhaité
    QuestionSet ac513 = questionSetService.findByCode(QuestionCode.AC513);
    if( ac513 == null ) {
        ac513 = new QuestionSet(QuestionCode.AC513, true, ac512);
        JPA.em().persist(ac513);
    }
    // == AC600
    // Autres véhicules pour déplacements divers (véhicules loués, visiteurs, sous-traitants…)
    QuestionSet ac600 = questionSetService.findByCode(QuestionCode.AC600);
    if( ac600 == null ) {
        ac600 = new QuestionSet(QuestionCode.AC600, false, ac62);
        JPA.em().persist(ac600);
    }
    // == AC601
    // Méthode au choix
    QuestionSet ac601 = questionSetService.findByCode(QuestionCode.AC601);
    if( ac601 == null ) {
        ac601 = new QuestionSet(QuestionCode.AC601, false, ac600);
        JPA.em().persist(ac601);
    }
    // == AC602
    // Calcul par les consommations
    QuestionSet ac602 = questionSetService.findByCode(QuestionCode.AC602);
    if( ac602 == null ) {
        ac602 = new QuestionSet(QuestionCode.AC602, false, ac601);
        JPA.em().persist(ac602);
    }
    // == AC606
    // Calcul par les kilomètres
    QuestionSet ac606 = questionSetService.findByCode(QuestionCode.AC606);
    if( ac606 == null ) {
        ac606 = new QuestionSet(QuestionCode.AC606, false, ac601);
        JPA.em().persist(ac606);
    }
    // == AC607
    // Créez autant de catégories de véhicules que souhaité
    QuestionSet ac607 = questionSetService.findByCode(QuestionCode.AC607);
    if( ac607 == null ) {
        ac607 = new QuestionSet(QuestionCode.AC607, true, ac606);
        JPA.em().persist(ac607);
    }
    // == AC612
    // Calcul par euros dépensés
    QuestionSet ac612 = questionSetService.findByCode(QuestionCode.AC612);
    if( ac612 == null ) {
        ac612 = new QuestionSet(QuestionCode.AC612, false, ac601);
        JPA.em().persist(ac612);
    }
    // == AC613
    // Créez autant de catégories de véhicules que souhaité
    QuestionSet ac613 = questionSetService.findByCode(QuestionCode.AC613);
    if( ac613 == null ) {
        ac613 = new QuestionSet(QuestionCode.AC613, true, ac612);
        JPA.em().persist(ac613);
    }
    // == AC92
    // Déplacement via les transports publics
    QuestionSet ac92 = questionSetService.findByCode(QuestionCode.AC92);
    if( ac92 == null ) {
        ac92 = new QuestionSet(QuestionCode.AC92, false, ac60);
        JPA.em().persist(ac92);
    }
    // == AC93
    // Déplacements domicile-travail des employés communaux
    QuestionSet ac93 = questionSetService.findByCode(QuestionCode.AC93);
    if( ac93 == null ) {
        ac93 = new QuestionSet(QuestionCode.AC93, false, ac92);
        JPA.em().persist(ac93);
    }
    // == AC98
    // Déplacements professionnels des employés communaux ainsi que des visiteurs de la commune
    QuestionSet ac98 = questionSetService.findByCode(QuestionCode.AC98);
    if( ac98 == null ) {
        ac98 = new QuestionSet(QuestionCode.AC98, false, ac92);
        JPA.em().persist(ac98);
    }
    // == AC106
    // Déplacement et voyages en avion
    QuestionSet ac106 = questionSetService.findByCode(QuestionCode.AC106);
    if( ac106 == null ) {
        ac106 = new QuestionSet(QuestionCode.AC106, false, ac60);
        JPA.em().persist(ac106);
    }
    // == AC107
    // Créez autant de catégories de vol que nécessaire
    QuestionSet ac107 = questionSetService.findByCode(QuestionCode.AC107);
    if( ac107 == null ) {
        ac107 = new QuestionSet(QuestionCode.AC107, true, ac106);
        JPA.em().persist(ac107);
    }
    // == AC114
    // Achat de biens et services
    QuestionSet ac114 = questionSetService.findByCode(QuestionCode.AC114);
    if( ac114 == null ) {
        ac114 = new QuestionSet(QuestionCode.AC114, false, null);
        JPA.em().persist(ac114);
    }
    form4.getQuestionSets().add(ac114);
    JPA.em().persist(form4);
    // == AC116
    // Veuillez  indiquez ici l'ensemble des biens et services que votre commune achète.
    QuestionSet ac116 = questionSetService.findByCode(QuestionCode.AC116);
    if( ac116 == null ) {
        ac116 = new QuestionSet(QuestionCode.AC116, true, ac114);
        JPA.em().persist(ac116);
    }
    // == AC130
    // Infrastructures (achetées durant l'année de déclaration)
    QuestionSet ac130 = questionSetService.findByCode(QuestionCode.AC130);
    if( ac130 == null ) {
        ac130 = new QuestionSet(QuestionCode.AC130, false, null);
        JPA.em().persist(ac130);
    }
    form5.getQuestionSets().add(ac130);
    JPA.em().persist(form5);
    // == AC132
    // Veuillez indiquer ici les différentes infrastructures achetées.
    QuestionSet ac132 = questionSetService.findByCode(QuestionCode.AC132);
    if( ac132 == null ) {
        ac132 = new QuestionSet(QuestionCode.AC132, true, ac130);
        JPA.em().persist(ac132);
    }
    // == AC137
    // Activités d'investissement
    QuestionSet ac137 = questionSetService.findByCode(QuestionCode.AC137);
    if( ac137 == null ) {
        ac137 = new QuestionSet(QuestionCode.AC137, false, null);
        JPA.em().persist(ac137);
    }
    form5.getQuestionSets().add(ac137);
    JPA.em().persist(form5);
    // == AC139
    // Veuillez indiquer ici tous les projets dans lesquels votre commune investit
    QuestionSet ac139 = questionSetService.findByCode(QuestionCode.AC139);
    if( ac139 == null ) {
        ac139 = new QuestionSet(QuestionCode.AC139, true, ac137);
        JPA.em().persist(ac139);
    }


    // =========================================================================
    // QUESTIONS
    // =========================================================================

    // == AC3
    // Année de référence du calcul

    
IntegerQuestion ac3 = (IntegerQuestion) questionService.findByCode(QuestionCode.AC3);
if (ac3 == null) {
    ac3 = new IntegerQuestion(ac2, 0, QuestionCode.AC3, null, null);
    JPA.em().persist(ac3);
} else {
    ac3.setDefaultValue(null);
    if (!ac3.getQuestionSet().equals(ac2) && ac2.getQuestions().contains(ac3)) {
        ac2.getQuestions().remove(ac3);
        JPA.em().persist(ac2);
    }
    if (ac3.getQuestionSet().equals(ac2) && !ac2.getQuestions().contains(ac3)) {
        ac2.getQuestions().add(ac3);
        JPA.em().persist(ac2);
    }
    ac3.setOrderIndex(0);
    ac3.setUnitCategory(null);
    JPA.em().persist(ac3);
}


    // == AC5
    // Nombre d'employés en début d'année de bilan

    
IntegerQuestion ac5 = (IntegerQuestion) questionService.findByCode(QuestionCode.AC5);
if (ac5 == null) {
    ac5 = new IntegerQuestion(ac2, 0, QuestionCode.AC5, null, null);
    JPA.em().persist(ac5);
} else {
    ac5.setDefaultValue(null);
    if (!ac5.getQuestionSet().equals(ac2) && ac2.getQuestions().contains(ac5)) {
        ac2.getQuestions().remove(ac5);
        JPA.em().persist(ac2);
    }
    if (ac5.getQuestionSet().equals(ac2) && !ac2.getQuestions().contains(ac5)) {
        ac2.getQuestions().add(ac5);
        JPA.em().persist(ac2);
    }
    ac5.setOrderIndex(0);
    ac5.setUnitCategory(null);
    JPA.em().persist(ac5);
}


    // == AC6
    // Nombre d'habitants dans la commune en début d'année de bilan

    
IntegerQuestion ac6 = (IntegerQuestion) questionService.findByCode(QuestionCode.AC6);
if (ac6 == null) {
    ac6 = new IntegerQuestion(ac2, 0, QuestionCode.AC6, null, null);
    JPA.em().persist(ac6);
} else {
    ac6.setDefaultValue(null);
    if (!ac6.getQuestionSet().equals(ac2) && ac2.getQuestions().contains(ac6)) {
        ac2.getQuestions().remove(ac6);
        JPA.em().persist(ac2);
    }
    if (ac6.getQuestionSet().equals(ac2) && !ac2.getQuestions().contains(ac6)) {
        ac2.getQuestions().add(ac6);
        JPA.em().persist(ac2);
    }
    ac6.setOrderIndex(0);
    ac6.setUnitCategory(null);
    JPA.em().persist(ac6);
}


    // == AC7
    // Nombre total de bâtiments communaux pris en compte dans le bilan

    
IntegerQuestion ac7 = (IntegerQuestion) questionService.findByCode(QuestionCode.AC7);
if (ac7 == null) {
    ac7 = new IntegerQuestion(ac2, 0, QuestionCode.AC7, null, null);
    JPA.em().persist(ac7);
} else {
    ac7.setDefaultValue(null);
    if (!ac7.getQuestionSet().equals(ac2) && ac2.getQuestions().contains(ac7)) {
        ac2.getQuestions().remove(ac7);
        JPA.em().persist(ac2);
    }
    if (ac7.getQuestionSet().equals(ac2) && !ac2.getQuestions().contains(ac7)) {
        ac2.getQuestions().add(ac7);
        JPA.em().persist(ac2);
    }
    ac7.setOrderIndex(0);
    ac7.setUnitCategory(null);
    JPA.em().persist(ac7);
}


    // == AC8
    // Superficie du territoire

    
DoubleQuestion ac8 = (DoubleQuestion) questionService.findByCode(QuestionCode.AC8);
if (ac8 == null) {
    ac8 = new DoubleQuestion( ac2, 0, QuestionCode.AC8, areaUnits, null, getUnitBySymbol("ha") );
    JPA.em().persist(ac8);
} else {
    ac8.setDefaultValue(null);
    if (!ac8.getQuestionSet().equals(ac2) && ac2.getQuestions().contains(ac8)) {
        ac2.getQuestions().remove(ac8);
        JPA.em().persist(ac2);
    }
    if (ac8.getQuestionSet().equals(ac2) && !ac2.getQuestions().contains(ac8)) {
        ac2.getQuestions().add(ac8);
        JPA.em().persist(ac2);
    }
    ac8.setUnitCategory(areaUnits);
    ac8.setOrderIndex(0);
    ac8.setDefaultUnit(getUnitBySymbol("ha"));
    JPA.em().persist(ac8);
}




    // == AC11
    // Nom du bâtiment ou groupe de bâtiments

    StringQuestion ac11 = (StringQuestion) questionService.findByCode(QuestionCode.AC11);
if (ac11 == null) {
    ac11 = new StringQuestion(ac10, 0, QuestionCode.AC11, null);
    JPA.em().persist(ac11);
} else {
    ac11.setDefaultValue(null);
    if (!ac11.getQuestionSet().equals(ac10) && ac10.getQuestions().contains(ac11)) {
        ac10.getQuestions().remove(ac11);
        JPA.em().persist(ac10);
    }
    if (ac11.getQuestionSet().equals(ac10) && !ac10.getQuestions().contains(ac11)) {
        ac10.getQuestions().add(ac11);
        JPA.em().persist(ac10);
    }
    ac11.setOrderIndex(0);
    JPA.em().persist(ac11);
}


    // == AC12
    // Catégorie du bâtiment

    ValueSelectionQuestion ac12 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AC12);
if (ac12 == null) {
    ac12 = new ValueSelectionQuestion(ac10, 0, QuestionCode.AC12, CodeList.SERVICECOMMUNAL);
    JPA.em().persist(ac12);
} else {
    if (!ac12.getQuestionSet().equals(ac10) && ac10.getQuestions().contains(ac12)) {
        ac10.getQuestions().remove(ac12);
        JPA.em().persist(ac10);
    }
    if (ac12.getQuestionSet().equals(ac10) && !ac10.getQuestions().contains(ac12)) {
        ac10.getQuestions().add(ac12);
        JPA.em().persist(ac10);
    }
    ac12.setOrderIndex(0);
    ac12.setCodeList(CodeList.SERVICECOMMUNAL);
    JPA.em().persist(ac12);
}


    // == AC13
    // Autre, veuillez préciser

    StringQuestion ac13 = (StringQuestion) questionService.findByCode(QuestionCode.AC13);
if (ac13 == null) {
    ac13 = new StringQuestion(ac10, 0, QuestionCode.AC13, null);
    JPA.em().persist(ac13);
} else {
    ac13.setDefaultValue(null);
    if (!ac13.getQuestionSet().equals(ac10) && ac10.getQuestions().contains(ac13)) {
        ac10.getQuestions().remove(ac13);
        JPA.em().persist(ac10);
    }
    if (ac13.getQuestionSet().equals(ac10) && !ac10.getQuestions().contains(ac13)) {
        ac10.getQuestions().add(ac13);
        JPA.em().persist(ac10);
    }
    ac13.setOrderIndex(0);
    JPA.em().persist(ac13);
}


    // == AC14
    // Adresse (rue et numéro):

    StringQuestion ac14 = (StringQuestion) questionService.findByCode(QuestionCode.AC14);
if (ac14 == null) {
    ac14 = new StringQuestion(ac10, 0, QuestionCode.AC14, null);
    JPA.em().persist(ac14);
} else {
    ac14.setDefaultValue(null);
    if (!ac14.getQuestionSet().equals(ac10) && ac10.getQuestions().contains(ac14)) {
        ac10.getQuestions().remove(ac14);
        JPA.em().persist(ac10);
    }
    if (ac14.getQuestionSet().equals(ac10) && !ac10.getQuestions().contains(ac14)) {
        ac10.getQuestions().add(ac14);
        JPA.em().persist(ac10);
    }
    ac14.setOrderIndex(0);
    JPA.em().persist(ac14);
}


    // == AC15
    // Code Postal:

    StringQuestion ac15 = (StringQuestion) questionService.findByCode(QuestionCode.AC15);
if (ac15 == null) {
    ac15 = new StringQuestion(ac10, 0, QuestionCode.AC15, null);
    JPA.em().persist(ac15);
} else {
    ac15.setDefaultValue(null);
    if (!ac15.getQuestionSet().equals(ac10) && ac10.getQuestions().contains(ac15)) {
        ac10.getQuestions().remove(ac15);
        JPA.em().persist(ac10);
    }
    if (ac15.getQuestionSet().equals(ac10) && !ac10.getQuestions().contains(ac15)) {
        ac10.getQuestions().add(ac15);
        JPA.em().persist(ac10);
    }
    ac15.setOrderIndex(0);
    JPA.em().persist(ac15);
}


    // == AC16
    // Nom et prénom de la personne de contact:

    StringQuestion ac16 = (StringQuestion) questionService.findByCode(QuestionCode.AC16);
if (ac16 == null) {
    ac16 = new StringQuestion(ac10, 0, QuestionCode.AC16, null);
    JPA.em().persist(ac16);
} else {
    ac16.setDefaultValue(null);
    if (!ac16.getQuestionSet().equals(ac10) && ac10.getQuestions().contains(ac16)) {
        ac10.getQuestions().remove(ac16);
        JPA.em().persist(ac10);
    }
    if (ac16.getQuestionSet().equals(ac10) && !ac10.getQuestions().contains(ac16)) {
        ac10.getQuestions().add(ac16);
        JPA.em().persist(ac10);
    }
    ac16.setOrderIndex(0);
    JPA.em().persist(ac16);
}


    // == AC17
    // Email de la personne de contact:

    StringQuestion ac17 = (StringQuestion) questionService.findByCode(QuestionCode.AC17);
if (ac17 == null) {
    ac17 = new StringQuestion(ac10, 0, QuestionCode.AC17, null);
    JPA.em().persist(ac17);
} else {
    ac17.setDefaultValue(null);
    if (!ac17.getQuestionSet().equals(ac10) && ac10.getQuestions().contains(ac17)) {
        ac10.getQuestions().remove(ac17);
        JPA.em().persist(ac10);
    }
    if (ac17.getQuestionSet().equals(ac10) && !ac10.getQuestions().contains(ac17)) {
        ac10.getQuestions().add(ac17);
        JPA.em().persist(ac10);
    }
    ac17.setOrderIndex(0);
    JPA.em().persist(ac17);
}


    // == AC18
    // Téléphone de la personne de contact:

    StringQuestion ac18 = (StringQuestion) questionService.findByCode(QuestionCode.AC18);
if (ac18 == null) {
    ac18 = new StringQuestion(ac10, 0, QuestionCode.AC18, null);
    JPA.em().persist(ac18);
} else {
    ac18.setDefaultValue(null);
    if (!ac18.getQuestionSet().equals(ac10) && ac10.getQuestions().contains(ac18)) {
        ac10.getQuestions().remove(ac18);
        JPA.em().persist(ac10);
    }
    if (ac18.getQuestionSet().equals(ac10) && !ac10.getQuestions().contains(ac18)) {
        ac10.getQuestions().add(ac18);
        JPA.em().persist(ac10);
    }
    ac18.setOrderIndex(0);
    JPA.em().persist(ac18);
}


    // == AC19
    // Est-ce un bâtiment dont la commune est propriétaire ou locataire?

    ValueSelectionQuestion ac19 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AC19);
if (ac19 == null) {
    ac19 = new ValueSelectionQuestion(ac10, 0, QuestionCode.AC19, CodeList.GESTIONBATIMENT);
    JPA.em().persist(ac19);
} else {
    if (!ac19.getQuestionSet().equals(ac10) && ac10.getQuestions().contains(ac19)) {
        ac10.getQuestions().remove(ac19);
        JPA.em().persist(ac10);
    }
    if (ac19.getQuestionSet().equals(ac10) && !ac10.getQuestions().contains(ac19)) {
        ac10.getQuestions().add(ac19);
        JPA.em().persist(ac10);
    }
    ac19.setOrderIndex(0);
    ac19.setCodeList(CodeList.GESTIONBATIMENT);
    JPA.em().persist(ac19);
}


    // == AC20
    // Quelle est la superficie totale du (groupe de) bâtiment?

    
DoubleQuestion ac20 = (DoubleQuestion) questionService.findByCode(QuestionCode.AC20);
if (ac20 == null) {
    ac20 = new DoubleQuestion( ac10, 0, QuestionCode.AC20, areaUnits, null, getUnitBySymbol("m2") );
    JPA.em().persist(ac20);
} else {
    ac20.setDefaultValue(null);
    if (!ac20.getQuestionSet().equals(ac10) && ac10.getQuestions().contains(ac20)) {
        ac10.getQuestions().remove(ac20);
        JPA.em().persist(ac10);
    }
    if (ac20.getQuestionSet().equals(ac10) && !ac10.getQuestions().contains(ac20)) {
        ac10.getQuestions().add(ac20);
        JPA.em().persist(ac10);
    }
    ac20.setUnitCategory(areaUnits);
    ac20.setOrderIndex(0);
    ac20.setDefaultUnit(getUnitBySymbol("m2"));
    JPA.em().persist(ac20);
}




    // == AC21
    // Quelle est la superficie chauffée sur tout le (groupe de) bâtiment?

    
DoubleQuestion ac21 = (DoubleQuestion) questionService.findByCode(QuestionCode.AC21);
if (ac21 == null) {
    ac21 = new DoubleQuestion( ac10, 0, QuestionCode.AC21, areaUnits, null, getUnitBySymbol("m2") );
    JPA.em().persist(ac21);
} else {
    ac21.setDefaultValue(null);
    if (!ac21.getQuestionSet().equals(ac10) && ac10.getQuestions().contains(ac21)) {
        ac10.getQuestions().remove(ac21);
        JPA.em().persist(ac10);
    }
    if (ac21.getQuestionSet().equals(ac10) && !ac10.getQuestions().contains(ac21)) {
        ac10.getQuestions().add(ac21);
        JPA.em().persist(ac10);
    }
    ac21.setUnitCategory(areaUnits);
    ac21.setOrderIndex(0);
    ac21.setDefaultUnit(getUnitBySymbol("m2"));
    JPA.em().persist(ac21);
}




    // == AC22
    // Quelle en est le % de la partie chauffée occupé par la commune?

    PercentageQuestion ac22 = (PercentageQuestion) questionService.findByCode(QuestionCode.AC22);
if (ac22 == null) {
    ac22 = new PercentageQuestion(ac10, 0, QuestionCode.AC22, null);
    JPA.em().persist(ac22);
} else {
    ac22.setDefaultValue(null);
    if (!ac22.getQuestionSet().equals(ac10) && ac10.getQuestions().contains(ac22)) {
        ac10.getQuestions().remove(ac22);
        JPA.em().persist(ac10);
    }
    if (ac22.getQuestionSet().equals(ac10) && !ac10.getQuestions().contains(ac22)) {
        ac10.getQuestions().add(ac22);
        JPA.em().persist(ac10);
    }
    ac22.setOrderIndex(0);
    JPA.em().persist(ac22);
}


    // == AC23
    // Fournir ici les documents éventuels justifiant les données de consommation (combustibles, électricité, vapeur)

    DocumentQuestion ac23 = (DocumentQuestion) questionService.findByCode(QuestionCode.AC23);
if (ac23 == null) {
    ac23 = new DocumentQuestion(ac10, 0, QuestionCode.AC23);
    JPA.em().persist(ac23);
} else {
    if (!ac23.getQuestionSet().equals(ac10) && ac10.getQuestions().contains(ac23)) {
        ac10.getQuestions().remove(ac23);
        JPA.em().persist(ac10);
    }
    if (ac23.getQuestionSet().equals(ac10) && !ac10.getQuestions().contains(ac23)) {
        ac10.getQuestions().add(ac23);
        JPA.em().persist(ac10);
    }
    ac23.setOrderIndex(0);
    JPA.em().persist(ac23);
}


    // == AC26
    // Combustible

    ValueSelectionQuestion ac26 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AC26);
if (ac26 == null) {
    ac26 = new ValueSelectionQuestion(ac25, 0, QuestionCode.AC26, CodeList.COMBUSTIBLESIMPLECOMMUNE);
    JPA.em().persist(ac26);
} else {
    if (!ac26.getQuestionSet().equals(ac25) && ac25.getQuestions().contains(ac26)) {
        ac25.getQuestions().remove(ac26);
        JPA.em().persist(ac25);
    }
    if (ac26.getQuestionSet().equals(ac25) && !ac25.getQuestions().contains(ac26)) {
        ac25.getQuestions().add(ac26);
        JPA.em().persist(ac25);
    }
    ac26.setOrderIndex(0);
    ac26.setCodeList(CodeList.COMBUSTIBLESIMPLECOMMUNE);
    JPA.em().persist(ac26);
}


    // == AC27
    // Quantité

    
DoubleQuestion ac27 = (DoubleQuestion) questionService.findByCode(QuestionCode.AC27);
if (ac27 == null) {
    ac27 = new DoubleQuestion( ac25, 0, QuestionCode.AC27, energyUnits, null, energyUnits.getMainUnit() );
    JPA.em().persist(ac27);
} else {
    ac27.setDefaultValue(null);
    if (!ac27.getQuestionSet().equals(ac25) && ac25.getQuestions().contains(ac27)) {
        ac25.getQuestions().remove(ac27);
        JPA.em().persist(ac25);
    }
    if (ac27.getQuestionSet().equals(ac25) && !ac25.getQuestions().contains(ac27)) {
        ac25.getQuestions().add(ac27);
        JPA.em().persist(ac25);
    }
    ac27.setUnitCategory(energyUnits);
    ac27.setOrderIndex(0);
    ac27.setDefaultUnit(energyUnits.getMainUnit());
    JPA.em().persist(ac27);
}




    // == AC901
    // Combustible

    ValueSelectionQuestion ac901 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AC901);
if (ac901 == null) {
    ac901 = new ValueSelectionQuestion(ac900, 0, QuestionCode.AC901, CodeList.COMBUSTIBLESIMPLECOMMUNEVOLUME);
    JPA.em().persist(ac901);
} else {
    if (!ac901.getQuestionSet().equals(ac900) && ac900.getQuestions().contains(ac901)) {
        ac900.getQuestions().remove(ac901);
        JPA.em().persist(ac900);
    }
    if (ac901.getQuestionSet().equals(ac900) && !ac900.getQuestions().contains(ac901)) {
        ac900.getQuestions().add(ac901);
        JPA.em().persist(ac900);
    }
    ac901.setOrderIndex(0);
    ac901.setCodeList(CodeList.COMBUSTIBLESIMPLECOMMUNEVOLUME);
    JPA.em().persist(ac901);
}


    // == AC902
    // Quantité

    
DoubleQuestion ac902 = (DoubleQuestion) questionService.findByCode(QuestionCode.AC902);
if (ac902 == null) {
    ac902 = new DoubleQuestion( ac900, 0, QuestionCode.AC902, volumeUnits, null, volumeUnits.getMainUnit() );
    JPA.em().persist(ac902);
} else {
    ac902.setDefaultValue(null);
    if (!ac902.getQuestionSet().equals(ac900) && ac900.getQuestions().contains(ac902)) {
        ac900.getQuestions().remove(ac902);
        JPA.em().persist(ac900);
    }
    if (ac902.getQuestionSet().equals(ac900) && !ac900.getQuestions().contains(ac902)) {
        ac900.getQuestions().add(ac902);
        JPA.em().persist(ac900);
    }
    ac902.setUnitCategory(volumeUnits);
    ac902.setOrderIndex(0);
    ac902.setDefaultUnit(volumeUnits.getMainUnit());
    JPA.em().persist(ac902);
}




    // == AC904
    // Combustible

    ValueSelectionQuestion ac904 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AC904);
if (ac904 == null) {
    ac904 = new ValueSelectionQuestion(ac903, 0, QuestionCode.AC904, CodeList.COMBUSTIBLESIMPLECOMMUNEPOIDS);
    JPA.em().persist(ac904);
} else {
    if (!ac904.getQuestionSet().equals(ac903) && ac903.getQuestions().contains(ac904)) {
        ac903.getQuestions().remove(ac904);
        JPA.em().persist(ac903);
    }
    if (ac904.getQuestionSet().equals(ac903) && !ac903.getQuestions().contains(ac904)) {
        ac903.getQuestions().add(ac904);
        JPA.em().persist(ac903);
    }
    ac904.setOrderIndex(0);
    ac904.setCodeList(CodeList.COMBUSTIBLESIMPLECOMMUNEPOIDS);
    JPA.em().persist(ac904);
}


    // == AC905
    // Quantité

    
DoubleQuestion ac905 = (DoubleQuestion) questionService.findByCode(QuestionCode.AC905);
if (ac905 == null) {
    ac905 = new DoubleQuestion( ac903, 0, QuestionCode.AC905, massUnits, null, massUnits.getMainUnit() );
    JPA.em().persist(ac905);
} else {
    ac905.setDefaultValue(null);
    if (!ac905.getQuestionSet().equals(ac903) && ac903.getQuestions().contains(ac905)) {
        ac903.getQuestions().remove(ac905);
        JPA.em().persist(ac903);
    }
    if (ac905.getQuestionSet().equals(ac903) && !ac903.getQuestions().contains(ac905)) {
        ac903.getQuestions().add(ac905);
        JPA.em().persist(ac903);
    }
    ac905.setUnitCategory(massUnits);
    ac905.setOrderIndex(0);
    ac905.setDefaultUnit(massUnits.getMainUnit());
    JPA.em().persist(ac905);
}




    // == AC30
    // Electricité grise

    
DoubleQuestion ac30 = (DoubleQuestion) questionService.findByCode(QuestionCode.AC30);
if (ac30 == null) {
    ac30 = new DoubleQuestion( ac29, 0, QuestionCode.AC30, energyUnits, null, getUnitBySymbol("kW.h") );
    JPA.em().persist(ac30);
} else {
    ac30.setDefaultValue(null);
    if (!ac30.getQuestionSet().equals(ac29) && ac29.getQuestions().contains(ac30)) {
        ac29.getQuestions().remove(ac30);
        JPA.em().persist(ac29);
    }
    if (ac30.getQuestionSet().equals(ac29) && !ac29.getQuestions().contains(ac30)) {
        ac29.getQuestions().add(ac30);
        JPA.em().persist(ac29);
    }
    ac30.setUnitCategory(energyUnits);
    ac30.setOrderIndex(0);
    ac30.setDefaultUnit(getUnitBySymbol("kW.h"));
    JPA.em().persist(ac30);
}




    // == AC31
    // Electricité verte

    
DoubleQuestion ac31 = (DoubleQuestion) questionService.findByCode(QuestionCode.AC31);
if (ac31 == null) {
    ac31 = new DoubleQuestion( ac29, 0, QuestionCode.AC31, energyUnits, null, getUnitBySymbol("kW.h") );
    JPA.em().persist(ac31);
} else {
    ac31.setDefaultValue(null);
    if (!ac31.getQuestionSet().equals(ac29) && ac29.getQuestions().contains(ac31)) {
        ac29.getQuestions().remove(ac31);
        JPA.em().persist(ac29);
    }
    if (ac31.getQuestionSet().equals(ac29) && !ac29.getQuestions().contains(ac31)) {
        ac29.getQuestions().add(ac31);
        JPA.em().persist(ac29);
    }
    ac31.setUnitCategory(energyUnits);
    ac31.setOrderIndex(0);
    ac31.setDefaultUnit(getUnitBySymbol("kW.h"));
    JPA.em().persist(ac31);
}




    // == AC34
    // Quel est le type d'énergie primaire utilisé pour produire la vapeur? 

    ValueSelectionQuestion ac34 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AC34);
if (ac34 == null) {
    ac34 = new ValueSelectionQuestion(ac33, 0, QuestionCode.AC34, CodeList.ENERGIEVAPEUR);
    JPA.em().persist(ac34);
} else {
    if (!ac34.getQuestionSet().equals(ac33) && ac33.getQuestions().contains(ac34)) {
        ac33.getQuestions().remove(ac34);
        JPA.em().persist(ac33);
    }
    if (ac34.getQuestionSet().equals(ac33) && !ac33.getQuestions().contains(ac34)) {
        ac33.getQuestions().add(ac34);
        JPA.em().persist(ac33);
    }
    ac34.setOrderIndex(0);
    ac34.setCodeList(CodeList.ENERGIEVAPEUR);
    JPA.em().persist(ac34);
}


    // == AC35
    // Efficacité de la chaudière

    PercentageQuestion ac35 = (PercentageQuestion) questionService.findByCode(QuestionCode.AC35);
if (ac35 == null) {
    ac35 = new PercentageQuestion(ac33, 0, QuestionCode.AC35, null);
    JPA.em().persist(ac35);
} else {
    ac35.setDefaultValue(null);
    if (!ac35.getQuestionSet().equals(ac33) && ac33.getQuestions().contains(ac35)) {
        ac33.getQuestions().remove(ac35);
        JPA.em().persist(ac33);
    }
    if (ac35.getQuestionSet().equals(ac33) && !ac33.getQuestions().contains(ac35)) {
        ac33.getQuestions().add(ac35);
        JPA.em().persist(ac33);
    }
    ac35.setOrderIndex(0);
    JPA.em().persist(ac35);
}


    // == AC36
    // Consommation annuelle de vapeur

    
DoubleQuestion ac36 = (DoubleQuestion) questionService.findByCode(QuestionCode.AC36);
if (ac36 == null) {
    ac36 = new DoubleQuestion( ac33, 0, QuestionCode.AC36, energyUnits, null, energyUnits.getMainUnit() );
    JPA.em().persist(ac36);
} else {
    ac36.setDefaultValue(null);
    if (!ac36.getQuestionSet().equals(ac33) && ac33.getQuestions().contains(ac36)) {
        ac33.getQuestions().remove(ac36);
        JPA.em().persist(ac33);
    }
    if (ac36.getQuestionSet().equals(ac33) && !ac33.getQuestions().contains(ac36)) {
        ac33.getQuestions().add(ac36);
        JPA.em().persist(ac33);
    }
    ac36.setUnitCategory(energyUnits);
    ac36.setOrderIndex(0);
    ac36.setDefaultUnit(energyUnits.getMainUnit());
    JPA.em().persist(ac36);
}




    // == AC38
    // Fournir ici les documents éventuels justifiant les données suivantes

    DocumentQuestion ac38 = (DocumentQuestion) questionService.findByCode(QuestionCode.AC38);
if (ac38 == null) {
    ac38 = new DocumentQuestion(ac37, 0, QuestionCode.AC38);
    JPA.em().persist(ac38);
} else {
    if (!ac38.getQuestionSet().equals(ac37) && ac37.getQuestions().contains(ac38)) {
        ac37.getQuestions().remove(ac38);
        JPA.em().persist(ac37);
    }
    if (ac38.getQuestionSet().equals(ac37) && !ac37.getQuestions().contains(ac38)) {
        ac37.getQuestions().add(ac38);
        JPA.em().persist(ac37);
    }
    ac38.setOrderIndex(0);
    JPA.em().persist(ac38);
}


    // == AC40
    // Type de gaz réfrigérant

    ValueSelectionQuestion ac40 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AC40);
if (ac40 == null) {
    ac40 = new ValueSelectionQuestion(ac39, 0, QuestionCode.AC40, CodeList.FRIGORIGENE);
    JPA.em().persist(ac40);
} else {
    if (!ac40.getQuestionSet().equals(ac39) && ac39.getQuestions().contains(ac40)) {
        ac39.getQuestions().remove(ac40);
        JPA.em().persist(ac39);
    }
    if (ac40.getQuestionSet().equals(ac39) && !ac39.getQuestions().contains(ac40)) {
        ac39.getQuestions().add(ac40);
        JPA.em().persist(ac39);
    }
    ac40.setOrderIndex(0);
    ac40.setCodeList(CodeList.FRIGORIGENE);
    JPA.em().persist(ac40);
}


    // == AC41
    // Quantité de recharge nécessaire (pour l'année d'utilisation rapportée)

    
DoubleQuestion ac41 = (DoubleQuestion) questionService.findByCode(QuestionCode.AC41);
if (ac41 == null) {
    ac41 = new DoubleQuestion( ac39, 0, QuestionCode.AC41, massUnits, null, massUnits.getMainUnit() );
    JPA.em().persist(ac41);
} else {
    ac41.setDefaultValue(null);
    if (!ac41.getQuestionSet().equals(ac39) && ac39.getQuestions().contains(ac41)) {
        ac39.getQuestions().remove(ac41);
        JPA.em().persist(ac39);
    }
    if (ac41.getQuestionSet().equals(ac39) && !ac39.getQuestions().contains(ac41)) {
        ac39.getQuestions().add(ac41);
        JPA.em().persist(ac39);
    }
    ac41.setUnitCategory(massUnits);
    ac41.setOrderIndex(0);
    ac41.setDefaultUnit(massUnits.getMainUnit());
    JPA.em().persist(ac41);
}




    // == AC43
    // Fournir ici les documents éventuels justifiant les données suivantes

    DocumentQuestion ac43 = (DocumentQuestion) questionService.findByCode(QuestionCode.AC43);
if (ac43 == null) {
    ac43 = new DocumentQuestion(ac42, 0, QuestionCode.AC43);
    JPA.em().persist(ac43);
} else {
    if (!ac43.getQuestionSet().equals(ac42) && ac42.getQuestions().contains(ac43)) {
        ac42.getQuestions().remove(ac43);
        JPA.em().persist(ac42);
    }
    if (ac43.getQuestionSet().equals(ac42) && !ac42.getQuestions().contains(ac43)) {
        ac42.getQuestions().add(ac43);
        JPA.em().persist(ac42);
    }
    ac43.setOrderIndex(0);
    JPA.em().persist(ac43);
}


    // == AC5001
    // Catégorie de déchet

    ValueSelectionQuestion ac5001 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AC5001);
if (ac5001 == null) {
    ac5001 = new ValueSelectionQuestion(ac5000, 0, QuestionCode.AC5001, CodeList.GESTIONDECHETS);
    JPA.em().persist(ac5001);
} else {
    if (!ac5001.getQuestionSet().equals(ac5000) && ac5000.getQuestions().contains(ac5001)) {
        ac5000.getQuestions().remove(ac5001);
        JPA.em().persist(ac5000);
    }
    if (ac5001.getQuestionSet().equals(ac5000) && !ac5000.getQuestions().contains(ac5001)) {
        ac5000.getQuestions().add(ac5001);
        JPA.em().persist(ac5000);
    }
    ac5001.setOrderIndex(0);
    ac5001.setCodeList(CodeList.GESTIONDECHETS);
    JPA.em().persist(ac5001);
}


    // == AC5002
    // Quantité de déchets 

    
DoubleQuestion ac5002 = (DoubleQuestion) questionService.findByCode(QuestionCode.AC5002);
if (ac5002 == null) {
    ac5002 = new DoubleQuestion( ac5000, 0, QuestionCode.AC5002, massUnits, null, getUnitBySymbol("t") );
    JPA.em().persist(ac5002);
} else {
    ac5002.setDefaultValue(null);
    if (!ac5002.getQuestionSet().equals(ac5000) && ac5000.getQuestions().contains(ac5002)) {
        ac5000.getQuestions().remove(ac5002);
        JPA.em().persist(ac5000);
    }
    if (ac5002.getQuestionSet().equals(ac5000) && !ac5000.getQuestions().contains(ac5002)) {
        ac5000.getQuestions().add(ac5002);
        JPA.em().persist(ac5000);
    }
    ac5002.setUnitCategory(massUnits);
    ac5002.setOrderIndex(0);
    ac5002.setDefaultUnit(getUnitBySymbol("t"));
    JPA.em().persist(ac5002);
}




    // == AC53
    // Fournir ici les documents éventuels justifiant les données suivantes

    DocumentQuestion ac53 = (DocumentQuestion) questionService.findByCode(QuestionCode.AC53);
if (ac53 == null) {
    ac53 = new DocumentQuestion(ac52, 0, QuestionCode.AC53);
    JPA.em().persist(ac53);
} else {
    if (!ac53.getQuestionSet().equals(ac52) && ac52.getQuestions().contains(ac53)) {
        ac52.getQuestions().remove(ac53);
        JPA.em().persist(ac52);
    }
    if (ac53.getQuestionSet().equals(ac52) && !ac52.getQuestions().contains(ac53)) {
        ac52.getQuestions().add(ac53);
        JPA.em().persist(ac52);
    }
    ac53.setOrderIndex(0);
    JPA.em().persist(ac53);
}


    // == AC54
    // Eclairage public: consommation d'électricité verte

    
DoubleQuestion ac54 = (DoubleQuestion) questionService.findByCode(QuestionCode.AC54);
if (ac54 == null) {
    ac54 = new DoubleQuestion( ac52, 0, QuestionCode.AC54, energyUnits, null, getUnitBySymbol("kW.h") );
    JPA.em().persist(ac54);
} else {
    ac54.setDefaultValue(null);
    if (!ac54.getQuestionSet().equals(ac52) && ac52.getQuestions().contains(ac54)) {
        ac52.getQuestions().remove(ac54);
        JPA.em().persist(ac52);
    }
    if (ac54.getQuestionSet().equals(ac52) && !ac52.getQuestions().contains(ac54)) {
        ac52.getQuestions().add(ac54);
        JPA.em().persist(ac52);
    }
    ac54.setUnitCategory(energyUnits);
    ac54.setOrderIndex(0);
    ac54.setDefaultUnit(getUnitBySymbol("kW.h"));
    JPA.em().persist(ac54);
}




    // == AC55
    // Eclairage public: consommation d'électricité grise

    
DoubleQuestion ac55 = (DoubleQuestion) questionService.findByCode(QuestionCode.AC55);
if (ac55 == null) {
    ac55 = new DoubleQuestion( ac52, 0, QuestionCode.AC55, energyUnits, null, getUnitBySymbol("kW.h") );
    JPA.em().persist(ac55);
} else {
    ac55.setDefaultValue(null);
    if (!ac55.getQuestionSet().equals(ac52) && ac52.getQuestions().contains(ac55)) {
        ac52.getQuestions().remove(ac55);
        JPA.em().persist(ac52);
    }
    if (ac55.getQuestionSet().equals(ac52) && !ac52.getQuestions().contains(ac55)) {
        ac52.getQuestions().add(ac55);
        JPA.em().persist(ac52);
    }
    ac55.setUnitCategory(energyUnits);
    ac55.setOrderIndex(0);
    ac55.setDefaultUnit(getUnitBySymbol("kW.h"));
    JPA.em().persist(ac55);
}




    // == AC57
    // Désignation du coffret

    StringQuestion ac57 = (StringQuestion) questionService.findByCode(QuestionCode.AC57);
if (ac57 == null) {
    ac57 = new StringQuestion(ac56, 0, QuestionCode.AC57, null);
    JPA.em().persist(ac57);
} else {
    ac57.setDefaultValue(null);
    if (!ac57.getQuestionSet().equals(ac56) && ac56.getQuestions().contains(ac57)) {
        ac56.getQuestions().remove(ac57);
        JPA.em().persist(ac56);
    }
    if (ac57.getQuestionSet().equals(ac56) && !ac56.getQuestions().contains(ac57)) {
        ac56.getQuestions().add(ac57);
        JPA.em().persist(ac56);
    }
    ac57.setOrderIndex(0);
    JPA.em().persist(ac57);
}


    // == AC58
    // Consommation d'électricité verte du coffret

    
DoubleQuestion ac58 = (DoubleQuestion) questionService.findByCode(QuestionCode.AC58);
if (ac58 == null) {
    ac58 = new DoubleQuestion( ac56, 0, QuestionCode.AC58, energyUnits, null, getUnitBySymbol("kW.h") );
    JPA.em().persist(ac58);
} else {
    ac58.setDefaultValue(null);
    if (!ac58.getQuestionSet().equals(ac56) && ac56.getQuestions().contains(ac58)) {
        ac56.getQuestions().remove(ac58);
        JPA.em().persist(ac56);
    }
    if (ac58.getQuestionSet().equals(ac56) && !ac56.getQuestions().contains(ac58)) {
        ac56.getQuestions().add(ac58);
        JPA.em().persist(ac56);
    }
    ac58.setUnitCategory(energyUnits);
    ac58.setOrderIndex(0);
    ac58.setDefaultUnit(getUnitBySymbol("kW.h"));
    JPA.em().persist(ac58);
}




    // == AC59
    // Consommation d'électricité grise du coffret

    
DoubleQuestion ac59 = (DoubleQuestion) questionService.findByCode(QuestionCode.AC59);
if (ac59 == null) {
    ac59 = new DoubleQuestion( ac56, 0, QuestionCode.AC59, energyUnits, null, getUnitBySymbol("kW.h") );
    JPA.em().persist(ac59);
} else {
    ac59.setDefaultValue(null);
    if (!ac59.getQuestionSet().equals(ac56) && ac56.getQuestions().contains(ac59)) {
        ac56.getQuestions().remove(ac59);
        JPA.em().persist(ac56);
    }
    if (ac59.getQuestionSet().equals(ac56) && !ac56.getQuestions().contains(ac59)) {
        ac56.getQuestions().add(ac59);
        JPA.em().persist(ac56);
    }
    ac59.setUnitCategory(energyUnits);
    ac59.setOrderIndex(0);
    ac59.setDefaultUnit(getUnitBySymbol("kW.h"));
    JPA.em().persist(ac59);
}




    // == AC61
    // Fournir ici les documents éventuels justifiant les données suivantes

    DocumentQuestion ac61 = (DocumentQuestion) questionService.findByCode(QuestionCode.AC61);
if (ac61 == null) {
    ac61 = new DocumentQuestion(ac60, 0, QuestionCode.AC61);
    JPA.em().persist(ac61);
} else {
    if (!ac61.getQuestionSet().equals(ac60) && ac60.getQuestions().contains(ac61)) {
        ac60.getQuestions().remove(ac61);
        JPA.em().persist(ac60);
    }
    if (ac61.getQuestionSet().equals(ac60) && !ac60.getQuestions().contains(ac61)) {
        ac60.getQuestions().add(ac61);
        JPA.em().persist(ac60);
    }
    ac61.setOrderIndex(0);
    JPA.em().persist(ac61);
}


    // == AC403
    // Consommation d'essence

    
DoubleQuestion ac403 = (DoubleQuestion) questionService.findByCode(QuestionCode.AC403);
if (ac403 == null) {
    ac403 = new DoubleQuestion( ac402, 0, QuestionCode.AC403, volumeUnits, null, volumeUnits.getMainUnit() );
    JPA.em().persist(ac403);
} else {
    ac403.setDefaultValue(null);
    if (!ac403.getQuestionSet().equals(ac402) && ac402.getQuestions().contains(ac403)) {
        ac402.getQuestions().remove(ac403);
        JPA.em().persist(ac402);
    }
    if (ac403.getQuestionSet().equals(ac402) && !ac402.getQuestions().contains(ac403)) {
        ac402.getQuestions().add(ac403);
        JPA.em().persist(ac402);
    }
    ac403.setUnitCategory(volumeUnits);
    ac403.setOrderIndex(0);
    ac403.setDefaultUnit(volumeUnits.getMainUnit());
    JPA.em().persist(ac403);
}




    // == AC404
    // Consommation de diesel

    
DoubleQuestion ac404 = (DoubleQuestion) questionService.findByCode(QuestionCode.AC404);
if (ac404 == null) {
    ac404 = new DoubleQuestion( ac402, 0, QuestionCode.AC404, volumeUnits, null, volumeUnits.getMainUnit() );
    JPA.em().persist(ac404);
} else {
    ac404.setDefaultValue(null);
    if (!ac404.getQuestionSet().equals(ac402) && ac402.getQuestions().contains(ac404)) {
        ac402.getQuestions().remove(ac404);
        JPA.em().persist(ac402);
    }
    if (ac404.getQuestionSet().equals(ac402) && !ac402.getQuestions().contains(ac404)) {
        ac402.getQuestions().add(ac404);
        JPA.em().persist(ac402);
    }
    ac404.setUnitCategory(volumeUnits);
    ac404.setOrderIndex(0);
    ac404.setDefaultUnit(volumeUnits.getMainUnit());
    JPA.em().persist(ac404);
}




    // == AC405
    // Consommation de gaz de pétrole liquéfié (GPL)

    
DoubleQuestion ac405 = (DoubleQuestion) questionService.findByCode(QuestionCode.AC405);
if (ac405 == null) {
    ac405 = new DoubleQuestion( ac402, 0, QuestionCode.AC405, volumeUnits, null, volumeUnits.getMainUnit() );
    JPA.em().persist(ac405);
} else {
    ac405.setDefaultValue(null);
    if (!ac405.getQuestionSet().equals(ac402) && ac402.getQuestions().contains(ac405)) {
        ac402.getQuestions().remove(ac405);
        JPA.em().persist(ac402);
    }
    if (ac405.getQuestionSet().equals(ac402) && !ac402.getQuestions().contains(ac405)) {
        ac402.getQuestions().add(ac405);
        JPA.em().persist(ac402);
    }
    ac405.setUnitCategory(volumeUnits);
    ac405.setOrderIndex(0);
    ac405.setDefaultUnit(volumeUnits.getMainUnit());
    JPA.em().persist(ac405);
}




    // == AC408
    // Catégorie de véhicule

    StringQuestion ac408 = (StringQuestion) questionService.findByCode(QuestionCode.AC408);
if (ac408 == null) {
    ac408 = new StringQuestion(ac407, 0, QuestionCode.AC408, null);
    JPA.em().persist(ac408);
} else {
    ac408.setDefaultValue(null);
    if (!ac408.getQuestionSet().equals(ac407) && ac407.getQuestions().contains(ac408)) {
        ac407.getQuestions().remove(ac408);
        JPA.em().persist(ac407);
    }
    if (ac408.getQuestionSet().equals(ac407) && !ac407.getQuestions().contains(ac408)) {
        ac407.getQuestions().add(ac408);
        JPA.em().persist(ac407);
    }
    ac408.setOrderIndex(0);
    JPA.em().persist(ac408);
}


    // == AC409
    // Quel type de carburant utilise-t-il ?

    ValueSelectionQuestion ac409 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AC409);
if (ac409 == null) {
    ac409 = new ValueSelectionQuestion(ac407, 0, QuestionCode.AC409, CodeList.CARBURANT);
    JPA.em().persist(ac409);
} else {
    if (!ac409.getQuestionSet().equals(ac407) && ac407.getQuestions().contains(ac409)) {
        ac407.getQuestions().remove(ac409);
        JPA.em().persist(ac407);
    }
    if (ac409.getQuestionSet().equals(ac407) && !ac407.getQuestions().contains(ac409)) {
        ac407.getQuestions().add(ac409);
        JPA.em().persist(ac407);
    }
    ac409.setOrderIndex(0);
    ac409.setCodeList(CodeList.CARBURANT);
    JPA.em().persist(ac409);
}


    // == AC410
    // Consommation moyenne (L/100km)

    
IntegerQuestion ac410 = (IntegerQuestion) questionService.findByCode(QuestionCode.AC410);
if (ac410 == null) {
    ac410 = new IntegerQuestion(ac407, 0, QuestionCode.AC410, null, null);
    JPA.em().persist(ac410);
} else {
    ac410.setDefaultValue(null);
    if (!ac410.getQuestionSet().equals(ac407) && ac407.getQuestions().contains(ac410)) {
        ac407.getQuestions().remove(ac410);
        JPA.em().persist(ac407);
    }
    if (ac410.getQuestionSet().equals(ac407) && !ac407.getQuestions().contains(ac410)) {
        ac407.getQuestions().add(ac410);
        JPA.em().persist(ac407);
    }
    ac410.setOrderIndex(0);
    ac410.setUnitCategory(null);
    JPA.em().persist(ac410);
}


    // == AC411
    // Quelle est le nombre de kilomètres parcourus par an?

    
IntegerQuestion ac411 = (IntegerQuestion) questionService.findByCode(QuestionCode.AC411);
if (ac411 == null) {
    ac411 = new IntegerQuestion(ac407, 0, QuestionCode.AC411, null, null);
    JPA.em().persist(ac411);
} else {
    ac411.setDefaultValue(null);
    if (!ac411.getQuestionSet().equals(ac407) && ac407.getQuestions().contains(ac411)) {
        ac407.getQuestions().remove(ac411);
        JPA.em().persist(ac407);
    }
    if (ac411.getQuestionSet().equals(ac407) && !ac407.getQuestions().contains(ac411)) {
        ac407.getQuestions().add(ac411);
        JPA.em().persist(ac407);
    }
    ac411.setOrderIndex(0);
    ac411.setUnitCategory(null);
    JPA.em().persist(ac411);
}


    // == AC414
    // Catégorie de véhicule

    StringQuestion ac414 = (StringQuestion) questionService.findByCode(QuestionCode.AC414);
if (ac414 == null) {
    ac414 = new StringQuestion(ac413, 0, QuestionCode.AC414, null);
    JPA.em().persist(ac414);
} else {
    ac414.setDefaultValue(null);
    if (!ac414.getQuestionSet().equals(ac413) && ac413.getQuestions().contains(ac414)) {
        ac413.getQuestions().remove(ac414);
        JPA.em().persist(ac413);
    }
    if (ac414.getQuestionSet().equals(ac413) && !ac413.getQuestions().contains(ac414)) {
        ac413.getQuestions().add(ac414);
        JPA.em().persist(ac413);
    }
    ac414.setOrderIndex(0);
    JPA.em().persist(ac414);
}


    // == AC415
    // Quel type de carburant utilise-t-il ?

    ValueSelectionQuestion ac415 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AC415);
if (ac415 == null) {
    ac415 = new ValueSelectionQuestion(ac413, 0, QuestionCode.AC415, CodeList.CARBURANT);
    JPA.em().persist(ac415);
} else {
    if (!ac415.getQuestionSet().equals(ac413) && ac413.getQuestions().contains(ac415)) {
        ac413.getQuestions().remove(ac415);
        JPA.em().persist(ac413);
    }
    if (ac415.getQuestionSet().equals(ac413) && !ac413.getQuestions().contains(ac415)) {
        ac413.getQuestions().add(ac415);
        JPA.em().persist(ac413);
    }
    ac415.setOrderIndex(0);
    ac415.setCodeList(CodeList.CARBURANT);
    JPA.em().persist(ac415);
}


    // == AC416
    // Prix moyen du litre de ce carburant

    
DoubleQuestion ac416 = (DoubleQuestion) questionService.findByCode(QuestionCode.AC416);
if (ac416 == null) {
    ac416 = new DoubleQuestion( ac413, 0, QuestionCode.AC416, moneyUnits, null, getUnitBySymbol("EUR") );
    JPA.em().persist(ac416);
} else {
    ac416.setDefaultValue(null);
    if (!ac416.getQuestionSet().equals(ac413) && ac413.getQuestions().contains(ac416)) {
        ac413.getQuestions().remove(ac416);
        JPA.em().persist(ac413);
    }
    if (ac416.getQuestionSet().equals(ac413) && !ac413.getQuestions().contains(ac416)) {
        ac413.getQuestions().add(ac416);
        JPA.em().persist(ac413);
    }
    ac416.setUnitCategory(moneyUnits);
    ac416.setOrderIndex(0);
    ac416.setDefaultUnit(getUnitBySymbol("EUR"));
    JPA.em().persist(ac416);
}




    // == AC417
    // Quel est le montant annuel de dépenses en carburant?

    
DoubleQuestion ac417 = (DoubleQuestion) questionService.findByCode(QuestionCode.AC417);
if (ac417 == null) {
    ac417 = new DoubleQuestion( ac413, 0, QuestionCode.AC417, moneyUnits, null, getUnitBySymbol("EUR") );
    JPA.em().persist(ac417);
} else {
    ac417.setDefaultValue(null);
    if (!ac417.getQuestionSet().equals(ac413) && ac413.getQuestions().contains(ac417)) {
        ac413.getQuestions().remove(ac417);
        JPA.em().persist(ac413);
    }
    if (ac417.getQuestionSet().equals(ac413) && !ac413.getQuestions().contains(ac417)) {
        ac413.getQuestions().add(ac417);
        JPA.em().persist(ac413);
    }
    ac417.setUnitCategory(moneyUnits);
    ac417.setOrderIndex(0);
    ac417.setDefaultUnit(getUnitBySymbol("EUR"));
    JPA.em().persist(ac417);
}




    // == AC503
    // Consommation d'essence

    
DoubleQuestion ac503 = (DoubleQuestion) questionService.findByCode(QuestionCode.AC503);
if (ac503 == null) {
    ac503 = new DoubleQuestion( ac502, 0, QuestionCode.AC503, volumeUnits, null, volumeUnits.getMainUnit() );
    JPA.em().persist(ac503);
} else {
    ac503.setDefaultValue(null);
    if (!ac503.getQuestionSet().equals(ac502) && ac502.getQuestions().contains(ac503)) {
        ac502.getQuestions().remove(ac503);
        JPA.em().persist(ac502);
    }
    if (ac503.getQuestionSet().equals(ac502) && !ac502.getQuestions().contains(ac503)) {
        ac502.getQuestions().add(ac503);
        JPA.em().persist(ac502);
    }
    ac503.setUnitCategory(volumeUnits);
    ac503.setOrderIndex(0);
    ac503.setDefaultUnit(volumeUnits.getMainUnit());
    JPA.em().persist(ac503);
}




    // == AC504
    // Consommation de diesel

    
DoubleQuestion ac504 = (DoubleQuestion) questionService.findByCode(QuestionCode.AC504);
if (ac504 == null) {
    ac504 = new DoubleQuestion( ac502, 0, QuestionCode.AC504, volumeUnits, null, volumeUnits.getMainUnit() );
    JPA.em().persist(ac504);
} else {
    ac504.setDefaultValue(null);
    if (!ac504.getQuestionSet().equals(ac502) && ac502.getQuestions().contains(ac504)) {
        ac502.getQuestions().remove(ac504);
        JPA.em().persist(ac502);
    }
    if (ac504.getQuestionSet().equals(ac502) && !ac502.getQuestions().contains(ac504)) {
        ac502.getQuestions().add(ac504);
        JPA.em().persist(ac502);
    }
    ac504.setUnitCategory(volumeUnits);
    ac504.setOrderIndex(0);
    ac504.setDefaultUnit(volumeUnits.getMainUnit());
    JPA.em().persist(ac504);
}




    // == AC505
    // Consommation de gaz de pétrole liquéfié (GPL)

    
DoubleQuestion ac505 = (DoubleQuestion) questionService.findByCode(QuestionCode.AC505);
if (ac505 == null) {
    ac505 = new DoubleQuestion( ac502, 0, QuestionCode.AC505, volumeUnits, null, volumeUnits.getMainUnit() );
    JPA.em().persist(ac505);
} else {
    ac505.setDefaultValue(null);
    if (!ac505.getQuestionSet().equals(ac502) && ac502.getQuestions().contains(ac505)) {
        ac502.getQuestions().remove(ac505);
        JPA.em().persist(ac502);
    }
    if (ac505.getQuestionSet().equals(ac502) && !ac502.getQuestions().contains(ac505)) {
        ac502.getQuestions().add(ac505);
        JPA.em().persist(ac502);
    }
    ac505.setUnitCategory(volumeUnits);
    ac505.setOrderIndex(0);
    ac505.setDefaultUnit(volumeUnits.getMainUnit());
    JPA.em().persist(ac505);
}




    // == AC508
    // Catégorie de véhicule

    StringQuestion ac508 = (StringQuestion) questionService.findByCode(QuestionCode.AC508);
if (ac508 == null) {
    ac508 = new StringQuestion(ac507, 0, QuestionCode.AC508, null);
    JPA.em().persist(ac508);
} else {
    ac508.setDefaultValue(null);
    if (!ac508.getQuestionSet().equals(ac507) && ac507.getQuestions().contains(ac508)) {
        ac507.getQuestions().remove(ac508);
        JPA.em().persist(ac507);
    }
    if (ac508.getQuestionSet().equals(ac507) && !ac507.getQuestions().contains(ac508)) {
        ac507.getQuestions().add(ac508);
        JPA.em().persist(ac507);
    }
    ac508.setOrderIndex(0);
    JPA.em().persist(ac508);
}


    // == AC509
    // Quel type de carburant utilise-t-il ?

    ValueSelectionQuestion ac509 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AC509);
if (ac509 == null) {
    ac509 = new ValueSelectionQuestion(ac507, 0, QuestionCode.AC509, CodeList.CARBURANT);
    JPA.em().persist(ac509);
} else {
    if (!ac509.getQuestionSet().equals(ac507) && ac507.getQuestions().contains(ac509)) {
        ac507.getQuestions().remove(ac509);
        JPA.em().persist(ac507);
    }
    if (ac509.getQuestionSet().equals(ac507) && !ac507.getQuestions().contains(ac509)) {
        ac507.getQuestions().add(ac509);
        JPA.em().persist(ac507);
    }
    ac509.setOrderIndex(0);
    ac509.setCodeList(CodeList.CARBURANT);
    JPA.em().persist(ac509);
}


    // == AC510
    // Consommation moyenne (L/100km)

    
IntegerQuestion ac510 = (IntegerQuestion) questionService.findByCode(QuestionCode.AC510);
if (ac510 == null) {
    ac510 = new IntegerQuestion(ac507, 0, QuestionCode.AC510, null, null);
    JPA.em().persist(ac510);
} else {
    ac510.setDefaultValue(null);
    if (!ac510.getQuestionSet().equals(ac507) && ac507.getQuestions().contains(ac510)) {
        ac507.getQuestions().remove(ac510);
        JPA.em().persist(ac507);
    }
    if (ac510.getQuestionSet().equals(ac507) && !ac507.getQuestions().contains(ac510)) {
        ac507.getQuestions().add(ac510);
        JPA.em().persist(ac507);
    }
    ac510.setOrderIndex(0);
    ac510.setUnitCategory(null);
    JPA.em().persist(ac510);
}


    // == AC511
    // Quelle est le nombre de kilomètres parcourus par an?

    
IntegerQuestion ac511 = (IntegerQuestion) questionService.findByCode(QuestionCode.AC511);
if (ac511 == null) {
    ac511 = new IntegerQuestion(ac507, 0, QuestionCode.AC511, null, null);
    JPA.em().persist(ac511);
} else {
    ac511.setDefaultValue(null);
    if (!ac511.getQuestionSet().equals(ac507) && ac507.getQuestions().contains(ac511)) {
        ac507.getQuestions().remove(ac511);
        JPA.em().persist(ac507);
    }
    if (ac511.getQuestionSet().equals(ac507) && !ac507.getQuestions().contains(ac511)) {
        ac507.getQuestions().add(ac511);
        JPA.em().persist(ac507);
    }
    ac511.setOrderIndex(0);
    ac511.setUnitCategory(null);
    JPA.em().persist(ac511);
}


    // == AC514
    // Catégorie de véhicule

    StringQuestion ac514 = (StringQuestion) questionService.findByCode(QuestionCode.AC514);
if (ac514 == null) {
    ac514 = new StringQuestion(ac513, 0, QuestionCode.AC514, null);
    JPA.em().persist(ac514);
} else {
    ac514.setDefaultValue(null);
    if (!ac514.getQuestionSet().equals(ac513) && ac513.getQuestions().contains(ac514)) {
        ac513.getQuestions().remove(ac514);
        JPA.em().persist(ac513);
    }
    if (ac514.getQuestionSet().equals(ac513) && !ac513.getQuestions().contains(ac514)) {
        ac513.getQuestions().add(ac514);
        JPA.em().persist(ac513);
    }
    ac514.setOrderIndex(0);
    JPA.em().persist(ac514);
}


    // == AC515
    // Quel type de carburant utilise-t-il ?

    ValueSelectionQuestion ac515 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AC515);
if (ac515 == null) {
    ac515 = new ValueSelectionQuestion(ac513, 0, QuestionCode.AC515, CodeList.CARBURANT);
    JPA.em().persist(ac515);
} else {
    if (!ac515.getQuestionSet().equals(ac513) && ac513.getQuestions().contains(ac515)) {
        ac513.getQuestions().remove(ac515);
        JPA.em().persist(ac513);
    }
    if (ac515.getQuestionSet().equals(ac513) && !ac513.getQuestions().contains(ac515)) {
        ac513.getQuestions().add(ac515);
        JPA.em().persist(ac513);
    }
    ac515.setOrderIndex(0);
    ac515.setCodeList(CodeList.CARBURANT);
    JPA.em().persist(ac515);
}


    // == AC516
    // Prix moyen du litre de ce carburant

    
DoubleQuestion ac516 = (DoubleQuestion) questionService.findByCode(QuestionCode.AC516);
if (ac516 == null) {
    ac516 = new DoubleQuestion( ac513, 0, QuestionCode.AC516, moneyUnits, null, getUnitBySymbol("EUR") );
    JPA.em().persist(ac516);
} else {
    ac516.setDefaultValue(null);
    if (!ac516.getQuestionSet().equals(ac513) && ac513.getQuestions().contains(ac516)) {
        ac513.getQuestions().remove(ac516);
        JPA.em().persist(ac513);
    }
    if (ac516.getQuestionSet().equals(ac513) && !ac513.getQuestions().contains(ac516)) {
        ac513.getQuestions().add(ac516);
        JPA.em().persist(ac513);
    }
    ac516.setUnitCategory(moneyUnits);
    ac516.setOrderIndex(0);
    ac516.setDefaultUnit(getUnitBySymbol("EUR"));
    JPA.em().persist(ac516);
}




    // == AC517
    // Quel est le montant annuel de dépenses en carburant?

    
DoubleQuestion ac517 = (DoubleQuestion) questionService.findByCode(QuestionCode.AC517);
if (ac517 == null) {
    ac517 = new DoubleQuestion( ac513, 0, QuestionCode.AC517, moneyUnits, null, getUnitBySymbol("EUR") );
    JPA.em().persist(ac517);
} else {
    ac517.setDefaultValue(null);
    if (!ac517.getQuestionSet().equals(ac513) && ac513.getQuestions().contains(ac517)) {
        ac513.getQuestions().remove(ac517);
        JPA.em().persist(ac513);
    }
    if (ac517.getQuestionSet().equals(ac513) && !ac513.getQuestions().contains(ac517)) {
        ac513.getQuestions().add(ac517);
        JPA.em().persist(ac513);
    }
    ac517.setUnitCategory(moneyUnits);
    ac517.setOrderIndex(0);
    ac517.setDefaultUnit(getUnitBySymbol("EUR"));
    JPA.em().persist(ac517);
}




    // == AC603
    // Consommation d'essence

    
DoubleQuestion ac603 = (DoubleQuestion) questionService.findByCode(QuestionCode.AC603);
if (ac603 == null) {
    ac603 = new DoubleQuestion( ac602, 0, QuestionCode.AC603, volumeUnits, null, volumeUnits.getMainUnit() );
    JPA.em().persist(ac603);
} else {
    ac603.setDefaultValue(null);
    if (!ac603.getQuestionSet().equals(ac602) && ac602.getQuestions().contains(ac603)) {
        ac602.getQuestions().remove(ac603);
        JPA.em().persist(ac602);
    }
    if (ac603.getQuestionSet().equals(ac602) && !ac602.getQuestions().contains(ac603)) {
        ac602.getQuestions().add(ac603);
        JPA.em().persist(ac602);
    }
    ac603.setUnitCategory(volumeUnits);
    ac603.setOrderIndex(0);
    ac603.setDefaultUnit(volumeUnits.getMainUnit());
    JPA.em().persist(ac603);
}




    // == AC604
    // Consommation de diesel

    
DoubleQuestion ac604 = (DoubleQuestion) questionService.findByCode(QuestionCode.AC604);
if (ac604 == null) {
    ac604 = new DoubleQuestion( ac602, 0, QuestionCode.AC604, volumeUnits, null, volumeUnits.getMainUnit() );
    JPA.em().persist(ac604);
} else {
    ac604.setDefaultValue(null);
    if (!ac604.getQuestionSet().equals(ac602) && ac602.getQuestions().contains(ac604)) {
        ac602.getQuestions().remove(ac604);
        JPA.em().persist(ac602);
    }
    if (ac604.getQuestionSet().equals(ac602) && !ac602.getQuestions().contains(ac604)) {
        ac602.getQuestions().add(ac604);
        JPA.em().persist(ac602);
    }
    ac604.setUnitCategory(volumeUnits);
    ac604.setOrderIndex(0);
    ac604.setDefaultUnit(volumeUnits.getMainUnit());
    JPA.em().persist(ac604);
}




    // == AC605
    // Consommation de gaz de pétrole liquéfié (GPL)

    
DoubleQuestion ac605 = (DoubleQuestion) questionService.findByCode(QuestionCode.AC605);
if (ac605 == null) {
    ac605 = new DoubleQuestion( ac602, 0, QuestionCode.AC605, volumeUnits, null, volumeUnits.getMainUnit() );
    JPA.em().persist(ac605);
} else {
    ac605.setDefaultValue(null);
    if (!ac605.getQuestionSet().equals(ac602) && ac602.getQuestions().contains(ac605)) {
        ac602.getQuestions().remove(ac605);
        JPA.em().persist(ac602);
    }
    if (ac605.getQuestionSet().equals(ac602) && !ac602.getQuestions().contains(ac605)) {
        ac602.getQuestions().add(ac605);
        JPA.em().persist(ac602);
    }
    ac605.setUnitCategory(volumeUnits);
    ac605.setOrderIndex(0);
    ac605.setDefaultUnit(volumeUnits.getMainUnit());
    JPA.em().persist(ac605);
}




    // == AC608
    // Catégorie de véhicule

    StringQuestion ac608 = (StringQuestion) questionService.findByCode(QuestionCode.AC608);
if (ac608 == null) {
    ac608 = new StringQuestion(ac607, 0, QuestionCode.AC608, null);
    JPA.em().persist(ac608);
} else {
    ac608.setDefaultValue(null);
    if (!ac608.getQuestionSet().equals(ac607) && ac607.getQuestions().contains(ac608)) {
        ac607.getQuestions().remove(ac608);
        JPA.em().persist(ac607);
    }
    if (ac608.getQuestionSet().equals(ac607) && !ac607.getQuestions().contains(ac608)) {
        ac607.getQuestions().add(ac608);
        JPA.em().persist(ac607);
    }
    ac608.setOrderIndex(0);
    JPA.em().persist(ac608);
}


    // == AC609
    // Quel type de carburant utilise-t-il ?

    ValueSelectionQuestion ac609 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AC609);
if (ac609 == null) {
    ac609 = new ValueSelectionQuestion(ac607, 0, QuestionCode.AC609, CodeList.CARBURANT);
    JPA.em().persist(ac609);
} else {
    if (!ac609.getQuestionSet().equals(ac607) && ac607.getQuestions().contains(ac609)) {
        ac607.getQuestions().remove(ac609);
        JPA.em().persist(ac607);
    }
    if (ac609.getQuestionSet().equals(ac607) && !ac607.getQuestions().contains(ac609)) {
        ac607.getQuestions().add(ac609);
        JPA.em().persist(ac607);
    }
    ac609.setOrderIndex(0);
    ac609.setCodeList(CodeList.CARBURANT);
    JPA.em().persist(ac609);
}


    // == AC610
    // Consommation moyenne (L/100km)

    
IntegerQuestion ac610 = (IntegerQuestion) questionService.findByCode(QuestionCode.AC610);
if (ac610 == null) {
    ac610 = new IntegerQuestion(ac607, 0, QuestionCode.AC610, null, null);
    JPA.em().persist(ac610);
} else {
    ac610.setDefaultValue(null);
    if (!ac610.getQuestionSet().equals(ac607) && ac607.getQuestions().contains(ac610)) {
        ac607.getQuestions().remove(ac610);
        JPA.em().persist(ac607);
    }
    if (ac610.getQuestionSet().equals(ac607) && !ac607.getQuestions().contains(ac610)) {
        ac607.getQuestions().add(ac610);
        JPA.em().persist(ac607);
    }
    ac610.setOrderIndex(0);
    ac610.setUnitCategory(null);
    JPA.em().persist(ac610);
}


    // == AC611
    // Quelle est le nombre de kilomètres parcourus par an?

    
IntegerQuestion ac611 = (IntegerQuestion) questionService.findByCode(QuestionCode.AC611);
if (ac611 == null) {
    ac611 = new IntegerQuestion(ac607, 0, QuestionCode.AC611, null, null);
    JPA.em().persist(ac611);
} else {
    ac611.setDefaultValue(null);
    if (!ac611.getQuestionSet().equals(ac607) && ac607.getQuestions().contains(ac611)) {
        ac607.getQuestions().remove(ac611);
        JPA.em().persist(ac607);
    }
    if (ac611.getQuestionSet().equals(ac607) && !ac607.getQuestions().contains(ac611)) {
        ac607.getQuestions().add(ac611);
        JPA.em().persist(ac607);
    }
    ac611.setOrderIndex(0);
    ac611.setUnitCategory(null);
    JPA.em().persist(ac611);
}


    // == AC614
    // Catégorie de véhicule

    StringQuestion ac614 = (StringQuestion) questionService.findByCode(QuestionCode.AC614);
if (ac614 == null) {
    ac614 = new StringQuestion(ac613, 0, QuestionCode.AC614, null);
    JPA.em().persist(ac614);
} else {
    ac614.setDefaultValue(null);
    if (!ac614.getQuestionSet().equals(ac613) && ac613.getQuestions().contains(ac614)) {
        ac613.getQuestions().remove(ac614);
        JPA.em().persist(ac613);
    }
    if (ac614.getQuestionSet().equals(ac613) && !ac613.getQuestions().contains(ac614)) {
        ac613.getQuestions().add(ac614);
        JPA.em().persist(ac613);
    }
    ac614.setOrderIndex(0);
    JPA.em().persist(ac614);
}


    // == AC615
    // Quel type de carburant utilise-t-il ?

    ValueSelectionQuestion ac615 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AC615);
if (ac615 == null) {
    ac615 = new ValueSelectionQuestion(ac613, 0, QuestionCode.AC615, CodeList.CARBURANT);
    JPA.em().persist(ac615);
} else {
    if (!ac615.getQuestionSet().equals(ac613) && ac613.getQuestions().contains(ac615)) {
        ac613.getQuestions().remove(ac615);
        JPA.em().persist(ac613);
    }
    if (ac615.getQuestionSet().equals(ac613) && !ac613.getQuestions().contains(ac615)) {
        ac613.getQuestions().add(ac615);
        JPA.em().persist(ac613);
    }
    ac615.setOrderIndex(0);
    ac615.setCodeList(CodeList.CARBURANT);
    JPA.em().persist(ac615);
}


    // == AC616
    // Prix moyen du litre de ce carburant

    
DoubleQuestion ac616 = (DoubleQuestion) questionService.findByCode(QuestionCode.AC616);
if (ac616 == null) {
    ac616 = new DoubleQuestion( ac613, 0, QuestionCode.AC616, moneyUnits, null, getUnitBySymbol("EUR") );
    JPA.em().persist(ac616);
} else {
    ac616.setDefaultValue(null);
    if (!ac616.getQuestionSet().equals(ac613) && ac613.getQuestions().contains(ac616)) {
        ac613.getQuestions().remove(ac616);
        JPA.em().persist(ac613);
    }
    if (ac616.getQuestionSet().equals(ac613) && !ac613.getQuestions().contains(ac616)) {
        ac613.getQuestions().add(ac616);
        JPA.em().persist(ac613);
    }
    ac616.setUnitCategory(moneyUnits);
    ac616.setOrderIndex(0);
    ac616.setDefaultUnit(getUnitBySymbol("EUR"));
    JPA.em().persist(ac616);
}




    // == AC617
    // Quel est le montant annuel de dépenses en carburant?

    
DoubleQuestion ac617 = (DoubleQuestion) questionService.findByCode(QuestionCode.AC617);
if (ac617 == null) {
    ac617 = new DoubleQuestion( ac613, 0, QuestionCode.AC617, moneyUnits, null, getUnitBySymbol("EUR") );
    JPA.em().persist(ac617);
} else {
    ac617.setDefaultValue(null);
    if (!ac617.getQuestionSet().equals(ac613) && ac613.getQuestions().contains(ac617)) {
        ac613.getQuestions().remove(ac617);
        JPA.em().persist(ac613);
    }
    if (ac617.getQuestionSet().equals(ac613) && !ac613.getQuestions().contains(ac617)) {
        ac613.getQuestions().add(ac617);
        JPA.em().persist(ac613);
    }
    ac617.setUnitCategory(moneyUnits);
    ac617.setOrderIndex(0);
    ac617.setDefaultUnit(getUnitBySymbol("EUR"));
    JPA.em().persist(ac617);
}




    // == AC94
    // Bus TEC (en km.passagers)

    
IntegerQuestion ac94 = (IntegerQuestion) questionService.findByCode(QuestionCode.AC94);
if (ac94 == null) {
    ac94 = new IntegerQuestion(ac93, 0, QuestionCode.AC94, null, null);
    JPA.em().persist(ac94);
} else {
    ac94.setDefaultValue(null);
    if (!ac94.getQuestionSet().equals(ac93) && ac93.getQuestions().contains(ac94)) {
        ac93.getQuestions().remove(ac94);
        JPA.em().persist(ac93);
    }
    if (ac94.getQuestionSet().equals(ac93) && !ac93.getQuestions().contains(ac94)) {
        ac93.getQuestions().add(ac94);
        JPA.em().persist(ac93);
    }
    ac94.setOrderIndex(0);
    ac94.setUnitCategory(null);
    JPA.em().persist(ac94);
}


    // == AC95
    // Métro (en km.passagers)

    
IntegerQuestion ac95 = (IntegerQuestion) questionService.findByCode(QuestionCode.AC95);
if (ac95 == null) {
    ac95 = new IntegerQuestion(ac93, 0, QuestionCode.AC95, null, null);
    JPA.em().persist(ac95);
} else {
    ac95.setDefaultValue(null);
    if (!ac95.getQuestionSet().equals(ac93) && ac93.getQuestions().contains(ac95)) {
        ac93.getQuestions().remove(ac95);
        JPA.em().persist(ac93);
    }
    if (ac95.getQuestionSet().equals(ac93) && !ac93.getQuestions().contains(ac95)) {
        ac93.getQuestions().add(ac95);
        JPA.em().persist(ac93);
    }
    ac95.setOrderIndex(0);
    ac95.setUnitCategory(null);
    JPA.em().persist(ac95);
}


    // == AC96
    // Train national SNCB (en km.passagers)

    
IntegerQuestion ac96 = (IntegerQuestion) questionService.findByCode(QuestionCode.AC96);
if (ac96 == null) {
    ac96 = new IntegerQuestion(ac93, 0, QuestionCode.AC96, null, null);
    JPA.em().persist(ac96);
} else {
    ac96.setDefaultValue(null);
    if (!ac96.getQuestionSet().equals(ac93) && ac93.getQuestions().contains(ac96)) {
        ac93.getQuestions().remove(ac96);
        JPA.em().persist(ac93);
    }
    if (ac96.getQuestionSet().equals(ac93) && !ac93.getQuestions().contains(ac96)) {
        ac93.getQuestions().add(ac96);
        JPA.em().persist(ac93);
    }
    ac96.setOrderIndex(0);
    ac96.setUnitCategory(null);
    JPA.em().persist(ac96);
}


    // == AC97
    // Tram  (en km.passagers)

    
IntegerQuestion ac97 = (IntegerQuestion) questionService.findByCode(QuestionCode.AC97);
if (ac97 == null) {
    ac97 = new IntegerQuestion(ac93, 0, QuestionCode.AC97, null, null);
    JPA.em().persist(ac97);
} else {
    ac97.setDefaultValue(null);
    if (!ac97.getQuestionSet().equals(ac93) && ac93.getQuestions().contains(ac97)) {
        ac93.getQuestions().remove(ac97);
        JPA.em().persist(ac93);
    }
    if (ac97.getQuestionSet().equals(ac93) && !ac93.getQuestions().contains(ac97)) {
        ac93.getQuestions().add(ac97);
        JPA.em().persist(ac93);
    }
    ac97.setOrderIndex(0);
    ac97.setUnitCategory(null);
    JPA.em().persist(ac97);
}


    // == AC99
    // Bus TEC (en km.passagers)

    
IntegerQuestion ac99 = (IntegerQuestion) questionService.findByCode(QuestionCode.AC99);
if (ac99 == null) {
    ac99 = new IntegerQuestion(ac98, 0, QuestionCode.AC99, null, null);
    JPA.em().persist(ac99);
} else {
    ac99.setDefaultValue(null);
    if (!ac99.getQuestionSet().equals(ac98) && ac98.getQuestions().contains(ac99)) {
        ac98.getQuestions().remove(ac99);
        JPA.em().persist(ac98);
    }
    if (ac99.getQuestionSet().equals(ac98) && !ac98.getQuestions().contains(ac99)) {
        ac98.getQuestions().add(ac99);
        JPA.em().persist(ac98);
    }
    ac99.setOrderIndex(0);
    ac99.setUnitCategory(null);
    JPA.em().persist(ac99);
}


    // == AC100
    // Métro (en km.passagers)

    
IntegerQuestion ac100 = (IntegerQuestion) questionService.findByCode(QuestionCode.AC100);
if (ac100 == null) {
    ac100 = new IntegerQuestion(ac98, 0, QuestionCode.AC100, null, null);
    JPA.em().persist(ac100);
} else {
    ac100.setDefaultValue(null);
    if (!ac100.getQuestionSet().equals(ac98) && ac98.getQuestions().contains(ac100)) {
        ac98.getQuestions().remove(ac100);
        JPA.em().persist(ac98);
    }
    if (ac100.getQuestionSet().equals(ac98) && !ac98.getQuestions().contains(ac100)) {
        ac98.getQuestions().add(ac100);
        JPA.em().persist(ac98);
    }
    ac100.setOrderIndex(0);
    ac100.setUnitCategory(null);
    JPA.em().persist(ac100);
}


    // == AC101
    // Train national SNCB (en km.passagers)

    
IntegerQuestion ac101 = (IntegerQuestion) questionService.findByCode(QuestionCode.AC101);
if (ac101 == null) {
    ac101 = new IntegerQuestion(ac98, 0, QuestionCode.AC101, null, null);
    JPA.em().persist(ac101);
} else {
    ac101.setDefaultValue(null);
    if (!ac101.getQuestionSet().equals(ac98) && ac98.getQuestions().contains(ac101)) {
        ac98.getQuestions().remove(ac101);
        JPA.em().persist(ac98);
    }
    if (ac101.getQuestionSet().equals(ac98) && !ac98.getQuestions().contains(ac101)) {
        ac98.getQuestions().add(ac101);
        JPA.em().persist(ac98);
    }
    ac101.setOrderIndex(0);
    ac101.setUnitCategory(null);
    JPA.em().persist(ac101);
}


    // == AC102
    // Tram (en km.passagers)

    
IntegerQuestion ac102 = (IntegerQuestion) questionService.findByCode(QuestionCode.AC102);
if (ac102 == null) {
    ac102 = new IntegerQuestion(ac98, 0, QuestionCode.AC102, null, null);
    JPA.em().persist(ac102);
} else {
    ac102.setDefaultValue(null);
    if (!ac102.getQuestionSet().equals(ac98) && ac98.getQuestions().contains(ac102)) {
        ac98.getQuestions().remove(ac102);
        JPA.em().persist(ac98);
    }
    if (ac102.getQuestionSet().equals(ac98) && !ac98.getQuestions().contains(ac102)) {
        ac98.getQuestions().add(ac102);
        JPA.em().persist(ac98);
    }
    ac102.setOrderIndex(0);
    ac102.setUnitCategory(null);
    JPA.em().persist(ac102);
}


    // == AC103
    // Taxi (en véhicule.km)

    
IntegerQuestion ac103 = (IntegerQuestion) questionService.findByCode(QuestionCode.AC103);
if (ac103 == null) {
    ac103 = new IntegerQuestion(ac98, 0, QuestionCode.AC103, null, null);
    JPA.em().persist(ac103);
} else {
    ac103.setDefaultValue(null);
    if (!ac103.getQuestionSet().equals(ac98) && ac98.getQuestions().contains(ac103)) {
        ac98.getQuestions().remove(ac103);
        JPA.em().persist(ac98);
    }
    if (ac103.getQuestionSet().equals(ac98) && !ac98.getQuestions().contains(ac103)) {
        ac98.getQuestions().add(ac103);
        JPA.em().persist(ac98);
    }
    ac103.setOrderIndex(0);
    ac103.setUnitCategory(null);
    JPA.em().persist(ac103);
}


    // == AC104
    // Taxi (en montant dépensé)

    
DoubleQuestion ac104 = (DoubleQuestion) questionService.findByCode(QuestionCode.AC104);
if (ac104 == null) {
    ac104 = new DoubleQuestion( ac98, 0, QuestionCode.AC104, moneyUnits, null, getUnitBySymbol("EUR") );
    JPA.em().persist(ac104);
} else {
    ac104.setDefaultValue(null);
    if (!ac104.getQuestionSet().equals(ac98) && ac98.getQuestions().contains(ac104)) {
        ac98.getQuestions().remove(ac104);
        JPA.em().persist(ac98);
    }
    if (ac104.getQuestionSet().equals(ac98) && !ac98.getQuestions().contains(ac104)) {
        ac98.getQuestions().add(ac104);
        JPA.em().persist(ac98);
    }
    ac104.setUnitCategory(moneyUnits);
    ac104.setOrderIndex(0);
    ac104.setDefaultUnit(getUnitBySymbol("EUR"));
    JPA.em().persist(ac104);
}




    // == AC105
    // TGV (en km.passagers)

    
IntegerQuestion ac105 = (IntegerQuestion) questionService.findByCode(QuestionCode.AC105);
if (ac105 == null) {
    ac105 = new IntegerQuestion(ac98, 0, QuestionCode.AC105, null, null);
    JPA.em().persist(ac105);
} else {
    ac105.setDefaultValue(null);
    if (!ac105.getQuestionSet().equals(ac98) && ac98.getQuestions().contains(ac105)) {
        ac98.getQuestions().remove(ac105);
        JPA.em().persist(ac98);
    }
    if (ac105.getQuestionSet().equals(ac98) && !ac98.getQuestions().contains(ac105)) {
        ac98.getQuestions().add(ac105);
        JPA.em().persist(ac98);
    }
    ac105.setOrderIndex(0);
    ac105.setUnitCategory(null);
    JPA.em().persist(ac105);
}


    // == AC108
    // Catégorie de vol

    StringQuestion ac108 = (StringQuestion) questionService.findByCode(QuestionCode.AC108);
if (ac108 == null) {
    ac108 = new StringQuestion(ac107, 0, QuestionCode.AC108, null);
    JPA.em().persist(ac108);
} else {
    ac108.setDefaultValue(null);
    if (!ac108.getQuestionSet().equals(ac107) && ac107.getQuestions().contains(ac108)) {
        ac107.getQuestions().remove(ac108);
        JPA.em().persist(ac107);
    }
    if (ac108.getQuestionSet().equals(ac107) && !ac107.getQuestions().contains(ac108)) {
        ac107.getQuestions().add(ac108);
        JPA.em().persist(ac107);
    }
    ac108.setOrderIndex(0);
    JPA.em().persist(ac108);
}


    // == AC109
    // Type de vol

    ValueSelectionQuestion ac109 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AC109);
if (ac109 == null) {
    ac109 = new ValueSelectionQuestion(ac107, 0, QuestionCode.AC109, CodeList.TYPEVOL);
    JPA.em().persist(ac109);
} else {
    if (!ac109.getQuestionSet().equals(ac107) && ac107.getQuestions().contains(ac109)) {
        ac107.getQuestions().remove(ac109);
        JPA.em().persist(ac107);
    }
    if (ac109.getQuestionSet().equals(ac107) && !ac107.getQuestions().contains(ac109)) {
        ac107.getQuestions().add(ac109);
        JPA.em().persist(ac107);
    }
    ac109.setOrderIndex(0);
    ac109.setCodeList(CodeList.TYPEVOL);
    JPA.em().persist(ac109);
}


    // == AC110
    // Classe du vol

    ValueSelectionQuestion ac110 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AC110);
if (ac110 == null) {
    ac110 = new ValueSelectionQuestion(ac107, 0, QuestionCode.AC110, CodeList.CATEGORIEVOL);
    JPA.em().persist(ac110);
} else {
    if (!ac110.getQuestionSet().equals(ac107) && ac107.getQuestions().contains(ac110)) {
        ac107.getQuestions().remove(ac110);
        JPA.em().persist(ac107);
    }
    if (ac110.getQuestionSet().equals(ac107) && !ac107.getQuestions().contains(ac110)) {
        ac107.getQuestions().add(ac110);
        JPA.em().persist(ac107);
    }
    ac110.setOrderIndex(0);
    ac110.setCodeList(CodeList.CATEGORIEVOL);
    JPA.em().persist(ac110);
}


    // == AC111
    // Nombre de vols/an

    
IntegerQuestion ac111 = (IntegerQuestion) questionService.findByCode(QuestionCode.AC111);
if (ac111 == null) {
    ac111 = new IntegerQuestion(ac107, 0, QuestionCode.AC111, null, null);
    JPA.em().persist(ac111);
} else {
    ac111.setDefaultValue(null);
    if (!ac111.getQuestionSet().equals(ac107) && ac107.getQuestions().contains(ac111)) {
        ac107.getQuestions().remove(ac111);
        JPA.em().persist(ac107);
    }
    if (ac111.getQuestionSet().equals(ac107) && !ac107.getQuestions().contains(ac111)) {
        ac107.getQuestions().add(ac111);
        JPA.em().persist(ac107);
    }
    ac111.setOrderIndex(0);
    ac111.setUnitCategory(null);
    JPA.em().persist(ac111);
}


    // == AC112
    // Distance moyenne A/R (km)

    
DoubleQuestion ac112 = (DoubleQuestion) questionService.findByCode(QuestionCode.AC112);
if (ac112 == null) {
    ac112 = new DoubleQuestion( ac107, 0, QuestionCode.AC112, lengthUnits, null, getUnitBySymbol("km") );
    JPA.em().persist(ac112);
} else {
    ac112.setDefaultValue(null);
    if (!ac112.getQuestionSet().equals(ac107) && ac107.getQuestions().contains(ac112)) {
        ac107.getQuestions().remove(ac112);
        JPA.em().persist(ac107);
    }
    if (ac112.getQuestionSet().equals(ac107) && !ac107.getQuestions().contains(ac112)) {
        ac107.getQuestions().add(ac112);
        JPA.em().persist(ac107);
    }
    ac112.setUnitCategory(lengthUnits);
    ac112.setOrderIndex(0);
    ac112.setDefaultUnit(getUnitBySymbol("km"));
    JPA.em().persist(ac112);
}




    // == AC113
    // Motif de déplacement

    ValueSelectionQuestion ac113 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AC113);
if (ac113 == null) {
    ac113 = new ValueSelectionQuestion(ac107, 0, QuestionCode.AC113, CodeList.MOTIFDEPLACEMENTHORSDDT);
    JPA.em().persist(ac113);
} else {
    if (!ac113.getQuestionSet().equals(ac107) && ac107.getQuestions().contains(ac113)) {
        ac107.getQuestions().remove(ac113);
        JPA.em().persist(ac107);
    }
    if (ac113.getQuestionSet().equals(ac107) && !ac107.getQuestions().contains(ac113)) {
        ac107.getQuestions().add(ac113);
        JPA.em().persist(ac107);
    }
    ac113.setOrderIndex(0);
    ac113.setCodeList(CodeList.MOTIFDEPLACEMENTHORSDDT);
    JPA.em().persist(ac113);
}


    // == AC115
    // Fournir ici les documents éventuels justifiant les données suivantes

    DocumentQuestion ac115 = (DocumentQuestion) questionService.findByCode(QuestionCode.AC115);
if (ac115 == null) {
    ac115 = new DocumentQuestion(ac114, 0, QuestionCode.AC115);
    JPA.em().persist(ac115);
} else {
    if (!ac115.getQuestionSet().equals(ac114) && ac114.getQuestions().contains(ac115)) {
        ac114.getQuestions().remove(ac115);
        JPA.em().persist(ac114);
    }
    if (ac115.getQuestionSet().equals(ac114) && !ac114.getQuestions().contains(ac115)) {
        ac114.getQuestions().add(ac115);
        JPA.em().persist(ac114);
    }
    ac115.setOrderIndex(0);
    JPA.em().persist(ac115);
}


    // == AC117
    // Poste d'achat

    StringQuestion ac117 = (StringQuestion) questionService.findByCode(QuestionCode.AC117);
if (ac117 == null) {
    ac117 = new StringQuestion(ac116, 0, QuestionCode.AC117, null);
    JPA.em().persist(ac117);
} else {
    ac117.setDefaultValue(null);
    if (!ac117.getQuestionSet().equals(ac116) && ac116.getQuestions().contains(ac117)) {
        ac116.getQuestions().remove(ac117);
        JPA.em().persist(ac116);
    }
    if (ac117.getQuestionSet().equals(ac116) && !ac116.getQuestions().contains(ac117)) {
        ac116.getQuestions().add(ac117);
        JPA.em().persist(ac116);
    }
    ac117.setOrderIndex(0);
    JPA.em().persist(ac117);
}


    // == AC118
    // Catégorie

    ValueSelectionQuestion ac118 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AC118);
if (ac118 == null) {
    ac118 = new ValueSelectionQuestion(ac116, 0, QuestionCode.AC118, CodeList.TYPEACHAT);
    JPA.em().persist(ac118);
} else {
    if (!ac118.getQuestionSet().equals(ac116) && ac116.getQuestions().contains(ac118)) {
        ac116.getQuestions().remove(ac118);
        JPA.em().persist(ac116);
    }
    if (ac118.getQuestionSet().equals(ac116) && !ac116.getQuestions().contains(ac118)) {
        ac116.getQuestions().add(ac118);
        JPA.em().persist(ac116);
    }
    ac118.setOrderIndex(0);
    ac118.setCodeList(CodeList.TYPEACHAT);
    JPA.em().persist(ac118);
}


    // == AC119
    // Type

    ValueSelectionQuestion ac119 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AC119);
if (ac119 == null) {
    ac119 = new ValueSelectionQuestion(ac116, 0, QuestionCode.AC119, CodeList.ACHATMETAL);
    JPA.em().persist(ac119);
} else {
    if (!ac119.getQuestionSet().equals(ac116) && ac116.getQuestions().contains(ac119)) {
        ac116.getQuestions().remove(ac119);
        JPA.em().persist(ac116);
    }
    if (ac119.getQuestionSet().equals(ac116) && !ac116.getQuestions().contains(ac119)) {
        ac116.getQuestions().add(ac119);
        JPA.em().persist(ac116);
    }
    ac119.setOrderIndex(0);
    ac119.setCodeList(CodeList.ACHATMETAL);
    JPA.em().persist(ac119);
}


    // == AC120
    // Type

    ValueSelectionQuestion ac120 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AC120);
if (ac120 == null) {
    ac120 = new ValueSelectionQuestion(ac116, 0, QuestionCode.AC120, CodeList.ACHATPLASTIQUE);
    JPA.em().persist(ac120);
} else {
    if (!ac120.getQuestionSet().equals(ac116) && ac116.getQuestions().contains(ac120)) {
        ac116.getQuestions().remove(ac120);
        JPA.em().persist(ac116);
    }
    if (ac120.getQuestionSet().equals(ac116) && !ac116.getQuestions().contains(ac120)) {
        ac116.getQuestions().add(ac120);
        JPA.em().persist(ac116);
    }
    ac120.setOrderIndex(0);
    ac120.setCodeList(CodeList.ACHATPLASTIQUE);
    JPA.em().persist(ac120);
}


    // == AC121
    // Type

    ValueSelectionQuestion ac121 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AC121);
if (ac121 == null) {
    ac121 = new ValueSelectionQuestion(ac116, 0, QuestionCode.AC121, CodeList.ACHATPAPIER);
    JPA.em().persist(ac121);
} else {
    if (!ac121.getQuestionSet().equals(ac116) && ac116.getQuestions().contains(ac121)) {
        ac116.getQuestions().remove(ac121);
        JPA.em().persist(ac116);
    }
    if (ac121.getQuestionSet().equals(ac116) && !ac116.getQuestions().contains(ac121)) {
        ac116.getQuestions().add(ac121);
        JPA.em().persist(ac116);
    }
    ac121.setOrderIndex(0);
    ac121.setCodeList(CodeList.ACHATPAPIER);
    JPA.em().persist(ac121);
}


    // == AC122
    // Type

    ValueSelectionQuestion ac122 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AC122);
if (ac122 == null) {
    ac122 = new ValueSelectionQuestion(ac116, 0, QuestionCode.AC122, CodeList.ACHATVERRE);
    JPA.em().persist(ac122);
} else {
    if (!ac122.getQuestionSet().equals(ac116) && ac116.getQuestions().contains(ac122)) {
        ac116.getQuestions().remove(ac122);
        JPA.em().persist(ac116);
    }
    if (ac122.getQuestionSet().equals(ac116) && !ac116.getQuestions().contains(ac122)) {
        ac116.getQuestions().add(ac122);
        JPA.em().persist(ac116);
    }
    ac122.setOrderIndex(0);
    ac122.setCodeList(CodeList.ACHATVERRE);
    JPA.em().persist(ac122);
}


    // == AC123
    // Type

    ValueSelectionQuestion ac123 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AC123);
if (ac123 == null) {
    ac123 = new ValueSelectionQuestion(ac116, 0, QuestionCode.AC123, CodeList.ACHATCHIMIQUE);
    JPA.em().persist(ac123);
} else {
    if (!ac123.getQuestionSet().equals(ac116) && ac116.getQuestions().contains(ac123)) {
        ac116.getQuestions().remove(ac123);
        JPA.em().persist(ac116);
    }
    if (ac123.getQuestionSet().equals(ac116) && !ac116.getQuestions().contains(ac123)) {
        ac116.getQuestions().add(ac123);
        JPA.em().persist(ac116);
    }
    ac123.setOrderIndex(0);
    ac123.setCodeList(CodeList.ACHATCHIMIQUE);
    JPA.em().persist(ac123);
}


    // == AC124
    // Type

    ValueSelectionQuestion ac124 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AC124);
if (ac124 == null) {
    ac124 = new ValueSelectionQuestion(ac116, 0, QuestionCode.AC124, CodeList.ACHATROUTE);
    JPA.em().persist(ac124);
} else {
    if (!ac124.getQuestionSet().equals(ac116) && ac116.getQuestions().contains(ac124)) {
        ac116.getQuestions().remove(ac124);
        JPA.em().persist(ac116);
    }
    if (ac124.getQuestionSet().equals(ac116) && !ac116.getQuestions().contains(ac124)) {
        ac116.getQuestions().add(ac124);
        JPA.em().persist(ac116);
    }
    ac124.setOrderIndex(0);
    ac124.setCodeList(CodeList.ACHATROUTE);
    JPA.em().persist(ac124);
}


    // == AC125
    // Type

    ValueSelectionQuestion ac125 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AC125);
if (ac125 == null) {
    ac125 = new ValueSelectionQuestion(ac116, 0, QuestionCode.AC125, CodeList.ACHATAGRO);
    JPA.em().persist(ac125);
} else {
    if (!ac125.getQuestionSet().equals(ac116) && ac116.getQuestions().contains(ac125)) {
        ac116.getQuestions().remove(ac125);
        JPA.em().persist(ac116);
    }
    if (ac125.getQuestionSet().equals(ac116) && !ac116.getQuestions().contains(ac125)) {
        ac116.getQuestions().add(ac125);
        JPA.em().persist(ac116);
    }
    ac125.setOrderIndex(0);
    ac125.setCodeList(CodeList.ACHATAGRO);
    JPA.em().persist(ac125);
}


    // == AC126
    // Type

    ValueSelectionQuestion ac126 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AC126);
if (ac126 == null) {
    ac126 = new ValueSelectionQuestion(ac116, 0, QuestionCode.AC126, CodeList.ACHATSERVICE);
    JPA.em().persist(ac126);
} else {
    if (!ac126.getQuestionSet().equals(ac116) && ac116.getQuestions().contains(ac126)) {
        ac116.getQuestions().remove(ac126);
        JPA.em().persist(ac116);
    }
    if (ac126.getQuestionSet().equals(ac116) && !ac116.getQuestions().contains(ac126)) {
        ac116.getQuestions().add(ac126);
        JPA.em().persist(ac116);
    }
    ac126.setOrderIndex(0);
    ac126.setCodeList(CodeList.ACHATSERVICE);
    JPA.em().persist(ac126);
}


    // == AC127
    // Taux de recyclé

    PercentageQuestion ac127 = (PercentageQuestion) questionService.findByCode(QuestionCode.AC127);
if (ac127 == null) {
    ac127 = new PercentageQuestion(ac116, 0, QuestionCode.AC127, 0.9);
    JPA.em().persist(ac127);
} else {
    ac127.setDefaultValue(0.9);
    if (!ac127.getQuestionSet().equals(ac116) && ac116.getQuestions().contains(ac127)) {
        ac116.getQuestions().remove(ac127);
        JPA.em().persist(ac116);
    }
    if (ac127.getQuestionSet().equals(ac116) && !ac116.getQuestions().contains(ac127)) {
        ac116.getQuestions().add(ac127);
        JPA.em().persist(ac116);
    }
    ac127.setOrderIndex(0);
    JPA.em().persist(ac127);
}


    // == AC128
    // Quantité

    
DoubleQuestion ac128 = (DoubleQuestion) questionService.findByCode(QuestionCode.AC128);
if (ac128 == null) {
    ac128 = new DoubleQuestion( ac116, 0, QuestionCode.AC128, massUnits, null, getUnitBySymbol("t") );
    JPA.em().persist(ac128);
} else {
    ac128.setDefaultValue(null);
    if (!ac128.getQuestionSet().equals(ac116) && ac116.getQuestions().contains(ac128)) {
        ac116.getQuestions().remove(ac128);
        JPA.em().persist(ac116);
    }
    if (ac128.getQuestionSet().equals(ac116) && !ac116.getQuestions().contains(ac128)) {
        ac116.getQuestions().add(ac128);
        JPA.em().persist(ac116);
    }
    ac128.setUnitCategory(massUnits);
    ac128.setOrderIndex(0);
    ac128.setDefaultUnit(getUnitBySymbol("t"));
    JPA.em().persist(ac128);
}




    // == AC129
    // Quantité

    
DoubleQuestion ac129 = (DoubleQuestion) questionService.findByCode(QuestionCode.AC129);
if (ac129 == null) {
    ac129 = new DoubleQuestion( ac116, 0, QuestionCode.AC129, moneyUnits, null, getUnitBySymbol("EUR") );
    JPA.em().persist(ac129);
} else {
    ac129.setDefaultValue(null);
    if (!ac129.getQuestionSet().equals(ac116) && ac116.getQuestions().contains(ac129)) {
        ac116.getQuestions().remove(ac129);
        JPA.em().persist(ac116);
    }
    if (ac129.getQuestionSet().equals(ac116) && !ac116.getQuestions().contains(ac129)) {
        ac116.getQuestions().add(ac129);
        JPA.em().persist(ac116);
    }
    ac129.setUnitCategory(moneyUnits);
    ac129.setOrderIndex(0);
    ac129.setDefaultUnit(getUnitBySymbol("EUR"));
    JPA.em().persist(ac129);
}




    // == AC131
    // Fournir ici les documents éventuels justifiant les données suivantes

    DocumentQuestion ac131 = (DocumentQuestion) questionService.findByCode(QuestionCode.AC131);
if (ac131 == null) {
    ac131 = new DocumentQuestion(ac130, 0, QuestionCode.AC131);
    JPA.em().persist(ac131);
} else {
    if (!ac131.getQuestionSet().equals(ac130) && ac130.getQuestions().contains(ac131)) {
        ac130.getQuestions().remove(ac131);
        JPA.em().persist(ac130);
    }
    if (ac131.getQuestionSet().equals(ac130) && !ac130.getQuestions().contains(ac131)) {
        ac130.getQuestions().add(ac131);
        JPA.em().persist(ac130);
    }
    ac131.setOrderIndex(0);
    JPA.em().persist(ac131);
}


    // == AC133
    // Type d'infrastructure

    ValueSelectionQuestion ac133 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AC133);
if (ac133 == null) {
    ac133 = new ValueSelectionQuestion(ac132, 0, QuestionCode.AC133, CodeList.INFRASTRUCTURE);
    JPA.em().persist(ac133);
} else {
    if (!ac133.getQuestionSet().equals(ac132) && ac132.getQuestions().contains(ac133)) {
        ac132.getQuestions().remove(ac133);
        JPA.em().persist(ac132);
    }
    if (ac133.getQuestionSet().equals(ac132) && !ac132.getQuestions().contains(ac133)) {
        ac132.getQuestions().add(ac133);
        JPA.em().persist(ac132);
    }
    ac133.setOrderIndex(0);
    ac133.setCodeList(CodeList.INFRASTRUCTURE);
    JPA.em().persist(ac133);
}


    // == AC134
    // Quantité

    
DoubleQuestion ac134 = (DoubleQuestion) questionService.findByCode(QuestionCode.AC134);
if (ac134 == null) {
    ac134 = new DoubleQuestion( ac132, 0, QuestionCode.AC134, areaUnits, null, getUnitBySymbol("m2") );
    JPA.em().persist(ac134);
} else {
    ac134.setDefaultValue(null);
    if (!ac134.getQuestionSet().equals(ac132) && ac132.getQuestions().contains(ac134)) {
        ac132.getQuestions().remove(ac134);
        JPA.em().persist(ac132);
    }
    if (ac134.getQuestionSet().equals(ac132) && !ac132.getQuestions().contains(ac134)) {
        ac132.getQuestions().add(ac134);
        JPA.em().persist(ac132);
    }
    ac134.setUnitCategory(areaUnits);
    ac134.setOrderIndex(0);
    ac134.setDefaultUnit(getUnitBySymbol("m2"));
    JPA.em().persist(ac134);
}




    // == AC135
    // Quantité

    
DoubleQuestion ac135 = (DoubleQuestion) questionService.findByCode(QuestionCode.AC135);
if (ac135 == null) {
    ac135 = new DoubleQuestion( ac132, 0, QuestionCode.AC135, massUnits, null, getUnitBySymbol("t") );
    JPA.em().persist(ac135);
} else {
    ac135.setDefaultValue(null);
    if (!ac135.getQuestionSet().equals(ac132) && ac132.getQuestions().contains(ac135)) {
        ac132.getQuestions().remove(ac135);
        JPA.em().persist(ac132);
    }
    if (ac135.getQuestionSet().equals(ac132) && !ac132.getQuestions().contains(ac135)) {
        ac132.getQuestions().add(ac135);
        JPA.em().persist(ac132);
    }
    ac135.setUnitCategory(massUnits);
    ac135.setOrderIndex(0);
    ac135.setDefaultUnit(getUnitBySymbol("t"));
    JPA.em().persist(ac135);
}




    // == AC136
    // Nombre d'unités achetées

    
IntegerQuestion ac136 = (IntegerQuestion) questionService.findByCode(QuestionCode.AC136);
if (ac136 == null) {
    ac136 = new IntegerQuestion(ac132, 0, QuestionCode.AC136, null, null);
    JPA.em().persist(ac136);
} else {
    ac136.setDefaultValue(null);
    if (!ac136.getQuestionSet().equals(ac132) && ac132.getQuestions().contains(ac136)) {
        ac132.getQuestions().remove(ac136);
        JPA.em().persist(ac132);
    }
    if (ac136.getQuestionSet().equals(ac132) && !ac132.getQuestions().contains(ac136)) {
        ac132.getQuestions().add(ac136);
        JPA.em().persist(ac132);
    }
    ac136.setOrderIndex(0);
    ac136.setUnitCategory(null);
    JPA.em().persist(ac136);
}


    // == AC138
    // Fournir ici les documents éventuels justifiant les données suivantes

    DocumentQuestion ac138 = (DocumentQuestion) questionService.findByCode(QuestionCode.AC138);
if (ac138 == null) {
    ac138 = new DocumentQuestion(ac137, 0, QuestionCode.AC138);
    JPA.em().persist(ac138);
} else {
    if (!ac138.getQuestionSet().equals(ac137) && ac137.getQuestions().contains(ac138)) {
        ac137.getQuestions().remove(ac138);
        JPA.em().persist(ac137);
    }
    if (ac138.getQuestionSet().equals(ac137) && !ac137.getQuestions().contains(ac138)) {
        ac137.getQuestions().add(ac138);
        JPA.em().persist(ac137);
    }
    ac138.setOrderIndex(0);
    JPA.em().persist(ac138);
}


    // == AC140
    // Nom du projet

    StringQuestion ac140 = (StringQuestion) questionService.findByCode(QuestionCode.AC140);
if (ac140 == null) {
    ac140 = new StringQuestion(ac139, 0, QuestionCode.AC140, null);
    JPA.em().persist(ac140);
} else {
    ac140.setDefaultValue(null);
    if (!ac140.getQuestionSet().equals(ac139) && ac139.getQuestions().contains(ac140)) {
        ac139.getQuestions().remove(ac140);
        JPA.em().persist(ac139);
    }
    if (ac140.getQuestionSet().equals(ac139) && !ac139.getQuestions().contains(ac140)) {
        ac139.getQuestions().add(ac140);
        JPA.em().persist(ac139);
    }
    ac140.setOrderIndex(0);
    JPA.em().persist(ac140);
}


    // == AC141
    // Part d'investissements dans le projet

    PercentageQuestion ac141 = (PercentageQuestion) questionService.findByCode(QuestionCode.AC141);
if (ac141 == null) {
    ac141 = new PercentageQuestion(ac139, 0, QuestionCode.AC141, null);
    JPA.em().persist(ac141);
} else {
    ac141.setDefaultValue(null);
    if (!ac141.getQuestionSet().equals(ac139) && ac139.getQuestions().contains(ac141)) {
        ac139.getQuestions().remove(ac141);
        JPA.em().persist(ac139);
    }
    if (ac141.getQuestionSet().equals(ac139) && !ac139.getQuestions().contains(ac141)) {
        ac139.getQuestions().add(ac141);
        JPA.em().persist(ac139);
    }
    ac141.setOrderIndex(0);
    JPA.em().persist(ac141);
}


    // == AC142
    // Emissions directes totales du projet (tCO²e)

    
IntegerQuestion ac142 = (IntegerQuestion) questionService.findByCode(QuestionCode.AC142);
if (ac142 == null) {
    ac142 = new IntegerQuestion(ac139, 0, QuestionCode.AC142, null, null);
    JPA.em().persist(ac142);
} else {
    ac142.setDefaultValue(null);
    if (!ac142.getQuestionSet().equals(ac139) && ac139.getQuestions().contains(ac142)) {
        ac139.getQuestions().remove(ac142);
        JPA.em().persist(ac139);
    }
    if (ac142.getQuestionSet().equals(ac139) && !ac139.getQuestions().contains(ac142)) {
        ac139.getQuestions().add(ac142);
        JPA.em().persist(ac139);
    }
    ac142.setOrderIndex(0);
    ac142.setUnitCategory(null);
    JPA.em().persist(ac142);
}


    // == AC143
    // Emissions indirectes totales du projet (tCO²e)

    
IntegerQuestion ac143 = (IntegerQuestion) questionService.findByCode(QuestionCode.AC143);
if (ac143 == null) {
    ac143 = new IntegerQuestion(ac139, 0, QuestionCode.AC143, null, null);
    JPA.em().persist(ac143);
} else {
    ac143.setDefaultValue(null);
    if (!ac143.getQuestionSet().equals(ac139) && ac139.getQuestions().contains(ac143)) {
        ac139.getQuestions().remove(ac143);
        JPA.em().persist(ac139);
    }
    if (ac143.getQuestionSet().equals(ac139) && !ac139.getQuestions().contains(ac143)) {
        ac139.getQuestions().add(ac143);
        JPA.em().persist(ac139);
    }
    ac143.setOrderIndex(0);
    ac143.setUnitCategory(null);
    JPA.em().persist(ac143);
}




        Logger.info("===> CREATE AWAC Municipality INITIAL DATA -- END (Took {} milliseconds)", (System.currentTimeMillis() - startTime));
    }

    private UnitCategory getUnitCategoryByCode(UnitCategoryCode unitCategoryCode) {
        return unitCategoryService.findByCode(unitCategoryCode);
    }

    private Unit getUnitBySymbol(String symbol) {
        return unitService.findBySymbol(symbol);
    }

}



