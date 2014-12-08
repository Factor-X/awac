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
public class AwacSmallEmitterInitialData {

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

    private Form form1,form2,form3,form4,form5,form6;
    private QuestionSet ap2,ap14,ap15,ap17,ap41,ap51,ap53,ap56,ap58,ap61,ap62,ap65,ap69,ap70,ap78,ap81,ap91,ap93,ap95,ap106,ap108,ap109,ap121,ap123,ap124,ap600,ap134,ap140,ap142,ap143,ap144,ap151,ap152,ap159,ap161,ap187,ap189;
    private Question ap5,ap6,ap7,ap8,ap9,ap10,ap11,ap12,ap13,ap16,ap18,ap19,ap20,ap21,ap22,ap23,ap24,ap25,ap500,ap501,ap26,ap27,ap28,ap29,ap30,ap31,ap32,ap33,ap34,ap35,ap36,ap37,ap38,ap42,ap43,ap44,ap45,ap46,ap47,ap48,ap49,ap50,ap52,ap54,ap55,ap57,ap59,ap60,ap63,ap64,ap66,ap67,ap68,ap71,ap73,ap76,ap77,ap79,ap80,ap82,ap83,ap84,ap85,ap86,ap87,ap90,ap92,ap94,ap96,ap97,ap98,ap99,ap100,ap101,ap102,ap103,ap104,ap105,ap107,ap110,ap112,ap116,ap117,ap118,ap119,ap120,ap122,ap125,ap127,ap131,ap132,ap133,ap135,ap136,ap137,ap138,ap139,ap141,ap145,ap146,ap147,ap148,ap149,ap150,ap153,ap154,ap155,ap156,ap157,ap158,ap160,ap162,ap163,ap164,ap165,ap166,ap188,ap190,ap191,ap192,ap193,ap194,ap195,ap196;

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

        Logger.info("===> CREATE AWAC SmallEmitter INITIAL DATA -- START");

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
            List<String> codes = Arrays.asList("AP5", "AP6", "AP7", "AP8", "AP9", "AP10", "AP11", "AP12", "AP13", "AP16", "AP18", "AP19", "AP20", "AP21", "AP22", "AP23", "AP24", "AP25", "AP500", "AP501", "AP26", "AP27", "AP28", "AP29", "AP30", "AP31", "AP32", "AP33", "AP34", "AP35", "AP36", "AP37", "AP38", "AP42", "AP43", "AP44", "AP45", "AP46", "AP47", "AP48", "AP49", "AP50", "AP52", "AP54", "AP55", "AP57", "AP59", "AP60", "AP63", "AP64", "AP66", "AP67", "AP68", "AP71", "AP73", "AP76", "AP77", "AP79", "AP80", "AP82", "AP83", "AP84", "AP85", "AP86", "AP87", "AP90", "AP92", "AP94", "AP96", "AP97", "AP98", "AP99", "AP100", "AP101", "AP102", "AP103", "AP104", "AP105", "AP107", "AP110", "AP112", "AP116", "AP117", "AP118", "AP119", "AP120", "AP122", "AP125", "AP127", "AP131", "AP132", "AP133", "AP135", "AP136", "AP137", "AP138", "AP139", "AP141", "AP145", "AP146", "AP147", "AP148", "AP149", "AP150", "AP153", "AP154", "AP155", "AP156", "AP157", "AP158", "AP160", "AP162", "AP163", "AP164", "AP165", "AP166", "AP188", "AP190", "AP191", "AP192", "AP193", "AP194", "AP195", "AP196");

