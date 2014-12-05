package eu.factorx.awac.service.impl;

import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.business.Site;
import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.code.label.CodeLabel;
import eu.factorx.awac.models.code.type.IndicatorIsoScopeCode;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.models.code.type.LanguageCode;
import eu.factorx.awac.models.forms.AwacCalculator;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.models.knowledge.Report;
import eu.factorx.awac.models.reporting.BaseActivityData;
import eu.factorx.awac.models.reporting.BaseActivityResult;
import eu.factorx.awac.models.reporting.ReportResult;
import eu.factorx.awac.service.*;
import eu.factorx.awac.service.impl.reporting.*;
import eu.factorx.awac.util.Colors;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import play.api.templates.Html;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Component
public class ResultPdfGeneratorServiceImpl implements ResultPdfGeneratorService {

    @Autowired
    private AwacCalculatorService     awacCalculatorService;
    @Autowired
    private ReportResultService       reportResultService;
    @Autowired
    private CodeLabelService          codeLabelService;
    @Autowired
    private QuestionSetAnswerService  questionSetAnswerService;
    @Autowired
    private PdfGenerator              pdfGenerator;
    @Autowired
    private SvgGenerator              svgGenerator;
    @Autowired
    private ResultSvgGeneratorService resultSvgGeneratorService;
    @Autowired
    private FactorValueService        factorValueService;
    @Autowired
    private BaseActivityResultService baseActivityResultService;


    private String translate(String code, CodeList cl, LanguageCode lang) {
        CodeLabel codeLabel = codeLabelService.findCodeLabelByCode(new Code(cl, code));
        if (codeLabel == null) {
            return code;
        } else {
            return codeLabel.getLabel(lang);
        }
    }


    @Override
    public byte[] generate(LanguageCode lang, List<Scope> scopes, Period period, Period comparedPeriod, InterfaceTypeCode interfaceCode) throws WriteException, IOException, BiffException {
        if (comparedPeriod == null) {
            return generateSimple(lang, scopes, period, interfaceCode);
        } else {
            return generateCompared(lang, scopes, period, comparedPeriod, interfaceCode);
        }
    }

    private byte[] generateSimple(LanguageCode lang, List<Scope> scopes, Period period, InterfaceTypeCode interfaceCode) throws WriteException, IOException, BiffException {

        AwacCalculator awacCalculator = awacCalculatorService.findByCode(interfaceCode);
        ReportResultCollection allReportResults = reportResultService.getReportResults(awacCalculator, scopes, period);

        String content = "\n" +
            "<!DOCTYPE html>\n" +
            "<head>\n" +
            "    <title>PDF test</title>\n" +
            "    <link rel=\"stylesheet\" href=\"/public/stylesheets/pdf.css\"/>\n" +
            "</head>\n" +
            "<body>\n";

        String r_1 = getReportKeyForCalculatorAndRestrictedIsoScope(awacCalculator, null);
        String r_2 = getReportKeyForCalculatorAndRestrictedIsoScope(awacCalculator, IndicatorIsoScopeCode.SCOPE1);
        String r_3 = getReportKeyForCalculatorAndRestrictedIsoScope(awacCalculator, IndicatorIsoScopeCode.SCOPE2);
        String r_4 = getReportKeyForCalculatorAndRestrictedIsoScope(awacCalculator, IndicatorIsoScopeCode.SCOPE3);


        // 0. Header
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

        content += "<table class=\"page-header\">";
        content += "<tr><td>Organisation :</td><td width=\"100%\">" + organization + "</td></tr>";
        content += "<tr><td>Site(s) :</td><td width=\"100%\">" + sites + "</td></tr>";
        content += "<tr><td>Année :</td><td width=\"100%\">" + period.getLabel() + "</td></tr>";
        content += "</table>";


        // 1. Report
        content += "<div>";
        content += writeTable(r_1, lang, allReportResults);
        content += "</div>";

        // 2. Graphs
        String histogram = writeHistogram(r_1, allReportResults);
        String donut1 = writeDonut(r_2, allReportResults);
        String donut2 = writeDonut(r_3, allReportResults);
        String donut3 = writeDonut(r_4, allReportResults);

        pdfGenerator.setMemoryResource("mem://svg/histogram", histogram);
        pdfGenerator.setMemoryResource("mem://svg/donut1", donut1);
        pdfGenerator.setMemoryResource("mem://svg/donut2", donut2);
        pdfGenerator.setMemoryResource("mem://svg/donut3", donut3);

        String kiviat = writeKiviat(r_1, allReportResults);
        pdfGenerator.setMemoryResource("mem://svg/kiviat", kiviat);


        // histogram
        content += "<div>";
        content += "<h1>" + translate("VALUES_BY_CATEGORY", CodeList.TRANSLATIONS_INTERFACE, lang) + "</h1>";
        content += "<img style=\"display: block; height: 6cm; width: auto;\" src=\"mem://svg/histogram\" /><br />";
        content += "<br/>";
        content += addLegend(r_1, lang, allReportResults, true);
        content += "</div>";

        // donuts
        content += "<div>";

        content += "<h1>" + translate("IMPACTS_PARTITION", CodeList.TRANSLATIONS_INTERFACE, lang) + "</h1>";

        // scope 1
        content += "<h2>" + translate("SCOPE_1", CodeList.TRANSLATIONS_INTERFACE, lang) + " : " + getTotal(r_2, lang, allReportResults) + " tCO2e</h2>";
        content += "<img style=\"display: inline-block; height: 4cm; width: auto;\" src=\"mem://svg/donut1\" />";
        content += "<br/>";
        content += "<br/>";
        content += addLegend(r_2, lang, allReportResults, false);

        // scope 2
        content += "<h2>" + translate("SCOPE_2", CodeList.TRANSLATIONS_INTERFACE, lang) + " : " + getTotal(r_3, lang, allReportResults) + " tCO2e</h2>";
        content += "<img style=\"display: inline-block; height: 4cm; width: auto;\" src=\"mem://svg/donut2\" />";
        content += "<br/>";
        content += "<br/>";
        content += addLegend(r_3, lang, allReportResults, false);

        // scope 3
        content += "<h2>" + translate("SCOPE_3", CodeList.TRANSLATIONS_INTERFACE, lang) + " : " + getTotal(r_4, lang, allReportResults) + " tCO2e</h2>";
        content += "<img style=\"display: inline-block; height: 4cm; width: auto;\" src=\"mem://svg/donut3\" />";
        content += "<br/>";
        content += "<br/>";
        content += addLegend(r_4, lang, allReportResults, false);

        content += "</div>";

        // web
        content += "<div>";
        content += "<h1>" + translate("KIVIAT_DIAGRAM", CodeList.TRANSLATIONS_INTERFACE, lang) + "</h1>";
        content += "<img style=\"display: inline-block; height: 10cm; width: auto;\" src=\"mem://svg/kiviat\" />";
        content += "<br/>";
        content += "<br/>";
        content += addLegend(r_1, lang, allReportResults, true);
        content += "</div>";

        // 3. Explanation
        content += "<div>";
        content += writeExplanation(lang, allReportResults);
        content += "</div>";

        content += "</body>";
        scala.collection.mutable.StringBuilder sb = new scala.collection.mutable.StringBuilder(content);
        Html html = new Html(sb);

        return pdfGenerator.toBytes(html);
    }

