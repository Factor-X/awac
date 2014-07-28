package eu.factorx.awac.util.data.importer;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;

import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;

import play.Logger;
import eu.factorx.awac.models.AbstractEntity;

public abstract class WorkbookDataImporter {

	public static final String CP1252_ENCODING = "Cp1252"; // for Windows files
	public static final NumberFormat NUMBER_WITH_DECIMAL_COMMA_FORMAT = NumberFormat.getInstance(Locale.FRANCE); // for decimal numbers with comma
	public static final String NEW_LINE = System.getProperty("line.separator");

	protected Session session;

	public synchronized void run() {
		try {
			String className = getClass().getSimpleName();
			Logger.info(className + " - START OF IMPORT");
			importData();
			Logger.info(className + " - END OF IMPORT");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected abstract void importData() throws Exception;

	protected Workbook getWorkbook(String path) throws IOException, BiffException {
		Workbook codesWkb = getWorkbook(path, CP1252_ENCODING);
		return codesWkb;
	}

	protected Workbook getWorkbook(String path, String encoding) throws IOException, BiffException {
		WorkbookSettings ws = new WorkbookSettings();
		ws.setEncoding(encoding);
		ws.setSuppressWarnings(true);
		return Workbook.getWorkbook(new File(path), ws);
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
		String cellContents = StringUtils.trimToNull(sheet.getCell(column, row).getContents());
		if (cellContents == null) {
			return null;
		}
		try {
			return NUMBER_WITH_DECIMAL_COMMA_FORMAT.parse(cellContents.replaceAll("\\.", ",")).doubleValue();
		} catch (ParseException e) {
			throw new RuntimeException("Exception while parsing number from the content of cell {" + row + ", " + column + "} : "
					+ cellContents, e);
		}
	}

	protected <T extends AbstractEntity> void persistEntities(Collection<T> entities) {
		for (T entity : entities) {
			persistEntity(entity);
		}
	}

	protected <T extends AbstractEntity> void persistEntity(T entity) {
		session.persist(entity);
	}

	protected <T extends AbstractEntity> void updateEntity(T entity) {
		session.merge(entity);
	}

}
