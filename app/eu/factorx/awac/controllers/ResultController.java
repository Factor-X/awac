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
import eu.factorx.awac.service.impl.reporting.*;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Controller;
import play.db.jpa.Transactional;
import play.mvc.Result;
import play.mvc.Security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

		// 2. Fetch the scopes
		List<Scope> scopes = new ArrayList<>();
		for (Long scopeId : dto.getScopesIds()) {
			scopes.add(scopeService.findById(scopeId));
		}

		// 3. Return simple or compared
		String periodKey = dto.getPeriodKey();
		String comparedPeriodKey = dto.getComparedPeriodKey();
		if (comparedPeriodKey == null) {
			Period period = periodService.findByCode(new PeriodCode(periodKey));
			return getSimpleReport(period, scopes);
		} else {
			Period period = periodService.findByCode(new PeriodCode(periodKey));
			Period comparedPeriod = periodService.findByCode(new PeriodCode(comparedPeriodKey));
			return getComparedReport(period, comparedPeriod, scopes);
		}
	}

	public Result getSimpleReport(Period period, List<Scope> scopes) throws BiffException, IOException, WriteException {
		ResultsDTO resultsDTO = new ResultsDTO();

		// 1. Compute the ReportResult
		AwacCalculator awacCalculator = awacCalculatorService.findByCode(securedController.getCurrentUser().getInterfaceCode());
		ReportResultCollection allReportResults = reportResultService.getReportResults(awacCalculator, scopes, period);
		List<ReportLogEntry> logEntries = allReportResults.getLogEntries();

		// 2. Populate the DTO
		for (ReportResult reportResult : allReportResults.getReportResults()) {
			String reportKey = reportResult.getReport().getCode().getKey();

			// 2.1. Aggregate report
			ReportResultAggregation reportResultAggregation = reportResultService.aggregate(reportResult);

			// 2.2. Each ReportResult is converted to a ResultDTO
			resultsDTO.getReportDTOs().put(reportKey, conversionService.convert(reportResultAggregation, ReportDTO.class));

			// 2.3. Each ReportResult is rendered to a SVG string - DONUT
			resultsDTO.getSvgDonuts().put(reportKey, resultSvgGeneratorService.getDonut(reportResultAggregation));

			// 2.4. Each ReportResult is rendered to a SVG string - HISTOGRAM
			resultsDTO.getSvgHistograms().put(reportKey, resultSvgGeneratorService.getHistogram(reportResultAggregation));

			// 2.5. Each ReportResult is rendered to a SVG string - WEB
			resultsDTO.getSvgWebs().put(reportKey, resultSvgGeneratorService.getWeb(reportResultAggregation));
		}

		// 3. Add log entries
		List<ReportLogEntryDTO> dtoLogEntries = resultsDTO.getLogEntries();
		for (ReportLogEntry logEntry : logEntries) {
			ReportLogEntryDTO reportLogEntryDTO = conversionService.convert(logEntry, ReportLogEntryDTO.class);
			dtoLogEntries.add(reportLogEntryDTO);
		}

		// 4. PUSH !!!
		return ok(resultsDTO);
	}

	public Result getComparedReport(Period period, Period comparedPeriod, List<Scope> scopes) throws BiffException, IOException, WriteException {
		ResultsDTO resultsDTO = new ResultsDTO();

		// 1. Compute the ReportResult
		AwacCalculator awacCalculator = awacCalculatorService.findByCode(securedController.getCurrentUser().getInterfaceCode());
		ReportResultCollection allReportResultsLeft = reportResultService.getReportResults(awacCalculator, scopes, period);
		ReportResultCollection allReportResultsRight = reportResultService.getReportResults(awacCalculator, scopes, period);

		List<ReportLogEntry> logEntries = new ArrayList<>();
		logEntries.addAll(allReportResultsLeft.getLogEntries());
		logEntries.addAll(allReportResultsRight.getLogEntries());


		MergedReportResultCollectionAggregation mergedReportResultCollectionAggregation = reportResultService.mergeAsComparision(
			reportResultService.aggregate(allReportResultsLeft),
			reportResultService.aggregate(allReportResultsRight)
		);

		// 2. Populate the DTO
		for (MergedReportResultAggregation mergedReportResultAggregation : mergedReportResultCollectionAggregation.getMergedReportResultAggregations()) {
			String reportKey = mergedReportResultAggregation.getReportCode();

			// 2.2. Each ReportResult is converted to a ResultDTO
			resultsDTO.getReportDTOs().put(reportKey, conversionService.convert(mergedReportResultAggregation, ReportDTO.class));


			// // 2.3. Each ReportResult is rendered to a SVG string - DONUT
			// resultsDTO.getSvgDonuts().put(reportKey, resultSvgGeneratorService.getDonut(reportResult));
//
			// // 2.4. Each ReportResult is rendered to a SVG string - HISTOGRAM
			// resultsDTO.getSvgHistograms().put(reportKey, resultSvgGeneratorService.getHistogram(reportResult));
//
			// // 2.5. Each ReportResult is rendered to a SVG string - WEB
			// resultsDTO.getSvgWebs().put(reportKey, resultSvgGeneratorService.getWeb(reportResult));

		}

		// 3. Add log entries
		List<ReportLogEntryDTO> dtoLogEntries = resultsDTO.getLogEntries();
		for (ReportLogEntry logEntry : logEntries) {
			ReportLogEntryDTO reportLogEntryDTO = conversionService.convert(logEntry, ReportLogEntryDTO.class);
			dtoLogEntries.add(reportLogEntryDTO);
		}

		// 4. PUSH !!!
		return ok(resultsDTO);
	}


}
