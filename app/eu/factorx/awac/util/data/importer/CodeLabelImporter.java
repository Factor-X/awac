package eu.factorx.awac.util.data.importer;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.code.conversion.CodeConversion;
import eu.factorx.awac.models.code.conversion.CodesEquivalence;
import eu.factorx.awac.models.code.conversion.ConversionCriterion;
import eu.factorx.awac.models.code.label.CodeLabel;
import eu.factorx.awac.service.CodeConversionService;
import eu.factorx.awac.service.CodeLabelService;
import eu.factorx.awac.service.CodesEquivalenceService;
import jxl.Sheet;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import play.Logger;

import java.util.*;

@Component
public class CodeLabelImporter extends WorkbookDataImporter {

    private static final String PRIMARY_KEY_COLUMN_NAME = "KEY";
    private static final String FOREIGN_KEY_SUFFIX = "_KEY";

    private static final String CODES_TO_IMPORT_COMMON_WORKBOOK_PATH = "data_importer_resources/codes/codes_to_import_common.generated.xls";
    private static final String CODES_TO_IMPORT_ENTERPRISE_WORKBOOK_PATH = "data_importer_resources/codes/codes_to_import_Enterprise.generated.xls";
    private static final String CODES_TO_IMPORT_MUNICIPALITY_WORKBOOK_PATH = "data_importer_resources/codes/codes_to_import_Municipality.generated.xls";

    //use the original excel file
    private static final String ORIGINAL_EXCEL_FILE = "data_importer_resources/awac_data_09-08-2014/AWAC-tous-calcul_FE.xls";
    private static final String SHEET_CODE_CONVERSION = "CodeConversion";

    private Set<CodeList> importedCodeLists = new HashSet<>();
    private Set<Pair<CodeList, CodeList>> importedCodesEquivalences = new HashSet<>();

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
        codeLabelService.removeAll();
        codesEquivalenceService.removeAll();

        Logger.info("== Importing Common Code DataCell (from {})", CODES_TO_IMPORT_COMMON_WORKBOOK_PATH);
        Map<String, Sheet> commonWbSheets = getWorkbookSheets(CODES_TO_IMPORT_COMMON_WORKBOOK_PATH);
        importCodeLabels(commonWbSheets);

        Logger.info("== Importing Enterprise Code DataCell (from {})", CODES_TO_IMPORT_ENTERPRISE_WORKBOOK_PATH);
        Map<String, Sheet> enterpriseWbSheets = getWorkbookSheets(CODES_TO_IMPORT_ENTERPRISE_WORKBOOK_PATH);
        importCodeLabels(enterpriseWbSheets);
        importCodesEquivalences(enterpriseWbSheets);

        Logger.info("== Importing Municipality Code DataCell (from {})", CODES_TO_IMPORT_MUNICIPALITY_WORKBOOK_PATH);
        Map<String, Sheet> municipalityWbSheets = getWorkbookSheets(CODES_TO_IMPORT_MUNICIPALITY_WORKBOOK_PATH);
        importCodeLabels(municipalityWbSheets);
        importCodesEquivalences(municipalityWbSheets);

        //load code equivalence
        Logger.info("== Importing Municipality Code DataCell (from {})", ORIGINAL_EXCEL_FILE + "/" + SHEET_CODE_CONVERSION);
        Map<String, Sheet> originalExcelFile = getWorkbookSheets(ORIGINAL_EXCEL_FILE);
        Sheet codeConversionSheet = originalExcelFile.get(SHEET_CODE_CONVERSION);
        importCodeConversion(codeConversionSheet);
    }

    private void importCodeLabels(Map<String, Sheet> codesWbSheets) {
        Logger.info("==== Importing Code Labels");
        for (String sheetName : codesWbSheets.keySet()) {
            CodeList codeList = getCodeListBySheetName(sheetName);
            if (importedCodeLists.contains(codeList)) {
                Logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>> CodeList '{}' has already been imported", codeList);
                continue;
            }
            Sheet sheet = codesWbSheets.get(sheetName);
            String firstCell = getCellContent(sheet, 0, 0);
            if (PRIMARY_KEY_COLUMN_NAME.equals(firstCell)) {
                importCodeLabels(sheet, codeList);
                importedCodeLists.add(codeList);
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
        if (importedCodeLists.contains(codeList)) {
            Logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>> CodeList '{}' has already been imported", codeList);
            return;
        }
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
        importedCodeLists.add(codeList);
        Logger.info("====== Imported code list '{}' ({} items) (sublist of '{}')", codeList, codesEquivalences.size(), referencedCodeList);
    }

    // Columns: 0:KEY, refKeyColumnIndex:REF_KEY
    private void importConversionData(Sheet sheet, CodeList codeList, CodeList referencedCodeList, int refKeyColumnIndex) {
        if (importedCodesEquivalences.contains(Pair.of(codeList, referencedCodeList))) {
            Logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>> DataCell for conversion from code list '{}' to code list '{}' has already been imported", codeList, referencedCodeList);
            return;
        }
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
        importedCodesEquivalences.add(Pair.of(codeList, referencedCodeList));
        Logger.info("====== Imported data for conversion from code list '{}' to code list '{}' ({} items)", codeList, referencedCodeList, codesEquivalences.size());
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
        if (!importedCodeLists.contains(refCodeList)) {
            throw new RuntimeException("Cannot import correspondance between CodeList '" + codeList + "' and referenced CodeList '"
                    + refCodeList + "' (defined in column '" + columnName + "' of sheet '" + sheetName
                    + "'): the referenced CodeList name does not match with any imported CodeList");
        }
        return refCodeList;
    }


    private void importCodeConversion(Sheet codeConversionSheet) {

        //remove all existing codeConversion
        codeConversionService.removeAll();

        // searching for foreigns key(s) columns (format: CodeListName + FOREIGN_KEY_SUFFIX)
        for (int rowIndex = 0; rowIndex < codeConversionSheet.getRows(); rowIndex++) {

            if (rowIndex > 0) {

                //load code
                String codeListString = codeConversionSheet.getCell(0, rowIndex).getContents();
                String codeKeyFrom = codeConversionSheet.getCell(1, rowIndex).getContents();
                String codeKeyTo = codeConversionSheet.getCell(3, rowIndex).getContents();
                String criterionString = codeConversionSheet.getCell(5, rowIndex).getContents();
                //IllegalArgumentException
                if (codeListString!=null && codeListString.length()>0) {

                    //load list
                    CodeList codeList = CodeList.valueOf(codeListString);

                    if (codeList == null) {
                        Logger.error("unknwon codeList : " + codeListString);
                        continue;
                    }

                    //load code
                    Code codeFrom = new Code(codeList, codeKeyFrom);

                    if (codeFrom == null) {
                        Logger.error("unknwon code : " + codeKeyFrom);
                        continue;
                    }


                    Code codeTo = new Code(codeList, codeKeyTo);


                    if (codeTo == null) {
                        Logger.error("unknwon code : " + codeKeyTo);
                        continue;
                    }

                    //load criterion
                    ConversionCriterion criterion = ConversionCriterion.valueOf(criterionString);


                    if (criterion == null) {
                        Logger.error("unknwon criterion : " + criterionString);
                        continue;
                    }


                    //create entity
                    CodeConversion codeConversion = new CodeConversion();
                    codeConversion.setCodeList(codeList);
                    codeConversion.setCodeKey(codeKeyFrom);
                    codeConversion.setReferencedCodeKey(codeKeyTo);
                    codeConversion.setConversionCriterion(criterion);

                    codeConversionService.saveOrUpdate(codeConversion);
                }
            }


        }

    }

}
