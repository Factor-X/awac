package eu.factorx.awac.util.data.importer;

import java.util.Map;

import jxl.Sheet;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.code.label.CodeLabel;
import eu.factorx.awac.service.CodeLabelService;

@Component
public class TranslationImporter extends WorkbookDataImporter {

	private static final String TRANSLATIONS_WORKBOOK_PATH = "data_importer_resources/translations/translations.xls";

	@Autowired
	private CodeLabelService codeLabelService;
	
	public TranslationImporter() {
		super();
	}

	@Override
	protected void importData() throws Exception {
		Map<String, Sheet> wbSheets = getWorkbookSheets(TRANSLATIONS_WORKBOOK_PATH);

		importTranslations(wbSheets.get("SURVEY"), CodeList.TRANSLATIONS_SURVEY);
		importTranslations(wbSheets.get("INTERFACE"), CodeList.TRANSLATIONS_INTERFACE);
		importTranslations(wbSheets.get("ERROR_MESSAGES"), CodeList.TRANSLATIONS_ERROR_MESSAGES);
	}

	private void importTranslations(Sheet sheet, CodeList codeList) {
		for (int i = 1; i < sheet.getRows(); i++) {

			String key = getCellContent(sheet, 0, i);
			if (StringUtils.isBlank(key)) {
				continue;
			}

			String labelEn = getCellContent(sheet, 1, i);
			String labelFr = getCellContent(sheet, 2, i);
			String labelNl = getCellContent(sheet, 3, i);

			codeLabelService.saveOrUpdate(new CodeLabel(codeList, key, labelEn, labelFr, labelNl));
		}
	}
}
