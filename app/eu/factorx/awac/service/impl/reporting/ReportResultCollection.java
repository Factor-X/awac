package eu.factorx.awac.service.impl.reporting;

import java.util.List;

import eu.factorx.awac.models.reporting.ReportResult;

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
