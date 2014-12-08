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
public class AwacEventInitialData {

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
    private QuestionSet aev2,aev10,aev11,aev13,aev27,aev31,aev37,aev38,aev42,aev57,aev58,aev64,aev66,aev67,aev78,aev84,aev85,aev91,aev94,aev96,aev97,aev98,aev106,aev109,aev112,aev116,aev124,aev126,aev127,aev130,aev132,aev133,aev136;
    private Question aev3,aev4,aev5,aev6,aev7,aev8,aev9,aev12,aev14,aev15,aev16,aev17,aev18,aev19,aev20,aev21,aev22,aev23,aev24,aev25,aev26,aev28,aev29,aev30,aev32,aev33,aev34,aev35,aev36,aev39,aev40,aev41,aev43,aev44,aev45,aev46,aev47,aev48,aev49,aev50,aev51,aev52,aev53,aev54,aev55,aev56,aev59,aev60,aev61,aev62,aev63,aev65,aev68,aev74,aev76,aev77,aev79,aev80,aev81,aev82,aev83,aev86,aev87,aev88,aev89,aev90,aev92,aev93,aev95,aev99,aev100,aev101,aev102,aev103,aev104,aev105,aev107,aev110,aev111,aev113,aev114,aev115,aev117,aev122,aev125,aev128,aev129,aev131,aev134,aev135,aev137;

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

        Logger.info("===> CREATE AWAC Event INITIAL DATA -- START");

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
            List<String> codes = Arrays.asList("AEV3", "AEV4", "AEV5", "AEV6", "AEV7", "AEV8", "AEV9", "AEV12", "AEV14", "AEV15", "AEV16", "AEV17", "AEV18", "AEV19", "AEV20", "AEV21", "AEV22", "AEV23", "AEV24", "AEV25", "AEV26", "AEV28", "AEV29", "AEV30", "AEV32", "AEV33", "AEV34", "AEV35", "AEV36", "AEV39", "AEV40", "AEV41", "AEV43", "AEV44", "AEV45", "AEV46", "AEV47", "AEV48", "AEV49", "AEV50", "AEV51", "AEV52", "AEV53", "AEV54", "AEV55", "AEV56", "AEV59", "AEV60", "AEV61", "AEV62", "AEV63", "AEV65", "AEV68", "AEV74", "AEV76", "AEV77", "AEV79", "AEV80", "AEV81", "AEV82", "AEV83", "AEV86", "AEV87", "AEV88", "AEV89", "AEV90", "AEV92", "AEV93", "AEV95", "AEV99", "AEV100", "AEV101", "AEV102", "AEV103", "AEV104", "AEV105", "AEV107", "AEV110", "AEV111", "AEV113", "AEV114", "AEV115", "AEV117", "AEV122", "AEV125", "AEV128", "AEV129", "AEV131", "AEV134", "AEV135", "AEV137");

