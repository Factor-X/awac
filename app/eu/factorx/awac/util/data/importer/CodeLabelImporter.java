package eu.factorx.awac.util.data.importer;

import java.io.File;

import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;

import org.hibernate.Session;
import org.springframework.stereotype.Component;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.code.label.CodeLabel;

@Component
public class CodeLabelImporter extends WorkbookDataImporter {

	private static final String CODE_TO_IMPORT_WORKBOOK_PATH = "data_importer_resources/codes/codes_to_import_full.xls";

	private Workbook codesWkb = null;

	public CodeLabelImporter() {
		super();
	}

	public CodeLabelImporter(Session session) {
		super();
		this.session = session;
	}

	@Override
	protected void importData() throws Exception {
		WorkbookSettings ws = new WorkbookSettings();
		ws.setEncoding(CP1252_ENCODING);
		codesWkb = Workbook.getWorkbook(new File(CODE_TO_IMPORT_WORKBOOK_PATH), ws);

		System.out.println("==== Importing Code Labels (from " + CODE_TO_IMPORT_WORKBOOK_PATH + ") ====");

		for (String sheetName : codesWkb.getSheetNames()) {
			CodeList codeList = CodeList.valueOf(sheetName);
			if (codeList == null) {
				throw new RuntimeException("No codeList named '" + sheetName + "'!");
			}
			saveCodeLabels(sheetName, codeList);
		}

		System.out.println("==== Finding codes correspondances ====");

		// TODO
		
	}

	// Columns: NAME, KEY, LABEL_EN, LABEL_FR, LABEL_NL
	protected <T extends Code> void saveCodeLabels(String sheetName, CodeList codeList) throws Exception {

		System.out.println("== Importing '" + codeList + "' Labels (from sheetName " + sheetName + ") ==");

		// Extract infos from sheet and persist code labels
		Sheet sheet = codesWkb.getSheet(sheetName);
		for (int i = 1; i < sheet.getRows(); i++) {
			String key = getCellContent(sheet, 1, i);
			String labelEn = getCellContent(sheet, 2, i);
			String labelFr = getCellContent(sheet, 3, i);
			String labelNl = getCellContent(sheet, 4, i);

			persistEntity(new CodeLabel(codeList, key, labelEn, labelFr, labelNl));
		}
	}

}
