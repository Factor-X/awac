package eu.factorx.awac.util.data.importer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;

import jxl.Sheet;

import org.apache.commons.lang3.StringUtils;

import play.db.jpa.JPA;
import eu.factorx.awac.models.AbstractEntity;
import eu.factorx.awac.models.code.Code;

public abstract class WorkbookDataImporter {

	public static final String CP1252_ENCODING = "Cp1252"; // for Windows files
	public static final NumberFormat NUMBER_WITH_DECIMAL_COMMA_FORMAT = NumberFormat.getInstance(Locale.FRANCE); // for decimal numbers with comma
	public static final String NEW_LINE = System.getProperty("line.separator");

	public void run() {
		try {
			importData();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected abstract void importData() throws Exception;

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
			throw new RuntimeException("Exception while parsing number from the content of cell {" + row + ", "
					+ column + "} : " + cellContents, e);
		}
	}

	protected static <T extends AbstractEntity> void persistEntities(Collection<T> entities) {
		System.out.println("== Inserting " + entities.size() + " ..." + entities.iterator().next().getClass());
		for (T entity : entities) {
			persistEntity(entity);
		}
	}

	protected static <T extends Code> void writeCodeConstants(Class<T> codeClass,
			Set<CodeExtract<T>> codeExtracts, BufferedWriter writer) throws IOException {
		writer.write(codeClass.getName() + ":" + NEW_LINE);
		String className = codeClass.getSimpleName();
		for (CodeExtract<T> codeExtract : codeExtracts) {
			String codeName = codeExtract.getName();
			String codeKey = codeExtract.getCode().getKey();
			writer.write("\tpublic static final " + className + " " + codeName + " = new " + className + "(\""
					+ codeKey + "\");" + NEW_LINE);
		}
		writer.write(NEW_LINE + "--------------" + NEW_LINE);
	}

	protected static <T extends AbstractEntity> void persistEntity(T entity) {
		JPA.em().persist(entity);
	}

	protected static <T extends AbstractEntity> void updateEntity(T entity) {
		JPA.em().merge(entity);
	}

}