			for (Question q : new ArrayList<>(allQuestions)) {
				if (codes.contains(q.getCode().getKey()) || !q.getCode().getKey().matches("AP[0-9]+")) {
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
            List<String> codes = Arrays.asList("AP2", "AP14", "AP15", "AP17", "AP41", "AP51", "AP53", "AP56", "AP58", "AP61", "AP62", "AP65", "AP69", "AP70", "AP78", "AP81", "AP91", "AP93", "AP95", "AP106", "AP108", "AP109", "AP121", "AP123", "AP124", "AP600", "AP134", "AP140", "AP142", "AP143", "AP144", "AP151", "AP152", "AP159", "AP161", "AP187", "AP189");

			for (QuestionSet qs : new ArrayList<>(allQuestionSets)) {
				if (codes.contains(qs.getCode().getKey()) || !qs.getCode().getKey().matches("AP[0-9]+")) {
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
        createForm6();

        createQuestionSetAP2();
        createQuestionSetAP14();
        createQuestionSetAP15();
        createQuestionSetAP17();
        createQuestionSetAP41();
        createQuestionSetAP51();
        createQuestionSetAP53();
        createQuestionSetAP56();
        createQuestionSetAP58();
        createQuestionSetAP61();
        createQuestionSetAP62();
        createQuestionSetAP65();
        createQuestionSetAP69();
        createQuestionSetAP70();
        createQuestionSetAP78();
        createQuestionSetAP81();
        createQuestionSetAP91();
        createQuestionSetAP93();
        createQuestionSetAP95();
        createQuestionSetAP106();
        createQuestionSetAP108();
        createQuestionSetAP109();
        createQuestionSetAP121();
        createQuestionSetAP123();
        createQuestionSetAP124();
        createQuestionSetAP600();
        createQuestionSetAP134();
        createQuestionSetAP140();
        createQuestionSetAP142();
        createQuestionSetAP143();
        createQuestionSetAP144();
        createQuestionSetAP151();
        createQuestionSetAP152();
        createQuestionSetAP159();
        createQuestionSetAP161();
        createQuestionSetAP187();
        createQuestionSetAP189();

        createQuestionAP5();
        createQuestionAP6();
        createQuestionAP7();
        createQuestionAP8();
        createQuestionAP9();
        createQuestionAP10();
        createQuestionAP11();
        createQuestionAP12();
        createQuestionAP13();
        createQuestionAP16();
        createQuestionAP18();
        createQuestionAP19();
        createQuestionAP20();
        createQuestionAP21();
        createQuestionAP22();
        createQuestionAP23();
        createQuestionAP24();
        createQuestionAP25();
        createQuestionAP500();
        createQuestionAP501();
        createQuestionAP26();
        createQuestionAP27();
        createQuestionAP28();
        createQuestionAP29();
        createQuestionAP30();
        createQuestionAP31();
        createQuestionAP32();
        createQuestionAP33();
        createQuestionAP34();
        createQuestionAP35();
        createQuestionAP36();
        createQuestionAP37();
        createQuestionAP38();
        createQuestionAP42();
        createQuestionAP43();
        createQuestionAP44();
        createQuestionAP45();
        createQuestionAP46();
        createQuestionAP47();
        createQuestionAP48();
        createQuestionAP49();
        createQuestionAP50();
        createQuestionAP52();
        createQuestionAP54();
        createQuestionAP55();
        createQuestionAP57();
        createQuestionAP59();
        createQuestionAP60();
        createQuestionAP63();
        createQuestionAP64();
        createQuestionAP66();
        createQuestionAP67();
        createQuestionAP68();
        createQuestionAP71();
        createQuestionAP73();
        createQuestionAP76();
        createQuestionAP77();
        createQuestionAP79();
        createQuestionAP80();
        createQuestionAP82();
        createQuestionAP83();
        createQuestionAP84();
        createQuestionAP85();
        createQuestionAP86();
        createQuestionAP87();
        createQuestionAP90();
        createQuestionAP92();
        createQuestionAP94();
        createQuestionAP96();
        createQuestionAP97();
        createQuestionAP98();
        createQuestionAP99();
        createQuestionAP100();
        createQuestionAP101();
        createQuestionAP102();
        createQuestionAP103();
        createQuestionAP104();
        createQuestionAP105();
        createQuestionAP107();
        createQuestionAP110();
        createQuestionAP112();
        createQuestionAP116();
        createQuestionAP117();
        createQuestionAP118();
        createQuestionAP119();
        createQuestionAP120();
        createQuestionAP122();
        createQuestionAP125();
        createQuestionAP127();
        createQuestionAP131();
        createQuestionAP132();
        createQuestionAP133();
        createQuestionAP135();
        createQuestionAP136();
        createQuestionAP137();
        createQuestionAP138();
        createQuestionAP139();
        createQuestionAP141();
        createQuestionAP145();
        createQuestionAP146();
        createQuestionAP147();
        createQuestionAP148();
        createQuestionAP149();
        createQuestionAP150();
        createQuestionAP153();
        createQuestionAP154();
        createQuestionAP155();
        createQuestionAP156();
        createQuestionAP157();
        createQuestionAP158();
        createQuestionAP160();
        createQuestionAP162();
        createQuestionAP163();
        createQuestionAP164();
        createQuestionAP165();
        createQuestionAP166();
        createQuestionAP188();
        createQuestionAP190();
        createQuestionAP191();
        createQuestionAP192();
        createQuestionAP193();
        createQuestionAP194();
        createQuestionAP195();
        createQuestionAP196();


        Logger.info("===> CREATE AWAC SmallEmitter INITIAL DATA -- END (Took {} milliseconds)", (System.currentTimeMillis() - startTime));
    }

    // =========================================================================
    // FORMS
    // =========================================================================

    private void createForm1() {
        // == TAB_P1
        // DESCRIPTION DE L'ENTREPRISE
        form1 = formService.findByIdentifier("TAB_P1");
        if (form1 == null) {
            form1 = new Form("TAB_P1");
            JPA.em().persist(form1);
        }
    }
    private void createForm2() {
        // == TAB_P2
        // CONSOMMATION & REJETS
        form2 = formService.findByIdentifier("TAB_P2");
        if (form2 == null) {
            form2 = new Form("TAB_P2");
            JPA.em().persist(form2);
        }
    }
    private void createForm3() {
        // == TAB_P3
        // MOBILITE
        form3 = formService.findByIdentifier("TAB_P3");
        if (form3 == null) {
            form3 = new Form("TAB_P3");
            JPA.em().persist(form3);
        }
    }
    private void createForm4() {
        // == TAB_P4
        // LOGISTIQUE
        form4 = formService.findByIdentifier("TAB_P4");
        if (form4 == null) {
            form4 = new Form("TAB_P4");
            JPA.em().persist(form4);
        }
    }
    private void createForm5() {
        // == TAB_P5
        // DECHETS
        form5 = formService.findByIdentifier("TAB_P5");
        if (form5 == null) {
            form5 = new Form("TAB_P5");
            JPA.em().persist(form5);
        }
    }
    private void createForm6() {
        // == TAB_P6
        // UTILISATION DE MATIERES
        form6 = formService.findByIdentifier("TAB_P6");
        if (form6 == null) {
            form6 = new Form("TAB_P6");
            JPA.em().persist(form6);
        }
    }


    // =========================================================================
    // QUESTION SETS
    // =========================================================================

    private void createQuestionSetAP2() {
        // == AP2
        // Données Générales
        ap2 = questionSetService.findByCode(QuestionCode.AP2);
        if( ap2 == null ) {
            ap2 = new QuestionSet(QuestionCode.AP2, false, null);
            JPA.em().persist(ap2);
        }
        form1.getQuestionSets().add(ap2);
        JPA.em().persist(form1);
    }
    private void createQuestionSetAP14() {
        // == AP14
        // Lieu de travail et processus
        ap14 = questionSetService.findByCode(QuestionCode.AP14);
        if( ap14 == null ) {
            ap14 = new QuestionSet(QuestionCode.AP14, false, null);
            JPA.em().persist(ap14);
        }
        form2.getQuestionSets().add(ap14);
        JPA.em().persist(form2);
    }
    private void createQuestionSetAP15() {
        // == AP15
        // Chauffage
        ap15 = questionSetService.findByCode(QuestionCode.AP15);
        if( ap15 == null ) {
            ap15 = new QuestionSet(QuestionCode.AP15, false, null);
            JPA.em().persist(ap15);
        }
        form2.getQuestionSets().add(ap15);
        JPA.em().persist(form2);
    }
    private void createQuestionSetAP17() {
        // == AP17
        // Listez les différentes sources d'énergie de chauffage que vous utilisez et renseignez ensuite vos consommations
        ap17 = questionSetService.findByCode(QuestionCode.AP17);
        if( ap17 == null ) {
            ap17 = new QuestionSet(QuestionCode.AP17, true, ap15);
            JPA.em().persist(ap17);
        }
    }
    private void createQuestionSetAP41() {
        // == AP41
        // Electricité
        ap41 = questionSetService.findByCode(QuestionCode.AP41);
        if( ap41 == null ) {
            ap41 = new QuestionSet(QuestionCode.AP41, false, null);
            JPA.em().persist(ap41);
        }
        form2.getQuestionSets().add(ap41);
        JPA.em().persist(form2);
    }
    private void createQuestionSetAP51() {
        // == AP51
        // Réfrigération
        ap51 = questionSetService.findByCode(QuestionCode.AP51);
        if( ap51 == null ) {
            ap51 = new QuestionSet(QuestionCode.AP51, false, null);
            JPA.em().persist(ap51);
        }
        form2.getQuestionSets().add(ap51);
        JPA.em().persist(form2);
    }
    private void createQuestionSetAP53() {
        // == AP53
        // Listez les types de gaz réfrigérants que vous utilisez et indiquez les quantités de recharge annuelle pour chacun d'eux
        ap53 = questionSetService.findByCode(QuestionCode.AP53);
        if( ap53 == null ) {
            ap53 = new QuestionSet(QuestionCode.AP53, true, ap51);
            JPA.em().persist(ap53);
        }
    }
    private void createQuestionSetAP56() {
        // == AP56
        // Climatisation
        ap56 = questionSetService.findByCode(QuestionCode.AP56);
        if( ap56 == null ) {
            ap56 = new QuestionSet(QuestionCode.AP56, false, null);
            JPA.em().persist(ap56);
        }
        form2.getQuestionSets().add(ap56);
        JPA.em().persist(form2);
    }
    private void createQuestionSetAP58() {
        // == AP58
        // Listez les différents types de gaz réfrigérants que vous utilisez
        ap58 = questionSetService.findByCode(QuestionCode.AP58);
        if( ap58 == null ) {
            ap58 = new QuestionSet(QuestionCode.AP58, true, ap56);
            JPA.em().persist(ap58);
        }
    }
    private void createQuestionSetAP61() {
        // == AP61
        // Mobilité
        ap61 = questionSetService.findByCode(QuestionCode.AP61);
        if( ap61 == null ) {
            ap61 = new QuestionSet(QuestionCode.AP61, false, null);
            JPA.em().persist(ap61);
        }
        form3.getQuestionSets().add(ap61);
        JPA.em().persist(form3);
    }
    private void createQuestionSetAP62() {
        // == AP62
        // Véhicules de société (propres à la société mais aussi ceux loués de façon régulière)
        ap62 = questionSetService.findByCode(QuestionCode.AP62);
        if( ap62 == null ) {
            ap62 = new QuestionSet(QuestionCode.AP62, false, null);
            JPA.em().persist(ap62);
        }
        form3.getQuestionSets().add(ap62);
        JPA.em().persist(form3);
    }
    private void createQuestionSetAP65() {
        // == AP65
        // Consommation de carburants
        ap65 = questionSetService.findByCode(QuestionCode.AP65);
        if( ap65 == null ) {
            ap65 = new QuestionSet(QuestionCode.AP65, false, ap62);
            JPA.em().persist(ap65);
        }
    }
    private void createQuestionSetAP69() {
        // == AP69
        // Listez le kilométrage annuel total par catégorie de véhicules
        ap69 = questionSetService.findByCode(QuestionCode.AP69);
        if( ap69 == null ) {
            ap69 = new QuestionSet(QuestionCode.AP69, false, ap62);
            JPA.em().persist(ap69);
        }
    }
    private void createQuestionSetAP70() {
        // == AP70
        // Créez autant de catégories de véhicules que souhaité
        ap70 = questionSetService.findByCode(QuestionCode.AP70);
        if( ap70 == null ) {
            ap70 = new QuestionSet(QuestionCode.AP70, true, ap69);
            JPA.em().persist(ap70);
        }
    }
    private void createQuestionSetAP78() {
        // == AP78
        // Déplacements domicile-travail
        ap78 = questionSetService.findByCode(QuestionCode.AP78);
        if( ap78 == null ) {
            ap78 = new QuestionSet(QuestionCode.AP78, false, null);
            JPA.em().persist(ap78);
        }
        form3.getQuestionSets().add(ap78);
        JPA.em().persist(form3);
    }
    private void createQuestionSetAP81() {
        // == AP81
        // Renseignez les déplacements de vos employés un par un
        ap81 = questionSetService.findByCode(QuestionCode.AP81);
        if( ap81 == null ) {
            ap81 = new QuestionSet(QuestionCode.AP81, true, ap78);
            JPA.em().persist(ap81);
        }
    }
    private void createQuestionSetAP91() {
        // == AP91
        // Renseignez lesdéplacements pour l'ensemble de vos travailleurs
        ap91 = questionSetService.findByCode(QuestionCode.AP91);
        if( ap91 == null ) {
            ap91 = new QuestionSet(QuestionCode.AP91, false, ap78);
            JPA.em().persist(ap91);
        }
    }
    private void createQuestionSetAP93() {
        // == AP93
        // Répartition de vos travailleurs par mode de déplacement principal (en % des travailleurs)
        ap93 = questionSetService.findByCode(QuestionCode.AP93);
        if( ap93 == null ) {
            ap93 = new QuestionSet(QuestionCode.AP93, false, ap91);
            JPA.em().persist(ap93);
        }
    }
    private void createQuestionSetAP95() {
        // == AP95
        // Répartition par type de voiture
        ap95 = questionSetService.findByCode(QuestionCode.AP95);
        if( ap95 == null ) {
            ap95 = new QuestionSet(QuestionCode.AP95, false, ap91);
            JPA.em().persist(ap95);
        }
    }
    private void createQuestionSetAP106() {
        // == AP106
        // Déplacements professionnels en Belgique
        ap106 = questionSetService.findByCode(QuestionCode.AP106);
        if( ap106 == null ) {
            ap106 = new QuestionSet(QuestionCode.AP106, false, null);
            JPA.em().persist(ap106);
        }
        form3.getQuestionSets().add(ap106);
        JPA.em().persist(form3);
    }
    private void createQuestionSetAP108() {
        // == AP108
        // Renseignez les distances cumulées parcourues par mode de transport en Belgique
        ap108 = questionSetService.findByCode(QuestionCode.AP108);
        if( ap108 == null ) {
            ap108 = new QuestionSet(QuestionCode.AP108, false, ap106);
            JPA.em().persist(ap108);
        }
    }
    private void createQuestionSetAP109() {
        // == AP109
        // Listez les différents types de véhicules utilisés (véhicules autres que ceux de société )
        ap109 = questionSetService.findByCode(QuestionCode.AP109);
        if( ap109 == null ) {
            ap109 = new QuestionSet(QuestionCode.AP109, true, ap108);
            JPA.em().persist(ap109);
        }
    }
    private void createQuestionSetAP121() {
        // == AP121
        // Déplacements professionnels à l'étranger
        ap121 = questionSetService.findByCode(QuestionCode.AP121);
        if( ap121 == null ) {
            ap121 = new QuestionSet(QuestionCode.AP121, false, null);
            JPA.em().persist(ap121);
        }
        form3.getQuestionSets().add(ap121);
        JPA.em().persist(form3);
    }
    private void createQuestionSetAP123() {
        // == AP123
        // Renseignez les disances cumulées  parcourues par mode de transport à l'étranger
        ap123 = questionSetService.findByCode(QuestionCode.AP123);
        if( ap123 == null ) {
            ap123 = new QuestionSet(QuestionCode.AP123, false, ap121);
            JPA.em().persist(ap123);
        }
    }
    private void createQuestionSetAP124() {
        // == AP124
        // Listez les différents types de véhicules utilisés (véhicules autres que ceux de société )
        ap124 = questionSetService.findByCode(QuestionCode.AP124);
        if( ap124 == null ) {
            ap124 = new QuestionSet(QuestionCode.AP124, true, ap108);
            JPA.em().persist(ap124);
        }
    }
    private void createQuestionSetAP600() {
        // == AP600
        // Avion
        ap600 = questionSetService.findByCode(QuestionCode.AP600);
        if( ap600 == null ) {
            ap600 = new QuestionSet(QuestionCode.AP600, false, null);
            JPA.em().persist(ap600);
        }
        form3.getQuestionSets().add(ap600);
        JPA.em().persist(form3);
    }
    private void createQuestionSetAP134() {
        // == AP134
        // Créez autant de catégories de voyages que nécessaire
        ap134 = questionSetService.findByCode(QuestionCode.AP134);
        if( ap134 == null ) {
            ap134 = new QuestionSet(QuestionCode.AP134, true, ap600);
            JPA.em().persist(ap134);
        }
    }
    private void createQuestionSetAP140() {
        // == AP140
        // Logistique
        ap140 = questionSetService.findByCode(QuestionCode.AP140);
        if( ap140 == null ) {
            ap140 = new QuestionSet(QuestionCode.AP140, false, null);
            JPA.em().persist(ap140);
        }
        form4.getQuestionSets().add(ap140);
        JPA.em().persist(form4);
    }
    private void createQuestionSetAP142() {
        // == AP142
        // Renseignez les informations relatives aux livraisons  de marchandises et expéditions de vos produits réalisées par des transporteurs
        ap142 = questionSetService.findByCode(QuestionCode.AP142);
        if( ap142 == null ) {
            ap142 = new QuestionSet(QuestionCode.AP142, false, ap140);
            JPA.em().persist(ap142);
        }
    }
    private void createQuestionSetAP143() {
        // == AP143
        // Livraisons de marchandises (Véhicules non détenus par l'enteprise)
        ap143 = questionSetService.findByCode(QuestionCode.AP143);
        if( ap143 == null ) {
            ap143 = new QuestionSet(QuestionCode.AP143, false, ap142);
            JPA.em().persist(ap143);
        }
    }
    private void createQuestionSetAP144() {
        // == AP144
        // Créez autant de marchandises que nécessaire
        ap144 = questionSetService.findByCode(QuestionCode.AP144);
        if( ap144 == null ) {
            ap144 = new QuestionSet(QuestionCode.AP144, true, ap108);
            JPA.em().persist(ap144);
        }
    }
    private void createQuestionSetAP151() {
        // == AP151
        // Expéditions de produits vers vos clients (Véhicules non détenus par l'enteprise)
        ap151 = questionSetService.findByCode(QuestionCode.AP151);
        if( ap151 == null ) {
            ap151 = new QuestionSet(QuestionCode.AP151, false, ap142);
            JPA.em().persist(ap151);
        }
    }
    private void createQuestionSetAP152() {
        // == AP152
        // Créez autant de marchandises que nécessaire
        ap152 = questionSetService.findByCode(QuestionCode.AP152);
        if( ap152 == null ) {
            ap152 = new QuestionSet(QuestionCode.AP152, true, ap108);
            JPA.em().persist(ap152);
        }
    }
    private void createQuestionSetAP159() {
        // == AP159
        // Déchets
        ap159 = questionSetService.findByCode(QuestionCode.AP159);
        if( ap159 == null ) {
            ap159 = new QuestionSet(QuestionCode.AP159, false, null);
            JPA.em().persist(ap159);
        }
        form5.getQuestionSets().add(ap159);
        JPA.em().persist(form5);
    }
    private void createQuestionSetAP161() {
        // == AP161
        // Créez une rubrique pour chaque type de déchets que vous générez
        ap161 = questionSetService.findByCode(QuestionCode.AP161);
        if( ap161 == null ) {
            ap161 = new QuestionSet(QuestionCode.AP161, true, ap159);
            JPA.em().persist(ap161);
        }
    }
    private void createQuestionSetAP187() {
        // == AP187
        // Utilisation des matières
        ap187 = questionSetService.findByCode(QuestionCode.AP187);
        if( ap187 == null ) {
            ap187 = new QuestionSet(QuestionCode.AP187, false, null);
            JPA.em().persist(ap187);
        }
        form6.getQuestionSets().add(ap187);
        JPA.em().persist(form6);
    }
    private void createQuestionSetAP189() {
        // == AP189
        // Merci de lister les quantités totales achetées par catégorie de produits
        ap189 = questionSetService.findByCode(QuestionCode.AP189);
        if( ap189 == null ) {
            ap189 = new QuestionSet(QuestionCode.AP189, false, ap187);
            JPA.em().persist(ap189);
        }
    }


    // =========================================================================
    // QUESTIONS
    // =========================================================================

    private void createQuestionAP5() {
        // == AP5
        // Secteur d'activité

        ap5 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AP5);
if (ap5 == null) {
    ap5 = new ValueSelectionQuestion(ap2, 0, QuestionCode.AP5, CodeList.TYPEACTIVITEPETITEMETTEUR);
    JPA.em().persist(ap5);
} else {
    if (!ap5.getQuestionSet().equals(ap2) && ap2.getQuestions().contains(ap5)) {
        ap2.getQuestions().remove(ap5);
        JPA.em().persist(ap2);
    }
    if (ap5.getQuestionSet().equals(ap2) && !ap2.getQuestions().contains(ap5)) {
        ap2.getQuestions().add(ap5);
        JPA.em().persist(ap2);
    }
    ap5.setOrderIndex(0);
    ((ValueSelectionQuestion)ap5).setCodeList(CodeList.TYPEACTIVITEPETITEMETTEUR);
    JPA.em().persist(ap5);
}

    }
    private void createQuestionAP6() {
        // == AP6
        // Précisez:

        ap6 = (StringQuestion) questionService.findByCode(QuestionCode.AP6);
if (ap6 == null) {
    ap6 = new StringQuestion(ap2, 0, QuestionCode.AP6, null);
    JPA.em().persist(ap6);
} else {
    ((StringQuestion)ap6).setDefaultValue(null);
    if (!ap6.getQuestionSet().equals(ap2) && ap2.getQuestions().contains(ap6)) {
        ap2.getQuestions().remove(ap6);
        JPA.em().persist(ap2);
    }
    if (ap6.getQuestionSet().equals(ap2) && !ap2.getQuestions().contains(ap6)) {
        ap2.getQuestions().add(ap6);
        JPA.em().persist(ap2);
    }
    ap6.setOrderIndex(0);
    JPA.em().persist(ap6);
}

    }
    private void createQuestionAP7() {
        // == AP7
        // Nombre de travailleurs sur l'année (personnes)

        ap7 = (IntegerQuestion) questionService.findByCode(QuestionCode.AP7);
if (ap7 == null) {
    ap7 = new IntegerQuestion(ap2, 0, QuestionCode.AP7, null);
    JPA.em().persist(ap7);

    // cleanup the driver
    Driver ap7_driver = driverService.findByName("AP7");
    if (ap7_driver != null) {
        driverService.remove(ap7_driver);
    }

} else {
    if (!ap7.getQuestionSet().equals(ap2) && ap2.getQuestions().contains(ap7)) {
        ap2.getQuestions().remove(ap7);
        JPA.em().persist(ap2);
    }
    if (ap7.getQuestionSet().equals(ap2) && !ap2.getQuestions().contains(ap7)) {
        ap2.getQuestions().add(ap7);
        JPA.em().persist(ap2);
    }
    ap7.setOrderIndex(0);
    ((NumericQuestion)ap7).setUnitCategory(null);

    // cleanup the driver
    Driver ap7_driver = driverService.findByName("AP7");
    if (ap7_driver != null) {
        driverService.remove(ap7_driver);
    }

    ((NumericQuestion)ap7).setDriver(null);

    JPA.em().persist(ap7);
}

    }
    private void createQuestionAP8() {
        // == AP8
        // Nombre d'équivalents temps plein (ETP) sur l'année

        ap8 = (IntegerQuestion) questionService.findByCode(QuestionCode.AP8);
if (ap8 == null) {
    ap8 = new IntegerQuestion(ap2, 0, QuestionCode.AP8, null);
    JPA.em().persist(ap8);

    // cleanup the driver
    Driver ap8_driver = driverService.findByName("AP8");
    if (ap8_driver != null) {
        driverService.remove(ap8_driver);
    }

} else {
    if (!ap8.getQuestionSet().equals(ap2) && ap2.getQuestions().contains(ap8)) {
        ap2.getQuestions().remove(ap8);
        JPA.em().persist(ap2);
    }
    if (ap8.getQuestionSet().equals(ap2) && !ap2.getQuestions().contains(ap8)) {
        ap2.getQuestions().add(ap8);
        JPA.em().persist(ap2);
    }
    ap8.setOrderIndex(0);
    ((NumericQuestion)ap8).setUnitCategory(null);

    // cleanup the driver
    Driver ap8_driver = driverService.findByName("AP8");
    if (ap8_driver != null) {
        driverService.remove(ap8_driver);
    }

    ((NumericQuestion)ap8).setDriver(null);

    JPA.em().persist(ap8);
}

    }
    private void createQuestionAP9() {
        // == AP9
        // Nombre moyen de jours ouvrables sur l'année

        ap9 = (IntegerQuestion) questionService.findByCode(QuestionCode.AP9);
if (ap9 == null) {
    ap9 = new IntegerQuestion(ap2, 0, QuestionCode.AP9, null);
    JPA.em().persist(ap9);

    // cleanup the driver
    Driver ap9_driver = driverService.findByName("AP9");
    if (ap9_driver != null) {
        driverService.remove(ap9_driver);
    }

} else {
    if (!ap9.getQuestionSet().equals(ap2) && ap2.getQuestions().contains(ap9)) {
        ap2.getQuestions().remove(ap9);
        JPA.em().persist(ap2);
    }
    if (ap9.getQuestionSet().equals(ap2) && !ap2.getQuestions().contains(ap9)) {
        ap2.getQuestions().add(ap9);
        JPA.em().persist(ap2);
    }
    ap9.setOrderIndex(0);
    ((NumericQuestion)ap9).setUnitCategory(null);

    // cleanup the driver
    Driver ap9_driver = driverService.findByName("AP9");
    if (ap9_driver != null) {
        driverService.remove(ap9_driver);
    }

    ((NumericQuestion)ap9).setDriver(null);

    JPA.em().persist(ap9);
}

    }
    private void createQuestionAP10() {
        // == AP10
        // Surface d'activité (réserve comprise)

        

ap10 = (DoubleQuestion) questionService.findByCode(QuestionCode.AP10);
if (ap10 == null) {
    ap10 = new DoubleQuestion( ap2, 0, QuestionCode.AP10, areaUnits, getUnitBySymbol("m2") );
    JPA.em().persist(ap10);

    // cleanup the driver
    Driver ap10_driver = driverService.findByName("AP10");
    if (ap10_driver != null) {
        driverService.remove(ap10_driver);
    }


} else {
    if (!ap10.getQuestionSet().equals(ap2) && ap2.getQuestions().contains(ap10)) {
        ap2.getQuestions().remove(ap10);
        JPA.em().persist(ap2);
    }
    if (ap10.getQuestionSet().equals(ap2) && !ap2.getQuestions().contains(ap10)) {
        ap2.getQuestions().add(ap10);
        JPA.em().persist(ap2);
    }
    ((NumericQuestion)ap10).setUnitCategory(areaUnits);
    ap10.setOrderIndex(0);
    ((NumericQuestion)ap10).setDefaultUnit(getUnitBySymbol("m2"));

    // cleanup the driver
    Driver ap10_driver = driverService.findByName("AP10");
    if (ap10_driver != null) {
        driverService.remove(ap10_driver);
    }

    ((NumericQuestion)ap10).setDriver(null);

    JPA.em().persist(ap10);
}



    }
    private void createQuestionAP11() {
        // == AP11
        // Superficie plancher chauffée occuppée par l'entreprise

        

ap11 = (DoubleQuestion) questionService.findByCode(QuestionCode.AP11);
if (ap11 == null) {
    ap11 = new DoubleQuestion( ap2, 0, QuestionCode.AP11, areaUnits, getUnitBySymbol("m2") );
    JPA.em().persist(ap11);

    // cleanup the driver
    Driver ap11_driver = driverService.findByName("AP11");
    if (ap11_driver != null) {
        driverService.remove(ap11_driver);
    }


} else {
    if (!ap11.getQuestionSet().equals(ap2) && ap2.getQuestions().contains(ap11)) {
        ap2.getQuestions().remove(ap11);
        JPA.em().persist(ap2);
    }
    if (ap11.getQuestionSet().equals(ap2) && !ap2.getQuestions().contains(ap11)) {
        ap2.getQuestions().add(ap11);
        JPA.em().persist(ap2);
    }
    ((NumericQuestion)ap11).setUnitCategory(areaUnits);
    ap11.setOrderIndex(0);
    ((NumericQuestion)ap11).setDefaultUnit(getUnitBySymbol("m2"));

    // cleanup the driver
    Driver ap11_driver = driverService.findByName("AP11");
    if (ap11_driver != null) {
        driverService.remove(ap11_driver);
    }

    ((NumericQuestion)ap11).setDriver(null);

    JPA.em().persist(ap11);
}



    }
    private void createQuestionAP12() {
        // == AP12
        // Superficie plancher chauffée de tout le bâtiment(si présence d'autres occuppants dans le bâtiment)

        

ap12 = (DoubleQuestion) questionService.findByCode(QuestionCode.AP12);
if (ap12 == null) {
    ap12 = new DoubleQuestion( ap2, 0, QuestionCode.AP12, areaUnits, getUnitBySymbol("m2") );
    JPA.em().persist(ap12);

    // cleanup the driver
    Driver ap12_driver = driverService.findByName("AP12");
    if (ap12_driver != null) {
        driverService.remove(ap12_driver);
    }


} else {
    if (!ap12.getQuestionSet().equals(ap2) && ap2.getQuestions().contains(ap12)) {
        ap2.getQuestions().remove(ap12);
        JPA.em().persist(ap2);
    }
    if (ap12.getQuestionSet().equals(ap2) && !ap2.getQuestions().contains(ap12)) {
        ap2.getQuestions().add(ap12);
        JPA.em().persist(ap2);
    }
    ((NumericQuestion)ap12).setUnitCategory(areaUnits);
    ap12.setOrderIndex(0);
    ((NumericQuestion)ap12).setDefaultUnit(getUnitBySymbol("m2"));

    // cleanup the driver
    Driver ap12_driver = driverService.findByName("AP12");
    if (ap12_driver != null) {
        driverService.remove(ap12_driver);
    }

    ((NumericQuestion)ap12).setDriver(null);

    JPA.em().persist(ap12);
}



    }
    private void createQuestionAP13() {
        // == AP13
        // <b>ATTENTION</b>: avec une surface supérieure à 300 m2, il nous semblerait plus adéquat que vous utilisiez le calculateur "entreprises" plutôt que celui-ci, destiné à des indépendants et services de proximité.

        

ap13 = (DoubleQuestion) questionService.findByCode(QuestionCode.AP13);
if (ap13 == null) {
    ap13 = new DoubleQuestion( ap2, 0, QuestionCode.AP13, areaUnits, getUnitBySymbol("m2") );
    JPA.em().persist(ap13);

    // cleanup the driver
    Driver ap13_driver = driverService.findByName("AP13");
    if (ap13_driver != null) {
        driverService.remove(ap13_driver);
    }


} else {
    if (!ap13.getQuestionSet().equals(ap2) && ap2.getQuestions().contains(ap13)) {
        ap2.getQuestions().remove(ap13);
        JPA.em().persist(ap2);
    }
    if (ap13.getQuestionSet().equals(ap2) && !ap2.getQuestions().contains(ap13)) {
        ap2.getQuestions().add(ap13);
        JPA.em().persist(ap2);
    }
    ((NumericQuestion)ap13).setUnitCategory(areaUnits);
    ap13.setOrderIndex(0);
    ((NumericQuestion)ap13).setDefaultUnit(getUnitBySymbol("m2"));

    // cleanup the driver
    Driver ap13_driver = driverService.findByName("AP13");
    if (ap13_driver != null) {
        driverService.remove(ap13_driver);
    }

    ((NumericQuestion)ap13).setDriver(null);

    JPA.em().persist(ap13);
}



    }
    private void createQuestionAP16() {
        // == AP16
        // Pièces documentaires liées au chauffage

        ap16 = (DocumentQuestion) questionService.findByCode(QuestionCode.AP16);
if (ap16 == null) {
    ap16 = new DocumentQuestion(ap15, 0, QuestionCode.AP16);
    JPA.em().persist(ap16);
} else {
    if (!ap16.getQuestionSet().equals(ap15) && ap15.getQuestions().contains(ap16)) {
        ap15.getQuestions().remove(ap16);
        JPA.em().persist(ap15);
    }
    if (ap16.getQuestionSet().equals(ap15) && !ap15.getQuestions().contains(ap16)) {
        ap15.getQuestions().add(ap16);
        JPA.em().persist(ap15);
    }
    ap16.setOrderIndex(0);
    JPA.em().persist(ap16);
}

    }
    private void createQuestionAP18() {
        // == AP18
        // Source d'énergie

        ap18 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AP18);
if (ap18 == null) {
    ap18 = new ValueSelectionQuestion(ap17, 0, QuestionCode.AP18, CodeList.COMBUSTIBLESIMPLEPETITEMETTEUR);
    JPA.em().persist(ap18);
} else {
    if (!ap18.getQuestionSet().equals(ap17) && ap17.getQuestions().contains(ap18)) {
        ap17.getQuestions().remove(ap18);
        JPA.em().persist(ap17);
    }
    if (ap18.getQuestionSet().equals(ap17) && !ap17.getQuestions().contains(ap18)) {
        ap17.getQuestions().add(ap18);
        JPA.em().persist(ap17);
    }
    ap18.setOrderIndex(0);
    ((ValueSelectionQuestion)ap18).setCodeList(CodeList.COMBUSTIBLESIMPLEPETITEMETTEUR);
    JPA.em().persist(ap18);
}

    }
    private void createQuestionAP19() {
        // == AP19
        // Cette consommation est-elle incluse dans la facture d'éléctricité?

        ap19 = (BooleanQuestion) questionService.findByCode(QuestionCode.AP19);
if (ap19 == null) {
    ap19 = new BooleanQuestion(ap17, 0, QuestionCode.AP19, null);
    JPA.em().persist(ap19);
} else {
    ((BooleanQuestion)ap19).setDefaultValue(null);
    if (!ap19.getQuestionSet().equals(ap17) && ap17.getQuestions().contains(ap19)) {
        ap17.getQuestions().remove(ap19);
        JPA.em().persist(ap17);
    }
    if (ap19.getQuestionSet().equals(ap17) && !ap17.getQuestions().contains(ap19)) {
        ap17.getQuestions().add(ap19);
        JPA.em().persist(ap17);
    }
    ap19.setOrderIndex(0);
    JPA.em().persist(ap19);
}

    }
    private void createQuestionAP20() {
        // == AP20
        // Type de données disponibles

        ap20 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AP20);
if (ap20 == null) {
    ap20 = new ValueSelectionQuestion(ap17, 0, QuestionCode.AP20, CodeList.GESTIONCHAUFFAGEPETITEMETTEUR);
    JPA.em().persist(ap20);
} else {
    if (!ap20.getQuestionSet().equals(ap17) && ap17.getQuestions().contains(ap20)) {
        ap17.getQuestions().remove(ap20);
        JPA.em().persist(ap17);
    }
    if (ap20.getQuestionSet().equals(ap17) && !ap17.getQuestions().contains(ap20)) {
        ap17.getQuestions().add(ap20);
        JPA.em().persist(ap17);
    }
    ap20.setOrderIndex(0);
    ((ValueSelectionQuestion)ap20).setCodeList(CodeList.GESTIONCHAUFFAGEPETITEMETTEUR);
    JPA.em().persist(ap20);
}

    }
    private void createQuestionAP21() {
        // == AP21
        // consommation annuelle

        
ap21 = (DoubleQuestion) questionService.findByCode(QuestionCode.AP21);
if (ap21 == null) {
    ap21 = new DoubleQuestion( ap17, 0, QuestionCode.AP21, volumeUnits, getUnitBySymbol("l") );
    JPA.em().persist(ap21);

    // cleanup the driver
    Driver ap21_driver = driverService.findByName("AP21");
    if (ap21_driver != null) {
        driverService.remove(ap21_driver);
    }

} else {
    if (!ap21.getQuestionSet().equals(ap17) && ap17.getQuestions().contains(ap21)) {
        ap17.getQuestions().remove(ap21);
        JPA.em().persist(ap17);
    }
    if (ap21.getQuestionSet().equals(ap17) && !ap17.getQuestions().contains(ap21)) {
        ap17.getQuestions().add(ap21);
        JPA.em().persist(ap17);
    }
    ((NumericQuestion)ap21).setUnitCategory(volumeUnits);
    ap21.setOrderIndex(0);
    ((NumericQuestion)ap21).setDefaultUnit(getUnitBySymbol("l"));

    // cleanup the driver
    Driver ap21_driver = driverService.findByName("AP21");
    if (ap21_driver != null) {
        driverService.remove(ap21_driver);
    }

    ((NumericQuestion)ap21).setDriver(null);

    JPA.em().persist(ap21);
}



    }
    private void createQuestionAP22() {
        // == AP22
        // consommation annuelle

        
ap22 = (DoubleQuestion) questionService.findByCode(QuestionCode.AP22);
if (ap22 == null) {
    ap22 = new DoubleQuestion( ap17, 0, QuestionCode.AP22, massUnits, getUnitBySymbol("kg") );
    JPA.em().persist(ap22);

    // cleanup the driver
    Driver ap22_driver = driverService.findByName("AP22");
    if (ap22_driver != null) {
        driverService.remove(ap22_driver);
    }

} else {
    if (!ap22.getQuestionSet().equals(ap17) && ap17.getQuestions().contains(ap22)) {
        ap17.getQuestions().remove(ap22);
        JPA.em().persist(ap17);
    }
    if (ap22.getQuestionSet().equals(ap17) && !ap17.getQuestions().contains(ap22)) {
        ap17.getQuestions().add(ap22);
        JPA.em().persist(ap17);
    }
    ((NumericQuestion)ap22).setUnitCategory(massUnits);
    ap22.setOrderIndex(0);
    ((NumericQuestion)ap22).setDefaultUnit(getUnitBySymbol("kg"));


    // cleanup the driver
    Driver ap22_driver = driverService.findByName("AP22");
    if (ap22_driver != null) {
        driverService.remove(ap22_driver);
    }

    ((NumericQuestion)ap22).setDriver(null);

    JPA.em().persist(ap22);
}



    }
    private void createQuestionAP23() {
        // == AP23
        // consommation annuelle

        
ap23 = (DoubleQuestion) questionService.findByCode(QuestionCode.AP23);
if (ap23 == null) {
    ap23 = new DoubleQuestion( ap17, 0, QuestionCode.AP23, energyUnits, getUnitBySymbol("kWh") );
    JPA.em().persist(ap23);

    // cleanup the driver
    Driver ap23_driver = driverService.findByName("AP23");
    if (ap23_driver != null) {
        driverService.remove(ap23_driver);
    }


} else {
    if (!ap23.getQuestionSet().equals(ap17) && ap17.getQuestions().contains(ap23)) {
        ap17.getQuestions().remove(ap23);
        JPA.em().persist(ap17);
    }
    if (ap23.getQuestionSet().equals(ap17) && !ap17.getQuestions().contains(ap23)) {
        ap17.getQuestions().add(ap23);
        JPA.em().persist(ap17);
    }
    ((NumericQuestion)ap23).setUnitCategory(energyUnits);
    ap23.setOrderIndex(0);
    ((NumericQuestion)ap23).setDefaultUnit(getUnitBySymbol("kWh"));

    // cleanup the driver
    Driver ap23_driver = driverService.findByName("AP23");
    if (ap23_driver != null) {
        driverService.remove(ap23_driver);
    }

    ((NumericQuestion)ap23).setDriver(null);

    JPA.em().persist(ap23);
}



    }
    private void createQuestionAP24() {
        // == AP24
        // coût annuel

        
ap24 = (DoubleQuestion) questionService.findByCode(QuestionCode.AP24);
if (ap24 == null) {
    ap24 = new DoubleQuestion( ap17, 0, QuestionCode.AP24, moneyUnits, getUnitBySymbol("€") );
    JPA.em().persist(ap24);

    // cleanup the driver
    Driver ap24_driver = driverService.findByName("AP24");
    if (ap24_driver != null) {
        driverService.remove(ap24_driver);
    }

} else {
    if (!ap24.getQuestionSet().equals(ap17) && ap17.getQuestions().contains(ap24)) {
        ap17.getQuestions().remove(ap24);
        JPA.em().persist(ap17);
    }
    if (ap24.getQuestionSet().equals(ap17) && !ap17.getQuestions().contains(ap24)) {
        ap17.getQuestions().add(ap24);
        JPA.em().persist(ap17);
    }
    ((NumericQuestion)ap24).setUnitCategory(moneyUnits);
    ap24.setOrderIndex(0);
    ((NumericQuestion)ap24).setDefaultUnit(getUnitBySymbol("€"));

    // cleanup the driver
    Driver ap24_driver = driverService.findByName("AP24");
    if (ap24_driver != null) {
        driverService.remove(ap24_driver);
    }

    ((NumericQuestion)ap24).setDriver(null);

    JPA.em().persist(ap24);
}



    }
    private void createQuestionAP25() {
        // == AP25
        // consommation annuelle du bâtiment

        
ap25 = (DoubleQuestion) questionService.findByCode(QuestionCode.AP25);
if (ap25 == null) {
    ap25 = new DoubleQuestion( ap17, 0, QuestionCode.AP25, volumeUnits, getUnitBySymbol("l") );
    JPA.em().persist(ap25);

    // cleanup the driver
    Driver ap25_driver = driverService.findByName("AP25");
    if (ap25_driver != null) {
        driverService.remove(ap25_driver);
    }

} else {
    if (!ap25.getQuestionSet().equals(ap17) && ap17.getQuestions().contains(ap25)) {
        ap17.getQuestions().remove(ap25);
        JPA.em().persist(ap17);
    }
    if (ap25.getQuestionSet().equals(ap17) && !ap17.getQuestions().contains(ap25)) {
        ap17.getQuestions().add(ap25);
        JPA.em().persist(ap17);
    }
    ((NumericQuestion)ap25).setUnitCategory(volumeUnits);
    ap25.setOrderIndex(0);
    ((NumericQuestion)ap25).setDefaultUnit(getUnitBySymbol("l"));

    // cleanup the driver
    Driver ap25_driver = driverService.findByName("AP25");
    if (ap25_driver != null) {
        driverService.remove(ap25_driver);
    }

    ((NumericQuestion)ap25).setDriver(null);

    JPA.em().persist(ap25);
}



    }
    private void createQuestionAP500() {
        // == AP500
        // consommation annuelle du bâtiment

        
ap500 = (DoubleQuestion) questionService.findByCode(QuestionCode.AP500);
if (ap500 == null) {
    ap500 = new DoubleQuestion( ap17, 0, QuestionCode.AP500, massUnits, getUnitBySymbol("kg") );
    JPA.em().persist(ap500);

    // cleanup the driver
    Driver ap500_driver = driverService.findByName("AP500");
    if (ap500_driver != null) {
        driverService.remove(ap500_driver);
    }

} else {
    if (!ap500.getQuestionSet().equals(ap17) && ap17.getQuestions().contains(ap500)) {
        ap17.getQuestions().remove(ap500);
        JPA.em().persist(ap17);
    }
    if (ap500.getQuestionSet().equals(ap17) && !ap17.getQuestions().contains(ap500)) {
        ap17.getQuestions().add(ap500);
        JPA.em().persist(ap17);
    }
    ((NumericQuestion)ap500).setUnitCategory(massUnits);
    ap500.setOrderIndex(0);
    ((NumericQuestion)ap500).setDefaultUnit(getUnitBySymbol("kg"));


    // cleanup the driver
    Driver ap500_driver = driverService.findByName("AP500");
    if (ap500_driver != null) {
        driverService.remove(ap500_driver);
    }

    ((NumericQuestion)ap500).setDriver(null);

    JPA.em().persist(ap500);
}



    }
    private void createQuestionAP501() {
        // == AP501
        // consommation annuelle du bâtiment

        
ap501 = (DoubleQuestion) questionService.findByCode(QuestionCode.AP501);
if (ap501 == null) {
    ap501 = new DoubleQuestion( ap17, 0, QuestionCode.AP501, energyUnits, getUnitBySymbol("kWh") );
    JPA.em().persist(ap501);

    // cleanup the driver
    Driver ap501_driver = driverService.findByName("AP501");
    if (ap501_driver != null) {
        driverService.remove(ap501_driver);
    }


} else {
    if (!ap501.getQuestionSet().equals(ap17) && ap17.getQuestions().contains(ap501)) {
        ap17.getQuestions().remove(ap501);
        JPA.em().persist(ap17);
    }
    if (ap501.getQuestionSet().equals(ap17) && !ap17.getQuestions().contains(ap501)) {
        ap17.getQuestions().add(ap501);
        JPA.em().persist(ap17);
    }
    ((NumericQuestion)ap501).setUnitCategory(energyUnits);
    ap501.setOrderIndex(0);
    ((NumericQuestion)ap501).setDefaultUnit(getUnitBySymbol("kWh"));

    // cleanup the driver
    Driver ap501_driver = driverService.findByName("AP501");
    if (ap501_driver != null) {
        driverService.remove(ap501_driver);
    }

    ((NumericQuestion)ap501).setDriver(null);

    JPA.em().persist(ap501);
}



    }
    private void createQuestionAP26() {
        // == AP26
        // Prix unitaire du combustible (€/l)

        
ap26 = (DoubleQuestion) questionService.findByCode(QuestionCode.AP26);
if (ap26 == null) {
    ap26 = new DoubleQuestion( ap17, 0, QuestionCode.AP26, moneyUnits, getUnitBySymbol("€") );
    JPA.em().persist(ap26);

    // cleanup the driver
    Driver ap26_driver = driverService.findByName("AP26");
    if (ap26_driver != null) {
        driverService.remove(ap26_driver);
    }

    // recreate with good value
    ap26_driver = new Driver("AP26");
    driverService.saveOrUpdate(ap26_driver);
    Period p2000 = periodService.findByCode(PeriodCode.P2000);
    DriverValue dv = new DriverValue(ap26_driver, p2000, Double.valueOf(0.8383));
    ap26_driver.getDriverValueList().add(dv);;
    driverService.saveOrUpdate(ap26_driver);
    JPA.em().persist(dv);

    ((NumericQuestion)ap26).setDriver(ap26_driver);
    JPA.em().persist(ap26);
} else {
    if (!ap26.getQuestionSet().equals(ap17) && ap17.getQuestions().contains(ap26)) {
        ap17.getQuestions().remove(ap26);
        JPA.em().persist(ap17);
    }
    if (ap26.getQuestionSet().equals(ap17) && !ap17.getQuestions().contains(ap26)) {
        ap17.getQuestions().add(ap26);
        JPA.em().persist(ap17);
    }
    ((NumericQuestion)ap26).setUnitCategory(moneyUnits);
    ap26.setOrderIndex(0);
    ((NumericQuestion)ap26).setDefaultUnit(getUnitBySymbol("€"));

    // cleanup the driver
    Driver ap26_driver = driverService.findByName("AP26");
    if (ap26_driver != null) {
        driverService.remove(ap26_driver);
    }

    // recreate with good value
    ap26_driver = new Driver("AP26");
    driverService.saveOrUpdate(ap26_driver);
    Period p2000 = periodService.findByCode(PeriodCode.P2000);
    DriverValue dv = new DriverValue(ap26_driver, p2000, Double.valueOf(0.8383));
    ap26_driver.getDriverValueList().add(dv);;
    driverService.saveOrUpdate(ap26_driver);
    JPA.em().persist(dv);

    ((NumericQuestion)ap26).setDriver(ap26_driver);

    JPA.em().persist(ap26);
}



    }
    private void createQuestionAP27() {
        // == AP27
        // Prix unitaire du combustible (€/m3)

        
ap27 = (DoubleQuestion) questionService.findByCode(QuestionCode.AP27);
if (ap27 == null) {
    ap27 = new DoubleQuestion( ap17, 0, QuestionCode.AP27, moneyUnits, getUnitBySymbol("€") );
    JPA.em().persist(ap27);

    // cleanup the driver
    Driver ap27_driver = driverService.findByName("AP27");
    if (ap27_driver != null) {
        driverService.remove(ap27_driver);
    }

    // recreate with good value
    ap27_driver = new Driver("AP27");
    driverService.saveOrUpdate(ap27_driver);
    Period p2000 = periodService.findByCode(PeriodCode.P2000);
    DriverValue dv = new DriverValue(ap27_driver, p2000, Double.valueOf(0.625));
    ap27_driver.getDriverValueList().add(dv);;
    driverService.saveOrUpdate(ap27_driver);
    JPA.em().persist(dv);

    ((NumericQuestion)ap27).setDriver(ap27_driver);
    JPA.em().persist(ap27);
} else {
    if (!ap27.getQuestionSet().equals(ap17) && ap17.getQuestions().contains(ap27)) {
        ap17.getQuestions().remove(ap27);
        JPA.em().persist(ap17);
    }
    if (ap27.getQuestionSet().equals(ap17) && !ap17.getQuestions().contains(ap27)) {
        ap17.getQuestions().add(ap27);
        JPA.em().persist(ap17);
    }
    ((NumericQuestion)ap27).setUnitCategory(moneyUnits);
    ap27.setOrderIndex(0);
    ((NumericQuestion)ap27).setDefaultUnit(getUnitBySymbol("€"));

    // cleanup the driver
    Driver ap27_driver = driverService.findByName("AP27");
    if (ap27_driver != null) {
        driverService.remove(ap27_driver);
    }

    // recreate with good value
    ap27_driver = new Driver("AP27");
    driverService.saveOrUpdate(ap27_driver);
    Period p2000 = periodService.findByCode(PeriodCode.P2000);
    DriverValue dv = new DriverValue(ap27_driver, p2000, Double.valueOf(0.625));
    ap27_driver.getDriverValueList().add(dv);;
    driverService.saveOrUpdate(ap27_driver);
    JPA.em().persist(dv);

    ((NumericQuestion)ap27).setDriver(ap27_driver);

    JPA.em().persist(ap27);
}



    }
    private void createQuestionAP28() {
        // == AP28
        // Prix unitaire du combustible (€/kg)

        
ap28 = (DoubleQuestion) questionService.findByCode(QuestionCode.AP28);
if (ap28 == null) {
    ap28 = new DoubleQuestion( ap17, 0, QuestionCode.AP28, moneyUnits, getUnitBySymbol("€") );
    JPA.em().persist(ap28);

    // cleanup the driver
    Driver ap28_driver = driverService.findByName("AP28");
    if (ap28_driver != null) {
        driverService.remove(ap28_driver);
    }

    // recreate with good value
    ap28_driver = new Driver("AP28");
    driverService.saveOrUpdate(ap28_driver);
    Period p2000 = periodService.findByCode(PeriodCode.P2000);
    DriverValue dv = new DriverValue(ap28_driver, p2000, Double.valueOf(0.27));
    ap28_driver.getDriverValueList().add(dv);;
    driverService.saveOrUpdate(ap28_driver);
    JPA.em().persist(dv);

    ((NumericQuestion)ap28).setDriver(ap28_driver);
    JPA.em().persist(ap28);
} else {
    if (!ap28.getQuestionSet().equals(ap17) && ap17.getQuestions().contains(ap28)) {
        ap17.getQuestions().remove(ap28);
        JPA.em().persist(ap17);
    }
    if (ap28.getQuestionSet().equals(ap17) && !ap17.getQuestions().contains(ap28)) {
        ap17.getQuestions().add(ap28);
        JPA.em().persist(ap17);
    }
    ((NumericQuestion)ap28).setUnitCategory(moneyUnits);
    ap28.setOrderIndex(0);
    ((NumericQuestion)ap28).setDefaultUnit(getUnitBySymbol("€"));

    // cleanup the driver
    Driver ap28_driver = driverService.findByName("AP28");
    if (ap28_driver != null) {
        driverService.remove(ap28_driver);
    }

    // recreate with good value
    ap28_driver = new Driver("AP28");
    driverService.saveOrUpdate(ap28_driver);
    Period p2000 = periodService.findByCode(PeriodCode.P2000);
    DriverValue dv = new DriverValue(ap28_driver, p2000, Double.valueOf(0.27));
    ap28_driver.getDriverValueList().add(dv);;
    driverService.saveOrUpdate(ap28_driver);
    JPA.em().persist(dv);

    ((NumericQuestion)ap28).setDriver(ap28_driver);

    JPA.em().persist(ap28);
}



    }
    private void createQuestionAP29() {
        // == AP29
        // Prix unitaire du combustible (€/kg)

        
ap29 = (DoubleQuestion) questionService.findByCode(QuestionCode.AP29);
if (ap29 == null) {
    ap29 = new DoubleQuestion( ap17, 0, QuestionCode.AP29, moneyUnits, getUnitBySymbol("€") );
    JPA.em().persist(ap29);

    // cleanup the driver
    Driver ap29_driver = driverService.findByName("AP29");
    if (ap29_driver != null) {
        driverService.remove(ap29_driver);
    }

    // recreate with good value
    ap29_driver = new Driver("AP29");
    driverService.saveOrUpdate(ap29_driver);
    Period p2000 = periodService.findByCode(PeriodCode.P2000);
    DriverValue dv = new DriverValue(ap29_driver, p2000, Double.valueOf(0.35));
    ap29_driver.getDriverValueList().add(dv);;
    driverService.saveOrUpdate(ap29_driver);
    JPA.em().persist(dv);

    ((NumericQuestion)ap29).setDriver(ap29_driver);
    JPA.em().persist(ap29);
} else {
    if (!ap29.getQuestionSet().equals(ap17) && ap17.getQuestions().contains(ap29)) {
        ap17.getQuestions().remove(ap29);
        JPA.em().persist(ap17);
    }
    if (ap29.getQuestionSet().equals(ap17) && !ap17.getQuestions().contains(ap29)) {
        ap17.getQuestions().add(ap29);
        JPA.em().persist(ap17);
    }
    ((NumericQuestion)ap29).setUnitCategory(moneyUnits);
    ap29.setOrderIndex(0);
    ((NumericQuestion)ap29).setDefaultUnit(getUnitBySymbol("€"));

    // cleanup the driver
    Driver ap29_driver = driverService.findByName("AP29");
    if (ap29_driver != null) {
        driverService.remove(ap29_driver);
    }

    // recreate with good value
    ap29_driver = new Driver("AP29");
    driverService.saveOrUpdate(ap29_driver);
    Period p2000 = periodService.findByCode(PeriodCode.P2000);
    DriverValue dv = new DriverValue(ap29_driver, p2000, Double.valueOf(0.35));
    ap29_driver.getDriverValueList().add(dv);;
    driverService.saveOrUpdate(ap29_driver);
    JPA.em().persist(dv);

    ((NumericQuestion)ap29).setDriver(ap29_driver);

    JPA.em().persist(ap29);
}



    }
    private void createQuestionAP30() {
        // == AP30
        // Prix unitaire de l'électricité (au kWh)

        
ap30 = (DoubleQuestion) questionService.findByCode(QuestionCode.AP30);
if (ap30 == null) {
    ap30 = new DoubleQuestion( ap17, 0, QuestionCode.AP30, moneyUnits, getUnitBySymbol("€") );
    JPA.em().persist(ap30);

    // cleanup the driver
    Driver ap30_driver = driverService.findByName("AP30");
    if (ap30_driver != null) {
        driverService.remove(ap30_driver);
    }

    // recreate with good value
    ap30_driver = new Driver("AP30");
    driverService.saveOrUpdate(ap30_driver);
    Period p2000 = periodService.findByCode(PeriodCode.P2000);
    DriverValue dv = new DriverValue(ap30_driver, p2000, Double.valueOf(0.03));
    ap30_driver.getDriverValueList().add(dv);;
    driverService.saveOrUpdate(ap30_driver);
    JPA.em().persist(dv);

    ((NumericQuestion)ap30).setDriver(ap30_driver);
    JPA.em().persist(ap30);
} else {
    if (!ap30.getQuestionSet().equals(ap17) && ap17.getQuestions().contains(ap30)) {
        ap17.getQuestions().remove(ap30);
        JPA.em().persist(ap17);
    }
    if (ap30.getQuestionSet().equals(ap17) && !ap17.getQuestions().contains(ap30)) {
        ap17.getQuestions().add(ap30);
        JPA.em().persist(ap17);
    }
    ((NumericQuestion)ap30).setUnitCategory(moneyUnits);
    ap30.setOrderIndex(0);
    ((NumericQuestion)ap30).setDefaultUnit(getUnitBySymbol("€"));

    // cleanup the driver
    Driver ap30_driver = driverService.findByName("AP30");
    if (ap30_driver != null) {
        driverService.remove(ap30_driver);
    }

    // recreate with good value
    ap30_driver = new Driver("AP30");
    driverService.saveOrUpdate(ap30_driver);
    Period p2000 = periodService.findByCode(PeriodCode.P2000);
    DriverValue dv = new DriverValue(ap30_driver, p2000, Double.valueOf(0.03));
    ap30_driver.getDriverValueList().add(dv);;
    driverService.saveOrUpdate(ap30_driver);
    JPA.em().persist(dv);

    ((NumericQuestion)ap30).setDriver(ap30_driver);

    JPA.em().persist(ap30);
}



    }
    private void createQuestionAP31() {
        // == AP31
        // Votre consommation estimée

        
ap31 = (DoubleQuestion) questionService.findByCode(QuestionCode.AP31);
if (ap31 == null) {
    ap31 = new DoubleQuestion( ap17, 0, QuestionCode.AP31, volumeUnits, getUnitBySymbol("l") );
    JPA.em().persist(ap31);

    // cleanup the driver
    Driver ap31_driver = driverService.findByName("AP31");
    if (ap31_driver != null) {
        driverService.remove(ap31_driver);
    }

} else {
    if (!ap31.getQuestionSet().equals(ap17) && ap17.getQuestions().contains(ap31)) {
        ap17.getQuestions().remove(ap31);
        JPA.em().persist(ap17);
    }
    if (ap31.getQuestionSet().equals(ap17) && !ap17.getQuestions().contains(ap31)) {
        ap17.getQuestions().add(ap31);
        JPA.em().persist(ap17);
    }
    ((NumericQuestion)ap31).setUnitCategory(volumeUnits);
    ap31.setOrderIndex(0);
    ((NumericQuestion)ap31).setDefaultUnit(getUnitBySymbol("l"));

    // cleanup the driver
    Driver ap31_driver = driverService.findByName("AP31");
    if (ap31_driver != null) {
        driverService.remove(ap31_driver);
    }

    ((NumericQuestion)ap31).setDriver(null);

    JPA.em().persist(ap31);
}



    }
    private void createQuestionAP32() {
        // == AP32
        // Votre consommation estimée

        
ap32 = (DoubleQuestion) questionService.findByCode(QuestionCode.AP32);
if (ap32 == null) {
    ap32 = new DoubleQuestion( ap17, 0, QuestionCode.AP32, volumeUnits, getUnitBySymbol("l") );
    JPA.em().persist(ap32);

    // cleanup the driver
    Driver ap32_driver = driverService.findByName("AP32");
    if (ap32_driver != null) {
        driverService.remove(ap32_driver);
    }

} else {
    if (!ap32.getQuestionSet().equals(ap17) && ap17.getQuestions().contains(ap32)) {
        ap17.getQuestions().remove(ap32);
        JPA.em().persist(ap17);
    }
    if (ap32.getQuestionSet().equals(ap17) && !ap17.getQuestions().contains(ap32)) {
        ap17.getQuestions().add(ap32);
        JPA.em().persist(ap17);
    }
    ((NumericQuestion)ap32).setUnitCategory(volumeUnits);
    ap32.setOrderIndex(0);
    ((NumericQuestion)ap32).setDefaultUnit(getUnitBySymbol("l"));

    // cleanup the driver
    Driver ap32_driver = driverService.findByName("AP32");
    if (ap32_driver != null) {
        driverService.remove(ap32_driver);
    }

    ((NumericQuestion)ap32).setDriver(null);

    JPA.em().persist(ap32);
}



    }
    private void createQuestionAP33() {
        // == AP33
        // Votre consommation estimée

        
ap33 = (DoubleQuestion) questionService.findByCode(QuestionCode.AP33);
if (ap33 == null) {
    ap33 = new DoubleQuestion( ap17, 0, QuestionCode.AP33, massUnits, getUnitBySymbol("kg") );
    JPA.em().persist(ap33);

    // cleanup the driver
    Driver ap33_driver = driverService.findByName("AP33");
    if (ap33_driver != null) {
        driverService.remove(ap33_driver);
    }

} else {
    if (!ap33.getQuestionSet().equals(ap17) && ap17.getQuestions().contains(ap33)) {
        ap17.getQuestions().remove(ap33);
        JPA.em().persist(ap17);
    }
    if (ap33.getQuestionSet().equals(ap17) && !ap17.getQuestions().contains(ap33)) {
        ap17.getQuestions().add(ap33);
        JPA.em().persist(ap17);
    }
    ((NumericQuestion)ap33).setUnitCategory(massUnits);
    ap33.setOrderIndex(0);
    ((NumericQuestion)ap33).setDefaultUnit(getUnitBySymbol("kg"));


    // cleanup the driver
    Driver ap33_driver = driverService.findByName("AP33");
    if (ap33_driver != null) {
        driverService.remove(ap33_driver);
    }

    ((NumericQuestion)ap33).setDriver(null);

    JPA.em().persist(ap33);
}



    }
    private void createQuestionAP34() {
        // == AP34
        // Votre consommation estimée

        
ap34 = (DoubleQuestion) questionService.findByCode(QuestionCode.AP34);
if (ap34 == null) {
    ap34 = new DoubleQuestion( ap17, 0, QuestionCode.AP34, massUnits, getUnitBySymbol("kg") );
    JPA.em().persist(ap34);

    // cleanup the driver
    Driver ap34_driver = driverService.findByName("AP34");
    if (ap34_driver != null) {
        driverService.remove(ap34_driver);
    }

} else {
    if (!ap34.getQuestionSet().equals(ap17) && ap17.getQuestions().contains(ap34)) {
        ap17.getQuestions().remove(ap34);
        JPA.em().persist(ap17);
    }
    if (ap34.getQuestionSet().equals(ap17) && !ap17.getQuestions().contains(ap34)) {
        ap17.getQuestions().add(ap34);
        JPA.em().persist(ap17);
    }
    ((NumericQuestion)ap34).setUnitCategory(massUnits);
    ap34.setOrderIndex(0);
    ((NumericQuestion)ap34).setDefaultUnit(getUnitBySymbol("kg"));


    // cleanup the driver
    Driver ap34_driver = driverService.findByName("AP34");
    if (ap34_driver != null) {
        driverService.remove(ap34_driver);
    }

    ((NumericQuestion)ap34).setDriver(null);

    JPA.em().persist(ap34);
}



    }
    private void createQuestionAP35() {
        // == AP35
        // Votre consommation estimée

        
ap35 = (DoubleQuestion) questionService.findByCode(QuestionCode.AP35);
if (ap35 == null) {
    ap35 = new DoubleQuestion( ap17, 0, QuestionCode.AP35, energyUnits, getUnitBySymbol("kWh") );
    JPA.em().persist(ap35);

    // cleanup the driver
    Driver ap35_driver = driverService.findByName("AP35");
    if (ap35_driver != null) {
        driverService.remove(ap35_driver);
    }


} else {
    if (!ap35.getQuestionSet().equals(ap17) && ap17.getQuestions().contains(ap35)) {
        ap17.getQuestions().remove(ap35);
        JPA.em().persist(ap17);
    }
    if (ap35.getQuestionSet().equals(ap17) && !ap17.getQuestions().contains(ap35)) {
        ap17.getQuestions().add(ap35);
        JPA.em().persist(ap17);
    }
    ((NumericQuestion)ap35).setUnitCategory(energyUnits);
    ap35.setOrderIndex(0);
    ((NumericQuestion)ap35).setDefaultUnit(getUnitBySymbol("kWh"));

    // cleanup the driver
    Driver ap35_driver = driverService.findByName("AP35");
    if (ap35_driver != null) {
        driverService.remove(ap35_driver);
    }

    ((NumericQuestion)ap35).setDriver(null);

    JPA.em().persist(ap35);
}



    }
    private void createQuestionAP36() {
        // == AP36
        // Votre consommation estimée

        
ap36 = (DoubleQuestion) questionService.findByCode(QuestionCode.AP36);
if (ap36 == null) {
    ap36 = new DoubleQuestion( ap17, 0, QuestionCode.AP36, volumeUnits, getUnitBySymbol("l") );
    JPA.em().persist(ap36);

    // cleanup the driver
    Driver ap36_driver = driverService.findByName("AP36");
    if (ap36_driver != null) {
        driverService.remove(ap36_driver);
    }

} else {
    if (!ap36.getQuestionSet().equals(ap17) && ap17.getQuestions().contains(ap36)) {
        ap17.getQuestions().remove(ap36);
        JPA.em().persist(ap17);
    }
    if (ap36.getQuestionSet().equals(ap17) && !ap17.getQuestions().contains(ap36)) {
        ap17.getQuestions().add(ap36);
        JPA.em().persist(ap17);
    }
    ((NumericQuestion)ap36).setUnitCategory(volumeUnits);
    ap36.setOrderIndex(0);
    ((NumericQuestion)ap36).setDefaultUnit(getUnitBySymbol("l"));

    // cleanup the driver
    Driver ap36_driver = driverService.findByName("AP36");
    if (ap36_driver != null) {
        driverService.remove(ap36_driver);
    }

    ((NumericQuestion)ap36).setDriver(null);

    JPA.em().persist(ap36);
}



    }
    private void createQuestionAP37() {
        // == AP37
        // Votre consommation estimée

        
ap37 = (DoubleQuestion) questionService.findByCode(QuestionCode.AP37);
if (ap37 == null) {
    ap37 = new DoubleQuestion( ap17, 0, QuestionCode.AP37, massUnits, getUnitBySymbol("kg") );
    JPA.em().persist(ap37);

    // cleanup the driver
    Driver ap37_driver = driverService.findByName("AP37");
    if (ap37_driver != null) {
        driverService.remove(ap37_driver);
    }

} else {
    if (!ap37.getQuestionSet().equals(ap17) && ap17.getQuestions().contains(ap37)) {
        ap17.getQuestions().remove(ap37);
        JPA.em().persist(ap17);
    }
    if (ap37.getQuestionSet().equals(ap17) && !ap17.getQuestions().contains(ap37)) {
        ap17.getQuestions().add(ap37);
        JPA.em().persist(ap17);
    }
    ((NumericQuestion)ap37).setUnitCategory(massUnits);
    ap37.setOrderIndex(0);
    ((NumericQuestion)ap37).setDefaultUnit(getUnitBySymbol("kg"));


    // cleanup the driver
    Driver ap37_driver = driverService.findByName("AP37");
    if (ap37_driver != null) {
        driverService.remove(ap37_driver);
    }

    ((NumericQuestion)ap37).setDriver(null);

    JPA.em().persist(ap37);
}



    }
    private void createQuestionAP38() {
        // == AP38
        // Votre consommation estimée

        
ap38 = (DoubleQuestion) questionService.findByCode(QuestionCode.AP38);
if (ap38 == null) {
    ap38 = new DoubleQuestion( ap17, 0, QuestionCode.AP38, energyUnits, getUnitBySymbol("kWh") );
    JPA.em().persist(ap38);

    // cleanup the driver
    Driver ap38_driver = driverService.findByName("AP38");
    if (ap38_driver != null) {
        driverService.remove(ap38_driver);
    }


} else {
    if (!ap38.getQuestionSet().equals(ap17) && ap17.getQuestions().contains(ap38)) {
        ap17.getQuestions().remove(ap38);
        JPA.em().persist(ap17);
    }
    if (ap38.getQuestionSet().equals(ap17) && !ap17.getQuestions().contains(ap38)) {
        ap17.getQuestions().add(ap38);
        JPA.em().persist(ap17);
    }
    ((NumericQuestion)ap38).setUnitCategory(energyUnits);
    ap38.setOrderIndex(0);
    ((NumericQuestion)ap38).setDefaultUnit(getUnitBySymbol("kWh"));

    // cleanup the driver
    Driver ap38_driver = driverService.findByName("AP38");
    if (ap38_driver != null) {
        driverService.remove(ap38_driver);
    }

    ((NumericQuestion)ap38).setDriver(null);

    JPA.em().persist(ap38);
}



    }
    private void createQuestionAP42() {
        // == AP42
        // Pièces documentaires liées à l'électricité

        ap42 = (DocumentQuestion) questionService.findByCode(QuestionCode.AP42);
if (ap42 == null) {
    ap42 = new DocumentQuestion(ap41, 0, QuestionCode.AP42);
    JPA.em().persist(ap42);
} else {
    if (!ap42.getQuestionSet().equals(ap41) && ap41.getQuestions().contains(ap42)) {
        ap41.getQuestions().remove(ap42);
        JPA.em().persist(ap41);
    }
    if (ap42.getQuestionSet().equals(ap41) && !ap41.getQuestions().contains(ap42)) {
        ap41.getQuestions().add(ap42);
        JPA.em().persist(ap41);
    }
    ap42.setOrderIndex(0);
    JPA.em().persist(ap42);
}

    }
    private void createQuestionAP43() {
        // == AP43
        // Type d'éléctricité

        ap43 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AP43);
if (ap43 == null) {
    ap43 = new ValueSelectionQuestion(ap41, 0, QuestionCode.AP43, CodeList.TYPEELECTRICITE);
    JPA.em().persist(ap43);
} else {
    if (!ap43.getQuestionSet().equals(ap41) && ap41.getQuestions().contains(ap43)) {
        ap41.getQuestions().remove(ap43);
        JPA.em().persist(ap41);
    }
    if (ap43.getQuestionSet().equals(ap41) && !ap41.getQuestions().contains(ap43)) {
        ap41.getQuestions().add(ap43);
        JPA.em().persist(ap41);
    }
    ap43.setOrderIndex(0);
    ((ValueSelectionQuestion)ap43).setCodeList(CodeList.TYPEELECTRICITE);
    JPA.em().persist(ap43);
}

    }
    private void createQuestionAP44() {
        // == AP44
        // Type de données disponibles

        ap44 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AP44);
