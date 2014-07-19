package eu.factorx.awac.util.data.importer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;
import java.util.TreeSet;

import org.hibernate.Session;
import org.springframework.stereotype.Component;

import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.code.label.CodeLabel;
import eu.factorx.awac.models.code.type.ACHATAGROCode;
import eu.factorx.awac.models.code.type.ACHATCHIMIQUECode;
import eu.factorx.awac.models.code.type.ACHATMETALCode;
import eu.factorx.awac.models.code.type.ACHATPAPIERCode;
import eu.factorx.awac.models.code.type.ACHATPLASTIQUECode;
import eu.factorx.awac.models.code.type.ACHATROUTECode;
import eu.factorx.awac.models.code.type.ACHATSERVICECode;
import eu.factorx.awac.models.code.type.ACHATVERRECode;
import eu.factorx.awac.models.code.type.ActivitySectorCode;
import eu.factorx.awac.models.code.type.BaseActivityDataCode;
import eu.factorx.awac.models.code.type.CARBURANTCode;
import eu.factorx.awac.models.code.type.CATEGORIEVOLCode;
import eu.factorx.awac.models.code.type.COMBUSTIBLECode;
import eu.factorx.awac.models.code.type.ENERGIEVAPEURCode;
import eu.factorx.awac.models.code.type.FRIGORIGENEBASECode;
import eu.factorx.awac.models.code.type.FRIGORIGENECode;
import eu.factorx.awac.models.code.type.FuelCode;
import eu.factorx.awac.models.code.type.GESCode;
import eu.factorx.awac.models.code.type.GESSIMPLIFIECode;
import eu.factorx.awac.models.code.type.INFRASTRUCTURECode;
import eu.factorx.awac.models.code.type.LanguageCode;
import eu.factorx.awac.models.code.type.MOTIFDEPLACEMENTCode;
import eu.factorx.awac.models.code.type.Nace1Code;
import eu.factorx.awac.models.code.type.Nace2Code;
import eu.factorx.awac.models.code.type.Nace3Code;
import eu.factorx.awac.models.code.type.ORIGINEEAUUSEECode;
import eu.factorx.awac.models.code.type.POURCENTSIMPLIFIECode;
import eu.factorx.awac.models.code.type.PROVENANCESIMPLIFIEECode;
import eu.factorx.awac.models.code.type.PublicPrivateCode;
import eu.factorx.awac.models.code.type.TRAITEMENTDECHETCode;
import eu.factorx.awac.models.code.type.TRAITEUREAUCode;
import eu.factorx.awac.models.code.type.TYPEACHATCode;
import eu.factorx.awac.models.code.type.TYPEDECHETCode;
import eu.factorx.awac.models.code.type.TYPEPRODUITCode;
import eu.factorx.awac.models.code.type.TYPEVEHICULECode;
import eu.factorx.awac.models.code.type.TYPEVOLCode;

@Component
public class CodeImporter extends WorkbookDataImporter {

	private static final String CODE_TO_IMPORT_WORKBOOK_PATH = "data_importer_resources/codes/codes_to_import_full.xls";
	private static final String CODE_CONSTANTS_FILE_PATH = "code_constants.txt";
	private static Workbook codesWkb = null;
	
	public CodeImporter() {
		super();
	}

	public CodeImporter(Session session) {
		super();
		this.session = session;
	}
	
