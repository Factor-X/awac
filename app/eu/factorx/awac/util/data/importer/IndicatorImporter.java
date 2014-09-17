package eu.factorx.awac.util.data.importer;

import eu.factorx.awac.models.code.type.*;
import eu.factorx.awac.models.knowledge.*;
import eu.factorx.awac.service.BaseIndicatorService;
import eu.factorx.awac.service.IndicatorService;
import eu.factorx.awac.service.ReportService;
import eu.factorx.awac.service.UnitService;
import eu.factorx.awac.util.Tuple;
import jxl.Cell;
import jxl.Sheet;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import play.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class IndicatorImporter extends WorkbookDataImporter {

	public static final String AWAC_DATA_WORKBOOK_PATH = "data_importer_resources/awac_data_09-08-2014/AWAC-tous-calcul_FE.xls";

	public static final String ENTERPRISE_BASE_INDICATORS_REFERENCE           = "BaseIndicators:A1";
	public static final String ENTERPRISE_INDICATORS_REFERENCE                = "Indicators:A1";
	public static final String ENTERPRISE_BASE_INDICATOR_INDICATORS_REFERENCE = "Indicators:F1";
	public static final String ENTERPRISE_REPORTS_REFERENCE                   = "Reports:A1";
	public static final String ENTERPRISE_INDICATOR_REPORTS_REFERENCE         = "Reports:F1";

	private Map<String, Sheet> sheets;
	private Map<String, Unit>  units;

	@Autowired
	private UnitService unitService;

	@Autowired
	private BaseIndicatorService baseIndicatorService;

	@Autowired
	private IndicatorService indicatorService;

	@Autowired
	private ReportService reportService;


	@Override
	protected void importData() throws Exception {

		// sheets
		this.sheets = getWorkbookSheets(AWAC_DATA_WORKBOOK_PATH);

		// units
		List<Unit> units = unitService.findAll();
		this.units = new HashMap<>();
		for (Unit unit : units) {
			this.units.put(unit.getUnitCode().getKey(), unit);
		}

		// 1. Establish a list of all new BaseIndicators
		List<BaseIndicator> baseIndicators = loadBaseIndicators();
		Logger.info("== BASE INDICATORS");
		for (BaseIndicator baseIndicator : baseIndicators) {
			Logger.info(baseIndicator.toString());
		}

		// 2. Establish a list of all new Indicators
		List<Indicator> indicators = loadIndicators();
		Logger.info("== INDICATORS");
		for (Indicator indicator : indicators) {
			Logger.info(indicator.toString());
		}

		// 3. Establish a list of all new links between BaseIndicators and Indicators
		List<Tuple<BaseIndicator, Indicator>> baseIndicatorIndicators = loadBaseIndicatorIndicators(baseIndicators, indicators);
		Logger.info("== BASE INDICATORS - INDICATORS");
		for (Tuple<BaseIndicator, Indicator> baseIndicatorIndicator : baseIndicatorIndicators) {
			Logger.info(baseIndicatorIndicator.toString());
		}

		// 4. Establish a list of all new Reports
		List<Report> reports = loadReports();
		Logger.info("== REPORTS");
		for (Report report : reports) {
			Logger.info(report.toString());
		}

		// 5. Establish a list of all new links between Indicators and Reports
		List<ReportIndicator> indicatorReports = loadIndicatorReports(indicators, reports);
		Logger.info("== INDICATOR - REPORT");
		for (ReportIndicator indicatorReport : indicatorReports) {
			Logger.info(indicatorReport.toString());
		}

		// 6. Persist entities
		// 6.1 base indicators
		for (BaseIndicator baseIndicator : baseIndicators) {

		}


		// 4. Verify that each baseIndicator has at least one Indicator, just checking
		// TODO: verifyNoOrphans(...);

		// 5.

	}

	//
	// Steps
	//
	private List<BaseIndicator> loadBaseIndicators() {
		String reference = ENTERPRISE_BASE_INDICATORS_REFERENCE;
		Sheet sheet = getSheet(reference);
		Cell first = getReferenceCell(reference);
		int firstColumn = first.getColumn();
		int firstRow = first.getRow();
		List<BaseIndicator> baseIndicators = new ArrayList<>();
		int i = 1;

		// indices
		int INDICATORTYPE_INDEX = 0;
		int KEY_INDEX = 1;
		int NAME_INDEX = 2;
		int ORGSCOPE_INDEX = 3;
		int INDICATORSCOPE_INDEX = 4;
		int INDICATORCATEGORY_KEY_INDEX = 5;
		int UNIT_INDEX = 7;
		int ACTIVITYCATEGORY_KEY_INDEX = 8;
		int ACTIVITYSUBCATEGORY_KEY_INDEX = 10;
		int ACTIVITYTYPE_KEY_INDEX = 12;
		int ACTIVITYSOURCE_KEY_INDEX = 13;
		int ACTIVITYOWNERSHIP_INDEX = 14;
		int ACTIVITYOPERATIONALCONTROL_INDEX = 15;
		int ACTIVITYPAIDFOR_INDEX = 16;

		// read until end of table
		while (firstRow + i < sheet.getRows()) {
			// read
			String indicatorType = getCellContent(sheet, firstColumn + INDICATORTYPE_INDEX, firstRow + i);
			String key = getCellContent(sheet, firstColumn + KEY_INDEX, firstRow + i);
			String name = getCellContent(sheet, firstColumn + NAME_INDEX, firstRow + i);
			String orgScope = getCellContent(sheet, firstColumn + ORGSCOPE_INDEX, firstRow + i);
			String indicatorScope = getCellContent(sheet, firstColumn + INDICATORSCOPE_INDEX, firstRow + i);
			String indicatorCategoryKey = getCellContent(sheet, firstColumn + INDICATORCATEGORY_KEY_INDEX, firstRow + i);
			String unit = getCellContent(sheet, firstColumn + UNIT_INDEX, firstRow + i);
			String activityCategoryKey = getCellContent(sheet, firstColumn + ACTIVITYCATEGORY_KEY_INDEX, firstRow + i);
			String activitySubCategoryKey = getCellContent(sheet, firstColumn + ACTIVITYSUBCATEGORY_KEY_INDEX, firstRow + i);
			String activityTypeKey = getCellContent(sheet, firstColumn + ACTIVITYTYPE_KEY_INDEX, firstRow + i); // ignored
			String activitySourceKey = getCellContent(sheet, firstColumn + ACTIVITYSOURCE_KEY_INDEX, firstRow + i); // ignored
			String activityOwnership = getCellContent(sheet, firstColumn + ACTIVITYOWNERSHIP_INDEX, firstRow + i);
			String activityOperationalControl = getCellContent(sheet, firstColumn + ACTIVITYOPERATIONALCONTROL_INDEX, firstRow + i); // ignored
			String activityPaidFor = getCellContent(sheet, firstColumn + ACTIVITYPAIDFOR_INDEX, firstRow + i); // ignored
			Boolean deleted = sheet.getCell(firstColumn, i).getCellFormat().getFont().isStruckout();

			if (StringUtils.isBlank(indicatorType) ||
				StringUtils.isBlank(key) ||
				StringUtils.isBlank(name) ||
				StringUtils.isBlank(orgScope) ||
				StringUtils.isBlank(indicatorScope) ||
				StringUtils.isBlank(indicatorCategoryKey) ||
				StringUtils.isBlank(unit) ||
				StringUtils.isBlank(activityCategoryKey) ||
				StringUtils.isBlank(activitySubCategoryKey)
				) break;


			Boolean activityOwnershipBoolean = null;
			if (activityOwnership != null) {
				activityOwnershipBoolean = activityOwnership.trim().equals("1");
			}


			// BI
			BaseIndicator baseIndicator = new BaseIndicator(
				key,
				name,
				new IndicatorTypeCode(indicatorType),
				new ScopeTypeCode(orgScope),
				new IndicatorIsoScopeCode(indicatorScope),
				new IndicatorCategoryCode(indicatorCategoryKey),
				new ActivityCategoryCode(activityCategoryKey),
				new ActivitySubCategoryCode(activitySubCategoryKey),
				activityOwnershipBoolean,
				units.get(unit),
				deleted);

			baseIndicators.add(baseIndicator);
			i++;
		}

		return baseIndicators;
	}

	private List<Indicator> loadIndicators() {
		String reference = ENTERPRISE_INDICATORS_REFERENCE;
		Sheet sheet = getSheet(reference);
		Cell first = getReferenceCell(reference);
		int firstColumn = first.getColumn();
		int firstRow = first.getRow();
		List<Indicator> indicators = new ArrayList<>();
		int i = 1;

		// indices
		int INDICATORTYPE_INDEX = 0;
		int KEY_INDEX = 1;
		int NAME_INDEX = 2;
		int ORGSCOPE_INDEX = 3;

		// read until end of table
		while (firstRow + i < sheet.getRows()) {
			// read
			String indicatorType = getCellContent(sheet, firstColumn + INDICATORTYPE_INDEX, firstRow + i);
			String key = getCellContent(sheet, firstColumn + KEY_INDEX, firstRow + i);
			String name = getCellContent(sheet, firstColumn + NAME_INDEX, firstRow + i);
			String orgScope = getCellContent(sheet, firstColumn + ORGSCOPE_INDEX, firstRow + i);

			if (StringUtils.isBlank(indicatorType) ||
				StringUtils.isBlank(key) ||
				StringUtils.isBlank(name) ||
				StringUtils.isBlank(orgScope)
				) break;

			// BI
			Indicator indicator = new Indicator(new IndicatorCode(key));

			indicators.add(indicator);
			i++;
		}

		return indicators;
	}

	private List<Tuple<BaseIndicator, Indicator>> loadBaseIndicatorIndicators(List<BaseIndicator> baseIndicators, List<Indicator> indicators) {
		String reference = ENTERPRISE_BASE_INDICATOR_INDICATORS_REFERENCE;
		Sheet sheet = getSheet(reference);
		Cell first = getReferenceCell(reference);
		int firstColumn = first.getColumn();
		int firstRow = first.getRow();
		List<Tuple<BaseIndicator, Indicator>> relations = new ArrayList<>();
		int i = 1;

		// indices
		int INDICATOR_KEY_INDEX = 0;
		int BASE_INDICATOR_KEY_INDEX = 2;

		// read until end of table
		while (firstRow + i < sheet.getRows()) {
			// read
			String indicatorCode = getCellContent(sheet, firstColumn + INDICATOR_KEY_INDEX, firstRow + i);
			String baseIndicatorKey = getCellContent(sheet, firstColumn + BASE_INDICATOR_KEY_INDEX, firstRow + i);

			if (StringUtils.isBlank(indicatorCode) ||
				StringUtils.isBlank(baseIndicatorKey)
				) break;

			BaseIndicator baseIndicator = findBaseIndicatorByKey(baseIndicators, baseIndicatorKey);
			Indicator indicator = findIndicatorByCode(indicators, indicatorCode);

			relations.add(new Tuple<>(baseIndicator, indicator));
			i++;
		}

		return relations;
	}

	private List<Report> loadReports() {
		String reference = ENTERPRISE_REPORTS_REFERENCE;
		Sheet sheet = getSheet(reference);
		Cell first = getReferenceCell(reference);
		int firstColumn = first.getColumn();
		int firstRow = first.getRow();
		List<Report> reports = new ArrayList<>();
		int i = 1;

		// indices
		int INDICATORTYPE_INDEX = 0;
		int KEY_INDEX = 1;
		int ORGSCOPE_INDEX = 2;
		int RESTRICTED_SCOPE_INDEX = 3;

		// read until end of table
		while (firstRow + i < sheet.getRows()) {
			// read
			String indicatorType = getCellContent(sheet, firstColumn + INDICATORTYPE_INDEX, firstRow + i);
			String key = getCellContent(sheet, firstColumn + KEY_INDEX, firstRow + i);
			String orgScope = getCellContent(sheet, firstColumn + ORGSCOPE_INDEX, firstRow + i);
			String restrictedScope = getCellContent(sheet, firstColumn + RESTRICTED_SCOPE_INDEX, firstRow + i);

			if (StringUtils.isBlank(indicatorType) ||
				StringUtils.isBlank(key) ||
				StringUtils.isBlank(orgScope)
				) break;

			Report report = new Report(new ReportCode(key), new IndicatorIsoScopeCode(orgScope.toUpperCase()));
			if (StringUtils.isNotBlank(restrictedScope)) {
				report.setRestrictedScope(new IndicatorIsoScopeCode(restrictedScope.toUpperCase()));
			}

			reports.add(report);
			i++;
		}

		return reports;
	}

	private List<ReportIndicator> loadIndicatorReports(List<Indicator> indicators, List<Report> reports) {
		String reference = ENTERPRISE_INDICATOR_REPORTS_REFERENCE;
		Sheet sheet = getSheet(reference);
		Cell first = getReferenceCell(reference);
		int firstColumn = first.getColumn();
		int firstRow = first.getRow();
		List<ReportIndicator> relations = new ArrayList<>();
		int i = 1;

		// indices
		int REPORT_KEY_INDEX = 0;
		int INDICATOR_KEY_INDEX = 1;
		int ORDER_INDEX = 3;

		// read until end of table
		while (firstRow + i < sheet.getRows()) {
			// read
			String reportKey = getCellContent(sheet, firstColumn + REPORT_KEY_INDEX, firstRow + i);
			String indicatorKey = getCellContent(sheet, firstColumn + INDICATOR_KEY_INDEX, firstRow + i);
			String order = getCellContent(sheet, firstColumn + ORDER_INDEX, firstRow + i);

			if (StringUtils.isBlank(reportKey) ||
				StringUtils.isBlank(indicatorKey) ||
				StringUtils.isBlank(order)
				) break;

			Indicator indicator = findIndicatorByCode(indicators, indicatorKey);
			Report report = findReportByCode(reports, reportKey);

			ReportIndicator reportIndicator = new ReportIndicator(report, indicator, Integer.valueOf(order));

			relations.add(reportIndicator);
			i++;
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
			if (baseIndicator.getKey().equals(baseIndicatorKey)) {
				return baseIndicator;
			}
		}
		throw new IllegalArgumentException("Base indicator not found with key: " + baseIndicatorKey);
	}

	private Indicator findIndicatorByCode(List<Indicator> indicators, String indicatorCode) {
		for (Indicator indicator : indicators) {
			if (indicator.getCode().getKey().equals(indicatorCode)) {
				return indicator;
			}
		}
		throw new IllegalArgumentException("Indicator not found with code: " + indicatorCode);
	}

	private Report findReportByCode(List<Report> reports, String reportKey) {
		for (Report report : reports) {
			if (report.getCode().getKey().equals(reportKey)) {
				return report;
			}
		}
		throw new IllegalArgumentException("Report not found with code: " + reportKey);
	}

}