if (ap44 == null) {
    ap44 = new ValueSelectionQuestion(ap41, 0, QuestionCode.AP44, CodeList.GESTIONCHAUFFAGEPETITEMETTEUR);
    JPA.em().persist(ap44);
} else {
    if (!ap44.getQuestionSet().equals(ap41) && ap41.getQuestions().contains(ap44)) {
        ap41.getQuestions().remove(ap44);
        JPA.em().persist(ap41);
    }
    if (ap44.getQuestionSet().equals(ap41) && !ap41.getQuestions().contains(ap44)) {
        ap41.getQuestions().add(ap44);
        JPA.em().persist(ap41);
    }
    ap44.setOrderIndex(0);
    ((ValueSelectionQuestion)ap44).setCodeList(CodeList.GESTIONCHAUFFAGEPETITEMETTEUR);
    JPA.em().persist(ap44);
}

    }
    private void createQuestionAP45() {
        // == AP45
        // consommation annuelle

        
ap45 = (DoubleQuestion) questionService.findByCode(QuestionCode.AP45);
if (ap45 == null) {
    ap45 = new DoubleQuestion( ap41, 0, QuestionCode.AP45, energyUnits, getUnitBySymbol("kWh") );
    JPA.em().persist(ap45);

    // cleanup the driver
    Driver ap45_driver = driverService.findByName("AP45");
    if (ap45_driver != null) {
        driverService.remove(ap45_driver);
    }


} else {
    if (!ap45.getQuestionSet().equals(ap41) && ap41.getQuestions().contains(ap45)) {
        ap41.getQuestions().remove(ap45);
        JPA.em().persist(ap41);
    }
    if (ap45.getQuestionSet().equals(ap41) && !ap41.getQuestions().contains(ap45)) {
        ap41.getQuestions().add(ap45);
        JPA.em().persist(ap41);
    }
    ((NumericQuestion)ap45).setUnitCategory(energyUnits);
    ap45.setOrderIndex(0);
    ((NumericQuestion)ap45).setDefaultUnit(getUnitBySymbol("kWh"));

    // cleanup the driver
    Driver ap45_driver = driverService.findByName("AP45");
    if (ap45_driver != null) {
        driverService.remove(ap45_driver);
    }

    ((NumericQuestion)ap45).setDriver(null);

    JPA.em().persist(ap45);
}



    }
    private void createQuestionAP46() {
        // == AP46
        // coût annuel

        
ap46 = (DoubleQuestion) questionService.findByCode(QuestionCode.AP46);
if (ap46 == null) {
    ap46 = new DoubleQuestion( ap41, 0, QuestionCode.AP46, moneyUnits, getUnitBySymbol("€") );
    JPA.em().persist(ap46);

    // cleanup the driver
    Driver ap46_driver = driverService.findByName("AP46");
    if (ap46_driver != null) {
        driverService.remove(ap46_driver);
    }

} else {
    if (!ap46.getQuestionSet().equals(ap41) && ap41.getQuestions().contains(ap46)) {
        ap41.getQuestions().remove(ap46);
        JPA.em().persist(ap41);
    }
    if (ap46.getQuestionSet().equals(ap41) && !ap41.getQuestions().contains(ap46)) {
        ap41.getQuestions().add(ap46);
        JPA.em().persist(ap41);
    }
    ((NumericQuestion)ap46).setUnitCategory(moneyUnits);
    ap46.setOrderIndex(0);
    ((NumericQuestion)ap46).setDefaultUnit(getUnitBySymbol("€"));

    // cleanup the driver
    Driver ap46_driver = driverService.findByName("AP46");
    if (ap46_driver != null) {
        driverService.remove(ap46_driver);
    }

    ((NumericQuestion)ap46).setDriver(null);

    JPA.em().persist(ap46);
}



    }
    private void createQuestionAP47() {
        // == AP47
        // Prix unitaire de l'électricité (au kWh)

        
ap47 = (DoubleQuestion) questionService.findByCode(QuestionCode.AP47);
if (ap47 == null) {
    ap47 = new DoubleQuestion( ap41, 0, QuestionCode.AP47, moneyUnits, getUnitBySymbol("€") );
    JPA.em().persist(ap47);

    // cleanup the driver
    Driver ap47_driver = driverService.findByName("AP47");
    if (ap47_driver != null) {
        driverService.remove(ap47_driver);
    }

    // recreate with good value
    ap47_driver = new Driver("AP47");
    driverService.saveOrUpdate(ap47_driver);
    Period p2000 = periodService.findByCode(PeriodCode.P2000);
    DriverValue dv = new DriverValue(ap47_driver, p2000, Double.valueOf(0.027));
    ap47_driver.getDriverValueList().add(dv);;
    driverService.saveOrUpdate(ap47_driver);
    JPA.em().persist(dv);

    ((NumericQuestion)ap47).setDriver(ap47_driver);
    JPA.em().persist(ap47);
} else {
    if (!ap47.getQuestionSet().equals(ap41) && ap41.getQuestions().contains(ap47)) {
        ap41.getQuestions().remove(ap47);
        JPA.em().persist(ap41);
    }
    if (ap47.getQuestionSet().equals(ap41) && !ap41.getQuestions().contains(ap47)) {
        ap41.getQuestions().add(ap47);
        JPA.em().persist(ap41);
    }
    ((NumericQuestion)ap47).setUnitCategory(moneyUnits);
    ap47.setOrderIndex(0);
    ((NumericQuestion)ap47).setDefaultUnit(getUnitBySymbol("€"));

    // cleanup the driver
    Driver ap47_driver = driverService.findByName("AP47");
    if (ap47_driver != null) {
        driverService.remove(ap47_driver);
    }

    // recreate with good value
    ap47_driver = new Driver("AP47");
    driverService.saveOrUpdate(ap47_driver);
    Period p2000 = periodService.findByCode(PeriodCode.P2000);
    DriverValue dv = new DriverValue(ap47_driver, p2000, Double.valueOf(0.027));
    ap47_driver.getDriverValueList().add(dv);;
    driverService.saveOrUpdate(ap47_driver);
    JPA.em().persist(dv);

    ((NumericQuestion)ap47).setDriver(ap47_driver);

    JPA.em().persist(ap47);
}



    }
    private void createQuestionAP48() {
        // == AP48
        // Votre consommation estimée 

        
ap48 = (DoubleQuestion) questionService.findByCode(QuestionCode.AP48);
if (ap48 == null) {
    ap48 = new DoubleQuestion( ap41, 0, QuestionCode.AP48, energyUnits, getUnitBySymbol("kWh") );
    JPA.em().persist(ap48);

    // cleanup the driver
    Driver ap48_driver = driverService.findByName("AP48");
    if (ap48_driver != null) {
        driverService.remove(ap48_driver);
    }


} else {
    if (!ap48.getQuestionSet().equals(ap41) && ap41.getQuestions().contains(ap48)) {
        ap41.getQuestions().remove(ap48);
        JPA.em().persist(ap41);
    }
    if (ap48.getQuestionSet().equals(ap41) && !ap41.getQuestions().contains(ap48)) {
        ap41.getQuestions().add(ap48);
        JPA.em().persist(ap41);
    }
    ((NumericQuestion)ap48).setUnitCategory(energyUnits);
    ap48.setOrderIndex(0);
    ((NumericQuestion)ap48).setDefaultUnit(getUnitBySymbol("kWh"));

    // cleanup the driver
    Driver ap48_driver = driverService.findByName("AP48");
    if (ap48_driver != null) {
        driverService.remove(ap48_driver);
    }

    ((NumericQuestion)ap48).setDriver(null);

    JPA.em().persist(ap48);
}



    }
    private void createQuestionAP49() {
        // == AP49
        // consommation annuelle du bâtiment

        
ap49 = (DoubleQuestion) questionService.findByCode(QuestionCode.AP49);
if (ap49 == null) {
    ap49 = new DoubleQuestion( ap41, 0, QuestionCode.AP49, energyUnits, getUnitBySymbol("kWh") );
    JPA.em().persist(ap49);

    // cleanup the driver
    Driver ap49_driver = driverService.findByName("AP49");
    if (ap49_driver != null) {
        driverService.remove(ap49_driver);
    }


} else {
    if (!ap49.getQuestionSet().equals(ap41) && ap41.getQuestions().contains(ap49)) {
        ap41.getQuestions().remove(ap49);
        JPA.em().persist(ap41);
    }
    if (ap49.getQuestionSet().equals(ap41) && !ap41.getQuestions().contains(ap49)) {
        ap41.getQuestions().add(ap49);
        JPA.em().persist(ap41);
    }
    ((NumericQuestion)ap49).setUnitCategory(energyUnits);
    ap49.setOrderIndex(0);
    ((NumericQuestion)ap49).setDefaultUnit(getUnitBySymbol("kWh"));

    // cleanup the driver
    Driver ap49_driver = driverService.findByName("AP49");
    if (ap49_driver != null) {
        driverService.remove(ap49_driver);
    }

    ((NumericQuestion)ap49).setDriver(null);

    JPA.em().persist(ap49);
}



    }
    private void createQuestionAP50() {
        // == AP50
        // Votre consommation estimée

        
ap50 = (DoubleQuestion) questionService.findByCode(QuestionCode.AP50);
if (ap50 == null) {
    ap50 = new DoubleQuestion( ap41, 0, QuestionCode.AP50, energyUnits, getUnitBySymbol("kWh") );
    JPA.em().persist(ap50);

    // cleanup the driver
    Driver ap50_driver = driverService.findByName("AP50");
    if (ap50_driver != null) {
        driverService.remove(ap50_driver);
    }


} else {
    if (!ap50.getQuestionSet().equals(ap41) && ap41.getQuestions().contains(ap50)) {
        ap41.getQuestions().remove(ap50);
        JPA.em().persist(ap41);
    }
    if (ap50.getQuestionSet().equals(ap41) && !ap41.getQuestions().contains(ap50)) {
        ap41.getQuestions().add(ap50);
        JPA.em().persist(ap41);
    }
    ((NumericQuestion)ap50).setUnitCategory(energyUnits);
    ap50.setOrderIndex(0);
    ((NumericQuestion)ap50).setDefaultUnit(getUnitBySymbol("kWh"));

    // cleanup the driver
    Driver ap50_driver = driverService.findByName("AP50");
    if (ap50_driver != null) {
        driverService.remove(ap50_driver);
    }

    ((NumericQuestion)ap50).setDriver(null);

    JPA.em().persist(ap50);
}



    }
    private void createQuestionAP52() {
        // == AP52
        // Pièces documentaires liées à la réfrigération

        ap52 = (DocumentQuestion) questionService.findByCode(QuestionCode.AP52);
if (ap52 == null) {
    ap52 = new DocumentQuestion(ap51, 0, QuestionCode.AP52);
    JPA.em().persist(ap52);
} else {
    if (!ap52.getQuestionSet().equals(ap51) && ap51.getQuestions().contains(ap52)) {
        ap51.getQuestions().remove(ap52);
        JPA.em().persist(ap51);
    }
    if (ap52.getQuestionSet().equals(ap51) && !ap51.getQuestions().contains(ap52)) {
        ap51.getQuestions().add(ap52);
        JPA.em().persist(ap51);
    }
    ap52.setOrderIndex(0);
    JPA.em().persist(ap52);
}

    }
    private void createQuestionAP54() {
        // == AP54
        // Gaz réfrigérant que vous utilisez

        ap54 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AP54);
