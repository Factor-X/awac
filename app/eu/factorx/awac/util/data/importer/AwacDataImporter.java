package eu.factorx.awac.util.data.importer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Sheet;
import jxl.Workbook;

import org.hibernate.Session;
import org.springframework.stereotype.Component;

import play.Logger;
import play.db.jpa.JPA;
import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.code.label.CodeLabel;
import eu.factorx.awac.models.code.type.ActivityCategoryCode;
import eu.factorx.awac.models.code.type.ActivitySourceCode;
import eu.factorx.awac.models.code.type.ActivitySubCategoryCode;
import eu.factorx.awac.models.code.type.ActivityTypeCode;
import eu.factorx.awac.models.code.type.IndicatorCategoryCode;
import eu.factorx.awac.models.code.type.IndicatorIsoScopeCode;
import eu.factorx.awac.models.code.type.IndicatorTypeCode;
import eu.factorx.awac.models.code.type.ScopeTypeCode;
import eu.factorx.awac.models.knowledge.Factor;
import eu.factorx.awac.models.knowledge.FactorValue;
import eu.factorx.awac.models.knowledge.Indicator;
import eu.factorx.awac.models.knowledge.Unit;

@Component
public class AwacDataImporter extends WorkbookDataImporter {

	private static final String AWAC_DATA_WORKBOOK__PATH = "data_importer_resources/awac_data_25-07-2014/AWAC-entreprise-calcul_FE.xls";

	/**
	 * <pre>
	 * Columns:
	 * 0: IndicatorType            (= {@link IndicatorTypeCode#CARBON})
	 * 1: KEY                      (a {@link String})
	 * 2: Name                     (a {@link String})
	 * 3: OrgScope                 (= {@link ScopeTypeCode#SITE})
	 * 4: IndicatorScope           (an {@link IndicatorIsoScopeCode})
	 * 5: IndicatorCategory_KEY    (an {@link IndicatorCategoryCode})
	 * 7: unit                     (= {@link Unit} with symbol 'tCO2e')
	 * 8: ActivityCategory_KEY     (an {@link ActivityCategoryCode})
	 * 10: ActivitySubCategory_KEY (an {@link ActivitySubCategoryCode})
	 * 14: ActivityOwnership       (a {@link Boolean})
	 * </pre>
	 */
	private static final String AWAC_DATA_WORKBOOK__INDICATORS_SHEET__NAME = "Indicators";

	/**
	 * <pre>
	 * Columns:
	 * 0: KEY                   (a {@link String})
	 * 1: IndicatorCategory_KEY (an {@link IndicatorCategoryCode})
	 * 3: ActivityType_KEY      (an {@link ActivityTypeCode})
	 * 5: ActivitySource_KEK    (an {@link ActivitySourceCode})
	 * 7: Unit IN               (a {@link Unit})
	 * 8: Institution           (a {@link String})
	 * 9: Value                 (a {@link Double})
	 * 10: Unit out             (a {@link Unit})
	 * </pre>
	 */
	private static final String AWAC_DATA_WORKBOOK__FACTORS_SHEET__NAME = "EFDB_V6";

	private Map<String, Unit> allUnitSymbols = null;

	private List<String> allIndicatorCategoryKeys = null;
	private List<String> allActivityTypeKeys = null;
	private List<String> allActivitySourceKeys = null;
	private List<String> allActivityCategoryKeys = null;
	private List<String> allActivitySubCategoryKeys = null;

	public AwacDataImporter() {
		super();
	}

	public AwacDataImporter(Session session) {
		super();
		this.session = session;
	}

	protected void importData() throws Exception {
		Logger.info("== Importing Awac Indicators and Factors (from {})", AWAC_DATA_WORKBOOK__PATH);

		Workbook awacDataWorkbook = getWorkbook(AWAC_DATA_WORKBOOK__PATH);
		Sheet indicatorsSheet = awacDataWorkbook.getSheet(AWAC_DATA_WORKBOOK__INDICATORS_SHEET__NAME);
		Sheet factorsSheet = awacDataWorkbook.getSheet(AWAC_DATA_WORKBOOK__FACTORS_SHEET__NAME);

		allUnitSymbols = findAllUnits();

		allIndicatorCategoryKeys = findAllCodeKeys(CodeList.IndicatorCategory);
		allActivityTypeKeys = findAllCodeKeys(CodeList.ActivityType);
		allActivitySourceKeys = findAllCodeKeys(CodeList.ActivitySource);
		allActivityCategoryKeys = findAllCodeKeys(CodeList.ActivityCategory);
		allActivitySubCategoryKeys = findAllCodeKeys(CodeList.ActivitySubCategory);

		saveIndicators(indicatorsSheet);
		saveFactors(factorsSheet);

	}

