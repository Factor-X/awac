package eu.factorx.awac.util.data.importer;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;

import org.springframework.stereotype.Component;

import eu.factorx.awac.models.knowledge.Unit;
import eu.factorx.awac.models.knowledge.UnitCategory;

@Component
public class MyrmexUnitsImporter extends WorkbookDataImporter {

	private static final String MYRMEX_UNITS_WORKBOOK__PATH = "data_importer_resources/myrmex_data_10-07-2014/myrmex_unit.xls";

	/**
	 * Columns:<br>
	 * 0: REF<br>
	 * 1: NAME<br>
	 * 2: MAIN_UNIT_SYMBOL
	 */
	private static final String MYRMEX_UNITS_WORKBOOK__CATEGORIES_SHEET__NAME = "categories";

	/**
	 * Columns:<br>
	 * 0: REF<br>
	 * 1: SYMBOL<br>
	 * 2: CATEGORY_REF
	 */
	private static final String MYRMEX_UNITS_WORKBOOK__UNITS_SHEET__NAME = "units";

	private static Map<String, UnitCategory> unitCategories = new LinkedHashMap<>(); // key: ref
	private static Map<String, Unit> units = new LinkedHashMap<String, Unit>(); // key: symbol

	protected void importData() throws BiffException, IOException {

		WorkbookSettings ws = new WorkbookSettings();
		ws.setEncoding(CP1252_ENCODING);
		Workbook myrmexUnitsWorkbook = Workbook.getWorkbook(new File(MYRMEX_UNITS_WORKBOOK__PATH), ws);

		// Persit Unit Categories
		Sheet unitCategoriesSheet = myrmexUnitsWorkbook.getSheet(MYRMEX_UNITS_WORKBOOK__CATEGORIES_SHEET__NAME);
		for (int i = 1; i < unitCategoriesSheet.getRows(); i++) {
			String ref = getCellContent(unitCategoriesSheet, 0, i);
			String name = getCellContent(unitCategoriesSheet, 1, i);
			unitCategories.put(ref, new UnitCategory(ref, name, "", null));
		}
		persistEntities(unitCategories.values());

		// Persit Units
		Sheet unitsSheet = myrmexUnitsWorkbook.getSheet(MYRMEX_UNITS_WORKBOOK__UNITS_SHEET__NAME);
		for (int i = 1; i < unitsSheet.getRows(); i++) {
			String ref = getCellContent(unitsSheet, 0, i);
			String symbol = getCellContent(unitsSheet, 1, i);
			String categoryRef = getCellContent(unitsSheet, 2, i);
			UnitCategory category = unitCategories.get(categoryRef);
			units.put(symbol, new Unit(ref, "", symbol, category));
		}
		System.out.println("== Inserting " + units.size() + " Units...");
		persistEntities(units.values());

		// Update Categories (set main Unit)
		System.out.println("== Updating " + unitCategories.size() + " Unit Categories (set main Unit)...");
		for (int i = 1; i < unitCategoriesSheet.getRows(); i++) {
			String ref = getCellContent(unitCategoriesSheet, 0, i);
			UnitCategory category = unitCategories.get(ref);
			String mainUnitSymbol = getCellContent(unitCategoriesSheet, 2, i);
			Unit mainUnit = units.get(mainUnitSymbol);
			if (mainUnit == null) {
				throw new RuntimeException("Cannot find the main unit (symbol '" + mainUnitSymbol
						+ "') of the category '" + category.getName() + "'!");
			}
			category.setMainUnit(mainUnit);
			updateEntity(category);
		}
	}

}
