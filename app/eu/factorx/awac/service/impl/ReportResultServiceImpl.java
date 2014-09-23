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
import eu.factorx.awac.service.BaseIndicatorService;
import eu.factorx.awac.service.FactorService;
import eu.factorx.awac.service.QuestionSetAnswerService;
import eu.factorx.awac.service.ReportResultService;
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
	private BaseIndicatorService baseIndicatorService;

	@Autowired
	private FactorService factorService;

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

		List<BaseIndicator> baseIndicators = getBaseIndicatorsForCalculator(awacCalculator);
		List<BaseActivityResult> baseActivityResults = computeBaseActivityResults(baseIndicators, scopes, period, logEntries);

		for (Report report : awacCalculator.getReports()) {
			reportResults.add(getReportResult(report, baseActivityResults, logEntries));
		}

		return new ReportResultCollection(reportResults, logEntries);
	}

	private ReportResult getReportResult(Report report, List<BaseActivityResult> baseActivityResults, List<ReportLogEntry> logEntries) {
		ReportResult reportResult = new ReportResult(report);

		IndicatorIsoScopeCode reportScope = report.getRestrictedScope();

		for (Indicator indicator : report.getIndicators()) {
			IndicatorCode indicatorCode = indicator.getCode();
			ArrayList<BaseActivityResult> indicatorActivityResults = new ArrayList<BaseActivityResult>();

			for (BaseIndicator baseIndicator : indicator.getBaseIndicators(reportScope)) {
				for (BaseActivityResult baseActivityResult : baseActivityResults) {
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

		if (scope instanceof  Site) {
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
		logEntries.add(new NoSuitableIndicator(baseActivityDataCode.getKey(), indicatorSearchParam));
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
		logEntries.add(new LowerRankInGroup(baseActivityData.getKey().getKey(), rank, alternativeGroup, minRank));
	}

	private void reportContribution(BaseActivityResult bar, List<ReportLogEntry> logEntries) {
		logEntries.add(new Contribution(bar));
	}

}