if (ap54 == null) {
    ap54 = new ValueSelectionQuestion(ap53, 0, QuestionCode.AP54, CodeList.FRIGO_PETITEMETTEUR);
    JPA.em().persist(ap54);
} else {
    if (!ap54.getQuestionSet().equals(ap53) && ap53.getQuestions().contains(ap54)) {
        ap53.getQuestions().remove(ap54);
        JPA.em().persist(ap53);
    }
    if (ap54.getQuestionSet().equals(ap53) && !ap53.getQuestions().contains(ap54)) {
        ap53.getQuestions().add(ap54);
        JPA.em().persist(ap53);
    }
    ap54.setOrderIndex(0);
    ((ValueSelectionQuestion)ap54).setCodeList(CodeList.FRIGO_PETITEMETTEUR);
    JPA.em().persist(ap54);
}

    }
    private void createQuestionAP55() {
        // == AP55
        // Quantité de recharge annuelle

        
ap55 = (DoubleQuestion) questionService.findByCode(QuestionCode.AP55);
if (ap55 == null) {
    ap55 = new DoubleQuestion( ap53, 0, QuestionCode.AP55, massUnits, getUnitBySymbol("kg") );
    JPA.em().persist(ap55);

    // cleanup the driver
    Driver ap55_driver = driverService.findByName("AP55");
    if (ap55_driver != null) {
        driverService.remove(ap55_driver);
    }

} else {
    if (!ap55.getQuestionSet().equals(ap53) && ap53.getQuestions().contains(ap55)) {
        ap53.getQuestions().remove(ap55);
        JPA.em().persist(ap53);
    }
    if (ap55.getQuestionSet().equals(ap53) && !ap53.getQuestions().contains(ap55)) {
        ap53.getQuestions().add(ap55);
        JPA.em().persist(ap53);
    }
    ((NumericQuestion)ap55).setUnitCategory(massUnits);
    ap55.setOrderIndex(0);
    ((NumericQuestion)ap55).setDefaultUnit(getUnitBySymbol("kg"));


    // cleanup the driver
    Driver ap55_driver = driverService.findByName("AP55");
    if (ap55_driver != null) {
        driverService.remove(ap55_driver);
    }

    ((NumericQuestion)ap55).setDriver(null);

    JPA.em().persist(ap55);
}



    }
    private void createQuestionAP57() {
        // == AP57
        // Pièces documentaires liées à la climatisation

        ap57 = (DocumentQuestion) questionService.findByCode(QuestionCode.AP57);
if (ap57 == null) {
    ap57 = new DocumentQuestion(ap56, 0, QuestionCode.AP57);
    JPA.em().persist(ap57);
} else {
    if (!ap57.getQuestionSet().equals(ap56) && ap56.getQuestions().contains(ap57)) {
        ap56.getQuestions().remove(ap57);
        JPA.em().persist(ap56);
    }
    if (ap57.getQuestionSet().equals(ap56) && !ap56.getQuestions().contains(ap57)) {
        ap56.getQuestions().add(ap57);
        JPA.em().persist(ap56);
    }
    ap57.setOrderIndex(0);
    JPA.em().persist(ap57);
}

    }
    private void createQuestionAP59() {
        // == AP59
        // Gaz réfrigérant que vous utilisez

        ap59 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AP59);
if (ap59 == null) {
    ap59 = new ValueSelectionQuestion(ap58, 0, QuestionCode.AP59, CodeList.FRIGO_PETITEMETTEUR);
    JPA.em().persist(ap59);
} else {
    if (!ap59.getQuestionSet().equals(ap58) && ap58.getQuestions().contains(ap59)) {
        ap58.getQuestions().remove(ap59);
        JPA.em().persist(ap58);
    }
    if (ap59.getQuestionSet().equals(ap58) && !ap58.getQuestions().contains(ap59)) {
        ap58.getQuestions().add(ap59);
        JPA.em().persist(ap58);
    }
    ap59.setOrderIndex(0);
    ((ValueSelectionQuestion)ap59).setCodeList(CodeList.FRIGO_PETITEMETTEUR);
    JPA.em().persist(ap59);
}

    }
    private void createQuestionAP60() {
        // == AP60
        // Quantité de recharge annuelle

        
ap60 = (DoubleQuestion) questionService.findByCode(QuestionCode.AP60);
if (ap60 == null) {
    ap60 = new DoubleQuestion( ap58, 0, QuestionCode.AP60, massUnits, getUnitBySymbol("kg") );
    JPA.em().persist(ap60);

    // cleanup the driver
    Driver ap60_driver = driverService.findByName("AP60");
    if (ap60_driver != null) {
        driverService.remove(ap60_driver);
    }

} else {
    if (!ap60.getQuestionSet().equals(ap58) && ap58.getQuestions().contains(ap60)) {
        ap58.getQuestions().remove(ap60);
        JPA.em().persist(ap58);
    }
    if (ap60.getQuestionSet().equals(ap58) && !ap58.getQuestions().contains(ap60)) {
        ap58.getQuestions().add(ap60);
        JPA.em().persist(ap58);
    }
    ((NumericQuestion)ap60).setUnitCategory(massUnits);
    ap60.setOrderIndex(0);
    ((NumericQuestion)ap60).setDefaultUnit(getUnitBySymbol("kg"));


    // cleanup the driver
    Driver ap60_driver = driverService.findByName("AP60");
    if (ap60_driver != null) {
        driverService.remove(ap60_driver);
    }

    ((NumericQuestion)ap60).setDriver(null);

    JPA.em().persist(ap60);
}



    }
    private void createQuestionAP63() {
        // == AP63
        // Pièces documentaires liées aux véhicules de société

        ap63 = (DocumentQuestion) questionService.findByCode(QuestionCode.AP63);
if (ap63 == null) {
    ap63 = new DocumentQuestion(ap62, 0, QuestionCode.AP63);
    JPA.em().persist(ap63);
} else {
    if (!ap63.getQuestionSet().equals(ap62) && ap62.getQuestions().contains(ap63)) {
        ap62.getQuestions().remove(ap63);
        JPA.em().persist(ap62);
    }
    if (ap63.getQuestionSet().equals(ap62) && !ap62.getQuestions().contains(ap63)) {
        ap62.getQuestions().add(ap63);
        JPA.em().persist(ap62);
    }
    ap63.setOrderIndex(0);
    JPA.em().persist(ap63);
}

    }
    private void createQuestionAP64() {
        // == AP64
        // Type de données disponibles

        ap64 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AP64);
