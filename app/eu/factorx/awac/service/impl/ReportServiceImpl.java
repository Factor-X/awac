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
import eu.factorx.awac.models.data.answer.QuestionSetAnswer;
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
import eu.factorx.awac.service.QuestionSetAnswerService;
import eu.factorx.awac.service.ReportService;
import eu.factorx.awac.service.UnitConversionService;
import eu.factorx.awac.service.UnitService;

@Component
public class ReportServiceImpl implements ReportService {

	@Autowired
	private QuestionSetAnswerService questionSetAnswerService;
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

		// find all question set answers (only "parents" => find where qsa.parent is null)
		Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers = getAllQuestionSetAnswers(scope, period);
		
		// find all activity data
		List<BaseActivityData> activityData = getActivityData(allQuestionSetAnswers);

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

	private List<BaseActivityData> getActivityData(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
		List<BaseActivityData> res = new ArrayList<>();

		res.addAll(getBaseActivityData1(allQuestionSetAnswers));
//		res.addAll(getBaseActivityData2(allQuestionSetAnswers));
//		res.addAll(getBaseActivityData3(allQuestionSetAnswers));
		// TODO To Continue...

		return res;
	}

	/**
	 * See business specifications related to this BaseActivityData
	 * 
	 * @param questionAnswers
	 * @return a {@link BaseActivityData}
	 */
	private List<BaseActivityData> getBaseActivityData1(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
		List<BaseActivityData> res = new ArrayList<>();

		// Get Target Unit (GJ in this case)
		// TODO Allow finding unit by a UnitCode: getUnitByCode(UnitCode.GJ)
		Unit baseActivityDataUnit = unitService.findBySymbol("GJ");

		// For each set of answers in A15, build an ActivityBaseData (see specifications)
		for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A15)) {
			Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());
			
			QuestionAnswer questionA16Answer = answersByCode.get(QuestionCode.A16);
			QuestionAnswer questionA17Answer = answersByCode.get(QuestionCode.A17);
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

			BaseActivityData baseActivityData = new BaseActivityData("AE-BAD", activityCategory, activitySubCategory, activityType, activitySource, activityOwnership, value, unit, rank, specificPurpose);
			
			res.add(baseActivityData);
		}
		return res;
	}

	private List<BaseActivityData> getBaseActivityData2(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
		List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (kWh in this case)
        // TODO Allow finding unit by a UnitCode: getUnitByCode(UnitCode.kWh)
        Unit baseActivityDataUnit = unitService.findBySymbol("kWh");

        // For each set of answers in A12, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A22)) {
            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA23Answer = answersByCode.get(QuestionCode.A23);
            QuestionAnswer questionA24Answer = answersByCode.get(QuestionCode.A24);

            if (questionA23Answer != null) {
                int rank = 1;
                String specificPurpose = null;
                ActivityCategoryCode activityCategory = ActivityCategoryCode.ENERGIE;
                ActivitySubCategoryCode activitySubCategory = ActivitySubCategoryCode.ELECTRICITE;
                ActivityTypeCode activityType = ActivityTypeCode.ELEC_PAYS_VERTE;
                ActivitySourceCode activitySource = ActivitySourceCode.MOYENNE_UE27;
                Boolean activityOwnership = Boolean.TRUE;
                Unit unit = baseActivityDataUnit;
                Double value = getValue(questionA23Answer, unit);

                BaseActivityData baseActivityData = new BaseActivityData("AE-BAD2a", activityCategory, activitySubCategory, activityType, activitySource, activityOwnership, value, unit, rank, specificPurpose);

                res.add(baseActivityData);
            }

            if (questionA24Answer != null) {
                int rank = 1;
                String specificPurpose = null;
                ActivityCategoryCode activityCategory = ActivityCategoryCode.ENERGIE;
                ActivitySubCategoryCode activitySubCategory = ActivitySubCategoryCode.ELECTRICITE;
                ActivityTypeCode activityType = ActivityTypeCode.ELEC_PAYS_GRISE;
                ActivitySourceCode activitySource = ActivitySourceCode.MOYENNE_UE27;
                Boolean activityOwnership = Boolean.TRUE;
                Unit unit = baseActivityDataUnit;
                Double value = getValue(questionA24Answer, unit);

                BaseActivityData baseActivityData = new BaseActivityData("AE-BAD2b", activityCategory, activitySubCategory, activityType, activitySource, activityOwnership, value, unit, rank, specificPurpose);

                res.add(baseActivityData);
            }
            return res;
        }


        // TODO To implement...
		return res;
	}

	private List<BaseActivityData> getBaseActivityData3(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
		List<BaseActivityData> res = new ArrayList<>();

		// TODO To implement...
		return res;
	}



	private Map<QuestionCode, QuestionAnswer> toQuestionAnswersByQuestionCodeMap(List<QuestionAnswer> questionAnswers) {
		Map<QuestionCode, QuestionAnswer> res = new HashMap<>();
		for (QuestionAnswer questionAnswer : questionAnswers) {
			res.put(questionAnswer.getQuestion().getCode(), questionAnswer);
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
