package eu.factorx.awac.service.impl.reporting;

import eu.factorx.awac.models.reporting.ReportResult;

import java.util.List;

public class ReportResultCollection {


	private final List<ReportResult>   reportResults;
	private final List<ReportLogEntry> logEntries;

	public ReportResultCollection(List<ReportResult> reportResults, List<ReportLogEntry> logEntries) {

		this.reportResults = reportResults;
		this.logEntries = logEntries;
	}

	public List<ReportResult> getReportResults() {
		return reportResults;
	}

	public List<ReportLogEntry> getLogEntries() {
		return logEntries;
	}
}
