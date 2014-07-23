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

	// Columns: NAME (not used), KEY, LABEL_EN, LABEL_FR, LABEL_NL
	private static final String CODE_TO_IMPORT_WORKBOOK_PATH = "data_importer_resources/codes/codes_to_import_full.xls";

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
		Workbook codesWkb = Workbook.getWorkbook(new File(CODE_TO_IMPORT_WORKBOOK_PATH), ws);

		System.out.println("==== Importing Code Labels (from " + CODE_TO_IMPORT_WORKBOOK_PATH + ") ====");

		for (String sheetName : codesWkb.getSheetNames()) {
			CodeList codeList = CodeList.valueOf(sheetName);
			if (codeList == null) {
				throw new RuntimeException("The sheet name '" + sheetName + "' does not match with any CodeList enum member");
			}
			System.out.println("== Importing labels from sheet " + sheetName + " ==");
			Sheet sheet = codesWkb.getSheet(sheetName);
			saveCodeLabels(sheet, codeList);
		}

		System.out.println("==== Finding codes correspondances ====");

		// TODO
		
	}

	protected <T extends Code> void saveCodeLabels(Sheet sheet, CodeList codeList) {
		for (int i = 1; i < sheet.getRows(); i++) {
			String key = getCellContent(sheet, 1, i);
			String labelEn = getCellContent(sheet, 2, i);
			String labelFr = getCellContent(sheet, 3, i);
			String labelNl = getCellContent(sheet, 4, i);

			persistEntity(new CodeLabel(codeList, key, labelEn, labelFr, labelNl));
		}
	}

}