	@Override
	protected void importData() throws Exception {
		WorkbookSettings ws = new WorkbookSettings();
		ws.setEncoding(CP1252_ENCODING);
		codesWkb = Workbook.getWorkbook(new File(CODE_TO_IMPORT_WORKBOOK_PATH), ws);

		System.out.println("==== Importing Code Labels (from " + CODE_TO_IMPORT_WORKBOOK_PATH + ") ====");

        importCodesFromDatasheet("language", LanguageCode.class, CodeList.LANGUAGE);
		importCodesFromDatasheet("public_private", PublicPrivateCode.class, CodeList.PUBLIC_PRIVATE);
		importCodesFromDatasheet("site_sectors", ActivitySectorCode.class, CodeList.SITE_SECTORS);
		importCodesFromDatasheet("nace_codes_1", Nace1Code.class, CodeList.NACE_CODES_1);
		importCodesFromDatasheet("nace_codes_2", Nace2Code.class, CodeList.NACE_CODES_2);
		importCodesFromDatasheet("nace_codes_3", Nace3Code.class, CodeList.NACE_CODES_3);
		importCodesFromDatasheet("fuel", FuelCode.class, CodeList.FUEL);
        importCodesFromDatasheet("BaseActivityData", BaseActivityDataCode.class, CodeList.BASE_ACTIVITY_DATA);

        importCodesFromDatasheet("ENERGIEVAPEUR", ENERGIEVAPEURCode.class, CodeList.ENERGIEVAPEUR);
        importCodesFromDatasheet("GES", GESCode.class, CodeList.GES);
        importCodesFromDatasheet("FRIGORIGENE", FRIGORIGENECode.class, CodeList.FRIGORIGENE);
        importCodesFromDatasheet("MOTIFDEPLACEMENT", MOTIFDEPLACEMENTCode.class, CodeList.MOTIFDEPLACEMENT);
        importCodesFromDatasheet("CARBURANT", CARBURANTCode.class, CodeList.CARBURANT);
        importCodesFromDatasheet("TYPEVEHICULE", TYPEVEHICULECode.class, CodeList.TYPEVEHICULE);
        importCodesFromDatasheet("TYPEVOL", TYPEVOLCode.class, CodeList.TYPEVOL);
        importCodesFromDatasheet("CATEGORIEVOL", CATEGORIEVOLCode.class, CodeList.CATEGORIEVOL);
        importCodesFromDatasheet("FRIGORIGENEBASE", FRIGORIGENEBASECode.class, CodeList.FRIGORIGENEBASE);
        importCodesFromDatasheet("PROVENANCESIMPLIFIEE", PROVENANCESIMPLIFIEECode.class, CodeList.PROVENANCESIMPLIFIEE);
        importCodesFromDatasheet("TYPEDECHET", TYPEDECHETCode.class, CodeList.TYPEDECHET);
        importCodesFromDatasheet("TRAITEMENTDECHET", TRAITEMENTDECHETCode.class, CodeList.TRAITEMENTDECHET);
        importCodesFromDatasheet("TRAITEUREAU", TRAITEUREAUCode.class, CodeList.TRAITEUREAU);
        importCodesFromDatasheet("ORIGINEEAUUSEE", ORIGINEEAUUSEECode.class, CodeList.ORIGINEEAUUSEE);
        importCodesFromDatasheet("TYPEACHAT", TYPEACHATCode.class, CodeList.TYPEACHAT);
        importCodesFromDatasheet("ACHATMETAL", ACHATMETALCode.class, CodeList.ACHATMETAL);
        importCodesFromDatasheet("ACHATPLASTIQUE", ACHATPLASTIQUECode.class, CodeList.ACHATPLASTIQUE);
        importCodesFromDatasheet("ACHATPAPIER", ACHATPAPIERCode.class, CodeList.ACHATPAPIER);
        importCodesFromDatasheet("ACHATVERRE", ACHATVERRECode.class, CodeList.ACHATVERRE);
        importCodesFromDatasheet("ACHATCHIMIQUE", ACHATCHIMIQUECode.class, CodeList.ACHATCHIMIQUE);
        importCodesFromDatasheet("ACHATROUTE", ACHATROUTECode.class, CodeList.ACHATROUTE);
        importCodesFromDatasheet("ACHATAGRO", ACHATAGROCode.class, CodeList.ACHATAGRO);
        importCodesFromDatasheet("ACHATSERVICE", ACHATSERVICECode.class, CodeList.ACHATSERVICE);
        importCodesFromDatasheet("INFRASTRUCTURE", INFRASTRUCTURECode.class, CodeList.INFRASTRUCTURE);
        importCodesFromDatasheet("TYPEPRODUIT", TYPEPRODUITCode.class, CodeList.TYPEPRODUIT);
        importCodesFromDatasheet("COMBUSTIBLE", COMBUSTIBLECode.class, CodeList.COMBUSTIBLE);
        importCodesFromDatasheet("GESSIMPLIFIE", GESSIMPLIFIECode.class, CodeList.GESSIMPLIFIE);
        importCodesFromDatasheet("POURCENTSIMPLIFIE", POURCENTSIMPLIFIECode.class, CodeList.POURCENTSIMPLIFIE);



	}

	// Columns: NAME, KEY, LABEL_EN, LABEL_FR, LABEL_NL
	protected <T extends Code> void importCodesFromDatasheet(String sheetName , Class<T> codeClass, CodeList codeList) throws Exception {

		System.out.println("== Importing '" + codeList + "' Labels (from sheetName " + sheetName + ") ==");
		
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
