package eu.factorx.awac.util.data.importer;

import java.util.*;

import jxl.Sheet;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import play.Logger;
import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.code.conversion.CodesEquivalence;
import eu.factorx.awac.models.code.label.CodeLabel;
import eu.factorx.awac.service.CodeConversionService;
import eu.factorx.awac.service.CodeLabelService;
import eu.factorx.awac.service.CodesEquivalenceService;

@Component
public class CodeLabelImporter extends WorkbookDataImporter {

	private static final String PRIMARY_KEY_COLUMN_NAME = "KEY";
	private static final String FOREIGN_KEY_SUFFIX = "_KEY";

	private static final String CODES_TO_IMPORT_WORKBOOK_PATH = "data_importer_resources/codes/codes_to_import_full.xls";

	private Map<CodeList, LinkedHashMap<String, CodeLabel>> importedCodeLists = new HashMap<>();

	@Autowired
	private CodeLabelService codeLabelService;
	
	@Autowired
	private CodesEquivalenceService codesEquivalenceService;

	@Autowired
	private CodeConversionService codeConversionService;
	
	public CodeLabelImporter() {
		super();
	}

	@Override
	protected void importData() throws Exception {
		Logger.info("== Importing Code Data (from {})", CODES_TO_IMPORT_WORKBOOK_PATH);
		Map<String, Sheet> codesWbSheets = getWorkbookSheets(CODES_TO_IMPORT_WORKBOOK_PATH);

		// First import labels of sheets with primary key column, which can be referenced in other lists
		importCodeLabels(codesWbSheets);

		// Then import correspondence data: sublists (only a foreign key), and linked lists (sheets with a primary key and one or more foreign keys)
		importCodesEquivalences(codesWbSheets);
	}

	private void importCodeLabels(Map<String, Sheet> codesWbSheets) {
		Logger.info("==== Importing Code Labels");
		for (String sheetName : codesWbSheets.keySet()) {
			CodeList codeList = getCodeListBySheetName(sheetName);
			Sheet sheet = codesWbSheets.get(sheetName);
			String firstCell = getCellContent(sheet, 0, 0);
			if (PRIMARY_KEY_COLUMN_NAME.equals(firstCell)) {
				LinkedHashMap<String, CodeLabel> codeLabels = importCodeLabels(sheet, codeList);
				importedCodeLists.put(codeList, codeLabels);
			}
		}
	}

	// Columns: 0:KEY, 1:LABEL_EN, 2:LABEL_FR, 3:LABEL_NL
	private LinkedHashMap<String, CodeLabel> importCodeLabels(Sheet sheet, CodeList codeList) {
		LinkedHashMap<String, CodeLabel> codeLabels = new LinkedHashMap<>();

		for (int i = 1; i < sheet.getRows(); i++) {
			String key = getCellContent(sheet, 0, i);
			if (key == null) {
				break;
			}
			String labelEn = getCellContent(sheet, 1, i);
			String labelFr = getCellContent(sheet, 2, i);
			String labelNl = getCellContent(sheet, 3, i);
			CodeLabel codeLabel = new CodeLabel(codeList, key, labelEn, labelFr, labelNl);
			codeLabelService.saveOrUpdate(codeLabel);
			codeLabels.put(key, codeLabel);
		}

		Logger.info("====== Imported code list '{}' ({} items)", codeList, codeLabels.size());
		return codeLabels;
	}

