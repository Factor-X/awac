package eu.factorx.awac.controllers;

import eu.factorx.awac.dto.awac.get.PromiseDTO;
import eu.factorx.awac.dto.awac.get.ReportDTO;
import eu.factorx.awac.dto.awac.get.ReportLogEntryDTO;
import eu.factorx.awac.dto.awac.get.ResultsDTO;
import eu.factorx.awac.dto.awac.post.GetReportParametersDTO;
import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.models.code.type.LanguageCode;
import eu.factorx.awac.models.code.type.PeriodCode;
import eu.factorx.awac.models.forms.AwacCalculator;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.models.reporting.ReportResult;
import eu.factorx.awac.service.*;
import eu.factorx.awac.service.impl.reporting.*;
import eu.factorx.awac.util.BusinessErrorType;
import eu.factorx.awac.util.Colors;
import eu.factorx.awac.util.MyrmexFatalException;
import eu.factorx.awac.util.document.messages.PDFDocumentMessage;
import eu.factorx.awac.util.document.messages.XLSDocumentMessage;
import eu.factorx.awac.util.document.service.DocumentService;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Controller;
import play.db.jpa.Transactional;
import play.mvc.Result;
import play.mvc.Security;

import java.io.IOException;
import java.util.*;

@Controller
public class ResultController extends AbstractController {

	@Autowired
	private PeriodService periodService;
	@Autowired
	private ScopeService scopeService;
	@Autowired
	private ConversionService conversionService;
	@Autowired
	private ReportResultService reportResultService;
	@Autowired
	private SecuredController securedController;
	@Autowired
	private AwacCalculatorService awacCalculatorService;
	@Autowired
	private ResultSvgGeneratorService resultSvgGeneratorService;
	@Autowired
	private CodeLabelService codeLabelService;
	@Autowired
	private ResultExcelGeneratorService resultExcelGeneratorService;
	@Autowired
	private VerificationRequestService verificationRequestService;
	@Autowired
	private PdfGenerator pdfGenerator;
	@Autowired
	private SvgGenerator svgGenerator;
	@Autowired
	private ResultPdfGeneratorService resultPdfGeneratorService;
	@Autowired
	private DocumentService documentService;

	//
	// ACTIONS
	//

