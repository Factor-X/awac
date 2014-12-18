package eu.factorx.awac.service.impl;

import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.business.Site;
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
import eu.factorx.awac.models.knowledge.BaseIndicator;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.models.knowledge.Report;
import eu.factorx.awac.models.knowledge.Unit;
import eu.factorx.awac.models.reporting.BaseActivityData;
import eu.factorx.awac.models.reporting.BaseActivityResult;
import eu.factorx.awac.models.reporting.ReportResult;
import eu.factorx.awac.service.*;
import eu.factorx.awac.service.impl.reporting.*;
import eu.factorx.awac.util.math.Vector2I;
import jxl.Range;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.*;
import jxl.format.VerticalAlignment;
import jxl.read.biff.BiffException;
import jxl.write.*;
import jxl.write.Number;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import play.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Component
public class ResultExcelGeneratorServiceImpl implements ResultExcelGeneratorService {

    @Autowired
    private AwacCalculatorService     awacCalculatorService;
    @Autowired
    private ReportResultService       reportResultService;
    @Autowired
    private CodeLabelService          codeLabelService;
    @Autowired
    private QuestionSetAnswerService  questionSetAnswerService;
    @Autowired
    private FactorValueService        factorValueService;
    @Autowired
    private BaseActivityResultService baseActivityResultService;

    //
    // Simple
    //

