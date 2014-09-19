package eu.factorx.awac.util.data.importer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import play.Logger;
import play.db.jpa.JPA;
import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.code.label.CodeLabel;
import eu.factorx.awac.models.code.type.*;
import eu.factorx.awac.models.forms.AwacCalculator;
import eu.factorx.awac.models.knowledge.*;
import eu.factorx.awac.service.*;

@Component
public class IndicatorImporter extends WorkbookDataImporter {

	public static final String AWAC_DATA_WORKBOOK_PATH = "data_importer_resources/awac_data_09-08-2014/AWAC-tous-calcul_FE.xls";

	public static final String ENTERPRISE_BASE_INDICATORS_REFERENCE = "BaseIndicators:A1";
	public static final String ENTERPRISE_INDICATORS_REFERENCE = "Indicators:A1";
	public static final String ENTERPRISE_BASE_INDICATOR_INDICATORS_REFERENCE = "Indicators:F1";
	public static final String ENTERPRISE_REPORTS_REFERENCE = "Reports:A1";
	public static final String ENTERPRISE_INDICATOR_REPORTS_REFERENCE = "Reports:F1";

	public static final String MUNICIPALITY_BASE_INDICATORS_REFERENCE = "BaseIndicators-commune:A1";
	public static final String MUNICIPALITY_INDICATORS_REFERENCE = "Indicators-commune:A1";
	public static final String MUNICIPALITY_BASE_INDICATOR_INDICATORS_REFERENCE = "Indicators-commune:F1";
	public static final String MUNICIPALITY_REPORTS_REFERENCE = "Reports-commune:A1";
	public static final String MUNICIPALITY_INDICATOR_REPORTS_REFERENCE = "Reports-commune:F1";

	private Map<String, Sheet> sheets;
	private Map<String, Unit> units;

	@Autowired
	private UnitService unitService;

	@Autowired
	private BaseIndicatorService baseIndicatorService;

	@Autowired
	private IndicatorService indicatorService;

	@Autowired
	private ReportService reportService;

	@Autowired
	private ReportIndicatorService reportIndicatorService;

	@Autowired
	private AwacCalculatorService awacCalculatorService;

	@Autowired
	private CodeLabelService codeLabelService;

	private List<String> allIndicatorCategoryKeys = null;
	private List<String> allActivityCategoryKeys = null;
	private List<String> allActivitySubCategoryKeys = null;

	@Override
	protected void importData() throws Exception {

		// first delete all reports, indicators, base indicators, and indicators labels
		reportService.removeAll();
		indicatorService.removeAll();
		baseIndicatorService.removeAll();
		codeLabelService.removeCodeLabelsByList(CodeList.INDICATOR);

		// sheets
		this.sheets = getWorkbookSheets(AWAC_DATA_WORKBOOK_PATH);

		// units
		List<Unit> units = unitService.findAll();
		this.units = new HashMap<>();
		for (Unit unit : units) {
			this.units.put(unit.getUnitCode().getKey(), unit);
		}

		// existing code labels (for validation)
		allIndicatorCategoryKeys = findAllCodeKeys(CodeList.IndicatorCategory);
		allActivityCategoryKeys = findAllCodeKeys(CodeList.ActivityCategory);
		allActivitySubCategoryKeys = findAllCodeKeys(CodeList.ActivitySubCategory);

		// ENTERPRISE DATA
		Logger.info("== Importing Enterprise Data");
		importData(InterfaceTypeCode.ENTERPRISE,
				ENTERPRISE_BASE_INDICATORS_REFERENCE,
				ENTERPRISE_INDICATORS_REFERENCE,
				ENTERPRISE_BASE_INDICATOR_INDICATORS_REFERENCE,
				ENTERPRISE_REPORTS_REFERENCE,
				ENTERPRISE_INDICATOR_REPORTS_REFERENCE);

		// MUNICIPALITY DATA
		Logger.info("== Importing Municipality Data");
		importData(InterfaceTypeCode.MUNICIPALITY,
				MUNICIPALITY_BASE_INDICATORS_REFERENCE,
				MUNICIPALITY_INDICATORS_REFERENCE,
				MUNICIPALITY_BASE_INDICATOR_INDICATORS_REFERENCE,
				MUNICIPALITY_REPORTS_REFERENCE,
				MUNICIPALITY_INDICATOR_REPORTS_REFERENCE);

		codeLabelService.resetCache();
	}

