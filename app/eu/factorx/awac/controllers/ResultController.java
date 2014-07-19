package eu.factorx.awac.controllers;

import eu.factorx.awac.service.PeriodService;
import eu.factorx.awac.service.ReportService;
import eu.factorx.awac.service.ScopeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

@org.springframework.stereotype.Controller
public class ResultController extends Controller {

	@Autowired
	private PeriodService periodService;
	@Autowired
	private ScopeService scopeService;
	@Autowired
	private ConversionService conversionService;
	@Autowired
	private ReportService reportService;

	@Transactional(readOnly = false)
	@Security.Authenticated(SecuredController.class)
	public Result getReport(Long periodId, Long scopeId) {
//        Period period = periodService.findById(periodId);
//        Scope scope = scopeService.findById(scopeId);
//		Report report = reportService.getReport(scope, period);
//		return ok(conversionService.convert(report, ReportDTO.class));
		return ok();
	}

}
