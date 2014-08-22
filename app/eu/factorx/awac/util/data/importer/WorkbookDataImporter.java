package eu.factorx.awac.util.data.importer;

import java.io.File;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.*;

import jxl.*;

import org.apache.commons.lang3.StringUtils;

import play.Logger;

public abstract class WorkbookDataImporter {

	public static final String CP1252_ENCODING = "Cp1252"; // for Windows files
	public static final NumberFormat NUMBER_WITH_DECIMAL_COMMA_FORMAT = NumberFormat.getInstance(Locale.FRANCE); // for decimal numbers with comma
	public static final String NEW_LINE = System.getProperty("line.separator");

	public void run() {
		try {
			String className = getClass().getSimpleName();
			Logger.info(className + " - START OF IMPORT");
			long startTime = System.currentTimeMillis();
			importData();
			long endTime = System.currentTimeMillis() - startTime;
			Logger.info(className + " - END OF IMPORT (took " + endTime + " msec)");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected abstract void importData() throws Exception;

	protected static Map<String, Sheet> getWorkbookSheets(String path) {
		return getWorkbookSheets(path, CP1252_ENCODING);
	}

	protected static Map<String, Sheet> getWorkbookSheets(String path, String encoding) {
		WorkbookSettings ws = new WorkbookSettings();
		ws.setEncoding(encoding);
		ws.setSuppressWarnings(true);
		Workbook workbook = null;
		try {
			workbook = Workbook.getWorkbook(new File(path), ws);
		} catch (Exception e) {
			throw new RuntimeException("Exception while loading workbook '" + path + "'", e);
		}

		// save all sheets in a map (by workbookPath and sheetName)
		// => reduces by 80% the time of import! (huge performance leak in Workbook.getSheet(String) method)
		return getAllSheets(workbook);
	}

	protected static Set<String> getColumnContent(Sheet sheet, int column) {
		return getColumnContent(sheet, column, 1);
	}

	protected static Set<String> getColumnContent(Sheet sheet, int column, int firstRow) {
		Set<String> res = new LinkedHashSet<>();
		for (int i = firstRow; i < sheet.getRows(); i++) {
			String identifier = getCellContent(sheet, column, i);
			if (identifier != null) {
				res.add(identifier);
			}
		}
		return res;
	}

	protected static String getCellContent(Sheet sheet, int column, int row) {
		return StringUtils.trimToNull(sheet.getCell(column, row).getContents());
	}

	protected static Set<Double> getColumnNumericContent(Sheet sheet, int column) {
		return getColumnNumericContent(sheet, column, 1);
	}

	protected static Set<Double> getColumnNumericContent(Sheet sheet, int column, int firstRow) {
		Set<Double> res = new LinkedHashSet<>();
		for (int i = firstRow; i < sheet.getRows(); i++) {
			Double value = getNumericCellContent(sheet, column, i);
			if (value != null) {
				res.add(value);
			}
		}
		return res;
	}

	protected static Double getNumericCellContent(Sheet sheet, int column, int row) {
		Cell cell = sheet.getCell(column, row);
		Double value = null;
		if (cell instanceof NumberCell) {
			value = ((NumberCell) cell).getValue();
		} else {
			String cellContents = StringUtils.trimToNull(cell.getContents());
			try {
				value = NUMBER_WITH_DECIMAL_COMMA_FORMAT.parse(cellContents.replaceAll("\\.", ",")).doubleValue();
			} catch (ParseException e) {
				throw new RuntimeException("Exception while parsing number from the content of cell {" + row + ", " + column + "} : " + cellContents, e);
			}
		}
		return value;
	}

	private static Map<String, Sheet> getAllSheets(Workbook workbook) {
		Map<String, Sheet> workbookSheets = new HashMap<>();
		for (Sheet sheet : workbook.getSheets()) {
			workbookSheets.put(sheet.getName(), sheet);
		}
		return workbookSheets;
	}

}
