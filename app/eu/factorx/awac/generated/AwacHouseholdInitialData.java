package eu.factorx.awac.generated;

import eu.factorx.awac.models.data.answer.QuestionSetAnswer;
import eu.factorx.awac.models.data.answer.QuestionAnswer;
import eu.factorx.awac.models.forms.AwacCalculator;
import eu.factorx.awac.service.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import eu.factorx.awac.models.data.question.Driver;
import eu.factorx.awac.models.data.question.DriverValue;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;

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
public class AwacHouseholdInitialData {

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

    @Autowired
    private AwacCalculatorService awacCalculatorService;

    private Form form1,form2,form3,form4,form5;
    private QuestionSet am2,am3,am7,am13,am23,am24,am26,am48,am63,am72,am110,am111,am117,am118,am122,am126,am130,am500,am134,am140,am600,am143,am147,am150,am153,am156,am160,am168,am169,am171,am175;
    private Question am4,am5,am6,am8,am9,am10,am11,am12,am14,am15,am16,am17,am18,am19,am20,am21,am22,am25,am27,am28,am29,am30,am31,am32,am33,am34,am35,am36,am37,am38,am39,am40,am41,am42,am43,am44,am45,am46,am47,am49,am50,am51,am52,am53,am54,am55,am56,am57,am58,am59,am60,am61,am62,am64,am65,am66,am67,am68,am69,am70,am71,am73,am74,am75,am76,am77,am78,am79,am80,am81,am82,am83,am84,am85,am86,am87,am88,am89,am90,am91,am92,am93,am94,am95,am96,am97,am98,am99,am100,am101,am102,am103,am104,am105,am106,am107,am108,am109,am112,am113,am114,am115,am116,am119,am120,am121,am123,am124,am125,am127,am128,am129,am131,am132,am133,am135,am136,am137,am138,am139,am141,am142,am144,am145,am146,am148,am149,am151,am152,am154,am155,am157,am158,am159,am161,am166,am170,am172,am173,am174,am176,am177,am178,am179;

    private UnitCategory energyUnits;
    private UnitCategory massUnits;
    private UnitCategory volumeUnits;
    private UnitCategory lengthUnits;
    private UnitCategory areaUnits;
    private UnitCategory powerUnits;
    private UnitCategory moneyUnits;
    private UnitCategory timeUnits;

    private AwacCalculator awacCalculator;


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

        Logger.info("===> CREATE AWAC Household INITIAL DATA -- START");

        long startTime = System.currentTimeMillis();

        energyUnits  = getUnitCategoryByCode(UnitCategoryCode.ENERGY);
        massUnits    = getUnitCategoryByCode(UnitCategoryCode.MASS);
        volumeUnits  = getUnitCategoryByCode(UnitCategoryCode.VOLUME);
        lengthUnits  = getUnitCategoryByCode(UnitCategoryCode.LENGTH);
        areaUnits    = getUnitCategoryByCode(UnitCategoryCode.AREA);
        powerUnits   = getUnitCategoryByCode(UnitCategoryCode.POWER);
        moneyUnits   = getUnitCategoryByCode(UnitCategoryCode.CURRENCY);
        timeUnits    = getUnitCategoryByCode(UnitCategoryCode.DURATION);

        awacCalculator = awacCalculatorService.findByCode(new InterfaceTypeCode("household"));