	@Transactional(readOnly = false)
	@Security.Authenticated(SecuredController.class)
	public Result getReport() throws BiffException, IOException, WriteException {

		// 1. Get the parameters from POST request
		GetReportParametersDTO dto = extractDTOFromRequest(GetReportParametersDTO.class);

		// 2. Fetch the scopes
		List<Scope> scopes = new ArrayList<>();
		for (Long scopeId : dto.getScopesIds()) {
			scopes.add(scopeService.findById(scopeId));
		}

		//control scope
		controlScope(scopes);

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

	@Transactional(readOnly = false)
	@Security.Authenticated(SecuredController.class)
	public Result getReportAsXls() throws BiffException, IOException, WriteException {
		GetReportParametersDTO dto = extractDTOFromRequest(GetReportParametersDTO.class);
		String periodKey = dto.getPeriodKey();
		String comparedPeriodKey = dto.getComparedPeriodKey();

		// 2. Fetch the scopes
		List<Scope> scopes = new ArrayList<>();
		for (Long scopeId : dto.getScopesIds()) {
			scopes.add(scopeService.findById(scopeId));
		}

		//control scope
		controlScope(scopes);

		LanguageCode lang = securedController.getCurrentUser().getDefaultLanguage();
		InterfaceTypeCode interfaceCode = securedController.getCurrentUser().getOrganization().getInterfaceCode();

		final XLSDocumentMessage xlsDocumentMessage = new XLSDocumentMessage();
		xlsDocumentMessage.setPeriodKey(periodKey);
		xlsDocumentMessage.setComparedPeriodKey(comparedPeriodKey);
		xlsDocumentMessage.setLang(lang);
		xlsDocumentMessage.setScopes(scopes);
		xlsDocumentMessage.setInterfaceCode(interfaceCode);
		xlsDocumentMessage.setIdealResultValues(getIdealResultValues(interfaceCode));
		xlsDocumentMessage.setTypicalResultValues(getTypicalResultValues(interfaceCode));
		xlsDocumentMessage.setUUID(UUID.randomUUID().toString());
		documentService.send(xlsDocumentMessage);

		return ok(new PromiseDTO(xlsDocumentMessage.getUUID()));
	}

	@Transactional(readOnly = false)
	@Security.Authenticated(SecuredController.class)
	public Result getReportAsPdf() throws IOException, BiffException, WriteException {

		GetReportParametersDTO dto = extractDTOFromRequest(GetReportParametersDTO.class);
		final String periodKey = dto.getPeriodKey();
		final String comparedPeriodKey = dto.getComparedPeriodKey();

		// 2. Fetch the scopes
		final List<Scope> scopes = new ArrayList<>();
		for (Long scopeId : dto.getScopesIds()) {
			scopes.add(scopeService.findById(scopeId));
		}

		controlScope(scopes);

		LanguageCode lang = securedController.getCurrentUser().getDefaultLanguage();
		InterfaceTypeCode interfaceCode = securedController.getCurrentUser().getOrganization().getInterfaceCode();

		final PDFDocumentMessage PDFDocumentMessage = new PDFDocumentMessage();
		PDFDocumentMessage.setPeriodKey(periodKey);
		PDFDocumentMessage.setComparedPeriodKey(comparedPeriodKey);
		PDFDocumentMessage.setLang(lang);
		PDFDocumentMessage.setScopes(scopes);
		PDFDocumentMessage.setInterfaceCode(interfaceCode);
		PDFDocumentMessage.setIdealResultValues(getIdealResultValues(interfaceCode));
		PDFDocumentMessage.setTypicalResultValues(getTypicalResultValues(interfaceCode));
		PDFDocumentMessage.setUUID(UUID.randomUUID().toString());
		documentService.send(PDFDocumentMessage);

		return ok(new PromiseDTO(PDFDocumentMessage.getUUID()));
	}

	//
	// UTILS
	//


	private Result getComparedReport(Period period, Period comparedPeriod, List<Scope> scopes) throws BiffException, IOException, WriteException {
		ResultsDTO resultsDTO = new ResultsDTO();

		// 1. Compute the ReportResult
		InterfaceTypeCode interfaceCode = scopes.get(0).getOrganization().getInterfaceCode();
		boolean isSmallCalculator = isSmallCalculator(interfaceCode);
		AwacCalculator awacCalculator = awacCalculatorService.findByCode(interfaceCode);
		ReportResultCollection allReportResultsLeft = reportResultService.getReportResults(awacCalculator, scopes, period);
		ReportResultCollection allReportResultsRight = reportResultService.getReportResults(awacCalculator, scopes, comparedPeriod);

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

			// 2.3. Each ReportResult is rendered to a SVG string - DONUT
			if (isSmallCalculator) {
				resultsDTO.getLeftSvgDonuts().put(reportKey, resultSvgGeneratorService.getLeftSimpleDonut(awacCalculator, mergedReportResultAggregation));
				resultsDTO.getRightSvgDonuts().put(reportKey, resultSvgGeneratorService.getRightSimpleDonut(awacCalculator, mergedReportResultAggregation));
			} else {
				resultsDTO.getLeftSvgDonuts().put(reportKey, resultSvgGeneratorService.getLeftDonut(awacCalculator, mergedReportResultAggregation));
				resultsDTO.getRightSvgDonuts().put(reportKey, resultSvgGeneratorService.getRightDonut(awacCalculator, mergedReportResultAggregation));
			}

			// 2.4. Each ReportResult is rendered to a SVG string - HISTOGRAM
			if (isSmallCalculator) {
				resultsDTO.getSvgHistograms().put(reportKey, resultSvgGeneratorService.getSimpleHistogram(awacCalculator, mergedReportResultAggregation));
			} else {
				resultsDTO.getSvgHistograms().put(reportKey, resultSvgGeneratorService.getHistogram(awacCalculator, mergedReportResultAggregation));
			}

			// 2.5. Each ReportResult is rendered to a SVG string - WEB
			if (isSmallCalculator) {
				Map<String, Double> type = getTypicalResultValues(interfaceCode);
				Map<String, Double> ideal = getIdealResultValues(interfaceCode);
				resultsDTO.getSvgWebs().put(reportKey, resultSvgGeneratorService.getWebWithReferences(awacCalculator, mergedReportResultAggregation, type, ideal));

				// 2.6. Add references
				resultsDTO.getTypeMap().putAll(type);
				resultsDTO.getIdealMap().putAll(ideal);
				setTypicalAndIdealResultsColors(resultsDTO, mergedReportResultAggregation);

			} else {
				resultsDTO.getSvgWebs().put(reportKey, resultSvgGeneratorService.getWeb(awacCalculator, mergedReportResultAggregation));
			}

		}


		ReportResultCollection allReportResultsCEFLeft = reportResultService.getReportResultsCEF(awacCalculator, scopes, period, comparedPeriod);

		MergedReportResultCollectionAggregation mergedReportResultCEFCollectionAggregation = reportResultService.mergeAsComparision(
			reportResultService.aggregate(allReportResultsCEFLeft),
			reportResultService.aggregate(allReportResultsRight)
		);

		for (MergedReportResultAggregation mergedReportResultAggregation : mergedReportResultCEFCollectionAggregation.getMergedReportResultAggregations()) {
			String reportKey = mergedReportResultAggregation.getReportCode();

			// 2.2b. Each ReportResult is rendered to a SVG string - WEB
			resultsDTO.getReportCEFDTOs().put(reportKey, conversionService.convert(mergedReportResultAggregation, ReportDTO.class));
			// 2.4. Each ReportResult is rendered to a SVG string - HISTOGRAM
			resultsDTO.getSvgHistogramsCEF().put(reportKey, resultSvgGeneratorService.getHistogram(awacCalculator, mergedReportResultAggregation));
		}


		// 3. Add log entries
		List<ReportLogEntryDTO> dtoLogEntries = resultsDTO.getLogEntries();
		for (ReportLogEntry logEntry : logEntries) {
			ReportLogEntryDTO reportLogEntryDTO = conversionService.convert(logEntry, ReportLogEntryDTO.class);
			if (reportLogEntryDTO != null) {
				dtoLogEntries.add(reportLogEntryDTO);
			}
		}

		// 4. PUSH !!!
		return ok(resultsDTO);
	}

