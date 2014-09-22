package eu.factorx.awac.controllers;

import eu.factorx.awac.dto.awac.get.ReportDTO;
import eu.factorx.awac.dto.awac.get.ReportLogEntryDTO;
import eu.factorx.awac.dto.awac.get.ResultsDTO;
import eu.factorx.awac.dto.awac.post.GetReportParametersDTO;
import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.code.type.PeriodCode;
import eu.factorx.awac.models.forms.AwacCalculator;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.models.reporting.ReportResult;
import eu.factorx.awac.service.*;
import eu.factorx.awac.service.impl.reporting.ReportLogEntry;
import eu.factorx.awac.service.impl.reporting.ReportResultCollection;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Controller;
import play.Logger;
import play.db.jpa.Transactional;
import play.mvc.Result;
import play.mvc.Security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ResultController extends AbstractController {

	@Autowired
	private PeriodService             periodService;
	@Autowired
	private ScopeService              scopeService;
	@Autowired
	private ConversionService         conversionService;
	@Autowired
	private ReportResultService       reportResultService;
	@Autowired
	private SecuredController         securedController;
	@Autowired
	private AwacCalculatorService     awacCalculatorService;
	@Autowired
	private ResultSvgGeneratorService resultSvgGeneratorService;

	@Transactional(readOnly = false)
	@Security.Authenticated(SecuredController.class)
	public Result getReport() throws BiffException, IOException, WriteException {
		ResultsDTO resultsDTO = new ResultsDTO();

		// 1. Get the parameters from POST request
		GetReportParametersDTO dto = extractDTOFromRequest(GetReportParametersDTO.class);

		// 2. Fetch the period
		Period period = periodService.findByCode(new PeriodCode(dto.getPeriodKey()));

		// 3. Fetch the scopes
		List<Scope> scopes = new ArrayList<>();
		for (Long scopeId : dto.getScopesIds()) {
			scopes.add(scopeService.findById(scopeId));
		}

		// 4. Compute the ReportResult
		List<ReportLogEntry> logEntries = new ArrayList<>();
		Map<String, ReportResult> reportResults = buildReportResults(period, scopes, logEntries);

		// 5. Populate the DTO
		for (Map.Entry<String, ReportResult> reportEntry : reportResults.entrySet()) {
			String reportKey = reportEntry.getKey();
			ReportResult reportResult = reportEntry.getValue();

			// 5.1 Each ReportResult is converted to a ResultDTO
			resultsDTO.getReportDTOs().put(reportKey, conversionService.convert(reportResult, ReportDTO.class));

			// 5.2 Each ReportResult is rendered to a SVG string - DONUT
			resultsDTO.getSvgDonuts().put(reportKey, resultSvgGeneratorService.getDonut(reportResult));

			// 5.3 Each ReportResult is rendered to a SVG string - HISTOGRAM
			resultsDTO.getSvgHistograms().put(reportKey, resultSvgGeneratorService.getHistogram(reportResult));

			// 5.4 Each ReportResult is rendered to a SVG string - WEB
			resultsDTO.getSvgWebs().put(reportKey, resultSvgGeneratorService.getWeb(reportResult));

		}

		// 6. Add log entries
		List<ReportLogEntryDTO> dtoLogEntries = resultsDTO.getLogEntries();
		for (ReportLogEntry logEntry : logEntries) {
			ReportLogEntryDTO reportLogEntryDTO = conversionService.convert(logEntry, ReportLogEntryDTO.class);
			dtoLogEntries.add(reportLogEntryDTO);
		}

		// 7. PUSH !!!
		return ok(resultsDTO);
	}


	private Map<String, ReportResult> buildReportResults(Period period, List<Scope> scopes, List<ReportLogEntry> logEntries) {
		AwacCalculator awacCalculator = awacCalculatorService.findByCode(securedController.getCurrentUser().getInterfaceCode());

		ReportResultCollection allReportResults = reportResultService.getReportResults(awacCalculator, scopes, period);
		List<ReportResult> reportResults = allReportResults.getReportResults();
		Logger.info("Built {} report(s):", reportResults.size());
		for (ReportResult reportResult : reportResults) {
			Logger.info("\t- Report '{}' ({} activity results)", reportResult.getReport().getCode().getKey(), reportResult.getActivityResults().size());
		}

		Map<String, ReportResult> allReportResultsMap = new HashMap<>();
		for (ReportResult reportResult : reportResults) {
			allReportResultsMap.put(reportResult.getReport().getCode().getKey(), reportResult);
		}

		logEntries.clear();
		logEntries.addAll(allReportResults.getLogEntries());

		return allReportResultsMap;
	}

}