	private void importData(InterfaceTypeCode interfaceTypeCode, String baseIndicatorsReference, String indicatorsReference, String baseIndicatorIndicatorsReference,
			String reportsReference, String indicatorReportsReference) {
		// 1. Establish a list of all new BaseIndicators
		List<BaseIndicator> baseIndicators = loadBaseIndicators(baseIndicatorsReference);

		// 2. Establish a list of all new Indicators
		List<CodeLabel> codeLabelsToAdd = new ArrayList<>();
		List<Indicator> indicators = loadIndicators(indicatorsReference, codeLabelsToAdd);

		// 3. Establish a list of all new links between BaseIndicators and Indicators
		List<Pair<BaseIndicator, Indicator>> baseIndicatorIndicators = loadBaseIndicatorIndicators(baseIndicatorIndicatorsReference, baseIndicators, indicators);

		// 4. Establish a list of all new Reports
		AwacCalculator awacCalculator = awacCalculatorService.findByCode(interfaceTypeCode);
		if (awacCalculator == null) {
			awacCalculator = awacCalculatorService.saveOrUpdate(new AwacCalculator(interfaceTypeCode));
		}
		List<Report> reports = loadReports(reportsReference, awacCalculator);

		// 5. Establish a list of all new links between Indicators and Reports
		List<ReportIndicator> reportIndicators = loadIndicatorReports(indicatorReportsReference, indicators, reports);

		// 6. Persist entities
		// 6.1 base indicators
		for (BaseIndicator baseIndicator : baseIndicators) {
			baseIndicatorService.saveOrUpdate(baseIndicator);
		}
		Logger.info("==== Imported {} BaseIndicators for '{}' calculator", baseIndicators.size(), interfaceTypeCode.getKey());

		// 6.2 indicators
		for (Indicator indicator : indicators) {
			indicatorService.saveOrUpdate(indicator);
		}
		Logger.info("==== Imported {} Indicators for '{}' calculator", indicators.size(), interfaceTypeCode.getKey());

		// 6.3 BaseIndicator Indicators
		for (Pair<BaseIndicator, Indicator> baseIndicatorIndicator : baseIndicatorIndicators) {
			Indicator indicator = baseIndicatorIndicator.getRight();
			BaseIndicator baseIndicator = baseIndicatorIndicator.getLeft();
			indicator.getBaseIndicators().add(baseIndicator);
			indicatorService.saveOrUpdate(indicator);
		}
		Logger.info("==== Imported {} BaseIndicator-Indicator associations for '{}' calculator", baseIndicatorIndicators.size(), interfaceTypeCode.getKey());

		// 6.4 reports
		for (Report report : reports) {
			reportService.saveOrUpdate(report);
		}
		Logger.info("==== Imported {} Reports for '{}' calculator", reports.size(), interfaceTypeCode.getKey());

		// 6.5 IndicatorReports
		for (ReportIndicator reportIndicator : reportIndicators) {
			reportIndicatorService.saveOrUpdate(reportIndicator);
		}
		Logger.info("==== Imported {} Report-Indicator associations for '{}' calculator", reportIndicators.size(), interfaceTypeCode.getKey());

		// 6.6 Indicator labels
		for (CodeLabel codeLabel : codeLabelsToAdd) {
			codeLabelService.saveOrUpdate(codeLabel);
		}
		Logger.info("==== Imported {} Indicators labels for '{}' calculator", codeLabelsToAdd.size(), interfaceTypeCode.getKey());
		
		// 4. Verify that each baseIndicator has at least one Indicator, just checking
		// TODO: verifyNoOrphans(...);

		// 5.
	}