        // delete old questions
		{
			List<Question> allQuestions = questionService.findAll();
            List<String> codes = Arrays.asList("AM4", "AM5", "AM6", "AM8", "AM9", "AM10", "AM11", "AM12", "AM14", "AM15", "AM16", "AM17", "AM18", "AM19", "AM20", "AM21", "AM22", "AM25", "AM27", "AM28", "AM29", "AM30", "AM31", "AM32", "AM33", "AM34", "AM35", "AM36", "AM37", "AM38", "AM39", "AM40", "AM41", "AM42", "AM43", "AM44", "AM45", "AM46", "AM47", "AM49", "AM50", "AM51", "AM52", "AM53", "AM54", "AM55", "AM56", "AM57", "AM58", "AM59", "AM60", "AM61", "AM62", "AM64", "AM65", "AM66", "AM67", "AM68", "AM69", "AM70", "AM71", "AM73", "AM74", "AM75", "AM76", "AM77", "AM78", "AM79", "AM80", "AM81", "AM82", "AM83", "AM84", "AM85", "AM86", "AM87", "AM88", "AM89", "AM90", "AM91", "AM92", "AM93", "AM94", "AM95", "AM96", "AM97", "AM98", "AM99", "AM100", "AM101", "AM102", "AM103", "AM104", "AM105", "AM106", "AM107", "AM108", "AM109", "AM112", "AM113", "AM114", "AM115", "AM116", "AM119", "AM120", "AM121", "AM123", "AM124", "AM125", "AM127", "AM128", "AM129", "AM131", "AM132", "AM133", "AM135", "AM136", "AM137", "AM138", "AM139", "AM141", "AM142", "AM144", "AM145", "AM146", "AM148", "AM149", "AM151", "AM152", "AM154", "AM155", "AM157", "AM158", "AM159", "AM161", "AM166", "AM170", "AM172", "AM173", "AM174", "AM176", "AM177", "AM178", "AM179");

			for (Question q : new ArrayList<>(allQuestions)) {
				if (codes.contains(q.getCode().getKey()) || !q.getCode().getKey().matches("AM[0-9]+")) {
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
            List<String> codes = Arrays.asList("AM2", "AM3", "AM7", "AM13", "AM23", "AM24", "AM26", "AM48", "AM63", "AM72", "AM110", "AM111", "AM117", "AM118", "AM122", "AM126", "AM130", "AM500", "AM134", "AM140", "AM600", "AM143", "AM147", "AM150", "AM153", "AM156", "AM160", "AM168", "AM169", "AM171", "AM175");

			for (QuestionSet qs : new ArrayList<>(allQuestionSets)) {
				if (codes.contains(qs.getCode().getKey()) || !qs.getCode().getKey().matches("AM[0-9]+")) {
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

        createQuestionSetAM2();
        createQuestionSetAM3();
        createQuestionSetAM7();
        createQuestionSetAM13();
        createQuestionSetAM23();
        createQuestionSetAM24();
        createQuestionSetAM26();
        createQuestionSetAM48();
        createQuestionSetAM63();
        createQuestionSetAM72();
        createQuestionSetAM110();
        createQuestionSetAM111();
        createQuestionSetAM117();
        createQuestionSetAM118();
        createQuestionSetAM122();
        createQuestionSetAM126();
        createQuestionSetAM130();
        createQuestionSetAM500();
        createQuestionSetAM134();
        createQuestionSetAM140();
        createQuestionSetAM600();
        createQuestionSetAM143();
        createQuestionSetAM147();
        createQuestionSetAM150();
        createQuestionSetAM153();
        createQuestionSetAM156();
        createQuestionSetAM160();
        createQuestionSetAM168();
        createQuestionSetAM169();
        createQuestionSetAM171();
        createQuestionSetAM175();

        createQuestionAM4();
        createQuestionAM5();
        createQuestionAM6();
        createQuestionAM8();
        createQuestionAM9();
        createQuestionAM10();
        createQuestionAM11();
        createQuestionAM12();
        createQuestionAM14();
        createQuestionAM15();
        createQuestionAM16();
        createQuestionAM17();
        createQuestionAM18();
        createQuestionAM19();
        createQuestionAM20();
        createQuestionAM21();
        createQuestionAM22();
        createQuestionAM25();
        createQuestionAM27();
        createQuestionAM28();
        createQuestionAM29();
        createQuestionAM30();
        createQuestionAM31();
        createQuestionAM32();
        createQuestionAM33();
        createQuestionAM34();
        createQuestionAM35();
        createQuestionAM36();
        createQuestionAM37();
        createQuestionAM38();
        createQuestionAM39();
        createQuestionAM40();
        createQuestionAM41();
        createQuestionAM42();
        createQuestionAM43();
        createQuestionAM44();
        createQuestionAM45();
        createQuestionAM46();
        createQuestionAM47();
        createQuestionAM49();
        createQuestionAM50();
        createQuestionAM51();
        createQuestionAM52();
        createQuestionAM53();
        createQuestionAM54();
        createQuestionAM55();
        createQuestionAM56();
        createQuestionAM57();
        createQuestionAM58();
        createQuestionAM59();
        createQuestionAM60();
        createQuestionAM61();
        createQuestionAM62();
        createQuestionAM64();
        createQuestionAM65();
        createQuestionAM66();
        createQuestionAM67();
        createQuestionAM68();
        createQuestionAM69();
        createQuestionAM70();
        createQuestionAM71();
        createQuestionAM73();
        createQuestionAM74();
        createQuestionAM75();
        createQuestionAM76();
        createQuestionAM77();
        createQuestionAM78();
        createQuestionAM79();
        createQuestionAM80();
        createQuestionAM81();
        createQuestionAM82();
        createQuestionAM83();
        createQuestionAM84();
        createQuestionAM85();
        createQuestionAM86();
        createQuestionAM87();
        createQuestionAM88();
        createQuestionAM89();
        createQuestionAM90();
        createQuestionAM91();
        createQuestionAM92();
        createQuestionAM93();
        createQuestionAM94();
        createQuestionAM95();
        createQuestionAM96();
        createQuestionAM97();
        createQuestionAM98();
        createQuestionAM99();
        createQuestionAM100();
        createQuestionAM101();
        createQuestionAM102();
        createQuestionAM103();
        createQuestionAM104();
        createQuestionAM105();
        createQuestionAM106();
        createQuestionAM107();
        createQuestionAM108();
        createQuestionAM109();
        createQuestionAM112();
        createQuestionAM113();
        createQuestionAM114();
        createQuestionAM115();
        createQuestionAM116();
        createQuestionAM119();
        createQuestionAM120();
        createQuestionAM121();
        createQuestionAM123();
        createQuestionAM124();
        createQuestionAM125();
        createQuestionAM127();
        createQuestionAM128();
        createQuestionAM129();
        createQuestionAM131();
        createQuestionAM132();
        createQuestionAM133();
        createQuestionAM135();
        createQuestionAM136();
        createQuestionAM137();
        createQuestionAM138();
        createQuestionAM139();
        createQuestionAM141();
        createQuestionAM142();
        createQuestionAM144();
        createQuestionAM145();
        createQuestionAM146();
        createQuestionAM148();
        createQuestionAM149();
        createQuestionAM151();
        createQuestionAM152();
        createQuestionAM154();
        createQuestionAM155();
        createQuestionAM157();
        createQuestionAM158();
        createQuestionAM159();
        createQuestionAM161();
        createQuestionAM166();
        createQuestionAM170();
        createQuestionAM172();
        createQuestionAM173();
        createQuestionAM174();
        createQuestionAM176();
        createQuestionAM177();
        createQuestionAM178();
        createQuestionAM179();


        Logger.info("===> CREATE AWAC Household INITIAL DATA -- END (Took {} milliseconds)", (System.currentTimeMillis() - startTime));
    }

    // =========================================================================
    // FORMS
    // =========================================================================

    private void createForm1() {
        // == TAB_M1
        // DESCRIPTION DU MENAGE
        form1 = formService.findByIdentifier("TAB_M1");
        if (form1 == null) {
            form1 = new Form("TAB_M1");
            form1.setAwacCalculator(awacCalculator);
            JPA.em().persist(form1);
        }
    }
    private void createForm2() {
        // == TAB_M2
        // LOGEMENT
        form2 = formService.findByIdentifier("TAB_M2");
        if (form2 == null) {
            form2 = new Form("TAB_M2");
            form2.setAwacCalculator(awacCalculator);
            JPA.em().persist(form2);
        }
    }
    private void createForm3() {
        // == TAB_M3
        // MOBILITE
        form3 = formService.findByIdentifier("TAB_M3");
        if (form3 == null) {
            form3 = new Form("TAB_M3");
            form3.setAwacCalculator(awacCalculator);
            JPA.em().persist(form3);
        }
    }
    private void createForm4() {
        // == TAB_M4
        // DECHETS
        form4 = formService.findByIdentifier("TAB_M4");
        if (form4 == null) {
            form4 = new Form("TAB_M4");
            form4.setAwacCalculator(awacCalculator);
            JPA.em().persist(form4);
        }
    }
    private void createForm5() {
        // == TAB_M5
        // CONSOMMATION
        form5 = formService.findByIdentifier("TAB_M5");
        if (form5 == null) {
            form5 = new Form("TAB_M5");
            form5.setAwacCalculator(awacCalculator);
            JPA.em().persist(form5);
        }
    }


    // =========================================================================
    // QUESTION SETS
    // =========================================================================

    private void createQuestionSetAM2() {
        // == AM2
        // Introduction
        am2 = questionSetService.findByCode(QuestionCode.AM2);
        if( am2 == null ) {
            am2 = new QuestionSet(QuestionCode.AM2, false, null);
            JPA.em().persist(am2);
        }
        form1.getQuestionSets().add(am2);
        JPA.em().persist(form1);
    }
    private void createQuestionSetAM3() {
        // == AM3
        // Localisation
        am3 = questionSetService.findByCode(QuestionCode.AM3);
        if( am3 == null ) {
            am3 = new QuestionSet(QuestionCode.AM3, false, null);
            JPA.em().persist(am3);
        }
        form1.getQuestionSets().add(am3);
        JPA.em().persist(form1);
    }
    private void createQuestionSetAM7() {
        // == AM7
        // Ménage
        am7 = questionSetService.findByCode(QuestionCode.AM7);
        if( am7 == null ) {
            am7 = new QuestionSet(QuestionCode.AM7, false, null);
            JPA.em().persist(am7);
        }
        form1.getQuestionSets().add(am7);
        JPA.em().persist(form1);
    }
    private void createQuestionSetAM13() {
        // == AM13
        // Description du logement
        am13 = questionSetService.findByCode(QuestionCode.AM13);
        if( am13 == null ) {
            am13 = new QuestionSet(QuestionCode.AM13, false, null);
            JPA.em().persist(am13);
        }
        form2.getQuestionSets().add(am13);
        JPA.em().persist(form2);
    }
    private void createQuestionSetAM23() {
        // == AM23
        // Logement
        am23 = questionSetService.findByCode(QuestionCode.AM23);
        if( am23 == null ) {
            am23 = new QuestionSet(QuestionCode.AM23, false, null);
            JPA.em().persist(am23);
        }
        form2.getQuestionSets().add(am23);
        JPA.em().persist(form2);
    }
    private void createQuestionSetAM24() {
        // == AM24
        // Chauffage
        am24 = questionSetService.findByCode(QuestionCode.AM24);
        if( am24 == null ) {
            am24 = new QuestionSet(QuestionCode.AM24, false, null);
            JPA.em().persist(am24);
        }
        form2.getQuestionSets().add(am24);
        JPA.em().persist(form2);
    }
    private void createQuestionSetAM26() {
        // == AM26
        // Listez les différentes sources d'énergie pour le chauffage que vous utilisez
        am26 = questionSetService.findByCode(QuestionCode.AM26);
        if( am26 == null ) {
            am26 = new QuestionSet(QuestionCode.AM26, true, am24);
            JPA.em().persist(am26);
        }
    }
    private void createQuestionSetAM48() {
        // == AM48
        // Cuisson
        am48 = questionSetService.findByCode(QuestionCode.AM48);
        if( am48 == null ) {
            am48 = new QuestionSet(QuestionCode.AM48, false, null);
            JPA.em().persist(am48);
        }
        form2.getQuestionSets().add(am48);
        JPA.em().persist(form2);
    }
    private void createQuestionSetAM63() {
        // == AM63
        // Electricité
        am63 = questionSetService.findByCode(QuestionCode.AM63);
        if( am63 == null ) {
            am63 = new QuestionSet(QuestionCode.AM63, false, null);
            JPA.em().persist(am63);
        }
        form2.getQuestionSets().add(am63);
        JPA.em().persist(form2);
    }
    private void createQuestionSetAM72() {
        // == AM72
        // Equipements
        am72 = questionSetService.findByCode(QuestionCode.AM72);
        if( am72 == null ) {
            am72 = new QuestionSet(QuestionCode.AM72, false, am63);
            JPA.em().persist(am72);
        }
    }
    private void createQuestionSetAM110() {
        // == AM110
        // Route
        am110 = questionSetService.findByCode(QuestionCode.AM110);
        if( am110 == null ) {
            am110 = new QuestionSet(QuestionCode.AM110, false, null);
            JPA.em().persist(am110);
        }
        form3.getQuestionSets().add(am110);
        JPA.em().persist(form3);
    }
    private void createQuestionSetAM111() {
        // == AM111
        // Créez autant de véhicules que vous en possédez ou utilisez
        am111 = questionSetService.findByCode(QuestionCode.AM111);
        if( am111 == null ) {
            am111 = new QuestionSet(QuestionCode.AM111, true, am110);
            JPA.em().persist(am111);
        }
    }
    private void createQuestionSetAM117() {
        // == AM117
        // Transport public
        am117 = questionSetService.findByCode(QuestionCode.AM117);
        if( am117 == null ) {
            am117 = new QuestionSet(QuestionCode.AM117, false, null);
            JPA.em().persist(am117);
        }
        form3.getQuestionSets().add(am117);
        JPA.em().persist(form3);
    }
    private void createQuestionSetAM118() {
        // == AM118
        // Bus
        am118 = questionSetService.findByCode(QuestionCode.AM118);
        if( am118 == null ) {
            am118 = new QuestionSet(QuestionCode.AM118, false, am117);
            JPA.em().persist(am118);
        }
    }
    private void createQuestionSetAM122() {
        // == AM122
        // Train
        am122 = questionSetService.findByCode(QuestionCode.AM122);
        if( am122 == null ) {
            am122 = new QuestionSet(QuestionCode.AM122, false, am117);
            JPA.em().persist(am122);
        }
    }
    private void createQuestionSetAM126() {
        // == AM126
        // Tram
        am126 = questionSetService.findByCode(QuestionCode.AM126);
        if( am126 == null ) {
            am126 = new QuestionSet(QuestionCode.AM126, false, am117);
            JPA.em().persist(am126);
        }
    }
    private void createQuestionSetAM130() {
        // == AM130
        // Métro
        am130 = questionSetService.findByCode(QuestionCode.AM130);
        if( am130 == null ) {
            am130 = new QuestionSet(QuestionCode.AM130, false, am117);
            JPA.em().persist(am130);
        }
    }
    private void createQuestionSetAM500() {
        // == AM500
        // Avion
        am500 = questionSetService.findByCode(QuestionCode.AM500);
        if( am500 == null ) {
            am500 = new QuestionSet(QuestionCode.AM500, false, null);
            JPA.em().persist(am500);
        }
        form3.getQuestionSets().add(am500);
        JPA.em().persist(form3);
    }
    private void createQuestionSetAM134() {
        // == AM134
        // Créez autant de voyagesen avion que nécessaire
        am134 = questionSetService.findByCode(QuestionCode.AM134);
        if( am134 == null ) {
            am134 = new QuestionSet(QuestionCode.AM134, true, am500);
            JPA.em().persist(am134);
        }
    }
    private void createQuestionSetAM140() {
        // == AM140
        // TGV
        am140 = questionSetService.findByCode(QuestionCode.AM140);
        if( am140 == null ) {
            am140 = new QuestionSet(QuestionCode.AM140, false, null);
            JPA.em().persist(am140);
        }
        form3.getQuestionSets().add(am140);
        JPA.em().persist(form3);
    }
    private void createQuestionSetAM600() {
        // == AM600
        // Vélo
        am600 = questionSetService.findByCode(QuestionCode.AM600);
        if( am600 == null ) {
            am600 = new QuestionSet(QuestionCode.AM600, false, null);
            JPA.em().persist(am600);
        }
        form3.getQuestionSets().add(am600);
        JPA.em().persist(form3);
    }
    private void createQuestionSetAM143() {
        // == AM143
        // Ajoutez autant de vélos que souhaité
        am143 = questionSetService.findByCode(QuestionCode.AM143);
        if( am143 == null ) {
            am143 = new QuestionSet(QuestionCode.AM143, true, am600);
            JPA.em().persist(am143);
        }
    }
    private void createQuestionSetAM147() {
        // == AM147
        // Marche à pied
        am147 = questionSetService.findByCode(QuestionCode.AM147);
        if( am147 == null ) {
            am147 = new QuestionSet(QuestionCode.AM147, false, null);
            JPA.em().persist(am147);
        }
        form3.getQuestionSets().add(am147);
        JPA.em().persist(form3);
    }
    private void createQuestionSetAM150() {
        // == AM150
        // Déchets
        am150 = questionSetService.findByCode(QuestionCode.AM150);
        if( am150 == null ) {
            am150 = new QuestionSet(QuestionCode.AM150, false, null);
            JPA.em().persist(am150);
        }
        form4.getQuestionSets().add(am150);
        JPA.em().persist(form4);
    }
    private void createQuestionSetAM153() {
        // == AM153
        // Créez une rubrique pour chaque type de déchets que vous générez
        am153 = questionSetService.findByCode(QuestionCode.AM153);
        if( am153 == null ) {
            am153 = new QuestionSet(QuestionCode.AM153, true, am150);
            JPA.em().persist(am153);
        }
    }
    private void createQuestionSetAM156() {
        // == AM156
        // Estimation via le volume des sacs/conteneurs
        am156 = questionSetService.findByCode(QuestionCode.AM156);
        if( am156 == null ) {
            am156 = new QuestionSet(QuestionCode.AM156, false, am153);
            JPA.em().persist(am156);
        }
    }
    private void createQuestionSetAM160() {
        // == AM160
        // Estimation via les données de poids
        am160 = questionSetService.findByCode(QuestionCode.AM160);
        if( am160 == null ) {
            am160 = new QuestionSet(QuestionCode.AM160, false, am153);
            JPA.em().persist(am160);
        }
    }
    private void createQuestionSetAM168() {
        // == AM168
        // Consommation
        am168 = questionSetService.findByCode(QuestionCode.AM168);
        if( am168 == null ) {
            am168 = new QuestionSet(QuestionCode.AM168, false, null);
            JPA.em().persist(am168);
        }
        form5.getQuestionSets().add(am168);
        JPA.em().persist(form5);
    }
    private void createQuestionSetAM169() {
        // == AM169
        // Alimentation
        am169 = questionSetService.findByCode(QuestionCode.AM169);
        if( am169 == null ) {
            am169 = new QuestionSet(QuestionCode.AM169, false, null);
            JPA.em().persist(am169);
        }
        form5.getQuestionSets().add(am169);
        JPA.em().persist(form5);
    }
    private void createQuestionSetAM171() {
        // == AM171
        // Nombre de repas/semaine
        am171 = questionSetService.findByCode(QuestionCode.AM171);
        if( am171 == null ) {
            am171 = new QuestionSet(QuestionCode.AM171, false, am169);
            JPA.em().persist(am171);
        }
    }
    private void createQuestionSetAM175() {
        // == AM175
        // Achats de produits
        am175 = questionSetService.findByCode(QuestionCode.AM175);
        if( am175 == null ) {
            am175 = new QuestionSet(QuestionCode.AM175, false, null);
            JPA.em().persist(am175);
        }
        form5.getQuestionSets().add(am175);
        JPA.em().persist(form5);
    }


    // =========================================================================
    // QUESTIONS
    // =========================================================================

    private void createQuestionAM4() {
        // == AM4
        // Code Postal

        am4 = (StringQuestion) questionService.findByCode(QuestionCode.AM4);
if (am4 == null) {
    am4 = new StringQuestion(am3, 0, QuestionCode.AM4, null);
    JPA.em().persist(am4);
} else {
    ((StringQuestion)am4).setDefaultValue(null);
    if (!am4.getQuestionSet().equals(am3) && am3.getQuestions().contains(am4)) {
        am3.getQuestions().remove(am4);
        JPA.em().persist(am3);
    }
    if (am4.getQuestionSet().equals(am3) && !am3.getQuestions().contains(am4)) {
        am3.getQuestions().add(am4);
        JPA.em().persist(am3);
    }
    am4.setOrderIndex(0);
    JPA.em().persist(am4);
}

    }
    private void createQuestionAM5() {
        // == AM5
        // Localité

        am5 = (StringQuestion) questionService.findByCode(QuestionCode.AM5);
if (am5 == null) {
    am5 = new StringQuestion(am3, 0, QuestionCode.AM5, null);
    JPA.em().persist(am5);
} else {
    ((StringQuestion)am5).setDefaultValue(null);
    if (!am5.getQuestionSet().equals(am3) && am3.getQuestions().contains(am5)) {
        am3.getQuestions().remove(am5);
        JPA.em().persist(am3);
    }
    if (am5.getQuestionSet().equals(am3) && !am3.getQuestions().contains(am5)) {
        am3.getQuestions().add(am5);
        JPA.em().persist(am3);
    }
    am5.setOrderIndex(0);
    JPA.em().persist(am5);
}

    }
    private void createQuestionAM6() {
        // == AM6
        // Pays

        am6 = (StringQuestion) questionService.findByCode(QuestionCode.AM6);
if (am6 == null) {
    am6 = new StringQuestion(am3, 0, QuestionCode.AM6, null);
    JPA.em().persist(am6);
} else {
    ((StringQuestion)am6).setDefaultValue(null);
    if (!am6.getQuestionSet().equals(am3) && am3.getQuestions().contains(am6)) {
        am3.getQuestions().remove(am6);
        JPA.em().persist(am3);
    }
    if (am6.getQuestionSet().equals(am3) && !am3.getQuestions().contains(am6)) {
        am3.getQuestions().add(am6);
        JPA.em().persist(am3);
    }
    am6.setOrderIndex(0);
    JPA.em().persist(am6);
}

    }
    private void createQuestionAM8() {
        // == AM8
        // Nombre d'adultes

        am8 = (IntegerQuestion) questionService.findByCode(QuestionCode.AM8);
if (am8 == null) {
    am8 = new IntegerQuestion(am7, 0, QuestionCode.AM8, null);
    JPA.em().persist(am8);

    // cleanup the driver
    Driver am8_driver = driverService.findByName("AM8");
    if (am8_driver != null) {
        driverService.remove(am8_driver);
    }

} else {
    if (!am8.getQuestionSet().equals(am7) && am7.getQuestions().contains(am8)) {
        am7.getQuestions().remove(am8);
        JPA.em().persist(am7);
    }
    if (am8.getQuestionSet().equals(am7) && !am7.getQuestions().contains(am8)) {
        am7.getQuestions().add(am8);
        JPA.em().persist(am7);
    }
    am8.setOrderIndex(0);
    ((NumericQuestion)am8).setUnitCategory(null);

    // cleanup the driver
    Driver am8_driver = driverService.findByName("AM8");
    if (am8_driver != null) {
        driverService.remove(am8_driver);
    }

    ((NumericQuestion)am8).setDriver(null);

    JPA.em().persist(am8);
}

    }
    private void createQuestionAM9() {
        // == AM9
        // Nombre d'adolescents (12-18 ans)

        am9 = (IntegerQuestion) questionService.findByCode(QuestionCode.AM9);
if (am9 == null) {
    am9 = new IntegerQuestion(am7, 0, QuestionCode.AM9, null);
    JPA.em().persist(am9);

    // cleanup the driver
    Driver am9_driver = driverService.findByName("AM9");
    if (am9_driver != null) {
        driverService.remove(am9_driver);
    }

} else {
    if (!am9.getQuestionSet().equals(am7) && am7.getQuestions().contains(am9)) {
        am7.getQuestions().remove(am9);
        JPA.em().persist(am7);
    }
    if (am9.getQuestionSet().equals(am7) && !am7.getQuestions().contains(am9)) {
        am7.getQuestions().add(am9);
        JPA.em().persist(am7);
    }
    am9.setOrderIndex(0);
    ((NumericQuestion)am9).setUnitCategory(null);

    // cleanup the driver
    Driver am9_driver = driverService.findByName("AM9");
    if (am9_driver != null) {
        driverService.remove(am9_driver);
    }

    ((NumericQuestion)am9).setDriver(null);

    JPA.em().persist(am9);
}

    }
    private void createQuestionAM10() {
        // == AM10
        // Nombre d'enfants (0-11 ans)

        am10 = (IntegerQuestion) questionService.findByCode(QuestionCode.AM10);
if (am10 == null) {
    am10 = new IntegerQuestion(am7, 0, QuestionCode.AM10, null);
    JPA.em().persist(am10);

    // cleanup the driver
    Driver am10_driver = driverService.findByName("AM10");
    if (am10_driver != null) {
        driverService.remove(am10_driver);
    }

} else {
    if (!am10.getQuestionSet().equals(am7) && am7.getQuestions().contains(am10)) {
        am7.getQuestions().remove(am10);
        JPA.em().persist(am7);
    }
    if (am10.getQuestionSet().equals(am7) && !am7.getQuestions().contains(am10)) {
        am7.getQuestions().add(am10);
        JPA.em().persist(am7);
    }
    am10.setOrderIndex(0);
    ((NumericQuestion)am10).setUnitCategory(null);

    // cleanup the driver
    Driver am10_driver = driverService.findByName("AM10");
    if (am10_driver != null) {
        driverService.remove(am10_driver);
    }

    ((NumericQuestion)am10).setDriver(null);

    JPA.em().persist(am10);
}

    }
    private void createQuestionAM11() {
        // == AM11
        // Nombre de personnes dans le ménage

        am11 = (IntegerQuestion) questionService.findByCode(QuestionCode.AM11);
if (am11 == null) {
    am11 = new IntegerQuestion(am7, 0, QuestionCode.AM11, null);
    JPA.em().persist(am11);

    // cleanup the driver
    Driver am11_driver = driverService.findByName("AM11");
    if (am11_driver != null) {
        driverService.remove(am11_driver);
    }

} else {
    if (!am11.getQuestionSet().equals(am7) && am7.getQuestions().contains(am11)) {
        am7.getQuestions().remove(am11);
        JPA.em().persist(am7);
    }
    if (am11.getQuestionSet().equals(am7) && !am7.getQuestions().contains(am11)) {
        am7.getQuestions().add(am11);
        JPA.em().persist(am7);
    }
    am11.setOrderIndex(0);
    ((NumericQuestion)am11).setUnitCategory(null);

    // cleanup the driver
    Driver am11_driver = driverService.findByName("AM11");
    if (am11_driver != null) {
        driverService.remove(am11_driver);
    }

    ((NumericQuestion)am11).setDriver(null);

    JPA.em().persist(am11);
}

    }
    private void createQuestionAM12() {
        // == AM12
        // Revenus nets annuels moyens du ménage

        am12 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AM12);
if (am12 == null) {
    am12 = new ValueSelectionQuestion(am7, 0, QuestionCode.AM12, CodeList.TRANCHEREVENUS);
    JPA.em().persist(am12);
} else {
    if (!am12.getQuestionSet().equals(am7) && am7.getQuestions().contains(am12)) {
        am7.getQuestions().remove(am12);
        JPA.em().persist(am7);
    }
    if (am12.getQuestionSet().equals(am7) && !am7.getQuestions().contains(am12)) {
        am7.getQuestions().add(am12);
        JPA.em().persist(am7);
    }
    am12.setOrderIndex(0);
    ((ValueSelectionQuestion)am12).setCodeList(CodeList.TRANCHEREVENUS);
    JPA.em().persist(am12);
}

    }
    private void createQuestionAM14() {
        // == AM14
        // Type d'habitation

        am14 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AM14);
if (am14 == null) {
    am14 = new ValueSelectionQuestion(am13, 0, QuestionCode.AM14, CodeList.TYPELOGEMENT);
    JPA.em().persist(am14);
} else {
    if (!am14.getQuestionSet().equals(am13) && am13.getQuestions().contains(am14)) {
        am13.getQuestions().remove(am14);
        JPA.em().persist(am13);
    }
    if (am14.getQuestionSet().equals(am13) && !am13.getQuestions().contains(am14)) {
        am13.getQuestions().add(am14);
        JPA.em().persist(am13);
    }
    am14.setOrderIndex(0);
    ((ValueSelectionQuestion)am14).setCodeList(CodeList.TYPELOGEMENT);
    JPA.em().persist(am14);
}

    }
    private void createQuestionAM15() {
        // == AM15
        // Surface habitable (m²)

        

am15 = (DoubleQuestion) questionService.findByCode(QuestionCode.AM15);
if (am15 == null) {
    am15 = new DoubleQuestion( am13, 0, QuestionCode.AM15, areaUnits, areaUnits.getMainUnit() );
    JPA.em().persist(am15);

    // cleanup the driver
    Driver am15_driver = driverService.findByName("AM15");
    if (am15_driver != null) {
        driverService.remove(am15_driver);
    }


} else {
    if (!am15.getQuestionSet().equals(am13) && am13.getQuestions().contains(am15)) {
        am13.getQuestions().remove(am15);
        JPA.em().persist(am13);
    }
    if (am15.getQuestionSet().equals(am13) && !am13.getQuestions().contains(am15)) {
        am13.getQuestions().add(am15);
        JPA.em().persist(am13);
    }
    ((NumericQuestion)am15).setUnitCategory(areaUnits);
    am15.setOrderIndex(0);
    ((NumericQuestion)am15).setDefaultUnit(areaUnits.getMainUnit());

    // cleanup the driver
    Driver am15_driver = driverService.findByName("AM15");
    if (am15_driver != null) {
        driverService.remove(am15_driver);
    }

    ((NumericQuestion)am15).setDriver(null);

    JPA.em().persist(am15);
}



    }
    private void createQuestionAM16() {
        // == AM16
        // Surface habitable de l'immeuble (m²)

        

am16 = (DoubleQuestion) questionService.findByCode(QuestionCode.AM16);
if (am16 == null) {
    am16 = new DoubleQuestion( am13, 0, QuestionCode.AM16, areaUnits, areaUnits.getMainUnit() );
    JPA.em().persist(am16);

    // cleanup the driver
    Driver am16_driver = driverService.findByName("AM16");
    if (am16_driver != null) {
        driverService.remove(am16_driver);
    }


} else {
    if (!am16.getQuestionSet().equals(am13) && am13.getQuestions().contains(am16)) {
        am13.getQuestions().remove(am16);
        JPA.em().persist(am13);
    }
    if (am16.getQuestionSet().equals(am13) && !am13.getQuestions().contains(am16)) {
        am13.getQuestions().add(am16);
        JPA.em().persist(am13);
    }
    ((NumericQuestion)am16).setUnitCategory(areaUnits);
    am16.setOrderIndex(0);
    ((NumericQuestion)am16).setDefaultUnit(areaUnits.getMainUnit());

    // cleanup the driver
    Driver am16_driver = driverService.findByName("AM16");
    if (am16_driver != null) {
        driverService.remove(am16_driver);
    }

    ((NumericQuestion)am16).setDriver(null);

    JPA.em().persist(am16);
}



    }
    private void createQuestionAM17() {
        // == AM17
        // Nombre de pièces chauffées

        am17 = (IntegerQuestion) questionService.findByCode(QuestionCode.AM17);
if (am17 == null) {
    am17 = new IntegerQuestion(am13, 0, QuestionCode.AM17, null);
    JPA.em().persist(am17);

    // cleanup the driver
    Driver am17_driver = driverService.findByName("AM17");
    if (am17_driver != null) {
        driverService.remove(am17_driver);
    }

} else {
    if (!am17.getQuestionSet().equals(am13) && am13.getQuestions().contains(am17)) {
        am13.getQuestions().remove(am17);
        JPA.em().persist(am13);
    }
    if (am17.getQuestionSet().equals(am13) && !am13.getQuestions().contains(am17)) {
        am13.getQuestions().add(am17);
        JPA.em().persist(am13);
    }
    am17.setOrderIndex(0);
    ((NumericQuestion)am17).setUnitCategory(null);

    // cleanup the driver
    Driver am17_driver = driverService.findByName("AM17");
    if (am17_driver != null) {
        driverService.remove(am17_driver);
    }

    ((NumericQuestion)am17).setDriver(null);

    JPA.em().persist(am17);
}

    }
    private void createQuestionAM18() {
        // == AM18
        // Construction avant 1975

        am18 = (BooleanQuestion) questionService.findByCode(QuestionCode.AM18);
if (am18 == null) {
    am18 = new BooleanQuestion(am13, 0, QuestionCode.AM18, null);
    JPA.em().persist(am18);
} else {
    ((BooleanQuestion)am18).setDefaultValue(null);
    if (!am18.getQuestionSet().equals(am13) && am13.getQuestions().contains(am18)) {
        am13.getQuestions().remove(am18);
        JPA.em().persist(am13);
    }
    if (am18.getQuestionSet().equals(am13) && !am13.getQuestions().contains(am18)) {
        am13.getQuestions().add(am18);
        JPA.em().persist(am13);
    }
    am18.setOrderIndex(0);
    JPA.em().persist(am18);
}

    }
    private void createQuestionAM19() {
        // == AM19
        // Avez-vous fait de l'isolation ces 10 dernières années?

        am19 = (BooleanQuestion) questionService.findByCode(QuestionCode.AM19);
if (am19 == null) {
    am19 = new BooleanQuestion(am13, 0, QuestionCode.AM19, null);
    JPA.em().persist(am19);
} else {
    ((BooleanQuestion)am19).setDefaultValue(null);
    if (!am19.getQuestionSet().equals(am13) && am13.getQuestions().contains(am19)) {
        am13.getQuestions().remove(am19);
        JPA.em().persist(am13);
    }
    if (am19.getQuestionSet().equals(am13) && !am13.getQuestions().contains(am19)) {
        am13.getQuestions().add(am19);
        JPA.em().persist(am13);
    }
    am19.setOrderIndex(0);
    JPA.em().persist(am19);
}

    }
    private void createQuestionAM20() {
        // == AM20
        // Isolation du toit

        am20 = (BooleanQuestion) questionService.findByCode(QuestionCode.AM20);
if (am20 == null) {
    am20 = new BooleanQuestion(am13, 0, QuestionCode.AM20, null);
    JPA.em().persist(am20);
} else {
    ((BooleanQuestion)am20).setDefaultValue(null);
    if (!am20.getQuestionSet().equals(am13) && am13.getQuestions().contains(am20)) {
        am13.getQuestions().remove(am20);
        JPA.em().persist(am13);
    }
    if (am20.getQuestionSet().equals(am13) && !am13.getQuestions().contains(am20)) {
        am13.getQuestions().add(am20);
        JPA.em().persist(am13);
    }
    am20.setOrderIndex(0);
    JPA.em().persist(am20);
}

    }
    private void createQuestionAM21() {
        // == AM21
        // Isolation des murs extérieurs

        am21 = (BooleanQuestion) questionService.findByCode(QuestionCode.AM21);
if (am21 == null) {
    am21 = new BooleanQuestion(am13, 0, QuestionCode.AM21, null);
    JPA.em().persist(am21);
} else {
    ((BooleanQuestion)am21).setDefaultValue(null);
    if (!am21.getQuestionSet().equals(am13) && am13.getQuestions().contains(am21)) {
        am13.getQuestions().remove(am21);
        JPA.em().persist(am13);
    }
    if (am21.getQuestionSet().equals(am13) && !am13.getQuestions().contains(am21)) {
        am13.getQuestions().add(am21);
        JPA.em().persist(am13);
    }
    am21.setOrderIndex(0);
    JPA.em().persist(am21);
}

    }
    private void createQuestionAM22() {
        // == AM22
        // Double vitrage

        am22 = (BooleanQuestion) questionService.findByCode(QuestionCode.AM22);
if (am22 == null) {
    am22 = new BooleanQuestion(am13, 0, QuestionCode.AM22, null);
    JPA.em().persist(am22);
} else {
    ((BooleanQuestion)am22).setDefaultValue(null);
    if (!am22.getQuestionSet().equals(am13) && am13.getQuestions().contains(am22)) {
        am13.getQuestions().remove(am22);
        JPA.em().persist(am13);
    }
    if (am22.getQuestionSet().equals(am13) && !am13.getQuestions().contains(am22)) {
        am13.getQuestions().add(am22);
        JPA.em().persist(am13);
    }
    am22.setOrderIndex(0);
    JPA.em().persist(am22);
}

    }
    private void createQuestionAM25() {
        // == AM25
        // Pièces documentaires liées au chauffage

        am25 = (DocumentQuestion) questionService.findByCode(QuestionCode.AM25);
if (am25 == null) {
    am25 = new DocumentQuestion(am24, 0, QuestionCode.AM25);
    JPA.em().persist(am25);
} else {
    if (!am25.getQuestionSet().equals(am24) && am24.getQuestions().contains(am25)) {
        am24.getQuestions().remove(am25);
        JPA.em().persist(am24);
    }
    if (am25.getQuestionSet().equals(am24) && !am24.getQuestions().contains(am25)) {
        am24.getQuestions().add(am25);
        JPA.em().persist(am24);
    }
    am25.setOrderIndex(0);
    JPA.em().persist(am25);
}

    }
    private void createQuestionAM27() {
        // == AM27
        // Source d'énergie

        am27 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AM27);
if (am27 == null) {
    am27 = new ValueSelectionQuestion(am26, 0, QuestionCode.AM27, CodeList.COMBUSTIBLESIMPLEMENAGE);
    JPA.em().persist(am27);
} else {
    if (!am27.getQuestionSet().equals(am26) && am26.getQuestions().contains(am27)) {
        am26.getQuestions().remove(am27);
        JPA.em().persist(am26);
    }
    if (am27.getQuestionSet().equals(am26) && !am26.getQuestions().contains(am27)) {
        am26.getQuestions().add(am27);
        JPA.em().persist(am26);
    }
    am27.setOrderIndex(0);
    ((ValueSelectionQuestion)am27).setCodeList(CodeList.COMBUSTIBLESIMPLEMENAGE);
    JPA.em().persist(am27);
}

    }
    private void createQuestionAM28() {
        // == AM28
        // Cette consommation est-elle incluse dans la facture d'éléctricité?

        am28 = (BooleanQuestion) questionService.findByCode(QuestionCode.AM28);
if (am28 == null) {
    am28 = new BooleanQuestion(am26, 0, QuestionCode.AM28, null);
    JPA.em().persist(am28);
} else {
    ((BooleanQuestion)am28).setDefaultValue(null);
    if (!am28.getQuestionSet().equals(am26) && am26.getQuestions().contains(am28)) {
        am26.getQuestions().remove(am28);
        JPA.em().persist(am26);
    }
    if (am28.getQuestionSet().equals(am26) && !am26.getQuestions().contains(am28)) {
        am26.getQuestions().add(am28);
        JPA.em().persist(am26);
    }
    am28.setOrderIndex(0);
    JPA.em().persist(am28);
}

    }
    private void createQuestionAM29() {
        // == AM29
        // Type de données disponibles

        am29 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AM29);
if (am29 == null) {
    am29 = new ValueSelectionQuestion(am26, 0, QuestionCode.AM29, CodeList.TYPECONSOMMATIONCHAUFFAGE);
    JPA.em().persist(am29);
} else {
    if (!am29.getQuestionSet().equals(am26) && am26.getQuestions().contains(am29)) {
        am26.getQuestions().remove(am29);
        JPA.em().persist(am26);
    }
    if (am29.getQuestionSet().equals(am26) && !am26.getQuestions().contains(am29)) {
        am26.getQuestions().add(am29);
        JPA.em().persist(am26);
    }
    am29.setOrderIndex(0);
    ((ValueSelectionQuestion)am29).setCodeList(CodeList.TYPECONSOMMATIONCHAUFFAGE);
    JPA.em().persist(am29);
}

    }
    private void createQuestionAM30() {
        // == AM30
        // consommation annuelle

        
am30 = (DoubleQuestion) questionService.findByCode(QuestionCode.AM30);
if (am30 == null) {
    am30 = new DoubleQuestion( am26, 0, QuestionCode.AM30, volumeUnits, getUnitBySymbol("l") );
    JPA.em().persist(am30);

    // cleanup the driver
    Driver am30_driver = driverService.findByName("AM30");
    if (am30_driver != null) {
        driverService.remove(am30_driver);
    }

} else {
    if (!am30.getQuestionSet().equals(am26) && am26.getQuestions().contains(am30)) {
        am26.getQuestions().remove(am30);
        JPA.em().persist(am26);
    }
    if (am30.getQuestionSet().equals(am26) && !am26.getQuestions().contains(am30)) {
        am26.getQuestions().add(am30);
        JPA.em().persist(am26);
    }
    ((NumericQuestion)am30).setUnitCategory(volumeUnits);
    am30.setOrderIndex(0);
    ((NumericQuestion)am30).setDefaultUnit(getUnitBySymbol("l"));

    // cleanup the driver
    Driver am30_driver = driverService.findByName("AM30");
    if (am30_driver != null) {
        driverService.remove(am30_driver);
    }

    ((NumericQuestion)am30).setDriver(null);

    JPA.em().persist(am30);
}



    }
    private void createQuestionAM31() {
        // == AM31
        // consommation annuelle

        
am31 = (DoubleQuestion) questionService.findByCode(QuestionCode.AM31);
if (am31 == null) {
    am31 = new DoubleQuestion( am26, 0, QuestionCode.AM31, massUnits, getUnitBySymbol("kg") );
    JPA.em().persist(am31);

    // cleanup the driver
    Driver am31_driver = driverService.findByName("AM31");
    if (am31_driver != null) {
        driverService.remove(am31_driver);
    }

} else {
    if (!am31.getQuestionSet().equals(am26) && am26.getQuestions().contains(am31)) {
        am26.getQuestions().remove(am31);
        JPA.em().persist(am26);
    }
    if (am31.getQuestionSet().equals(am26) && !am26.getQuestions().contains(am31)) {
        am26.getQuestions().add(am31);
        JPA.em().persist(am26);
    }
    ((NumericQuestion)am31).setUnitCategory(massUnits);
    am31.setOrderIndex(0);
    ((NumericQuestion)am31).setDefaultUnit(getUnitBySymbol("kg"));


    // cleanup the driver
    Driver am31_driver = driverService.findByName("AM31");
    if (am31_driver != null) {
        driverService.remove(am31_driver);
    }

    ((NumericQuestion)am31).setDriver(null);

    JPA.em().persist(am31);
}



    }
    private void createQuestionAM32() {
        // == AM32
        // consommation annuelle

        
am32 = (DoubleQuestion) questionService.findByCode(QuestionCode.AM32);
if (am32 == null) {
    am32 = new DoubleQuestion( am26, 0, QuestionCode.AM32, energyUnits, getUnitBySymbol("kW.h") );
    JPA.em().persist(am32);

    // cleanup the driver
    Driver am32_driver = driverService.findByName("AM32");
    if (am32_driver != null) {
        driverService.remove(am32_driver);
    }


} else {
    if (!am32.getQuestionSet().equals(am26) && am26.getQuestions().contains(am32)) {
        am26.getQuestions().remove(am32);
        JPA.em().persist(am26);
    }
    if (am32.getQuestionSet().equals(am26) && !am26.getQuestions().contains(am32)) {
        am26.getQuestions().add(am32);
        JPA.em().persist(am26);
    }
    ((NumericQuestion)am32).setUnitCategory(energyUnits);
    am32.setOrderIndex(0);
    ((NumericQuestion)am32).setDefaultUnit(getUnitBySymbol("kW.h"));

    // cleanup the driver
    Driver am32_driver = driverService.findByName("AM32");
    if (am32_driver != null) {
        driverService.remove(am32_driver);
    }

    ((NumericQuestion)am32).setDriver(null);

    JPA.em().persist(am32);
}



    }
    private void createQuestionAM33() {
        // == AM33
        // coût annuel

        
am33 = (DoubleQuestion) questionService.findByCode(QuestionCode.AM33);
if (am33 == null) {
    am33 = new DoubleQuestion( am26, 0, QuestionCode.AM33, moneyUnits, getUnitBySymbol("EUR") );
    JPA.em().persist(am33);

    // cleanup the driver
    Driver am33_driver = driverService.findByName("AM33");
    if (am33_driver != null) {
        driverService.remove(am33_driver);
    }

} else {
    if (!am33.getQuestionSet().equals(am26) && am26.getQuestions().contains(am33)) {
        am26.getQuestions().remove(am33);
        JPA.em().persist(am26);
    }
    if (am33.getQuestionSet().equals(am26) && !am26.getQuestions().contains(am33)) {
        am26.getQuestions().add(am33);
        JPA.em().persist(am26);
    }
    ((NumericQuestion)am33).setUnitCategory(moneyUnits);
    am33.setOrderIndex(0);
    ((NumericQuestion)am33).setDefaultUnit(getUnitBySymbol("EUR"));

    // cleanup the driver
    Driver am33_driver = driverService.findByName("AM33");
    if (am33_driver != null) {
        driverService.remove(am33_driver);
    }

    ((NumericQuestion)am33).setDriver(null);

    JPA.em().persist(am33);
}



    }
    private void createQuestionAM34() {
        // == AM34
        // Prix unitaire du mazout (au litre)

        
am34 = (DoubleQuestion) questionService.findByCode(QuestionCode.AM34);
if (am34 == null) {
    am34 = new DoubleQuestion( am26, 0, QuestionCode.AM34, moneyUnits, getUnitBySymbol("EUR") );
    JPA.em().persist(am34);

    // cleanup the driver
    Driver am34_driver = driverService.findByName("AM34");
    if (am34_driver != null) {
        driverService.remove(am34_driver);
    }

    // recreate with good value
    am34_driver = new Driver("AM34");
    driverService.saveOrUpdate(am34_driver);
    Period p2000 = periodService.findByCode(PeriodCode.P2000);
    DriverValue dv = new DriverValue(am34_driver, p2000, Double.valueOf(0.8383));
    am34_driver.getDriverValueList().add(dv);;
    driverService.saveOrUpdate(am34_driver);
    JPA.em().persist(dv);

    ((NumericQuestion)am34).setDriver(am34_driver);
    JPA.em().persist(am34);
} else {
    if (!am34.getQuestionSet().equals(am26) && am26.getQuestions().contains(am34)) {
        am26.getQuestions().remove(am34);
        JPA.em().persist(am26);
    }
    if (am34.getQuestionSet().equals(am26) && !am26.getQuestions().contains(am34)) {
        am26.getQuestions().add(am34);
        JPA.em().persist(am26);
    }
    ((NumericQuestion)am34).setUnitCategory(moneyUnits);
    am34.setOrderIndex(0);
    ((NumericQuestion)am34).setDefaultUnit(getUnitBySymbol("EUR"));

    // cleanup the driver
    Driver am34_driver = driverService.findByName("AM34");
    if (am34_driver != null) {
        driverService.remove(am34_driver);
    }

    // recreate with good value
    am34_driver = new Driver("AM34");
    driverService.saveOrUpdate(am34_driver);
    Period p2000 = periodService.findByCode(PeriodCode.P2000);
    DriverValue dv = new DriverValue(am34_driver, p2000, Double.valueOf(0.8383));
    am34_driver.getDriverValueList().add(dv);;
    driverService.saveOrUpdate(am34_driver);
    JPA.em().persist(dv);

    ((NumericQuestion)am34).setDriver(am34_driver);

    JPA.em().persist(am34);
}



    }
    private void createQuestionAM35() {
        // == AM35
        // Prix unitaire du gaz naturel (au m³)

        
am35 = (DoubleQuestion) questionService.findByCode(QuestionCode.AM35);
if (am35 == null) {
    am35 = new DoubleQuestion( am26, 0, QuestionCode.AM35, moneyUnits, getUnitBySymbol("EUR") );
    JPA.em().persist(am35);

    // cleanup the driver
    Driver am35_driver = driverService.findByName("AM35");
    if (am35_driver != null) {
        driverService.remove(am35_driver);
    }

    // recreate with good value
    am35_driver = new Driver("AM35");
    driverService.saveOrUpdate(am35_driver);
    Period p2000 = periodService.findByCode(PeriodCode.P2000);
    DriverValue dv = new DriverValue(am35_driver, p2000, Double.valueOf(0.625));
    am35_driver.getDriverValueList().add(dv);;
    driverService.saveOrUpdate(am35_driver);
    JPA.em().persist(dv);

    ((NumericQuestion)am35).setDriver(am35_driver);
    JPA.em().persist(am35);
} else {
    if (!am35.getQuestionSet().equals(am26) && am26.getQuestions().contains(am35)) {
        am26.getQuestions().remove(am35);
        JPA.em().persist(am26);
    }
    if (am35.getQuestionSet().equals(am26) && !am26.getQuestions().contains(am35)) {
        am26.getQuestions().add(am35);
        JPA.em().persist(am26);
    }
    ((NumericQuestion)am35).setUnitCategory(moneyUnits);
    am35.setOrderIndex(0);
    ((NumericQuestion)am35).setDefaultUnit(getUnitBySymbol("EUR"));

    // cleanup the driver
    Driver am35_driver = driverService.findByName("AM35");
    if (am35_driver != null) {
        driverService.remove(am35_driver);
    }

    // recreate with good value
    am35_driver = new Driver("AM35");
    driverService.saveOrUpdate(am35_driver);
    Period p2000 = periodService.findByCode(PeriodCode.P2000);
    DriverValue dv = new DriverValue(am35_driver, p2000, Double.valueOf(0.625));
    am35_driver.getDriverValueList().add(dv);;
    driverService.saveOrUpdate(am35_driver);
    JPA.em().persist(dv);

    ((NumericQuestion)am35).setDriver(am35_driver);

    JPA.em().persist(am35);
}



    }
    private void createQuestionAM36() {
        // == AM36
        // Prix unitaire du propane (au m³)

        
am36 = (DoubleQuestion) questionService.findByCode(QuestionCode.AM36);
if (am36 == null) {
    am36 = new DoubleQuestion( am26, 0, QuestionCode.AM36, moneyUnits, getUnitBySymbol("EUR") );
    JPA.em().persist(am36);

    // cleanup the driver
    Driver am36_driver = driverService.findByName("AM36");
    if (am36_driver != null) {
        driverService.remove(am36_driver);
    }

    // recreate with good value
    am36_driver = new Driver("AM36");
    driverService.saveOrUpdate(am36_driver);
    Period p2000 = periodService.findByCode(PeriodCode.P2000);
    DriverValue dv = new DriverValue(am36_driver, p2000, Double.valueOf(0.625));
    am36_driver.getDriverValueList().add(dv);;
    driverService.saveOrUpdate(am36_driver);
    JPA.em().persist(dv);

    ((NumericQuestion)am36).setDriver(am36_driver);
    JPA.em().persist(am36);
} else {
    if (!am36.getQuestionSet().equals(am26) && am26.getQuestions().contains(am36)) {
        am26.getQuestions().remove(am36);
        JPA.em().persist(am26);
    }
    if (am36.getQuestionSet().equals(am26) && !am26.getQuestions().contains(am36)) {
        am26.getQuestions().add(am36);
        JPA.em().persist(am26);
    }
    ((NumericQuestion)am36).setUnitCategory(moneyUnits);
    am36.setOrderIndex(0);
    ((NumericQuestion)am36).setDefaultUnit(getUnitBySymbol("EUR"));

    // cleanup the driver
    Driver am36_driver = driverService.findByName("AM36");
    if (am36_driver != null) {
        driverService.remove(am36_driver);
    }

    // recreate with good value
    am36_driver = new Driver("AM36");
    driverService.saveOrUpdate(am36_driver);
    Period p2000 = periodService.findByCode(PeriodCode.P2000);
    DriverValue dv = new DriverValue(am36_driver, p2000, Double.valueOf(0.625));
    am36_driver.getDriverValueList().add(dv);;
    driverService.saveOrUpdate(am36_driver);
    JPA.em().persist(dv);

    ((NumericQuestion)am36).setDriver(am36_driver);

    JPA.em().persist(am36);
}



    }
    private void createQuestionAM37() {
        // == AM37
        // Prix unitaire du butane (au m³)

        
am37 = (DoubleQuestion) questionService.findByCode(QuestionCode.AM37);
if (am37 == null) {
    am37 = new DoubleQuestion( am26, 0, QuestionCode.AM37, moneyUnits, getUnitBySymbol("EUR") );
    JPA.em().persist(am37);

    // cleanup the driver
    Driver am37_driver = driverService.findByName("AM37");
    if (am37_driver != null) {
        driverService.remove(am37_driver);
    }

    // recreate with good value
    am37_driver = new Driver("AM37");
    driverService.saveOrUpdate(am37_driver);
    Period p2000 = periodService.findByCode(PeriodCode.P2000);
    DriverValue dv = new DriverValue(am37_driver, p2000, Double.valueOf(0.625));
    am37_driver.getDriverValueList().add(dv);;
    driverService.saveOrUpdate(am37_driver);
    JPA.em().persist(dv);

    ((NumericQuestion)am37).setDriver(am37_driver);
    JPA.em().persist(am37);
} else {
    if (!am37.getQuestionSet().equals(am26) && am26.getQuestions().contains(am37)) {
        am26.getQuestions().remove(am37);
        JPA.em().persist(am26);
    }
    if (am37.getQuestionSet().equals(am26) && !am26.getQuestions().contains(am37)) {
        am26.getQuestions().add(am37);
        JPA.em().persist(am26);
    }
    ((NumericQuestion)am37).setUnitCategory(moneyUnits);
    am37.setOrderIndex(0);
    ((NumericQuestion)am37).setDefaultUnit(getUnitBySymbol("EUR"));

    // cleanup the driver
    Driver am37_driver = driverService.findByName("AM37");
    if (am37_driver != null) {
        driverService.remove(am37_driver);
    }

    // recreate with good value
    am37_driver = new Driver("AM37");
    driverService.saveOrUpdate(am37_driver);
    Period p2000 = periodService.findByCode(PeriodCode.P2000);
    DriverValue dv = new DriverValue(am37_driver, p2000, Double.valueOf(0.625));
    am37_driver.getDriverValueList().add(dv);;
    driverService.saveOrUpdate(am37_driver);
    JPA.em().persist(dv);

    ((NumericQuestion)am37).setDriver(am37_driver);

    JPA.em().persist(am37);
}



    }
    private void createQuestionAM38() {
        // == AM38
        // Prix unitaire du bois (au kilo)

        
am38 = (DoubleQuestion) questionService.findByCode(QuestionCode.AM38);
if (am38 == null) {
    am38 = new DoubleQuestion( am26, 0, QuestionCode.AM38, moneyUnits, getUnitBySymbol("EUR") );
    JPA.em().persist(am38);

    // cleanup the driver
    Driver am38_driver = driverService.findByName("AM38");
    if (am38_driver != null) {
        driverService.remove(am38_driver);
    }

    // recreate with good value
    am38_driver = new Driver("AM38");
    driverService.saveOrUpdate(am38_driver);
    Period p2000 = periodService.findByCode(PeriodCode.P2000);
    DriverValue dv = new DriverValue(am38_driver, p2000, Double.valueOf(0.27));
    am38_driver.getDriverValueList().add(dv);;
    driverService.saveOrUpdate(am38_driver);
    JPA.em().persist(dv);

    ((NumericQuestion)am38).setDriver(am38_driver);
    JPA.em().persist(am38);
} else {
    if (!am38.getQuestionSet().equals(am26) && am26.getQuestions().contains(am38)) {
        am26.getQuestions().remove(am38);
        JPA.em().persist(am26);
    }
    if (am38.getQuestionSet().equals(am26) && !am26.getQuestions().contains(am38)) {
        am26.getQuestions().add(am38);
        JPA.em().persist(am26);
    }
    ((NumericQuestion)am38).setUnitCategory(moneyUnits);
    am38.setOrderIndex(0);
    ((NumericQuestion)am38).setDefaultUnit(getUnitBySymbol("EUR"));

    // cleanup the driver
    Driver am38_driver = driverService.findByName("AM38");
    if (am38_driver != null) {
        driverService.remove(am38_driver);
    }

    // recreate with good value
    am38_driver = new Driver("AM38");
    driverService.saveOrUpdate(am38_driver);
    Period p2000 = periodService.findByCode(PeriodCode.P2000);
    DriverValue dv = new DriverValue(am38_driver, p2000, Double.valueOf(0.27));
    am38_driver.getDriverValueList().add(dv);;
    driverService.saveOrUpdate(am38_driver);
    JPA.em().persist(dv);

    ((NumericQuestion)am38).setDriver(am38_driver);

    JPA.em().persist(am38);
}



    }
    private void createQuestionAM39() {
        // == AM39
        // Prix unitaire du charbon (au kilo)

        
am39 = (DoubleQuestion) questionService.findByCode(QuestionCode.AM39);
if (am39 == null) {
    am39 = new DoubleQuestion( am26, 0, QuestionCode.AM39, moneyUnits, getUnitBySymbol("EUR") );
    JPA.em().persist(am39);

    // cleanup the driver
    Driver am39_driver = driverService.findByName("AM39");
    if (am39_driver != null) {
        driverService.remove(am39_driver);
    }

    // recreate with good value
    am39_driver = new Driver("AM39");
    driverService.saveOrUpdate(am39_driver);
    Period p2000 = periodService.findByCode(PeriodCode.P2000);
    DriverValue dv = new DriverValue(am39_driver, p2000, Double.valueOf(0.35));
    am39_driver.getDriverValueList().add(dv);;
    driverService.saveOrUpdate(am39_driver);
    JPA.em().persist(dv);

    ((NumericQuestion)am39).setDriver(am39_driver);
    JPA.em().persist(am39);
} else {
    if (!am39.getQuestionSet().equals(am26) && am26.getQuestions().contains(am39)) {
        am26.getQuestions().remove(am39);
        JPA.em().persist(am26);
    }
    if (am39.getQuestionSet().equals(am26) && !am26.getQuestions().contains(am39)) {
        am26.getQuestions().add(am39);
        JPA.em().persist(am26);
    }
    ((NumericQuestion)am39).setUnitCategory(moneyUnits);
    am39.setOrderIndex(0);
    ((NumericQuestion)am39).setDefaultUnit(getUnitBySymbol("EUR"));

    // cleanup the driver
    Driver am39_driver = driverService.findByName("AM39");
    if (am39_driver != null) {
        driverService.remove(am39_driver);
    }

    // recreate with good value
    am39_driver = new Driver("AM39");
    driverService.saveOrUpdate(am39_driver);
    Period p2000 = periodService.findByCode(PeriodCode.P2000);
    DriverValue dv = new DriverValue(am39_driver, p2000, Double.valueOf(0.35));
    am39_driver.getDriverValueList().add(dv);;
    driverService.saveOrUpdate(am39_driver);
    JPA.em().persist(dv);

    ((NumericQuestion)am39).setDriver(am39_driver);

    JPA.em().persist(am39);
}



    }
    private void createQuestionAM40() {
        // == AM40
        // Prix unitaire de l'électricité (au kWh)

        
am40 = (DoubleQuestion) questionService.findByCode(QuestionCode.AM40);
if (am40 == null) {
    am40 = new DoubleQuestion( am26, 0, QuestionCode.AM40, moneyUnits, getUnitBySymbol("EUR") );
    JPA.em().persist(am40);

    // cleanup the driver
    Driver am40_driver = driverService.findByName("AM40");
    if (am40_driver != null) {
        driverService.remove(am40_driver);
    }

    // recreate with good value
    am40_driver = new Driver("AM40");
    driverService.saveOrUpdate(am40_driver);
    Period p2000 = periodService.findByCode(PeriodCode.P2000);
    DriverValue dv = new DriverValue(am40_driver, p2000, Double.valueOf(0.03));
    am40_driver.getDriverValueList().add(dv);;
    driverService.saveOrUpdate(am40_driver);
    JPA.em().persist(dv);

    ((NumericQuestion)am40).setDriver(am40_driver);
    JPA.em().persist(am40);
} else {
    if (!am40.getQuestionSet().equals(am26) && am26.getQuestions().contains(am40)) {
        am26.getQuestions().remove(am40);
        JPA.em().persist(am26);
    }
    if (am40.getQuestionSet().equals(am26) && !am26.getQuestions().contains(am40)) {
        am26.getQuestions().add(am40);
        JPA.em().persist(am26);
    }
    ((NumericQuestion)am40).setUnitCategory(moneyUnits);
    am40.setOrderIndex(0);
    ((NumericQuestion)am40).setDefaultUnit(getUnitBySymbol("EUR"));

    // cleanup the driver
    Driver am40_driver = driverService.findByName("AM40");
    if (am40_driver != null) {
        driverService.remove(am40_driver);
    }

    // recreate with good value
    am40_driver = new Driver("AM40");
    driverService.saveOrUpdate(am40_driver);
    Period p2000 = periodService.findByCode(PeriodCode.P2000);
    DriverValue dv = new DriverValue(am40_driver, p2000, Double.valueOf(0.03));
    am40_driver.getDriverValueList().add(dv);;
    driverService.saveOrUpdate(am40_driver);
    JPA.em().persist(dv);

    ((NumericQuestion)am40).setDriver(am40_driver);

    JPA.em().persist(am40);
}



    }
    private void createQuestionAM41() {
        // == AM41
        // Votre consommation estimée

        
am41 = (DoubleQuestion) questionService.findByCode(QuestionCode.AM41);
if (am41 == null) {
    am41 = new DoubleQuestion( am26, 0, QuestionCode.AM41, volumeUnits, getUnitBySymbol("l") );
    JPA.em().persist(am41);

    // cleanup the driver
    Driver am41_driver = driverService.findByName("AM41");
    if (am41_driver != null) {
        driverService.remove(am41_driver);
    }

} else {
    if (!am41.getQuestionSet().equals(am26) && am26.getQuestions().contains(am41)) {
        am26.getQuestions().remove(am41);
        JPA.em().persist(am26);
    }
    if (am41.getQuestionSet().equals(am26) && !am26.getQuestions().contains(am41)) {
        am26.getQuestions().add(am41);
        JPA.em().persist(am26);
    }
    ((NumericQuestion)am41).setUnitCategory(volumeUnits);
    am41.setOrderIndex(0);
    ((NumericQuestion)am41).setDefaultUnit(getUnitBySymbol("l"));

    // cleanup the driver
    Driver am41_driver = driverService.findByName("AM41");
    if (am41_driver != null) {
        driverService.remove(am41_driver);
    }

    ((NumericQuestion)am41).setDriver(null);

    JPA.em().persist(am41);
}



    }
    private void createQuestionAM42() {
        // == AM42
        // Votre consommation estimée

        
am42 = (DoubleQuestion) questionService.findByCode(QuestionCode.AM42);
if (am42 == null) {
    am42 = new DoubleQuestion( am26, 0, QuestionCode.AM42, volumeUnits, getUnitBySymbol("m3") );
    JPA.em().persist(am42);

    // cleanup the driver
    Driver am42_driver = driverService.findByName("AM42");
    if (am42_driver != null) {
        driverService.remove(am42_driver);
    }

} else {
    if (!am42.getQuestionSet().equals(am26) && am26.getQuestions().contains(am42)) {
        am26.getQuestions().remove(am42);
        JPA.em().persist(am26);
    }
    if (am42.getQuestionSet().equals(am26) && !am26.getQuestions().contains(am42)) {
        am26.getQuestions().add(am42);
        JPA.em().persist(am26);
    }
    ((NumericQuestion)am42).setUnitCategory(volumeUnits);
    am42.setOrderIndex(0);
    ((NumericQuestion)am42).setDefaultUnit(getUnitBySymbol("m3"));

    // cleanup the driver
    Driver am42_driver = driverService.findByName("AM42");
    if (am42_driver != null) {
        driverService.remove(am42_driver);
    }

    ((NumericQuestion)am42).setDriver(null);

    JPA.em().persist(am42);
}



    }
    private void createQuestionAM43() {
        // == AM43
        // Votre consommation estimée

        
am43 = (DoubleQuestion) questionService.findByCode(QuestionCode.AM43);
if (am43 == null) {
    am43 = new DoubleQuestion( am26, 0, QuestionCode.AM43, volumeUnits, getUnitBySymbol("m3") );
    JPA.em().persist(am43);

    // cleanup the driver
    Driver am43_driver = driverService.findByName("AM43");
    if (am43_driver != null) {
        driverService.remove(am43_driver);
    }

} else {
    if (!am43.getQuestionSet().equals(am26) && am26.getQuestions().contains(am43)) {
        am26.getQuestions().remove(am43);
        JPA.em().persist(am26);
    }
    if (am43.getQuestionSet().equals(am26) && !am26.getQuestions().contains(am43)) {
        am26.getQuestions().add(am43);
        JPA.em().persist(am26);
    }
    ((NumericQuestion)am43).setUnitCategory(volumeUnits);
    am43.setOrderIndex(0);
    ((NumericQuestion)am43).setDefaultUnit(getUnitBySymbol("m3"));

    // cleanup the driver
    Driver am43_driver = driverService.findByName("AM43");
    if (am43_driver != null) {
        driverService.remove(am43_driver);
    }

    ((NumericQuestion)am43).setDriver(null);

    JPA.em().persist(am43);
}



    }
    private void createQuestionAM44() {
        // == AM44
        // Votre consommation estimée

        
am44 = (DoubleQuestion) questionService.findByCode(QuestionCode.AM44);
if (am44 == null) {
    am44 = new DoubleQuestion( am26, 0, QuestionCode.AM44, volumeUnits, getUnitBySymbol("m3") );
    JPA.em().persist(am44);

    // cleanup the driver
    Driver am44_driver = driverService.findByName("AM44");
    if (am44_driver != null) {
        driverService.remove(am44_driver);
    }

} else {
    if (!am44.getQuestionSet().equals(am26) && am26.getQuestions().contains(am44)) {
        am26.getQuestions().remove(am44);
        JPA.em().persist(am26);
    }
    if (am44.getQuestionSet().equals(am26) && !am26.getQuestions().contains(am44)) {
        am26.getQuestions().add(am44);
        JPA.em().persist(am26);
    }
    ((NumericQuestion)am44).setUnitCategory(volumeUnits);
    am44.setOrderIndex(0);
    ((NumericQuestion)am44).setDefaultUnit(getUnitBySymbol("m3"));

    // cleanup the driver
    Driver am44_driver = driverService.findByName("AM44");
    if (am44_driver != null) {
        driverService.remove(am44_driver);
    }

    ((NumericQuestion)am44).setDriver(null);

    JPA.em().persist(am44);
}



    }
    private void createQuestionAM45() {
        // == AM45
        // Votre consommation estimée

        
am45 = (DoubleQuestion) questionService.findByCode(QuestionCode.AM45);
if (am45 == null) {
    am45 = new DoubleQuestion( am26, 0, QuestionCode.AM45, massUnits, getUnitBySymbol("kg") );
    JPA.em().persist(am45);

    // cleanup the driver
    Driver am45_driver = driverService.findByName("AM45");
    if (am45_driver != null) {
        driverService.remove(am45_driver);
    }

} else {
    if (!am45.getQuestionSet().equals(am26) && am26.getQuestions().contains(am45)) {
        am26.getQuestions().remove(am45);
        JPA.em().persist(am26);
    }
    if (am45.getQuestionSet().equals(am26) && !am26.getQuestions().contains(am45)) {
        am26.getQuestions().add(am45);
        JPA.em().persist(am26);
    }
    ((NumericQuestion)am45).setUnitCategory(massUnits);
    am45.setOrderIndex(0);
    ((NumericQuestion)am45).setDefaultUnit(getUnitBySymbol("kg"));


    // cleanup the driver
    Driver am45_driver = driverService.findByName("AM45");
    if (am45_driver != null) {
        driverService.remove(am45_driver);
    }

    ((NumericQuestion)am45).setDriver(null);

    JPA.em().persist(am45);
}



    }
    private void createQuestionAM46() {
        // == AM46
        // Votre consommation estimée

        
am46 = (DoubleQuestion) questionService.findByCode(QuestionCode.AM46);
if (am46 == null) {
    am46 = new DoubleQuestion( am26, 0, QuestionCode.AM46, massUnits, getUnitBySymbol("kg") );
    JPA.em().persist(am46);

    // cleanup the driver
    Driver am46_driver = driverService.findByName("AM46");
    if (am46_driver != null) {
        driverService.remove(am46_driver);
    }

} else {
    if (!am46.getQuestionSet().equals(am26) && am26.getQuestions().contains(am46)) {
        am26.getQuestions().remove(am46);
        JPA.em().persist(am26);
    }
    if (am46.getQuestionSet().equals(am26) && !am26.getQuestions().contains(am46)) {
        am26.getQuestions().add(am46);
        JPA.em().persist(am26);
    }
    ((NumericQuestion)am46).setUnitCategory(massUnits);
    am46.setOrderIndex(0);
    ((NumericQuestion)am46).setDefaultUnit(getUnitBySymbol("kg"));


    // cleanup the driver
    Driver am46_driver = driverService.findByName("AM46");
    if (am46_driver != null) {
        driverService.remove(am46_driver);
    }

    ((NumericQuestion)am46).setDriver(null);

    JPA.em().persist(am46);
}



    }
    private void createQuestionAM47() {
        // == AM47
        // Votre consommation estimée

        
am47 = (DoubleQuestion) questionService.findByCode(QuestionCode.AM47);
if (am47 == null) {
    am47 = new DoubleQuestion( am26, 0, QuestionCode.AM47, energyUnits, getUnitBySymbol("kW.h") );
    JPA.em().persist(am47);

    // cleanup the driver
    Driver am47_driver = driverService.findByName("AM47");
    if (am47_driver != null) {
        driverService.remove(am47_driver);
    }


} else {
    if (!am47.getQuestionSet().equals(am26) && am26.getQuestions().contains(am47)) {
        am26.getQuestions().remove(am47);
        JPA.em().persist(am26);
    }
    if (am47.getQuestionSet().equals(am26) && !am26.getQuestions().contains(am47)) {
        am26.getQuestions().add(am47);
        JPA.em().persist(am26);
    }
    ((NumericQuestion)am47).setUnitCategory(energyUnits);
    am47.setOrderIndex(0);
    ((NumericQuestion)am47).setDefaultUnit(getUnitBySymbol("kW.h"));

    // cleanup the driver
    Driver am47_driver = driverService.findByName("AM47");
    if (am47_driver != null) {
        driverService.remove(am47_driver);
    }

    ((NumericQuestion)am47).setDriver(null);

    JPA.em().persist(am47);
}



    }
    private void createQuestionAM49() {
        // == AM49
        // Moyen de cuisson que vous utilisez

        am49 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AM49);
if (am49 == null) {
    am49 = new ValueSelectionQuestion(am48, 0, QuestionCode.AM49, CodeList.COMBUSTIBLECUISSONMENAGE);
    JPA.em().persist(am49);
} else {
    if (!am49.getQuestionSet().equals(am48) && am48.getQuestions().contains(am49)) {
        am48.getQuestions().remove(am49);
        JPA.em().persist(am48);
    }
    if (am49.getQuestionSet().equals(am48) && !am48.getQuestions().contains(am49)) {
        am48.getQuestions().add(am49);
        JPA.em().persist(am48);
    }
    am49.setOrderIndex(0);
    ((ValueSelectionQuestion)am49).setCodeList(CodeList.COMBUSTIBLECUISSONMENAGE);
    JPA.em().persist(am49);
}

    }
    private void createQuestionAM50() {
        // == AM50
        // Cette consommation est-elle incluse dans la facture de chauffage ou d'éléctricité?

        am50 = (BooleanQuestion) questionService.findByCode(QuestionCode.AM50);
if (am50 == null) {
    am50 = new BooleanQuestion(am48, 0, QuestionCode.AM50, null);
    JPA.em().persist(am50);
} else {
    ((BooleanQuestion)am50).setDefaultValue(null);
    if (!am50.getQuestionSet().equals(am48) && am48.getQuestions().contains(am50)) {
        am48.getQuestions().remove(am50);
        JPA.em().persist(am48);
    }
    if (am50.getQuestionSet().equals(am48) && !am48.getQuestions().contains(am50)) {
        am48.getQuestions().add(am50);
        JPA.em().persist(am48);
    }
    am50.setOrderIndex(0);
    JPA.em().persist(am50);
}

    }
    private void createQuestionAM51() {
        // == AM51
        // Type de données disponibles (si pas incluse dans la facture)

        am51 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AM51);