	private void importCodesEquivalences(Map<String, Sheet> codesWbSheets) {
		Logger.info("==== Importing Codes Equivalences");
		for (String sheetName : codesWbSheets.keySet()) {
			CodeList codeList = getCodeListBySheetName(sheetName);
			Sheet sheet = codesWbSheets.get(sheetName);

			// searching for foreigns key(s) columns (format: CodeListName + FOREIGN_KEY_SUFFIX)
			for (int columnIndex = 0; columnIndex < sheet.getColumns(); columnIndex++) {
				String columnName = getCellContent(sheet, columnIndex, 0);
				if (!columnName.endsWith(FOREIGN_KEY_SUFFIX)) {
					continue;
				}
				// columns[columnIndex] contains keys of referencedCodeList...
				CodeList referencedCodeList = getReferencedCodeList(codeList, sheetName, columnName);
				if (columnIndex == 0) {
					// case 1: first column contains keys of referencedCodeList - codeList is only a sublist of referencedCodeList
					importSubListData(sheet, codeList, referencedCodeList);
				} else {
					// case 2: first column contains primary keys of codeList - codeList is linked to referencedCodeList (for conversions purpose)
					String firstCell = getCellContent(sheet, 0, 0);
					if (!PRIMARY_KEY_COLUMN_NAME.equals(firstCell)) {
						throw new RuntimeException("Cannot import conversion data from CodeList '" + codeList + "' to CodeList '"
								+ referencedCodeList + "' (defined in column '" + columnName + "' of sheet '" + sheetName
								+ "'): CodeList is not a sublist, and then should have its own primary key defined in the first column");
					}
					importConversionData(sheet, codeList, referencedCodeList, columnIndex);
				}
			}
		}
	}

	// Columns: 0:REF_KEY
	private void importSubListData(Sheet sheet, CodeList codeList, CodeList referencedCodeList) {
		List<CodesEquivalence> codesEquivalences = new ArrayList<>();
		for (int i = 1; i < sheet.getRows(); i++) {
			String referencedCodeKey = getCellContent(sheet, 0, i);
			if (referencedCodeKey == null) {
				break;
			}
			CodesEquivalence codesEquivalence = new CodesEquivalence(codeList, referencedCodeKey, referencedCodeList, referencedCodeKey);
			codesEquivalenceService.saveOrUpdate(codesEquivalence);
			codesEquivalences.add(codesEquivalence);
		}
		Logger.info("====== Imported code list '{}' ({} items) (sublist of '{}')", codeList, codesEquivalences.size(), referencedCodeList);
	}

	// Columns: 0:KEY, refKeyColumnIndex:REF_KEY
	private void importConversionData(Sheet sheet, CodeList codeList, CodeList referencedCodeList, int refKeyColumnIndex) {
		Logger.info("====== Importing data for conversion from code list '{}' to code list '{}'", codeList, referencedCodeList);
		List<CodesEquivalence> codesEquivalences = new ArrayList<>();
		for (int i = 1; i < sheet.getRows(); i++) {
			String codeKey = getCellContent(sheet, 0, i);
			if (codeKey == null) {
				break;
			}
			String referencedCodeKey = getCellContent(sheet, refKeyColumnIndex, i);
			CodesEquivalence codesEquivalence = new CodesEquivalence(codeList, codeKey, referencedCodeList, referencedCodeKey);
			codesEquivalenceService.saveOrUpdate(codesEquivalence);
			codesEquivalences.add(codesEquivalence);
		}
	}

	private static CodeList getCodeListBySheetName(String sheetName) {
		CodeList codeList = CodeList.valueOf(sheetName);
		if (codeList == null) {
			throw new RuntimeException("Cannot import data from sheet '" + sheetName
					+ "': this name does not match with any CodeList enum member");
		}
		return codeList;
	}

	private CodeList getReferencedCodeList(CodeList codeList, String sheetName, String columnName) {
		String refCodeListName = StringUtils.left(columnName, (columnName.length() - FOREIGN_KEY_SUFFIX.length()));
		CodeList refCodeList = CodeList.valueOf(refCodeListName);
		if (!importedCodeLists.containsKey(refCodeList)) {
			throw new RuntimeException("Cannot import correspondance between CodeList '" + codeList + "' and referenced CodeList '"
					+ refCodeList + "' (defined in column '" + columnName + "' of sheet '" + sheetName
					+ "'): the referenced CodeList name does not match with any imported CodeList");
		}
		return refCodeList;
	}

}
