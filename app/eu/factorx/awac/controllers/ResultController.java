package eu.factorx.awac.controllers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.*;

import jxl.read.biff.BiffException;
import jxl.write.WriteException;

import org.apache.commons.collections.map.LRUMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;

import play.Logger;
import play.api.templates.Html;
import play.db.jpa.Transactional;
import play.mvc.Result;
import play.mvc.Security;
import eu.factorx.awac.dto.SvgContent;
import eu.factorx.awac.dto.awac.get.ReportDTO;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.code.type.IndicatorIsoScopeCode;
import eu.factorx.awac.models.code.type.PeriodCode;
import eu.factorx.awac.models.forms.AwacCalculator;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.models.reporting.ReportResult;
import eu.factorx.awac.service.*;
import eu.factorx.awac.util.NumberFormatWrapper;
import eu.factorx.awac.util.Table;
import eu.factorx.awac.views.html.pdf.results;

@org.springframework.stereotype.Controller
public class ResultController extends AbstractController {

	private static final int CACHE_MAP_SIZE = 100;
	@Autowired
	private PeriodService periodService;
	@Autowired
	private ScopeService scopeService;
	@Autowired
	private ConversionService conversionService;
	@Autowired
	private ReportResultService reportResultService;
	@Autowired
	private ResultExcelGeneratorService resultExcelGeneratorService;
	@Autowired
	private SecuredController securedController;
	@Autowired
	private PdfGenerator pdfGenerator;
	@Autowired
	private SvgGenerator svgGenerator;
	@Autowired
	private AwacCalculatorService awacCalculatorService;

	@SuppressWarnings("unchecked")
	public static Map<String, Map<String, ReportResult>> reportsCache = (Map<String, Map<String, ReportResult>>)
			Collections.synchronizedMap(new LRUMap(CACHE_MAP_SIZE));

	@Transactional(readOnly = false)
	@Security.Authenticated(SecuredController.class)
	public Result getReport(String periodKey, List<Long> scopesIds) {
		Period period = periodService.findByCode(new PeriodCode(periodKey));
		List<Scope> scopes = new ArrayList<>();
		for (Long scopeId : scopesIds) {
			scopes.add(scopeService.findById(scopeId));
		}
		Map<String, ReportResult> reportResults = getReportResults(period, scopes);
		
		HashMap<String, ReportDTO> reportDTOs = new HashMap<>();
		for (String reportKey : reportResults.keySet()) {
			reportDTOs.put(reportKey, conversionService.convert(reportResults.get(reportKey), ReportDTO.class));
		}
		
		
		
		return resultsDTO;
	}

	@Transactional(readOnly = false)
	@Security.Authenticated(SecuredController.class)
	public Result getReportAsXls(String reportKey, String periodKey, List<Long> scopesIds) throws IOException, WriteException, BiffException {
		Period period = periodService.findByCode(new PeriodCode(periodKey));
		List<Scope> scopes = new ArrayList<>();
		for (Long scopeId : scopesIds) {
			scopes.add(scopeService.findById(scopeId));
		}
		ReportResult report = getReportResult(reportKey, period, scopes);

		Table allScopes = new Table();
		Table scope1 = new Table();
		Table scope2 = new Table();
		Table scope3 = new Table();
		Table outOfScope = new Table();

		int row = 0;
		for (Map.Entry<String, List<Double>> entry : report.getScopeValuesByIndicator().entrySet()) {

			allScopes.setCell(0, row, entry.getKey());
			scope1.setCell(0, row, entry.getKey());
			scope2.setCell(0, row, entry.getKey());
			scope3.setCell(0, row, entry.getKey());
			outOfScope.setCell(0, row, entry.getKey());

			allScopes.setCell(1, row, entry.getValue().get(0));
			scope1.setCell(1, row, entry.getValue().get(1));
			scope2.setCell(1, row, entry.getValue().get(2));
			scope3.setCell(1, row, entry.getValue().get(3));
			outOfScope.setCell(1, row, entry.getValue().get(4));

			row++;
		}

		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		resultExcelGeneratorService.generateExcelInStream(stream, scopes, period.getLabel(), allScopes, scope1, scope2, scope3, outOfScope);
		response().setHeader("Content-Disposition", "attachment; filename=\"export.xls\"");
		ByteArrayInputStream content = new ByteArrayInputStream(stream.toByteArray());
		return ok(content);
	}

