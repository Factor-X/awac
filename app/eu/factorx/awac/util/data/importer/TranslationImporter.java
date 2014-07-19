package eu.factorx.awac.util.data.importer;

import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.code.label.CodeLabel;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class TranslationImporter extends WorkbookDataImporter {

	private static final String CODE_TO_IMPORT_WORKBOOK_PATH = "data_importer_resources/translations/translations.xls";

	private static Workbook wb = null;

	public TranslationImporter() {
		super();
	}

	public TranslationImporter(Session session) {
		super();
		this.session = session;
	}

	@Override
	protected void importData() throws Exception {
		WorkbookSettings ws = new WorkbookSettings();
		ws.setEncoding(CP1252_ENCODING);
		wb = Workbook.getWorkbook(new File(CODE_TO_IMPORT_WORKBOOK_PATH), ws);

		Sheet sheet = wb.getSheet(0);

		for (int i = 1; i < sheet.getRows(); i++) {

			String key = getCellContent(sheet, 0, i);
			String labelEn = getCellContent(sheet, 1, i);
			String labelFr = getCellContent(sheet, 2, i);
			String labelNl = getCellContent(sheet, 3, i);

			CodeLabel cl = new CodeLabel(CodeList.TRANSLATIONS_SURVEY, key, labelEn, labelFr, labelNl);
			session.saveOrUpdate(cl);

		}

	}
}
