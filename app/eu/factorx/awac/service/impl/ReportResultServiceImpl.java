package eu.factorx.awac.service.impl;

import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.business.Site;
import eu.factorx.awac.models.code.type.*;
import eu.factorx.awac.models.data.answer.QuestionSetAnswer;
import eu.factorx.awac.models.forms.AwacCalculator;
import eu.factorx.awac.models.knowledge.*;
import eu.factorx.awac.models.reporting.BaseActivityData;
import eu.factorx.awac.models.reporting.BaseActivityResult;
import eu.factorx.awac.models.reporting.ReportResult;
import eu.factorx.awac.service.*;
import eu.factorx.awac.service.impl.reporting.*;
import eu.factorx.awac.service.knowledge.activity.contributor.ActivityResultContributor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import play.Logger;

import java.util.*;

@Component
public class ReportResultServiceImpl implements ReportResultService {

	private static final String NO_SUITABLE_INDICATOR_ERROR_MSG = "No suitable baseIndicator to build activity result with BaseActivityData [key='{}'] : no baseIndicator matching {}";

	private static final String NO_SUITABLE_FACTOR_ERROR_MSG = "No suitable factor to build activity result with BaseActivityData [key='{}'] and BaseIndicator [key='{}'] : no factor matching {}";

	@Autowired
	private QuestionSetAnswerService questionSetAnswerService;
	@Autowired
	private BaseIndicatorService     baseIndicatorService;
	@Autowired
	private FactorService            factorService;
	@Autowired
	private IndicatorService         indicatorService;
	@Autowired
	private ReportIndicatorService   reportIndicatorService;

	/**
	 * No auto-wiring for this property: contributors have to be explicitly declared in components.xml
	 */
	private Set<ActivityResultContributor> activityResultContributors;

	private static List<BaseActivityData> filterByIndicator(List<BaseActivityData> allBads, BaseIndicator baseIndicator) {
		ActivityCategoryCode category = baseIndicator.getActivityCategory();
		ActivitySubCategoryCode subCategory = baseIndicator.getActivitySubCategory();
		Boolean ownership = baseIndicator.getActivityOwnership();

		List<BaseActivityData> res = new ArrayList<>();
		for (BaseActivityData bad : allBads) {
			if (category.equals(bad.getActivityCategory()) && subCategory.equals(bad.getActivitySubCategory())
					&& ((ownership == null) || ownership.equals(bad.getActivityOwnership()))) {
				res.add(bad);
			}
		}
		return res;
	}

	private static List<BaseActivityData> filterByRank(List<BaseActivityData> indicatorBADs, List<ReportLogEntry> logEntries) {
		List<BaseActivityData> res = new ArrayList<>();
		Map<String, Integer> minRankByAlternativeGroup = getMinRankByAlternativeGroup(indicatorBADs);
		reportLowRankMeasureWarnings(minRankByAlternativeGroup, logEntries);
		for (BaseActivityData baseActivityData : indicatorBADs) {
			String alternativeGroup = getAlternativeGroupKey(baseActivityData);
			if (alternativeGroup == null) {
				res.add(baseActivityData);
			} else {
				Integer rank = baseActivityData.getRank();
				Integer minRank = minRankByAlternativeGroup.get(alternativeGroup);
				if (rank == minRank) {
					res.add(baseActivityData);
				} else {
					reportLowerRankInGroup(baseActivityData, alternativeGroup, rank, minRank, logEntries);
				}
			}
		}
		return res;
	}

	private static void reportLowRankMeasureWarnings(Map<String, Integer> minRankByAlternativeGroup, List<ReportLogEntry> logEntries) {
		for (Map.Entry<String, Integer> minRankEntry : minRankByAlternativeGroup.entrySet()) {
			Integer minRank = minRankEntry.getValue();
			if (minRank >= 1) {
				String alternativeGroupKey = StringUtils.split(minRankEntry.getKey(), '-')[0];
				logEntries.add(new LowRankMeasureWarning(alternativeGroupKey));
			}
		}
	}

