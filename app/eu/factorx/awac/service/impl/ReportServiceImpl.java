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

	@Autowired
	private QuestionSetAnswerService questionSetAnswerService;

	@Autowired
	private IndicatorService indicatorService;

	@Autowired
	private FactorService factorService;

	/**
	 * No Autowired for this property: contributors have to be explicitly declared in components.xml
	 */
	private Set<ActivityResultContributor> activityResultContributors;

	// TODO Implement all ActivityResultContributors!
	// Only one contributor implemented for now... BaseActivityDataAE_BAD1, consolidating answers to QuestionSet A15 (Tab2)
	@Override
	public Report getReport(Scope scope, Period period) {

		// find all question set answers (only "parents" => find where qsa.parent is null)
		Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers = getAllQuestionSetAnswers(scope, period);

		// find all activity data
		List<BaseActivityData> activityData = getActivityData(allQuestionSetAnswers);

		// build activity results (== link suitable indicators and factors to each activity data)
		List<BaseActivityResult> activityResults = new ArrayList<>();
		for (BaseActivityData baseActivityData : activityData) {
			List<Indicator> indicators = indicatorService.findCarbonIndicatorsForSitesByActivity(baseActivityData);
			if (indicators.isEmpty()) {
				Logger.error("No suitable indicator to build activity result with BaseActivityData: " + baseActivityData);
				saveNoSuitableIndicatorError(baseActivityData);
			}
			for (Indicator indicator : indicators) {
				Factor factor = factorService.findByIndicatorAndActivity(indicator, baseActivityData);
				if (factor == null) {
					Logger.error("No suitable factor to build activity result with BaseActivityData: " + baseActivityData + " and Indicator: " + indicator);
					saveNoSuitableFactorError(baseActivityData, indicator);
				} else {
					BaseActivityResult baseActivityResult = new BaseActivityResult(indicator, baseActivityData, factor);
//					Logger.info("Adding BaseActivityResult: " + baseActivityResult);
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

	private void saveNoSuitableIndicatorError(BaseActivityData baseActivityData) {
		JPA.em().persist(new ReportBusinessException(ErrorType.NO_SUITABLE_INDICATOR, baseActivityData, null));
	}

	private void saveNoSuitableFactorError(BaseActivityData baseActivityData, Indicator indicator) {
		JPA.em().persist(new ReportBusinessException(ErrorType.NO_SUITABLE_FACTOR, baseActivityData, indicator));
	}

	public void setActivityResultContributors(Set<ActivityResultContributor> activityResultContributors) {
		this.activityResultContributors = activityResultContributors;
	}

}
