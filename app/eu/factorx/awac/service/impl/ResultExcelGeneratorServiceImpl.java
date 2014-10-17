package eu.factorx.awac.service.impl;

import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.code.label.CodeLabel;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.models.code.type.LanguageCode;
import eu.factorx.awac.models.data.answer.AnswerValue;
import eu.factorx.awac.models.data.answer.QuestionAnswer;
import eu.factorx.awac.models.data.answer.QuestionSetAnswer;
import eu.factorx.awac.models.data.answer.type.*;
import eu.factorx.awac.models.forms.AwacCalculator;
import eu.factorx.awac.models.forms.Form;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.models.knowledge.Unit;
import eu.factorx.awac.models.reporting.ReportResult;
import eu.factorx.awac.service.*;
import eu.factorx.awac.service.impl.reporting.*;
import eu.factorx.awac.util.Table;
import eu.factorx.awac.util.math.Vector2I;
import jxl.Range;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.CellFormat;
import jxl.read.biff.BiffException;
import jxl.write.*;
import jxl.write.Number;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.Boolean;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Component
public class ResultExcelGeneratorServiceImpl implements ResultExcelGeneratorService {

	@Autowired
	private AwacCalculatorService    awacCalculatorService;
	@Autowired
	private ReportResultService      reportResultService;
	@Autowired
	private CodeLabelService         codeLabelService;
	@Autowired
	private QuestionSetAnswerService questionSetAnswerService;

	private String translate(String code, CodeList cl, LanguageCode lang) {
		CodeLabel codeLabel = codeLabelService.findCodeLabelByCode(new Code(cl, code));
		if (codeLabel == null) {
			return code;
		} else {
			return codeLabel.getLabel(lang);
		}
	}

	private WritableCell getRealCell(WritableSheet sheet, int col, int row) {
		for (Range r : sheet.getMergedCells()) {
			if (col >= r.getTopLeft().getColumn()
				&& col <= r.getBottomRight().getColumn()
				&& row <= r.getTopLeft().getRow()
				&& row >= r.getBottomRight().getRow()) {
				return (WritableCell) r.getTopLeft();
			}
		}
		return sheet.getWritableCell(col, row);
	}

	private void drawBorder(WritableSheet sheet, int col1, int row1, int col2, int row2) throws WriteException {

		jxl.format.BorderLineStyle borderWidth = BorderLineStyle.THIN;

		for (int i = row1; i <= row2; i++) {
			WritableCell writableCell = getRealCell(sheet, col1, i);
			CellFormat format = writableCell.getCellFormat();
			WritableCellFormat cellFormat;
			if (format == null) {
				cellFormat = new WritableCellFormat();
			} else {
				cellFormat = new WritableCellFormat(format);
			}
			cellFormat.setBorder(Border.LEFT, borderWidth);
			writableCell.setCellFormat(cellFormat);
		}

		for (int i = row1; i <= row2; i++) {
			WritableCell writableCell = getRealCell(sheet, col2, i);
			CellFormat format = writableCell.getCellFormat();
			WritableCellFormat cellFormat;
			if (format == null) {
				cellFormat = new WritableCellFormat();
			} else {
				cellFormat = new WritableCellFormat(format);
			}
			cellFormat.setBorder(Border.RIGHT, borderWidth);
			writableCell.setCellFormat(cellFormat);
		}

		for (int i = col1; i <= col2; i++) {
			WritableCell writableCell = getRealCell(sheet, i, row1);
			CellFormat format = writableCell.getCellFormat();
			WritableCellFormat cellFormat;
			if (format == null) {
				cellFormat = new WritableCellFormat();
			} else {
				cellFormat = new WritableCellFormat(format);
			}
			cellFormat.setBorder(Border.TOP, borderWidth);
			writableCell.setCellFormat(cellFormat);
		}

		for (int i = col1; i <= col2; i++) {
			WritableCell writableCell = getRealCell(sheet, i, row2);
			CellFormat format = writableCell.getCellFormat();
			WritableCellFormat cellFormat;
			if (format == null) {
				cellFormat = new WritableCellFormat();
			} else {
				cellFormat = new WritableCellFormat(format);
			}
			cellFormat.setBorder(Border.BOTTOM, borderWidth);
			writableCell.setCellFormat(cellFormat);
		}


	}

	@Override
	public byte[] generateExcelInStream(LanguageCode lang, List<Scope> scopes, Period period, InterfaceTypeCode interfaceCode) throws IOException, WriteException, BiffException {

		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

		// Create cell font and format
		WritableFont cellFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
		WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
		cellFormat.setAlignment(Alignment.LEFT);
		cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);