    private byte[] generateCompared(LanguageCode lang, List<Scope> scopes, Period period, Period comparedPeriod, InterfaceTypeCode interfaceCode) throws WriteException, IOException, BiffException {

        AwacCalculator awacCalculator = awacCalculatorService.findByCode(interfaceCode);
        ReportResultCollection allReportResultsLeft = reportResultService.getReportResults(awacCalculator, scopes, period);
        ReportResultCollection allReportResultsRight = reportResultService.getReportResults(awacCalculator, scopes, comparedPeriod);

        ReportResultCollectionAggregation left = reportResultService.aggregate(allReportResultsLeft);
        ReportResultCollectionAggregation right = reportResultService.aggregate(allReportResultsRight);

        MergedReportResultCollectionAggregation merged = reportResultService.mergeAsComparision(left, right);

        String content = "\n" +
            "<!DOCTYPE html>\n" +
            "<head>\n" +
            "    <title>PDF test</title>\n" +
            "    <link rel=\"stylesheet\" href=\"/public/stylesheets/pdf.css\"/>\n" +
            "</head>\n" +
            "<body>\n";

        String r_1 = getReportKeyForCalculatorAndRestrictedIsoScope(awacCalculator, null);
        String r_2 = getReportKeyForCalculatorAndRestrictedIsoScope(awacCalculator, IndicatorIsoScopeCode.SCOPE1);
        String r_3 = getReportKeyForCalculatorAndRestrictedIsoScope(awacCalculator, IndicatorIsoScopeCode.SCOPE2);
        String r_4 = getReportKeyForCalculatorAndRestrictedIsoScope(awacCalculator, IndicatorIsoScopeCode.SCOPE3);

        // 0. Header
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

        content += "<table class=\"page-header\">";
        content += "<tr><td>Organisation :</td><td width=\"100%\">" + organization + "</td></tr>";
        content += "<tr><td>Site(s) :</td><td width=\"100%\">" + sites + "</td></tr>";
        content += "<tr><td>Années :</td><td width=\"100%\">" + period.getLabel() + " / " + comparedPeriod.getLabel() + "</td></tr>";
        content += "</table>";

        // 1. Report
        content += writeTable(r_1, lang, merged);

        // 2. Graphs
        String histogram = writeHistogram(r_1, merged);
        List<String> donut1 = writeDonut(r_2, merged);
        List<String> donut2 = writeDonut(r_3, merged);
        List<String> donut3 = writeDonut(r_4, merged);

        String donut1L = donut1.get(0);
        String donut1R = donut1.get(1);
        String donut2L = donut2.get(0);
        String donut2R = donut2.get(1);
        String donut3L = donut3.get(0);
        String donut3R = donut3.get(1);

        pdfGenerator.setMemoryResource("mem://svg/histogram", histogram);
        pdfGenerator.setMemoryResource("mem://svg/donut1L", donut1L);
        pdfGenerator.setMemoryResource("mem://svg/donut1R", donut1R);
        pdfGenerator.setMemoryResource("mem://svg/donut2L", donut2L);
        pdfGenerator.setMemoryResource("mem://svg/donut2R", donut2R);
        pdfGenerator.setMemoryResource("mem://svg/donut3L", donut3L);
        pdfGenerator.setMemoryResource("mem://svg/donut3R", donut3R);

        String kiviat = writeKiviat(r_1, merged);
        pdfGenerator.setMemoryResource("mem://svg/kiviat", kiviat);

        // histogram
        content += "<h1>" + translate("VALUES_BY_CATEGORY", CodeList.TRANSLATIONS_INTERFACE, lang) + "</h1>";
        content += "<img style=\"display: block; height: 6cm; width: auto;\" src=\"mem://svg/histogram\" /><br />";
        content += "<br/>";
        content += addLegend(r_1, lang, merged, true);

        // donuts
        content += "<h1>" + translate("IMPACTS_PARTITION", CodeList.TRANSLATIONS_INTERFACE, lang) + "</h1>";

        // scope 1
        content += "<h2>" + translate("SCOPE_1", CodeList.TRANSLATIONS_INTERFACE, lang) + "</h2>";
        content += "<img style=\"display: inline-block; height: 4cm; width: auto;\" src=\"mem://svg/donut1L\" />";
        content += "<img style=\"display: inline-block; height: 4cm; width: auto;\" src=\"mem://svg/donut1R\" />";
        content += "<br/>";
        content += "<br/>";
        content += addLegend(r_2, lang, merged, false);

        // scope 2
        content += "<h2>" + translate("SCOPE_2", CodeList.TRANSLATIONS_INTERFACE, lang) + "</h2>";
        content += "<img style=\"display: inline-block; height: 4cm; width: auto;\" src=\"mem://svg/donut2L\" />";
        content += "<img style=\"display: inline-block; height: 4cm; width: auto;\" src=\"mem://svg/donut2R\" />";
        content += "<br/>";
        content += "<br/>";
        content += addLegend(r_3, lang, merged, false);

        // scope 3
        content += "<h2>" + translate("SCOPE_3", CodeList.TRANSLATIONS_INTERFACE, lang) + "</h2>";
        content += "<img style=\"display: inline-block; height: 4cm; width: auto;\" src=\"mem://svg/donut3L\" />";
        content += "<img style=\"display: inline-block; height: 4cm; width: auto;\" src=\"mem://svg/donut3R\" />";
        content += "<br/>";
        content += "<br/>";
        content += addLegend(r_4, lang, merged, false);

        // web
        content += "<h1>" + translate("KIVIAT_DIAGRAM", CodeList.TRANSLATIONS_INTERFACE, lang) + "</h1>";
        content += "<img style=\"display: inline-block; height: 10cm; width: auto;\" src=\"mem://svg/kiviat\" />";
        content += "<br/>";
        content += "<br/>";
        content += addLegend(r_1, lang, merged, true);

        content += "</body>";
        scala.collection.mutable.StringBuilder sb = new scala.collection.mutable.StringBuilder(content);
        Html html = new Html(sb);

        return pdfGenerator.toBytes(html);
    }


