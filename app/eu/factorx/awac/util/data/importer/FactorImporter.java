package eu.factorx.awac.util.data.importer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import play.Logger;
import play.db.jpa.JPA;
import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.code.label.CodeLabel;
import eu.factorx.awac.models.code.type.ActivitySourceCode;
import eu.factorx.awac.models.code.type.ActivityTypeCode;
import eu.factorx.awac.models.code.type.IndicatorCategoryCode;
import eu.factorx.awac.models.knowledge.Factor;
import eu.factorx.awac.models.knowledge.FactorValue;
import eu.factorx.awac.models.knowledge.Unit;
import eu.factorx.awac.service.FactorService;

@Component
public class FactorImporter extends WorkbookDataImporter {

	public static final String AWAC_DATA_WORKBOOK_PATH = "data_importer_resources/awac_data_09-08-2014/AWAC-tous-calcul_FE.xls";

	/**
	 * <pre>
	 * Columns:
	 * 0: KEY                   (a {@link String})
	 * 1: IndicatorCategory_KEY (an {@link IndicatorCategoryCode})
	 * 3: ActivityType_KEY      (an {@link ActivityTypeCode})
	 * 5: ActivitySource_KEK    (an {@link ActivitySourceCode})
	 * 7: Unit IN               (a {@link Unit})
	 * 9: Institution           (a {@link String})
	 * 10: Value                 (a {@link Double})
	 * 11: Unit out             (a {@link Unit})
	 * </pre>
	 */
	public static final String FACTORS_SHEET_NAME = "EFDB_V6";

	@Autowired
	private FactorService factorService;

	private Map<String, Unit> allUnitKeys = null;

	private List<String> allIndicatorCategoryKeys = null;
	private List<String> allActivityTypeKeys = null;
	private List<String> allActivitySourceKeys = null;

	public FactorImporter() {
		super();
	}

	protected void importData() throws Exception {
		factorService.removeAll();

		allUnitKeys = findAllUnits();

		allIndicatorCategoryKeys = findAllCodeKeys(CodeList.IndicatorCategory);
		allActivityTypeKeys = findAllCodeKeys(CodeList.ActivityType);
		allActivitySourceKeys = findAllCodeKeys(CodeList.ActivitySource);

		Map<String, Sheet> awacDataWbSheets = getWorkbookSheets(AWAC_DATA_WORKBOOK_PATH);

		Logger.info("== Importing Factors");
		saveFactors(awacDataWbSheets.get(FACTORS_SHEET_NAME));
	}

	private void saveFactors(Sheet factorsSheet) {
		List<Factor> factors = new ArrayList<>();
		Integer noValueFactors = 0;

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

			String unitInKey = getCellContent(factorsSheet, 7, i);
			if (!allUnitKeys.containsKey(unitInKey)) {
				Logger.error("Cannot import factor '{}': Invalid UnitIN symbol '{}'!", key, unitInKey);
				continue;
			}

			String institution = getCellContent(factorsSheet, 9, i);

			Double value = getNumericCellContent(factorsSheet, 10, i);
			if (value == null) {
				Logger.warn("Value of factor '{}' is null!", key);
			}

			String unitOutKey = getCellContent(factorsSheet, 11, i);
			if (!allUnitKeys.containsKey(unitOutKey)) {
				Logger.error("Cannot import factor '{}': Invalid UnitOUT symbol '{}'!", key, unitOutKey);
				continue;
			}

			IndicatorCategoryCode indicatorCategory = new IndicatorCategoryCode(indicatorCategoryKey);
			ActivityTypeCode activityType = new ActivityTypeCode(activityTypeKey);
			ActivitySourceCode activitySource = new ActivitySourceCode(activitySourceKey);
			Unit unitIn = allUnitKeys.get(unitInKey);
			Unit unitOut = allUnitKeys.get(unitOutKey);

			Factor factor = factorService.saveOrUpdate(new Factor(key, indicatorCategory, activityType, activitySource, unitIn, unitOut, institution));

			if (value == null) {
				noValueFactors++;
			} else {
				FactorValue factorvalue = new FactorValue(value, 2000, null, factor);
				factor.getValues().add(factorvalue);
				factorService.saveOrUpdate(factor);
			}

			factors.add(factor);
		}

		if (noValueFactors == 0) {
			Logger.info("==== Imported {} factors");
		} else {
			Logger.info("==== Imported {} factors (including {} factors without value(s), and therefore unusable)", factors.size(), noValueFactors);
		}
	}

	private Map<String, Unit> findAllUnits() {
		List<Unit> units = JPA.em().createNamedQuery(Unit.FIND_ALL, Unit.class).getResultList();
		Map<String, Unit> unitsBySymbol = new HashMap<>();
		for (Unit unit : units) {
			unitsBySymbol.put(unit.getUnitCode().getKey(), unit);
		}
		return unitsBySymbol;
	}

	private List<String> findAllCodeKeys(CodeList codeList) {
		return JPA.em().createNamedQuery(CodeLabel.FIND_KEYS_BY_LIST, String.class).setParameter("codeList", codeList).getResultList();
	}

}