		WorkbookSettings wbSettings = new WorkbookSettings();
		wbSettings.setLocale(new Locale(lang.getKey()));
		WritableWorkbook wb = Workbook.createWorkbook(byteArrayOutputStream, wbSettings);

		// 1. Compute the ReportResult
		AwacCalculator awacCalculator = awacCalculatorService.findByCode(interfaceCode);
		ReportResultCollection allReportResults = reportResultService.getReportResults(awacCalculator, scopes, period);

		// 2.1 Table
		writeTable(lang, wb, allReportResults, cellFormat);

		// 2.2 Explanation
		writeExplanation(wb, lang, allReportResults, cellFormat);

		// 2.3 Survey
		for (Scope scope : scopes) {

			WritableSheet sheet = wb.createSheet("Données " + scope.getName() + " - " + period.getLabel(), wb.getNumberOfSheets());

			Vector2I cell = new Vector2I(0, 0);
			for (Form form : awacCalculator.getForms()) {
				sheet.addCell(new Label(
					cell.getX(),
					cell.getY(),
					translate(form.getIdentifier(), CodeList.TRANSLATIONS_SURVEY, lang),
					cellFormat));

				cell.setY(cell.getY() + 1);

				List<QuestionSetAnswer> questionSetAnswers = questionSetAnswerService.findByScopeAndPeriodAndForm(scope, period, form);

				for (QuestionSetAnswer questionSetAnswer : questionSetAnswers) {
					cell = writePartBorder(sheet, cell, questionSetAnswer, lang, 0);
				}

				cell.setY(cell.getY() + 1);
			}

			sheet.setColumnView(0, 600);

		}

		wb.write();
		wb.close();

		byte[] content = byteArrayOutputStream.toByteArray();