	private Result getSimpleReport(Period period, List<Scope> scopes) throws BiffException, IOException, WriteException {

		ResultsDTO resultsDTO = new ResultsDTO();

		// 1. Compute the ReportResult
		InterfaceTypeCode interfaceCode = scopes.get(0).getOrganization().getInterfaceCode();
		boolean isSmallCalculator = isSmallCalculator(interfaceCode);
		AwacCalculator awacCalculator = awacCalculatorService.findByCode(interfaceCode);

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
			if (isSmallCalculator) {
				resultsDTO.getLeftSvgDonuts().put(reportKey, resultSvgGeneratorService.getSimpleDonut(awacCalculator, reportResultAggregation));
			} else {
				resultsDTO.getLeftSvgDonuts().put(reportKey, resultSvgGeneratorService.getDonut(awacCalculator, reportResultAggregation));
			}

			// 2.4. Each ReportResult is rendered to a SVG string - HISTOGRAM
			if (isSmallCalculator) {
				resultsDTO.getSvgHistograms().put(reportKey, resultSvgGeneratorService.getSimpleHistogram(awacCalculator, reportResultAggregation));
			} else {
				resultsDTO.getSvgHistograms().put(reportKey, resultSvgGeneratorService.getHistogram(awacCalculator, reportResultAggregation));
			}

			// 2.5. Each ReportResult is rendered to a SVG string - WEB
			if (isSmallCalculator) {
				Map<String, Double> type = getTypicalResultValues(interfaceCode);
				Map<String, Double> ideal = getIdealResultValues(interfaceCode);
				resultsDTO.getSvgWebs().put(reportKey, resultSvgGeneratorService.getWebWithReferences(awacCalculator, reportResultAggregation, type, ideal));

				// 2.6. Add references
				resultsDTO.getTypeMap().putAll(type);
				resultsDTO.getIdealMap().putAll(ideal);
				setTypicalAndIdealResultsColors(resultsDTO, reportResultAggregation);

			} else {
				resultsDTO.getSvgWebs().put(reportKey, resultSvgGeneratorService.getWeb(awacCalculator, reportResultAggregation));
			}
		}

