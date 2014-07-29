package eu.factorx.awac.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import play.Logger;
import play.db.jpa.JPA;
import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.code.type.QuestionCode;
import eu.factorx.awac.models.data.answer.QuestionSetAnswer;
import eu.factorx.awac.models.knowledge.Factor;
import eu.factorx.awac.models.knowledge.Indicator;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.models.reporting.BaseActivityData;
import eu.factorx.awac.models.reporting.BaseActivityResult;
import eu.factorx.awac.models.reporting.Report;
import eu.factorx.awac.models.reporting.ReportBusinessException;
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
		List<BaseActivityData> activityData = getActivityData(allQuestionSetAnswers);

		// build activity results (== link suitable indicators and factors to each activity data)
		List<BaseActivityResult> activityResults = new ArrayList<>();
		for (BaseActivityData baseActivityData : activityData) {
			String baseActivityDataKey = baseActivityData.getKey().getKey();
			IndicatorSearchParameter indicatorSearchParam = new IndicatorSearchParameter(baseActivityData);
			List<Indicator> indicators = indicatorService.findByParameters(indicatorSearchParam);
			if (indicators.isEmpty()) {
				Logger.error(NO_SUITABLE_INDICATOR_ERROR_MSG, baseActivityDataKey, indicatorSearchParam);
				saveNoSuitableIndicatorError(baseActivityDataKey, indicatorSearchParam);
			}
			for (Indicator indicator : indicators) {
				String indicatorKey = indicator.getKey();
				FactorSearchParameter factorSearchParam = new FactorSearchParameter(indicator, baseActivityData);
				Factor factor = factorService.findByParameters(factorSearchParam);
				if (factor == null) {
					Logger.error(NO_SUITABLE_FACTOR_ERROR_MSG, baseActivityDataKey, indicatorKey, factorSearchParam);
					saveNoSuitableFactorError(baseActivityDataKey, indicatorKey, factorSearchParam);
				} else {
					BaseActivityResult baseActivityResult = new BaseActivityResult(indicator, baseActivityData, factor);
					activityResults.add(baseActivityResult);
				}
			}
		}

		return new Report(activityResults);
	}

	private List<BaseActivityData> getActivityData(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
		List<BaseActivityData> res = new ArrayList<>();
		for (ActivityResultContributor contributor : activityResultContributors) {
			res.addAll(contributor.getBaseActivityData(allQuestionSetAnswers));
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

	private void saveNoSuitableIndicatorError(String baseActivityDataKey, IndicatorSearchParameter indicatorSearchParam) {
		JPA.em().persist(new ReportBusinessException(ErrorType.NO_SUITABLE_INDICATOR, baseActivityDataKey, null, indicatorSearchParam.toString()));
	}

	private void saveNoSuitableFactorError(String baseActivityDataKey, String indicatorKey, FactorSearchParameter factorSearchParam) {
		JPA.em().persist(new ReportBusinessException(ErrorType.NO_SUITABLE_FACTOR, baseActivityDataKey, indicatorKey, factorSearchParam.toString()));
	}

	public void setActivityResultContributors(Set<ActivityResultContributor> activityResultContributors) {
		this.activityResultContributors = activityResultContributors;
	}

}