if (am51 == null) {
    am51 = new ValueSelectionQuestion(am48, 0, QuestionCode.AM51, CodeList.TYPECONSOMMATIONCHAUFFAGE);
    JPA.em().persist(am51);
} else {
    if (!am51.getQuestionSet().equals(am48) && am48.getQuestions().contains(am51)) {
        am48.getQuestions().remove(am51);
        JPA.em().persist(am48);
    }
    if (am51.getQuestionSet().equals(am48) && !am48.getQuestions().contains(am51)) {
        am48.getQuestions().add(am51);
        JPA.em().persist(am48);
    }
    am51.setOrderIndex(0);
    ((ValueSelectionQuestion)am51).setCodeList(CodeList.TYPECONSOMMATIONCHAUFFAGE);
    JPA.em().persist(am51);
}

    }
    private void createQuestionAM52() {
        // == AM52
        // consommation annuelle

        
am52 = (DoubleQuestion) questionService.findByCode(QuestionCode.AM52);
if (am52 == null) {
    am52 = new DoubleQuestion( am48, 0, QuestionCode.AM52, volumeUnits, getUnitBySymbol("m3") );
    JPA.em().persist(am52);

    // cleanup the driver
    Driver am52_driver = driverService.findByName("AM52");
    if (am52_driver != null) {
        driverService.remove(am52_driver);
    }

} else {
    if (!am52.getQuestionSet().equals(am48) && am48.getQuestions().contains(am52)) {
        am48.getQuestions().remove(am52);
        JPA.em().persist(am48);
    }
    if (am52.getQuestionSet().equals(am48) && !am48.getQuestions().contains(am52)) {
        am48.getQuestions().add(am52);
        JPA.em().persist(am48);
    }
    ((NumericQuestion)am52).setUnitCategory(volumeUnits);
    am52.setOrderIndex(0);
    ((NumericQuestion)am52).setDefaultUnit(getUnitBySymbol("m3"));

    // cleanup the driver
    Driver am52_driver = driverService.findByName("AM52");
    if (am52_driver != null) {
        driverService.remove(am52_driver);
    }

    ((NumericQuestion)am52).setDriver(null);

    JPA.em().persist(am52);
}



    }
    private void createQuestionAM53() {
        // == AM53
        // consommation annuelle

        
am53 = (DoubleQuestion) questionService.findByCode(QuestionCode.AM53);
if (am53 == null) {
    am53 = new DoubleQuestion( am48, 0, QuestionCode.AM53, energyUnits, getUnitBySymbol("kW.h") );
    JPA.em().persist(am53);

    // cleanup the driver
    Driver am53_driver = driverService.findByName("AM53");
    if (am53_driver != null) {
        driverService.remove(am53_driver);
    }


} else {
    if (!am53.getQuestionSet().equals(am48) && am48.getQuestions().contains(am53)) {
        am48.getQuestions().remove(am53);
        JPA.em().persist(am48);
    }
    if (am53.getQuestionSet().equals(am48) && !am48.getQuestions().contains(am53)) {
        am48.getQuestions().add(am53);
        JPA.em().persist(am48);
    }
    ((NumericQuestion)am53).setUnitCategory(energyUnits);
    am53.setOrderIndex(0);
    ((NumericQuestion)am53).setDefaultUnit(getUnitBySymbol("kW.h"));

    // cleanup the driver
    Driver am53_driver = driverService.findByName("AM53");
    if (am53_driver != null) {
        driverService.remove(am53_driver);
    }

    ((NumericQuestion)am53).setDriver(null);

    JPA.em().persist(am53);
}



    }
    private void createQuestionAM54() {
        // == AM54
        // coût annuel

        
am54 = (DoubleQuestion) questionService.findByCode(QuestionCode.AM54);
if (am54 == null) {
    am54 = new DoubleQuestion( am48, 0, QuestionCode.AM54, moneyUnits, getUnitBySymbol("EUR") );
    JPA.em().persist(am54);

    // cleanup the driver
    Driver am54_driver = driverService.findByName("AM54");
    if (am54_driver != null) {
        driverService.remove(am54_driver);
    }

} else {
    if (!am54.getQuestionSet().equals(am48) && am48.getQuestions().contains(am54)) {
        am48.getQuestions().remove(am54);
        JPA.em().persist(am48);
    }
    if (am54.getQuestionSet().equals(am48) && !am48.getQuestions().contains(am54)) {
        am48.getQuestions().add(am54);
        JPA.em().persist(am48);
    }
    ((NumericQuestion)am54).setUnitCategory(moneyUnits);
    am54.setOrderIndex(0);
    ((NumericQuestion)am54).setDefaultUnit(getUnitBySymbol("EUR"));

    // cleanup the driver
    Driver am54_driver = driverService.findByName("AM54");
    if (am54_driver != null) {
        driverService.remove(am54_driver);
    }

    ((NumericQuestion)am54).setDriver(null);

    JPA.em().persist(am54);
}



    }
    private void createQuestionAM55() {
        // == AM55
        // Prix unitaire du gaz naturel (au m³)

        
am55 = (DoubleQuestion) questionService.findByCode(QuestionCode.AM55);
if (am55 == null) {
    am55 = new DoubleQuestion( am48, 0, QuestionCode.AM55, moneyUnits, getUnitBySymbol("EUR") );
    JPA.em().persist(am55);

    // cleanup the driver
    Driver am55_driver = driverService.findByName("AM55");
    if (am55_driver != null) {
        driverService.remove(am55_driver);
    }

    // recreate with good value
    am55_driver = new Driver("AM55");
    driverService.saveOrUpdate(am55_driver);
    Period p2000 = periodService.findByCode(PeriodCode.P2000);
    DriverValue dv = new DriverValue(am55_driver, p2000, Double.valueOf(0.625));
    am55_driver.getDriverValueList().add(dv);;
    driverService.saveOrUpdate(am55_driver);
    JPA.em().persist(dv);

    ((NumericQuestion)am55).setDriver(am55_driver);
    JPA.em().persist(am55);
} else {
    if (!am55.getQuestionSet().equals(am48) && am48.getQuestions().contains(am55)) {
        am48.getQuestions().remove(am55);
        JPA.em().persist(am48);
    }
    if (am55.getQuestionSet().equals(am48) && !am48.getQuestions().contains(am55)) {
        am48.getQuestions().add(am55);
        JPA.em().persist(am48);
    }
    ((NumericQuestion)am55).setUnitCategory(moneyUnits);
    am55.setOrderIndex(0);
    ((NumericQuestion)am55).setDefaultUnit(getUnitBySymbol("EUR"));

    // cleanup the driver
    Driver am55_driver = driverService.findByName("AM55");
    if (am55_driver != null) {
        driverService.remove(am55_driver);
    }

    // recreate with good value
    am55_driver = new Driver("AM55");
    driverService.saveOrUpdate(am55_driver);
    Period p2000 = periodService.findByCode(PeriodCode.P2000);
    DriverValue dv = new DriverValue(am55_driver, p2000, Double.valueOf(0.625));
    am55_driver.getDriverValueList().add(dv);;
    driverService.saveOrUpdate(am55_driver);
    JPA.em().persist(dv);

    ((NumericQuestion)am55).setDriver(am55_driver);

    JPA.em().persist(am55);
}



    }
    private void createQuestionAM56() {
        // == AM56
        // Prix unitaire du propane (au m³)

        
am56 = (DoubleQuestion) questionService.findByCode(QuestionCode.AM56);
if (am56 == null) {
    am56 = new DoubleQuestion( am48, 0, QuestionCode.AM56, moneyUnits, getUnitBySymbol("EUR") );
    JPA.em().persist(am56);

    // cleanup the driver
    Driver am56_driver = driverService.findByName("AM56");
    if (am56_driver != null) {
        driverService.remove(am56_driver);
    }

    // recreate with good value
    am56_driver = new Driver("AM56");
    driverService.saveOrUpdate(am56_driver);
    Period p2000 = periodService.findByCode(PeriodCode.P2000);
    DriverValue dv = new DriverValue(am56_driver, p2000, Double.valueOf(0.625));
    am56_driver.getDriverValueList().add(dv);;
    driverService.saveOrUpdate(am56_driver);
    JPA.em().persist(dv);

    ((NumericQuestion)am56).setDriver(am56_driver);
    JPA.em().persist(am56);
} else {
    if (!am56.getQuestionSet().equals(am48) && am48.getQuestions().contains(am56)) {
        am48.getQuestions().remove(am56);
        JPA.em().persist(am48);
    }
    if (am56.getQuestionSet().equals(am48) && !am48.getQuestions().contains(am56)) {
        am48.getQuestions().add(am56);
        JPA.em().persist(am48);
    }
    ((NumericQuestion)am56).setUnitCategory(moneyUnits);
    am56.setOrderIndex(0);
    ((NumericQuestion)am56).setDefaultUnit(getUnitBySymbol("EUR"));

    // cleanup the driver
    Driver am56_driver = driverService.findByName("AM56");
    if (am56_driver != null) {
        driverService.remove(am56_driver);
    }

    // recreate with good value
    am56_driver = new Driver("AM56");
    driverService.saveOrUpdate(am56_driver);
    Period p2000 = periodService.findByCode(PeriodCode.P2000);
    DriverValue dv = new DriverValue(am56_driver, p2000, Double.valueOf(0.625));
    am56_driver.getDriverValueList().add(dv);;
    driverService.saveOrUpdate(am56_driver);
    JPA.em().persist(dv);

    ((NumericQuestion)am56).setDriver(am56_driver);

    JPA.em().persist(am56);
}



    }
    private void createQuestionAM57() {
        // == AM57
        // Prix unitaire du butane (au m³)

        
am57 = (DoubleQuestion) questionService.findByCode(QuestionCode.AM57);
if (am57 == null) {
    am57 = new DoubleQuestion( am48, 0, QuestionCode.AM57, moneyUnits, getUnitBySymbol("EUR") );
    JPA.em().persist(am57);

    // cleanup the driver
    Driver am57_driver = driverService.findByName("AM57");
    if (am57_driver != null) {
        driverService.remove(am57_driver);
    }

    // recreate with good value
    am57_driver = new Driver("AM57");
    driverService.saveOrUpdate(am57_driver);
    Period p2000 = periodService.findByCode(PeriodCode.P2000);
    DriverValue dv = new DriverValue(am57_driver, p2000, Double.valueOf(0.625));
    am57_driver.getDriverValueList().add(dv);;
    driverService.saveOrUpdate(am57_driver);
    JPA.em().persist(dv);

    ((NumericQuestion)am57).setDriver(am57_driver);
    JPA.em().persist(am57);
} else {
    if (!am57.getQuestionSet().equals(am48) && am48.getQuestions().contains(am57)) {
        am48.getQuestions().remove(am57);
        JPA.em().persist(am48);
    }
    if (am57.getQuestionSet().equals(am48) && !am48.getQuestions().contains(am57)) {
        am48.getQuestions().add(am57);
        JPA.em().persist(am48);
    }
    ((NumericQuestion)am57).setUnitCategory(moneyUnits);
    am57.setOrderIndex(0);
    ((NumericQuestion)am57).setDefaultUnit(getUnitBySymbol("EUR"));

    // cleanup the driver
    Driver am57_driver = driverService.findByName("AM57");
    if (am57_driver != null) {
        driverService.remove(am57_driver);
    }

    // recreate with good value
    am57_driver = new Driver("AM57");
    driverService.saveOrUpdate(am57_driver);
    Period p2000 = periodService.findByCode(PeriodCode.P2000);
    DriverValue dv = new DriverValue(am57_driver, p2000, Double.valueOf(0.625));
    am57_driver.getDriverValueList().add(dv);;
    driverService.saveOrUpdate(am57_driver);
    JPA.em().persist(dv);

    ((NumericQuestion)am57).setDriver(am57_driver);

    JPA.em().persist(am57);
}



    }
    private void createQuestionAM58() {
        // == AM58
        // Prix unitaire de l'électricité (au kWh)

        
am58 = (DoubleQuestion) questionService.findByCode(QuestionCode.AM58);
if (am58 == null) {
    am58 = new DoubleQuestion( am48, 0, QuestionCode.AM58, moneyUnits, getUnitBySymbol("EUR") );
    JPA.em().persist(am58);

    // cleanup the driver
    Driver am58_driver = driverService.findByName("AM58");
    if (am58_driver != null) {
        driverService.remove(am58_driver);
    }

    // recreate with good value
    am58_driver = new Driver("AM58");
    driverService.saveOrUpdate(am58_driver);
    Period p2000 = periodService.findByCode(PeriodCode.P2000);
    DriverValue dv = new DriverValue(am58_driver, p2000, Double.valueOf(0.03));
    am58_driver.getDriverValueList().add(dv);;
    driverService.saveOrUpdate(am58_driver);
    JPA.em().persist(dv);

    ((NumericQuestion)am58).setDriver(am58_driver);
    JPA.em().persist(am58);
} else {
    if (!am58.getQuestionSet().equals(am48) && am48.getQuestions().contains(am58)) {
        am48.getQuestions().remove(am58);
        JPA.em().persist(am48);
    }
    if (am58.getQuestionSet().equals(am48) && !am48.getQuestions().contains(am58)) {
        am48.getQuestions().add(am58);
        JPA.em().persist(am48);
    }
    ((NumericQuestion)am58).setUnitCategory(moneyUnits);
    am58.setOrderIndex(0);
    ((NumericQuestion)am58).setDefaultUnit(getUnitBySymbol("EUR"));

    // cleanup the driver
    Driver am58_driver = driverService.findByName("AM58");
    if (am58_driver != null) {
        driverService.remove(am58_driver);
    }

    // recreate with good value
    am58_driver = new Driver("AM58");
    driverService.saveOrUpdate(am58_driver);
    Period p2000 = periodService.findByCode(PeriodCode.P2000);
    DriverValue dv = new DriverValue(am58_driver, p2000, Double.valueOf(0.03));
    am58_driver.getDriverValueList().add(dv);;
    driverService.saveOrUpdate(am58_driver);
    JPA.em().persist(dv);

    ((NumericQuestion)am58).setDriver(am58_driver);

    JPA.em().persist(am58);
}



    }
    private void createQuestionAM59() {
        // == AM59
        // Votre consommation estimée

        
am59 = (DoubleQuestion) questionService.findByCode(QuestionCode.AM59);
if (am59 == null) {
    am59 = new DoubleQuestion( am48, 0, QuestionCode.AM59, volumeUnits, getUnitBySymbol("m3") );
    JPA.em().persist(am59);

    // cleanup the driver
    Driver am59_driver = driverService.findByName("AM59");
    if (am59_driver != null) {
        driverService.remove(am59_driver);
    }

} else {
    if (!am59.getQuestionSet().equals(am48) && am48.getQuestions().contains(am59)) {
        am48.getQuestions().remove(am59);
        JPA.em().persist(am48);
    }
    if (am59.getQuestionSet().equals(am48) && !am48.getQuestions().contains(am59)) {
        am48.getQuestions().add(am59);
        JPA.em().persist(am48);
    }
    ((NumericQuestion)am59).setUnitCategory(volumeUnits);
    am59.setOrderIndex(0);
    ((NumericQuestion)am59).setDefaultUnit(getUnitBySymbol("m3"));

    // cleanup the driver
    Driver am59_driver = driverService.findByName("AM59");
    if (am59_driver != null) {
        driverService.remove(am59_driver);
    }

    ((NumericQuestion)am59).setDriver(null);

    JPA.em().persist(am59);
}



    }
    private void createQuestionAM60() {
        // == AM60
        // Votre consommation estimée

        
am60 = (DoubleQuestion) questionService.findByCode(QuestionCode.AM60);
if (am60 == null) {
    am60 = new DoubleQuestion( am48, 0, QuestionCode.AM60, volumeUnits, getUnitBySymbol("m3") );
    JPA.em().persist(am60);

    // cleanup the driver
    Driver am60_driver = driverService.findByName("AM60");
    if (am60_driver != null) {
        driverService.remove(am60_driver);
    }

} else {
    if (!am60.getQuestionSet().equals(am48) && am48.getQuestions().contains(am60)) {
        am48.getQuestions().remove(am60);
        JPA.em().persist(am48);
    }
    if (am60.getQuestionSet().equals(am48) && !am48.getQuestions().contains(am60)) {
        am48.getQuestions().add(am60);
        JPA.em().persist(am48);
    }
    ((NumericQuestion)am60).setUnitCategory(volumeUnits);
    am60.setOrderIndex(0);
    ((NumericQuestion)am60).setDefaultUnit(getUnitBySymbol("m3"));

    // cleanup the driver
    Driver am60_driver = driverService.findByName("AM60");
    if (am60_driver != null) {
        driverService.remove(am60_driver);
    }

    ((NumericQuestion)am60).setDriver(null);

    JPA.em().persist(am60);
}



    }
    private void createQuestionAM61() {
        // == AM61
        // Votre consommation estimée

        
am61 = (DoubleQuestion) questionService.findByCode(QuestionCode.AM61);
if (am61 == null) {
    am61 = new DoubleQuestion( am48, 0, QuestionCode.AM61, volumeUnits, getUnitBySymbol("m3") );
    JPA.em().persist(am61);

    // cleanup the driver
    Driver am61_driver = driverService.findByName("AM61");
    if (am61_driver != null) {
        driverService.remove(am61_driver);
    }

} else {
    if (!am61.getQuestionSet().equals(am48) && am48.getQuestions().contains(am61)) {
        am48.getQuestions().remove(am61);
        JPA.em().persist(am48);
    }
    if (am61.getQuestionSet().equals(am48) && !am48.getQuestions().contains(am61)) {
        am48.getQuestions().add(am61);
        JPA.em().persist(am48);
    }
    ((NumericQuestion)am61).setUnitCategory(volumeUnits);
    am61.setOrderIndex(0);
    ((NumericQuestion)am61).setDefaultUnit(getUnitBySymbol("m3"));

    // cleanup the driver
    Driver am61_driver = driverService.findByName("AM61");
    if (am61_driver != null) {
        driverService.remove(am61_driver);
    }

    ((NumericQuestion)am61).setDriver(null);

    JPA.em().persist(am61);
}



    }
    private void createQuestionAM62() {
        // == AM62
        // Votre consommation estimée

        
am62 = (DoubleQuestion) questionService.findByCode(QuestionCode.AM62);
if (am62 == null) {
    am62 = new DoubleQuestion( am48, 0, QuestionCode.AM62, energyUnits, getUnitBySymbol("kW.h") );
    JPA.em().persist(am62);

    // cleanup the driver
    Driver am62_driver = driverService.findByName("AM62");
    if (am62_driver != null) {
        driverService.remove(am62_driver);
    }


} else {
    if (!am62.getQuestionSet().equals(am48) && am48.getQuestions().contains(am62)) {
        am48.getQuestions().remove(am62);
        JPA.em().persist(am48);
    }
    if (am62.getQuestionSet().equals(am48) && !am48.getQuestions().contains(am62)) {
        am48.getQuestions().add(am62);
        JPA.em().persist(am48);
    }
    ((NumericQuestion)am62).setUnitCategory(energyUnits);
    am62.setOrderIndex(0);
    ((NumericQuestion)am62).setDefaultUnit(getUnitBySymbol("kW.h"));

    // cleanup the driver
    Driver am62_driver = driverService.findByName("AM62");
    if (am62_driver != null) {
        driverService.remove(am62_driver);
    }

    ((NumericQuestion)am62).setDriver(null);

    JPA.em().persist(am62);
}



    }
    private void createQuestionAM64() {
        // == AM64
        // Pièces documentaires liées à l'électricité

        am64 = (DocumentQuestion) questionService.findByCode(QuestionCode.AM64);
if (am64 == null) {
    am64 = new DocumentQuestion(am63, 0, QuestionCode.AM64);
    JPA.em().persist(am64);
} else {
    if (!am64.getQuestionSet().equals(am63) && am63.getQuestions().contains(am64)) {
        am63.getQuestions().remove(am64);
        JPA.em().persist(am63);
    }
    if (am64.getQuestionSet().equals(am63) && !am63.getQuestions().contains(am64)) {
        am63.getQuestions().add(am64);
        JPA.em().persist(am63);
    }
    am64.setOrderIndex(0);
    JPA.em().persist(am64);
}

    }
    private void createQuestionAM65() {
        // == AM65
        // Type d'éléctricité

        am65 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AM65);
