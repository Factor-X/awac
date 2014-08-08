package eu.factorx.awac.util.data.importer;

import java.util.Map;

import jxl.Sheet;

import org.hibernate.Session;
import org.springframework.stereotype.Component;

import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.code.label.CodeLabel;

@Component
public class TranslationImporter extends WorkbookDataImporter {

	private static final String TRANSLATIONS_WORKBOOK_PATH = "data_importer_resources/translations/translations.xls";

	public TranslationImporter() {
		super();
	}

	public TranslationImporter(Session session) {
		super();
		this.session = session;
	}

	@Override
	protected void importData() throws Exception {
		Map<String, Sheet> wbSheets = getWorkbookSheets(TRANSLATIONS_WORKBOOK_PATH);

		Sheet survey = wbSheets.get("SURVEY");

		for (int i = 1; i < survey.getRows(); i++) {

			String key = getCellContent(survey, 0, i);
			if (key == null || key.trim().length() == 0) continue;
			String labelEn = getCellContent(survey, 1, i);
			String labelFr = getCellContent(survey, 2, i);
			String labelNl = getCellContent(survey, 3, i);

			CodeLabel cl = new CodeLabel(CodeList.TRANSLATIONS_SURVEY, key, labelEn, labelFr, labelNl);
			session.saveOrUpdate(cl);

		}

		Sheet ui = wbSheets.get("INTERFACE");

		for (int i = 1; i < ui.getRows(); i++) {

			String key = getCellContent(ui, 0, i);
			if (key == null || key.trim().length() == 0) continue;

			String labelEn = getCellContent(ui, 1, i);
			String labelFr = getCellContent(ui, 2, i);
			String labelNl = getCellContent(ui, 3, i);

			CodeLabel cl = new CodeLabel(CodeList.TRANSLATIONS_INTERFACE, key, labelEn, labelFr, labelNl);
			session.saveOrUpdate(cl);

		}

	}
}