	@Transactional(readOnly = false)
	@Security.Authenticated(SecuredController.class)
	public Result getReportAsPdf(String reportKey, String periodKey, List<Long> scopesIds) throws IOException, WriteException, BiffException {
		Period period = periodService.findByCode(new PeriodCode(periodKey));
		List<Scope> scopes = new ArrayList<>();
		for (Long scopeId : scopesIds) {
			scopes.add(scopeService.findById(scopeId));
		}
		ReportResult report = getReportResult(reportKey, period, scopes);

		Table allScopes = new Table();
		Table scope1 = new Table();
		Table scope2 = new Table();
		Table scope3 = new Table();
		Table outOfScope = new Table();

		for (Map.Entry<String, List<Double>> entry : report.getScopeValuesByIndicator().entrySet()) {
			if (entry.getValue().get(0) > 0) {
				int row = allScopes.getRowCount();
				allScopes.setCell(0, row, entry.getKey());

				if (entry.getValue().get(1) > 0)
					allScopes.setCell(1, row, entry.getValue().get(1));

				if (entry.getValue().get(2) > 0)
					allScopes.setCell(2, row, entry.getValue().get(2));

				if (entry.getValue().get(3) > 0)
					allScopes.setCell(3, row, entry.getValue().get(3));

				if (entry.getValue().get(4) > 0)
					allScopes.setCell(4, row, entry.getValue().get(4));
			}

			if (entry.getValue().get(1) > 0) {
				int row = scope1.getRowCount();
				scope1.setCell(0, row, entry.getKey());
				scope1.setCell(1, row, entry.getValue().get(1));
			}
			if (entry.getValue().get(2) > 0) {
				int row = scope2.getRowCount();
				scope2.setCell(0, row, entry.getKey());
				scope2.setCell(1, row, entry.getValue().get(2));
			}
			if (entry.getValue().get(3) > 0) {
				int row = scope3.getRowCount();
				scope3.setCell(0, row, entry.getKey());
				scope3.setCell(1, row, entry.getValue().get(3));
			}
			if (entry.getValue().get(4) > 0) {
				int row = outOfScope.getRowCount();
				outOfScope.setCell(0, row, entry.getKey());
				outOfScope.setCell(1, row, entry.getValue().get(4));
			}
		}

		NumberFormat nf = NumberFormat.getInstance(Locale.forLanguageTag(securedController.getCurrentUser().getPerson().getDefaultLanguage().getKey()));
		nf.setMaximumFractionDigits(2);
		NumberFormatWrapper wrapper = new NumberFormatWrapper(nf);

		Html rendered = results.render(wrapper, allScopes, scope1, scope2, scope3, outOfScope);

		pdfGenerator.setMemoryResource("mem://svg/donut/2013/2/1", svgGenerator.getDonut(scope1));

		return pdfGenerator.ok(rendered);
	}

	@Transactional(readOnly = false)
	@Security.Authenticated(SecuredController.class)
	public Result getDonut(String reportKey, String periodKey, List<Long> scopesIds) throws IOException, WriteException, BiffException {
		Period period = periodService.findByCode(new PeriodCode(periodKey));
		List<Scope> scopes = new ArrayList<>();
		for (Long scopeId : scopesIds) {
			scopes.add(scopeService.findById(scopeId));
		}
		ReportResult report = getReportResult(reportKey, period, scopes);
		IndicatorIsoScopeCode reportScope = report.getReport().getRestrictedScope();
		Table scopeTable = new Table();

		NumberFormat nf = NumberFormat.getInstance(Locale.forLanguageTag("FR"));
		nf.setMaximumFractionDigits(2);

		 for (Map.Entry<String, List<Double>> entry : report.getScopeValuesByIndicator().entrySet()) {
		 if (entry.getValue().get(scopeType) > 0) {
		 int row = scopeTable.getRowCount();
		 scopeTable.setCell(0, row, entry.getKey());
		 scopeTable.setCell(1, row, entry.getValue().get(scopeType));
		 scopeTable.setCell(2, row, nf.format(entry.getValue().get(scopeType)));
		 }
		 }

		markNoCache();

		SvgContent svg = toSvg(svgGenerator.getDonut(scopeTable));
		return ok(svg);
	}