if (am65 == null) {
    am65 = new ValueSelectionQuestion(am63, 0, QuestionCode.AM65, CodeList.TYPEELECTRICITE);
    JPA.em().persist(am65);
} else {
    if (!am65.getQuestionSet().equals(am63) && am63.getQuestions().contains(am65)) {
        am63.getQuestions().remove(am65);
        JPA.em().persist(am63);
    }
    if (am65.getQuestionSet().equals(am63) && !am63.getQuestions().contains(am65)) {
        am63.getQuestions().add(am65);
        JPA.em().persist(am63);
    }
    am65.setOrderIndex(0);
    ((ValueSelectionQuestion)am65).setCodeList(CodeList.TYPEELECTRICITE);
    JPA.em().persist(am65);
}

    }
    private void createQuestionAM66() {
        // == AM66
        // Type de données disponibles

        am66 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AM66);
if (am66 == null) {
    am66 = new ValueSelectionQuestion(am63, 0, QuestionCode.AM66, CodeList.TYPECONSOMMATIONELECTRICITE);
    JPA.em().persist(am66);
} else {
    if (!am66.getQuestionSet().equals(am63) && am63.getQuestions().contains(am66)) {
        am63.getQuestions().remove(am66);
        JPA.em().persist(am63);
    }
    if (am66.getQuestionSet().equals(am63) && !am63.getQuestions().contains(am66)) {
        am63.getQuestions().add(am66);
        JPA.em().persist(am63);
    }
    am66.setOrderIndex(0);
    ((ValueSelectionQuestion)am66).setCodeList(CodeList.TYPECONSOMMATIONELECTRICITE);
    JPA.em().persist(am66);
}

    }
    private void createQuestionAM67() {
        // == AM67
        // Consommation annuelle

        
am67 = (DoubleQuestion) questionService.findByCode(QuestionCode.AM67);
if (am67 == null) {
    am67 = new DoubleQuestion( am63, 0, QuestionCode.AM67, energyUnits, getUnitBySymbol("kW.h") );
    JPA.em().persist(am67);

    // cleanup the driver
    Driver am67_driver = driverService.findByName("AM67");
    if (am67_driver != null) {
        driverService.remove(am67_driver);
    }


} else {
    if (!am67.getQuestionSet().equals(am63) && am63.getQuestions().contains(am67)) {
        am63.getQuestions().remove(am67);
        JPA.em().persist(am63);
    }
    if (am67.getQuestionSet().equals(am63) && !am63.getQuestions().contains(am67)) {
        am63.getQuestions().add(am67);
        JPA.em().persist(am63);
    }
    ((NumericQuestion)am67).setUnitCategory(energyUnits);
    am67.setOrderIndex(0);
    ((NumericQuestion)am67).setDefaultUnit(getUnitBySymbol("kW.h"));

    // cleanup the driver
    Driver am67_driver = driverService.findByName("AM67");
    if (am67_driver != null) {
        driverService.remove(am67_driver);
    }

    ((NumericQuestion)am67).setDriver(null);

    JPA.em().persist(am67);
}



    }
    private void createQuestionAM68() {
        // == AM68
        // Coût annuel

        
am68 = (DoubleQuestion) questionService.findByCode(QuestionCode.AM68);
if (am68 == null) {
    am68 = new DoubleQuestion( am63, 0, QuestionCode.AM68, moneyUnits, getUnitBySymbol("EUR") );
    JPA.em().persist(am68);

    // cleanup the driver
    Driver am68_driver = driverService.findByName("AM68");
    if (am68_driver != null) {
        driverService.remove(am68_driver);
    }

} else {
    if (!am68.getQuestionSet().equals(am63) && am63.getQuestions().contains(am68)) {
        am63.getQuestions().remove(am68);
        JPA.em().persist(am63);
    }
    if (am68.getQuestionSet().equals(am63) && !am63.getQuestions().contains(am68)) {
        am63.getQuestions().add(am68);
        JPA.em().persist(am63);
    }
    ((NumericQuestion)am68).setUnitCategory(moneyUnits);
    am68.setOrderIndex(0);
    ((NumericQuestion)am68).setDefaultUnit(getUnitBySymbol("EUR"));

    // cleanup the driver
    Driver am68_driver = driverService.findByName("AM68");
    if (am68_driver != null) {
        driverService.remove(am68_driver);
    }

    ((NumericQuestion)am68).setDriver(null);

    JPA.em().persist(am68);
}



    }
    private void createQuestionAM69() {
        // == AM69
        // Prix unitaire de l'électricité (au kWh)

        
am69 = (DoubleQuestion) questionService.findByCode(QuestionCode.AM69);
if (am69 == null) {
    am69 = new DoubleQuestion( am63, 0, QuestionCode.AM69, moneyUnits, getUnitBySymbol("EUR") );
    JPA.em().persist(am69);

    // cleanup the driver
    Driver am69_driver = driverService.findByName("AM69");
    if (am69_driver != null) {
        driverService.remove(am69_driver);
    }

    // recreate with good value
    am69_driver = new Driver("AM69");
    driverService.saveOrUpdate(am69_driver);
    Period p2000 = periodService.findByCode(PeriodCode.P2000);
    DriverValue dv = new DriverValue(am69_driver, p2000, Double.valueOf(0.027));
    am69_driver.getDriverValueList().add(dv);;
    driverService.saveOrUpdate(am69_driver);
    JPA.em().persist(dv);

    ((NumericQuestion)am69).setDriver(am69_driver);
    JPA.em().persist(am69);
} else {
    if (!am69.getQuestionSet().equals(am63) && am63.getQuestions().contains(am69)) {
        am63.getQuestions().remove(am69);
        JPA.em().persist(am63);
    }
    if (am69.getQuestionSet().equals(am63) && !am63.getQuestions().contains(am69)) {
        am63.getQuestions().add(am69);
        JPA.em().persist(am63);
    }
    ((NumericQuestion)am69).setUnitCategory(moneyUnits);
    am69.setOrderIndex(0);
    ((NumericQuestion)am69).setDefaultUnit(getUnitBySymbol("EUR"));

    // cleanup the driver
    Driver am69_driver = driverService.findByName("AM69");
    if (am69_driver != null) {
        driverService.remove(am69_driver);
    }

    // recreate with good value
    am69_driver = new Driver("AM69");
    driverService.saveOrUpdate(am69_driver);
    Period p2000 = periodService.findByCode(PeriodCode.P2000);
    DriverValue dv = new DriverValue(am69_driver, p2000, Double.valueOf(0.027));
    am69_driver.getDriverValueList().add(dv);;
    driverService.saveOrUpdate(am69_driver);
    JPA.em().persist(dv);

    ((NumericQuestion)am69).setDriver(am69_driver);

    JPA.em().persist(am69);
}



    }
    private void createQuestionAM70() {
        // == AM70
        // Votre consommation estimée

        
am70 = (DoubleQuestion) questionService.findByCode(QuestionCode.AM70);
if (am70 == null) {
    am70 = new DoubleQuestion( am63, 0, QuestionCode.AM70, energyUnits, getUnitBySymbol("kW.h") );
    JPA.em().persist(am70);

    // cleanup the driver
    Driver am70_driver = driverService.findByName("AM70");
    if (am70_driver != null) {
        driverService.remove(am70_driver);
    }


} else {
    if (!am70.getQuestionSet().equals(am63) && am63.getQuestions().contains(am70)) {
        am63.getQuestions().remove(am70);
        JPA.em().persist(am63);
    }
    if (am70.getQuestionSet().equals(am63) && !am63.getQuestions().contains(am70)) {
        am63.getQuestions().add(am70);
        JPA.em().persist(am63);
    }
    ((NumericQuestion)am70).setUnitCategory(energyUnits);
    am70.setOrderIndex(0);
    ((NumericQuestion)am70).setDefaultUnit(getUnitBySymbol("kW.h"));

    // cleanup the driver
    Driver am70_driver = driverService.findByName("AM70");
    if (am70_driver != null) {
        driverService.remove(am70_driver);
    }

    ((NumericQuestion)am70).setDriver(null);

    JPA.em().persist(am70);
}



    }
    private void createQuestionAM71() {
        // == AM71
        // Souhaitez-vous lister vos équipements électriques afin de nous permettre de vous faire par la suite des recommendations plus précises?

        am71 = (BooleanQuestion) questionService.findByCode(QuestionCode.AM71);
if (am71 == null) {
    am71 = new BooleanQuestion(am63, 0, QuestionCode.AM71, null);
    JPA.em().persist(am71);
} else {
    ((BooleanQuestion)am71).setDefaultValue(null);
    if (!am71.getQuestionSet().equals(am63) && am63.getQuestions().contains(am71)) {
        am63.getQuestions().remove(am71);
        JPA.em().persist(am63);
    }
    if (am71.getQuestionSet().equals(am63) && !am63.getQuestions().contains(am71)) {
        am63.getQuestions().add(am71);
        JPA.em().persist(am63);
    }
    am71.setOrderIndex(0);
    JPA.em().persist(am71);
}

    }
    private void createQuestionAM73() {
        // == AM73
        // Frigo

        am73 = (IntegerQuestion) questionService.findByCode(QuestionCode.AM73);
if (am73 == null) {
    am73 = new IntegerQuestion(am72, 0, QuestionCode.AM73, null);
    JPA.em().persist(am73);

    // cleanup the driver
    Driver am73_driver = driverService.findByName("AM73");
    if (am73_driver != null) {
        driverService.remove(am73_driver);
    }

} else {
    if (!am73.getQuestionSet().equals(am72) && am72.getQuestions().contains(am73)) {
        am72.getQuestions().remove(am73);
        JPA.em().persist(am72);
    }
    if (am73.getQuestionSet().equals(am72) && !am72.getQuestions().contains(am73)) {
        am72.getQuestions().add(am73);
        JPA.em().persist(am72);
    }
    am73.setOrderIndex(0);
    ((NumericQuestion)am73).setUnitCategory(null);

    // cleanup the driver
    Driver am73_driver = driverService.findByName("AM73");
    if (am73_driver != null) {
        driverService.remove(am73_driver);
    }

    ((NumericQuestion)am73).setDriver(null);

    JPA.em().persist(am73);
}

    }
    private void createQuestionAM74() {
        // == AM74
        // Label Energétique de ce(s) frigo(s)

        am74 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AM74);