			for (Question q : new ArrayList<>(allQuestions)) {
				if (codes.contains(q.getCode().getKey()) || !q.getCode().getKey().matches("AEV[0-9]+")) {
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
            List<String> codes = Arrays.asList("AEV2", "AEV10", "AEV11", "AEV13", "AEV27", "AEV31", "AEV37", "AEV38", "AEV42", "AEV57", "AEV58", "AEV64", "AEV66", "AEV67", "AEV78", "AEV84", "AEV85", "AEV91", "AEV94", "AEV96", "AEV97", "AEV98", "AEV106", "AEV109", "AEV112", "AEV116", "AEV124", "AEV126", "AEV127", "AEV130", "AEV132", "AEV133", "AEV136");

			for (QuestionSet qs : new ArrayList<>(allQuestionSets)) {
				if (codes.contains(qs.getCode().getKey()) || !qs.getCode().getKey().matches("AEV[0-9]+")) {
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

        createQuestionSetAEV2();
        createQuestionSetAEV10();
        createQuestionSetAEV11();
        createQuestionSetAEV13();
        createQuestionSetAEV27();
        createQuestionSetAEV31();
        createQuestionSetAEV37();
        createQuestionSetAEV38();
        createQuestionSetAEV42();
        createQuestionSetAEV57();
        createQuestionSetAEV58();
        createQuestionSetAEV64();
        createQuestionSetAEV66();
        createQuestionSetAEV67();
        createQuestionSetAEV78();
        createQuestionSetAEV84();
        createQuestionSetAEV85();
        createQuestionSetAEV91();
        createQuestionSetAEV94();
        createQuestionSetAEV96();
        createQuestionSetAEV97();
        createQuestionSetAEV98();
        createQuestionSetAEV106();
        createQuestionSetAEV109();
        createQuestionSetAEV112();
        createQuestionSetAEV116();
        createQuestionSetAEV124();
        createQuestionSetAEV126();
        createQuestionSetAEV127();
        createQuestionSetAEV130();
        createQuestionSetAEV132();
        createQuestionSetAEV133();
        createQuestionSetAEV136();

        createQuestionAEV3();
        createQuestionAEV4();
        createQuestionAEV5();
        createQuestionAEV6();
        createQuestionAEV7();
        createQuestionAEV8();
        createQuestionAEV9();
        createQuestionAEV12();
        createQuestionAEV14();
        createQuestionAEV15();
        createQuestionAEV16();
        createQuestionAEV17();
        createQuestionAEV18();
        createQuestionAEV19();
        createQuestionAEV20();
        createQuestionAEV21();
        createQuestionAEV22();
        createQuestionAEV23();
        createQuestionAEV24();
        createQuestionAEV25();
        createQuestionAEV26();
        createQuestionAEV28();
        createQuestionAEV29();
        createQuestionAEV30();
        createQuestionAEV32();
        createQuestionAEV33();
        createQuestionAEV34();
        createQuestionAEV35();
        createQuestionAEV36();
        createQuestionAEV39();
        createQuestionAEV40();
        createQuestionAEV41();
        createQuestionAEV43();
        createQuestionAEV44();
        createQuestionAEV45();
        createQuestionAEV46();
        createQuestionAEV47();
        createQuestionAEV48();
        createQuestionAEV49();
        createQuestionAEV50();
        createQuestionAEV51();
        createQuestionAEV52();
        createQuestionAEV53();
        createQuestionAEV54();
        createQuestionAEV55();
        createQuestionAEV56();
        createQuestionAEV59();
        createQuestionAEV60();
        createQuestionAEV61();
        createQuestionAEV62();
        createQuestionAEV63();
        createQuestionAEV65();
        createQuestionAEV68();
        createQuestionAEV74();
        createQuestionAEV76();
        createQuestionAEV77();
        createQuestionAEV79();
        createQuestionAEV80();
        createQuestionAEV81();
        createQuestionAEV82();
        createQuestionAEV83();
        createQuestionAEV86();
        createQuestionAEV87();
        createQuestionAEV88();
        createQuestionAEV89();
        createQuestionAEV90();
        createQuestionAEV92();
        createQuestionAEV93();
        createQuestionAEV95();
        createQuestionAEV99();
        createQuestionAEV100();
        createQuestionAEV101();
        createQuestionAEV102();
        createQuestionAEV103();
        createQuestionAEV104();
        createQuestionAEV105();
        createQuestionAEV107();
        createQuestionAEV110();
        createQuestionAEV111();
        createQuestionAEV113();
        createQuestionAEV114();
        createQuestionAEV115();
        createQuestionAEV117();
        createQuestionAEV122();
        createQuestionAEV125();
        createQuestionAEV128();
        createQuestionAEV129();
        createQuestionAEV131();
        createQuestionAEV134();
        createQuestionAEV135();
        createQuestionAEV137();


        Logger.info("===> CREATE AWAC Event INITIAL DATA -- END (Took {} milliseconds)", (System.currentTimeMillis() - startTime));
    }

    // =========================================================================
    // FORMS
    // =========================================================================

    private void createForm1() {
        // == TAB_EV1
        // DESCRIPTION DE L'EVENEMENT
        form1 = formService.findByIdentifier("TAB_EV1");
        if (form1 == null) {
            form1 = new Form("TAB_EV1");
            JPA.em().persist(form1);
        }
    }
    private void createForm2() {
        // == TAB_EV2
        // COMBUSTBILES & REFRIGERATION
        form2 = formService.findByIdentifier("TAB_EV2");
        if (form2 == null) {
            form2 = new Form("TAB_EV2");
            JPA.em().persist(form2);
        }
    }
    private void createForm3() {
        // == TAB_EV3
        // MOBILITE
        form3 = formService.findByIdentifier("TAB_EV3");
        if (form3 == null) {
            form3 = new Form("TAB_EV3");
            JPA.em().persist(form3);
        }
    }
    private void createForm4() {
        // == TAB_EV4
        // LOGISTIQUE
        form4 = formService.findByIdentifier("TAB_EV4");
        if (form4 == null) {
            form4 = new Form("TAB_EV4");
            JPA.em().persist(form4);
        }
    }
    private void createForm5() {
        // == TAB_EV5
        // DECHETS
        form5 = formService.findByIdentifier("TAB_EV5");
        if (form5 == null) {
            form5 = new Form("TAB_EV5");
            JPA.em().persist(form5);
        }
    }
    private void createForm6() {
        // == TAB_EV6
        // ACHATS
        form6 = formService.findByIdentifier("TAB_EV6");
        if (form6 == null) {
            form6 = new Form("TAB_EV6");
            JPA.em().persist(form6);
        }
    }


    // =========================================================================
    // QUESTION SETS
    // =========================================================================

    private void createQuestionSetAEV2() {
        // == AEV2
        // Renseignements généraux
        aev2 = questionSetService.findByCode(QuestionCode.AEV2);
        if( aev2 == null ) {
            aev2 = new QuestionSet(QuestionCode.AEV2, false, null);
            JPA.em().persist(aev2);
        }
        form1.getQuestionSets().add(aev2);
        JPA.em().persist(form1);
    }
    private void createQuestionSetAEV10() {
        // == AEV10
        // Lieu de l'évènement
        aev10 = questionSetService.findByCode(QuestionCode.AEV10);
        if( aev10 == null ) {
            aev10 = new QuestionSet(QuestionCode.AEV10, false, null);
            JPA.em().persist(aev10);
        }
        form2.getQuestionSets().add(aev10);
        JPA.em().persist(form2);
    }
    private void createQuestionSetAEV11() {
        // == AEV11
        // Energie - Combustibles
        aev11 = questionSetService.findByCode(QuestionCode.AEV11);
        if( aev11 == null ) {
            aev11 = new QuestionSet(QuestionCode.AEV11, false, null);
            JPA.em().persist(aev11);
        }
        form2.getQuestionSets().add(aev11);
        JPA.em().persist(form2);
    }
    private void createQuestionSetAEV13() {
        // == AEV13
        // Listez les différents types de combustibles utilisés pour l'évènement
        aev13 = questionSetService.findByCode(QuestionCode.AEV13);
        if( aev13 == null ) {
            aev13 = new QuestionSet(QuestionCode.AEV13, true, aev11);
            JPA.em().persist(aev13);
        }
    }
    private void createQuestionSetAEV27() {
        // == AEV27
        // Energie - Electricité
        aev27 = questionSetService.findByCode(QuestionCode.AEV27);
        if( aev27 == null ) {
            aev27 = new QuestionSet(QuestionCode.AEV27, false, null);
            JPA.em().persist(aev27);
        }
        form2.getQuestionSets().add(aev27);
        JPA.em().persist(form2);
    }
    private void createQuestionSetAEV31() {
        // == AEV31
        // Ciimatisation & production de froid - Gaz réfrigérant
        aev31 = questionSetService.findByCode(QuestionCode.AEV31);
        if( aev31 == null ) {
            aev31 = new QuestionSet(QuestionCode.AEV31, false, null);
            JPA.em().persist(aev31);
        }
        form2.getQuestionSets().add(aev31);
        JPA.em().persist(form2);
    }
    private void createQuestionSetAEV37() {
        // == AEV37
        // Mobilité
        aev37 = questionSetService.findByCode(QuestionCode.AEV37);
        if( aev37 == null ) {
            aev37 = new QuestionSet(QuestionCode.AEV37, false, null);
            JPA.em().persist(aev37);
        }
        form3.getQuestionSets().add(aev37);
        JPA.em().persist(form3);
    }
    private void createQuestionSetAEV38() {
        // == AEV38
        // Déplacements des visiteurs (hors avion)
        aev38 = questionSetService.findByCode(QuestionCode.AEV38);
        if( aev38 == null ) {
            aev38 = new QuestionSet(QuestionCode.AEV38, false, null);
            JPA.em().persist(aev38);
        }
        form3.getQuestionSets().add(aev38);
        JPA.em().persist(form3);
    }
    private void createQuestionSetAEV42() {
        // == AEV42
        // Mode de déplacements des visiteurs:
        aev42 = questionSetService.findByCode(QuestionCode.AEV42);
        if( aev42 == null ) {
            aev42 = new QuestionSet(QuestionCode.AEV42, false, aev38);
            JPA.em().persist(aev42);
        }
    }
    private void createQuestionSetAEV57() {
        // == AEV57
        // Déplacements en avion des visiteurs
        aev57 = questionSetService.findByCode(QuestionCode.AEV57);
        if( aev57 == null ) {
            aev57 = new QuestionSet(QuestionCode.AEV57, false, null);
            JPA.em().persist(aev57);
        }
        form3.getQuestionSets().add(aev57);
        JPA.em().persist(form3);
    }
    private void createQuestionSetAEV58() {
        // == AEV58
        // Créez autant de catégories de voyage que nécessaire
        aev58 = questionSetService.findByCode(QuestionCode.AEV58);
        if( aev58 == null ) {
            aev58 = new QuestionSet(QuestionCode.AEV58, true, aev57);
            JPA.em().persist(aev58);
        }
    }
    private void createQuestionSetAEV64() {
        // == AEV64
        // Staff organisateur
        aev64 = questionSetService.findByCode(QuestionCode.AEV64);
        if( aev64 == null ) {
            aev64 = new QuestionSet(QuestionCode.AEV64, false, null);
            JPA.em().persist(aev64);
        }
        form3.getQuestionSets().add(aev64);
        JPA.em().persist(form3);
    }
    private void createQuestionSetAEV66() {
        // == AEV66
        // Déplacements par route
        aev66 = questionSetService.findByCode(QuestionCode.AEV66);
        if( aev66 == null ) {
            aev66 = new QuestionSet(QuestionCode.AEV66, false, aev64);
            JPA.em().persist(aev66);
        }
    }
    private void createQuestionSetAEV67() {
        // == AEV67
        // Listez les différents types de véhicules utilisés pour l'évènement
        aev67 = questionSetService.findByCode(QuestionCode.AEV67);
        if( aev67 == null ) {
            aev67 = new QuestionSet(QuestionCode.AEV67, true, aev66);
            JPA.em().persist(aev67);
        }
    }
    private void createQuestionSetAEV78() {
        // == AEV78
        // Déplacements en transports en commun
        aev78 = questionSetService.findByCode(QuestionCode.AEV78);
        if( aev78 == null ) {
            aev78 = new QuestionSet(QuestionCode.AEV78, false, aev64);
            JPA.em().persist(aev78);
        }
    }
    private void createQuestionSetAEV84() {
        // == AEV84
        // Déplacements en avion
        aev84 = questionSetService.findByCode(QuestionCode.AEV84);
        if( aev84 == null ) {
            aev84 = new QuestionSet(QuestionCode.AEV84, false, aev64);
            JPA.em().persist(aev84);
        }
    }
    private void createQuestionSetAEV85() {
        // == AEV85
        // Créez autant de catégories de voyage que nécessaire
        aev85 = questionSetService.findByCode(QuestionCode.AEV85);
        if( aev85 == null ) {
            aev85 = new QuestionSet(QuestionCode.AEV85, true, aev84);
            JPA.em().persist(aev85);
        }
    }
    private void createQuestionSetAEV91() {
        // == AEV91
        // Autres
        aev91 = questionSetService.findByCode(QuestionCode.AEV91);
        if( aev91 == null ) {
            aev91 = new QuestionSet(QuestionCode.AEV91, false, aev64);
            JPA.em().persist(aev91);
        }
    }
    private void createQuestionSetAEV94() {
        // == AEV94
        // Logistique
        aev94 = questionSetService.findByCode(QuestionCode.AEV94);
        if( aev94 == null ) {
            aev94 = new QuestionSet(QuestionCode.AEV94, false, null);
            JPA.em().persist(aev94);
        }
        form4.getQuestionSets().add(aev94);
        JPA.em().persist(form4);
    }
    private void createQuestionSetAEV96() {
        // == AEV96
        // Transport des produits et matériels
        aev96 = questionSetService.findByCode(QuestionCode.AEV96);
        if( aev96 == null ) {
            aev96 = new QuestionSet(QuestionCode.AEV96, false, null);
            JPA.em().persist(aev96);
        }
        form4.getQuestionSets().add(aev96);
        JPA.em().persist(form4);
    }
    private void createQuestionSetAEV97() {
        // == AEV97
        // Route
        aev97 = questionSetService.findByCode(QuestionCode.AEV97);
        if( aev97 == null ) {
            aev97 = new QuestionSet(QuestionCode.AEV97, false, aev96);
            JPA.em().persist(aev97);
        }
    }
    private void createQuestionSetAEV98() {
        // == AEV98
        // Créez autant de transports que nécessaire
        aev98 = questionSetService.findByCode(QuestionCode.AEV98);
        if( aev98 == null ) {
            aev98 = new QuestionSet(QuestionCode.AEV98, true, aev97);
            JPA.em().persist(aev98);
        }
    }
    private void createQuestionSetAEV106() {
        // == AEV106
        // Déchets
        aev106 = questionSetService.findByCode(QuestionCode.AEV106);
        if( aev106 == null ) {
            aev106 = new QuestionSet(QuestionCode.AEV106, false, null);
            JPA.em().persist(aev106);
        }
        form5.getQuestionSets().add(aev106);
        JPA.em().persist(form5);
    }
    private void createQuestionSetAEV109() {
        // == AEV109
        // Créez une rubrique pour chaque type de déchets que vous générez
        aev109 = questionSetService.findByCode(QuestionCode.AEV109);
        if( aev109 == null ) {
            aev109 = new QuestionSet(QuestionCode.AEV109, true, aev106);
            JPA.em().persist(aev109);
        }
    }
    private void createQuestionSetAEV112() {
        // == AEV112
        // Estimation via le volume des sacs/conteneurs
        aev112 = questionSetService.findByCode(QuestionCode.AEV112);
        if( aev112 == null ) {
            aev112 = new QuestionSet(QuestionCode.AEV112, false, aev109);
            JPA.em().persist(aev112);
        }
    }
    private void createQuestionSetAEV116() {
        // == AEV116
        // Estimation via les données de poids
        aev116 = questionSetService.findByCode(QuestionCode.AEV116);
        if( aev116 == null ) {
            aev116 = new QuestionSet(QuestionCode.AEV116, false, aev109);
            JPA.em().persist(aev116);
        }
    }
    private void createQuestionSetAEV124() {
        // == AEV124
        // Achats
        aev124 = questionSetService.findByCode(QuestionCode.AEV124);
        if( aev124 == null ) {
            aev124 = new QuestionSet(QuestionCode.AEV124, false, null);
            JPA.em().persist(aev124);
        }
        form6.getQuestionSets().add(aev124);
        JPA.em().persist(form6);
    }
    private void createQuestionSetAEV126() {
        // == AEV126
        // Achats de matériel (gobelets, documents, flyers, goodies,…)
        aev126 = questionSetService.findByCode(QuestionCode.AEV126);
        if( aev126 == null ) {
            aev126 = new QuestionSet(QuestionCode.AEV126, false, aev124);
            JPA.em().persist(aev126);
        }
    }
    private void createQuestionSetAEV127() {
        // == AEV127
        // Listez les différents types de matériaux dont sont composés les achats de matériel
        aev127 = questionSetService.findByCode(QuestionCode.AEV127);
        if( aev127 == null ) {
            aev127 = new QuestionSet(QuestionCode.AEV127, true, aev126);
            JPA.em().persist(aev127);
        }
    }
    private void createQuestionSetAEV130() {
        // == AEV130
        // Achats de textile (T-Shirts, casquettes,…)
        aev130 = questionSetService.findByCode(QuestionCode.AEV130);
        if( aev130 == null ) {
            aev130 = new QuestionSet(QuestionCode.AEV130, false, aev124);
            JPA.em().persist(aev130);
        }
    }
    private void createQuestionSetAEV132() {
        // == AEV132
        // Alimentation
        aev132 = questionSetService.findByCode(QuestionCode.AEV132);
        if( aev132 == null ) {
            aev132 = new QuestionSet(QuestionCode.AEV132, false, aev124);
            JPA.em().persist(aev132);
        }
    }
    private void createQuestionSetAEV133() {
        // == AEV133
        // Repas avec viande
        aev133 = questionSetService.findByCode(QuestionCode.AEV133);
        if( aev133 == null ) {
            aev133 = new QuestionSet(QuestionCode.AEV133, false, aev132);
            JPA.em().persist(aev133);
        }
    }
    private void createQuestionSetAEV136() {
        // == AEV136
        // Repas végétariens
        aev136 = questionSetService.findByCode(QuestionCode.AEV136);
        if( aev136 == null ) {
            aev136 = new QuestionSet(QuestionCode.AEV136, false, aev132);
            JPA.em().persist(aev136);
        }
    }


    // =========================================================================
    // QUESTIONS
    // =========================================================================

    private void createQuestionAEV3() {
        // == AEV3
        // Type d'événement

        aev3 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AEV3);
if (aev3 == null) {
    aev3 = new ValueSelectionQuestion(aev2, 0, QuestionCode.AEV3, CodeList.TYPEEVENEMENT);
    JPA.em().persist(aev3);
} else {
    if (!aev3.getQuestionSet().equals(aev2) && aev2.getQuestions().contains(aev3)) {
        aev2.getQuestions().remove(aev3);
        JPA.em().persist(aev2);
    }
    if (aev3.getQuestionSet().equals(aev2) && !aev2.getQuestions().contains(aev3)) {
        aev2.getQuestions().add(aev3);
        JPA.em().persist(aev2);
    }
    aev3.setOrderIndex(0);
    ((ValueSelectionQuestion)aev3).setCodeList(CodeList.TYPEEVENEMENT);
    JPA.em().persist(aev3);
}

    }
    private void createQuestionAEV4() {
        // == AEV4
        // Précisez:

        aev4 = (StringQuestion) questionService.findByCode(QuestionCode.AEV4);
if (aev4 == null) {
    aev4 = new StringQuestion(aev2, 0, QuestionCode.AEV4, null);
    JPA.em().persist(aev4);
} else {
    ((StringQuestion)aev4).setDefaultValue(null);
    if (!aev4.getQuestionSet().equals(aev2) && aev2.getQuestions().contains(aev4)) {
        aev2.getQuestions().remove(aev4);
        JPA.em().persist(aev2);
    }
    if (aev4.getQuestionSet().equals(aev2) && !aev2.getQuestions().contains(aev4)) {
        aev2.getQuestions().add(aev4);
        JPA.em().persist(aev2);
    }
    aev4.setOrderIndex(0);
    JPA.em().persist(aev4);
}

    }
    private void createQuestionAEV5() {
        // == AEV5
        // Date et heure de début de l'évènement

        aev5 = (DateTimeQuestion) questionService.findByCode(QuestionCode.AEV5);
if (aev5 == null) {
    aev5 = new DateTimeQuestion(aev2, 0, QuestionCode.AEV5);
    JPA.em().persist(aev5);


} else {
    if (!aev5.getQuestionSet().equals(aev2) && aev2.getQuestions().contains(aev5)) {
        aev2.getQuestions().remove(aev5);
        JPA.em().persist(aev2);
    }
    if (aev5.getQuestionSet().equals(aev2) && !aev2.getQuestions().contains(aev5)) {
        aev2.getQuestions().add(aev5);
        JPA.em().persist(aev2);
    }

}

    }
    private void createQuestionAEV6() {
        // == AEV6
        // Date et heure de fin de l'évènement

        aev6 = (DateTimeQuestion) questionService.findByCode(QuestionCode.AEV6);
if (aev6 == null) {
    aev6 = new DateTimeQuestion(aev2, 0, QuestionCode.AEV6);
    JPA.em().persist(aev6);


} else {
    if (!aev6.getQuestionSet().equals(aev2) && aev2.getQuestions().contains(aev6)) {
        aev2.getQuestions().remove(aev6);
        JPA.em().persist(aev2);
    }
    if (aev6.getQuestionSet().equals(aev2) && !aev2.getQuestions().contains(aev6)) {
        aev2.getQuestions().add(aev6);
        JPA.em().persist(aev2);
    }

}

    }
    private void createQuestionAEV7() {
        // == AEV7
        // Durée de l'évènement

        aev7 = (DateTimeQuestion) questionService.findByCode(QuestionCode.AEV7);
if (aev7 == null) {
    aev7 = new DateTimeQuestion(aev2, 0, QuestionCode.AEV7);
    JPA.em().persist(aev7);


} else {
    if (!aev7.getQuestionSet().equals(aev2) && aev2.getQuestions().contains(aev7)) {
        aev2.getQuestions().remove(aev7);
        JPA.em().persist(aev2);
    }
    if (aev7.getQuestionSet().equals(aev2) && !aev2.getQuestions().contains(aev7)) {
        aev2.getQuestions().add(aev7);
        JPA.em().persist(aev2);
    }

}

    }
    private void createQuestionAEV8() {
        // == AEV8
        // Indiquez la surface occupée par l'évènement

        

aev8 = (DoubleQuestion) questionService.findByCode(QuestionCode.AEV8);
if (aev8 == null) {
    aev8 = new DoubleQuestion( aev2, 0, QuestionCode.AEV8, areaUnits, areaUnits.getMainUnit() );
    JPA.em().persist(aev8);

    // cleanup the driver
    Driver aev8_driver = driverService.findByName("AEV8");
    if (aev8_driver != null) {
        driverService.remove(aev8_driver);
    }


} else {
    if (!aev8.getQuestionSet().equals(aev2) && aev2.getQuestions().contains(aev8)) {
        aev2.getQuestions().remove(aev8);
        JPA.em().persist(aev2);
    }
    if (aev8.getQuestionSet().equals(aev2) && !aev2.getQuestions().contains(aev8)) {
        aev2.getQuestions().add(aev8);
        JPA.em().persist(aev2);
    }
    ((NumericQuestion)aev8).setUnitCategory(areaUnits);
    aev8.setOrderIndex(0);
    ((NumericQuestion)aev8).setDefaultUnit(areaUnits.getMainUnit());

    // cleanup the driver
    Driver aev8_driver = driverService.findByName("AEV8");
    if (aev8_driver != null) {
        driverService.remove(aev8_driver);
    }

    ((NumericQuestion)aev8).setDriver(null);

    JPA.em().persist(aev8);
}



    }
    private void createQuestionAEV9() {
        // == AEV9
        // Pièces documentaires liées aux renseignements généraux

        aev9 = (DocumentQuestion) questionService.findByCode(QuestionCode.AEV9);
if (aev9 == null) {
    aev9 = new DocumentQuestion(aev2, 0, QuestionCode.AEV9);
    JPA.em().persist(aev9);
} else {
    if (!aev9.getQuestionSet().equals(aev2) && aev2.getQuestions().contains(aev9)) {
        aev2.getQuestions().remove(aev9);
        JPA.em().persist(aev2);
    }
    if (aev9.getQuestionSet().equals(aev2) && !aev2.getQuestions().contains(aev9)) {
        aev2.getQuestions().add(aev9);
        JPA.em().persist(aev2);
    }
    aev9.setOrderIndex(0);
    JPA.em().persist(aev9);
}

    }
    private void createQuestionAEV12() {
        // == AEV12
        // Pièces documentaires liées aux combustibles

        aev12 = (DocumentQuestion) questionService.findByCode(QuestionCode.AEV12);
if (aev12 == null) {
    aev12 = new DocumentQuestion(aev11, 0, QuestionCode.AEV12);
    JPA.em().persist(aev12);
} else {
    if (!aev12.getQuestionSet().equals(aev11) && aev11.getQuestions().contains(aev12)) {
        aev11.getQuestions().remove(aev12);
        JPA.em().persist(aev11);
    }
    if (aev12.getQuestionSet().equals(aev11) && !aev11.getQuestions().contains(aev12)) {
        aev11.getQuestions().add(aev12);
        JPA.em().persist(aev11);
    }
    aev12.setOrderIndex(0);
    JPA.em().persist(aev12);
}

    }
    private void createQuestionAEV14() {
        // == AEV14
        // Combustible que vous avez utilisé

        aev14 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AEV14);
if (aev14 == null) {
    aev14 = new ValueSelectionQuestion(aev13, 0, QuestionCode.AEV14, CodeList.COMBUSTIBLESIMPLEEVENEMENT);
    JPA.em().persist(aev14);
} else {
    if (!aev14.getQuestionSet().equals(aev13) && aev13.getQuestions().contains(aev14)) {
        aev13.getQuestions().remove(aev14);
        JPA.em().persist(aev13);
    }
    if (aev14.getQuestionSet().equals(aev13) && !aev13.getQuestions().contains(aev14)) {
        aev13.getQuestions().add(aev14);
        JPA.em().persist(aev13);
    }
    aev14.setOrderIndex(0);
    ((ValueSelectionQuestion)aev14).setCodeList(CodeList.COMBUSTIBLESIMPLEEVENEMENT);
    JPA.em().persist(aev14);
}

    }
    private void createQuestionAEV15() {
        // == AEV15
        // Type de données disponibles

        aev15 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AEV15);
if (aev15 == null) {
    aev15 = new ValueSelectionQuestion(aev13, 0, QuestionCode.AEV15, CodeList.TYPEENERGIEEVENEMENT);
    JPA.em().persist(aev15);
} else {
    if (!aev15.getQuestionSet().equals(aev13) && aev13.getQuestions().contains(aev15)) {
        aev13.getQuestions().remove(aev15);
        JPA.em().persist(aev13);
    }
    if (aev15.getQuestionSet().equals(aev13) && !aev13.getQuestions().contains(aev15)) {
        aev13.getQuestions().add(aev15);
        JPA.em().persist(aev13);
    }
    aev15.setOrderIndex(0);
    ((ValueSelectionQuestion)aev15).setCodeList(CodeList.TYPEENERGIEEVENEMENT);
    JPA.em().persist(aev15);
}

    }
    private void createQuestionAEV16() {
        // == AEV16
        // Consommation totale pour l'évènement

        
aev16 = (DoubleQuestion) questionService.findByCode(QuestionCode.AEV16);
if (aev16 == null) {
    aev16 = new DoubleQuestion( aev13, 0, QuestionCode.AEV16, volumeUnits, getUnitBySymbol("l") );
    JPA.em().persist(aev16);

    // cleanup the driver
    Driver aev16_driver = driverService.findByName("AEV16");
    if (aev16_driver != null) {
        driverService.remove(aev16_driver);
    }

} else {
    if (!aev16.getQuestionSet().equals(aev13) && aev13.getQuestions().contains(aev16)) {
        aev13.getQuestions().remove(aev16);
        JPA.em().persist(aev13);
    }
    if (aev16.getQuestionSet().equals(aev13) && !aev13.getQuestions().contains(aev16)) {
        aev13.getQuestions().add(aev16);
        JPA.em().persist(aev13);
    }
    ((NumericQuestion)aev16).setUnitCategory(volumeUnits);
    aev16.setOrderIndex(0);
    ((NumericQuestion)aev16).setDefaultUnit(getUnitBySymbol("l"));

    // cleanup the driver
    Driver aev16_driver = driverService.findByName("AEV16");
    if (aev16_driver != null) {
        driverService.remove(aev16_driver);
    }

    ((NumericQuestion)aev16).setDriver(null);

    JPA.em().persist(aev16);
}



    }
    private void createQuestionAEV17() {
        // == AEV17
        // Consommation totale pour l'évènement

        
aev17 = (DoubleQuestion) questionService.findByCode(QuestionCode.AEV17);
if (aev17 == null) {
    aev17 = new DoubleQuestion( aev13, 0, QuestionCode.AEV17, massUnits, getUnitBySymbol("kg") );
    JPA.em().persist(aev17);

    // cleanup the driver
    Driver aev17_driver = driverService.findByName("AEV17");
    if (aev17_driver != null) {
        driverService.remove(aev17_driver);
    }

} else {
    if (!aev17.getQuestionSet().equals(aev13) && aev13.getQuestions().contains(aev17)) {
        aev13.getQuestions().remove(aev17);
        JPA.em().persist(aev13);
    }
    if (aev17.getQuestionSet().equals(aev13) && !aev13.getQuestions().contains(aev17)) {
        aev13.getQuestions().add(aev17);
        JPA.em().persist(aev13);
    }
    ((NumericQuestion)aev17).setUnitCategory(massUnits);
    aev17.setOrderIndex(0);
    ((NumericQuestion)aev17).setDefaultUnit(getUnitBySymbol("kg"));


    // cleanup the driver
    Driver aev17_driver = driverService.findByName("AEV17");
    if (aev17_driver != null) {
        driverService.remove(aev17_driver);
    }

    ((NumericQuestion)aev17).setDriver(null);

    JPA.em().persist(aev17);
}



    }
    private void createQuestionAEV18() {
        // == AEV18
        // Coût total pour l'évènement

        
aev18 = (DoubleQuestion) questionService.findByCode(QuestionCode.AEV18);
if (aev18 == null) {
    aev18 = new DoubleQuestion( aev13, 0, QuestionCode.AEV18, moneyUnits, getUnitBySymbol("EUR") );
    JPA.em().persist(aev18);

    // cleanup the driver
    Driver aev18_driver = driverService.findByName("AEV18");
    if (aev18_driver != null) {
        driverService.remove(aev18_driver);
    }

} else {
    if (!aev18.getQuestionSet().equals(aev13) && aev13.getQuestions().contains(aev18)) {
        aev13.getQuestions().remove(aev18);
        JPA.em().persist(aev13);
    }
    if (aev18.getQuestionSet().equals(aev13) && !aev13.getQuestions().contains(aev18)) {
        aev13.getQuestions().add(aev18);
        JPA.em().persist(aev13);
    }
    ((NumericQuestion)aev18).setUnitCategory(moneyUnits);
    aev18.setOrderIndex(0);
    ((NumericQuestion)aev18).setDefaultUnit(getUnitBySymbol("EUR"));

    // cleanup the driver
    Driver aev18_driver = driverService.findByName("AEV18");
    if (aev18_driver != null) {
        driverService.remove(aev18_driver);
    }

    ((NumericQuestion)aev18).setDriver(null);

    JPA.em().persist(aev18);
}



    }
    private void createQuestionAEV19() {
        // == AEV19
        // Prix unitaire du mazout (au litre)

        
aev19 = (DoubleQuestion) questionService.findByCode(QuestionCode.AEV19);
if (aev19 == null) {
    aev19 = new DoubleQuestion( aev13, 0, QuestionCode.AEV19, moneyUnits, getUnitBySymbol("EUR") );
    JPA.em().persist(aev19);

    // cleanup the driver
    Driver aev19_driver = driverService.findByName("AEV19");
    if (aev19_driver != null) {
        driverService.remove(aev19_driver);
    }

    // recreate with good value
    aev19_driver = new Driver("AEV19");
    driverService.saveOrUpdate(aev19_driver);
    Period p2000 = periodService.findByCode(PeriodCode.P2000);
    DriverValue dv = new DriverValue(aev19_driver, p2000, Double.valueOf(0.8383));
    aev19_driver.getDriverValueList().add(dv);;
    driverService.saveOrUpdate(aev19_driver);
    JPA.em().persist(dv);

    ((NumericQuestion)aev19).setDriver(aev19_driver);
    JPA.em().persist(aev19);
} else {
    if (!aev19.getQuestionSet().equals(aev13) && aev13.getQuestions().contains(aev19)) {
        aev13.getQuestions().remove(aev19);
        JPA.em().persist(aev13);
    }
    if (aev19.getQuestionSet().equals(aev13) && !aev13.getQuestions().contains(aev19)) {
        aev13.getQuestions().add(aev19);
        JPA.em().persist(aev13);
    }
    ((NumericQuestion)aev19).setUnitCategory(moneyUnits);
    aev19.setOrderIndex(0);
    ((NumericQuestion)aev19).setDefaultUnit(getUnitBySymbol("EUR"));

    // cleanup the driver
    Driver aev19_driver = driverService.findByName("AEV19");
    if (aev19_driver != null) {
        driverService.remove(aev19_driver);
    }

    // recreate with good value
    aev19_driver = new Driver("AEV19");
    driverService.saveOrUpdate(aev19_driver);
    Period p2000 = periodService.findByCode(PeriodCode.P2000);
    DriverValue dv = new DriverValue(aev19_driver, p2000, Double.valueOf(0.8383));
    aev19_driver.getDriverValueList().add(dv);;
    driverService.saveOrUpdate(aev19_driver);
    JPA.em().persist(dv);

    ((NumericQuestion)aev19).setDriver(aev19_driver);

    JPA.em().persist(aev19);
}



    }
    private void createQuestionAEV20() {
        // == AEV20
        // Prix unitaire du gaz naturel (au m³)

        
aev20 = (DoubleQuestion) questionService.findByCode(QuestionCode.AEV20);
if (aev20 == null) {
    aev20 = new DoubleQuestion( aev13, 0, QuestionCode.AEV20, moneyUnits, getUnitBySymbol("EUR") );
    JPA.em().persist(aev20);

    // cleanup the driver
    Driver aev20_driver = driverService.findByName("AEV20");
    if (aev20_driver != null) {
        driverService.remove(aev20_driver);
    }

    // recreate with good value
    aev20_driver = new Driver("AEV20");
    driverService.saveOrUpdate(aev20_driver);
    Period p2000 = periodService.findByCode(PeriodCode.P2000);
    DriverValue dv = new DriverValue(aev20_driver, p2000, Double.valueOf(0.625));
    aev20_driver.getDriverValueList().add(dv);;
    driverService.saveOrUpdate(aev20_driver);
    JPA.em().persist(dv);

    ((NumericQuestion)aev20).setDriver(aev20_driver);
    JPA.em().persist(aev20);
} else {
    if (!aev20.getQuestionSet().equals(aev13) && aev13.getQuestions().contains(aev20)) {
        aev13.getQuestions().remove(aev20);
        JPA.em().persist(aev13);
    }
    if (aev20.getQuestionSet().equals(aev13) && !aev13.getQuestions().contains(aev20)) {
        aev13.getQuestions().add(aev20);
        JPA.em().persist(aev13);
    }
    ((NumericQuestion)aev20).setUnitCategory(moneyUnits);
    aev20.setOrderIndex(0);
    ((NumericQuestion)aev20).setDefaultUnit(getUnitBySymbol("EUR"));

    // cleanup the driver
    Driver aev20_driver = driverService.findByName("AEV20");
    if (aev20_driver != null) {
        driverService.remove(aev20_driver);
    }

    // recreate with good value
    aev20_driver = new Driver("AEV20");
    driverService.saveOrUpdate(aev20_driver);
    Period p2000 = periodService.findByCode(PeriodCode.P2000);
    DriverValue dv = new DriverValue(aev20_driver, p2000, Double.valueOf(0.625));
    aev20_driver.getDriverValueList().add(dv);;
    driverService.saveOrUpdate(aev20_driver);
    JPA.em().persist(dv);

    ((NumericQuestion)aev20).setDriver(aev20_driver);

    JPA.em().persist(aev20);
}



    }
    private void createQuestionAEV21() {
        // == AEV21
        // Prix unitaire du bois (bûche ou pellets) (au kilo)

        
aev21 = (DoubleQuestion) questionService.findByCode(QuestionCode.AEV21);
if (aev21 == null) {
    aev21 = new DoubleQuestion( aev13, 0, QuestionCode.AEV21, moneyUnits, getUnitBySymbol("EUR") );
    JPA.em().persist(aev21);

    // cleanup the driver
    Driver aev21_driver = driverService.findByName("AEV21");
    if (aev21_driver != null) {
        driverService.remove(aev21_driver);
    }

    // recreate with good value
    aev21_driver = new Driver("AEV21");
    driverService.saveOrUpdate(aev21_driver);
    Period p2000 = periodService.findByCode(PeriodCode.P2000);
    DriverValue dv = new DriverValue(aev21_driver, p2000, Double.valueOf(0.27));
    aev21_driver.getDriverValueList().add(dv);;
    driverService.saveOrUpdate(aev21_driver);
    JPA.em().persist(dv);

    ((NumericQuestion)aev21).setDriver(aev21_driver);
    JPA.em().persist(aev21);
} else {
    if (!aev21.getQuestionSet().equals(aev13) && aev13.getQuestions().contains(aev21)) {
        aev13.getQuestions().remove(aev21);
        JPA.em().persist(aev13);
    }
    if (aev21.getQuestionSet().equals(aev13) && !aev13.getQuestions().contains(aev21)) {
        aev13.getQuestions().add(aev21);
        JPA.em().persist(aev13);
    }
    ((NumericQuestion)aev21).setUnitCategory(moneyUnits);
    aev21.setOrderIndex(0);
    ((NumericQuestion)aev21).setDefaultUnit(getUnitBySymbol("EUR"));

    // cleanup the driver
    Driver aev21_driver = driverService.findByName("AEV21");
    if (aev21_driver != null) {
        driverService.remove(aev21_driver);
    }

    // recreate with good value
    aev21_driver = new Driver("AEV21");
    driverService.saveOrUpdate(aev21_driver);
    Period p2000 = periodService.findByCode(PeriodCode.P2000);
    DriverValue dv = new DriverValue(aev21_driver, p2000, Double.valueOf(0.27));
    aev21_driver.getDriverValueList().add(dv);;
    driverService.saveOrUpdate(aev21_driver);
    JPA.em().persist(dv);

    ((NumericQuestion)aev21).setDriver(aev21_driver);

    JPA.em().persist(aev21);
}



    }
    private void createQuestionAEV22() {
        // == AEV22
        // Prix unitaire du charbon (au kilo)

        
aev22 = (DoubleQuestion) questionService.findByCode(QuestionCode.AEV22);
if (aev22 == null) {
    aev22 = new DoubleQuestion( aev13, 0, QuestionCode.AEV22, moneyUnits, getUnitBySymbol("EUR") );
    JPA.em().persist(aev22);

    // cleanup the driver
    Driver aev22_driver = driverService.findByName("AEV22");
    if (aev22_driver != null) {
        driverService.remove(aev22_driver);
    }

    // recreate with good value
    aev22_driver = new Driver("AEV22");
    driverService.saveOrUpdate(aev22_driver);
    Period p2000 = periodService.findByCode(PeriodCode.P2000);
    DriverValue dv = new DriverValue(aev22_driver, p2000, Double.valueOf(0.35));
    aev22_driver.getDriverValueList().add(dv);;
    driverService.saveOrUpdate(aev22_driver);
    JPA.em().persist(dv);

    ((NumericQuestion)aev22).setDriver(aev22_driver);
    JPA.em().persist(aev22);
} else {
    if (!aev22.getQuestionSet().equals(aev13) && aev13.getQuestions().contains(aev22)) {
        aev13.getQuestions().remove(aev22);
        JPA.em().persist(aev13);
    }
    if (aev22.getQuestionSet().equals(aev13) && !aev13.getQuestions().contains(aev22)) {
        aev13.getQuestions().add(aev22);
        JPA.em().persist(aev13);
    }
    ((NumericQuestion)aev22).setUnitCategory(moneyUnits);
    aev22.setOrderIndex(0);
    ((NumericQuestion)aev22).setDefaultUnit(getUnitBySymbol("EUR"));

    // cleanup the driver
    Driver aev22_driver = driverService.findByName("AEV22");
    if (aev22_driver != null) {
        driverService.remove(aev22_driver);
    }

    // recreate with good value
    aev22_driver = new Driver("AEV22");
    driverService.saveOrUpdate(aev22_driver);
    Period p2000 = periodService.findByCode(PeriodCode.P2000);
    DriverValue dv = new DriverValue(aev22_driver, p2000, Double.valueOf(0.35));
    aev22_driver.getDriverValueList().add(dv);;
    driverService.saveOrUpdate(aev22_driver);
    JPA.em().persist(dv);

    ((NumericQuestion)aev22).setDriver(aev22_driver);

    JPA.em().persist(aev22);
}



    }
    private void createQuestionAEV23() {
        // == AEV23
        // Votre consommation estimée

        
aev23 = (DoubleQuestion) questionService.findByCode(QuestionCode.AEV23);
if (aev23 == null) {
    aev23 = new DoubleQuestion( aev13, 0, QuestionCode.AEV23, volumeUnits, getUnitBySymbol("l") );
    JPA.em().persist(aev23);

    // cleanup the driver
    Driver aev23_driver = driverService.findByName("AEV23");
    if (aev23_driver != null) {
        driverService.remove(aev23_driver);
    }

} else {
    if (!aev23.getQuestionSet().equals(aev13) && aev13.getQuestions().contains(aev23)) {
        aev13.getQuestions().remove(aev23);
        JPA.em().persist(aev13);
    }
    if (aev23.getQuestionSet().equals(aev13) && !aev13.getQuestions().contains(aev23)) {
        aev13.getQuestions().add(aev23);
        JPA.em().persist(aev13);
    }
    ((NumericQuestion)aev23).setUnitCategory(volumeUnits);
    aev23.setOrderIndex(0);
    ((NumericQuestion)aev23).setDefaultUnit(getUnitBySymbol("l"));

    // cleanup the driver
    Driver aev23_driver = driverService.findByName("AEV23");
    if (aev23_driver != null) {
        driverService.remove(aev23_driver);
    }

    ((NumericQuestion)aev23).setDriver(null);

    JPA.em().persist(aev23);
}



    }
    private void createQuestionAEV24() {
        // == AEV24
        // Votre consommation estimée

        
aev24 = (DoubleQuestion) questionService.findByCode(QuestionCode.AEV24);
if (aev24 == null) {
    aev24 = new DoubleQuestion( aev13, 0, QuestionCode.AEV24, volumeUnits, getUnitBySymbol("l") );
    JPA.em().persist(aev24);

    // cleanup the driver
    Driver aev24_driver = driverService.findByName("AEV24");
    if (aev24_driver != null) {
        driverService.remove(aev24_driver);
    }

} else {
    if (!aev24.getQuestionSet().equals(aev13) && aev13.getQuestions().contains(aev24)) {
        aev13.getQuestions().remove(aev24);
        JPA.em().persist(aev13);
    }
    if (aev24.getQuestionSet().equals(aev13) && !aev13.getQuestions().contains(aev24)) {
        aev13.getQuestions().add(aev24);
        JPA.em().persist(aev13);
    }
    ((NumericQuestion)aev24).setUnitCategory(volumeUnits);
    aev24.setOrderIndex(0);
    ((NumericQuestion)aev24).setDefaultUnit(getUnitBySymbol("l"));

    // cleanup the driver
    Driver aev24_driver = driverService.findByName("AEV24");
    if (aev24_driver != null) {
        driverService.remove(aev24_driver);
    }

    ((NumericQuestion)aev24).setDriver(null);

    JPA.em().persist(aev24);
}



    }
    private void createQuestionAEV25() {
        // == AEV25
        // Votre consommation estimée

        
aev25 = (DoubleQuestion) questionService.findByCode(QuestionCode.AEV25);
if (aev25 == null) {
    aev25 = new DoubleQuestion( aev13, 0, QuestionCode.AEV25, massUnits, getUnitBySymbol("kg") );
    JPA.em().persist(aev25);

    // cleanup the driver
    Driver aev25_driver = driverService.findByName("AEV25");
    if (aev25_driver != null) {
        driverService.remove(aev25_driver);
    }

} else {
    if (!aev25.getQuestionSet().equals(aev13) && aev13.getQuestions().contains(aev25)) {
        aev13.getQuestions().remove(aev25);
        JPA.em().persist(aev13);
    }
    if (aev25.getQuestionSet().equals(aev13) && !aev13.getQuestions().contains(aev25)) {
        aev13.getQuestions().add(aev25);
        JPA.em().persist(aev13);
    }
    ((NumericQuestion)aev25).setUnitCategory(massUnits);
    aev25.setOrderIndex(0);
    ((NumericQuestion)aev25).setDefaultUnit(getUnitBySymbol("kg"));


    // cleanup the driver
    Driver aev25_driver = driverService.findByName("AEV25");
    if (aev25_driver != null) {
        driverService.remove(aev25_driver);
    }

    ((NumericQuestion)aev25).setDriver(null);

    JPA.em().persist(aev25);
}



    }
    private void createQuestionAEV26() {
        // == AEV26
        // Votre consommation estimée

        
aev26 = (DoubleQuestion) questionService.findByCode(QuestionCode.AEV26);
if (aev26 == null) {
    aev26 = new DoubleQuestion( aev13, 0, QuestionCode.AEV26, massUnits, getUnitBySymbol("kg") );
    JPA.em().persist(aev26);

    // cleanup the driver
    Driver aev26_driver = driverService.findByName("AEV26");
    if (aev26_driver != null) {
        driverService.remove(aev26_driver);
    }

} else {
    if (!aev26.getQuestionSet().equals(aev13) && aev13.getQuestions().contains(aev26)) {
        aev13.getQuestions().remove(aev26);
        JPA.em().persist(aev13);
    }
    if (aev26.getQuestionSet().equals(aev13) && !aev13.getQuestions().contains(aev26)) {
        aev13.getQuestions().add(aev26);
        JPA.em().persist(aev13);
    }
    ((NumericQuestion)aev26).setUnitCategory(massUnits);
    aev26.setOrderIndex(0);
    ((NumericQuestion)aev26).setDefaultUnit(getUnitBySymbol("kg"));


    // cleanup the driver
    Driver aev26_driver = driverService.findByName("AEV26");
    if (aev26_driver != null) {
        driverService.remove(aev26_driver);
    }

    ((NumericQuestion)aev26).setDriver(null);

    JPA.em().persist(aev26);
}



    }
    private void createQuestionAEV28() {
        // == AEV28
        // Pièces documentaires liées à l'électricité

        aev28 = (DocumentQuestion) questionService.findByCode(QuestionCode.AEV28);
if (aev28 == null) {
    aev28 = new DocumentQuestion(aev27, 0, QuestionCode.AEV28);
    JPA.em().persist(aev28);
} else {
    if (!aev28.getQuestionSet().equals(aev27) && aev27.getQuestions().contains(aev28)) {
        aev27.getQuestions().remove(aev28);
        JPA.em().persist(aev27);
    }
    if (aev28.getQuestionSet().equals(aev27) && !aev27.getQuestions().contains(aev28)) {
        aev27.getQuestions().add(aev28);
        JPA.em().persist(aev27);
    }
    aev28.setOrderIndex(0);
    JPA.em().persist(aev28);
}

    }
    private void createQuestionAEV29() {
        // == AEV29
        // Sélectionnez le type d'électricité utilisée

        aev29 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AEV29);
if (aev29 == null) {
    aev29 = new ValueSelectionQuestion(aev27, 0, QuestionCode.AEV29, CodeList.TYPEELECTRICITE);
    JPA.em().persist(aev29);
} else {
    if (!aev29.getQuestionSet().equals(aev27) && aev27.getQuestions().contains(aev29)) {
        aev27.getQuestions().remove(aev29);
        JPA.em().persist(aev27);
    }
    if (aev29.getQuestionSet().equals(aev27) && !aev27.getQuestions().contains(aev29)) {
        aev27.getQuestions().add(aev29);
        JPA.em().persist(aev27);
    }
    aev29.setOrderIndex(0);
    ((ValueSelectionQuestion)aev29).setCodeList(CodeList.TYPEELECTRICITE);
    JPA.em().persist(aev29);
}

    }
    private void createQuestionAEV30() {
        // == AEV30
        // Consommation totale d'électricité (avant, pendant et après évènement)

        
aev30 = (DoubleQuestion) questionService.findByCode(QuestionCode.AEV30);
if (aev30 == null) {
    aev30 = new DoubleQuestion( aev27, 0, QuestionCode.AEV30, energyUnits, getUnitBySymbol("kWh") );
    JPA.em().persist(aev30);

    // cleanup the driver
    Driver aev30_driver = driverService.findByName("AEV30");
    if (aev30_driver != null) {
        driverService.remove(aev30_driver);
    }


} else {
    if (!aev30.getQuestionSet().equals(aev27) && aev27.getQuestions().contains(aev30)) {
        aev27.getQuestions().remove(aev30);
        JPA.em().persist(aev27);
    }
    if (aev30.getQuestionSet().equals(aev27) && !aev27.getQuestions().contains(aev30)) {
        aev27.getQuestions().add(aev30);
        JPA.em().persist(aev27);
    }
    ((NumericQuestion)aev30).setUnitCategory(energyUnits);
    aev30.setOrderIndex(0);
    ((NumericQuestion)aev30).setDefaultUnit(getUnitBySymbol("kWh"));

    // cleanup the driver
    Driver aev30_driver = driverService.findByName("AEV30");
    if (aev30_driver != null) {
        driverService.remove(aev30_driver);
    }

    ((NumericQuestion)aev30).setDriver(null);

    JPA.em().persist(aev30);
}



    }
    private void createQuestionAEV32() {
        // == AEV32
        // Pièces documentaires liées aux gaz réfriégérants

        aev32 = (DocumentQuestion) questionService.findByCode(QuestionCode.AEV32);
if (aev32 == null) {
    aev32 = new DocumentQuestion(aev31, 0, QuestionCode.AEV32);
    JPA.em().persist(aev32);
} else {
    if (!aev32.getQuestionSet().equals(aev31) && aev31.getQuestions().contains(aev32)) {
        aev31.getQuestions().remove(aev32);
        JPA.em().persist(aev31);
    }
    if (aev32.getQuestionSet().equals(aev31) && !aev31.getQuestions().contains(aev32)) {
        aev31.getQuestions().add(aev32);
        JPA.em().persist(aev31);
    }
    aev32.setOrderIndex(0);
    JPA.em().persist(aev32);
}

    }
    private void createQuestionAEV33() {
        // == AEV33
        // Sélectionnez le type de données dont vous disposez sur les installations de climatisation et de production de froid

        aev33 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AEV33);