if (ap64 == null) {
    ap64 = new ValueSelectionQuestion(ap62, 0, QuestionCode.AP64, CodeList.TYPEDONNEESMOBILITE);
    JPA.em().persist(ap64);
} else {
    if (!ap64.getQuestionSet().equals(ap62) && ap62.getQuestions().contains(ap64)) {
        ap62.getQuestions().remove(ap64);
        JPA.em().persist(ap62);
    }
    if (ap64.getQuestionSet().equals(ap62) && !ap62.getQuestions().contains(ap64)) {
        ap62.getQuestions().add(ap64);
        JPA.em().persist(ap62);
    }
    ap64.setOrderIndex(0);
    ((ValueSelectionQuestion)ap64).setCodeList(CodeList.TYPEDONNEESMOBILITE);
    JPA.em().persist(ap64);
}

    }
    private void createQuestionAP66() {
        // == AP66
        // Consommation totale d'essence

        
ap66 = (DoubleQuestion) questionService.findByCode(QuestionCode.AP66);
if (ap66 == null) {
    ap66 = new DoubleQuestion( ap65, 0, QuestionCode.AP66, volumeUnits, getUnitBySymbol("l") );
    JPA.em().persist(ap66);

    // cleanup the driver
    Driver ap66_driver = driverService.findByName("AP66");
    if (ap66_driver != null) {
        driverService.remove(ap66_driver);
    }

} else {
    if (!ap66.getQuestionSet().equals(ap65) && ap65.getQuestions().contains(ap66)) {
        ap65.getQuestions().remove(ap66);
        JPA.em().persist(ap65);
    }
    if (ap66.getQuestionSet().equals(ap65) && !ap65.getQuestions().contains(ap66)) {
        ap65.getQuestions().add(ap66);
        JPA.em().persist(ap65);
    }
    ((NumericQuestion)ap66).setUnitCategory(volumeUnits);
    ap66.setOrderIndex(0);
    ((NumericQuestion)ap66).setDefaultUnit(getUnitBySymbol("l"));

    // cleanup the driver
    Driver ap66_driver = driverService.findByName("AP66");
    if (ap66_driver != null) {
        driverService.remove(ap66_driver);
    }

    ((NumericQuestion)ap66).setDriver(null);

    JPA.em().persist(ap66);
}



    }
    private void createQuestionAP67() {
        // == AP67
        // Consommation totale de diesel

        
ap67 = (DoubleQuestion) questionService.findByCode(QuestionCode.AP67);
if (ap67 == null) {
    ap67 = new DoubleQuestion( ap65, 0, QuestionCode.AP67, volumeUnits, getUnitBySymbol("l") );
    JPA.em().persist(ap67);

    // cleanup the driver
    Driver ap67_driver = driverService.findByName("AP67");
    if (ap67_driver != null) {
        driverService.remove(ap67_driver);
    }

} else {
    if (!ap67.getQuestionSet().equals(ap65) && ap65.getQuestions().contains(ap67)) {
        ap65.getQuestions().remove(ap67);
        JPA.em().persist(ap65);
    }
    if (ap67.getQuestionSet().equals(ap65) && !ap65.getQuestions().contains(ap67)) {
        ap65.getQuestions().add(ap67);
        JPA.em().persist(ap65);
    }
    ((NumericQuestion)ap67).setUnitCategory(volumeUnits);
    ap67.setOrderIndex(0);
    ((NumericQuestion)ap67).setDefaultUnit(getUnitBySymbol("l"));

    // cleanup the driver
    Driver ap67_driver = driverService.findByName("AP67");
    if (ap67_driver != null) {
        driverService.remove(ap67_driver);
    }

    ((NumericQuestion)ap67).setDriver(null);

    JPA.em().persist(ap67);
}



    }
    private void createQuestionAP68() {
        // == AP68
        // Consommation totale de gaz de pétrole liquéfié (LPG)

        
ap68 = (DoubleQuestion) questionService.findByCode(QuestionCode.AP68);
if (ap68 == null) {
    ap68 = new DoubleQuestion( ap65, 0, QuestionCode.AP68, volumeUnits, getUnitBySymbol("l") );
    JPA.em().persist(ap68);

    // cleanup the driver
    Driver ap68_driver = driverService.findByName("AP68");
    if (ap68_driver != null) {
        driverService.remove(ap68_driver);
    }

} else {
    if (!ap68.getQuestionSet().equals(ap65) && ap65.getQuestions().contains(ap68)) {
        ap65.getQuestions().remove(ap68);
        JPA.em().persist(ap65);
    }
    if (ap68.getQuestionSet().equals(ap65) && !ap65.getQuestions().contains(ap68)) {
        ap65.getQuestions().add(ap68);
        JPA.em().persist(ap65);
    }
    ((NumericQuestion)ap68).setUnitCategory(volumeUnits);
    ap68.setOrderIndex(0);
    ((NumericQuestion)ap68).setDefaultUnit(getUnitBySymbol("l"));

    // cleanup the driver
    Driver ap68_driver = driverService.findByName("AP68");
    if (ap68_driver != null) {
        driverService.remove(ap68_driver);
    }

    ((NumericQuestion)ap68).setDriver(null);

    JPA.em().persist(ap68);
}



    }
    private void createQuestionAP71() {
        // == AP71
        // Catégorie de véhicule

        ap71 = (StringQuestion) questionService.findByCode(QuestionCode.AP71);
if (ap71 == null) {
    ap71 = new StringQuestion(ap70, 0, QuestionCode.AP71, null);
    JPA.em().persist(ap71);
} else {
    ((StringQuestion)ap71).setDefaultValue(null);
    if (!ap71.getQuestionSet().equals(ap70) && ap70.getQuestions().contains(ap71)) {
        ap70.getQuestions().remove(ap71);
        JPA.em().persist(ap70);
    }
    if (ap71.getQuestionSet().equals(ap70) && !ap70.getQuestions().contains(ap71)) {
        ap70.getQuestions().add(ap71);
        JPA.em().persist(ap70);
    }
    ap71.setOrderIndex(0);
    JPA.em().persist(ap71);
}

    }
    private void createQuestionAP73() {
        // == AP73
        // Type de carburant

        ap73 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AP73);
if (ap73 == null) {
    ap73 = new ValueSelectionQuestion(ap70, 0, QuestionCode.AP73, CodeList.CARBURANT);
    JPA.em().persist(ap73);
} else {
    if (!ap73.getQuestionSet().equals(ap70) && ap70.getQuestions().contains(ap73)) {
        ap70.getQuestions().remove(ap73);
        JPA.em().persist(ap70);
    }
    if (ap73.getQuestionSet().equals(ap70) && !ap70.getQuestions().contains(ap73)) {
        ap70.getQuestions().add(ap73);
        JPA.em().persist(ap70);
    }
    ap73.setOrderIndex(0);
    ((ValueSelectionQuestion)ap73).setCodeList(CodeList.CARBURANT);
    JPA.em().persist(ap73);
}

    }
    private void createQuestionAP76() {
        // == AP76
        // Consommation moyenne (l/100km)

        ap76 = (IntegerQuestion) questionService.findByCode(QuestionCode.AP76);
if (ap76 == null) {
    ap76 = new IntegerQuestion(ap70, 0, QuestionCode.AP76, null);
    JPA.em().persist(ap76);

    // cleanup the driver
    Driver ap76_driver = driverService.findByName("AP76");
    if (ap76_driver != null) {
        driverService.remove(ap76_driver);
    }

} else {
    if (!ap76.getQuestionSet().equals(ap70) && ap70.getQuestions().contains(ap76)) {
        ap70.getQuestions().remove(ap76);
        JPA.em().persist(ap70);
    }
    if (ap76.getQuestionSet().equals(ap70) && !ap70.getQuestions().contains(ap76)) {
        ap70.getQuestions().add(ap76);
        JPA.em().persist(ap70);
    }
    ap76.setOrderIndex(0);
    ((NumericQuestion)ap76).setUnitCategory(null);

    // cleanup the driver
    Driver ap76_driver = driverService.findByName("AP76");
    if (ap76_driver != null) {
        driverService.remove(ap76_driver);
    }

    ((NumericQuestion)ap76).setDriver(null);

    JPA.em().persist(ap76);
}

    }
    private void createQuestionAP77() {
        // == AP77
        // Nombre de kilomètre parcourus annuellement par l'ensemble des véhicules de la catégorie

        
ap77 = (DoubleQuestion) questionService.findByCode(QuestionCode.AP77);
if (ap77 == null) {
    ap77 = new DoubleQuestion( ap70, 0, QuestionCode.AP77, lengthUnits, getUnitBySymbol("km") );
    JPA.em().persist(ap77);

    // cleanup the driver
    Driver ap77_driver = driverService.findByName("AP77");
    if (ap77_driver != null) {
        driverService.remove(ap77_driver);
    }

} else {
    if (!ap77.getQuestionSet().equals(ap70) && ap70.getQuestions().contains(ap77)) {
        ap70.getQuestions().remove(ap77);
        JPA.em().persist(ap70);
    }
    if (ap77.getQuestionSet().equals(ap70) && !ap70.getQuestions().contains(ap77)) {
        ap70.getQuestions().add(ap77);
        JPA.em().persist(ap70);
    }
    ((NumericQuestion)ap77).setUnitCategory(lengthUnits);
    ap77.setOrderIndex(0);
    ((NumericQuestion)ap77).setDefaultUnit(getUnitBySymbol("km"));

    // cleanup the driver
    Driver ap77_driver = driverService.findByName("AP77");
    if (ap77_driver != null) {
        driverService.remove(ap77_driver);
    }

    ((NumericQuestion)ap77).setDriver(null);

    JPA.em().persist(ap77);
}



    }
    private void createQuestionAP79() {
        // == AP79
        // Pièces documentaires liées aux déplacements domicile-travail

        ap79 = (DocumentQuestion) questionService.findByCode(QuestionCode.AP79);
if (ap79 == null) {
    ap79 = new DocumentQuestion(ap78, 0, QuestionCode.AP79);
    JPA.em().persist(ap79);
} else {
    if (!ap79.getQuestionSet().equals(ap78) && ap78.getQuestions().contains(ap79)) {
        ap78.getQuestions().remove(ap79);
        JPA.em().persist(ap78);
    }
    if (ap79.getQuestionSet().equals(ap78) && !ap78.getQuestions().contains(ap79)) {
        ap78.getQuestions().add(ap79);
        JPA.em().persist(ap78);
    }
    ap79.setOrderIndex(0);
    JPA.em().persist(ap79);
}

    }
    private void createQuestionAP80() {
        // == AP80
        // Choisissez le mode d'encodage adapté à votre organisation

        ap80 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AP80);
if (ap80 == null) {
    ap80 = new ValueSelectionQuestion(ap78, 0, QuestionCode.AP80, CodeList.TYPEDONNEESDDT);
    JPA.em().persist(ap80);
} else {
    if (!ap80.getQuestionSet().equals(ap78) && ap78.getQuestions().contains(ap80)) {
        ap78.getQuestions().remove(ap80);
        JPA.em().persist(ap78);
    }
    if (ap80.getQuestionSet().equals(ap78) && !ap78.getQuestions().contains(ap80)) {
        ap78.getQuestions().add(ap80);
        JPA.em().persist(ap78);
    }
    ap80.setOrderIndex(0);
    ((ValueSelectionQuestion)ap80).setCodeList(CodeList.TYPEDONNEESDDT);
    JPA.em().persist(ap80);
}

    }
    private void createQuestionAP82() {
        // == AP82
        // Employé (nom ou initiale)

        ap82 = (StringQuestion) questionService.findByCode(QuestionCode.AP82);
if (ap82 == null) {
    ap82 = new StringQuestion(ap81, 0, QuestionCode.AP82, null);
    JPA.em().persist(ap82);
} else {
    ((StringQuestion)ap82).setDefaultValue(null);
    if (!ap82.getQuestionSet().equals(ap81) && ap81.getQuestions().contains(ap82)) {
        ap81.getQuestions().remove(ap82);
        JPA.em().persist(ap81);
    }
    if (ap82.getQuestionSet().equals(ap81) && !ap81.getQuestions().contains(ap82)) {
        ap81.getQuestions().add(ap82);
        JPA.em().persist(ap81);
    }
    ap82.setOrderIndex(0);
    JPA.em().persist(ap82);
}

    }
    private void createQuestionAP83() {
        // == AP83
        // Mode principal de déplacement

        ap83 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AP83);
