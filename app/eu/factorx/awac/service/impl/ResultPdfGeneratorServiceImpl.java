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
import eu.factorx.awac.service.*;
import eu.factorx.awac.service.impl.reporting.*;
import eu.factorx.awac.util.Colors;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import play.api.templates.Html;

import java.io.IOException;
import java.util.List;
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

	private String translate(String code, CodeList cl, LanguageCode lang) {
		CodeLabel codeLabel = codeLabelService.findCodeLabelByCode(new Code(cl, code));
		if (codeLabel == null) {
			return code;
		} else {
			return codeLabel.getLabel(lang);
		}
	}


	@Override
	public byte[] generate(LanguageCode lang, List<Scope> scopes, Period period, InterfaceTypeCode interfaceCode) throws WriteException, IOException, BiffException {


		AwacCalculator awacCalculator = awacCalculatorService.findByCode(interfaceCode);

		ReportResultCollection allReportResults = reportResultService.getReportResults(awacCalculator, scopes, period);


		String content = "\n" +
			"<!DOCTYPE html>\n" +
			"<head>\n" +
			"    <title>PDF test</title>\n" +
			"    <link rel=\"stylesheet\" href=\"/public/stylesheets/pdf.css\"/>\n" +
			"</head>\n" +
			"<body>\n";


		// 1. Report
		content += writeTable(lang, allReportResults);

		// 2. Explanation
		content += writeExplanation(lang, allReportResults);

		// 3. Graphs
		String histogram = writeHistogram(lang, allReportResults);
		String donut1 = writeDonut("R_2", lang, allReportResults);
		String donut2 = writeDonut("R_3", lang, allReportResults);
		String donut3 = writeDonut("R_4", lang, allReportResults);

		pdfGenerator.setMemoryResource("mem://svg/histogram", histogram);
		pdfGenerator.setMemoryResource("mem://svg/donut1", donut1);
		pdfGenerator.setMemoryResource("mem://svg/donut2", donut2);
		pdfGenerator.setMemoryResource("mem://svg/donut3", donut3);


		// histogram
		content += "<h1>" + translate("VALUES_BY_CATEGORY", CodeList.TRANSLATIONS_INTERFACE, lang) + "</h1>";
		content += "<img style=\"display: block; height: 6cm; width: auto;\" src=\"mem://svg/histogram\" /><br />";
		content += addLegend("R_1", lang, allReportResults, true);

		// donuts
		content += "<h1>" + translate("IMPACTS_PARTITION", CodeList.TRANSLATIONS_INTERFACE, lang) + "</h1>";

		// scope 1
		content += "<h2>" + translate("SCOPE_1", CodeList.TRANSLATIONS_INTERFACE, lang) + " : tCO2e</h2>";
		content += "<center>";
		content += "<img style=\"display: inline-block; height: 4cm; width: auto;\" src=\"mem://svg/donut1\" />";
		content += "</center>";
		content += "<br/>";
		content += addLegend("R_2", lang, allReportResults, false);

		// scope 2
		content += "<h2>" + translate("SCOPE_2", CodeList.TRANSLATIONS_INTERFACE, lang) + " : tCO2e</h2>";
		content += "<center>";
		content += "<img style=\"display: inline-block; height: 4cm; width: auto;\" src=\"mem://svg/donut2\" /><br />";
		content += "</center>";
		content += "<br/>";
		content += addLegend("R_3", lang, allReportResults, false);

		// scope 3
		content += "<h2>" + translate("SCOPE_3", CodeList.TRANSLATIONS_INTERFACE, lang) + " : tCO2e</h2>";
		content += "<center>";
		content += "<img style=\"display: inline-block; height: 4cm; width: auto;\" src=\"mem://svg/donut3\" /><br />";
		content += "</center>";
		content += "<br/>";
		content += addLegend("R_4", lang, allReportResults, false);

		content += "</body>";
		scala.collection.mutable.StringBuilder sb = new scala.collection.mutable.StringBuilder(content);
		Html html = new Html(sb);

		return pdfGenerator.toBytes(html);
	}

	private String addLegend(String reportKey, LanguageCode lang, ReportResultCollection allReportResults, boolean numbers) {
		String content = "";
		for (ReportResult reportResult : allReportResults.getReportResults()) {
			if (reportResult.getReport().getCode().getKey().equals(reportKey)) {

				int count = 0;
				for (Map.Entry<String, List<Double>> e : reportResult.getScopeValuesByIndicator().entrySet()) {
					if (e.getValue().get(0) > 0) {
						count++;
					}
				}

				int i = 0;
				content += "<table style=\"display:table; width: auto; margin: auto; border:solid 3px red;\">";
				for (Map.Entry<String, List<Double>> e : reportResult.getScopeValuesByIndicator().entrySet()) {
					Double total = e.getValue().get(0);
					if (total > 0) {
						content += "<tr>";

						if (numbers) {
							content += "<td><span class=\"circled-number\">" + (i + 1) + "</span></td>";
						} else {
							content += "<td><span style=\"display: inline-block; width: 8px; height 8px; background: #" + Colors.makeGoodColorForSerieElement(i+1, count) + "\">&nbsp;</span></td>";
						}

						content += "<td>" + translate(e.getKey(), CodeList.INDICATOR, lang) + "</td>";
						content += "<td>" + e.getValue().get(0) + " tCO2e" + "</td>";

						content += "</tr>";
						i++;
					}
				}
				content += "</table>";

			}
		}
		return content;
	}

	private String writeHistogram(LanguageCode lang, ReportResultCollection allReportResults) throws BiffException, IOException, WriteException {

		String content = "";

		for (ReportResult reportResult : allReportResults.getReportResults()) {
			if (reportResult.getReport().getCode().getKey().equals("R_1")) {
				ReportResultAggregation reportResultAggregation = reportResultService.aggregate(reportResult);
				content += resultSvgGeneratorService.getHistogram(reportResultAggregation);
			}
		}

		return content;
	}

	private String writeDonut(String search, LanguageCode lang, ReportResultCollection allReportResults) throws BiffException, IOException, WriteException {

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

	private String writeExplanation(LanguageCode lang, ReportResultCollection allReportResults) {
		String content = "<h1>Explication</h1>";

		for (ReportLogEntry logEntry : allReportResults.getLogEntries()) {

			if (logEntry instanceof Contribution) {
				Contribution entry = (Contribution) logEntry;

				content += "<p>";
				content += translate("RESULTS_EXPLANATION_CONTRIB_PART1", CodeList.TRANSLATIONS_INTERFACE, lang);
				content += " ";
				content += translate(entry.getBar().getBaseIndicator().getActivityCategory().getKey(), CodeList.ActivityCategory, lang);
				content += "/";
				content += translate(entry.getBar().getBaseIndicator().getActivitySubCategory().getKey(), CodeList.ActivitySubCategory, lang);
				content += "/";
				content += translate(entry.getBar().getActivityData().getActivityType().getKey(), CodeList.ActivityType, lang);
				content += "/";
				content += translate(entry.getBar().getActivityData().getActivitySource().getKey(), CodeList.ActivitySource, lang);
				content += " ";
				content += translate("RESULTS_EXPLANATION_CONTRIB_PART2", CodeList.TRANSLATIONS_INTERFACE, lang);
				content += " ";
				content += entry.getBar().getActivityData().getValue();
				content += " ";
				content += entry.getBar().getActivityData().getUnit().getSymbol();
				content += "<br />&nbsp;&nbsp;&nbsp;&nbsp;";
				content += translate("RESULTS_EXPLANATION_CONTRIB_PART3", CodeList.TRANSLATIONS_INTERFACE, lang);
				content += " ";
				content += translate(entry.getBar().getBaseIndicator().getIndicatorCategory().getKey(), CodeList.IndicatorCategory, lang);
				content += "/";
				content += translate(entry.getBar().getActivityData().getActivityType().getKey(), CodeList.ActivityType, lang);
				content += "/";
				content += translate(entry.getBar().getActivityData().getActivitySource().getKey(), CodeList.ActivitySource, lang);
				content += " ";
				content += translate("RESULTS_EXPLANATION_CONTRIB_PART4", CodeList.TRANSLATIONS_INTERFACE, lang);
				content += " ";
				content += entry.getBar().getFactor().getCurrentValue();
				content += " ";
				content += entry.getBar().getFactor().getUnitOut().getSymbol();
				content += " ";
				content += translate("RESULTS_EXPLANATION_CONTRIB_PART5", CodeList.TRANSLATIONS_INTERFACE, lang);
				content += " ";
				content += entry.getBar().getFactor().getUnitIn().getSymbol();
				content += "<br />&nbsp;&nbsp;&nbsp;&nbsp;";
				content += translate("RESULTS_EXPLANATION_CONTRIB_PART6", CodeList.TRANSLATIONS_INTERFACE, lang);
				content += " ";
				content += entry.getBar().getNumericValue();
				content += " ";
				content += entry.getBar().getBaseIndicator().getUnit().getSymbol();
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

	private String writeTable(LanguageCode lang, ReportResultCollection allReportResults) {
		String content = "<h1>Rapport</h1>";
		for (ReportResult reportResult : allReportResults.getReportResults()) {

			if (reportResult.getReport().getCode().getKey().equals("R_1")) {
				content += "<table>";

				content += "<thead>";
				content += "<tr>";
				content += "<th class=\"header\">Indicateur</th>";
				content += "<th class=\"header\">Scope 1</th>";
				content += "<th class=\"header\">Scope 2</th>";
				content += "<th class=\"header\">Scope 3</th>";
				content += "<th class=\"header\">Hors scope</th>";
				content += "</tr>";
				content += "</thead>";


				content += "<tbody>";

				Map<String, List<Double>> activityResults = reportResult.getScopeValuesByIndicator();

				for (Map.Entry<String, List<Double>> entry : activityResults.entrySet()) {

					content += "<tr>";
					content += "<td class=\"dotted\">";
					content += translate(entry.getKey(), CodeList.INDICATOR, lang);
					content += "</td>";
					content += "<td class=\"dotted\">";
					content += entry.getValue().get(1);
					content += "</td>";
					content += "<td class=\"dotted\">";
					content += entry.getValue().get(2);
					content += "</td>";
					content += "<td class=\"dotted\">";
					content += entry.getValue().get(3);
					content += "</td>";
					content += "<td class=\"dotted\">";
					content += entry.getValue().get(4);
					content += "</td>";
					content += "</tr>";

				}
				content += "</tbody>";

				content += "</table>";
			}
		}
		return content;
	}

}