package eu.factorx.awac.controllers;

import eu.factorx.awac.dto.awac.get.DownloadFileDTO;
import eu.factorx.awac.dto.awac.get.ReportDTO;
import eu.factorx.awac.dto.awac.get.ReportLogEntryDTO;
import eu.factorx.awac.dto.awac.get.ResultsDTO;
import eu.factorx.awac.dto.awac.post.GetReportParametersDTO;
import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.models.code.type.LanguageCode;
import eu.factorx.awac.models.code.type.PeriodCode;
import eu.factorx.awac.models.forms.AwacCalculator;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.models.reporting.ReportResult;
import eu.factorx.awac.service.*;
import eu.factorx.awac.service.impl.reporting.*;
import eu.factorx.awac.util.BusinessErrorType;
import eu.factorx.awac.util.MyrmexFatalException;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;
import org.apache.commons.codec.binary.Base64;
import org.joda.time.DateTime;
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
    private PeriodService               periodService;
    @Autowired
    private ScopeService                scopeService;
    @Autowired
    private ConversionService           conversionService;
    @Autowired
    private ReportResultService         reportResultService;
    @Autowired
    private SecuredController           securedController;
    @Autowired
    private AwacCalculatorService       awacCalculatorService;
    @Autowired
    private ResultSvgGeneratorService   resultSvgGeneratorService;
    @Autowired
    private CodeLabelService            codeLabelService;
    @Autowired
    private ResultExcelGeneratorService resultExcelGeneratorService;
    @Autowired
    private VerificationRequestService  verificationRequestService;
    @Autowired
    private PdfGenerator                pdfGenerator;
    @Autowired
    private SvgGenerator                svgGenerator;
    @Autowired
    private ResultPdfGeneratorService   resultPdfGeneratorService;

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

        if (comparedPeriodKey == null) {
            Period period = periodService.findByCode(new PeriodCode(periodKey));

            return getSimpleReportAsXls(period, scopes);
        } else {
            Period period = periodService.findByCode(new PeriodCode(periodKey));
            Period comparedPeriod = periodService.findByCode(new PeriodCode(comparedPeriodKey));

            return getComparedReportAsXls(period, comparedPeriod, scopes);
        }
    }

    @Transactional(readOnly = false)
    @Security.Authenticated(SecuredController.class)
    public Result getReportAsPdf() throws BiffException, IOException, WriteException {

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

        Period period = periodService.findByCode(new PeriodCode(periodKey));
        Period comparedPeriod = null;
        if (comparedPeriodKey != null) {
            comparedPeriod = periodService.findByCode(new PeriodCode(comparedPeriodKey));
        }

        LanguageCode lang = securedController.getCurrentUser().getPerson().getDefaultLanguage();
        InterfaceTypeCode interfaceCode = securedController.getCurrentUser().getOrganization().getInterfaceCode();
        byte[] bytes = resultPdfGeneratorService.generate(lang, scopes, period, comparedPeriod, interfaceCode);

        DownloadFileDTO downloadFileDTO = new DownloadFileDTO();
        downloadFileDTO.setFilename("export_bilanGES_" + DateTime.now().toString("YMd-HH:mm").replace(':','h') + ".pdf");
        downloadFileDTO.setMimeType("application/pdf");
        downloadFileDTO.setBase64(new Base64().encodeAsString(bytes));

        return ok(downloadFileDTO);
    }

    //
    // UTILS
    //

    private Result getSimpleReportAsXls(Period period, List<Scope> scopes) throws IOException, WriteException, BiffException {
        LanguageCode lang = securedController.getCurrentUser().getPerson().getDefaultLanguage();
        InterfaceTypeCode interfaceCode = securedController.getCurrentUser().getOrganization().getInterfaceCode();
        byte[] content = resultExcelGeneratorService.generateExcelInStream(lang, scopes, period, interfaceCode);

        DownloadFileDTO downloadFileDTO = new DownloadFileDTO();
        downloadFileDTO.setFilename("export_bilanGES_" + DateTime.now().toString("YMd-HH:mm").replace(':','h') + ".xls");
        downloadFileDTO.setMimeType("application/vnd.ms-excel");
        downloadFileDTO.setBase64(new Base64().encodeAsString(content));

        return ok(downloadFileDTO);
    }

    private Result getComparedReportAsXls(Period period, Period comparedPeriod, List<Scope> scopes) throws IOException, WriteException, BiffException {
        LanguageCode lang = securedController.getCurrentUser().getPerson().getDefaultLanguage();
        InterfaceTypeCode interfaceCode = securedController.getCurrentUser().getOrganization().getInterfaceCode();
        byte[] content = resultExcelGeneratorService.generateComparedExcelInStream(lang, scopes, period, comparedPeriod, interfaceCode);

        DownloadFileDTO downloadFileDTO = new DownloadFileDTO();
        downloadFileDTO.setFilename("export_bilanGES_" + DateTime.now().toString("YMd-HH:mm").replace(':','h') + ".xls");
        downloadFileDTO.setMimeType("application/vnd.ms-excel");
        downloadFileDTO.setBase64(new Base64().encodeAsString(content));

        return ok(downloadFileDTO);
    }

    private Result getComparedReport(Period period, Period comparedPeriod, List<Scope> scopes) throws BiffException, IOException, WriteException {
        ResultsDTO resultsDTO = new ResultsDTO();

        // 1. Compute the ReportResult
        AwacCalculator awacCalculator = awacCalculatorService.findByCode(scopes.get(0).getOrganization().getInterfaceCode());
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
            resultsDTO.getLeftSvgDonuts().put(reportKey, resultSvgGeneratorService.getLeftDonut(mergedReportResultAggregation));
            resultsDTO.getRightSvgDonuts().put(reportKey, resultSvgGeneratorService.getRightDonut(mergedReportResultAggregation));

            // 2.4. Each ReportResult is rendered to a SVG string - HISTOGRAM
            resultsDTO.getSvgHistograms().put(reportKey, resultSvgGeneratorService.getHistogram(mergedReportResultAggregation));

            // 2.5. Each ReportResult is rendered to a SVG string - WEB
            resultsDTO.getSvgWebs().put(reportKey, resultSvgGeneratorService.getWeb(mergedReportResultAggregation));

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
            resultsDTO.getSvgHistogramsCEF().put(reportKey, resultSvgGeneratorService.getHistogram(mergedReportResultAggregation));
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
        AwacCalculator awacCalculator = awacCalculatorService.findByCode(scopes.get(0).getOrganization().getInterfaceCode());
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
            resultsDTO.getLeftSvgDonuts().put(reportKey, resultSvgGeneratorService.getDonut(reportResultAggregation));

            // 2.4. Each ReportResult is rendered to a SVG string - HISTOGRAM
            resultsDTO.getSvgHistograms().put(reportKey, resultSvgGeneratorService.getHistogram(reportResultAggregation));

            // 2.5. Each ReportResult is rendered to a SVG string - WEB
            resultsDTO.getSvgWebs().put(reportKey, resultSvgGeneratorService.getWeb(reportResultAggregation));

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


}