if (ap83 == null) {
    ap83 = new ValueSelectionQuestion(ap81, 0, QuestionCode.AP83, CodeList.TYPEDEPLEMPLPETITEMETTEUR);
    JPA.em().persist(ap83);
} else {
    if (!ap83.getQuestionSet().equals(ap81) && ap81.getQuestions().contains(ap83)) {
        ap81.getQuestions().remove(ap83);
        JPA.em().persist(ap81);
    }
    if (ap83.getQuestionSet().equals(ap81) && !ap81.getQuestions().contains(ap83)) {
        ap81.getQuestions().add(ap83);
        JPA.em().persist(ap81);
    }
    ap83.setOrderIndex(0);
    ((ValueSelectionQuestion)ap83).setCodeList(CodeList.TYPEDEPLEMPLPETITEMETTEUR);
    JPA.em().persist(ap83);
}

    }
    private void createQuestionAP84() {
        // == AP84
        // <b>Attention</b>: ne déclarer ici que les véhicules autres que ceux appartenant ou loués régulièrement par la société

        ap84 = (BooleanQuestion) questionService.findByCode(QuestionCode.AP84);
if (ap84 == null) {
    ap84 = new BooleanQuestion(ap81, 0, QuestionCode.AP84, null);
    JPA.em().persist(ap84);
} else {
    ((BooleanQuestion)ap84).setDefaultValue(null);
    if (!ap84.getQuestionSet().equals(ap81) && ap81.getQuestions().contains(ap84)) {
        ap81.getQuestions().remove(ap84);
        JPA.em().persist(ap81);
    }
    if (ap84.getQuestionSet().equals(ap81) && !ap81.getQuestions().contains(ap84)) {
        ap81.getQuestions().add(ap84);
        JPA.em().persist(ap81);
    }
    ap84.setOrderIndex(0);
    JPA.em().persist(ap84);
}

    }
    private void createQuestionAP85() {
        // == AP85
        // Nombre de trajets aller-retour par jour

        ap85 = (IntegerQuestion) questionService.findByCode(QuestionCode.AP85);
if (ap85 == null) {
    ap85 = new IntegerQuestion(ap81, 0, QuestionCode.AP85, null);
    JPA.em().persist(ap85);

    // cleanup the driver
    Driver ap85_driver = driverService.findByName("AP85");
    if (ap85_driver != null) {
        driverService.remove(ap85_driver);
    }

} else {
    if (!ap85.getQuestionSet().equals(ap81) && ap81.getQuestions().contains(ap85)) {
        ap81.getQuestions().remove(ap85);
        JPA.em().persist(ap81);
    }
    if (ap85.getQuestionSet().equals(ap81) && !ap81.getQuestions().contains(ap85)) {
        ap81.getQuestions().add(ap85);
        JPA.em().persist(ap81);
    }
    ap85.setOrderIndex(0);
    ((NumericQuestion)ap85).setUnitCategory(null);

    // cleanup the driver
    Driver ap85_driver = driverService.findByName("AP85");
    if (ap85_driver != null) {
        driverService.remove(ap85_driver);
    }

    ((NumericQuestion)ap85).setDriver(null);

    JPA.em().persist(ap85);
}

    }
    private void createQuestionAP86() {
        // == AP86
        // Nombre de jours de travail par an

        ap86 = (IntegerQuestion) questionService.findByCode(QuestionCode.AP86);
if (ap86 == null) {
    ap86 = new IntegerQuestion(ap81, 0, QuestionCode.AP86, null);
    JPA.em().persist(ap86);

    // cleanup the driver
    Driver ap86_driver = driverService.findByName("AP86");
    if (ap86_driver != null) {
        driverService.remove(ap86_driver);
    }

    // recreate with good value
    ap86_driver = new Driver("AP86");
    driverService.saveOrUpdate(ap86_driver);
    Period p2000 = periodService.findByCode(PeriodCode.P2000);
    DriverValue dv = new DriverValue(ap86_driver, p2000, Double.valueOf(220.0));
    ap86_driver.getDriverValueList().add(dv);;
    driverService.saveOrUpdate(ap86_driver);
    JPA.em().persist(dv);

    ((NumericQuestion)ap86).setDriver(ap86_driver);
    JPA.em().persist(ap86);
} else {
    if (!ap86.getQuestionSet().equals(ap81) && ap81.getQuestions().contains(ap86)) {
        ap81.getQuestions().remove(ap86);
        JPA.em().persist(ap81);
    }
    if (ap86.getQuestionSet().equals(ap81) && !ap81.getQuestions().contains(ap86)) {
        ap81.getQuestions().add(ap86);
        JPA.em().persist(ap81);
    }
    ap86.setOrderIndex(0);
    ((NumericQuestion)ap86).setUnitCategory(null);

    // cleanup the driver
    Driver ap86_driver = driverService.findByName("AP86");
    if (ap86_driver != null) {
        driverService.remove(ap86_driver);
    }

    // recreate with good value
    ap86_driver = new Driver("AP86");
    driverService.saveOrUpdate(ap86_driver);
    Period p2000 = periodService.findByCode(PeriodCode.P2000);
    DriverValue dv = new DriverValue(ap86_driver, p2000, Double.valueOf(220.0));
    ap86_driver.getDriverValueList().add(dv);;
    driverService.saveOrUpdate(ap86_driver);
    JPA.em().persist(dv);

    ((NumericQuestion)ap86).setDriver(ap86_driver);

    JPA.em().persist(ap86);
}

    }
    private void createQuestionAP87() {
        // == AP87
        // Distance moyenne aller

        
ap87 = (DoubleQuestion) questionService.findByCode(QuestionCode.AP87);
if (ap87 == null) {
    ap87 = new DoubleQuestion( ap81, 0, QuestionCode.AP87, lengthUnits, getUnitBySymbol("km") );
    JPA.em().persist(ap87);

    // cleanup the driver
    Driver ap87_driver = driverService.findByName("AP87");
    if (ap87_driver != null) {
        driverService.remove(ap87_driver);
    }

} else {
    if (!ap87.getQuestionSet().equals(ap81) && ap81.getQuestions().contains(ap87)) {
        ap81.getQuestions().remove(ap87);
        JPA.em().persist(ap81);
    }
    if (ap87.getQuestionSet().equals(ap81) && !ap81.getQuestions().contains(ap87)) {
        ap81.getQuestions().add(ap87);
        JPA.em().persist(ap81);
    }
    ((NumericQuestion)ap87).setUnitCategory(lengthUnits);
    ap87.setOrderIndex(0);
    ((NumericQuestion)ap87).setDefaultUnit(getUnitBySymbol("km"));

    // cleanup the driver
    Driver ap87_driver = driverService.findByName("AP87");
    if (ap87_driver != null) {
        driverService.remove(ap87_driver);
    }

    ((NumericQuestion)ap87).setDriver(null);

    JPA.em().persist(ap87);
}



    }
    private void createQuestionAP90() {
        // == AP90
        // Consommation moyenne (L/100km)

        ap90 = (IntegerQuestion) questionService.findByCode(QuestionCode.AP90);
if (ap90 == null) {
    ap90 = new IntegerQuestion(ap81, 0, QuestionCode.AP90, null);
    JPA.em().persist(ap90);

    // cleanup the driver
    Driver ap90_driver = driverService.findByName("AP90");
    if (ap90_driver != null) {
        driverService.remove(ap90_driver);
    }

} else {
    if (!ap90.getQuestionSet().equals(ap81) && ap81.getQuestions().contains(ap90)) {
        ap81.getQuestions().remove(ap90);
        JPA.em().persist(ap81);
    }
    if (ap90.getQuestionSet().equals(ap81) && !ap81.getQuestions().contains(ap90)) {
        ap81.getQuestions().add(ap90);
        JPA.em().persist(ap81);
    }
    ap90.setOrderIndex(0);
    ((NumericQuestion)ap90).setUnitCategory(null);

    // cleanup the driver
    Driver ap90_driver = driverService.findByName("AP90");
    if (ap90_driver != null) {
        driverService.remove(ap90_driver);
    }

    ((NumericQuestion)ap90).setDriver(null);

    JPA.em().persist(ap90);
}

    }
    private void createQuestionAP92() {
        // == AP92
        // Distance moyenne entre domicile et travail

        
ap92 = (DoubleQuestion) questionService.findByCode(QuestionCode.AP92);
if (ap92 == null) {
    ap92 = new DoubleQuestion( ap91, 0, QuestionCode.AP92, lengthUnits, getUnitBySymbol("km") );
    JPA.em().persist(ap92);

    // cleanup the driver
    Driver ap92_driver = driverService.findByName("AP92");
    if (ap92_driver != null) {
        driverService.remove(ap92_driver);
    }

} else {
    if (!ap92.getQuestionSet().equals(ap91) && ap91.getQuestions().contains(ap92)) {
        ap91.getQuestions().remove(ap92);
        JPA.em().persist(ap91);
    }
    if (ap92.getQuestionSet().equals(ap91) && !ap91.getQuestions().contains(ap92)) {
        ap91.getQuestions().add(ap92);
        JPA.em().persist(ap91);
    }
    ((NumericQuestion)ap92).setUnitCategory(lengthUnits);
    ap92.setOrderIndex(0);
    ((NumericQuestion)ap92).setDefaultUnit(getUnitBySymbol("km"));

    // cleanup the driver
    Driver ap92_driver = driverService.findByName("AP92");
    if (ap92_driver != null) {
        driverService.remove(ap92_driver);
    }

    ((NumericQuestion)ap92).setDriver(null);

    JPA.em().persist(ap92);
}



    }
    private void createQuestionAP94() {
        // == AP94
        // Voiture (avec des voitures autres que celles de société p.e. les voitures appartenant aux employés y compris la direction)

        ap94 = (PercentageQuestion) questionService.findByCode(QuestionCode.AP94);
if (ap94 == null) {
    ap94 = new PercentageQuestion(ap93, 0, QuestionCode.AP94);
    JPA.em().persist(ap94);

    // cleanup the driver
    Driver ap94_driver = driverService.findByName("AP94");
    if (ap94_driver != null) {
        driverService.remove(ap94_driver);
    }

} else {
    if (!ap94.getQuestionSet().equals(ap93) && ap93.getQuestions().contains(ap94)) {
        ap93.getQuestions().remove(ap94);
        JPA.em().persist(ap93);
    }
    if (ap94.getQuestionSet().equals(ap93) && !ap93.getQuestions().contains(ap94)) {
        ap93.getQuestions().add(ap94);
        JPA.em().persist(ap93);
    }
    ap94.setOrderIndex(0);

    // cleanup the driver
    Driver ap94_driver = driverService.findByName("AP94");
    if (ap94_driver != null) {
        driverService.remove(ap94_driver);
    }

    ((NumericQuestion)ap94).setDriver(null);

    JPA.em().persist(ap94);
}

    }
    private void createQuestionAP96() {
        // == AP96
        // Proportion de voiture Diesel (%)

        ap96 = (PercentageQuestion) questionService.findByCode(QuestionCode.AP96);
if (ap96 == null) {
    ap96 = new PercentageQuestion(ap95, 0, QuestionCode.AP96);
    JPA.em().persist(ap96);

    // cleanup the driver
    Driver ap96_driver = driverService.findByName("AP96");
    if (ap96_driver != null) {
        driverService.remove(ap96_driver);
    }

    // recreate with good value
    ap96_driver = new Driver("AP96");
    driverService.saveOrUpdate(ap96_driver);
    Period p2000 = periodService.findByCode(PeriodCode.P2000);
    DriverValue dv = new DriverValue(ap96_driver, p2000, Double.valueOf(61));
    ap96_driver.getDriverValueList().add(dv);;
    driverService.saveOrUpdate(ap96_driver);
    JPA.em().persist(dv);

    ((NumericQuestion)ap96).setDriver(ap96_driver);
    JPA.em().persist(ap96);
} else {
    if (!ap96.getQuestionSet().equals(ap95) && ap95.getQuestions().contains(ap96)) {
        ap95.getQuestions().remove(ap96);
        JPA.em().persist(ap95);
    }
    if (ap96.getQuestionSet().equals(ap95) && !ap95.getQuestions().contains(ap96)) {
        ap95.getQuestions().add(ap96);
        JPA.em().persist(ap95);
    }
    ap96.setOrderIndex(0);

    // cleanup the driver
    Driver ap96_driver = driverService.findByName("AP96");
    if (ap96_driver != null) {
        driverService.remove(ap96_driver);
    }

    // recreate with good value
    ap96_driver = new Driver("AP96");
    driverService.saveOrUpdate(ap96_driver);
    Period p2000 = periodService.findByCode(PeriodCode.P2000);
    DriverValue dv = new DriverValue(ap96_driver, p2000, Double.valueOf(61));
    ap96_driver.getDriverValueList().add(dv);;
    driverService.saveOrUpdate(ap96_driver);
    JPA.em().persist(dv);

    ((NumericQuestion)ap96).setDriver(ap96_driver);

    JPA.em().persist(ap96);
}

    }
    private void createQuestionAP97() {
        // == AP97
        // Proportion de voiture Essence (%)

        ap97 = (PercentageQuestion) questionService.findByCode(QuestionCode.AP97);
if (ap97 == null) {
    ap97 = new PercentageQuestion(ap95, 0, QuestionCode.AP97);
    JPA.em().persist(ap97);

    // cleanup the driver
    Driver ap97_driver = driverService.findByName("AP97");
    if (ap97_driver != null) {
        driverService.remove(ap97_driver);
    }

    // recreate with good value
    ap97_driver = new Driver("AP97");
    driverService.saveOrUpdate(ap97_driver);
    Period p2000 = periodService.findByCode(PeriodCode.P2000);
    DriverValue dv = new DriverValue(ap97_driver, p2000, Double.valueOf(39));
    ap97_driver.getDriverValueList().add(dv);;
    driverService.saveOrUpdate(ap97_driver);
    JPA.em().persist(dv);

    ((NumericQuestion)ap97).setDriver(ap97_driver);
    JPA.em().persist(ap97);
} else {
    if (!ap97.getQuestionSet().equals(ap95) && ap95.getQuestions().contains(ap97)) {
        ap95.getQuestions().remove(ap97);
        JPA.em().persist(ap95);
    }
    if (ap97.getQuestionSet().equals(ap95) && !ap95.getQuestions().contains(ap97)) {
        ap95.getQuestions().add(ap97);
        JPA.em().persist(ap95);
    }
    ap97.setOrderIndex(0);

    // cleanup the driver
    Driver ap97_driver = driverService.findByName("AP97");
    if (ap97_driver != null) {
        driverService.remove(ap97_driver);
    }

    // recreate with good value
    ap97_driver = new Driver("AP97");
    driverService.saveOrUpdate(ap97_driver);
    Period p2000 = periodService.findByCode(PeriodCode.P2000);
    DriverValue dv = new DriverValue(ap97_driver, p2000, Double.valueOf(39));
    ap97_driver.getDriverValueList().add(dv);;
    driverService.saveOrUpdate(ap97_driver);
    JPA.em().persist(dv);

    ((NumericQuestion)ap97).setDriver(ap97_driver);

    JPA.em().persist(ap97);
}

    }
    private void createQuestionAP98() {
        // == AP98
        // Consommation moyenne des véhicules (l/100km)

        ap98 = (IntegerQuestion) questionService.findByCode(QuestionCode.AP98);
if (ap98 == null) {
    ap98 = new IntegerQuestion(ap95, 0, QuestionCode.AP98, null);
    JPA.em().persist(ap98);

    // cleanup the driver
    Driver ap98_driver = driverService.findByName("AP98");
    if (ap98_driver != null) {
        driverService.remove(ap98_driver);
    }

} else {
    if (!ap98.getQuestionSet().equals(ap95) && ap95.getQuestions().contains(ap98)) {
        ap95.getQuestions().remove(ap98);
        JPA.em().persist(ap95);
    }
    if (ap98.getQuestionSet().equals(ap95) && !ap95.getQuestions().contains(ap98)) {
        ap95.getQuestions().add(ap98);
        JPA.em().persist(ap95);
    }
    ap98.setOrderIndex(0);
    ((NumericQuestion)ap98).setUnitCategory(null);

    // cleanup the driver
    Driver ap98_driver = driverService.findByName("AP98");
    if (ap98_driver != null) {
        driverService.remove(ap98_driver);
    }

    ((NumericQuestion)ap98).setDriver(null);

    JPA.em().persist(ap98);
}

    }
    private void createQuestionAP99() {
        // == AP99
        // Train

        ap99 = (PercentageQuestion) questionService.findByCode(QuestionCode.AP99);
if (ap99 == null) {
    ap99 = new PercentageQuestion(ap93, 0, QuestionCode.AP99);
    JPA.em().persist(ap99);

    // cleanup the driver
    Driver ap99_driver = driverService.findByName("AP99");
    if (ap99_driver != null) {
        driverService.remove(ap99_driver);
    }

} else {
    if (!ap99.getQuestionSet().equals(ap93) && ap93.getQuestions().contains(ap99)) {
        ap93.getQuestions().remove(ap99);
        JPA.em().persist(ap93);
    }
    if (ap99.getQuestionSet().equals(ap93) && !ap93.getQuestions().contains(ap99)) {
        ap93.getQuestions().add(ap99);
        JPA.em().persist(ap93);
    }
    ap99.setOrderIndex(0);

    // cleanup the driver
    Driver ap99_driver = driverService.findByName("AP99");
    if (ap99_driver != null) {
        driverService.remove(ap99_driver);
    }

    ((NumericQuestion)ap99).setDriver(null);

    JPA.em().persist(ap99);
}

    }
    private void createQuestionAP100() {
        // == AP100
        // Bus

        ap100 = (PercentageQuestion) questionService.findByCode(QuestionCode.AP100);
if (ap100 == null) {
    ap100 = new PercentageQuestion(ap93, 0, QuestionCode.AP100);
    JPA.em().persist(ap100);

    // cleanup the driver
    Driver ap100_driver = driverService.findByName("AP100");
    if (ap100_driver != null) {
        driverService.remove(ap100_driver);
    }

} else {
    if (!ap100.getQuestionSet().equals(ap93) && ap93.getQuestions().contains(ap100)) {
        ap93.getQuestions().remove(ap100);
        JPA.em().persist(ap93);
    }
    if (ap100.getQuestionSet().equals(ap93) && !ap93.getQuestions().contains(ap100)) {
        ap93.getQuestions().add(ap100);
        JPA.em().persist(ap93);
    }
    ap100.setOrderIndex(0);

    // cleanup the driver
    Driver ap100_driver = driverService.findByName("AP100");
    if (ap100_driver != null) {
        driverService.remove(ap100_driver);
    }

    ((NumericQuestion)ap100).setDriver(null);

    JPA.em().persist(ap100);
}

    }
    private void createQuestionAP101() {
        // == AP101
        // Métro ou Tram

        ap101 = (PercentageQuestion) questionService.findByCode(QuestionCode.AP101);
if (ap101 == null) {
    ap101 = new PercentageQuestion(ap93, 0, QuestionCode.AP101);
    JPA.em().persist(ap101);

    // cleanup the driver
    Driver ap101_driver = driverService.findByName("AP101");
    if (ap101_driver != null) {
        driverService.remove(ap101_driver);
    }

} else {
    if (!ap101.getQuestionSet().equals(ap93) && ap93.getQuestions().contains(ap101)) {
        ap93.getQuestions().remove(ap101);
        JPA.em().persist(ap93);
    }
    if (ap101.getQuestionSet().equals(ap93) && !ap93.getQuestions().contains(ap101)) {
        ap93.getQuestions().add(ap101);
        JPA.em().persist(ap93);
    }
    ap101.setOrderIndex(0);

    // cleanup the driver
    Driver ap101_driver = driverService.findByName("AP101");
    if (ap101_driver != null) {
        driverService.remove(ap101_driver);
    }

    ((NumericQuestion)ap101).setDriver(null);

    JPA.em().persist(ap101);
}

    }
    private void createQuestionAP102() {
        // == AP102
        // Cyclomoteur ou moto

        ap102 = (PercentageQuestion) questionService.findByCode(QuestionCode.AP102);
if (ap102 == null) {
    ap102 = new PercentageQuestion(ap93, 0, QuestionCode.AP102);
    JPA.em().persist(ap102);

    // cleanup the driver
    Driver ap102_driver = driverService.findByName("AP102");
    if (ap102_driver != null) {
        driverService.remove(ap102_driver);
    }

} else {
    if (!ap102.getQuestionSet().equals(ap93) && ap93.getQuestions().contains(ap102)) {
        ap93.getQuestions().remove(ap102);
        JPA.em().persist(ap93);
    }
    if (ap102.getQuestionSet().equals(ap93) && !ap93.getQuestions().contains(ap102)) {
        ap93.getQuestions().add(ap102);
        JPA.em().persist(ap93);
    }
    ap102.setOrderIndex(0);

    // cleanup the driver
    Driver ap102_driver = driverService.findByName("AP102");
    if (ap102_driver != null) {
        driverService.remove(ap102_driver);
    }

    ((NumericQuestion)ap102).setDriver(null);

    JPA.em().persist(ap102);
}

    }
    private void createQuestionAP103() {
        // == AP103
        // Vélo

        ap103 = (PercentageQuestion) questionService.findByCode(QuestionCode.AP103);
if (ap103 == null) {
    ap103 = new PercentageQuestion(ap93, 0, QuestionCode.AP103);
    JPA.em().persist(ap103);

    // cleanup the driver
    Driver ap103_driver = driverService.findByName("AP103");
    if (ap103_driver != null) {
        driverService.remove(ap103_driver);
    }

} else {
    if (!ap103.getQuestionSet().equals(ap93) && ap93.getQuestions().contains(ap103)) {
        ap93.getQuestions().remove(ap103);
        JPA.em().persist(ap93);
    }
    if (ap103.getQuestionSet().equals(ap93) && !ap93.getQuestions().contains(ap103)) {
        ap93.getQuestions().add(ap103);
        JPA.em().persist(ap93);
    }
    ap103.setOrderIndex(0);

    // cleanup the driver
    Driver ap103_driver = driverService.findByName("AP103");
    if (ap103_driver != null) {
        driverService.remove(ap103_driver);
    }

    ((NumericQuestion)ap103).setDriver(null);

    JPA.em().persist(ap103);
}

    }
    private void createQuestionAP104() {
        // == AP104
        // Marche

        ap104 = (PercentageQuestion) questionService.findByCode(QuestionCode.AP104);
if (ap104 == null) {
    ap104 = new PercentageQuestion(ap93, 0, QuestionCode.AP104);
    JPA.em().persist(ap104);

    // cleanup the driver
    Driver ap104_driver = driverService.findByName("AP104");
    if (ap104_driver != null) {
        driverService.remove(ap104_driver);
    }

} else {
    if (!ap104.getQuestionSet().equals(ap93) && ap93.getQuestions().contains(ap104)) {
        ap93.getQuestions().remove(ap104);
        JPA.em().persist(ap93);
    }
    if (ap104.getQuestionSet().equals(ap93) && !ap93.getQuestions().contains(ap104)) {
        ap93.getQuestions().add(ap104);
        JPA.em().persist(ap93);
    }
    ap104.setOrderIndex(0);

    // cleanup the driver
    Driver ap104_driver = driverService.findByName("AP104");
    if (ap104_driver != null) {
        driverService.remove(ap104_driver);
    }

    ((NumericQuestion)ap104).setDriver(null);

    JPA.em().persist(ap104);
}

    }
    private void createQuestionAP105() {
        // == AP105
        // Total (doit être égal a 100%)

        ap105 = (PercentageQuestion) questionService.findByCode(QuestionCode.AP105);
if (ap105 == null) {
    ap105 = new PercentageQuestion(ap93, 0, QuestionCode.AP105);
    JPA.em().persist(ap105);

    // cleanup the driver
    Driver ap105_driver = driverService.findByName("AP105");
    if (ap105_driver != null) {
        driverService.remove(ap105_driver);
    }

} else {
    if (!ap105.getQuestionSet().equals(ap93) && ap93.getQuestions().contains(ap105)) {
        ap93.getQuestions().remove(ap105);
        JPA.em().persist(ap93);
    }
    if (ap105.getQuestionSet().equals(ap93) && !ap93.getQuestions().contains(ap105)) {
        ap93.getQuestions().add(ap105);
        JPA.em().persist(ap93);
    }
    ap105.setOrderIndex(0);

    // cleanup the driver
    Driver ap105_driver = driverService.findByName("AP105");
    if (ap105_driver != null) {
        driverService.remove(ap105_driver);
    }

    ((NumericQuestion)ap105).setDriver(null);

    JPA.em().persist(ap105);
}

    }
    private void createQuestionAP107() {
        // == AP107
        // Pièces documentaires liées aux déplacements professionnels en Belgique

        ap107 = (DocumentQuestion) questionService.findByCode(QuestionCode.AP107);
if (ap107 == null) {
    ap107 = new DocumentQuestion(ap106, 0, QuestionCode.AP107);
    JPA.em().persist(ap107);
} else {
    if (!ap107.getQuestionSet().equals(ap106) && ap106.getQuestions().contains(ap107)) {
        ap106.getQuestions().remove(ap107);
        JPA.em().persist(ap106);
    }
    if (ap107.getQuestionSet().equals(ap106) && !ap106.getQuestions().contains(ap107)) {
        ap106.getQuestions().add(ap107);
        JPA.em().persist(ap106);
    }
    ap107.setOrderIndex(0);
    JPA.em().persist(ap107);
}

    }
    private void createQuestionAP110() {
        // == AP110
        // Nom du (groupe de) véhicule

        ap110 = (StringQuestion) questionService.findByCode(QuestionCode.AP110);
if (ap110 == null) {
    ap110 = new StringQuestion(ap109, 0, QuestionCode.AP110, null);
    JPA.em().persist(ap110);
} else {
    ((StringQuestion)ap110).setDefaultValue(null);
    if (!ap110.getQuestionSet().equals(ap109) && ap109.getQuestions().contains(ap110)) {
        ap109.getQuestions().remove(ap110);
        JPA.em().persist(ap109);
    }
    if (ap110.getQuestionSet().equals(ap109) && !ap109.getQuestions().contains(ap110)) {
        ap109.getQuestions().add(ap110);
        JPA.em().persist(ap109);
    }
    ap110.setOrderIndex(0);
    JPA.em().persist(ap110);
}

    }
    private void createQuestionAP112() {
        // == AP112
        // Quel type de carburant utilise-t-il ?

        ap112 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AP112);
if (ap112 == null) {
    ap112 = new ValueSelectionQuestion(ap109, 0, QuestionCode.AP112, CodeList.CARBURANT);
    JPA.em().persist(ap112);
} else {
    if (!ap112.getQuestionSet().equals(ap109) && ap109.getQuestions().contains(ap112)) {
        ap109.getQuestions().remove(ap112);
        JPA.em().persist(ap109);
    }
    if (ap112.getQuestionSet().equals(ap109) && !ap109.getQuestions().contains(ap112)) {
        ap109.getQuestions().add(ap112);
        JPA.em().persist(ap109);
    }
    ap112.setOrderIndex(0);
    ((ValueSelectionQuestion)ap112).setCodeList(CodeList.CARBURANT);
    JPA.em().persist(ap112);
}

    }
    private void createQuestionAP116() {
        // == AP116
        // Consommation moyenne (L/100km)

        ap116 = (IntegerQuestion) questionService.findByCode(QuestionCode.AP116);
if (ap116 == null) {
    ap116 = new IntegerQuestion(ap109, 0, QuestionCode.AP116, null);
    JPA.em().persist(ap116);

    // cleanup the driver
    Driver ap116_driver = driverService.findByName("AP116");
    if (ap116_driver != null) {
        driverService.remove(ap116_driver);
    }

} else {
    if (!ap116.getQuestionSet().equals(ap109) && ap109.getQuestions().contains(ap116)) {
        ap109.getQuestions().remove(ap116);
        JPA.em().persist(ap109);
    }
    if (ap116.getQuestionSet().equals(ap109) && !ap109.getQuestions().contains(ap116)) {
        ap109.getQuestions().add(ap116);
        JPA.em().persist(ap109);
    }
    ap116.setOrderIndex(0);
    ((NumericQuestion)ap116).setUnitCategory(null);

    // cleanup the driver
    Driver ap116_driver = driverService.findByName("AP116");
    if (ap116_driver != null) {
        driverService.remove(ap116_driver);
    }

    ((NumericQuestion)ap116).setDriver(null);

    JPA.em().persist(ap116);
}

    }
    private void createQuestionAP117() {
        // == AP117
        // Quelle est le nombre de kilomètres parcourus par an?

        
ap117 = (DoubleQuestion) questionService.findByCode(QuestionCode.AP117);
if (ap117 == null) {
    ap117 = new DoubleQuestion( ap109, 0, QuestionCode.AP117, lengthUnits, getUnitBySymbol("km") );
    JPA.em().persist(ap117);

    // cleanup the driver
    Driver ap117_driver = driverService.findByName("AP117");
    if (ap117_driver != null) {
        driverService.remove(ap117_driver);
    }

} else {
    if (!ap117.getQuestionSet().equals(ap109) && ap109.getQuestions().contains(ap117)) {
        ap109.getQuestions().remove(ap117);
        JPA.em().persist(ap109);
    }
    if (ap117.getQuestionSet().equals(ap109) && !ap109.getQuestions().contains(ap117)) {
        ap109.getQuestions().add(ap117);
        JPA.em().persist(ap109);
    }
    ((NumericQuestion)ap117).setUnitCategory(lengthUnits);
    ap117.setOrderIndex(0);
    ((NumericQuestion)ap117).setDefaultUnit(getUnitBySymbol("km"));

    // cleanup the driver
    Driver ap117_driver = driverService.findByName("AP117");
    if (ap117_driver != null) {
        driverService.remove(ap117_driver);
    }

    ((NumericQuestion)ap117).setDriver(null);

    JPA.em().persist(ap117);
}



    }
    private void createQuestionAP118() {
        // == AP118
        // Train (passagers.km)

        ap118 = (IntegerQuestion) questionService.findByCode(QuestionCode.AP118);
if (ap118 == null) {
    ap118 = new IntegerQuestion(ap108, 0, QuestionCode.AP118, null);
    JPA.em().persist(ap118);

    // cleanup the driver
    Driver ap118_driver = driverService.findByName("AP118");
    if (ap118_driver != null) {
        driverService.remove(ap118_driver);
    }

} else {
    if (!ap118.getQuestionSet().equals(ap108) && ap108.getQuestions().contains(ap118)) {
        ap108.getQuestions().remove(ap118);
        JPA.em().persist(ap108);
    }
    if (ap118.getQuestionSet().equals(ap108) && !ap108.getQuestions().contains(ap118)) {
        ap108.getQuestions().add(ap118);
        JPA.em().persist(ap108);
    }
    ap118.setOrderIndex(0);
    ((NumericQuestion)ap118).setUnitCategory(null);

    // cleanup the driver
    Driver ap118_driver = driverService.findByName("AP118");
    if (ap118_driver != null) {
        driverService.remove(ap118_driver);
    }

    ((NumericQuestion)ap118).setDriver(null);

    JPA.em().persist(ap118);
}

    }
    private void createQuestionAP119() {
        // == AP119
        // Bus (passagers.km)

        ap119 = (IntegerQuestion) questionService.findByCode(QuestionCode.AP119);
if (ap119 == null) {
    ap119 = new IntegerQuestion(ap108, 0, QuestionCode.AP119, null);
    JPA.em().persist(ap119);

    // cleanup the driver
    Driver ap119_driver = driverService.findByName("AP119");
    if (ap119_driver != null) {
        driverService.remove(ap119_driver);
    }

} else {
    if (!ap119.getQuestionSet().equals(ap108) && ap108.getQuestions().contains(ap119)) {
        ap108.getQuestions().remove(ap119);
        JPA.em().persist(ap108);
    }
    if (ap119.getQuestionSet().equals(ap108) && !ap108.getQuestions().contains(ap119)) {
        ap108.getQuestions().add(ap119);
        JPA.em().persist(ap108);
    }
    ap119.setOrderIndex(0);
    ((NumericQuestion)ap119).setUnitCategory(null);

    // cleanup the driver
    Driver ap119_driver = driverService.findByName("AP119");
    if (ap119_driver != null) {
        driverService.remove(ap119_driver);
    }

    ((NumericQuestion)ap119).setDriver(null);

    JPA.em().persist(ap119);
}

    }
    private void createQuestionAP120() {
        // == AP120
        // Tram/Métro (passagers.km)

        ap120 = (IntegerQuestion) questionService.findByCode(QuestionCode.AP120);
if (ap120 == null) {
    ap120 = new IntegerQuestion(ap108, 0, QuestionCode.AP120, null);
    JPA.em().persist(ap120);

    // cleanup the driver
    Driver ap120_driver = driverService.findByName("AP120");
    if (ap120_driver != null) {
        driverService.remove(ap120_driver);
    }

} else {
    if (!ap120.getQuestionSet().equals(ap108) && ap108.getQuestions().contains(ap120)) {
        ap108.getQuestions().remove(ap120);
        JPA.em().persist(ap108);
    }
    if (ap120.getQuestionSet().equals(ap108) && !ap108.getQuestions().contains(ap120)) {
        ap108.getQuestions().add(ap120);
        JPA.em().persist(ap108);
    }
    ap120.setOrderIndex(0);
    ((NumericQuestion)ap120).setUnitCategory(null);

    // cleanup the driver
    Driver ap120_driver = driverService.findByName("AP120");
    if (ap120_driver != null) {
        driverService.remove(ap120_driver);
    }

    ((NumericQuestion)ap120).setDriver(null);

    JPA.em().persist(ap120);
}

    }
    private void createQuestionAP122() {
        // == AP122
        // Pièces documentaires liées aux déplacements professionnels à l'étranger

        ap122 = (DocumentQuestion) questionService.findByCode(QuestionCode.AP122);
if (ap122 == null) {
    ap122 = new DocumentQuestion(ap121, 0, QuestionCode.AP122);
    JPA.em().persist(ap122);
} else {
    if (!ap122.getQuestionSet().equals(ap121) && ap121.getQuestions().contains(ap122)) {
        ap121.getQuestions().remove(ap122);
        JPA.em().persist(ap121);
    }
    if (ap122.getQuestionSet().equals(ap121) && !ap121.getQuestions().contains(ap122)) {
        ap121.getQuestions().add(ap122);
        JPA.em().persist(ap121);
    }
    ap122.setOrderIndex(0);
    JPA.em().persist(ap122);
}

    }
    private void createQuestionAP125() {
        // == AP125
        // Nom du (groupe de) véhicule

        ap125 = (StringQuestion) questionService.findByCode(QuestionCode.AP125);
if (ap125 == null) {
    ap125 = new StringQuestion(ap124, 0, QuestionCode.AP125, null);
    JPA.em().persist(ap125);
} else {
    ((StringQuestion)ap125).setDefaultValue(null);
    if (!ap125.getQuestionSet().equals(ap124) && ap124.getQuestions().contains(ap125)) {
        ap124.getQuestions().remove(ap125);
        JPA.em().persist(ap124);
    }
    if (ap125.getQuestionSet().equals(ap124) && !ap124.getQuestions().contains(ap125)) {
        ap124.getQuestions().add(ap125);
        JPA.em().persist(ap124);
    }
    ap125.setOrderIndex(0);
    JPA.em().persist(ap125);
}

    }
    private void createQuestionAP127() {
        // == AP127
        // Quel type de carburant utilise-t-il ?

        ap127 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AP127);