if (aev33 == null) {
    aev33 = new ValueSelectionQuestion(aev31, 0, QuestionCode.AEV33, CodeList.TYPEFROIDEVENEMENT);
    JPA.em().persist(aev33);
} else {
    if (!aev33.getQuestionSet().equals(aev31) && aev31.getQuestions().contains(aev33)) {
        aev31.getQuestions().remove(aev33);
        JPA.em().persist(aev31);
    }
    if (aev33.getQuestionSet().equals(aev31) && !aev31.getQuestions().contains(aev33)) {
        aev31.getQuestions().add(aev33);
        JPA.em().persist(aev31);
    }
    aev33.setOrderIndex(0);
    ((ValueSelectionQuestion)aev33).setCodeList(CodeList.TYPEFROIDEVENEMENT);
    JPA.em().persist(aev33);
}

    }
    private void createQuestionAEV34() {
        // == AEV34
        // Sélectionnez le type de gaz réfrigérant rechargé

        aev34 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AEV34);
if (aev34 == null) {
    aev34 = new ValueSelectionQuestion(aev31, 0, QuestionCode.AEV34, CodeList.FRIGO_PETITEMETTEUR);
    JPA.em().persist(aev34);
} else {
    if (!aev34.getQuestionSet().equals(aev31) && aev31.getQuestions().contains(aev34)) {
        aev31.getQuestions().remove(aev34);
        JPA.em().persist(aev31);
    }
    if (aev34.getQuestionSet().equals(aev31) && !aev31.getQuestions().contains(aev34)) {
        aev31.getQuestions().add(aev34);
        JPA.em().persist(aev31);
    }
    aev34.setOrderIndex(0);
    ((ValueSelectionQuestion)aev34).setCodeList(CodeList.FRIGO_PETITEMETTEUR);
    JPA.em().persist(aev34);
}

    }
    private void createQuestionAEV35() {
        // == AEV35
        // Sélectionnez la quantité de recharge utilisée pour l'évènement

        
aev35 = (DoubleQuestion) questionService.findByCode(QuestionCode.AEV35);
if (aev35 == null) {
    aev35 = new DoubleQuestion( aev31, 0, QuestionCode.AEV35, massUnits, getUnitBySymbol("kg") );
    JPA.em().persist(aev35);

    // cleanup the driver
    Driver aev35_driver = driverService.findByName("AEV35");
    if (aev35_driver != null) {
        driverService.remove(aev35_driver);
    }

} else {
    if (!aev35.getQuestionSet().equals(aev31) && aev31.getQuestions().contains(aev35)) {
        aev31.getQuestions().remove(aev35);
        JPA.em().persist(aev31);
    }
    if (aev35.getQuestionSet().equals(aev31) && !aev31.getQuestions().contains(aev35)) {
        aev31.getQuestions().add(aev35);
        JPA.em().persist(aev31);
    }
    ((NumericQuestion)aev35).setUnitCategory(massUnits);
    aev35.setOrderIndex(0);
    ((NumericQuestion)aev35).setDefaultUnit(getUnitBySymbol("kg"));


    // cleanup the driver
    Driver aev35_driver = driverService.findByName("AEV35");
    if (aev35_driver != null) {
        driverService.remove(aev35_driver);
    }

    ((NumericQuestion)aev35).setDriver(null);

    JPA.em().persist(aev35);
}



    }
    private void createQuestionAEV36() {
        // == AEV36
        // Indiquez la puissance totale des installations frigorifiques

        
aev36 = (DoubleQuestion) questionService.findByCode(QuestionCode.AEV36);
if (aev36 == null) {
    aev36 = new DoubleQuestion( aev31, 0, QuestionCode.AEV36, powerUnits, getUnitBySymbol("kW") );
    JPA.em().persist(aev36);

    // cleanup the driver
    Driver aev36_driver = driverService.findByName("AEV36");
    if (aev36_driver != null) {
        driverService.remove(aev36_driver);
    }


} else {
    if (!aev36.getQuestionSet().equals(aev31) && aev31.getQuestions().contains(aev36)) {
        aev31.getQuestions().remove(aev36);
        JPA.em().persist(aev31);
    }
    if (aev36.getQuestionSet().equals(aev31) && !aev31.getQuestions().contains(aev36)) {
        aev31.getQuestions().add(aev36);
        JPA.em().persist(aev31);
    }
    ((NumericQuestion)aev36).setUnitCategory(powerUnits);
    aev36.setOrderIndex(0);
    ((NumericQuestion)aev36).setDefaultUnit(getUnitBySymbol("kW"));

    // cleanup the driver
    Driver aev36_driver = driverService.findByName("AEV36");
    if (aev36_driver != null) {
        driverService.remove(aev36_driver);
    }

    ((NumericQuestion)aev36).setDriver(null);

    JPA.em().persist(aev36);
}



    }
    private void createQuestionAEV39() {
        // == AEV39
        // Pièces documentaires liées à la mobilité des visiteurs

        aev39 = (DocumentQuestion) questionService.findByCode(QuestionCode.AEV39);
if (aev39 == null) {
    aev39 = new DocumentQuestion(aev38, 0, QuestionCode.AEV39);
    JPA.em().persist(aev39);
} else {
    if (!aev39.getQuestionSet().equals(aev38) && aev38.getQuestions().contains(aev39)) {
        aev38.getQuestions().remove(aev39);
        JPA.em().persist(aev38);
    }
    if (aev39.getQuestionSet().equals(aev38) && !aev38.getQuestions().contains(aev39)) {
        aev38.getQuestions().add(aev39);
        JPA.em().persist(aev38);
    }
    aev39.setOrderIndex(0);
    JPA.em().persist(aev39);
}

    }
    private void createQuestionAEV40() {
        // == AEV40
        // Nombre total de visiteurs de l'événement qui sont venus en voiture, autocar ou en transports en commun (train, bus, métro,tram, TGV)

        aev40 = (IntegerQuestion) questionService.findByCode(QuestionCode.AEV40);
if (aev40 == null) {
    aev40 = new IntegerQuestion(aev38, 0, QuestionCode.AEV40, null);
    JPA.em().persist(aev40);

    // cleanup the driver
    Driver aev40_driver = driverService.findByName("AEV40");
    if (aev40_driver != null) {
        driverService.remove(aev40_driver);
    }

} else {
    if (!aev40.getQuestionSet().equals(aev38) && aev38.getQuestions().contains(aev40)) {
        aev38.getQuestions().remove(aev40);
        JPA.em().persist(aev38);
    }
    if (aev40.getQuestionSet().equals(aev38) && !aev38.getQuestions().contains(aev40)) {
        aev38.getQuestions().add(aev40);
        JPA.em().persist(aev38);
    }
    aev40.setOrderIndex(0);
    ((NumericQuestion)aev40).setUnitCategory(null);

    // cleanup the driver
    Driver aev40_driver = driverService.findByName("AEV40");
    if (aev40_driver != null) {
        driverService.remove(aev40_driver);
    }

    ((NumericQuestion)aev40).setDriver(null);

    JPA.em().persist(aev40);
}

    }
    private void createQuestionAEV41() {
        // == AEV41
        // Estimez la distance moyenne parcourue par chaque visiteur (aller + retour)

        
aev41 = (DoubleQuestion) questionService.findByCode(QuestionCode.AEV41);
if (aev41 == null) {
    aev41 = new DoubleQuestion( aev38, 0, QuestionCode.AEV41, lengthUnits, getUnitBySymbol("km") );
    JPA.em().persist(aev41);

    // cleanup the driver
    Driver aev41_driver = driverService.findByName("AEV41");
    if (aev41_driver != null) {
        driverService.remove(aev41_driver);
    }

} else {
    if (!aev41.getQuestionSet().equals(aev38) && aev38.getQuestions().contains(aev41)) {
        aev38.getQuestions().remove(aev41);
        JPA.em().persist(aev38);
    }
    if (aev41.getQuestionSet().equals(aev38) && !aev38.getQuestions().contains(aev41)) {
        aev38.getQuestions().add(aev41);
        JPA.em().persist(aev38);
    }
    ((NumericQuestion)aev41).setUnitCategory(lengthUnits);
    aev41.setOrderIndex(0);
    ((NumericQuestion)aev41).setDefaultUnit(getUnitBySymbol("km"));

    // cleanup the driver
    Driver aev41_driver = driverService.findByName("AEV41");
    if (aev41_driver != null) {
        driverService.remove(aev41_driver);
    }

    ((NumericQuestion)aev41).setDriver(null);

    JPA.em().persist(aev41);
}



    }
    private void createQuestionAEV43() {
        // == AEV43
        // Part des visiteurs qui ont utilisé leur voiture

        aev43 = (PercentageQuestion) questionService.findByCode(QuestionCode.AEV43);
if (aev43 == null) {
    aev43 = new PercentageQuestion(aev42, 0, QuestionCode.AEV43);
    JPA.em().persist(aev43);

    // cleanup the driver
    Driver aev43_driver = driverService.findByName("AEV43");
    if (aev43_driver != null) {
        driverService.remove(aev43_driver);
    }

} else {
    if (!aev43.getQuestionSet().equals(aev42) && aev42.getQuestions().contains(aev43)) {
        aev42.getQuestions().remove(aev43);
        JPA.em().persist(aev42);
    }
    if (aev43.getQuestionSet().equals(aev42) && !aev42.getQuestions().contains(aev43)) {
        aev42.getQuestions().add(aev43);
        JPA.em().persist(aev42);
    }
    aev43.setOrderIndex(0);

    // cleanup the driver
    Driver aev43_driver = driverService.findByName("AEV43");
    if (aev43_driver != null) {
        driverService.remove(aev43_driver);
    }

    ((NumericQuestion)aev43).setDriver(null);

    JPA.em().persist(aev43);
}

    }
    private void createQuestionAEV44() {
        // == AEV44
        // Indiquez la consommation moyenne des voitures, (en litres par 100 km)

        aev44 = (IntegerQuestion) questionService.findByCode(QuestionCode.AEV44);
if (aev44 == null) {
    aev44 = new IntegerQuestion(aev42, 0, QuestionCode.AEV44, null);
    JPA.em().persist(aev44);

    // cleanup the driver
    Driver aev44_driver = driverService.findByName("AEV44");
    if (aev44_driver != null) {
        driverService.remove(aev44_driver);
    }

} else {
    if (!aev44.getQuestionSet().equals(aev42) && aev42.getQuestions().contains(aev44)) {
        aev42.getQuestions().remove(aev44);
        JPA.em().persist(aev42);
    }
    if (aev44.getQuestionSet().equals(aev42) && !aev42.getQuestions().contains(aev44)) {
        aev42.getQuestions().add(aev44);
        JPA.em().persist(aev42);
    }
    aev44.setOrderIndex(0);
    ((NumericQuestion)aev44).setUnitCategory(null);

    // cleanup the driver
    Driver aev44_driver = driverService.findByName("AEV44");
    if (aev44_driver != null) {
        driverService.remove(aev44_driver);
    }

    ((NumericQuestion)aev44).setDriver(null);

    JPA.em().persist(aev44);
}

    }
    private void createQuestionAEV45() {
        // == AEV45
        // Part des véhicules roulant au diesel

        aev45 = (PercentageQuestion) questionService.findByCode(QuestionCode.AEV45);
if (aev45 == null) {
    aev45 = new PercentageQuestion(aev42, 0, QuestionCode.AEV45);
    JPA.em().persist(aev45);

    // cleanup the driver
    Driver aev45_driver = driverService.findByName("AEV45");
    if (aev45_driver != null) {
        driverService.remove(aev45_driver);
    }

    // recreate with good value
    aev45_driver = new Driver("AEV45");
    driverService.saveOrUpdate(aev45_driver);
    Period p2000 = periodService.findByCode(PeriodCode.P2000);
    DriverValue dv = new DriverValue(aev45_driver, p2000, Double.valueOf(69));
    aev45_driver.getDriverValueList().add(dv);;
    driverService.saveOrUpdate(aev45_driver);
    JPA.em().persist(dv);

    ((NumericQuestion)aev45).setDriver(aev45_driver);
    JPA.em().persist(aev45);
} else {
    if (!aev45.getQuestionSet().equals(aev42) && aev42.getQuestions().contains(aev45)) {
        aev42.getQuestions().remove(aev45);
        JPA.em().persist(aev42);
    }
    if (aev45.getQuestionSet().equals(aev42) && !aev42.getQuestions().contains(aev45)) {
        aev42.getQuestions().add(aev45);
        JPA.em().persist(aev42);
    }
    aev45.setOrderIndex(0);

    // cleanup the driver
    Driver aev45_driver = driverService.findByName("AEV45");
    if (aev45_driver != null) {
        driverService.remove(aev45_driver);
    }

    // recreate with good value
    aev45_driver = new Driver("AEV45");
    driverService.saveOrUpdate(aev45_driver);
    Period p2000 = periodService.findByCode(PeriodCode.P2000);
    DriverValue dv = new DriverValue(aev45_driver, p2000, Double.valueOf(69));
    aev45_driver.getDriverValueList().add(dv);;
    driverService.saveOrUpdate(aev45_driver);
    JPA.em().persist(dv);

    ((NumericQuestion)aev45).setDriver(aev45_driver);

    JPA.em().persist(aev45);
}

    }
    private void createQuestionAEV46() {
        // == AEV46
        // Part des véhicules roulant à l'essence

        aev46 = (PercentageQuestion) questionService.findByCode(QuestionCode.AEV46);
if (aev46 == null) {
    aev46 = new PercentageQuestion(aev42, 0, QuestionCode.AEV46);
    JPA.em().persist(aev46);

    // cleanup the driver
    Driver aev46_driver = driverService.findByName("AEV46");
    if (aev46_driver != null) {
        driverService.remove(aev46_driver);
    }

    // recreate with good value
    aev46_driver = new Driver("AEV46");
    driverService.saveOrUpdate(aev46_driver);
    Period p2000 = periodService.findByCode(PeriodCode.P2000);
    DriverValue dv = new DriverValue(aev46_driver, p2000, Double.valueOf(31));
    aev46_driver.getDriverValueList().add(dv);;
    driverService.saveOrUpdate(aev46_driver);
    JPA.em().persist(dv);

    ((NumericQuestion)aev46).setDriver(aev46_driver);
    JPA.em().persist(aev46);
} else {
    if (!aev46.getQuestionSet().equals(aev42) && aev42.getQuestions().contains(aev46)) {
        aev42.getQuestions().remove(aev46);
        JPA.em().persist(aev42);
    }
    if (aev46.getQuestionSet().equals(aev42) && !aev42.getQuestions().contains(aev46)) {
        aev42.getQuestions().add(aev46);
        JPA.em().persist(aev42);
    }
    aev46.setOrderIndex(0);

    // cleanup the driver
    Driver aev46_driver = driverService.findByName("AEV46");
    if (aev46_driver != null) {
        driverService.remove(aev46_driver);
    }

    // recreate with good value
    aev46_driver = new Driver("AEV46");
    driverService.saveOrUpdate(aev46_driver);
    Period p2000 = periodService.findByCode(PeriodCode.P2000);
    DriverValue dv = new DriverValue(aev46_driver, p2000, Double.valueOf(31));
    aev46_driver.getDriverValueList().add(dv);;
    driverService.saveOrUpdate(aev46_driver);
    JPA.em().persist(dv);

    ((NumericQuestion)aev46).setDriver(aev46_driver);

    JPA.em().persist(aev46);
}

    }
    private void createQuestionAEV47() {
        // == AEV47
        // Part des visiteurs qui ont co-voituré

        aev47 = (PercentageQuestion) questionService.findByCode(QuestionCode.AEV47);
if (aev47 == null) {
    aev47 = new PercentageQuestion(aev42, 0, QuestionCode.AEV47);
    JPA.em().persist(aev47);

    // cleanup the driver
    Driver aev47_driver = driverService.findByName("AEV47");
    if (aev47_driver != null) {
        driverService.remove(aev47_driver);
    }

} else {
    if (!aev47.getQuestionSet().equals(aev42) && aev42.getQuestions().contains(aev47)) {
        aev42.getQuestions().remove(aev47);
        JPA.em().persist(aev42);
    }
    if (aev47.getQuestionSet().equals(aev42) && !aev42.getQuestions().contains(aev47)) {
        aev42.getQuestions().add(aev47);
        JPA.em().persist(aev42);
    }
    aev47.setOrderIndex(0);

    // cleanup the driver
    Driver aev47_driver = driverService.findByName("AEV47");
    if (aev47_driver != null) {
        driverService.remove(aev47_driver);
    }

    ((NumericQuestion)aev47).setDriver(null);

    JPA.em().persist(aev47);
}

    }
    private void createQuestionAEV48() {
        // == AEV48
        // Part des visiteurs qui ont pris l'autocar

        aev48 = (PercentageQuestion) questionService.findByCode(QuestionCode.AEV48);
if (aev48 == null) {
    aev48 = new PercentageQuestion(aev42, 0, QuestionCode.AEV48);
    JPA.em().persist(aev48);

    // cleanup the driver
    Driver aev48_driver = driverService.findByName("AEV48");
    if (aev48_driver != null) {
        driverService.remove(aev48_driver);
    }

} else {
    if (!aev48.getQuestionSet().equals(aev42) && aev42.getQuestions().contains(aev48)) {
        aev42.getQuestions().remove(aev48);
        JPA.em().persist(aev42);
    }
    if (aev48.getQuestionSet().equals(aev42) && !aev42.getQuestions().contains(aev48)) {
        aev42.getQuestions().add(aev48);
        JPA.em().persist(aev42);
    }
    aev48.setOrderIndex(0);

    // cleanup the driver
    Driver aev48_driver = driverService.findByName("AEV48");
    if (aev48_driver != null) {
        driverService.remove(aev48_driver);
    }

    ((NumericQuestion)aev48).setDriver(null);

    JPA.em().persist(aev48);
}

    }
    private void createQuestionAEV49() {
        // == AEV49
        // Indiquez la consommation moyenne des autocars (en litres par 100 km)

        aev49 = (IntegerQuestion) questionService.findByCode(QuestionCode.AEV49);
if (aev49 == null) {
    aev49 = new IntegerQuestion(aev42, 0, QuestionCode.AEV49, null);
    JPA.em().persist(aev49);

    // cleanup the driver
    Driver aev49_driver = driverService.findByName("AEV49");
    if (aev49_driver != null) {
        driverService.remove(aev49_driver);
    }

} else {
    if (!aev49.getQuestionSet().equals(aev42) && aev42.getQuestions().contains(aev49)) {
        aev42.getQuestions().remove(aev49);
        JPA.em().persist(aev42);
    }
    if (aev49.getQuestionSet().equals(aev42) && !aev42.getQuestions().contains(aev49)) {
        aev42.getQuestions().add(aev49);
        JPA.em().persist(aev42);
    }
    aev49.setOrderIndex(0);
    ((NumericQuestion)aev49).setUnitCategory(null);

    // cleanup the driver
    Driver aev49_driver = driverService.findByName("AEV49");
    if (aev49_driver != null) {
        driverService.remove(aev49_driver);
    }

    ((NumericQuestion)aev49).setDriver(null);

    JPA.em().persist(aev49);
}

    }
    private void createQuestionAEV50() {
        // == AEV50
        // Part des visiteurs qui ont pris le train

        aev50 = (PercentageQuestion) questionService.findByCode(QuestionCode.AEV50);
if (aev50 == null) {
    aev50 = new PercentageQuestion(aev42, 0, QuestionCode.AEV50);
    JPA.em().persist(aev50);

    // cleanup the driver
    Driver aev50_driver = driverService.findByName("AEV50");
    if (aev50_driver != null) {
        driverService.remove(aev50_driver);
    }

} else {
    if (!aev50.getQuestionSet().equals(aev42) && aev42.getQuestions().contains(aev50)) {
        aev42.getQuestions().remove(aev50);
        JPA.em().persist(aev42);
    }
    if (aev50.getQuestionSet().equals(aev42) && !aev42.getQuestions().contains(aev50)) {
        aev42.getQuestions().add(aev50);
        JPA.em().persist(aev42);
    }
    aev50.setOrderIndex(0);

    // cleanup the driver
    Driver aev50_driver = driverService.findByName("AEV50");
    if (aev50_driver != null) {
        driverService.remove(aev50_driver);
    }

    ((NumericQuestion)aev50).setDriver(null);

    JPA.em().persist(aev50);
}

    }
    private void createQuestionAEV51() {
        // == AEV51
        // Part des visiteurs qui ont pris le bus (en transport en commun)

        aev51 = (PercentageQuestion) questionService.findByCode(QuestionCode.AEV51);
if (aev51 == null) {
    aev51 = new PercentageQuestion(aev42, 0, QuestionCode.AEV51);
    JPA.em().persist(aev51);

    // cleanup the driver
    Driver aev51_driver = driverService.findByName("AEV51");
    if (aev51_driver != null) {
        driverService.remove(aev51_driver);
    }

} else {
    if (!aev51.getQuestionSet().equals(aev42) && aev42.getQuestions().contains(aev51)) {
        aev42.getQuestions().remove(aev51);
        JPA.em().persist(aev42);
    }
    if (aev51.getQuestionSet().equals(aev42) && !aev42.getQuestions().contains(aev51)) {
        aev42.getQuestions().add(aev51);
        JPA.em().persist(aev42);
    }
    aev51.setOrderIndex(0);

    // cleanup the driver
    Driver aev51_driver = driverService.findByName("AEV51");
    if (aev51_driver != null) {
        driverService.remove(aev51_driver);
    }

    ((NumericQuestion)aev51).setDriver(null);

    JPA.em().persist(aev51);
}

    }
    private void createQuestionAEV52() {
        // == AEV52
        // Part des visiteurs qui ont pris le métro

        aev52 = (PercentageQuestion) questionService.findByCode(QuestionCode.AEV52);
if (aev52 == null) {
    aev52 = new PercentageQuestion(aev42, 0, QuestionCode.AEV52);
    JPA.em().persist(aev52);

    // cleanup the driver
    Driver aev52_driver = driverService.findByName("AEV52");
    if (aev52_driver != null) {
        driverService.remove(aev52_driver);
    }

} else {
    if (!aev52.getQuestionSet().equals(aev42) && aev42.getQuestions().contains(aev52)) {
        aev42.getQuestions().remove(aev52);
        JPA.em().persist(aev42);
    }
    if (aev52.getQuestionSet().equals(aev42) && !aev42.getQuestions().contains(aev52)) {
        aev42.getQuestions().add(aev52);
        JPA.em().persist(aev42);
    }
    aev52.setOrderIndex(0);

    // cleanup the driver
    Driver aev52_driver = driverService.findByName("AEV52");
    if (aev52_driver != null) {
        driverService.remove(aev52_driver);
    }

    ((NumericQuestion)aev52).setDriver(null);

    JPA.em().persist(aev52);
}

    }
    private void createQuestionAEV53() {
        // == AEV53
        // Part des visiteurs qui ont pris le tram

        aev53 = (PercentageQuestion) questionService.findByCode(QuestionCode.AEV53);
if (aev53 == null) {
    aev53 = new PercentageQuestion(aev42, 0, QuestionCode.AEV53);
    JPA.em().persist(aev53);

    // cleanup the driver
    Driver aev53_driver = driverService.findByName("AEV53");
    if (aev53_driver != null) {
        driverService.remove(aev53_driver);
    }

} else {
    if (!aev53.getQuestionSet().equals(aev42) && aev42.getQuestions().contains(aev53)) {
        aev42.getQuestions().remove(aev53);
        JPA.em().persist(aev42);
    }
    if (aev53.getQuestionSet().equals(aev42) && !aev42.getQuestions().contains(aev53)) {
        aev42.getQuestions().add(aev53);
        JPA.em().persist(aev42);
    }
    aev53.setOrderIndex(0);

    // cleanup the driver
    Driver aev53_driver = driverService.findByName("AEV53");
    if (aev53_driver != null) {
        driverService.remove(aev53_driver);
    }

    ((NumericQuestion)aev53).setDriver(null);

    JPA.em().persist(aev53);
}

    }
    private void createQuestionAEV54() {
        // == AEV54
        // Part des visiteurs qui ont pris le vélo

        aev54 = (PercentageQuestion) questionService.findByCode(QuestionCode.AEV54);
if (aev54 == null) {
    aev54 = new PercentageQuestion(aev42, 0, QuestionCode.AEV54);
    JPA.em().persist(aev54);

    // cleanup the driver
    Driver aev54_driver = driverService.findByName("AEV54");
    if (aev54_driver != null) {
        driverService.remove(aev54_driver);
    }

} else {
    if (!aev54.getQuestionSet().equals(aev42) && aev42.getQuestions().contains(aev54)) {
        aev42.getQuestions().remove(aev54);
        JPA.em().persist(aev42);
    }
    if (aev54.getQuestionSet().equals(aev42) && !aev42.getQuestions().contains(aev54)) {
        aev42.getQuestions().add(aev54);
        JPA.em().persist(aev42);
    }
    aev54.setOrderIndex(0);

    // cleanup the driver
    Driver aev54_driver = driverService.findByName("AEV54");
    if (aev54_driver != null) {
        driverService.remove(aev54_driver);
    }

    ((NumericQuestion)aev54).setDriver(null);

    JPA.em().persist(aev54);
}

    }
    private void createQuestionAEV55() {
        // == AEV55
        // Part des visiteurs qui sont venus à pied

        aev55 = (PercentageQuestion) questionService.findByCode(QuestionCode.AEV55);
if (aev55 == null) {
    aev55 = new PercentageQuestion(aev42, 0, QuestionCode.AEV55);
    JPA.em().persist(aev55);

    // cleanup the driver
    Driver aev55_driver = driverService.findByName("AEV55");
    if (aev55_driver != null) {
        driverService.remove(aev55_driver);
    }

} else {
    if (!aev55.getQuestionSet().equals(aev42) && aev42.getQuestions().contains(aev55)) {
        aev42.getQuestions().remove(aev55);
        JPA.em().persist(aev42);
    }
    if (aev55.getQuestionSet().equals(aev42) && !aev42.getQuestions().contains(aev55)) {
        aev42.getQuestions().add(aev55);
        JPA.em().persist(aev42);
    }
    aev55.setOrderIndex(0);

    // cleanup the driver
    Driver aev55_driver = driverService.findByName("AEV55");
    if (aev55_driver != null) {
        driverService.remove(aev55_driver);
    }

    ((NumericQuestion)aev55).setDriver(null);

    JPA.em().persist(aev55);
}

    }
    private void createQuestionAEV56() {
        // == AEV56
        // Le total doit faire 100%

        aev56 = (PercentageQuestion) questionService.findByCode(QuestionCode.AEV56);
if (aev56 == null) {
    aev56 = new PercentageQuestion(aev42, 0, QuestionCode.AEV56);
    JPA.em().persist(aev56);

    // cleanup the driver
    Driver aev56_driver = driverService.findByName("AEV56");
    if (aev56_driver != null) {
        driverService.remove(aev56_driver);
    }

} else {
    if (!aev56.getQuestionSet().equals(aev42) && aev42.getQuestions().contains(aev56)) {
        aev42.getQuestions().remove(aev56);
        JPA.em().persist(aev42);
    }
    if (aev56.getQuestionSet().equals(aev42) && !aev42.getQuestions().contains(aev56)) {
        aev42.getQuestions().add(aev56);
        JPA.em().persist(aev42);
    }
    aev56.setOrderIndex(0);

    // cleanup the driver
    Driver aev56_driver = driverService.findByName("AEV56");
    if (aev56_driver != null) {
        driverService.remove(aev56_driver);
    }

    ((NumericQuestion)aev56).setDriver(null);

    JPA.em().persist(aev56);
}

    }
    private void createQuestionAEV59() {
        // == AEV59
        // Catégorie de voyage

        aev59 = (StringQuestion) questionService.findByCode(QuestionCode.AEV59);
if (aev59 == null) {
    aev59 = new StringQuestion(aev58, 0, QuestionCode.AEV59, null);
    JPA.em().persist(aev59);
} else {
    ((StringQuestion)aev59).setDefaultValue(null);
    if (!aev59.getQuestionSet().equals(aev58) && aev58.getQuestions().contains(aev59)) {
        aev58.getQuestions().remove(aev59);
        JPA.em().persist(aev58);
    }
    if (aev59.getQuestionSet().equals(aev58) && !aev58.getQuestions().contains(aev59)) {
        aev58.getQuestions().add(aev59);
        JPA.em().persist(aev58);
    }
    aev59.setOrderIndex(0);
    JPA.em().persist(aev59);
}

    }
    private void createQuestionAEV60() {
        // == AEV60
        // Type de vol

        aev60 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AEV60);
