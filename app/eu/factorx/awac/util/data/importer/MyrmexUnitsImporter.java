package eu.factorx.awac.util.data.importer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;

import org.hibernate.Session;
import org.springframework.stereotype.Component;

import play.Logger;
import eu.factorx.awac.models.knowledge.Unit;
import eu.factorx.awac.models.knowledge.UnitCategory;
import eu.factorx.awac.models.knowledge.UnitConversionFormula;

@Component
public class MyrmexUnitsImporter extends WorkbookDataImporter {

	private static final String MYRMEX_UNITS_WORKBOOK__PATH = "data_importer_resources/myrmex_data_10-07-2014/myrmex_unit.xls";

	/**
	 * Columns:<br>
	 * 0: REF<br>
	 * 1: NAME<br>
	 * 2: MAIN_UNIT_REF
	 */
	private static final String MYRMEX_UNITS_WORKBOOK__CATEGORIES_SHEET__NAME = "categories";

	/**
	 * Columns:<br>
	 * 0: REF<br>
	 * 1: NAME<br>
	 * 2: SYMBOL<br>
	 * 3: CATEGORY_REF
	 */
	private static final String MYRMEX_UNITS_WORKBOOK__UNITS_SHEET__NAME = "units";

	/**
	 * Columns:<br>
	 * 0: UNIT_REF<br>
	 * 1: UNIT_TO_REFERENCE<br>
	 * 2: REFERENCE_TO_UNIT<br>
	 * 3: YEAR
	 */
	private static final String MYRMEX_UNITS_WORKBOOK__CONNVERSIONS_SHEET__NAME = "conversions";

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

		WorkbookSettings ws = new WorkbookSettings();
		ws.setEncoding(CP1252_ENCODING);
		Workbook myrmexUnitsWorkbook = Workbook.getWorkbook(new File(MYRMEX_UNITS_WORKBOOK__PATH), ws);

		persistUnitCategories(myrmexUnitsWorkbook);
		persistUnits(myrmexUnitsWorkbook);
		updateCategories(myrmexUnitsWorkbook);
		persistConversionFormulas(myrmexUnitsWorkbook);
	}

	private void persistUnitCategories(Workbook myrmexUnitsWorkbook) {
		Logger.info("== Importing Unit Categories");
		Sheet unitCategoriesSheet = myrmexUnitsWorkbook.getSheet(MYRMEX_UNITS_WORKBOOK__CATEGORIES_SHEET__NAME);
		for (int i = 1; i < unitCategoriesSheet.getRows(); i++) {
			String ref = getCellContent(unitCategoriesSheet, 0, i);
			String name = getCellContent(unitCategoriesSheet, 1, i);
			unitCategoriesByRef.put(ref, new UnitCategory(ref, name, "", null));
		}
		persistEntities(unitCategoriesByRef.values());
		Logger.info("==== Imported {} Unit Categories", unitCategoriesByRef.size());
	}

	private void persistUnits(Workbook myrmexUnitsWorkbook) {
		Logger.info("== Importing Units");
		Sheet unitsSheet = myrmexUnitsWorkbook.getSheet(MYRMEX_UNITS_WORKBOOK__UNITS_SHEET__NAME);
		for (int i = 1; i < unitsSheet.getRows(); i++) {
			String ref = getCellContent(unitsSheet, 0, i);
			String name = getCellContent(unitsSheet, 1, i);
			String symbol = getCellContent(unitsSheet, 2, i);
			String categoryRef = getCellContent(unitsSheet, 3, i);
			UnitCategory category = unitCategoriesByRef.get(categoryRef);
			unitsByRef.put(ref, new Unit(ref, name, symbol, category));
		}
		persistEntities(unitsByRef.values());
		Logger.info("==== Imported {} Units", unitsByRef.size());
	}

	private void updateCategories(Workbook myrmexUnitsWorkbook) {
		Logger.info("== Updating Unit Categories (setting main Unit)");
		Sheet unitCategoriesSheet = myrmexUnitsWorkbook.getSheet(MYRMEX_UNITS_WORKBOOK__CATEGORIES_SHEET__NAME);
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

	private void persistConversionFormulas(Workbook myrmexUnitsWorkbook) {
		Logger.info("== Importing Unit Conversion Formulas");
		Sheet conversionsSheet = myrmexUnitsWorkbook.getSheet(MYRMEX_UNITS_WORKBOOK__CONNVERSIONS_SHEET__NAME);
		List<UnitConversionFormula> conversionFormulas = new ArrayList<>();
		for (int i = 1; i < conversionsSheet.getRows(); i++) {
			String unitRef = getCellContent(conversionsSheet, 0, i);
			Unit unit = unitsByRef.get(unitRef);
			if (unit == null) {
				throw new RuntimeException("Cannot find the conversion formula for unit (ref = " + unitRef + ")");
			}
			String unitToReferenceFormula = getCellContent(conversionsSheet, 1, i);
			String referenceToUnitFormula = getCellContent(conversionsSheet, 2, i);
			Integer year = null;
			String strYear = getCellContent(conversionsSheet, 3, i);
			if (strYear != null) {
				year = Integer.valueOf(strYear);
			}
			conversionFormulas.add(new UnitConversionFormula(unit, unitToReferenceFormula, referenceToUnitFormula, year));
		}
		persistEntities(conversionFormulas);
		Logger.info("==== Imported {} Unit Conversion Formulas", conversionFormulas.size());
	}

}
