package eu.factorx.awac.util.data.importer.badImporter;

import eu.factorx.awac.models.code.type.*;
import eu.factorx.awac.models.data.question.Question;
import eu.factorx.awac.models.data.question.type.BooleanQuestion;
import eu.factorx.awac.service.QuestionService;
import eu.factorx.awac.util.MyrmexException;
import eu.factorx.awac.util.data.importer.ExcelEquivalenceColumn;
import eu.factorx.awac.util.data.importer.WorkbookDataImporter;
import eu.factorx.awac.util.data.importer.badImporter.Reader.Data;
import eu.factorx.awac.util.data.importer.badImporter.Reader.ExcelReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import play.Logger;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by florian on 29/08/14.
 * <p/>
 * !! the column F of the excel file is used to detect a bad : if the column is not empty, it's a BAD !!
 */
@Component
public class BADImporter extends WorkbookDataImporter {

	private final static String FILE_PATH = "data_importer_resources/awac_data_09-08-2014/AWAC-tous-calcul_FE.xls";

	private final static boolean DEBUG = false;

	private final static int TOTAL_COL = ExcelEquivalenceColumn.T;

	//only string => ref to a BaseActivityDataKey
	private final static int BAD_KEY_COL = ExcelEquivalenceColumn.B;

	//only string
	private final static int BAD_NAME_COL = ExcelEquivalenceColumn.C;

	//only integer
	private final static int BAD_RANK_COL = ExcelEquivalenceColumn.E;

	//can be an answer
	private final static int BAD_SPECIFIC_PURPOSE_COL = ExcelEquivalenceColumn.F;

	//condition
	private final static int BAD_CONDITION_COL = ExcelEquivalenceColumn.G;

	//ActivityCategoryKey
	private final static int BAD_ACTIVITY_CATEGORY_KEY_COL = ExcelEquivalenceColumn.H;

	//ActivitySubCategoryKey
	private final static int BAD_ACTIVITY_SUB_CATEGORY_KEY_COL = ExcelEquivalenceColumn.J;

	//ActivityTypeKey
	private final static int BAD_ACTIVITY_TYPE_KEY_COL = ExcelEquivalenceColumn.L;

	//ActivitySourceKey or answer
	private final static int BAD_ACTIVITY_SOURCE_KEY_COL = ExcelEquivalenceColumn.N;

	//UnitCode
	private final static int BAD_UNIT_COL = ExcelEquivalenceColumn.Q;

	//activityOwnerShip : boolean
	private final static int BAD_ACTIVITY_OWNERSHIP_COL = ExcelEquivalenceColumn.P;

	//value
	private final static int BAD_VALUE_COL = ExcelEquivalenceColumn.S;


	private List<BAD> listBAD = new ArrayList<>();

	@Autowired
	private QuestionService questionService;