	private void saveIndicators(Sheet indicatorsSheet) {
		Logger.info("==== Importing indicators");
		List<Indicator> indicators = new ArrayList<>();
		Unit unit = allUnitSymbols.get("tCO2e");
		for (int i = 1; i < indicatorsSheet.getRows(); i++) {
			String key = getCellContent(indicatorsSheet, 1, i);
			String name = getCellContent(indicatorsSheet, 2, i);
			String indicatorIsoScopeKey = getCellContent(indicatorsSheet, 4, i);

			String indicatorCategoryKey = getCellContent(indicatorsSheet, 5, i);
			if (!allIndicatorCategoryKeys.contains(indicatorCategoryKey)) {
				Logger.error("Cannot import indicator '{}': Unknown IndicatorCategory key '{}'!", key, indicatorCategoryKey);
				continue;
			}

			String activityCategoryKey = getCellContent(indicatorsSheet, 8, i);
			if (!allActivityCategoryKeys.contains(activityCategoryKey)) {
				Logger.error("Cannot import indicator '{}': Unknown ActivityCategory key '{}'!", key, activityCategoryKey);
				continue;
			}

			String activitySubCategoryKey = getCellContent(indicatorsSheet, 10, i);
			if (!allActivitySubCategoryKeys.contains(activitySubCategoryKey)) {
				Logger.error("Cannot import indicator '{}': Unknown ActivitySubCategory key '{}'!", key, activitySubCategoryKey);
				continue;
			}

			String strActivityOwnership = getCellContent(indicatorsSheet, 14, i);
			Boolean activityOwnership = null;
			if ("1".equals(strActivityOwnership)) {
				activityOwnership = Boolean.TRUE;
			} else if ("0".equals(strActivityOwnership)) {
				activityOwnership = Boolean.FALSE;
			}

			Boolean deleted = indicatorsSheet.getCell(1, i).getCellFormat().getFont().isStruckout();

			IndicatorIsoScopeCode isoScope = new IndicatorIsoScopeCode(indicatorIsoScopeKey);
			IndicatorCategoryCode indicatorCategory = new IndicatorCategoryCode(indicatorCategoryKey);
			ActivityCategoryCode activityCategory = new ActivityCategoryCode(activityCategoryKey);
			ActivitySubCategoryCode activitySubCategory = new ActivitySubCategoryCode(activitySubCategoryKey);

			indicators.add(new Indicator(key, name, IndicatorTypeCode.CARBON, ScopeTypeCode.SITE, isoScope, indicatorCategory, activityCategory,
					activitySubCategory, activityOwnership, unit, deleted));
		}
		persistEntities(indicators);
		Logger.info("====== Imported {} indicators", indicators.size());
	}

	private void saveFactors(Sheet factorsSheet) {
		Logger.info("==== Importing factors");
		List<Factor> factors = new ArrayList<>();
		List<FactorValue> factorValues = new ArrayList<>();
		for (int i = 1; i < factorsSheet.getRows(); i++) {
			String key = getCellContent(factorsSheet, 0, i);

			String indicatorCategoryKey = getCellContent(factorsSheet, 1, i);
			if (!allIndicatorCategoryKeys.contains(indicatorCategoryKey)) {
				Logger.error("Cannot import factor '{}': Unknown IndicatorCategory key '{}'!", key, indicatorCategoryKey);
				continue;
			}

			String activityTypeKey = getCellContent(factorsSheet, 3, i);
			if (!allActivityTypeKeys.contains(activityTypeKey)) {
				Logger.error("Cannot import factor '{}': Unknown ActivityType key '{}'!", key, activityTypeKey);
				continue;
			}

			String activitySourceKey = getCellContent(factorsSheet, 5, i);
			if (!allActivitySourceKeys.contains(activitySourceKey)) {
				Logger.error("Cannot import factor '{}': Unknown ActivitySource key '{}'!", key, activitySourceKey);
				continue;
			}

			String unitInSymbol = getCellContent(factorsSheet, 7, i);
			if (!allUnitSymbols.containsKey(unitInSymbol)) {
				Logger.error("Cannot import factor '{}': Invalid UnitIN symbol '{}'!", key, unitInSymbol);
				continue;
			}

			String institution = getCellContent(factorsSheet, 8, i);

			Double value = getNumericCellContent(factorsSheet, 9, i);
			if (value == null) {
				Logger.error("Cannot import factor '{}': Value is null!", key);
				continue;
			}

			String unitOutSymbol = getCellContent(factorsSheet, 10, i);
			if (!allUnitSymbols.containsKey(unitInSymbol)) {
				Logger.error("Cannot import factor '{}': Invalid UnitIN symbol '{}'!", key, unitInSymbol);
				continue;
			}

			IndicatorCategoryCode indicatorCategory = new IndicatorCategoryCode(indicatorCategoryKey);
			ActivityTypeCode activityType = new ActivityTypeCode(activityTypeKey);
			ActivitySourceCode activitySource = new ActivitySourceCode(activitySourceKey);
			Unit unitIn = allUnitSymbols.get(unitInSymbol);
			Unit unitOut = allUnitSymbols.get(unitOutSymbol);

			Factor factor = new Factor(key, indicatorCategory, activityType, activitySource, unitIn, unitOut, institution);
			factors.add(factor);
			factorValues.add(new FactorValue(value, null, null, factor));
		}
		persistEntities(factors);
		persistEntities(factorValues);
		Logger.info("====== Imported {} factors", factors.size());
	}

	private Map<String, Unit> findAllUnits() {
		List<Unit> units = JPA.em().createNamedQuery(Unit.FIND_ALL, Unit.class).getResultList();
		Map<String, Unit> unitsBySymbol = new HashMap<>();
		for (Unit unit : units) {
			unitsBySymbol.put(unit.getSymbol(), unit);
		}
		return unitsBySymbol;
	}

	private List<String> findAllCodeKeys(CodeList codeList) {
		return JPA.em().createNamedQuery(CodeLabel.FIND_KEYS_BY_LIST, String.class).setParameter("codeList", codeList).getResultList();
	}

}