    // Legend

    private String addLegend(String reportKey, LanguageCode lang, ReportResultCollection allReportResults, boolean numbers) {
        String content = "";
        NumberFormat nf = NumberFormat.getInstance(Locale.forLanguageTag(lang.getKey()));
        nf.setMaximumFractionDigits(12);
        for (ReportResult reportResult : allReportResults.getReportResults()) {
            if (reportResult.getReport().getCode().getKey().equals(reportKey)) {

                int count = 0;
                for (Map.Entry<String, List<Double>> e : reportResultService.getScopeValuesByIndicator(reportResult).entrySet()) {
                    if (e.getValue().get(0) > 0) {
                        count++;
                    }
                }
                if (count > 0) {
                    int i = 0;
                    content += "<table style=\"display:table; width: auto;\">";
                    for (Map.Entry<String, List<Double>> e : reportResultService.getScopeValuesByIndicator(reportResult).entrySet()) {
                        Double total = e.getValue().get(0);
                        if (total > 0) {
                            content += "<tr>";
                            if (numbers) {
                                content += "<td><span class=\"circled-number\">" + (i + 1) + "</span></td>";
                            } else {
                                content += "<td><span style=\"display: inline-block; width: 8px; height 8px; background: #" + Colors.makeGoodColorForSerieElement(i + 1, count) + "\">&nbsp;</span></td>";
                            }
                            content += "<td>" + translate(e.getKey(), CodeList.INDICATOR, lang) + "</td>";
                            content += "<td style=\"text-align: right\">" + nf.format(total) + " tCO2e" + "</td>";
                            content += "</tr>";
                            i++;
                        }
                    }
                    content += "</table>";
                }
            }
        }
        return content;
    }

