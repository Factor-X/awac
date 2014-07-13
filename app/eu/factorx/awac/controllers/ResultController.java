package eu.factorx.awac.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;

import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import eu.factorx.awac.dto.awac.get.ReportDTO;
import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.models.reporting.Report;
import eu.factorx.awac.service.ReportService;

@org.springframework.stereotype.Controller
public class ResultController extends Controller {

	@Autowired
	private ConversionService conversionService;

	@Autowired
	private ReportService reportService;

	@Transactional(readOnly = false)
	@Security.Authenticated(SecuredController.class)
	public Result calculateIndicator(Scope scope, Period period) {
		Report report = reportService.getReport(scope, period);
		return ok(conversionService.convert(report, ReportDTO.class));
	}

}