		return content;
	}

	private void writeTable(LanguageCode lang, WritableWorkbook wb, ReportResultCollection allReportResults, WritableCellFormat cellFormat) throws WriteException {
		for (ReportResult reportResult : allReportResults.getReportResults()) {
			String reportKey = reportResult.getReport().getCode().getKey();

			if (reportKey.equals("R_1")) {

				WritableSheet sheet = wb.createSheet("Rapport", wb.getNumberOfSheets());

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
	}

	private void writeExplanation(WritableWorkbook wb, LanguageCode lang, ReportResultCollection allReportResults, WritableCellFormat cellFormat) throws WriteException {
		WritableSheet sheet = wb.createSheet("Explication", wb.getNumberOfSheets());

		sheet.addCell(new Label(1, 0, "Activité", cellFormat));
		sheet.addCell(new Label(9, 0, "Facteur d'émission", cellFormat));
		sheet.addCell(new Label(18, 0, "Résultat", cellFormat));


		sheet.addCell(new Label(1, 1, "Catégorie ", cellFormat));
		sheet.addCell(new Label(2, 1, "Sous-catégorie", cellFormat));
		sheet.addCell(new Label(3, 1, "Type", cellFormat));
		sheet.addCell(new Label(4, 1, "Source", cellFormat));
		sheet.addCell(new Label(6, 1, "Valeur", cellFormat));
		sheet.addCell(new Label(7, 1, "Unité", cellFormat));

		sheet.addCell(new Label(9, 1, "Indicateur", cellFormat));
		sheet.addCell(new Label(10, 1, "Type", cellFormat));
		sheet.addCell(new Label(11, 1, "Source", cellFormat));

		sheet.addCell(new Label(13, 1, "Valeur", cellFormat));
		sheet.addCell(new Label(14, 1, "Unité OUT", cellFormat));

		sheet.addCell(new Label(16, 1, "Unité IN", cellFormat));

		sheet.addCell(new Label(18, 1, "Valeur", cellFormat));
		sheet.addCell(new Label(19, 1, "Unité", cellFormat));

		sheet.mergeCells(1, 0, 7, 0);
		sheet.mergeCells(9, 0, 16, 0);
		sheet.mergeCells(18, 0, 19, 0);


		int row = 2;
		for (ReportLogEntry reportLogEntry : allReportResults.getLogEntries()) {

			String c1 = null;
			String c2 = null;
			String c3 = null;
			String c4 = null;
			String c5 = null;
			String c6 = null;
			Double c7 = null;
			String c8 = null;
			String c9 = null;
			String c10 = null;
			String c11 = null;
			String c12 = null;
			String c13 = null;
			Double c14 = null;
			String c15 = null;
			String c16 = null;
			String c17 = null;
			String c18 = null;
			Double c19 = null;
			String c20 = null;

			if (reportLogEntry instanceof Contribution) {
				Contribution logEntry = (Contribution) reportLogEntry;

				c1 = translate("RESULTS_EXPLANATION_CONTRIB_PART1", CodeList.TRANSLATIONS_INTERFACE, lang);
				c2 = translate(logEntry.getBar().getBaseIndicator().getActivityCategory().getKey(), CodeList.ActivityCategory, lang);
				c3 = translate(logEntry.getBar().getBaseIndicator().getActivitySubCategory().getKey(), CodeList.ActivitySubCategory, lang);
				c4 = translate(logEntry.getBar().getActivityData().getActivityType().getKey(), CodeList.ActivityType, lang);
				c5 = translate(logEntry.getBar().getActivityData().getActivitySource().getKey(), CodeList.ActivitySource, lang);
				c6 = translate("RESULTS_EXPLANATION_CONTRIB_PART2", CodeList.TRANSLATIONS_INTERFACE, lang);
				c7 = logEntry.getBar().getActivityData().getValue();
				c8 = logEntry.getBar().getActivityData().getUnit().getSymbol();
				c9 = translate("RESULTS_EXPLANATION_CONTRIB_PART3", CodeList.TRANSLATIONS_INTERFACE, lang);
				c10 = translate(logEntry.getBar().getBaseIndicator().getIndicatorCategory().getKey(), CodeList.IndicatorCategory, lang);
				c11 = translate(logEntry.getBar().getActivityData().getActivityType().getKey(), CodeList.ActivityType, lang);
				c12 = translate(logEntry.getBar().getActivityData().getActivitySource().getKey(), CodeList.ActivitySource, lang);
				c13 = translate("RESULTS_EXPLANATION_CONTRIB_PART4", CodeList.TRANSLATIONS_INTERFACE, lang);
				c14 = logEntry.getBar().getFactor().getCurrentValue();
				c15 = logEntry.getBar().getFactor().getUnitOut().getSymbol();
				c16 = translate("RESULTS_EXPLANATION_CONTRIB_PART5", CodeList.TRANSLATIONS_INTERFACE, lang);
				c17 = logEntry.getBar().getFactor().getUnitIn().getSymbol();
				c18 = translate("RESULTS_EXPLANATION_CONTRIB_PART6", CodeList.TRANSLATIONS_INTERFACE, lang);
				c19 = logEntry.getBar().getNumericValue();
				c20 = logEntry.getBar().getBaseIndicator().getUnit().getSymbol();

			}

			if (reportLogEntry instanceof LowerRankInGroup) {
				LowerRankInGroup logEntry = (LowerRankInGroup) reportLogEntry;

				c1 = translate("RESULTS_EXPLANATION_LOWER_RANK_PART1", CodeList.TRANSLATIONS_INTERFACE, lang);
				c2 = translate(logEntry.getBaseActivityData().getActivityCategory().getKey(), CodeList.ActivityCategory, lang);
				c3 = translate(logEntry.getBaseActivityData().getActivitySubCategory().getKey(), CodeList.ActivitySubCategory, lang);
				c4 = translate(logEntry.getBaseActivityData().getActivityType().getKey(), CodeList.ActivityType, lang);
				c5 = translate(logEntry.getBaseActivityData().getActivitySource().getKey(), CodeList.ActivitySource, lang);
				c6 = translate("RESULTS_EXPLANATION_LOWER_RANK_PART2", CodeList.TRANSLATIONS_INTERFACE, lang);
				c7 = logEntry.getBaseActivityData().getValue();
				c8 = logEntry.getBaseActivityData().getUnit().getSymbol();
				c9 = translate("RESULTS_EXPLANATION_LOWER_RANK_PART3", CodeList.TRANSLATIONS_INTERFACE, lang);

			}

			if (reportLogEntry instanceof NoSuitableFactor) {
				NoSuitableFactor logEntry = (NoSuitableFactor) reportLogEntry;

				c1 = translate("RESULTS_EXPLANATION_NOFACTOR_PART1", CodeList.TRANSLATIONS_INTERFACE, lang);
				c2 = translate(logEntry.getBaseIndicator().getActivityCategory().getKey(), CodeList.ActivityCategory, lang);
				c3 = translate(logEntry.getBaseIndicator().getActivitySubCategory().getKey(), CodeList.ActivitySubCategory, lang);
				c4 = translate(logEntry.getBad().getActivityType().getKey(), CodeList.ActivityType, lang);
				c5 = translate(logEntry.getBad().getActivitySource().getKey(), CodeList.ActivitySource, lang);
				c6 = translate("RESULTS_EXPLANATION_NOFACTOR_PART2", CodeList.TRANSLATIONS_INTERFACE, lang);
				c7 = logEntry.getBad().getValue();
				c8 = logEntry.getBad().getUnit().getSymbol();
				c9 = translate("RESULTS_EXPLANATION_NOFACTOR_PART3", CodeList.TRANSLATIONS_INTERFACE, lang);
				c10 = translate(logEntry.getBaseIndicator().getIndicatorCategory().getKey(), CodeList.IndicatorCategory, lang);
				c11 = translate(logEntry.getBad().getActivityType().getKey(), CodeList.ActivityType, lang);
				c12 = translate(logEntry.getBad().getActivitySource().getKey(), CodeList.ActivitySource, lang);
				c13 = translate("RESULTS_EXPLANATION_NOFACTOR_PART4", CodeList.TRANSLATIONS_INTERFACE, lang);

			}

			if (reportLogEntry instanceof NoSuitableIndicator) {
				NoSuitableIndicator logEntry = (NoSuitableIndicator) reportLogEntry;

				c1 = translate("RESULTS_EXPLANATION_NOINDICATOR_PART1", CodeList.TRANSLATIONS_INTERFACE, lang);
				c2 = translate(logEntry.getBaseActivityData().getActivityCategory().getKey(), CodeList.ActivityCategory, lang);
				c3 = translate(logEntry.getBaseActivityData().getActivitySubCategory().getKey(), CodeList.ActivitySubCategory, lang);
				c4 = translate(logEntry.getBaseActivityData().getActivityType().getKey(), CodeList.ActivityType, lang);
				c5 = translate(logEntry.getBaseActivityData().getActivitySource().getKey(), CodeList.ActivitySource, lang);
				c6 = translate("RESULTS_EXPLANATION_NOINDICATOR_PART2", CodeList.TRANSLATIONS_INTERFACE, lang);
				c7 = logEntry.getBaseActivityData().getValue();
				c8 = logEntry.getBaseActivityData().getUnit().getSymbol();
				c9 = translate("RESULTS_EXPLANATION_NOINDICATOR_PART3", CodeList.TRANSLATIONS_INTERFACE, lang);
			}

			sheet.addCell(new Label(0, row, c1));
			sheet.addCell(new Label(1, row, c2));
			sheet.addCell(new Label(2, row, c3));
			sheet.addCell(new Label(3, row, c4));
			sheet.addCell(new Label(4, row, c5));
			sheet.addCell(new Label(5, row, c6));
			sheet.addCell(new Number(6, row, c7));
			sheet.addCell(new Label(7, row, c8));
			sheet.addCell(new Label(8, row, c9));
			sheet.addCell(new Label(9, row, c10));
			sheet.addCell(new Label(10, row, c11));
			sheet.addCell(new Label(11, row, c12));
			sheet.addCell(new Label(12, row, c13));
			sheet.addCell(new Number(13, row, c14));
			sheet.addCell(new Label(14, row, c15));
			sheet.addCell(new Label(15, row, c16));
			sheet.addCell(new Label(16, row, c17));
			sheet.addCell(new Label(17, row, c18));
			sheet.addCell(new Number(18, row, c19));
			sheet.addCell(new Label(19, row, c20));

			row++;

		}

		drawBorder(sheet, 1, 0, 7, row - 1);
		drawBorder(sheet, 9, 0, 16, row - 1);
		drawBorder(sheet, 18, 0, 19, row - 1);
	}

	private Vector2I writePartBorder(WritableSheet sheet, Vector2I cell, QuestionSetAnswer questionSetAnswer, LanguageCode lang, int indent) throws WriteException {

		final String indentString = "        ";

		sheet.addCell(new Label(
			cell.getX(),
			cell.getY(),
			StringUtils.repeat(indentString, indent) + translate(questionSetAnswer.getQuestionSet().getCode().getKey(), CodeList.QUESTION, lang)
		));

		cell.setY(cell.getY() + 1);

		for (QuestionSetAnswer qs : questionSetAnswer.getChildren()) {
			cell = writePartBorder(sheet, cell, qs, lang, indent + 1);
		}

		for (QuestionAnswer questionAnswer : questionSetAnswer.getQuestionAnswers()) {

			WritableFont cellFont = new WritableFont(WritableFont.ARIAL, 10);
			cellFont.setItalic(true);
			WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
			cellFormat.setAlignment(Alignment.LEFT);
			cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);

			sheet.addCell(new Label(
				cell.getX(),
				cell.getY(),
				StringUtils.repeat(indentString, indent + 1) + translate(questionAnswer.getQuestion().getCode().getKey(), CodeList.QUESTION, lang),
				cellFormat
			));

			int col = 1;
			for (AnswerValue answerValue : questionAnswer.getAnswerValues()) {

				// YES / NO
				if (answerValue instanceof BooleanAnswerValue) {
					BooleanAnswerValue value = (BooleanAnswerValue) answerValue;

					Boolean tf = value.getValue();

					sheet.addCell(new jxl.write.Boolean(
						cell.getX() + col,
						cell.getY(),
						tf
					));
				}

				// STRING
				if (answerValue instanceof StringAnswerValue) {
					StringAnswerValue value = (StringAnswerValue) answerValue;

					sheet.addCell(new Label(
						cell.getX() + col,
						cell.getY(),
						value.getValue()
					));
				}

				// CODE
				if (answerValue instanceof CodeAnswerValue) {
					CodeAnswerValue value = (CodeAnswerValue) answerValue;


					sheet.addCell(new Label(
						cell.getX() + col,
						cell.getY(),
						translate(value.getValue().getKey(), value.getValue().getCodeList(), lang)
					));
				}

				// ENTITY
				if (answerValue instanceof EntityAnswerValue) {
					EntityAnswerValue value = (EntityAnswerValue) answerValue;

					sheet.addCell(new Label(
						cell.getX() + col,
						cell.getY(),
						value.getEntityName()
					));
				}

				// DOUBLE
				if (answerValue instanceof DoubleAnswerValue) {
					DoubleAnswerValue value = (DoubleAnswerValue) answerValue;


					sheet.addCell(new Number(
						cell.getX() + col,
						cell.getY(),
						value.getValue()
					));

					Unit unit = value.getUnit();
					String unitString = "";
					if (unit != null) {
						col++;
						unitString = unit.getSymbol();


						sheet.addCell(new Label(
							cell.getX() + col,
							cell.getY(),
							unitString
						));
					}

				}

				// INTEGER
				if (answerValue instanceof IntegerAnswerValue) {
					IntegerAnswerValue value = (IntegerAnswerValue) answerValue;


					sheet.addCell(new Number(
						cell.getX() + col,
						cell.getY(),
						value.getValue()
					));

					Unit unit = value.getUnit();
					String unitString = "";
					if (unit != null) {
						unitString = unit.getSymbol();
						col++;

						sheet.addCell(new Label(
							cell.getX() + col,
							cell.getY(),
							unitString
						));
					}

				}

				col++;
			}

			cell.setY(cell.getY() + 1);
		}

		return cell;
	}


	@Override
	public byte[] generateComparedExcelInStream(LanguageCode lang, List<Scope> scopes, Period period, Period comparedPeriod, InterfaceTypeCode interfaceCode)
		throws IOException, WriteException, BiffException {


		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

		// Create cell font and format
		WritableFont cellFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
		WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
		cellFormat.setAlignment(Alignment.LEFT);
		cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);

		WorkbookSettings wbSettings = new WorkbookSettings();
		wbSettings.setLocale(new Locale(lang.getKey()));
		WritableWorkbook wb = Workbook.createWorkbook(byteArrayOutputStream, wbSettings);

		// 1. Compute the ReportResult
		AwacCalculator awacCalculator = awacCalculatorService.findByCode(interfaceCode);
		ReportResultCollection allReportResultsLeft = reportResultService.getReportResults(awacCalculator, scopes, period);
		ReportResultCollection allReportResultsRight = reportResultService.getReportResults(awacCalculator, scopes, comparedPeriod);

		ReportResultCollectionAggregation left = reportResultService.aggregate(allReportResultsLeft);
		ReportResultCollectionAggregation right = reportResultService.aggregate(allReportResultsRight);

		MergedReportResultCollectionAggregation merged = reportResultService.mergeAsComparision(left, right);


		// 2.1 Table
		writeComparisionTable(lang, wb, merged, cellFormat);

		// 2.3 Survey
		for (Scope scope : scopes) {

			WritableSheet sheet = wb.createSheet("Données " + scope.getName() + " - " + period.getLabel(), wb.getNumberOfSheets());

			Vector2I cell = new Vector2I(0, 0);
			for (Form form : awacCalculator.getForms()) {
				sheet.addCell(new Label(
					cell.getX(),
					cell.getY(),
					translate(form.getIdentifier(), CodeList.TRANSLATIONS_SURVEY, lang),
					cellFormat));

				cell.setY(cell.getY() + 1);

				List<QuestionSetAnswer> questionSetAnswers = questionSetAnswerService.findByScopeAndPeriodAndForm(scope, period, form);

				for (QuestionSetAnswer questionSetAnswer : questionSetAnswers) {
					cell = writePartBorder(sheet, cell, questionSetAnswer, lang, 0);
				}

				cell.setY(cell.getY() + 1);
			}

			sheet.setColumnView(0, 600);

		}

		for (Scope scope : scopes) {

			WritableSheet sheet = wb.createSheet("Données " + scope.getName() + " - " + comparedPeriod.getLabel(), wb.getNumberOfSheets());

			Vector2I cell = new Vector2I(0, 0);
			for (Form form : awacCalculator.getForms()) {
				sheet.addCell(new Label(
					cell.getX(),
					cell.getY(),
					translate(form.getIdentifier(), CodeList.TRANSLATIONS_SURVEY, lang),
					cellFormat));

				cell.setY(cell.getY() + 1);

				List<QuestionSetAnswer> questionSetAnswers = questionSetAnswerService.findByScopeAndPeriodAndForm(scope, comparedPeriod, form);

				for (QuestionSetAnswer questionSetAnswer : questionSetAnswers) {
					cell = writePartBorder(sheet, cell, questionSetAnswer, lang, 0);
				}

				cell.setY(cell.getY() + 1);
			}

			sheet.setColumnView(0, 600);

		}

		wb.write();
		wb.close();

		byte[] content = byteArrayOutputStream.toByteArray();

		return content;
	}

	private void writeComparisionTable(LanguageCode lang, WritableWorkbook wb, MergedReportResultCollectionAggregation merged, WritableCellFormat cellFormat) throws WriteException {
		for (MergedReportResultAggregation aggregation : merged.getMergedReportResultAggregations()) {
			String reportKey = aggregation.getReportCode();

			if (reportKey.equals("R_1")) {

				WritableSheet sheet = wb.createSheet("Rapport", wb.getNumberOfSheets());

				List<MergedReportResultIndicatorAggregation> scopeValuesByIndicator = aggregation.getMergedReportResultIndicatorAggregationList();

				sheet.addCell(new Label(0, 1, "Indicator", cellFormat));

				sheet.addCell(new Label(1, 1, "Scope 1", cellFormat));
				sheet.addCell(new Label(2, 1, "Scope 2", cellFormat));
				sheet.addCell(new Label(3, 1, "Scope 3", cellFormat));
				sheet.addCell(new Label(4, 1, "Out of scope", cellFormat));

				sheet.addCell(new Label(5, 1, "Scope 1", cellFormat));
				sheet.addCell(new Label(6, 1, "Scope 2", cellFormat));
				sheet.addCell(new Label(7, 1, "Scope 3", cellFormat));
				sheet.addCell(new Label(8, 1, "Out of scope", cellFormat));

				sheet.addCell(new Label(1, 0, aggregation.getLeftPeriod().getLabel(), cellFormat));
				sheet.addCell(new Label(5, 0, aggregation.getRightPeriod().getLabel(), cellFormat));

				sheet.mergeCells(1, 0, 4, 0);
				sheet.mergeCells(5, 0, 8, 0);


				int index = 2;
				for (MergedReportResultIndicatorAggregation row : scopeValuesByIndicator) {

					CodeLabel codeLabel = codeLabelService.findCodeLabelByCode(new Code(CodeList.INDICATOR, row.getIndicator()));

					sheet.addCell(new Label(0, index, codeLabel.getLabel(lang)));

					sheet.addCell(new Number(1, index, row.getLeftScope1Value()));
					sheet.addCell(new Number(2, index, row.getLeftScope2Value()));
					sheet.addCell(new Number(3, index, row.getLeftScope3Value()));
					sheet.addCell(new Number(4, index, row.getLeftOutOfScopeValue()));

					sheet.addCell(new Number(5, index, row.getRightScope1Value()));
					sheet.addCell(new Number(6, index, row.getRightScope2Value()));
					sheet.addCell(new Number(7, index, row.getRightScope3Value()));
					sheet.addCell(new Number(8, index, row.getRightOutOfScopeValue()));

					index++;
				}
			}
		}
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