		// 3. Add log entries
		List<ReportLogEntryDTO> dtoLogEntries = resultsDTO.getLogEntries();
		for (ReportLogEntry logEntry : logEntries) {
			ReportLogEntryDTO reportLogEntryDTO = conversionService.convert(logEntry, ReportLogEntryDTO.class);
			if (reportLogEntryDTO != null) {
				dtoLogEntries.add(reportLogEntryDTO);
			}
		}


		// 4. PUSH !!!
		return ok(resultsDTO);
	}

	private void controlScope(List<Scope> listScope) {

		boolean fromMyOrganization = true;

		//control if scope are my scopes
		for (Scope scope : listScope) {
			if (!scope.getOrganization().equals(securedController.getCurrentUser().getOrganization())) {
				fromMyOrganization = false;
			}
		}

		if (fromMyOrganization) {
			return;
		}

		if (securedController.getCurrentUser().getOrganization().getInterfaceCode().equals(InterfaceTypeCode.VERIFICATION)) {

			//if scope do not come from my organization, test verification
			Organization targetOrganization = listScope.get(0).getOrganization();

			if (verificationRequestService.findByOrganizationCustomerAndOrganizationVerifier(targetOrganization, securedController.getCurrentUser().getOrganization()) != null) {


				//control other scope
				boolean fromSameOrganization = true;
				for (Scope scope : listScope) {
					if (!scope.getOrganization().equals(targetOrganization)) {
						fromSameOrganization = false;
					}
				}

				if (fromSameOrganization) {
					return;
				}
			}
		}
		throw new MyrmexFatalException(BusinessErrorType.NOT_YOUR_SCOPE_LITTLE);
	}

	private boolean isSmallCalculator(InterfaceTypeCode interfaceCode) {
		return InterfaceTypeCode.HOUSEHOLD.equals(interfaceCode) || InterfaceTypeCode.LITTLEEMITTER.equals(interfaceCode) || InterfaceTypeCode.EVENT.equals(interfaceCode);
	}

	private Map<String, Double> getTypicalResultValues(InterfaceTypeCode interfaceTypeCode) {
		Map<String, Double> type = new HashMap<>();
		if (InterfaceTypeCode.HOUSEHOLD.equals(interfaceTypeCode)) {
			type.put("IMe_1", getDoubleValue("TYPICAL_HOUSEHOLD_HOUSING"));
			type.put("IMe_2", getDoubleValue("TYPICAL_HOUSEHOLD_MOBILITY"));
			type.put("IMe_3", getDoubleValue("TYPICAL_HOUSEHOLD_CONSUMPTION"));
			type.put("IMe_4", getDoubleValue("TYPICAL_HOUSEHOLD_WASTE"));
		} else if (InterfaceTypeCode.EVENT.equals(interfaceTypeCode)) {
			type.put("IEv_1", getDoubleValue("TYPICAL_EVENT_LOCATION"));
			type.put("IEv_2", getDoubleValue("TYPICAL_EVENT_MOBILITY"));
			type.put("IEv_3", getDoubleValue("TYPICAL_EVENT_LOGISTICS"));
			type.put("IEv_4", getDoubleValue("TYPICAL_EVENT_SUPPLIES"));
			type.put("IEv_5", getDoubleValue("TYPICAL_EVENT_WASTE"));
		} else if (InterfaceTypeCode.LITTLEEMITTER.equals(interfaceTypeCode)) {
			type.put("IPE_1", getDoubleValue("TYPICAL_LITTLEEMITTER_SITE_ACTIVITIES"));
			type.put("IPE_2", getDoubleValue("TYPICAL_LITTLEEMITTER_MOBILITY"));
			type.put("IPE_3", getDoubleValue("TYPICAL_LITTLEEMITTER_LOGISTICS"));
			type.put("IPE_4", getDoubleValue("TYPICAL_LITTLEEMITTER_MATERIALS"));
			type.put("IPE_5", getDoubleValue("TYPICAL_LITTLEEMITTER_WASTE"));
		}
		return type;
	}

	private Map<String, Double> getIdealResultValues(InterfaceTypeCode interfaceTypeCode) {
		Map<String, Double> ideal = new HashMap<>();
		if (InterfaceTypeCode.HOUSEHOLD.equals(interfaceTypeCode)) {
			ideal.put("IMe_1", getDoubleValue("IDEAL_HOUSEHOLD_HOUSING"));
			ideal.put("IMe_2", getDoubleValue("IDEAL_HOUSEHOLD_MOBILITY"));
			ideal.put("IMe_3", getDoubleValue("IDEAL_HOUSEHOLD_CONSUMPTION"));
			ideal.put("IMe_4", getDoubleValue("IDEAL_HOUSEHOLD_WASTE"));
		} else if (InterfaceTypeCode.EVENT.equals(interfaceTypeCode)) {
			ideal.put("IEv_1", getDoubleValue("IDEAL_EVENT_LOCATION"));
			ideal.put("IEv_2", getDoubleValue("IDEAL_EVENT_MOBILITY"));
			ideal.put("IEv_3", getDoubleValue("IDEAL_EVENT_LOGISTICS"));
			ideal.put("IEv_4", getDoubleValue("IDEAL_EVENT_SUPPLIES"));
			ideal.put("IEv_5", getDoubleValue("IDEAL_EVENT_WASTE"));
		} else if (InterfaceTypeCode.LITTLEEMITTER.equals(interfaceTypeCode)) {
			ideal.put("IPE_1", getDoubleValue("IDEAL_LITTLEEMITTER_SITE_ACTIVITIES"));
			ideal.put("IPE_2", getDoubleValue("IDEAL_LITTLEEMITTER_MOBILITY"));
			ideal.put("IPE_3", getDoubleValue("IDEAL_LITTLEEMITTER_LOGISTICS"));
			ideal.put("IPE_4", getDoubleValue("IDEAL_LITTLEEMITTER_MATERIALS"));
			ideal.put("IPE_5", getDoubleValue("IDEAL_LITTLEEMITTER_WASTE"));
		}
		return ideal;
	}

	private Double getDoubleValue(String valueKey) {
		return Double.valueOf(codeLabelService.findCodeLabelByCode(new Code(CodeList.TRANSLATIONS_INTERFACE, valueKey)).getLabelEn());
	}

	private void setTypicalAndIdealResultsColors(ResultsDTO resultsDTO, ReportResultAggregation reportResultAggregation) {
		int c;
		Double totalValue = 0.0;
		for (ReportResultIndicatorAggregation aggregation : reportResultAggregation.getReportResultIndicatorAggregationList()) {
			totalValue += aggregation.getTotalValue();
		}
		if (totalValue > 0) {
			c = 1;
		} else {
			c = 0;
		}

		resultsDTO.setTypeColor("#" + Colors.makeGoodColorForSerieElement(c, c + 2));
		resultsDTO.setIdealColor("#" + Colors.makeGoodColorForSerieElement(c + 1, c + 2));
	}

	private void setTypicalAndIdealResultsColors(ResultsDTO resultsDTO, MergedReportResultAggregation mergedReportResultAggregation) {
		int c;
		Double totalValue = 0.0;
		for (MergedReportResultIndicatorAggregation aggregation : mergedReportResultAggregation.getMergedReportResultIndicatorAggregationList()) {
			totalValue += aggregation.getLeftTotalValue() + aggregation.getRightTotalValue();
		}
		if (totalValue > 0) {
			c = 1;
		} else {
			c = 0;
		}

		resultsDTO.setTypeColor("#" + Colors.makeGoodColorForSerieElement(c, c + 2));
		resultsDTO.setIdealColor("#" + Colors.makeGoodColorForSerieElement(c + 1, c + 2));
	}

}