if (aev60 == null) {
    aev60 = new ValueSelectionQuestion(aev58, 0, QuestionCode.AEV60, CodeList.TYPEVOL);
    JPA.em().persist(aev60);
} else {
    if (!aev60.getQuestionSet().equals(aev58) && aev58.getQuestions().contains(aev60)) {
        aev58.getQuestions().remove(aev60);
        JPA.em().persist(aev58);
    }
    if (aev60.getQuestionSet().equals(aev58) && !aev58.getQuestions().contains(aev60)) {
        aev58.getQuestions().add(aev60);
        JPA.em().persist(aev58);
    }
    aev60.setOrderIndex(0);
    ((ValueSelectionQuestion)aev60).setCodeList(CodeList.TYPEVOL);
    JPA.em().persist(aev60);
}

    }
    private void createQuestionAEV61() {
        // == AEV61
        // Classe du vol

        aev61 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AEV61);
if (aev61 == null) {
    aev61 = new ValueSelectionQuestion(aev58, 0, QuestionCode.AEV61, CodeList.CATEGORIEVOL);
    JPA.em().persist(aev61);
} else {
    if (!aev61.getQuestionSet().equals(aev58) && aev58.getQuestions().contains(aev61)) {
        aev58.getQuestions().remove(aev61);
        JPA.em().persist(aev58);
    }
    if (aev61.getQuestionSet().equals(aev58) && !aev58.getQuestions().contains(aev61)) {
        aev58.getQuestions().add(aev61);
        JPA.em().persist(aev58);
    }
    aev61.setOrderIndex(0);
    ((ValueSelectionQuestion)aev61).setCodeList(CodeList.CATEGORIEVOL);
    JPA.em().persist(aev61);
}

    }
    private void createQuestionAEV62() {
        // == AEV62
        // Nombre de vols/an

        aev62 = (IntegerQuestion) questionService.findByCode(QuestionCode.AEV62);
if (aev62 == null) {
    aev62 = new IntegerQuestion(aev58, 0, QuestionCode.AEV62, null);
    JPA.em().persist(aev62);

    // cleanup the driver
    Driver aev62_driver = driverService.findByName("AEV62");
    if (aev62_driver != null) {
        driverService.remove(aev62_driver);
    }

} else {
    if (!aev62.getQuestionSet().equals(aev58) && aev58.getQuestions().contains(aev62)) {
        aev58.getQuestions().remove(aev62);
        JPA.em().persist(aev58);
    }
    if (aev62.getQuestionSet().equals(aev58) && !aev58.getQuestions().contains(aev62)) {
        aev58.getQuestions().add(aev62);
        JPA.em().persist(aev58);
    }
    aev62.setOrderIndex(0);
    ((NumericQuestion)aev62).setUnitCategory(null);

    // cleanup the driver
    Driver aev62_driver = driverService.findByName("AEV62");
    if (aev62_driver != null) {
        driverService.remove(aev62_driver);
    }

    ((NumericQuestion)aev62).setDriver(null);

    JPA.em().persist(aev62);
}

    }
    private void createQuestionAEV63() {
        // == AEV63
        // Distance moyenne aller-retour

        
aev63 = (DoubleQuestion) questionService.findByCode(QuestionCode.AEV63);
if (aev63 == null) {
    aev63 = new DoubleQuestion( aev58, 0, QuestionCode.AEV63, lengthUnits, getUnitBySymbol("km") );
    JPA.em().persist(aev63);

    // cleanup the driver
    Driver aev63_driver = driverService.findByName("AEV63");
    if (aev63_driver != null) {
        driverService.remove(aev63_driver);
    }

} else {
    if (!aev63.getQuestionSet().equals(aev58) && aev58.getQuestions().contains(aev63)) {
        aev58.getQuestions().remove(aev63);
        JPA.em().persist(aev58);
    }
    if (aev63.getQuestionSet().equals(aev58) && !aev58.getQuestions().contains(aev63)) {
        aev58.getQuestions().add(aev63);
        JPA.em().persist(aev58);
    }
    ((NumericQuestion)aev63).setUnitCategory(lengthUnits);
    aev63.setOrderIndex(0);
    ((NumericQuestion)aev63).setDefaultUnit(getUnitBySymbol("km"));

    // cleanup the driver
    Driver aev63_driver = driverService.findByName("AEV63");
    if (aev63_driver != null) {
        driverService.remove(aev63_driver);
    }

    ((NumericQuestion)aev63).setDriver(null);

    JPA.em().persist(aev63);
}



    }
    private void createQuestionAEV65() {
        // == AEV65
        // Pièces documentaires liées à la mobilité des organisateurs

        aev65 = (DocumentQuestion) questionService.findByCode(QuestionCode.AEV65);
if (aev65 == null) {
    aev65 = new DocumentQuestion(aev64, 0, QuestionCode.AEV65);
    JPA.em().persist(aev65);
} else {
    if (!aev65.getQuestionSet().equals(aev64) && aev64.getQuestions().contains(aev65)) {
        aev64.getQuestions().remove(aev65);
        JPA.em().persist(aev64);
    }
    if (aev65.getQuestionSet().equals(aev64) && !aev64.getQuestions().contains(aev65)) {
        aev64.getQuestions().add(aev65);
        JPA.em().persist(aev64);
    }
    aev65.setOrderIndex(0);
    JPA.em().persist(aev65);
}

    }
    private void createQuestionAEV68() {
        // == AEV68
        // Type de véhicule

        aev68 = (StringQuestion) questionService.findByCode(QuestionCode.AEV68);
if (aev68 == null) {
    aev68 = new StringQuestion(aev67, 0, QuestionCode.AEV68, null);
    JPA.em().persist(aev68);
} else {
    ((StringQuestion)aev68).setDefaultValue(null);
    if (!aev68.getQuestionSet().equals(aev67) && aev67.getQuestions().contains(aev68)) {
        aev67.getQuestions().remove(aev68);
        JPA.em().persist(aev67);
    }
    if (aev68.getQuestionSet().equals(aev67) && !aev67.getQuestions().contains(aev68)) {
        aev67.getQuestions().add(aev68);
        JPA.em().persist(aev67);
    }
    aev68.setOrderIndex(0);
    JPA.em().persist(aev68);
}

    }
    private void createQuestionAEV74() {
        // == AEV74
        // Consommation moyenne en litres au 100 km

        aev74 = (IntegerQuestion) questionService.findByCode(QuestionCode.AEV74);
if (aev74 == null) {
    aev74 = new IntegerQuestion(aev67, 0, QuestionCode.AEV74, null);
    JPA.em().persist(aev74);

    // cleanup the driver
    Driver aev74_driver = driverService.findByName("AEV74");
    if (aev74_driver != null) {
        driverService.remove(aev74_driver);
    }

} else {
    if (!aev74.getQuestionSet().equals(aev67) && aev67.getQuestions().contains(aev74)) {
        aev67.getQuestions().remove(aev74);
        JPA.em().persist(aev67);
    }
    if (aev74.getQuestionSet().equals(aev67) && !aev67.getQuestions().contains(aev74)) {
        aev67.getQuestions().add(aev74);
        JPA.em().persist(aev67);
    }
    aev74.setOrderIndex(0);
    ((NumericQuestion)aev74).setUnitCategory(null);

    // cleanup the driver
    Driver aev74_driver = driverService.findByName("AEV74");
    if (aev74_driver != null) {
        driverService.remove(aev74_driver);
    }

    ((NumericQuestion)aev74).setDriver(null);

    JPA.em().persist(aev74);
}

    }
    private void createQuestionAEV76() {
        // == AEV76
        // Type de carburant

        aev76 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AEV76);
