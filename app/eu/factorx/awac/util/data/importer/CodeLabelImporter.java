package eu.factorx.awac.util.data.importer;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;

import org.hibernate.Session;
import org.springframework.stereotype.Component;

import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.code.label.CodeLabel;

@Component
public class CodeLabelImporter extends WorkbookDataImporter {

	// Columns: NAME (not used), KEY, LABEL_EN, LABEL_FR, LABEL_NL
	private static final String CODE_TO_IMPORT_WORKBOOK_PATH = "data_importer_resources/codes/codes_to_import_full.xls";

	private Map<String, CodeLabel> activityData = new HashMap<>();

	private List<String> ActivityDataSheetNames =Arrays.asList(new String[]{"ActivityType", "ActivitySubCategory", "ActivitySource"});
	
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

		// First Import code labels of ActivitySources, ActivityTypes and ActivitySubCategories
		for (String sheetName : ActivityDataSheetNames) {
			CodeList codeList = CodeList.valueOf(sheetName);
			if (codeList == null) {
				throw new RuntimeException("The sheet name '" + sheetName + "' does not match with any CodeList enum member");
			}
			System.out.println("== Importing labels from sheet " + sheetName + " ==");
			Sheet sheet = codesWkb.getSheet(sheetName);
			saveActivityDataCodeLabels(sheet, codeList);
		}

		// Then import other lists
		for (String sheetName : codesWkb.getSheetNames()) {
			if (ActivityDataSheetNames.contains(sheetName)) {
				continue;
			}
			
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

	protected void saveActivityDataCodeLabels(Sheet sheet, CodeList codeList) {
		for (int i = 1; i < sheet.getRows(); i++) {
			
			String key = getCellContent(sheet, 0, i);
			if (key == null) {
				return;
			}
			String labelEn = getCellContent(sheet, 1, i);
			String labelFr = getCellContent(sheet, 2, i);
			String labelNl = getCellContent(sheet, 3, i);

			CodeLabel codeLabel = new CodeLabel(codeList, key, labelEn, labelFr, labelNl);
			activityData.put(key, codeLabel);
			persistEntity(codeLabel);
		}
	}
	protected void saveCodeLabels(Sheet sheet, CodeList codeList) {
		String firstCell = getCellContent(sheet, 0, 0);

		if ("KEY".equals(firstCell)) {
			// "Main" code list
			for (int i = 1; i < sheet.getRows(); i++) {
				String key = getCellContent(sheet, 0, i);
				if (key == null) {
					return;
				}
				String labelEn = getCellContent(sheet, 1, i);
				String labelFr = getCellContent(sheet, 2, i);
				String labelNl = getCellContent(sheet, 3, i);

				persistEntity(new CodeLabel(codeList, key, labelEn, labelFr, labelNl));
			}

		} else if ("ActivitySource_KEY".equals(firstCell)) {
			// Sublist of ActivitySources
			for (int i = 1; i < sheet.getRows(); i++) {
				String activitySourceKey = getCellContent(sheet, 0, i);
				if (activitySourceKey == null) {
					return;
				}
				CodeLabel codeLabel = activityData.get(activitySourceKey);
				persistEntity(new CodeLabel(codeList, activitySourceKey, codeLabel.getLabelEn(), codeLabel.getLabelFr(),
						codeLabel.getLabelNl()));
			}

		} else if ("ActivityType_KEY".equals(firstCell)) {
			// Sublist of ActivityTypes
			for (int i = 1; i < sheet.getRows(); i++) {
				String activityTypeKey = getCellContent(sheet, 0, i);
				if (activityTypeKey == null) {
					return;
				}
				CodeLabel codeLabel = activityData.get(activityTypeKey);
				persistEntity(new CodeLabel(codeList, activityTypeKey, codeLabel.getLabelEn(), codeLabel.getLabelFr(),
						codeLabel.getLabelNl()));
			}

		} else if ("ActivitySubCategory_KEY".equals(firstCell)) {
			// Sublist of ActivitySubCategories
			for (int i = 1; i < sheet.getRows(); i++) {
				String activitySubCategoryKey = getCellContent(sheet, 0, i);
				if (activitySubCategoryKey == null) {
					return;
				}
				CodeLabel codeLabel = activityData.get(activitySubCategoryKey);
				persistEntity(new CodeLabel(codeList, activitySubCategoryKey, codeLabel.getLabelEn(), codeLabel.getLabelFr(),
						codeLabel.getLabelNl()));
			}

		} else {
			throw new RuntimeException("Cannot handle sheet '" + sheet.getName() + "': type unknown");
		}
	}

}