    private String addLegend(String reportKey, LanguageCode lang, MergedReportResultCollectionAggregation merged, boolean numbers) {
        String content = "";
        NumberFormat nf = NumberFormat.getInstance(Locale.forLanguageTag(lang.getKey()));
        nf.setMaximumFractionDigits(12);
        for (MergedReportResultAggregation reportResult : merged.getMergedReportResultAggregations()) {
            if (reportResult.getReportCode().equals(reportKey)) {

                int count = 0;
                for (MergedReportResultIndicatorAggregation e : reportResult.getMergedReportResultIndicatorAggregationList()) {
                    if (e.getLeftTotalValue() > 0 || e.getRightTotalValue() > 0) {
                        count++;
                    }
                }

                if (count > 0) {
                    int i = 0;
                    content += "<table style=\"display:table; width: auto;\">";

                    content += "<thead>";

                    content += "<tr>";

                    content += "<th></th>";
                    content += "<th></th>";
                    content += "<th style=\"text-align: center\">" + reportResult.getLeftPeriod().getLabel() + "</th>";
                    content += "<th style=\"text-align: center\">" + reportResult.getRightPeriod().getLabel() + "</th>";

                    content += "</tr>";

                    content += "</thead>";
                    content += "<tbody>";
                    for (MergedReportResultIndicatorAggregation e : reportResult.getMergedReportResultIndicatorAggregationList()) {
                        Double left = e.getLeftTotalValue();
                        Double right = e.getRightTotalValue();
                        if (left > 0 || right > 0) {
                            content += "<tr>";
                            if (numbers) {
                                content += "<td><span class=\"circled-number\">" + (i + 1) + "</span></td>";
                            } else {
                                content += "<td><span style=\"display: inline-block; width: 8px; height 8px; background: #" + Colors.makeGoodColorForSerieElement(i + 1, count) + "\">&nbsp;</span></td>";
                            }
                            content += "<td>" + translate(e.getIndicator(), CodeList.INDICATOR, lang) + "</td>";
                            content += "<td style=\"text-align: right\">" + nf.format(left) + " tCO2e" + "</td>";
                            content += "<td style=\"text-align: right\">" + nf.format(right) + " tCO2e" + "</td>";
                            content += "</tr>";
                            i++;
                        }
                    }
                    content += "</tbody>";
                    content += "</table>";
                }
            }
        }
        return content;
    }

    // Histogram

    private String writeHistogram(String search, ReportResultCollection allReportResults) throws BiffException, IOException, WriteException {
        String content = "";
        for (ReportResult reportResult : allReportResults.getReportResults()) {
            if (reportResult.getReport().getCode().getKey().equals(search)) {
                ReportResultAggregation reportResultAggregation = reportResultService.aggregate(reportResult);
                content += resultSvgGeneratorService.getHistogram(reportResultAggregation);
            }
        }
        return content;
    }

    private String writeHistogram(String search, MergedReportResultCollectionAggregation merged) throws BiffException, IOException, WriteException {
        String content = "";
        for (MergedReportResultAggregation mergedReportResultAggregation : merged.getMergedReportResultAggregations()) {
            if (mergedReportResultAggregation.getReportCode().equals(search)) {
                content += resultSvgGeneratorService.getHistogram(mergedReportResultAggregation);
            }
        }
        return content;
    }

    // Donut

    private String writeDonut(String search, ReportResultCollection allReportResults) throws BiffException, IOException, WriteException {

        String content = "";

        for (ReportResult reportResult : allReportResults.getReportResults()) {
            String key = reportResult.getReport().getCode().getKey();
            if (key.equals(search)) {
                ReportResultAggregation reportResultAggregation = reportResultService.aggregate(reportResult);
                content += resultSvgGeneratorService.getDonut(reportResultAggregation);
            }
        }

        return content;
    }

