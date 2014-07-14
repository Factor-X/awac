package eu.factorx.awac.util.data.importer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;
import java.util.TreeSet;

import eu.factorx.awac.models.code.type.*;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.code.label.CodeLabel;

public class CodeImporter extends WorkbookDataImporter {

	private static final String CODE_TO_IMPORT_WORKBOOK_PATH = "data_importer_resources/codes/codes_to_import.xls";
	private static final String CODE_CONSTANTS_FILE_PATH = "code_constants.txt";
	private static Workbook codesWkb = null;
	
	@Override
	protected void importData() throws Exception {
		WorkbookSettings ws = new WorkbookSettings();
		ws.setEncoding(CP1252_ENCODING);
		codesWkb = Workbook.getWorkbook(new File(CODE_TO_IMPORT_WORKBOOK_PATH), ws);

        importCodesFromDatasheet("language", LanguageCode.class, CodeList.LANGUAGE);
		importCodesFromDatasheet("public_private", PublicPrivateCode.class, CodeList.PUBLIC_PRIVATE);
		importCodesFromDatasheet("site_sectors", ActivitySectorCode.class, CodeList.SITE_SECTORS);
		importCodesFromDatasheet("nace_codes_1", Nace1Code.class, CodeList.NACE_CODES_1);
		importCodesFromDatasheet("nace_codes_2", Nace2Code.class, CodeList.NACE_CODES_2);
		importCodesFromDatasheet("nace_codes_3", Nace3Code.class, CodeList.NACE_CODES_3);
		importCodesFromDatasheet("fuel", FuelCode.class, CodeList.FUEL);

	}

	// Columns: NAME, KEY, LABEL_EN, LABEL_FR, LABEL_NL
	protected <T extends Code> void importCodesFromDatasheet(String sheetName , Class<T> codeClass, CodeList codeList) throws Exception {

		// check if there is a suitable constructor ( {CodeList, String} or {String} )
		Constructor<T> classConstructor = getConstructor(codeClass);
		int nbParams = classConstructor.getParameterTypes().length;
		
		// Extract infos from sheet and persist code labels
		Set<CodeExtract<T>> codeExtracts = new TreeSet<>();
		Sheet sheet = codesWkb.getSheet(sheetName);
		for (int i = 1; i < sheet.getRows(); i++) {
			String name = getCellContent(sheet, 0, i);
			String key = getCellContent(sheet, 1, i);
			String labelEn = getCellContent(sheet, 2, i);
			String labelFr = getCellContent(sheet, 3, i);
			String labelNl = getCellContent(sheet, 4, i);

			T code = getNewCodeInstance(classConstructor, nbParams, codeList, key);
			CodeLabel codeLabel = new CodeLabel(code, labelEn, labelFr, labelNl);
			persistEntity(codeLabel);
			codeExtracts.add(new CodeExtract<T>(name, code, codeLabel));
		}

		// output Java constants
		FileWriter out = new FileWriter(CODE_CONSTANTS_FILE_PATH, true);
		BufferedWriter writer = new BufferedWriter(out);
		writeCodeConstants(codeClass, codeExtracts, writer);
		writer.flush();
		writer.close();
	}

	private static <T extends Code> Constructor<T> getConstructor(Class<T> codeClass) throws NoSuchMethodException {
		Constructor<T> classConstructor = null;
		try {
			classConstructor = codeClass.getConstructor(String.class);
		} catch (NoSuchMethodException | SecurityException e) {
			classConstructor = codeClass.getConstructor(CodeList.class, String.class);
		}
		if (classConstructor == null) {
			throw new RuntimeException("The class " + codeClass.getName() + " contains no suitable constructor ( {CodeList, String} or {String} ), or this constructor is not visible");
		}
		return classConstructor;
	}

	private <T extends Code> T getNewCodeInstance(Constructor<T> classConstructor, int nbParams, CodeList codeList,
			String key) throws InstantiationException, IllegalAccessException, InvocationTargetException {
		T code = null;
		if (nbParams == 1) {
			code = classConstructor.newInstance(key);
			code.setCodeList(codeList); // if it was omitted in the constructor
		} else {
			code = classConstructor.newInstance(codeList, key);
		}
		return code;
	}

}