if (am74 == null) {
    am74 = new ValueSelectionQuestion(am72, 0, QuestionCode.AM74, CodeList.TYPEENERGYLABEL);
    JPA.em().persist(am74);
} else {
    if (!am74.getQuestionSet().equals(am72) && am72.getQuestions().contains(am74)) {
        am72.getQuestions().remove(am74);
        JPA.em().persist(am72);
    }
    if (am74.getQuestionSet().equals(am72) && !am72.getQuestions().contains(am74)) {
        am72.getQuestions().add(am74);
        JPA.em().persist(am72);
    }
    am74.setOrderIndex(0);
    ((ValueSelectionQuestion)am74).setCodeList(CodeList.TYPEENERGYLABEL);
    JPA.em().persist(am74);
}

    }
    private void createQuestionAM75() {
        // == AM75
        // Congélateur

        am75 = (IntegerQuestion) questionService.findByCode(QuestionCode.AM75);
if (am75 == null) {
    am75 = new IntegerQuestion(am72, 0, QuestionCode.AM75, null);
    JPA.em().persist(am75);

    // cleanup the driver
    Driver am75_driver = driverService.findByName("AM75");
    if (am75_driver != null) {
        driverService.remove(am75_driver);
    }

} else {
    if (!am75.getQuestionSet().equals(am72) && am72.getQuestions().contains(am75)) {
        am72.getQuestions().remove(am75);
        JPA.em().persist(am72);
    }
    if (am75.getQuestionSet().equals(am72) && !am72.getQuestions().contains(am75)) {
        am72.getQuestions().add(am75);
        JPA.em().persist(am72);
    }
    am75.setOrderIndex(0);
    ((NumericQuestion)am75).setUnitCategory(null);

    // cleanup the driver
    Driver am75_driver = driverService.findByName("AM75");
    if (am75_driver != null) {
        driverService.remove(am75_driver);
    }

    ((NumericQuestion)am75).setDriver(null);

    JPA.em().persist(am75);
}

    }
    private void createQuestionAM76() {
        // == AM76
        // Label Energétique de ce(s) congélateur(s)

        am76 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AM76);
if (am76 == null) {
    am76 = new ValueSelectionQuestion(am72, 0, QuestionCode.AM76, CodeList.TYPEENERGYLABEL);
    JPA.em().persist(am76);
} else {
    if (!am76.getQuestionSet().equals(am72) && am72.getQuestions().contains(am76)) {
        am72.getQuestions().remove(am76);
        JPA.em().persist(am72);
    }
    if (am76.getQuestionSet().equals(am72) && !am72.getQuestions().contains(am76)) {
        am72.getQuestions().add(am76);
        JPA.em().persist(am72);
    }
    am76.setOrderIndex(0);
    ((ValueSelectionQuestion)am76).setCodeList(CodeList.TYPEENERGYLABEL);
    JPA.em().persist(am76);
}

    }
    private void createQuestionAM77() {
        // == AM77
        // Four

        am77 = (IntegerQuestion) questionService.findByCode(QuestionCode.AM77);
if (am77 == null) {
    am77 = new IntegerQuestion(am72, 0, QuestionCode.AM77, null);
    JPA.em().persist(am77);

    // cleanup the driver
    Driver am77_driver = driverService.findByName("AM77");
    if (am77_driver != null) {
        driverService.remove(am77_driver);
    }

} else {
    if (!am77.getQuestionSet().equals(am72) && am72.getQuestions().contains(am77)) {
        am72.getQuestions().remove(am77);
        JPA.em().persist(am72);
    }
    if (am77.getQuestionSet().equals(am72) && !am72.getQuestions().contains(am77)) {
        am72.getQuestions().add(am77);
        JPA.em().persist(am72);
    }
    am77.setOrderIndex(0);
    ((NumericQuestion)am77).setUnitCategory(null);

    // cleanup the driver
    Driver am77_driver = driverService.findByName("AM77");
    if (am77_driver != null) {
        driverService.remove(am77_driver);
    }

    ((NumericQuestion)am77).setDriver(null);

    JPA.em().persist(am77);
}

    }
    private void createQuestionAM78() {
        // == AM78
        // Label Energétique de ce four(s)

        am78 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AM78);
if (am78 == null) {
    am78 = new ValueSelectionQuestion(am72, 0, QuestionCode.AM78, CodeList.TYPEENERGYLABEL);
    JPA.em().persist(am78);
} else {
    if (!am78.getQuestionSet().equals(am72) && am72.getQuestions().contains(am78)) {
        am72.getQuestions().remove(am78);
        JPA.em().persist(am72);
    }
    if (am78.getQuestionSet().equals(am72) && !am72.getQuestions().contains(am78)) {
        am72.getQuestions().add(am78);
        JPA.em().persist(am72);
    }
    am78.setOrderIndex(0);
    ((ValueSelectionQuestion)am78).setCodeList(CodeList.TYPEENERGYLABEL);
    JPA.em().persist(am78);
}

    }
    private void createQuestionAM79() {
        // == AM79
        // Plaque de cuisson

        am79 = (IntegerQuestion) questionService.findByCode(QuestionCode.AM79);
if (am79 == null) {
    am79 = new IntegerQuestion(am72, 0, QuestionCode.AM79, null);
    JPA.em().persist(am79);

    // cleanup the driver
    Driver am79_driver = driverService.findByName("AM79");
    if (am79_driver != null) {
        driverService.remove(am79_driver);
    }

} else {
    if (!am79.getQuestionSet().equals(am72) && am72.getQuestions().contains(am79)) {
        am72.getQuestions().remove(am79);
        JPA.em().persist(am72);
    }
    if (am79.getQuestionSet().equals(am72) && !am72.getQuestions().contains(am79)) {
        am72.getQuestions().add(am79);
        JPA.em().persist(am72);
    }
    am79.setOrderIndex(0);
    ((NumericQuestion)am79).setUnitCategory(null);

    // cleanup the driver
    Driver am79_driver = driverService.findByName("AM79");
    if (am79_driver != null) {
        driverService.remove(am79_driver);
    }

    ((NumericQuestion)am79).setDriver(null);

    JPA.em().persist(am79);
}

    }
    private void createQuestionAM80() {
        // == AM80
        // Label Energétique de ce(s) plaque(s)

        am80 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AM80);
if (am80 == null) {
    am80 = new ValueSelectionQuestion(am72, 0, QuestionCode.AM80, CodeList.TYPEENERGYLABEL);
    JPA.em().persist(am80);
} else {
    if (!am80.getQuestionSet().equals(am72) && am72.getQuestions().contains(am80)) {
        am72.getQuestions().remove(am80);
        JPA.em().persist(am72);
    }
    if (am80.getQuestionSet().equals(am72) && !am72.getQuestions().contains(am80)) {
        am72.getQuestions().add(am80);
        JPA.em().persist(am72);
    }
    am80.setOrderIndex(0);
    ((ValueSelectionQuestion)am80).setCodeList(CodeList.TYPEENERGYLABEL);
    JPA.em().persist(am80);
}

    }
    private void createQuestionAM81() {
        // == AM81
        // Four à micro-ondes

        am81 = (IntegerQuestion) questionService.findByCode(QuestionCode.AM81);
if (am81 == null) {
    am81 = new IntegerQuestion(am72, 0, QuestionCode.AM81, null);
    JPA.em().persist(am81);

    // cleanup the driver
    Driver am81_driver = driverService.findByName("AM81");
    if (am81_driver != null) {
        driverService.remove(am81_driver);
    }

} else {
    if (!am81.getQuestionSet().equals(am72) && am72.getQuestions().contains(am81)) {
        am72.getQuestions().remove(am81);
        JPA.em().persist(am72);
    }
    if (am81.getQuestionSet().equals(am72) && !am72.getQuestions().contains(am81)) {
        am72.getQuestions().add(am81);
        JPA.em().persist(am72);
    }
    am81.setOrderIndex(0);
    ((NumericQuestion)am81).setUnitCategory(null);

    // cleanup the driver
    Driver am81_driver = driverService.findByName("AM81");
    if (am81_driver != null) {
        driverService.remove(am81_driver);
    }

    ((NumericQuestion)am81).setDriver(null);

    JPA.em().persist(am81);
}

    }
    private void createQuestionAM82() {
        // == AM82
        // Label Energétique de ce(s) four(s)

        am82 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AM82);
if (am82 == null) {
    am82 = new ValueSelectionQuestion(am72, 0, QuestionCode.AM82, CodeList.TYPEENERGYLABEL);
    JPA.em().persist(am82);
} else {
    if (!am82.getQuestionSet().equals(am72) && am72.getQuestions().contains(am82)) {
        am72.getQuestions().remove(am82);
        JPA.em().persist(am72);
    }
    if (am82.getQuestionSet().equals(am72) && !am72.getQuestions().contains(am82)) {
        am72.getQuestions().add(am82);
        JPA.em().persist(am72);
    }
    am82.setOrderIndex(0);
    ((ValueSelectionQuestion)am82).setCodeList(CodeList.TYPEENERGYLABEL);
    JPA.em().persist(am82);
}

    }
    private void createQuestionAM83() {
        // == AM83
        // Hotte

        am83 = (IntegerQuestion) questionService.findByCode(QuestionCode.AM83);
if (am83 == null) {
    am83 = new IntegerQuestion(am72, 0, QuestionCode.AM83, null);
    JPA.em().persist(am83);

    // cleanup the driver
    Driver am83_driver = driverService.findByName("AM83");
    if (am83_driver != null) {
        driverService.remove(am83_driver);
    }

} else {
    if (!am83.getQuestionSet().equals(am72) && am72.getQuestions().contains(am83)) {
        am72.getQuestions().remove(am83);
        JPA.em().persist(am72);
    }
    if (am83.getQuestionSet().equals(am72) && !am72.getQuestions().contains(am83)) {
        am72.getQuestions().add(am83);
        JPA.em().persist(am72);
    }
    am83.setOrderIndex(0);
    ((NumericQuestion)am83).setUnitCategory(null);

    // cleanup the driver
    Driver am83_driver = driverService.findByName("AM83");
    if (am83_driver != null) {
        driverService.remove(am83_driver);
    }

    ((NumericQuestion)am83).setDriver(null);

    JPA.em().persist(am83);
}

    }
    private void createQuestionAM84() {
        // == AM84
        // Label Energétique de cette(ces) hotte(s)

        am84 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AM84);
if (am84 == null) {
    am84 = new ValueSelectionQuestion(am72, 0, QuestionCode.AM84, CodeList.TYPEENERGYLABEL);
    JPA.em().persist(am84);
} else {
    if (!am84.getQuestionSet().equals(am72) && am72.getQuestions().contains(am84)) {
        am72.getQuestions().remove(am84);
        JPA.em().persist(am72);
    }
    if (am84.getQuestionSet().equals(am72) && !am72.getQuestions().contains(am84)) {
        am72.getQuestions().add(am84);
        JPA.em().persist(am72);
    }
    am84.setOrderIndex(0);
    ((ValueSelectionQuestion)am84).setCodeList(CodeList.TYPEENERGYLABEL);
    JPA.em().persist(am84);
}

    }
    private void createQuestionAM85() {
        // == AM85
        // Lave-vaisselle

        am85 = (IntegerQuestion) questionService.findByCode(QuestionCode.AM85);
if (am85 == null) {
    am85 = new IntegerQuestion(am72, 0, QuestionCode.AM85, null);
    JPA.em().persist(am85);

    // cleanup the driver
    Driver am85_driver = driverService.findByName("AM85");
    if (am85_driver != null) {
        driverService.remove(am85_driver);
    }

} else {
    if (!am85.getQuestionSet().equals(am72) && am72.getQuestions().contains(am85)) {
        am72.getQuestions().remove(am85);
        JPA.em().persist(am72);
    }
    if (am85.getQuestionSet().equals(am72) && !am72.getQuestions().contains(am85)) {
        am72.getQuestions().add(am85);
        JPA.em().persist(am72);
    }
    am85.setOrderIndex(0);
    ((NumericQuestion)am85).setUnitCategory(null);

    // cleanup the driver
    Driver am85_driver = driverService.findByName("AM85");
    if (am85_driver != null) {
        driverService.remove(am85_driver);
    }

    ((NumericQuestion)am85).setDriver(null);

    JPA.em().persist(am85);
}

    }
    private void createQuestionAM86() {
        // == AM86
        // Label Energétique de ce(s) lave-vaisselle(s)

        am86 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AM86);
if (am86 == null) {
    am86 = new ValueSelectionQuestion(am72, 0, QuestionCode.AM86, CodeList.TYPEENERGYLABEL);
    JPA.em().persist(am86);
} else {
    if (!am86.getQuestionSet().equals(am72) && am72.getQuestions().contains(am86)) {
        am72.getQuestions().remove(am86);
        JPA.em().persist(am72);
    }
    if (am86.getQuestionSet().equals(am72) && !am72.getQuestions().contains(am86)) {
        am72.getQuestions().add(am86);
        JPA.em().persist(am72);
    }
    am86.setOrderIndex(0);
    ((ValueSelectionQuestion)am86).setCodeList(CodeList.TYPEENERGYLABEL);
    JPA.em().persist(am86);
}

    }
    private void createQuestionAM87() {
        // == AM87
        // Lave-linge

        am87 = (IntegerQuestion) questionService.findByCode(QuestionCode.AM87);
if (am87 == null) {
    am87 = new IntegerQuestion(am72, 0, QuestionCode.AM87, null);
    JPA.em().persist(am87);

    // cleanup the driver
    Driver am87_driver = driverService.findByName("AM87");
    if (am87_driver != null) {
        driverService.remove(am87_driver);
    }

} else {
    if (!am87.getQuestionSet().equals(am72) && am72.getQuestions().contains(am87)) {
        am72.getQuestions().remove(am87);
        JPA.em().persist(am72);
    }
    if (am87.getQuestionSet().equals(am72) && !am72.getQuestions().contains(am87)) {
        am72.getQuestions().add(am87);
        JPA.em().persist(am72);
    }
    am87.setOrderIndex(0);
    ((NumericQuestion)am87).setUnitCategory(null);

    // cleanup the driver
    Driver am87_driver = driverService.findByName("AM87");
    if (am87_driver != null) {
        driverService.remove(am87_driver);
    }

    ((NumericQuestion)am87).setDriver(null);

    JPA.em().persist(am87);
}

    }
    private void createQuestionAM88() {
        // == AM88
        // Label Energétique de ce(s) lave-linge(s)

        am88 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AM88);
if (am88 == null) {
    am88 = new ValueSelectionQuestion(am72, 0, QuestionCode.AM88, CodeList.TYPEENERGYLABEL);
    JPA.em().persist(am88);
} else {
    if (!am88.getQuestionSet().equals(am72) && am72.getQuestions().contains(am88)) {
        am72.getQuestions().remove(am88);
        JPA.em().persist(am72);
    }
    if (am88.getQuestionSet().equals(am72) && !am72.getQuestions().contains(am88)) {
        am72.getQuestions().add(am88);
        JPA.em().persist(am72);
    }
    am88.setOrderIndex(0);
    ((ValueSelectionQuestion)am88).setCodeList(CodeList.TYPEENERGYLABEL);
    JPA.em().persist(am88);
}

    }
    private void createQuestionAM89() {
        // == AM89
        // Sèche-linge

        am89 = (IntegerQuestion) questionService.findByCode(QuestionCode.AM89);
if (am89 == null) {
    am89 = new IntegerQuestion(am72, 0, QuestionCode.AM89, null);
    JPA.em().persist(am89);

    // cleanup the driver
    Driver am89_driver = driverService.findByName("AM89");
    if (am89_driver != null) {
        driverService.remove(am89_driver);
    }

} else {
    if (!am89.getQuestionSet().equals(am72) && am72.getQuestions().contains(am89)) {
        am72.getQuestions().remove(am89);
        JPA.em().persist(am72);
    }
    if (am89.getQuestionSet().equals(am72) && !am72.getQuestions().contains(am89)) {
        am72.getQuestions().add(am89);
        JPA.em().persist(am72);
    }
    am89.setOrderIndex(0);
    ((NumericQuestion)am89).setUnitCategory(null);

    // cleanup the driver
    Driver am89_driver = driverService.findByName("AM89");
    if (am89_driver != null) {
        driverService.remove(am89_driver);
    }

    ((NumericQuestion)am89).setDriver(null);

    JPA.em().persist(am89);
}

    }
    private void createQuestionAM90() {
        // == AM90
        // Label Energétique de ce(s) sèche-linge(s)

        am90 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AM90);
if (am90 == null) {
    am90 = new ValueSelectionQuestion(am72, 0, QuestionCode.AM90, CodeList.TYPEENERGYLABEL);
    JPA.em().persist(am90);
} else {
    if (!am90.getQuestionSet().equals(am72) && am72.getQuestions().contains(am90)) {
        am72.getQuestions().remove(am90);
        JPA.em().persist(am72);
    }
    if (am90.getQuestionSet().equals(am72) && !am72.getQuestions().contains(am90)) {
        am72.getQuestions().add(am90);
        JPA.em().persist(am72);
    }
    am90.setOrderIndex(0);
    ((ValueSelectionQuestion)am90).setCodeList(CodeList.TYPEENERGYLABEL);
    JPA.em().persist(am90);
}

    }
    private void createQuestionAM91() {
        // == AM91
        // Eclairage

        am91 = (IntegerQuestion) questionService.findByCode(QuestionCode.AM91);
if (am91 == null) {
    am91 = new IntegerQuestion(am72, 0, QuestionCode.AM91, null);
    JPA.em().persist(am91);

    // cleanup the driver
    Driver am91_driver = driverService.findByName("AM91");
    if (am91_driver != null) {
        driverService.remove(am91_driver);
    }

} else {
    if (!am91.getQuestionSet().equals(am72) && am72.getQuestions().contains(am91)) {
        am72.getQuestions().remove(am91);
        JPA.em().persist(am72);
    }
    if (am91.getQuestionSet().equals(am72) && !am72.getQuestions().contains(am91)) {
        am72.getQuestions().add(am91);
        JPA.em().persist(am72);
    }
    am91.setOrderIndex(0);
    ((NumericQuestion)am91).setUnitCategory(null);

    // cleanup the driver
    Driver am91_driver = driverService.findByName("AM91");
    if (am91_driver != null) {
        driverService.remove(am91_driver);
    }

    ((NumericQuestion)am91).setDriver(null);

    JPA.em().persist(am91);
}

    }
    private void createQuestionAM92() {
        // == AM92
        // Label Energétique de ce(s) ampoule(s)

        am92 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AM92);
