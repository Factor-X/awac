package eu.factorx.awac.util.data.importer;

import java.util.*;
import java.util.Map.Entry;

import jxl.Sheet;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import play.Logger;
import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.code.label.CodeLabel;
import eu.factorx.awac.service.CodeLabelService;

@Component
public class TranslationImporter extends WorkbookDataImporter {

	private static final String TRANSLATIONS_WORKBOOK_PATH = "data_importer_resources/translations/translations_common.xls";
	private static final String TRANSLATIONS_ENTERPRISES_PATH = "data_importer_resources/translations/translations_Enterprise.generated.xls";
	private static final String TRANSLATIONS_MUNICIPALITY_PATH = "data_importer_resources/translations/translations_Municipality.generated.xls";
	
	@Autowired
	private CodeLabelService codeLabelService;

	public TranslationImporter() {
		super();
	}

	@Override
	protected void importData() throws Exception {

		codeLabelService.removeCodeLabelsByList(CodeList.TRANSLATIONS_SURVEY, CodeList.QUESTION,
				CodeList.TRANSLATIONS_INTERFACE, CodeList.TRANSLATIONS_ERROR_MESSAGES, CodeList.TRANSLATIONS_EMAIL_MESSAGE);

		Map<String, Sheet> wbSheets = getWorkbookSheets(TRANSLATIONS_WORKBOOK_PATH);
		importTranslations(wbSheets.get("INTERFACE"), CodeList.TRANSLATIONS_INTERFACE);
		importTranslations(wbSheets.get("ERROR_MESSAGES"), CodeList.TRANSLATIONS_ERROR_MESSAGES);
		importTranslations(wbSheets.get("EMAIL_MESSAGES"), CodeList.TRANSLATIONS_EMAIL_MESSAGE);
		importManyCodeListsTranslations(wbSheets.get("OTHERS"));

		for (String workbookPath : new String[] {TRANSLATIONS_ENTERPRISES_PATH, TRANSLATIONS_MUNICIPALITY_PATH}) {
			wbSheets = getWorkbookSheets(workbookPath);
			importTranslations(wbSheets.get("FORMS"), CodeList.TRANSLATIONS_SURVEY);
			importTranslations(wbSheets.get("QUESTION_SETS"), CodeList.QUESTION);
			importTranslations(wbSheets.get("QUESTIONS"), CodeList.QUESTION);
		}

	}

	private void importManyCodeListsTranslations(Sheet sheet) {
		Map<CodeList, List<CodeLabel>> codeLabels = new HashMap<>();

		for (int i = 1; i < sheet.getRows(); i++) {

			String codeListName = getCellContent(sheet, 0, i);
			if (StringUtils.isBlank(codeListName)) {
				break;
			}

			CodeList codeList = CodeList.valueOf(codeListName);
			if (codeList == null) {
				Logger.error("Cannot import data from row {} of sheet '{}': the name of the code list ('{}') does not match with any CodeList enum member");
				continue;
			}
			String key = getCellContent(sheet, 1, i);
			String labelEn = getCellContent(sheet, 2, i);
			String labelFr = getCellContent(sheet, 3, i);
			String labelNl = getCellContent(sheet, 4, i);

            if (StringUtils.isBlank(labelEn)) {
                Logger.error("No English translation found for key: '{}' -> skipping", key);
                continue;
            }
			CodeLabel codeLabel = new CodeLabel(codeList, key, labelEn, labelFr, labelNl);

			if (!codeLabels.containsKey(codeList)) {
				codeLabels.put(codeList, new ArrayList<CodeLabel>());
			}
			codeLabels.get(codeList).add(codeLabel);
		}
		saveCodeLabels(codeLabels);
	}

	private void saveCodeLabels(Map<CodeList, List<CodeLabel>> codeLabels) {
		for (Entry<CodeList, List<CodeLabel>> codeListEntry : codeLabels.entrySet()) {
			CodeList codeList = codeListEntry.getKey();
			codeLabelService.removeCodeLabelsByList(codeList);
			List<CodeLabel> codeListLabels = codeListEntry.getValue();
			for (CodeLabel codeLabel : codeListLabels) {
				codeLabelService.saveOrUpdate(codeLabel);
			}
	        Logger.info("====== Imported code list '{}' ({} items)", codeList, codeListLabels.size());
		}
		
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

            if (StringUtils.isBlank(labelEn)) {
                Logger.error("No English translation found for key: '{}' -> skipping", key);
                continue;
            }
			codeLabelService.saveOrUpdate(new CodeLabel(codeList, key, labelEn, labelFr, labelNl));
		}
	}
}
