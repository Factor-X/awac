package eu.factorx.awac.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.type.ActivityCategoryCode;
import eu.factorx.awac.models.code.type.ActivitySourceCode;
import eu.factorx.awac.models.code.type.ActivitySubCategoryCode;
import eu.factorx.awac.models.code.type.ActivityTypeCode;
import eu.factorx.awac.models.code.type.QuestionCode;
import eu.factorx.awac.models.code.type.UnitCode;
import eu.factorx.awac.models.data.answer.QuestionAnswer;
import eu.factorx.awac.models.data.answer.type.CodeAnswerValue;
import eu.factorx.awac.models.data.answer.type.NumericAnswerValue;
import eu.factorx.awac.models.knowledge.Factor;
import eu.factorx.awac.models.knowledge.Indicator;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.models.knowledge.Unit;
import eu.factorx.awac.models.reporting.BaseActivityData;
import eu.factorx.awac.models.reporting.BaseActivityResult;
import eu.factorx.awac.models.reporting.Report;
import eu.factorx.awac.service.FactorService;
import eu.factorx.awac.service.IndicatorService;
import eu.factorx.awac.service.QuestionAnswerService;
import eu.factorx.awac.service.ReportService;

@Component
public class ReportServiceImpl implements ReportService {

	@Autowired
	private QuestionAnswerService questionAnswerService;
	@Autowired
	private IndicatorService indicatorService;
	@Autowired
	private FactorService factorService;

	@Override
	public Report getReport(Scope scope, Period period) {
		Map<QuestionCode, QuestionAnswer> questionAnswers = getQuestionAnswers(scope, period);
		List<BaseActivityData> activityData = getActivityData(questionAnswers);

		List<BaseActivityResult> activityResults = new ArrayList<>();
		for (BaseActivityData baseActivityData : activityData) {
			List<Indicator> indicators = indicatorService.findCarbonIndicatorsForSitesByActivity(baseActivityData);
			for (Indicator indicator : indicators) {
				Factor factor = factorService.findByIndicatorAndActivity(indicator, baseActivityData);
				if (factor != null) {
					activityResults.add(new BaseActivityResult(indicator, baseActivityData, factor));
				}
			}
		}

		return new Report(activityResults);
	}

	private List<BaseActivityData> getActivityData(Map<QuestionCode, QuestionAnswer> questionAnswers) {
		List<BaseActivityData> res = new ArrayList<>();

		BaseActivityData baseActivityData1 = getBaseActivityData1(questionAnswers);
		if (baseActivityData1 != null)
			res.add(baseActivityData1);

		BaseActivityData baseActivityData2 = getBaseActivityData2(questionAnswers);
		if (baseActivityData2 != null)
			res.add(baseActivityData2);

		// TODO TO continue...
		
		return res;
	}

	/**
	 * See business specifications related to this BaseActivityData
	 * 
	 * @param questionAnswers
	 * @return a {@link BaseActivityData}
	 */
	private BaseActivityData getBaseActivityData1(Map<QuestionCode, QuestionAnswer> questionAnswers) {
		QuestionAnswer questionA16Answer = questionAnswers.get(QuestionCode.A16);
		QuestionAnswer questionA17Answer = questionAnswers.get(QuestionCode.A17);
		if (questionA16Answer == null || questionA17Answer == null) {
			return null;
		}

		return new BaseActivityData(1, null, ActivityCategoryCode.ENERGIE, ActivitySubCategoryCode.ENERGIE_FOSSILE,
				ActivityTypeCode.COMBUSTION_FOSSILE, getAnswerOfValueSelectionQuestion(questionA16Answer,
						ActivitySourceCode.class), Boolean.TRUE, getUnitByCode(UnitCode.GJ),
				getAnswerOfNumericQuestion(questionA17Answer));
	}

	private BaseActivityData getBaseActivityData2(Map<QuestionCode, QuestionAnswer> questionAnswers) {
		// TODO To implement...
		return null;
	}

	private Map<QuestionCode, QuestionAnswer> getQuestionAnswers(Scope scope, Period period) {
		Map<QuestionCode, QuestionAnswer> res = new HashMap<>();
		for (QuestionAnswer questionAnswer : questionAnswerService.findByScopeAndPeriod(scope, period)) {
			res.put(questionAnswer.getQuestion().getCode(), questionAnswer);
		}
		return res;
	}

	private <T extends Code> T getAnswerOfValueSelectionQuestion(QuestionAnswer questionAnswer, Class<T> codeClass) {
		if (questionAnswer == null) {
			return null;
		}
		CodeAnswerValue answerValue = (CodeAnswerValue) questionAnswer.getAnswerValues().get(0);
		@SuppressWarnings("unchecked")
		T value = (T) answerValue.getValue();
		return value;
	}

	private NumericAnswerValue getAnswerOfNumericQuestion(QuestionAnswer questionAnswer) {
		if (questionAnswer == null) {
			return null;
		}
		return (NumericAnswerValue) questionAnswer.getAnswerValues().get(0);
	}

	private Unit getUnitByCode(UnitCode unitCode) {
		// TODO Auto-generated method stub
		return null;
	}

}