    @Override
    public byte[] generateExcelInStream(LanguageCode lang, List<Scope> scopes, Period period, InterfaceTypeCode interfaceCode, Map<String, Double> typicalResultValues, Map<String, Double> idealResultValues) throws IOException, WriteException, BiffException {

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

        String r_1 = "";
        for (Report report : awacCalculator.getReports()) {
            if (report.getRestrictedScope() == null) {
                r_1 = report.getCode().getKey();
            }
        }

        String organization = scopes.get(0).getOrganization().getName();
        String sites = "";
        for (Scope scope : scopes) {
            if (scope instanceof Site) {
                Site site = (Site) scope;
                if (sites.length() > 0) {
                    sites += ", ";
                }
                sites += site.getName();
            }
        }

        // 2.1 Table
        writeTable(r_1, organization, sites, period.getLabel(), lang, wb, allReportResults, cellFormat, interfaceCode, typicalResultValues, idealResultValues);

        // 2.2 Explanation
        writeExplanation(wb, organization, sites, period.getLabel(), lang, allReportResults, cellFormat);

        // 2.3 Survey
        for (Scope scope : scopes) {

            WritableSheet sheet = wb.createSheet("Données " + period.getLabel() + " - " + scope.getName(), wb.getNumberOfSheets());

            String thisSite = "";
            if (scope instanceof Site) {
                thisSite = ((Site) scope).getName();
            }
            insertHeader(sheet, null, organization, thisSite, period.getLabel(), cellFormat);

            Vector2I cell = new Vector2I(0, 4);
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

        return byteArrayOutputStream.toByteArray();
    }

	private void writeTable(String search, String organization, String sites, String period, LanguageCode lang, WritableWorkbook wb, ReportResultCollection allReportResults, WritableCellFormat cellFormat, InterfaceTypeCode interfaceTypeCode, Map<String, Double> typicalResultValues, Map<String, Double> idealResultValues) throws WriteException {
		if (InterfaceTypeCode.HOUSEHOLD.equals(interfaceTypeCode) || InterfaceTypeCode.LITTLEEMITTER.equals(interfaceTypeCode) || InterfaceTypeCode.EVENT.equals(interfaceTypeCode)) {
			String resultLabel = translate("SCOPE_SIMPLE", CodeList.TRANSLATIONS_INTERFACE, lang) + " (tCO2e)";
			String typicalResultLabel = translate(getTypicalResultLabelKey(interfaceTypeCode), CodeList.TRANSLATIONS_INTERFACE, lang) + " (tCO2e)";
			String idealResultLabel = translate(getIdealResultLabelKey(interfaceTypeCode), CodeList.TRANSLATIONS_INTERFACE, lang) + " (tCO2e)";
			for (ReportResult reportResult : allReportResults.getReportResults()) {
				String reportKey = reportResult.getReport().getCode().getKey();

				if (reportKey.equals(search)) {

					WritableSheet sheet = wb.createSheet("Résultat", wb.getNumberOfSheets());

					insertHeader(sheet, null, organization, sites, period, cellFormat);

					Map<String, List<Double>> scopeValuesByIndicator = reportResultService.getScopeValuesByIndicator(reportResult);

					sheet.addCell(new Label(1, 4, resultLabel, cellFormat));
					sheet.addCell(new Label(2, 4, typicalResultLabel, cellFormat));
					sheet.addCell(new Label(3, 4, idealResultLabel, cellFormat));

					int index = 5;
					for (Map.Entry<String, List<Double>> row : scopeValuesByIndicator.entrySet()) {

						Logger.info("row = " + row);
						CodeLabel codeLabel = codeLabelService.findCodeLabelByCode(new Code(CodeList.INDICATOR, row.getKey()));

						sheet.addCell(new Label(0, index, codeLabel.getLabel(lang)));

						// total of emissions (all scopes)
						sheet.addCell(new Number(1, index, row.getValue().get(0)));

						// typical emission
						sheet.addCell(new Number(2, index, typicalResultValues.get(row.getKey())));

						// ideal emission
						sheet.addCell(new Number(3, index, idealResultValues.get(row.getKey())));

						index++;
					}
				}
			}
		} else {
			for (ReportResult reportResult : allReportResults.getReportResults()) {
				String reportKey = reportResult.getReport().getCode().getKey();

				if (reportKey.equals(search)) {

					WritableSheet sheet = wb.createSheet("Résultat", wb.getNumberOfSheets());

					insertHeader(sheet, null, organization, sites, period, cellFormat);

					Map<String, List<Double>> scopeValuesByIndicator = reportResultService.getScopeValuesByIndicator(reportResult);

					sheet.addCell(new Label(1, 4, translate("SCOPE_1", CodeList.TRANSLATIONS_INTERFACE, lang) + " (tCO2e)", cellFormat));
					sheet.addCell(new Label(2, 4, translate("SCOPE_2", CodeList.TRANSLATIONS_INTERFACE, lang) + " (tCO2e)", cellFormat));
					sheet.addCell(new Label(3, 4, translate("SCOPE_3", CodeList.TRANSLATIONS_INTERFACE, lang) + " (tCO2e)", cellFormat));
					sheet.addCell(new Label(4, 4, translate("OUT_OF_SCOPE", CodeList.TRANSLATIONS_INTERFACE, lang) + " (tCO2)", cellFormat));

					int index = 5;
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
    }

	private void writeExplanation(WritableWorkbook wb, String organization, String sites, String period, LanguageCode lang, ReportResultCollection allReportResults, WritableCellFormat cellFormat) throws WriteException {
        WritableSheet sheet = wb.createSheet("Explication", wb.getNumberOfSheets());

        insertHeader(sheet, null, organization, sites, period, cellFormat);

        sheet.addCell(new Label(1, 4, "Activité", cellFormat));
        sheet.addCell(new Label(9, 4, "Facteur d'émission", cellFormat));
        sheet.addCell(new Label(18, 4, "Résultat", cellFormat));


        sheet.addCell(new Label(1, 5, "Catégorie ", cellFormat));
        sheet.addCell(new Label(2, 5, "Sous-catégorie", cellFormat));
        sheet.addCell(new Label(3, 5, "Type", cellFormat));
        sheet.addCell(new Label(4, 5, "Source", cellFormat));
        sheet.addCell(new Label(6, 5, "Valeur", cellFormat));
        sheet.addCell(new Label(7, 5, "Unité", cellFormat));

        sheet.addCell(new Label(9, 5, "Indicateur", cellFormat));
        sheet.addCell(new Label(10, 5, "Type", cellFormat));
        sheet.addCell(new Label(11, 5, "Source", cellFormat));

        sheet.addCell(new Label(13, 5, "Valeur", cellFormat));
        sheet.addCell(new Label(14, 5, "Unité OUT", cellFormat));

        sheet.addCell(new Label(16, 5, "Unité IN", cellFormat));

        sheet.addCell(new Label(18, 5, "Valeur", cellFormat));
        sheet.addCell(new Label(19, 5, "Unité", cellFormat));

        sheet.mergeCells(1, 4, 7, 4);
        sheet.mergeCells(9, 4, 16, 4);
        sheet.mergeCells(18, 4, 19, 4);


        int row = 6;
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
                BaseActivityResult bar = logEntry.getBar();
                BaseActivityData activityData = bar.getActivityData();
                BaseIndicator baseIndicator = bar.getBaseIndicator();

                c1 = translate("RESULTS_EXPLANATION_CONTRIB_PART1", CodeList.TRANSLATIONS_INTERFACE, lang);
                c2 = translate(baseIndicator.getActivityCategory().getKey(), CodeList.ActivityCategory, lang);
                c3 = translate(baseIndicator.getActivitySubCategory().getKey(), CodeList.ActivitySubCategory, lang);
                c4 = translate(activityData.getActivityType().getKey(), CodeList.ActivityType, lang);
                c5 = translate(activityData.getActivitySource().getKey(), CodeList.ActivitySource, lang);
                c6 = translate("RESULTS_EXPLANATION_CONTRIB_PART2", CodeList.TRANSLATIONS_INTERFACE, lang);
                c7 = activityData.getValue();
                c8 = activityData.getUnit().getSymbol();
                c9 = translate("RESULTS_EXPLANATION_CONTRIB_PART3", CodeList.TRANSLATIONS_INTERFACE, lang);
                c10 = translate(baseIndicator.getIndicatorCategory().getKey(), CodeList.IndicatorCategory, lang);
                c11 = translate(activityData.getActivityType().getKey(), CodeList.ActivityType, lang);
                c12 = translate(activityData.getActivitySource().getKey(), CodeList.ActivitySource, lang);
                c13 = translate("RESULTS_EXPLANATION_CONTRIB_PART4", CodeList.TRANSLATIONS_INTERFACE, lang);
                c14 = factorValueService.findByFactorAndYear(bar.getFactor(), bar.getYear()).getValue();
                c15 = bar.getFactor().getUnitOut().getSymbol();
                c16 = translate("RESULTS_EXPLANATION_CONTRIB_PART5", CodeList.TRANSLATIONS_INTERFACE, lang);
                c17 = bar.getFactor().getUnitIn().getSymbol();
                c18 = translate("RESULTS_EXPLANATION_CONTRIB_PART6", CodeList.TRANSLATIONS_INTERFACE, lang);
                c19 = baseActivityResultService.getValueForYear(bar);
                c20 = baseIndicator.getUnit().getSymbol();

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

            if (c1 != null) {
                sheet.addCell(new Label(0, row, c1));
            }
            if (c2 != null) {
                sheet.addCell(new Label(1, row, c2));
            }
            if (c3 != null) {
                sheet.addCell(new Label(2, row, c3));
            }
            if (c4 != null) {
                sheet.addCell(new Label(3, row, c4));
            }
            if (c5 != null) {
                sheet.addCell(new Label(4, row, c5));
            }
            if (c6 != null) {
                sheet.addCell(new Label(5, row, c6));
            }
            if (c7 != null) {
                sheet.addCell(new Number(6, row, c7));
            }
            if (c8 != null) {
                sheet.addCell(new Label(7, row, c8));
            }
            if (c9 != null) {
                sheet.addCell(new Label(8, row, c9));
            }
            if (c10 != null) {
                sheet.addCell(new Label(9, row, c10));
            }
            if (c11 != null) {
                sheet.addCell(new Label(10, row, c11));
            }
            if (c12 != null) {
                sheet.addCell(new Label(11, row, c12));
            }
            if (c13 != null) {
                sheet.addCell(new Label(12, row, c13));
            }
            if (c14 != null) {
                sheet.addCell(new Number(13, row, c14));
            }
            if (c15 != null) {
                sheet.addCell(new Label(14, row, c15));
            }
            if (c16 != null) {
                sheet.addCell(new Label(15, row, c16));
            }
            if (c17 != null) {
                sheet.addCell(new Label(16, row, c17));
            }
            if (c18 != null) {
                sheet.addCell(new Label(17, row, c18));
            }
            if (c19 != null) {
                sheet.addCell(new Number(18, row, c19));
            }
            if (c20 != null) {
                sheet.addCell(new Label(19, row, c20));
            }

            row++;

        }

        drawBorder(sheet, 1, 4, 7, row - 1);
        drawBorder(sheet, 9, 4, 16, row - 1);
        drawBorder(sheet, 18, 4, 19, row - 1);
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

                    java.lang.Boolean tf = value.getValue();

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

                // DATE
                if (answerValue instanceof DateTimeAnswerValue) {
                    DateTimeAnswerValue value = (DateTimeAnswerValue) answerValue;

                    sheet.addCell(new DateTime(
                        cell.getX() + col,
                        cell.getY(),
                        value.getDateTime().toDate()
                    ));

                }

                col++;
            }

            cell.setY(cell.getY() + 1);
        }

        return cell;
    }

    //
    // Compared
    //

    @Override
    public byte[] generateComparedExcelInStream(LanguageCode lang, List<Scope> scopes, Period period, Period comparedPeriod, InterfaceTypeCode interfaceCode, Map<String, Double> typicalResultValues, Map<String, Double> idealResultValues) throws IOException, WriteException, BiffException {


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


        ReportResultCollection allReportResultsCEFLeft = reportResultService.getReportResultsCEF(awacCalculator, scopes, period, comparedPeriod);
        MergedReportResultCollectionAggregation mergedReportResultCEFCollectionAggregation = reportResultService.mergeAsComparision(
            reportResultService.aggregate(allReportResultsCEFLeft),
            reportResultService.aggregate(allReportResultsRight)
        );


        // 2.1 Table
        String r_1 = "";
        for (Report report : awacCalculator.getReports()) {
            if (report.getRestrictedScope() == null) {
                r_1 = report.getCode().getKey();
            }
        }

        String organization = scopes.get(0).getOrganization().getName();
        String sites = "";
        for (Scope scope : scopes) {
            if (scope instanceof Site) {
                Site site = (Site) scope;
                if (sites.length() > 0) {
                    sites += ", ";
                }
                sites += site.getName();
            }
        }

        writeComparisionTable(r_1, "Résultat", null, organization, sites, period.getLabel() + " / " + comparedPeriod.getLabel(), lang, wb, merged, cellFormat, interfaceCode, typicalResultValues, idealResultValues);
        writeComparisionTable(r_1, "Résultat facteurs constants", comparedPeriod.getLabel(), organization, sites, period.getLabel() + " / " + comparedPeriod.getLabel(), lang, wb, mergedReportResultCEFCollectionAggregation, cellFormat, interfaceCode, typicalResultValues, idealResultValues);

        // 2.3 Survey
        for (Scope scope : scopes) {

            WritableSheet sheet = wb.createSheet("Données " + period.getLabel() + " - " + scope.getName(), wb.getNumberOfSheets());

            String thisSite = "";
            if (scope instanceof Site) {
                thisSite = ((Site) scope).getName();
            }

            insertHeader(sheet, null, organization, thisSite, period.getLabel(), cellFormat);

            Vector2I cell = new Vector2I(0, 4);
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

            WritableSheet sheet = wb.createSheet("Données " + comparedPeriod.getLabel() + " - " + scope.getName(), wb.getNumberOfSheets());

            String thisSite = "";
            if (scope instanceof Site) {
                thisSite = ((Site) scope).getName();
            }

            insertHeader(sheet, null, organization, thisSite, comparedPeriod.getLabel(), cellFormat);

            Vector2I cell = new Vector2I(0, 4);
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

    private void writeComparisionTable(String search, String title, String cefPeriod, String organization, String sites, String period, LanguageCode lang, WritableWorkbook wb, MergedReportResultCollectionAggregation merged, WritableCellFormat cellFormat, InterfaceTypeCode interfaceTypeCode, Map<String, Double> typicalResultValues, Map<String, Double> idealResultValues) throws WriteException {
		if (InterfaceTypeCode.HOUSEHOLD.equals(interfaceTypeCode) || InterfaceTypeCode.LITTLEEMITTER.equals(interfaceTypeCode) || InterfaceTypeCode.EVENT.equals(interfaceTypeCode)) {
			String typicalResultLabel = translate(getTypicalResultLabelKey(interfaceTypeCode), CodeList.TRANSLATIONS_INTERFACE, lang) + " (tCO2e)";
			String idealResultLabel = translate(getIdealResultLabelKey(interfaceTypeCode), CodeList.TRANSLATIONS_INTERFACE, lang) + " (tCO2e)";
			for (MergedReportResultAggregation aggregation : merged.getMergedReportResultAggregations()) {
				String reportKey = aggregation.getReportCode();
				String leftResultLabel = translate("SCOPE_SIMPLE", CodeList.TRANSLATIONS_INTERFACE, lang) + " " + aggregation.getLeftPeriod().getLabel() + " (tCO2e)";
				String rightResultLabel = translate("SCOPE_SIMPLE", CodeList.TRANSLATIONS_INTERFACE, lang) + " " + aggregation.getRightPeriod().getLabel() + " (tCO2e)";

				if (reportKey.equals(search)) {

					WritableSheet sheet = wb.createSheet(title, wb.getNumberOfSheets());

					insertHeader(sheet, cefPeriod, organization, sites, period, cellFormat);

					List<MergedReportResultIndicatorAggregation> scopeValuesByIndicator = aggregation.getMergedReportResultIndicatorAggregationList();

					sheet.addCell(new Label(0, 5, "Indicator", cellFormat));

					sheet.addCell(new Label(1, 5, leftResultLabel, cellFormat));
					sheet.addCell(new Label(2, 5, rightResultLabel, cellFormat));
					sheet.addCell(new Label(3, 5, typicalResultLabel, cellFormat));
					sheet.addCell(new Label(4, 5, idealResultLabel, cellFormat));


					int index = 6;
					for (MergedReportResultIndicatorAggregation row : scopeValuesByIndicator) {

						String indicatorKey = row.getIndicator();
						CodeLabel codeLabel = codeLabelService.findCodeLabelByCode(new Code(CodeList.INDICATOR, indicatorKey));

						sheet.addCell(new Label(0, index, codeLabel.getLabel(lang)));

						sheet.addCell(new Number(1, index, row.getLeftTotalValue()));
						sheet.addCell(new Number(2, index, row.getRightTotalValue()));
						sheet.addCell(new Number(3, index, typicalResultValues.get(indicatorKey)));
						sheet.addCell(new Number(4, index, idealResultValues.get(indicatorKey)));

						index++;
					}
				}
			}
		} else {
			for (MergedReportResultAggregation aggregation : merged.getMergedReportResultAggregations()) {
				String reportKey = aggregation.getReportCode();

				if (reportKey.equals(search)) {

					WritableSheet sheet = wb.createSheet(title, wb.getNumberOfSheets());

					insertHeader(sheet, cefPeriod, organization, sites, period, cellFormat);

					List<MergedReportResultIndicatorAggregation> scopeValuesByIndicator = aggregation.getMergedReportResultIndicatorAggregationList();

					sheet.addCell(new Label(0, 5, "Indicator", cellFormat));

					sheet.addCell(new Label(1, 5, translate("SCOPE_1", CodeList.TRANSLATIONS_INTERFACE, lang) + " (tCO2e)", cellFormat));
					sheet.addCell(new Label(2, 5, translate("SCOPE_2", CodeList.TRANSLATIONS_INTERFACE, lang) + " (tCO2e)", cellFormat));
					sheet.addCell(new Label(3, 5, translate("SCOPE_3", CodeList.TRANSLATIONS_INTERFACE, lang) + " (tCO2e)", cellFormat));
					sheet.addCell(new Label(4, 5, translate("OUT_OF_SCOPE", CodeList.TRANSLATIONS_INTERFACE, lang) + " (tCO2)", cellFormat));

					sheet.addCell(new Label(5, 5, translate("SCOPE_1", CodeList.TRANSLATIONS_INTERFACE, lang) + " (tCO2e)", cellFormat));
					sheet.addCell(new Label(6, 5, translate("SCOPE_2", CodeList.TRANSLATIONS_INTERFACE, lang) + " (tCO2e)", cellFormat));
					sheet.addCell(new Label(7, 5, translate("SCOPE_3", CodeList.TRANSLATIONS_INTERFACE, lang) + " (tCO2e)", cellFormat));
					sheet.addCell(new Label(8, 5, translate("OUT_OF_SCOPE", CodeList.TRANSLATIONS_INTERFACE, lang) + " (tCO2)", cellFormat));

					sheet.addCell(new Label(1, 4, aggregation.getLeftPeriod().getLabel(), cellFormat));
					sheet.addCell(new Label(5, 4, aggregation.getRightPeriod().getLabel(), cellFormat));

					sheet.mergeCells(1, 4, 4, 4);
					sheet.mergeCells(5, 4, 8, 4);


					int index = 6;
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
    }

    //
    // Utils
    //

    private void insertHeader(WritableSheet sheet, String cefPeriod, String organizationName, String sites, String period, WritableCellFormat cellFormat) throws WriteException {

        sheet.addCell(new Label(0, 0, "Organisation", cellFormat));
        sheet.addCell(new Label(0, 1, "Site(s)", cellFormat));
        sheet.addCell(new Label(0, 2, "Année", cellFormat));
        if (cefPeriod != null) {
            sheet.addCell(new Label(0, 3, "Facteurs utilisés", cellFormat));
        }

        sheet.addCell(new Label(1, 0, organizationName));
        sheet.addCell(new Label(1, 1, sites));
        sheet.addCell(new Label(1, 2, period));
        if (cefPeriod != null) {
            sheet.addCell(new Label(1, 3, cefPeriod));
        }

    }

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

	private String getTypicalResultLabelKey(InterfaceTypeCode interfaceTypeCode) {
		if (InterfaceTypeCode.HOUSEHOLD.equals(interfaceTypeCode)) {
			return "TYPICAL_HOUSEHOLD_TITLE";
		}
		if (InterfaceTypeCode.LITTLEEMITTER.equals(interfaceTypeCode)) {
			return "TYPICAL_LITTLEEMITTER_TITLE";
		}
		if (InterfaceTypeCode.EVENT.equals(interfaceTypeCode)) {
			return "TYPICAL_EVENT_TITLE";
		}
		return null;
	}

	private String getIdealResultLabelKey(InterfaceTypeCode interfaceTypeCode) {
		if (InterfaceTypeCode.HOUSEHOLD.equals(interfaceTypeCode)) {
			return "IDEAL_HOUSEHOLD_TITLE";
		}
		if (InterfaceTypeCode.LITTLEEMITTER.equals(interfaceTypeCode)) {
			return "IDEAL_LITTLEEMITTER_TITLE";
		}
		if (InterfaceTypeCode.EVENT.equals(interfaceTypeCode)) {
			return "IDEAL_EVENT_TITLE";
		}
		return null;
	}

}