if (aev76 == null) {
    aev76 = new ValueSelectionQuestion(aev67, 0, QuestionCode.AEV76, CodeList.CARBURANT);
    JPA.em().persist(aev76);
} else {
    if (!aev76.getQuestionSet().equals(aev67) && aev67.getQuestions().contains(aev76)) {
        aev67.getQuestions().remove(aev76);
        JPA.em().persist(aev67);
    }
    if (aev76.getQuestionSet().equals(aev67) && !aev67.getQuestions().contains(aev76)) {
        aev67.getQuestions().add(aev76);
        JPA.em().persist(aev67);
    }
    aev76.setOrderIndex(0);
    ((ValueSelectionQuestion)aev76).setCodeList(CodeList.CARBURANT);
    JPA.em().persist(aev76);
}

    }
    private void createQuestionAEV77() {
        // == AEV77
        // Indiquez la distance parcourue  (dans le cadre de l'événement)

        
aev77 = (DoubleQuestion) questionService.findByCode(QuestionCode.AEV77);
if (aev77 == null) {
    aev77 = new DoubleQuestion( aev67, 0, QuestionCode.AEV77, lengthUnits, getUnitBySymbol("km") );
    JPA.em().persist(aev77);

    // cleanup the driver
    Driver aev77_driver = driverService.findByName("AEV77");
    if (aev77_driver != null) {
        driverService.remove(aev77_driver);
    }

} else {
    if (!aev77.getQuestionSet().equals(aev67) && aev67.getQuestions().contains(aev77)) {
        aev67.getQuestions().remove(aev77);
        JPA.em().persist(aev67);
    }
    if (aev77.getQuestionSet().equals(aev67) && !aev67.getQuestions().contains(aev77)) {
        aev67.getQuestions().add(aev77);
        JPA.em().persist(aev67);
    }
    ((NumericQuestion)aev77).setUnitCategory(lengthUnits);
    aev77.setOrderIndex(0);
    ((NumericQuestion)aev77).setDefaultUnit(getUnitBySymbol("km"));

    // cleanup the driver
    Driver aev77_driver = driverService.findByName("AEV77");
    if (aev77_driver != null) {
        driverService.remove(aev77_driver);
    }

    ((NumericQuestion)aev77).setDriver(null);

    JPA.em().persist(aev77);
}



    }
    private void createQuestionAEV79() {
        // == AEV79
        // Indiquez le nombre de passagers.km parcourus en train   (dans le cadre de l'événement)

        aev79 = (IntegerQuestion) questionService.findByCode(QuestionCode.AEV79);
if (aev79 == null) {
    aev79 = new IntegerQuestion(aev78, 0, QuestionCode.AEV79, null);
    JPA.em().persist(aev79);

    // cleanup the driver
    Driver aev79_driver = driverService.findByName("AEV79");
    if (aev79_driver != null) {
        driverService.remove(aev79_driver);
    }

} else {
    if (!aev79.getQuestionSet().equals(aev78) && aev78.getQuestions().contains(aev79)) {
        aev78.getQuestions().remove(aev79);
        JPA.em().persist(aev78);
    }
    if (aev79.getQuestionSet().equals(aev78) && !aev78.getQuestions().contains(aev79)) {
        aev78.getQuestions().add(aev79);
        JPA.em().persist(aev78);
    }
    aev79.setOrderIndex(0);
    ((NumericQuestion)aev79).setUnitCategory(null);

    // cleanup the driver
    Driver aev79_driver = driverService.findByName("AEV79");
    if (aev79_driver != null) {
        driverService.remove(aev79_driver);
    }

    ((NumericQuestion)aev79).setDriver(null);

    JPA.em().persist(aev79);
}

    }
    private void createQuestionAEV80() {
        // == AEV80
        // Indiquez le nombre de passagers.km parcourus en bus  (dans le cadre de l'événement)

        aev80 = (IntegerQuestion) questionService.findByCode(QuestionCode.AEV80);
if (aev80 == null) {
    aev80 = new IntegerQuestion(aev78, 0, QuestionCode.AEV80, null);
    JPA.em().persist(aev80);

    // cleanup the driver
    Driver aev80_driver = driverService.findByName("AEV80");
    if (aev80_driver != null) {
        driverService.remove(aev80_driver);
    }

} else {
    if (!aev80.getQuestionSet().equals(aev78) && aev78.getQuestions().contains(aev80)) {
        aev78.getQuestions().remove(aev80);
        JPA.em().persist(aev78);
    }
    if (aev80.getQuestionSet().equals(aev78) && !aev78.getQuestions().contains(aev80)) {
        aev78.getQuestions().add(aev80);
        JPA.em().persist(aev78);
    }
    aev80.setOrderIndex(0);
    ((NumericQuestion)aev80).setUnitCategory(null);

    // cleanup the driver
    Driver aev80_driver = driverService.findByName("AEV80");
    if (aev80_driver != null) {
        driverService.remove(aev80_driver);
    }

    ((NumericQuestion)aev80).setDriver(null);

    JPA.em().persist(aev80);
}

    }
    private void createQuestionAEV81() {
        // == AEV81
        // Indiquez le nombre de passagers.km parcourus en métro   (dans le cadre de l'événement)

        aev81 = (IntegerQuestion) questionService.findByCode(QuestionCode.AEV81);
if (aev81 == null) {
    aev81 = new IntegerQuestion(aev78, 0, QuestionCode.AEV81, null);
    JPA.em().persist(aev81);

    // cleanup the driver
    Driver aev81_driver = driverService.findByName("AEV81");
    if (aev81_driver != null) {
        driverService.remove(aev81_driver);
    }

} else {
    if (!aev81.getQuestionSet().equals(aev78) && aev78.getQuestions().contains(aev81)) {
        aev78.getQuestions().remove(aev81);
        JPA.em().persist(aev78);
    }
    if (aev81.getQuestionSet().equals(aev78) && !aev78.getQuestions().contains(aev81)) {
        aev78.getQuestions().add(aev81);
        JPA.em().persist(aev78);
    }
    aev81.setOrderIndex(0);
    ((NumericQuestion)aev81).setUnitCategory(null);

    // cleanup the driver
    Driver aev81_driver = driverService.findByName("AEV81");
    if (aev81_driver != null) {
        driverService.remove(aev81_driver);
    }

    ((NumericQuestion)aev81).setDriver(null);

    JPA.em().persist(aev81);
}

    }
    private void createQuestionAEV82() {
        // == AEV82
        // Indiquez le nombre de passagers.km parcourus en tram   (dans le cadre de l'événement)

        aev82 = (IntegerQuestion) questionService.findByCode(QuestionCode.AEV82);
if (aev82 == null) {
    aev82 = new IntegerQuestion(aev78, 0, QuestionCode.AEV82, null);
    JPA.em().persist(aev82);

    // cleanup the driver
    Driver aev82_driver = driverService.findByName("AEV82");
    if (aev82_driver != null) {
        driverService.remove(aev82_driver);
    }

} else {
    if (!aev82.getQuestionSet().equals(aev78) && aev78.getQuestions().contains(aev82)) {
        aev78.getQuestions().remove(aev82);
        JPA.em().persist(aev78);
    }
    if (aev82.getQuestionSet().equals(aev78) && !aev78.getQuestions().contains(aev82)) {
        aev78.getQuestions().add(aev82);
        JPA.em().persist(aev78);
    }
    aev82.setOrderIndex(0);
    ((NumericQuestion)aev82).setUnitCategory(null);

    // cleanup the driver
    Driver aev82_driver = driverService.findByName("AEV82");
    if (aev82_driver != null) {
        driverService.remove(aev82_driver);
    }

    ((NumericQuestion)aev82).setDriver(null);

    JPA.em().persist(aev82);
}

    }
    private void createQuestionAEV83() {
        // == AEV83
        // Indiquez le nombre de passagers.km parcourus en TGV  (dans le cadre de l'événement)

        aev83 = (IntegerQuestion) questionService.findByCode(QuestionCode.AEV83);
if (aev83 == null) {
    aev83 = new IntegerQuestion(aev78, 0, QuestionCode.AEV83, null);
    JPA.em().persist(aev83);

    // cleanup the driver
    Driver aev83_driver = driverService.findByName("AEV83");
    if (aev83_driver != null) {
        driverService.remove(aev83_driver);
    }

} else {
    if (!aev83.getQuestionSet().equals(aev78) && aev78.getQuestions().contains(aev83)) {
        aev78.getQuestions().remove(aev83);
        JPA.em().persist(aev78);
    }
    if (aev83.getQuestionSet().equals(aev78) && !aev78.getQuestions().contains(aev83)) {
        aev78.getQuestions().add(aev83);
        JPA.em().persist(aev78);
    }
    aev83.setOrderIndex(0);
    ((NumericQuestion)aev83).setUnitCategory(null);

    // cleanup the driver
    Driver aev83_driver = driverService.findByName("AEV83");
    if (aev83_driver != null) {
        driverService.remove(aev83_driver);
    }

    ((NumericQuestion)aev83).setDriver(null);

    JPA.em().persist(aev83);
}

    }
    private void createQuestionAEV86() {
        // == AEV86
        // Catégorie de voyage

        aev86 = (StringQuestion) questionService.findByCode(QuestionCode.AEV86);
if (aev86 == null) {
    aev86 = new StringQuestion(aev85, 0, QuestionCode.AEV86, null);
    JPA.em().persist(aev86);
} else {
    ((StringQuestion)aev86).setDefaultValue(null);
    if (!aev86.getQuestionSet().equals(aev85) && aev85.getQuestions().contains(aev86)) {
        aev85.getQuestions().remove(aev86);
        JPA.em().persist(aev85);
    }
    if (aev86.getQuestionSet().equals(aev85) && !aev85.getQuestions().contains(aev86)) {
        aev85.getQuestions().add(aev86);
        JPA.em().persist(aev85);
    }
    aev86.setOrderIndex(0);
    JPA.em().persist(aev86);
}

    }
    private void createQuestionAEV87() {
        // == AEV87
        // Type de vol

        aev87 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AEV87);