	@Transactional(readOnly = false)
	@Security.Authenticated(SecuredController.class)
	public Result getWeb(String periodKey, Long scopeId) throws IOException, WriteException, BiffException {
		Period period = periodService.findByCode(new PeriodCode(periodKey));
		Scope scope = scopeService.findById(scopeId);
		Account currentUser = securedController.getCurrentUser();
		ReportResult report = reportResultService.getReportResult(currentUser.getInterfaceCode(), scope, period);

		Table scopeTable = new Table();

		NumberFormat nf = NumberFormat.getInstance(Locale.forLanguageTag("FR"));
		nf.setMaximumFractionDigits(2);

		// System.out.println("== BEGIN WEB ==");
		for (Map.Entry<String, List<Double>> entry : report.getScopeValuesByIndicator().entrySet()) {
			double v = entry.getValue().get(1) + entry.getValue().get(2) + entry.getValue().get(3) + entry.getValue().get(4);
			if (v > 0) {
				// System.out.println("== web == " + entry);
				int row = scopeTable.getRowCount();
				scopeTable.setCell(0, row, entry.getKey());
				scopeTable.setCell(1, row, v);
			}
		}
		// System.out.println("== END WEB ==");

		markNoCache();

		SvgContent svg = toSvg(svgGenerator.getWeb(scopeTable));
		return ok(svg);
	}

	@Transactional(readOnly = false)
	@Security.Authenticated(SecuredController.class)
	public Result getHistogram(String periodKey, Long scopeId) throws IOException, WriteException, BiffException {
		Period period = periodService.findByCode(new PeriodCode(periodKey));
		Scope scope = scopeService.findById(scopeId);
		Account currentUser = securedController.getCurrentUser();
		ReportResult report = reportResultService.getReportResult(currentUser.getInterfaceCode(), scope, period);

		Table scopeTable = new Table();

		NumberFormat nf = NumberFormat.getInstance(Locale.forLanguageTag("FR"));
		nf.setMaximumFractionDigits(2);

		for (Map.Entry<String, List<Double>> entry : report.getScopeValuesByIndicator().entrySet()) {
			double v = entry.getValue().get(1) + entry.getValue().get(2) + entry.getValue().get(3) + entry.getValue().get(4);
			if (v > 0) {
				int row = scopeTable.getRowCount();
				scopeTable.setCell(0, row, entry.getKey());
				scopeTable.setCell(1, row, v);
			}
		}

		markNoCache();

		SvgContent svg = toSvg(svgGenerator.getHistogram(scopeTable));
		return ok(svg);
	}

	private Map<String, ReportResult> getReportResults(Period period, List<Scope> scopes) {
		AwacCalculator awacCalculator = awacCalculatorService.findByCode(securedController.getCurrentUser().getInterfaceCode());

		List<ReportResult> allReportResults = reportResultService.getReportResults(awacCalculator, scopes, period);
		Logger.info("Built {} report(s):", allReportResults.size());
		for (ReportResult reportResult : allReportResults) {
			Logger.info("\t- Report '{}' ({} activity results)", reportResult.getActivityResults().size());
		}

		Map<String, ReportResult> allReportResultsMap = new HashMap<>();
		for (ReportResult reportResult : allReportResults) {
			allReportResultsMap.put(reportResult.getReport().getCode().getKey(), reportResult);
		}

		return allReportResultsMap;
	}

}
