package eu.factorx.awac.util.data.importer.badImporter;

import eu.factorx.awac.models.code.type.*;
import eu.factorx.awac.models.knowledge.Unit;
import eu.factorx.awac.models.reporting.BaseActivityData;
import eu.factorx.awac.util.data.importer.AwacDataImporter;
import eu.factorx.awac.util.data.importer.ExcelEquivalenceColumn;
import eu.factorx.awac.util.data.importer.WorkbookDataImporter;
import jxl.Sheet;
import sun.rmi.runtime.Log;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by florian on 29/08/14.
 * <p/>
 * !! the column F of the excel file is used to detect a bad : if the column is not empty, it's a BAD !!
 */
public class BADImporter extends WorkbookDataImporter {

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

	//ActivityCategoryKey
	private final static int BAD_ACTIVITY_CATEGORY_KEY_COL = ExcelEquivalenceColumn.G;

	//ActivitySubCategoryKey
	private final static int BAD_ACTIVITY_SUB_CATEGORY_KEY_COL = ExcelEquivalenceColumn.I;

	//ActivityTypeKey
	private final static int BAD_ACTIVITY_TYPE_KEY_COL = ExcelEquivalenceColumn.K;

	//ActivitySourceKey or answer
	private final static int BAD_ACTIVITY_SOURCE_KEY_COL = ExcelEquivalenceColumn.M;

	//UnitCode
	private final static int BAD_UNIT_COL = ExcelEquivalenceColumn.P;

	//activityOwnerShip : boolean
	private final static int BAD_ACTIVITY_OWNERSHIP_COL = ExcelEquivalenceColumn.O;

	//value
	private final static int BAD_VALUE_COL = ExcelEquivalenceColumn.Q;


	private List<BAD> listBAD = new ArrayList<>();

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

		//1. load file
		Map<String, Sheet> awacDataWbSheets = getWorkbookSheets("data_importer_resources/awac_data_09-08-2014/AWAC-tous-calcul_FE_COPY.xls");//AwacDataImporter.AWAC_DATA_WORKBOOK_PATH);


		Sheet sheet = awacDataWbSheets.get(ENTERPRISE_METHOD);

		//2. read
		reader(sheet);

