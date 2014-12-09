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

	@Autowired
    private DriverService driverService;

    @Autowired
    private PeriodService periodService;

    @Autowired
    private AwacCalculatorService awacCalculatorService;

    private Form form2,form3,form4,form5,form6,form7;
    private QuestionSet a1,a13,a15,a1000,a1003,a20,a22,a25,a31,a34,a37,a40,a41,a42,a45,a47,a6000,a6001,a6002,a6006,a50,a52,a400,a401,a402,a406,a407,a412,a413,a518,a519,a502,a506,a507,a512,a513,a600,a601,a602,a606,a607,a612,a613,a93,a94,a113,a114,a115,a121,a205,a208,a209,a223,a224,a128,a130,a131,a132,a140,a141,a142,a157,a163,a164,a166,a1006,a1009,a170,a173,a4999,a5000,a180,a181,a182,a185,a188,a191,a194,a196,a197,a201,a229,a231,a237,a238,a309,a311,a313,a1012,a1015,a317,a320,a322,a325,a1018,a1021,a329,a332,a334,a243,a244,a8000,a250,a252,a253,a266,a272,a273,a275,a1024,a1027,a279,a282,a284,a1030,a1033,a288,a291,a297,a300,a5010;
    private Question a2,a3,a4,a5,a6,a9,a10,a11,a12,a14,a16,a17,a1001,a1002,a1004,a1005,a21,a23,a24,a26,a27,a28,a32,a33,a35,a36,a38,a39,a43,a44,a46,a48,a49,a6003,a6004,a6005,a6007,a6008,a6009,a51,a403,a404,a405,a408,a409,a410,a411,a414,a415,a416,a417,a503,a504,a505,a508,a509,a510,a511,a514,a515,a516,a517,a603,a604,a605,a608,a609,a610,a611,a614,a615,a616,a617,a95,a96,a97,a98,a99,a100,a101,a102,a103,a104,a105,a106,a107,a108,a116,a117,a118,a119,a120,a122,a123,a124,a125,a126,a127,a206,a210,a211,a212,a213,a214,a215,a216,a217,a218,a219,a220,a221,a222,a225,a226,a227,a228,a129,a133,a134,a135,a136,a137,a138,a139,a500,a143,a145,a146,a147,a148,a149,a150,a151,a152,a153,a154,a155,a156,a158,a159,a160,a161,a162,a165,a167,a168,a1007,a1008,a1010,a1011,a169,a171,a172,a174,a5001,a5002,a5003,a183,a184,a186,a187,a189,a190,a192,a193,a198,a199,a200,a202,a203,a204,a230,a232,a233,a234,a235,a236,a239,a240,a241,a242,a310,a312,a314,a315,a1013,a1014,a1016,a1017,a316,a318,a319,a321,a323,a324,a326,a327,a1019,a1020,a1022,a1023,a328,a330,a331,a333,a335,a336,a337,a338,a245,a246,a247,a248,a249,a251,a254,a255,a256,a257,a258,a259,a260,a261,a262,a263,a264,a265,a267,a268,a269,a270,a271,a274,a276,a277,a1025,a1026,a1028,a1029,a278,a280,a281,a283,a285,a286,a1031,a1032,a1034,a1035,a287,a289,a290,a292,a293,a294,a295,a296,a298,a299,a301,a302,a5011,a5012,a5013,a5014;

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

        Logger.info("===> CREATE AWAC Enterprise INITIAL DATA -- START");

        long startTime = System.currentTimeMillis();

        energyUnits  = getUnitCategoryByCode(UnitCategoryCode.ENERGY);
        massUnits    = getUnitCategoryByCode(UnitCategoryCode.MASS);
        volumeUnits  = getUnitCategoryByCode(UnitCategoryCode.VOLUME);
        lengthUnits  = getUnitCategoryByCode(UnitCategoryCode.LENGTH);
        areaUnits    = getUnitCategoryByCode(UnitCategoryCode.AREA);
        powerUnits   = getUnitCategoryByCode(UnitCategoryCode.POWER);
        moneyUnits   = getUnitCategoryByCode(UnitCategoryCode.CURRENCY);
        timeUnits    = getUnitCategoryByCode(UnitCategoryCode.DURATION);

        awacCalculator = awacCalculatorService.findByCode(new InterfaceTypeCode("enterprise"));

        // delete old questions
		{
			List<Question> allQuestions = questionService.findAll();
            List<String> codes = Arrays.asList("A2", "A3", "A4", "A5", "A6", "A9", "A10", "A11", "A12", "A14", "A16", "A17", "A1001", "A1002", "A1004", "A1005", "A21", "A23", "A24", "A26", "A27", "A28", "A32", "A33", "A35", "A36", "A38", "A39", "A43", "A44", "A46", "A48", "A49", "A6003", "A6004", "A6005", "A6007", "A6008", "A6009", "A51", "A403", "A404", "A405", "A408", "A409", "A410", "A411", "A414", "A415", "A416", "A417", "A503", "A504", "A505", "A508", "A509", "A510", "A511", "A514", "A515", "A516", "A517", "A603", "A604", "A605", "A608", "A609", "A610", "A611", "A614", "A615", "A616", "A617", "A95", "A96", "A97", "A98", "A99", "A100", "A101", "A102", "A103", "A104", "A105", "A106", "A107", "A108", "A116", "A117", "A118", "A119", "A120", "A122", "A123", "A124", "A125", "A126", "A127", "A206", "A210", "A211", "A212", "A213", "A214", "A215", "A216", "A217", "A218", "A219", "A220", "A221", "A222", "A225", "A226", "A227", "A228", "A129", "A133", "A134", "A135", "A136", "A137", "A138", "A139", "A500", "A143", "A145", "A146", "A147", "A148", "A149", "A150", "A151", "A152", "A153", "A154", "A155", "A156", "A158", "A159", "A160", "A161", "A162", "A165", "A167", "A168", "A1007", "A1008", "A1010", "A1011", "A169", "A171", "A172", "A174", "A5001", "A5002", "A5003", "A183", "A184", "A186", "A187", "A189", "A190", "A192", "A193", "A198", "A199", "A200", "A202", "A203", "A204", "A230", "A232", "A233", "A234", "A235", "A236", "A239", "A240", "A241", "A242", "A310", "A312", "A314", "A315", "A1013", "A1014", "A1016", "A1017", "A316", "A318", "A319", "A321", "A323", "A324", "A326", "A327", "A1019", "A1020", "A1022", "A1023", "A328", "A330", "A331", "A333", "A335", "A336", "A337", "A338", "A245", "A246", "A247", "A248", "A249", "A251", "A254", "A255", "A256", "A257", "A258", "A259", "A260", "A261", "A262", "A263", "A264", "A265", "A267", "A268", "A269", "A270", "A271", "A274", "A276", "A277", "A1025", "A1026", "A1028", "A1029", "A278", "A280", "A281", "A283", "A285", "A286", "A1031", "A1032", "A1034", "A1035", "A287", "A289", "A290", "A292", "A293", "A294", "A295", "A296", "A298", "A299", "A301", "A302", "A5011", "A5012", "A5013", "A5014");

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
            List<String> codes = Arrays.asList("A1", "A13", "A15", "A1000", "A1003", "A20", "A22", "A25", "A31", "A34", "A37", "A40", "A41", "A42", "A45", "A47", "A6000", "A6001", "A6002", "A6006", "A50", "A52", "A400", "A401", "A402", "A406", "A407", "A412", "A413", "A518", "A519", "A502", "A506", "A507", "A512", "A513", "A600", "A601", "A602", "A606", "A607", "A612", "A613", "A93", "A94", "A113", "A114", "A115", "A121", "A205", "A208", "A209", "A223", "A224", "A128", "A130", "A131", "A132", "A140", "A141", "A142", "A157", "A163", "A164", "A166", "A1006", "A1009", "A170", "A173", "A4999", "A5000", "A180", "A181", "A182", "A185", "A188", "A191", "A194", "A196", "A197", "A201", "A229", "A231", "A237", "A238", "A309", "A311", "A313", "A1012", "A1015", "A317", "A320", "A322", "A325", "A1018", "A1021", "A329", "A332", "A334", "A243", "A244", "A8000", "A250", "A252", "A253", "A266", "A272", "A273", "A275", "A1024", "A1027", "A279", "A282", "A284", "A1030", "A1033", "A288", "A291", "A297", "A300", "A5010");

			for (QuestionSet qs : new ArrayList<>(allQuestionSets)) {
				if (codes.contains(qs.getCode().getKey()) || !qs.getCode().getKey().matches("A[0-9]+")) {
					allQuestionSets.remove(qs);
				}
			}
			for (QuestionSet qs : allQuestionSets) {
				deleteQuestionSet(qs, 0);
			}
		}

        createForm2();
        createForm3();
        createForm4();
        createForm5();
        createForm6();
        createForm7();

        createQuestionSetA1();
        createQuestionSetA13();
        createQuestionSetA15();
        createQuestionSetA1000();
        createQuestionSetA1003();
        createQuestionSetA20();
        createQuestionSetA22();
        createQuestionSetA25();
        createQuestionSetA31();
        createQuestionSetA34();
        createQuestionSetA37();
        createQuestionSetA40();
        createQuestionSetA41();
        createQuestionSetA42();
        createQuestionSetA45();
        createQuestionSetA47();
        createQuestionSetA6000();
        createQuestionSetA6001();
        createQuestionSetA6002();
        createQuestionSetA6006();
        createQuestionSetA50();
        createQuestionSetA52();
        createQuestionSetA400();
        createQuestionSetA401();
        createQuestionSetA402();
        createQuestionSetA406();
        createQuestionSetA407();
        createQuestionSetA412();
        createQuestionSetA413();
        createQuestionSetA518();
        createQuestionSetA519();
        createQuestionSetA502();
        createQuestionSetA506();
        createQuestionSetA507();
        createQuestionSetA512();
        createQuestionSetA513();
        createQuestionSetA600();
        createQuestionSetA601();
        createQuestionSetA602();
        createQuestionSetA606();
        createQuestionSetA607();
        createQuestionSetA612();
        createQuestionSetA613();
        createQuestionSetA93();
        createQuestionSetA94();
        createQuestionSetA113();
        createQuestionSetA114();
        createQuestionSetA115();
        createQuestionSetA121();
        createQuestionSetA205();
        createQuestionSetA208();
        createQuestionSetA209();
        createQuestionSetA223();
        createQuestionSetA224();
        createQuestionSetA128();
        createQuestionSetA130();
        createQuestionSetA131();
        createQuestionSetA132();
        createQuestionSetA140();
        createQuestionSetA141();
        createQuestionSetA142();
        createQuestionSetA157();
        createQuestionSetA163();
        createQuestionSetA164();
        createQuestionSetA166();
        createQuestionSetA1006();
        createQuestionSetA1009();
        createQuestionSetA170();
        createQuestionSetA173();
        createQuestionSetA4999();
        createQuestionSetA5000();
        createQuestionSetA180();
        createQuestionSetA181();
        createQuestionSetA182();
        createQuestionSetA185();
        createQuestionSetA188();
        createQuestionSetA191();
        createQuestionSetA194();
        createQuestionSetA196();
        createQuestionSetA197();
        createQuestionSetA201();
        createQuestionSetA229();
        createQuestionSetA231();
        createQuestionSetA237();
        createQuestionSetA238();
        createQuestionSetA309();
        createQuestionSetA311();
        createQuestionSetA313();
        createQuestionSetA1012();
        createQuestionSetA1015();
        createQuestionSetA317();
        createQuestionSetA320();
        createQuestionSetA322();
        createQuestionSetA325();
        createQuestionSetA1018();
        createQuestionSetA1021();
        createQuestionSetA329();
        createQuestionSetA332();
        createQuestionSetA334();
        createQuestionSetA243();
        createQuestionSetA244();
        createQuestionSetA8000();
        createQuestionSetA250();
        createQuestionSetA252();
        createQuestionSetA253();
        createQuestionSetA266();
        createQuestionSetA272();
        createQuestionSetA273();
        createQuestionSetA275();
        createQuestionSetA1024();
        createQuestionSetA1027();
        createQuestionSetA279();
        createQuestionSetA282();
        createQuestionSetA284();
        createQuestionSetA1030();
        createQuestionSetA1033();
        createQuestionSetA288();
        createQuestionSetA291();
        createQuestionSetA297();
        createQuestionSetA300();
        createQuestionSetA5010();

        createQuestionA2();
        createQuestionA3();
        createQuestionA4();
        createQuestionA5();
        createQuestionA6();
        createQuestionA9();
        createQuestionA10();
        createQuestionA11();
        createQuestionA12();
        createQuestionA14();
        createQuestionA16();
        createQuestionA17();
        createQuestionA1001();
        createQuestionA1002();
        createQuestionA1004();
        createQuestionA1005();
        createQuestionA21();
        createQuestionA23();
        createQuestionA24();
        createQuestionA26();
        createQuestionA27();
        createQuestionA28();
        createQuestionA32();
        createQuestionA33();
        createQuestionA35();
        createQuestionA36();
        createQuestionA38();
        createQuestionA39();
        createQuestionA43();
        createQuestionA44();
        createQuestionA46();
        createQuestionA48();
        createQuestionA49();
        createQuestionA6003();
        createQuestionA6004();
        createQuestionA6005();
        createQuestionA6007();
        createQuestionA6008();
        createQuestionA6009();
        createQuestionA51();
        createQuestionA403();
        createQuestionA404();
        createQuestionA405();
        createQuestionA408();
        createQuestionA409();
        createQuestionA410();
        createQuestionA411();
        createQuestionA414();
        createQuestionA415();
        createQuestionA416();
        createQuestionA417();
        createQuestionA503();
        createQuestionA504();
        createQuestionA505();
        createQuestionA508();
        createQuestionA509();
        createQuestionA510();
        createQuestionA511();
        createQuestionA514();
        createQuestionA515();
        createQuestionA516();
        createQuestionA517();
        createQuestionA603();
        createQuestionA604();
        createQuestionA605();
        createQuestionA608();
        createQuestionA609();
        createQuestionA610();
        createQuestionA611();
        createQuestionA614();
        createQuestionA615();
        createQuestionA616();
        createQuestionA617();
        createQuestionA95();
        createQuestionA96();
        createQuestionA97();
        createQuestionA98();
        createQuestionA99();
        createQuestionA100();
        createQuestionA101();
        createQuestionA102();
        createQuestionA103();
        createQuestionA104();
        createQuestionA105();
        createQuestionA106();
        createQuestionA107();
        createQuestionA108();
        createQuestionA116();
        createQuestionA117();
        createQuestionA118();
        createQuestionA119();
        createQuestionA120();
        createQuestionA122();
        createQuestionA123();
        createQuestionA124();
        createQuestionA125();
        createQuestionA126();
        createQuestionA127();
        createQuestionA206();
        createQuestionA210();
        createQuestionA211();
        createQuestionA212();
        createQuestionA213();
        createQuestionA214();
        createQuestionA215();
        createQuestionA216();
        createQuestionA217();
        createQuestionA218();
        createQuestionA219();
        createQuestionA220();
        createQuestionA221();
        createQuestionA222();
        createQuestionA225();
        createQuestionA226();
        createQuestionA227();
        createQuestionA228();
        createQuestionA129();
        createQuestionA133();
        createQuestionA134();
        createQuestionA135();
        createQuestionA136();
        createQuestionA137();
        createQuestionA138();
        createQuestionA139();
        createQuestionA500();
        createQuestionA143();
        createQuestionA145();
        createQuestionA146();
        createQuestionA147();
        createQuestionA148();
        createQuestionA149();
        createQuestionA150();
        createQuestionA151();
        createQuestionA152();
        createQuestionA153();
        createQuestionA154();
        createQuestionA155();
        createQuestionA156();
        createQuestionA158();
        createQuestionA159();
        createQuestionA160();
        createQuestionA161();
        createQuestionA162();
        createQuestionA165();
        createQuestionA167();
        createQuestionA168();
        createQuestionA1007();
        createQuestionA1008();
        createQuestionA1010();
        createQuestionA1011();
        createQuestionA169();
        createQuestionA171();
        createQuestionA172();
        createQuestionA174();
        createQuestionA5001();
        createQuestionA5002();
        createQuestionA5003();
        createQuestionA183();
        createQuestionA184();
        createQuestionA186();
        createQuestionA187();
        createQuestionA189();
        createQuestionA190();
        createQuestionA192();
        createQuestionA193();
        createQuestionA198();
        createQuestionA199();
        createQuestionA200();
        createQuestionA202();
        createQuestionA203();
        createQuestionA204();
        createQuestionA230();
        createQuestionA232();
        createQuestionA233();
        createQuestionA234();
        createQuestionA235();
        createQuestionA236();
        createQuestionA239();
        createQuestionA240();
        createQuestionA241();
        createQuestionA242();
        createQuestionA310();
        createQuestionA312();
        createQuestionA314();
        createQuestionA315();
        createQuestionA1013();
        createQuestionA1014();
        createQuestionA1016();
        createQuestionA1017();
        createQuestionA316();
        createQuestionA318();
        createQuestionA319();
        createQuestionA321();
        createQuestionA323();
        createQuestionA324();
        createQuestionA326();
        createQuestionA327();
        createQuestionA1019();
        createQuestionA1020();
        createQuestionA1022();
        createQuestionA1023();
        createQuestionA328();
        createQuestionA330();
        createQuestionA331();
        createQuestionA333();
        createQuestionA335();
        createQuestionA336();
        createQuestionA337();
        createQuestionA338();
        createQuestionA245();
        createQuestionA246();
        createQuestionA247();
        createQuestionA248();
        createQuestionA249();
        createQuestionA251();
        createQuestionA254();
        createQuestionA255();
        createQuestionA256();
        createQuestionA257();
        createQuestionA258();
        createQuestionA259();
        createQuestionA260();
        createQuestionA261();
        createQuestionA262();
        createQuestionA263();
        createQuestionA264();
        createQuestionA265();
        createQuestionA267();
        createQuestionA268();
        createQuestionA269();
        createQuestionA270();
        createQuestionA271();
        createQuestionA274();
        createQuestionA276();
        createQuestionA277();
        createQuestionA1025();
        createQuestionA1026();
        createQuestionA1028();
        createQuestionA1029();
        createQuestionA278();
        createQuestionA280();
        createQuestionA281();
        createQuestionA283();
        createQuestionA285();
        createQuestionA286();
        createQuestionA1031();
        createQuestionA1032();
        createQuestionA1034();
        createQuestionA1035();
        createQuestionA287();
        createQuestionA289();
        createQuestionA290();
        createQuestionA292();
        createQuestionA293();
        createQuestionA294();
        createQuestionA295();
        createQuestionA296();
        createQuestionA298();
        createQuestionA299();
        createQuestionA301();
        createQuestionA302();
        createQuestionA5011();
        createQuestionA5012();
        createQuestionA5013();
        createQuestionA5014();


        Logger.info("===> CREATE AWAC Enterprise INITIAL DATA -- END (Took {} milliseconds)", (System.currentTimeMillis() - startTime));
    }

    // =========================================================================
    // FORMS
    // =========================================================================

    private void createForm2() {
        // == TAB2
        // DESCRIPTION, CONSOMMATION & REJETS DU  SITE
        form2 = formService.findByIdentifier("TAB2");
        if (form2 == null) {
            form2 = new Form("TAB2");
            form2.setAwacCalculator(awacCalculator);
            JPA.em().persist(form2);
        }
    }
    private void createForm3() {
        // == TAB3
        // MOBILITE
        form3 = formService.findByIdentifier("TAB3");
        if (form3 == null) {
            form3 = new Form("TAB3");
            form3.setAwacCalculator(awacCalculator);
            JPA.em().persist(form3);
        }
    }
    private void createForm4() {
        // == TAB4
        // ACHATS, TRANSPORT ET DISTRIBUTION AMONT
        form4 = formService.findByIdentifier("TAB4");
        if (form4 == null) {
            form4 = new Form("TAB4");
            form4.setAwacCalculator(awacCalculator);
            JPA.em().persist(form4);
        }
    }
    private void createForm5() {
        // == TAB5
        // DECHETS, EAUX USEES
        form5 = formService.findByIdentifier("TAB5");
        if (form5 == null) {
            form5 = new Form("TAB5");
            form5.setAwacCalculator(awacCalculator);
            JPA.em().persist(form5);
        }
    }
    private void createForm6() {
        // == TAB6
        // BIENS D'EQUIPEMENT, ACTIFS, FRANCHISES, INVESTISSEMENTS
        form6 = formService.findByIdentifier("TAB6");
        if (form6 == null) {
            form6 = new Form("TAB6");
            form6.setAwacCalculator(awacCalculator);
            JPA.em().persist(form6);
        }
    }
    private void createForm7() {
        // == TAB7
        // PRODUITS VENDUS
        form7 = formService.findByIdentifier("TAB7");
        if (form7 == null) {
            form7 = new Form("TAB7");
            form7.setAwacCalculator(awacCalculator);
            JPA.em().persist(form7);
        }
    }


    // =========================================================================
    // QUESTION SETS
    // =========================================================================

    private void createQuestionSetA1() {
        // == A1
        // Données générales
        a1 = questionSetService.findByCode(QuestionCode.A1);
        if( a1 == null ) {
            a1 = new QuestionSet(QuestionCode.A1, false, null);
            JPA.em().persist(a1);
        }
        form2.getQuestionSets().add(a1);
        JPA.em().persist(form2);
    }
    private void createQuestionSetA13() {
        // == A13
        // Consommation de combustibles
        a13 = questionSetService.findByCode(QuestionCode.A13);
        if( a13 == null ) {
            a13 = new QuestionSet(QuestionCode.A13, false, a1);
            JPA.em().persist(a13);
        }
    }
    private void createQuestionSetA15() {
        // == A15
        // Combustion de combustible (mesurés en unités d'énergie)
        a15 = questionSetService.findByCode(QuestionCode.A15);
        if( a15 == null ) {
            a15 = new QuestionSet(QuestionCode.A15, true, a13);
            JPA.em().persist(a15);
        }
    }
    private void createQuestionSetA1000() {
        // == A1000
        // Combustion de combustible (mesurés en volume)
        a1000 = questionSetService.findByCode(QuestionCode.A1000);
        if( a1000 == null ) {
            a1000 = new QuestionSet(QuestionCode.A1000, true, a13);
            JPA.em().persist(a1000);
        }
    }
    private void createQuestionSetA1003() {
        // == A1003
        // Combustion de combustible  (mesurés en poids)
        a1003 = questionSetService.findByCode(QuestionCode.A1003);
        if( a1003 == null ) {
            a1003 = new QuestionSet(QuestionCode.A1003, true, a13);
            JPA.em().persist(a1003);
        }
    }
    private void createQuestionSetA20() {
        // == A20
        // Electricité et vapeur achetées
        a20 = questionSetService.findByCode(QuestionCode.A20);
        if( a20 == null ) {
            a20 = new QuestionSet(QuestionCode.A20, false, null);
            JPA.em().persist(a20);
        }
        form2.getQuestionSets().add(a20);
        JPA.em().persist(form2);
    }
    private void createQuestionSetA22() {
        // == A22
        // Electricité
        a22 = questionSetService.findByCode(QuestionCode.A22);
        if( a22 == null ) {
            a22 = new QuestionSet(QuestionCode.A22, false, a20);
            JPA.em().persist(a22);
        }
    }
    private void createQuestionSetA25() {
        // == A25
        // Vapeur
        a25 = questionSetService.findByCode(QuestionCode.A25);
        if( a25 == null ) {
            a25 = new QuestionSet(QuestionCode.A25, true, a20);
            JPA.em().persist(a25);
        }
    }
    private void createQuestionSetA31() {
        // == A31
        // GES des procédés de production
        a31 = questionSetService.findByCode(QuestionCode.A31);
        if( a31 == null ) {
            a31 = new QuestionSet(QuestionCode.A31, false, null);
            JPA.em().persist(a31);
        }
        form2.getQuestionSets().add(a31);
        JPA.em().persist(form2);
    }
    private void createQuestionSetA34() {
        // == A34
        // Type de GES émis par les procédés de production
        a34 = questionSetService.findByCode(QuestionCode.A34);
        if( a34 == null ) {
            a34 = new QuestionSet(QuestionCode.A34, true, a31);
            JPA.em().persist(a34);
        }
    }
    private void createQuestionSetA37() {
        // == A37
        // Systèmes de refroidissement
        a37 = questionSetService.findByCode(QuestionCode.A37);
        if( a37 == null ) {
            a37 = new QuestionSet(QuestionCode.A37, false, null);
            JPA.em().persist(a37);
        }
        form2.getQuestionSets().add(a37);
        JPA.em().persist(form2);
    }
    private void createQuestionSetA40() {
        // == A40
        // Méthodes au choix
        a40 = questionSetService.findByCode(QuestionCode.A40);
        if( a40 == null ) {
            a40 = new QuestionSet(QuestionCode.A40, false, a37);
            JPA.em().persist(a40);
        }
    }
    private void createQuestionSetA41() {
        // == A41
        // Méthode directe à partir des recharges
        a41 = questionSetService.findByCode(QuestionCode.A41);
        if( a41 == null ) {
            a41 = new QuestionSet(QuestionCode.A41, false, a40);
            JPA.em().persist(a41);
        }
    }
    private void createQuestionSetA42() {
        // == A42
        // Listes des types de gaz réfrigérants utilisés
        a42 = questionSetService.findByCode(QuestionCode.A42);
        if( a42 == null ) {
            a42 = new QuestionSet(QuestionCode.A42, true, a41);
            JPA.em().persist(a42);
        }
    }
    private void createQuestionSetA45() {
        // == A45
        // Méthode indirecte à partir de la puissance des équipements de froid
        a45 = questionSetService.findByCode(QuestionCode.A45);
        if( a45 == null ) {
            a45 = new QuestionSet(QuestionCode.A45, false, a40);
            JPA.em().persist(a45);
        }
    }
    private void createQuestionSetA47() {
        // == A47
        // Méthode indirecte à partir de la consommation éléctrique du site
        a47 = questionSetService.findByCode(QuestionCode.A47);
        if( a47 == null ) {
            a47 = new QuestionSet(QuestionCode.A47, false, a40);
            JPA.em().persist(a47);
        }
    }
    private void createQuestionSetA6000() {
        // == A6000
        // Eaux usées industrielles traitées par l'entreprise
        a6000 = questionSetService.findByCode(QuestionCode.A6000);
        if( a6000 == null ) {
            a6000 = new QuestionSet(QuestionCode.A6000, false, null);
            JPA.em().persist(a6000);
        }
        form2.getQuestionSets().add(a6000);
        JPA.em().persist(form2);
    }
    private void createQuestionSetA6001() {
        // == A6001
        // Méthodes alternatives
        a6001 = questionSetService.findByCode(QuestionCode.A6001);
        if( a6001 == null ) {
            a6001 = new QuestionSet(QuestionCode.A6001, false, a6000);
            JPA.em().persist(a6001);
        }
    }
    private void createQuestionSetA6002() {
        // == A6002
        // Méthode par la quantité de m³ rejetés
        a6002 = questionSetService.findByCode(QuestionCode.A6002);
        if( a6002 == null ) {
            a6002 = new QuestionSet(QuestionCode.A6002, false, a6001);
            JPA.em().persist(a6002);
        }
    }
    private void createQuestionSetA6006() {
        // == A6006
        // Méthode par le poids de CO2 chimique des effluents rejetés
        a6006 = questionSetService.findByCode(QuestionCode.A6006);
        if( a6006 == null ) {
            a6006 = new QuestionSet(QuestionCode.A6006, false, a6001);
            JPA.em().persist(a6006);
        }
    }
    private void createQuestionSetA50() {
        // == A50
        // Mobilité
        a50 = questionSetService.findByCode(QuestionCode.A50);
        if( a50 == null ) {
            a50 = new QuestionSet(QuestionCode.A50, false, null);
            JPA.em().persist(a50);
        }
        form3.getQuestionSets().add(a50);
        JPA.em().persist(form3);
    }
    private void createQuestionSetA52() {
        // == A52
        // Transport routier
        a52 = questionSetService.findByCode(QuestionCode.A52);
        if( a52 == null ) {
            a52 = new QuestionSet(QuestionCode.A52, false, null);
            JPA.em().persist(a52);
        }
        form3.getQuestionSets().add(a52);
        JPA.em().persist(form3);
    }
    private void createQuestionSetA400() {
        // == A400
        // Véhicules de société ou détenus par l'entreprise
        a400 = questionSetService.findByCode(QuestionCode.A400);
        if( a400 == null ) {
            a400 = new QuestionSet(QuestionCode.A400, false, a52);
            JPA.em().persist(a400);
        }
    }
    private void createQuestionSetA401() {
        // == A401
        // Méthode au choix
        a401 = questionSetService.findByCode(QuestionCode.A401);
        if( a401 == null ) {
            a401 = new QuestionSet(QuestionCode.A401, false, a400);
            JPA.em().persist(a401);
        }
    }
    private void createQuestionSetA402() {
        // == A402
        // Méthode basée sur les consommations 
        a402 = questionSetService.findByCode(QuestionCode.A402);
        if( a402 == null ) {
            a402 = new QuestionSet(QuestionCode.A402, false, a401);
            JPA.em().persist(a402);
        }
    }
    private void createQuestionSetA406() {
        // == A406
        // Méthode basée sur le kilométrage
        a406 = questionSetService.findByCode(QuestionCode.A406);
        if( a406 == null ) {
            a406 = new QuestionSet(QuestionCode.A406, false, a401);
            JPA.em().persist(a406);
        }
    }
    private void createQuestionSetA407() {
        // == A407
        // Créez autant de catégories de véhicules que souhaité
        a407 = questionSetService.findByCode(QuestionCode.A407);
        if( a407 == null ) {
            a407 = new QuestionSet(QuestionCode.A407, true, a406);
            JPA.em().persist(a407);
        }
    }
    private void createQuestionSetA412() {
        // == A412
        // Méthode basée sur les dépenses
        a412 = questionSetService.findByCode(QuestionCode.A412);
        if( a412 == null ) {
            a412 = new QuestionSet(QuestionCode.A412, false, a401);
            JPA.em().persist(a412);
        }
    }
    private void createQuestionSetA413() {
        // == A413
        // Créez autant de catégories de véhicules que souhaité
        a413 = questionSetService.findByCode(QuestionCode.A413);
        if( a413 == null ) {
            a413 = new QuestionSet(QuestionCode.A413, true, a412);
            JPA.em().persist(a413);
        }
    }
    private void createQuestionSetA518() {
        // == A518
        // Autres véhicules pour déplacements domicile-travail des employés
        a518 = questionSetService.findByCode(QuestionCode.A518);
        if( a518 == null ) {
            a518 = new QuestionSet(QuestionCode.A518, false, a52);
            JPA.em().persist(a518);
        }
    }
    private void createQuestionSetA519() {
        // == A519
        // Méthode au choix
        a519 = questionSetService.findByCode(QuestionCode.A519);
        if( a519 == null ) {
            a519 = new QuestionSet(QuestionCode.A519, false, a518);
            JPA.em().persist(a519);
        }
    }
    private void createQuestionSetA502() {
        // == A502
        // Méthode basée sur les consommations
        a502 = questionSetService.findByCode(QuestionCode.A502);
        if( a502 == null ) {
            a502 = new QuestionSet(QuestionCode.A502, false, a519);
            JPA.em().persist(a502);
        }
    }
    private void createQuestionSetA506() {
        // == A506
        // Méthode basée sur le kilométrage
        a506 = questionSetService.findByCode(QuestionCode.A506);
        if( a506 == null ) {
            a506 = new QuestionSet(QuestionCode.A506, false, a519);
            JPA.em().persist(a506);
        }
    }
    private void createQuestionSetA507() {
        // == A507
        // Créez autant de catégories de véhicules que souhaité
        a507 = questionSetService.findByCode(QuestionCode.A507);
        if( a507 == null ) {
            a507 = new QuestionSet(QuestionCode.A507, true, a506);
            JPA.em().persist(a507);
        }
    }
    private void createQuestionSetA512() {
        // == A512
        // Méthode basée sur les dépenses
        a512 = questionSetService.findByCode(QuestionCode.A512);
        if( a512 == null ) {
            a512 = new QuestionSet(QuestionCode.A512, false, a519);
            JPA.em().persist(a512);
        }
    }
    private void createQuestionSetA513() {
        // == A513
        // Créez autant de catégories de véhicules que souhaité
        a513 = questionSetService.findByCode(QuestionCode.A513);
        if( a513 == null ) {
            a513 = new QuestionSet(QuestionCode.A513, true, a512);
            JPA.em().persist(a513);
        }
    }
    private void createQuestionSetA600() {
        // == A600
        // Autres véhicules pour déplacements divers (véhicules loués, visiteurs, consultants, sous-traitants…)
        a600 = questionSetService.findByCode(QuestionCode.A600);
        if( a600 == null ) {
            a600 = new QuestionSet(QuestionCode.A600, false, a52);
            JPA.em().persist(a600);
        }
    }
    private void createQuestionSetA601() {
        // == A601
        // Méthode au choix
        a601 = questionSetService.findByCode(QuestionCode.A601);
        if( a601 == null ) {
            a601 = new QuestionSet(QuestionCode.A601, false, a600);
            JPA.em().persist(a601);
        }
    }
    private void createQuestionSetA602() {
        // == A602
        // Méthode basée sur les consommations
        a602 = questionSetService.findByCode(QuestionCode.A602);
        if( a602 == null ) {
            a602 = new QuestionSet(QuestionCode.A602, false, a601);
            JPA.em().persist(a602);
        }
    }
    private void createQuestionSetA606() {
        // == A606
        // Méthode basée sur le kilométrage
        a606 = questionSetService.findByCode(QuestionCode.A606);
        if( a606 == null ) {
            a606 = new QuestionSet(QuestionCode.A606, false, a601);
            JPA.em().persist(a606);
        }
    }
    private void createQuestionSetA607() {
        // == A607
        // Créez autant de catégories de véhicules que souhaité
        a607 = questionSetService.findByCode(QuestionCode.A607);
        if( a607 == null ) {
            a607 = new QuestionSet(QuestionCode.A607, true, a606);
            JPA.em().persist(a607);
        }
    }
    private void createQuestionSetA612() {
        // == A612
        // Méthode basée sur les dépenses
        a612 = questionSetService.findByCode(QuestionCode.A612);
        if( a612 == null ) {
            a612 = new QuestionSet(QuestionCode.A612, false, a601);
            JPA.em().persist(a612);
        }
    }
    private void createQuestionSetA613() {
        // == A613
        // Créez autant de catégories de véhicules que souhaité
        a613 = questionSetService.findByCode(QuestionCode.A613);
        if( a613 == null ) {
            a613 = new QuestionSet(QuestionCode.A613, true, a612);
            JPA.em().persist(a613);
        }
    }
    private void createQuestionSetA93() {
        // == A93
        // Transport en commun
        a93 = questionSetService.findByCode(QuestionCode.A93);
        if( a93 == null ) {
            a93 = new QuestionSet(QuestionCode.A93, false, null);
            JPA.em().persist(a93);
        }
        form3.getQuestionSets().add(a93);
        JPA.em().persist(form3);
    }
    private void createQuestionSetA94() {
        // == A94
        // Estimation par le détail des déplacements
        a94 = questionSetService.findByCode(QuestionCode.A94);
        if( a94 == null ) {
            a94 = new QuestionSet(QuestionCode.A94, false, a93);
            JPA.em().persist(a94);
        }
    }
    private void createQuestionSetA113() {
        // == A113
        // Transport en avion (déplacements professionnels ou des visiteurs)
        a113 = questionSetService.findByCode(QuestionCode.A113);
        if( a113 == null ) {
            a113 = new QuestionSet(QuestionCode.A113, false, null);
            JPA.em().persist(a113);
        }
        form3.getQuestionSets().add(a113);
        JPA.em().persist(form3);
    }
    private void createQuestionSetA114() {
        // == A114
        // Méthode par le détail des vols
        a114 = questionSetService.findByCode(QuestionCode.A114);
        if( a114 == null ) {
            a114 = new QuestionSet(QuestionCode.A114, false, a113);
            JPA.em().persist(a114);
        }
    }
    private void createQuestionSetA115() {
        // == A115
        // Créez autant de catégories de vol que nécessaire
        a115 = questionSetService.findByCode(QuestionCode.A115);
        if( a115 == null ) {
            a115 = new QuestionSet(QuestionCode.A115, true, a114);
            JPA.em().persist(a115);
        }
    }
    private void createQuestionSetA121() {
        // == A121
        // Méthode des moyennes
        a121 = questionSetService.findByCode(QuestionCode.A121);
        if( a121 == null ) {
            a121 = new QuestionSet(QuestionCode.A121, false, a113);
            JPA.em().persist(a121);
        }
    }
    private void createQuestionSetA205() {
        // == A205
        // Achat de biens et services
        a205 = questionSetService.findByCode(QuestionCode.A205);
        if( a205 == null ) {
            a205 = new QuestionSet(QuestionCode.A205, false, null);
            JPA.em().persist(a205);
        }
        form4.getQuestionSets().add(a205);
        JPA.em().persist(form4);
    }
    private void createQuestionSetA208() {
        // == A208
        // Achat de biens et services
        a208 = questionSetService.findByCode(QuestionCode.A208);
        if( a208 == null ) {
            a208 = new QuestionSet(QuestionCode.A208, false, null);
            JPA.em().persist(a208);
        }
        form4.getQuestionSets().add(a208);
        JPA.em().persist(form4);
    }
    private void createQuestionSetA209() {
        // == A209
        // Créez et nommez vos postes d'achats (et préciser la famille et le type de matériaux ensuite)
        a209 = questionSetService.findByCode(QuestionCode.A209);
        if( a209 == null ) {
            a209 = new QuestionSet(QuestionCode.A209, true, a208);
            JPA.em().persist(a209);
        }
    }
    private void createQuestionSetA223() {
        // == A223
        // Autres matériaux spécifiques pour lesquels l'entreprise dispose du facteur d'émissions cradle-to-gate
        a223 = questionSetService.findByCode(QuestionCode.A223);
        if( a223 == null ) {
            a223 = new QuestionSet(QuestionCode.A223, false, a208);
            JPA.em().persist(a223);
        }
    }
    private void createQuestionSetA224() {
        // == A224
        // Créez et nommez vos postes d'achats spécifiques (et précisez ensuite la famille, le type de matériaux et le facteur d'émission cradle-to-gate spécifique)
        a224 = questionSetService.findByCode(QuestionCode.A224);
        if( a224 == null ) {
            a224 = new QuestionSet(QuestionCode.A224, true, a223);
            JPA.em().persist(a224);
        }
    }
    private void createQuestionSetA128() {
        // == A128
        // Transport et distribution de marchandises amont
        a128 = questionSetService.findByCode(QuestionCode.A128);
        if( a128 == null ) {
            a128 = new QuestionSet(QuestionCode.A128, false, null);
            JPA.em().persist(a128);
        }
        form4.getQuestionSets().add(a128);
        JPA.em().persist(form4);
    }
    private void createQuestionSetA130() {
        // == A130
        // Transport amont
        a130 = questionSetService.findByCode(QuestionCode.A130);
        if( a130 == null ) {
            a130 = new QuestionSet(QuestionCode.A130, false, null);
            JPA.em().persist(a130);
        }
        form4.getQuestionSets().add(a130);
        JPA.em().persist(form4);
    }
    private void createQuestionSetA131() {
        // == A131
        // Transport avec des véhicules détenus par l'entreprise
        a131 = questionSetService.findByCode(QuestionCode.A131);
        if( a131 == null ) {
            a131 = new QuestionSet(QuestionCode.A131, false, a130);
            JPA.em().persist(a131);
        }
    }
    private void createQuestionSetA132() {
        // == A132
        // Méthode par consommation de carburants
        a132 = questionSetService.findByCode(QuestionCode.A132);
        if( a132 == null ) {
            a132 = new QuestionSet(QuestionCode.A132, false, a131);
            JPA.em().persist(a132);
        }
    }
    private void createQuestionSetA140() {
        // == A140
        // Transport effectué par des transporteurs
        a140 = questionSetService.findByCode(QuestionCode.A140);
        if( a140 == null ) {
            a140 = new QuestionSet(QuestionCode.A140, false, a130);
            JPA.em().persist(a140);
        }
    }
    private void createQuestionSetA141() {
        // == A141
        // Méthode basée sur le kilométrage
        a141 = questionSetService.findByCode(QuestionCode.A141);
        if( a141 == null ) {
            a141 = new QuestionSet(QuestionCode.A141, false, a140);
            JPA.em().persist(a141);
        }
    }
    private void createQuestionSetA142() {
        // == A142
        // Créez autant de schémas modaux que nécessaire
        a142 = questionSetService.findByCode(QuestionCode.A142);
        if( a142 == null ) {
            a142 = new QuestionSet(QuestionCode.A142, true, a141);
            JPA.em().persist(a142);
        }
    }
    private void createQuestionSetA157() {
        // == A157
        // Méthode des moyennes
        a157 = questionSetService.findByCode(QuestionCode.A157);
        if( a157 == null ) {
            a157 = new QuestionSet(QuestionCode.A157, false, a140);
            JPA.em().persist(a157);
        }
    }
    private void createQuestionSetA163() {
        // == A163
        // Distribution amont: Energie et froid
        a163 = questionSetService.findByCode(QuestionCode.A163);
        if( a163 == null ) {
            a163 = new QuestionSet(QuestionCode.A163, false, null);
            JPA.em().persist(a163);
        }
        form4.getQuestionSets().add(a163);
        JPA.em().persist(form4);
    }
    private void createQuestionSetA164() {
        // == A164
        // Créez autant de postes de consommation que nécessaire
        a164 = questionSetService.findByCode(QuestionCode.A164);
        if( a164 == null ) {
            a164 = new QuestionSet(QuestionCode.A164, true, a163);
            JPA.em().persist(a164);
        }
    }
    private void createQuestionSetA166() {
        // == A166
        // Listez les totaux de combustibles consommés (exprimés en unités d'énergie)
        a166 = questionSetService.findByCode(QuestionCode.A166);
        if( a166 == null ) {
            a166 = new QuestionSet(QuestionCode.A166, true, a164);
            JPA.em().persist(a166);
        }
    }
    private void createQuestionSetA1006() {
        // == A1006
        // Listez les totaux de combustibles consommés (exprimés en volume)
        a1006 = questionSetService.findByCode(QuestionCode.A1006);
        if( a1006 == null ) {
            a1006 = new QuestionSet(QuestionCode.A1006, true, a164);
            JPA.em().persist(a1006);
        }
    }
    private void createQuestionSetA1009() {
        // == A1009
        // Listez les totaux de combustibles consommés (exprimés en poids)
        a1009 = questionSetService.findByCode(QuestionCode.A1009);
        if( a1009 == null ) {
            a1009 = new QuestionSet(QuestionCode.A1009, true, a164);
            JPA.em().persist(a1009);
        }
    }
    private void createQuestionSetA170() {
        // == A170
        // Listez les gaz réfrigérants utilisés pour les marchandises amont
        a170 = questionSetService.findByCode(QuestionCode.A170);
        if( a170 == null ) {
            a170 = new QuestionSet(QuestionCode.A170, true, a164);
            JPA.em().persist(a170);
        }
    }
    private void createQuestionSetA173() {
        // == A173
        // Déchets générés par les opérations
        a173 = questionSetService.findByCode(QuestionCode.A173);
        if( a173 == null ) {
            a173 = new QuestionSet(QuestionCode.A173, false, null);
            JPA.em().persist(a173);
        }
        form5.getQuestionSets().add(a173);
        JPA.em().persist(form5);
    }
    private void createQuestionSetA4999() {
        // == A4999
        // Déchets solides
        a4999 = questionSetService.findByCode(QuestionCode.A4999);
        if( a4999 == null ) {
            a4999 = new QuestionSet(QuestionCode.A4999, false, null);
            JPA.em().persist(a4999);
        }
        form5.getQuestionSets().add(a4999);
        JPA.em().persist(form5);
    }
    private void createQuestionSetA5000() {
        // == A5000
        // Listez vos différents postes de déchets
        a5000 = questionSetService.findByCode(QuestionCode.A5000);
        if( a5000 == null ) {
            a5000 = new QuestionSet(QuestionCode.A5000, true, a4999);
            JPA.em().persist(a5000);
        }
    }
    private void createQuestionSetA180() {
        // == A180
        // Eaux usées
        a180 = questionSetService.findByCode(QuestionCode.A180);
        if( a180 == null ) {
            a180 = new QuestionSet(QuestionCode.A180, false, null);
            JPA.em().persist(a180);
        }
        form5.getQuestionSets().add(a180);
        JPA.em().persist(form5);
    }
    private void createQuestionSetA181() {
        // == A181
        // Eaux usées domestiques par grand type de bâtiments
        a181 = questionSetService.findByCode(QuestionCode.A181);
        if( a181 == null ) {
            a181 = new QuestionSet(QuestionCode.A181, false, a180);
            JPA.em().persist(a181);
        }
    }
    private void createQuestionSetA182() {
        // == A182
        // Usine ou atelier
        a182 = questionSetService.findByCode(QuestionCode.A182);
        if( a182 == null ) {
            a182 = new QuestionSet(QuestionCode.A182, false, a181);
            JPA.em().persist(a182);
        }
    }
    private void createQuestionSetA185() {
        // == A185
        // Bureau
        a185 = questionSetService.findByCode(QuestionCode.A185);
        if( a185 == null ) {
            a185 = new QuestionSet(QuestionCode.A185, false, a181);
            JPA.em().persist(a185);
        }
    }
    private void createQuestionSetA188() {
        // == A188
        // Hôtel, pension, hôpitaux, prison
        a188 = questionSetService.findByCode(QuestionCode.A188);
        if( a188 == null ) {
            a188 = new QuestionSet(QuestionCode.A188, false, a181);
            JPA.em().persist(a188);
        }
    }
    private void createQuestionSetA191() {
        // == A191
        // Restaurant ou cantine
        a191 = questionSetService.findByCode(QuestionCode.A191);
        if( a191 == null ) {
            a191 = new QuestionSet(QuestionCode.A191, false, a181);
            JPA.em().persist(a191);
        }
    }
    private void createQuestionSetA194() {
        // == A194
        // Eaux usées industrielles traitées par des tiers
        a194 = questionSetService.findByCode(QuestionCode.A194);
        if( a194 == null ) {
            a194 = new QuestionSet(QuestionCode.A194, false, a180);
            JPA.em().persist(a194);
        }
    }
    private void createQuestionSetA196() {
        // == A196
        // Méthodes alternatives
        a196 = questionSetService.findByCode(QuestionCode.A196);
        if( a196 == null ) {
            a196 = new QuestionSet(QuestionCode.A196, false, a194);
            JPA.em().persist(a196);
        }
    }
    private void createQuestionSetA197() {
        // == A197
        // Méthode par la quantité de m³ rejetés
        a197 = questionSetService.findByCode(QuestionCode.A197);
        if( a197 == null ) {
            a197 = new QuestionSet(QuestionCode.A197, false, a196);
            JPA.em().persist(a197);
        }
    }
    private void createQuestionSetA201() {
        // == A201
        // Méthode par le poids de CO2 chimique des effluents rejetés
        a201 = questionSetService.findByCode(QuestionCode.A201);
        if( a201 == null ) {
            a201 = new QuestionSet(QuestionCode.A201, false, a196);
            JPA.em().persist(a201);
        }
    }
    private void createQuestionSetA229() {
        // == A229
        // Biens d'équipement
        a229 = questionSetService.findByCode(QuestionCode.A229);
        if( a229 == null ) {
            a229 = new QuestionSet(QuestionCode.A229, false, null);
            JPA.em().persist(a229);
        }
        form6.getQuestionSets().add(a229);
        JPA.em().persist(form6);
    }
    private void createQuestionSetA231() {
        // == A231
        // Créez et nommez vos biens d'équipement
        a231 = questionSetService.findByCode(QuestionCode.A231);
        if( a231 == null ) {
            a231 = new QuestionSet(QuestionCode.A231, true, a229);
            JPA.em().persist(a231);
        }
    }
    private void createQuestionSetA237() {
        // == A237
        // Autres biens d'équipement spécifiques pour lesquels l'entreprise dispose du facteur d'émission cradle-to-gate
        a237 = questionSetService.findByCode(QuestionCode.A237);
        if( a237 == null ) {
            a237 = new QuestionSet(QuestionCode.A237, false, a229);
            JPA.em().persist(a237);
        }
    }
    private void createQuestionSetA238() {
        // == A238
        // Créez et nommez vos biens d'équipement
        a238 = questionSetService.findByCode(QuestionCode.A238);
        if( a238 == null ) {
            a238 = new QuestionSet(QuestionCode.A238, true, a237);
            JPA.em().persist(a238);
        }
    }
    private void createQuestionSetA309() {
        // == A309
        // Actifs loués (aval)
        a309 = questionSetService.findByCode(QuestionCode.A309);
        if( a309 == null ) {
            a309 = new QuestionSet(QuestionCode.A309, false, null);
            JPA.em().persist(a309);
        }
        form6.getQuestionSets().add(a309);
        JPA.em().persist(form6);
    }
    private void createQuestionSetA311() {
        // == A311
        // Créez autant de catégories d'actifs loués que nécessaire
        a311 = questionSetService.findByCode(QuestionCode.A311);
        if( a311 == null ) {
            a311 = new QuestionSet(QuestionCode.A311, true, a309);
            JPA.em().persist(a311);
        }
    }
    private void createQuestionSetA313() {
        // == A313
        // Listez les totaux de combustibles utilisés pour les actifs loués (exprimés en unités d'énergie)
        a313 = questionSetService.findByCode(QuestionCode.A313);
        if( a313 == null ) {
            a313 = new QuestionSet(QuestionCode.A313, true, a311);
            JPA.em().persist(a313);
        }
    }
    private void createQuestionSetA1012() {
        // == A1012
        // Listez les totaux de combustibles utilisés pour les actifs loués (exprimés en volume)
        a1012 = questionSetService.findByCode(QuestionCode.A1012);
        if( a1012 == null ) {
            a1012 = new QuestionSet(QuestionCode.A1012, true, a311);
            JPA.em().persist(a1012);
        }
    }
    private void createQuestionSetA1015() {
        // == A1015
        // Listez les totaux de combustibles utilisés pour les actifs loués (exprimés en poids)
        a1015 = questionSetService.findByCode(QuestionCode.A1015);
        if( a1015 == null ) {
            a1015 = new QuestionSet(QuestionCode.A1015, true, a311);
            JPA.em().persist(a1015);
        }
    }
    private void createQuestionSetA317() {
        // == A317
        // Listez les gaz réfrigérants et autres nécessaires à l'opération des actifs loués
        a317 = questionSetService.findByCode(QuestionCode.A317);
        if( a317 == null ) {
            a317 = new QuestionSet(QuestionCode.A317, true, a311);
            JPA.em().persist(a317);
        }
    }
    private void createQuestionSetA320() {
        // == A320
        // Franchises
        a320 = questionSetService.findByCode(QuestionCode.A320);
        if( a320 == null ) {
            a320 = new QuestionSet(QuestionCode.A320, false, null);
            JPA.em().persist(a320);
        }
        form6.getQuestionSets().add(a320);
        JPA.em().persist(form6);
    }
    private void createQuestionSetA322() {
        // == A322
        // Créez autant de catégories de franchisés que nécessaire
        a322 = questionSetService.findByCode(QuestionCode.A322);
        if( a322 == null ) {
            a322 = new QuestionSet(QuestionCode.A322, true, a320);
            JPA.em().persist(a322);
        }
    }
    private void createQuestionSetA325() {
        // == A325
        // Listez les moyennes de combustibles utilisés par franchisé (exprimés en unités d'énergie)
        a325 = questionSetService.findByCode(QuestionCode.A325);
        if( a325 == null ) {
            a325 = new QuestionSet(QuestionCode.A325, true, a322);
            JPA.em().persist(a325);
        }
    }
    private void createQuestionSetA1018() {
        // == A1018
        // Listez les moyennes de combustibles utilisés par franchisé (exprimés en volume)
        a1018 = questionSetService.findByCode(QuestionCode.A1018);
        if( a1018 == null ) {
            a1018 = new QuestionSet(QuestionCode.A1018, true, a322);
            JPA.em().persist(a1018);
        }
    }
    private void createQuestionSetA1021() {
        // == A1021
        // Listez les moyennes de combustibles utilisés par franchisé (exprimés en poids)
        a1021 = questionSetService.findByCode(QuestionCode.A1021);
        if( a1021 == null ) {
            a1021 = new QuestionSet(QuestionCode.A1021, true, a322);
            JPA.em().persist(a1021);
        }
    }
    private void createQuestionSetA329() {
        // == A329
        // Listez les gaz réfrigérants et autres utilisés en moyenne par franchisé
        a329 = questionSetService.findByCode(QuestionCode.A329);
        if( a329 == null ) {
            a329 = new QuestionSet(QuestionCode.A329, true, a322);
            JPA.em().persist(a329);
        }
    }
    private void createQuestionSetA332() {
        // == A332
        // Investissements financiers
        a332 = questionSetService.findByCode(QuestionCode.A332);
        if( a332 == null ) {
            a332 = new QuestionSet(QuestionCode.A332, false, null);
            JPA.em().persist(a332);
        }
        form6.getQuestionSets().add(a332);
        JPA.em().persist(form6);
    }
    private void createQuestionSetA334() {
        // == A334
        // Veuillez indiquer ici tous les projets dans lesquels votre entreprise investit
        a334 = questionSetService.findByCode(QuestionCode.A334);
        if( a334 == null ) {
            a334 = new QuestionSet(QuestionCode.A334, true, a332);
            JPA.em().persist(a334);
        }
    }
    private void createQuestionSetA243() {
        // == A243
        // Transport & Distribution, Traitement, Utilisation et Fin de vie des produits vendus
        a243 = questionSetService.findByCode(QuestionCode.A243);
        if( a243 == null ) {
            a243 = new QuestionSet(QuestionCode.A243, false, null);
            JPA.em().persist(a243);
        }
        form7.getQuestionSets().add(a243);
        JPA.em().persist(form7);
    }
    private void createQuestionSetA244() {
        // == A244
        // Lister les différents produits ou groupes de produits vendus par l'entreprise
        a244 = questionSetService.findByCode(QuestionCode.A244);
        if( a244 == null ) {
            a244 = new QuestionSet(QuestionCode.A244, true, a243);
            JPA.em().persist(a244);
        }
    }
    private void createQuestionSetA8000() {
        // == A8000
        // Utilisation, traitement et fin de vie
        a8000 = questionSetService.findByCode(QuestionCode.A8000);
        if( a8000 == null ) {
            a8000 = new QuestionSet(QuestionCode.A8000, false, a244);
            JPA.em().persist(a8000);
        }
    }
    private void createQuestionSetA250() {
        // == A250
        // Transport et distribution aval
        a250 = questionSetService.findByCode(QuestionCode.A250);
        if( a250 == null ) {
            a250 = new QuestionSet(QuestionCode.A250, false, a244);
            JPA.em().persist(a250);
        }
    }
    private void createQuestionSetA252() {
        // == A252
        // Transport aval
        a252 = questionSetService.findByCode(QuestionCode.A252);
        if( a252 == null ) {
            a252 = new QuestionSet(QuestionCode.A252, false, a250);
            JPA.em().persist(a252);
        }
    }
    private void createQuestionSetA253() {
        // == A253
        // Méthode par kilométrage
        a253 = questionSetService.findByCode(QuestionCode.A253);
        if( a253 == null ) {
            a253 = new QuestionSet(QuestionCode.A253, false, a252);
            JPA.em().persist(a253);
        }
    }
    private void createQuestionSetA266() {
        // == A266
        // Méthode des moyennes
        a266 = questionSetService.findByCode(QuestionCode.A266);
        if( a266 == null ) {
            a266 = new QuestionSet(QuestionCode.A266, false, a252);
            JPA.em().persist(a266);
        }
    }
    private void createQuestionSetA272() {
        // == A272
        // Distribution avale: Energie et Froid
        a272 = questionSetService.findByCode(QuestionCode.A272);
        if( a272 == null ) {
            a272 = new QuestionSet(QuestionCode.A272, false, a244);
            JPA.em().persist(a272);
        }
    }
    private void createQuestionSetA273() {
        // == A273
        // Créez autant de postes de consommation que nécessaire
        a273 = questionSetService.findByCode(QuestionCode.A273);
        if( a273 == null ) {
            a273 = new QuestionSet(QuestionCode.A273, true, a272);
            JPA.em().persist(a273);
        }
    }
    private void createQuestionSetA275() {
        // == A275
        // Listez les totaux de combustibles utilisés (exprimés en unités d'énergie)
        a275 = questionSetService.findByCode(QuestionCode.A275);
        if( a275 == null ) {
            a275 = new QuestionSet(QuestionCode.A275, true, a273);
            JPA.em().persist(a275);
        }
    }
    private void createQuestionSetA1024() {
        // == A1024
        // Listez les totaux de combustibles utilisés (exprimés en volume)
        a1024 = questionSetService.findByCode(QuestionCode.A1024);
        if( a1024 == null ) {
            a1024 = new QuestionSet(QuestionCode.A1024, true, a273);
            JPA.em().persist(a1024);
        }
    }
    private void createQuestionSetA1027() {
        // == A1027
        // Listez les totaux de combustibles utilisés (exprimés en poids)
        a1027 = questionSetService.findByCode(QuestionCode.A1027);
        if( a1027 == null ) {
            a1027 = new QuestionSet(QuestionCode.A1027, true, a273);
            JPA.em().persist(a1027);
        }
    }
    private void createQuestionSetA279() {
        // == A279
        // Listez les gaz réfrigérants
        a279 = questionSetService.findByCode(QuestionCode.A279);
        if( a279 == null ) {
            a279 = new QuestionSet(QuestionCode.A279, true, a273);
            JPA.em().persist(a279);
        }
    }
    private void createQuestionSetA282() {
        // == A282
        // Traitement
        a282 = questionSetService.findByCode(QuestionCode.A282);
        if( a282 == null ) {
            a282 = new QuestionSet(QuestionCode.A282, false, a244);
            JPA.em().persist(a282);
        }
    }
    private void createQuestionSetA284() {
        // == A284
        // Listez les totaux de combustibles (exprimés en unités d'énergie)
        a284 = questionSetService.findByCode(QuestionCode.A284);
        if( a284 == null ) {
            a284 = new QuestionSet(QuestionCode.A284, true, a282);
            JPA.em().persist(a284);
        }
    }
    private void createQuestionSetA1030() {
        // == A1030
        // Listez les totaux de combustibles (exprimés en volume)
        a1030 = questionSetService.findByCode(QuestionCode.A1030);
        if( a1030 == null ) {
            a1030 = new QuestionSet(QuestionCode.A1030, true, a282);
            JPA.em().persist(a1030);
        }
    }
    private void createQuestionSetA1033() {
        // == A1033
        // Listez les totaux de combustibles (exprimés en poids)
        a1033 = questionSetService.findByCode(QuestionCode.A1033);
        if( a1033 == null ) {
            a1033 = new QuestionSet(QuestionCode.A1033, true, a282);
            JPA.em().persist(a1033);
        }
    }
    private void createQuestionSetA288() {
        // == A288
        // Listez les gaz réfrigérants
        a288 = questionSetService.findByCode(QuestionCode.A288);
        if( a288 == null ) {
            a288 = new QuestionSet(QuestionCode.A288, true, a282);
            JPA.em().persist(a288);
        }
    }
    private void createQuestionSetA291() {
        // == A291
        // Utilisation
        a291 = questionSetService.findByCode(QuestionCode.A291);
        if( a291 == null ) {
            a291 = new QuestionSet(QuestionCode.A291, false, a244);
            JPA.em().persist(a291);
        }
    }
    private void createQuestionSetA297() {
        // == A297
        // Listez les gaz émis par utilisation de produit
        a297 = questionSetService.findByCode(QuestionCode.A297);
        if( a297 == null ) {
            a297 = new QuestionSet(QuestionCode.A297, true, a291);
            JPA.em().persist(a297);
        }
    }
    private void createQuestionSetA300() {
        // == A300
        // Traitement de fin de vie
        a300 = questionSetService.findByCode(QuestionCode.A300);
        if( a300 == null ) {
            a300 = new QuestionSet(QuestionCode.A300, false, a244);
            JPA.em().persist(a300);
        }
    }
    private void createQuestionSetA5010() {
        // == A5010
        // Créez autant de postes de déchet que nécessaire
        a5010 = questionSetService.findByCode(QuestionCode.A5010);
        if( a5010 == null ) {
            a5010 = new QuestionSet(QuestionCode.A5010, true, a300);
            JPA.em().persist(a5010);
        }
    }


    // =========================================================================
    // QUESTIONS
    // =========================================================================

    private void createQuestionA2() {
        // == A2
        // Année de référence pour comparaison du présent bilan GES

        a2 = (IntegerQuestion) questionService.findByCode(QuestionCode.A2);
if (a2 == null) {
    a2 = new IntegerQuestion(a1, 0, QuestionCode.A2, null);
    JPA.em().persist(a2);

    // cleanup the driver
    Driver a2_driver = driverService.findByName("A2");
    if (a2_driver != null) {
        driverService.remove(a2_driver);
    }

} else {
    if (!a2.getQuestionSet().equals(a1) && a1.getQuestions().contains(a2)) {
        a1.getQuestions().remove(a2);
        JPA.em().persist(a1);
    }
    if (a2.getQuestionSet().equals(a1) && !a1.getQuestions().contains(a2)) {
        a1.getQuestions().add(a2);
        JPA.em().persist(a1);
    }
    a2.setOrderIndex(0);
    ((NumericQuestion)a2).setUnitCategory(null);

    // cleanup the driver
    Driver a2_driver = driverService.findByName("A2");
    if (a2_driver != null) {
        driverService.remove(a2_driver);
    }

    ((NumericQuestion)a2).setDriver(null);

    JPA.em().persist(a2);
}

    }
    private void createQuestionA3() {
        // == A3
        // A quel secteur principal appartient votre site?

        a3 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A3);
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
    ((ValueSelectionQuestion)a3).setCodeList(CodeList.SECTEURPRINCIPAL);
    JPA.em().persist(a3);
}

    }
    private void createQuestionA4() {
        // == A4
        // Quel est le code NACE principal de votre site?

        a4 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A4);
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
    ((ValueSelectionQuestion)a4).setCodeList(CodeList.SECTEURPRIMAIRE);
    JPA.em().persist(a4);
}

    }
    private void createQuestionA5() {
        // == A5
        // Quel est le code NACE principal de votre site?

        a5 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A5);
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
    ((ValueSelectionQuestion)a5).setCodeList(CodeList.SECTEURSECONDAIRE);
    JPA.em().persist(a5);
}

    }
    private void createQuestionA6() {
        // == A6
        // Quel est le code NACE principal de votre site?

        a6 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A6);
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
    ((ValueSelectionQuestion)a6).setCodeList(CodeList.SECTEURTERTIAIRE);
    JPA.em().persist(a6);
}

    }
    private void createQuestionA9() {
        // == A9
        // Indiquez la surface totale du site:

        

a9 = (DoubleQuestion) questionService.findByCode(QuestionCode.A9);
if (a9 == null) {
    a9 = new DoubleQuestion( a1, 0, QuestionCode.A9, areaUnits, areaUnits.getMainUnit() );
    JPA.em().persist(a9);

    // cleanup the driver
    Driver a9_driver = driverService.findByName("A9");
    if (a9_driver != null) {
        driverService.remove(a9_driver);
    }


} else {
    if (!a9.getQuestionSet().equals(a1) && a1.getQuestions().contains(a9)) {
        a1.getQuestions().remove(a9);
        JPA.em().persist(a1);
    }
    if (a9.getQuestionSet().equals(a1) && !a1.getQuestions().contains(a9)) {
        a1.getQuestions().add(a9);
        JPA.em().persist(a1);
    }
    ((NumericQuestion)a9).setUnitCategory(areaUnits);
    a9.setOrderIndex(0);
    ((NumericQuestion)a9).setDefaultUnit(areaUnits.getMainUnit());

    // cleanup the driver
    Driver a9_driver = driverService.findByName("A9");
    if (a9_driver != null) {
        driverService.remove(a9_driver);
    }

    ((NumericQuestion)a9).setDriver(null);

    JPA.em().persist(a9);
}



    }
    private void createQuestionA10() {
        // == A10
        // Quelle est la surface des bureaux?

        

a10 = (DoubleQuestion) questionService.findByCode(QuestionCode.A10);
if (a10 == null) {
    a10 = new DoubleQuestion( a1, 0, QuestionCode.A10, areaUnits, areaUnits.getMainUnit() );
    JPA.em().persist(a10);

    // cleanup the driver
    Driver a10_driver = driverService.findByName("A10");
    if (a10_driver != null) {
        driverService.remove(a10_driver);
    }


} else {
    if (!a10.getQuestionSet().equals(a1) && a1.getQuestions().contains(a10)) {
        a1.getQuestions().remove(a10);
        JPA.em().persist(a1);
    }
    if (a10.getQuestionSet().equals(a1) && !a1.getQuestions().contains(a10)) {
        a1.getQuestions().add(a10);
        JPA.em().persist(a1);
    }
    ((NumericQuestion)a10).setUnitCategory(areaUnits);
    a10.setOrderIndex(0);
    ((NumericQuestion)a10).setDefaultUnit(areaUnits.getMainUnit());

    // cleanup the driver
    Driver a10_driver = driverService.findByName("A10");
    if (a10_driver != null) {
        driverService.remove(a10_driver);
    }

    ((NumericQuestion)a10).setDriver(null);

    JPA.em().persist(a10);
}



    }
    private void createQuestionA11() {
        // == A11
        // Etes-vous participant aux accords de branche de 2ème génération?

        a11 = (BooleanQuestion) questionService.findByCode(QuestionCode.A11);
if (a11 == null) {
    a11 = new BooleanQuestion(a1, 0, QuestionCode.A11, null);
    JPA.em().persist(a11);
} else {
    ((BooleanQuestion)a11).setDefaultValue(null);
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

    }
    private void createQuestionA12() {
        // == A12
        // Quel est le nombre d'employés sur l'année du bilan?

        a12 = (IntegerQuestion) questionService.findByCode(QuestionCode.A12);
if (a12 == null) {
    a12 = new IntegerQuestion(a1, 0, QuestionCode.A12, null);
    JPA.em().persist(a12);

    // cleanup the driver
    Driver a12_driver = driverService.findByName("A12");
    if (a12_driver != null) {
        driverService.remove(a12_driver);
    }

} else {
    if (!a12.getQuestionSet().equals(a1) && a1.getQuestions().contains(a12)) {
        a1.getQuestions().remove(a12);
        JPA.em().persist(a1);
    }
    if (a12.getQuestionSet().equals(a1) && !a1.getQuestions().contains(a12)) {
        a1.getQuestions().add(a12);
        JPA.em().persist(a1);
    }
    a12.setOrderIndex(0);
    ((NumericQuestion)a12).setUnitCategory(null);

    // cleanup the driver
    Driver a12_driver = driverService.findByName("A12");
    if (a12_driver != null) {
        driverService.remove(a12_driver);
    }

    ((NumericQuestion)a12).setDriver(null);

    JPA.em().persist(a12);
}

    }
    private void createQuestionA14() {
        // == A14
        // Pièces documentaires liées aux consommations de combustible

        a14 = (DocumentQuestion) questionService.findByCode(QuestionCode.A14);
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

    }
    private void createQuestionA16() {
        // == A16
        // Combustible

        a16 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A16);
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
    ((ValueSelectionQuestion)a16).setCodeList(CodeList.COMBUSTIBLE);
    JPA.em().persist(a16);
}

    }
    private void createQuestionA17() {
        // == A17
        // Quantité

        
a17 = (DoubleQuestion) questionService.findByCode(QuestionCode.A17);
if (a17 == null) {
    a17 = new DoubleQuestion( a15, 0, QuestionCode.A17, energyUnits, getUnitBySymbol("kW.h") );
    JPA.em().persist(a17);

    // cleanup the driver
    Driver a17_driver = driverService.findByName("A17");
    if (a17_driver != null) {
        driverService.remove(a17_driver);
    }


} else {
    if (!a17.getQuestionSet().equals(a15) && a15.getQuestions().contains(a17)) {
        a15.getQuestions().remove(a17);
        JPA.em().persist(a15);
    }
    if (a17.getQuestionSet().equals(a15) && !a15.getQuestions().contains(a17)) {
        a15.getQuestions().add(a17);
        JPA.em().persist(a15);
    }
    ((NumericQuestion)a17).setUnitCategory(energyUnits);
    a17.setOrderIndex(0);
    ((NumericQuestion)a17).setDefaultUnit(getUnitBySymbol("kW.h"));

    // cleanup the driver
    Driver a17_driver = driverService.findByName("A17");
    if (a17_driver != null) {
        driverService.remove(a17_driver);
    }

    ((NumericQuestion)a17).setDriver(null);

    JPA.em().persist(a17);
}



    }
    private void createQuestionA1001() {
        // == A1001
        // Combustible

        a1001 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A1001);
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
    ((ValueSelectionQuestion)a1001).setCodeList(CodeList.COMBUSTIBLEVOLUME);
    JPA.em().persist(a1001);
}

    }
    private void createQuestionA1002() {
        // == A1002
        // Quantité

        
a1002 = (DoubleQuestion) questionService.findByCode(QuestionCode.A1002);
if (a1002 == null) {
    a1002 = new DoubleQuestion( a1000, 0, QuestionCode.A1002, volumeUnits, volumeUnits.getMainUnit() );
    JPA.em().persist(a1002);

    // cleanup the driver
    Driver a1002_driver = driverService.findByName("A1002");
    if (a1002_driver != null) {
        driverService.remove(a1002_driver);
    }

} else {
    if (!a1002.getQuestionSet().equals(a1000) && a1000.getQuestions().contains(a1002)) {
        a1000.getQuestions().remove(a1002);
        JPA.em().persist(a1000);
    }
    if (a1002.getQuestionSet().equals(a1000) && !a1000.getQuestions().contains(a1002)) {
        a1000.getQuestions().add(a1002);
        JPA.em().persist(a1000);
    }
    ((NumericQuestion)a1002).setUnitCategory(volumeUnits);
    a1002.setOrderIndex(0);
    ((NumericQuestion)a1002).setDefaultUnit(volumeUnits.getMainUnit());

    // cleanup the driver
    Driver a1002_driver = driverService.findByName("A1002");
    if (a1002_driver != null) {
        driverService.remove(a1002_driver);
    }

    ((NumericQuestion)a1002).setDriver(null);

    JPA.em().persist(a1002);
}



    }
    private void createQuestionA1004() {
        // == A1004
        // Combustible

        a1004 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A1004);
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
    ((ValueSelectionQuestion)a1004).setCodeList(CodeList.COMBUSTIBLEPOIDS);
    JPA.em().persist(a1004);
}

    }
    private void createQuestionA1005() {
        // == A1005
        // Quantité

        
a1005 = (DoubleQuestion) questionService.findByCode(QuestionCode.A1005);
if (a1005 == null) {
    a1005 = new DoubleQuestion( a1003, 0, QuestionCode.A1005, massUnits, massUnits.getMainUnit() );
    JPA.em().persist(a1005);

    // cleanup the driver
    Driver a1005_driver = driverService.findByName("A1005");
    if (a1005_driver != null) {
        driverService.remove(a1005_driver);
    }

} else {
    if (!a1005.getQuestionSet().equals(a1003) && a1003.getQuestions().contains(a1005)) {
        a1003.getQuestions().remove(a1005);
        JPA.em().persist(a1003);
    }
    if (a1005.getQuestionSet().equals(a1003) && !a1003.getQuestions().contains(a1005)) {
        a1003.getQuestions().add(a1005);
        JPA.em().persist(a1003);
    }
    ((NumericQuestion)a1005).setUnitCategory(massUnits);
    a1005.setOrderIndex(0);
    ((NumericQuestion)a1005).setDefaultUnit(massUnits.getMainUnit());


    // cleanup the driver
    Driver a1005_driver = driverService.findByName("A1005");
    if (a1005_driver != null) {
        driverService.remove(a1005_driver);
    }

    ((NumericQuestion)a1005).setDriver(null);

    JPA.em().persist(a1005);
}



    }
    private void createQuestionA21() {
        // == A21
        // Pièces documentaires liées aux achats d'électricité et de vapeur

        a21 = (DocumentQuestion) questionService.findByCode(QuestionCode.A21);
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

    }
    private void createQuestionA23() {
        // == A23
        // Consommation d'électricité verte

        
a23 = (DoubleQuestion) questionService.findByCode(QuestionCode.A23);
if (a23 == null) {
    a23 = new DoubleQuestion( a22, 0, QuestionCode.A23, energyUnits, getUnitBySymbol("kW.h") );
    JPA.em().persist(a23);

    // cleanup the driver
    Driver a23_driver = driverService.findByName("A23");
    if (a23_driver != null) {
        driverService.remove(a23_driver);
    }


} else {
    if (!a23.getQuestionSet().equals(a22) && a22.getQuestions().contains(a23)) {
        a22.getQuestions().remove(a23);
        JPA.em().persist(a22);
    }
    if (a23.getQuestionSet().equals(a22) && !a22.getQuestions().contains(a23)) {
        a22.getQuestions().add(a23);
        JPA.em().persist(a22);
    }
    ((NumericQuestion)a23).setUnitCategory(energyUnits);
    a23.setOrderIndex(0);
    ((NumericQuestion)a23).setDefaultUnit(getUnitBySymbol("kW.h"));

    // cleanup the driver
    Driver a23_driver = driverService.findByName("A23");
    if (a23_driver != null) {
        driverService.remove(a23_driver);
    }

    ((NumericQuestion)a23).setDriver(null);

    JPA.em().persist(a23);
}



    }
    private void createQuestionA24() {
        // == A24
        // Consommation d'électricité grise

        
a24 = (DoubleQuestion) questionService.findByCode(QuestionCode.A24);
if (a24 == null) {
    a24 = new DoubleQuestion( a22, 0, QuestionCode.A24, energyUnits, getUnitBySymbol("kW.h") );
    JPA.em().persist(a24);

    // cleanup the driver
    Driver a24_driver = driverService.findByName("A24");
    if (a24_driver != null) {
        driverService.remove(a24_driver);
    }


} else {
    if (!a24.getQuestionSet().equals(a22) && a22.getQuestions().contains(a24)) {
        a22.getQuestions().remove(a24);
        JPA.em().persist(a22);
    }
    if (a24.getQuestionSet().equals(a22) && !a22.getQuestions().contains(a24)) {
        a22.getQuestions().add(a24);
        JPA.em().persist(a22);
    }
    ((NumericQuestion)a24).setUnitCategory(energyUnits);
    a24.setOrderIndex(0);
    ((NumericQuestion)a24).setDefaultUnit(getUnitBySymbol("kW.h"));

    // cleanup the driver
    Driver a24_driver = driverService.findByName("A24");
    if (a24_driver != null) {
        driverService.remove(a24_driver);
    }

    ((NumericQuestion)a24).setDriver(null);

    JPA.em().persist(a24);
}



    }
    private void createQuestionA26() {
        // == A26
        // Energie primaire utilisée pour produire la vapeur:

        a26 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A26);
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
    ((ValueSelectionQuestion)a26).setCodeList(CodeList.ENERGIEVAPEUR);
    JPA.em().persist(a26);
}

    }
    private void createQuestionA27() {
        // == A27
        // Efficacité de la chaudière

        a27 = (PercentageQuestion) questionService.findByCode(QuestionCode.A27);
if (a27 == null) {
    a27 = new PercentageQuestion(a25, 0, QuestionCode.A27);
    JPA.em().persist(a27);

    // cleanup the driver
    Driver a27_driver = driverService.findByName("A27");
    if (a27_driver != null) {
        driverService.remove(a27_driver);
    }

} else {
    if (!a27.getQuestionSet().equals(a25) && a25.getQuestions().contains(a27)) {
        a25.getQuestions().remove(a27);
        JPA.em().persist(a25);
    }
    if (a27.getQuestionSet().equals(a25) && !a25.getQuestions().contains(a27)) {
        a25.getQuestions().add(a27);
        JPA.em().persist(a25);
    }
    a27.setOrderIndex(0);

    // cleanup the driver
    Driver a27_driver = driverService.findByName("A27");
    if (a27_driver != null) {
        driverService.remove(a27_driver);
    }

    ((NumericQuestion)a27).setDriver(null);

    JPA.em().persist(a27);
}

    }
    private void createQuestionA28() {
        // == A28
        // Quantité achetée

        
a28 = (DoubleQuestion) questionService.findByCode(QuestionCode.A28);
if (a28 == null) {
    a28 = new DoubleQuestion( a25, 0, QuestionCode.A28, energyUnits, getUnitBySymbol("kW.h") );
    JPA.em().persist(a28);

    // cleanup the driver
    Driver a28_driver = driverService.findByName("A28");
    if (a28_driver != null) {
        driverService.remove(a28_driver);
    }


} else {
    if (!a28.getQuestionSet().equals(a25) && a25.getQuestions().contains(a28)) {
        a25.getQuestions().remove(a28);
        JPA.em().persist(a25);
    }
    if (a28.getQuestionSet().equals(a25) && !a25.getQuestions().contains(a28)) {
        a25.getQuestions().add(a28);
        JPA.em().persist(a25);
    }
    ((NumericQuestion)a28).setUnitCategory(energyUnits);
    a28.setOrderIndex(0);
    ((NumericQuestion)a28).setDefaultUnit(getUnitBySymbol("kW.h"));

    // cleanup the driver
    Driver a28_driver = driverService.findByName("A28");
    if (a28_driver != null) {
        driverService.remove(a28_driver);
    }

    ((NumericQuestion)a28).setDriver(null);

    JPA.em().persist(a28);
}



    }
    private void createQuestionA32() {
        // == A32
        // Est-ce que vos activités impliquent des procédés chimiques et physiques émetteurs directs de gaz à effet de serre ?

        a32 = (BooleanQuestion) questionService.findByCode(QuestionCode.A32);
if (a32 == null) {
    a32 = new BooleanQuestion(a31, 0, QuestionCode.A32, null);
    JPA.em().persist(a32);
} else {
    ((BooleanQuestion)a32).setDefaultValue(null);
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

    }
    private void createQuestionA33() {
        // == A33
        // Pièces documentaires liées aux GES des procédés de production

        a33 = (DocumentQuestion) questionService.findByCode(QuestionCode.A33);
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

    }
    private void createQuestionA35() {
        // == A35
        // Type de GES

        a35 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A35);
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
    ((ValueSelectionQuestion)a35).setCodeList(CodeList.GES);
    JPA.em().persist(a35);
}

    }
    private void createQuestionA36() {
        // == A36
        // Quantité

        
a36 = (DoubleQuestion) questionService.findByCode(QuestionCode.A36);
if (a36 == null) {
    a36 = new DoubleQuestion( a34, 0, QuestionCode.A36, massUnits, getUnitBySymbol("t") );
    JPA.em().persist(a36);

    // cleanup the driver
    Driver a36_driver = driverService.findByName("A36");
    if (a36_driver != null) {
        driverService.remove(a36_driver);
    }

} else {
    if (!a36.getQuestionSet().equals(a34) && a34.getQuestions().contains(a36)) {
        a34.getQuestions().remove(a36);
        JPA.em().persist(a34);
    }
    if (a36.getQuestionSet().equals(a34) && !a34.getQuestions().contains(a36)) {
        a34.getQuestions().add(a36);
        JPA.em().persist(a34);
    }
    ((NumericQuestion)a36).setUnitCategory(massUnits);
    a36.setOrderIndex(0);
    ((NumericQuestion)a36).setDefaultUnit(getUnitBySymbol("t"));


    // cleanup the driver
    Driver a36_driver = driverService.findByName("A36");
    if (a36_driver != null) {
        driverService.remove(a36_driver);
    }

    ((NumericQuestion)a36).setDriver(null);

    JPA.em().persist(a36);
}



    }
    private void createQuestionA38() {
        // == A38
        // Disposez-vous d’un système de froid nécessitant un apport ponctuel d’agent réfrigérant (p.e. les chillers, les climatiseurs à air et à eau glacée, les réfrigérateurs, bacs à surgelés, etc.)?

        a38 = (BooleanQuestion) questionService.findByCode(QuestionCode.A38);
if (a38 == null) {
    a38 = new BooleanQuestion(a37, 0, QuestionCode.A38, null);
    JPA.em().persist(a38);
} else {
    ((BooleanQuestion)a38).setDefaultValue(null);
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

    }
    private void createQuestionA39() {
        // == A39
        // Pièces documentaires liées aux systèmes de refroidissement

        a39 = (DocumentQuestion) questionService.findByCode(QuestionCode.A39);
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

    }
    private void createQuestionA43() {
        // == A43
        // Type de gaz

        a43 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A43);
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
    ((ValueSelectionQuestion)a43).setCodeList(CodeList.FRIGORIGENE);
    JPA.em().persist(a43);
}

    }
    private void createQuestionA44() {
        // == A44
        // Quantité de recharge nécessaire pour l'année de bilan

        
a44 = (DoubleQuestion) questionService.findByCode(QuestionCode.A44);
if (a44 == null) {
    a44 = new DoubleQuestion( a42, 0, QuestionCode.A44, massUnits, massUnits.getMainUnit() );
    JPA.em().persist(a44);

    // cleanup the driver
    Driver a44_driver = driverService.findByName("A44");
    if (a44_driver != null) {
        driverService.remove(a44_driver);
    }

} else {
    if (!a44.getQuestionSet().equals(a42) && a42.getQuestions().contains(a44)) {
        a42.getQuestions().remove(a44);
        JPA.em().persist(a42);
    }
    if (a44.getQuestionSet().equals(a42) && !a42.getQuestions().contains(a44)) {
        a42.getQuestions().add(a44);
        JPA.em().persist(a42);
    }
    ((NumericQuestion)a44).setUnitCategory(massUnits);
    a44.setOrderIndex(0);
    ((NumericQuestion)a44).setDefaultUnit(massUnits.getMainUnit());


    // cleanup the driver
    Driver a44_driver = driverService.findByName("A44");
    if (a44_driver != null) {
        driverService.remove(a44_driver);
    }

    ((NumericQuestion)a44).setDriver(null);

    JPA.em().persist(a44);
}



    }
    private void createQuestionA46() {
        // == A46
        // Quel est la puissance frigorifique des groupes froid?

        
a46 = (DoubleQuestion) questionService.findByCode(QuestionCode.A46);
if (a46 == null) {
    a46 = new DoubleQuestion( a45, 0, QuestionCode.A46, powerUnits, powerUnits.getMainUnit() );
    JPA.em().persist(a46);

    // cleanup the driver
    Driver a46_driver = driverService.findByName("A46");
    if (a46_driver != null) {
        driverService.remove(a46_driver);
    }


} else {
    if (!a46.getQuestionSet().equals(a45) && a45.getQuestions().contains(a46)) {
        a45.getQuestions().remove(a46);
        JPA.em().persist(a45);
    }
    if (a46.getQuestionSet().equals(a45) && !a45.getQuestions().contains(a46)) {
        a45.getQuestions().add(a46);
        JPA.em().persist(a45);
    }
    ((NumericQuestion)a46).setUnitCategory(powerUnits);
    a46.setOrderIndex(0);
    ((NumericQuestion)a46).setDefaultUnit(powerUnits.getMainUnit());

    // cleanup the driver
    Driver a46_driver = driverService.findByName("A46");
    if (a46_driver != null) {
        driverService.remove(a46_driver);
    }

    ((NumericQuestion)a46).setDriver(null);

    JPA.em().persist(a46);
}



    }
    private void createQuestionA48() {
        // == A48
        // Est-ce que votre entreprise produit du sucre ou des pâtes sèches?

        a48 = (BooleanQuestion) questionService.findByCode(QuestionCode.A48);
if (a48 == null) {
    a48 = new BooleanQuestion(a47, 0, QuestionCode.A48, null);
    JPA.em().persist(a48);
} else {
    ((BooleanQuestion)a48).setDefaultValue(null);
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

    }
    private void createQuestionA49() {
        // == A49
        // Quel est le nombre d'heures de fonctionnement annuel du site?

        
a49 = (DoubleQuestion) questionService.findByCode(QuestionCode.A49);
if (a49 == null) {
    a49 = new DoubleQuestion( a47, 0, QuestionCode.A49, timeUnits, getUnitBySymbol("h") );
    JPA.em().persist(a49);

    // cleanup the driver
    Driver a49_driver = driverService.findByName("A49");
    if (a49_driver != null) {
        driverService.remove(a49_driver);
    }

} else {
    if (!a49.getQuestionSet().equals(a47) && a47.getQuestions().contains(a49)) {
        a47.getQuestions().remove(a49);
        JPA.em().persist(a47);
    }
    if (a49.getQuestionSet().equals(a47) && !a47.getQuestions().contains(a49)) {
        a47.getQuestions().add(a49);
        JPA.em().persist(a47);
    }
    ((NumericQuestion)a49).setUnitCategory(timeUnits);
    a49.setOrderIndex(0);
    ((NumericQuestion)a49).setDefaultUnit(getUnitBySymbol("h"));

    // cleanup the driver
    Driver a49_driver = driverService.findByName("A49");
    if (a49_driver != null) {
        driverService.remove(a49_driver);
    }

    ((NumericQuestion)a49).setDriver(null);

    JPA.em().persist(a49);
}



    }
    private void createQuestionA6003() {
        // == A6003
        // Source de rejet

        a6003 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A6003);
if (a6003 == null) {
    a6003 = new ValueSelectionQuestion(a6002, 0, QuestionCode.A6003, CodeList.ORIGINEEAUUSEE);
    JPA.em().persist(a6003);
} else {
    if (!a6003.getQuestionSet().equals(a6002) && a6002.getQuestions().contains(a6003)) {
        a6002.getQuestions().remove(a6003);
        JPA.em().persist(a6002);
    }
    if (a6003.getQuestionSet().equals(a6002) && !a6002.getQuestions().contains(a6003)) {
        a6002.getQuestions().add(a6003);
        JPA.em().persist(a6002);
    }
    a6003.setOrderIndex(0);
    ((ValueSelectionQuestion)a6003).setCodeList(CodeList.ORIGINEEAUUSEE);
    JPA.em().persist(a6003);
}

    }
    private void createQuestionA6004() {
        // == A6004
        // Quantités de m³ rejetés

        
a6004 = (DoubleQuestion) questionService.findByCode(QuestionCode.A6004);
if (a6004 == null) {
    a6004 = new DoubleQuestion( a6002, 0, QuestionCode.A6004, volumeUnits, volumeUnits.getMainUnit() );
    JPA.em().persist(a6004);

    // cleanup the driver
    Driver a6004_driver = driverService.findByName("A6004");
    if (a6004_driver != null) {
        driverService.remove(a6004_driver);
    }

} else {
    if (!a6004.getQuestionSet().equals(a6002) && a6002.getQuestions().contains(a6004)) {
        a6002.getQuestions().remove(a6004);
        JPA.em().persist(a6002);
    }
    if (a6004.getQuestionSet().equals(a6002) && !a6002.getQuestions().contains(a6004)) {
        a6002.getQuestions().add(a6004);
        JPA.em().persist(a6002);
    }
    ((NumericQuestion)a6004).setUnitCategory(volumeUnits);
    a6004.setOrderIndex(0);
    ((NumericQuestion)a6004).setDefaultUnit(volumeUnits.getMainUnit());

    // cleanup the driver
    Driver a6004_driver = driverService.findByName("A6004");
    if (a6004_driver != null) {
        driverService.remove(a6004_driver);
    }

    ((NumericQuestion)a6004).setDriver(null);

    JPA.em().persist(a6004);
}



    }
    private void createQuestionA6005() {
        // == A6005
        // Méthode de traitement des eaux usées

        a6005 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A6005);
