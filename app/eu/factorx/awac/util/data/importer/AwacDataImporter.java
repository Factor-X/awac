package eu.factorx.awac.util.data.importer;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.code.label.CodeLabel;
import eu.factorx.awac.models.code.type.*;
import eu.factorx.awac.models.knowledge.Factor;
import eu.factorx.awac.models.knowledge.FactorValue;
import eu.factorx.awac.models.knowledge.Indicator;
import eu.factorx.awac.models.knowledge.Unit;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;
import org.springframework.stereotype.Component;
import play.db.jpa.JPA;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.*;

@Component
public class AwacDataImporter extends WorkbookDataImporter {

	private static final String CODE_CONSTANTS_FILE_PATH = "code_constants.txt";
	/**
	 * All sheets have the same columns:<br>
	 * 0: NUM<br>
	 * 1: IDENTIFIER (for matching identifiers used in Awac data wkb)<br>
	 * 2: CODE (which will be used in Awac application)<br>
	 * 3: LABEL (which will be used in Awac application for i18n; ~ like IDENTIFIER, but with some fixes)<br>
	 */
	private static final String AWAC_CODES_EXTRACT_WORKBOOK__PATH = "data_importer_resources/awac_data_10-07-2014/codes_extract.xls";

	private static final String AWAC_DATA_WORKBOOK__PATH = "data_importer_resources/awac_data_10-07-2014/AWAC-entreprise-calcul_FE.xls";

	/**
	 * Columns:<br>
	 * 0: INDICATOR CATEGORY<br>
	 * 1: ACTIVITY TYPE<br>
	 * 2: ACTIVITY SOURCE<br>
	 * 3: UNIT IN<br>
	 * 4: INSTITUTION<br>
	 * 5: VALUE<br>
	 * 6: UNIT OUT
	 */
	private static final String AWAC_DATA_WORKBOOK__FACTORS_SHEET__NAME = "EFDB";

	/**
	 * Columns:<br>
	 * 0: INDICATOR TYPE (= {@link IndicatorTypeCode#CARBON})<br>
	 * 1: NAME<br>
	 * 2: ORG SCOPE (= {@link ScopeTypeCode#ORG})<br>
	 * 3: INDICATOR SCOPE<br>
	 * 4: INDICATOR CATEGORY<br>
	 * 5: UNIT (= 'tCO2e')<br>
	 * 6: ACTIVITY CATEGORY<br>
	 * 7: ACTIVITY SUBCATEGORY<br>
	 * 10: ACTIVITY OWNERSHIP
	 */
	private static final String AWAC_DATA_WORKBOOK__INDICATORS_SHEET__NAME = "Indicators";

	// codes extracts by identifier
	private static Map<String, CodeExtract<IndicatorCategoryCode>> indicatorCategories = null;
	private static Map<String, CodeExtract<ActivityTypeCode>> activityTypes = null;
	private static Map<String, CodeExtract<ActivitySourceCode>> activitySources = null;
	private static Map<String, CodeExtract<ActivityCategoryCode>> activityCategories = null;
	private static Map<String, CodeExtract<ActivitySubCategoryCode>> activitySubCategories = null;

	// unit by symbol map
	private static Map<String, Unit> knownUnits = null;

	private static void extractCodes() throws Exception {
		WorkbookSettings ws = new WorkbookSettings();
		ws.setEncoding(CP1252_ENCODING);

		Workbook awacCodesWorkbook = Workbook.getWorkbook(new File(AWAC_CODES_EXTRACT_WORKBOOK__PATH), ws);
		Sheet indicatorcategoriesSheet = awacCodesWorkbook.getSheet("indicatorcategories");
		Sheet activitytypesSheet = awacCodesWorkbook.getSheet("activitytypes");
		Sheet activitysourcesSheet = awacCodesWorkbook.getSheet("activitysources");
		Sheet activitycategoriesSheet = awacCodesWorkbook.getSheet("activitycategories");
		Sheet activitysubcategoriesSheet = awacCodesWorkbook.getSheet("activitysubcategories");

		indicatorCategories = toCodeExtracts(indicatorcategoriesSheet, IndicatorCategoryCode.class,
				CodeList.INDICATOR_CATEGORY);
		activityTypes = toCodeExtracts(activitytypesSheet, ActivityTypeCode.class, CodeList.ACTIVITY_TYPE);
		activitySources = toCodeExtracts(activitysourcesSheet, ActivitySourceCode.class, CodeList.ACTIVITY_SOURCE);
		activityCategories = toCodeExtracts(activitycategoriesSheet, ActivityCategoryCode.class,
				CodeList.ACTIVITY_CATEGORY);
		activitySubCategories = toCodeExtracts(activitysubcategoriesSheet, ActivitySubCategoryCode.class,
				CodeList.ACTIVITY_SUB_CATEGORY);
	}