	private static Map<String, Integer> getMinRankByAlternativeGroup(List<BaseActivityData> indicatorBADs) {
		Map<String, Integer> minRankByAlternativeGroup = new HashMap<>();
		for (BaseActivityData baseActivityData : indicatorBADs) {
			String alternativeGroupKey = getAlternativeGroupKey(baseActivityData);
			if (alternativeGroupKey == null) {
				continue;
			}
			Integer rank = baseActivityData.getRank();
			if (minRankByAlternativeGroup.containsKey(alternativeGroupKey)) {
				Integer minRank = minRankByAlternativeGroup.get(alternativeGroupKey);
				if (rank < minRank) {
					minRankByAlternativeGroup.put(alternativeGroupKey, rank);
				}
			} else {
				minRankByAlternativeGroup.put(alternativeGroupKey, rank);
			}
		}
		return minRankByAlternativeGroup;
	}

	private static String getAlternativeGroupKey(BaseActivityData baseActivityData) {
		String alternativeGroup = StringUtils.trimToNull(baseActivityData.getAlternativeGroup());
		if (alternativeGroup == null) {
			return null;
		}
		String specificPurpose = StringUtils.trimToNull(baseActivityData.getSpecificPurpose());
		if (specificPurpose != null) {
			alternativeGroup += ("-" + specificPurpose);
		}
		return alternativeGroup;
	}

	private List<BaseActivityData> getActivityData(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
		List<BaseActivityData> res = new ArrayList<>();
		for (ActivityResultContributor contributor : activityResultContributors) {
			try {
				res.addAll(contributor.getBaseActivityData(allQuestionSetAnswers));
			} catch (Exception e) {
				Logger.error("Error wile retrieving base activity data from BAD: " + contributor.getClass().getSimpleName(), e);
			}
		}
		return res;
	}

	@Override
	public ReportResultCollection getReportResults(AwacCalculator awacCalculator, List<Scope> scopes, Period period) {
		List<ReportResult> reportResults = new ArrayList<>();
		List<ReportLogEntry> logEntries = new ArrayList<>();

		List<BaseActivityResult> baseActivityResults = getBaseActivityResults(awacCalculator, scopes, period, logEntries);

		for (Report report : awacCalculator.getReports()) {
			reportResults.add(getReportResult(report, period, baseActivityResults, logEntries));
		}

		return new ReportResultCollection(reportResults, logEntries);
	}

	@Override
	public List<BaseActivityResult> getBaseActivityResults(AwacCalculator awacCalculator, List<Scope> scopes, Period period, List<ReportLogEntry> logEntries) {
		List<BaseIndicator> baseIndicators = getBaseIndicatorsForCalculator(awacCalculator);
		return computeBaseActivityResults(baseIndicators, scopes, period, logEntries);
	}

	@Override
	public ReportResultCollectionAggregation aggregate(ReportResultCollection reportResultCollection) {
		ReportResultCollectionAggregation result = new ReportResultCollectionAggregation();

		for (ReportResult reportResult : reportResultCollection.getReportResults()) {
			result.getReportResultAggregations().add(aggregate(reportResult));
		}

		return result;
	}

	@Override
	public ReportResultAggregation aggregate(ReportResult reportResult) {
		ReportResultAggregation aggregationForResult = new ReportResultAggregation();

		Map<String, List<Double>> scopeValuesByIndicator = reportResult.getScopeValuesByIndicator();

		aggregationForResult.setReportCode(reportResult.getReport().getCode().getKey());
		aggregationForResult.setPeriod(reportResult.getPeriod());
		aggregationForResult.setReportRestrictedScope(reportResult.getReport().getRestrictedScope());

		for (Map.Entry<String, List<Double>> entry : scopeValuesByIndicator.entrySet()) {
			ReportResultIndicatorAggregation indicator = new ReportResultIndicatorAggregation(entry.getKey());
			indicator.setTotalValue(entry.getValue().get(0));
			indicator.setScope1Value(entry.getValue().get(1));
			indicator.setScope2Value(entry.getValue().get(2));
			indicator.setScope3Value(entry.getValue().get(3));
			indicator.setOutOfScopeValue(entry.getValue().get(4));
			aggregationForResult.getReportResultIndicatorAggregationList().add(indicator);
		}

		final Map<String, ReportIndicator> indicators = new HashMap<>();
		for (Map.Entry<String, List<Double>> entry : scopeValuesByIndicator.entrySet()) {
			indicators.put(entry.getKey(), reportIndicatorService.findByReportCodeAndIndicatorCode(reportResult.getReport().getCode().getKey(), entry.getKey()));
		}

		Collections.sort(aggregationForResult.getReportResultIndicatorAggregationList(), new Comparator<ReportResultIndicatorAggregation>() {
			@Override
			public int compare(ReportResultIndicatorAggregation o1, ReportResultIndicatorAggregation o2) {
				return indicators.get(o1.getIndicator()).getOrderIndex().compareTo(indicators.get(o2.getIndicator()).getOrderIndex());
			}
		});

		return aggregationForResult;
	}