    private List<String> writeDonut(String search, MergedReportResultCollectionAggregation merged) throws BiffException, IOException, WriteException {
        for (MergedReportResultAggregation mergedReportResultAggregation : merged.getMergedReportResultAggregations()) {
            String key = mergedReportResultAggregation.getReportCode();
            if (key.equals(search)) {
                return Arrays.asList(
                    resultSvgGeneratorService.getLeftDonut(mergedReportResultAggregation),
                    resultSvgGeneratorService.getRightDonut(mergedReportResultAggregation)
                );
            }
        }
        throw new IllegalArgumentException();
    }

    // Kiviat

    private String writeKiviat(String search, ReportResultCollection allReportResults) throws BiffException, IOException, WriteException {
        String content = "";
        for (ReportResult reportResult : allReportResults.getReportResults()) {
            String key = reportResult.getReport().getCode().getKey();
            if (key.equals(search)) {
                ReportResultAggregation reportResultAggregation = reportResultService.aggregate(reportResult);
                content += resultSvgGeneratorService.getWeb(reportResultAggregation);
            }
        }
        return content;
    }

    private String writeKiviat(String search, MergedReportResultCollectionAggregation merged) throws BiffException, IOException, WriteException {
        String content = "";
        for (MergedReportResultAggregation mergedReportResultAggregation : merged.getMergedReportResultAggregations()) {
            String key = mergedReportResultAggregation.getReportCode();
            if (key.equals(search)) {
                content += resultSvgGeneratorService.getWeb(mergedReportResultAggregation);
            }
        }
        return content;
    }

    // Explanation

