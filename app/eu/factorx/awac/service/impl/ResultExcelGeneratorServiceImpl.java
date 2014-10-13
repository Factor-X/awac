package eu.factorx.awac.service.impl;

import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.code.label.CodeLabel;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.models.code.type.LanguageCode;
import eu.factorx.awac.models.forms.AwacCalculator;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.models.reporting.ReportResult;
import eu.factorx.awac.service.AwacCalculatorService;
import eu.factorx.awac.service.CodeLabelService;
import eu.factorx.awac.service.ReportResultService;
import eu.factorx.awac.service.ResultExcelGeneratorService;
import eu.factorx.awac.service.impl.reporting.*;
import eu.factorx.awac.util.Table;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;
import jxl.write.*;
import jxl.write.Number;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Component
public class ResultExcelGeneratorServiceImpl implements ResultExcelGeneratorService {

	@Autowired
	private AwacCalculatorService awacCalculatorService;
	@Autowired
	private ReportResultService   reportResultService;
	@Autowired
	private CodeLabelService      codeLabelService;

	@Override
	public byte[] generateExcelInStream(LanguageCode lang, List<Scope> scopes, Period period, InterfaceTypeCode interfaceCode) throws IOException, WriteException, BiffException {

		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

		WorkbookSettings wbSettings = new WorkbookSettings();
		wbSettings.setLocale(new Locale(lang.getKey()));

		WritableWorkbook wb = Workbook.createWorkbook(byteArrayOutputStream, wbSettings);

		// 1. Compute the ReportResult
		AwacCalculator awacCalculator = awacCalculatorService.findByCode(interfaceCode);
		ReportResultCollection allReportResults = reportResultService.getReportResults(awacCalculator, scopes, period);

		// Create cell font and format
		WritableFont cellFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);

		WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
		cellFormat.setAlignment(Alignment.CENTRE);
		cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);

		// 2. Populate the DTO
		for (ReportResult reportResult : allReportResults.getReportResults()) {
			String reportKey = reportResult.getReport().getCode().getKey();

			if (reportKey.equals("R_1")) {

				WritableSheet sheet = wb.createSheet(reportKey, wb.getNumberOfSheets());

				Map<String, List<Double>> scopeValuesByIndicator = reportResult.getScopeValuesByIndicator();

				sheet.addCell(new Label(0, 0, "Indicator", cellFormat));
				sheet.addCell(new Label(1, 0, "Scope 1", cellFormat));
				sheet.addCell(new Label(2, 0, "Scope 2", cellFormat));
				sheet.addCell(new Label(3, 0, "Scope 3", cellFormat));
				sheet.addCell(new Label(4, 0, "Out of scope", cellFormat));

				int index = 1;
				for (Map.Entry<String, List<Double>> row : scopeValuesByIndicator.entrySet()) {


					CodeLabel codeLabel = codeLabelService.findCodeLabelByCode(new Code(CodeList.INDICATOR, row.getKey()));

					sheet.addCell(new Label(0, index, codeLabel.getLabel(lang)));

					sheet.addCell(new Number(1, index, row.getValue().get(1)));
					sheet.addCell(new Number(2, index, row.getValue().get(2)));
					sheet.addCell(new Number(3, index, row.getValue().get(3)));
					sheet.addCell(new Number(4, index, row.getValue().get(4)));

					index++;
				}
			}
		}


		WritableSheet sheet = wb.createSheet("Explication", wb.getNumberOfSheets());

		for (ReportLogEntry reportLogEntry : allReportResults.getLogEntries()) {

			String c1 = null;
			String c2 = null;
			String c3 = null;
			String c4 = null;
			String c5 = null;
			String c6 = null;
			Double c7 = null;
			String c8 = null;

			if (reportLogEntry instanceof Contribution) {
				Contribution logEntry = (Contribution) reportLogEntry;

				/*
				c1 = "RESULTS_EXPLANATION_CONTRIB_PART1";
				c2 = logEntry.getBar().getBaseIndicator().getActivityCategory().getKey();
				c3 = logEntry.getBar().getBaseIndicator().getActivitySubCategory().getKey();
				c4 = logEntry.getBar().getActivityData().getActivityType().getKey();
				c5 = logEntry.getBar().getActivityData().getActivitySource().getKey();
				c6 = "RESULTS_EXPLANATION_CONTRIB_PART2";
				c7 = logEntry.getBar().getActivityData().getValue();
				c8 = logEntry.getBar().getActivityData().getUnit().getSymbol();
				c9 = "RESULTS_EXPLANATION_CONTRIB_PART3";
				c8 = logEntry.getBar().getBaseIndicator().getIndicatorCategory().getKey()
*/

			}

			if (reportLogEntry instanceof LowerRankInGroup) {
				LowerRankInGroup logEntry = (LowerRankInGroup) reportLogEntry;


			}

			if (reportLogEntry instanceof NoSuitableFactor) {
				NoSuitableFactor logEntry = (NoSuitableFactor) reportLogEntry;


			}

			if (reportLogEntry instanceof NoSuitableIndicator) {
				NoSuitableIndicator logEntry = (NoSuitableIndicator) reportLogEntry;


			}

		}


		wb.write();
		wb.close();

		byte[] content = byteArrayOutputStream.toByteArray();

		return content;
	}


	@Override
	public byte[] generateComparedExcelInStream(LanguageCode lang, List<Scope> scopes, Period period, Period comparedPeriod, InterfaceTypeCode interfaceCode)
		throws IOException, WriteException, BiffException {

		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

		WorkbookSettings wbSettings = new WorkbookSettings();
		wbSettings.setLocale(new Locale(lang.getKey()));

		WritableWorkbook wb = Workbook.createWorkbook(byteArrayOutputStream, wbSettings);

		// 1. Compute the ReportResult
		AwacCalculator awacCalculator = awacCalculatorService.findByCode(interfaceCode);

		ReportResultCollection leftReportResults = reportResultService.getReportResults(awacCalculator, scopes, period);
		ReportResultCollection rightReportResults = reportResultService.getReportResults(awacCalculator, scopes, comparedPeriod);

		MergedReportResultCollectionAggregation mergedReportResultCollectionAggregation = reportResultService.mergeAsComparision(
			reportResultService.aggregate(leftReportResults),
			reportResultService.aggregate(rightReportResults)
		);

		// Create cell font and format
		WritableFont cellFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);

		WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
		cellFormat.setAlignment(Alignment.CENTRE);
		cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);


		// 2. Populate the DTO
		for (MergedReportResultAggregation reportResult : mergedReportResultCollectionAggregation.getMergedReportResultAggregations()) {
			String reportKey = reportResult.getReportCode();
			if (reportKey.equals("R_1")) {
				WritableSheet sheet = wb.createSheet(reportKey, wb.getNumberOfSheets());

				sheet.addCell(new Label(0, 1, "Indicator", cellFormat));

				sheet.addCell(new Label(1, 1, "Scope 1", cellFormat));
				sheet.addCell(new Label(2, 1, "Scope 2", cellFormat));
				sheet.addCell(new Label(3, 1, "Scope 3", cellFormat));
				sheet.addCell(new Label(4, 1, "Out of scope", cellFormat));

				sheet.addCell(new Label(5, 1, "Scope 1", cellFormat));
				sheet.addCell(new Label(6, 1, "Scope 2", cellFormat));
				sheet.addCell(new Label(7, 1, "Scope 3", cellFormat));
				sheet.addCell(new Label(8, 1, "Out of scope", cellFormat));

				sheet.addCell(new Label(1, 0, reportResult.getLeftPeriod().getLabel(), cellFormat));
				sheet.addCell(new Label(5, 0, reportResult.getRightPeriod().getLabel(), cellFormat));

				sheet.mergeCells(1, 0, 4, 0);
				sheet.mergeCells(5, 0, 8, 0);


				int index = 2;
				for (MergedReportResultIndicatorAggregation mergedReportResultIndicatorAggregationList : reportResult.getMergedReportResultIndicatorAggregationList()) {

					CodeLabel codeLabel = codeLabelService.findCodeLabelByCode(new Code(CodeList.INDICATOR, mergedReportResultIndicatorAggregationList.getIndicator()));

					sheet.addCell(new Label(0, index, codeLabel.getLabel(lang)));

					sheet.addCell(new Number(1, index, mergedReportResultIndicatorAggregationList.getLeftScope1Value()));
					sheet.addCell(new Number(2, index, mergedReportResultIndicatorAggregationList.getLeftScope2Value()));
					sheet.addCell(new Number(3, index, mergedReportResultIndicatorAggregationList.getLeftScope3Value()));
					sheet.addCell(new Number(4, index, mergedReportResultIndicatorAggregationList.getLeftOutOfScopeValue()));

					sheet.addCell(new Number(5, index, mergedReportResultIndicatorAggregationList.getRightScope1Value()));
					sheet.addCell(new Number(6, index, mergedReportResultIndicatorAggregationList.getRightScope2Value()));
					sheet.addCell(new Number(7, index, mergedReportResultIndicatorAggregationList.getRightScope3Value()));
					sheet.addCell(new Number(8, index, mergedReportResultIndicatorAggregationList.getRightOutOfScopeValue()));

					index++;
				}
			}
		}

		wb.write();
		wb.close();

		byte[] content = byteArrayOutputStream.toByteArray();

		return content;
	}


	private void writeSheet(WritableSheet sheet, int firstColumn, int firstRow, Table table) throws WriteException {

		for (int r = 0; r < table.getRowCount(); r++) {
			for (int c = 0; c < table.getColumnCount(); c++) {
				Object value = table.getCell(c, r);
				if (value != null) {
					if (value instanceof Integer) {
						sheet.addCell(new Number(firstColumn + c, firstRow + r, (Integer) value));
					} else if (value instanceof Double) {
						sheet.addCell(new Number(firstColumn + c, firstRow + r, (Double) value));
					} else if (value instanceof Float) {
						sheet.addCell(new Number(firstColumn + c, firstRow + r, (Float) value));
					} else if (value instanceof Long) {
						sheet.addCell(new Number(firstColumn + c, firstRow + r, (Long) value));
					} else {
						sheet.addCell(new Label(firstColumn + c, firstRow + r, value.toString()));
					}
				}
			}
		}


	}


}