if (aev87 == null) {
    aev87 = new ValueSelectionQuestion(aev85, 0, QuestionCode.AEV87, CodeList.TYPEVOL);
    JPA.em().persist(aev87);
} else {
    if (!aev87.getQuestionSet().equals(aev85) && aev85.getQuestions().contains(aev87)) {
        aev85.getQuestions().remove(aev87);
        JPA.em().persist(aev85);
    }
    if (aev87.getQuestionSet().equals(aev85) && !aev85.getQuestions().contains(aev87)) {
        aev85.getQuestions().add(aev87);
        JPA.em().persist(aev85);
    }
    aev87.setOrderIndex(0);
    ((ValueSelectionQuestion)aev87).setCodeList(CodeList.TYPEVOL);
    JPA.em().persist(aev87);
}

    }
    private void createQuestionAEV88() {
        // == AEV88
        // Classe du vol

        aev88 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AEV88);
if (aev88 == null) {
    aev88 = new ValueSelectionQuestion(aev85, 0, QuestionCode.AEV88, CodeList.CATEGORIEVOL);
    JPA.em().persist(aev88);
} else {
    if (!aev88.getQuestionSet().equals(aev85) && aev85.getQuestions().contains(aev88)) {
        aev85.getQuestions().remove(aev88);
        JPA.em().persist(aev85);
    }
    if (aev88.getQuestionSet().equals(aev85) && !aev85.getQuestions().contains(aev88)) {
        aev85.getQuestions().add(aev88);
        JPA.em().persist(aev85);
    }
    aev88.setOrderIndex(0);
    ((ValueSelectionQuestion)aev88).setCodeList(CodeList.CATEGORIEVOL);
    JPA.em().persist(aev88);
}

    }
    private void createQuestionAEV89() {
        // == AEV89
        // Nombre de vols/an   (dans le cadre de l'événement)

        aev89 = (IntegerQuestion) questionService.findByCode(QuestionCode.AEV89);
if (aev89 == null) {
    aev89 = new IntegerQuestion(aev85, 0, QuestionCode.AEV89, null);
    JPA.em().persist(aev89);

    // cleanup the driver
    Driver aev89_driver = driverService.findByName("AEV89");
    if (aev89_driver != null) {
        driverService.remove(aev89_driver);
    }

} else {
    if (!aev89.getQuestionSet().equals(aev85) && aev85.getQuestions().contains(aev89)) {
        aev85.getQuestions().remove(aev89);
        JPA.em().persist(aev85);
    }
    if (aev89.getQuestionSet().equals(aev85) && !aev85.getQuestions().contains(aev89)) {
        aev85.getQuestions().add(aev89);
        JPA.em().persist(aev85);
    }
    aev89.setOrderIndex(0);
    ((NumericQuestion)aev89).setUnitCategory(null);

    // cleanup the driver
    Driver aev89_driver = driverService.findByName("AEV89");
    if (aev89_driver != null) {
        driverService.remove(aev89_driver);
    }

    ((NumericQuestion)aev89).setDriver(null);

    JPA.em().persist(aev89);
}

    }
    private void createQuestionAEV90() {
        // == AEV90
        // Distance moyenne aller-retour

        
aev90 = (DoubleQuestion) questionService.findByCode(QuestionCode.AEV90);
if (aev90 == null) {
    aev90 = new DoubleQuestion( aev85, 0, QuestionCode.AEV90, lengthUnits, getUnitBySymbol("km") );
    JPA.em().persist(aev90);

    // cleanup the driver
    Driver aev90_driver = driverService.findByName("AEV90");
    if (aev90_driver != null) {
        driverService.remove(aev90_driver);
    }

} else {
    if (!aev90.getQuestionSet().equals(aev85) && aev85.getQuestions().contains(aev90)) {
        aev85.getQuestions().remove(aev90);
        JPA.em().persist(aev85);
    }
    if (aev90.getQuestionSet().equals(aev85) && !aev85.getQuestions().contains(aev90)) {
        aev85.getQuestions().add(aev90);
        JPA.em().persist(aev85);
    }
    ((NumericQuestion)aev90).setUnitCategory(lengthUnits);
    aev90.setOrderIndex(0);
    ((NumericQuestion)aev90).setDefaultUnit(getUnitBySymbol("km"));

    // cleanup the driver
    Driver aev90_driver = driverService.findByName("AEV90");
    if (aev90_driver != null) {
        driverService.remove(aev90_driver);
    }

    ((NumericQuestion)aev90).setDriver(null);

    JPA.em().persist(aev90);
}



    }
    private void createQuestionAEV92() {
        // == AEV92
        // Distance parcourue en vélo (dans le cadre de l'événement)

        
aev92 = (DoubleQuestion) questionService.findByCode(QuestionCode.AEV92);
if (aev92 == null) {
    aev92 = new DoubleQuestion( aev91, 0, QuestionCode.AEV92, lengthUnits, getUnitBySymbol("km") );
    JPA.em().persist(aev92);

    // cleanup the driver
    Driver aev92_driver = driverService.findByName("AEV92");
    if (aev92_driver != null) {
        driverService.remove(aev92_driver);
    }

} else {
    if (!aev92.getQuestionSet().equals(aev91) && aev91.getQuestions().contains(aev92)) {
        aev91.getQuestions().remove(aev92);
        JPA.em().persist(aev91);
    }
    if (aev92.getQuestionSet().equals(aev91) && !aev91.getQuestions().contains(aev92)) {
        aev91.getQuestions().add(aev92);
        JPA.em().persist(aev91);
    }
    ((NumericQuestion)aev92).setUnitCategory(lengthUnits);
    aev92.setOrderIndex(0);
    ((NumericQuestion)aev92).setDefaultUnit(getUnitBySymbol("km"));

    // cleanup the driver
    Driver aev92_driver = driverService.findByName("AEV92");
    if (aev92_driver != null) {
        driverService.remove(aev92_driver);
    }

    ((NumericQuestion)aev92).setDriver(null);

    JPA.em().persist(aev92);
}



    }
    private void createQuestionAEV93() {
        // == AEV93
        // Distance parcourue à pied (dans le cadre de l'événement)

        
aev93 = (DoubleQuestion) questionService.findByCode(QuestionCode.AEV93);
if (aev93 == null) {
    aev93 = new DoubleQuestion( aev91, 0, QuestionCode.AEV93, lengthUnits, getUnitBySymbol("km") );
    JPA.em().persist(aev93);

    // cleanup the driver
    Driver aev93_driver = driverService.findByName("AEV93");
    if (aev93_driver != null) {
        driverService.remove(aev93_driver);
    }

} else {
    if (!aev93.getQuestionSet().equals(aev91) && aev91.getQuestions().contains(aev93)) {
        aev91.getQuestions().remove(aev93);
        JPA.em().persist(aev91);
    }
    if (aev93.getQuestionSet().equals(aev91) && !aev91.getQuestions().contains(aev93)) {
        aev91.getQuestions().add(aev93);
        JPA.em().persist(aev91);
    }
    ((NumericQuestion)aev93).setUnitCategory(lengthUnits);
    aev93.setOrderIndex(0);
    ((NumericQuestion)aev93).setDefaultUnit(getUnitBySymbol("km"));

    // cleanup the driver
    Driver aev93_driver = driverService.findByName("AEV93");
    if (aev93_driver != null) {
        driverService.remove(aev93_driver);
    }

    ((NumericQuestion)aev93).setDriver(null);

    JPA.em().persist(aev93);
}



    }
    private void createQuestionAEV95() {
        // == AEV95
        // Pièces documentaires liées à la logistique

        aev95 = (DocumentQuestion) questionService.findByCode(QuestionCode.AEV95);
if (aev95 == null) {
    aev95 = new DocumentQuestion(aev94, 0, QuestionCode.AEV95);
    JPA.em().persist(aev95);
} else {
    if (!aev95.getQuestionSet().equals(aev94) && aev94.getQuestions().contains(aev95)) {
        aev94.getQuestions().remove(aev95);
        JPA.em().persist(aev94);
    }
    if (aev95.getQuestionSet().equals(aev94) && !aev94.getQuestions().contains(aev95)) {
        aev94.getQuestions().add(aev95);
        JPA.em().persist(aev94);
    }
    aev95.setOrderIndex(0);
    JPA.em().persist(aev95);
}

    }
    private void createQuestionAEV99() {
        // == AEV99
        // Transport

        aev99 = (StringQuestion) questionService.findByCode(QuestionCode.AEV99);
if (aev99 == null) {
    aev99 = new StringQuestion(aev98, 0, QuestionCode.AEV99, null);
    JPA.em().persist(aev99);
} else {
    ((StringQuestion)aev99).setDefaultValue(null);
    if (!aev99.getQuestionSet().equals(aev98) && aev98.getQuestions().contains(aev99)) {
        aev98.getQuestions().remove(aev99);
        JPA.em().persist(aev98);
    }
    if (aev99.getQuestionSet().equals(aev98) && !aev98.getQuestions().contains(aev99)) {
        aev98.getQuestions().add(aev99);
        JPA.em().persist(aev98);
    }
    aev99.setOrderIndex(0);
    JPA.em().persist(aev99);
}

    }
    private void createQuestionAEV100() {
        // == AEV100
        // Type de véhicules

        aev100 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AEV100);