		play.Logger.info("run badimporter end !");

	}

	public void reader(Sheet sheet) {


		for (int line = 1; line < sheet.getRows(); line++) {

			//escape the first line : presentation
			if (line == 1) {
				continue;
			}

			//F is the reference column => not empty = it's a BAD !
			if (getCellStringContent(sheet, ExcelEquivalenceColumn.B, line) != null &&
					!getCellStringContent(sheet, ExcelEquivalenceColumn.B, line).equals("BAD-KEY") &&
					getCellStringContent(sheet, ExcelEquivalenceColumn.B, line).contains("BAD")) {
				// activity data founded

				//print the line in DEBUG
				addToLog(LogType.INFO, line, "This is a BAD " + getCellStringContent(sheet, ExcelEquivalenceColumn.B, line));


				//create and write new BAD
				boolean toGenerate = true;

				// --- test badKey ---
				String badKey = normalize(getCellStringContent(sheet, BAD_KEY_COL, line));
				if (badKey == null || badKey.length() == 0) {
					addToLog(LogType.ERROR, line, "There is no badKey : " + getCellStringContent(sheet, BAD_KEY_COL, line) + ". The bad was not generated");
					toGenerate = false;
				}

				if (!controlList(BaseActivityDataCode.class, badKey)) {
					addToLog(LogType.ERROR, line, "This is not a badKey : " + getCellStringContent(sheet, BAD_KEY_COL, line) + ". The bad was not generated");
					toGenerate = false;
				}

				// ---- name ----
				String name = getCellStringContent(sheet, BAD_NAME_COL, line);
				if (name == null || name.length() == 0) {
					addToLog(LogType.ERROR, line, "There is no name: " + getCellStringContent(sheet, BAD_NAME_COL, line) + ". The bad was not generated");
					toGenerate = false;
				}

				// ---- control rank ---
				// TODO can be null
				Integer rank = null;
				if (getCellStringContent(sheet, BAD_RANK_COL, line) == null || getCellStringContent(sheet, BAD_RANK_COL, line).length() == 0) {
					addToLog(LogType.ERROR, line, "There is no rank : " + getCellStringContent(sheet, BAD_RANK_COL, line) + ". The bad was not generated");
					toGenerate = false;
				}
				try {
					rank = Integer.parseInt(getCellStringContent(sheet, BAD_RANK_COL, line));
				} catch (NumberFormatException e) {
					addToLog(LogType.ERROR, line, "The rank is not a valid number  : " + getCellStringContent(sheet, BAD_RANK_COL, line) + ". The bad was not generated");
				}

				// ---- specific purpose ----
				String specificPurpose = null;
				//!! can be an activityCode or an answer or can be null
				//excepted in fine => String
				if (getCellStringContent(sheet, BAD_SPECIFIC_PURPOSE_COL, line) != null && getCellStringContent(sheet, BAD_SPECIFIC_PURPOSE_COL, line).length() > 0) {


					specificPurpose = getCellStringContent(sheet, BAD_SPECIFIC_PURPOSE_COL, line);

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
				String activityCategory = getCellStringContent(sheet, BAD_ACTIVITY_CATEGORY_KEY_COL, line);
				if (activityCategory == null || activityCategory.length() == 0) {
					addToLog(LogType.ERROR, line, "There is no activityCategory : " + getCellStringContent(sheet, BAD_ACTIVITY_CATEGORY_KEY_COL, line) + ". The bad was not generated");
					toGenerate = false;
				}

				//save all data into one object
				if (!controlList(ActivityCategoryCode.class, activityCategory)) {
					addToLog(LogType.ERROR, line, "This activityCategory was not found : " + activityCategory + ". The bad was not generated");
					continue;
				}

				// --- activitySubCategory ---
				//not null, ActivitySubCat or answer(control list content) or more complex
				String activitySubCategory = getCellStringContent(sheet, BAD_ACTIVITY_SUB_CATEGORY_KEY_COL, line);
				if (activitySubCategory == null || activitySubCategory.length() == 0) {
					addToLog(LogType.ERROR, line, "There is no activitySubCategory : " + getCellStringContent(sheet, BAD_ACTIVITY_SUB_CATEGORY_KEY_COL, line) + ". The bad was not generated");
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
				String activityType = getCellStringContent(sheet, BAD_ACTIVITY_TYPE_KEY_COL, line);
				if (activityType == null || activityType.length() == 0) {
					addToLog(LogType.ERROR, line, "There is no activityType : " + getCellStringContent(sheet, BAD_ACTIVITY_TYPE_KEY_COL, line) + ". The bad was not generated");
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
				String activitySource = getCellStringContent(sheet, BAD_ACTIVITY_SOURCE_KEY_COL, line);
				if (activitySource == null || activitySource.length() == 0) {
					addToLog(LogType.ERROR, line, "There is no activitySource : " + getCellStringContent(sheet, BAD_ACTIVITY_SOURCE_KEY_COL, line) + ". The bad was not generated");
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
				Boolean activityOwnerShip = null;//getCellStringContent(sheet, BAD_ACTIVITY_OWNERSHIP_COL, line);
				if (getCellStringContent(sheet, BAD_ACTIVITY_OWNERSHIP_COL, line) == null || getCellStringContent(sheet, BAD_ACTIVITY_OWNERSHIP_COL, line).length() == 0) {
					addToLog(LogType.ERROR, line, "There is no activityOwnerShip : " + getCellStringContent(sheet, BAD_ACTIVITY_OWNERSHIP_COL, line) + ". The bad was not generated");
					toGenerate = false;
				}
				try {
					activityOwnerShip = Boolean.parseBoolean(getCellStringContent(sheet, BAD_ACTIVITY_OWNERSHIP_COL, line));
				} catch (NumberFormatException e) {
					addToLog(LogType.ERROR, line, "The activityOwnerShip is not a valid boolean  : " + getCellStringContent(sheet, BAD_ACTIVITY_OWNERSHIP_COL, line) + ". The bad was not generated");
				}

				// ---- unit ---
				// excepted unti code
				String unit = getCellStringContent(sheet, BAD_UNIT_COL, line);
				if (getCellStringContent(sheet, BAD_UNIT_COL, line) == null || getCellStringContent(sheet, BAD_UNIT_COL, line).length() == 0) {
					addToLog(LogType.ERROR, line, "There is no unit : " + getCellStringContent(sheet, BAD_UNIT_COL, line) + ". The bad was not generated");
					toGenerate = false;
				}
				//test if the activityType is a code
				if (!controlList(UnitCode.class, getCellStringContent(sheet, BAD_UNIT_COL, line))) {
					addToLog(LogType.ERROR, line, "This is not a unit code : " + unit);
				}


				// --- value ---
				// formule => compute formule
				String value = getCellStringContent(sheet, BAD_VALUE_COL, line);
				if (value == null || value.length() == 0) {
					addToLog(LogType.ERROR, line, "There is no value : " + getCellStringContent(sheet, BAD_VALUE_COL, line) + ". The bad was not generated");
					toGenerate = false;
				}


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
			if (field.getName().equals(code)) {
				return true;
			}
		}
		return false;
	}


}