if (a6005 == null) {
    a6005 = new ValueSelectionQuestion(a6002, 0, QuestionCode.A6005, CodeList.TRAITEMENTEAU);
    JPA.em().persist(a6005);
} else {
    if (!a6005.getQuestionSet().equals(a6002) && a6002.getQuestions().contains(a6005)) {
        a6002.getQuestions().remove(a6005);
        JPA.em().persist(a6002);
    }
    if (a6005.getQuestionSet().equals(a6002) && !a6002.getQuestions().contains(a6005)) {
        a6002.getQuestions().add(a6005);
        JPA.em().persist(a6002);
    }
    a6005.setOrderIndex(0);
    ((ValueSelectionQuestion)a6005).setCodeList(CodeList.TRAITEMENTEAU);
    JPA.em().persist(a6005);
}

    }
    private void createQuestionA6007() {
        // == A6007
        // Quantités de DCO rejetés

        
a6007 = (DoubleQuestion) questionService.findByCode(QuestionCode.A6007);
if (a6007 == null) {
    a6007 = new DoubleQuestion( a6006, 0, QuestionCode.A6007, massUnits, massUnits.getMainUnit() );
    JPA.em().persist(a6007);

    // cleanup the driver
    Driver a6007_driver = driverService.findByName("A6007");
    if (a6007_driver != null) {
        driverService.remove(a6007_driver);
    }

} else {
    if (!a6007.getQuestionSet().equals(a6006) && a6006.getQuestions().contains(a6007)) {
        a6006.getQuestions().remove(a6007);
        JPA.em().persist(a6006);
    }
    if (a6007.getQuestionSet().equals(a6006) && !a6006.getQuestions().contains(a6007)) {
        a6006.getQuestions().add(a6007);
        JPA.em().persist(a6006);
    }
    ((NumericQuestion)a6007).setUnitCategory(massUnits);
    a6007.setOrderIndex(0);
    ((NumericQuestion)a6007).setDefaultUnit(massUnits.getMainUnit());


    // cleanup the driver
    Driver a6007_driver = driverService.findByName("A6007");
    if (a6007_driver != null) {
        driverService.remove(a6007_driver);
    }

    ((NumericQuestion)a6007).setDriver(null);

    JPA.em().persist(a6007);
}



    }
    private void createQuestionA6008() {
        // == A6008
        // Quantités d'azote rejetés

        
a6008 = (DoubleQuestion) questionService.findByCode(QuestionCode.A6008);
if (a6008 == null) {
    a6008 = new DoubleQuestion( a6006, 0, QuestionCode.A6008, massUnits, massUnits.getMainUnit() );
    JPA.em().persist(a6008);

    // cleanup the driver
    Driver a6008_driver = driverService.findByName("A6008");
    if (a6008_driver != null) {
        driverService.remove(a6008_driver);
    }

} else {
    if (!a6008.getQuestionSet().equals(a6006) && a6006.getQuestions().contains(a6008)) {
        a6006.getQuestions().remove(a6008);
        JPA.em().persist(a6006);
    }
    if (a6008.getQuestionSet().equals(a6006) && !a6006.getQuestions().contains(a6008)) {
        a6006.getQuestions().add(a6008);
        JPA.em().persist(a6006);
    }
    ((NumericQuestion)a6008).setUnitCategory(massUnits);
    a6008.setOrderIndex(0);
    ((NumericQuestion)a6008).setDefaultUnit(massUnits.getMainUnit());


    // cleanup the driver
    Driver a6008_driver = driverService.findByName("A6008");
    if (a6008_driver != null) {
        driverService.remove(a6008_driver);
    }

    ((NumericQuestion)a6008).setDriver(null);

    JPA.em().persist(a6008);
}



    }
    private void createQuestionA6009() {
        // == A6009
        // Méthode de traitement des eaux usées

        a6009 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A6009);