if (aev100 == null) {
    aev100 = new ValueSelectionQuestion(aev98, 0, QuestionCode.AEV100, CodeList.TYPETRANSPORTEVENEMENT);
    JPA.em().persist(aev100);
} else {
    if (!aev100.getQuestionSet().equals(aev98) && aev98.getQuestions().contains(aev100)) {
        aev98.getQuestions().remove(aev100);
        JPA.em().persist(aev98);
    }
    if (aev100.getQuestionSet().equals(aev98) && !aev98.getQuestions().contains(aev100)) {
        aev98.getQuestions().add(aev100);
        JPA.em().persist(aev98);
    }
    aev100.setOrderIndex(0);
    ((ValueSelectionQuestion)aev100).setCodeList(CodeList.TYPETRANSPORTEVENEMENT);
    JPA.em().persist(aev100);
}

    }
    private void createQuestionAEV101() {
        // == AEV101
        // Consommation moyenne (l/100 km)

        aev101 = (IntegerQuestion) questionService.findByCode(QuestionCode.AEV101);
if (aev101 == null) {
    aev101 = new IntegerQuestion(aev98, 0, QuestionCode.AEV101, null);
    JPA.em().persist(aev101);

    // cleanup the driver
    Driver aev101_driver = driverService.findByName("AEV101");
    if (aev101_driver != null) {
        driverService.remove(aev101_driver);
    }

} else {
    if (!aev101.getQuestionSet().equals(aev98) && aev98.getQuestions().contains(aev101)) {
        aev98.getQuestions().remove(aev101);
        JPA.em().persist(aev98);
    }
    if (aev101.getQuestionSet().equals(aev98) && !aev98.getQuestions().contains(aev101)) {
        aev98.getQuestions().add(aev101);
        JPA.em().persist(aev98);
    }
    aev101.setOrderIndex(0);
    ((NumericQuestion)aev101).setUnitCategory(null);

    // cleanup the driver
    Driver aev101_driver = driverService.findByName("AEV101");
    if (aev101_driver != null) {
        driverService.remove(aev101_driver);
    }

    ((NumericQuestion)aev101).setDriver(null);

    JPA.em().persist(aev101);
}

    }
    private void createQuestionAEV102() {
        // == AEV102
        // Type de carburant

        aev102 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AEV102);
if (aev102 == null) {
    aev102 = new ValueSelectionQuestion(aev98, 0, QuestionCode.AEV102, CodeList.CARBURANT);
    JPA.em().persist(aev102);
} else {
    if (!aev102.getQuestionSet().equals(aev98) && aev98.getQuestions().contains(aev102)) {
        aev98.getQuestions().remove(aev102);
        JPA.em().persist(aev98);
    }
    if (aev102.getQuestionSet().equals(aev98) && !aev98.getQuestions().contains(aev102)) {
        aev98.getQuestions().add(aev102);
        JPA.em().persist(aev98);
    }
    aev102.setOrderIndex(0);
    ((ValueSelectionQuestion)aev102).setCodeList(CodeList.CARBURANT);
    JPA.em().persist(aev102);
}

    }
    private void createQuestionAEV103() {
        // == AEV103
        // Nombre de trajet effectués  (dans le cadre de l'événement)

        aev103 = (IntegerQuestion) questionService.findByCode(QuestionCode.AEV103);
if (aev103 == null) {
    aev103 = new IntegerQuestion(aev98, 0, QuestionCode.AEV103, null);
    JPA.em().persist(aev103);

    // cleanup the driver
    Driver aev103_driver = driverService.findByName("AEV103");
    if (aev103_driver != null) {
        driverService.remove(aev103_driver);
    }

} else {
    if (!aev103.getQuestionSet().equals(aev98) && aev98.getQuestions().contains(aev103)) {
        aev98.getQuestions().remove(aev103);
        JPA.em().persist(aev98);
    }
    if (aev103.getQuestionSet().equals(aev98) && !aev98.getQuestions().contains(aev103)) {
        aev98.getQuestions().add(aev103);
        JPA.em().persist(aev98);
    }
    aev103.setOrderIndex(0);
    ((NumericQuestion)aev103).setUnitCategory(null);

    // cleanup the driver
    Driver aev103_driver = driverService.findByName("AEV103");
    if (aev103_driver != null) {
        driverService.remove(aev103_driver);
    }

    ((NumericQuestion)aev103).setDriver(null);

    JPA.em().persist(aev103);
}

    }
    private void createQuestionAEV104() {
        // == AEV104
        // Distance moyenne d'un trajet (aller+retour)

        
aev104 = (DoubleQuestion) questionService.findByCode(QuestionCode.AEV104);
if (aev104 == null) {
    aev104 = new DoubleQuestion( aev98, 0, QuestionCode.AEV104, lengthUnits, getUnitBySymbol("km") );
    JPA.em().persist(aev104);

    // cleanup the driver
    Driver aev104_driver = driverService.findByName("AEV104");
    if (aev104_driver != null) {
        driverService.remove(aev104_driver);
    }

} else {
    if (!aev104.getQuestionSet().equals(aev98) && aev98.getQuestions().contains(aev104)) {
        aev98.getQuestions().remove(aev104);
        JPA.em().persist(aev98);
    }
    if (aev104.getQuestionSet().equals(aev98) && !aev98.getQuestions().contains(aev104)) {
        aev98.getQuestions().add(aev104);
        JPA.em().persist(aev98);
    }
    ((NumericQuestion)aev104).setUnitCategory(lengthUnits);
    aev104.setOrderIndex(0);
    ((NumericQuestion)aev104).setDefaultUnit(getUnitBySymbol("km"));

    // cleanup the driver
    Driver aev104_driver = driverService.findByName("AEV104");
    if (aev104_driver != null) {
        driverService.remove(aev104_driver);
    }

    ((NumericQuestion)aev104).setDriver(null);

    JPA.em().persist(aev104);
}



    }
    private void createQuestionAEV105() {
        // == AEV105
        // Est-ce un véhicule détenu par l'organisateur de l'événement?

        aev105 = (BooleanQuestion) questionService.findByCode(QuestionCode.AEV105);
if (aev105 == null) {
    aev105 = new BooleanQuestion(aev98, 0, QuestionCode.AEV105, null);
    JPA.em().persist(aev105);
} else {
    ((BooleanQuestion)aev105).setDefaultValue(null);
    if (!aev105.getQuestionSet().equals(aev98) && aev98.getQuestions().contains(aev105)) {
        aev98.getQuestions().remove(aev105);
        JPA.em().persist(aev98);
    }
    if (aev105.getQuestionSet().equals(aev98) && !aev98.getQuestions().contains(aev105)) {
        aev98.getQuestions().add(aev105);
        JPA.em().persist(aev98);
    }
    aev105.setOrderIndex(0);
    JPA.em().persist(aev105);
}

    }
    private void createQuestionAEV107() {
        // == AEV107
        // Pièces documentaires liées aux déchets

        aev107 = (DocumentQuestion) questionService.findByCode(QuestionCode.AEV107);
if (aev107 == null) {
    aev107 = new DocumentQuestion(aev106, 0, QuestionCode.AEV107);
    JPA.em().persist(aev107);
} else {
    if (!aev107.getQuestionSet().equals(aev106) && aev106.getQuestions().contains(aev107)) {
        aev106.getQuestions().remove(aev107);
        JPA.em().persist(aev106);
    }
    if (aev107.getQuestionSet().equals(aev106) && !aev106.getQuestions().contains(aev107)) {
        aev106.getQuestions().add(aev107);
        JPA.em().persist(aev106);
    }
    aev107.setOrderIndex(0);
    JPA.em().persist(aev107);
}

    }
    private void createQuestionAEV110() {
        // == AEV110
        // Type de déchet, et traitement appliqué

        aev110 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AEV110);