	private static <T extends Code> Map<String, CodeExtract<T>> toCodeExtracts(Sheet codesSheet, Class<T> codeClass,
	                                                                           CodeList codeList) throws Exception {
		Map<String, CodeExtract<T>> res = new HashMap<>();
		Constructor<T> constructor = codeClass.getConstructor(String.class);
		for (int i = 1; i < codesSheet.getRows(); i++) {
			String key = getCellContent(codesSheet, 0, i);
			String identifier = getCellContent(codesSheet, 1, i);
			String name = getCellContent(codesSheet, 2, i);
			String label = getCellContent(codesSheet, 3, i);
			T code = constructor.newInstance(key);
			CodeLabel codeLabel = new CodeLabel(code, label, label, label);
			res.put(identifier, new CodeExtract<T>(name, code, codeLabel));
		}
		return res;
	}

	private static void verifyAwacData(Sheet factorsSheet, Sheet indicatorsSheet) throws BiffException, IOException {
		Set<String> indicatorCategoriesIdentifiers = getColumnContent(factorsSheet, 0);
		Set<String> activityTypesIdentifiers = getColumnContent(factorsSheet, 1);
		Set<String> activitySourcesIdentifiers = getColumnContent(factorsSheet, 2);
		Set<String> unitsSymbols = getColumnContent(factorsSheet, 3);
		getColumnNumericContent(factorsSheet, 5);
		unitsSymbols.addAll(getColumnContent(factorsSheet, 6));

		indicatorCategoriesIdentifiers.addAll(getColumnContent(indicatorsSheet, 4));
		Set<String> activityCategoriesIdentifiers = getColumnContent(indicatorsSheet, 6);
		Set<String> activitySubCategoriesIdentifiers = getColumnContent(indicatorsSheet, 7);

		// verify identifiers and unit symbols
		verifyCodeExist(indicatorCategoriesIdentifiers, indicatorCategories);
		verifyCodeExist(activityTypesIdentifiers, activityTypes);
		verifyCodeExist(activitySourcesIdentifiers, activitySources);
		verifyCodeExist(activityCategoriesIdentifiers, activityCategories);
		verifyCodeExist(activitySubCategoriesIdentifiers, activitySubCategories);
		verifyUnitExist(unitsSymbols);
	}

	private static <T extends Code> void verifyCodeExist(Set<String> identifiers,
	                                                     Map<String, CodeExtract<T>> codeByIdentifierMap) {
		for (String identifier : identifiers) {
			if (!codeByIdentifierMap.containsKey(identifier)) {
				throw new RuntimeException("This identifier cannot be associated to any code: '" + identifier + "'");
			}
		}
	}

	private static void verifyUnitExist(Set<String> unitSymbols) {
		Set<String> unknownUnitSymbols = new HashSet<>();
		for (String unitSymbol : unitSymbols) {
			if (!knownUnits.containsKey(unitSymbol)) {
				unknownUnitSymbols.add(unitSymbol);
			}
		}
		if (!unknownUnitSymbols.isEmpty()) {
			System.out.println("These unit symbols cannot be associated to any known units: '" + unknownUnitSymbols
					+ "'");
		}
	}

	private static void outputCodeConstants() throws IOException {
		FileWriter out = new FileWriter(CODE_CONSTANTS_FILE_PATH, true);
		BufferedWriter writer = new BufferedWriter(out);

		writeCodeConstants(IndicatorCategoryCode.class, new TreeSet<>(indicatorCategories.values()), writer);
		writeCodeConstants(ActivityTypeCode.class, new TreeSet<>(activityTypes.values()), writer);
		writeCodeConstants(ActivitySourceCode.class, new TreeSet<>(activitySources.values()), writer);
		writeCodeConstants(ActivityCategoryCode.class, new TreeSet<>(activityCategories.values()), writer);
		writeCodeConstants(ActivitySubCategoryCode.class, new TreeSet<>(activitySubCategories.values()), writer);

		writer.flush();
		writer.close();
	}

	private static void saveCodeLabels() {
		saveCodeLabels(indicatorCategories);
		saveCodeLabels(activityTypes);
		saveCodeLabels(activitySources);
		saveCodeLabels(activityCategories);
		saveCodeLabels(activitySubCategories);
	}

