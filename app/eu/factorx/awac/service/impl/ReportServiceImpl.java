package eu.factorx.awac.service.impl;

import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import play.Logger;
import play.db.jpa.JPA;
import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.code.type.ActivityCategoryCode;
import eu.factorx.awac.models.code.type.ActivitySubCategoryCode;
import eu.factorx.awac.models.code.type.BaseActivityDataCode;
import eu.factorx.awac.models.code.type.QuestionCode;
import eu.factorx.awac.models.data.answer.QuestionSetAnswer;
import eu.factorx.awac.models.knowledge.Factor;
import eu.factorx.awac.models.knowledge.Indicator;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.models.reporting.*;
import eu.factorx.awac.models.reporting.ReportBusinessException.ErrorType;
import eu.factorx.awac.service.FactorService;
import eu.factorx.awac.service.IndicatorService;
import eu.factorx.awac.service.QuestionSetAnswerService;
import eu.factorx.awac.service.ReportService;
import eu.factorx.awac.service.knowledge.activity.contributor.ActivityResultContributor;

@Component
public class ReportServiceImpl implements ReportService {

	private static final String NO_SUITABLE_INDICATOR_ERROR_MSG = "No suitable indicator to build activity result with BaseActivityData [key='{}'] : no indicator matching {}";

	private static final String NO_SUITABLE_FACTOR_ERROR_MSG = "No suitable factor to build activity result with BaseActivityData [key='{}'] and Indicator [key='{}'] : no factor matching {}";

	@Autowired
	private QuestionSetAnswerService questionSetAnswerService;

	@Autowired
	private IndicatorService indicatorService;

	@Autowired
	private FactorService factorService;

	/**
	 * No auto-wiring for this property: contributors have to be explicitly declared in components.xml
	 */
	private Set<ActivityResultContributor> activityResultContributors;

	@Override
	public Report getReport(Scope scope, Period period) {

		// find all question set answers (only "parents" => find where qsa.parent is null)
		Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers = getAllQuestionSetAnswers(scope, period);

		// find all activity data
		List<BaseActivityData> allActivityData = getActivityData(allQuestionSetAnswers);
		Logger.info("Built {} BADs for scope: {} and period: {}", allActivityData.size(), scope, period.getLabel());
		Set<BaseActivityDataCode> matchingIndicatorBADs = new HashSet<>();

		// find all carbon indicators for sites
		List<Indicator> indicators = indicatorService.findAllCarbonIndicatorsForSites();

		// calculate activity results
		List<BaseActivityResult> activityResults = new ArrayList<>();

		for (Indicator indicator : indicators) {

			List<BaseActivityData> activityData = filterByIndicator(allActivityData, indicator);
			for (BaseActivityData baseActivityData : activityData) {				
				matchingIndicatorBADs.add(baseActivityData.getKey());
			}

			if (!activityData.isEmpty()) {
				Logger.info("Indicator '{}': found {} BADs", indicator.getKey(), activityData.size());
				activityData = filterByRank(indicator.getKey(), activityData);
			}

			for (BaseActivityData baseActivityData : activityData) {
				FactorSearchParameter factorSearchParam = new FactorSearchParameter(indicator, baseActivityData);
				Factor factor = factorService.findByParameters(factorSearchParam);
				if (factor == null) {
					saveNoSuitableFactorError(baseActivityData, indicator, factorSearchParam);
				} else {
					activityResults.add(new BaseActivityResult(indicator, baseActivityData, factor));
				}
			}
		}

		// check not used BADs
		for (BaseActivityData bad : allActivityData) {
			if (!matchingIndicatorBADs.contains(bad.getKey())) {
				saveNoSuitableIndicatorError(bad);
			}
		}

		return new Report(activityResults, indicatorService);
	}

	private static List<BaseActivityData> filterByIndicator(List<BaseActivityData> allBads, Indicator indicator) {
		ActivityCategoryCode category = indicator.getActivityCategory();
		ActivitySubCategoryCode subCategory = indicator.getActivitySubCategory();
		Boolean ownership = indicator.getActivityOwnership();

		List<BaseActivityData> res = new ArrayList<>();
		for (BaseActivityData bad : allBads) {
			if (category.equals(bad.getActivityCategory()) && subCategory.equals(bad.getActivitySubCategory())
					&& ((ownership == null) || ownership.equals(bad.getActivityOwnership()))) {
				res.add(bad);
			}
		}
		return res;
	}

	private static List<BaseActivityData> filterByRank(String indicatorKey, List<BaseActivityData> indicatorBADs) {
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
					Logger.info("--> Excluding BAD '{}' with rank = {} (lowest rank for alternative group '{}' = {})", baseActivityData.getKey().getKey(), rank, alternativeGroup, minRank);
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

	private Map<QuestionCode, List<QuestionSetAnswer>> getAllQuestionSetAnswers(Scope scope, Period period) {
		List<QuestionSetAnswer> questionSetAnswers = questionSetAnswerService.findByScopeAndPeriod(scope, period);

		Map<QuestionCode, List<QuestionSetAnswer>> res = new HashMap<>();
		for (QuestionSetAnswer questionSetAnswer : questionSetAnswers) {
			QuestionCode code = questionSetAnswer.getQuestionSet().getCode();
			if (!res.containsKey(code)) {
				res.put(code, new ArrayList<QuestionSetAnswer>());
			}
			res.get(code).add(questionSetAnswer);
		}

		return res;
	}

	private void saveNoSuitableIndicatorError(BaseActivityData bad) {
		BaseActivityDataCode baseActivityDataCode = bad.getKey();
		IndicatorSearchParameter indicatorSearchParam = new IndicatorSearchParameter(bad);
		Logger.error(NO_SUITABLE_INDICATOR_ERROR_MSG, baseActivityDataCode.getKey(), indicatorSearchParam);
		JPA.em().persist(new ReportBusinessException(ErrorType.NO_SUITABLE_INDICATOR, baseActivityDataCode.getKey(), null, indicatorSearchParam.toString()));
	}

	private void saveNoSuitableFactorError(BaseActivityData bad, Indicator indicator, FactorSearchParameter factorSearchParam) {
		BaseActivityDataCode baseActivityDataCode = bad.getKey();
		String indicatorKey = indicator.getKey();
		Logger.error(NO_SUITABLE_FACTOR_ERROR_MSG, baseActivityDataCode.getKey(), indicatorKey, factorSearchParam);
		JPA.em().persist(new ReportBusinessException(ErrorType.NO_SUITABLE_FACTOR, baseActivityDataCode.getKey(), indicatorKey, factorSearchParam.toString()));
	}

	public void setActivityResultContributors(Set<ActivityResultContributor> activityResultContributors) {
		this.activityResultContributors = activityResultContributors;
	}

}
