package eu.factorx.awac.controllers;

import eu.factorx.awac.dto.awac.get.ReportDTO;
import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.code.type.PeriodCode;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.models.reporting.BaseActivityResult;
import eu.factorx.awac.models.reporting.Report;
import eu.factorx.awac.service.PeriodService;
import eu.factorx.awac.service.ReportService;
import eu.factorx.awac.service.ResultExcelGeneratorService;
import eu.factorx.awac.service.ScopeService;
import eu.factorx.awac.util.Table;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import play.Logger;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
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

	@Transactional(readOnly = false)
	@Security.Authenticated(SecuredController.class)
	public Result getReport(String periodKey, Long scopeId) {
		Period period = periodService.findByCode(new PeriodCode(periodKey));
		Scope scope = scopeService.findById(scopeId);
		Report report = reportService.getReport(scope, period);
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
		Report report = reportService.getReport(scope, period);


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
//		response().setHeader("Content-Type", "application/octet-stream");
		response().setHeader("Content-Disposition", "attachment; filename=\"export.xls\"");
		return ok(new ByteArrayInputStream(stream.toByteArray()));
	}

}