	//
	// Steps
	//
	private List<BaseIndicator> loadBaseIndicators(String reference) {
		Sheet sheet = getSheet(reference);
		Cell first = getReferenceCell(reference);
		int firstColumn = first.getColumn();
		int firstRow = first.getRow();
		List<BaseIndicator> baseIndicators = new ArrayList<>();

		// indices
		int KEY_INDEX = 1;
		int INDICATORSCOPE_INDEX = 4;
		int INDICATORCATEGORY_KEY_INDEX = 5;
		int ACTIVITYCATEGORY_KEY_INDEX = 8;
		int ACTIVITYSUBCATEGORY_KEY_INDEX = 10;
		int ACTIVITYOWNERSHIP_INDEX = 14;

		Unit unit = units.get("U5331");
		Integer deletedIndicators = 0;

		// read until end of table
		for (int i = 1; i < sheet.getRows(); i++) {
			// read
			String key = getCellContent(sheet, firstColumn + KEY_INDEX, firstRow + i);
			if (StringUtils.isBlank(key)) {
				break;
			}

			String indicatorScope = getCellContent(sheet, firstColumn + INDICATORSCOPE_INDEX, firstRow + i);

			String indicatorCategoryKey = getCellContent(sheet, firstColumn + INDICATORCATEGORY_KEY_INDEX, firstRow + i);
			if (!allIndicatorCategoryKeys.contains(indicatorCategoryKey)) {
				Logger.error("Cannot import baseIndicator '{}': Unknown IndicatorCategory key '{}'!", key, indicatorCategoryKey);
				continue;
			}

			String activityCategoryKey = getCellContent(sheet, firstColumn + ACTIVITYCATEGORY_KEY_INDEX, firstRow + i);
			if (!allActivityCategoryKeys.contains(activityCategoryKey)) {
				Logger.error("Cannot import baseIndicator '{}': Unknown ActivityCategory key '{}'!", key, activityCategoryKey);
				continue;
			}

			String activitySubCategoryKey = getCellContent(sheet, firstColumn + ACTIVITYSUBCATEGORY_KEY_INDEX, firstRow + i);
			if (!allActivitySubCategoryKeys.contains(activitySubCategoryKey)) {
				Logger.error("Cannot import baseIndicator '{}': Unknown ActivitySubCategory key '{}'!", key, activitySubCategoryKey);
				continue;
			}

			String activityOwnership = getCellContent(sheet, firstColumn + ACTIVITYOWNERSHIP_INDEX, firstRow + i);
			Boolean activityOwnershipBoolean = null;
			if ("1".equals(activityOwnership)) {
				activityOwnershipBoolean = Boolean.TRUE;
			} else if ("0".equals(activityOwnership)) {
				activityOwnershipBoolean = Boolean.FALSE;
			}

			Boolean deleted = sheet.getCell(firstColumn, i).getCellFormat().getFont().isStruckout();
			if (deleted) {
				deletedIndicators++;
			}

			BaseIndicator baseIndicator = new BaseIndicator(new BaseIndicatorCode(key), IndicatorTypeCode.CARBON, ScopeTypeCode.SITE, new IndicatorIsoScopeCode(indicatorScope),
					new IndicatorCategoryCode(indicatorCategoryKey), new ActivityCategoryCode(activityCategoryKey),
					new ActivitySubCategoryCode(activitySubCategoryKey), activityOwnershipBoolean, unit, deleted);

			baseIndicators.add(baseIndicator);
		}

		return baseIndicators;
	}

	private List<Indicator> loadIndicators(String reference, List<CodeLabel> codeLabelsToAdd) {
		Sheet sheet = getSheet(reference);
		Cell first = getReferenceCell(reference);
		int firstColumn = first.getColumn();
		int firstRow = first.getRow();
		List<Indicator> indicators = new ArrayList<>();

		// indices
		int INDICATORTYPE_INDEX = 0;
		int KEY_INDEX = 1;
		int NAME_INDEX = 2;
		int ORGSCOPE_INDEX = 3;

		// read until end of table
		for (int i = 1; i < sheet.getRows(); i++) {
			// read
			String indicatorType = getCellContent(sheet, firstColumn + INDICATORTYPE_INDEX, firstRow + i);
			String key = getCellContent(sheet, firstColumn + KEY_INDEX, firstRow + i);
			String name = getCellContent(sheet, firstColumn + NAME_INDEX, firstRow + i);
			String orgScope = getCellContent(sheet, firstColumn + ORGSCOPE_INDEX, firstRow + i);

			if (StringUtils.isBlank(indicatorType) ||
					StringUtils.isBlank(key) ||
					StringUtils.isBlank(name) ||
					StringUtils.isBlank(orgScope))
				break;

			// BI
			Indicator indicator = new Indicator(new IndicatorCode(key));

			indicators.add(indicator);
			codeLabelsToAdd.add(new CodeLabel(CodeList.INDICATOR, key, name, name, name));
		}

		return indicators;
	}

	private List<Pair<BaseIndicator, Indicator>> loadBaseIndicatorIndicators(String reference, List<BaseIndicator> baseIndicators, List<Indicator> indicators) {
		Sheet sheet = getSheet(reference);
		Cell first = getReferenceCell(reference);
		int firstColumn = first.getColumn();
		int firstRow = first.getRow();
		List<Pair<BaseIndicator, Indicator>> relations = new ArrayList<>();

		// indices
		int INDICATOR_KEY_INDEX = 0;
		int BASE_INDICATOR_KEY_INDEX = 2;

		// read until end of table
		for (int i = 1; i < sheet.getRows(); i++) {
			// read
			String indicatorKey = getCellContent(sheet, firstColumn + INDICATOR_KEY_INDEX, firstRow + i);
			String baseIndicatorKey = getCellContent(sheet, firstColumn + BASE_INDICATOR_KEY_INDEX, firstRow + i);

			if (StringUtils.isBlank(indicatorKey) || StringUtils.isBlank(baseIndicatorKey)) {
				break;
			}

			BaseIndicator baseIndicator = findBaseIndicatorByKey(baseIndicators, baseIndicatorKey);
			Indicator indicator = findIndicatorByKey(indicators, indicatorKey);

			relations.add(Pair.of(baseIndicator, indicator));
		}

		return relations;
	}

