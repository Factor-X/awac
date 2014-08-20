package eu.factorx.awac.util.data.importer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jxl.Sheet;
import jxl.read.biff.BiffException;

import org.hibernate.Session;
import org.springframework.stereotype.Component;

import play.Logger;
import eu.factorx.awac.models.code.type.UnitCategoryCode;
import eu.factorx.awac.models.code.type.UnitCode;
import eu.factorx.awac.models.knowledge.Unit;
import eu.factorx.awac.models.knowledge.UnitCategory;
import eu.factorx.awac.models.knowledge.UnitConversionFormula;

@Component
public class MyrmexUnitsImporter extends WorkbookDataImporter {

	private static final String MYRMEX_UNITS_WORKBOOK_PATH = "data_importer_resources/myrmex_data_10-07-2014/myrmex_unit.xls";

	/**
	 * Columns:<br>
	 * 0: REF<br>
	 * 1: NAME<br>
	 * 2: MAIN_UNIT_REF
	 */
	private static final String UNIT_CATEGORIES_SHEET_NAME = "categories";

	/**
	 * Columns:<br>
	 * 0: REF<br>
	 * 1: NAME<br>
	 * 2: SYMBOL<br>
	 * 3: CATEGORY_REF
	 */
	private static final String UNITS_SHEET_NAME = "units";

	/**
	 * Columns:<br>
	 * 0: UNIT_REF<br>
	 * 1: UNIT_TO_REFERENCE<br>
	 * 2: REFERENCE_TO_UNIT<br>
	 * 3: YEAR
	 */
	private static final String UNIT_CONVERSIONS_SHEET_NAME = "conversions";

	private Map<String, UnitCategory> unitCategoriesByRef = new LinkedHashMap<>();
	private Map<String, Unit> unitsByRef = new LinkedHashMap<String, Unit>();

	public MyrmexUnitsImporter() {
		super();
	}

	public MyrmexUnitsImporter(Session session) {
		super();
		this.session = session;
	}

	protected void importData() throws BiffException, IOException {

		Map<String, Sheet> myrmexUnitsWbSheets = getWorkbookSheets(MYRMEX_UNITS_WORKBOOK_PATH);

		Sheet unitCategoriesSheet = myrmexUnitsWbSheets.get(UNIT_CATEGORIES_SHEET_NAME);
		Sheet unitsSheet = myrmexUnitsWbSheets.get(UNITS_SHEET_NAME);
		Sheet unitConversionsSheet = myrmexUnitsWbSheets.get(UNIT_CONVERSIONS_SHEET_NAME);

		persistUnitCategories(unitCategoriesSheet);
		persistUnits(unitsSheet);
		updateCategories(unitCategoriesSheet);
		persistConversionFormulas(unitConversionsSheet);
	}

	private void persistUnitCategories(Sheet unitCategoriesSheet) {
		Logger.info("== Importing Unit Categories");
		for (int i = 1; i < unitCategoriesSheet.getRows(); i++) {
			String ref = getCellContent(unitCategoriesSheet, 0, i);
			String name = getCellContent(unitCategoriesSheet, 1, i);
			unitCategoriesByRef.put(ref, new UnitCategory(new UnitCategoryCode(ref), name, "", null));
		}
		persistEntities(unitCategoriesByRef.values());
		Logger.info("==== Imported {} Unit Categories", unitCategoriesByRef.size());
	}

	private void persistUnits(Sheet unitsSheet) {
		Logger.info("== Importing Units");
		for (int i = 1; i < unitsSheet.getRows(); i++) {
			String ref = getCellContent(unitsSheet, 0, i);
			String name = getCellContent(unitsSheet, 1, i);
			String symbol = getCellContent(unitsSheet, 2, i);
			String categoryRef = getCellContent(unitsSheet, 3, i);
			UnitCategory category = unitCategoriesByRef.get(categoryRef);
			if (category == null) {
				Logger.warn("No unit category defined for unit with symbol: '{}'!", symbol);
			}
			unitsByRef.put(ref, new Unit(new UnitCode(ref), name, symbol, category));
		}
		persistEntities(unitsByRef.values());
		Logger.info("==== Imported {} Units", unitsByRef.size());
	}

	private void updateCategories(Sheet unitCategoriesSheet) {
		Logger.info("== Updating Unit Categories (setting main Unit)");
		for (int i = 1; i < unitCategoriesSheet.getRows(); i++) {
			String ref = getCellContent(unitCategoriesSheet, 0, i);
			UnitCategory category = unitCategoriesByRef.get(ref);
			String mainUnitRef = getCellContent(unitCategoriesSheet, 2, i);
			Unit mainUnit = unitsByRef.get(mainUnitRef);
			if (mainUnit == null) {
				throw new RuntimeException("Cannot find the main unit (ref = " + mainUnitRef + ") of the category '" + category.getName()
						+ "'");
			}
			category.setMainUnit(mainUnit);
			updateEntity(category);
		}
	}

	private void persistConversionFormulas(Sheet unitConversionsSheet) {
		Logger.info("== Importing Unit Conversion Formulas");
		List<UnitConversionFormula> conversionFormulas = new ArrayList<>();
		for (int i = 1; i < unitConversionsSheet.getRows(); i++) {
			String unitRef = getCellContent(unitConversionsSheet, 0, i);
			Unit unit = unitsByRef.get(unitRef);
			if (unit == null) {
				throw new RuntimeException("Cannot find the conversion formula for unit (ref = " + unitRef + ")");
			}
			String unitToReferenceFormula = getCellContent(unitConversionsSheet, 1, i);
			String referenceToUnitFormula = getCellContent(unitConversionsSheet, 2, i);
			Integer year = null;
			String strYear = getCellContent(unitConversionsSheet, 3, i);
			if (strYear != null) {
				year = Integer.valueOf(strYear);
			}
			conversionFormulas.add(new UnitConversionFormula(unit, unitToReferenceFormula, referenceToUnitFormula, year));
		}
		persistEntities(conversionFormulas);
		Logger.info("==== Imported {} Unit Conversion Formulas", conversionFormulas.size());
	}

}