	@Override
	public MergedReportResultCollectionAggregation mergeAsComparision(ReportResultCollectionAggregation a1, ReportResultCollectionAggregation a2) {
		MergedReportResultCollectionAggregation mergedReportResultCollectionAggregation = new MergedReportResultCollectionAggregation();

		// Establish a list of all reports
		Set<String> reports = new HashSet<>();
		Map<String, ReportResultAggregation> a1ReportsMap = new HashMap<>();
		Map<String, ReportResultAggregation> a2ReportsMap = new HashMap<>();
		for (ReportResultAggregation reportResultAggregation : a1.getReportResultAggregations()) {
			reports.add(reportResultAggregation.getReportCode());
			a1ReportsMap.put(reportResultAggregation.getReportCode(), reportResultAggregation);
		}
		for (ReportResultAggregation reportResultAggregation : a2.getReportResultAggregations()) {
			reports.add(reportResultAggregation.getReportCode());
			a2ReportsMap.put(reportResultAggregation.getReportCode(), reportResultAggregation);
		}

		// Iterate over them
		for (String report : reports) {

			ReportResultAggregation r1 = a1ReportsMap.get(report);
			ReportResultAggregation r2 = a2ReportsMap.get(report);

			if (r1 != null && r2 != null) {
				mergedReportResultCollectionAggregation.getMergedReportResultAggregations().add(merge(r1, r2));
			}

			if (r1 != null && r2 == null) {
				mergedReportResultCollectionAggregation.getMergedReportResultAggregations().add(convert(r1));
			}

			if (r1 == null && r2 != null) {
				mergedReportResultCollectionAggregation.getMergedReportResultAggregations().add(convert(r2));
			}

		}

		return mergedReportResultCollectionAggregation;
	}