if (am92 == null) {
    am92 = new ValueSelectionQuestion(am72, 0, QuestionCode.AM92, CodeList.TYPEENERGYLABEL);
    JPA.em().persist(am92);
} else {
    if (!am92.getQuestionSet().equals(am72) && am72.getQuestions().contains(am92)) {
        am72.getQuestions().remove(am92);
        JPA.em().persist(am72);
    }
    if (am92.getQuestionSet().equals(am72) && !am72.getQuestions().contains(am92)) {
        am72.getQuestions().add(am92);
        JPA.em().persist(am72);
    }
    am92.setOrderIndex(0);
    ((ValueSelectionQuestion)am92).setCodeList(CodeList.TYPEENERGYLABEL);
    JPA.em().persist(am92);
}

    }
    private void createQuestionAM93() {
        // == AM93
        // Durée d'utilisation des équipements électro-ménagers

        am93 = (IntegerQuestion) questionService.findByCode(QuestionCode.AM93);
if (am93 == null) {
    am93 = new IntegerQuestion(am72, 0, QuestionCode.AM93, null);
    JPA.em().persist(am93);

    // cleanup the driver
    Driver am93_driver = driverService.findByName("AM93");
    if (am93_driver != null) {
        driverService.remove(am93_driver);
    }

} else {
    if (!am93.getQuestionSet().equals(am72) && am72.getQuestions().contains(am93)) {
        am72.getQuestions().remove(am93);
        JPA.em().persist(am72);
    }
    if (am93.getQuestionSet().equals(am72) && !am72.getQuestions().contains(am93)) {
        am72.getQuestions().add(am93);
        JPA.em().persist(am72);
    }
    am93.setOrderIndex(0);
    ((NumericQuestion)am93).setUnitCategory(null);

    // cleanup the driver
    Driver am93_driver = driverService.findByName("AM93");
    if (am93_driver != null) {
        driverService.remove(am93_driver);
    }

    ((NumericQuestion)am93).setDriver(null);

    JPA.em().persist(am93);
}

    }
    private void createQuestionAM94() {
        // == AM94
        // TV HD

        am94 = (IntegerQuestion) questionService.findByCode(QuestionCode.AM94);
if (am94 == null) {
    am94 = new IntegerQuestion(am72, 0, QuestionCode.AM94, null);
    JPA.em().persist(am94);

    // cleanup the driver
    Driver am94_driver = driverService.findByName("AM94");
    if (am94_driver != null) {
        driverService.remove(am94_driver);
    }

} else {
    if (!am94.getQuestionSet().equals(am72) && am72.getQuestions().contains(am94)) {
        am72.getQuestions().remove(am94);
        JPA.em().persist(am72);
    }
    if (am94.getQuestionSet().equals(am72) && !am72.getQuestions().contains(am94)) {
        am72.getQuestions().add(am94);
        JPA.em().persist(am72);
    }
    am94.setOrderIndex(0);
    ((NumericQuestion)am94).setUnitCategory(null);

    // cleanup the driver
    Driver am94_driver = driverService.findByName("AM94");
    if (am94_driver != null) {
        driverService.remove(am94_driver);
    }

    ((NumericQuestion)am94).setDriver(null);

    JPA.em().persist(am94);
}

    }
    private void createQuestionAM95() {
        // == AM95
        // Durée d'utilisation (années)

        am95 = (IntegerQuestion) questionService.findByCode(QuestionCode.AM95);
if (am95 == null) {
    am95 = new IntegerQuestion(am72, 0, QuestionCode.AM95, null);
    JPA.em().persist(am95);

    // cleanup the driver
    Driver am95_driver = driverService.findByName("AM95");
    if (am95_driver != null) {
        driverService.remove(am95_driver);
    }

} else {
    if (!am95.getQuestionSet().equals(am72) && am72.getQuestions().contains(am95)) {
        am72.getQuestions().remove(am95);
        JPA.em().persist(am72);
    }
    if (am95.getQuestionSet().equals(am72) && !am72.getQuestions().contains(am95)) {
        am72.getQuestions().add(am95);
        JPA.em().persist(am72);
    }
    am95.setOrderIndex(0);
    ((NumericQuestion)am95).setUnitCategory(null);

    // cleanup the driver
    Driver am95_driver = driverService.findByName("AM95");
    if (am95_driver != null) {
        driverService.remove(am95_driver);
    }

    ((NumericQuestion)am95).setDriver(null);

    JPA.em().persist(am95);
}

    }
    private void createQuestionAM96() {
        // == AM96
        // Ordinateur portable (laptop)

        am96 = (IntegerQuestion) questionService.findByCode(QuestionCode.AM96);
if (am96 == null) {
    am96 = new IntegerQuestion(am72, 0, QuestionCode.AM96, null);
    JPA.em().persist(am96);

    // cleanup the driver
    Driver am96_driver = driverService.findByName("AM96");
    if (am96_driver != null) {
        driverService.remove(am96_driver);
    }

} else {
    if (!am96.getQuestionSet().equals(am72) && am72.getQuestions().contains(am96)) {
        am72.getQuestions().remove(am96);
        JPA.em().persist(am72);
    }
    if (am96.getQuestionSet().equals(am72) && !am72.getQuestions().contains(am96)) {
        am72.getQuestions().add(am96);
        JPA.em().persist(am72);
    }
    am96.setOrderIndex(0);
    ((NumericQuestion)am96).setUnitCategory(null);

    // cleanup the driver
    Driver am96_driver = driverService.findByName("AM96");
    if (am96_driver != null) {
        driverService.remove(am96_driver);
    }

    ((NumericQuestion)am96).setDriver(null);

    JPA.em().persist(am96);
}

    }
    private void createQuestionAM97() {
        // == AM97
        // Durée d'utilisation (années)

        am97 = (IntegerQuestion) questionService.findByCode(QuestionCode.AM97);
if (am97 == null) {
    am97 = new IntegerQuestion(am72, 0, QuestionCode.AM97, null);
    JPA.em().persist(am97);

    // cleanup the driver
    Driver am97_driver = driverService.findByName("AM97");
    if (am97_driver != null) {
        driverService.remove(am97_driver);
    }

} else {
    if (!am97.getQuestionSet().equals(am72) && am72.getQuestions().contains(am97)) {
        am72.getQuestions().remove(am97);
        JPA.em().persist(am72);
    }
    if (am97.getQuestionSet().equals(am72) && !am72.getQuestions().contains(am97)) {
        am72.getQuestions().add(am97);
        JPA.em().persist(am72);
    }
    am97.setOrderIndex(0);
    ((NumericQuestion)am97).setUnitCategory(null);

    // cleanup the driver
    Driver am97_driver = driverService.findByName("AM97");
    if (am97_driver != null) {
        driverService.remove(am97_driver);
    }

    ((NumericQuestion)am97).setDriver(null);

    JPA.em().persist(am97);
}

    }
    private void createQuestionAM98() {
        // == AM98
        // Tablette-PC

        am98 = (IntegerQuestion) questionService.findByCode(QuestionCode.AM98);
if (am98 == null) {
    am98 = new IntegerQuestion(am72, 0, QuestionCode.AM98, null);
    JPA.em().persist(am98);

    // cleanup the driver
    Driver am98_driver = driverService.findByName("AM98");
    if (am98_driver != null) {
        driverService.remove(am98_driver);
    }

} else {
    if (!am98.getQuestionSet().equals(am72) && am72.getQuestions().contains(am98)) {
        am72.getQuestions().remove(am98);
        JPA.em().persist(am72);
    }
    if (am98.getQuestionSet().equals(am72) && !am72.getQuestions().contains(am98)) {
        am72.getQuestions().add(am98);
        JPA.em().persist(am72);
    }
    am98.setOrderIndex(0);
    ((NumericQuestion)am98).setUnitCategory(null);

    // cleanup the driver
    Driver am98_driver = driverService.findByName("AM98");
    if (am98_driver != null) {
        driverService.remove(am98_driver);
    }

    ((NumericQuestion)am98).setDriver(null);

    JPA.em().persist(am98);
}

    }
    private void createQuestionAM99() {
        // == AM99
        // Durée d'utilisation (années)

        am99 = (IntegerQuestion) questionService.findByCode(QuestionCode.AM99);
if (am99 == null) {
    am99 = new IntegerQuestion(am72, 0, QuestionCode.AM99, null);
    JPA.em().persist(am99);

    // cleanup the driver
    Driver am99_driver = driverService.findByName("AM99");
    if (am99_driver != null) {
        driverService.remove(am99_driver);
    }

} else {
    if (!am99.getQuestionSet().equals(am72) && am72.getQuestions().contains(am99)) {
        am72.getQuestions().remove(am99);
        JPA.em().persist(am72);
    }
    if (am99.getQuestionSet().equals(am72) && !am72.getQuestions().contains(am99)) {
        am72.getQuestions().add(am99);
        JPA.em().persist(am72);
    }
    am99.setOrderIndex(0);
    ((NumericQuestion)am99).setUnitCategory(null);

    // cleanup the driver
    Driver am99_driver = driverService.findByName("AM99");
    if (am99_driver != null) {
        driverService.remove(am99_driver);
    }

    ((NumericQuestion)am99).setDriver(null);

    JPA.em().persist(am99);
}

    }
    private void createQuestionAM100() {
        // == AM100
        // Desktop PC

        am100 = (IntegerQuestion) questionService.findByCode(QuestionCode.AM100);
if (am100 == null) {
    am100 = new IntegerQuestion(am72, 0, QuestionCode.AM100, null);
    JPA.em().persist(am100);

    // cleanup the driver
    Driver am100_driver = driverService.findByName("AM100");
    if (am100_driver != null) {
        driverService.remove(am100_driver);
    }

} else {
    if (!am100.getQuestionSet().equals(am72) && am72.getQuestions().contains(am100)) {
        am72.getQuestions().remove(am100);
        JPA.em().persist(am72);
    }
    if (am100.getQuestionSet().equals(am72) && !am72.getQuestions().contains(am100)) {
        am72.getQuestions().add(am100);
        JPA.em().persist(am72);
    }
    am100.setOrderIndex(0);
    ((NumericQuestion)am100).setUnitCategory(null);

    // cleanup the driver
    Driver am100_driver = driverService.findByName("AM100");
    if (am100_driver != null) {
        driverService.remove(am100_driver);
    }

    ((NumericQuestion)am100).setDriver(null);

    JPA.em().persist(am100);
}

    }
    private void createQuestionAM101() {
        // == AM101
        // Durée d'utilisation (années)

        am101 = (IntegerQuestion) questionService.findByCode(QuestionCode.AM101);
if (am101 == null) {
    am101 = new IntegerQuestion(am72, 0, QuestionCode.AM101, null);
    JPA.em().persist(am101);

    // cleanup the driver
    Driver am101_driver = driverService.findByName("AM101");
    if (am101_driver != null) {
        driverService.remove(am101_driver);
    }

} else {
    if (!am101.getQuestionSet().equals(am72) && am72.getQuestions().contains(am101)) {
        am72.getQuestions().remove(am101);
        JPA.em().persist(am72);
    }
    if (am101.getQuestionSet().equals(am72) && !am72.getQuestions().contains(am101)) {
        am72.getQuestions().add(am101);
        JPA.em().persist(am72);
    }
    am101.setOrderIndex(0);
    ((NumericQuestion)am101).setUnitCategory(null);

    // cleanup the driver
    Driver am101_driver = driverService.findByName("AM101");
    if (am101_driver != null) {
        driverService.remove(am101_driver);
    }

    ((NumericQuestion)am101).setDriver(null);

    JPA.em().persist(am101);
}

    }
    private void createQuestionAM102() {
        // == AM102
        // Smartphone

        am102 = (IntegerQuestion) questionService.findByCode(QuestionCode.AM102);
if (am102 == null) {
    am102 = new IntegerQuestion(am72, 0, QuestionCode.AM102, null);
    JPA.em().persist(am102);

    // cleanup the driver
    Driver am102_driver = driverService.findByName("AM102");
    if (am102_driver != null) {
        driverService.remove(am102_driver);
    }

} else {
    if (!am102.getQuestionSet().equals(am72) && am72.getQuestions().contains(am102)) {
        am72.getQuestions().remove(am102);
        JPA.em().persist(am72);
    }
    if (am102.getQuestionSet().equals(am72) && !am72.getQuestions().contains(am102)) {
        am72.getQuestions().add(am102);
        JPA.em().persist(am72);
    }
    am102.setOrderIndex(0);
    ((NumericQuestion)am102).setUnitCategory(null);

    // cleanup the driver
    Driver am102_driver = driverService.findByName("AM102");
    if (am102_driver != null) {
        driverService.remove(am102_driver);
    }

    ((NumericQuestion)am102).setDriver(null);

    JPA.em().persist(am102);
}

    }
    private void createQuestionAM103() {
        // == AM103
        // Durée d'utilisation (années)

        am103 = (IntegerQuestion) questionService.findByCode(QuestionCode.AM103);
if (am103 == null) {
    am103 = new IntegerQuestion(am72, 0, QuestionCode.AM103, null);
    JPA.em().persist(am103);

    // cleanup the driver
    Driver am103_driver = driverService.findByName("AM103");
    if (am103_driver != null) {
        driverService.remove(am103_driver);
    }

} else {
    if (!am103.getQuestionSet().equals(am72) && am72.getQuestions().contains(am103)) {
        am72.getQuestions().remove(am103);
        JPA.em().persist(am72);
    }
    if (am103.getQuestionSet().equals(am72) && !am72.getQuestions().contains(am103)) {
        am72.getQuestions().add(am103);
        JPA.em().persist(am72);
    }
    am103.setOrderIndex(0);
    ((NumericQuestion)am103).setUnitCategory(null);

    // cleanup the driver
    Driver am103_driver = driverService.findByName("AM103");
    if (am103_driver != null) {
        driverService.remove(am103_driver);
    }

    ((NumericQuestion)am103).setDriver(null);

    JPA.em().persist(am103);
}

    }
    private void createQuestionAM104() {
        // == AM104
        // Imprimante

        am104 = (IntegerQuestion) questionService.findByCode(QuestionCode.AM104);
if (am104 == null) {
    am104 = new IntegerQuestion(am72, 0, QuestionCode.AM104, null);
    JPA.em().persist(am104);

    // cleanup the driver
    Driver am104_driver = driverService.findByName("AM104");
    if (am104_driver != null) {
        driverService.remove(am104_driver);
    }

} else {
    if (!am104.getQuestionSet().equals(am72) && am72.getQuestions().contains(am104)) {
        am72.getQuestions().remove(am104);
        JPA.em().persist(am72);
    }
    if (am104.getQuestionSet().equals(am72) && !am72.getQuestions().contains(am104)) {
        am72.getQuestions().add(am104);
        JPA.em().persist(am72);
    }
    am104.setOrderIndex(0);
    ((NumericQuestion)am104).setUnitCategory(null);

    // cleanup the driver
    Driver am104_driver = driverService.findByName("AM104");
    if (am104_driver != null) {
        driverService.remove(am104_driver);
    }

    ((NumericQuestion)am104).setDriver(null);

    JPA.em().persist(am104);
}

    }
    private void createQuestionAM105() {
        // == AM105
        // Durée d'utilisation (années)

        am105 = (IntegerQuestion) questionService.findByCode(QuestionCode.AM105);
if (am105 == null) {
    am105 = new IntegerQuestion(am72, 0, QuestionCode.AM105, null);
    JPA.em().persist(am105);

    // cleanup the driver
    Driver am105_driver = driverService.findByName("AM105");
    if (am105_driver != null) {
        driverService.remove(am105_driver);
    }

} else {
    if (!am105.getQuestionSet().equals(am72) && am72.getQuestions().contains(am105)) {
        am72.getQuestions().remove(am105);
        JPA.em().persist(am72);
    }
    if (am105.getQuestionSet().equals(am72) && !am72.getQuestions().contains(am105)) {
        am72.getQuestions().add(am105);
        JPA.em().persist(am72);
    }
    am105.setOrderIndex(0);
    ((NumericQuestion)am105).setUnitCategory(null);

    // cleanup the driver
    Driver am105_driver = driverService.findByName("AM105");
    if (am105_driver != null) {
        driverService.remove(am105_driver);
    }

    ((NumericQuestion)am105).setDriver(null);

    JPA.em().persist(am105);
}

    }
    private void createQuestionAM106() {
        // == AM106
        // GSM

        am106 = (IntegerQuestion) questionService.findByCode(QuestionCode.AM106);
if (am106 == null) {
    am106 = new IntegerQuestion(am72, 0, QuestionCode.AM106, null);
    JPA.em().persist(am106);

    // cleanup the driver
    Driver am106_driver = driverService.findByName("AM106");
    if (am106_driver != null) {
        driverService.remove(am106_driver);
    }

} else {
    if (!am106.getQuestionSet().equals(am72) && am72.getQuestions().contains(am106)) {
        am72.getQuestions().remove(am106);
        JPA.em().persist(am72);
    }
    if (am106.getQuestionSet().equals(am72) && !am72.getQuestions().contains(am106)) {
        am72.getQuestions().add(am106);
        JPA.em().persist(am72);
    }
    am106.setOrderIndex(0);
    ((NumericQuestion)am106).setUnitCategory(null);

    // cleanup the driver
    Driver am106_driver = driverService.findByName("AM106");
    if (am106_driver != null) {
        driverService.remove(am106_driver);
    }

    ((NumericQuestion)am106).setDriver(null);

    JPA.em().persist(am106);
}

    }
    private void createQuestionAM107() {
        // == AM107
        // Durée d'utilisation (années)

        am107 = (IntegerQuestion) questionService.findByCode(QuestionCode.AM107);
if (am107 == null) {
    am107 = new IntegerQuestion(am72, 0, QuestionCode.AM107, null);
    JPA.em().persist(am107);

    // cleanup the driver
    Driver am107_driver = driverService.findByName("AM107");
    if (am107_driver != null) {
        driverService.remove(am107_driver);
    }

} else {
    if (!am107.getQuestionSet().equals(am72) && am72.getQuestions().contains(am107)) {
        am72.getQuestions().remove(am107);
        JPA.em().persist(am72);
    }
    if (am107.getQuestionSet().equals(am72) && !am72.getQuestions().contains(am107)) {
        am72.getQuestions().add(am107);
        JPA.em().persist(am72);
    }
    am107.setOrderIndex(0);
    ((NumericQuestion)am107).setUnitCategory(null);

    // cleanup the driver
    Driver am107_driver = driverService.findByName("AM107");
    if (am107_driver != null) {
        driverService.remove(am107_driver);
    }

    ((NumericQuestion)am107).setDriver(null);

    JPA.em().persist(am107);
}

    }
    private void createQuestionAM108() {
        // == AM108
        // Console de jeux

        am108 = (IntegerQuestion) questionService.findByCode(QuestionCode.AM108);
if (am108 == null) {
    am108 = new IntegerQuestion(am72, 0, QuestionCode.AM108, null);
    JPA.em().persist(am108);

    // cleanup the driver
    Driver am108_driver = driverService.findByName("AM108");
    if (am108_driver != null) {
        driverService.remove(am108_driver);
    }

} else {
    if (!am108.getQuestionSet().equals(am72) && am72.getQuestions().contains(am108)) {
        am72.getQuestions().remove(am108);
        JPA.em().persist(am72);
    }
    if (am108.getQuestionSet().equals(am72) && !am72.getQuestions().contains(am108)) {
        am72.getQuestions().add(am108);
        JPA.em().persist(am72);
    }
    am108.setOrderIndex(0);
    ((NumericQuestion)am108).setUnitCategory(null);

    // cleanup the driver
    Driver am108_driver = driverService.findByName("AM108");
    if (am108_driver != null) {
        driverService.remove(am108_driver);
    }

    ((NumericQuestion)am108).setDriver(null);

    JPA.em().persist(am108);
}

    }
    private void createQuestionAM109() {
        // == AM109
        // Durée d'utilisation (années)

        am109 = (IntegerQuestion) questionService.findByCode(QuestionCode.AM109);
if (am109 == null) {
    am109 = new IntegerQuestion(am72, 0, QuestionCode.AM109, null);
    JPA.em().persist(am109);

    // cleanup the driver
    Driver am109_driver = driverService.findByName("AM109");
    if (am109_driver != null) {
        driverService.remove(am109_driver);
    }

} else {
    if (!am109.getQuestionSet().equals(am72) && am72.getQuestions().contains(am109)) {
        am72.getQuestions().remove(am109);
        JPA.em().persist(am72);
    }
    if (am109.getQuestionSet().equals(am72) && !am72.getQuestions().contains(am109)) {
        am72.getQuestions().add(am109);
        JPA.em().persist(am72);
    }
    am109.setOrderIndex(0);
    ((NumericQuestion)am109).setUnitCategory(null);

    // cleanup the driver
    Driver am109_driver = driverService.findByName("AM109");
    if (am109_driver != null) {
        driverService.remove(am109_driver);
    }

    ((NumericQuestion)am109).setDriver(null);

    JPA.em().persist(am109);
}

    }
    private void createQuestionAM112() {
        // == AM112
        // Véhicule

        am112 = (StringQuestion) questionService.findByCode(QuestionCode.AM112);
if (am112 == null) {
    am112 = new StringQuestion(am111, 0, QuestionCode.AM112, null);
    JPA.em().persist(am112);
} else {
    ((StringQuestion)am112).setDefaultValue(null);
    if (!am112.getQuestionSet().equals(am111) && am111.getQuestions().contains(am112)) {
        am111.getQuestions().remove(am112);
        JPA.em().persist(am111);
    }
    if (am112.getQuestionSet().equals(am111) && !am111.getQuestions().contains(am112)) {
        am111.getQuestions().add(am112);
        JPA.em().persist(am111);
    }
    am112.setOrderIndex(0);
    JPA.em().persist(am112);
}

    }
    private void createQuestionAM113() {
        // == AM113
        // Carburant

        am113 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AM113);
