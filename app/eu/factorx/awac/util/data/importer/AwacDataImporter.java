package eu.factorx.awac.util.data.importer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import play.Logger;
import play.db.jpa.JPA;
import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.code.label.CodeLabel;
import eu.factorx.awac.models.code.type.ActivityCategoryCode;
import eu.factorx.awac.models.code.type.ActivitySourceCode;
import eu.factorx.awac.models.code.type.ActivitySubCategoryCode;
import eu.factorx.awac.models.code.type.ActivityTypeCode;
import eu.factorx.awac.models.code.type.IndicatorCategoryCode;
import eu.factorx.awac.models.code.type.IndicatorIsoScopeCode;
import eu.factorx.awac.models.code.type.IndicatorTypeCode;
import eu.factorx.awac.models.code.type.ScopeTypeCode;
import eu.factorx.awac.models.knowledge.Factor;
import eu.factorx.awac.models.knowledge.FactorValue;
import eu.factorx.awac.models.knowledge.Indicator;
import eu.factorx.awac.models.knowledge.Unit;
import eu.factorx.awac.service.CodeLabelService;

@Component
public class AwacDataImporter extends WorkbookDataImporter {

	private static final String AWAC_DATA_WORKBOOK__PATH = "data_importer_resources/awac_data_25-07-2014/AWAC-entreprise-calcul_FE.xls";

	/**
	 * Columns:<br>
	 * 0: INDICATOR CATEGORY<br>
	 * 1: ACTIVITY TYPE<br>
	 * 2: ACTIVITY SOURCE<br>
	 * 3: UNIT IN<br>
	 * 4: INSTITUTION<br>
	 * 5: VALUE<br>
	 * 6: UNIT OUT
	 */
	private static final String AWAC_DATA_WORKBOOK__FACTORS_SHEET__NAME = "EFDB_V6";

	/**
	 * Columns:<br>
	 * 0: INDICATOR TYPE (= {@link IndicatorTypeCode#CARBON})<br>
	 * 1: NAME<br>
	 * 2: ORG SCOPE (= {@link ScopeTypeCode#ORG})<br>
	 * 3: INDICATOR SCOPE<br>
	 * 4: INDICATOR CATEGORY<br>
	 * 5: UNIT (= 'tCO2e')<br>
	 * 6: ACTIVITY CATEGORY<br>
	 * 7: ACTIVITY SUBCATEGORY<br>
	 * 10: ACTIVITY OWNERSHIP
	 */
	private static final String AWAC_DATA_WORKBOOK__INDICATORS_SHEET__NAME = "Indicators";

	@Autowired
	private CodeLabelService codeLabelService;
	
	// unit by symbol map
	private Map<String, Unit> knownUnits = null;

	// code key by code label maps
	private Map<String, String> indicatorCategories = null;
	private Map<String, String> activityTypes = null;
	private Map<String, String> activitySources = null;
	private Map<String, String> activityCategories = null;
	private Map<String, String> activitySubCategories = null;

	public AwacDataImporter() {
		super();
	}

	public AwacDataImporter(Session session) {
		super();
		this.session = session;
	}
	
	protected void importData() throws Exception {

		knownUnits = new HashMap<>();
		for (Unit unit : findAllUnits()) {
			knownUnits.put(unit.getSymbol(), unit);
		}

		indicatorCategories = findCodesLabelsByCodeList(CodeList.IndicatorCategory);
		activityTypes = findCodesLabelsByCodeList(CodeList.ActivityType);
		activitySources = findCodesLabelsByCodeList(CodeList.ActivitySource);
		activityCategories = findCodesLabelsByCodeList(CodeList.ActivityCategory);
		activitySubCategories = findCodesLabelsByCodeList(CodeList.ActivitySubCategory);

		WorkbookSettings ws = new WorkbookSettings();
		ws.setEncoding(CP1252_ENCODING);

		Workbook awacDataWorkbook = Workbook.getWorkbook(new File(AWAC_DATA_WORKBOOK__PATH), ws);
		Sheet factorsSheet = awacDataWorkbook.getSheet(AWAC_DATA_WORKBOOK__FACTORS_SHEET__NAME);
		Sheet indicatorsSheet = awacDataWorkbook.getSheet(AWAC_DATA_WORKBOOK__INDICATORS_SHEET__NAME);

		System.out.println("==== Verify Awac business Data (from " + AWAC_DATA_WORKBOOK__PATH + ") ====");
		try {
//			verifyAwacData(factorsSheet, indicatorsSheet);
		} catch (RuntimeException e) {
			Logger.error("######## ABORTING IMPORT OF INDICATORS AND FACTORS - Please fix errors and relaunch the DB import script!");
			return;
		}

		System.out.println("==== Save Factors ====");
		saveFactors(factorsSheet);

		System.out.println("==== Save Indicators ====");
		saveIndicators(indicatorsSheet);
	}

