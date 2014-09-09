package eu.factorx.awac.controllers;

import eu.factorx.awac.dto.awac.get.ReportDTO;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.code.type.PeriodCode;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.models.reporting.BaseActivityResult;
import eu.factorx.awac.models.reporting.Report;
import eu.factorx.awac.service.*;
import eu.factorx.awac.util.NumberFormatWrapper;
import eu.factorx.awac.util.Table;
import eu.factorx.awac.views.html.pdf.results;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import play.Logger;
import play.api.templates.Html;
import play.db.jpa.Transactional;
import play.mvc.Result;
import play.mvc.Security;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@org.springframework.stereotype.Controller
public class ResultController extends AbstractController {

	@Autowired
	private PeriodService               periodService;
	@Autowired
	private ScopeService                scopeService;
	@Autowired
	private ConversionService           conversionService;
	@Autowired
	private ReportService               reportService;
	@Autowired
	private ResultExcelGeneratorService resultExcelGeneratorService;
	@Autowired
	private SecuredController           securedController;
	@Autowired
	private PdfGenerator                pdfGenerator;
	@Autowired
	private SvgGenerator                svgGenerator;


	@Transactional(readOnly = false)
	@Security.Authenticated(SecuredController.class)
	public Result getReport(String periodKey, Long scopeId) {
		Period period = periodService.findByCode(new PeriodCode(periodKey));
		Scope scope = scopeService.findById(scopeId);
		Report report = reportService.getReport(securedController.getCurrentUser().getInterfaceCode(), scope, period);
		Logger.info("Built report on the basis of {} base activity results:", report.getActivityResults().size());
		for (BaseActivityResult activityResult : report.getActivityResults()) {
			Logger.info("\t{}", activityResult);
		}
		return ok(conversionService.convert(report, ReportDTO.class));
	}


	@Transactional(readOnly = false)
	@Security.Authenticated(SecuredController.class)
	public Result getReportAsXls(String periodKey, Long scopeId) throws IOException, WriteException, BiffException {
		Period period = periodService.findByCode(new PeriodCode(periodKey));
		Scope scope = scopeService.findById(scopeId);
		Report report = reportService.getReport(securedController.getCurrentUser().getInterfaceCode(), scope, period);


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
		resultExcelGeneratorService.generateExcelInStream(stream, scope.getSite().getName(), period.getLabel(), allScopes, scope1, scope2, scope3, outOfScope);
		response().setHeader("Content-Disposition", "attachment; filename=\"export.xls\"");
		return ok(new ByteArrayInputStream(stream.toByteArray()));
	}

	@Transactional(readOnly = false)
	@Security.Authenticated(SecuredController.class)
	public Result getReportAsPdf(String periodKey, Long scopeId) throws IOException, WriteException, BiffException {
		Period period = periodService.findByCode(new PeriodCode(periodKey));
		Scope scope = scopeService.findById(scopeId);
		Report report = reportService.getReport(securedController.getCurrentUser().getInterfaceCode(), scope, period);

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
	//@Security.Authenticated(SecuredController.class)
	public Result getSvgDonutForScope(String periodKey, Long scopeId, int scopeType) throws IOException, WriteException, BiffException {
		Period period = periodService.findByCode(new PeriodCode(periodKey));
		Scope scope = scopeService.findById(scopeId);
		Report report = reportService.getReport(securedController.getCurrentUser().getInterfaceCode(), scope, period);

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

		return ok(eu.factorx.awac.views.html.svg.donut.render(scopeTable));
	}

	@Transactional(readOnly = false)
	@Security.Authenticated(SecuredController.class)
	public Result getWebForScope(String periodKey, Long scopeId) throws IOException, WriteException, BiffException {
		Period period = periodService.findByCode(new PeriodCode(periodKey));
		Scope scope = scopeService.findById(scopeId);
		Account currentUser = securedController.getCurrentUser();
		Report report = reportService.getReport(currentUser.getInterfaceCode(), scope, period);

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

		return ok(toSvg(svgGenerator.getWeb(scopeTable)));
	}



}