    private String writeExplanation(LanguageCode lang, ReportResultCollection allReportResults) {
        String content = "<h1>Explication</h1>";

        for (ReportLogEntry logEntry : allReportResults.getLogEntries()) {

            if (logEntry instanceof Contribution) {
                Contribution entry = (Contribution) logEntry;
                BaseActivityResult bar = entry.getBar();
                BaseActivityData activityData = bar.getActivityData();

                content += "<p>";
                content += translate("RESULTS_EXPLANATION_CONTRIB_PART1", CodeList.TRANSLATIONS_INTERFACE, lang);
                content += " ";
                content += translate(bar.getBaseIndicator().getActivityCategory().getKey(), CodeList.ActivityCategory, lang);
                content += "/";
                content += translate(bar.getBaseIndicator().getActivitySubCategory().getKey(), CodeList.ActivitySubCategory, lang);
                content += "/";
                content += translate(activityData.getActivityType().getKey(), CodeList.ActivityType, lang);
                content += "/";
                content += translate(activityData.getActivitySource().getKey(), CodeList.ActivitySource, lang);
                content += " ";
                content += translate("RESULTS_EXPLANATION_CONTRIB_PART2", CodeList.TRANSLATIONS_INTERFACE, lang);
                content += " ";
                content += activityData.getValue();
                content += " ";
                content += activityData.getUnit().getSymbol();
                content += "<br />&nbsp;&nbsp;&nbsp;&nbsp;";
                content += translate("RESULTS_EXPLANATION_CONTRIB_PART3", CodeList.TRANSLATIONS_INTERFACE, lang);
                content += " ";
                content += translate(bar.getBaseIndicator().getIndicatorCategory().getKey(), CodeList.IndicatorCategory, lang);
                content += "/";
                content += translate(activityData.getActivityType().getKey(), CodeList.ActivityType, lang);
                content += "/";
                content += translate(activityData.getActivitySource().getKey(), CodeList.ActivitySource, lang);
                content += " ";
                content += translate("RESULTS_EXPLANATION_CONTRIB_PART4", CodeList.TRANSLATIONS_INTERFACE, lang);
                content += " ";
                content += factorValueService.findByFactorAndYear(bar.getFactor(), bar.getYear()).getValue();
                content += " ";
                content += bar.getFactor().getUnitOut().getSymbol();
                content += " ";
                content += translate("RESULTS_EXPLANATION_CONTRIB_PART5", CodeList.TRANSLATIONS_INTERFACE, lang);
                content += " ";
                content += bar.getFactor().getUnitIn().getSymbol();
                content += "<br />&nbsp;&nbsp;&nbsp;&nbsp;";
                content += translate("RESULTS_EXPLANATION_CONTRIB_PART6", CodeList.TRANSLATIONS_INTERFACE, lang);
                content += " ";
                content += baseActivityResultService.getValueForYear(bar);
                content += " ";
                content += bar.getBaseIndicator().getUnit().getSymbol();
                content += "</p>";
            }

            if (logEntry instanceof NoSuitableFactor) {
                NoSuitableFactor entry = (NoSuitableFactor) logEntry;

                content += "<p>";
                content += translate("RESULTS_EXPLANATION_NOFACTOR_PART1", CodeList.TRANSLATIONS_INTERFACE, lang);
                content += " ";
                content += translate(entry.getBaseIndicator().getActivityCategory().getKey(), CodeList.ActivityCategory, lang);
                content += "/";
                content += translate(entry.getBaseIndicator().getActivitySubCategory().getKey(), CodeList.ActivitySubCategory, lang);
                content += "/";
                content += translate(entry.getBad().getActivityType().getKey(), CodeList.ActivityType, lang);
                content += "/";
                content += translate(entry.getBad().getActivitySource().getKey(), CodeList.ActivitySource, lang);
                content += " ";
                content += translate("RESULTS_EXPLANATION_NOFACTOR_PART2", CodeList.TRANSLATIONS_INTERFACE, lang);
                content += " ";
                content += entry.getBad().getValue();
                content += " ";
                content += entry.getBad().getUnit().getSymbol();
                content += "<br />&nbsp;&nbsp;&nbsp;&nbsp;";
                content += translate("RESULTS_EXPLANATION_NOFACTOR_PART3", CodeList.TRANSLATIONS_INTERFACE, lang);
                content += " ";
                content += translate(entry.getBaseIndicator().getIndicatorCategory().getKey(), CodeList.IndicatorCategory, lang);
                content += "/";
                content += translate(entry.getBad().getActivityType().getKey(), CodeList.ActivityType, lang);
                content += "/";
                content += translate(entry.getBad().getActivitySource().getKey(), CodeList.ActivitySource, lang);
                content += " ";
                content += translate("RESULTS_EXPLANATION_NOFACTOR_PART4", CodeList.TRANSLATIONS_INTERFACE, lang);

                content += "</p>";
            }

            if (logEntry instanceof LowerRankInGroup) {
                LowerRankInGroup entry = (LowerRankInGroup) logEntry;

                content += "<p>";
                content += translate("RESULTS_EXPLANATION_LOWER_RANK_PART1", CodeList.TRANSLATIONS_INTERFACE, lang);
                content += " ";
                content += translate(entry.getBaseActivityData().getActivityCategory().getKey(), CodeList.ActivityCategory, lang);
                content += "/";
                content += translate(entry.getBaseActivityData().getActivitySubCategory().getKey(), CodeList.ActivitySubCategory, lang);
                content += "/";
                content += translate(entry.getBaseActivityData().getActivityType().getKey(), CodeList.ActivityType, lang);
                content += "/";
                content += translate(entry.getBaseActivityData().getActivitySource().getKey(), CodeList.ActivitySource, lang);
                content += " ";
                content += translate("RESULTS_EXPLANATION_LOWER_RANK_PART2", CodeList.TRANSLATIONS_INTERFACE, lang);
                content += " ";
                content += entry.getBaseActivityData().getValue();
                content += "/";
                content += entry.getBaseActivityData().getUnit().getSymbol();
                content += " ";
                content += translate("RESULTS_EXPLANATION_LOWER_RANK_PART3", CodeList.TRANSLATIONS_INTERFACE, lang);

                content += "</p>";
            }

            if (logEntry instanceof NoSuitableIndicator) {
                NoSuitableIndicator entry = (NoSuitableIndicator) logEntry;

                content += "<p>";
                content += translate("RESULTS_EXPLANATION_NOINDICATOR_PART1", CodeList.TRANSLATIONS_INTERFACE, lang);
                content += " ";
                content += translate(entry.getBaseActivityData().getActivityCategory().getKey(), CodeList.ActivityCategory, lang);
                content += "/";
                content += translate(entry.getBaseActivityData().getActivitySubCategory().getKey(), CodeList.ActivitySubCategory, lang);
                content += "/";
                content += translate(entry.getBaseActivityData().getActivityType().getKey(), CodeList.ActivityType, lang);
                content += "/";
                content += translate(entry.getBaseActivityData().getActivitySource().getKey(), CodeList.ActivitySource, lang);
                content += " ";
                content += translate("RESULTS_EXPLANATION_NOINDICATOR_PART2", CodeList.TRANSLATIONS_INTERFACE, lang);
                content += " ";
                content += entry.getBaseActivityData().getValue();
                content += "/";
                content += entry.getBaseActivityData().getUnit().getSymbol();
                content += " ";
                content += translate("RESULTS_EXPLANATION_NOINDICATOR_PART3", CodeList.TRANSLATIONS_INTERFACE, lang);

                content += "</p>";
            }

        }
        return content;
    }

    // Table