if (ap127 == null) {
    ap127 = new ValueSelectionQuestion(ap124, 0, QuestionCode.AP127, CodeList.CARBURANT);
    JPA.em().persist(ap127);
} else {
    if (!ap127.getQuestionSet().equals(ap124) && ap124.getQuestions().contains(ap127)) {
        ap124.getQuestions().remove(ap127);
        JPA.em().persist(ap124);
    }
    if (ap127.getQuestionSet().equals(ap124) && !ap124.getQuestions().contains(ap127)) {
        ap124.getQuestions().add(ap127);
        JPA.em().persist(ap124);
    }
    ap127.setOrderIndex(0);
    ((ValueSelectionQuestion)ap127).setCodeList(CodeList.CARBURANT);
    JPA.em().persist(ap127);
}

    }
    private void createQuestionAP131() {
        // == AP131
        // Consommation moyenne (L/100km)

        ap131 = (IntegerQuestion) questionService.findByCode(QuestionCode.AP131);
if (ap131 == null) {
    ap131 = new IntegerQuestion(ap124, 0, QuestionCode.AP131, null);
    JPA.em().persist(ap131);

    // cleanup the driver
    Driver ap131_driver = driverService.findByName("AP131");
    if (ap131_driver != null) {
        driverService.remove(ap131_driver);
    }

} else {
    if (!ap131.getQuestionSet().equals(ap124) && ap124.getQuestions().contains(ap131)) {
        ap124.getQuestions().remove(ap131);
        JPA.em().persist(ap124);
    }
    if (ap131.getQuestionSet().equals(ap124) && !ap124.getQuestions().contains(ap131)) {
        ap124.getQuestions().add(ap131);
        JPA.em().persist(ap124);
    }
    ap131.setOrderIndex(0);
    ((NumericQuestion)ap131).setUnitCategory(null);

    // cleanup the driver
    Driver ap131_driver = driverService.findByName("AP131");
    if (ap131_driver != null) {
        driverService.remove(ap131_driver);
    }

    ((NumericQuestion)ap131).setDriver(null);

    JPA.em().persist(ap131);
}

    }
    private void createQuestionAP132() {
        // == AP132
        // Quelle est le nombre de kilomètres parcourus par an?

        
ap132 = (DoubleQuestion) questionService.findByCode(QuestionCode.AP132);
if (ap132 == null) {
    ap132 = new DoubleQuestion( ap124, 0, QuestionCode.AP132, lengthUnits, getUnitBySymbol("km") );
    JPA.em().persist(ap132);

    // cleanup the driver
    Driver ap132_driver = driverService.findByName("AP132");
    if (ap132_driver != null) {
        driverService.remove(ap132_driver);
    }

} else {
    if (!ap132.getQuestionSet().equals(ap124) && ap124.getQuestions().contains(ap132)) {
        ap124.getQuestions().remove(ap132);
        JPA.em().persist(ap124);
    }
    if (ap132.getQuestionSet().equals(ap124) && !ap124.getQuestions().contains(ap132)) {
        ap124.getQuestions().add(ap132);
        JPA.em().persist(ap124);
    }
    ((NumericQuestion)ap132).setUnitCategory(lengthUnits);
    ap132.setOrderIndex(0);
    ((NumericQuestion)ap132).setDefaultUnit(getUnitBySymbol("km"));

    // cleanup the driver
    Driver ap132_driver = driverService.findByName("AP132");
    if (ap132_driver != null) {
        driverService.remove(ap132_driver);
    }

    ((NumericQuestion)ap132).setDriver(null);

    JPA.em().persist(ap132);
}



    }
    private void createQuestionAP133() {
        // == AP133
        // TGV (passagers.km)

        ap133 = (IntegerQuestion) questionService.findByCode(QuestionCode.AP133);
if (ap133 == null) {
    ap133 = new IntegerQuestion(ap123, 0, QuestionCode.AP133, null);
    JPA.em().persist(ap133);

    // cleanup the driver
    Driver ap133_driver = driverService.findByName("AP133");
    if (ap133_driver != null) {
        driverService.remove(ap133_driver);
    }

} else {
    if (!ap133.getQuestionSet().equals(ap123) && ap123.getQuestions().contains(ap133)) {
        ap123.getQuestions().remove(ap133);
        JPA.em().persist(ap123);
    }
    if (ap133.getQuestionSet().equals(ap123) && !ap123.getQuestions().contains(ap133)) {
        ap123.getQuestions().add(ap133);
        JPA.em().persist(ap123);
    }
    ap133.setOrderIndex(0);
    ((NumericQuestion)ap133).setUnitCategory(null);

    // cleanup the driver
    Driver ap133_driver = driverService.findByName("AP133");
    if (ap133_driver != null) {
        driverService.remove(ap133_driver);
    }

    ((NumericQuestion)ap133).setDriver(null);

    JPA.em().persist(ap133);
}

    }
    private void createQuestionAP135() {
        // == AP135
        // Catégorie de voyage

        ap135 = (StringQuestion) questionService.findByCode(QuestionCode.AP135);
if (ap135 == null) {
    ap135 = new StringQuestion(ap134, 0, QuestionCode.AP135, null);
    JPA.em().persist(ap135);
} else {
    ((StringQuestion)ap135).setDefaultValue(null);
    if (!ap135.getQuestionSet().equals(ap134) && ap134.getQuestions().contains(ap135)) {
        ap134.getQuestions().remove(ap135);
        JPA.em().persist(ap134);
    }
    if (ap135.getQuestionSet().equals(ap134) && !ap134.getQuestions().contains(ap135)) {
        ap134.getQuestions().add(ap135);
        JPA.em().persist(ap134);
    }
    ap135.setOrderIndex(0);
    JPA.em().persist(ap135);
}

    }
    private void createQuestionAP136() {
        // == AP136
        // Type de vol

        ap136 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AP136);
if (ap136 == null) {
    ap136 = new ValueSelectionQuestion(ap134, 0, QuestionCode.AP136, CodeList.TYPEVOL);
    JPA.em().persist(ap136);
} else {
    if (!ap136.getQuestionSet().equals(ap134) && ap134.getQuestions().contains(ap136)) {
        ap134.getQuestions().remove(ap136);
        JPA.em().persist(ap134);
    }
    if (ap136.getQuestionSet().equals(ap134) && !ap134.getQuestions().contains(ap136)) {
        ap134.getQuestions().add(ap136);
        JPA.em().persist(ap134);
    }
    ap136.setOrderIndex(0);
    ((ValueSelectionQuestion)ap136).setCodeList(CodeList.TYPEVOL);
    JPA.em().persist(ap136);
}

    }
    private void createQuestionAP137() {
        // == AP137
        // Classe du vol

        ap137 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AP137);
if (ap137 == null) {
    ap137 = new ValueSelectionQuestion(ap134, 0, QuestionCode.AP137, CodeList.CATEGORIEVOL);
    JPA.em().persist(ap137);
} else {
    if (!ap137.getQuestionSet().equals(ap134) && ap134.getQuestions().contains(ap137)) {
        ap134.getQuestions().remove(ap137);
        JPA.em().persist(ap134);
    }
    if (ap137.getQuestionSet().equals(ap134) && !ap134.getQuestions().contains(ap137)) {
        ap134.getQuestions().add(ap137);
        JPA.em().persist(ap134);
    }
    ap137.setOrderIndex(0);
    ((ValueSelectionQuestion)ap137).setCodeList(CodeList.CATEGORIEVOL);
    JPA.em().persist(ap137);
}

    }
    private void createQuestionAP138() {
        // == AP138
        // Nombre de vols/an

        ap138 = (IntegerQuestion) questionService.findByCode(QuestionCode.AP138);
if (ap138 == null) {
    ap138 = new IntegerQuestion(ap134, 0, QuestionCode.AP138, null);
    JPA.em().persist(ap138);

    // cleanup the driver
    Driver ap138_driver = driverService.findByName("AP138");
    if (ap138_driver != null) {
        driverService.remove(ap138_driver);
    }

} else {
    if (!ap138.getQuestionSet().equals(ap134) && ap134.getQuestions().contains(ap138)) {
        ap134.getQuestions().remove(ap138);
        JPA.em().persist(ap134);
    }
    if (ap138.getQuestionSet().equals(ap134) && !ap134.getQuestions().contains(ap138)) {
        ap134.getQuestions().add(ap138);
        JPA.em().persist(ap134);
    }
    ap138.setOrderIndex(0);
    ((NumericQuestion)ap138).setUnitCategory(null);

    // cleanup the driver
    Driver ap138_driver = driverService.findByName("AP138");
    if (ap138_driver != null) {
        driverService.remove(ap138_driver);
    }

    ((NumericQuestion)ap138).setDriver(null);

    JPA.em().persist(ap138);
}

    }
    private void createQuestionAP139() {
        // == AP139
        // Distance moyenne A/R (km)

        
ap139 = (DoubleQuestion) questionService.findByCode(QuestionCode.AP139);
if (ap139 == null) {
    ap139 = new DoubleQuestion( ap134, 0, QuestionCode.AP139, lengthUnits, getUnitBySymbol("km") );
    JPA.em().persist(ap139);

    // cleanup the driver
    Driver ap139_driver = driverService.findByName("AP139");
    if (ap139_driver != null) {
        driverService.remove(ap139_driver);
    }

} else {
    if (!ap139.getQuestionSet().equals(ap134) && ap134.getQuestions().contains(ap139)) {
        ap134.getQuestions().remove(ap139);
        JPA.em().persist(ap134);
    }
    if (ap139.getQuestionSet().equals(ap134) && !ap134.getQuestions().contains(ap139)) {
        ap134.getQuestions().add(ap139);
        JPA.em().persist(ap134);
    }
    ((NumericQuestion)ap139).setUnitCategory(lengthUnits);
    ap139.setOrderIndex(0);
    ((NumericQuestion)ap139).setDefaultUnit(getUnitBySymbol("km"));

    // cleanup the driver
    Driver ap139_driver = driverService.findByName("AP139");
    if (ap139_driver != null) {
        driverService.remove(ap139_driver);
    }

    ((NumericQuestion)ap139).setDriver(null);

    JPA.em().persist(ap139);
}



    }
    private void createQuestionAP141() {
        // == AP141
        // Pièces documentaires liées à la Logistique

        ap141 = (DocumentQuestion) questionService.findByCode(QuestionCode.AP141);
if (ap141 == null) {
    ap141 = new DocumentQuestion(ap140, 0, QuestionCode.AP141);
    JPA.em().persist(ap141);
} else {
    if (!ap141.getQuestionSet().equals(ap140) && ap140.getQuestions().contains(ap141)) {
        ap140.getQuestions().remove(ap141);
        JPA.em().persist(ap140);
    }
    if (ap141.getQuestionSet().equals(ap140) && !ap140.getQuestions().contains(ap141)) {
        ap140.getQuestions().add(ap141);
        JPA.em().persist(ap140);
    }
    ap141.setOrderIndex(0);
    JPA.em().persist(ap141);
}

    }
    private void createQuestionAP145() {
        // == AP145
        // Marchandise

        ap145 = (StringQuestion) questionService.findByCode(QuestionCode.AP145);
if (ap145 == null) {
    ap145 = new StringQuestion(ap144, 0, QuestionCode.AP145, null);
    JPA.em().persist(ap145);
} else {
    ((StringQuestion)ap145).setDefaultValue(null);
    if (!ap145.getQuestionSet().equals(ap144) && ap144.getQuestions().contains(ap145)) {
        ap144.getQuestions().remove(ap145);
        JPA.em().persist(ap144);
    }
    if (ap145.getQuestionSet().equals(ap144) && !ap144.getQuestions().contains(ap145)) {
        ap144.getQuestions().add(ap145);
        JPA.em().persist(ap144);
    }
    ap145.setOrderIndex(0);
    JPA.em().persist(ap145);
}

    }
    private void createQuestionAP146() {
        // == AP146
        // Poids total transporté:

        
ap146 = (DoubleQuestion) questionService.findByCode(QuestionCode.AP146);
if (ap146 == null) {
    ap146 = new DoubleQuestion( ap144, 0, QuestionCode.AP146, massUnits, getUnitBySymbol("t") );
    JPA.em().persist(ap146);

    // cleanup the driver
    Driver ap146_driver = driverService.findByName("AP146");
    if (ap146_driver != null) {
        driverService.remove(ap146_driver);
    }

} else {
    if (!ap146.getQuestionSet().equals(ap144) && ap144.getQuestions().contains(ap146)) {
        ap144.getQuestions().remove(ap146);
        JPA.em().persist(ap144);
    }
    if (ap146.getQuestionSet().equals(ap144) && !ap144.getQuestions().contains(ap146)) {
        ap144.getQuestions().add(ap146);
        JPA.em().persist(ap144);
    }
    ((NumericQuestion)ap146).setUnitCategory(massUnits);
    ap146.setOrderIndex(0);
    ((NumericQuestion)ap146).setDefaultUnit(getUnitBySymbol("t"));


    // cleanup the driver
    Driver ap146_driver = driverService.findByName("AP146");
    if (ap146_driver != null) {
        driverService.remove(ap146_driver);
    }

    ((NumericQuestion)ap146).setDriver(null);

    JPA.em().persist(ap146);
}



    }
    private void createQuestionAP147() {
        // == AP147
        // Distance totale entre le point de départ et le point d'arrivée de la marchandise:

        
ap147 = (DoubleQuestion) questionService.findByCode(QuestionCode.AP147);
if (ap147 == null) {
    ap147 = new DoubleQuestion( ap144, 0, QuestionCode.AP147, lengthUnits, getUnitBySymbol("km") );
    JPA.em().persist(ap147);

    // cleanup the driver
    Driver ap147_driver = driverService.findByName("AP147");
    if (ap147_driver != null) {
        driverService.remove(ap147_driver);
    }

} else {
    if (!ap147.getQuestionSet().equals(ap144) && ap144.getQuestions().contains(ap147)) {
        ap144.getQuestions().remove(ap147);
        JPA.em().persist(ap144);
    }
    if (ap147.getQuestionSet().equals(ap144) && !ap144.getQuestions().contains(ap147)) {
        ap144.getQuestions().add(ap147);
        JPA.em().persist(ap144);
    }
    ((NumericQuestion)ap147).setUnitCategory(lengthUnits);
    ap147.setOrderIndex(0);
    ((NumericQuestion)ap147).setDefaultUnit(getUnitBySymbol("km"));

    // cleanup the driver
    Driver ap147_driver = driverService.findByName("AP147");
    if (ap147_driver != null) {
        driverService.remove(ap147_driver);
    }

    ((NumericQuestion)ap147).setDriver(null);

    JPA.em().persist(ap147);
}



    }
    private void createQuestionAP148() {
        // == AP148
        // % de distance effectuée par transport routier local par camionnette

        ap148 = (PercentageQuestion) questionService.findByCode(QuestionCode.AP148);
if (ap148 == null) {
    ap148 = new PercentageQuestion(ap144, 0, QuestionCode.AP148);
    JPA.em().persist(ap148);

    // cleanup the driver
    Driver ap148_driver = driverService.findByName("AP148");
    if (ap148_driver != null) {
        driverService.remove(ap148_driver);
    }

} else {
    if (!ap148.getQuestionSet().equals(ap144) && ap144.getQuestions().contains(ap148)) {
        ap144.getQuestions().remove(ap148);
        JPA.em().persist(ap144);
    }
    if (ap148.getQuestionSet().equals(ap144) && !ap144.getQuestions().contains(ap148)) {
        ap144.getQuestions().add(ap148);
        JPA.em().persist(ap144);
    }
    ap148.setOrderIndex(0);

    // cleanup the driver
    Driver ap148_driver = driverService.findByName("AP148");
    if (ap148_driver != null) {
        driverService.remove(ap148_driver);
    }

    ((NumericQuestion)ap148).setDriver(null);

    JPA.em().persist(ap148);
}

    }
    private void createQuestionAP149() {
        // == AP149
        // % de distance effectuée par transport routier local par camion local

        ap149 = (PercentageQuestion) questionService.findByCode(QuestionCode.AP149);
if (ap149 == null) {
    ap149 = new PercentageQuestion(ap144, 0, QuestionCode.AP149);
    JPA.em().persist(ap149);

    // cleanup the driver
    Driver ap149_driver = driverService.findByName("AP149");
    if (ap149_driver != null) {
        driverService.remove(ap149_driver);
    }

} else {
    if (!ap149.getQuestionSet().equals(ap144) && ap144.getQuestions().contains(ap149)) {
        ap144.getQuestions().remove(ap149);
        JPA.em().persist(ap144);
    }
    if (ap149.getQuestionSet().equals(ap144) && !ap144.getQuestions().contains(ap149)) {
        ap144.getQuestions().add(ap149);
        JPA.em().persist(ap144);
    }
    ap149.setOrderIndex(0);

    // cleanup the driver
    Driver ap149_driver = driverService.findByName("AP149");
    if (ap149_driver != null) {
        driverService.remove(ap149_driver);
    }

    ((NumericQuestion)ap149).setDriver(null);

    JPA.em().persist(ap149);
}

    }
    private void createQuestionAP150() {
        // == AP150
        // % de distance effectuée par transport routier local par camion international

        ap150 = (PercentageQuestion) questionService.findByCode(QuestionCode.AP150);
if (ap150 == null) {
    ap150 = new PercentageQuestion(ap144, 0, QuestionCode.AP150);
    JPA.em().persist(ap150);

    // cleanup the driver
    Driver ap150_driver = driverService.findByName("AP150");
    if (ap150_driver != null) {
        driverService.remove(ap150_driver);
    }

} else {
    if (!ap150.getQuestionSet().equals(ap144) && ap144.getQuestions().contains(ap150)) {
        ap144.getQuestions().remove(ap150);
        JPA.em().persist(ap144);
    }
    if (ap150.getQuestionSet().equals(ap144) && !ap144.getQuestions().contains(ap150)) {
        ap144.getQuestions().add(ap150);
        JPA.em().persist(ap144);
    }
    ap150.setOrderIndex(0);

    // cleanup the driver
    Driver ap150_driver = driverService.findByName("AP150");
    if (ap150_driver != null) {
        driverService.remove(ap150_driver);
    }

    ((NumericQuestion)ap150).setDriver(null);

    JPA.em().persist(ap150);
}

    }
    private void createQuestionAP153() {
        // == AP153
        // Marchandise

        ap153 = (StringQuestion) questionService.findByCode(QuestionCode.AP153);
if (ap153 == null) {
    ap153 = new StringQuestion(ap152, 0, QuestionCode.AP153, null);
    JPA.em().persist(ap153);
} else {
    ((StringQuestion)ap153).setDefaultValue(null);
    if (!ap153.getQuestionSet().equals(ap152) && ap152.getQuestions().contains(ap153)) {
        ap152.getQuestions().remove(ap153);
        JPA.em().persist(ap152);
    }
    if (ap153.getQuestionSet().equals(ap152) && !ap152.getQuestions().contains(ap153)) {
        ap152.getQuestions().add(ap153);
        JPA.em().persist(ap152);
    }
    ap153.setOrderIndex(0);
    JPA.em().persist(ap153);
}

    }
    private void createQuestionAP154() {
        // == AP154
        // Poids total transporté:

        
ap154 = (DoubleQuestion) questionService.findByCode(QuestionCode.AP154);
if (ap154 == null) {
    ap154 = new DoubleQuestion( ap152, 0, QuestionCode.AP154, massUnits, getUnitBySymbol("t") );
    JPA.em().persist(ap154);

    // cleanup the driver
    Driver ap154_driver = driverService.findByName("AP154");
    if (ap154_driver != null) {
        driverService.remove(ap154_driver);
    }

} else {
    if (!ap154.getQuestionSet().equals(ap152) && ap152.getQuestions().contains(ap154)) {
        ap152.getQuestions().remove(ap154);
        JPA.em().persist(ap152);
    }
    if (ap154.getQuestionSet().equals(ap152) && !ap152.getQuestions().contains(ap154)) {
        ap152.getQuestions().add(ap154);
        JPA.em().persist(ap152);
    }
    ((NumericQuestion)ap154).setUnitCategory(massUnits);
    ap154.setOrderIndex(0);
    ((NumericQuestion)ap154).setDefaultUnit(getUnitBySymbol("t"));


    // cleanup the driver
    Driver ap154_driver = driverService.findByName("AP154");
    if (ap154_driver != null) {
        driverService.remove(ap154_driver);
    }

    ((NumericQuestion)ap154).setDriver(null);

    JPA.em().persist(ap154);
}



    }
    private void createQuestionAP155() {
        // == AP155
        // Distance totale entre le point de départ et le point d'arrivée de la marchandise:

        
ap155 = (DoubleQuestion) questionService.findByCode(QuestionCode.AP155);
if (ap155 == null) {
    ap155 = new DoubleQuestion( ap152, 0, QuestionCode.AP155, lengthUnits, getUnitBySymbol("km") );
    JPA.em().persist(ap155);

    // cleanup the driver
    Driver ap155_driver = driverService.findByName("AP155");
    if (ap155_driver != null) {
        driverService.remove(ap155_driver);
    }

} else {
    if (!ap155.getQuestionSet().equals(ap152) && ap152.getQuestions().contains(ap155)) {
        ap152.getQuestions().remove(ap155);
        JPA.em().persist(ap152);
    }
    if (ap155.getQuestionSet().equals(ap152) && !ap152.getQuestions().contains(ap155)) {
        ap152.getQuestions().add(ap155);
        JPA.em().persist(ap152);
    }
    ((NumericQuestion)ap155).setUnitCategory(lengthUnits);
    ap155.setOrderIndex(0);
    ((NumericQuestion)ap155).setDefaultUnit(getUnitBySymbol("km"));

    // cleanup the driver
    Driver ap155_driver = driverService.findByName("AP155");
    if (ap155_driver != null) {
        driverService.remove(ap155_driver);
    }

    ((NumericQuestion)ap155).setDriver(null);

    JPA.em().persist(ap155);
}



    }
    private void createQuestionAP156() {
        // == AP156
        // % de distance effectuée par transport routier local par camionnette

        ap156 = (PercentageQuestion) questionService.findByCode(QuestionCode.AP156);
if (ap156 == null) {
    ap156 = new PercentageQuestion(ap152, 0, QuestionCode.AP156);
    JPA.em().persist(ap156);

    // cleanup the driver
    Driver ap156_driver = driverService.findByName("AP156");
    if (ap156_driver != null) {
        driverService.remove(ap156_driver);
    }

} else {
    if (!ap156.getQuestionSet().equals(ap152) && ap152.getQuestions().contains(ap156)) {
        ap152.getQuestions().remove(ap156);
        JPA.em().persist(ap152);
    }
    if (ap156.getQuestionSet().equals(ap152) && !ap152.getQuestions().contains(ap156)) {
        ap152.getQuestions().add(ap156);
        JPA.em().persist(ap152);
    }
    ap156.setOrderIndex(0);

    // cleanup the driver
    Driver ap156_driver = driverService.findByName("AP156");
    if (ap156_driver != null) {
        driverService.remove(ap156_driver);
    }

    ((NumericQuestion)ap156).setDriver(null);

    JPA.em().persist(ap156);
}

    }
    private void createQuestionAP157() {
        // == AP157
        // % de distance effectuée par transport routier local par camion local

        ap157 = (PercentageQuestion) questionService.findByCode(QuestionCode.AP157);
if (ap157 == null) {
    ap157 = new PercentageQuestion(ap152, 0, QuestionCode.AP157);
    JPA.em().persist(ap157);

    // cleanup the driver
    Driver ap157_driver = driverService.findByName("AP157");
    if (ap157_driver != null) {
        driverService.remove(ap157_driver);
    }

} else {
    if (!ap157.getQuestionSet().equals(ap152) && ap152.getQuestions().contains(ap157)) {
        ap152.getQuestions().remove(ap157);
        JPA.em().persist(ap152);
    }
    if (ap157.getQuestionSet().equals(ap152) && !ap152.getQuestions().contains(ap157)) {
        ap152.getQuestions().add(ap157);
        JPA.em().persist(ap152);
    }
    ap157.setOrderIndex(0);

    // cleanup the driver
    Driver ap157_driver = driverService.findByName("AP157");
    if (ap157_driver != null) {
        driverService.remove(ap157_driver);
    }

    ((NumericQuestion)ap157).setDriver(null);

    JPA.em().persist(ap157);
}

    }
    private void createQuestionAP158() {
        // == AP158
        // % de distance effectuée par transport routier local par camion international

        ap158 = (PercentageQuestion) questionService.findByCode(QuestionCode.AP158);
if (ap158 == null) {
    ap158 = new PercentageQuestion(ap152, 0, QuestionCode.AP158);
    JPA.em().persist(ap158);

    // cleanup the driver
    Driver ap158_driver = driverService.findByName("AP158");
    if (ap158_driver != null) {
        driverService.remove(ap158_driver);
    }

} else {
    if (!ap158.getQuestionSet().equals(ap152) && ap152.getQuestions().contains(ap158)) {
        ap152.getQuestions().remove(ap158);
        JPA.em().persist(ap152);
    }
    if (ap158.getQuestionSet().equals(ap152) && !ap152.getQuestions().contains(ap158)) {
        ap152.getQuestions().add(ap158);
        JPA.em().persist(ap152);
    }
    ap158.setOrderIndex(0);

    // cleanup the driver
    Driver ap158_driver = driverService.findByName("AP158");
    if (ap158_driver != null) {
        driverService.remove(ap158_driver);
    }

    ((NumericQuestion)ap158).setDriver(null);

    JPA.em().persist(ap158);
}

    }
    private void createQuestionAP160() {
        // == AP160
        // Pièces documentaires liées à la gestion des déchets

        ap160 = (DocumentQuestion) questionService.findByCode(QuestionCode.AP160);
if (ap160 == null) {
    ap160 = new DocumentQuestion(ap159, 0, QuestionCode.AP160);
    JPA.em().persist(ap160);
} else {
    if (!ap160.getQuestionSet().equals(ap159) && ap159.getQuestions().contains(ap160)) {
        ap159.getQuestions().remove(ap160);
        JPA.em().persist(ap159);
    }
    if (ap160.getQuestionSet().equals(ap159) && !ap159.getQuestions().contains(ap160)) {
        ap159.getQuestions().add(ap160);
        JPA.em().persist(ap159);
    }
    ap160.setOrderIndex(0);
    JPA.em().persist(ap160);
}

    }
    private void createQuestionAP162() {
        // == AP162
        // Type de déchet, et traitement appliqué

        ap162 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AP162);