	private MergedReportResultAggregation merge(ReportResultAggregation r1, ReportResultAggregation r2) {
		MergedReportResultAggregation result = new MergedReportResultAggregation();

		result.setReportCode(r1.getReportCode());
		result.setReportRestrictedScope(r1.getReportRestrictedScope());

		result.setLeftPeriod(r1.getPeriod());
		result.setRightPeriod(r2.getPeriod());

		// Create a list of indicators
		Set<String> indicators = new HashSet<>();
		Map<String, ReportResultIndicatorAggregation> r1IndicatorsMap = new HashMap<>();
		Map<String, ReportResultIndicatorAggregation> r2IndicatorsMap = new HashMap<>();
		for (ReportResultIndicatorAggregation reportResultIndicatorAggregation : r1.getReportResultIndicatorAggregationList()) {
			indicators.add(reportResultIndicatorAggregation.getIndicator());
			r1IndicatorsMap.put(reportResultIndicatorAggregation.getIndicator(), reportResultIndicatorAggregation);
		}
		for (ReportResultIndicatorAggregation reportResultIndicatorAggregation : r2.getReportResultIndicatorAggregationList()) {
			indicators.add(reportResultIndicatorAggregation.getIndicator());
			r2IndicatorsMap.put(reportResultIndicatorAggregation.getIndicator(), reportResultIndicatorAggregation);
		}

		List<String> indicatorsList = new ArrayList<>();
		indicatorsList.addAll(indicators);

		final String rc = result.getReportCode();
		Collections.sort(indicatorsList, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				ReportIndicator ri1 = reportIndicatorService.findByReportCodeAndIndicatorCode(rc, o1);
				ReportIndicator ri2 = reportIndicatorService.findByReportCodeAndIndicatorCode(rc, o2);
				return ri1.getOrderIndex().compareTo(ri2.getOrderIndex());
			}
		});

		// For each indicator, create a MergedReportResultIndicatorAggregation
		for (String indicator : indicators) {
			MergedReportResultIndicatorAggregation indicatorAggregation = new MergedReportResultIndicatorAggregation();

			indicatorAggregation.setIndicator(indicator);

			ReportResultIndicatorAggregation left = r1IndicatorsMap.get(indicator);
			if (left != null) {
				indicatorAggregation.setLeftTotalValue(left.getTotalValue());
				indicatorAggregation.setLeftScope1Value(left.getScope1Value());
				indicatorAggregation.setLeftScope2Value(left.getScope2Value());
				indicatorAggregation.setLeftScope3Value(left.getScope3Value());
				indicatorAggregation.setLeftOutOfScopeValue(left.getOutOfScopeValue());
			}

			ReportResultIndicatorAggregation right = r2IndicatorsMap.get(indicator);
			if (right != null) {
				indicatorAggregation.setRightTotalValue(right.getTotalValue());
				indicatorAggregation.setRightScope1Value(right.getScope1Value());
				indicatorAggregation.setRightScope2Value(right.getScope2Value());
				indicatorAggregation.setRightScope3Value(right.getScope3Value());
				indicatorAggregation.setRightOutOfScopeValue(right.getOutOfScopeValue());
			}

			result.getMergedReportResultIndicatorAggregationList().add(indicatorAggregation);
		}

		return result;
	}

	private MergedReportResultAggregation convert(ReportResultAggregation r1) {
		MergedReportResultAggregation result = new MergedReportResultAggregation();

		result.setReportCode(r1.getReportCode());
		result.setReportRestrictedScope(r1.getReportRestrictedScope());
		result.setLeftPeriod(r1.getPeriod());
		for (ReportResultIndicatorAggregation reportResultIndicatorAggregation : r1.getReportResultIndicatorAggregationList()) {
			MergedReportResultIndicatorAggregation indicatorAggregation = new MergedReportResultIndicatorAggregation();

			indicatorAggregation.setIndicator(reportResultIndicatorAggregation.getIndicator());

			indicatorAggregation.setLeftTotalValue(reportResultIndicatorAggregation.getTotalValue());
			indicatorAggregation.setLeftScope1Value(reportResultIndicatorAggregation.getScope1Value());
			indicatorAggregation.setLeftScope2Value(reportResultIndicatorAggregation.getScope1Value());
			indicatorAggregation.setLeftScope3Value(reportResultIndicatorAggregation.getScope1Value());
			indicatorAggregation.setLeftOutOfScopeValue(reportResultIndicatorAggregation.getOutOfScopeValue());

			result.getMergedReportResultIndicatorAggregationList().add(indicatorAggregation);
		}

		return result;
	}

	private ReportResult getReportResult(Report report, Period period, List<BaseActivityResult> baseActivityResults, List<ReportLogEntry> logEntries) {
		ReportResult reportResult = new ReportResult(report);
		reportResult.setPeriod(period);
		IndicatorIsoScopeCode reportScope = report.getRestrictedScope();

		for (Indicator indicator : report.getIndicators()) {
			IndicatorCode indicatorCode = indicator.getCode();
			ArrayList<BaseActivityResult> indicatorActivityResults = new ArrayList<BaseActivityResult>();

			for (BaseIndicator baseIndicator : indicator.getBaseIndicators(reportScope)) {
				if (reportScope != null) {
					if (!baseIndicator.getIsoScope().equals(reportScope)) {
						continue;
					}
				}
				for (BaseActivityResult baseActivityResult : baseActivityResults) {
					if (reportScope != null) {
						if (!baseActivityResult.getBaseIndicator().getIsoScope().equals(reportScope)) {
							continue;
						}
					}
					if (baseIndicator.getCode().equals(baseActivityResult.getBaseIndicator().getCode())) {
						indicatorActivityResults.add(baseActivityResult);
					}
				}
			}
			reportResult.getActivityResults().put(indicatorCode, indicatorActivityResults);
		}
		return reportResult;
	}

	private List<BaseIndicator> getBaseIndicatorsForCalculator(AwacCalculator awacCalculator) {
		Set<BaseIndicator> result = new HashSet<>();
		// for each report, add all base indicators (not yet present) in result
		for (Report report : awacCalculator.getReports()) {
			for (BaseIndicator baseIndicator : report.getBaseIndicators()) {
				result.add(baseIndicator);
			}
		}
		return new ArrayList<>(result);
	}

	private List<BaseActivityResult> computeBaseActivityResults(List<BaseIndicator> baseIndicators, List<Scope> scopes, Period period, List<ReportLogEntry> logEntries) {
		List<BaseActivityResult> results = new ArrayList<>();
		for (Scope scope : scopes) {
			List<BaseActivityResult> scopeBaseActivityResults = getReportResultForSingleScope(baseIndicators, scope, period, logEntries);
			results.addAll(scopeBaseActivityResults);
		}
		return results;
	}

	private List<BaseActivityResult> getReportResultForSingleScope(List<BaseIndicator> baseIndicators, Scope scope, Period period, List<ReportLogEntry> logEntries) {

		// find all question set answers (only "parents" => find where qsa.parent is null)
		Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers = questionSetAnswerService.getAllQuestionSetAnswers(scope, period);


		Site site = null;

		if (scope instanceof Site) {
			site = (Site) scope;
		}

		// find all activity data
		List<BaseActivityData> allBADs = getActivityData(allQuestionSetAnswers);
		Logger.info("Built {} BADs for scope: {} and period: {}", allBADs.size(), scope, period.getLabel());
		Set<BaseActivityDataCode> matchingIndicatorBADs = new HashSet<>();

		// calculate activity results
		List<BaseActivityResult> activityResults = new ArrayList<>();

		for (BaseIndicator baseIndicator : baseIndicators) {

			List<BaseActivityData> indicatorBADs = filterByIndicator(allBADs, baseIndicator);
			if (indicatorBADs.isEmpty()) {
				continue;
			}

			Logger.info("BaseIndicator '{}': found {} BADs", baseIndicator.getCode(), indicatorBADs.size());
			for (BaseActivityData baseActivityData : indicatorBADs) {
				matchingIndicatorBADs.add(baseActivityData.getKey());
			}

			indicatorBADs = filterByRank(indicatorBADs, logEntries);

			for (BaseActivityData baseActivityData : indicatorBADs) {
				FactorSearchParameter factorSearchParam = new FactorSearchParameter(baseIndicator, baseActivityData);
				Factor factor = factorService.findByParameters(factorSearchParam);
				if (factor == null) {
					reportNoSuitableFactorError(baseActivityData, baseIndicator, factorSearchParam, logEntries);
				} else {
					BaseActivityResult bar = new BaseActivityResult(baseIndicator, baseActivityData, factor, site);
					activityResults.add(bar);
					reportContribution(bar, logEntries);
				}
			}
		}

		checkNotUsedBADs(allBADs, matchingIndicatorBADs, logEntries);

		return activityResults;
	}

	private void checkNotUsedBADs(List<BaseActivityData> allBADs, Set<BaseActivityDataCode> matchingIndicatorBADs, List<ReportLogEntry> logEntries) {
		for (BaseActivityData bad : allBADs) {
			if (!matchingIndicatorBADs.contains(bad.getKey())) {
				reportNoSuitableIndicatorError(InterfaceTypeCode.ENTERPRISE, bad, logEntries);
			}
		}
	}

	public void setActivityResultContributors(Set<ActivityResultContributor> activityResultContributors) {
		this.activityResultContributors = activityResultContributors;
	}


	private void reportNoSuitableIndicatorError(InterfaceTypeCode interfaceType, BaseActivityData bad, List<ReportLogEntry> logEntries) {
		BaseActivityDataCode baseActivityDataCode = bad.getKey();
		IndicatorSearchParameter indicatorSearchParam = new IndicatorSearchParameter(interfaceType, bad);
		Logger.error(NO_SUITABLE_INDICATOR_ERROR_MSG, baseActivityDataCode.getKey(), indicatorSearchParam);
		logEntries.add(new NoSuitableIndicator(bad));
	}

	private void reportNoSuitableFactorError(BaseActivityData bad, BaseIndicator baseIndicator, FactorSearchParameter factorSearchParam, List<ReportLogEntry> logEntries) {
		BaseActivityDataCode baseActivityDataCode = bad.getKey();
		String baseIndicatorKey = baseIndicator.getCode().getKey();
		Logger.error(NO_SUITABLE_FACTOR_ERROR_MSG, baseActivityDataCode.getKey(), baseIndicatorKey, factorSearchParam);
		logEntries.add(new NoSuitableFactor(baseIndicator, bad));
	}

	private static void reportLowerRankInGroup(BaseActivityData baseActivityData, String alternativeGroup, Integer rank, Integer minRank, List<ReportLogEntry> logEntries) {
		Logger.info("--> Excluding BAD '{}' with rank = {} (lowest rank for alternative group '{}' = {})", baseActivityData.getKey().getKey(), rank, alternativeGroup,
				minRank);

		logEntries.add(new LowerRankInGroup(baseActivityData));
	}

	private void reportContribution(BaseActivityResult bar, List<ReportLogEntry> logEntries) {
		logEntries.add(new Contribution(bar));
	}

}