if (aev110 == null) {
    aev110 = new ValueSelectionQuestion(aev109, 0, QuestionCode.AEV110, CodeList.TYPEDECHETMENAGE);
    JPA.em().persist(aev110);
} else {
    if (!aev110.getQuestionSet().equals(aev109) && aev109.getQuestions().contains(aev110)) {
        aev109.getQuestions().remove(aev110);
        JPA.em().persist(aev109);
    }
    if (aev110.getQuestionSet().equals(aev109) && !aev109.getQuestions().contains(aev110)) {
        aev109.getQuestions().add(aev110);
        JPA.em().persist(aev109);
    }
    aev110.setOrderIndex(0);
    ((ValueSelectionQuestion)aev110).setCodeList(CodeList.TYPEDECHETMENAGE);
    JPA.em().persist(aev110);
}

    }
    private void createQuestionAEV111() {
        // == AEV111
        // Type de données disponibles

        aev111 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AEV111);
if (aev111 == null) {
    aev111 = new ValueSelectionQuestion(aev109, 0, QuestionCode.AEV111, CodeList.MESUREDECHETMENAGE);
    JPA.em().persist(aev111);
} else {
    if (!aev111.getQuestionSet().equals(aev109) && aev109.getQuestions().contains(aev111)) {
        aev109.getQuestions().remove(aev111);
        JPA.em().persist(aev109);
    }
    if (aev111.getQuestionSet().equals(aev109) && !aev109.getQuestions().contains(aev111)) {
        aev109.getQuestions().add(aev111);
        JPA.em().persist(aev109);
    }
    aev111.setOrderIndex(0);
    ((ValueSelectionQuestion)aev111).setCodeList(CodeList.MESUREDECHETMENAGE);
    JPA.em().persist(aev111);
}

    }
    private void createQuestionAEV113() {
        // == AEV113
        // Nombre de sac/conteneurs pendant l'évènement

        aev113 = (IntegerQuestion) questionService.findByCode(QuestionCode.AEV113);
if (aev113 == null) {
    aev113 = new IntegerQuestion(aev112, 0, QuestionCode.AEV113, null);
    JPA.em().persist(aev113);

    // cleanup the driver
    Driver aev113_driver = driverService.findByName("AEV113");
    if (aev113_driver != null) {
        driverService.remove(aev113_driver);
    }

} else {
    if (!aev113.getQuestionSet().equals(aev112) && aev112.getQuestions().contains(aev113)) {
        aev112.getQuestions().remove(aev113);
        JPA.em().persist(aev112);
    }
    if (aev113.getQuestionSet().equals(aev112) && !aev112.getQuestions().contains(aev113)) {
        aev112.getQuestions().add(aev113);
        JPA.em().persist(aev112);
    }
    aev113.setOrderIndex(0);
    ((NumericQuestion)aev113).setUnitCategory(null);

    // cleanup the driver
    Driver aev113_driver = driverService.findByName("AEV113");
    if (aev113_driver != null) {
        driverService.remove(aev113_driver);
    }

    ((NumericQuestion)aev113).setDriver(null);

    JPA.em().persist(aev113);
}

    }
    private void createQuestionAEV114() {
        // == AEV114
        // Volume des sacs/conteneurs

        
aev114 = (DoubleQuestion) questionService.findByCode(QuestionCode.AEV114);
if (aev114 == null) {
    aev114 = new DoubleQuestion( aev112, 0, QuestionCode.AEV114, volumeUnits, getUnitBySymbol("l") );
    JPA.em().persist(aev114);

    // cleanup the driver
    Driver aev114_driver = driverService.findByName("AEV114");
    if (aev114_driver != null) {
        driverService.remove(aev114_driver);
    }

} else {
    if (!aev114.getQuestionSet().equals(aev112) && aev112.getQuestions().contains(aev114)) {
        aev112.getQuestions().remove(aev114);
        JPA.em().persist(aev112);
    }
    if (aev114.getQuestionSet().equals(aev112) && !aev112.getQuestions().contains(aev114)) {
        aev112.getQuestions().add(aev114);
        JPA.em().persist(aev112);
    }
    ((NumericQuestion)aev114).setUnitCategory(volumeUnits);
    aev114.setOrderIndex(0);
    ((NumericQuestion)aev114).setDefaultUnit(getUnitBySymbol("l"));

    // cleanup the driver
    Driver aev114_driver = driverService.findByName("AEV114");
    if (aev114_driver != null) {
        driverService.remove(aev114_driver);
    }

    ((NumericQuestion)aev114).setDriver(null);

    JPA.em().persist(aev114);
}



    }
    private void createQuestionAEV115() {
        // == AEV115
        // % de remplissage

        aev115 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AEV115);
if (aev115 == null) {
    aev115 = new ValueSelectionQuestion(aev112, 0, QuestionCode.AEV115, CodeList.FRACTIONREMPLISSAGEDECHETS);
    JPA.em().persist(aev115);
} else {
    if (!aev115.getQuestionSet().equals(aev112) && aev112.getQuestions().contains(aev115)) {
        aev112.getQuestions().remove(aev115);
        JPA.em().persist(aev112);
    }
    if (aev115.getQuestionSet().equals(aev112) && !aev112.getQuestions().contains(aev115)) {
        aev112.getQuestions().add(aev115);
        JPA.em().persist(aev112);
    }
    aev115.setOrderIndex(0);
    ((ValueSelectionQuestion)aev115).setCodeList(CodeList.FRACTIONREMPLISSAGEDECHETS);
    JPA.em().persist(aev115);
}

    }
    private void createQuestionAEV117() {
        // == AEV117
        // Quantité liée à l'évènement

        
aev117 = (DoubleQuestion) questionService.findByCode(QuestionCode.AEV117);
if (aev117 == null) {
    aev117 = new DoubleQuestion( aev116, 0, QuestionCode.AEV117, massUnits, getUnitBySymbol("kg") );
    JPA.em().persist(aev117);

    // cleanup the driver
    Driver aev117_driver = driverService.findByName("AEV117");
    if (aev117_driver != null) {
        driverService.remove(aev117_driver);
    }

} else {
    if (!aev117.getQuestionSet().equals(aev116) && aev116.getQuestions().contains(aev117)) {
        aev116.getQuestions().remove(aev117);
        JPA.em().persist(aev116);
    }
    if (aev117.getQuestionSet().equals(aev116) && !aev116.getQuestions().contains(aev117)) {
        aev116.getQuestions().add(aev117);
        JPA.em().persist(aev116);
    }
    ((NumericQuestion)aev117).setUnitCategory(massUnits);
    aev117.setOrderIndex(0);
    ((NumericQuestion)aev117).setDefaultUnit(getUnitBySymbol("kg"));


    // cleanup the driver
    Driver aev117_driver = driverService.findByName("AEV117");
    if (aev117_driver != null) {
        driverService.remove(aev117_driver);
    }

    ((NumericQuestion)aev117).setDriver(null);

    JPA.em().persist(aev117);
}



    }
    private void createQuestionAEV122() {
        // == AEV122
        // Quantité liée à l'évènement

        
aev122 = (DoubleQuestion) questionService.findByCode(QuestionCode.AEV122);
if (aev122 == null) {
    aev122 = new DoubleQuestion( aev109, 0, QuestionCode.AEV122, massUnits, getUnitBySymbol("kg") );
    JPA.em().persist(aev122);

    // cleanup the driver
    Driver aev122_driver = driverService.findByName("AEV122");
    if (aev122_driver != null) {
        driverService.remove(aev122_driver);
    }

} else {
    if (!aev122.getQuestionSet().equals(aev109) && aev109.getQuestions().contains(aev122)) {
        aev109.getQuestions().remove(aev122);
        JPA.em().persist(aev109);
    }
    if (aev122.getQuestionSet().equals(aev109) && !aev109.getQuestions().contains(aev122)) {
        aev109.getQuestions().add(aev122);
        JPA.em().persist(aev109);
    }
    ((NumericQuestion)aev122).setUnitCategory(massUnits);
    aev122.setOrderIndex(0);
    ((NumericQuestion)aev122).setDefaultUnit(getUnitBySymbol("kg"));


    // cleanup the driver
    Driver aev122_driver = driverService.findByName("AEV122");
    if (aev122_driver != null) {
        driverService.remove(aev122_driver);
    }

    ((NumericQuestion)aev122).setDriver(null);

    JPA.em().persist(aev122);
}



    }
    private void createQuestionAEV125() {
        // == AEV125
        // Pièces documentaires liées aux achats

        aev125 = (DocumentQuestion) questionService.findByCode(QuestionCode.AEV125);
if (aev125 == null) {
    aev125 = new DocumentQuestion(aev124, 0, QuestionCode.AEV125);
    JPA.em().persist(aev125);
} else {
    if (!aev125.getQuestionSet().equals(aev124) && aev124.getQuestions().contains(aev125)) {
        aev124.getQuestions().remove(aev125);
        JPA.em().persist(aev124);
    }
    if (aev125.getQuestionSet().equals(aev124) && !aev124.getQuestions().contains(aev125)) {
        aev124.getQuestions().add(aev125);
        JPA.em().persist(aev124);
    }
    aev125.setOrderIndex(0);
    JPA.em().persist(aev125);
}

    }
    private void createQuestionAEV128() {
        // == AEV128
        // Type de matériaux

        aev128 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.AEV128);
if (aev128 == null) {
    aev128 = new ValueSelectionQuestion(aev127, 0, QuestionCode.AEV128, CodeList.TYPEACHATEVENEMENT);
    JPA.em().persist(aev128);
} else {
    if (!aev128.getQuestionSet().equals(aev127) && aev127.getQuestions().contains(aev128)) {
        aev127.getQuestions().remove(aev128);
        JPA.em().persist(aev127);
    }
    if (aev128.getQuestionSet().equals(aev127) && !aev127.getQuestions().contains(aev128)) {
        aev127.getQuestions().add(aev128);
        JPA.em().persist(aev127);
    }
    aev128.setOrderIndex(0);
    ((ValueSelectionQuestion)aev128).setCodeList(CodeList.TYPEACHATEVENEMENT);
    JPA.em().persist(aev128);
}

    }
    private void createQuestionAEV129() {
        // == AEV129
        // Indiquez les quantités de matériel achetées

        
aev129 = (DoubleQuestion) questionService.findByCode(QuestionCode.AEV129);
if (aev129 == null) {
    aev129 = new DoubleQuestion( aev127, 0, QuestionCode.AEV129, massUnits, getUnitBySymbol("kg") );
    JPA.em().persist(aev129);

    // cleanup the driver
    Driver aev129_driver = driverService.findByName("AEV129");
    if (aev129_driver != null) {
        driverService.remove(aev129_driver);
    }

} else {
    if (!aev129.getQuestionSet().equals(aev127) && aev127.getQuestions().contains(aev129)) {
        aev127.getQuestions().remove(aev129);
        JPA.em().persist(aev127);
    }
    if (aev129.getQuestionSet().equals(aev127) && !aev127.getQuestions().contains(aev129)) {
        aev127.getQuestions().add(aev129);
        JPA.em().persist(aev127);
    }
    ((NumericQuestion)aev129).setUnitCategory(massUnits);
    aev129.setOrderIndex(0);
    ((NumericQuestion)aev129).setDefaultUnit(getUnitBySymbol("kg"));


    // cleanup the driver
    Driver aev129_driver = driverService.findByName("AEV129");
    if (aev129_driver != null) {
        driverService.remove(aev129_driver);
    }

    ((NumericQuestion)aev129).setDriver(null);

    JPA.em().persist(aev129);
}



    }
    private void createQuestionAEV131() {
        // == AEV131
        // Indiquez le nombre de pièces de textile achetées

        aev131 = (IntegerQuestion) questionService.findByCode(QuestionCode.AEV131);
if (aev131 == null) {
    aev131 = new IntegerQuestion(aev130, 0, QuestionCode.AEV131, null);
    JPA.em().persist(aev131);

    // cleanup the driver
    Driver aev131_driver = driverService.findByName("AEV131");
    if (aev131_driver != null) {
        driverService.remove(aev131_driver);
    }

} else {
    if (!aev131.getQuestionSet().equals(aev130) && aev130.getQuestions().contains(aev131)) {
        aev130.getQuestions().remove(aev131);
        JPA.em().persist(aev130);
    }
    if (aev131.getQuestionSet().equals(aev130) && !aev130.getQuestions().contains(aev131)) {
        aev130.getQuestions().add(aev131);
        JPA.em().persist(aev130);
    }
    aev131.setOrderIndex(0);
    ((NumericQuestion)aev131).setUnitCategory(null);

    // cleanup the driver
    Driver aev131_driver = driverService.findByName("AEV131");
    if (aev131_driver != null) {
        driverService.remove(aev131_driver);
    }

    ((NumericQuestion)aev131).setDriver(null);

    JPA.em().persist(aev131);
}

    }
    private void createQuestionAEV134() {
        // == AEV134
        // Indiquez le nombre de repas avec viande rouge

        aev134 = (IntegerQuestion) questionService.findByCode(QuestionCode.AEV134);
if (aev134 == null) {
    aev134 = new IntegerQuestion(aev133, 0, QuestionCode.AEV134, null);
    JPA.em().persist(aev134);

    // cleanup the driver
    Driver aev134_driver = driverService.findByName("AEV134");
    if (aev134_driver != null) {
        driverService.remove(aev134_driver);
    }

} else {
    if (!aev134.getQuestionSet().equals(aev133) && aev133.getQuestions().contains(aev134)) {
        aev133.getQuestions().remove(aev134);
        JPA.em().persist(aev133);
    }
    if (aev134.getQuestionSet().equals(aev133) && !aev133.getQuestions().contains(aev134)) {
        aev133.getQuestions().add(aev134);
        JPA.em().persist(aev133);
    }
    aev134.setOrderIndex(0);
    ((NumericQuestion)aev134).setUnitCategory(null);

    // cleanup the driver
    Driver aev134_driver = driverService.findByName("AEV134");
    if (aev134_driver != null) {
        driverService.remove(aev134_driver);
    }

    ((NumericQuestion)aev134).setDriver(null);

    JPA.em().persist(aev134);
}

    }
    private void createQuestionAEV135() {
        // == AEV135
        // Indiquez le nombre de repas avec viande blanche ou poisson

        aev135 = (IntegerQuestion) questionService.findByCode(QuestionCode.AEV135);
if (aev135 == null) {
    aev135 = new IntegerQuestion(aev133, 0, QuestionCode.AEV135, null);
    JPA.em().persist(aev135);

    // cleanup the driver
    Driver aev135_driver = driverService.findByName("AEV135");
    if (aev135_driver != null) {
        driverService.remove(aev135_driver);
    }

} else {
    if (!aev135.getQuestionSet().equals(aev133) && aev133.getQuestions().contains(aev135)) {
        aev133.getQuestions().remove(aev135);
        JPA.em().persist(aev133);
    }
    if (aev135.getQuestionSet().equals(aev133) && !aev133.getQuestions().contains(aev135)) {
        aev133.getQuestions().add(aev135);
        JPA.em().persist(aev133);
    }
    aev135.setOrderIndex(0);
    ((NumericQuestion)aev135).setUnitCategory(null);

    // cleanup the driver
    Driver aev135_driver = driverService.findByName("AEV135");
    if (aev135_driver != null) {
        driverService.remove(aev135_driver);
    }

    ((NumericQuestion)aev135).setDriver(null);

    JPA.em().persist(aev135);
}

    }
    private void createQuestionAEV137() {
        // == AEV137
        // Indiquez le nombre de repas végétariens

        aev137 = (IntegerQuestion) questionService.findByCode(QuestionCode.AEV137);
if (aev137 == null) {
    aev137 = new IntegerQuestion(aev136, 0, QuestionCode.AEV137, null);
    JPA.em().persist(aev137);

    // cleanup the driver
    Driver aev137_driver = driverService.findByName("AEV137");
    if (aev137_driver != null) {
        driverService.remove(aev137_driver);
    }

} else {
    if (!aev137.getQuestionSet().equals(aev136) && aev136.getQuestions().contains(aev137)) {
        aev136.getQuestions().remove(aev137);
        JPA.em().persist(aev136);
    }
    if (aev137.getQuestionSet().equals(aev136) && !aev136.getQuestions().contains(aev137)) {
        aev136.getQuestions().add(aev137);
        JPA.em().persist(aev136);
    }
    aev137.setOrderIndex(0);
    ((NumericQuestion)aev137).setUnitCategory(null);

    // cleanup the driver
    Driver aev137_driver = driverService.findByName("AEV137");
    if (aev137_driver != null) {
        driverService.remove(aev137_driver);
    }

    ((NumericQuestion)aev137).setDriver(null);

    JPA.em().persist(aev137);
}

    }


    private UnitCategory getUnitCategoryByCode(UnitCategoryCode unitCategoryCode) {
        return unitCategoryService.findByCode(unitCategoryCode);
    }

    private Unit getUnitBySymbol(String symbol) {
        return unitService.findBySymbol(symbol);
    }

}



