package eu.factorx.awac.generated;

import eu.factorx.awac.models.data.answer.QuestionSetAnswer;
import eu.factorx.awac.models.data.answer.QuestionAnswer;
import eu.factorx.awac.service.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import eu.factorx.awac.models.data.question.Driver;
import eu.factorx.awac.models.data.question.DriverValue;

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

	@Autowired
    private DriverService driverService;

    @Autowired
    private PeriodService periodService;

    private Form form1,form2,form3,form4,form5;
    private QuestionSet ac1,ac2,ac9,ac10,ac24,ac25,ac900,ac903,ac28,ac29,ac32,ac33,ac37,ac39,ac42,ac5000,ac52,ac2000,ac2001,ac56,ac60,ac62,ac400,ac401,ac402,ac406,ac407,ac412,ac413,ac500,ac501,ac502,ac506,ac507,ac512,ac513,ac600,ac601,ac602,ac606,ac607,ac612,ac613,ac92,ac93,ac98,ac106,ac107,ac114,ac116,ac130,ac132,ac137,ac139;
    private Question ac3,ac6,ac7,ac8,ac11,ac12,ac13,ac2002,ac2003,ac14,ac15,ac16,ac17,ac18,ac19,ac20,ac21,ac22,ac23,ac26,ac27,ac901,ac902,ac904,ac905,ac30,ac31,ac34,ac35,ac36,ac38,ac40,ac41,ac43,ac5001,ac5002,ac53,ac54,ac55,ac57,ac58,ac59,ac61,ac403,ac404,ac405,ac408,ac409,ac410,ac411,ac414,ac415,ac416,ac417,ac503,ac504,ac505,ac508,ac509,ac510,ac511,ac514,ac515,ac516,ac517,ac603,ac604,ac605,ac608,ac609,ac610,ac611,ac614,ac615,ac616,ac617,ac94,ac95,ac96,ac97,ac99,ac100,ac101,ac102,ac103,ac104,ac105,ac108,ac109,ac110,ac111,ac112,ac113,ac115,ac117,ac118,ac119,ac120,ac121,ac122,ac123,ac124,ac125,ac126,ac127,ac128,ac129,ac131,ac133,ac134,ac135,ac136,ac138,ac140,ac141,ac142,ac143;

    private UnitCategory energyUnits;
    private UnitCategory massUnits;
    private UnitCategory volumeUnits;
    private UnitCategory lengthUnits;
    private UnitCategory areaUnits;
    private UnitCategory powerUnits;
    private UnitCategory moneyUnits;
    private UnitCategory timeUnits;


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
					Set<QuestionSet> questionSets = form.getQuestionSets();
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

        energyUnits  = getUnitCategoryByCode(UnitCategoryCode.ENERGY);
        massUnits    = getUnitCategoryByCode(UnitCategoryCode.MASS);
        volumeUnits  = getUnitCategoryByCode(UnitCategoryCode.VOLUME);
        lengthUnits  = getUnitCategoryByCode(UnitCategoryCode.LENGTH);
        areaUnits    = getUnitCategoryByCode(UnitCategoryCode.AREA);
        powerUnits   = getUnitCategoryByCode(UnitCategoryCode.POWER);
        moneyUnits   = getUnitCategoryByCode(UnitCategoryCode.CURRENCY);
        timeUnits    = getUnitCategoryByCode(UnitCategoryCode.DURATION);


        // delete old questions
		{
			List<Question> allQuestions = questionService.findAll();
            List<String> codes = Arrays.asList("AC3", "AC6", "AC7", "AC8", "AC11", "AC12", "AC13", "AC2002", "AC2003", "AC14", "AC15", "AC16", "AC17", "AC18", "AC19", "AC20", "AC21", "AC22", "AC23", "AC26", "AC27", "AC901", "AC902", "AC904", "AC905", "AC30", "AC31", "AC34", "AC35", "AC36", "AC38", "AC40", "AC41", "AC43", "AC5001", "AC5002", "AC53", "AC54", "AC55", "AC57", "AC58", "AC59", "AC61", "AC403", "AC404", "AC405", "AC408", "AC409", "AC410", "AC411", "AC414", "AC415", "AC416", "AC417", "AC503", "AC504", "AC505", "AC508", "AC509", "AC510", "AC511", "AC514", "AC515", "AC516", "AC517", "AC603", "AC604", "AC605", "AC608", "AC609", "AC610", "AC611", "AC614", "AC615", "AC616", "AC617", "AC94", "AC95", "AC96", "AC97", "AC99", "AC100", "AC101", "AC102", "AC103", "AC104", "AC105", "AC108", "AC109", "AC110", "AC111", "AC112", "AC113", "AC115", "AC117", "AC118", "AC119", "AC120", "AC121", "AC122", "AC123", "AC124", "AC125", "AC126", "AC127", "AC128", "AC129", "AC131", "AC133", "AC134", "AC135", "AC136", "AC138", "AC140", "AC141", "AC142", "AC143");

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
            List<String> codes = Arrays.asList("AC1", "AC2", "AC9", "AC10", "AC24", "AC25", "AC900", "AC903", "AC28", "AC29", "AC32", "AC33", "AC37", "AC39", "AC42", "AC5000", "AC52", "AC2000", "AC2001", "AC56", "AC60", "AC62", "AC400", "AC401", "AC402", "AC406", "AC407", "AC412", "AC413", "AC500", "AC501", "AC502", "AC506", "AC507", "AC512", "AC513", "AC600", "AC601", "AC602", "AC606", "AC607", "AC612", "AC613", "AC92", "AC93", "AC98", "AC106", "AC107", "AC114", "AC116", "AC130", "AC132", "AC137", "AC139");

			for (QuestionSet qs : new ArrayList<>(allQuestionSets)) {
				if (codes.contains(qs.getCode().getKey()) || !qs.getCode().getKey().matches("AC[0-9]+")) {
					allQuestionSets.remove(qs);
				}
			}
			for (QuestionSet qs : allQuestionSets) {
				deleteQuestionSet(qs, 0);
			}
		}

        createForm1();
        createForm2();
        createForm3();
        createForm4();
        createForm5();

        createQuestionSetAC1();
        createQuestionSetAC2();
        createQuestionSetAC9();
        createQuestionSetAC10();
        createQuestionSetAC24();
        createQuestionSetAC25();
        createQuestionSetAC900();
        createQuestionSetAC903();
        createQuestionSetAC28();
        createQuestionSetAC29();
        createQuestionSetAC32();
        createQuestionSetAC33();
        createQuestionSetAC37();
        createQuestionSetAC39();
        createQuestionSetAC42();
        createQuestionSetAC5000();
        createQuestionSetAC52();
        createQuestionSetAC2000();
        createQuestionSetAC2001();
        createQuestionSetAC56();
        createQuestionSetAC60();
        createQuestionSetAC62();
        createQuestionSetAC400();
        createQuestionSetAC401();
        createQuestionSetAC402();
        createQuestionSetAC406();
        createQuestionSetAC407();
        createQuestionSetAC412();
        createQuestionSetAC413();
        createQuestionSetAC500();
        createQuestionSetAC501();
        createQuestionSetAC502();
        createQuestionSetAC506();
        createQuestionSetAC507();
        createQuestionSetAC512();
        createQuestionSetAC513();
        createQuestionSetAC600();
        createQuestionSetAC601();
        createQuestionSetAC602();
        createQuestionSetAC606();
        createQuestionSetAC607();
        createQuestionSetAC612();
        createQuestionSetAC613();
        createQuestionSetAC92();
        createQuestionSetAC93();
        createQuestionSetAC98();
        createQuestionSetAC106();
        createQuestionSetAC107();
        createQuestionSetAC114();
        createQuestionSetAC116();
        createQuestionSetAC130();
        createQuestionSetAC132();
        createQuestionSetAC137();
        createQuestionSetAC139();

        createQuestionAC3();
        createQuestionAC6();
        createQuestionAC7();
        createQuestionAC8();
        createQuestionAC11();
        createQuestionAC12();
        createQuestionAC13();
        createQuestionAC2002();
        createQuestionAC2003();
        createQuestionAC14();
        createQuestionAC15();
        createQuestionAC16();
        createQuestionAC17();
        createQuestionAC18();
        createQuestionAC19();
        createQuestionAC20();
        createQuestionAC21();
        createQuestionAC22();
        createQuestionAC23();
        createQuestionAC26();
        createQuestionAC27();
        createQuestionAC901();
        createQuestionAC902();
        createQuestionAC904();
        createQuestionAC905();
        createQuestionAC30();
        createQuestionAC31();
        createQuestionAC34();
        createQuestionAC35();
        createQuestionAC36();
        createQuestionAC38();
        createQuestionAC40();
        createQuestionAC41();
        createQuestionAC43();
        createQuestionAC5001();
        createQuestionAC5002();
        createQuestionAC53();
        createQuestionAC54();
        createQuestionAC55();
        createQuestionAC57();
        createQuestionAC58();
        createQuestionAC59();
        createQuestionAC61();
        createQuestionAC403();
        createQuestionAC404();
        createQuestionAC405();
        createQuestionAC408();
        createQuestionAC409();
        createQuestionAC410();
        createQuestionAC411();
        createQuestionAC414();
        createQuestionAC415();
        createQuestionAC416();
        createQuestionAC417();
        createQuestionAC503();
        createQuestionAC504();
        createQuestionAC505();
        createQuestionAC508();
        createQuestionAC509();
        createQuestionAC510();
        createQuestionAC511();
        createQuestionAC514();
        createQuestionAC515();
        createQuestionAC516();
        createQuestionAC517();
        createQuestionAC603();
        createQuestionAC604();
        createQuestionAC605();
        createQuestionAC608();
        createQuestionAC609();
        createQuestionAC610();
        createQuestionAC611();
        createQuestionAC614();
        createQuestionAC615();
        createQuestionAC616();
        createQuestionAC617();
        createQuestionAC94();
        createQuestionAC95();
        createQuestionAC96();
        createQuestionAC97();
        createQuestionAC99();
        createQuestionAC100();
        createQuestionAC101();
        createQuestionAC102();
        createQuestionAC103();
        createQuestionAC104();
        createQuestionAC105();
        createQuestionAC108();
        createQuestionAC109();
        createQuestionAC110();
        createQuestionAC111();
        createQuestionAC112();
        createQuestionAC113();
        createQuestionAC115();
        createQuestionAC117();
        createQuestionAC118();
        createQuestionAC119();
        createQuestionAC120();
        createQuestionAC121();
        createQuestionAC122();
        createQuestionAC123();
        createQuestionAC124();
        createQuestionAC125();
        createQuestionAC126();
        createQuestionAC127();
        createQuestionAC128();
        createQuestionAC129();
        createQuestionAC131();
        createQuestionAC133();
        createQuestionAC134();
        createQuestionAC135();
        createQuestionAC136();
        createQuestionAC138();
        createQuestionAC140();
        createQuestionAC141();
        createQuestionAC142();
        createQuestionAC143();


        Logger.info("===> CREATE AWAC Municipality INITIAL DATA -- END (Took {} milliseconds)", (System.currentTimeMillis() - startTime));
    }

    // =========================================================================
    // FORMS
    // =========================================================================

    private void createForm1() {
        // == TAB_C1
        // DONNEES GENERALES
        form1 = formService.findByIdentifier("TAB_C1");
        if (form1 == null) {
            form1 = new Form("TAB_C1");
            JPA.em().persist(form1);
        }
    }
    private void createForm2() {
        // == TAB_C2
        // ENERGIE, FROID ET DECHETS
        form2 = formService.findByIdentifier("TAB_C2");
        if (form2 == null) {
            form2 = new Form("TAB_C2");
            JPA.em().persist(form2);
        }
    }
    private void createForm3() {
        // == TAB_C3
        // MOBILITE
        form3 = formService.findByIdentifier("TAB_C3");
        if (form3 == null) {
            form3 = new Form("TAB_C3");
            JPA.em().persist(form3);
        }
    }
    private void createForm4() {
        // == TAB_C4
        // ACHATS DE BIENS ET SERVICES
        form4 = formService.findByIdentifier("TAB_C4");
        if (form4 == null) {
            form4 = new Form("TAB_C4");
            JPA.em().persist(form4);
        }
    }
    private void createForm5() {
        // == TAB_C5
        // INFRASTRUCTURES ET INVESTISSEMENTS
        form5 = formService.findByIdentifier("TAB_C5");
        if (form5 == null) {
            form5 = new Form("TAB_C5");
            JPA.em().persist(form5);
        }
    }


    // =========================================================================
    // QUESTION SETS
    // =========================================================================

    private void createQuestionSetAC1() {
        // == AC1
        // AWAC - Communes
        ac1 = questionSetService.findByCode(QuestionCode.AC1);
        if( ac1 == null ) {
            ac1 = new QuestionSet(QuestionCode.AC1, false, null);
            JPA.em().persist(ac1);
        }
        form1.getQuestionSets().add(ac1);
        JPA.em().persist(form1);
    }
    private void createQuestionSetAC2() {
        // == AC2
        // Introduction - Paramètres de votre commune
        ac2 = questionSetService.findByCode(QuestionCode.AC2);
        if( ac2 == null ) {
            ac2 = new QuestionSet(QuestionCode.AC2, false, ac1);
            JPA.em().persist(ac2);
        }
    }
    private void createQuestionSetAC9() {
        // == AC9
        // Energie, froid et déchets
        ac9 = questionSetService.findByCode(QuestionCode.AC9);
        if( ac9 == null ) {
            ac9 = new QuestionSet(QuestionCode.AC9, false, null);
            JPA.em().persist(ac9);
        }
        form2.getQuestionSets().add(ac9);
        JPA.em().persist(form2);
    }
    private void createQuestionSetAC10() {
        // == AC10
        // Lister les différents bâtiments ou groupes de bâtiments gérés par la commune
        ac10 = questionSetService.findByCode(QuestionCode.AC10);
        if( ac10 == null ) {
            ac10 = new QuestionSet(QuestionCode.AC10, true, ac9);
            JPA.em().persist(ac10);
        }
    }
    private void createQuestionSetAC24() {
        // == AC24
        // Consommation de combustibles mesurés soit en énergie, en volume ou en poids
        ac24 = questionSetService.findByCode(QuestionCode.AC24);
        if( ac24 == null ) {
            ac24 = new QuestionSet(QuestionCode.AC24, false, ac10);
            JPA.em().persist(ac24);
        }
    }
    private void createQuestionSetAC25() {
        // == AC25
        // Indiquez ici vos différentes consommations de combustibles exprimés en énergie pour l'ensemble du  bâtiment ou groupe de bâtiments
        ac25 = questionSetService.findByCode(QuestionCode.AC25);
        if( ac25 == null ) {
            ac25 = new QuestionSet(QuestionCode.AC25, true, ac24);
            JPA.em().persist(ac25);
        }
    }
    private void createQuestionSetAC900() {
        // == AC900
        // Indiquez ici vos différentes consommations de combustibles exprimés en volume pour l'ensemble du  bâtiment ou groupe de bâtiments
        ac900 = questionSetService.findByCode(QuestionCode.AC900);
        if( ac900 == null ) {
            ac900 = new QuestionSet(QuestionCode.AC900, true, ac24);
            JPA.em().persist(ac900);
        }
    }
    private void createQuestionSetAC903() {
        // == AC903
        // Indiquez ici vos différentes consommations de combustibles exprimés en poids pour l'ensemble du  bâtiment ou groupe de bâtiments
        ac903 = questionSetService.findByCode(QuestionCode.AC903);
        if( ac903 == null ) {
            ac903 = new QuestionSet(QuestionCode.AC903, true, ac24);
            JPA.em().persist(ac903);
        }
    }
    private void createQuestionSetAC28() {
        // == AC28
        // Consommation d'électricité (achetée sur le réseau ou à des tiers)
        ac28 = questionSetService.findByCode(QuestionCode.AC28);
        if( ac28 == null ) {
            ac28 = new QuestionSet(QuestionCode.AC28, false, ac10);
            JPA.em().persist(ac28);
        }
    }
    private void createQuestionSetAC29() {
        // == AC29
        // Indiquez ici vos différentes consommations éléctriques pour ce bâtiment ou groupe de bâtiments (hors éclairage public)
        ac29 = questionSetService.findByCode(QuestionCode.AC29);
        if( ac29 == null ) {
            ac29 = new QuestionSet(QuestionCode.AC29, false, ac28);
            JPA.em().persist(ac29);
        }
    }
    private void createQuestionSetAC32() {
        // == AC32
        // Consommation de vapeur (achetée à des tiers)
        ac32 = questionSetService.findByCode(QuestionCode.AC32);
        if( ac32 == null ) {
            ac32 = new QuestionSet(QuestionCode.AC32, false, ac10);
            JPA.em().persist(ac32);
        }
    }
    private void createQuestionSetAC33() {
        // == AC33
        // Indiquez ici vos différentes quantités de vapeur achetées pour ce bâtiment ou groupe de bâtiments
        ac33 = questionSetService.findByCode(QuestionCode.AC33);
        if( ac33 == null ) {
            ac33 = new QuestionSet(QuestionCode.AC33, true, ac32);
            JPA.em().persist(ac33);
        }
    }
    private void createQuestionSetAC37() {
        // == AC37
        // Système de refroidissement
        ac37 = questionSetService.findByCode(QuestionCode.AC37);
        if( ac37 == null ) {
            ac37 = new QuestionSet(QuestionCode.AC37, false, ac10);
            JPA.em().persist(ac37);
        }
    }
    private void createQuestionSetAC39() {
        // == AC39
        // Ajoutez ici les différents gaz réfrigérant utilisés dans les systèmes de refroidissement  situés dans ce bâtiment ou groupe de bâtiments
        ac39 = questionSetService.findByCode(QuestionCode.AC39);
        if( ac39 == null ) {
            ac39 = new QuestionSet(QuestionCode.AC39, true, ac37);
            JPA.em().persist(ac39);
        }
    }
    private void createQuestionSetAC42() {
        // == AC42
        // Production de déchets
        ac42 = questionSetService.findByCode(QuestionCode.AC42);
        if( ac42 == null ) {
            ac42 = new QuestionSet(QuestionCode.AC42, false, ac10);
            JPA.em().persist(ac42);
        }
    }
    private void createQuestionSetAC5000() {
        // == AC5000
        // Indiquez ici les différentes quantités de déchets produits dans votre bâtiment ou groupe de bâtiments
        ac5000 = questionSetService.findByCode(QuestionCode.AC5000);
        if( ac5000 == null ) {
            ac5000 = new QuestionSet(QuestionCode.AC5000, true, ac42);
            JPA.em().persist(ac5000);
        }
    }
    private void createQuestionSetAC52() {
        // == AC52
        // Eclairage public et coffrets de voirie
        ac52 = questionSetService.findByCode(QuestionCode.AC52);
        if( ac52 == null ) {
            ac52 = new QuestionSet(QuestionCode.AC52, false, null);
            JPA.em().persist(ac52);
        }
        form2.getQuestionSets().add(ac52);
        JPA.em().persist(form2);
    }
    private void createQuestionSetAC2000() {
        // == AC2000
        // Eclairage public
        ac2000 = questionSetService.findByCode(QuestionCode.AC2000);
        if( ac2000 == null ) {
            ac2000 = new QuestionSet(QuestionCode.AC2000, false, ac52);
            JPA.em().persist(ac2000);
        }
    }
    private void createQuestionSetAC2001() {
        // == AC2001
        // Coffrets de voirie
        ac2001 = questionSetService.findByCode(QuestionCode.AC2001);
        if( ac2001 == null ) {
            ac2001 = new QuestionSet(QuestionCode.AC2001, false, ac52);
            JPA.em().persist(ac2001);
        }
    }
    private void createQuestionSetAC56() {
        // == AC56
        // Créez autant de coffrets de voirie que souhaité
        ac56 = questionSetService.findByCode(QuestionCode.AC56);
        if( ac56 == null ) {
            ac56 = new QuestionSet(QuestionCode.AC56, true, ac52);
            JPA.em().persist(ac56);
        }
    }
    private void createQuestionSetAC60() {
        // == AC60
        // Mobilité
        ac60 = questionSetService.findByCode(QuestionCode.AC60);
        if( ac60 == null ) {
            ac60 = new QuestionSet(QuestionCode.AC60, false, null);
            JPA.em().persist(ac60);
        }
        form3.getQuestionSets().add(ac60);
        JPA.em().persist(form3);
    }
    private void createQuestionSetAC62() {
        // == AC62
        // Transport routier
        ac62 = questionSetService.findByCode(QuestionCode.AC62);
        if( ac62 == null ) {
            ac62 = new QuestionSet(QuestionCode.AC62, false, ac60);
            JPA.em().persist(ac62);
        }
    }
    private void createQuestionSetAC400() {
        // == AC400
        // Véhicules communaux ou détenus par la commune
        ac400 = questionSetService.findByCode(QuestionCode.AC400);
        if( ac400 == null ) {
            ac400 = new QuestionSet(QuestionCode.AC400, false, ac62);
            JPA.em().persist(ac400);
        }
    }
    private void createQuestionSetAC401() {
        // == AC401
        // Méthode au choix
        ac401 = questionSetService.findByCode(QuestionCode.AC401);
        if( ac401 == null ) {
            ac401 = new QuestionSet(QuestionCode.AC401, false, ac2000);
            JPA.em().persist(ac401);
        }
    }
    private void createQuestionSetAC402() {
        // == AC402
        // Méthode basée sur les consommations 
        ac402 = questionSetService.findByCode(QuestionCode.AC402);
        if( ac402 == null ) {
            ac402 = new QuestionSet(QuestionCode.AC402, false, ac2001);
            JPA.em().persist(ac402);
        }
    }
    private void createQuestionSetAC406() {
        // == AC406
        // Méthode basée sur le kilométrage
        ac406 = questionSetService.findByCode(QuestionCode.AC406);
        if( ac406 == null ) {
            ac406 = new QuestionSet(QuestionCode.AC406, false, ac2001);
            JPA.em().persist(ac406);
        }
    }
    private void createQuestionSetAC407() {
        // == AC407
        // Créez autant de catégories de véhicules que souhaité
        ac407 = questionSetService.findByCode(QuestionCode.AC407);
        if( ac407 == null ) {
            ac407 = new QuestionSet(QuestionCode.AC407, true, ac406);
            JPA.em().persist(ac407);
        }
    }
    private void createQuestionSetAC412() {
        // == AC412
        // Méthode basée sur les dépenses
        ac412 = questionSetService.findByCode(QuestionCode.AC412);
        if( ac412 == null ) {
            ac412 = new QuestionSet(QuestionCode.AC412, false, ac2001);
            JPA.em().persist(ac412);
        }
    }
    private void createQuestionSetAC413() {
        // == AC413
        // Créez autant de catégories de véhicules que souhaité
        ac413 = questionSetService.findByCode(QuestionCode.AC413);
        if( ac413 == null ) {
            ac413 = new QuestionSet(QuestionCode.AC413, true, ac412);
            JPA.em().persist(ac413);
        }
    }
    private void createQuestionSetAC500() {
        // == AC500
        // Autres véhicules pour déplacements domicile-travail des employés
        ac500 = questionSetService.findByCode(QuestionCode.AC500);
        if( ac500 == null ) {
            ac500 = new QuestionSet(QuestionCode.AC500, false, ac62);
            JPA.em().persist(ac500);
        }
    }
    private void createQuestionSetAC501() {
        // == AC501
        // Méthode au choix
        ac501 = questionSetService.findByCode(QuestionCode.AC501);
        if( ac501 == null ) {
            ac501 = new QuestionSet(QuestionCode.AC501, false, ac500);
            JPA.em().persist(ac501);
        }
    }
    private void createQuestionSetAC502() {
        // == AC502
        // Méthode basée sur les consommations
        ac502 = questionSetService.findByCode(QuestionCode.AC502);
        if( ac502 == null ) {
            ac502 = new QuestionSet(QuestionCode.AC502, false, ac501);
            JPA.em().persist(ac502);
        }
    }
    private void createQuestionSetAC506() {
        // == AC506
        // Méthode basée sur le kilométrage
        ac506 = questionSetService.findByCode(QuestionCode.AC506);
        if( ac506 == null ) {
            ac506 = new QuestionSet(QuestionCode.AC506, false, ac501);
            JPA.em().persist(ac506);
        }
    }
    private void createQuestionSetAC507() {
        // == AC507
        // Créez autant de catégories de véhicules que souhaité
        ac507 = questionSetService.findByCode(QuestionCode.AC507);
        if( ac507 == null ) {
            ac507 = new QuestionSet(QuestionCode.AC507, true, ac506);
            JPA.em().persist(ac507);
        }
    }
    private void createQuestionSetAC512() {
        // == AC512
        // Méthode basée sur les dépenses
        ac512 = questionSetService.findByCode(QuestionCode.AC512);
        if( ac512 == null ) {
            ac512 = new QuestionSet(QuestionCode.AC512, false, ac501);
            JPA.em().persist(ac512);
        }
    }
    private void createQuestionSetAC513() {
        // == AC513
        // Créez autant de catégories de véhicules que souhaité
        ac513 = questionSetService.findByCode(QuestionCode.AC513);
        if( ac513 == null ) {
            ac513 = new QuestionSet(QuestionCode.AC513, true, ac512);
            JPA.em().persist(ac513);
        }
    }
    private void createQuestionSetAC600() {
        // == AC600
        // Autres véhicules pour déplacements divers (véhicules loués, visiteurs, sous-traitants…)
        ac600 = questionSetService.findByCode(QuestionCode.AC600);
        if( ac600 == null ) {
            ac600 = new QuestionSet(QuestionCode.AC600, false, ac62);
            JPA.em().persist(ac600);
        }
    }
    private void createQuestionSetAC601() {
        // == AC601
        // Méthode au choix
        ac601 = questionSetService.findByCode(QuestionCode.AC601);
        if( ac601 == null ) {
            ac601 = new QuestionSet(QuestionCode.AC601, false, ac600);
            JPA.em().persist(ac601);
        }
    }
    private void createQuestionSetAC602() {
        // == AC602
        // Méthode basée sur les consommations
        ac602 = questionSetService.findByCode(QuestionCode.AC602);
        if( ac602 == null ) {
            ac602 = new QuestionSet(QuestionCode.AC602, false, ac601);
            JPA.em().persist(ac602);
        }
    }
    private void createQuestionSetAC606() {
        // == AC606
        // Méthode basée sur le kilométrage
        ac606 = questionSetService.findByCode(QuestionCode.AC606);
        if( ac606 == null ) {
            ac606 = new QuestionSet(QuestionCode.AC606, false, ac601);
            JPA.em().persist(ac606);
        }
    }
    private void createQuestionSetAC607() {
        // == AC607
        // Créez autant de catégories de véhicules que souhaité
        ac607 = questionSetService.findByCode(QuestionCode.AC607);
        if( ac607 == null ) {
            ac607 = new QuestionSet(QuestionCode.AC607, true, ac606);
            JPA.em().persist(ac607);
        }
    }
    private void createQuestionSetAC612() {
        // == AC612
        // Méthode basée sur les dépenses
        ac612 = questionSetService.findByCode(QuestionCode.AC612);
        if( ac612 == null ) {
            ac612 = new QuestionSet(QuestionCode.AC612, false, ac601);
            JPA.em().persist(ac612);
        }
    }
    private void createQuestionSetAC613() {
        // == AC613
        // Créez autant de catégories de véhicules que souhaité
        ac613 = questionSetService.findByCode(QuestionCode.AC613);
        if( ac613 == null ) {
            ac613 = new QuestionSet(QuestionCode.AC613, true, ac612);
            JPA.em().persist(ac613);
        }
    }
    private void createQuestionSetAC92() {
        // == AC92
        // Déplacement via les transports publics
        ac92 = questionSetService.findByCode(QuestionCode.AC92);
        if( ac92 == null ) {
            ac92 = new QuestionSet(QuestionCode.AC92, false, ac60);
            JPA.em().persist(ac92);
        }
    }
    private void createQuestionSetAC93() {
        // == AC93
        // Déplacements domicile-travail des employés communaux
        ac93 = questionSetService.findByCode(QuestionCode.AC93);
        if( ac93 == null ) {
            ac93 = new QuestionSet(QuestionCode.AC93, false, ac92);
            JPA.em().persist(ac93);
        }
    }
    private void createQuestionSetAC98() {
        // == AC98
        // Déplacements professionnels des employés communaux ainsi que des visiteurs de la commune
        ac98 = questionSetService.findByCode(QuestionCode.AC98);
        if( ac98 == null ) {
            ac98 = new QuestionSet(QuestionCode.AC98, false, ac92);
            JPA.em().persist(ac98);
        }
    }
    private void createQuestionSetAC106() {
        // == AC106
        // Déplacement et voyages en avion
        ac106 = questionSetService.findByCode(QuestionCode.AC106);
        if( ac106 == null ) {
            ac106 = new QuestionSet(QuestionCode.AC106, false, ac60);
            JPA.em().persist(ac106);
        }
    }
    private void createQuestionSetAC107() {
        // == AC107
        // Créez autant de catégories de vol que nécessaire
        ac107 = questionSetService.findByCode(QuestionCode.AC107);
        if( ac107 == null ) {
            ac107 = new QuestionSet(QuestionCode.AC107, true, ac106);
            JPA.em().persist(ac107);
        }
    }
    private void createQuestionSetAC114() {
        // == AC114
        // Achat de biens et services
        ac114 = questionSetService.findByCode(QuestionCode.AC114);
        if( ac114 == null ) {
            ac114 = new QuestionSet(QuestionCode.AC114, false, null);
            JPA.em().persist(ac114);
        }
        form4.getQuestionSets().add(ac114);
        JPA.em().persist(form4);
    }
    private void createQuestionSetAC116() {
        // == AC116
        // Veuillez  indiquez ici l'ensemble des biens et services que votre commune achète.
        ac116 = questionSetService.findByCode(QuestionCode.AC116);
        if( ac116 == null ) {
            ac116 = new QuestionSet(QuestionCode.AC116, true, ac114);
            JPA.em().persist(ac116);
        }
    }
    private void createQuestionSetAC130() {
        // == AC130
        // Biens d'équipement (achetés durant l'année de bilan)
        ac130 = questionSetService.findByCode(QuestionCode.AC130);
        if( ac130 == null ) {
            ac130 = new QuestionSet(QuestionCode.AC130, false, null);
            JPA.em().persist(ac130);
        }
        form5.getQuestionSets().add(ac130);
        JPA.em().persist(form5);
    }
    private void createQuestionSetAC132() {
        // == AC132
        // Veuillez indiquer ici les différents équipements achetés
        ac132 = questionSetService.findByCode(QuestionCode.AC132);
        if( ac132 == null ) {
            ac132 = new QuestionSet(QuestionCode.AC132, true, ac130);
            JPA.em().persist(ac132);
        }
    }
    private void createQuestionSetAC137() {
        // == AC137
        // Activités d'investissement
        ac137 = questionSetService.findByCode(QuestionCode.AC137);
        if( ac137 == null ) {
            ac137 = new QuestionSet(QuestionCode.AC137, false, null);
            JPA.em().persist(ac137);
        }
        form5.getQuestionSets().add(ac137);
        JPA.em().persist(form5);
    }
    private void createQuestionSetAC139() {
        // == AC139
        // Veuillez indiquer ici tous les projets dans lesquels votre commune investit
        ac139 = questionSetService.findByCode(QuestionCode.AC139);
        if( ac139 == null ) {
            ac139 = new QuestionSet(QuestionCode.AC139, true, ac137);
            JPA.em().persist(ac139);
        }
    }


    // =========================================================================
    // QUESTIONS
    // =========================================================================

    private void createQuestionAC3() {
        // == AC3
        // Année de référence du calcul

        ac3 = (IntegerQuestion) questionService.findByCode(QuestionCode.AC3);
if (ac3 == null) {
    ac3 = new IntegerQuestion(ac2, 0, QuestionCode.AC3, null);
    JPA.em().persist(ac3);

    // cleanup the driver
    Driver ac3_driver = driverService.findByName("AC3");
    if (ac3_driver != null) {
        driverService.remove(ac3_driver);
    }

} else {
    if (!ac3.getQuestionSet().equals(ac2) && ac2.getQuestions().contains(ac3)) {
        ac2.getQuestions().remove(ac3);
        JPA.em().persist(ac2);
    }
    if (ac3.getQuestionSet().equals(ac2) && !ac2.getQuestions().contains(ac3)) {
        ac2.getQuestions().add(ac3);
        JPA.em().persist(ac2);
    }
    ac3.setOrderIndex(0);
    ((NumericQuestion)ac3).setUnitCategory(null);

    // cleanup the driver
    Driver ac3_driver = driverService.findByName("AC3");
    if (ac3_driver != null) {
        driverService.remove(ac3_driver);
    }

    ((NumericQuestion)ac3).setDriver(null);

    JPA.em().persist(ac3);
}

    }
    private void createQuestionAC6() {
        // == AC6
        // Nombre d'habitants dans la commune en début d'année de bilan

        ac6 = (IntegerQuestion) questionService.findByCode(QuestionCode.AC6);
if (ac6 == null) {
    ac6 = new IntegerQuestion(ac2, 0, QuestionCode.AC6, null);
    JPA.em().persist(ac6);

    // cleanup the driver
    Driver ac6_driver = driverService.findByName("AC6");
    if (ac6_driver != null) {
        driverService.remove(ac6_driver);
    }

} else {
    if (!ac6.getQuestionSet().equals(ac2) && ac2.getQuestions().contains(ac6)) {
        ac2.getQuestions().remove(ac6);
        JPA.em().persist(ac2);
    }
    if (ac6.getQuestionSet().equals(ac2) && !ac2.getQuestions().contains(ac6)) {
        ac2.getQuestions().add(ac6);
        JPA.em().persist(ac2);
    }
    ac6.setOrderIndex(0);
    ((NumericQuestion)ac6).setUnitCategory(null);

    // cleanup the driver
    Driver ac6_driver = driverService.findByName("AC6");
    if (ac6_driver != null) {
        driverService.remove(ac6_driver);
    }

    ((NumericQuestion)ac6).setDriver(null);

    JPA.em().persist(ac6);
}

    }
    private void createQuestionAC7() {
        // == AC7
        // Nombre total de bâtiments communaux pris en compte dans le bilan

        ac7 = (IntegerQuestion) questionService.findByCode(QuestionCode.AC7);
if (ac7 == null) {
    ac7 = new IntegerQuestion(ac2, 0, QuestionCode.AC7, null);
    JPA.em().persist(ac7);

    // cleanup the driver
    Driver ac7_driver = driverService.findByName("AC7");
    if (ac7_driver != null) {
        driverService.remove(ac7_driver);
    }

} else {
    if (!ac7.getQuestionSet().equals(ac2) && ac2.getQuestions().contains(ac7)) {
        ac2.getQuestions().remove(ac7);
        JPA.em().persist(ac2);
    }
    if (ac7.getQuestionSet().equals(ac2) && !ac2.getQuestions().contains(ac7)) {
        ac2.getQuestions().add(ac7);
        JPA.em().persist(ac2);
    }
    ac7.setOrderIndex(0);
    ((NumericQuestion)ac7).setUnitCategory(null);

    // cleanup the driver
    Driver ac7_driver = driverService.findByName("AC7");
    if (ac7_driver != null) {
        driverService.remove(ac7_driver);
    }

    ((NumericQuestion)ac7).setDriver(null);

    JPA.em().persist(ac7);
}

    }
    private void createQuestionAC8() {
        // == AC8
        // Superficie du territoire

        

ac8 = (DoubleQuestion) questionService.findByCode(QuestionCode.AC8);
if (ac8 == null) {
    ac8 = new DoubleQuestion( ac2, 0, QuestionCode.AC8, areaUnits, getUnitBySymbol("ha") );
    JPA.em().persist(ac8);

    // cleanup the driver
    Driver ac8_driver = driverService.findByName("AC8");
    if (ac8_driver != null) {
        driverService.remove(ac8_driver);
    }


} else {
    if (!ac8.getQuestionSet().equals(ac2) && ac2.getQuestions().contains(ac8)) {
        ac2.getQuestions().remove(ac8);
        JPA.em().persist(ac2);
    }
    if (ac8.getQuestionSet().equals(ac2) && !ac2.getQuestions().contains(ac8)) {
        ac2.getQuestions().add(ac8);
        JPA.em().persist(ac2);
    }
    ((NumericQuestion)ac8).setUnitCategory(areaUnits);
    ac8.setOrderIndex(0);
    ((NumericQuestion)ac8).setDefaultUnit(getUnitBySymbol("ha"));

    // cleanup the driver
    Driver ac8_driver = driverService.findByName("AC8");
    if (ac8_driver != null) {
        driverService.remove(ac8_driver);
    }

    ((NumericQuestion)ac8).setDriver(null);

    JPA.em().persist(ac8);
}



    }
    private void createQuestionAC11() {
        // == AC11
        // Nom du bâtiment ou groupe de bâtiments

        ac11 = (StringQuestion) questionService.findByCode(QuestionCode.AC11);
if (ac11 == null) {
    ac11 = new StringQuestion(ac10, 0, QuestionCode.AC11, null);
    JPA.em().persist(ac11);
} else {
    ((StringQuestion)ac11).setDefaultValue(null);
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

    }
    private void createQuestionAC12() {
        // == AC12
        // Catégorie du bâtiment

        ac12 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AC12);
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
    ((ValueSelectionQuestion)ac12).setCodeList(CodeList.SERVICECOMMUNAL);
    JPA.em().persist(ac12);
}

    }
    private void createQuestionAC13() {
        // == AC13
        // Autre, veuillez préciser

        ac13 = (StringQuestion) questionService.findByCode(QuestionCode.AC13);
if (ac13 == null) {
    ac13 = new StringQuestion(ac10, 0, QuestionCode.AC13, null);
    JPA.em().persist(ac13);
} else {
    ((StringQuestion)ac13).setDefaultValue(null);
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

    }
    private void createQuestionAC2002() {
        // == AC2002
        // Nombre de bâtiments concernés par ce groupe

        ac2002 = (IntegerQuestion) questionService.findByCode(QuestionCode.AC2002);
if (ac2002 == null) {
    ac2002 = new IntegerQuestion(ac10, 0, QuestionCode.AC2002, null);
    JPA.em().persist(ac2002);

    // cleanup the driver
    Driver ac2002_driver = driverService.findByName("AC2002");
    if (ac2002_driver != null) {
        driverService.remove(ac2002_driver);
    }

} else {
    if (!ac2002.getQuestionSet().equals(ac10) && ac10.getQuestions().contains(ac2002)) {
        ac10.getQuestions().remove(ac2002);
        JPA.em().persist(ac10);
    }
    if (ac2002.getQuestionSet().equals(ac10) && !ac10.getQuestions().contains(ac2002)) {
        ac10.getQuestions().add(ac2002);
        JPA.em().persist(ac10);
    }
    ac2002.setOrderIndex(0);
    ((NumericQuestion)ac2002).setUnitCategory(null);

    // cleanup the driver
    Driver ac2002_driver = driverService.findByName("AC2002");
    if (ac2002_driver != null) {
        driverService.remove(ac2002_driver);
    }

    ((NumericQuestion)ac2002).setDriver(null);

    JPA.em().persist(ac2002);
}

    }
    private void createQuestionAC2003() {
        // == AC2003
        // Nombre d'occupants

        ac2003 = (IntegerQuestion) questionService.findByCode(QuestionCode.AC2003);
if (ac2003 == null) {
    ac2003 = new IntegerQuestion(ac10, 0, QuestionCode.AC2003, null);
    JPA.em().persist(ac2003);

    // cleanup the driver
    Driver ac2003_driver = driverService.findByName("AC2003");
    if (ac2003_driver != null) {
        driverService.remove(ac2003_driver);
    }

} else {
    if (!ac2003.getQuestionSet().equals(ac10) && ac10.getQuestions().contains(ac2003)) {
        ac10.getQuestions().remove(ac2003);
        JPA.em().persist(ac10);
    }
    if (ac2003.getQuestionSet().equals(ac10) && !ac10.getQuestions().contains(ac2003)) {
        ac10.getQuestions().add(ac2003);
        JPA.em().persist(ac10);
    }
    ac2003.setOrderIndex(0);
    ((NumericQuestion)ac2003).setUnitCategory(null);

    // cleanup the driver
    Driver ac2003_driver = driverService.findByName("AC2003");
    if (ac2003_driver != null) {
        driverService.remove(ac2003_driver);
    }

    ((NumericQuestion)ac2003).setDriver(null);

    JPA.em().persist(ac2003);
}

    }
    private void createQuestionAC14() {
        // == AC14
        // Adresse (rue et numéro):

        ac14 = (StringQuestion) questionService.findByCode(QuestionCode.AC14);
if (ac14 == null) {
    ac14 = new StringQuestion(ac10, 0, QuestionCode.AC14, null);
    JPA.em().persist(ac14);
} else {
    ((StringQuestion)ac14).setDefaultValue(null);
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

    }
    private void createQuestionAC15() {
        // == AC15
        // Code Postal:

        ac15 = (StringQuestion) questionService.findByCode(QuestionCode.AC15);
if (ac15 == null) {
    ac15 = new StringQuestion(ac10, 0, QuestionCode.AC15, null);
    JPA.em().persist(ac15);
} else {
    ((StringQuestion)ac15).setDefaultValue(null);
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

    }
    private void createQuestionAC16() {
        // == AC16
        // Nom et prénom de la personne de contact:

        ac16 = (StringQuestion) questionService.findByCode(QuestionCode.AC16);
if (ac16 == null) {
    ac16 = new StringQuestion(ac10, 0, QuestionCode.AC16, null);
    JPA.em().persist(ac16);
} else {
    ((StringQuestion)ac16).setDefaultValue(null);
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

    }
    private void createQuestionAC17() {
        // == AC17
        // Email de la personne de contact:

        ac17 = (StringQuestion) questionService.findByCode(QuestionCode.AC17);
if (ac17 == null) {
    ac17 = new StringQuestion(ac10, 0, QuestionCode.AC17, null);
    JPA.em().persist(ac17);
} else {
    ((StringQuestion)ac17).setDefaultValue(null);
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

    }
    private void createQuestionAC18() {
        // == AC18
        // Téléphone de la personne de contact:

        ac18 = (StringQuestion) questionService.findByCode(QuestionCode.AC18);
if (ac18 == null) {
    ac18 = new StringQuestion(ac10, 0, QuestionCode.AC18, null);
    JPA.em().persist(ac18);
} else {
    ((StringQuestion)ac18).setDefaultValue(null);
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

    }
    private void createQuestionAC19() {
        // == AC19
        // Est-ce un bâtiment dont la commune est propriétaire ou locataire?

        ac19 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AC19);
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
    ((ValueSelectionQuestion)ac19).setCodeList(CodeList.GESTIONBATIMENT);
    JPA.em().persist(ac19);
}

    }
    private void createQuestionAC20() {
        // == AC20
        // Quelle est la superficie totale du (groupe de) bâtiment?

        

ac20 = (DoubleQuestion) questionService.findByCode(QuestionCode.AC20);
if (ac20 == null) {
    ac20 = new DoubleQuestion( ac10, 0, QuestionCode.AC20, areaUnits, getUnitBySymbol("m2") );
    JPA.em().persist(ac20);

    // cleanup the driver
    Driver ac20_driver = driverService.findByName("AC20");
    if (ac20_driver != null) {
        driverService.remove(ac20_driver);
    }


} else {
    if (!ac20.getQuestionSet().equals(ac10) && ac10.getQuestions().contains(ac20)) {
        ac10.getQuestions().remove(ac20);
        JPA.em().persist(ac10);
    }
    if (ac20.getQuestionSet().equals(ac10) && !ac10.getQuestions().contains(ac20)) {
        ac10.getQuestions().add(ac20);
        JPA.em().persist(ac10);
    }
    ((NumericQuestion)ac20).setUnitCategory(areaUnits);
    ac20.setOrderIndex(0);
    ((NumericQuestion)ac20).setDefaultUnit(getUnitBySymbol("m2"));

    // cleanup the driver
    Driver ac20_driver = driverService.findByName("AC20");
    if (ac20_driver != null) {
        driverService.remove(ac20_driver);
    }

    ((NumericQuestion)ac20).setDriver(null);

    JPA.em().persist(ac20);
}



    }
    private void createQuestionAC21() {
        // == AC21
        // Quelle est la superficie chauffée sur tout le (groupe de) bâtiment?

        

ac21 = (DoubleQuestion) questionService.findByCode(QuestionCode.AC21);
if (ac21 == null) {
    ac21 = new DoubleQuestion( ac10, 0, QuestionCode.AC21, areaUnits, getUnitBySymbol("m2") );
    JPA.em().persist(ac21);

    // cleanup the driver
    Driver ac21_driver = driverService.findByName("AC21");
    if (ac21_driver != null) {
        driverService.remove(ac21_driver);
    }


} else {
    if (!ac21.getQuestionSet().equals(ac10) && ac10.getQuestions().contains(ac21)) {
        ac10.getQuestions().remove(ac21);
        JPA.em().persist(ac10);
    }
    if (ac21.getQuestionSet().equals(ac10) && !ac10.getQuestions().contains(ac21)) {
        ac10.getQuestions().add(ac21);
        JPA.em().persist(ac10);
    }
    ((NumericQuestion)ac21).setUnitCategory(areaUnits);
    ac21.setOrderIndex(0);
    ((NumericQuestion)ac21).setDefaultUnit(getUnitBySymbol("m2"));

    // cleanup the driver
    Driver ac21_driver = driverService.findByName("AC21");
    if (ac21_driver != null) {
        driverService.remove(ac21_driver);
    }

    ((NumericQuestion)ac21).setDriver(null);

    JPA.em().persist(ac21);
}



    }
    private void createQuestionAC22() {
        // == AC22
        // Quelle en est le % de la partie chauffée occupé par la commune?

        ac22 = (PercentageQuestion) questionService.findByCode(QuestionCode.AC22);
if (ac22 == null) {
    ac22 = new PercentageQuestion(ac10, 0, QuestionCode.AC22);
    JPA.em().persist(ac22);

    // cleanup the driver
    Driver ac22_driver = driverService.findByName("AC22");
    if (ac22_driver != null) {
        driverService.remove(ac22_driver);
    }

} else {
    if (!ac22.getQuestionSet().equals(ac10) && ac10.getQuestions().contains(ac22)) {
        ac10.getQuestions().remove(ac22);
        JPA.em().persist(ac10);
    }
    if (ac22.getQuestionSet().equals(ac10) && !ac10.getQuestions().contains(ac22)) {
        ac10.getQuestions().add(ac22);
        JPA.em().persist(ac10);
    }
    ac22.setOrderIndex(0);

    // cleanup the driver
    Driver ac22_driver = driverService.findByName("AC22");
    if (ac22_driver != null) {
        driverService.remove(ac22_driver);
    }

    ((NumericQuestion)ac22).setDriver(null);

    JPA.em().persist(ac22);
}

    }
    private void createQuestionAC23() {
        // == AC23
        // Fournir ici les documents éventuels justifiant les données de consommation (combustibles, électricité, vapeur)

        ac23 = (DocumentQuestion) questionService.findByCode(QuestionCode.AC23);
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

    }
    private void createQuestionAC26() {
        // == AC26
        // Combustible

        ac26 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AC26);
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
    ((ValueSelectionQuestion)ac26).setCodeList(CodeList.COMBUSTIBLESIMPLECOMMUNE);
    JPA.em().persist(ac26);
}

    }
    private void createQuestionAC27() {
        // == AC27
        // Quantité

        
ac27 = (DoubleQuestion) questionService.findByCode(QuestionCode.AC27);
if (ac27 == null) {
    ac27 = new DoubleQuestion( ac25, 0, QuestionCode.AC27, energyUnits, energyUnits.getMainUnit() );
    JPA.em().persist(ac27);

    // cleanup the driver
    Driver ac27_driver = driverService.findByName("AC27");
    if (ac27_driver != null) {
        driverService.remove(ac27_driver);
    }


} else {
    if (!ac27.getQuestionSet().equals(ac25) && ac25.getQuestions().contains(ac27)) {
        ac25.getQuestions().remove(ac27);
        JPA.em().persist(ac25);
    }
    if (ac27.getQuestionSet().equals(ac25) && !ac25.getQuestions().contains(ac27)) {
        ac25.getQuestions().add(ac27);
        JPA.em().persist(ac25);
    }
    ((NumericQuestion)ac27).setUnitCategory(energyUnits);
    ac27.setOrderIndex(0);
    ((NumericQuestion)ac27).setDefaultUnit(energyUnits.getMainUnit());

    // cleanup the driver
    Driver ac27_driver = driverService.findByName("AC27");
    if (ac27_driver != null) {
        driverService.remove(ac27_driver);
    }

    ((NumericQuestion)ac27).setDriver(null);

    JPA.em().persist(ac27);
}



    }
    private void createQuestionAC901() {
        // == AC901
        // Combustible

        ac901 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AC901);
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
    ((ValueSelectionQuestion)ac901).setCodeList(CodeList.COMBUSTIBLESIMPLECOMMUNEVOLUME);
    JPA.em().persist(ac901);
}

    }
    private void createQuestionAC902() {
        // == AC902
        // Quantité

        
ac902 = (DoubleQuestion) questionService.findByCode(QuestionCode.AC902);
if (ac902 == null) {
    ac902 = new DoubleQuestion( ac900, 0, QuestionCode.AC902, volumeUnits, volumeUnits.getMainUnit() );
    JPA.em().persist(ac902);

    // cleanup the driver
    Driver ac902_driver = driverService.findByName("AC902");
    if (ac902_driver != null) {
        driverService.remove(ac902_driver);
    }

} else {
    if (!ac902.getQuestionSet().equals(ac900) && ac900.getQuestions().contains(ac902)) {
        ac900.getQuestions().remove(ac902);
        JPA.em().persist(ac900);
    }
    if (ac902.getQuestionSet().equals(ac900) && !ac900.getQuestions().contains(ac902)) {
        ac900.getQuestions().add(ac902);
        JPA.em().persist(ac900);
    }
    ((NumericQuestion)ac902).setUnitCategory(volumeUnits);
    ac902.setOrderIndex(0);
    ((NumericQuestion)ac902).setDefaultUnit(volumeUnits.getMainUnit());

    // cleanup the driver
    Driver ac902_driver = driverService.findByName("AC902");
    if (ac902_driver != null) {
        driverService.remove(ac902_driver);
    }

    ((NumericQuestion)ac902).setDriver(null);

    JPA.em().persist(ac902);
}



    }
    private void createQuestionAC904() {
        // == AC904
        // Combustible

        ac904 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AC904);
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
    ((ValueSelectionQuestion)ac904).setCodeList(CodeList.COMBUSTIBLESIMPLECOMMUNEPOIDS);
    JPA.em().persist(ac904);
}

    }
    private void createQuestionAC905() {
        // == AC905
        // Quantité

        
ac905 = (DoubleQuestion) questionService.findByCode(QuestionCode.AC905);
if (ac905 == null) {
    ac905 = new DoubleQuestion( ac903, 0, QuestionCode.AC905, massUnits, massUnits.getMainUnit() );
    JPA.em().persist(ac905);

    // cleanup the driver
    Driver ac905_driver = driverService.findByName("AC905");
    if (ac905_driver != null) {
        driverService.remove(ac905_driver);
    }

} else {
    if (!ac905.getQuestionSet().equals(ac903) && ac903.getQuestions().contains(ac905)) {
        ac903.getQuestions().remove(ac905);
        JPA.em().persist(ac903);
    }
    if (ac905.getQuestionSet().equals(ac903) && !ac903.getQuestions().contains(ac905)) {
        ac903.getQuestions().add(ac905);
        JPA.em().persist(ac903);
    }
    ((NumericQuestion)ac905).setUnitCategory(massUnits);
    ac905.setOrderIndex(0);
    ((NumericQuestion)ac905).setDefaultUnit(massUnits.getMainUnit());


    // cleanup the driver
    Driver ac905_driver = driverService.findByName("AC905");
    if (ac905_driver != null) {
        driverService.remove(ac905_driver);
    }

    ((NumericQuestion)ac905).setDriver(null);

    JPA.em().persist(ac905);
}



    }
    private void createQuestionAC30() {
        // == AC30
        // Electricité grise

        
ac30 = (DoubleQuestion) questionService.findByCode(QuestionCode.AC30);
if (ac30 == null) {
    ac30 = new DoubleQuestion( ac29, 0, QuestionCode.AC30, energyUnits, getUnitBySymbol("kW.h") );
    JPA.em().persist(ac30);

    // cleanup the driver
    Driver ac30_driver = driverService.findByName("AC30");
    if (ac30_driver != null) {
        driverService.remove(ac30_driver);
    }


} else {
    if (!ac30.getQuestionSet().equals(ac29) && ac29.getQuestions().contains(ac30)) {
        ac29.getQuestions().remove(ac30);
        JPA.em().persist(ac29);
    }
    if (ac30.getQuestionSet().equals(ac29) && !ac29.getQuestions().contains(ac30)) {
        ac29.getQuestions().add(ac30);
        JPA.em().persist(ac29);
    }
    ((NumericQuestion)ac30).setUnitCategory(energyUnits);
    ac30.setOrderIndex(0);
    ((NumericQuestion)ac30).setDefaultUnit(getUnitBySymbol("kW.h"));

    // cleanup the driver
    Driver ac30_driver = driverService.findByName("AC30");
    if (ac30_driver != null) {
        driverService.remove(ac30_driver);
    }

    ((NumericQuestion)ac30).setDriver(null);

    JPA.em().persist(ac30);
}



    }
    private void createQuestionAC31() {
        // == AC31
        // Electricité verte

        
ac31 = (DoubleQuestion) questionService.findByCode(QuestionCode.AC31);
if (ac31 == null) {
    ac31 = new DoubleQuestion( ac29, 0, QuestionCode.AC31, energyUnits, getUnitBySymbol("kW.h") );
    JPA.em().persist(ac31);

    // cleanup the driver
    Driver ac31_driver = driverService.findByName("AC31");
    if (ac31_driver != null) {
        driverService.remove(ac31_driver);
    }


} else {
    if (!ac31.getQuestionSet().equals(ac29) && ac29.getQuestions().contains(ac31)) {
        ac29.getQuestions().remove(ac31);
        JPA.em().persist(ac29);
    }
    if (ac31.getQuestionSet().equals(ac29) && !ac29.getQuestions().contains(ac31)) {
        ac29.getQuestions().add(ac31);
        JPA.em().persist(ac29);
    }
    ((NumericQuestion)ac31).setUnitCategory(energyUnits);
    ac31.setOrderIndex(0);
    ((NumericQuestion)ac31).setDefaultUnit(getUnitBySymbol("kW.h"));

    // cleanup the driver
    Driver ac31_driver = driverService.findByName("AC31");
    if (ac31_driver != null) {
        driverService.remove(ac31_driver);
    }

    ((NumericQuestion)ac31).setDriver(null);

    JPA.em().persist(ac31);
}



    }
    private void createQuestionAC34() {
        // == AC34
        // Quel est le type d'énergie primaire utilisé pour produire la vapeur? 

        ac34 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AC34);
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
    ((ValueSelectionQuestion)ac34).setCodeList(CodeList.ENERGIEVAPEUR);
    JPA.em().persist(ac34);
}

    }
    private void createQuestionAC35() {
        // == AC35
        // Efficacité de la chaudière

        ac35 = (PercentageQuestion) questionService.findByCode(QuestionCode.AC35);
if (ac35 == null) {
    ac35 = new PercentageQuestion(ac33, 0, QuestionCode.AC35);
    JPA.em().persist(ac35);

    // cleanup the driver
    Driver ac35_driver = driverService.findByName("AC35");
    if (ac35_driver != null) {
        driverService.remove(ac35_driver);
    }

} else {
    if (!ac35.getQuestionSet().equals(ac33) && ac33.getQuestions().contains(ac35)) {
        ac33.getQuestions().remove(ac35);
        JPA.em().persist(ac33);
    }
    if (ac35.getQuestionSet().equals(ac33) && !ac33.getQuestions().contains(ac35)) {
        ac33.getQuestions().add(ac35);
        JPA.em().persist(ac33);
    }
    ac35.setOrderIndex(0);

    // cleanup the driver
    Driver ac35_driver = driverService.findByName("AC35");
    if (ac35_driver != null) {
        driverService.remove(ac35_driver);
    }

    ((NumericQuestion)ac35).setDriver(null);

    JPA.em().persist(ac35);
}

    }
    private void createQuestionAC36() {
        // == AC36
        // Consommation annuelle de vapeur

        
ac36 = (DoubleQuestion) questionService.findByCode(QuestionCode.AC36);
if (ac36 == null) {
    ac36 = new DoubleQuestion( ac33, 0, QuestionCode.AC36, energyUnits, energyUnits.getMainUnit() );
    JPA.em().persist(ac36);

    // cleanup the driver
    Driver ac36_driver = driverService.findByName("AC36");
    if (ac36_driver != null) {
        driverService.remove(ac36_driver);
    }


} else {
    if (!ac36.getQuestionSet().equals(ac33) && ac33.getQuestions().contains(ac36)) {
        ac33.getQuestions().remove(ac36);
        JPA.em().persist(ac33);
    }
    if (ac36.getQuestionSet().equals(ac33) && !ac33.getQuestions().contains(ac36)) {
        ac33.getQuestions().add(ac36);
        JPA.em().persist(ac33);
    }
    ((NumericQuestion)ac36).setUnitCategory(energyUnits);
    ac36.setOrderIndex(0);
    ((NumericQuestion)ac36).setDefaultUnit(energyUnits.getMainUnit());

    // cleanup the driver
    Driver ac36_driver = driverService.findByName("AC36");
    if (ac36_driver != null) {
        driverService.remove(ac36_driver);
    }

    ((NumericQuestion)ac36).setDriver(null);

    JPA.em().persist(ac36);
}



    }
    private void createQuestionAC38() {
        // == AC38
        // Fournir ici les documents éventuels justifiant les données suivantes

        ac38 = (DocumentQuestion) questionService.findByCode(QuestionCode.AC38);
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

    }
    private void createQuestionAC40() {
        // == AC40
        // Type de gaz réfrigérant

        ac40 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AC40);
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
    ((ValueSelectionQuestion)ac40).setCodeList(CodeList.FRIGORIGENE);
    JPA.em().persist(ac40);
}

    }
    private void createQuestionAC41() {
        // == AC41
        // Quantité de recharge nécessaire (pour l'année de bilan)

        
ac41 = (DoubleQuestion) questionService.findByCode(QuestionCode.AC41);
if (ac41 == null) {
    ac41 = new DoubleQuestion( ac39, 0, QuestionCode.AC41, massUnits, massUnits.getMainUnit() );
    JPA.em().persist(ac41);

    // cleanup the driver
    Driver ac41_driver = driverService.findByName("AC41");
    if (ac41_driver != null) {
        driverService.remove(ac41_driver);
    }

} else {
    if (!ac41.getQuestionSet().equals(ac39) && ac39.getQuestions().contains(ac41)) {
        ac39.getQuestions().remove(ac41);
        JPA.em().persist(ac39);
    }
    if (ac41.getQuestionSet().equals(ac39) && !ac39.getQuestions().contains(ac41)) {
        ac39.getQuestions().add(ac41);
        JPA.em().persist(ac39);
    }
    ((NumericQuestion)ac41).setUnitCategory(massUnits);
    ac41.setOrderIndex(0);
    ((NumericQuestion)ac41).setDefaultUnit(massUnits.getMainUnit());


    // cleanup the driver
    Driver ac41_driver = driverService.findByName("AC41");
    if (ac41_driver != null) {
        driverService.remove(ac41_driver);
    }

    ((NumericQuestion)ac41).setDriver(null);

    JPA.em().persist(ac41);
}



    }
    private void createQuestionAC43() {
        // == AC43
        // Fournir ici les documents éventuels justifiant les données suivantes

        ac43 = (DocumentQuestion) questionService.findByCode(QuestionCode.AC43);
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

    }
    private void createQuestionAC5001() {
        // == AC5001
        // Type de déchet

        ac5001 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AC5001);
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
    ((ValueSelectionQuestion)ac5001).setCodeList(CodeList.GESTIONDECHETS);
    JPA.em().persist(ac5001);
}

    }
    private void createQuestionAC5002() {
        // == AC5002
        // Quantité de déchets 

        
ac5002 = (DoubleQuestion) questionService.findByCode(QuestionCode.AC5002);
if (ac5002 == null) {
    ac5002 = new DoubleQuestion( ac5000, 0, QuestionCode.AC5002, massUnits, getUnitBySymbol("t") );
    JPA.em().persist(ac5002);

    // cleanup the driver
    Driver ac5002_driver = driverService.findByName("AC5002");
    if (ac5002_driver != null) {
        driverService.remove(ac5002_driver);
    }

} else {
    if (!ac5002.getQuestionSet().equals(ac5000) && ac5000.getQuestions().contains(ac5002)) {
        ac5000.getQuestions().remove(ac5002);
        JPA.em().persist(ac5000);
    }
    if (ac5002.getQuestionSet().equals(ac5000) && !ac5000.getQuestions().contains(ac5002)) {
        ac5000.getQuestions().add(ac5002);
        JPA.em().persist(ac5000);
    }
    ((NumericQuestion)ac5002).setUnitCategory(massUnits);
    ac5002.setOrderIndex(0);
    ((NumericQuestion)ac5002).setDefaultUnit(getUnitBySymbol("t"));


    // cleanup the driver
    Driver ac5002_driver = driverService.findByName("AC5002");
    if (ac5002_driver != null) {
        driverService.remove(ac5002_driver);
    }

    ((NumericQuestion)ac5002).setDriver(null);

    JPA.em().persist(ac5002);
}



    }
    private void createQuestionAC53() {
        // == AC53
        // Fournir ici les documents éventuels justifiant les données suivantes

        ac53 = (DocumentQuestion) questionService.findByCode(QuestionCode.AC53);
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

    }
    private void createQuestionAC54() {
        // == AC54
        // Consommation d'électricité verte

        
ac54 = (DoubleQuestion) questionService.findByCode(QuestionCode.AC54);
if (ac54 == null) {
    ac54 = new DoubleQuestion( ac52, 0, QuestionCode.AC54, energyUnits, getUnitBySymbol("kW.h") );
    JPA.em().persist(ac54);

    // cleanup the driver
    Driver ac54_driver = driverService.findByName("AC54");
    if (ac54_driver != null) {
        driverService.remove(ac54_driver);
    }


} else {
    if (!ac54.getQuestionSet().equals(ac52) && ac52.getQuestions().contains(ac54)) {
        ac52.getQuestions().remove(ac54);
        JPA.em().persist(ac52);
    }
    if (ac54.getQuestionSet().equals(ac52) && !ac52.getQuestions().contains(ac54)) {
        ac52.getQuestions().add(ac54);
        JPA.em().persist(ac52);
    }
    ((NumericQuestion)ac54).setUnitCategory(energyUnits);
    ac54.setOrderIndex(0);
    ((NumericQuestion)ac54).setDefaultUnit(getUnitBySymbol("kW.h"));

    // cleanup the driver
    Driver ac54_driver = driverService.findByName("AC54");
    if (ac54_driver != null) {
        driverService.remove(ac54_driver);
    }

    ((NumericQuestion)ac54).setDriver(null);

    JPA.em().persist(ac54);
}



    }
    private void createQuestionAC55() {
        // == AC55
        // Consommation d'électricité grise

        
ac55 = (DoubleQuestion) questionService.findByCode(QuestionCode.AC55);
if (ac55 == null) {
    ac55 = new DoubleQuestion( ac52, 0, QuestionCode.AC55, energyUnits, getUnitBySymbol("kW.h") );
    JPA.em().persist(ac55);

    // cleanup the driver
    Driver ac55_driver = driverService.findByName("AC55");
    if (ac55_driver != null) {
        driverService.remove(ac55_driver);
    }


} else {
    if (!ac55.getQuestionSet().equals(ac52) && ac52.getQuestions().contains(ac55)) {
        ac52.getQuestions().remove(ac55);
        JPA.em().persist(ac52);
    }
    if (ac55.getQuestionSet().equals(ac52) && !ac52.getQuestions().contains(ac55)) {
        ac52.getQuestions().add(ac55);
        JPA.em().persist(ac52);
    }
    ((NumericQuestion)ac55).setUnitCategory(energyUnits);
    ac55.setOrderIndex(0);
    ((NumericQuestion)ac55).setDefaultUnit(getUnitBySymbol("kW.h"));

    // cleanup the driver
    Driver ac55_driver = driverService.findByName("AC55");
    if (ac55_driver != null) {
        driverService.remove(ac55_driver);
    }

    ((NumericQuestion)ac55).setDriver(null);

    JPA.em().persist(ac55);
}



    }
    private void createQuestionAC57() {
        // == AC57
        // Désignation du coffret

        ac57 = (StringQuestion) questionService.findByCode(QuestionCode.AC57);
if (ac57 == null) {
    ac57 = new StringQuestion(ac56, 0, QuestionCode.AC57, null);
    JPA.em().persist(ac57);
} else {
    ((StringQuestion)ac57).setDefaultValue(null);
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

    }
    private void createQuestionAC58() {
        // == AC58
        // Consommation d'électricité verte du coffret

        
ac58 = (DoubleQuestion) questionService.findByCode(QuestionCode.AC58);
if (ac58 == null) {
    ac58 = new DoubleQuestion( ac56, 0, QuestionCode.AC58, energyUnits, getUnitBySymbol("kW.h") );
    JPA.em().persist(ac58);

    // cleanup the driver
    Driver ac58_driver = driverService.findByName("AC58");
    if (ac58_driver != null) {
        driverService.remove(ac58_driver);
    }


} else {
    if (!ac58.getQuestionSet().equals(ac56) && ac56.getQuestions().contains(ac58)) {
        ac56.getQuestions().remove(ac58);
        JPA.em().persist(ac56);
    }
    if (ac58.getQuestionSet().equals(ac56) && !ac56.getQuestions().contains(ac58)) {
        ac56.getQuestions().add(ac58);
        JPA.em().persist(ac56);
    }
    ((NumericQuestion)ac58).setUnitCategory(energyUnits);
    ac58.setOrderIndex(0);
    ((NumericQuestion)ac58).setDefaultUnit(getUnitBySymbol("kW.h"));

    // cleanup the driver
    Driver ac58_driver = driverService.findByName("AC58");
    if (ac58_driver != null) {
        driverService.remove(ac58_driver);
    }

    ((NumericQuestion)ac58).setDriver(null);

    JPA.em().persist(ac58);
}



    }
    private void createQuestionAC59() {
        // == AC59
        // Consommation d'électricité grise du coffret

        
ac59 = (DoubleQuestion) questionService.findByCode(QuestionCode.AC59);
if (ac59 == null) {
    ac59 = new DoubleQuestion( ac56, 0, QuestionCode.AC59, energyUnits, getUnitBySymbol("kW.h") );
    JPA.em().persist(ac59);

    // cleanup the driver
    Driver ac59_driver = driverService.findByName("AC59");
    if (ac59_driver != null) {
        driverService.remove(ac59_driver);
    }


} else {
    if (!ac59.getQuestionSet().equals(ac56) && ac56.getQuestions().contains(ac59)) {
        ac56.getQuestions().remove(ac59);
        JPA.em().persist(ac56);
    }
    if (ac59.getQuestionSet().equals(ac56) && !ac56.getQuestions().contains(ac59)) {
        ac56.getQuestions().add(ac59);
        JPA.em().persist(ac56);
    }
    ((NumericQuestion)ac59).setUnitCategory(energyUnits);
    ac59.setOrderIndex(0);
    ((NumericQuestion)ac59).setDefaultUnit(getUnitBySymbol("kW.h"));

    // cleanup the driver
    Driver ac59_driver = driverService.findByName("AC59");
    if (ac59_driver != null) {
        driverService.remove(ac59_driver);
    }

    ((NumericQuestion)ac59).setDriver(null);

    JPA.em().persist(ac59);
}



    }
    private void createQuestionAC61() {
        // == AC61
        // Fournir ici les documents éventuels justifiant les données suivantes

        ac61 = (DocumentQuestion) questionService.findByCode(QuestionCode.AC61);
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

    }
    private void createQuestionAC403() {
        // == AC403
        // Consommation d'essence

        
ac403 = (DoubleQuestion) questionService.findByCode(QuestionCode.AC403);
if (ac403 == null) {
    ac403 = new DoubleQuestion( ac402, 0, QuestionCode.AC403, volumeUnits, volumeUnits.getMainUnit() );
    JPA.em().persist(ac403);

    // cleanup the driver
    Driver ac403_driver = driverService.findByName("AC403");
    if (ac403_driver != null) {
        driverService.remove(ac403_driver);
    }

} else {
    if (!ac403.getQuestionSet().equals(ac402) && ac402.getQuestions().contains(ac403)) {
        ac402.getQuestions().remove(ac403);
        JPA.em().persist(ac402);
    }
    if (ac403.getQuestionSet().equals(ac402) && !ac402.getQuestions().contains(ac403)) {
        ac402.getQuestions().add(ac403);
        JPA.em().persist(ac402);
    }
    ((NumericQuestion)ac403).setUnitCategory(volumeUnits);
    ac403.setOrderIndex(0);
    ((NumericQuestion)ac403).setDefaultUnit(volumeUnits.getMainUnit());

    // cleanup the driver
    Driver ac403_driver = driverService.findByName("AC403");
    if (ac403_driver != null) {
        driverService.remove(ac403_driver);
    }

    ((NumericQuestion)ac403).setDriver(null);

    JPA.em().persist(ac403);
}



    }
    private void createQuestionAC404() {
        // == AC404
        // Consommation de diesel

        
ac404 = (DoubleQuestion) questionService.findByCode(QuestionCode.AC404);
if (ac404 == null) {
    ac404 = new DoubleQuestion( ac402, 0, QuestionCode.AC404, volumeUnits, volumeUnits.getMainUnit() );
    JPA.em().persist(ac404);

    // cleanup the driver
    Driver ac404_driver = driverService.findByName("AC404");
    if (ac404_driver != null) {
        driverService.remove(ac404_driver);
    }

} else {
    if (!ac404.getQuestionSet().equals(ac402) && ac402.getQuestions().contains(ac404)) {
        ac402.getQuestions().remove(ac404);
        JPA.em().persist(ac402);
    }
    if (ac404.getQuestionSet().equals(ac402) && !ac402.getQuestions().contains(ac404)) {
        ac402.getQuestions().add(ac404);
        JPA.em().persist(ac402);
    }
    ((NumericQuestion)ac404).setUnitCategory(volumeUnits);
    ac404.setOrderIndex(0);
    ((NumericQuestion)ac404).setDefaultUnit(volumeUnits.getMainUnit());

    // cleanup the driver
    Driver ac404_driver = driverService.findByName("AC404");
    if (ac404_driver != null) {
        driverService.remove(ac404_driver);
    }

    ((NumericQuestion)ac404).setDriver(null);

    JPA.em().persist(ac404);
}



    }
    private void createQuestionAC405() {
        // == AC405
        // Consommation de gaz de pétrole liquéfié (GPL)

        
ac405 = (DoubleQuestion) questionService.findByCode(QuestionCode.AC405);
if (ac405 == null) {
    ac405 = new DoubleQuestion( ac402, 0, QuestionCode.AC405, volumeUnits, volumeUnits.getMainUnit() );
    JPA.em().persist(ac405);

    // cleanup the driver
    Driver ac405_driver = driverService.findByName("AC405");
    if (ac405_driver != null) {
        driverService.remove(ac405_driver);
    }

} else {
    if (!ac405.getQuestionSet().equals(ac402) && ac402.getQuestions().contains(ac405)) {
        ac402.getQuestions().remove(ac405);
        JPA.em().persist(ac402);
    }
    if (ac405.getQuestionSet().equals(ac402) && !ac402.getQuestions().contains(ac405)) {
        ac402.getQuestions().add(ac405);
        JPA.em().persist(ac402);
    }
    ((NumericQuestion)ac405).setUnitCategory(volumeUnits);
    ac405.setOrderIndex(0);
    ((NumericQuestion)ac405).setDefaultUnit(volumeUnits.getMainUnit());

    // cleanup the driver
    Driver ac405_driver = driverService.findByName("AC405");
    if (ac405_driver != null) {
        driverService.remove(ac405_driver);
    }

    ((NumericQuestion)ac405).setDriver(null);

    JPA.em().persist(ac405);
}



    }
    private void createQuestionAC408() {
        // == AC408
        // Catégorie de véhicule

        ac408 = (StringQuestion) questionService.findByCode(QuestionCode.AC408);
if (ac408 == null) {
    ac408 = new StringQuestion(ac407, 0, QuestionCode.AC408, null);
    JPA.em().persist(ac408);
} else {
    ((StringQuestion)ac408).setDefaultValue(null);
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

    }
    private void createQuestionAC409() {
        // == AC409
        // Quel type de carburant utilise-t-il ?

        ac409 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AC409);
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
    ((ValueSelectionQuestion)ac409).setCodeList(CodeList.CARBURANT);
    JPA.em().persist(ac409);
}

    }
    private void createQuestionAC410() {
        // == AC410
        // Consommation moyenne (L/100km)

        ac410 = (IntegerQuestion) questionService.findByCode(QuestionCode.AC410);
if (ac410 == null) {
    ac410 = new IntegerQuestion(ac407, 0, QuestionCode.AC410, null);
    JPA.em().persist(ac410);

    // cleanup the driver
    Driver ac410_driver = driverService.findByName("AC410");
    if (ac410_driver != null) {
        driverService.remove(ac410_driver);
    }

} else {
    if (!ac410.getQuestionSet().equals(ac407) && ac407.getQuestions().contains(ac410)) {
        ac407.getQuestions().remove(ac410);
        JPA.em().persist(ac407);
    }
    if (ac410.getQuestionSet().equals(ac407) && !ac407.getQuestions().contains(ac410)) {
        ac407.getQuestions().add(ac410);
        JPA.em().persist(ac407);
    }
    ac410.setOrderIndex(0);
    ((NumericQuestion)ac410).setUnitCategory(null);

    // cleanup the driver
    Driver ac410_driver = driverService.findByName("AC410");
    if (ac410_driver != null) {
        driverService.remove(ac410_driver);
    }

    ((NumericQuestion)ac410).setDriver(null);

    JPA.em().persist(ac410);
}

    }
    private void createQuestionAC411() {
        // == AC411
        // Quelle est le nombre de kilomètres parcourus par an?

        ac411 = (IntegerQuestion) questionService.findByCode(QuestionCode.AC411);
if (ac411 == null) {
    ac411 = new IntegerQuestion(ac407, 0, QuestionCode.AC411, null);
    JPA.em().persist(ac411);

    // cleanup the driver
    Driver ac411_driver = driverService.findByName("AC411");
    if (ac411_driver != null) {
        driverService.remove(ac411_driver);
    }

} else {
    if (!ac411.getQuestionSet().equals(ac407) && ac407.getQuestions().contains(ac411)) {
        ac407.getQuestions().remove(ac411);
        JPA.em().persist(ac407);
    }
    if (ac411.getQuestionSet().equals(ac407) && !ac407.getQuestions().contains(ac411)) {
        ac407.getQuestions().add(ac411);
        JPA.em().persist(ac407);
    }
    ac411.setOrderIndex(0);
    ((NumericQuestion)ac411).setUnitCategory(null);

    // cleanup the driver
    Driver ac411_driver = driverService.findByName("AC411");
    if (ac411_driver != null) {
        driverService.remove(ac411_driver);
    }

    ((NumericQuestion)ac411).setDriver(null);

    JPA.em().persist(ac411);
}

    }
    private void createQuestionAC414() {
        // == AC414
        // Catégorie de véhicule

        ac414 = (StringQuestion) questionService.findByCode(QuestionCode.AC414);
if (ac414 == null) {
    ac414 = new StringQuestion(ac413, 0, QuestionCode.AC414, null);
    JPA.em().persist(ac414);
} else {
    ((StringQuestion)ac414).setDefaultValue(null);
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

    }
    private void createQuestionAC415() {
        // == AC415
        // Quel type de carburant utilise-t-il ?

        ac415 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AC415);
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
    ((ValueSelectionQuestion)ac415).setCodeList(CodeList.CARBURANT);
    JPA.em().persist(ac415);
}

    }
    private void createQuestionAC416() {
        // == AC416
        // Prix moyen du litre de ce carburant

        
ac416 = (DoubleQuestion) questionService.findByCode(QuestionCode.AC416);
if (ac416 == null) {
    ac416 = new DoubleQuestion( ac413, 0, QuestionCode.AC416, moneyUnits, getUnitBySymbol("EUR") );
    JPA.em().persist(ac416);

    // cleanup the driver
    Driver ac416_driver = driverService.findByName("AC416");
    if (ac416_driver != null) {
        driverService.remove(ac416_driver);
    }

} else {
    if (!ac416.getQuestionSet().equals(ac413) && ac413.getQuestions().contains(ac416)) {
        ac413.getQuestions().remove(ac416);
        JPA.em().persist(ac413);
    }
    if (ac416.getQuestionSet().equals(ac413) && !ac413.getQuestions().contains(ac416)) {
        ac413.getQuestions().add(ac416);
        JPA.em().persist(ac413);
    }
    ((NumericQuestion)ac416).setUnitCategory(moneyUnits);
    ac416.setOrderIndex(0);
    ((NumericQuestion)ac416).setDefaultUnit(getUnitBySymbol("EUR"));

    // cleanup the driver
    Driver ac416_driver = driverService.findByName("AC416");
    if (ac416_driver != null) {
        driverService.remove(ac416_driver);
    }

    ((NumericQuestion)ac416).setDriver(null);

    JPA.em().persist(ac416);
}



    }
    private void createQuestionAC417() {
        // == AC417
        // Quel est le montant annuel de dépenses en carburant?

        
ac417 = (DoubleQuestion) questionService.findByCode(QuestionCode.AC417);
if (ac417 == null) {
    ac417 = new DoubleQuestion( ac413, 0, QuestionCode.AC417, moneyUnits, getUnitBySymbol("EUR") );
    JPA.em().persist(ac417);

    // cleanup the driver
    Driver ac417_driver = driverService.findByName("AC417");
    if (ac417_driver != null) {
        driverService.remove(ac417_driver);
    }

} else {
    if (!ac417.getQuestionSet().equals(ac413) && ac413.getQuestions().contains(ac417)) {
        ac413.getQuestions().remove(ac417);
        JPA.em().persist(ac413);
    }
    if (ac417.getQuestionSet().equals(ac413) && !ac413.getQuestions().contains(ac417)) {
        ac413.getQuestions().add(ac417);
        JPA.em().persist(ac413);
    }
    ((NumericQuestion)ac417).setUnitCategory(moneyUnits);
    ac417.setOrderIndex(0);
    ((NumericQuestion)ac417).setDefaultUnit(getUnitBySymbol("EUR"));

    // cleanup the driver
    Driver ac417_driver = driverService.findByName("AC417");
    if (ac417_driver != null) {
        driverService.remove(ac417_driver);
    }

    ((NumericQuestion)ac417).setDriver(null);

    JPA.em().persist(ac417);
}



    }
    private void createQuestionAC503() {
        // == AC503
        // Consommation d'essence

        
ac503 = (DoubleQuestion) questionService.findByCode(QuestionCode.AC503);
if (ac503 == null) {
    ac503 = new DoubleQuestion( ac502, 0, QuestionCode.AC503, volumeUnits, volumeUnits.getMainUnit() );
    JPA.em().persist(ac503);

    // cleanup the driver
    Driver ac503_driver = driverService.findByName("AC503");
    if (ac503_driver != null) {
        driverService.remove(ac503_driver);
    }

} else {
    if (!ac503.getQuestionSet().equals(ac502) && ac502.getQuestions().contains(ac503)) {
        ac502.getQuestions().remove(ac503);
        JPA.em().persist(ac502);
    }
    if (ac503.getQuestionSet().equals(ac502) && !ac502.getQuestions().contains(ac503)) {
        ac502.getQuestions().add(ac503);
        JPA.em().persist(ac502);
    }
    ((NumericQuestion)ac503).setUnitCategory(volumeUnits);
    ac503.setOrderIndex(0);
    ((NumericQuestion)ac503).setDefaultUnit(volumeUnits.getMainUnit());

    // cleanup the driver
    Driver ac503_driver = driverService.findByName("AC503");
    if (ac503_driver != null) {
        driverService.remove(ac503_driver);
    }

    ((NumericQuestion)ac503).setDriver(null);

    JPA.em().persist(ac503);
}



    }
    private void createQuestionAC504() {
        // == AC504
        // Consommation de diesel

        
ac504 = (DoubleQuestion) questionService.findByCode(QuestionCode.AC504);
if (ac504 == null) {
    ac504 = new DoubleQuestion( ac502, 0, QuestionCode.AC504, volumeUnits, volumeUnits.getMainUnit() );
    JPA.em().persist(ac504);

    // cleanup the driver
    Driver ac504_driver = driverService.findByName("AC504");
    if (ac504_driver != null) {
        driverService.remove(ac504_driver);
    }

} else {
    if (!ac504.getQuestionSet().equals(ac502) && ac502.getQuestions().contains(ac504)) {
        ac502.getQuestions().remove(ac504);
        JPA.em().persist(ac502);
    }
    if (ac504.getQuestionSet().equals(ac502) && !ac502.getQuestions().contains(ac504)) {
        ac502.getQuestions().add(ac504);
        JPA.em().persist(ac502);
    }
    ((NumericQuestion)ac504).setUnitCategory(volumeUnits);
    ac504.setOrderIndex(0);
    ((NumericQuestion)ac504).setDefaultUnit(volumeUnits.getMainUnit());

    // cleanup the driver
    Driver ac504_driver = driverService.findByName("AC504");
    if (ac504_driver != null) {
        driverService.remove(ac504_driver);
    }

    ((NumericQuestion)ac504).setDriver(null);

    JPA.em().persist(ac504);
}



    }
    private void createQuestionAC505() {
        // == AC505
        // Consommation de gaz de pétrole liquéfié (GPL)

        
ac505 = (DoubleQuestion) questionService.findByCode(QuestionCode.AC505);
if (ac505 == null) {
    ac505 = new DoubleQuestion( ac502, 0, QuestionCode.AC505, volumeUnits, volumeUnits.getMainUnit() );
    JPA.em().persist(ac505);

    // cleanup the driver
    Driver ac505_driver = driverService.findByName("AC505");
    if (ac505_driver != null) {
        driverService.remove(ac505_driver);
    }

} else {
    if (!ac505.getQuestionSet().equals(ac502) && ac502.getQuestions().contains(ac505)) {
        ac502.getQuestions().remove(ac505);
        JPA.em().persist(ac502);
    }
    if (ac505.getQuestionSet().equals(ac502) && !ac502.getQuestions().contains(ac505)) {
        ac502.getQuestions().add(ac505);
        JPA.em().persist(ac502);
    }
    ((NumericQuestion)ac505).setUnitCategory(volumeUnits);
    ac505.setOrderIndex(0);
    ((NumericQuestion)ac505).setDefaultUnit(volumeUnits.getMainUnit());

    // cleanup the driver
    Driver ac505_driver = driverService.findByName("AC505");
    if (ac505_driver != null) {
        driverService.remove(ac505_driver);
    }

    ((NumericQuestion)ac505).setDriver(null);

    JPA.em().persist(ac505);
}



    }
    private void createQuestionAC508() {
        // == AC508
        // Catégorie de véhicule

        ac508 = (StringQuestion) questionService.findByCode(QuestionCode.AC508);
if (ac508 == null) {
    ac508 = new StringQuestion(ac507, 0, QuestionCode.AC508, null);
    JPA.em().persist(ac508);
} else {
    ((StringQuestion)ac508).setDefaultValue(null);
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

    }
    private void createQuestionAC509() {
        // == AC509
        // Quel type de carburant utilise-t-il ?

        ac509 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AC509);
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
    ((ValueSelectionQuestion)ac509).setCodeList(CodeList.CARBURANT);
    JPA.em().persist(ac509);
}

    }
    private void createQuestionAC510() {
        // == AC510
        // Consommation moyenne (L/100km)

        ac510 = (IntegerQuestion) questionService.findByCode(QuestionCode.AC510);
if (ac510 == null) {
    ac510 = new IntegerQuestion(ac507, 0, QuestionCode.AC510, null);
    JPA.em().persist(ac510);

    // cleanup the driver
    Driver ac510_driver = driverService.findByName("AC510");
    if (ac510_driver != null) {
        driverService.remove(ac510_driver);
    }

} else {
    if (!ac510.getQuestionSet().equals(ac507) && ac507.getQuestions().contains(ac510)) {
        ac507.getQuestions().remove(ac510);
        JPA.em().persist(ac507);
    }
    if (ac510.getQuestionSet().equals(ac507) && !ac507.getQuestions().contains(ac510)) {
        ac507.getQuestions().add(ac510);
        JPA.em().persist(ac507);
    }
    ac510.setOrderIndex(0);
    ((NumericQuestion)ac510).setUnitCategory(null);

    // cleanup the driver
    Driver ac510_driver = driverService.findByName("AC510");
    if (ac510_driver != null) {
        driverService.remove(ac510_driver);
    }

    ((NumericQuestion)ac510).setDriver(null);

    JPA.em().persist(ac510);
}

    }
    private void createQuestionAC511() {
        // == AC511
        // Quelle est le nombre de kilomètres parcourus par an?

        ac511 = (IntegerQuestion) questionService.findByCode(QuestionCode.AC511);
if (ac511 == null) {
    ac511 = new IntegerQuestion(ac507, 0, QuestionCode.AC511, null);
    JPA.em().persist(ac511);

    // cleanup the driver
    Driver ac511_driver = driverService.findByName("AC511");
    if (ac511_driver != null) {
        driverService.remove(ac511_driver);
    }

} else {
    if (!ac511.getQuestionSet().equals(ac507) && ac507.getQuestions().contains(ac511)) {
        ac507.getQuestions().remove(ac511);
        JPA.em().persist(ac507);
    }
    if (ac511.getQuestionSet().equals(ac507) && !ac507.getQuestions().contains(ac511)) {
        ac507.getQuestions().add(ac511);
        JPA.em().persist(ac507);
    }
    ac511.setOrderIndex(0);
    ((NumericQuestion)ac511).setUnitCategory(null);

    // cleanup the driver
    Driver ac511_driver = driverService.findByName("AC511");
    if (ac511_driver != null) {
        driverService.remove(ac511_driver);
    }

    ((NumericQuestion)ac511).setDriver(null);

    JPA.em().persist(ac511);
}

    }
    private void createQuestionAC514() {
        // == AC514
        // Catégorie de véhicule

        ac514 = (StringQuestion) questionService.findByCode(QuestionCode.AC514);
if (ac514 == null) {
    ac514 = new StringQuestion(ac513, 0, QuestionCode.AC514, null);
    JPA.em().persist(ac514);
} else {
    ((StringQuestion)ac514).setDefaultValue(null);
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

    }
    private void createQuestionAC515() {
        // == AC515
        // Quel type de carburant utilise-t-il ?

        ac515 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AC515);
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
    ((ValueSelectionQuestion)ac515).setCodeList(CodeList.CARBURANT);
    JPA.em().persist(ac515);
}

    }
    private void createQuestionAC516() {
        // == AC516
        // Prix moyen du litre de ce carburant

        
ac516 = (DoubleQuestion) questionService.findByCode(QuestionCode.AC516);
if (ac516 == null) {
    ac516 = new DoubleQuestion( ac513, 0, QuestionCode.AC516, moneyUnits, getUnitBySymbol("EUR") );
    JPA.em().persist(ac516);

    // cleanup the driver
    Driver ac516_driver = driverService.findByName("AC516");
    if (ac516_driver != null) {
        driverService.remove(ac516_driver);
    }

} else {
    if (!ac516.getQuestionSet().equals(ac513) && ac513.getQuestions().contains(ac516)) {
        ac513.getQuestions().remove(ac516);
        JPA.em().persist(ac513);
    }
    if (ac516.getQuestionSet().equals(ac513) && !ac513.getQuestions().contains(ac516)) {
        ac513.getQuestions().add(ac516);
        JPA.em().persist(ac513);
    }
    ((NumericQuestion)ac516).setUnitCategory(moneyUnits);
    ac516.setOrderIndex(0);
    ((NumericQuestion)ac516).setDefaultUnit(getUnitBySymbol("EUR"));

    // cleanup the driver
    Driver ac516_driver = driverService.findByName("AC516");
    if (ac516_driver != null) {
        driverService.remove(ac516_driver);
    }

    ((NumericQuestion)ac516).setDriver(null);

    JPA.em().persist(ac516);
}



    }
    private void createQuestionAC517() {
        // == AC517
        // Quel est le montant annuel de dépenses en carburant?

        
ac517 = (DoubleQuestion) questionService.findByCode(QuestionCode.AC517);
if (ac517 == null) {
    ac517 = new DoubleQuestion( ac513, 0, QuestionCode.AC517, moneyUnits, getUnitBySymbol("EUR") );
    JPA.em().persist(ac517);

    // cleanup the driver
    Driver ac517_driver = driverService.findByName("AC517");
    if (ac517_driver != null) {
        driverService.remove(ac517_driver);
    }

} else {
    if (!ac517.getQuestionSet().equals(ac513) && ac513.getQuestions().contains(ac517)) {
        ac513.getQuestions().remove(ac517);
        JPA.em().persist(ac513);
    }
    if (ac517.getQuestionSet().equals(ac513) && !ac513.getQuestions().contains(ac517)) {
        ac513.getQuestions().add(ac517);
        JPA.em().persist(ac513);
    }
    ((NumericQuestion)ac517).setUnitCategory(moneyUnits);
    ac517.setOrderIndex(0);
    ((NumericQuestion)ac517).setDefaultUnit(getUnitBySymbol("EUR"));

    // cleanup the driver
    Driver ac517_driver = driverService.findByName("AC517");
    if (ac517_driver != null) {
        driverService.remove(ac517_driver);
    }

    ((NumericQuestion)ac517).setDriver(null);

    JPA.em().persist(ac517);
}



    }
    private void createQuestionAC603() {
        // == AC603
        // Consommation d'essence

        
ac603 = (DoubleQuestion) questionService.findByCode(QuestionCode.AC603);
if (ac603 == null) {
    ac603 = new DoubleQuestion( ac602, 0, QuestionCode.AC603, volumeUnits, volumeUnits.getMainUnit() );
    JPA.em().persist(ac603);

    // cleanup the driver
    Driver ac603_driver = driverService.findByName("AC603");
    if (ac603_driver != null) {
        driverService.remove(ac603_driver);
    }

} else {
    if (!ac603.getQuestionSet().equals(ac602) && ac602.getQuestions().contains(ac603)) {
        ac602.getQuestions().remove(ac603);
        JPA.em().persist(ac602);
    }
    if (ac603.getQuestionSet().equals(ac602) && !ac602.getQuestions().contains(ac603)) {
        ac602.getQuestions().add(ac603);
        JPA.em().persist(ac602);
    }
    ((NumericQuestion)ac603).setUnitCategory(volumeUnits);
    ac603.setOrderIndex(0);
    ((NumericQuestion)ac603).setDefaultUnit(volumeUnits.getMainUnit());

    // cleanup the driver
    Driver ac603_driver = driverService.findByName("AC603");
    if (ac603_driver != null) {
        driverService.remove(ac603_driver);
    }

    ((NumericQuestion)ac603).setDriver(null);

    JPA.em().persist(ac603);
}



    }
    private void createQuestionAC604() {
        // == AC604
        // Consommation de diesel

        
ac604 = (DoubleQuestion) questionService.findByCode(QuestionCode.AC604);
if (ac604 == null) {
    ac604 = new DoubleQuestion( ac602, 0, QuestionCode.AC604, volumeUnits, volumeUnits.getMainUnit() );
    JPA.em().persist(ac604);

    // cleanup the driver
    Driver ac604_driver = driverService.findByName("AC604");
    if (ac604_driver != null) {
        driverService.remove(ac604_driver);
    }

} else {
    if (!ac604.getQuestionSet().equals(ac602) && ac602.getQuestions().contains(ac604)) {
        ac602.getQuestions().remove(ac604);
        JPA.em().persist(ac602);
    }
    if (ac604.getQuestionSet().equals(ac602) && !ac602.getQuestions().contains(ac604)) {
        ac602.getQuestions().add(ac604);
        JPA.em().persist(ac602);
    }
    ((NumericQuestion)ac604).setUnitCategory(volumeUnits);
    ac604.setOrderIndex(0);
    ((NumericQuestion)ac604).setDefaultUnit(volumeUnits.getMainUnit());

    // cleanup the driver
    Driver ac604_driver = driverService.findByName("AC604");
    if (ac604_driver != null) {
        driverService.remove(ac604_driver);
    }

    ((NumericQuestion)ac604).setDriver(null);

    JPA.em().persist(ac604);
}



    }
    private void createQuestionAC605() {
        // == AC605
        // Consommation de gaz de pétrole liquéfié (GPL)

        
ac605 = (DoubleQuestion) questionService.findByCode(QuestionCode.AC605);
if (ac605 == null) {
    ac605 = new DoubleQuestion( ac602, 0, QuestionCode.AC605, volumeUnits, volumeUnits.getMainUnit() );
    JPA.em().persist(ac605);

    // cleanup the driver
    Driver ac605_driver = driverService.findByName("AC605");
    if (ac605_driver != null) {
        driverService.remove(ac605_driver);
    }

} else {
    if (!ac605.getQuestionSet().equals(ac602) && ac602.getQuestions().contains(ac605)) {
        ac602.getQuestions().remove(ac605);
        JPA.em().persist(ac602);
    }
    if (ac605.getQuestionSet().equals(ac602) && !ac602.getQuestions().contains(ac605)) {
        ac602.getQuestions().add(ac605);
        JPA.em().persist(ac602);
    }
    ((NumericQuestion)ac605).setUnitCategory(volumeUnits);
    ac605.setOrderIndex(0);
    ((NumericQuestion)ac605).setDefaultUnit(volumeUnits.getMainUnit());

    // cleanup the driver
    Driver ac605_driver = driverService.findByName("AC605");
    if (ac605_driver != null) {
        driverService.remove(ac605_driver);
    }

    ((NumericQuestion)ac605).setDriver(null);

    JPA.em().persist(ac605);
}



    }
    private void createQuestionAC608() {
        // == AC608
        // Catégorie de véhicule

        ac608 = (StringQuestion) questionService.findByCode(QuestionCode.AC608);
if (ac608 == null) {
    ac608 = new StringQuestion(ac607, 0, QuestionCode.AC608, null);
    JPA.em().persist(ac608);
} else {
    ((StringQuestion)ac608).setDefaultValue(null);
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

    }
    private void createQuestionAC609() {
        // == AC609
        // Quel type de carburant utilise-t-il ?

        ac609 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AC609);
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
    ((ValueSelectionQuestion)ac609).setCodeList(CodeList.CARBURANT);
    JPA.em().persist(ac609);
}

    }
    private void createQuestionAC610() {
        // == AC610
        // Consommation moyenne (L/100km)

        ac610 = (IntegerQuestion) questionService.findByCode(QuestionCode.AC610);
if (ac610 == null) {
    ac610 = new IntegerQuestion(ac607, 0, QuestionCode.AC610, null);
    JPA.em().persist(ac610);

    // cleanup the driver
    Driver ac610_driver = driverService.findByName("AC610");
    if (ac610_driver != null) {
        driverService.remove(ac610_driver);
    }

} else {
    if (!ac610.getQuestionSet().equals(ac607) && ac607.getQuestions().contains(ac610)) {
        ac607.getQuestions().remove(ac610);
        JPA.em().persist(ac607);
    }
    if (ac610.getQuestionSet().equals(ac607) && !ac607.getQuestions().contains(ac610)) {
        ac607.getQuestions().add(ac610);
        JPA.em().persist(ac607);
    }
    ac610.setOrderIndex(0);
    ((NumericQuestion)ac610).setUnitCategory(null);

    // cleanup the driver
    Driver ac610_driver = driverService.findByName("AC610");
    if (ac610_driver != null) {
        driverService.remove(ac610_driver);
    }

    ((NumericQuestion)ac610).setDriver(null);

    JPA.em().persist(ac610);
}

    }
    private void createQuestionAC611() {
        // == AC611
        // Quelle est le nombre de kilomètres parcourus par an?

        ac611 = (IntegerQuestion) questionService.findByCode(QuestionCode.AC611);
if (ac611 == null) {
    ac611 = new IntegerQuestion(ac607, 0, QuestionCode.AC611, null);
    JPA.em().persist(ac611);

    // cleanup the driver
    Driver ac611_driver = driverService.findByName("AC611");
    if (ac611_driver != null) {
        driverService.remove(ac611_driver);
    }

} else {
    if (!ac611.getQuestionSet().equals(ac607) && ac607.getQuestions().contains(ac611)) {
        ac607.getQuestions().remove(ac611);
        JPA.em().persist(ac607);
    }
    if (ac611.getQuestionSet().equals(ac607) && !ac607.getQuestions().contains(ac611)) {
        ac607.getQuestions().add(ac611);
        JPA.em().persist(ac607);
    }
    ac611.setOrderIndex(0);
    ((NumericQuestion)ac611).setUnitCategory(null);

    // cleanup the driver
    Driver ac611_driver = driverService.findByName("AC611");
    if (ac611_driver != null) {
        driverService.remove(ac611_driver);
    }

    ((NumericQuestion)ac611).setDriver(null);

    JPA.em().persist(ac611);
}

    }
    private void createQuestionAC614() {
        // == AC614
        // Catégorie de véhicule

        ac614 = (StringQuestion) questionService.findByCode(QuestionCode.AC614);
if (ac614 == null) {
    ac614 = new StringQuestion(ac613, 0, QuestionCode.AC614, null);
    JPA.em().persist(ac614);
} else {
    ((StringQuestion)ac614).setDefaultValue(null);
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

    }
    private void createQuestionAC615() {
        // == AC615
        // Quel type de carburant utilise-t-il ?

        ac615 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AC615);
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
    ((ValueSelectionQuestion)ac615).setCodeList(CodeList.CARBURANT);
    JPA.em().persist(ac615);
}

    }
    private void createQuestionAC616() {
        // == AC616
        // Prix moyen du litre de ce carburant

        
ac616 = (DoubleQuestion) questionService.findByCode(QuestionCode.AC616);
if (ac616 == null) {
    ac616 = new DoubleQuestion( ac613, 0, QuestionCode.AC616, moneyUnits, getUnitBySymbol("EUR") );
    JPA.em().persist(ac616);

    // cleanup the driver
    Driver ac616_driver = driverService.findByName("AC616");
    if (ac616_driver != null) {
        driverService.remove(ac616_driver);
    }

} else {
    if (!ac616.getQuestionSet().equals(ac613) && ac613.getQuestions().contains(ac616)) {
        ac613.getQuestions().remove(ac616);
        JPA.em().persist(ac613);
    }
    if (ac616.getQuestionSet().equals(ac613) && !ac613.getQuestions().contains(ac616)) {
        ac613.getQuestions().add(ac616);
        JPA.em().persist(ac613);
    }
    ((NumericQuestion)ac616).setUnitCategory(moneyUnits);
    ac616.setOrderIndex(0);
    ((NumericQuestion)ac616).setDefaultUnit(getUnitBySymbol("EUR"));

    // cleanup the driver
    Driver ac616_driver = driverService.findByName("AC616");
    if (ac616_driver != null) {
        driverService.remove(ac616_driver);
    }

    ((NumericQuestion)ac616).setDriver(null);

    JPA.em().persist(ac616);
}



    }
    private void createQuestionAC617() {
        // == AC617
        // Quel est le montant annuel de dépenses en carburant?

        
ac617 = (DoubleQuestion) questionService.findByCode(QuestionCode.AC617);
if (ac617 == null) {
    ac617 = new DoubleQuestion( ac613, 0, QuestionCode.AC617, moneyUnits, getUnitBySymbol("EUR") );
    JPA.em().persist(ac617);

    // cleanup the driver
    Driver ac617_driver = driverService.findByName("AC617");
    if (ac617_driver != null) {
        driverService.remove(ac617_driver);
    }

} else {
    if (!ac617.getQuestionSet().equals(ac613) && ac613.getQuestions().contains(ac617)) {
        ac613.getQuestions().remove(ac617);
        JPA.em().persist(ac613);
    }
    if (ac617.getQuestionSet().equals(ac613) && !ac613.getQuestions().contains(ac617)) {
        ac613.getQuestions().add(ac617);
        JPA.em().persist(ac613);
    }
    ((NumericQuestion)ac617).setUnitCategory(moneyUnits);
    ac617.setOrderIndex(0);
    ((NumericQuestion)ac617).setDefaultUnit(getUnitBySymbol("EUR"));

    // cleanup the driver
    Driver ac617_driver = driverService.findByName("AC617");
    if (ac617_driver != null) {
        driverService.remove(ac617_driver);
    }

    ((NumericQuestion)ac617).setDriver(null);

    JPA.em().persist(ac617);
}



    }
    private void createQuestionAC94() {
        // == AC94
        // Bus TEC (en km.passagers)

        ac94 = (IntegerQuestion) questionService.findByCode(QuestionCode.AC94);
if (ac94 == null) {
    ac94 = new IntegerQuestion(ac93, 0, QuestionCode.AC94, null);
    JPA.em().persist(ac94);

    // cleanup the driver
    Driver ac94_driver = driverService.findByName("AC94");
    if (ac94_driver != null) {
        driverService.remove(ac94_driver);
    }

} else {
    if (!ac94.getQuestionSet().equals(ac93) && ac93.getQuestions().contains(ac94)) {
        ac93.getQuestions().remove(ac94);
        JPA.em().persist(ac93);
    }
    if (ac94.getQuestionSet().equals(ac93) && !ac93.getQuestions().contains(ac94)) {
        ac93.getQuestions().add(ac94);
        JPA.em().persist(ac93);
    }
    ac94.setOrderIndex(0);
    ((NumericQuestion)ac94).setUnitCategory(null);

    // cleanup the driver
    Driver ac94_driver = driverService.findByName("AC94");
    if (ac94_driver != null) {
        driverService.remove(ac94_driver);
    }

    ((NumericQuestion)ac94).setDriver(null);

    JPA.em().persist(ac94);
}

    }
    private void createQuestionAC95() {
        // == AC95
        // Métro (en km.passagers)

        ac95 = (IntegerQuestion) questionService.findByCode(QuestionCode.AC95);
if (ac95 == null) {
    ac95 = new IntegerQuestion(ac93, 0, QuestionCode.AC95, null);
    JPA.em().persist(ac95);

    // cleanup the driver
    Driver ac95_driver = driverService.findByName("AC95");
    if (ac95_driver != null) {
        driverService.remove(ac95_driver);
    }

} else {
    if (!ac95.getQuestionSet().equals(ac93) && ac93.getQuestions().contains(ac95)) {
        ac93.getQuestions().remove(ac95);
        JPA.em().persist(ac93);
    }
    if (ac95.getQuestionSet().equals(ac93) && !ac93.getQuestions().contains(ac95)) {
        ac93.getQuestions().add(ac95);
        JPA.em().persist(ac93);
    }
    ac95.setOrderIndex(0);
    ((NumericQuestion)ac95).setUnitCategory(null);

    // cleanup the driver
    Driver ac95_driver = driverService.findByName("AC95");
    if (ac95_driver != null) {
        driverService.remove(ac95_driver);
    }

    ((NumericQuestion)ac95).setDriver(null);

    JPA.em().persist(ac95);
}

    }
    private void createQuestionAC96() {
        // == AC96
        // Train national SNCB (en km.passagers)

        ac96 = (IntegerQuestion) questionService.findByCode(QuestionCode.AC96);
if (ac96 == null) {
    ac96 = new IntegerQuestion(ac93, 0, QuestionCode.AC96, null);
    JPA.em().persist(ac96);

    // cleanup the driver
    Driver ac96_driver = driverService.findByName("AC96");
    if (ac96_driver != null) {
        driverService.remove(ac96_driver);
    }

} else {
    if (!ac96.getQuestionSet().equals(ac93) && ac93.getQuestions().contains(ac96)) {
        ac93.getQuestions().remove(ac96);
        JPA.em().persist(ac93);
    }
    if (ac96.getQuestionSet().equals(ac93) && !ac93.getQuestions().contains(ac96)) {
        ac93.getQuestions().add(ac96);
        JPA.em().persist(ac93);
    }
    ac96.setOrderIndex(0);
    ((NumericQuestion)ac96).setUnitCategory(null);

    // cleanup the driver
    Driver ac96_driver = driverService.findByName("AC96");
    if (ac96_driver != null) {
        driverService.remove(ac96_driver);
    }

    ((NumericQuestion)ac96).setDriver(null);

    JPA.em().persist(ac96);
}

    }
    private void createQuestionAC97() {
        // == AC97
        // Tram  (en km.passagers)

        ac97 = (IntegerQuestion) questionService.findByCode(QuestionCode.AC97);
if (ac97 == null) {
    ac97 = new IntegerQuestion(ac93, 0, QuestionCode.AC97, null);
    JPA.em().persist(ac97);

    // cleanup the driver
    Driver ac97_driver = driverService.findByName("AC97");
    if (ac97_driver != null) {
        driverService.remove(ac97_driver);
    }

} else {
    if (!ac97.getQuestionSet().equals(ac93) && ac93.getQuestions().contains(ac97)) {
        ac93.getQuestions().remove(ac97);
        JPA.em().persist(ac93);
    }
    if (ac97.getQuestionSet().equals(ac93) && !ac93.getQuestions().contains(ac97)) {
        ac93.getQuestions().add(ac97);
        JPA.em().persist(ac93);
    }
    ac97.setOrderIndex(0);
    ((NumericQuestion)ac97).setUnitCategory(null);

    // cleanup the driver
    Driver ac97_driver = driverService.findByName("AC97");
    if (ac97_driver != null) {
        driverService.remove(ac97_driver);
    }

    ((NumericQuestion)ac97).setDriver(null);

    JPA.em().persist(ac97);
}

    }
    private void createQuestionAC99() {
        // == AC99
        // Bus TEC (en km.passagers)

        ac99 = (IntegerQuestion) questionService.findByCode(QuestionCode.AC99);
if (ac99 == null) {
    ac99 = new IntegerQuestion(ac98, 0, QuestionCode.AC99, null);
    JPA.em().persist(ac99);

    // cleanup the driver
    Driver ac99_driver = driverService.findByName("AC99");
    if (ac99_driver != null) {
        driverService.remove(ac99_driver);
    }

} else {
    if (!ac99.getQuestionSet().equals(ac98) && ac98.getQuestions().contains(ac99)) {
        ac98.getQuestions().remove(ac99);
        JPA.em().persist(ac98);
    }
    if (ac99.getQuestionSet().equals(ac98) && !ac98.getQuestions().contains(ac99)) {
        ac98.getQuestions().add(ac99);
        JPA.em().persist(ac98);
    }
    ac99.setOrderIndex(0);
    ((NumericQuestion)ac99).setUnitCategory(null);

    // cleanup the driver
    Driver ac99_driver = driverService.findByName("AC99");
    if (ac99_driver != null) {
        driverService.remove(ac99_driver);
    }

    ((NumericQuestion)ac99).setDriver(null);

    JPA.em().persist(ac99);
}

    }
    private void createQuestionAC100() {
        // == AC100
        // Métro (en km.passagers)

        ac100 = (IntegerQuestion) questionService.findByCode(QuestionCode.AC100);
if (ac100 == null) {
    ac100 = new IntegerQuestion(ac98, 0, QuestionCode.AC100, null);
    JPA.em().persist(ac100);

    // cleanup the driver
    Driver ac100_driver = driverService.findByName("AC100");
    if (ac100_driver != null) {
        driverService.remove(ac100_driver);
    }

} else {
    if (!ac100.getQuestionSet().equals(ac98) && ac98.getQuestions().contains(ac100)) {
        ac98.getQuestions().remove(ac100);
        JPA.em().persist(ac98);
    }
    if (ac100.getQuestionSet().equals(ac98) && !ac98.getQuestions().contains(ac100)) {
        ac98.getQuestions().add(ac100);
        JPA.em().persist(ac98);
    }
    ac100.setOrderIndex(0);
    ((NumericQuestion)ac100).setUnitCategory(null);

    // cleanup the driver
    Driver ac100_driver = driverService.findByName("AC100");
    if (ac100_driver != null) {
        driverService.remove(ac100_driver);
    }

    ((NumericQuestion)ac100).setDriver(null);

    JPA.em().persist(ac100);
}

    }
    private void createQuestionAC101() {
        // == AC101
        // Train national SNCB (en km.passagers)

        ac101 = (IntegerQuestion) questionService.findByCode(QuestionCode.AC101);
if (ac101 == null) {
    ac101 = new IntegerQuestion(ac98, 0, QuestionCode.AC101, null);
    JPA.em().persist(ac101);

    // cleanup the driver
    Driver ac101_driver = driverService.findByName("AC101");
    if (ac101_driver != null) {
        driverService.remove(ac101_driver);
    }

} else {
    if (!ac101.getQuestionSet().equals(ac98) && ac98.getQuestions().contains(ac101)) {
        ac98.getQuestions().remove(ac101);
        JPA.em().persist(ac98);
    }
    if (ac101.getQuestionSet().equals(ac98) && !ac98.getQuestions().contains(ac101)) {
        ac98.getQuestions().add(ac101);
        JPA.em().persist(ac98);
    }
    ac101.setOrderIndex(0);
    ((NumericQuestion)ac101).setUnitCategory(null);

    // cleanup the driver
    Driver ac101_driver = driverService.findByName("AC101");
    if (ac101_driver != null) {
        driverService.remove(ac101_driver);
    }

    ((NumericQuestion)ac101).setDriver(null);

    JPA.em().persist(ac101);
}

    }
    private void createQuestionAC102() {
        // == AC102
        // Tram (en km.passagers)

        ac102 = (IntegerQuestion) questionService.findByCode(QuestionCode.AC102);
if (ac102 == null) {
    ac102 = new IntegerQuestion(ac98, 0, QuestionCode.AC102, null);
    JPA.em().persist(ac102);

    // cleanup the driver
    Driver ac102_driver = driverService.findByName("AC102");
    if (ac102_driver != null) {
        driverService.remove(ac102_driver);
    }

} else {
    if (!ac102.getQuestionSet().equals(ac98) && ac98.getQuestions().contains(ac102)) {
        ac98.getQuestions().remove(ac102);
        JPA.em().persist(ac98);
    }
    if (ac102.getQuestionSet().equals(ac98) && !ac98.getQuestions().contains(ac102)) {
        ac98.getQuestions().add(ac102);
        JPA.em().persist(ac98);
    }
    ac102.setOrderIndex(0);
    ((NumericQuestion)ac102).setUnitCategory(null);

    // cleanup the driver
    Driver ac102_driver = driverService.findByName("AC102");
    if (ac102_driver != null) {
        driverService.remove(ac102_driver);
    }

    ((NumericQuestion)ac102).setDriver(null);

    JPA.em().persist(ac102);
}

    }
    private void createQuestionAC103() {
        // == AC103
        // Taxi (en véhicule.km)

        ac103 = (IntegerQuestion) questionService.findByCode(QuestionCode.AC103);
if (ac103 == null) {
    ac103 = new IntegerQuestion(ac98, 0, QuestionCode.AC103, null);
    JPA.em().persist(ac103);

    // cleanup the driver
    Driver ac103_driver = driverService.findByName("AC103");
    if (ac103_driver != null) {
        driverService.remove(ac103_driver);
    }

} else {
    if (!ac103.getQuestionSet().equals(ac98) && ac98.getQuestions().contains(ac103)) {
        ac98.getQuestions().remove(ac103);
        JPA.em().persist(ac98);
    }
    if (ac103.getQuestionSet().equals(ac98) && !ac98.getQuestions().contains(ac103)) {
        ac98.getQuestions().add(ac103);
        JPA.em().persist(ac98);
    }
    ac103.setOrderIndex(0);
    ((NumericQuestion)ac103).setUnitCategory(null);

    // cleanup the driver
    Driver ac103_driver = driverService.findByName("AC103");
    if (ac103_driver != null) {
        driverService.remove(ac103_driver);
    }

    ((NumericQuestion)ac103).setDriver(null);

    JPA.em().persist(ac103);
}

    }
    private void createQuestionAC104() {
        // == AC104
        // Taxi (en montant dépensé)

        
ac104 = (DoubleQuestion) questionService.findByCode(QuestionCode.AC104);
if (ac104 == null) {
    ac104 = new DoubleQuestion( ac98, 0, QuestionCode.AC104, moneyUnits, getUnitBySymbol("EUR") );
    JPA.em().persist(ac104);

    // cleanup the driver
    Driver ac104_driver = driverService.findByName("AC104");
    if (ac104_driver != null) {
        driverService.remove(ac104_driver);
    }

} else {
    if (!ac104.getQuestionSet().equals(ac98) && ac98.getQuestions().contains(ac104)) {
        ac98.getQuestions().remove(ac104);
        JPA.em().persist(ac98);
    }
    if (ac104.getQuestionSet().equals(ac98) && !ac98.getQuestions().contains(ac104)) {
        ac98.getQuestions().add(ac104);
        JPA.em().persist(ac98);
    }
    ((NumericQuestion)ac104).setUnitCategory(moneyUnits);
    ac104.setOrderIndex(0);
    ((NumericQuestion)ac104).setDefaultUnit(getUnitBySymbol("EUR"));

    // cleanup the driver
    Driver ac104_driver = driverService.findByName("AC104");
    if (ac104_driver != null) {
        driverService.remove(ac104_driver);
    }

    ((NumericQuestion)ac104).setDriver(null);

    JPA.em().persist(ac104);
}



    }
    private void createQuestionAC105() {
        // == AC105
        // TGV (en km.passagers)

        ac105 = (IntegerQuestion) questionService.findByCode(QuestionCode.AC105);
if (ac105 == null) {
    ac105 = new IntegerQuestion(ac98, 0, QuestionCode.AC105, null);
    JPA.em().persist(ac105);

    // cleanup the driver
    Driver ac105_driver = driverService.findByName("AC105");
    if (ac105_driver != null) {
        driverService.remove(ac105_driver);
    }

} else {
    if (!ac105.getQuestionSet().equals(ac98) && ac98.getQuestions().contains(ac105)) {
        ac98.getQuestions().remove(ac105);
        JPA.em().persist(ac98);
    }
    if (ac105.getQuestionSet().equals(ac98) && !ac98.getQuestions().contains(ac105)) {
        ac98.getQuestions().add(ac105);
        JPA.em().persist(ac98);
    }
    ac105.setOrderIndex(0);
    ((NumericQuestion)ac105).setUnitCategory(null);

    // cleanup the driver
    Driver ac105_driver = driverService.findByName("AC105");
    if (ac105_driver != null) {
        driverService.remove(ac105_driver);
    }

    ((NumericQuestion)ac105).setDriver(null);

    JPA.em().persist(ac105);
}

    }
    private void createQuestionAC108() {
        // == AC108
        // Catégorie de vol

        ac108 = (StringQuestion) questionService.findByCode(QuestionCode.AC108);
if (ac108 == null) {
    ac108 = new StringQuestion(ac107, 0, QuestionCode.AC108, null);
    JPA.em().persist(ac108);
} else {
    ((StringQuestion)ac108).setDefaultValue(null);
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

    }
    private void createQuestionAC109() {
        // == AC109
        // Type de vol

        ac109 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AC109);
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
    ((ValueSelectionQuestion)ac109).setCodeList(CodeList.TYPEVOL);
    JPA.em().persist(ac109);
}

    }
    private void createQuestionAC110() {
        // == AC110
        // Classe du vol

        ac110 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AC110);
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
    ((ValueSelectionQuestion)ac110).setCodeList(CodeList.CATEGORIEVOL);
    JPA.em().persist(ac110);
}

    }
    private void createQuestionAC111() {
        // == AC111
        // Nombre total de passagers pour cette catégorie de vols

        ac111 = (IntegerQuestion) questionService.findByCode(QuestionCode.AC111);
if (ac111 == null) {
    ac111 = new IntegerQuestion(ac107, 0, QuestionCode.AC111, null);
    JPA.em().persist(ac111);

    // cleanup the driver
    Driver ac111_driver = driverService.findByName("AC111");
    if (ac111_driver != null) {
        driverService.remove(ac111_driver);
    }

} else {
    if (!ac111.getQuestionSet().equals(ac107) && ac107.getQuestions().contains(ac111)) {
        ac107.getQuestions().remove(ac111);
        JPA.em().persist(ac107);
    }
    if (ac111.getQuestionSet().equals(ac107) && !ac107.getQuestions().contains(ac111)) {
        ac107.getQuestions().add(ac111);
        JPA.em().persist(ac107);
    }
    ac111.setOrderIndex(0);
    ((NumericQuestion)ac111).setUnitCategory(null);

    // cleanup the driver
    Driver ac111_driver = driverService.findByName("AC111");
    if (ac111_driver != null) {
        driverService.remove(ac111_driver);
    }

    ((NumericQuestion)ac111).setDriver(null);

    JPA.em().persist(ac111);
}

    }
    private void createQuestionAC112() {
        // == AC112
        // Distance totale (aller-retour)

        
ac112 = (DoubleQuestion) questionService.findByCode(QuestionCode.AC112);
if (ac112 == null) {
    ac112 = new DoubleQuestion( ac107, 0, QuestionCode.AC112, lengthUnits, getUnitBySymbol("km") );
    JPA.em().persist(ac112);

    // cleanup the driver
    Driver ac112_driver = driverService.findByName("AC112");
    if (ac112_driver != null) {
        driverService.remove(ac112_driver);
    }

} else {
    if (!ac112.getQuestionSet().equals(ac107) && ac107.getQuestions().contains(ac112)) {
        ac107.getQuestions().remove(ac112);
        JPA.em().persist(ac107);
    }
    if (ac112.getQuestionSet().equals(ac107) && !ac107.getQuestions().contains(ac112)) {
        ac107.getQuestions().add(ac112);
        JPA.em().persist(ac107);
    }
    ((NumericQuestion)ac112).setUnitCategory(lengthUnits);
    ac112.setOrderIndex(0);
    ((NumericQuestion)ac112).setDefaultUnit(getUnitBySymbol("km"));

    // cleanup the driver
    Driver ac112_driver = driverService.findByName("AC112");
    if (ac112_driver != null) {
        driverService.remove(ac112_driver);
    }

    ((NumericQuestion)ac112).setDriver(null);

    JPA.em().persist(ac112);
}



    }
    private void createQuestionAC113() {
        // == AC113
        // Motif de déplacement

        ac113 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AC113);
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
    ((ValueSelectionQuestion)ac113).setCodeList(CodeList.MOTIFDEPLACEMENTHORSDDT);
    JPA.em().persist(ac113);
}

    }
    private void createQuestionAC115() {
        // == AC115
        // Fournir ici les documents éventuels justifiant les données suivantes

        ac115 = (DocumentQuestion) questionService.findByCode(QuestionCode.AC115);
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

    }
    private void createQuestionAC117() {
        // == AC117
        // Poste d'achat

        ac117 = (StringQuestion) questionService.findByCode(QuestionCode.AC117);
if (ac117 == null) {
    ac117 = new StringQuestion(ac116, 0, QuestionCode.AC117, null);
    JPA.em().persist(ac117);
} else {
    ((StringQuestion)ac117).setDefaultValue(null);
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

    }
    private void createQuestionAC118() {
        // == AC118
        // Catégorie

        ac118 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AC118);
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
    ((ValueSelectionQuestion)ac118).setCodeList(CodeList.TYPEACHAT);
    JPA.em().persist(ac118);
}

    }
    private void createQuestionAC119() {
        // == AC119
        // Type

        ac119 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AC119);
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
    ((ValueSelectionQuestion)ac119).setCodeList(CodeList.ACHATMETAL);
    JPA.em().persist(ac119);
}

    }
    private void createQuestionAC120() {
        // == AC120
        // Type

        ac120 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AC120);
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
    ((ValueSelectionQuestion)ac120).setCodeList(CodeList.ACHATPLASTIQUE);
    JPA.em().persist(ac120);
}

    }
    private void createQuestionAC121() {
        // == AC121
        // Type

        ac121 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AC121);
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
    ((ValueSelectionQuestion)ac121).setCodeList(CodeList.ACHATPAPIER);
    JPA.em().persist(ac121);
}

    }
    private void createQuestionAC122() {
        // == AC122
        // Type

        ac122 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AC122);
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
    ((ValueSelectionQuestion)ac122).setCodeList(CodeList.ACHATVERRE);
    JPA.em().persist(ac122);
}

    }
    private void createQuestionAC123() {
        // == AC123
        // Type

        ac123 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AC123);
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
    ((ValueSelectionQuestion)ac123).setCodeList(CodeList.ACHATCHIMIQUE);
    JPA.em().persist(ac123);
}

    }
    private void createQuestionAC124() {
        // == AC124
        // Type

        ac124 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AC124);
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
    ((ValueSelectionQuestion)ac124).setCodeList(CodeList.ACHATROUTE);
    JPA.em().persist(ac124);
}

    }
    private void createQuestionAC125() {
        // == AC125
        // Type

        ac125 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AC125);
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
    ((ValueSelectionQuestion)ac125).setCodeList(CodeList.ACHATAGRO);
    JPA.em().persist(ac125);
}

    }
    private void createQuestionAC126() {
        // == AC126
        // Type

        ac126 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AC126);
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
    ((ValueSelectionQuestion)ac126).setCodeList(CodeList.ACHATSERVICE);
    JPA.em().persist(ac126);
}

    }
    private void createQuestionAC127() {
        // == AC127
        // Taux de recyclé

        ac127 = (PercentageQuestion) questionService.findByCode(QuestionCode.AC127);
if (ac127 == null) {
    ac127 = new PercentageQuestion(ac116, 0, QuestionCode.AC127);
    JPA.em().persist(ac127);

    // cleanup the driver
    Driver ac127_driver = driverService.findByName("AC127");
    if (ac127_driver != null) {
        driverService.remove(ac127_driver);
    }

    // recreate with good value
    ac127_driver = new Driver("AC127");
    driverService.saveOrUpdate(ac127_driver);
    Period p2000 = periodService.findByCode(PeriodCode.P2000);
    DriverValue dv = new DriverValue(ac127_driver, p2000, Double.valueOf(0.9));
    ac127_driver.getDriverValueList().add(dv);;
    driverService.saveOrUpdate(ac127_driver);
    JPA.em().persist(dv);

    ((NumericQuestion)ac127).setDriver(ac127_driver);
    JPA.em().persist(ac127);
} else {
    if (!ac127.getQuestionSet().equals(ac116) && ac116.getQuestions().contains(ac127)) {
        ac116.getQuestions().remove(ac127);
        JPA.em().persist(ac116);
    }
    if (ac127.getQuestionSet().equals(ac116) && !ac116.getQuestions().contains(ac127)) {
        ac116.getQuestions().add(ac127);
        JPA.em().persist(ac116);
    }
    ac127.setOrderIndex(0);

    // cleanup the driver
    Driver ac127_driver = driverService.findByName("AC127");
    if (ac127_driver != null) {
        driverService.remove(ac127_driver);
    }

    // recreate with good value
    ac127_driver = new Driver("AC127");
    driverService.saveOrUpdate(ac127_driver);
    Period p2000 = periodService.findByCode(PeriodCode.P2000);
    DriverValue dv = new DriverValue(ac127_driver, p2000, Double.valueOf(0.9));
    ac127_driver.getDriverValueList().add(dv);;
    driverService.saveOrUpdate(ac127_driver);
    JPA.em().persist(dv);

    ((NumericQuestion)ac127).setDriver(ac127_driver);

    JPA.em().persist(ac127);
}

    }
    private void createQuestionAC128() {
        // == AC128
        // Quantité

        
ac128 = (DoubleQuestion) questionService.findByCode(QuestionCode.AC128);
if (ac128 == null) {
    ac128 = new DoubleQuestion( ac116, 0, QuestionCode.AC128, massUnits, getUnitBySymbol("t") );
    JPA.em().persist(ac128);

    // cleanup the driver
    Driver ac128_driver = driverService.findByName("AC128");
    if (ac128_driver != null) {
        driverService.remove(ac128_driver);
    }

} else {
    if (!ac128.getQuestionSet().equals(ac116) && ac116.getQuestions().contains(ac128)) {
        ac116.getQuestions().remove(ac128);
        JPA.em().persist(ac116);
    }
    if (ac128.getQuestionSet().equals(ac116) && !ac116.getQuestions().contains(ac128)) {
        ac116.getQuestions().add(ac128);
        JPA.em().persist(ac116);
    }
    ((NumericQuestion)ac128).setUnitCategory(massUnits);
    ac128.setOrderIndex(0);
    ((NumericQuestion)ac128).setDefaultUnit(getUnitBySymbol("t"));


    // cleanup the driver
    Driver ac128_driver = driverService.findByName("AC128");
    if (ac128_driver != null) {
        driverService.remove(ac128_driver);
    }

    ((NumericQuestion)ac128).setDriver(null);

    JPA.em().persist(ac128);
}



    }
    private void createQuestionAC129() {
        // == AC129
        // Quantité

        
ac129 = (DoubleQuestion) questionService.findByCode(QuestionCode.AC129);
if (ac129 == null) {
    ac129 = new DoubleQuestion( ac116, 0, QuestionCode.AC129, moneyUnits, getUnitBySymbol("EUR") );
    JPA.em().persist(ac129);

    // cleanup the driver
    Driver ac129_driver = driverService.findByName("AC129");
    if (ac129_driver != null) {
        driverService.remove(ac129_driver);
    }

} else {
    if (!ac129.getQuestionSet().equals(ac116) && ac116.getQuestions().contains(ac129)) {
        ac116.getQuestions().remove(ac129);
        JPA.em().persist(ac116);
    }
    if (ac129.getQuestionSet().equals(ac116) && !ac116.getQuestions().contains(ac129)) {
        ac116.getQuestions().add(ac129);
        JPA.em().persist(ac116);
    }
    ((NumericQuestion)ac129).setUnitCategory(moneyUnits);
    ac129.setOrderIndex(0);
    ((NumericQuestion)ac129).setDefaultUnit(getUnitBySymbol("EUR"));

    // cleanup the driver
    Driver ac129_driver = driverService.findByName("AC129");
    if (ac129_driver != null) {
        driverService.remove(ac129_driver);
    }

    ((NumericQuestion)ac129).setDriver(null);

    JPA.em().persist(ac129);
}



    }
    private void createQuestionAC131() {
        // == AC131
        // Fournir ici les documents éventuels justifiant les données suivantes

        ac131 = (DocumentQuestion) questionService.findByCode(QuestionCode.AC131);
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

    }
    private void createQuestionAC133() {
        // == AC133
        // Type d'équipement

        ac133 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AC133);
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
    ((ValueSelectionQuestion)ac133).setCodeList(CodeList.INFRASTRUCTURE);
    JPA.em().persist(ac133);
}

    }
    private void createQuestionAC134() {
        // == AC134
        // Quantité

        

ac134 = (DoubleQuestion) questionService.findByCode(QuestionCode.AC134);
if (ac134 == null) {
    ac134 = new DoubleQuestion( ac132, 0, QuestionCode.AC134, areaUnits, getUnitBySymbol("m2") );
    JPA.em().persist(ac134);

    // cleanup the driver
    Driver ac134_driver = driverService.findByName("AC134");
    if (ac134_driver != null) {
        driverService.remove(ac134_driver);
    }


} else {
    if (!ac134.getQuestionSet().equals(ac132) && ac132.getQuestions().contains(ac134)) {
        ac132.getQuestions().remove(ac134);
        JPA.em().persist(ac132);
    }
    if (ac134.getQuestionSet().equals(ac132) && !ac132.getQuestions().contains(ac134)) {
        ac132.getQuestions().add(ac134);
        JPA.em().persist(ac132);
    }
    ((NumericQuestion)ac134).setUnitCategory(areaUnits);
    ac134.setOrderIndex(0);
    ((NumericQuestion)ac134).setDefaultUnit(getUnitBySymbol("m2"));

    // cleanup the driver
    Driver ac134_driver = driverService.findByName("AC134");
    if (ac134_driver != null) {
        driverService.remove(ac134_driver);
    }

    ((NumericQuestion)ac134).setDriver(null);

    JPA.em().persist(ac134);
}



    }
    private void createQuestionAC135() {
        // == AC135
        // Quantité

        
ac135 = (DoubleQuestion) questionService.findByCode(QuestionCode.AC135);
if (ac135 == null) {
    ac135 = new DoubleQuestion( ac132, 0, QuestionCode.AC135, massUnits, getUnitBySymbol("t") );
    JPA.em().persist(ac135);

    // cleanup the driver
    Driver ac135_driver = driverService.findByName("AC135");
    if (ac135_driver != null) {
        driverService.remove(ac135_driver);
    }

} else {
    if (!ac135.getQuestionSet().equals(ac132) && ac132.getQuestions().contains(ac135)) {
        ac132.getQuestions().remove(ac135);
        JPA.em().persist(ac132);
    }
    if (ac135.getQuestionSet().equals(ac132) && !ac132.getQuestions().contains(ac135)) {
        ac132.getQuestions().add(ac135);
        JPA.em().persist(ac132);
    }
    ((NumericQuestion)ac135).setUnitCategory(massUnits);
    ac135.setOrderIndex(0);
    ((NumericQuestion)ac135).setDefaultUnit(getUnitBySymbol("t"));


    // cleanup the driver
    Driver ac135_driver = driverService.findByName("AC135");
    if (ac135_driver != null) {
        driverService.remove(ac135_driver);
    }

    ((NumericQuestion)ac135).setDriver(null);

    JPA.em().persist(ac135);
}



    }
    private void createQuestionAC136() {
        // == AC136
        // Nombre d'unités achetées

        ac136 = (IntegerQuestion) questionService.findByCode(QuestionCode.AC136);
if (ac136 == null) {
    ac136 = new IntegerQuestion(ac132, 0, QuestionCode.AC136, null);
    JPA.em().persist(ac136);

    // cleanup the driver
    Driver ac136_driver = driverService.findByName("AC136");
    if (ac136_driver != null) {
        driverService.remove(ac136_driver);
    }

} else {
    if (!ac136.getQuestionSet().equals(ac132) && ac132.getQuestions().contains(ac136)) {
        ac132.getQuestions().remove(ac136);
        JPA.em().persist(ac132);
    }
    if (ac136.getQuestionSet().equals(ac132) && !ac132.getQuestions().contains(ac136)) {
        ac132.getQuestions().add(ac136);
        JPA.em().persist(ac132);
    }
    ac136.setOrderIndex(0);
    ((NumericQuestion)ac136).setUnitCategory(null);

    // cleanup the driver
    Driver ac136_driver = driverService.findByName("AC136");
    if (ac136_driver != null) {
        driverService.remove(ac136_driver);
    }

    ((NumericQuestion)ac136).setDriver(null);

    JPA.em().persist(ac136);
}

    }
    private void createQuestionAC138() {
        // == AC138
        // Fournir ici les documents éventuels justifiant les données suivantes

        ac138 = (DocumentQuestion) questionService.findByCode(QuestionCode.AC138);
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

    }
    private void createQuestionAC140() {
        // == AC140
        // Nom du projet

        ac140 = (StringQuestion) questionService.findByCode(QuestionCode.AC140);
if (ac140 == null) {
    ac140 = new StringQuestion(ac139, 0, QuestionCode.AC140, null);
    JPA.em().persist(ac140);
} else {
    ((StringQuestion)ac140).setDefaultValue(null);
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

    }
    private void createQuestionAC141() {
        // == AC141
        // Part d'investissements dans le projet

        ac141 = (PercentageQuestion) questionService.findByCode(QuestionCode.AC141);
if (ac141 == null) {
    ac141 = new PercentageQuestion(ac139, 0, QuestionCode.AC141);
    JPA.em().persist(ac141);

    // cleanup the driver
    Driver ac141_driver = driverService.findByName("AC141");
    if (ac141_driver != null) {
        driverService.remove(ac141_driver);
    }

} else {
    if (!ac141.getQuestionSet().equals(ac139) && ac139.getQuestions().contains(ac141)) {
        ac139.getQuestions().remove(ac141);
        JPA.em().persist(ac139);
    }
    if (ac141.getQuestionSet().equals(ac139) && !ac139.getQuestions().contains(ac141)) {
        ac139.getQuestions().add(ac141);
        JPA.em().persist(ac139);
    }
    ac141.setOrderIndex(0);

    // cleanup the driver
    Driver ac141_driver = driverService.findByName("AC141");
    if (ac141_driver != null) {
        driverService.remove(ac141_driver);
    }

    ((NumericQuestion)ac141).setDriver(null);

    JPA.em().persist(ac141);
}

    }
    private void createQuestionAC142() {
        // == AC142
        // Emissions directes totales du projet (tCO²e)

        ac142 = (IntegerQuestion) questionService.findByCode(QuestionCode.AC142);
if (ac142 == null) {
    ac142 = new IntegerQuestion(ac139, 0, QuestionCode.AC142, null);
    JPA.em().persist(ac142);

    // cleanup the driver
    Driver ac142_driver = driverService.findByName("AC142");
    if (ac142_driver != null) {
        driverService.remove(ac142_driver);
    }

} else {
    if (!ac142.getQuestionSet().equals(ac139) && ac139.getQuestions().contains(ac142)) {
        ac139.getQuestions().remove(ac142);
        JPA.em().persist(ac139);
    }
    if (ac142.getQuestionSet().equals(ac139) && !ac139.getQuestions().contains(ac142)) {
        ac139.getQuestions().add(ac142);
        JPA.em().persist(ac139);
    }
    ac142.setOrderIndex(0);
    ((NumericQuestion)ac142).setUnitCategory(null);

    // cleanup the driver
    Driver ac142_driver = driverService.findByName("AC142");
    if (ac142_driver != null) {
        driverService.remove(ac142_driver);
    }

    ((NumericQuestion)ac142).setDriver(null);

    JPA.em().persist(ac142);
}

    }
    private void createQuestionAC143() {
        // == AC143
        // Emissions indirectes totales du projet (tCO²e)

        ac143 = (IntegerQuestion) questionService.findByCode(QuestionCode.AC143);
if (ac143 == null) {
    ac143 = new IntegerQuestion(ac139, 0, QuestionCode.AC143, null);
    JPA.em().persist(ac143);

    // cleanup the driver
    Driver ac143_driver = driverService.findByName("AC143");
    if (ac143_driver != null) {
        driverService.remove(ac143_driver);
    }

} else {
    if (!ac143.getQuestionSet().equals(ac139) && ac139.getQuestions().contains(ac143)) {
        ac139.getQuestions().remove(ac143);
        JPA.em().persist(ac139);
    }
    if (ac143.getQuestionSet().equals(ac139) && !ac139.getQuestions().contains(ac143)) {
        ac139.getQuestions().add(ac143);
        JPA.em().persist(ac139);
    }
    ac143.setOrderIndex(0);
    ((NumericQuestion)ac143).setUnitCategory(null);

    // cleanup the driver
    Driver ac143_driver = driverService.findByName("AC143");
    if (ac143_driver != null) {
        driverService.remove(ac143_driver);
    }

    ((NumericQuestion)ac143).setDriver(null);

    JPA.em().persist(ac143);
}

    }


    private UnitCategory getUnitCategoryByCode(UnitCategoryCode unitCategoryCode) {
        return unitCategoryService.findByCode(unitCategoryCode);
    }

    private Unit getUnitBySymbol(String symbol) {
        return unitService.findBySymbol(symbol);
    }

}