	public void run() {
		try {
			importData();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final String ENTERPRISE_METHOD = "site entreprise-activityData";

	@Override
	protected void importData() throws Exception {

		play.Logger.info("run badimporter....");


		ExcelReader excelReader = new ExcelReader();

		Data data = excelReader.readFile(FILE_PATH,ENTERPRISE_METHOD);

		//2. read
		reader(data);

		play.Logger.info("run badimporter end !");

	}

	public void reader(Data data) {


		for (int line = 1; line < data.getNbRows(); line++) {

			//escape the first line : presentation
			if (line == 1) {
				continue;
			}

			//F is the reference column => not empty = it's a BAD !
			if (data.getData(ExcelEquivalenceColumn.B, line) != null &&
					!data.getData(ExcelEquivalenceColumn.B, line).equals("BAD-KEY") &&
					data.getData(ExcelEquivalenceColumn.B, line).contains("BAD")) {
				// activity data founded

				//print the line in DEBUG
				addToLog(LogType.INFO, line, "This is a BAD " + data.getData(ExcelEquivalenceColumn.B, line));


				//create and write new BAD
				boolean toGenerate = true;

				// --- test badKey ---
				String badKey = normalize(data.getData(BAD_KEY_COL, line));
				if (badKey == null || badKey.length() == 0) {
					addToLog(LogType.ERROR, line, "There is no badKey : " + data.getData(BAD_KEY_COL, line) + ". The bad was not generated");
					toGenerate = false;
				}

				if (!controlList(BaseActivityDataCode.class, badKey)) {
					addToLog(LogType.ERROR, line, "This is not a badKey : " + data.getData(BAD_KEY_COL, line) + ". The bad was not generated");
					toGenerate = false;
				}

				// ---- name ----
				String name = data.getData(BAD_NAME_COL, line);
				if (name == null || name.length() == 0) {
					addToLog(LogType.ERROR, line, "There is no name: " + data.getData(BAD_NAME_COL, line) + ". The bad was not generated");
					toGenerate = false;
				}

				// ---- control rank ---
				// TODO can be null
				Integer rank = null;
				if (data.getData(BAD_RANK_COL, line) == null || data.getData(BAD_RANK_COL, line).length() == 0) {
					addToLog(LogType.ERROR, line, "There is no rank : " + data.getData(BAD_RANK_COL, line) + ". The bad was not generated");
					toGenerate = false;
				}
				try {
					rank = Integer.parseInt(data.getData(BAD_RANK_COL, line));
				} catch (NumberFormatException e) {
					addToLog(LogType.ERROR, line, "The rank is not a valid number  : " + data.getData(BAD_RANK_COL, line) + ". The bad was not generated");
				}

				// ---- specific purpose ----
				String specificPurpose = null;
				//!! can be an activityCode or an answer or can be null
				//excepted in fine => String
				if (data.getData(BAD_SPECIFIC_PURPOSE_COL, line) != null && data.getData(BAD_SPECIFIC_PURPOSE_COL, line).length() > 0) {


					specificPurpose = data.getData(BAD_SPECIFIC_PURPOSE_COL, line);

					//test if the specific purpose is a code
					if (controlList(ActivityCategoryCode.class, specificPurpose)) {
						addToLog(LogType.DEBUG, line, "There is a SpecificPurpose and it's an activityCode");
					} else if (controlList(QuestionCode.class, specificPurpose)) {
						addToLog(LogType.DEBUG, line, "There is a SpecificPurpose and it's a question");
					} else {
						addToLog(LogType.WARNING, line, "There is a SpecificPurpose but it's not an activityCode or an answer.");
					}
				}

				// ---- activityCategory ---
				//not null, always an activityCategory
				String activityCategory = data.getData(BAD_ACTIVITY_CATEGORY_KEY_COL, line);
				if (activityCategory == null || activityCategory.length() == 0) {
					addToLog(LogType.ERROR, line, "There is no activityCategory : " + data.getData(BAD_ACTIVITY_CATEGORY_KEY_COL, line) + ". The bad was not generated");
					toGenerate = false;
				}

				//save all data into one object
				if (!controlList(ActivityCategoryCode.class, activityCategory)) {
					addToLog(LogType.ERROR, line, "This activityCategory was not found : " + activityCategory + ". The bad was not generated");
					continue;
				}

				// --- activitySubCategory ---
				//not null, ActivitySubCat or answer(control list content) or more complex
				String activitySubCategory = data.getData(BAD_ACTIVITY_SUB_CATEGORY_KEY_COL, line);
				if (activitySubCategory == null || activitySubCategory.length() == 0) {
					addToLog(LogType.ERROR, line, "There is no activitySubCategory : " + data.getData(BAD_ACTIVITY_SUB_CATEGORY_KEY_COL, line) + ". The bad was not generated");
					toGenerate = false;
				}
				//test if the activitySubCategory is a code
				if (controlList(ActivitySubCategoryCode.class, activitySubCategory)) {
					addToLog(LogType.DEBUG, line, "There is a activitySubCategory and it's an ActivitySubCategoryCode");
				} else if (controlList(QuestionCode.class, activitySubCategory)) {
					addToLog(LogType.DEBUG, line, "There is a activitySubCategory and it's a question");
				} else {
					addToLog(LogType.ERROR, line, "There is a activitySubCategory but it's not an ActivitySubCategoryCode or an answer."+activitySubCategory);
				}

				// --- ActivityType ---
				//not null, ActivityType or answer(control list content) or more complex
				String activityType = data.getData(BAD_ACTIVITY_TYPE_KEY_COL, line);
				if (activityType == null || activityType.length() == 0) {
					addToLog(LogType.ERROR, line, "There is no activityType : " + data.getData(BAD_ACTIVITY_TYPE_KEY_COL, line) + ". The bad was not generated");
					toGenerate = false;
				}
				//test if the activityType is a code
				if (controlList(ActivityTypeCode.class, activityType)) {
					addToLog(LogType.DEBUG, line, "There is a activityType and it's an ActivityTypeCode");
				} else if (controlList(QuestionCode.class, activityType)) {
					addToLog(LogType.DEBUG, line, "There is a activityType and it's a question");
				} else {
					addToLog(LogType.ERROR, line, "There is a activityType but it's not an ActivityTypeCode or an answer."+activityType);
				}


				// --- ActivitySource ---
				//not null, ActivitySubCat or answer(control list content) or more complex
				String activitySource = data.getData(BAD_ACTIVITY_SOURCE_KEY_COL, line);
				if (activitySource == null || activitySource.length() == 0) {
					addToLog(LogType.ERROR, line, "There is no activitySource : " + data.getData(BAD_ACTIVITY_SOURCE_KEY_COL, line) + ". The bad was not generated");
					toGenerate = false;
				}
				//test if the activityType is a code
				if (controlList(ActivitySourceCode.class, activitySource)) {
					addToLog(LogType.DEBUG, line, "There is a activitySource and it's an ActivitySourceCode");
				} else if (controlList(QuestionCode.class, activitySource)) {
					addToLog(LogType.DEBUG, line, "There is a activitySource and it's a question");
				} else {
					addToLog(LogType.ERROR, line, "There is a activitySource but it's not an ActivitySourceCode or an answer : "+activitySource);
				}


				// --- activityOwnerShip ---
				// boolean expected. Can be null, boolean value or answer boolean type or comparaison
				String activityOwnerShip = null;//data.getData(BAD_ACTIVITY_OWNERSHIP_COL, line);
				boolean activityOwnerShipValid = false;
				if (data.getData(BAD_ACTIVITY_OWNERSHIP_COL, line) == null) {
					addToLog(LogType.WARNING, line, "ActivityOwnerShip is null");
				}
				else {

					//try to convert to boolean
					activityOwnerShip = controlBoolean(data.getData(BAD_ACTIVITY_OWNERSHIP_COL, line));

					if (activityOwnerShip == null) {
						//try to convert to BooleanQuestion
						try {
							if (controlQuestionType(data.getData(BAD_ACTIVITY_OWNERSHIP_COL, line), BooleanQuestion.class)) {
								activityOwnerShip = data.getData(BAD_ACTIVITY_OWNERSHIP_COL, line);
							}
							else{
								addToLog(LogType.ERROR, line, "ActivityOwnerShip  : this is a questionCode but this question is not BooleanQuestion");
							}
						} catch (MyrmexException e) {
							addToLog(LogType.ERROR, line, "ActivityOwnerShip is not null but it's not a boolean or a question");
						}
					}
				}



				// ---- unit ---
				// excepted unti code
				String unit = data.getData(BAD_UNIT_COL, line);
				if (data.getData(BAD_UNIT_COL, line) == null || data.getData(BAD_UNIT_COL, line).length() == 0) {
					addToLog(LogType.ERROR, line, "There is no unit : " + data.getData(BAD_UNIT_COL, line) + ". The bad was not generated");
					toGenerate = false;
				}
				//test if the activityType is a code
				if (!controlList(UnitCode.class, data.getData(BAD_UNIT_COL, line))) {
					addToLog(LogType.ERROR, line, "This is not a unit code : " + unit);
				}


				// --- value ---
				// formule => compute formule
				String value = data.getData(BAD_VALUE_COL, line);
				if (value == null || value.length() == 0) {
					addToLog(LogType.ERROR, line, "There is no value : " + data.getData(BAD_VALUE_COL, line) + ". The bad was not generated");
					toGenerate = false;
				}

				// ----- condition ---
				String condition = data.getData(BAD_CONDITION_COL, line);
				//TODO test condition


				//create BAD
				if (toGenerate) {
					BAD bad = new BAD();
					bad.setBaseActivityDataCode(badKey);
					bad.setName(name);
					bad.setRank(rank);
					bad.setSpecificPurpose(specificPurpose);
					bad.setActivityCategoryCode(activityCategory);
					bad.setActivitySubCategory(activitySubCategory);
					bad.setActivityType(activityType);
					bad.setActivitySource(activitySource);
					bad.setActivityOwnership(activityOwnerShip);
					bad.setUnit(unit);
					bad.setValue(value);
					bad.setCondition(condition);

					//print
					//Logger.info(bad.toString());

					listBAD.add(bad);
				}
			}
		}
	}

	public void addToLog(LogType logType, int line, String message) {
		switch (logType) {
			case INFO:
				play.Logger.info("Line " + line + "=>" + message);
				break;
			case ERROR:
				play.Logger.error("Line " + line + "=>" + message);
				break;
			case WARNING:
				play.Logger.warn("Line " + line + "=>" + message);
				break;
			case DEBUG:
				play.Logger.debug("Line " + line + "=>" + message);
				break;
		}
	}

	public enum LogType {
		INFO, WARNING, ERROR, DEBUG;


	}

	public boolean controlList(Class classToTest, String code) {
		for (Field field : classToTest.getDeclaredFields()) {
			if (field.getName().equalsIgnoreCase(code)) {
				return true;
			}
		}
		return false;
	}

	public <T extends Question> boolean controlQuestionType(String questionCode, Class<T> questionClass) throws MyrmexException {

		//control code
		if(!controlList(QuestionCode.class, questionCode)){
			throw new MyrmexException("This is not a questionCode");
		}

		Question question = questionService.findByCode(new QuestionCode(questionCode));

		if(questionClass.isInstance(questionCode)){
			return true;
		}
		return false;
	}



	protected static String controlBoolean(String s){
		if(s != null){
			if(s.equals("1") || s.equals("true")){
				return "true";
			}
			else if(s.equals("0") || s.equals("false")){
				return "false";
			}
		}
		return null;
	}


}