	private void verifyAwacData(Sheet factorsSheet, Sheet indicatorsSheet) throws BiffException, IOException {
		
		boolean validationResult = true;

		// parse Factors
		Set<String> indicatorCategoriesIdentifiers = getColumnContent(factorsSheet, 0);
		Set<String> activityTypesIdentifiers = getColumnContent(factorsSheet, 1);
		Set<String> activitySourcesIdentifiers = getColumnContent(factorsSheet, 2);
		Set<String> unitsSymbols = getColumnContent(factorsSheet, 3);
		getColumnNumericContent(factorsSheet, 5);
		unitsSymbols.addAll(getColumnContent(factorsSheet, 6));

		List<String> unknownIdentifiers = verifyCodeExist(indicatorCategoriesIdentifiers, indicatorCategories);
		if (!unknownIdentifiers.isEmpty()) {			
			System.out.println("Error while parsing '" + factorsSheet.getName() + "' : these Indicator Categories are unknown : " + unknownIdentifiers + " (not repertoried in sheet '" + CodeList.IndicatorCategory + "')");
			validationResult = false;
		}
		unknownIdentifiers = verifyCodeExist(activityTypesIdentifiers, activityTypes);
		if (!unknownIdentifiers.isEmpty()) {			
			validationResult = false;
			System.out.println("Error while parsing '" + factorsSheet.getName() + "' : these Activity Types are unknown : " + unknownIdentifiers + " (not repertoried in sheet '" + CodeList.ActivityType + "')");
		}
		unknownIdentifiers = verifyCodeExist(activitySourcesIdentifiers, activitySources);
		if (!unknownIdentifiers.isEmpty()) {			
			System.out.println("Error while parsing '" + factorsSheet.getName() + "' : these Activity Sources are unknown : " + unknownIdentifiers + " (not repertoried in sheet '" + CodeList.ActivitySource + "')");
			validationResult = false;
		}

		// parse Indicators
		indicatorCategoriesIdentifiers = getColumnContent(indicatorsSheet, 4);
		Set<String> activityCategoriesIdentifiers = getColumnContent(indicatorsSheet, 6);
		Set<String> activitySubCategoriesIdentifiers = getColumnContent(indicatorsSheet, 7);

		unknownIdentifiers = verifyCodeExist(indicatorCategoriesIdentifiers, indicatorCategories);
		if (!unknownIdentifiers.isEmpty()) {			
			System.out.println("Error while parsing '" + indicatorsSheet.getName() + "' : these Indicator Categories are unknown : " + unknownIdentifiers + " (not repertoried in sheet '" + CodeList.IndicatorCategory + "')");
			validationResult = false;
		}
		unknownIdentifiers = verifyCodeExist(activityCategoriesIdentifiers, activityCategories);
		if (!unknownIdentifiers.isEmpty()) {			
			System.out.println("Error while parsing '" + indicatorsSheet.getName() + "' : these Activity Categories are unknown : " + unknownIdentifiers + " (not repertoried in sheet '" + CodeList.ActivityCategory + "')");
			validationResult = false;
		}
		unknownIdentifiers = verifyCodeExist(activitySubCategoriesIdentifiers, activitySubCategories);
		if (!unknownIdentifiers.isEmpty()) {			
			System.out.println("Error while parsing '" + indicatorsSheet.getName() + "' : these Activity SubCategories are unknown : " + unknownIdentifiers + " (not repertoried in sheet '" + CodeList.ActivitySubCategory + "')");
			validationResult = false;
		}
		
		if (!validationResult) {
			throw new RuntimeException("Validation of sheet " + factorsSheet.getName() + " and/or " + indicatorsSheet.getName() + " failed!");
		}
		verifyUnitExist(unitsSymbols);
	}

	private <T extends Code> List<String> verifyCodeExist(Set<String> identifiers, Map<String, String> keyByLabelMap) {
		List<String> unknownsIdentifiers = new ArrayList<>();
		for (String identifier : identifiers) {
			if (!keyByLabelMap.containsKey(identifier)) {
				unknownsIdentifiers.add(identifier);
			}
		}
		return unknownsIdentifiers;
	}