if (am113 == null) {
    am113 = new ValueSelectionQuestion(am111, 0, QuestionCode.AM113, CodeList.CARBURANT);
    JPA.em().persist(am113);
} else {
    if (!am113.getQuestionSet().equals(am111) && am111.getQuestions().contains(am113)) {
        am111.getQuestions().remove(am113);
        JPA.em().persist(am111);
    }
    if (am113.getQuestionSet().equals(am111) && !am111.getQuestions().contains(am113)) {
        am111.getQuestions().add(am113);
        JPA.em().persist(am111);
    }
    am113.setOrderIndex(0);
    ((ValueSelectionQuestion)am113).setCodeList(CodeList.CARBURANT);
    JPA.em().persist(am113);
}

    }
    private void createQuestionAM114() {
        // == AM114
        // Distance parcourue sur l'année

        
am114 = (DoubleQuestion) questionService.findByCode(QuestionCode.AM114);
if (am114 == null) {
    am114 = new DoubleQuestion( am111, 0, QuestionCode.AM114, lengthUnits, getUnitBySymbol("km") );
    JPA.em().persist(am114);

    // cleanup the driver
    Driver am114_driver = driverService.findByName("AM114");
    if (am114_driver != null) {
        driverService.remove(am114_driver);
    }

} else {
    if (!am114.getQuestionSet().equals(am111) && am111.getQuestions().contains(am114)) {
        am111.getQuestions().remove(am114);
        JPA.em().persist(am111);
    }
    if (am114.getQuestionSet().equals(am111) && !am111.getQuestions().contains(am114)) {
        am111.getQuestions().add(am114);
        JPA.em().persist(am111);
    }
    ((NumericQuestion)am114).setUnitCategory(lengthUnits);
    am114.setOrderIndex(0);
    ((NumericQuestion)am114).setDefaultUnit(getUnitBySymbol("km"));

    // cleanup the driver
    Driver am114_driver = driverService.findByName("AM114");
    if (am114_driver != null) {
        driverService.remove(am114_driver);
    }

    ((NumericQuestion)am114).setDriver(null);

    JPA.em().persist(am114);
}



    }
    private void createQuestionAM115() {
        // == AM115
        // Consommation moyenne (l/100km)

        am115 = (IntegerQuestion) questionService.findByCode(QuestionCode.AM115);
if (am115 == null) {
    am115 = new IntegerQuestion(am111, 0, QuestionCode.AM115, null);
    JPA.em().persist(am115);

    // cleanup the driver
    Driver am115_driver = driverService.findByName("AM115");
    if (am115_driver != null) {
        driverService.remove(am115_driver);
    }

} else {
    if (!am115.getQuestionSet().equals(am111) && am111.getQuestions().contains(am115)) {
        am111.getQuestions().remove(am115);
        JPA.em().persist(am111);
    }
    if (am115.getQuestionSet().equals(am111) && !am111.getQuestions().contains(am115)) {
        am111.getQuestions().add(am115);
        JPA.em().persist(am111);
    }
    am115.setOrderIndex(0);
    ((NumericQuestion)am115).setUnitCategory(null);

    // cleanup the driver
    Driver am115_driver = driverService.findByName("AM115");
    if (am115_driver != null) {
        driverService.remove(am115_driver);
    }

    ((NumericQuestion)am115).setDriver(null);

    JPA.em().persist(am115);
}

    }
    private void createQuestionAM116() {
        // == AM116
        // Durée de détention des véhicules (en années)

        am116 = (IntegerQuestion) questionService.findByCode(QuestionCode.AM116);
if (am116 == null) {
    am116 = new IntegerQuestion(am110, 0, QuestionCode.AM116, null);
    JPA.em().persist(am116);

    // cleanup the driver
    Driver am116_driver = driverService.findByName("AM116");
    if (am116_driver != null) {
        driverService.remove(am116_driver);
    }

} else {
    if (!am116.getQuestionSet().equals(am110) && am110.getQuestions().contains(am116)) {
        am110.getQuestions().remove(am116);
        JPA.em().persist(am110);
    }
    if (am116.getQuestionSet().equals(am110) && !am110.getQuestions().contains(am116)) {
        am110.getQuestions().add(am116);
        JPA.em().persist(am110);
    }
    am116.setOrderIndex(0);
    ((NumericQuestion)am116).setUnitCategory(null);

    // cleanup the driver
    Driver am116_driver = driverService.findByName("AM116");
    if (am116_driver != null) {
        driverService.remove(am116_driver);
    }

    ((NumericQuestion)am116).setDriver(null);

    JPA.em().persist(am116);
}

    }
    private void createQuestionAM119() {
        // == AM119
        // Nombre de trajets aller et retour par jour

        am119 = (IntegerQuestion) questionService.findByCode(QuestionCode.AM119);
if (am119 == null) {
    am119 = new IntegerQuestion(am118, 0, QuestionCode.AM119, null);
    JPA.em().persist(am119);

    // cleanup the driver
    Driver am119_driver = driverService.findByName("AM119");
    if (am119_driver != null) {
        driverService.remove(am119_driver);
    }

} else {
    if (!am119.getQuestionSet().equals(am118) && am118.getQuestions().contains(am119)) {
        am118.getQuestions().remove(am119);
        JPA.em().persist(am118);
    }
    if (am119.getQuestionSet().equals(am118) && !am118.getQuestions().contains(am119)) {
        am118.getQuestions().add(am119);
        JPA.em().persist(am118);
    }
    am119.setOrderIndex(0);
    ((NumericQuestion)am119).setUnitCategory(null);

    // cleanup the driver
    Driver am119_driver = driverService.findByName("AM119");
    if (am119_driver != null) {
        driverService.remove(am119_driver);
    }

    ((NumericQuestion)am119).setDriver(null);

    JPA.em().persist(am119);
}

    }
    private void createQuestionAM120() {
        // == AM120
        // Nombre de jours/an d'utilisation du transport public (en moyenne)

        am120 = (IntegerQuestion) questionService.findByCode(QuestionCode.AM120);
if (am120 == null) {
    am120 = new IntegerQuestion(am118, 0, QuestionCode.AM120, null);
    JPA.em().persist(am120);

    // cleanup the driver
    Driver am120_driver = driverService.findByName("AM120");
    if (am120_driver != null) {
        driverService.remove(am120_driver);
    }

} else {
    if (!am120.getQuestionSet().equals(am118) && am118.getQuestions().contains(am120)) {
        am118.getQuestions().remove(am120);
        JPA.em().persist(am118);
    }
    if (am120.getQuestionSet().equals(am118) && !am118.getQuestions().contains(am120)) {
        am118.getQuestions().add(am120);
        JPA.em().persist(am118);
    }
    am120.setOrderIndex(0);
    ((NumericQuestion)am120).setUnitCategory(null);

    // cleanup the driver
    Driver am120_driver = driverService.findByName("AM120");
    if (am120_driver != null) {
        driverService.remove(am120_driver);
    }

    ((NumericQuestion)am120).setDriver(null);

    JPA.em().persist(am120);
}

    }
    private void createQuestionAM121() {
        // == AM121
        // Distance moyenne aller

        
am121 = (DoubleQuestion) questionService.findByCode(QuestionCode.AM121);
if (am121 == null) {
    am121 = new DoubleQuestion( am118, 0, QuestionCode.AM121, lengthUnits, getUnitBySymbol("km") );
    JPA.em().persist(am121);

    // cleanup the driver
    Driver am121_driver = driverService.findByName("AM121");
    if (am121_driver != null) {
        driverService.remove(am121_driver);
    }

} else {
    if (!am121.getQuestionSet().equals(am118) && am118.getQuestions().contains(am121)) {
        am118.getQuestions().remove(am121);
        JPA.em().persist(am118);
    }
    if (am121.getQuestionSet().equals(am118) && !am118.getQuestions().contains(am121)) {
        am118.getQuestions().add(am121);
        JPA.em().persist(am118);
    }
    ((NumericQuestion)am121).setUnitCategory(lengthUnits);
    am121.setOrderIndex(0);
    ((NumericQuestion)am121).setDefaultUnit(getUnitBySymbol("km"));

    // cleanup the driver
    Driver am121_driver = driverService.findByName("AM121");
    if (am121_driver != null) {
        driverService.remove(am121_driver);
    }

    ((NumericQuestion)am121).setDriver(null);

    JPA.em().persist(am121);
}



    }
    private void createQuestionAM123() {
        // == AM123
        // Nombre de trajets aller et retour par jour

        am123 = (IntegerQuestion) questionService.findByCode(QuestionCode.AM123);
if (am123 == null) {
    am123 = new IntegerQuestion(am122, 0, QuestionCode.AM123, null);
    JPA.em().persist(am123);

    // cleanup the driver
    Driver am123_driver = driverService.findByName("AM123");
    if (am123_driver != null) {
        driverService.remove(am123_driver);
    }

} else {
    if (!am123.getQuestionSet().equals(am122) && am122.getQuestions().contains(am123)) {
        am122.getQuestions().remove(am123);
        JPA.em().persist(am122);
    }
    if (am123.getQuestionSet().equals(am122) && !am122.getQuestions().contains(am123)) {
        am122.getQuestions().add(am123);
        JPA.em().persist(am122);
    }
    am123.setOrderIndex(0);
    ((NumericQuestion)am123).setUnitCategory(null);

    // cleanup the driver
    Driver am123_driver = driverService.findByName("AM123");
    if (am123_driver != null) {
        driverService.remove(am123_driver);
    }

    ((NumericQuestion)am123).setDriver(null);

    JPA.em().persist(am123);
}

    }
    private void createQuestionAM124() {
        // == AM124
        // Nombre de jours/an d'utilisation du transport public (en moyenne)

        am124 = (IntegerQuestion) questionService.findByCode(QuestionCode.AM124);
if (am124 == null) {
    am124 = new IntegerQuestion(am122, 0, QuestionCode.AM124, null);
    JPA.em().persist(am124);

    // cleanup the driver
    Driver am124_driver = driverService.findByName("AM124");
    if (am124_driver != null) {
        driverService.remove(am124_driver);
    }

} else {
    if (!am124.getQuestionSet().equals(am122) && am122.getQuestions().contains(am124)) {
        am122.getQuestions().remove(am124);
        JPA.em().persist(am122);
    }
    if (am124.getQuestionSet().equals(am122) && !am122.getQuestions().contains(am124)) {
        am122.getQuestions().add(am124);
        JPA.em().persist(am122);
    }
    am124.setOrderIndex(0);
    ((NumericQuestion)am124).setUnitCategory(null);

    // cleanup the driver
    Driver am124_driver = driverService.findByName("AM124");
    if (am124_driver != null) {
        driverService.remove(am124_driver);
    }

    ((NumericQuestion)am124).setDriver(null);

    JPA.em().persist(am124);
}

    }
    private void createQuestionAM125() {
        // == AM125
        // Distance moyenne aller (km)

        
am125 = (DoubleQuestion) questionService.findByCode(QuestionCode.AM125);
if (am125 == null) {
    am125 = new DoubleQuestion( am122, 0, QuestionCode.AM125, lengthUnits, getUnitBySymbol("km") );
    JPA.em().persist(am125);

    // cleanup the driver
    Driver am125_driver = driverService.findByName("AM125");
    if (am125_driver != null) {
        driverService.remove(am125_driver);
    }

} else {
    if (!am125.getQuestionSet().equals(am122) && am122.getQuestions().contains(am125)) {
        am122.getQuestions().remove(am125);
        JPA.em().persist(am122);
    }
    if (am125.getQuestionSet().equals(am122) && !am122.getQuestions().contains(am125)) {
        am122.getQuestions().add(am125);
        JPA.em().persist(am122);
    }
    ((NumericQuestion)am125).setUnitCategory(lengthUnits);
    am125.setOrderIndex(0);
    ((NumericQuestion)am125).setDefaultUnit(getUnitBySymbol("km"));

    // cleanup the driver
    Driver am125_driver = driverService.findByName("AM125");
    if (am125_driver != null) {
        driverService.remove(am125_driver);
    }

    ((NumericQuestion)am125).setDriver(null);

    JPA.em().persist(am125);
}



    }
    private void createQuestionAM127() {
        // == AM127
        // Nombre de trajets aller et retour par jour

        am127 = (IntegerQuestion) questionService.findByCode(QuestionCode.AM127);
if (am127 == null) {
    am127 = new IntegerQuestion(am126, 0, QuestionCode.AM127, null);
    JPA.em().persist(am127);

    // cleanup the driver
    Driver am127_driver = driverService.findByName("AM127");
    if (am127_driver != null) {
        driverService.remove(am127_driver);
    }

} else {
    if (!am127.getQuestionSet().equals(am126) && am126.getQuestions().contains(am127)) {
        am126.getQuestions().remove(am127);
        JPA.em().persist(am126);
    }
    if (am127.getQuestionSet().equals(am126) && !am126.getQuestions().contains(am127)) {
        am126.getQuestions().add(am127);
        JPA.em().persist(am126);
    }
    am127.setOrderIndex(0);
    ((NumericQuestion)am127).setUnitCategory(null);

    // cleanup the driver
    Driver am127_driver = driverService.findByName("AM127");
    if (am127_driver != null) {
        driverService.remove(am127_driver);
    }

    ((NumericQuestion)am127).setDriver(null);

    JPA.em().persist(am127);
}

    }
    private void createQuestionAM128() {
        // == AM128
        // Nombre de jours/an d'utilisation du transport public (en moyenne)

        am128 = (IntegerQuestion) questionService.findByCode(QuestionCode.AM128);
if (am128 == null) {
    am128 = new IntegerQuestion(am126, 0, QuestionCode.AM128, null);
    JPA.em().persist(am128);

    // cleanup the driver
    Driver am128_driver = driverService.findByName("AM128");
    if (am128_driver != null) {
        driverService.remove(am128_driver);
    }

} else {
    if (!am128.getQuestionSet().equals(am126) && am126.getQuestions().contains(am128)) {
        am126.getQuestions().remove(am128);
        JPA.em().persist(am126);
    }
    if (am128.getQuestionSet().equals(am126) && !am126.getQuestions().contains(am128)) {
        am126.getQuestions().add(am128);
        JPA.em().persist(am126);
    }
    am128.setOrderIndex(0);
    ((NumericQuestion)am128).setUnitCategory(null);

    // cleanup the driver
    Driver am128_driver = driverService.findByName("AM128");
    if (am128_driver != null) {
        driverService.remove(am128_driver);
    }

    ((NumericQuestion)am128).setDriver(null);

    JPA.em().persist(am128);
}

    }
    private void createQuestionAM129() {
        // == AM129
        // Distance moyenne aller (km)

        
am129 = (DoubleQuestion) questionService.findByCode(QuestionCode.AM129);
if (am129 == null) {
    am129 = new DoubleQuestion( am126, 0, QuestionCode.AM129, lengthUnits, getUnitBySymbol("km") );
    JPA.em().persist(am129);

    // cleanup the driver
    Driver am129_driver = driverService.findByName("AM129");
    if (am129_driver != null) {
        driverService.remove(am129_driver);
    }

} else {
    if (!am129.getQuestionSet().equals(am126) && am126.getQuestions().contains(am129)) {
        am126.getQuestions().remove(am129);
        JPA.em().persist(am126);
    }
    if (am129.getQuestionSet().equals(am126) && !am126.getQuestions().contains(am129)) {
        am126.getQuestions().add(am129);
        JPA.em().persist(am126);
    }
    ((NumericQuestion)am129).setUnitCategory(lengthUnits);
    am129.setOrderIndex(0);
    ((NumericQuestion)am129).setDefaultUnit(getUnitBySymbol("km"));

    // cleanup the driver
    Driver am129_driver = driverService.findByName("AM129");
    if (am129_driver != null) {
        driverService.remove(am129_driver);
    }

    ((NumericQuestion)am129).setDriver(null);

    JPA.em().persist(am129);
}



    }
    private void createQuestionAM131() {
        // == AM131
        // Nombre de trajets aller et retour par jour

        am131 = (IntegerQuestion) questionService.findByCode(QuestionCode.AM131);
if (am131 == null) {
    am131 = new IntegerQuestion(am130, 0, QuestionCode.AM131, null);
    JPA.em().persist(am131);

    // cleanup the driver
    Driver am131_driver = driverService.findByName("AM131");
    if (am131_driver != null) {
        driverService.remove(am131_driver);
    }

} else {
    if (!am131.getQuestionSet().equals(am130) && am130.getQuestions().contains(am131)) {
        am130.getQuestions().remove(am131);
        JPA.em().persist(am130);
    }
    if (am131.getQuestionSet().equals(am130) && !am130.getQuestions().contains(am131)) {
        am130.getQuestions().add(am131);
        JPA.em().persist(am130);
    }
    am131.setOrderIndex(0);
    ((NumericQuestion)am131).setUnitCategory(null);

    // cleanup the driver
    Driver am131_driver = driverService.findByName("AM131");
    if (am131_driver != null) {
        driverService.remove(am131_driver);
    }

    ((NumericQuestion)am131).setDriver(null);

    JPA.em().persist(am131);
}

    }
    private void createQuestionAM132() {
        // == AM132
        // Nombre de jours/an d'utilisation du transport public (en moyenne)

        am132 = (IntegerQuestion) questionService.findByCode(QuestionCode.AM132);
if (am132 == null) {
    am132 = new IntegerQuestion(am130, 0, QuestionCode.AM132, null);
    JPA.em().persist(am132);

    // cleanup the driver
    Driver am132_driver = driverService.findByName("AM132");
    if (am132_driver != null) {
        driverService.remove(am132_driver);
    }

} else {
    if (!am132.getQuestionSet().equals(am130) && am130.getQuestions().contains(am132)) {
        am130.getQuestions().remove(am132);
        JPA.em().persist(am130);
    }
    if (am132.getQuestionSet().equals(am130) && !am130.getQuestions().contains(am132)) {
        am130.getQuestions().add(am132);
        JPA.em().persist(am130);
    }
    am132.setOrderIndex(0);
    ((NumericQuestion)am132).setUnitCategory(null);

    // cleanup the driver
    Driver am132_driver = driverService.findByName("AM132");
    if (am132_driver != null) {
        driverService.remove(am132_driver);
    }

    ((NumericQuestion)am132).setDriver(null);

    JPA.em().persist(am132);
}

    }
    private void createQuestionAM133() {
        // == AM133
        // Distance moyenne aller (km)

        
am133 = (DoubleQuestion) questionService.findByCode(QuestionCode.AM133);
if (am133 == null) {
    am133 = new DoubleQuestion( am130, 0, QuestionCode.AM133, lengthUnits, getUnitBySymbol("km") );
    JPA.em().persist(am133);

    // cleanup the driver
    Driver am133_driver = driverService.findByName("AM133");
    if (am133_driver != null) {
        driverService.remove(am133_driver);
    }

} else {
    if (!am133.getQuestionSet().equals(am130) && am130.getQuestions().contains(am133)) {
        am130.getQuestions().remove(am133);
        JPA.em().persist(am130);
    }
    if (am133.getQuestionSet().equals(am130) && !am130.getQuestions().contains(am133)) {
        am130.getQuestions().add(am133);
        JPA.em().persist(am130);
    }
    ((NumericQuestion)am133).setUnitCategory(lengthUnits);
    am133.setOrderIndex(0);
    ((NumericQuestion)am133).setDefaultUnit(getUnitBySymbol("km"));

    // cleanup the driver
    Driver am133_driver = driverService.findByName("AM133");
    if (am133_driver != null) {
        driverService.remove(am133_driver);
    }

    ((NumericQuestion)am133).setDriver(null);

    JPA.em().persist(am133);
}



    }
    private void createQuestionAM135() {
        // == AM135
        // Voyage

        am135 = (StringQuestion) questionService.findByCode(QuestionCode.AM135);
if (am135 == null) {
    am135 = new StringQuestion(am134, 0, QuestionCode.AM135, null);
    JPA.em().persist(am135);
} else {
    ((StringQuestion)am135).setDefaultValue(null);
    if (!am135.getQuestionSet().equals(am134) && am134.getQuestions().contains(am135)) {
        am134.getQuestions().remove(am135);
        JPA.em().persist(am134);
    }
    if (am135.getQuestionSet().equals(am134) && !am134.getQuestions().contains(am135)) {
        am134.getQuestions().add(am135);
        JPA.em().persist(am134);
    }
    am135.setOrderIndex(0);
    JPA.em().persist(am135);
}

    }
    private void createQuestionAM136() {
        // == AM136
        // Type de vol

        am136 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AM136);
if (am136 == null) {
    am136 = new ValueSelectionQuestion(am134, 0, QuestionCode.AM136, CodeList.TYPEVOL);
    JPA.em().persist(am136);
} else {
    if (!am136.getQuestionSet().equals(am134) && am134.getQuestions().contains(am136)) {
        am134.getQuestions().remove(am136);
        JPA.em().persist(am134);
    }
    if (am136.getQuestionSet().equals(am134) && !am134.getQuestions().contains(am136)) {
        am134.getQuestions().add(am136);
        JPA.em().persist(am134);
    }
    am136.setOrderIndex(0);
    ((ValueSelectionQuestion)am136).setCodeList(CodeList.TYPEVOL);
    JPA.em().persist(am136);
}

    }
    private void createQuestionAM137() {
        // == AM137
        // Classe du vol

        am137 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AM137);
if (am137 == null) {
    am137 = new ValueSelectionQuestion(am134, 0, QuestionCode.AM137, CodeList.CATEGORIEVOL);
    JPA.em().persist(am137);
} else {
    if (!am137.getQuestionSet().equals(am134) && am134.getQuestions().contains(am137)) {
        am134.getQuestions().remove(am137);
        JPA.em().persist(am134);
    }
    if (am137.getQuestionSet().equals(am134) && !am134.getQuestions().contains(am137)) {
        am134.getQuestions().add(am137);
        JPA.em().persist(am134);
    }
    am137.setOrderIndex(0);
    ((ValueSelectionQuestion)am137).setCodeList(CodeList.CATEGORIEVOL);
    JPA.em().persist(am137);
}

    }
    private void createQuestionAM138() {
        // == AM138
        // Nombre de vols/an

        am138 = (IntegerQuestion) questionService.findByCode(QuestionCode.AM138);
if (am138 == null) {
    am138 = new IntegerQuestion(am134, 0, QuestionCode.AM138, null);
    JPA.em().persist(am138);

    // cleanup the driver
    Driver am138_driver = driverService.findByName("AM138");
    if (am138_driver != null) {
        driverService.remove(am138_driver);
    }

} else {
    if (!am138.getQuestionSet().equals(am134) && am134.getQuestions().contains(am138)) {
        am134.getQuestions().remove(am138);
        JPA.em().persist(am134);
    }
    if (am138.getQuestionSet().equals(am134) && !am134.getQuestions().contains(am138)) {
        am134.getQuestions().add(am138);
        JPA.em().persist(am134);
    }
    am138.setOrderIndex(0);
    ((NumericQuestion)am138).setUnitCategory(null);

    // cleanup the driver
    Driver am138_driver = driverService.findByName("AM138");
    if (am138_driver != null) {
        driverService.remove(am138_driver);
    }

    ((NumericQuestion)am138).setDriver(null);

    JPA.em().persist(am138);
}

    }
    private void createQuestionAM139() {
        // == AM139
        // Distance moyenne A/R (km)

        
am139 = (DoubleQuestion) questionService.findByCode(QuestionCode.AM139);
if (am139 == null) {
    am139 = new DoubleQuestion( am134, 0, QuestionCode.AM139, lengthUnits, getUnitBySymbol("km") );
    JPA.em().persist(am139);

    // cleanup the driver
    Driver am139_driver = driverService.findByName("AM139");
    if (am139_driver != null) {
        driverService.remove(am139_driver);
    }

} else {
    if (!am139.getQuestionSet().equals(am134) && am134.getQuestions().contains(am139)) {
        am134.getQuestions().remove(am139);
        JPA.em().persist(am134);
    }
    if (am139.getQuestionSet().equals(am134) && !am134.getQuestions().contains(am139)) {
        am134.getQuestions().add(am139);
        JPA.em().persist(am134);
    }
    ((NumericQuestion)am139).setUnitCategory(lengthUnits);
    am139.setOrderIndex(0);
    ((NumericQuestion)am139).setDefaultUnit(getUnitBySymbol("km"));

    // cleanup the driver
    Driver am139_driver = driverService.findByName("AM139");
    if (am139_driver != null) {
        driverService.remove(am139_driver);
    }

    ((NumericQuestion)am139).setDriver(null);

    JPA.em().persist(am139);
}



    }
    private void createQuestionAM141() {
        // == AM141
        // Nombre de trajets aller et retour par an

        am141 = (IntegerQuestion) questionService.findByCode(QuestionCode.AM141);
if (am141 == null) {
    am141 = new IntegerQuestion(am140, 0, QuestionCode.AM141, null);
    JPA.em().persist(am141);

    // cleanup the driver
    Driver am141_driver = driverService.findByName("AM141");
    if (am141_driver != null) {
        driverService.remove(am141_driver);
    }

} else {
    if (!am141.getQuestionSet().equals(am140) && am140.getQuestions().contains(am141)) {
        am140.getQuestions().remove(am141);
        JPA.em().persist(am140);
    }
    if (am141.getQuestionSet().equals(am140) && !am140.getQuestions().contains(am141)) {
        am140.getQuestions().add(am141);
        JPA.em().persist(am140);
    }
    am141.setOrderIndex(0);
    ((NumericQuestion)am141).setUnitCategory(null);

    // cleanup the driver
    Driver am141_driver = driverService.findByName("AM141");
    if (am141_driver != null) {
        driverService.remove(am141_driver);
    }

    ((NumericQuestion)am141).setDriver(null);

    JPA.em().persist(am141);
}

    }
    private void createQuestionAM142() {
        // == AM142
        // Distance moyenne aller (km)

        
am142 = (DoubleQuestion) questionService.findByCode(QuestionCode.AM142);
if (am142 == null) {
    am142 = new DoubleQuestion( am140, 0, QuestionCode.AM142, lengthUnits, getUnitBySymbol("km") );
    JPA.em().persist(am142);

    // cleanup the driver
    Driver am142_driver = driverService.findByName("AM142");
    if (am142_driver != null) {
        driverService.remove(am142_driver);
    }

} else {
    if (!am142.getQuestionSet().equals(am140) && am140.getQuestions().contains(am142)) {
        am140.getQuestions().remove(am142);
        JPA.em().persist(am140);
    }
    if (am142.getQuestionSet().equals(am140) && !am140.getQuestions().contains(am142)) {
        am140.getQuestions().add(am142);
        JPA.em().persist(am140);
    }
    ((NumericQuestion)am142).setUnitCategory(lengthUnits);
    am142.setOrderIndex(0);
    ((NumericQuestion)am142).setDefaultUnit(getUnitBySymbol("km"));

    // cleanup the driver
    Driver am142_driver = driverService.findByName("AM142");
    if (am142_driver != null) {
        driverService.remove(am142_driver);
    }

    ((NumericQuestion)am142).setDriver(null);

    JPA.em().persist(am142);
}



    }
    private void createQuestionAM144() {
        // == AM144
        // Type de vélo

        am144 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AM144);