    private String writeTable(String search, LanguageCode lang, ReportResultCollection allReportResults) {
        NumberFormat nf = NumberFormat.getInstance(Locale.forLanguageTag(lang.getKey()));
        nf.setMaximumFractionDigits(12);
        String content = "<h1>Résultat</h1>";
        for (ReportResult reportResult : allReportResults.getReportResults()) {

            if (reportResult.getReport().getCode().getKey().equals(search)) {
                content += "<table>";

                content += "<thead>";
                content += "<tr>";
                content += "<th class=\"header\"></th>";
                content += "<th class=\"header\">" + translate("SCOPE_1", CodeList.TRANSLATIONS_INTERFACE, lang) + " (tCO2e)</th>";
                content += "<th class=\"header\">" + translate("SCOPE_2", CodeList.TRANSLATIONS_INTERFACE, lang) + " (tCO2e)</th>";
                content += "<th class=\"header\">" + translate("SCOPE_3", CodeList.TRANSLATIONS_INTERFACE, lang) + " (tCO2e)</th>";
                content += "<th class=\"header\">" + translate("OUT_OF_SCOPE", CodeList.TRANSLATIONS_INTERFACE, lang) + " (tCO2)</th>";
                content += "</tr>";
                content += "</thead>";

                content += "<tbody>";

                Map<String, List<Double>> activityResults = reportResultService.getScopeValuesByIndicator(reportResult);

                double totalScope1 = 0.0;
                double totalScope2 = 0.0;
                double totalScope3 = 0.0;
                double totalOutOfScope = 0.0;

                for (Map.Entry<String, List<Double>> entry : activityResults.entrySet()) {

                    content += "<tr>";
                    content += "<td class=\"dotted\" style=\"white-space: normal\">" + translate(entry.getKey(), CodeList.INDICATOR, lang) + "</td>";
                    content += "<td class=\"dotted\">" + nf.format(entry.getValue().get(1)) + "</td>";
                    content += "<td class=\"dotted\">" + nf.format(entry.getValue().get(2)) + "</td>";
                    content += "<td class=\"dotted\">" + nf.format(entry.getValue().get(3)) + "</td>";
                    content += "<td class=\"dotted\">" + nf.format(entry.getValue().get(4)) + "</td>";
                    content += "</tr>";

                    totalScope1 += entry.getValue().get(1);
                    totalScope2 += entry.getValue().get(2);
                    totalScope3 += entry.getValue().get(3);
                    totalOutOfScope += entry.getValue().get(4);

                }
                content += "</tbody>";

                content += "<tfoot>";
                content += "<tr>";
                content += "<th class=\"footer\" style=\"text-align: right\">" + translate("RESULTS_TOTAL", CodeList.TRANSLATIONS_INTERFACE, lang) + "</th>";
                content += "<th class=\"footer\">" + nf.format(totalScope1) + "</th>";
                content += "<th class=\"footer\">" + nf.format(totalScope2) + "</th>";
                content += "<th class=\"footer\">" + nf.format(totalScope3) + "</th>";
                content += "<th class=\"footer\">" + nf.format(totalOutOfScope) + "</th>";
                content += "</tr>";
                content += "</tfoot>";

                content += "</table>";
            }
        }
        return content;
    }