	private List<Report> loadReports(String reference, AwacCalculator awacCalculator) {
		Sheet sheet = getSheet(reference);
		Cell first = getReferenceCell(reference);
		int firstColumn = first.getColumn();
		int firstRow = first.getRow();
		List<Report> reports = new ArrayList<>();

		// indices
		int KEY_INDEX = 0;
		int RESTRICTED_SCOPE_INDEX = 3;

		// read until end of table
		for (int i = 1; i < sheet.getRows(); i++) {
			// read
			String key = getCellContent(sheet, firstColumn + KEY_INDEX, firstRow + i);
			String restrictedScope = getCellContent(sheet, firstColumn + RESTRICTED_SCOPE_INDEX, firstRow + i);

			if (StringUtils.isBlank(key))
				break;

			Report report;
			if (StringUtils.isNotBlank(restrictedScope)) {
				report = new Report(new ReportCode(key), awacCalculator, new IndicatorIsoScopeCode(restrictedScope));
			} else {
				report = new Report(new ReportCode(key), awacCalculator, null);
			}

			reports.add(report);
		}

		return reports;
	}

	private List<ReportIndicator> loadIndicatorReports(String reference, List<Indicator> indicators, List<Report> reports) {
		Sheet sheet = getSheet(reference);
		Cell first = getReferenceCell(reference);
		int firstColumn = first.getColumn();
		int firstRow = first.getRow();
		List<ReportIndicator> relations = new ArrayList<>();

		// indices
		int REPORT_KEY_INDEX = 0;
		int INDICATOR_KEY_INDEX = 1;
		int ORDER_INDEX = 3;

		// read until end of table
		for (int i = 1; i < sheet.getRows(); i++) {
			// read
			String reportKey = getCellContent(sheet, firstColumn + REPORT_KEY_INDEX, firstRow + i);
			String indicatorKey = getCellContent(sheet, firstColumn + INDICATOR_KEY_INDEX, firstRow + i);
			String order = getCellContent(sheet, firstColumn + ORDER_INDEX, firstRow + i);

			if (StringUtils.isBlank(reportKey))
				break;

			Indicator indicator = findIndicatorByKey(indicators, indicatorKey);
			Report report = findReportByCode(reports, reportKey);

			ReportIndicator reportIndicator = new ReportIndicator(report, indicator, Integer.valueOf(order));

			relations.add(reportIndicator);
		}

		return relations;
	}

	//
	// Utils
	//
	private Sheet getSheet(String reference) {
		return sheets.get(reference.split(":")[0]);
	}

	private Cell getReferenceCell(String reference) {
		return sheets.get(reference.split(":")[0]).getCell(reference.split(":")[1]);
	}

	private BaseIndicator findBaseIndicatorByKey(List<BaseIndicator> baseIndicators, String baseIndicatorKey) {
		for (BaseIndicator baseIndicator : baseIndicators) {
			if (StringUtils.equalsIgnoreCase(baseIndicator.getCode().getKey(), baseIndicatorKey)) {
				return baseIndicator;
			}
		}
		throw new IllegalArgumentException("BaseIndicator '{" + baseIndicatorKey + "}'  was not found!");
	}

	private Indicator findIndicatorByKey(List<Indicator> indicators, String indicatorKey) {
		for (Indicator indicator : indicators) {
			if (StringUtils.equalsIgnoreCase(indicator.getCode().getKey(), indicatorKey)) {
				return indicator;
			}
		}
		throw new IllegalArgumentException("Indicator '{" + indicatorKey + "}'  was not found!");
	}

	private Report findReportByCode(List<Report> reports, String reportKey) {
		for (Report report : reports) {
			if (StringUtils.equalsIgnoreCase(report.getCode().getKey(), reportKey)) {
				return report;
			}
		}
		throw new IllegalArgumentException("Report '" + reportKey + "'  was not found!");
	}

	private List<String> findAllCodeKeys(CodeList codeList) {
		return JPA.em().createNamedQuery(CodeLabel.FIND_KEYS_BY_LIST, String.class).setParameter("codeList", codeList).getResultList();
	}

}
