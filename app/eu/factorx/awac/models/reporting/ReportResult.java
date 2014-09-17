package eu.factorx.awac.models.reporting;

import java.io.Serializable;
import java.util.*;

import javax.persistence.MappedSuperclass;

import eu.factorx.awac.models.code.type.IndicatorCode;
import eu.factorx.awac.models.code.type.IndicatorIsoScopeCode;
import eu.factorx.awac.models.knowledge.Indicator;
import eu.factorx.awac.models.knowledge.Report;

@MappedSuperclass
public class ReportResult implements Serializable {

	private static final long serialVersionUID = 1L;

	private Report report;
	
	private Map<IndicatorCode, List<BaseActivityResult>> activityResults = new HashMap<>();

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

	public Map<String, List<Double>> getScopeValuesByIndicator() {

		// group data by baseIndicator code key
		Map<String, List<BaseActivityResult>> dataByIndicator = new HashMap<>();
		for (Indicator indicator : report.getIndicators()) {
			String indicatorKey = indicator.getCode().getKey();
			dataByIndicator.put(indicatorKey, new ArrayList<BaseActivityResult>());
		}
		for (IndicatorCode indicatorCode : activityResults.keySet()) {
			List<BaseActivityResult> baseActivityResults = activityResults.get(indicatorCode);
			dataByIndicator.get(indicatorCode.getKey()).addAll(baseActivityResults);
		}

		// KEY => (allScopes, scope1, scope2, scope3, outOfScope)
		Map<String, List<Double>> scopeValuesByIndicator = new HashMap<>();

		// build a report line for each entry (=> adding values of each activity data linked to the baseIndicator name.)
		for (Map.Entry<String, List<BaseActivityResult>> indicatorData : dataByIndicator.entrySet()) {
			String indicatorKey = indicatorData.getKey();

			double scope1 = 0.0;
			double scope2 = 0.0;
			double scope3 = 0.0;
			double outOfScope = 0.0;

			for (BaseActivityResult baseActivityResult : indicatorData.getValue()) {
				Double numericValue = baseActivityResult.getNumericValue();
				IndicatorIsoScopeCode isoScope = baseActivityResult.getBaseIndicator().getIsoScope();

				if (IndicatorIsoScopeCode.SCOPE1.equals(isoScope)) {
					scope1 += numericValue;
				} else if (IndicatorIsoScopeCode.SCOPE2.equals(isoScope)) {
					scope2 += numericValue;
				} else if (IndicatorIsoScopeCode.SCOPE3.equals(isoScope)) {
					scope3 += numericValue;
				} else if (IndicatorIsoScopeCode.OUT_OF_SCOPE.equals(isoScope)) {
					outOfScope += numericValue;
				}
			}

			double allScopes = scope1 + scope2 + scope3 + outOfScope;

			scopeValuesByIndicator.put(indicatorKey, Arrays.asList(allScopes, scope1, scope2, scope3, outOfScope));
		}
		
		return scopeValuesByIndicator;
	}
}