    private String writeTable(String search, LanguageCode lang, MergedReportResultCollectionAggregation merged) {

        NumberFormat nf = NumberFormat.getInstance(Locale.forLanguageTag(lang.getKey()));
        nf.setMaximumFractionDigits(12);

        String content = "<h1>Résultat</h1>";
        for (MergedReportResultAggregation mrra : merged.getMergedReportResultAggregations()) {

            if (mrra.getReportCode().equals(search)) {
                content += "<table>";

                content += "<thead>";

                content += "<tr>";
                content += "<th class=\"header\"></th>";
                content += "<th class=\"header\" colspan=\"4\">" + mrra.getLeftPeriod().getLabel() + "</th>";
                content += "<th class=\"header\" colspan=\"4\">" + mrra.getRightPeriod().getLabel() + "</th>";
                content += "</tr>";

                content += "<tr>";
                content += "<th class=\"header\"></th>";
                content += "<th class=\"header\">" + translate("SCOPE_1", CodeList.TRANSLATIONS_INTERFACE, lang) + " (tCO2e)</th>";
                content += "<th class=\"header\">" + translate("SCOPE_2", CodeList.TRANSLATIONS_INTERFACE, lang) + " (tCO2e)</th>";
                content += "<th class=\"header\">" + translate("SCOPE_3", CodeList.TRANSLATIONS_INTERFACE, lang) + " (tCO2e)</th>";
                content += "<th class=\"header\">" + translate("OUT_OF_SCOPE", CodeList.TRANSLATIONS_INTERFACE, lang) + " (tCO2e)</th>";
                content += "<th class=\"header\">" + translate("SCOPE_1", CodeList.TRANSLATIONS_INTERFACE, lang) + " (tCO2e)</th>";
                content += "<th class=\"header\">" + translate("SCOPE_2", CodeList.TRANSLATIONS_INTERFACE, lang) + " (tCO2e)</th>";
                content += "<th class=\"header\">" + translate("SCOPE_3", CodeList.TRANSLATIONS_INTERFACE, lang) + " (tCO2e)</th>";
                content += "<th class=\"header\">" + translate("OUT_OF_SCOPE", CodeList.TRANSLATIONS_INTERFACE, lang) + " (tCO2e)</th>";
                content += "</tr>";

                content += "</thead>";

                double totalScope1Left = 0.0;
                double totalScope2Left = 0.0;
                double totalScope3Left = 0.0;
                double totalOutOfScopeLeft = 0.0;
                double totalScope1Right = 0.0;
                double totalScope2Right = 0.0;
                double totalScope3Right = 0.0;
                double totalOutOfScopeRight = 0.0;

                content += "<tbody>";

                for (MergedReportResultIndicatorAggregation entry : mrra.getMergedReportResultIndicatorAggregationList()) {

                    content += "<tr>";

                    content += "<td class=\"dotted\" style=\"white-space: normal\">" + translate(entry.getIndicator(), CodeList.INDICATOR, lang) + "</td>";

                    content += "<td class=\"dotted\">" + nf.format(entry.getLeftScope1Value()) + "</td>";
                    content += "<td class=\"dotted\">" + nf.format(entry.getLeftScope2Value()) + "</td>";
                    content += "<td class=\"dotted\">" + nf.format(entry.getLeftScope3Value()) + "</td>";
                    content += "<td class=\"dotted\">" + nf.format(entry.getLeftOutOfScopeValue()) + "</td>";

                    content += "<td class=\"dotted\">" + nf.format(entry.getRightScope1Value()) + "</td>";
                    content += "<td class=\"dotted\">" + nf.format(entry.getRightScope2Value()) + "</td>";
                    content += "<td class=\"dotted\">" + nf.format(entry.getRightScope3Value()) + "</td>";
                    content += "<td class=\"dotted\">" + nf.format(entry.getRightOutOfScopeValue()) + "</td>";

                    content += "</tr>";

                    totalScope1Left += entry.getLeftScope1Value();
                    totalScope2Left += entry.getLeftScope2Value();
                    totalScope3Left += entry.getLeftScope3Value();
                    totalOutOfScopeLeft += entry.getLeftOutOfScopeValue();

                    totalScope1Right += entry.getRightScope1Value();
                    totalScope2Right += entry.getRightScope2Value();
                    totalScope3Right += entry.getRightScope3Value();
                    totalOutOfScopeRight += entry.getRightOutOfScopeValue();

                }
                content += "</tbody>";


                content += "<tfoot>";
                content += "<tr>";
                content += "<th class=\"footer\" style=\"text-align: right\">" + translate("RESULTS_TOTAL", CodeList.TRANSLATIONS_INTERFACE, lang) + "</th>";
                content += "<th class=\"footer\">" + nf.format(totalScope1Left) + "</th>";
                content += "<th class=\"footer\">" + nf.format(totalScope2Left) + "</th>";
                content += "<th class=\"footer\">" + nf.format(totalScope3Left) + "</th>";
                content += "<th class=\"footer\">" + nf.format(totalOutOfScopeLeft) + "</th>";
                content += "<th class=\"footer\">" + nf.format(totalScope1Right) + "</th>";
                content += "<th class=\"footer\">" + nf.format(totalScope2Right) + "</th>";
                content += "<th class=\"footer\">" + nf.format(totalScope3Right) + "</th>";
                content += "<th class=\"footer\">" + nf.format(totalOutOfScopeRight) + "</th>";
                content += "</tr>";
                content += "</tfoot>";

                content += "</table>";
            }
        }
        return content;

    }

    // Utils

    private String getReportKeyForCalculatorAndRestrictedIsoScope(AwacCalculator awacCalculator, IndicatorIsoScopeCode scope) {

        for (Report report : awacCalculator.getReports()) {
            if (report.getRestrictedScope() == null) {
                if (scope == null) {
                    return report.getCode().getKey();
                }
            } else {
                if (report.getRestrictedScope().equals(scope)) {
                    return report.getCode().getKey();
                }
            }

        }

        throw new IllegalArgumentException("No report found for calculator id=" + awacCalculator.getId() + " and restrictedIsScope=" + scope);
    }

    private String getTotal(String reportKey, LanguageCode lang, ReportResultCollection allReportResults) {
        double total = 0.0;
        for (ReportResult reportResult : allReportResults.getReportResults()) {
            if (reportResult.getReport().getCode().getKey().equals(reportKey)) {
                for (Map.Entry<String, List<Double>> e : reportResultService.getScopeValuesByIndicator(reportResult).entrySet()) {
                    total += e.getValue().get(0);
                }
                break;
            }
        }
        NumberFormat nf = NumberFormat.getInstance(Locale.forLanguageTag(lang.getKey()));
        nf.setMaximumFractionDigits(12);
        return nf.format(total);
    }

}