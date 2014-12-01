package eu.factorx.awac.models.reporting;

import java.io.Serializable;
import java.util.*;

import javax.persistence.MappedSuperclass;

import eu.factorx.awac.models.code.type.IndicatorCode;
import eu.factorx.awac.models.code.type.IndicatorIsoScopeCode;
import eu.factorx.awac.models.knowledge.Indicator;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.models.knowledge.Report;
import eu.factorx.awac.service.BaseActivityResultService;
import eu.factorx.awac.service.impl.reporting.ReportLogEntry;
import org.springframework.beans.factory.annotation.Autowired;

@MappedSuperclass
public class ReportResult implements Serializable {

    @Autowired
    private BaseActivityResultService baseActivityResultService;

	private static final long serialVersionUID = 1L;

	private Report report;

	private Map<IndicatorCode, List<BaseActivityResult>> activityResults = new HashMap<>();

	private Map<IndicatorCode, Integer> activityResultsOrder = new HashMap<>();

	private List<ReportLogEntry> logEntries = new ArrayList<>();
	private Period period;

	protected ReportResult() {
		super();
	}

	public ReportResult(Report report) {
		this.report = report;
	}

	public Report getReport() {
		return report;
	}

	public Map<IndicatorCode, List<BaseActivityResult>> getActivityResults() {
		return activityResults;
	}

	public List<ReportLogEntry> getLogEntries() {
		return logEntries;
	}

	public Period getPeriod() {
		return period;
	}

	public void setPeriod(Period period) {
		this.period = period;
	}


	public Map<IndicatorCode, Integer> getActivityResultsOrder() {
		return activityResultsOrder;
	}
}