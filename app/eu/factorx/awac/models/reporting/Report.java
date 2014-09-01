package eu.factorx.awac.models.reporting;

import eu.factorx.awac.models.code.type.IndicatorIsoScopeCode;
import eu.factorx.awac.service.IndicatorService;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.*;

@MappedSuperclass
public class Report implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<BaseActivityResult>  activityResults;
	private Map<String, List<Double>> scopeValuesByIndicator; // KEY => (allScopes, scope1, scope2, scope3, outOfScope)

	private IndicatorService indicatorService;

	protected Report() {
		super();
	}

	public Report(List<BaseActivityResult> activityResults, IndicatorService indicatorService) {
		super();

		this.indicatorService = indicatorService;
		this.scopeValuesByIndicator = new HashMap<>();

		setActivityResults(activityResults);
	}

	public List<BaseActivityResult> getActivityResults() {
		return activityResults;
	}

	public void setActivityResults(List<BaseActivityResult> activityResults) {

		this.activityResults = activityResults;

		// group data by indicator name
		Map<String, List<BaseActivityResult>> dataByIndicator = new HashMap<>();
		for (String indicatorName : indicatorService.findAllIndicatorNames()) {
			dataByIndicator.put(indicatorName, new ArrayList<BaseActivityResult>());
		}
		for (BaseActivityResult baseActivityResult : activityResults) {
			String indicatorName = baseActivityResult.getIndicator().getName();
			dataByIndicator.get(indicatorName).add(baseActivityResult);
		}

		scopeValuesByIndicator.clear();

		// build a report line for each entry (=> adding values of each activity data linked to the indicator name.)
		for (Map.Entry<String, List<BaseActivityResult>> indicatorData : dataByIndicator.entrySet()) {
			String indicatorName = indicatorData.getKey();

			double scope1 = 0.0;
			double scope2 = 0.0;
			double scope3 = 0.0;
			double outOfScope = 0.0;

			for (BaseActivityResult baseActivityResult : indicatorData.getValue()) {
				Double numericValue = baseActivityResult.getNumericValue();
				IndicatorIsoScopeCode isoScope = baseActivityResult.getIndicator().getIsoScope();

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

			scopeValuesByIndicator.put(indicatorName, Arrays.asList(allScopes, scope1, scope2, scope3, outOfScope));
		}
	}

	public Map<String, List<Double>> getScopeValuesByIndicator() {
		return scopeValuesByIndicator;
	}
}