if (ap162 == null) {
    ap162 = new ValueSelectionQuestion(ap161, 0, QuestionCode.AP162, CodeList.TYPEDECHETMENAGE);
    JPA.em().persist(ap162);
} else {
    if (!ap162.getQuestionSet().equals(ap161) && ap161.getQuestions().contains(ap162)) {
        ap161.getQuestions().remove(ap162);
        JPA.em().persist(ap161);
    }
    if (ap162.getQuestionSet().equals(ap161) && !ap161.getQuestions().contains(ap162)) {
        ap161.getQuestions().add(ap162);
        JPA.em().persist(ap161);
    }
    ap162.setOrderIndex(0);
    ((ValueSelectionQuestion)ap162).setCodeList(CodeList.TYPEDECHETMENAGE);
    JPA.em().persist(ap162);
}

    }
    private void createQuestionAP163() {
        // == AP163
        // Type de données disponibles

        ap163 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AP163);
if (ap163 == null) {
    ap163 = new ValueSelectionQuestion(ap161, 0, QuestionCode.AP163, CodeList.MESUREDECHETMENAGE);
    JPA.em().persist(ap163);
} else {
    if (!ap163.getQuestionSet().equals(ap161) && ap161.getQuestions().contains(ap163)) {
        ap161.getQuestions().remove(ap163);
        JPA.em().persist(ap161);
    }
    if (ap163.getQuestionSet().equals(ap161) && !ap161.getQuestions().contains(ap163)) {
        ap161.getQuestions().add(ap163);
        JPA.em().persist(ap161);
    }
    ap163.setOrderIndex(0);
    ((ValueSelectionQuestion)ap163).setCodeList(CodeList.MESUREDECHETMENAGE);
    JPA.em().persist(ap163);
}

    }
    private void createQuestionAP164() {
        // == AP164
        // Indiquez le volume annuel jeté

        
ap164 = (DoubleQuestion) questionService.findByCode(QuestionCode.AP164);
if (ap164 == null) {
    ap164 = new DoubleQuestion( ap161, 0, QuestionCode.AP164, volumeUnits, getUnitBySymbol("m3") );
    JPA.em().persist(ap164);

    // cleanup the driver
    Driver ap164_driver = driverService.findByName("AP164");
    if (ap164_driver != null) {
        driverService.remove(ap164_driver);
    }

} else {
    if (!ap164.getQuestionSet().equals(ap161) && ap161.getQuestions().contains(ap164)) {
        ap161.getQuestions().remove(ap164);
        JPA.em().persist(ap161);
    }
    if (ap164.getQuestionSet().equals(ap161) && !ap161.getQuestions().contains(ap164)) {
        ap161.getQuestions().add(ap164);
        JPA.em().persist(ap161);
    }
    ((NumericQuestion)ap164).setUnitCategory(volumeUnits);
    ap164.setOrderIndex(0);
    ((NumericQuestion)ap164).setDefaultUnit(getUnitBySymbol("m3"));

    // cleanup the driver
    Driver ap164_driver = driverService.findByName("AP164");
    if (ap164_driver != null) {
        driverService.remove(ap164_driver);
    }

    ((NumericQuestion)ap164).setDriver(null);

    JPA.em().persist(ap164);
}



    }
    private void createQuestionAP165() {
        // == AP165
        // Indiquez la quantité annuelle jetée

        
ap165 = (DoubleQuestion) questionService.findByCode(QuestionCode.AP165);
if (ap165 == null) {
    ap165 = new DoubleQuestion( ap161, 0, QuestionCode.AP165, massUnits, getUnitBySymbol("t") );
    JPA.em().persist(ap165);

    // cleanup the driver
    Driver ap165_driver = driverService.findByName("AP165");
    if (ap165_driver != null) {
        driverService.remove(ap165_driver);
    }

} else {
    if (!ap165.getQuestionSet().equals(ap161) && ap161.getQuestions().contains(ap165)) {
        ap161.getQuestions().remove(ap165);
        JPA.em().persist(ap161);
    }
    if (ap165.getQuestionSet().equals(ap161) && !ap161.getQuestions().contains(ap165)) {
        ap161.getQuestions().add(ap165);
        JPA.em().persist(ap161);
    }
    ((NumericQuestion)ap165).setUnitCategory(massUnits);
    ap165.setOrderIndex(0);
    ((NumericQuestion)ap165).setDefaultUnit(getUnitBySymbol("t"));


    // cleanup the driver
    Driver ap165_driver = driverService.findByName("AP165");
    if (ap165_driver != null) {
        driverService.remove(ap165_driver);
    }

    ((NumericQuestion)ap165).setDriver(null);

    JPA.em().persist(ap165);
}



    }
    private void createQuestionAP166() {
        // == AP166
        // Indiquez la quantité annuelle jetée

        
ap166 = (DoubleQuestion) questionService.findByCode(QuestionCode.AP166);
if (ap166 == null) {
    ap166 = new DoubleQuestion( ap161, 0, QuestionCode.AP166, massUnits, getUnitBySymbol("t") );
    JPA.em().persist(ap166);

    // cleanup the driver
    Driver ap166_driver = driverService.findByName("AP166");
    if (ap166_driver != null) {
        driverService.remove(ap166_driver);
    }

} else {
    if (!ap166.getQuestionSet().equals(ap161) && ap161.getQuestions().contains(ap166)) {
        ap161.getQuestions().remove(ap166);
        JPA.em().persist(ap161);
    }
    if (ap166.getQuestionSet().equals(ap161) && !ap161.getQuestions().contains(ap166)) {
        ap161.getQuestions().add(ap166);
        JPA.em().persist(ap161);
    }
    ((NumericQuestion)ap166).setUnitCategory(massUnits);
    ap166.setOrderIndex(0);
    ((NumericQuestion)ap166).setDefaultUnit(getUnitBySymbol("t"));


    // cleanup the driver
    Driver ap166_driver = driverService.findByName("AP166");
    if (ap166_driver != null) {
        driverService.remove(ap166_driver);
    }

    ((NumericQuestion)ap166).setDriver(null);

    JPA.em().persist(ap166);
}



    }
    private void createQuestionAP188() {
        // == AP188
        // Pièces documentaires liées à l'utilisation de matières dans le cadre de votre activité

        ap188 = (DocumentQuestion) questionService.findByCode(QuestionCode.AP188);
if (ap188 == null) {
    ap188 = new DocumentQuestion(ap187, 0, QuestionCode.AP188);
    JPA.em().persist(ap188);
} else {
    if (!ap188.getQuestionSet().equals(ap187) && ap187.getQuestions().contains(ap188)) {
        ap187.getQuestions().remove(ap188);
        JPA.em().persist(ap187);
    }
    if (ap188.getQuestionSet().equals(ap187) && !ap187.getQuestions().contains(ap188)) {
        ap187.getQuestions().add(ap188);
        JPA.em().persist(ap187);
    }
    ap188.setOrderIndex(0);
    JPA.em().persist(ap188);
}

    }
    private void createQuestionAP190() {
        // == AP190
        // Plastiques

        
ap190 = (DoubleQuestion) questionService.findByCode(QuestionCode.AP190);
if (ap190 == null) {
    ap190 = new DoubleQuestion( ap189, 0, QuestionCode.AP190, massUnits, getUnitBySymbol("t") );
    JPA.em().persist(ap190);

    // cleanup the driver
    Driver ap190_driver = driverService.findByName("AP190");
    if (ap190_driver != null) {
        driverService.remove(ap190_driver);
    }

} else {
    if (!ap190.getQuestionSet().equals(ap189) && ap189.getQuestions().contains(ap190)) {
        ap189.getQuestions().remove(ap190);
        JPA.em().persist(ap189);
    }
    if (ap190.getQuestionSet().equals(ap189) && !ap189.getQuestions().contains(ap190)) {
        ap189.getQuestions().add(ap190);
        JPA.em().persist(ap189);
    }
    ((NumericQuestion)ap190).setUnitCategory(massUnits);
    ap190.setOrderIndex(0);
    ((NumericQuestion)ap190).setDefaultUnit(getUnitBySymbol("t"));


    // cleanup the driver
    Driver ap190_driver = driverService.findByName("AP190");
    if (ap190_driver != null) {
        driverService.remove(ap190_driver);
    }

    ((NumericQuestion)ap190).setDriver(null);

    JPA.em().persist(ap190);
}



    }
    private void createQuestionAP191() {
        // == AP191
        // Métaux

        
ap191 = (DoubleQuestion) questionService.findByCode(QuestionCode.AP191);
if (ap191 == null) {
    ap191 = new DoubleQuestion( ap189, 0, QuestionCode.AP191, massUnits, getUnitBySymbol("t") );
    JPA.em().persist(ap191);

    // cleanup the driver
    Driver ap191_driver = driverService.findByName("AP191");
    if (ap191_driver != null) {
        driverService.remove(ap191_driver);
    }

} else {
    if (!ap191.getQuestionSet().equals(ap189) && ap189.getQuestions().contains(ap191)) {
        ap189.getQuestions().remove(ap191);
        JPA.em().persist(ap189);
    }
    if (ap191.getQuestionSet().equals(ap189) && !ap189.getQuestions().contains(ap191)) {
        ap189.getQuestions().add(ap191);
        JPA.em().persist(ap189);
    }
    ((NumericQuestion)ap191).setUnitCategory(massUnits);
    ap191.setOrderIndex(0);
    ((NumericQuestion)ap191).setDefaultUnit(getUnitBySymbol("t"));


    // cleanup the driver
    Driver ap191_driver = driverService.findByName("AP191");
    if (ap191_driver != null) {
        driverService.remove(ap191_driver);
    }

    ((NumericQuestion)ap191).setDriver(null);

    JPA.em().persist(ap191);
}



    }
    private void createQuestionAP192() {
        // == AP192
        // Verre

        
ap192 = (DoubleQuestion) questionService.findByCode(QuestionCode.AP192);
if (ap192 == null) {
    ap192 = new DoubleQuestion( ap189, 0, QuestionCode.AP192, massUnits, getUnitBySymbol("t") );
    JPA.em().persist(ap192);

    // cleanup the driver
    Driver ap192_driver = driverService.findByName("AP192");
    if (ap192_driver != null) {
        driverService.remove(ap192_driver);
    }

} else {
    if (!ap192.getQuestionSet().equals(ap189) && ap189.getQuestions().contains(ap192)) {
        ap189.getQuestions().remove(ap192);
        JPA.em().persist(ap189);
    }
    if (ap192.getQuestionSet().equals(ap189) && !ap189.getQuestions().contains(ap192)) {
        ap189.getQuestions().add(ap192);
        JPA.em().persist(ap189);
    }
    ((NumericQuestion)ap192).setUnitCategory(massUnits);
    ap192.setOrderIndex(0);
    ((NumericQuestion)ap192).setDefaultUnit(getUnitBySymbol("t"));


    // cleanup the driver
    Driver ap192_driver = driverService.findByName("AP192");
    if (ap192_driver != null) {
        driverService.remove(ap192_driver);
    }

    ((NumericQuestion)ap192).setDriver(null);

    JPA.em().persist(ap192);
}



    }
    private void createQuestionAP193() {
        // == AP193
        // Papiers et Cartons

        
ap193 = (DoubleQuestion) questionService.findByCode(QuestionCode.AP193);
if (ap193 == null) {
    ap193 = new DoubleQuestion( ap189, 0, QuestionCode.AP193, massUnits, getUnitBySymbol("t") );
    JPA.em().persist(ap193);

    // cleanup the driver
    Driver ap193_driver = driverService.findByName("AP193");
    if (ap193_driver != null) {
        driverService.remove(ap193_driver);
    }

} else {
    if (!ap193.getQuestionSet().equals(ap189) && ap189.getQuestions().contains(ap193)) {
        ap189.getQuestions().remove(ap193);
        JPA.em().persist(ap189);
    }
    if (ap193.getQuestionSet().equals(ap189) && !ap189.getQuestions().contains(ap193)) {
        ap189.getQuestions().add(ap193);
        JPA.em().persist(ap189);
    }
    ((NumericQuestion)ap193).setUnitCategory(massUnits);
    ap193.setOrderIndex(0);
    ((NumericQuestion)ap193).setDefaultUnit(getUnitBySymbol("t"));


    // cleanup the driver
    Driver ap193_driver = driverService.findByName("AP193");
    if (ap193_driver != null) {
        driverService.remove(ap193_driver);
    }

    ((NumericQuestion)ap193).setDriver(null);

    JPA.em().persist(ap193);
}



    }
    private void createQuestionAP194() {
        // == AP194
        // Produits alimentaires

        
ap194 = (DoubleQuestion) questionService.findByCode(QuestionCode.AP194);
if (ap194 == null) {
    ap194 = new DoubleQuestion( ap189, 0, QuestionCode.AP194, massUnits, getUnitBySymbol("t") );
    JPA.em().persist(ap194);

    // cleanup the driver
    Driver ap194_driver = driverService.findByName("AP194");
    if (ap194_driver != null) {
        driverService.remove(ap194_driver);
    }

} else {
    if (!ap194.getQuestionSet().equals(ap189) && ap189.getQuestions().contains(ap194)) {
        ap189.getQuestions().remove(ap194);
        JPA.em().persist(ap189);
    }
    if (ap194.getQuestionSet().equals(ap189) && !ap189.getQuestions().contains(ap194)) {
        ap189.getQuestions().add(ap194);
        JPA.em().persist(ap189);
    }
    ((NumericQuestion)ap194).setUnitCategory(massUnits);
    ap194.setOrderIndex(0);
    ((NumericQuestion)ap194).setDefaultUnit(getUnitBySymbol("t"));


    // cleanup the driver
    Driver ap194_driver = driverService.findByName("AP194");
    if (ap194_driver != null) {
        driverService.remove(ap194_driver);
    }

    ((NumericQuestion)ap194).setDriver(null);

    JPA.em().persist(ap194);
}



    }
    private void createQuestionAP195() {
        // == AP195
        // Autres produits manufacturés

        
ap195 = (DoubleQuestion) questionService.findByCode(QuestionCode.AP195);
if (ap195 == null) {
    ap195 = new DoubleQuestion( ap189, 0, QuestionCode.AP195, massUnits, getUnitBySymbol("t") );
    JPA.em().persist(ap195);

    // cleanup the driver
    Driver ap195_driver = driverService.findByName("AP195");
    if (ap195_driver != null) {
        driverService.remove(ap195_driver);
    }

} else {
    if (!ap195.getQuestionSet().equals(ap189) && ap189.getQuestions().contains(ap195)) {
        ap189.getQuestions().remove(ap195);
        JPA.em().persist(ap189);
    }
    if (ap195.getQuestionSet().equals(ap189) && !ap189.getQuestions().contains(ap195)) {
        ap189.getQuestions().add(ap195);
        JPA.em().persist(ap189);
    }
    ((NumericQuestion)ap195).setUnitCategory(massUnits);
    ap195.setOrderIndex(0);
    ((NumericQuestion)ap195).setDefaultUnit(getUnitBySymbol("t"));


    // cleanup the driver
    Driver ap195_driver = driverService.findByName("AP195");
    if (ap195_driver != null) {
        driverService.remove(ap195_driver);
    }

    ((NumericQuestion)ap195).setDriver(null);

    JPA.em().persist(ap195);
}



    }
    private void createQuestionAP196() {
        // == AP196
        // TOTAL

        
ap196 = (DoubleQuestion) questionService.findByCode(QuestionCode.AP196);
if (ap196 == null) {
    ap196 = new DoubleQuestion( ap189, 0, QuestionCode.AP196, massUnits, getUnitBySymbol("t") );
    JPA.em().persist(ap196);

    // cleanup the driver
    Driver ap196_driver = driverService.findByName("AP196");
    if (ap196_driver != null) {
        driverService.remove(ap196_driver);
    }

} else {
    if (!ap196.getQuestionSet().equals(ap189) && ap189.getQuestions().contains(ap196)) {
        ap189.getQuestions().remove(ap196);
        JPA.em().persist(ap189);
    }
    if (ap196.getQuestionSet().equals(ap189) && !ap189.getQuestions().contains(ap196)) {
        ap189.getQuestions().add(ap196);
        JPA.em().persist(ap189);
    }
    ((NumericQuestion)ap196).setUnitCategory(massUnits);
    ap196.setOrderIndex(0);
    ((NumericQuestion)ap196).setDefaultUnit(getUnitBySymbol("t"));


    // cleanup the driver
    Driver ap196_driver = driverService.findByName("AP196");
    if (ap196_driver != null) {
        driverService.remove(ap196_driver);
    }

    ((NumericQuestion)ap196).setDriver(null);

    JPA.em().persist(ap196);
}



    }


    private UnitCategory getUnitCategoryByCode(UnitCategoryCode unitCategoryCode) {
        return unitCategoryService.findByCode(unitCategoryCode);
    }

    private Unit getUnitBySymbol(String symbol) {
        return unitService.findBySymbol(symbol);
    }

}