	private static <T extends Code> void saveCodeLabels(Map<String, CodeExtract<T>> codeExtracts) {
		List<CodeLabel> codeLabels = new ArrayList<>();
		for (CodeExtract<T> codeExtract : new TreeSet<>(codeExtracts.values())) {
			codeLabels.add(codeExtract.getCodeLabel());
		}
		persistEntities(codeLabels);
	}

	private static void saveFactors(Sheet factorsSheet) {
		List<Factor> factors = new ArrayList<>();
		List<FactorValue> factorValues = new ArrayList<>();
		for (int i = 1; i < factorsSheet.getRows(); i++) {
			IndicatorCategoryCode indicatorCategory = indicatorCategories.get(getCellContent(factorsSheet, 0, i))
					.getCode();
			ActivityTypeCode activityType = activityTypes.get(getCellContent(factorsSheet, 1, i)).getCode();
			ActivitySourceCode activitySource = activitySources.get(getCellContent(factorsSheet, 2, i)).getCode();
			Unit unitIn = knownUnits.get(getCellContent(factorsSheet, 3, i));
			String institution = getCellContent(factorsSheet, 4, i);
			Double value = getNumericCellContent(factorsSheet, 5, i);
			Unit unitOut = knownUnits.get(getCellContent(factorsSheet, 6, i));

			Factor factor = new Factor(indicatorCategory, activityType, activitySource, unitIn, unitOut, institution);
			factors.add(factor);
			if (value != null) {
				factorValues.add(new FactorValue(value, new Date(), null, factor));
			}
		}
		persistEntities(factors);
		persistEntities(factorValues);
	}

	private static void saveIndicators(Sheet indicatorsSheet) {
		List<Indicator> indicators = new ArrayList<>();
		Unit unit = knownUnits.get("tCO2e");
		for (int i = 1; i < indicatorsSheet.getRows(); i++) {
			String name = getCellContent(indicatorsSheet, 1, i);
			IndicatorIsoScopeCode isoScope = new IndicatorIsoScopeCode(getCellContent(indicatorsSheet, 3, i));
			IndicatorCategoryCode indicatorCategory = indicatorCategories.get(getCellContent(indicatorsSheet, 4, i))
					.getCode();
			ActivityCategoryCode activityCategory = activityCategories.get(getCellContent(indicatorsSheet, 6, i))
					.getCode();
			ActivitySubCategoryCode activitySubCategory = activitySubCategories.get(
					getCellContent(indicatorsSheet, 7, i)).getCode();
			String strActivityOwnership = getCellContent(indicatorsSheet, 10, i);
			Boolean activityOwnership = null;
			if ("1".equals(strActivityOwnership)) {
				activityOwnership = Boolean.TRUE;
			} else if ("0".equals(strActivityOwnership)) {
				activityOwnership = Boolean.FALSE;
			}
			Boolean deleted = indicatorsSheet.getCell(1, i).getCellFormat().getFont().isStruckout();

			indicators.add(new Indicator(name, IndicatorTypeCode.CARBON, ScopeTypeCode.SITE, isoScope,
					indicatorCategory, activityCategory, activitySubCategory, activityOwnership, unit, deleted));
		}
		persistEntities(indicators);
	}

	private static List<Unit> findAllUnits() {
		return JPA.em().createNamedQuery(Unit.FIND_ALL, Unit.class).getResultList();
	}

	protected void importData() throws Exception {

		knownUnits = new HashMap<>();
		for (Unit unit : findAllUnits()) {
			knownUnits.put(unit.getSymbol(), unit);
		}

		WorkbookSettings ws = new WorkbookSettings();
		ws.setEncoding(CP1252_ENCODING);

		Workbook awacDataWorkbook = Workbook.getWorkbook(new File(AWAC_DATA_WORKBOOK__PATH), ws);
		Sheet factorsSheet = awacDataWorkbook.getSheet(AWAC_DATA_WORKBOOK__FACTORS_SHEET__NAME);
		Sheet indicatorsSheet = awacDataWorkbook.getSheet(AWAC_DATA_WORKBOOK__INDICATORS_SHEET__NAME);

		extractCodes();
		verifyAwacData(factorsSheet, indicatorsSheet);
		outputCodeConstants();

		saveCodeLabels();
		saveFactors(factorsSheet);
		saveIndicators(indicatorsSheet);
	}

}