if (a6009 == null) {
    a6009 = new ValueSelectionQuestion(a6006, 0, QuestionCode.A6009, CodeList.TRAITEMENTEAU);
    JPA.em().persist(a6009);
} else {
    if (!a6009.getQuestionSet().equals(a6006) && a6006.getQuestions().contains(a6009)) {
        a6006.getQuestions().remove(a6009);
        JPA.em().persist(a6006);
    }
    if (a6009.getQuestionSet().equals(a6006) && !a6006.getQuestions().contains(a6009)) {
        a6006.getQuestions().add(a6009);
        JPA.em().persist(a6006);
    }
    a6009.setOrderIndex(0);
    ((ValueSelectionQuestion)a6009).setCodeList(CodeList.TRAITEMENTEAU);
    JPA.em().persist(a6009);
}

    }
    private void createQuestionA51() {
        // == A51
        // Pièces documentaires liées à la mobilité

        a51 = (DocumentQuestion) questionService.findByCode(QuestionCode.A51);
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

    }
    private void createQuestionA403() {
        // == A403
        // Consommation d'essence

        
a403 = (DoubleQuestion) questionService.findByCode(QuestionCode.A403);
if (a403 == null) {
    a403 = new DoubleQuestion( a402, 0, QuestionCode.A403, volumeUnits, volumeUnits.getMainUnit() );
    JPA.em().persist(a403);

    // cleanup the driver
    Driver a403_driver = driverService.findByName("A403");
    if (a403_driver != null) {
        driverService.remove(a403_driver);
    }

} else {
    if (!a403.getQuestionSet().equals(a402) && a402.getQuestions().contains(a403)) {
        a402.getQuestions().remove(a403);
        JPA.em().persist(a402);
    }
    if (a403.getQuestionSet().equals(a402) && !a402.getQuestions().contains(a403)) {
        a402.getQuestions().add(a403);
        JPA.em().persist(a402);
    }
    ((NumericQuestion)a403).setUnitCategory(volumeUnits);
    a403.setOrderIndex(0);
    ((NumericQuestion)a403).setDefaultUnit(volumeUnits.getMainUnit());

    // cleanup the driver
    Driver a403_driver = driverService.findByName("A403");
    if (a403_driver != null) {
        driverService.remove(a403_driver);
    }

    ((NumericQuestion)a403).setDriver(null);

    JPA.em().persist(a403);
}



    }
    private void createQuestionA404() {
        // == A404
        // Consommation de diesel

        
a404 = (DoubleQuestion) questionService.findByCode(QuestionCode.A404);
if (a404 == null) {
    a404 = new DoubleQuestion( a402, 0, QuestionCode.A404, volumeUnits, volumeUnits.getMainUnit() );
    JPA.em().persist(a404);

    // cleanup the driver
    Driver a404_driver = driverService.findByName("A404");
    if (a404_driver != null) {
        driverService.remove(a404_driver);
    }

} else {
    if (!a404.getQuestionSet().equals(a402) && a402.getQuestions().contains(a404)) {
        a402.getQuestions().remove(a404);
        JPA.em().persist(a402);
    }
    if (a404.getQuestionSet().equals(a402) && !a402.getQuestions().contains(a404)) {
        a402.getQuestions().add(a404);
        JPA.em().persist(a402);
    }
    ((NumericQuestion)a404).setUnitCategory(volumeUnits);
    a404.setOrderIndex(0);
    ((NumericQuestion)a404).setDefaultUnit(volumeUnits.getMainUnit());

    // cleanup the driver
    Driver a404_driver = driverService.findByName("A404");
    if (a404_driver != null) {
        driverService.remove(a404_driver);
    }

    ((NumericQuestion)a404).setDriver(null);

    JPA.em().persist(a404);
}



    }
    private void createQuestionA405() {
        // == A405
        // Consommation de gaz de pétrole liquéfié (GPL)

        
a405 = (DoubleQuestion) questionService.findByCode(QuestionCode.A405);
if (a405 == null) {
    a405 = new DoubleQuestion( a402, 0, QuestionCode.A405, volumeUnits, volumeUnits.getMainUnit() );
    JPA.em().persist(a405);

    // cleanup the driver
    Driver a405_driver = driverService.findByName("A405");
    if (a405_driver != null) {
        driverService.remove(a405_driver);
    }

} else {
    if (!a405.getQuestionSet().equals(a402) && a402.getQuestions().contains(a405)) {
        a402.getQuestions().remove(a405);
        JPA.em().persist(a402);
    }
    if (a405.getQuestionSet().equals(a402) && !a402.getQuestions().contains(a405)) {
        a402.getQuestions().add(a405);
        JPA.em().persist(a402);
    }
    ((NumericQuestion)a405).setUnitCategory(volumeUnits);
    a405.setOrderIndex(0);
    ((NumericQuestion)a405).setDefaultUnit(volumeUnits.getMainUnit());

    // cleanup the driver
    Driver a405_driver = driverService.findByName("A405");
    if (a405_driver != null) {
        driverService.remove(a405_driver);
    }

    ((NumericQuestion)a405).setDriver(null);

    JPA.em().persist(a405);
}



    }
    private void createQuestionA408() {
        // == A408
        // Catégorie de véhicule

        a408 = (StringQuestion) questionService.findByCode(QuestionCode.A408);
if (a408 == null) {
    a408 = new StringQuestion(a407, 0, QuestionCode.A408, null);
    JPA.em().persist(a408);
} else {
    ((StringQuestion)a408).setDefaultValue(null);
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

    }
    private void createQuestionA409() {
        // == A409
        // Quel type de carburant utilise-t-il ?

        a409 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A409);
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
    ((ValueSelectionQuestion)a409).setCodeList(CodeList.CARBURANT);
    JPA.em().persist(a409);
}

    }
    private void createQuestionA410() {
        // == A410
        // Consommation moyenne (L/100km)

        a410 = (IntegerQuestion) questionService.findByCode(QuestionCode.A410);
if (a410 == null) {
    a410 = new IntegerQuestion(a407, 0, QuestionCode.A410, null);
    JPA.em().persist(a410);

    // cleanup the driver
    Driver a410_driver = driverService.findByName("A410");
    if (a410_driver != null) {
        driverService.remove(a410_driver);
    }

} else {
    if (!a410.getQuestionSet().equals(a407) && a407.getQuestions().contains(a410)) {
        a407.getQuestions().remove(a410);
        JPA.em().persist(a407);
    }
    if (a410.getQuestionSet().equals(a407) && !a407.getQuestions().contains(a410)) {
        a407.getQuestions().add(a410);
        JPA.em().persist(a407);
    }
    a410.setOrderIndex(0);
    ((NumericQuestion)a410).setUnitCategory(null);

    // cleanup the driver
    Driver a410_driver = driverService.findByName("A410");
    if (a410_driver != null) {
        driverService.remove(a410_driver);
    }

    ((NumericQuestion)a410).setDriver(null);

    JPA.em().persist(a410);
}

    }
    private void createQuestionA411() {
        // == A411
        // Quelle est le nombre de kilomètres parcourus par an?

        a411 = (IntegerQuestion) questionService.findByCode(QuestionCode.A411);
if (a411 == null) {
    a411 = new IntegerQuestion(a407, 0, QuestionCode.A411, null);
    JPA.em().persist(a411);

    // cleanup the driver
    Driver a411_driver = driverService.findByName("A411");
    if (a411_driver != null) {
        driverService.remove(a411_driver);
    }

} else {
    if (!a411.getQuestionSet().equals(a407) && a407.getQuestions().contains(a411)) {
        a407.getQuestions().remove(a411);
        JPA.em().persist(a407);
    }
    if (a411.getQuestionSet().equals(a407) && !a407.getQuestions().contains(a411)) {
        a407.getQuestions().add(a411);
        JPA.em().persist(a407);
    }
    a411.setOrderIndex(0);
    ((NumericQuestion)a411).setUnitCategory(null);

    // cleanup the driver
    Driver a411_driver = driverService.findByName("A411");
    if (a411_driver != null) {
        driverService.remove(a411_driver);
    }

    ((NumericQuestion)a411).setDriver(null);

    JPA.em().persist(a411);
}

    }
    private void createQuestionA414() {
        // == A414
        // Catégorie de véhicule

        a414 = (StringQuestion) questionService.findByCode(QuestionCode.A414);
if (a414 == null) {
    a414 = new StringQuestion(a413, 0, QuestionCode.A414, null);
    JPA.em().persist(a414);
} else {
    ((StringQuestion)a414).setDefaultValue(null);
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

    }
    private void createQuestionA415() {
        // == A415
        // Quel type de carburant utilise-t-il ?

        a415 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A415);
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
    ((ValueSelectionQuestion)a415).setCodeList(CodeList.CARBURANT);
    JPA.em().persist(a415);
}

    }
    private void createQuestionA416() {
        // == A416
        // Prix moyen du litre de ce carburant

        
a416 = (DoubleQuestion) questionService.findByCode(QuestionCode.A416);
if (a416 == null) {
    a416 = new DoubleQuestion( a413, 0, QuestionCode.A416, moneyUnits, getUnitBySymbol("EUR") );
    JPA.em().persist(a416);

    // cleanup the driver
    Driver a416_driver = driverService.findByName("A416");
    if (a416_driver != null) {
        driverService.remove(a416_driver);
    }

} else {
    if (!a416.getQuestionSet().equals(a413) && a413.getQuestions().contains(a416)) {
        a413.getQuestions().remove(a416);
        JPA.em().persist(a413);
    }
    if (a416.getQuestionSet().equals(a413) && !a413.getQuestions().contains(a416)) {
        a413.getQuestions().add(a416);
        JPA.em().persist(a413);
    }
    ((NumericQuestion)a416).setUnitCategory(moneyUnits);
    a416.setOrderIndex(0);
    ((NumericQuestion)a416).setDefaultUnit(getUnitBySymbol("EUR"));

    // cleanup the driver
    Driver a416_driver = driverService.findByName("A416");
    if (a416_driver != null) {
        driverService.remove(a416_driver);
    }

    ((NumericQuestion)a416).setDriver(null);

    JPA.em().persist(a416);
}



    }
    private void createQuestionA417() {
        // == A417
        // Quel est le montant annuel de dépenses en carburant?

        
a417 = (DoubleQuestion) questionService.findByCode(QuestionCode.A417);
if (a417 == null) {
    a417 = new DoubleQuestion( a413, 0, QuestionCode.A417, moneyUnits, getUnitBySymbol("EUR") );
    JPA.em().persist(a417);

    // cleanup the driver
    Driver a417_driver = driverService.findByName("A417");
    if (a417_driver != null) {
        driverService.remove(a417_driver);
    }

} else {
    if (!a417.getQuestionSet().equals(a413) && a413.getQuestions().contains(a417)) {
        a413.getQuestions().remove(a417);
        JPA.em().persist(a413);
    }
    if (a417.getQuestionSet().equals(a413) && !a413.getQuestions().contains(a417)) {
        a413.getQuestions().add(a417);
        JPA.em().persist(a413);
    }
    ((NumericQuestion)a417).setUnitCategory(moneyUnits);
    a417.setOrderIndex(0);
    ((NumericQuestion)a417).setDefaultUnit(getUnitBySymbol("EUR"));

    // cleanup the driver
    Driver a417_driver = driverService.findByName("A417");
    if (a417_driver != null) {
        driverService.remove(a417_driver);
    }

    ((NumericQuestion)a417).setDriver(null);

    JPA.em().persist(a417);
}



    }
    private void createQuestionA503() {
        // == A503
        // Consommation d'essence

        
a503 = (DoubleQuestion) questionService.findByCode(QuestionCode.A503);
if (a503 == null) {
    a503 = new DoubleQuestion( a502, 0, QuestionCode.A503, volumeUnits, volumeUnits.getMainUnit() );
    JPA.em().persist(a503);

    // cleanup the driver
    Driver a503_driver = driverService.findByName("A503");
    if (a503_driver != null) {
        driverService.remove(a503_driver);
    }

} else {
    if (!a503.getQuestionSet().equals(a502) && a502.getQuestions().contains(a503)) {
        a502.getQuestions().remove(a503);
        JPA.em().persist(a502);
    }
    if (a503.getQuestionSet().equals(a502) && !a502.getQuestions().contains(a503)) {
        a502.getQuestions().add(a503);
        JPA.em().persist(a502);
    }
    ((NumericQuestion)a503).setUnitCategory(volumeUnits);
    a503.setOrderIndex(0);
    ((NumericQuestion)a503).setDefaultUnit(volumeUnits.getMainUnit());

    // cleanup the driver
    Driver a503_driver = driverService.findByName("A503");
    if (a503_driver != null) {
        driverService.remove(a503_driver);
    }

    ((NumericQuestion)a503).setDriver(null);

    JPA.em().persist(a503);
}



    }
    private void createQuestionA504() {
        // == A504
        // Consommation de diesel

        
a504 = (DoubleQuestion) questionService.findByCode(QuestionCode.A504);
if (a504 == null) {
    a504 = new DoubleQuestion( a502, 0, QuestionCode.A504, volumeUnits, volumeUnits.getMainUnit() );
    JPA.em().persist(a504);

    // cleanup the driver
    Driver a504_driver = driverService.findByName("A504");
    if (a504_driver != null) {
        driverService.remove(a504_driver);
    }

} else {
    if (!a504.getQuestionSet().equals(a502) && a502.getQuestions().contains(a504)) {
        a502.getQuestions().remove(a504);
        JPA.em().persist(a502);
    }
    if (a504.getQuestionSet().equals(a502) && !a502.getQuestions().contains(a504)) {
        a502.getQuestions().add(a504);
        JPA.em().persist(a502);
    }
    ((NumericQuestion)a504).setUnitCategory(volumeUnits);
    a504.setOrderIndex(0);
    ((NumericQuestion)a504).setDefaultUnit(volumeUnits.getMainUnit());

    // cleanup the driver
    Driver a504_driver = driverService.findByName("A504");
    if (a504_driver != null) {
        driverService.remove(a504_driver);
    }

    ((NumericQuestion)a504).setDriver(null);

    JPA.em().persist(a504);
}



    }
    private void createQuestionA505() {
        // == A505
        // Consommation de gaz de pétrole liquéfié (GPL)

        
a505 = (DoubleQuestion) questionService.findByCode(QuestionCode.A505);
if (a505 == null) {
    a505 = new DoubleQuestion( a502, 0, QuestionCode.A505, volumeUnits, volumeUnits.getMainUnit() );
    JPA.em().persist(a505);

    // cleanup the driver
    Driver a505_driver = driverService.findByName("A505");
    if (a505_driver != null) {
        driverService.remove(a505_driver);
    }

} else {
    if (!a505.getQuestionSet().equals(a502) && a502.getQuestions().contains(a505)) {
        a502.getQuestions().remove(a505);
        JPA.em().persist(a502);
    }
    if (a505.getQuestionSet().equals(a502) && !a502.getQuestions().contains(a505)) {
        a502.getQuestions().add(a505);
        JPA.em().persist(a502);
    }
    ((NumericQuestion)a505).setUnitCategory(volumeUnits);
    a505.setOrderIndex(0);
    ((NumericQuestion)a505).setDefaultUnit(volumeUnits.getMainUnit());

    // cleanup the driver
    Driver a505_driver = driverService.findByName("A505");
    if (a505_driver != null) {
        driverService.remove(a505_driver);
    }

    ((NumericQuestion)a505).setDriver(null);

    JPA.em().persist(a505);
}



    }
    private void createQuestionA508() {
        // == A508
        // Catégorie de véhicule

        a508 = (StringQuestion) questionService.findByCode(QuestionCode.A508);
if (a508 == null) {
    a508 = new StringQuestion(a507, 0, QuestionCode.A508, null);
    JPA.em().persist(a508);
} else {
    ((StringQuestion)a508).setDefaultValue(null);
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

    }
    private void createQuestionA509() {
        // == A509
        // Quel type de carburant utilise-t-il ?

        a509 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A509);
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
    ((ValueSelectionQuestion)a509).setCodeList(CodeList.CARBURANT);
    JPA.em().persist(a509);
}

    }
    private void createQuestionA510() {
        // == A510
        // Consommation moyenne (L/100km)

        a510 = (IntegerQuestion) questionService.findByCode(QuestionCode.A510);
if (a510 == null) {
    a510 = new IntegerQuestion(a507, 0, QuestionCode.A510, null);
    JPA.em().persist(a510);

    // cleanup the driver
    Driver a510_driver = driverService.findByName("A510");
    if (a510_driver != null) {
        driverService.remove(a510_driver);
    }

} else {
    if (!a510.getQuestionSet().equals(a507) && a507.getQuestions().contains(a510)) {
        a507.getQuestions().remove(a510);
        JPA.em().persist(a507);
    }
    if (a510.getQuestionSet().equals(a507) && !a507.getQuestions().contains(a510)) {
        a507.getQuestions().add(a510);
        JPA.em().persist(a507);
    }
    a510.setOrderIndex(0);
    ((NumericQuestion)a510).setUnitCategory(null);

    // cleanup the driver
    Driver a510_driver = driverService.findByName("A510");
    if (a510_driver != null) {
        driverService.remove(a510_driver);
    }

    ((NumericQuestion)a510).setDriver(null);

    JPA.em().persist(a510);
}

    }
    private void createQuestionA511() {
        // == A511
        // Quelle est le nombre de kilomètres parcourus par an?

        a511 = (IntegerQuestion) questionService.findByCode(QuestionCode.A511);
if (a511 == null) {
    a511 = new IntegerQuestion(a507, 0, QuestionCode.A511, null);
    JPA.em().persist(a511);

    // cleanup the driver
    Driver a511_driver = driverService.findByName("A511");
    if (a511_driver != null) {
        driverService.remove(a511_driver);
    }

} else {
    if (!a511.getQuestionSet().equals(a507) && a507.getQuestions().contains(a511)) {
        a507.getQuestions().remove(a511);
        JPA.em().persist(a507);
    }
    if (a511.getQuestionSet().equals(a507) && !a507.getQuestions().contains(a511)) {
        a507.getQuestions().add(a511);
        JPA.em().persist(a507);
    }
    a511.setOrderIndex(0);
    ((NumericQuestion)a511).setUnitCategory(null);

    // cleanup the driver
    Driver a511_driver = driverService.findByName("A511");
    if (a511_driver != null) {
        driverService.remove(a511_driver);
    }

    ((NumericQuestion)a511).setDriver(null);

    JPA.em().persist(a511);
}

    }
    private void createQuestionA514() {
        // == A514
        // Catégorie de véhicule

        a514 = (StringQuestion) questionService.findByCode(QuestionCode.A514);
if (a514 == null) {
    a514 = new StringQuestion(a513, 0, QuestionCode.A514, null);
    JPA.em().persist(a514);
} else {
    ((StringQuestion)a514).setDefaultValue(null);
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

    }
    private void createQuestionA515() {
        // == A515
        // Quel type de carburant utilise-t-il ?

        a515 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A515);
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
    ((ValueSelectionQuestion)a515).setCodeList(CodeList.CARBURANT);
    JPA.em().persist(a515);
}

    }
    private void createQuestionA516() {
        // == A516
        // Prix moyen du litre de ce carburant

        
a516 = (DoubleQuestion) questionService.findByCode(QuestionCode.A516);
if (a516 == null) {
    a516 = new DoubleQuestion( a513, 0, QuestionCode.A516, moneyUnits, getUnitBySymbol("EUR") );
    JPA.em().persist(a516);

    // cleanup the driver
    Driver a516_driver = driverService.findByName("A516");
    if (a516_driver != null) {
        driverService.remove(a516_driver);
    }

} else {
    if (!a516.getQuestionSet().equals(a513) && a513.getQuestions().contains(a516)) {
        a513.getQuestions().remove(a516);
        JPA.em().persist(a513);
    }
    if (a516.getQuestionSet().equals(a513) && !a513.getQuestions().contains(a516)) {
        a513.getQuestions().add(a516);
        JPA.em().persist(a513);
    }
    ((NumericQuestion)a516).setUnitCategory(moneyUnits);
    a516.setOrderIndex(0);
    ((NumericQuestion)a516).setDefaultUnit(getUnitBySymbol("EUR"));

    // cleanup the driver
    Driver a516_driver = driverService.findByName("A516");
    if (a516_driver != null) {
        driverService.remove(a516_driver);
    }

    ((NumericQuestion)a516).setDriver(null);

    JPA.em().persist(a516);
}



    }
    private void createQuestionA517() {
        // == A517
        // Quel est le montant annuel de dépenses en carburant?

        
a517 = (DoubleQuestion) questionService.findByCode(QuestionCode.A517);
if (a517 == null) {
    a517 = new DoubleQuestion( a513, 0, QuestionCode.A517, moneyUnits, getUnitBySymbol("EUR") );
    JPA.em().persist(a517);

    // cleanup the driver
    Driver a517_driver = driverService.findByName("A517");
    if (a517_driver != null) {
        driverService.remove(a517_driver);
    }

} else {
    if (!a517.getQuestionSet().equals(a513) && a513.getQuestions().contains(a517)) {
        a513.getQuestions().remove(a517);
        JPA.em().persist(a513);
    }
    if (a517.getQuestionSet().equals(a513) && !a513.getQuestions().contains(a517)) {
        a513.getQuestions().add(a517);
        JPA.em().persist(a513);
    }
    ((NumericQuestion)a517).setUnitCategory(moneyUnits);
    a517.setOrderIndex(0);
    ((NumericQuestion)a517).setDefaultUnit(getUnitBySymbol("EUR"));

    // cleanup the driver
    Driver a517_driver = driverService.findByName("A517");
    if (a517_driver != null) {
        driverService.remove(a517_driver);
    }

    ((NumericQuestion)a517).setDriver(null);

    JPA.em().persist(a517);
}



    }
    private void createQuestionA603() {
        // == A603
        // Consommation d'essence

        
a603 = (DoubleQuestion) questionService.findByCode(QuestionCode.A603);
if (a603 == null) {
    a603 = new DoubleQuestion( a602, 0, QuestionCode.A603, volumeUnits, volumeUnits.getMainUnit() );
    JPA.em().persist(a603);

    // cleanup the driver
    Driver a603_driver = driverService.findByName("A603");
    if (a603_driver != null) {
        driverService.remove(a603_driver);
    }

} else {
    if (!a603.getQuestionSet().equals(a602) && a602.getQuestions().contains(a603)) {
        a602.getQuestions().remove(a603);
        JPA.em().persist(a602);
    }
    if (a603.getQuestionSet().equals(a602) && !a602.getQuestions().contains(a603)) {
        a602.getQuestions().add(a603);
        JPA.em().persist(a602);
    }
    ((NumericQuestion)a603).setUnitCategory(volumeUnits);
    a603.setOrderIndex(0);
    ((NumericQuestion)a603).setDefaultUnit(volumeUnits.getMainUnit());

    // cleanup the driver
    Driver a603_driver = driverService.findByName("A603");
    if (a603_driver != null) {
        driverService.remove(a603_driver);
    }

    ((NumericQuestion)a603).setDriver(null);

    JPA.em().persist(a603);
}



    }
    private void createQuestionA604() {
        // == A604
        // Consommation de diesel

        
a604 = (DoubleQuestion) questionService.findByCode(QuestionCode.A604);
if (a604 == null) {
    a604 = new DoubleQuestion( a602, 0, QuestionCode.A604, volumeUnits, volumeUnits.getMainUnit() );
    JPA.em().persist(a604);

    // cleanup the driver
    Driver a604_driver = driverService.findByName("A604");
    if (a604_driver != null) {
        driverService.remove(a604_driver);
    }

} else {
    if (!a604.getQuestionSet().equals(a602) && a602.getQuestions().contains(a604)) {
        a602.getQuestions().remove(a604);
        JPA.em().persist(a602);
    }
    if (a604.getQuestionSet().equals(a602) && !a602.getQuestions().contains(a604)) {
        a602.getQuestions().add(a604);
        JPA.em().persist(a602);
    }
    ((NumericQuestion)a604).setUnitCategory(volumeUnits);
    a604.setOrderIndex(0);
    ((NumericQuestion)a604).setDefaultUnit(volumeUnits.getMainUnit());

    // cleanup the driver
    Driver a604_driver = driverService.findByName("A604");
    if (a604_driver != null) {
        driverService.remove(a604_driver);
    }

    ((NumericQuestion)a604).setDriver(null);

    JPA.em().persist(a604);
}



    }
    private void createQuestionA605() {
        // == A605
        // Consommation de gaz de pétrole liquéfié (GPL)

        
a605 = (DoubleQuestion) questionService.findByCode(QuestionCode.A605);
if (a605 == null) {
    a605 = new DoubleQuestion( a602, 0, QuestionCode.A605, volumeUnits, volumeUnits.getMainUnit() );
    JPA.em().persist(a605);

    // cleanup the driver
    Driver a605_driver = driverService.findByName("A605");
    if (a605_driver != null) {
        driverService.remove(a605_driver);
    }

} else {
    if (!a605.getQuestionSet().equals(a602) && a602.getQuestions().contains(a605)) {
        a602.getQuestions().remove(a605);
        JPA.em().persist(a602);
    }
    if (a605.getQuestionSet().equals(a602) && !a602.getQuestions().contains(a605)) {
        a602.getQuestions().add(a605);
        JPA.em().persist(a602);
    }
    ((NumericQuestion)a605).setUnitCategory(volumeUnits);
    a605.setOrderIndex(0);
    ((NumericQuestion)a605).setDefaultUnit(volumeUnits.getMainUnit());

    // cleanup the driver
    Driver a605_driver = driverService.findByName("A605");
    if (a605_driver != null) {
        driverService.remove(a605_driver);
    }

    ((NumericQuestion)a605).setDriver(null);

    JPA.em().persist(a605);
}



    }
    private void createQuestionA608() {
        // == A608
        // Catégorie de véhicule

        a608 = (StringQuestion) questionService.findByCode(QuestionCode.A608);
if (a608 == null) {
    a608 = new StringQuestion(a607, 0, QuestionCode.A608, null);
    JPA.em().persist(a608);
} else {
    ((StringQuestion)a608).setDefaultValue(null);
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

    }
    private void createQuestionA609() {
        // == A609
        // Quel type de carburant utilise-t-il ?

        a609 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A609);
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
    ((ValueSelectionQuestion)a609).setCodeList(CodeList.CARBURANT);
    JPA.em().persist(a609);
}

    }
    private void createQuestionA610() {
        // == A610
        // Consommation moyenne (L/100km)

        a610 = (IntegerQuestion) questionService.findByCode(QuestionCode.A610);
if (a610 == null) {
    a610 = new IntegerQuestion(a607, 0, QuestionCode.A610, null);
    JPA.em().persist(a610);

    // cleanup the driver
    Driver a610_driver = driverService.findByName("A610");
    if (a610_driver != null) {
        driverService.remove(a610_driver);
    }

} else {
    if (!a610.getQuestionSet().equals(a607) && a607.getQuestions().contains(a610)) {
        a607.getQuestions().remove(a610);
        JPA.em().persist(a607);
    }
    if (a610.getQuestionSet().equals(a607) && !a607.getQuestions().contains(a610)) {
        a607.getQuestions().add(a610);
        JPA.em().persist(a607);
    }
    a610.setOrderIndex(0);
    ((NumericQuestion)a610).setUnitCategory(null);

    // cleanup the driver
    Driver a610_driver = driverService.findByName("A610");
    if (a610_driver != null) {
        driverService.remove(a610_driver);
    }

    ((NumericQuestion)a610).setDriver(null);

    JPA.em().persist(a610);
}

    }
    private void createQuestionA611() {
        // == A611
        // Quelle est le nombre de kilomètres parcourus par an?

        a611 = (IntegerQuestion) questionService.findByCode(QuestionCode.A611);
if (a611 == null) {
    a611 = new IntegerQuestion(a607, 0, QuestionCode.A611, null);
    JPA.em().persist(a611);

    // cleanup the driver
    Driver a611_driver = driverService.findByName("A611");
    if (a611_driver != null) {
        driverService.remove(a611_driver);
    }

} else {
    if (!a611.getQuestionSet().equals(a607) && a607.getQuestions().contains(a611)) {
        a607.getQuestions().remove(a611);
        JPA.em().persist(a607);
    }
    if (a611.getQuestionSet().equals(a607) && !a607.getQuestions().contains(a611)) {
        a607.getQuestions().add(a611);
        JPA.em().persist(a607);
    }
    a611.setOrderIndex(0);
    ((NumericQuestion)a611).setUnitCategory(null);

    // cleanup the driver
    Driver a611_driver = driverService.findByName("A611");
    if (a611_driver != null) {
        driverService.remove(a611_driver);
    }

    ((NumericQuestion)a611).setDriver(null);

    JPA.em().persist(a611);
}

    }
    private void createQuestionA614() {
        // == A614
        // Catégorie de véhicule

        a614 = (StringQuestion) questionService.findByCode(QuestionCode.A614);
if (a614 == null) {
    a614 = new StringQuestion(a613, 0, QuestionCode.A614, null);
    JPA.em().persist(a614);
} else {
    ((StringQuestion)a614).setDefaultValue(null);
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

    }
    private void createQuestionA615() {
        // == A615
        // Quel type de carburant utilise-t-il ?

        a615 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A615);
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
    ((ValueSelectionQuestion)a615).setCodeList(CodeList.CARBURANT);
    JPA.em().persist(a615);
}

    }
    private void createQuestionA616() {
        // == A616
        // Prix moyen du litre de ce carburant

        
a616 = (DoubleQuestion) questionService.findByCode(QuestionCode.A616);
if (a616 == null) {
    a616 = new DoubleQuestion( a613, 0, QuestionCode.A616, moneyUnits, getUnitBySymbol("EUR") );
    JPA.em().persist(a616);

    // cleanup the driver
    Driver a616_driver = driverService.findByName("A616");
    if (a616_driver != null) {
        driverService.remove(a616_driver);
    }

} else {
    if (!a616.getQuestionSet().equals(a613) && a613.getQuestions().contains(a616)) {
        a613.getQuestions().remove(a616);
        JPA.em().persist(a613);
    }
    if (a616.getQuestionSet().equals(a613) && !a613.getQuestions().contains(a616)) {
        a613.getQuestions().add(a616);
        JPA.em().persist(a613);
    }
    ((NumericQuestion)a616).setUnitCategory(moneyUnits);
    a616.setOrderIndex(0);
    ((NumericQuestion)a616).setDefaultUnit(getUnitBySymbol("EUR"));

    // cleanup the driver
    Driver a616_driver = driverService.findByName("A616");
    if (a616_driver != null) {
        driverService.remove(a616_driver);
    }

    ((NumericQuestion)a616).setDriver(null);

    JPA.em().persist(a616);
}



    }
    private void createQuestionA617() {
        // == A617
        // Quel est le montant annuel de dépenses en carburant?

        
a617 = (DoubleQuestion) questionService.findByCode(QuestionCode.A617);
if (a617 == null) {
    a617 = new DoubleQuestion( a613, 0, QuestionCode.A617, moneyUnits, getUnitBySymbol("EUR") );
    JPA.em().persist(a617);

    // cleanup the driver
    Driver a617_driver = driverService.findByName("A617");
    if (a617_driver != null) {
        driverService.remove(a617_driver);
    }

} else {
    if (!a617.getQuestionSet().equals(a613) && a613.getQuestions().contains(a617)) {
        a613.getQuestions().remove(a617);
        JPA.em().persist(a613);
    }
    if (a617.getQuestionSet().equals(a613) && !a613.getQuestions().contains(a617)) {
        a613.getQuestions().add(a617);
        JPA.em().persist(a613);
    }
    ((NumericQuestion)a617).setUnitCategory(moneyUnits);
    a617.setOrderIndex(0);
    ((NumericQuestion)a617).setDefaultUnit(getUnitBySymbol("EUR"));

    // cleanup the driver
    Driver a617_driver = driverService.findByName("A617");
    if (a617_driver != null) {
        driverService.remove(a617_driver);
    }

    ((NumericQuestion)a617).setDriver(null);

    JPA.em().persist(a617);
}



    }
    private void createQuestionA95() {
        // == A95
        // Bus TEC pour déplacement domicile-travail des employés (en km.passagers)

        a95 = (IntegerQuestion) questionService.findByCode(QuestionCode.A95);
if (a95 == null) {
    a95 = new IntegerQuestion(a94, 0, QuestionCode.A95, null);
    JPA.em().persist(a95);

    // cleanup the driver
    Driver a95_driver = driverService.findByName("A95");
    if (a95_driver != null) {
        driverService.remove(a95_driver);
    }

} else {
    if (!a95.getQuestionSet().equals(a94) && a94.getQuestions().contains(a95)) {
        a94.getQuestions().remove(a95);
        JPA.em().persist(a94);
    }
    if (a95.getQuestionSet().equals(a94) && !a94.getQuestions().contains(a95)) {
        a94.getQuestions().add(a95);
        JPA.em().persist(a94);
    }
    a95.setOrderIndex(0);
    ((NumericQuestion)a95).setUnitCategory(null);

    // cleanup the driver
    Driver a95_driver = driverService.findByName("A95");
    if (a95_driver != null) {
        driverService.remove(a95_driver);
    }

    ((NumericQuestion)a95).setDriver(null);

    JPA.em().persist(a95);
}

    }
    private void createQuestionA96() {
        // == A96
        // Bus TEC pour déplacements professionnels & des visiteurs (en km.passagers)

        a96 = (IntegerQuestion) questionService.findByCode(QuestionCode.A96);
if (a96 == null) {
    a96 = new IntegerQuestion(a94, 0, QuestionCode.A96, null);
    JPA.em().persist(a96);

    // cleanup the driver
    Driver a96_driver = driverService.findByName("A96");
    if (a96_driver != null) {
        driverService.remove(a96_driver);
    }

} else {
    if (!a96.getQuestionSet().equals(a94) && a94.getQuestions().contains(a96)) {
        a94.getQuestions().remove(a96);
        JPA.em().persist(a94);
    }
    if (a96.getQuestionSet().equals(a94) && !a94.getQuestions().contains(a96)) {
        a94.getQuestions().add(a96);
        JPA.em().persist(a94);
    }
    a96.setOrderIndex(0);
    ((NumericQuestion)a96).setUnitCategory(null);

    // cleanup the driver
    Driver a96_driver = driverService.findByName("A96");
    if (a96_driver != null) {
        driverService.remove(a96_driver);
    }

    ((NumericQuestion)a96).setDriver(null);

    JPA.em().persist(a96);
}

    }
    private void createQuestionA97() {
        // == A97
        // Métro pour déplacement domicile-travail des employés (en km.passagers)

        a97 = (IntegerQuestion) questionService.findByCode(QuestionCode.A97);
if (a97 == null) {
    a97 = new IntegerQuestion(a94, 0, QuestionCode.A97, null);
    JPA.em().persist(a97);

    // cleanup the driver
    Driver a97_driver = driverService.findByName("A97");
    if (a97_driver != null) {
        driverService.remove(a97_driver);
    }

} else {
    if (!a97.getQuestionSet().equals(a94) && a94.getQuestions().contains(a97)) {
        a94.getQuestions().remove(a97);
        JPA.em().persist(a94);
    }
    if (a97.getQuestionSet().equals(a94) && !a94.getQuestions().contains(a97)) {
        a94.getQuestions().add(a97);
        JPA.em().persist(a94);
    }
    a97.setOrderIndex(0);
    ((NumericQuestion)a97).setUnitCategory(null);

    // cleanup the driver
    Driver a97_driver = driverService.findByName("A97");
    if (a97_driver != null) {
        driverService.remove(a97_driver);
    }

    ((NumericQuestion)a97).setDriver(null);

    JPA.em().persist(a97);
}

    }
    private void createQuestionA98() {
        // == A98
        // Métro pour déplacements professionnels & des visiteurs (en km.passagers)

        a98 = (IntegerQuestion) questionService.findByCode(QuestionCode.A98);
if (a98 == null) {
    a98 = new IntegerQuestion(a94, 0, QuestionCode.A98, null);
    JPA.em().persist(a98);

    // cleanup the driver
    Driver a98_driver = driverService.findByName("A98");
    if (a98_driver != null) {
        driverService.remove(a98_driver);
    }

} else {
    if (!a98.getQuestionSet().equals(a94) && a94.getQuestions().contains(a98)) {
        a94.getQuestions().remove(a98);
        JPA.em().persist(a94);
    }
    if (a98.getQuestionSet().equals(a94) && !a94.getQuestions().contains(a98)) {
        a94.getQuestions().add(a98);
        JPA.em().persist(a94);
    }
    a98.setOrderIndex(0);
    ((NumericQuestion)a98).setUnitCategory(null);

    // cleanup the driver
    Driver a98_driver = driverService.findByName("A98");
    if (a98_driver != null) {
        driverService.remove(a98_driver);
    }

    ((NumericQuestion)a98).setDriver(null);

    JPA.em().persist(a98);
}

    }
    private void createQuestionA99() {
        // == A99
        // Train national SNCB pour déplacement domicile-travail des employés (en km.passagers)

        a99 = (IntegerQuestion) questionService.findByCode(QuestionCode.A99);
if (a99 == null) {
    a99 = new IntegerQuestion(a94, 0, QuestionCode.A99, null);
    JPA.em().persist(a99);

    // cleanup the driver
    Driver a99_driver = driverService.findByName("A99");
    if (a99_driver != null) {
        driverService.remove(a99_driver);
    }

} else {
    if (!a99.getQuestionSet().equals(a94) && a94.getQuestions().contains(a99)) {
        a94.getQuestions().remove(a99);
        JPA.em().persist(a94);
    }
    if (a99.getQuestionSet().equals(a94) && !a94.getQuestions().contains(a99)) {
        a94.getQuestions().add(a99);
        JPA.em().persist(a94);
    }
    a99.setOrderIndex(0);
    ((NumericQuestion)a99).setUnitCategory(null);

    // cleanup the driver
    Driver a99_driver = driverService.findByName("A99");
    if (a99_driver != null) {
        driverService.remove(a99_driver);
    }

    ((NumericQuestion)a99).setDriver(null);

    JPA.em().persist(a99);
}

    }
    private void createQuestionA100() {
        // == A100
        // Train national SNCB pour déplacements professionnels & des visiteurs (en km.passagers)

        a100 = (IntegerQuestion) questionService.findByCode(QuestionCode.A100);
if (a100 == null) {
    a100 = new IntegerQuestion(a94, 0, QuestionCode.A100, null);
    JPA.em().persist(a100);

    // cleanup the driver
    Driver a100_driver = driverService.findByName("A100");
    if (a100_driver != null) {
        driverService.remove(a100_driver);
    }

} else {
    if (!a100.getQuestionSet().equals(a94) && a94.getQuestions().contains(a100)) {
        a94.getQuestions().remove(a100);
        JPA.em().persist(a94);
    }
    if (a100.getQuestionSet().equals(a94) && !a94.getQuestions().contains(a100)) {
        a94.getQuestions().add(a100);
        JPA.em().persist(a94);
    }
    a100.setOrderIndex(0);
    ((NumericQuestion)a100).setUnitCategory(null);

    // cleanup the driver
    Driver a100_driver = driverService.findByName("A100");
    if (a100_driver != null) {
        driverService.remove(a100_driver);
    }

    ((NumericQuestion)a100).setDriver(null);

    JPA.em().persist(a100);
}

    }
    private void createQuestionA101() {
        // == A101
        // Train international (TGV) pour déplacement domicile-travail des employés (en km.passagers)

        a101 = (IntegerQuestion) questionService.findByCode(QuestionCode.A101);
if (a101 == null) {
    a101 = new IntegerQuestion(a94, 0, QuestionCode.A101, null);
    JPA.em().persist(a101);

    // cleanup the driver
    Driver a101_driver = driverService.findByName("A101");
    if (a101_driver != null) {
        driverService.remove(a101_driver);
    }

} else {
    if (!a101.getQuestionSet().equals(a94) && a94.getQuestions().contains(a101)) {
        a94.getQuestions().remove(a101);
        JPA.em().persist(a94);
    }
    if (a101.getQuestionSet().equals(a94) && !a94.getQuestions().contains(a101)) {
        a94.getQuestions().add(a101);
        JPA.em().persist(a94);
    }
    a101.setOrderIndex(0);
    ((NumericQuestion)a101).setUnitCategory(null);

    // cleanup the driver
    Driver a101_driver = driverService.findByName("A101");
    if (a101_driver != null) {
        driverService.remove(a101_driver);
    }

    ((NumericQuestion)a101).setDriver(null);

    JPA.em().persist(a101);
}

    }
    private void createQuestionA102() {
        // == A102
        // Train international (TGV) pour déplacements professionnels & des visiteurs (en km.passagers)

        a102 = (IntegerQuestion) questionService.findByCode(QuestionCode.A102);
if (a102 == null) {
    a102 = new IntegerQuestion(a94, 0, QuestionCode.A102, null);
    JPA.em().persist(a102);

    // cleanup the driver
    Driver a102_driver = driverService.findByName("A102");
    if (a102_driver != null) {
        driverService.remove(a102_driver);
    }

} else {
    if (!a102.getQuestionSet().equals(a94) && a94.getQuestions().contains(a102)) {
        a94.getQuestions().remove(a102);
        JPA.em().persist(a94);
    }
    if (a102.getQuestionSet().equals(a94) && !a94.getQuestions().contains(a102)) {
        a94.getQuestions().add(a102);
        JPA.em().persist(a94);
    }
    a102.setOrderIndex(0);
    ((NumericQuestion)a102).setUnitCategory(null);

    // cleanup the driver
    Driver a102_driver = driverService.findByName("A102");
    if (a102_driver != null) {
        driverService.remove(a102_driver);
    }

    ((NumericQuestion)a102).setDriver(null);

    JPA.em().persist(a102);
}

    }
    private void createQuestionA103() {
        // == A103
        // Tram pour déplacement domicile-travail des employés (en km.passagers)

        a103 = (IntegerQuestion) questionService.findByCode(QuestionCode.A103);
if (a103 == null) {
    a103 = new IntegerQuestion(a94, 0, QuestionCode.A103, null);
    JPA.em().persist(a103);

    // cleanup the driver
    Driver a103_driver = driverService.findByName("A103");
    if (a103_driver != null) {
        driverService.remove(a103_driver);
    }

} else {
    if (!a103.getQuestionSet().equals(a94) && a94.getQuestions().contains(a103)) {
        a94.getQuestions().remove(a103);
        JPA.em().persist(a94);
    }
    if (a103.getQuestionSet().equals(a94) && !a94.getQuestions().contains(a103)) {
        a94.getQuestions().add(a103);
        JPA.em().persist(a94);
    }
    a103.setOrderIndex(0);
    ((NumericQuestion)a103).setUnitCategory(null);

    // cleanup the driver
    Driver a103_driver = driverService.findByName("A103");
    if (a103_driver != null) {
        driverService.remove(a103_driver);
    }

    ((NumericQuestion)a103).setDriver(null);

    JPA.em().persist(a103);
}

    }
    private void createQuestionA104() {
        // == A104
        // Tram pour déplacements professionnels & des visiteurs (en km.passagers)

        a104 = (IntegerQuestion) questionService.findByCode(QuestionCode.A104);
if (a104 == null) {
    a104 = new IntegerQuestion(a94, 0, QuestionCode.A104, null);
    JPA.em().persist(a104);

    // cleanup the driver
    Driver a104_driver = driverService.findByName("A104");
    if (a104_driver != null) {
        driverService.remove(a104_driver);
    }

} else {
    if (!a104.getQuestionSet().equals(a94) && a94.getQuestions().contains(a104)) {
        a94.getQuestions().remove(a104);
        JPA.em().persist(a94);
    }
    if (a104.getQuestionSet().equals(a94) && !a94.getQuestions().contains(a104)) {
        a94.getQuestions().add(a104);
        JPA.em().persist(a94);
    }
    a104.setOrderIndex(0);
    ((NumericQuestion)a104).setUnitCategory(null);

    // cleanup the driver
    Driver a104_driver = driverService.findByName("A104");
    if (a104_driver != null) {
        driverService.remove(a104_driver);
    }

    ((NumericQuestion)a104).setDriver(null);

    JPA.em().persist(a104);
}

    }
    private void createQuestionA105() {
        // == A105
        // Taxi pour déplacement domicile-travail des employés (en véhicules.km)

        a105 = (IntegerQuestion) questionService.findByCode(QuestionCode.A105);
if (a105 == null) {
    a105 = new IntegerQuestion(a94, 0, QuestionCode.A105, null);
    JPA.em().persist(a105);

    // cleanup the driver
    Driver a105_driver = driverService.findByName("A105");
    if (a105_driver != null) {
        driverService.remove(a105_driver);
    }

} else {
    if (!a105.getQuestionSet().equals(a94) && a94.getQuestions().contains(a105)) {
        a94.getQuestions().remove(a105);
        JPA.em().persist(a94);
    }
    if (a105.getQuestionSet().equals(a94) && !a94.getQuestions().contains(a105)) {
        a94.getQuestions().add(a105);
        JPA.em().persist(a94);
    }
    a105.setOrderIndex(0);
    ((NumericQuestion)a105).setUnitCategory(null);

    // cleanup the driver
    Driver a105_driver = driverService.findByName("A105");
    if (a105_driver != null) {
        driverService.remove(a105_driver);
    }

    ((NumericQuestion)a105).setDriver(null);

    JPA.em().persist(a105);
}

    }
    private void createQuestionA106() {
        // == A106
        // Taxi pour déplacements professionnels & des visiteurs (en véhicules.km)

        a106 = (IntegerQuestion) questionService.findByCode(QuestionCode.A106);
if (a106 == null) {
    a106 = new IntegerQuestion(a94, 0, QuestionCode.A106, null);
    JPA.em().persist(a106);

    // cleanup the driver
    Driver a106_driver = driverService.findByName("A106");
    if (a106_driver != null) {
        driverService.remove(a106_driver);
    }

} else {
    if (!a106.getQuestionSet().equals(a94) && a94.getQuestions().contains(a106)) {
        a94.getQuestions().remove(a106);
        JPA.em().persist(a94);
    }
    if (a106.getQuestionSet().equals(a94) && !a94.getQuestions().contains(a106)) {
        a94.getQuestions().add(a106);
        JPA.em().persist(a94);
    }
    a106.setOrderIndex(0);
    ((NumericQuestion)a106).setUnitCategory(null);

    // cleanup the driver
    Driver a106_driver = driverService.findByName("A106");
    if (a106_driver != null) {
        driverService.remove(a106_driver);
    }

    ((NumericQuestion)a106).setDriver(null);

    JPA.em().persist(a106);
}

    }
    private void createQuestionA107() {
        // == A107
        // Taxi pour déplacement domicile-travail des employés (en valeur)

        
a107 = (DoubleQuestion) questionService.findByCode(QuestionCode.A107);
if (a107 == null) {
    a107 = new DoubleQuestion( a94, 0, QuestionCode.A107, moneyUnits, getUnitBySymbol("EUR") );
    JPA.em().persist(a107);

    // cleanup the driver
    Driver a107_driver = driverService.findByName("A107");
    if (a107_driver != null) {
        driverService.remove(a107_driver);
    }

} else {
    if (!a107.getQuestionSet().equals(a94) && a94.getQuestions().contains(a107)) {
        a94.getQuestions().remove(a107);
        JPA.em().persist(a94);
    }
    if (a107.getQuestionSet().equals(a94) && !a94.getQuestions().contains(a107)) {
        a94.getQuestions().add(a107);
        JPA.em().persist(a94);
    }
    ((NumericQuestion)a107).setUnitCategory(moneyUnits);
    a107.setOrderIndex(0);
    ((NumericQuestion)a107).setDefaultUnit(getUnitBySymbol("EUR"));

    // cleanup the driver
    Driver a107_driver = driverService.findByName("A107");
    if (a107_driver != null) {
        driverService.remove(a107_driver);
    }

    ((NumericQuestion)a107).setDriver(null);

    JPA.em().persist(a107);
}



    }
    private void createQuestionA108() {
        // == A108
        // Taxi pour déplacements professionnels & des visiteurs (en valeur)

        
a108 = (DoubleQuestion) questionService.findByCode(QuestionCode.A108);
if (a108 == null) {
    a108 = new DoubleQuestion( a94, 0, QuestionCode.A108, moneyUnits, getUnitBySymbol("EUR") );
    JPA.em().persist(a108);

    // cleanup the driver
    Driver a108_driver = driverService.findByName("A108");
    if (a108_driver != null) {
        driverService.remove(a108_driver);
    }

} else {
    if (!a108.getQuestionSet().equals(a94) && a94.getQuestions().contains(a108)) {
        a94.getQuestions().remove(a108);
        JPA.em().persist(a94);
    }
    if (a108.getQuestionSet().equals(a94) && !a94.getQuestions().contains(a108)) {
        a94.getQuestions().add(a108);
        JPA.em().persist(a94);
    }
    ((NumericQuestion)a108).setUnitCategory(moneyUnits);
    a108.setOrderIndex(0);
    ((NumericQuestion)a108).setDefaultUnit(getUnitBySymbol("EUR"));

    // cleanup the driver
    Driver a108_driver = driverService.findByName("A108");
    if (a108_driver != null) {
        driverService.remove(a108_driver);
    }

    ((NumericQuestion)a108).setDriver(null);

    JPA.em().persist(a108);
}



    }
    private void createQuestionA116() {
        // == A116
        // Catégorie de vol

        a116 = (StringQuestion) questionService.findByCode(QuestionCode.A116);
if (a116 == null) {
    a116 = new StringQuestion(a115, 0, QuestionCode.A116, null);
    JPA.em().persist(a116);
} else {
    ((StringQuestion)a116).setDefaultValue(null);
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

    }
    private void createQuestionA117() {
        // == A117
        // Type de vol

        a117 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A117);
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
    ((ValueSelectionQuestion)a117).setCodeList(CodeList.TYPEVOL);
    JPA.em().persist(a117);
}

    }
    private void createQuestionA118() {
        // == A118
        // Classe du vol

        a118 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A118);
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
    ((ValueSelectionQuestion)a118).setCodeList(CodeList.CATEGORIEVOL);
    JPA.em().persist(a118);
}

    }
    private void createQuestionA119() {
        // == A119
        // Nombre total de passagers pour cette catégorie de vols

        a119 = (IntegerQuestion) questionService.findByCode(QuestionCode.A119);
if (a119 == null) {
    a119 = new IntegerQuestion(a115, 0, QuestionCode.A119, null);
    JPA.em().persist(a119);

    // cleanup the driver
    Driver a119_driver = driverService.findByName("A119");
    if (a119_driver != null) {
        driverService.remove(a119_driver);
    }

} else {
    if (!a119.getQuestionSet().equals(a115) && a115.getQuestions().contains(a119)) {
        a115.getQuestions().remove(a119);
        JPA.em().persist(a115);
    }
    if (a119.getQuestionSet().equals(a115) && !a115.getQuestions().contains(a119)) {
        a115.getQuestions().add(a119);
        JPA.em().persist(a115);
    }
    a119.setOrderIndex(0);
    ((NumericQuestion)a119).setUnitCategory(null);

    // cleanup the driver
    Driver a119_driver = driverService.findByName("A119");
    if (a119_driver != null) {
        driverService.remove(a119_driver);
    }

    ((NumericQuestion)a119).setDriver(null);

    JPA.em().persist(a119);
}

    }
    private void createQuestionA120() {
        // == A120
        // Distance totale (aller-retour)

        
a120 = (DoubleQuestion) questionService.findByCode(QuestionCode.A120);
if (a120 == null) {
    a120 = new DoubleQuestion( a115, 0, QuestionCode.A120, lengthUnits, getUnitBySymbol("km") );
    JPA.em().persist(a120);

    // cleanup the driver
    Driver a120_driver = driverService.findByName("A120");
    if (a120_driver != null) {
        driverService.remove(a120_driver);
    }

} else {
    if (!a120.getQuestionSet().equals(a115) && a115.getQuestions().contains(a120)) {
        a115.getQuestions().remove(a120);
        JPA.em().persist(a115);
    }
    if (a120.getQuestionSet().equals(a115) && !a115.getQuestions().contains(a120)) {
        a115.getQuestions().add(a120);
        JPA.em().persist(a115);
    }
    ((NumericQuestion)a120).setUnitCategory(lengthUnits);
    a120.setOrderIndex(0);
    ((NumericQuestion)a120).setDefaultUnit(getUnitBySymbol("km"));

    // cleanup the driver
    Driver a120_driver = driverService.findByName("A120");
    if (a120_driver != null) {
        driverService.remove(a120_driver);
    }

    ((NumericQuestion)a120).setDriver(null);

    JPA.em().persist(a120);
}



    }
    private void createQuestionA122() {
        // == A122
        // % des employés qui réalisent des déplacements en avion

        a122 = (PercentageQuestion) questionService.findByCode(QuestionCode.A122);
if (a122 == null) {
    a122 = new PercentageQuestion(a121, 0, QuestionCode.A122);
    JPA.em().persist(a122);

    // cleanup the driver
    Driver a122_driver = driverService.findByName("A122");
    if (a122_driver != null) {
        driverService.remove(a122_driver);
    }

} else {
    if (!a122.getQuestionSet().equals(a121) && a121.getQuestions().contains(a122)) {
        a121.getQuestions().remove(a122);
        JPA.em().persist(a121);
    }
    if (a122.getQuestionSet().equals(a121) && !a121.getQuestions().contains(a122)) {
        a121.getQuestions().add(a122);
        JPA.em().persist(a121);
    }
    a122.setOrderIndex(0);

    // cleanup the driver
    Driver a122_driver = driverService.findByName("A122");
    if (a122_driver != null) {
        driverService.remove(a122_driver);
    }

    ((NumericQuestion)a122).setDriver(null);

    JPA.em().persist(a122);
}

    }
    private void createQuestionA123() {
        // == A123
        // Connaissez-vous la distance totale (aller-retour) parcourue en avion?

        a123 = (BooleanQuestion) questionService.findByCode(QuestionCode.A123);
if (a123 == null) {
    a123 = new BooleanQuestion(a121, 0, QuestionCode.A123, null);
    JPA.em().persist(a123);
} else {
    ((BooleanQuestion)a123).setDefaultValue(null);
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

    }
    private void createQuestionA124() {
        // == A124
        // Les voyages ont-ils lieu en Europe?

        a124 = (BooleanQuestion) questionService.findByCode(QuestionCode.A124);
if (a124 == null) {
    a124 = new BooleanQuestion(a121, 0, QuestionCode.A124, null);
    JPA.em().persist(a124);
} else {
    ((BooleanQuestion)a124).setDefaultValue(null);
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

    }
    private void createQuestionA125() {
        // == A125
        // Distance totale (aller-retour) assignée à chaque employé voyageant

        
a125 = (DoubleQuestion) questionService.findByCode(QuestionCode.A125);
if (a125 == null) {
    a125 = new DoubleQuestion( a121, 0, QuestionCode.A125, lengthUnits, getUnitBySymbol("km") );
    JPA.em().persist(a125);

    // cleanup the driver
    Driver a125_driver = driverService.findByName("A125");
    if (a125_driver != null) {
        driverService.remove(a125_driver);
    }

} else {
    if (!a125.getQuestionSet().equals(a121) && a121.getQuestions().contains(a125)) {
        a121.getQuestions().remove(a125);
        JPA.em().persist(a121);
    }
    if (a125.getQuestionSet().equals(a121) && !a121.getQuestions().contains(a125)) {
        a121.getQuestions().add(a125);
        JPA.em().persist(a121);
    }
    ((NumericQuestion)a125).setUnitCategory(lengthUnits);
    a125.setOrderIndex(0);
    ((NumericQuestion)a125).setDefaultUnit(getUnitBySymbol("km"));

    // cleanup the driver
    Driver a125_driver = driverService.findByName("A125");
    if (a125_driver != null) {
        driverService.remove(a125_driver);
    }

    ((NumericQuestion)a125).setDriver(null);

    JPA.em().persist(a125);
}



    }
    private void createQuestionA126() {
        // == A126
        // Distance totale (aller-retour) assignée à chaque employé voyageant

        
a126 = (DoubleQuestion) questionService.findByCode(QuestionCode.A126);
if (a126 == null) {
    a126 = new DoubleQuestion( a121, 0, QuestionCode.A126, lengthUnits, getUnitBySymbol("km") );
    JPA.em().persist(a126);

    // cleanup the driver
    Driver a126_driver = driverService.findByName("A126");
    if (a126_driver != null) {
        driverService.remove(a126_driver);
    }

} else {
    if (!a126.getQuestionSet().equals(a121) && a121.getQuestions().contains(a126)) {
        a121.getQuestions().remove(a126);
        JPA.em().persist(a121);
    }
    if (a126.getQuestionSet().equals(a121) && !a121.getQuestions().contains(a126)) {
        a121.getQuestions().add(a126);
        JPA.em().persist(a121);
    }
    ((NumericQuestion)a126).setUnitCategory(lengthUnits);
    a126.setOrderIndex(0);
    ((NumericQuestion)a126).setDefaultUnit(getUnitBySymbol("km"));

    // cleanup the driver
    Driver a126_driver = driverService.findByName("A126");
    if (a126_driver != null) {
        driverService.remove(a126_driver);
    }

    ((NumericQuestion)a126).setDriver(null);

    JPA.em().persist(a126);
}



    }
    private void createQuestionA127() {
        // == A127
        // Distance totale (aller-retour) assignée à chaque employé voyageant

        
a127 = (DoubleQuestion) questionService.findByCode(QuestionCode.A127);
if (a127 == null) {
    a127 = new DoubleQuestion( a121, 0, QuestionCode.A127, lengthUnits, getUnitBySymbol("km") );
    JPA.em().persist(a127);

    // cleanup the driver
    Driver a127_driver = driverService.findByName("A127");
    if (a127_driver != null) {
        driverService.remove(a127_driver);
    }

} else {
    if (!a127.getQuestionSet().equals(a121) && a121.getQuestions().contains(a127)) {
        a121.getQuestions().remove(a127);
        JPA.em().persist(a121);
    }
    if (a127.getQuestionSet().equals(a121) && !a121.getQuestions().contains(a127)) {
        a121.getQuestions().add(a127);
        JPA.em().persist(a121);
    }
    ((NumericQuestion)a127).setUnitCategory(lengthUnits);
    a127.setOrderIndex(0);
    ((NumericQuestion)a127).setDefaultUnit(getUnitBySymbol("km"));

    // cleanup the driver
    Driver a127_driver = driverService.findByName("A127");
    if (a127_driver != null) {
        driverService.remove(a127_driver);
    }

    ((NumericQuestion)a127).setDriver(null);

    JPA.em().persist(a127);
}



    }
    private void createQuestionA206() {
        // == A206
        // Pièces documentaires liées aux achats

        a206 = (DocumentQuestion) questionService.findByCode(QuestionCode.A206);
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

    }
    private void createQuestionA210() {
        // == A210
        // Poste d'achat

        a210 = (StringQuestion) questionService.findByCode(QuestionCode.A210);
if (a210 == null) {
    a210 = new StringQuestion(a209, 0, QuestionCode.A210, null);
    JPA.em().persist(a210);
} else {
    ((StringQuestion)a210).setDefaultValue(null);
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

    }
    private void createQuestionA211() {
        // == A211
        // Famille de matériau (ou service)

        a211 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A211);
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
    ((ValueSelectionQuestion)a211).setCodeList(CodeList.TYPEACHAT);
    JPA.em().persist(a211);
}

    }
    private void createQuestionA212() {
        // == A212
        // Type de matériau (ou service)

        a212 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A212);
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
    ((ValueSelectionQuestion)a212).setCodeList(CodeList.ACHATMETAL);
    JPA.em().persist(a212);
}

    }
    private void createQuestionA213() {
        // == A213
        // Type de matériau (ou service)

        a213 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A213);
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
    ((ValueSelectionQuestion)a213).setCodeList(CodeList.ACHATPLASTIQUE);
    JPA.em().persist(a213);
}

    }
    private void createQuestionA214() {
        // == A214
        // Type de matériau (ou service)

        a214 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A214);
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
    ((ValueSelectionQuestion)a214).setCodeList(CodeList.ACHATPAPIER);
    JPA.em().persist(a214);
}

    }
    private void createQuestionA215() {
        // == A215
        // Type de matériau (ou service)

        a215 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A215);
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
    ((ValueSelectionQuestion)a215).setCodeList(CodeList.ACHATVERRE);
    JPA.em().persist(a215);
}

    }
    private void createQuestionA216() {
        // == A216
        // Type de matériau (ou service)

        a216 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A216);
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
    ((ValueSelectionQuestion)a216).setCodeList(CodeList.ACHATCHIMIQUE);
    JPA.em().persist(a216);
}

    }
    private void createQuestionA217() {
        // == A217
        // Type de matériau (ou service)

        a217 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A217);
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
    ((ValueSelectionQuestion)a217).setCodeList(CodeList.ACHATROUTE);
    JPA.em().persist(a217);
}

    }
    private void createQuestionA218() {
        // == A218
        // Type de matériau (ou service)

        a218 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A218);
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
    ((ValueSelectionQuestion)a218).setCodeList(CodeList.ACHATAGRO);
    JPA.em().persist(a218);
}

    }
    private void createQuestionA219() {
        // == A219
        // Type de matériau (ou service)

        a219 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A219);
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
    ((ValueSelectionQuestion)a219).setCodeList(CodeList.ACHATSERVICE);
    JPA.em().persist(a219);
}

    }
    private void createQuestionA220() {
        // == A220
        // Taux de recyclé

        a220 = (PercentageQuestion) questionService.findByCode(QuestionCode.A220);
if (a220 == null) {
    a220 = new PercentageQuestion(a209, 0, QuestionCode.A220);
    JPA.em().persist(a220);

    // cleanup the driver
    Driver a220_driver = driverService.findByName("A220");
    if (a220_driver != null) {
        driverService.remove(a220_driver);
    }

} else {
    if (!a220.getQuestionSet().equals(a209) && a209.getQuestions().contains(a220)) {
        a209.getQuestions().remove(a220);
        JPA.em().persist(a209);
    }
    if (a220.getQuestionSet().equals(a209) && !a209.getQuestions().contains(a220)) {
        a209.getQuestions().add(a220);
        JPA.em().persist(a209);
    }
    a220.setOrderIndex(0);

    // cleanup the driver
    Driver a220_driver = driverService.findByName("A220");
    if (a220_driver != null) {
        driverService.remove(a220_driver);
    }

    ((NumericQuestion)a220).setDriver(null);

    JPA.em().persist(a220);
}

    }
    private void createQuestionA221() {
        // == A221
        // Quantité

        
a221 = (DoubleQuestion) questionService.findByCode(QuestionCode.A221);
if (a221 == null) {
    a221 = new DoubleQuestion( a209, 0, QuestionCode.A221, massUnits, getUnitBySymbol("t") );
    JPA.em().persist(a221);

    // cleanup the driver
    Driver a221_driver = driverService.findByName("A221");
    if (a221_driver != null) {
        driverService.remove(a221_driver);
    }

} else {
    if (!a221.getQuestionSet().equals(a209) && a209.getQuestions().contains(a221)) {
        a209.getQuestions().remove(a221);
        JPA.em().persist(a209);
    }
    if (a221.getQuestionSet().equals(a209) && !a209.getQuestions().contains(a221)) {
        a209.getQuestions().add(a221);
        JPA.em().persist(a209);
    }
    ((NumericQuestion)a221).setUnitCategory(massUnits);
    a221.setOrderIndex(0);
    ((NumericQuestion)a221).setDefaultUnit(getUnitBySymbol("t"));


    // cleanup the driver
    Driver a221_driver = driverService.findByName("A221");
    if (a221_driver != null) {
        driverService.remove(a221_driver);
    }

    ((NumericQuestion)a221).setDriver(null);

    JPA.em().persist(a221);
}



    }
    private void createQuestionA222() {
        // == A222
        // Quantité

        
a222 = (DoubleQuestion) questionService.findByCode(QuestionCode.A222);
if (a222 == null) {
    a222 = new DoubleQuestion( a209, 0, QuestionCode.A222, moneyUnits, getUnitBySymbol("EUR") );
    JPA.em().persist(a222);

    // cleanup the driver
    Driver a222_driver = driverService.findByName("A222");
    if (a222_driver != null) {
        driverService.remove(a222_driver);
    }

} else {
    if (!a222.getQuestionSet().equals(a209) && a209.getQuestions().contains(a222)) {
        a209.getQuestions().remove(a222);
        JPA.em().persist(a209);
    }
    if (a222.getQuestionSet().equals(a209) && !a209.getQuestions().contains(a222)) {
        a209.getQuestions().add(a222);
        JPA.em().persist(a209);
    }
    ((NumericQuestion)a222).setUnitCategory(moneyUnits);
    a222.setOrderIndex(0);
    ((NumericQuestion)a222).setDefaultUnit(getUnitBySymbol("EUR"));

    // cleanup the driver
    Driver a222_driver = driverService.findByName("A222");
    if (a222_driver != null) {
        driverService.remove(a222_driver);
    }

    ((NumericQuestion)a222).setDriver(null);

    JPA.em().persist(a222);
}



    }
    private void createQuestionA225() {
        // == A225
        // Poste d'achat

        a225 = (StringQuestion) questionService.findByCode(QuestionCode.A225);
if (a225 == null) {
    a225 = new StringQuestion(a224, 0, QuestionCode.A225, null);
    JPA.em().persist(a225);
} else {
    ((StringQuestion)a225).setDefaultValue(null);
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

    }
    private void createQuestionA226() {
        // == A226
        // Quantité

        a226 = (IntegerQuestion) questionService.findByCode(QuestionCode.A226);
if (a226 == null) {
    a226 = new IntegerQuestion(a224, 0, QuestionCode.A226, null);
    JPA.em().persist(a226);

    // cleanup the driver
    Driver a226_driver = driverService.findByName("A226");
    if (a226_driver != null) {
        driverService.remove(a226_driver);
    }

} else {
    if (!a226.getQuestionSet().equals(a224) && a224.getQuestions().contains(a226)) {
        a224.getQuestions().remove(a226);
        JPA.em().persist(a224);
    }
    if (a226.getQuestionSet().equals(a224) && !a224.getQuestions().contains(a226)) {
        a224.getQuestions().add(a226);
        JPA.em().persist(a224);
    }
    a226.setOrderIndex(0);
    ((NumericQuestion)a226).setUnitCategory(null);

    // cleanup the driver
    Driver a226_driver = driverService.findByName("A226");
    if (a226_driver != null) {
        driverService.remove(a226_driver);
    }

    ((NumericQuestion)a226).setDriver(null);

    JPA.em().persist(a226);
}

    }
    private void createQuestionA227() {
        // == A227
        // Unité dans laquelle s'exprime cette quantité

        a227 = (StringQuestion) questionService.findByCode(QuestionCode.A227);
if (a227 == null) {
    a227 = new StringQuestion(a224, 0, QuestionCode.A227, null);
    JPA.em().persist(a227);
} else {
    ((StringQuestion)a227).setDefaultValue(null);
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

    }
    private void createQuestionA228() {
        // == A228
        // Facteur d'émission en tCO2e par unité ci-dessus

        a228 = (IntegerQuestion) questionService.findByCode(QuestionCode.A228);
if (a228 == null) {
    a228 = new IntegerQuestion(a224, 0, QuestionCode.A228, null);
    JPA.em().persist(a228);

    // cleanup the driver
    Driver a228_driver = driverService.findByName("A228");
    if (a228_driver != null) {
        driverService.remove(a228_driver);
    }

} else {
    if (!a228.getQuestionSet().equals(a224) && a224.getQuestions().contains(a228)) {
        a224.getQuestions().remove(a228);
        JPA.em().persist(a224);
    }
    if (a228.getQuestionSet().equals(a224) && !a224.getQuestions().contains(a228)) {
        a224.getQuestions().add(a228);
        JPA.em().persist(a224);
    }
    a228.setOrderIndex(0);
    ((NumericQuestion)a228).setUnitCategory(null);

    // cleanup the driver
    Driver a228_driver = driverService.findByName("A228");
    if (a228_driver != null) {
        driverService.remove(a228_driver);
    }

    ((NumericQuestion)a228).setDriver(null);

    JPA.em().persist(a228);
}

    }
    private void createQuestionA129() {
        // == A129
        // Pièces documentaires liées au transport et stockage amont

        a129 = (DocumentQuestion) questionService.findByCode(QuestionCode.A129);
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

    }
    private void createQuestionA133() {
        // == A133
        // Consommation d'essence

        
a133 = (DoubleQuestion) questionService.findByCode(QuestionCode.A133);
if (a133 == null) {
    a133 = new DoubleQuestion( a132, 0, QuestionCode.A133, volumeUnits, getUnitBySymbol("l") );
    JPA.em().persist(a133);

    // cleanup the driver
    Driver a133_driver = driverService.findByName("A133");
    if (a133_driver != null) {
        driverService.remove(a133_driver);
    }

} else {
    if (!a133.getQuestionSet().equals(a132) && a132.getQuestions().contains(a133)) {
        a132.getQuestions().remove(a133);
        JPA.em().persist(a132);
    }
    if (a133.getQuestionSet().equals(a132) && !a132.getQuestions().contains(a133)) {
        a132.getQuestions().add(a133);
        JPA.em().persist(a132);
    }
    ((NumericQuestion)a133).setUnitCategory(volumeUnits);
    a133.setOrderIndex(0);
    ((NumericQuestion)a133).setDefaultUnit(getUnitBySymbol("l"));

    // cleanup the driver
    Driver a133_driver = driverService.findByName("A133");
    if (a133_driver != null) {
        driverService.remove(a133_driver);
    }

    ((NumericQuestion)a133).setDriver(null);

    JPA.em().persist(a133);
}



    }
    private void createQuestionA134() {
        // == A134
        // Consommation de diesel

        
a134 = (DoubleQuestion) questionService.findByCode(QuestionCode.A134);
if (a134 == null) {
    a134 = new DoubleQuestion( a132, 0, QuestionCode.A134, volumeUnits, getUnitBySymbol("l") );
    JPA.em().persist(a134);

    // cleanup the driver
    Driver a134_driver = driverService.findByName("A134");
    if (a134_driver != null) {
        driverService.remove(a134_driver);
    }

} else {
    if (!a134.getQuestionSet().equals(a132) && a132.getQuestions().contains(a134)) {
        a132.getQuestions().remove(a134);
        JPA.em().persist(a132);
    }
    if (a134.getQuestionSet().equals(a132) && !a132.getQuestions().contains(a134)) {
        a132.getQuestions().add(a134);
        JPA.em().persist(a132);
    }
    ((NumericQuestion)a134).setUnitCategory(volumeUnits);
    a134.setOrderIndex(0);
    ((NumericQuestion)a134).setDefaultUnit(getUnitBySymbol("l"));

    // cleanup the driver
    Driver a134_driver = driverService.findByName("A134");
    if (a134_driver != null) {
        driverService.remove(a134_driver);
    }

    ((NumericQuestion)a134).setDriver(null);

    JPA.em().persist(a134);
}



    }
    private void createQuestionA135() {
        // == A135
        // Consommation de gaz de pétrole liquéfié (GPL)

        
a135 = (DoubleQuestion) questionService.findByCode(QuestionCode.A135);
if (a135 == null) {
    a135 = new DoubleQuestion( a132, 0, QuestionCode.A135, volumeUnits, getUnitBySymbol("l") );
    JPA.em().persist(a135);

    // cleanup the driver
    Driver a135_driver = driverService.findByName("A135");
    if (a135_driver != null) {
        driverService.remove(a135_driver);
    }

} else {
    if (!a135.getQuestionSet().equals(a132) && a132.getQuestions().contains(a135)) {
        a132.getQuestions().remove(a135);
        JPA.em().persist(a132);
    }
    if (a135.getQuestionSet().equals(a132) && !a132.getQuestions().contains(a135)) {
        a132.getQuestions().add(a135);
        JPA.em().persist(a132);
    }
    ((NumericQuestion)a135).setUnitCategory(volumeUnits);
    a135.setOrderIndex(0);
    ((NumericQuestion)a135).setDefaultUnit(getUnitBySymbol("l"));

    // cleanup the driver
    Driver a135_driver = driverService.findByName("A135");
    if (a135_driver != null) {
        driverService.remove(a135_driver);
    }

    ((NumericQuestion)a135).setDriver(null);

    JPA.em().persist(a135);
}



    }
    private void createQuestionA136() {
        // == A136
        // Est-ce les marchandises sont refrigérées durant le transport?

        a136 = (BooleanQuestion) questionService.findByCode(QuestionCode.A136);
if (a136 == null) {
    a136 = new BooleanQuestion(a132, 0, QuestionCode.A136, null);
    JPA.em().persist(a136);
} else {
    ((BooleanQuestion)a136).setDefaultValue(null);
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

    }
    private void createQuestionA137() {
        // == A137
        // Type de Gaz

        a137 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A137);
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
    ((ValueSelectionQuestion)a137).setCodeList(CodeList.FRIGORIGENEBASE);
    JPA.em().persist(a137);
}

    }
    private void createQuestionA138() {
        // == A138
        // Connaissez-vous la quantité annuelle de recharge de ce gaz?

        a138 = (BooleanQuestion) questionService.findByCode(QuestionCode.A138);
if (a138 == null) {
    a138 = new BooleanQuestion(a132, 0, QuestionCode.A138, null);
    JPA.em().persist(a138);
} else {
    ((BooleanQuestion)a138).setDefaultValue(null);
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

    }
    private void createQuestionA139() {
        // == A139
        // Quantité de recharge annuelle

        
a139 = (DoubleQuestion) questionService.findByCode(QuestionCode.A139);
if (a139 == null) {
    a139 = new DoubleQuestion( a132, 0, QuestionCode.A139, massUnits, getUnitBySymbol("kg") );
    JPA.em().persist(a139);

    // cleanup the driver
    Driver a139_driver = driverService.findByName("A139");
    if (a139_driver != null) {
        driverService.remove(a139_driver);
    }

} else {
    if (!a139.getQuestionSet().equals(a132) && a132.getQuestions().contains(a139)) {
        a132.getQuestions().remove(a139);
        JPA.em().persist(a132);
    }
    if (a139.getQuestionSet().equals(a132) && !a132.getQuestions().contains(a139)) {
        a132.getQuestions().add(a139);
        JPA.em().persist(a132);
    }
    ((NumericQuestion)a139).setUnitCategory(massUnits);
    a139.setOrderIndex(0);
    ((NumericQuestion)a139).setDefaultUnit(getUnitBySymbol("kg"));


    // cleanup the driver
    Driver a139_driver = driverService.findByName("A139");
    if (a139_driver != null) {
        driverService.remove(a139_driver);
    }

    ((NumericQuestion)a139).setDriver(null);

    JPA.em().persist(a139);
}



    }
    private void createQuestionA500() {
        // == A500
        // Quantité de recharge annuelle

        
a500 = (DoubleQuestion) questionService.findByCode(QuestionCode.A500);
if (a500 == null) {
    a500 = new DoubleQuestion( a132, 0, QuestionCode.A500, massUnits, getUnitBySymbol("kg") );
    JPA.em().persist(a500);

    // cleanup the driver
    Driver a500_driver = driverService.findByName("A500");
    if (a500_driver != null) {
        driverService.remove(a500_driver);
    }

} else {
    if (!a500.getQuestionSet().equals(a132) && a132.getQuestions().contains(a500)) {
        a132.getQuestions().remove(a500);
        JPA.em().persist(a132);
    }
    if (a500.getQuestionSet().equals(a132) && !a132.getQuestions().contains(a500)) {
        a132.getQuestions().add(a500);
        JPA.em().persist(a132);
    }
    ((NumericQuestion)a500).setUnitCategory(massUnits);
    a500.setOrderIndex(0);
    ((NumericQuestion)a500).setDefaultUnit(getUnitBySymbol("kg"));


    // cleanup the driver
    Driver a500_driver = driverService.findByName("A500");
    if (a500_driver != null) {
        driverService.remove(a500_driver);
    }

    ((NumericQuestion)a500).setDriver(null);

    JPA.em().persist(a500);
}



    }
    private void createQuestionA143() {
        // == A143
        // Nom du produit transporté

        a143 = (StringQuestion) questionService.findByCode(QuestionCode.A143);
if (a143 == null) {
    a143 = new StringQuestion(a142, 0, QuestionCode.A143, null);
    JPA.em().persist(a143);
} else {
    ((StringQuestion)a143).setDefaultValue(null);
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

    }
    private void createQuestionA145() {
        // == A145
        // Poids total transporté sur l'année de bilan

        
a145 = (DoubleQuestion) questionService.findByCode(QuestionCode.A145);
if (a145 == null) {
    a145 = new DoubleQuestion( a142, 0, QuestionCode.A145, massUnits, getUnitBySymbol("t") );
    JPA.em().persist(a145);

    // cleanup the driver
    Driver a145_driver = driverService.findByName("A145");
    if (a145_driver != null) {
        driverService.remove(a145_driver);
    }

} else {
    if (!a145.getQuestionSet().equals(a142) && a142.getQuestions().contains(a145)) {
        a142.getQuestions().remove(a145);
        JPA.em().persist(a142);
    }
    if (a145.getQuestionSet().equals(a142) && !a142.getQuestions().contains(a145)) {
        a142.getQuestions().add(a145);
        JPA.em().persist(a142);
    }
    ((NumericQuestion)a145).setUnitCategory(massUnits);
    a145.setOrderIndex(0);
    ((NumericQuestion)a145).setDefaultUnit(getUnitBySymbol("t"));


    // cleanup the driver
    Driver a145_driver = driverService.findByName("A145");
    if (a145_driver != null) {
        driverService.remove(a145_driver);
    }

    ((NumericQuestion)a145).setDriver(null);

    JPA.em().persist(a145);
}



    }
    private void createQuestionA146() {
        // == A146
        // Distance entre le point d'enlèvement et de livraison du produit

        
a146 = (DoubleQuestion) questionService.findByCode(QuestionCode.A146);
if (a146 == null) {
    a146 = new DoubleQuestion( a142, 0, QuestionCode.A146, lengthUnits, getUnitBySymbol("km") );
    JPA.em().persist(a146);

    // cleanup the driver
    Driver a146_driver = driverService.findByName("A146");
    if (a146_driver != null) {
        driverService.remove(a146_driver);
    }

} else {
    if (!a146.getQuestionSet().equals(a142) && a142.getQuestions().contains(a146)) {
        a142.getQuestions().remove(a146);
        JPA.em().persist(a142);
    }
    if (a146.getQuestionSet().equals(a142) && !a142.getQuestions().contains(a146)) {
        a142.getQuestions().add(a146);
        JPA.em().persist(a142);
    }
    ((NumericQuestion)a146).setUnitCategory(lengthUnits);
    a146.setOrderIndex(0);
    ((NumericQuestion)a146).setDefaultUnit(getUnitBySymbol("km"));

    // cleanup the driver
    Driver a146_driver = driverService.findByName("A146");
    if (a146_driver != null) {
        driverService.remove(a146_driver);
    }

    ((NumericQuestion)a146).setDriver(null);

    JPA.em().persist(a146);
}



    }
    private void createQuestionA147() {
        // == A147
        // % de distance effectuée par transport routier local par camion

        a147 = (PercentageQuestion) questionService.findByCode(QuestionCode.A147);
if (a147 == null) {
    a147 = new PercentageQuestion(a142, 0, QuestionCode.A147);
    JPA.em().persist(a147);

    // cleanup the driver
    Driver a147_driver = driverService.findByName("A147");
    if (a147_driver != null) {
        driverService.remove(a147_driver);
    }

} else {
    if (!a147.getQuestionSet().equals(a142) && a142.getQuestions().contains(a147)) {
        a142.getQuestions().remove(a147);
        JPA.em().persist(a142);
    }
    if (a147.getQuestionSet().equals(a142) && !a142.getQuestions().contains(a147)) {
        a142.getQuestions().add(a147);
        JPA.em().persist(a142);
    }
    a147.setOrderIndex(0);

    // cleanup the driver
    Driver a147_driver = driverService.findByName("A147");
    if (a147_driver != null) {
        driverService.remove(a147_driver);
    }

    ((NumericQuestion)a147).setDriver(null);

    JPA.em().persist(a147);
}

    }
    private void createQuestionA148() {
        // == A148
        // % de distance effectuée par transport routier local par camionnette

        a148 = (PercentageQuestion) questionService.findByCode(QuestionCode.A148);
if (a148 == null) {
    a148 = new PercentageQuestion(a142, 0, QuestionCode.A148);
    JPA.em().persist(a148);

    // cleanup the driver
    Driver a148_driver = driverService.findByName("A148");
    if (a148_driver != null) {
        driverService.remove(a148_driver);
    }

} else {
    if (!a148.getQuestionSet().equals(a142) && a142.getQuestions().contains(a148)) {
        a142.getQuestions().remove(a148);
        JPA.em().persist(a142);
    }
    if (a148.getQuestionSet().equals(a142) && !a142.getQuestions().contains(a148)) {
        a142.getQuestions().add(a148);
        JPA.em().persist(a142);
    }
    a148.setOrderIndex(0);

    // cleanup the driver
    Driver a148_driver = driverService.findByName("A148");
    if (a148_driver != null) {
        driverService.remove(a148_driver);
    }

    ((NumericQuestion)a148).setDriver(null);

    JPA.em().persist(a148);
}

    }
    private void createQuestionA149() {
        // == A149
        // % de distance effectuée par transport routier international

        a149 = (PercentageQuestion) questionService.findByCode(QuestionCode.A149);
if (a149 == null) {
    a149 = new PercentageQuestion(a142, 0, QuestionCode.A149);
    JPA.em().persist(a149);

    // cleanup the driver
    Driver a149_driver = driverService.findByName("A149");
    if (a149_driver != null) {
        driverService.remove(a149_driver);
    }

} else {
    if (!a149.getQuestionSet().equals(a142) && a142.getQuestions().contains(a149)) {
        a142.getQuestions().remove(a149);
        JPA.em().persist(a142);
    }
    if (a149.getQuestionSet().equals(a142) && !a142.getQuestions().contains(a149)) {
        a142.getQuestions().add(a149);
        JPA.em().persist(a142);
    }
    a149.setOrderIndex(0);

    // cleanup the driver
    Driver a149_driver = driverService.findByName("A149");
    if (a149_driver != null) {
        driverService.remove(a149_driver);
    }

    ((NumericQuestion)a149).setDriver(null);

    JPA.em().persist(a149);
}

    }
    private void createQuestionA150() {
        // == A150
        // % de distance effectuée par voie ferroviaire

        a150 = (PercentageQuestion) questionService.findByCode(QuestionCode.A150);
if (a150 == null) {
    a150 = new PercentageQuestion(a142, 0, QuestionCode.A150);
    JPA.em().persist(a150);

    // cleanup the driver
    Driver a150_driver = driverService.findByName("A150");
    if (a150_driver != null) {
        driverService.remove(a150_driver);
    }

} else {
    if (!a150.getQuestionSet().equals(a142) && a142.getQuestions().contains(a150)) {
        a142.getQuestions().remove(a150);
        JPA.em().persist(a142);
    }
    if (a150.getQuestionSet().equals(a142) && !a142.getQuestions().contains(a150)) {
        a142.getQuestions().add(a150);
        JPA.em().persist(a142);
    }
    a150.setOrderIndex(0);

    // cleanup the driver
    Driver a150_driver = driverService.findByName("A150");
    if (a150_driver != null) {
        driverService.remove(a150_driver);
    }

    ((NumericQuestion)a150).setDriver(null);

    JPA.em().persist(a150);
}

    }
    private void createQuestionA151() {
        // == A151
        // % de distance effectuée par voie maritime

        a151 = (PercentageQuestion) questionService.findByCode(QuestionCode.A151);
if (a151 == null) {
    a151 = new PercentageQuestion(a142, 0, QuestionCode.A151);
    JPA.em().persist(a151);

    // cleanup the driver
    Driver a151_driver = driverService.findByName("A151");
    if (a151_driver != null) {
        driverService.remove(a151_driver);
    }

} else {
    if (!a151.getQuestionSet().equals(a142) && a142.getQuestions().contains(a151)) {
        a142.getQuestions().remove(a151);
        JPA.em().persist(a142);
    }
    if (a151.getQuestionSet().equals(a142) && !a142.getQuestions().contains(a151)) {
        a142.getQuestions().add(a151);
        JPA.em().persist(a142);
    }
    a151.setOrderIndex(0);

    // cleanup the driver
    Driver a151_driver = driverService.findByName("A151");
    if (a151_driver != null) {
        driverService.remove(a151_driver);
    }

    ((NumericQuestion)a151).setDriver(null);

    JPA.em().persist(a151);
}

    }
    private void createQuestionA152() {
        // == A152
        // % de distance effectuée par voie fluviale

        a152 = (PercentageQuestion) questionService.findByCode(QuestionCode.A152);
if (a152 == null) {
    a152 = new PercentageQuestion(a142, 0, QuestionCode.A152);
    JPA.em().persist(a152);

    // cleanup the driver
    Driver a152_driver = driverService.findByName("A152");
    if (a152_driver != null) {
        driverService.remove(a152_driver);
    }

} else {
    if (!a152.getQuestionSet().equals(a142) && a142.getQuestions().contains(a152)) {
        a142.getQuestions().remove(a152);
        JPA.em().persist(a142);
    }
    if (a152.getQuestionSet().equals(a142) && !a142.getQuestions().contains(a152)) {
        a142.getQuestions().add(a152);
        JPA.em().persist(a142);
    }
    a152.setOrderIndex(0);

    // cleanup the driver
    Driver a152_driver = driverService.findByName("A152");
    if (a152_driver != null) {
        driverService.remove(a152_driver);
    }

    ((NumericQuestion)a152).setDriver(null);

    JPA.em().persist(a152);
}

    }
    private void createQuestionA153() {
        // == A153
        // % de distance effectuée par transport aérien court courrier (<1000 km)

        a153 = (PercentageQuestion) questionService.findByCode(QuestionCode.A153);
if (a153 == null) {
    a153 = new PercentageQuestion(a142, 0, QuestionCode.A153);
    JPA.em().persist(a153);

    // cleanup the driver
    Driver a153_driver = driverService.findByName("A153");
    if (a153_driver != null) {
        driverService.remove(a153_driver);
    }

} else {
    if (!a153.getQuestionSet().equals(a142) && a142.getQuestions().contains(a153)) {
        a142.getQuestions().remove(a153);
        JPA.em().persist(a142);
    }
    if (a153.getQuestionSet().equals(a142) && !a142.getQuestions().contains(a153)) {
        a142.getQuestions().add(a153);
        JPA.em().persist(a142);
    }
    a153.setOrderIndex(0);

    // cleanup the driver
    Driver a153_driver = driverService.findByName("A153");
    if (a153_driver != null) {
        driverService.remove(a153_driver);
    }

    ((NumericQuestion)a153).setDriver(null);

    JPA.em().persist(a153);
}

    }
    private void createQuestionA154() {
        // == A154
        // % de distance effectuée par transport aérien moyen courrier (1000 à 4000 km)

        a154 = (PercentageQuestion) questionService.findByCode(QuestionCode.A154);
if (a154 == null) {
    a154 = new PercentageQuestion(a142, 0, QuestionCode.A154);
    JPA.em().persist(a154);

    // cleanup the driver
    Driver a154_driver = driverService.findByName("A154");
    if (a154_driver != null) {
        driverService.remove(a154_driver);
    }

} else {
    if (!a154.getQuestionSet().equals(a142) && a142.getQuestions().contains(a154)) {
        a142.getQuestions().remove(a154);
        JPA.em().persist(a142);
    }
    if (a154.getQuestionSet().equals(a142) && !a142.getQuestions().contains(a154)) {
        a142.getQuestions().add(a154);
        JPA.em().persist(a142);
    }
    a154.setOrderIndex(0);

    // cleanup the driver
    Driver a154_driver = driverService.findByName("A154");
    if (a154_driver != null) {
        driverService.remove(a154_driver);
    }

    ((NumericQuestion)a154).setDriver(null);

    JPA.em().persist(a154);
}

    }
    private void createQuestionA155() {
        // == A155
        // % de distance effectuée par transport aérien long courrier (> 4000 km)

        a155 = (PercentageQuestion) questionService.findByCode(QuestionCode.A155);
if (a155 == null) {
    a155 = new PercentageQuestion(a142, 0, QuestionCode.A155);
    JPA.em().persist(a155);

    // cleanup the driver
    Driver a155_driver = driverService.findByName("A155");
    if (a155_driver != null) {
        driverService.remove(a155_driver);
    }

} else {
    if (!a155.getQuestionSet().equals(a142) && a142.getQuestions().contains(a155)) {
        a142.getQuestions().remove(a155);
        JPA.em().persist(a142);
    }
    if (a155.getQuestionSet().equals(a142) && !a142.getQuestions().contains(a155)) {
        a142.getQuestions().add(a155);
        JPA.em().persist(a142);
    }
    a155.setOrderIndex(0);

    // cleanup the driver
    Driver a155_driver = driverService.findByName("A155");
    if (a155_driver != null) {
        driverService.remove(a155_driver);
    }

    ((NumericQuestion)a155).setDriver(null);

    JPA.em().persist(a155);
}

    }
    private void createQuestionA156() {
        // == A156
        // Total (supposé être égal à 100%)

        a156 = (PercentageQuestion) questionService.findByCode(QuestionCode.A156);
if (a156 == null) {
    a156 = new PercentageQuestion(a142, 0, QuestionCode.A156);
    JPA.em().persist(a156);

    // cleanup the driver
    Driver a156_driver = driverService.findByName("A156");
    if (a156_driver != null) {
        driverService.remove(a156_driver);
    }

} else {
    if (!a156.getQuestionSet().equals(a142) && a142.getQuestions().contains(a156)) {
        a142.getQuestions().remove(a156);
        JPA.em().persist(a142);
    }
    if (a156.getQuestionSet().equals(a142) && !a142.getQuestions().contains(a156)) {
        a142.getQuestions().add(a156);
        JPA.em().persist(a142);
    }
    a156.setOrderIndex(0);

    // cleanup the driver
    Driver a156_driver = driverService.findByName("A156");
    if (a156_driver != null) {
        driverService.remove(a156_driver);
    }

    ((NumericQuestion)a156).setDriver(null);

    JPA.em().persist(a156);
}

    }
    private void createQuestionA158() {
        // == A158
        // Quel est le poids total transporté sur toute l'année du bilan (tous produits confondus)?

        
a158 = (DoubleQuestion) questionService.findByCode(QuestionCode.A158);
if (a158 == null) {
    a158 = new DoubleQuestion( a157, 0, QuestionCode.A158, massUnits, getUnitBySymbol("t") );
    JPA.em().persist(a158);

    // cleanup the driver
    Driver a158_driver = driverService.findByName("A158");
    if (a158_driver != null) {
        driverService.remove(a158_driver);
    }

} else {
    if (!a158.getQuestionSet().equals(a157) && a157.getQuestions().contains(a158)) {
        a157.getQuestions().remove(a158);
        JPA.em().persist(a157);
    }
    if (a158.getQuestionSet().equals(a157) && !a157.getQuestions().contains(a158)) {
        a157.getQuestions().add(a158);
        JPA.em().persist(a157);
    }
    ((NumericQuestion)a158).setUnitCategory(massUnits);
    a158.setOrderIndex(0);
    ((NumericQuestion)a158).setDefaultUnit(getUnitBySymbol("t"));


    // cleanup the driver
    Driver a158_driver = driverService.findByName("A158");
    if (a158_driver != null) {
        driverService.remove(a158_driver);
    }

    ((NumericQuestion)a158).setDriver(null);

    JPA.em().persist(a158);
}



    }
    private void createQuestionA159() {
        // == A159
        // Quelle est la provenance géographique des produits?

        a159 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A159);
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
    ((ValueSelectionQuestion)a159).setCodeList(CodeList.PROVENANCESIMPLIFIEE);
    JPA.em().persist(a159);
}

    }
    private void createQuestionA160() {
        // == A160
        // Distance moyenne assignée entre le point d'enlèvement et le point de livraison

        
a160 = (DoubleQuestion) questionService.findByCode(QuestionCode.A160);
if (a160 == null) {
    a160 = new DoubleQuestion( a157, 0, QuestionCode.A160, lengthUnits, getUnitBySymbol("km") );
    JPA.em().persist(a160);

    // cleanup the driver
    Driver a160_driver = driverService.findByName("A160");
    if (a160_driver != null) {
        driverService.remove(a160_driver);
    }

} else {
    if (!a160.getQuestionSet().equals(a157) && a157.getQuestions().contains(a160)) {
        a157.getQuestions().remove(a160);
        JPA.em().persist(a157);
    }
    if (a160.getQuestionSet().equals(a157) && !a157.getQuestions().contains(a160)) {
        a157.getQuestions().add(a160);
        JPA.em().persist(a157);
    }
    ((NumericQuestion)a160).setUnitCategory(lengthUnits);
    a160.setOrderIndex(0);
    ((NumericQuestion)a160).setDefaultUnit(getUnitBySymbol("km"));

    // cleanup the driver
    Driver a160_driver = driverService.findByName("A160");
    if (a160_driver != null) {
        driverService.remove(a160_driver);
    }

    ((NumericQuestion)a160).setDriver(null);

    JPA.em().persist(a160);
}



    }
    private void createQuestionA161() {
        // == A161
        // Distance moyenne assignée entre le point d'enlèvement et le point de livraison

        
a161 = (DoubleQuestion) questionService.findByCode(QuestionCode.A161);
if (a161 == null) {
    a161 = new DoubleQuestion( a157, 0, QuestionCode.A161, lengthUnits, getUnitBySymbol("km") );
    JPA.em().persist(a161);

    // cleanup the driver
    Driver a161_driver = driverService.findByName("A161");
    if (a161_driver != null) {
        driverService.remove(a161_driver);
    }

} else {
    if (!a161.getQuestionSet().equals(a157) && a157.getQuestions().contains(a161)) {
        a157.getQuestions().remove(a161);
        JPA.em().persist(a157);
    }
    if (a161.getQuestionSet().equals(a157) && !a157.getQuestions().contains(a161)) {
        a157.getQuestions().add(a161);
        JPA.em().persist(a157);
    }
    ((NumericQuestion)a161).setUnitCategory(lengthUnits);
    a161.setOrderIndex(0);
    ((NumericQuestion)a161).setDefaultUnit(getUnitBySymbol("km"));

    // cleanup the driver
    Driver a161_driver = driverService.findByName("A161");
    if (a161_driver != null) {
        driverService.remove(a161_driver);
    }

    ((NumericQuestion)a161).setDriver(null);

    JPA.em().persist(a161);
}



    }
    private void createQuestionA162() {
        // == A162
        // Distance moyenne assignée entre le point d'enlèvement et le point de livraison

        
a162 = (DoubleQuestion) questionService.findByCode(QuestionCode.A162);
if (a162 == null) {
    a162 = new DoubleQuestion( a157, 0, QuestionCode.A162, lengthUnits, getUnitBySymbol("km") );
    JPA.em().persist(a162);

    // cleanup the driver
    Driver a162_driver = driverService.findByName("A162");
    if (a162_driver != null) {
        driverService.remove(a162_driver);
    }

} else {
    if (!a162.getQuestionSet().equals(a157) && a157.getQuestions().contains(a162)) {
        a157.getQuestions().remove(a162);
        JPA.em().persist(a157);
    }
    if (a162.getQuestionSet().equals(a157) && !a157.getQuestions().contains(a162)) {
        a157.getQuestions().add(a162);
        JPA.em().persist(a157);
    }
    ((NumericQuestion)a162).setUnitCategory(lengthUnits);
    a162.setOrderIndex(0);
    ((NumericQuestion)a162).setDefaultUnit(getUnitBySymbol("km"));

    // cleanup the driver
    Driver a162_driver = driverService.findByName("A162");
    if (a162_driver != null) {
        driverService.remove(a162_driver);
    }

    ((NumericQuestion)a162).setDriver(null);

    JPA.em().persist(a162);
}



    }
    private void createQuestionA165() {
        // == A165
        // Poste de consommation

        a165 = (StringQuestion) questionService.findByCode(QuestionCode.A165);
if (a165 == null) {
    a165 = new StringQuestion(a164, 0, QuestionCode.A165, null);
    JPA.em().persist(a165);
} else {
    ((StringQuestion)a165).setDefaultValue(null);
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

    }
    private void createQuestionA167() {
        // == A167
        // Combustible consommé en amont

        a167 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A167);
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
    ((ValueSelectionQuestion)a167).setCodeList(CodeList.COMBUSTIBLE);
    JPA.em().persist(a167);
}

    }
    private void createQuestionA168() {
        // == A168
        // Quantité

        
a168 = (DoubleQuestion) questionService.findByCode(QuestionCode.A168);
if (a168 == null) {
    a168 = new DoubleQuestion( a166, 0, QuestionCode.A168, energyUnits, getUnitBySymbol("kW.h") );
    JPA.em().persist(a168);

    // cleanup the driver
    Driver a168_driver = driverService.findByName("A168");
    if (a168_driver != null) {
        driverService.remove(a168_driver);
    }


} else {
    if (!a168.getQuestionSet().equals(a166) && a166.getQuestions().contains(a168)) {
        a166.getQuestions().remove(a168);
        JPA.em().persist(a166);
    }
    if (a168.getQuestionSet().equals(a166) && !a166.getQuestions().contains(a168)) {
        a166.getQuestions().add(a168);
        JPA.em().persist(a166);
    }
    ((NumericQuestion)a168).setUnitCategory(energyUnits);
    a168.setOrderIndex(0);
    ((NumericQuestion)a168).setDefaultUnit(getUnitBySymbol("kW.h"));

    // cleanup the driver
    Driver a168_driver = driverService.findByName("A168");
    if (a168_driver != null) {
        driverService.remove(a168_driver);
    }

    ((NumericQuestion)a168).setDriver(null);

    JPA.em().persist(a168);
}



    }
    private void createQuestionA1007() {
        // == A1007
        // Combustible consommé en amont

        a1007 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A1007);
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
    ((ValueSelectionQuestion)a1007).setCodeList(CodeList.COMBUSTIBLEVOLUME);
    JPA.em().persist(a1007);
}

    }
    private void createQuestionA1008() {
        // == A1008
        // Quantité

        
a1008 = (DoubleQuestion) questionService.findByCode(QuestionCode.A1008);
if (a1008 == null) {
    a1008 = new DoubleQuestion( a1006, 0, QuestionCode.A1008, volumeUnits, volumeUnits.getMainUnit() );
    JPA.em().persist(a1008);

    // cleanup the driver
    Driver a1008_driver = driverService.findByName("A1008");
    if (a1008_driver != null) {
        driverService.remove(a1008_driver);
    }

} else {
    if (!a1008.getQuestionSet().equals(a1006) && a1006.getQuestions().contains(a1008)) {
        a1006.getQuestions().remove(a1008);
        JPA.em().persist(a1006);
    }
    if (a1008.getQuestionSet().equals(a1006) && !a1006.getQuestions().contains(a1008)) {
        a1006.getQuestions().add(a1008);
        JPA.em().persist(a1006);
    }
    ((NumericQuestion)a1008).setUnitCategory(volumeUnits);
    a1008.setOrderIndex(0);
    ((NumericQuestion)a1008).setDefaultUnit(volumeUnits.getMainUnit());

    // cleanup the driver
    Driver a1008_driver = driverService.findByName("A1008");
    if (a1008_driver != null) {
        driverService.remove(a1008_driver);
    }

    ((NumericQuestion)a1008).setDriver(null);

    JPA.em().persist(a1008);
}



    }
    private void createQuestionA1010() {
        // == A1010
        // Combustible consommé en amont

        a1010 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A1010);
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
    ((ValueSelectionQuestion)a1010).setCodeList(CodeList.COMBUSTIBLEPOIDS);
    JPA.em().persist(a1010);
}

    }
    private void createQuestionA1011() {
        // == A1011
        // Quantité

        
a1011 = (DoubleQuestion) questionService.findByCode(QuestionCode.A1011);
if (a1011 == null) {
    a1011 = new DoubleQuestion( a1009, 0, QuestionCode.A1011, massUnits, massUnits.getMainUnit() );
    JPA.em().persist(a1011);

    // cleanup the driver
    Driver a1011_driver = driverService.findByName("A1011");
    if (a1011_driver != null) {
        driverService.remove(a1011_driver);
    }

} else {
    if (!a1011.getQuestionSet().equals(a1009) && a1009.getQuestions().contains(a1011)) {
        a1009.getQuestions().remove(a1011);
        JPA.em().persist(a1009);
    }
    if (a1011.getQuestionSet().equals(a1009) && !a1009.getQuestions().contains(a1011)) {
        a1009.getQuestions().add(a1011);
        JPA.em().persist(a1009);
    }
    ((NumericQuestion)a1011).setUnitCategory(massUnits);
    a1011.setOrderIndex(0);
    ((NumericQuestion)a1011).setDefaultUnit(massUnits.getMainUnit());


    // cleanup the driver
    Driver a1011_driver = driverService.findByName("A1011");
    if (a1011_driver != null) {
        driverService.remove(a1011_driver);
    }

    ((NumericQuestion)a1011).setDriver(null);

    JPA.em().persist(a1011);
}



    }
    private void createQuestionA169() {
        // == A169
        // Electricité

        
a169 = (DoubleQuestion) questionService.findByCode(QuestionCode.A169);
if (a169 == null) {
    a169 = new DoubleQuestion( a164, 0, QuestionCode.A169, energyUnits, getUnitBySymbol("kW.h") );
    JPA.em().persist(a169);

    // cleanup the driver
    Driver a169_driver = driverService.findByName("A169");
    if (a169_driver != null) {
        driverService.remove(a169_driver);
    }


} else {
    if (!a169.getQuestionSet().equals(a164) && a164.getQuestions().contains(a169)) {
        a164.getQuestions().remove(a169);
        JPA.em().persist(a164);
    }
    if (a169.getQuestionSet().equals(a164) && !a164.getQuestions().contains(a169)) {
        a164.getQuestions().add(a169);
        JPA.em().persist(a164);
    }
    ((NumericQuestion)a169).setUnitCategory(energyUnits);
    a169.setOrderIndex(0);
    ((NumericQuestion)a169).setDefaultUnit(getUnitBySymbol("kW.h"));

    // cleanup the driver
    Driver a169_driver = driverService.findByName("A169");
    if (a169_driver != null) {
        driverService.remove(a169_driver);
    }

    ((NumericQuestion)a169).setDriver(null);

    JPA.em().persist(a169);
}



    }
    private void createQuestionA171() {
        // == A171
        // Type de gaz

        a171 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A171);
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
    ((ValueSelectionQuestion)a171).setCodeList(CodeList.FRIGORIGENE);
    JPA.em().persist(a171);
}

    }
    private void createQuestionA172() {
        // == A172
        // Quantité de recharge nécessaire pour l'année

        
a172 = (DoubleQuestion) questionService.findByCode(QuestionCode.A172);
if (a172 == null) {
    a172 = new DoubleQuestion( a170, 0, QuestionCode.A172, massUnits, massUnits.getMainUnit() );
    JPA.em().persist(a172);

    // cleanup the driver
    Driver a172_driver = driverService.findByName("A172");
    if (a172_driver != null) {
        driverService.remove(a172_driver);
    }

} else {
    if (!a172.getQuestionSet().equals(a170) && a170.getQuestions().contains(a172)) {
        a170.getQuestions().remove(a172);
        JPA.em().persist(a170);
    }
    if (a172.getQuestionSet().equals(a170) && !a170.getQuestions().contains(a172)) {
        a170.getQuestions().add(a172);
        JPA.em().persist(a170);
    }
    ((NumericQuestion)a172).setUnitCategory(massUnits);
    a172.setOrderIndex(0);
    ((NumericQuestion)a172).setDefaultUnit(massUnits.getMainUnit());


    // cleanup the driver
    Driver a172_driver = driverService.findByName("A172");
    if (a172_driver != null) {
        driverService.remove(a172_driver);
    }

    ((NumericQuestion)a172).setDriver(null);

    JPA.em().persist(a172);
}



    }
    private void createQuestionA174() {
        // == A174
        // Pièces documentaires liées aux déchets

        a174 = (DocumentQuestion) questionService.findByCode(QuestionCode.A174);
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

    }
    private void createQuestionA5001() {
        // == A5001
        // Poste de déchet

        a5001 = (StringQuestion) questionService.findByCode(QuestionCode.A5001);
if (a5001 == null) {
    a5001 = new StringQuestion(a5000, 0, QuestionCode.A5001, null);
    JPA.em().persist(a5001);
} else {
    ((StringQuestion)a5001).setDefaultValue(null);
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

    }
    private void createQuestionA5002() {
        // == A5002
        // Type de déchet et type de traitement

        a5002 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A5002);
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
    ((ValueSelectionQuestion)a5002).setCodeList(CodeList.GESTIONDECHETS);
    JPA.em().persist(a5002);
}

    }
    private void createQuestionA5003() {
        // == A5003
        // Quantité

        
a5003 = (DoubleQuestion) questionService.findByCode(QuestionCode.A5003);
if (a5003 == null) {
    a5003 = new DoubleQuestion( a5000, 0, QuestionCode.A5003, massUnits, getUnitBySymbol("t") );
    JPA.em().persist(a5003);

    // cleanup the driver
    Driver a5003_driver = driverService.findByName("A5003");
    if (a5003_driver != null) {
        driverService.remove(a5003_driver);
    }

} else {
    if (!a5003.getQuestionSet().equals(a5000) && a5000.getQuestions().contains(a5003)) {
        a5000.getQuestions().remove(a5003);
        JPA.em().persist(a5000);
    }
    if (a5003.getQuestionSet().equals(a5000) && !a5000.getQuestions().contains(a5003)) {
        a5000.getQuestions().add(a5003);
        JPA.em().persist(a5000);
    }
    ((NumericQuestion)a5003).setUnitCategory(massUnits);
    a5003.setOrderIndex(0);
    ((NumericQuestion)a5003).setDefaultUnit(getUnitBySymbol("t"));


    // cleanup the driver
    Driver a5003_driver = driverService.findByName("A5003");
    if (a5003_driver != null) {
        driverService.remove(a5003_driver);
    }

    ((NumericQuestion)a5003).setDriver(null);

    JPA.em().persist(a5003);
}



    }
    private void createQuestionA183() {
        // == A183
        // Nombre d'ouvriers

        a183 = (IntegerQuestion) questionService.findByCode(QuestionCode.A183);
if (a183 == null) {
    a183 = new IntegerQuestion(a182, 0, QuestionCode.A183, null);
    JPA.em().persist(a183);

    // cleanup the driver
    Driver a183_driver = driverService.findByName("A183");
    if (a183_driver != null) {
        driverService.remove(a183_driver);
    }

} else {
    if (!a183.getQuestionSet().equals(a182) && a182.getQuestions().contains(a183)) {
        a182.getQuestions().remove(a183);
        JPA.em().persist(a182);
    }
    if (a183.getQuestionSet().equals(a182) && !a182.getQuestions().contains(a183)) {
        a182.getQuestions().add(a183);
        JPA.em().persist(a182);
    }
    a183.setOrderIndex(0);
    ((NumericQuestion)a183).setUnitCategory(null);

    // cleanup the driver
    Driver a183_driver = driverService.findByName("A183");
    if (a183_driver != null) {
        driverService.remove(a183_driver);
    }

    ((NumericQuestion)a183).setDriver(null);

    JPA.em().persist(a183);
}

    }
    private void createQuestionA184() {
        // == A184
        // Nombre de jours de travail/an

        a184 = (IntegerQuestion) questionService.findByCode(QuestionCode.A184);
if (a184 == null) {
    a184 = new IntegerQuestion(a182, 0, QuestionCode.A184, null);
    JPA.em().persist(a184);

    // cleanup the driver
    Driver a184_driver = driverService.findByName("A184");
    if (a184_driver != null) {
        driverService.remove(a184_driver);
    }

    // recreate with good value
    a184_driver = new Driver("A184");
    driverService.saveOrUpdate(a184_driver);
    Period p2000 = periodService.findByCode(PeriodCode.P2000);
    DriverValue dv = new DriverValue(a184_driver, p2000, Double.valueOf(220));
    a184_driver.getDriverValueList().add(dv);;
    driverService.saveOrUpdate(a184_driver);
    JPA.em().persist(dv);

    ((NumericQuestion)a184).setDriver(a184_driver);
    JPA.em().persist(a184);
} else {
    if (!a184.getQuestionSet().equals(a182) && a182.getQuestions().contains(a184)) {
        a182.getQuestions().remove(a184);
        JPA.em().persist(a182);
    }
    if (a184.getQuestionSet().equals(a182) && !a182.getQuestions().contains(a184)) {
        a182.getQuestions().add(a184);
        JPA.em().persist(a182);
    }
    a184.setOrderIndex(0);
    ((NumericQuestion)a184).setUnitCategory(null);

    // cleanup the driver
    Driver a184_driver = driverService.findByName("A184");
    if (a184_driver != null) {
        driverService.remove(a184_driver);
    }

    // recreate with good value
    a184_driver = new Driver("A184");
    driverService.saveOrUpdate(a184_driver);
    Period p2000 = periodService.findByCode(PeriodCode.P2000);
    DriverValue dv = new DriverValue(a184_driver, p2000, Double.valueOf(220));
    a184_driver.getDriverValueList().add(dv);;
    driverService.saveOrUpdate(a184_driver);
    JPA.em().persist(dv);

    ((NumericQuestion)a184).setDriver(a184_driver);

    JPA.em().persist(a184);
}

    }
    private void createQuestionA186() {
        // == A186
        // Nombre d'employés

        a186 = (IntegerQuestion) questionService.findByCode(QuestionCode.A186);
if (a186 == null) {
    a186 = new IntegerQuestion(a185, 0, QuestionCode.A186, null);
    JPA.em().persist(a186);

    // cleanup the driver
    Driver a186_driver = driverService.findByName("A186");
    if (a186_driver != null) {
        driverService.remove(a186_driver);
    }

} else {
    if (!a186.getQuestionSet().equals(a185) && a185.getQuestions().contains(a186)) {
        a185.getQuestions().remove(a186);
        JPA.em().persist(a185);
    }
    if (a186.getQuestionSet().equals(a185) && !a185.getQuestions().contains(a186)) {
        a185.getQuestions().add(a186);
        JPA.em().persist(a185);
    }
    a186.setOrderIndex(0);
    ((NumericQuestion)a186).setUnitCategory(null);

    // cleanup the driver
    Driver a186_driver = driverService.findByName("A186");
    if (a186_driver != null) {
        driverService.remove(a186_driver);
    }

    ((NumericQuestion)a186).setDriver(null);

    JPA.em().persist(a186);
}

    }
    private void createQuestionA187() {
        // == A187
        // Nombre de jours de travail/an

        a187 = (IntegerQuestion) questionService.findByCode(QuestionCode.A187);
if (a187 == null) {
    a187 = new IntegerQuestion(a185, 0, QuestionCode.A187, null);
    JPA.em().persist(a187);

    // cleanup the driver
    Driver a187_driver = driverService.findByName("A187");
    if (a187_driver != null) {
        driverService.remove(a187_driver);
    }

    // recreate with good value
    a187_driver = new Driver("A187");
    driverService.saveOrUpdate(a187_driver);
    Period p2000 = periodService.findByCode(PeriodCode.P2000);
    DriverValue dv = new DriverValue(a187_driver, p2000, Double.valueOf(220));
    a187_driver.getDriverValueList().add(dv);;
    driverService.saveOrUpdate(a187_driver);
    JPA.em().persist(dv);

    ((NumericQuestion)a187).setDriver(a187_driver);
    JPA.em().persist(a187);
} else {
    if (!a187.getQuestionSet().equals(a185) && a185.getQuestions().contains(a187)) {
        a185.getQuestions().remove(a187);
        JPA.em().persist(a185);
    }
    if (a187.getQuestionSet().equals(a185) && !a185.getQuestions().contains(a187)) {
        a185.getQuestions().add(a187);
        JPA.em().persist(a185);
    }
    a187.setOrderIndex(0);
    ((NumericQuestion)a187).setUnitCategory(null);

    // cleanup the driver
    Driver a187_driver = driverService.findByName("A187");
    if (a187_driver != null) {
        driverService.remove(a187_driver);
    }

    // recreate with good value
    a187_driver = new Driver("A187");
    driverService.saveOrUpdate(a187_driver);
    Period p2000 = periodService.findByCode(PeriodCode.P2000);
    DriverValue dv = new DriverValue(a187_driver, p2000, Double.valueOf(220));
    a187_driver.getDriverValueList().add(dv);;
    driverService.saveOrUpdate(a187_driver);
    JPA.em().persist(dv);

    ((NumericQuestion)a187).setDriver(a187_driver);

    JPA.em().persist(a187);
}

    }
    private void createQuestionA189() {
        // == A189
        // Nombre de lits

        a189 = (IntegerQuestion) questionService.findByCode(QuestionCode.A189);
if (a189 == null) {
    a189 = new IntegerQuestion(a188, 0, QuestionCode.A189, null);
    JPA.em().persist(a189);

    // cleanup the driver
    Driver a189_driver = driverService.findByName("A189");
    if (a189_driver != null) {
        driverService.remove(a189_driver);
    }

} else {
    if (!a189.getQuestionSet().equals(a188) && a188.getQuestions().contains(a189)) {
        a188.getQuestions().remove(a189);
        JPA.em().persist(a188);
    }
    if (a189.getQuestionSet().equals(a188) && !a188.getQuestions().contains(a189)) {
        a188.getQuestions().add(a189);
        JPA.em().persist(a188);
    }
    a189.setOrderIndex(0);
    ((NumericQuestion)a189).setUnitCategory(null);

    // cleanup the driver
    Driver a189_driver = driverService.findByName("A189");
    if (a189_driver != null) {
        driverService.remove(a189_driver);
    }

    ((NumericQuestion)a189).setDriver(null);

    JPA.em().persist(a189);
}

    }
    private void createQuestionA190() {
        // == A190
        // Nombre de jours d'ouverture/an

        a190 = (IntegerQuestion) questionService.findByCode(QuestionCode.A190);
if (a190 == null) {
    a190 = new IntegerQuestion(a188, 0, QuestionCode.A190, null);
    JPA.em().persist(a190);

    // cleanup the driver
    Driver a190_driver = driverService.findByName("A190");
    if (a190_driver != null) {
        driverService.remove(a190_driver);
    }

    // recreate with good value
    a190_driver = new Driver("A190");
    driverService.saveOrUpdate(a190_driver);
    Period p2000 = periodService.findByCode(PeriodCode.P2000);
    DriverValue dv = new DriverValue(a190_driver, p2000, Double.valueOf(365));
    a190_driver.getDriverValueList().add(dv);;
    driverService.saveOrUpdate(a190_driver);
    JPA.em().persist(dv);

    ((NumericQuestion)a190).setDriver(a190_driver);
    JPA.em().persist(a190);
} else {
    if (!a190.getQuestionSet().equals(a188) && a188.getQuestions().contains(a190)) {
        a188.getQuestions().remove(a190);
        JPA.em().persist(a188);
    }
    if (a190.getQuestionSet().equals(a188) && !a188.getQuestions().contains(a190)) {
        a188.getQuestions().add(a190);
        JPA.em().persist(a188);
    }
    a190.setOrderIndex(0);
    ((NumericQuestion)a190).setUnitCategory(null);

    // cleanup the driver
    Driver a190_driver = driverService.findByName("A190");
    if (a190_driver != null) {
        driverService.remove(a190_driver);
    }

    // recreate with good value
    a190_driver = new Driver("A190");
    driverService.saveOrUpdate(a190_driver);
    Period p2000 = periodService.findByCode(PeriodCode.P2000);
    DriverValue dv = new DriverValue(a190_driver, p2000, Double.valueOf(365));
    a190_driver.getDriverValueList().add(dv);;
    driverService.saveOrUpdate(a190_driver);
    JPA.em().persist(dv);

    ((NumericQuestion)a190).setDriver(a190_driver);

    JPA.em().persist(a190);
}

    }
    private void createQuestionA192() {
        // == A192
        // Nombre de couverts/jour

        a192 = (IntegerQuestion) questionService.findByCode(QuestionCode.A192);
if (a192 == null) {
    a192 = new IntegerQuestion(a191, 0, QuestionCode.A192, null);
    JPA.em().persist(a192);

    // cleanup the driver
    Driver a192_driver = driverService.findByName("A192");
    if (a192_driver != null) {
        driverService.remove(a192_driver);
    }

} else {
    if (!a192.getQuestionSet().equals(a191) && a191.getQuestions().contains(a192)) {
        a191.getQuestions().remove(a192);
        JPA.em().persist(a191);
    }
    if (a192.getQuestionSet().equals(a191) && !a191.getQuestions().contains(a192)) {
        a191.getQuestions().add(a192);
        JPA.em().persist(a191);
    }
    a192.setOrderIndex(0);
    ((NumericQuestion)a192).setUnitCategory(null);

    // cleanup the driver
    Driver a192_driver = driverService.findByName("A192");
    if (a192_driver != null) {
        driverService.remove(a192_driver);
    }

    ((NumericQuestion)a192).setDriver(null);

    JPA.em().persist(a192);
}

    }
    private void createQuestionA193() {
        // == A193
        // Nombre de jours d'ouverture/an

        a193 = (IntegerQuestion) questionService.findByCode(QuestionCode.A193);
if (a193 == null) {
    a193 = new IntegerQuestion(a191, 0, QuestionCode.A193, null);
    JPA.em().persist(a193);

    // cleanup the driver
    Driver a193_driver = driverService.findByName("A193");
    if (a193_driver != null) {
        driverService.remove(a193_driver);
    }

    // recreate with good value
    a193_driver = new Driver("A193");
    driverService.saveOrUpdate(a193_driver);
    Period p2000 = periodService.findByCode(PeriodCode.P2000);
    DriverValue dv = new DriverValue(a193_driver, p2000, Double.valueOf(220));
    a193_driver.getDriverValueList().add(dv);;
    driverService.saveOrUpdate(a193_driver);
    JPA.em().persist(dv);

    ((NumericQuestion)a193).setDriver(a193_driver);
    JPA.em().persist(a193);
} else {
    if (!a193.getQuestionSet().equals(a191) && a191.getQuestions().contains(a193)) {
        a191.getQuestions().remove(a193);
        JPA.em().persist(a191);
    }
    if (a193.getQuestionSet().equals(a191) && !a191.getQuestions().contains(a193)) {
        a191.getQuestions().add(a193);
        JPA.em().persist(a191);
    }
    a193.setOrderIndex(0);
    ((NumericQuestion)a193).setUnitCategory(null);

    // cleanup the driver
    Driver a193_driver = driverService.findByName("A193");
    if (a193_driver != null) {
        driverService.remove(a193_driver);
    }

    // recreate with good value
    a193_driver = new Driver("A193");
    driverService.saveOrUpdate(a193_driver);
    Period p2000 = periodService.findByCode(PeriodCode.P2000);
    DriverValue dv = new DriverValue(a193_driver, p2000, Double.valueOf(220));
    a193_driver.getDriverValueList().add(dv);;
    driverService.saveOrUpdate(a193_driver);
    JPA.em().persist(dv);

    ((NumericQuestion)a193).setDriver(a193_driver);

    JPA.em().persist(a193);
}

    }
    private void createQuestionA198() {
        // == A198
        // Source de rejet

        a198 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A198);
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
    ((ValueSelectionQuestion)a198).setCodeList(CodeList.ORIGINEEAUUSEE);
    JPA.em().persist(a198);
}

    }
    private void createQuestionA199() {
        // == A199
        // Quantités de m³ rejetés

        
a199 = (DoubleQuestion) questionService.findByCode(QuestionCode.A199);
if (a199 == null) {
    a199 = new DoubleQuestion( a197, 0, QuestionCode.A199, volumeUnits, volumeUnits.getMainUnit() );
    JPA.em().persist(a199);

    // cleanup the driver
    Driver a199_driver = driverService.findByName("A199");
    if (a199_driver != null) {
        driverService.remove(a199_driver);
    }

} else {
    if (!a199.getQuestionSet().equals(a197) && a197.getQuestions().contains(a199)) {
        a197.getQuestions().remove(a199);
        JPA.em().persist(a197);
    }
    if (a199.getQuestionSet().equals(a197) && !a197.getQuestions().contains(a199)) {
        a197.getQuestions().add(a199);
        JPA.em().persist(a197);
    }
    ((NumericQuestion)a199).setUnitCategory(volumeUnits);
    a199.setOrderIndex(0);
    ((NumericQuestion)a199).setDefaultUnit(volumeUnits.getMainUnit());

    // cleanup the driver
    Driver a199_driver = driverService.findByName("A199");
    if (a199_driver != null) {
        driverService.remove(a199_driver);
    }

    ((NumericQuestion)a199).setDriver(null);

    JPA.em().persist(a199);
}



    }
    private void createQuestionA200() {
        // == A200
        // Méthode de traitement des eaux usées

        a200 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A200);
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
    ((ValueSelectionQuestion)a200).setCodeList(CodeList.TRAITEMENTEAU);
    JPA.em().persist(a200);
}

    }
    private void createQuestionA202() {
        // == A202
        // Quantités de DCO rejetés

        
a202 = (DoubleQuestion) questionService.findByCode(QuestionCode.A202);
if (a202 == null) {
    a202 = new DoubleQuestion( a201, 0, QuestionCode.A202, massUnits, massUnits.getMainUnit() );
    JPA.em().persist(a202);

    // cleanup the driver
    Driver a202_driver = driverService.findByName("A202");
    if (a202_driver != null) {
        driverService.remove(a202_driver);
    }

} else {
    if (!a202.getQuestionSet().equals(a201) && a201.getQuestions().contains(a202)) {
        a201.getQuestions().remove(a202);
        JPA.em().persist(a201);
    }
    if (a202.getQuestionSet().equals(a201) && !a201.getQuestions().contains(a202)) {
        a201.getQuestions().add(a202);
        JPA.em().persist(a201);
    }
    ((NumericQuestion)a202).setUnitCategory(massUnits);
    a202.setOrderIndex(0);
    ((NumericQuestion)a202).setDefaultUnit(massUnits.getMainUnit());


    // cleanup the driver
    Driver a202_driver = driverService.findByName("A202");
    if (a202_driver != null) {
        driverService.remove(a202_driver);
    }

    ((NumericQuestion)a202).setDriver(null);

    JPA.em().persist(a202);
}



    }
    private void createQuestionA203() {
        // == A203
        // Quantités d'azote rejetés

        
a203 = (DoubleQuestion) questionService.findByCode(QuestionCode.A203);
if (a203 == null) {
    a203 = new DoubleQuestion( a201, 0, QuestionCode.A203, massUnits, massUnits.getMainUnit() );
    JPA.em().persist(a203);

    // cleanup the driver
    Driver a203_driver = driverService.findByName("A203");
    if (a203_driver != null) {
        driverService.remove(a203_driver);
    }

} else {
    if (!a203.getQuestionSet().equals(a201) && a201.getQuestions().contains(a203)) {
        a201.getQuestions().remove(a203);
        JPA.em().persist(a201);
    }
    if (a203.getQuestionSet().equals(a201) && !a201.getQuestions().contains(a203)) {
        a201.getQuestions().add(a203);
        JPA.em().persist(a201);
    }
    ((NumericQuestion)a203).setUnitCategory(massUnits);
    a203.setOrderIndex(0);
    ((NumericQuestion)a203).setDefaultUnit(massUnits.getMainUnit());


    // cleanup the driver
    Driver a203_driver = driverService.findByName("A203");
    if (a203_driver != null) {
        driverService.remove(a203_driver);
    }

    ((NumericQuestion)a203).setDriver(null);

    JPA.em().persist(a203);
}



    }
    private void createQuestionA204() {
        // == A204
        // Méthode de traitement des eaux usées

        a204 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A204);
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
    ((ValueSelectionQuestion)a204).setCodeList(CodeList.TRAITEMENTEAU);
    JPA.em().persist(a204);
}

    }
    private void createQuestionA230() {
        // == A230
        // Pièces documentaires liées aux biens d'équipements

        a230 = (DocumentQuestion) questionService.findByCode(QuestionCode.A230);
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

    }
    private void createQuestionA232() {
        // == A232
        // Nom

        a232 = (StringQuestion) questionService.findByCode(QuestionCode.A232);
if (a232 == null) {
    a232 = new StringQuestion(a231, 0, QuestionCode.A232, null);
    JPA.em().persist(a232);
} else {
    ((StringQuestion)a232).setDefaultValue(null);
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

    }
    private void createQuestionA233() {
        // == A233
        // Type d'équipement

        a233 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A233);
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
    ((ValueSelectionQuestion)a233).setCodeList(CodeList.INFRASTRUCTURE);
    JPA.em().persist(a233);
}

    }
    private void createQuestionA234() {
        // == A234
        // Quantité

        

a234 = (DoubleQuestion) questionService.findByCode(QuestionCode.A234);
if (a234 == null) {
    a234 = new DoubleQuestion( a231, 0, QuestionCode.A234, areaUnits, areaUnits.getMainUnit() );
    JPA.em().persist(a234);

    // cleanup the driver
    Driver a234_driver = driverService.findByName("A234");
    if (a234_driver != null) {
        driverService.remove(a234_driver);
    }


} else {
    if (!a234.getQuestionSet().equals(a231) && a231.getQuestions().contains(a234)) {
        a231.getQuestions().remove(a234);
        JPA.em().persist(a231);
    }
    if (a234.getQuestionSet().equals(a231) && !a231.getQuestions().contains(a234)) {
        a231.getQuestions().add(a234);
        JPA.em().persist(a231);
    }
    ((NumericQuestion)a234).setUnitCategory(areaUnits);
    a234.setOrderIndex(0);
    ((NumericQuestion)a234).setDefaultUnit(areaUnits.getMainUnit());

    // cleanup the driver
    Driver a234_driver = driverService.findByName("A234");
    if (a234_driver != null) {
        driverService.remove(a234_driver);
    }

    ((NumericQuestion)a234).setDriver(null);

    JPA.em().persist(a234);
}



    }
    private void createQuestionA235() {
        // == A235
        // Quantité

        
a235 = (DoubleQuestion) questionService.findByCode(QuestionCode.A235);
if (a235 == null) {
    a235 = new DoubleQuestion( a231, 0, QuestionCode.A235, massUnits, getUnitBySymbol("t") );
    JPA.em().persist(a235);

    // cleanup the driver
    Driver a235_driver = driverService.findByName("A235");
    if (a235_driver != null) {
        driverService.remove(a235_driver);
    }

} else {
    if (!a235.getQuestionSet().equals(a231) && a231.getQuestions().contains(a235)) {
        a231.getQuestions().remove(a235);
        JPA.em().persist(a231);
    }
    if (a235.getQuestionSet().equals(a231) && !a231.getQuestions().contains(a235)) {
        a231.getQuestions().add(a235);
        JPA.em().persist(a231);
    }
    ((NumericQuestion)a235).setUnitCategory(massUnits);
    a235.setOrderIndex(0);
    ((NumericQuestion)a235).setDefaultUnit(getUnitBySymbol("t"));


    // cleanup the driver
    Driver a235_driver = driverService.findByName("A235");
    if (a235_driver != null) {
        driverService.remove(a235_driver);
    }

    ((NumericQuestion)a235).setDriver(null);

    JPA.em().persist(a235);
}



    }
    private void createQuestionA236() {
        // == A236
        // Quantité

        a236 = (IntegerQuestion) questionService.findByCode(QuestionCode.A236);
if (a236 == null) {
    a236 = new IntegerQuestion(a231, 0, QuestionCode.A236, null);
    JPA.em().persist(a236);

    // cleanup the driver
    Driver a236_driver = driverService.findByName("A236");
    if (a236_driver != null) {
        driverService.remove(a236_driver);
    }

} else {
    if (!a236.getQuestionSet().equals(a231) && a231.getQuestions().contains(a236)) {
        a231.getQuestions().remove(a236);
        JPA.em().persist(a231);
    }
    if (a236.getQuestionSet().equals(a231) && !a231.getQuestions().contains(a236)) {
        a231.getQuestions().add(a236);
        JPA.em().persist(a231);
    }
    a236.setOrderIndex(0);
    ((NumericQuestion)a236).setUnitCategory(null);

    // cleanup the driver
    Driver a236_driver = driverService.findByName("A236");
    if (a236_driver != null) {
        driverService.remove(a236_driver);
    }

    ((NumericQuestion)a236).setDriver(null);

    JPA.em().persist(a236);
}

    }
    private void createQuestionA239() {
        // == A239
        // Nom

        a239 = (StringQuestion) questionService.findByCode(QuestionCode.A239);
if (a239 == null) {
    a239 = new StringQuestion(a238, 0, QuestionCode.A239, null);
    JPA.em().persist(a239);
} else {
    ((StringQuestion)a239).setDefaultValue(null);
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

    }
    private void createQuestionA240() {
        // == A240
        // Quantité

        a240 = (IntegerQuestion) questionService.findByCode(QuestionCode.A240);
if (a240 == null) {
    a240 = new IntegerQuestion(a238, 0, QuestionCode.A240, null);
    JPA.em().persist(a240);

    // cleanup the driver
    Driver a240_driver = driverService.findByName("A240");
    if (a240_driver != null) {
        driverService.remove(a240_driver);
    }

} else {
    if (!a240.getQuestionSet().equals(a238) && a238.getQuestions().contains(a240)) {
        a238.getQuestions().remove(a240);
        JPA.em().persist(a238);
    }
    if (a240.getQuestionSet().equals(a238) && !a238.getQuestions().contains(a240)) {
        a238.getQuestions().add(a240);
        JPA.em().persist(a238);
    }
    a240.setOrderIndex(0);
    ((NumericQuestion)a240).setUnitCategory(null);

    // cleanup the driver
    Driver a240_driver = driverService.findByName("A240");
    if (a240_driver != null) {
        driverService.remove(a240_driver);
    }

    ((NumericQuestion)a240).setDriver(null);

    JPA.em().persist(a240);
}

    }
    private void createQuestionA241() {
        // == A241
        // Unité dans laquelle s'exprime cette quantité

        a241 = (StringQuestion) questionService.findByCode(QuestionCode.A241);
if (a241 == null) {
    a241 = new StringQuestion(a238, 0, QuestionCode.A241, null);
    JPA.em().persist(a241);
} else {
    ((StringQuestion)a241).setDefaultValue(null);
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

    }
    private void createQuestionA242() {
        // == A242
        // Facteur d'émission en tCO2e par unité ci-dessus

        a242 = (IntegerQuestion) questionService.findByCode(QuestionCode.A242);
if (a242 == null) {
    a242 = new IntegerQuestion(a238, 0, QuestionCode.A242, null);
    JPA.em().persist(a242);

    // cleanup the driver
    Driver a242_driver = driverService.findByName("A242");
    if (a242_driver != null) {
        driverService.remove(a242_driver);
    }

} else {
    if (!a242.getQuestionSet().equals(a238) && a238.getQuestions().contains(a242)) {
        a238.getQuestions().remove(a242);
        JPA.em().persist(a238);
    }
    if (a242.getQuestionSet().equals(a238) && !a238.getQuestions().contains(a242)) {
        a238.getQuestions().add(a242);
        JPA.em().persist(a238);
    }
    a242.setOrderIndex(0);
    ((NumericQuestion)a242).setUnitCategory(null);

    // cleanup the driver
    Driver a242_driver = driverService.findByName("A242");
    if (a242_driver != null) {
        driverService.remove(a242_driver);
    }

    ((NumericQuestion)a242).setDriver(null);

    JPA.em().persist(a242);
}

    }
    private void createQuestionA310() {
        // == A310
        // Fournir ici les documents éventuels justifiant les données suivantes

        a310 = (DocumentQuestion) questionService.findByCode(QuestionCode.A310);
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

    }
    private void createQuestionA312() {
        // == A312
        // Catégorie d'actif loué

        a312 = (StringQuestion) questionService.findByCode(QuestionCode.A312);
if (a312 == null) {
    a312 = new StringQuestion(a311, 0, QuestionCode.A312, null);
    JPA.em().persist(a312);
} else {
    ((StringQuestion)a312).setDefaultValue(null);
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

    }
    private void createQuestionA314() {
        // == A314
        // Combustible utilisé

        a314 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A314);
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
    ((ValueSelectionQuestion)a314).setCodeList(CodeList.COMBUSTIBLE);
    JPA.em().persist(a314);
}

    }
    private void createQuestionA315() {
        // == A315
        // Quantité

        
a315 = (DoubleQuestion) questionService.findByCode(QuestionCode.A315);
if (a315 == null) {
    a315 = new DoubleQuestion( a313, 0, QuestionCode.A315, energyUnits, getUnitBySymbol("kW.h") );
    JPA.em().persist(a315);

    // cleanup the driver
    Driver a315_driver = driverService.findByName("A315");
    if (a315_driver != null) {
        driverService.remove(a315_driver);
    }


} else {
    if (!a315.getQuestionSet().equals(a313) && a313.getQuestions().contains(a315)) {
        a313.getQuestions().remove(a315);
        JPA.em().persist(a313);
    }
    if (a315.getQuestionSet().equals(a313) && !a313.getQuestions().contains(a315)) {
        a313.getQuestions().add(a315);
        JPA.em().persist(a313);
    }
    ((NumericQuestion)a315).setUnitCategory(energyUnits);
    a315.setOrderIndex(0);
    ((NumericQuestion)a315).setDefaultUnit(getUnitBySymbol("kW.h"));

    // cleanup the driver
    Driver a315_driver = driverService.findByName("A315");
    if (a315_driver != null) {
        driverService.remove(a315_driver);
    }

    ((NumericQuestion)a315).setDriver(null);

    JPA.em().persist(a315);
}



    }
    private void createQuestionA1013() {
        // == A1013
        // Combustible utilisé

        a1013 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A1013);
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
    ((ValueSelectionQuestion)a1013).setCodeList(CodeList.COMBUSTIBLEVOLUME);
    JPA.em().persist(a1013);
}

    }
    private void createQuestionA1014() {
        // == A1014
        // Quantité

        
a1014 = (DoubleQuestion) questionService.findByCode(QuestionCode.A1014);
if (a1014 == null) {
    a1014 = new DoubleQuestion( a1012, 0, QuestionCode.A1014, volumeUnits, volumeUnits.getMainUnit() );
    JPA.em().persist(a1014);

    // cleanup the driver
    Driver a1014_driver = driverService.findByName("A1014");
    if (a1014_driver != null) {
        driverService.remove(a1014_driver);
    }

} else {
    if (!a1014.getQuestionSet().equals(a1012) && a1012.getQuestions().contains(a1014)) {
        a1012.getQuestions().remove(a1014);
        JPA.em().persist(a1012);
    }
    if (a1014.getQuestionSet().equals(a1012) && !a1012.getQuestions().contains(a1014)) {
        a1012.getQuestions().add(a1014);
        JPA.em().persist(a1012);
    }
    ((NumericQuestion)a1014).setUnitCategory(volumeUnits);
    a1014.setOrderIndex(0);
    ((NumericQuestion)a1014).setDefaultUnit(volumeUnits.getMainUnit());

    // cleanup the driver
    Driver a1014_driver = driverService.findByName("A1014");
    if (a1014_driver != null) {
        driverService.remove(a1014_driver);
    }

    ((NumericQuestion)a1014).setDriver(null);

    JPA.em().persist(a1014);
}



    }
    private void createQuestionA1016() {
        // == A1016
        // Combustible utilisé

        a1016 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A1016);
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
    ((ValueSelectionQuestion)a1016).setCodeList(CodeList.COMBUSTIBLEPOIDS);
    JPA.em().persist(a1016);
}

    }
    private void createQuestionA1017() {
        // == A1017
        // Quantité

        
a1017 = (DoubleQuestion) questionService.findByCode(QuestionCode.A1017);
if (a1017 == null) {
    a1017 = new DoubleQuestion( a1015, 0, QuestionCode.A1017, massUnits, massUnits.getMainUnit() );
    JPA.em().persist(a1017);

    // cleanup the driver
    Driver a1017_driver = driverService.findByName("A1017");
    if (a1017_driver != null) {
        driverService.remove(a1017_driver);
    }

} else {
    if (!a1017.getQuestionSet().equals(a1015) && a1015.getQuestions().contains(a1017)) {
        a1015.getQuestions().remove(a1017);
        JPA.em().persist(a1015);
    }
    if (a1017.getQuestionSet().equals(a1015) && !a1015.getQuestions().contains(a1017)) {
        a1015.getQuestions().add(a1017);
        JPA.em().persist(a1015);
    }
    ((NumericQuestion)a1017).setUnitCategory(massUnits);
    a1017.setOrderIndex(0);
    ((NumericQuestion)a1017).setDefaultUnit(massUnits.getMainUnit());


    // cleanup the driver
    Driver a1017_driver = driverService.findByName("A1017");
    if (a1017_driver != null) {
        driverService.remove(a1017_driver);
    }

    ((NumericQuestion)a1017).setDriver(null);

    JPA.em().persist(a1017);
}



    }
    private void createQuestionA316() {
        // == A316
        // Electricité

        
a316 = (DoubleQuestion) questionService.findByCode(QuestionCode.A316);
if (a316 == null) {
    a316 = new DoubleQuestion( a311, 0, QuestionCode.A316, energyUnits, getUnitBySymbol("kW.h") );
    JPA.em().persist(a316);

    // cleanup the driver
    Driver a316_driver = driverService.findByName("A316");
    if (a316_driver != null) {
        driverService.remove(a316_driver);
    }


} else {
    if (!a316.getQuestionSet().equals(a311) && a311.getQuestions().contains(a316)) {
        a311.getQuestions().remove(a316);
        JPA.em().persist(a311);
    }
    if (a316.getQuestionSet().equals(a311) && !a311.getQuestions().contains(a316)) {
        a311.getQuestions().add(a316);
        JPA.em().persist(a311);
    }
    ((NumericQuestion)a316).setUnitCategory(energyUnits);
    a316.setOrderIndex(0);
    ((NumericQuestion)a316).setDefaultUnit(getUnitBySymbol("kW.h"));

    // cleanup the driver
    Driver a316_driver = driverService.findByName("A316");
    if (a316_driver != null) {
        driverService.remove(a316_driver);
    }

    ((NumericQuestion)a316).setDriver(null);

    JPA.em().persist(a316);
}



    }
    private void createQuestionA318() {
        // == A318
        // Type de gaz

        a318 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A318);
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
    ((ValueSelectionQuestion)a318).setCodeList(CodeList.FRIGORIGENE);
    JPA.em().persist(a318);
}

    }
    private void createQuestionA319() {
        // == A319
        // Quantité de recharge nécessaire pour l'année

        
a319 = (DoubleQuestion) questionService.findByCode(QuestionCode.A319);
if (a319 == null) {
    a319 = new DoubleQuestion( a317, 0, QuestionCode.A319, massUnits, massUnits.getMainUnit() );
    JPA.em().persist(a319);

    // cleanup the driver
    Driver a319_driver = driverService.findByName("A319");
    if (a319_driver != null) {
        driverService.remove(a319_driver);
    }

} else {
    if (!a319.getQuestionSet().equals(a317) && a317.getQuestions().contains(a319)) {
        a317.getQuestions().remove(a319);
        JPA.em().persist(a317);
    }
    if (a319.getQuestionSet().equals(a317) && !a317.getQuestions().contains(a319)) {
        a317.getQuestions().add(a319);
        JPA.em().persist(a317);
    }
    ((NumericQuestion)a319).setUnitCategory(massUnits);
    a319.setOrderIndex(0);
    ((NumericQuestion)a319).setDefaultUnit(massUnits.getMainUnit());


    // cleanup the driver
    Driver a319_driver = driverService.findByName("A319");
    if (a319_driver != null) {
        driverService.remove(a319_driver);
    }

    ((NumericQuestion)a319).setDriver(null);

    JPA.em().persist(a319);
}



    }
    private void createQuestionA321() {
        // == A321
        // Fournir ici les documents éventuels justifiant les données suivantes

        a321 = (DocumentQuestion) questionService.findByCode(QuestionCode.A321);
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

    }
    private void createQuestionA323() {
        // == A323
        // Catégorie de franchisé

        a323 = (StringQuestion) questionService.findByCode(QuestionCode.A323);
if (a323 == null) {
    a323 = new StringQuestion(a322, 0, QuestionCode.A323, null);
    JPA.em().persist(a323);
} else {
    ((StringQuestion)a323).setDefaultValue(null);
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

    }
    private void createQuestionA324() {
        // == A324
        // Nombre de franchisés

        a324 = (IntegerQuestion) questionService.findByCode(QuestionCode.A324);
if (a324 == null) {
    a324 = new IntegerQuestion(a322, 0, QuestionCode.A324, null);
    JPA.em().persist(a324);

    // cleanup the driver
    Driver a324_driver = driverService.findByName("A324");
    if (a324_driver != null) {
        driverService.remove(a324_driver);
    }

} else {
    if (!a324.getQuestionSet().equals(a322) && a322.getQuestions().contains(a324)) {
        a322.getQuestions().remove(a324);
        JPA.em().persist(a322);
    }
    if (a324.getQuestionSet().equals(a322) && !a322.getQuestions().contains(a324)) {
        a322.getQuestions().add(a324);
        JPA.em().persist(a322);
    }
    a324.setOrderIndex(0);
    ((NumericQuestion)a324).setUnitCategory(null);

    // cleanup the driver
    Driver a324_driver = driverService.findByName("A324");
    if (a324_driver != null) {
        driverService.remove(a324_driver);
    }

    ((NumericQuestion)a324).setDriver(null);

    JPA.em().persist(a324);
}

    }
    private void createQuestionA326() {
        // == A326
        // Combustible utilisé

        a326 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A326);
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
    ((ValueSelectionQuestion)a326).setCodeList(CodeList.COMBUSTIBLE);
    JPA.em().persist(a326);
}

    }
    private void createQuestionA327() {
        // == A327
        // Quantité

        
a327 = (DoubleQuestion) questionService.findByCode(QuestionCode.A327);
if (a327 == null) {
    a327 = new DoubleQuestion( a325, 0, QuestionCode.A327, energyUnits, getUnitBySymbol("kW.h") );
    JPA.em().persist(a327);

    // cleanup the driver
    Driver a327_driver = driverService.findByName("A327");
    if (a327_driver != null) {
        driverService.remove(a327_driver);
    }


} else {
    if (!a327.getQuestionSet().equals(a325) && a325.getQuestions().contains(a327)) {
        a325.getQuestions().remove(a327);
        JPA.em().persist(a325);
    }
    if (a327.getQuestionSet().equals(a325) && !a325.getQuestions().contains(a327)) {
        a325.getQuestions().add(a327);
        JPA.em().persist(a325);
    }
    ((NumericQuestion)a327).setUnitCategory(energyUnits);
    a327.setOrderIndex(0);
    ((NumericQuestion)a327).setDefaultUnit(getUnitBySymbol("kW.h"));

    // cleanup the driver
    Driver a327_driver = driverService.findByName("A327");
    if (a327_driver != null) {
        driverService.remove(a327_driver);
    }

    ((NumericQuestion)a327).setDriver(null);

    JPA.em().persist(a327);
}



    }
    private void createQuestionA1019() {
        // == A1019
        // Combustible utilisé

        a1019 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A1019);
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
    ((ValueSelectionQuestion)a1019).setCodeList(CodeList.COMBUSTIBLEVOLUME);
    JPA.em().persist(a1019);
}

    }
    private void createQuestionA1020() {
        // == A1020
        // Quantité

        
a1020 = (DoubleQuestion) questionService.findByCode(QuestionCode.A1020);
if (a1020 == null) {
    a1020 = new DoubleQuestion( a1018, 0, QuestionCode.A1020, volumeUnits, volumeUnits.getMainUnit() );
    JPA.em().persist(a1020);

    // cleanup the driver
    Driver a1020_driver = driverService.findByName("A1020");
    if (a1020_driver != null) {
        driverService.remove(a1020_driver);
    }

} else {
    if (!a1020.getQuestionSet().equals(a1018) && a1018.getQuestions().contains(a1020)) {
        a1018.getQuestions().remove(a1020);
        JPA.em().persist(a1018);
    }
    if (a1020.getQuestionSet().equals(a1018) && !a1018.getQuestions().contains(a1020)) {
        a1018.getQuestions().add(a1020);
        JPA.em().persist(a1018);
    }
    ((NumericQuestion)a1020).setUnitCategory(volumeUnits);
    a1020.setOrderIndex(0);
    ((NumericQuestion)a1020).setDefaultUnit(volumeUnits.getMainUnit());

    // cleanup the driver
    Driver a1020_driver = driverService.findByName("A1020");
    if (a1020_driver != null) {
        driverService.remove(a1020_driver);
    }

    ((NumericQuestion)a1020).setDriver(null);

    JPA.em().persist(a1020);
}



    }
    private void createQuestionA1022() {
        // == A1022
        // Combustible utilisé

        a1022 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A1022);
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
    ((ValueSelectionQuestion)a1022).setCodeList(CodeList.COMBUSTIBLEPOIDS);
    JPA.em().persist(a1022);
}

    }
    private void createQuestionA1023() {
        // == A1023
        // Quantité

        
a1023 = (DoubleQuestion) questionService.findByCode(QuestionCode.A1023);
if (a1023 == null) {
    a1023 = new DoubleQuestion( a1021, 0, QuestionCode.A1023, massUnits, massUnits.getMainUnit() );
    JPA.em().persist(a1023);

    // cleanup the driver
    Driver a1023_driver = driverService.findByName("A1023");
    if (a1023_driver != null) {
        driverService.remove(a1023_driver);
    }

} else {
    if (!a1023.getQuestionSet().equals(a1021) && a1021.getQuestions().contains(a1023)) {
        a1021.getQuestions().remove(a1023);
        JPA.em().persist(a1021);
    }
    if (a1023.getQuestionSet().equals(a1021) && !a1021.getQuestions().contains(a1023)) {
        a1021.getQuestions().add(a1023);
        JPA.em().persist(a1021);
    }
    ((NumericQuestion)a1023).setUnitCategory(massUnits);
    a1023.setOrderIndex(0);
    ((NumericQuestion)a1023).setDefaultUnit(massUnits.getMainUnit());


    // cleanup the driver
    Driver a1023_driver = driverService.findByName("A1023");
    if (a1023_driver != null) {
        driverService.remove(a1023_driver);
    }

    ((NumericQuestion)a1023).setDriver(null);

    JPA.em().persist(a1023);
}



    }
    private void createQuestionA328() {
        // == A328
        // Electricité (moyenne par franchisé)

        
a328 = (DoubleQuestion) questionService.findByCode(QuestionCode.A328);
if (a328 == null) {
    a328 = new DoubleQuestion( a322, 0, QuestionCode.A328, energyUnits, getUnitBySymbol("kW.h") );
    JPA.em().persist(a328);

    // cleanup the driver
    Driver a328_driver = driverService.findByName("A328");
    if (a328_driver != null) {
        driverService.remove(a328_driver);
    }


} else {
    if (!a328.getQuestionSet().equals(a322) && a322.getQuestions().contains(a328)) {
        a322.getQuestions().remove(a328);
        JPA.em().persist(a322);
    }
    if (a328.getQuestionSet().equals(a322) && !a322.getQuestions().contains(a328)) {
        a322.getQuestions().add(a328);
        JPA.em().persist(a322);
    }
    ((NumericQuestion)a328).setUnitCategory(energyUnits);
    a328.setOrderIndex(0);
    ((NumericQuestion)a328).setDefaultUnit(getUnitBySymbol("kW.h"));

    // cleanup the driver
    Driver a328_driver = driverService.findByName("A328");
    if (a328_driver != null) {
        driverService.remove(a328_driver);
    }

    ((NumericQuestion)a328).setDriver(null);

    JPA.em().persist(a328);
}



    }
    private void createQuestionA330() {
        // == A330
        // Type de gaz

        a330 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A330);
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
    ((ValueSelectionQuestion)a330).setCodeList(CodeList.FRIGORIGENE);
    JPA.em().persist(a330);
}

    }
    private void createQuestionA331() {
        // == A331
        // Quantité de recharge nécessaire pour l'année

        
a331 = (DoubleQuestion) questionService.findByCode(QuestionCode.A331);
if (a331 == null) {
    a331 = new DoubleQuestion( a329, 0, QuestionCode.A331, massUnits, massUnits.getMainUnit() );
    JPA.em().persist(a331);

    // cleanup the driver
    Driver a331_driver = driverService.findByName("A331");
    if (a331_driver != null) {
        driverService.remove(a331_driver);
    }

} else {
    if (!a331.getQuestionSet().equals(a329) && a329.getQuestions().contains(a331)) {
        a329.getQuestions().remove(a331);
        JPA.em().persist(a329);
    }
    if (a331.getQuestionSet().equals(a329) && !a329.getQuestions().contains(a331)) {
        a329.getQuestions().add(a331);
        JPA.em().persist(a329);
    }
    ((NumericQuestion)a331).setUnitCategory(massUnits);
    a331.setOrderIndex(0);
    ((NumericQuestion)a331).setDefaultUnit(massUnits.getMainUnit());


    // cleanup the driver
    Driver a331_driver = driverService.findByName("A331");
    if (a331_driver != null) {
        driverService.remove(a331_driver);
    }

    ((NumericQuestion)a331).setDriver(null);

    JPA.em().persist(a331);
}



    }
    private void createQuestionA333() {
        // == A333
        // Fournir ici les documents éventuels justifiant les données suivantes

        a333 = (DocumentQuestion) questionService.findByCode(QuestionCode.A333);
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

    }
    private void createQuestionA335() {
        // == A335
        // Nom du projet

        a335 = (StringQuestion) questionService.findByCode(QuestionCode.A335);
if (a335 == null) {
    a335 = new StringQuestion(a334, 0, QuestionCode.A335, null);
    JPA.em().persist(a335);
} else {
    ((StringQuestion)a335).setDefaultValue(null);
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

    }
    private void createQuestionA336() {
        // == A336
        // Part d'investissements dans le projet

        a336 = (PercentageQuestion) questionService.findByCode(QuestionCode.A336);
if (a336 == null) {
    a336 = new PercentageQuestion(a334, 0, QuestionCode.A336);
    JPA.em().persist(a336);

    // cleanup the driver
    Driver a336_driver = driverService.findByName("A336");
    if (a336_driver != null) {
        driverService.remove(a336_driver);
    }

} else {
    if (!a336.getQuestionSet().equals(a334) && a334.getQuestions().contains(a336)) {
        a334.getQuestions().remove(a336);
        JPA.em().persist(a334);
    }
    if (a336.getQuestionSet().equals(a334) && !a334.getQuestions().contains(a336)) {
        a334.getQuestions().add(a336);
        JPA.em().persist(a334);
    }
    a336.setOrderIndex(0);

    // cleanup the driver
    Driver a336_driver = driverService.findByName("A336");
    if (a336_driver != null) {
        driverService.remove(a336_driver);
    }

    ((NumericQuestion)a336).setDriver(null);

    JPA.em().persist(a336);
}

    }
    private void createQuestionA337() {
        // == A337
        // Emissions directes totales - Scope 1 (tCO2e)

        a337 = (IntegerQuestion) questionService.findByCode(QuestionCode.A337);
if (a337 == null) {
    a337 = new IntegerQuestion(a334, 0, QuestionCode.A337, null);
    JPA.em().persist(a337);

    // cleanup the driver
    Driver a337_driver = driverService.findByName("A337");
    if (a337_driver != null) {
        driverService.remove(a337_driver);
    }

} else {
    if (!a337.getQuestionSet().equals(a334) && a334.getQuestions().contains(a337)) {
        a334.getQuestions().remove(a337);
        JPA.em().persist(a334);
    }
    if (a337.getQuestionSet().equals(a334) && !a334.getQuestions().contains(a337)) {
        a334.getQuestions().add(a337);
        JPA.em().persist(a334);
    }
    a337.setOrderIndex(0);
    ((NumericQuestion)a337).setUnitCategory(null);

    // cleanup the driver
    Driver a337_driver = driverService.findByName("A337");
    if (a337_driver != null) {
        driverService.remove(a337_driver);
    }

    ((NumericQuestion)a337).setDriver(null);

    JPA.em().persist(a337);
}

    }
    private void createQuestionA338() {
        // == A338
        // Emissions indirectes totales - Scope 2 (tCO2e)

        a338 = (IntegerQuestion) questionService.findByCode(QuestionCode.A338);
if (a338 == null) {
    a338 = new IntegerQuestion(a334, 0, QuestionCode.A338, null);
    JPA.em().persist(a338);

    // cleanup the driver
    Driver a338_driver = driverService.findByName("A338");
    if (a338_driver != null) {
        driverService.remove(a338_driver);
    }

} else {
    if (!a338.getQuestionSet().equals(a334) && a334.getQuestions().contains(a338)) {
        a334.getQuestions().remove(a338);
        JPA.em().persist(a334);
    }
    if (a338.getQuestionSet().equals(a334) && !a334.getQuestions().contains(a338)) {
        a334.getQuestions().add(a338);
        JPA.em().persist(a334);
    }
    a338.setOrderIndex(0);
    ((NumericQuestion)a338).setUnitCategory(null);

    // cleanup the driver
    Driver a338_driver = driverService.findByName("A338");
    if (a338_driver != null) {
        driverService.remove(a338_driver);
    }

    ((NumericQuestion)a338).setDriver(null);

    JPA.em().persist(a338);
}

    }
    private void createQuestionA245() {
        // == A245
        // Nom du produit ou groupe de produits

        a245 = (StringQuestion) questionService.findByCode(QuestionCode.A245);
if (a245 == null) {
    a245 = new StringQuestion(a244, 0, QuestionCode.A245, null);
    JPA.em().persist(a245);
} else {
    ((StringQuestion)a245).setDefaultValue(null);
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

    }
    private void createQuestionA246() {
        // == A246
        // Nombre de produits vendus sur l'année du bilan

        a246 = (IntegerQuestion) questionService.findByCode(QuestionCode.A246);
if (a246 == null) {
    a246 = new IntegerQuestion(a244, 0, QuestionCode.A246, null);
    JPA.em().persist(a246);

    // cleanup the driver
    Driver a246_driver = driverService.findByName("A246");
    if (a246_driver != null) {
        driverService.remove(a246_driver);
    }

} else {
    if (!a246.getQuestionSet().equals(a244) && a244.getQuestions().contains(a246)) {
        a244.getQuestions().remove(a246);
        JPA.em().persist(a244);
    }
    if (a246.getQuestionSet().equals(a244) && !a244.getQuestions().contains(a246)) {
        a244.getQuestions().add(a246);
        JPA.em().persist(a244);
    }
    a246.setOrderIndex(0);
    ((NumericQuestion)a246).setUnitCategory(null);

    // cleanup the driver
    Driver a246_driver = driverService.findByName("A246");
    if (a246_driver != null) {
        driverService.remove(a246_driver);
    }

    ((NumericQuestion)a246).setDriver(null);

    JPA.em().persist(a246);
}

    }
    private void createQuestionA247() {
        // == A247
        // Unité dans laquelle s'exprime cette quantité

        a247 = (StringQuestion) questionService.findByCode(QuestionCode.A247);
if (a247 == null) {
    a247 = new StringQuestion(a244, 0, QuestionCode.A247, null);
    JPA.em().persist(a247);
} else {
    ((StringQuestion)a247).setDefaultValue(null);
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

    }
    private void createQuestionA248() {
        // == A248
        // S'agit-il d'un produit (ou groupe de produits) final ou intermédiaire?

        a248 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A248);
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
    ((ValueSelectionQuestion)a248).setCodeList(CodeList.TYPEPRODUIT);
    JPA.em().persist(a248);
}

    }
    private void createQuestionA249() {
        // == A249
        // Connaissez-vous la ou les applications ultérieures?

        a249 = (BooleanQuestion) questionService.findByCode(QuestionCode.A249);
if (a249 == null) {
    a249 = new BooleanQuestion(a244, 0, QuestionCode.A249, null);
    JPA.em().persist(a249);
} else {
    ((BooleanQuestion)a249).setDefaultValue(null);
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

    }
    private void createQuestionA251() {
        // == A251
        // Pièces documentaires liées au transport et stockage aval

        a251 = (DocumentQuestion) questionService.findByCode(QuestionCode.A251);
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

    }
    private void createQuestionA254() {
        // == A254
        // Poids total transporté sur l'année de bilan:

        
a254 = (DoubleQuestion) questionService.findByCode(QuestionCode.A254);
if (a254 == null) {
    a254 = new DoubleQuestion( a253, 0, QuestionCode.A254, massUnits, getUnitBySymbol("t") );
    JPA.em().persist(a254);

    // cleanup the driver
    Driver a254_driver = driverService.findByName("A254");
    if (a254_driver != null) {
        driverService.remove(a254_driver);
    }

} else {
    if (!a254.getQuestionSet().equals(a253) && a253.getQuestions().contains(a254)) {
        a253.getQuestions().remove(a254);
        JPA.em().persist(a253);
    }
    if (a254.getQuestionSet().equals(a253) && !a253.getQuestions().contains(a254)) {
        a253.getQuestions().add(a254);
        JPA.em().persist(a253);
    }
    ((NumericQuestion)a254).setUnitCategory(massUnits);
    a254.setOrderIndex(0);
    ((NumericQuestion)a254).setDefaultUnit(getUnitBySymbol("t"));


    // cleanup the driver
    Driver a254_driver = driverService.findByName("A254");
    if (a254_driver != null) {
        driverService.remove(a254_driver);
    }

    ((NumericQuestion)a254).setDriver(null);

    JPA.em().persist(a254);
}



    }
    private void createQuestionA255() {
        // == A255
        // Distance entre le point d'enlèvement et de livraison du produit

        
a255 = (DoubleQuestion) questionService.findByCode(QuestionCode.A255);
if (a255 == null) {
    a255 = new DoubleQuestion( a253, 0, QuestionCode.A255, lengthUnits, getUnitBySymbol("km") );
    JPA.em().persist(a255);

    // cleanup the driver
    Driver a255_driver = driverService.findByName("A255");
    if (a255_driver != null) {
        driverService.remove(a255_driver);
    }

} else {
    if (!a255.getQuestionSet().equals(a253) && a253.getQuestions().contains(a255)) {
        a253.getQuestions().remove(a255);
        JPA.em().persist(a253);
    }
    if (a255.getQuestionSet().equals(a253) && !a253.getQuestions().contains(a255)) {
        a253.getQuestions().add(a255);
        JPA.em().persist(a253);
    }
    ((NumericQuestion)a255).setUnitCategory(lengthUnits);
    a255.setOrderIndex(0);
    ((NumericQuestion)a255).setDefaultUnit(getUnitBySymbol("km"));

    // cleanup the driver
    Driver a255_driver = driverService.findByName("A255");
    if (a255_driver != null) {
        driverService.remove(a255_driver);
    }

    ((NumericQuestion)a255).setDriver(null);

    JPA.em().persist(a255);
}



    }
    private void createQuestionA256() {
        // == A256
        // % de distance effectuée par transport routier local par camion

        a256 = (PercentageQuestion) questionService.findByCode(QuestionCode.A256);
if (a256 == null) {
    a256 = new PercentageQuestion(a253, 0, QuestionCode.A256);
    JPA.em().persist(a256);

    // cleanup the driver
    Driver a256_driver = driverService.findByName("A256");
    if (a256_driver != null) {
        driverService.remove(a256_driver);
    }

} else {
    if (!a256.getQuestionSet().equals(a253) && a253.getQuestions().contains(a256)) {
        a253.getQuestions().remove(a256);
        JPA.em().persist(a253);
    }
    if (a256.getQuestionSet().equals(a253) && !a253.getQuestions().contains(a256)) {
        a253.getQuestions().add(a256);
        JPA.em().persist(a253);
    }
    a256.setOrderIndex(0);

    // cleanup the driver
    Driver a256_driver = driverService.findByName("A256");
    if (a256_driver != null) {
        driverService.remove(a256_driver);
    }

    ((NumericQuestion)a256).setDriver(null);

    JPA.em().persist(a256);
}

    }
    private void createQuestionA257() {
        // == A257
        // % de distance effectuée par transport routier local par camionnette

        a257 = (PercentageQuestion) questionService.findByCode(QuestionCode.A257);
if (a257 == null) {
    a257 = new PercentageQuestion(a253, 0, QuestionCode.A257);
    JPA.em().persist(a257);

    // cleanup the driver
    Driver a257_driver = driverService.findByName("A257");
    if (a257_driver != null) {
        driverService.remove(a257_driver);
    }

} else {
    if (!a257.getQuestionSet().equals(a253) && a253.getQuestions().contains(a257)) {
        a253.getQuestions().remove(a257);
        JPA.em().persist(a253);
    }
    if (a257.getQuestionSet().equals(a253) && !a253.getQuestions().contains(a257)) {
        a253.getQuestions().add(a257);
        JPA.em().persist(a253);
    }
    a257.setOrderIndex(0);

    // cleanup the driver
    Driver a257_driver = driverService.findByName("A257");
    if (a257_driver != null) {
        driverService.remove(a257_driver);
    }

    ((NumericQuestion)a257).setDriver(null);

    JPA.em().persist(a257);
}

    }
    private void createQuestionA258() {
        // == A258
        // % de distance effectuée par transport routier international

        a258 = (PercentageQuestion) questionService.findByCode(QuestionCode.A258);
if (a258 == null) {
    a258 = new PercentageQuestion(a253, 0, QuestionCode.A258);
    JPA.em().persist(a258);

    // cleanup the driver
    Driver a258_driver = driverService.findByName("A258");
    if (a258_driver != null) {
        driverService.remove(a258_driver);
    }

} else {
    if (!a258.getQuestionSet().equals(a253) && a253.getQuestions().contains(a258)) {
        a253.getQuestions().remove(a258);
        JPA.em().persist(a253);
    }
    if (a258.getQuestionSet().equals(a253) && !a253.getQuestions().contains(a258)) {
        a253.getQuestions().add(a258);
        JPA.em().persist(a253);
    }
    a258.setOrderIndex(0);

    // cleanup the driver
    Driver a258_driver = driverService.findByName("A258");
    if (a258_driver != null) {
        driverService.remove(a258_driver);
    }

    ((NumericQuestion)a258).setDriver(null);

    JPA.em().persist(a258);
}

    }
    private void createQuestionA259() {
        // == A259
        // % de distance effectuée par voie ferroviaire

        a259 = (PercentageQuestion) questionService.findByCode(QuestionCode.A259);
if (a259 == null) {
    a259 = new PercentageQuestion(a253, 0, QuestionCode.A259);
    JPA.em().persist(a259);

    // cleanup the driver
    Driver a259_driver = driverService.findByName("A259");
    if (a259_driver != null) {
        driverService.remove(a259_driver);
    }

} else {
    if (!a259.getQuestionSet().equals(a253) && a253.getQuestions().contains(a259)) {
        a253.getQuestions().remove(a259);
        JPA.em().persist(a253);
    }
    if (a259.getQuestionSet().equals(a253) && !a253.getQuestions().contains(a259)) {
        a253.getQuestions().add(a259);
        JPA.em().persist(a253);
    }
    a259.setOrderIndex(0);

    // cleanup the driver
    Driver a259_driver = driverService.findByName("A259");
    if (a259_driver != null) {
        driverService.remove(a259_driver);
    }

    ((NumericQuestion)a259).setDriver(null);

    JPA.em().persist(a259);
}

    }
    private void createQuestionA260() {
        // == A260
        // % de distance effectuée par voie maritime

        a260 = (PercentageQuestion) questionService.findByCode(QuestionCode.A260);
if (a260 == null) {
    a260 = new PercentageQuestion(a253, 0, QuestionCode.A260);
    JPA.em().persist(a260);

    // cleanup the driver
    Driver a260_driver = driverService.findByName("A260");
    if (a260_driver != null) {
        driverService.remove(a260_driver);
    }

} else {
    if (!a260.getQuestionSet().equals(a253) && a253.getQuestions().contains(a260)) {
        a253.getQuestions().remove(a260);
        JPA.em().persist(a253);
    }
    if (a260.getQuestionSet().equals(a253) && !a253.getQuestions().contains(a260)) {
        a253.getQuestions().add(a260);
        JPA.em().persist(a253);
    }
    a260.setOrderIndex(0);

    // cleanup the driver
    Driver a260_driver = driverService.findByName("A260");
    if (a260_driver != null) {
        driverService.remove(a260_driver);
    }

    ((NumericQuestion)a260).setDriver(null);

    JPA.em().persist(a260);
}

    }
    private void createQuestionA261() {
        // == A261
        // % de distance effectuée par voie fluviale

        a261 = (PercentageQuestion) questionService.findByCode(QuestionCode.A261);
if (a261 == null) {
    a261 = new PercentageQuestion(a253, 0, QuestionCode.A261);
    JPA.em().persist(a261);

    // cleanup the driver
    Driver a261_driver = driverService.findByName("A261");
    if (a261_driver != null) {
        driverService.remove(a261_driver);
    }

} else {
    if (!a261.getQuestionSet().equals(a253) && a253.getQuestions().contains(a261)) {
        a253.getQuestions().remove(a261);
        JPA.em().persist(a253);
    }
    if (a261.getQuestionSet().equals(a253) && !a253.getQuestions().contains(a261)) {
        a253.getQuestions().add(a261);
        JPA.em().persist(a253);
    }
    a261.setOrderIndex(0);

    // cleanup the driver
    Driver a261_driver = driverService.findByName("A261");
    if (a261_driver != null) {
        driverService.remove(a261_driver);
    }

    ((NumericQuestion)a261).setDriver(null);

    JPA.em().persist(a261);
}

    }
    private void createQuestionA262() {
        // == A262
        // % de distance effectuée par transport aérien court courrier (<1000 km)

        a262 = (PercentageQuestion) questionService.findByCode(QuestionCode.A262);
if (a262 == null) {
    a262 = new PercentageQuestion(a253, 0, QuestionCode.A262);
    JPA.em().persist(a262);

    // cleanup the driver
    Driver a262_driver = driverService.findByName("A262");
    if (a262_driver != null) {
        driverService.remove(a262_driver);
    }

} else {
    if (!a262.getQuestionSet().equals(a253) && a253.getQuestions().contains(a262)) {
        a253.getQuestions().remove(a262);
        JPA.em().persist(a253);
    }
    if (a262.getQuestionSet().equals(a253) && !a253.getQuestions().contains(a262)) {
        a253.getQuestions().add(a262);
        JPA.em().persist(a253);
    }
    a262.setOrderIndex(0);

    // cleanup the driver
    Driver a262_driver = driverService.findByName("A262");
    if (a262_driver != null) {
        driverService.remove(a262_driver);
    }

    ((NumericQuestion)a262).setDriver(null);

    JPA.em().persist(a262);
}

    }
    private void createQuestionA263() {
        // == A263
        // % de distance effectuée par transport aérien moyen courrier (1000 à 4000 km)

        a263 = (PercentageQuestion) questionService.findByCode(QuestionCode.A263);
if (a263 == null) {
    a263 = new PercentageQuestion(a253, 0, QuestionCode.A263);
    JPA.em().persist(a263);

    // cleanup the driver
    Driver a263_driver = driverService.findByName("A263");
    if (a263_driver != null) {
        driverService.remove(a263_driver);
    }

} else {
    if (!a263.getQuestionSet().equals(a253) && a253.getQuestions().contains(a263)) {
        a253.getQuestions().remove(a263);
        JPA.em().persist(a253);
    }
    if (a263.getQuestionSet().equals(a253) && !a253.getQuestions().contains(a263)) {
        a253.getQuestions().add(a263);
        JPA.em().persist(a253);
    }
    a263.setOrderIndex(0);

    // cleanup the driver
    Driver a263_driver = driverService.findByName("A263");
    if (a263_driver != null) {
        driverService.remove(a263_driver);
    }

    ((NumericQuestion)a263).setDriver(null);

    JPA.em().persist(a263);
}

    }
    private void createQuestionA264() {
        // == A264
        // % de distance effectuée par transport aérien long courrier (> 4000 km)

        a264 = (PercentageQuestion) questionService.findByCode(QuestionCode.A264);
if (a264 == null) {
    a264 = new PercentageQuestion(a253, 0, QuestionCode.A264);
    JPA.em().persist(a264);

    // cleanup the driver
    Driver a264_driver = driverService.findByName("A264");
    if (a264_driver != null) {
        driverService.remove(a264_driver);
    }

} else {
    if (!a264.getQuestionSet().equals(a253) && a253.getQuestions().contains(a264)) {
        a253.getQuestions().remove(a264);
        JPA.em().persist(a253);
    }
    if (a264.getQuestionSet().equals(a253) && !a253.getQuestions().contains(a264)) {
        a253.getQuestions().add(a264);
        JPA.em().persist(a253);
    }
    a264.setOrderIndex(0);

    // cleanup the driver
    Driver a264_driver = driverService.findByName("A264");
    if (a264_driver != null) {
        driverService.remove(a264_driver);
    }

    ((NumericQuestion)a264).setDriver(null);

    JPA.em().persist(a264);
}

    }
    private void createQuestionA265() {
        // == A265
        // Total (supposé être égal à 100%)

        a265 = (PercentageQuestion) questionService.findByCode(QuestionCode.A265);
if (a265 == null) {
    a265 = new PercentageQuestion(a253, 0, QuestionCode.A265);
    JPA.em().persist(a265);

    // cleanup the driver
    Driver a265_driver = driverService.findByName("A265");
    if (a265_driver != null) {
        driverService.remove(a265_driver);
    }

} else {
    if (!a265.getQuestionSet().equals(a253) && a253.getQuestions().contains(a265)) {
        a253.getQuestions().remove(a265);
        JPA.em().persist(a253);
    }
    if (a265.getQuestionSet().equals(a253) && !a253.getQuestions().contains(a265)) {
        a253.getQuestions().add(a265);
        JPA.em().persist(a253);
    }
    a265.setOrderIndex(0);

    // cleanup the driver
    Driver a265_driver = driverService.findByName("A265");
    if (a265_driver != null) {
        driverService.remove(a265_driver);
    }

    ((NumericQuestion)a265).setDriver(null);

    JPA.em().persist(a265);
}

    }
    private void createQuestionA267() {
        // == A267
        // Quel est le poids total transporté sur toute l'année du bilan (tous produits confondus)?

        
a267 = (DoubleQuestion) questionService.findByCode(QuestionCode.A267);
if (a267 == null) {
    a267 = new DoubleQuestion( a266, 0, QuestionCode.A267, massUnits, getUnitBySymbol("t") );
    JPA.em().persist(a267);

    // cleanup the driver
    Driver a267_driver = driverService.findByName("A267");
    if (a267_driver != null) {
        driverService.remove(a267_driver);
    }

} else {
    if (!a267.getQuestionSet().equals(a266) && a266.getQuestions().contains(a267)) {
        a266.getQuestions().remove(a267);
        JPA.em().persist(a266);
    }
    if (a267.getQuestionSet().equals(a266) && !a266.getQuestions().contains(a267)) {
        a266.getQuestions().add(a267);
        JPA.em().persist(a266);
    }
    ((NumericQuestion)a267).setUnitCategory(massUnits);
    a267.setOrderIndex(0);
    ((NumericQuestion)a267).setDefaultUnit(getUnitBySymbol("t"));


    // cleanup the driver
    Driver a267_driver = driverService.findByName("A267");
    if (a267_driver != null) {
        driverService.remove(a267_driver);
    }

    ((NumericQuestion)a267).setDriver(null);

    JPA.em().persist(a267);
}



    }
    private void createQuestionA268() {
        // == A268
        // Quelle est la destination géographique des produits vendus?

        a268 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A268);
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
    ((ValueSelectionQuestion)a268).setCodeList(CodeList.PROVENANCESIMPLIFIEE);
    JPA.em().persist(a268);
}

    }
    private void createQuestionA269() {
        // == A269
        // Distance moyenne assignée entre le point d'enlèvement et le point de livraison

        
a269 = (DoubleQuestion) questionService.findByCode(QuestionCode.A269);
if (a269 == null) {
    a269 = new DoubleQuestion( a266, 0, QuestionCode.A269, lengthUnits, getUnitBySymbol("km") );
    JPA.em().persist(a269);

    // cleanup the driver
    Driver a269_driver = driverService.findByName("A269");
    if (a269_driver != null) {
        driverService.remove(a269_driver);
    }

} else {
    if (!a269.getQuestionSet().equals(a266) && a266.getQuestions().contains(a269)) {
        a266.getQuestions().remove(a269);
        JPA.em().persist(a266);
    }
    if (a269.getQuestionSet().equals(a266) && !a266.getQuestions().contains(a269)) {
        a266.getQuestions().add(a269);
        JPA.em().persist(a266);
    }
    ((NumericQuestion)a269).setUnitCategory(lengthUnits);
    a269.setOrderIndex(0);
    ((NumericQuestion)a269).setDefaultUnit(getUnitBySymbol("km"));

    // cleanup the driver
    Driver a269_driver = driverService.findByName("A269");
    if (a269_driver != null) {
        driverService.remove(a269_driver);
    }

    ((NumericQuestion)a269).setDriver(null);

    JPA.em().persist(a269);
}



    }
    private void createQuestionA270() {
        // == A270
        // Distance moyenne assignée entre le point d'enlèvement et le point de livraison

        
a270 = (DoubleQuestion) questionService.findByCode(QuestionCode.A270);
if (a270 == null) {
    a270 = new DoubleQuestion( a266, 0, QuestionCode.A270, lengthUnits, getUnitBySymbol("km") );
    JPA.em().persist(a270);

    // cleanup the driver
    Driver a270_driver = driverService.findByName("A270");
    if (a270_driver != null) {
        driverService.remove(a270_driver);
    }

} else {
    if (!a270.getQuestionSet().equals(a266) && a266.getQuestions().contains(a270)) {
        a266.getQuestions().remove(a270);
        JPA.em().persist(a266);
    }
    if (a270.getQuestionSet().equals(a266) && !a266.getQuestions().contains(a270)) {
        a266.getQuestions().add(a270);
        JPA.em().persist(a266);
    }
    ((NumericQuestion)a270).setUnitCategory(lengthUnits);
    a270.setOrderIndex(0);
    ((NumericQuestion)a270).setDefaultUnit(getUnitBySymbol("km"));

    // cleanup the driver
    Driver a270_driver = driverService.findByName("A270");
    if (a270_driver != null) {
        driverService.remove(a270_driver);
    }

    ((NumericQuestion)a270).setDriver(null);

    JPA.em().persist(a270);
}



    }
    private void createQuestionA271() {
        // == A271
        // Distance moyenne assignée entre le point d'enlèvement et le point de livraison

        
a271 = (DoubleQuestion) questionService.findByCode(QuestionCode.A271);
if (a271 == null) {
    a271 = new DoubleQuestion( a266, 0, QuestionCode.A271, lengthUnits, getUnitBySymbol("km") );
    JPA.em().persist(a271);

    // cleanup the driver
    Driver a271_driver = driverService.findByName("A271");
    if (a271_driver != null) {
        driverService.remove(a271_driver);
    }

} else {
    if (!a271.getQuestionSet().equals(a266) && a266.getQuestions().contains(a271)) {
        a266.getQuestions().remove(a271);
        JPA.em().persist(a266);
    }
    if (a271.getQuestionSet().equals(a266) && !a266.getQuestions().contains(a271)) {
        a266.getQuestions().add(a271);
        JPA.em().persist(a266);
    }
    ((NumericQuestion)a271).setUnitCategory(lengthUnits);
    a271.setOrderIndex(0);
    ((NumericQuestion)a271).setDefaultUnit(getUnitBySymbol("km"));

    // cleanup the driver
    Driver a271_driver = driverService.findByName("A271");
    if (a271_driver != null) {
        driverService.remove(a271_driver);
    }

    ((NumericQuestion)a271).setDriver(null);

    JPA.em().persist(a271);
}



    }
    private void createQuestionA274() {
        // == A274
        // Poste de consommation

        a274 = (StringQuestion) questionService.findByCode(QuestionCode.A274);
if (a274 == null) {
    a274 = new StringQuestion(a273, 0, QuestionCode.A274, null);
    JPA.em().persist(a274);
} else {
    ((StringQuestion)a274).setDefaultValue(null);
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

    }
    private void createQuestionA276() {
        // == A276
        // Combustible utilisé

        a276 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A276);
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
    ((ValueSelectionQuestion)a276).setCodeList(CodeList.COMBUSTIBLE);
    JPA.em().persist(a276);
}

    }
    private void createQuestionA277() {
        // == A277
        // Quantité

        
a277 = (DoubleQuestion) questionService.findByCode(QuestionCode.A277);
if (a277 == null) {
    a277 = new DoubleQuestion( a275, 0, QuestionCode.A277, energyUnits, getUnitBySymbol("kW.h") );
    JPA.em().persist(a277);

    // cleanup the driver
    Driver a277_driver = driverService.findByName("A277");
    if (a277_driver != null) {
        driverService.remove(a277_driver);
    }


} else {
    if (!a277.getQuestionSet().equals(a275) && a275.getQuestions().contains(a277)) {
        a275.getQuestions().remove(a277);
        JPA.em().persist(a275);
    }
    if (a277.getQuestionSet().equals(a275) && !a275.getQuestions().contains(a277)) {
        a275.getQuestions().add(a277);
        JPA.em().persist(a275);
    }
    ((NumericQuestion)a277).setUnitCategory(energyUnits);
    a277.setOrderIndex(0);
    ((NumericQuestion)a277).setDefaultUnit(getUnitBySymbol("kW.h"));

    // cleanup the driver
    Driver a277_driver = driverService.findByName("A277");
    if (a277_driver != null) {
        driverService.remove(a277_driver);
    }

    ((NumericQuestion)a277).setDriver(null);

    JPA.em().persist(a277);
}



    }
    private void createQuestionA1025() {
        // == A1025
        // Combustible utilisé

        a1025 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A1025);
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
    ((ValueSelectionQuestion)a1025).setCodeList(CodeList.COMBUSTIBLEVOLUME);
    JPA.em().persist(a1025);
}

    }
    private void createQuestionA1026() {
        // == A1026
        // Quantité

        
a1026 = (DoubleQuestion) questionService.findByCode(QuestionCode.A1026);
if (a1026 == null) {
    a1026 = new DoubleQuestion( a1024, 0, QuestionCode.A1026, volumeUnits, volumeUnits.getMainUnit() );
    JPA.em().persist(a1026);

    // cleanup the driver
    Driver a1026_driver = driverService.findByName("A1026");
    if (a1026_driver != null) {
        driverService.remove(a1026_driver);
    }

} else {
    if (!a1026.getQuestionSet().equals(a1024) && a1024.getQuestions().contains(a1026)) {
        a1024.getQuestions().remove(a1026);
        JPA.em().persist(a1024);
    }
    if (a1026.getQuestionSet().equals(a1024) && !a1024.getQuestions().contains(a1026)) {
        a1024.getQuestions().add(a1026);
        JPA.em().persist(a1024);
    }
    ((NumericQuestion)a1026).setUnitCategory(volumeUnits);
    a1026.setOrderIndex(0);
    ((NumericQuestion)a1026).setDefaultUnit(volumeUnits.getMainUnit());

    // cleanup the driver
    Driver a1026_driver = driverService.findByName("A1026");
    if (a1026_driver != null) {
        driverService.remove(a1026_driver);
    }

    ((NumericQuestion)a1026).setDriver(null);

    JPA.em().persist(a1026);
}



    }
    private void createQuestionA1028() {
        // == A1028
        // Combustible utilisé

        a1028 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A1028);
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
    ((ValueSelectionQuestion)a1028).setCodeList(CodeList.COMBUSTIBLEPOIDS);
    JPA.em().persist(a1028);
}

    }
    private void createQuestionA1029() {
        // == A1029
        // Quantité

        
a1029 = (DoubleQuestion) questionService.findByCode(QuestionCode.A1029);
if (a1029 == null) {
    a1029 = new DoubleQuestion( a1027, 0, QuestionCode.A1029, massUnits, massUnits.getMainUnit() );
    JPA.em().persist(a1029);

    // cleanup the driver
    Driver a1029_driver = driverService.findByName("A1029");
    if (a1029_driver != null) {
        driverService.remove(a1029_driver);
    }

} else {
    if (!a1029.getQuestionSet().equals(a1027) && a1027.getQuestions().contains(a1029)) {
        a1027.getQuestions().remove(a1029);
        JPA.em().persist(a1027);
    }
    if (a1029.getQuestionSet().equals(a1027) && !a1027.getQuestions().contains(a1029)) {
        a1027.getQuestions().add(a1029);
        JPA.em().persist(a1027);
    }
    ((NumericQuestion)a1029).setUnitCategory(massUnits);
    a1029.setOrderIndex(0);
    ((NumericQuestion)a1029).setDefaultUnit(massUnits.getMainUnit());


    // cleanup the driver
    Driver a1029_driver = driverService.findByName("A1029");
    if (a1029_driver != null) {
        driverService.remove(a1029_driver);
    }

    ((NumericQuestion)a1029).setDriver(null);

    JPA.em().persist(a1029);
}



    }
    private void createQuestionA278() {
        // == A278
        // Electricité

        
a278 = (DoubleQuestion) questionService.findByCode(QuestionCode.A278);
if (a278 == null) {
    a278 = new DoubleQuestion( a273, 0, QuestionCode.A278, energyUnits, getUnitBySymbol("kW.h") );
    JPA.em().persist(a278);

    // cleanup the driver
    Driver a278_driver = driverService.findByName("A278");
    if (a278_driver != null) {
        driverService.remove(a278_driver);
    }


} else {
    if (!a278.getQuestionSet().equals(a273) && a273.getQuestions().contains(a278)) {
        a273.getQuestions().remove(a278);
        JPA.em().persist(a273);
    }
    if (a278.getQuestionSet().equals(a273) && !a273.getQuestions().contains(a278)) {
        a273.getQuestions().add(a278);
        JPA.em().persist(a273);
    }
    ((NumericQuestion)a278).setUnitCategory(energyUnits);
    a278.setOrderIndex(0);
    ((NumericQuestion)a278).setDefaultUnit(getUnitBySymbol("kW.h"));

    // cleanup the driver
    Driver a278_driver = driverService.findByName("A278");
    if (a278_driver != null) {
        driverService.remove(a278_driver);
    }

    ((NumericQuestion)a278).setDriver(null);

    JPA.em().persist(a278);
}



    }
    private void createQuestionA280() {
        // == A280
        // Type de gaz

        a280 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A280);
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
    ((ValueSelectionQuestion)a280).setCodeList(CodeList.FRIGORIGENE);
    JPA.em().persist(a280);
}

    }
    private void createQuestionA281() {
        // == A281
        // Quantité de recharge nécessaire pour l'année

        
a281 = (DoubleQuestion) questionService.findByCode(QuestionCode.A281);
if (a281 == null) {
    a281 = new DoubleQuestion( a279, 0, QuestionCode.A281, massUnits, massUnits.getMainUnit() );
    JPA.em().persist(a281);

    // cleanup the driver
    Driver a281_driver = driverService.findByName("A281");
    if (a281_driver != null) {
        driverService.remove(a281_driver);
    }

} else {
    if (!a281.getQuestionSet().equals(a279) && a279.getQuestions().contains(a281)) {
        a279.getQuestions().remove(a281);
        JPA.em().persist(a279);
    }
    if (a281.getQuestionSet().equals(a279) && !a279.getQuestions().contains(a281)) {
        a279.getQuestions().add(a281);
        JPA.em().persist(a279);
    }
    ((NumericQuestion)a281).setUnitCategory(massUnits);
    a281.setOrderIndex(0);
    ((NumericQuestion)a281).setDefaultUnit(massUnits.getMainUnit());


    // cleanup the driver
    Driver a281_driver = driverService.findByName("A281");
    if (a281_driver != null) {
        driverService.remove(a281_driver);
    }

    ((NumericQuestion)a281).setDriver(null);

    JPA.em().persist(a281);
}



    }
    private void createQuestionA283() {
        // == A283
        // Fournir ici les documents éventuels justifiant les données suivantes

        a283 = (DocumentQuestion) questionService.findByCode(QuestionCode.A283);
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

    }
    private void createQuestionA285() {
        // == A285
        // Combustible utilisé

        a285 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A285);
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
    ((ValueSelectionQuestion)a285).setCodeList(CodeList.COMBUSTIBLE);
    JPA.em().persist(a285);
}

    }
    private void createQuestionA286() {
        // == A286
        // Quantité

        
a286 = (DoubleQuestion) questionService.findByCode(QuestionCode.A286);
if (a286 == null) {
    a286 = new DoubleQuestion( a284, 0, QuestionCode.A286, energyUnits, getUnitBySymbol("kW.h") );
    JPA.em().persist(a286);

    // cleanup the driver
    Driver a286_driver = driverService.findByName("A286");
    if (a286_driver != null) {
        driverService.remove(a286_driver);
    }


} else {
    if (!a286.getQuestionSet().equals(a284) && a284.getQuestions().contains(a286)) {
        a284.getQuestions().remove(a286);
        JPA.em().persist(a284);
    }
    if (a286.getQuestionSet().equals(a284) && !a284.getQuestions().contains(a286)) {
        a284.getQuestions().add(a286);
        JPA.em().persist(a284);
    }
    ((NumericQuestion)a286).setUnitCategory(energyUnits);
    a286.setOrderIndex(0);
    ((NumericQuestion)a286).setDefaultUnit(getUnitBySymbol("kW.h"));

    // cleanup the driver
    Driver a286_driver = driverService.findByName("A286");
    if (a286_driver != null) {
        driverService.remove(a286_driver);
    }

    ((NumericQuestion)a286).setDriver(null);

    JPA.em().persist(a286);
}



    }
    private void createQuestionA1031() {
        // == A1031
        // Combustible utilisé

        a1031 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A1031);
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
    ((ValueSelectionQuestion)a1031).setCodeList(CodeList.COMBUSTIBLEVOLUME);
    JPA.em().persist(a1031);
}

    }
    private void createQuestionA1032() {
        // == A1032
        // Quantité

        
a1032 = (DoubleQuestion) questionService.findByCode(QuestionCode.A1032);
if (a1032 == null) {
    a1032 = new DoubleQuestion( a1030, 0, QuestionCode.A1032, volumeUnits, volumeUnits.getMainUnit() );
    JPA.em().persist(a1032);

    // cleanup the driver
    Driver a1032_driver = driverService.findByName("A1032");
    if (a1032_driver != null) {
        driverService.remove(a1032_driver);
    }

} else {
    if (!a1032.getQuestionSet().equals(a1030) && a1030.getQuestions().contains(a1032)) {
        a1030.getQuestions().remove(a1032);
        JPA.em().persist(a1030);
    }
    if (a1032.getQuestionSet().equals(a1030) && !a1030.getQuestions().contains(a1032)) {
        a1030.getQuestions().add(a1032);
        JPA.em().persist(a1030);
    }
    ((NumericQuestion)a1032).setUnitCategory(volumeUnits);
    a1032.setOrderIndex(0);
    ((NumericQuestion)a1032).setDefaultUnit(volumeUnits.getMainUnit());

    // cleanup the driver
    Driver a1032_driver = driverService.findByName("A1032");
    if (a1032_driver != null) {
        driverService.remove(a1032_driver);
    }

    ((NumericQuestion)a1032).setDriver(null);

    JPA.em().persist(a1032);
}



    }
    private void createQuestionA1034() {
        // == A1034
        // Combustible utilisé

        a1034 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A1034);
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
    ((ValueSelectionQuestion)a1034).setCodeList(CodeList.COMBUSTIBLEPOIDS);
    JPA.em().persist(a1034);
}

    }
    private void createQuestionA1035() {
        // == A1035
        // Quantité

        
a1035 = (DoubleQuestion) questionService.findByCode(QuestionCode.A1035);
if (a1035 == null) {
    a1035 = new DoubleQuestion( a1033, 0, QuestionCode.A1035, massUnits, massUnits.getMainUnit() );
    JPA.em().persist(a1035);

    // cleanup the driver
    Driver a1035_driver = driverService.findByName("A1035");
    if (a1035_driver != null) {
        driverService.remove(a1035_driver);
    }

} else {
    if (!a1035.getQuestionSet().equals(a1033) && a1033.getQuestions().contains(a1035)) {
        a1033.getQuestions().remove(a1035);
        JPA.em().persist(a1033);
    }
    if (a1035.getQuestionSet().equals(a1033) && !a1033.getQuestions().contains(a1035)) {
        a1033.getQuestions().add(a1035);
        JPA.em().persist(a1033);
    }
    ((NumericQuestion)a1035).setUnitCategory(massUnits);
    a1035.setOrderIndex(0);
    ((NumericQuestion)a1035).setDefaultUnit(massUnits.getMainUnit());


    // cleanup the driver
    Driver a1035_driver = driverService.findByName("A1035");
    if (a1035_driver != null) {
        driverService.remove(a1035_driver);
    }

    ((NumericQuestion)a1035).setDriver(null);

    JPA.em().persist(a1035);
}



    }
    private void createQuestionA287() {
        // == A287
        // Electricité

        
a287 = (DoubleQuestion) questionService.findByCode(QuestionCode.A287);
if (a287 == null) {
    a287 = new DoubleQuestion( a282, 0, QuestionCode.A287, energyUnits, getUnitBySymbol("kW.h") );
    JPA.em().persist(a287);

    // cleanup the driver
    Driver a287_driver = driverService.findByName("A287");
    if (a287_driver != null) {
        driverService.remove(a287_driver);
    }


} else {
    if (!a287.getQuestionSet().equals(a282) && a282.getQuestions().contains(a287)) {
        a282.getQuestions().remove(a287);
        JPA.em().persist(a282);
    }
    if (a287.getQuestionSet().equals(a282) && !a282.getQuestions().contains(a287)) {
        a282.getQuestions().add(a287);
        JPA.em().persist(a282);
    }
    ((NumericQuestion)a287).setUnitCategory(energyUnits);
    a287.setOrderIndex(0);
    ((NumericQuestion)a287).setDefaultUnit(getUnitBySymbol("kW.h"));

    // cleanup the driver
    Driver a287_driver = driverService.findByName("A287");
    if (a287_driver != null) {
        driverService.remove(a287_driver);
    }

    ((NumericQuestion)a287).setDriver(null);

    JPA.em().persist(a287);
}



    }
    private void createQuestionA289() {
        // == A289
        // Type de gaz

        a289 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A289);
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
    ((ValueSelectionQuestion)a289).setCodeList(CodeList.FRIGORIGENE);
    JPA.em().persist(a289);
}

    }
    private void createQuestionA290() {
        // == A290
        // Quantité de recharge nécessaire pour l'année

        
a290 = (DoubleQuestion) questionService.findByCode(QuestionCode.A290);
if (a290 == null) {
    a290 = new DoubleQuestion( a288, 0, QuestionCode.A290, massUnits, massUnits.getMainUnit() );
    JPA.em().persist(a290);

    // cleanup the driver
    Driver a290_driver = driverService.findByName("A290");
    if (a290_driver != null) {
        driverService.remove(a290_driver);
    }

} else {
    if (!a290.getQuestionSet().equals(a288) && a288.getQuestions().contains(a290)) {
        a288.getQuestions().remove(a290);
        JPA.em().persist(a288);
    }
    if (a290.getQuestionSet().equals(a288) && !a288.getQuestions().contains(a290)) {
        a288.getQuestions().add(a290);
        JPA.em().persist(a288);
    }
    ((NumericQuestion)a290).setUnitCategory(massUnits);
    a290.setOrderIndex(0);
    ((NumericQuestion)a290).setDefaultUnit(massUnits.getMainUnit());


    // cleanup the driver
    Driver a290_driver = driverService.findByName("A290");
    if (a290_driver != null) {
        driverService.remove(a290_driver);
    }

    ((NumericQuestion)a290).setDriver(null);

    JPA.em().persist(a290);
}



    }
    private void createQuestionA292() {
        // == A292
        // Fournir ici les documents éventuels justifiant les données suivantes

        a292 = (DocumentQuestion) questionService.findByCode(QuestionCode.A292);
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

    }
    private void createQuestionA293() {
        // == A293
        // Nombre total d'utilisations du produit ou groupe de produits sur toute sa durée de vie

        a293 = (IntegerQuestion) questionService.findByCode(QuestionCode.A293);
if (a293 == null) {
    a293 = new IntegerQuestion(a291, 0, QuestionCode.A293, null);
    JPA.em().persist(a293);

    // cleanup the driver
    Driver a293_driver = driverService.findByName("A293");
    if (a293_driver != null) {
        driverService.remove(a293_driver);
    }

} else {
    if (!a293.getQuestionSet().equals(a291) && a291.getQuestions().contains(a293)) {
        a291.getQuestions().remove(a293);
        JPA.em().persist(a291);
    }
    if (a293.getQuestionSet().equals(a291) && !a291.getQuestions().contains(a293)) {
        a291.getQuestions().add(a293);
        JPA.em().persist(a291);
    }
    a293.setOrderIndex(0);
    ((NumericQuestion)a293).setUnitCategory(null);

    // cleanup the driver
    Driver a293_driver = driverService.findByName("A293");
    if (a293_driver != null) {
        driverService.remove(a293_driver);
    }

    ((NumericQuestion)a293).setDriver(null);

    JPA.em().persist(a293);
}

    }
    private void createQuestionA294() {
        // == A294
        // Consommation de diesel par utilisation de produit

        
a294 = (DoubleQuestion) questionService.findByCode(QuestionCode.A294);
if (a294 == null) {
    a294 = new DoubleQuestion( a291, 0, QuestionCode.A294, volumeUnits, getUnitBySymbol("l") );
    JPA.em().persist(a294);

    // cleanup the driver
    Driver a294_driver = driverService.findByName("A294");
    if (a294_driver != null) {
        driverService.remove(a294_driver);
    }

} else {
    if (!a294.getQuestionSet().equals(a291) && a291.getQuestions().contains(a294)) {
        a291.getQuestions().remove(a294);
        JPA.em().persist(a291);
    }
    if (a294.getQuestionSet().equals(a291) && !a291.getQuestions().contains(a294)) {
        a291.getQuestions().add(a294);
        JPA.em().persist(a291);
    }
    ((NumericQuestion)a294).setUnitCategory(volumeUnits);
    a294.setOrderIndex(0);
    ((NumericQuestion)a294).setDefaultUnit(getUnitBySymbol("l"));

    // cleanup the driver
    Driver a294_driver = driverService.findByName("A294");
    if (a294_driver != null) {
        driverService.remove(a294_driver);
    }

    ((NumericQuestion)a294).setDriver(null);

    JPA.em().persist(a294);
}



    }
    private void createQuestionA295() {
        // == A295
        // Consommation d'essence par utilisation de produit

        
a295 = (DoubleQuestion) questionService.findByCode(QuestionCode.A295);
if (a295 == null) {
    a295 = new DoubleQuestion( a291, 0, QuestionCode.A295, volumeUnits, getUnitBySymbol("l") );
    JPA.em().persist(a295);

    // cleanup the driver
    Driver a295_driver = driverService.findByName("A295");
    if (a295_driver != null) {
        driverService.remove(a295_driver);
    }

} else {
    if (!a295.getQuestionSet().equals(a291) && a291.getQuestions().contains(a295)) {
        a291.getQuestions().remove(a295);
        JPA.em().persist(a291);
    }
    if (a295.getQuestionSet().equals(a291) && !a291.getQuestions().contains(a295)) {
        a291.getQuestions().add(a295);
        JPA.em().persist(a291);
    }
    ((NumericQuestion)a295).setUnitCategory(volumeUnits);
    a295.setOrderIndex(0);
    ((NumericQuestion)a295).setDefaultUnit(getUnitBySymbol("l"));

    // cleanup the driver
    Driver a295_driver = driverService.findByName("A295");
    if (a295_driver != null) {
        driverService.remove(a295_driver);
    }

    ((NumericQuestion)a295).setDriver(null);

    JPA.em().persist(a295);
}



    }
    private void createQuestionA296() {
        // == A296
        // Consommation d'électricité par utilisation de produit

        
a296 = (DoubleQuestion) questionService.findByCode(QuestionCode.A296);
if (a296 == null) {
    a296 = new DoubleQuestion( a291, 0, QuestionCode.A296, energyUnits, getUnitBySymbol("kW.h") );
    JPA.em().persist(a296);

    // cleanup the driver
    Driver a296_driver = driverService.findByName("A296");
    if (a296_driver != null) {
        driverService.remove(a296_driver);
    }


} else {
    if (!a296.getQuestionSet().equals(a291) && a291.getQuestions().contains(a296)) {
        a291.getQuestions().remove(a296);
        JPA.em().persist(a291);
    }
    if (a296.getQuestionSet().equals(a291) && !a291.getQuestions().contains(a296)) {
        a291.getQuestions().add(a296);
        JPA.em().persist(a291);
    }
    ((NumericQuestion)a296).setUnitCategory(energyUnits);
    a296.setOrderIndex(0);
    ((NumericQuestion)a296).setDefaultUnit(getUnitBySymbol("kW.h"));

    // cleanup the driver
    Driver a296_driver = driverService.findByName("A296");
    if (a296_driver != null) {
        driverService.remove(a296_driver);
    }

    ((NumericQuestion)a296).setDriver(null);

    JPA.em().persist(a296);
}



    }
    private void createQuestionA298() {
        // == A298
        // Gaz émis

        a298 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A298);
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
    ((ValueSelectionQuestion)a298).setCodeList(CodeList.GESSIMPLIFIE);
    JPA.em().persist(a298);
}

    }
    private void createQuestionA299() {
        // == A299
        // Quantité

        
a299 = (DoubleQuestion) questionService.findByCode(QuestionCode.A299);
if (a299 == null) {
    a299 = new DoubleQuestion( a297, 0, QuestionCode.A299, massUnits, massUnits.getMainUnit() );
    JPA.em().persist(a299);

    // cleanup the driver
    Driver a299_driver = driverService.findByName("A299");
    if (a299_driver != null) {
        driverService.remove(a299_driver);
    }

} else {
    if (!a299.getQuestionSet().equals(a297) && a297.getQuestions().contains(a299)) {
        a297.getQuestions().remove(a299);
        JPA.em().persist(a297);
    }
    if (a299.getQuestionSet().equals(a297) && !a297.getQuestions().contains(a299)) {
        a297.getQuestions().add(a299);
        JPA.em().persist(a297);
    }
    ((NumericQuestion)a299).setUnitCategory(massUnits);
    a299.setOrderIndex(0);
    ((NumericQuestion)a299).setDefaultUnit(massUnits.getMainUnit());


    // cleanup the driver
    Driver a299_driver = driverService.findByName("A299");
    if (a299_driver != null) {
        driverService.remove(a299_driver);
    }

    ((NumericQuestion)a299).setDriver(null);

    JPA.em().persist(a299);
}



    }
    private void createQuestionA301() {
        // == A301
        // Fournir ici les documents éventuels justifiant les données suivantes

        a301 = (DocumentQuestion) questionService.findByCode(QuestionCode.A301);
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

    }
    private void createQuestionA302() {
        // == A302
        // Poids total de produit vendu

        
a302 = (DoubleQuestion) questionService.findByCode(QuestionCode.A302);
if (a302 == null) {
    a302 = new DoubleQuestion( a300, 0, QuestionCode.A302, massUnits, getUnitBySymbol("t") );
    JPA.em().persist(a302);

    // cleanup the driver
    Driver a302_driver = driverService.findByName("A302");
    if (a302_driver != null) {
        driverService.remove(a302_driver);
    }

} else {
    if (!a302.getQuestionSet().equals(a300) && a300.getQuestions().contains(a302)) {
        a300.getQuestions().remove(a302);
        JPA.em().persist(a300);
    }
    if (a302.getQuestionSet().equals(a300) && !a300.getQuestions().contains(a302)) {
        a300.getQuestions().add(a302);
        JPA.em().persist(a300);
    }
    ((NumericQuestion)a302).setUnitCategory(massUnits);
    a302.setOrderIndex(0);
    ((NumericQuestion)a302).setDefaultUnit(getUnitBySymbol("t"));


    // cleanup the driver
    Driver a302_driver = driverService.findByName("A302");
    if (a302_driver != null) {
        driverService.remove(a302_driver);
    }

    ((NumericQuestion)a302).setDriver(null);

    JPA.em().persist(a302);
}



    }
    private void createQuestionA5011() {
        // == A5011
        // Poste de déchet

        a5011 = (StringQuestion) questionService.findByCode(QuestionCode.A5011);
if (a5011 == null) {
    a5011 = new StringQuestion(a5010, 0, QuestionCode.A5011, null);
    JPA.em().persist(a5011);
} else {
    ((StringQuestion)a5011).setDefaultValue(null);
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

    }
    private void createQuestionA5012() {
        // == A5012
        // Poids total de ce poste de déchet après utilisation ou consommation

        
a5012 = (DoubleQuestion) questionService.findByCode(QuestionCode.A5012);
if (a5012 == null) {
    a5012 = new DoubleQuestion( a5010, 0, QuestionCode.A5012, massUnits, getUnitBySymbol("t") );
    JPA.em().persist(a5012);

    // cleanup the driver
    Driver a5012_driver = driverService.findByName("A5012");
    if (a5012_driver != null) {
        driverService.remove(a5012_driver);
    }

} else {
    if (!a5012.getQuestionSet().equals(a5010) && a5010.getQuestions().contains(a5012)) {
        a5010.getQuestions().remove(a5012);
        JPA.em().persist(a5010);
    }
    if (a5012.getQuestionSet().equals(a5010) && !a5010.getQuestions().contains(a5012)) {
        a5010.getQuestions().add(a5012);
        JPA.em().persist(a5010);
    }
    ((NumericQuestion)a5012).setUnitCategory(massUnits);
    a5012.setOrderIndex(0);
    ((NumericQuestion)a5012).setDefaultUnit(getUnitBySymbol("t"));


    // cleanup the driver
    Driver a5012_driver = driverService.findByName("A5012");
    if (a5012_driver != null) {
        driverService.remove(a5012_driver);
    }

    ((NumericQuestion)a5012).setDriver(null);

    JPA.em().persist(a5012);
}



    }
    private void createQuestionA5013() {
        // == A5013
        // Type de déchet et de traitement de ce poste de déchet

        a5013 = (ValueSelectionQuestion) questionService.findByCode(QuestionCode.A5013);
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
    ((ValueSelectionQuestion)a5013).setCodeList(CodeList.GESTIONDECHETS);
    JPA.em().persist(a5013);
}

    }
    private void createQuestionA5014() {
        // == A5014
        // % du poids de déchet traité par la méthode précédemment renseignée

        a5014 = (PercentageQuestion) questionService.findByCode(QuestionCode.A5014);
if (a5014 == null) {
    a5014 = new PercentageQuestion(a5010, 0, QuestionCode.A5014);
    JPA.em().persist(a5014);

    // cleanup the driver
    Driver a5014_driver = driverService.findByName("A5014");
    if (a5014_driver != null) {
        driverService.remove(a5014_driver);
    }

} else {
    if (!a5014.getQuestionSet().equals(a5010) && a5010.getQuestions().contains(a5014)) {
        a5010.getQuestions().remove(a5014);
        JPA.em().persist(a5010);
    }
    if (a5014.getQuestionSet().equals(a5010) && !a5010.getQuestions().contains(a5014)) {
        a5010.getQuestions().add(a5014);
        JPA.em().persist(a5010);
    }
    a5014.setOrderIndex(0);

    // cleanup the driver
    Driver a5014_driver = driverService.findByName("A5014");
    if (a5014_driver != null) {
        driverService.remove(a5014_driver);
    }

    ((NumericQuestion)a5014).setDriver(null);

    JPA.em().persist(a5014);
}

    }


    private UnitCategory getUnitCategoryByCode(UnitCategoryCode unitCategoryCode) {
        return unitCategoryService.findByCode(unitCategoryCode);
    }

    private Unit getUnitBySymbol(String symbol) {
        return unitService.findBySymbol(symbol);
    }

}



