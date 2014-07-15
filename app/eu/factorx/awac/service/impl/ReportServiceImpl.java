package eu.factorx.awac.service.impl;

import java.lang.reflect.InvocationTargetException;
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
import eu.factorx.awac.service.UnitConversionService;
import eu.factorx.awac.service.UnitService;

@Component
public class ReportServiceImpl implements ReportService {

	@Autowired
	private QuestionAnswerService questionAnswerService;
	@Autowired
	private IndicatorService indicatorService;
	@Autowired
	private FactorService factorService;
	@Autowired
	private UnitService unitService;
	@Autowired
	private UnitConversionService unitConversionService;

	@Override
	public Report getReport(Scope scope, Period period) {

		// find all activity data
		List<BaseActivityData> activityData = getActivityData(scope, period);

		// build activity results (== link suitable indicators and factors to each activity data)
		List<BaseActivityResult> activityResults = new ArrayList<>();
		for (BaseActivityData baseActivityData : activityData) {
			List<Indicator> indicators = indicatorService.findCarbonIndicatorsForSitesByActivity(baseActivityData);
			if (indicators.isEmpty()) {
				// TODO save a warning: no indicator for activity base data: ...
			}
			for (Indicator indicator : indicators) {
				Factor factor = factorService.findByIndicatorAndActivity(indicator, baseActivityData);
				if (factor == null) {
					// TODO save a warning: no factor for activity base data: ... and indicator:...
				} else {
					activityResults.add(new BaseActivityResult(indicator, baseActivityData, factor));
				}
			}
		}

		return new Report(activityResults);
	}

	private List<BaseActivityData> getActivityData(Scope scope, Period period) {
		List<BaseActivityData> res = new ArrayList<>();

		res.addAll(getBaseActivityData1(scope, period));
		res.addAll(getBaseActivityData2(scope, period));
		res.addAll(getBaseActivityData3(scope, period));
		// TODO To Continue...

		return res;
	}

	/**
	 * See business specifications related to this BaseActivityData
	 * 
	 * @param questionAnswers
	 * @return a {@link BaseActivityData}
	 */
	private List<BaseActivityData> getBaseActivityData1(Scope scope, Period period) {
		List<BaseActivityData> res = new ArrayList<>();

		// Get Target Unit (GJ in this case)
		// TODO Allow finding unit by a UnitCode: getUnitByCode(UnitCode.GJ)
		Unit baseActivityDataUnit = unitService.findBySymbol("GJ");

		// Get all QuestionSet answers
		List<Map<QuestionCode, QuestionAnswer>> questionSetAnswers = getQuestionSetAnswers(scope, period,
				QuestionCode.A15);

		// For each set of answers, build an ActivityBaseData (see specifications)
		for (Map<QuestionCode, QuestionAnswer> questionAnswers : questionSetAnswers) {

			QuestionAnswer questionA16Answer = questionAnswers.get(QuestionCode.A16);
			QuestionAnswer questionA17Answer = questionAnswers.get(QuestionCode.A17);
			if (questionA16Answer == null || questionA17Answer == null) {
				continue;
			}

			int rank = 1;
			String specificPurpose = null;
			ActivityCategoryCode activityCategory = ActivityCategoryCode.ENERGIE;
			ActivitySubCategoryCode activitySubCategory = ActivitySubCategoryCode.ENERGIE_FOSSILE;
			ActivityTypeCode activityType = ActivityTypeCode.COMBUSTION_FOSSILE;
			ActivitySourceCode activitySource = getCode(questionA16Answer, ActivitySourceCode.class);
			Boolean activityOwnership = Boolean.TRUE;
			Unit unit = baseActivityDataUnit;
			Double value = getValue(questionA17Answer, unit);

			BaseActivityData baseActivityData = new BaseActivityData(rank, specificPurpose, activityCategory,
					activitySubCategory, activityType, activitySource, activityOwnership, unit, value);
			res.add(baseActivityData);
		}

		return res;
	}

	private List<BaseActivityData> getBaseActivityData2(Scope scope, Period period) {
		List<BaseActivityData> res = new ArrayList<>();

		// TODO To implement...
		return res;
	}

	private List<BaseActivityData> getBaseActivityData3(Scope scope, Period period) {
		List<BaseActivityData> res = new ArrayList<>();

		// TODO To implement...
		return res;
	}



	private List<Map<QuestionCode, QuestionAnswer>> getQuestionSetAnswers(Scope scope, Period period,
			QuestionCode questionSetCode) {
		List<QuestionAnswer> questionAnswers = questionAnswerService.findByScopeAndPeriodAndQuestionSet(scope, period,
				questionSetCode);

		Map<Integer, Map<QuestionCode, QuestionAnswer>> byRepetitionIndex = new HashMap<>();
		for (QuestionAnswer questionAnswer : questionAnswers) {
			questionAnswer.getQuestion();
			Integer repetitionIndex = questionAnswer.getRepetitionIndex();
			if (!byRepetitionIndex.containsKey(repetitionIndex)) {
				byRepetitionIndex.put(repetitionIndex, new HashMap<QuestionCode, QuestionAnswer>());
			}
			byRepetitionIndex.get(repetitionIndex).put(questionAnswer.getQuestion().getCode(), questionAnswer);
		}

		return new ArrayList<Map<QuestionCode, QuestionAnswer>>(byRepetitionIndex.values());
	}

	private <T extends Code> T getCode(QuestionAnswer questionAnswer, Class<T> codeClass) {
		if (questionAnswer == null) {
			return null;
		}
		CodeAnswerValue answerValue = (CodeAnswerValue) questionAnswer.getAnswerValues().get(0);

		T code = null;
		// TODO find a better way to instantiate a new Code impl!
		try {
			code = codeClass.getConstructor(String.class).newInstance(answerValue.getValue().getKey());
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			throw new RuntimeException("Unable to build a new " + codeClass, e);
		}
		return code;
	}

	private Double getValue(QuestionAnswer questionAnswer, Unit unit) {
		NumericAnswerValue numericAnswerValue = (NumericAnswerValue) questionAnswer.getAnswerValues().get(0);
		Unit answerUnit = numericAnswerValue.getUnit();
		Double answerValue = numericAnswerValue.doubleValue();
		if (!answerUnit.equals(unit)) {
			answerValue = convertValue(answerValue, answerUnit, unit);
		}
		return answerValue;
	}

	private Double convertValue(Double answerValue, Unit unitFrom, Unit toUnit) {
		return unitConversionService.convert(answerValue, unitFrom, toUnit, null);
	}

}