	private void verifyUnitExist(Set<String> unitSymbols) {
		Set<String> unknownUnitSymbols = new HashSet<>();
		for (String unitSymbol : unitSymbols) {
			if (!knownUnits.containsKey(unitSymbol)) {
				unknownUnitSymbols.add(unitSymbol);
			}
		}
		if (!unknownUnitSymbols.isEmpty()) {
			System.out.println("These unit symbols cannot be associated to any known units: '" + unknownUnitSymbols + "'");
		}
	}


	private void saveFactors(Sheet factorsSheet) {
		List<Factor> factors = new ArrayList<>();
		List<FactorValue> factorValues = new ArrayList<>();
		for (int i = 1; i < factorsSheet.getRows(); i++) {
			String indicatorKey = getCellContent(factorsSheet, 0, i);
			String activityTypeKey = getCellContent(factorsSheet, 1, i);
			String activitySourceKey = getCellContent(factorsSheet, 2, i);
			Unit unitIn = knownUnits.get(getCellContent(factorsSheet, 3, i));
			String institution = getCellContent(factorsSheet, 4, i);
			Double value = getNumericCellContent(factorsSheet, 5, i);
			Unit unitOut = knownUnits.get(getCellContent(factorsSheet, 6, i));

			IndicatorCategoryCode indicatorCategory = new IndicatorCategoryCode(indicatorKey);
			ActivityTypeCode activityType = new ActivityTypeCode(activityTypeKey);
			ActivitySourceCode activitySource = new ActivitySourceCode(activitySourceKey);
			Factor factor = new Factor(indicatorCategory, activityType, activitySource, unitIn, unitOut, institution);
			factors.add(factor);
			if (value != null) {
				factorValues.add(new FactorValue(value, new Date(), null, factor));
			}
		}
		persistEntities(factors);
		persistEntities(factorValues);
	}

	private void saveIndicators(Sheet indicatorsSheet) {
		List<Indicator> indicators = new ArrayList<>();
		Unit unit = knownUnits.get("tCO2e");
		for (int i = 1; i < indicatorsSheet.getRows(); i++) {
			String name = getCellContent(indicatorsSheet, 1, i);
			String indicatorIsoScopeKey = getCellContent(indicatorsSheet, 3, i);
			String indicatorCategoryKey = getCellContent(indicatorsSheet, 4, i);
			String activityCategoryKey = getCellContent(indicatorsSheet, 6, i);
			String activitySubCategoryKey = getCellContent(indicatorsSheet, 7, i);
			String strActivityOwnership = getCellContent(indicatorsSheet, 10, i);
			Boolean activityOwnership = null;
			if ("1".equals(strActivityOwnership)) {
				activityOwnership = Boolean.TRUE;
			} else if ("0".equals(strActivityOwnership)) {
				activityOwnership = Boolean.FALSE;
			}
			Boolean deleted = indicatorsSheet.getCell(1, i).getCellFormat().getFont().isStruckout();

			IndicatorIsoScopeCode isoScope = new IndicatorIsoScopeCode(indicatorIsoScopeKey);
			IndicatorCategoryCode indicatorCategory = new IndicatorCategoryCode(indicatorCategoryKey);
			ActivityCategoryCode activityCategory = new ActivityCategoryCode(activityCategoryKey);
			ActivitySubCategoryCode activitySubCategory = new ActivitySubCategoryCode(activitySubCategoryKey);
			indicators.add(new Indicator(name, IndicatorTypeCode.CARBON, ScopeTypeCode.SITE, isoScope, indicatorCategory, activityCategory,
					activitySubCategory, activityOwnership, unit, deleted));
		}
		persistEntities(indicators);
	}

	private List<Unit> findAllUnits() {
		return JPA.em().createNamedQuery(Unit.FIND_ALL, Unit.class).getResultList();
	}

	/**
	 * Return all labels of the given codeList
	 * @param codeList
	 * @return a map <label, key>
	 */
	private Map<String, String> findCodesLabelsByCodeList(CodeList codeList) {

		List<CodeLabel> codeLabels = JPA.em().createNamedQuery(CodeLabel.FIND_BY_LIST, CodeLabel.class).setParameter("codeList", codeList).getResultList();

		Map<String, String> res = new HashMap<>();
		for (CodeLabel codeLabel : codeLabels) {
			res.put(codeLabel.getLabelFr(), codeLabel.getKey());
		}
		return res;
	}
}