if (am144 == null) {
    am144 = new ValueSelectionQuestion(am143, 0, QuestionCode.AM144, CodeList.TYPEVELO);
    JPA.em().persist(am144);
} else {
    if (!am144.getQuestionSet().equals(am143) && am143.getQuestions().contains(am144)) {
        am143.getQuestions().remove(am144);
        JPA.em().persist(am143);
    }
    if (am144.getQuestionSet().equals(am143) && !am143.getQuestions().contains(am144)) {
        am143.getQuestions().add(am144);
        JPA.em().persist(am143);
    }
    am144.setOrderIndex(0);
    ((ValueSelectionQuestion)am144).setCodeList(CodeList.TYPEVELO);
    JPA.em().persist(am144);
}

    }
    private void createQuestionAM145() {
        // == AM145
        // Distance parcourue par semaine

        
am145 = (DoubleQuestion) questionService.findByCode(QuestionCode.AM145);
if (am145 == null) {
    am145 = new DoubleQuestion( am143, 0, QuestionCode.AM145, lengthUnits, getUnitBySymbol("km") );
    JPA.em().persist(am145);

    // cleanup the driver
    Driver am145_driver = driverService.findByName("AM145");
    if (am145_driver != null) {
        driverService.remove(am145_driver);
    }

} else {
    if (!am145.getQuestionSet().equals(am143) && am143.getQuestions().contains(am145)) {
        am143.getQuestions().remove(am145);
        JPA.em().persist(am143);
    }
    if (am145.getQuestionSet().equals(am143) && !am143.getQuestions().contains(am145)) {
        am143.getQuestions().add(am145);
        JPA.em().persist(am143);
    }
    ((NumericQuestion)am145).setUnitCategory(lengthUnits);
    am145.setOrderIndex(0);
    ((NumericQuestion)am145).setDefaultUnit(getUnitBySymbol("km"));

    // cleanup the driver
    Driver am145_driver = driverService.findByName("AM145");
    if (am145_driver != null) {
        driverService.remove(am145_driver);
    }

    ((NumericQuestion)am145).setDriver(null);

    JPA.em().persist(am145);
}



    }
    private void createQuestionAM146() {
        // == AM146
        // Nombre moyen de semaines/an d'utilisation du vélo

        am146 = (IntegerQuestion) questionService.findByCode(QuestionCode.AM146);
if (am146 == null) {
    am146 = new IntegerQuestion(am143, 0, QuestionCode.AM146, null);
    JPA.em().persist(am146);

    // cleanup the driver
    Driver am146_driver = driverService.findByName("AM146");
    if (am146_driver != null) {
        driverService.remove(am146_driver);
    }

} else {
    if (!am146.getQuestionSet().equals(am143) && am143.getQuestions().contains(am146)) {
        am143.getQuestions().remove(am146);
        JPA.em().persist(am143);
    }
    if (am146.getQuestionSet().equals(am143) && !am143.getQuestions().contains(am146)) {
        am143.getQuestions().add(am146);
        JPA.em().persist(am143);
    }
    am146.setOrderIndex(0);
    ((NumericQuestion)am146).setUnitCategory(null);

    // cleanup the driver
    Driver am146_driver = driverService.findByName("AM146");
    if (am146_driver != null) {
        driverService.remove(am146_driver);
    }

    ((NumericQuestion)am146).setDriver(null);

    JPA.em().persist(am146);
}

    }
    private void createQuestionAM148() {
        // == AM148
        // Distance parcourue par semaine

        
am148 = (DoubleQuestion) questionService.findByCode(QuestionCode.AM148);
if (am148 == null) {
    am148 = new DoubleQuestion( am147, 0, QuestionCode.AM148, lengthUnits, getUnitBySymbol("km") );
    JPA.em().persist(am148);

    // cleanup the driver
    Driver am148_driver = driverService.findByName("AM148");
    if (am148_driver != null) {
        driverService.remove(am148_driver);
    }

} else {
    if (!am148.getQuestionSet().equals(am147) && am147.getQuestions().contains(am148)) {
        am147.getQuestions().remove(am148);
        JPA.em().persist(am147);
    }
    if (am148.getQuestionSet().equals(am147) && !am147.getQuestions().contains(am148)) {
        am147.getQuestions().add(am148);
        JPA.em().persist(am147);
    }
    ((NumericQuestion)am148).setUnitCategory(lengthUnits);
    am148.setOrderIndex(0);
    ((NumericQuestion)am148).setDefaultUnit(getUnitBySymbol("km"));

    // cleanup the driver
    Driver am148_driver = driverService.findByName("AM148");
    if (am148_driver != null) {
        driverService.remove(am148_driver);
    }

    ((NumericQuestion)am148).setDriver(null);

    JPA.em().persist(am148);
}



    }
    private void createQuestionAM149() {
        // == AM149
        // Nombre moyen de semaines/an d'utilisation de marche

        am149 = (IntegerQuestion) questionService.findByCode(QuestionCode.AM149);
if (am149 == null) {
    am149 = new IntegerQuestion(am147, 0, QuestionCode.AM149, null);
    JPA.em().persist(am149);

    // cleanup the driver
    Driver am149_driver = driverService.findByName("AM149");
    if (am149_driver != null) {
        driverService.remove(am149_driver);
    }

} else {
    if (!am149.getQuestionSet().equals(am147) && am147.getQuestions().contains(am149)) {
        am147.getQuestions().remove(am149);
        JPA.em().persist(am147);
    }
    if (am149.getQuestionSet().equals(am147) && !am147.getQuestions().contains(am149)) {
        am147.getQuestions().add(am149);
        JPA.em().persist(am147);
    }
    am149.setOrderIndex(0);
    ((NumericQuestion)am149).setUnitCategory(null);

    // cleanup the driver
    Driver am149_driver = driverService.findByName("AM149");
    if (am149_driver != null) {
        driverService.remove(am149_driver);
    }

    ((NumericQuestion)am149).setDriver(null);

    JPA.em().persist(am149);
}

    }
    private void createQuestionAM151() {
        // == AM151
        // Disposez-vous des données de poids ou de volume de déchets générés par votre ménage?

        am151 = (BooleanQuestion) questionService.findByCode(QuestionCode.AM151);
if (am151 == null) {
    am151 = new BooleanQuestion(am150, 0, QuestionCode.AM151, null);
    JPA.em().persist(am151);
} else {
    ((BooleanQuestion)am151).setDefaultValue(null);
    if (!am151.getQuestionSet().equals(am150) && am150.getQuestions().contains(am151)) {
        am150.getQuestions().remove(am151);
        JPA.em().persist(am150);
    }
    if (am151.getQuestionSet().equals(am150) && !am150.getQuestions().contains(am151)) {
        am150.getQuestions().add(am151);
        JPA.em().persist(am150);
    }
    am151.setOrderIndex(0);
    JPA.em().persist(am151);
}

    }
    private void createQuestionAM152() {
        // == AM152
        // Nous vous assignerons donc la moyenne wallonne de déchets ménagers sur une année:

        
am152 = (DoubleQuestion) questionService.findByCode(QuestionCode.AM152);
if (am152 == null) {
    am152 = new DoubleQuestion( am150, 0, QuestionCode.AM152, massUnits, massUnits.getMainUnit() );
    JPA.em().persist(am152);

    // cleanup the driver
    Driver am152_driver = driverService.findByName("AM152");
    if (am152_driver != null) {
        driverService.remove(am152_driver);
    }

} else {
    if (!am152.getQuestionSet().equals(am150) && am150.getQuestions().contains(am152)) {
        am150.getQuestions().remove(am152);
        JPA.em().persist(am150);
    }
    if (am152.getQuestionSet().equals(am150) && !am150.getQuestions().contains(am152)) {
        am150.getQuestions().add(am152);
        JPA.em().persist(am150);
    }
    ((NumericQuestion)am152).setUnitCategory(massUnits);
    am152.setOrderIndex(0);
    ((NumericQuestion)am152).setDefaultUnit(massUnits.getMainUnit());


    // cleanup the driver
    Driver am152_driver = driverService.findByName("AM152");
    if (am152_driver != null) {
        driverService.remove(am152_driver);
    }

    ((NumericQuestion)am152).setDriver(null);

    JPA.em().persist(am152);
}



    }
    private void createQuestionAM154() {
        // == AM154
        // Type de déchet, et traitement appliqué

        am154 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AM154);
if (am154 == null) {
    am154 = new ValueSelectionQuestion(am153, 0, QuestionCode.AM154, CodeList.TYPEDECHETMENAGE);
    JPA.em().persist(am154);
} else {
    if (!am154.getQuestionSet().equals(am153) && am153.getQuestions().contains(am154)) {
        am153.getQuestions().remove(am154);
        JPA.em().persist(am153);
    }
    if (am154.getQuestionSet().equals(am153) && !am153.getQuestions().contains(am154)) {
        am153.getQuestions().add(am154);
        JPA.em().persist(am153);
    }
    am154.setOrderIndex(0);
    ((ValueSelectionQuestion)am154).setCodeList(CodeList.TYPEDECHETMENAGE);
    JPA.em().persist(am154);
}

    }
    private void createQuestionAM155() {
        // == AM155
        // Type de données disponibles

        am155 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AM155);
if (am155 == null) {
    am155 = new ValueSelectionQuestion(am153, 0, QuestionCode.AM155, CodeList.MESUREDECHETMENAGE);
    JPA.em().persist(am155);
} else {
    if (!am155.getQuestionSet().equals(am153) && am153.getQuestions().contains(am155)) {
        am153.getQuestions().remove(am155);
        JPA.em().persist(am153);
    }
    if (am155.getQuestionSet().equals(am153) && !am153.getQuestions().contains(am155)) {
        am153.getQuestions().add(am155);
        JPA.em().persist(am153);
    }
    am155.setOrderIndex(0);
    ((ValueSelectionQuestion)am155).setCodeList(CodeList.MESUREDECHETMENAGE);
    JPA.em().persist(am155);
}

    }
    private void createQuestionAM157() {
        // == AM157
        // Nombre de sac/conteneurs par mois

        am157 = (IntegerQuestion) questionService.findByCode(QuestionCode.AM157);
if (am157 == null) {
    am157 = new IntegerQuestion(am156, 0, QuestionCode.AM157, null);
    JPA.em().persist(am157);

    // cleanup the driver
    Driver am157_driver = driverService.findByName("AM157");
    if (am157_driver != null) {
        driverService.remove(am157_driver);
    }

} else {
    if (!am157.getQuestionSet().equals(am156) && am156.getQuestions().contains(am157)) {
        am156.getQuestions().remove(am157);
        JPA.em().persist(am156);
    }
    if (am157.getQuestionSet().equals(am156) && !am156.getQuestions().contains(am157)) {
        am156.getQuestions().add(am157);
        JPA.em().persist(am156);
    }
    am157.setOrderIndex(0);
    ((NumericQuestion)am157).setUnitCategory(null);

    // cleanup the driver
    Driver am157_driver = driverService.findByName("AM157");
    if (am157_driver != null) {
        driverService.remove(am157_driver);
    }

    ((NumericQuestion)am157).setDriver(null);

    JPA.em().persist(am157);
}

    }
    private void createQuestionAM158() {
        // == AM158
        // Volume des sacs/conteneurs

        
am158 = (DoubleQuestion) questionService.findByCode(QuestionCode.AM158);
if (am158 == null) {
    am158 = new DoubleQuestion( am156, 0, QuestionCode.AM158, volumeUnits, getUnitBySymbol("l") );
    JPA.em().persist(am158);

    // cleanup the driver
    Driver am158_driver = driverService.findByName("AM158");
    if (am158_driver != null) {
        driverService.remove(am158_driver);
    }

} else {
    if (!am158.getQuestionSet().equals(am156) && am156.getQuestions().contains(am158)) {
        am156.getQuestions().remove(am158);
        JPA.em().persist(am156);
    }
    if (am158.getQuestionSet().equals(am156) && !am156.getQuestions().contains(am158)) {
        am156.getQuestions().add(am158);
        JPA.em().persist(am156);
    }
    ((NumericQuestion)am158).setUnitCategory(volumeUnits);
    am158.setOrderIndex(0);
    ((NumericQuestion)am158).setDefaultUnit(getUnitBySymbol("l"));

    // cleanup the driver
    Driver am158_driver = driverService.findByName("AM158");
    if (am158_driver != null) {
        driverService.remove(am158_driver);
    }

    ((NumericQuestion)am158).setDriver(null);

    JPA.em().persist(am158);
}



    }
    private void createQuestionAM159() {
        // == AM159
        // % de remplissage

        am159 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AM159);
if (am159 == null) {
    am159 = new ValueSelectionQuestion(am156, 0, QuestionCode.AM159, CodeList.FRACTIONREMPLISSAGEDECHETS);
    JPA.em().persist(am159);
} else {
    if (!am159.getQuestionSet().equals(am156) && am156.getQuestions().contains(am159)) {
        am156.getQuestions().remove(am159);
        JPA.em().persist(am156);
    }
    if (am159.getQuestionSet().equals(am156) && !am156.getQuestions().contains(am159)) {
        am156.getQuestions().add(am159);
        JPA.em().persist(am156);
    }
    am159.setOrderIndex(0);
    ((ValueSelectionQuestion)am159).setCodeList(CodeList.FRACTIONREMPLISSAGEDECHETS);
    JPA.em().persist(am159);
}

    }
    private void createQuestionAM161() {
        // == AM161
        // Quantité annuelle

        
am161 = (DoubleQuestion) questionService.findByCode(QuestionCode.AM161);
if (am161 == null) {
    am161 = new DoubleQuestion( am160, 0, QuestionCode.AM161, massUnits, getUnitBySymbol("kg") );
    JPA.em().persist(am161);

    // cleanup the driver
    Driver am161_driver = driverService.findByName("AM161");
    if (am161_driver != null) {
        driverService.remove(am161_driver);
    }

} else {
    if (!am161.getQuestionSet().equals(am160) && am160.getQuestions().contains(am161)) {
        am160.getQuestions().remove(am161);
        JPA.em().persist(am160);
    }
    if (am161.getQuestionSet().equals(am160) && !am160.getQuestions().contains(am161)) {
        am160.getQuestions().add(am161);
        JPA.em().persist(am160);
    }
    ((NumericQuestion)am161).setUnitCategory(massUnits);
    am161.setOrderIndex(0);
    ((NumericQuestion)am161).setDefaultUnit(getUnitBySymbol("kg"));


    // cleanup the driver
    Driver am161_driver = driverService.findByName("AM161");
    if (am161_driver != null) {
        driverService.remove(am161_driver);
    }

    ((NumericQuestion)am161).setDriver(null);

    JPA.em().persist(am161);
}



    }
    private void createQuestionAM166() {
        // == AM166
        // Quantité annuelle

        
am166 = (DoubleQuestion) questionService.findByCode(QuestionCode.AM166);
if (am166 == null) {
    am166 = new DoubleQuestion( am153, 0, QuestionCode.AM166, massUnits, getUnitBySymbol("kg") );
    JPA.em().persist(am166);

    // cleanup the driver
    Driver am166_driver = driverService.findByName("AM166");
    if (am166_driver != null) {
        driverService.remove(am166_driver);
    }

} else {
    if (!am166.getQuestionSet().equals(am153) && am153.getQuestions().contains(am166)) {
        am153.getQuestions().remove(am166);
        JPA.em().persist(am153);
    }
    if (am166.getQuestionSet().equals(am153) && !am153.getQuestions().contains(am166)) {
        am153.getQuestions().add(am166);
        JPA.em().persist(am153);
    }
    ((NumericQuestion)am166).setUnitCategory(massUnits);
    am166.setOrderIndex(0);
    ((NumericQuestion)am166).setDefaultUnit(getUnitBySymbol("kg"));


    // cleanup the driver
    Driver am166_driver = driverService.findByName("AM166");
    if (am166_driver != null) {
        driverService.remove(am166_driver);
    }

    ((NumericQuestion)am166).setDriver(null);

    JPA.em().persist(am166);
}



    }
    private void createQuestionAM170() {
        // == AM170
        // Pièces documentaires liées à l'alimentation

        am170 = (DocumentQuestion) questionService.findByCode(QuestionCode.AM170);
if (am170 == null) {
    am170 = new DocumentQuestion(am169, 0, QuestionCode.AM170);
    JPA.em().persist(am170);
} else {
    if (!am170.getQuestionSet().equals(am169) && am169.getQuestions().contains(am170)) {
        am169.getQuestions().remove(am170);
        JPA.em().persist(am169);
    }
    if (am170.getQuestionSet().equals(am169) && !am169.getQuestions().contains(am170)) {
        am169.getQuestions().add(am170);
        JPA.em().persist(am169);
    }
    am170.setOrderIndex(0);
    JPA.em().persist(am170);
}

    }
    private void createQuestionAM172() {
        // == AM172
        // Avec viande rouge (nombre de repas par semaine)

        am172 = (IntegerQuestion) questionService.findByCode(QuestionCode.AM172);
if (am172 == null) {
    am172 = new IntegerQuestion(am171, 0, QuestionCode.AM172, null);
    JPA.em().persist(am172);

    // cleanup the driver
    Driver am172_driver = driverService.findByName("AM172");
    if (am172_driver != null) {
        driverService.remove(am172_driver);
    }

} else {
    if (!am172.getQuestionSet().equals(am171) && am171.getQuestions().contains(am172)) {
        am171.getQuestions().remove(am172);
        JPA.em().persist(am171);
    }
    if (am172.getQuestionSet().equals(am171) && !am171.getQuestions().contains(am172)) {
        am171.getQuestions().add(am172);
        JPA.em().persist(am171);
    }
    am172.setOrderIndex(0);
    ((NumericQuestion)am172).setUnitCategory(null);

    // cleanup the driver
    Driver am172_driver = driverService.findByName("AM172");
    if (am172_driver != null) {
        driverService.remove(am172_driver);
    }

    ((NumericQuestion)am172).setDriver(null);

    JPA.em().persist(am172);
}

    }
    private void createQuestionAM173() {
        // == AM173
        // Avec viande blanche ou poisson (nombre de repas par semaine)

        am173 = (IntegerQuestion) questionService.findByCode(QuestionCode.AM173);
if (am173 == null) {
    am173 = new IntegerQuestion(am171, 0, QuestionCode.AM173, null);
    JPA.em().persist(am173);

    // cleanup the driver
    Driver am173_driver = driverService.findByName("AM173");
    if (am173_driver != null) {
        driverService.remove(am173_driver);
    }

} else {
    if (!am173.getQuestionSet().equals(am171) && am171.getQuestions().contains(am173)) {
        am171.getQuestions().remove(am173);
        JPA.em().persist(am171);
    }
    if (am173.getQuestionSet().equals(am171) && !am171.getQuestions().contains(am173)) {
        am171.getQuestions().add(am173);
        JPA.em().persist(am171);
    }
    am173.setOrderIndex(0);
    ((NumericQuestion)am173).setUnitCategory(null);

    // cleanup the driver
    Driver am173_driver = driverService.findByName("AM173");
    if (am173_driver != null) {
        driverService.remove(am173_driver);
    }

    ((NumericQuestion)am173).setDriver(null);

    JPA.em().persist(am173);
}

    }
    private void createQuestionAM174() {
        // == AM174
        // Repas végétariens (nombre de repas par semaine)

        am174 = (IntegerQuestion) questionService.findByCode(QuestionCode.AM174);
if (am174 == null) {
    am174 = new IntegerQuestion(am171, 0, QuestionCode.AM174, null);
    JPA.em().persist(am174);

    // cleanup the driver
    Driver am174_driver = driverService.findByName("AM174");
    if (am174_driver != null) {
        driverService.remove(am174_driver);
    }

} else {
    if (!am174.getQuestionSet().equals(am171) && am171.getQuestions().contains(am174)) {
        am171.getQuestions().remove(am174);
        JPA.em().persist(am171);
    }
    if (am174.getQuestionSet().equals(am171) && !am171.getQuestions().contains(am174)) {
        am171.getQuestions().add(am174);
        JPA.em().persist(am171);
    }
    am174.setOrderIndex(0);
    ((NumericQuestion)am174).setUnitCategory(null);

    // cleanup the driver
    Driver am174_driver = driverService.findByName("AM174");
    if (am174_driver != null) {
        driverService.remove(am174_driver);
    }

    ((NumericQuestion)am174).setDriver(null);

    JPA.em().persist(am174);
}

    }
    private void createQuestionAM176() {
        // == AM176
        // Pièces documentaires liées aux achats

        am176 = (DocumentQuestion) questionService.findByCode(QuestionCode.AM176);
if (am176 == null) {
    am176 = new DocumentQuestion(am175, 0, QuestionCode.AM176);
    JPA.em().persist(am176);
} else {
    if (!am176.getQuestionSet().equals(am175) && am175.getQuestions().contains(am176)) {
        am175.getQuestions().remove(am176);
        JPA.em().persist(am175);
    }
    if (am176.getQuestionSet().equals(am175) && !am175.getQuestions().contains(am176)) {
        am175.getQuestions().add(am176);
        JPA.em().persist(am175);
    }
    am176.setOrderIndex(0);
    JPA.em().persist(am176);
}

    }
    private void createQuestionAM177() {
        // == AM177
        // Achats textiles (nombre de pièces par an)

        am177 = (IntegerQuestion) questionService.findByCode(QuestionCode.AM177);
if (am177 == null) {
    am177 = new IntegerQuestion(am175, 0, QuestionCode.AM177, null);
    JPA.em().persist(am177);

    // cleanup the driver
    Driver am177_driver = driverService.findByName("AM177");
    if (am177_driver != null) {
        driverService.remove(am177_driver);
    }

} else {
    if (!am177.getQuestionSet().equals(am175) && am175.getQuestions().contains(am177)) {
        am175.getQuestions().remove(am177);
        JPA.em().persist(am175);
    }
    if (am177.getQuestionSet().equals(am175) && !am175.getQuestions().contains(am177)) {
        am175.getQuestions().add(am177);
        JPA.em().persist(am175);
    }
    am177.setOrderIndex(0);
    ((NumericQuestion)am177).setUnitCategory(null);

    // cleanup the driver
    Driver am177_driver = driverService.findByName("AM177");
    if (am177_driver != null) {
        driverService.remove(am177_driver);
    }

    ((NumericQuestion)am177).setDriver(null);

    JPA.em().persist(am177);
}

    }
    private void createQuestionAM178() {
        // == AM178
        // Achats informatiques(valeur sur l'année)

        
am178 = (DoubleQuestion) questionService.findByCode(QuestionCode.AM178);
if (am178 == null) {
    am178 = new DoubleQuestion( am175, 0, QuestionCode.AM178, moneyUnits, getUnitBySymbol("EUR") );
    JPA.em().persist(am178);

    // cleanup the driver
    Driver am178_driver = driverService.findByName("AM178");
    if (am178_driver != null) {
        driverService.remove(am178_driver);
    }

} else {
    if (!am178.getQuestionSet().equals(am175) && am175.getQuestions().contains(am178)) {
        am175.getQuestions().remove(am178);
        JPA.em().persist(am175);
    }
    if (am178.getQuestionSet().equals(am175) && !am175.getQuestions().contains(am178)) {
        am175.getQuestions().add(am178);
        JPA.em().persist(am175);
    }
    ((NumericQuestion)am178).setUnitCategory(moneyUnits);
    am178.setOrderIndex(0);
    ((NumericQuestion)am178).setDefaultUnit(getUnitBySymbol("EUR"));

    // cleanup the driver
    Driver am178_driver = driverService.findByName("AM178");
    if (am178_driver != null) {
        driverService.remove(am178_driver);
    }

    ((NumericQuestion)am178).setDriver(null);

    JPA.em().persist(am178);
}



    }
    private void createQuestionAM179() {
        // == AM179
        // Quantité de papier (nombre de feuilles par an)

        am179 = (IntegerQuestion) questionService.findByCode(QuestionCode.AM179);
if (am179 == null) {
    am179 = new IntegerQuestion(am175, 0, QuestionCode.AM179, null);
    JPA.em().persist(am179);

    // cleanup the driver
    Driver am179_driver = driverService.findByName("AM179");
    if (am179_driver != null) {
        driverService.remove(am179_driver);
    }

} else {
    if (!am179.getQuestionSet().equals(am175) && am175.getQuestions().contains(am179)) {
        am175.getQuestions().remove(am179);
        JPA.em().persist(am175);
    }
    if (am179.getQuestionSet().equals(am175) && !am175.getQuestions().contains(am179)) {
        am175.getQuestions().add(am179);
        JPA.em().persist(am175);
    }
    am179.setOrderIndex(0);
    ((NumericQuestion)am179).setUnitCategory(null);

    // cleanup the driver
    Driver am179_driver = driverService.findByName("AM179");
    if (am179_driver != null) {
        driverService.remove(am179_driver);
    }

    ((NumericQuestion)am179).setDriver(null);

    JPA.em().persist(am179);
}

    }


    private UnitCategory getUnitCategoryByCode(UnitCategoryCode unitCategoryCode) {
        return unitCategoryService.findByCode(unitCategoryCode);
    }

    private Unit getUnitBySymbol(String symbol) {
        return unitService.findBySymbol(symbol);
    }

}



