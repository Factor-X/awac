package eu.factorx.awac.service.knowledge.activity.contributor.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import eu.factorx.awac.models.code.type.*;
import eu.factorx.awac.models.data.answer.QuestionAnswer;
import eu.factorx.awac.models.data.answer.QuestionSetAnswer;
import eu.factorx.awac.models.knowledge.Unit;
import eu.factorx.awac.models.reporting.BaseActivityData;
import eu.factorx.awac.service.knowledge.activity.contributor.ActivityResultContributor;

/**
 * CHECK XM
 */
public class BaseActivityDataAE_BAD6 extends ActivityResultContributor {

	@Override
	public List<BaseActivityData> getBaseActivityData(Map<QuestionCode, List<QuestionSetAnswer>> questionSetAnswers) {
		List<BaseActivityData> res = new ArrayList<>();

		Unit kW = getUnitByCode(UnitCode.U5324);
		Unit kWh = getUnitByCode(UnitCode.U5156);
		Unit hour = getUnitByCode(UnitCode.U5147);

		// Get reference Electrical Consumption
		List<QuestionSetAnswer> questionSetAnswersA22 = questionSetAnswers.get(QuestionCode.A22);
		if ((questionSetAnswersA22 == null) || questionSetAnswersA22.isEmpty()) {
			return res;
		}
		QuestionSetAnswer questionSet22Answer = questionSetAnswersA22.get(0);
		Map<QuestionCode, QuestionAnswer> questionSet22AnswerQuestionAnswers = byQuestionCode(questionSet22Answer.getQuestionAnswers());
		QuestionAnswer questionA23Answer = questionSet22AnswerQuestionAnswers.get(QuestionCode.A23);
		QuestionAnswer questionA24Answer = questionSet22AnswerQuestionAnswers.get(QuestionCode.A24);

		if (questionA23Answer == null && questionA24Answer == null) {
			return res;
		}

		Double elecConsumption = 0.0;
		if (questionA23Answer != null) {
			elecConsumption += toDouble(questionA23Answer, kWh);
		}
		if (questionA24Answer != null) {
			elecConsumption += toDouble(questionA24Answer, kWh);
		}

		// For each set of answers in A47, build an ActivityBaseData (see specifications)
		List<QuestionSetAnswer> questionSetAnswersA47 = questionSetAnswers.get(QuestionCode.A47);
		if (questionSetAnswersA47 == null) {
			return res;
		}

		for (QuestionSetAnswer questionSetAnswer : questionSetAnswersA47) {

			Map<QuestionCode, QuestionAnswer> answersByCode = byQuestionCode(questionSetAnswer.getQuestionAnswers());

			QuestionAnswer questionA48Answer = answersByCode.get(QuestionCode.A48);
			QuestionAnswer questionA49Answer = answersByCode.get(QuestionCode.A49);

			if (questionA48Answer == null) {
				continue;
			}
			if (toBoolean(questionA48Answer) && questionA49Answer == null) {
				continue;
			}


			BaseActivityData baseActivityData = new BaseActivityData();

			baseActivityData.setKey(BaseActivityDataCode.AE_BAD6);
			baseActivityData.setRank(3);
			baseActivityData.setSpecificPurpose(null);
			baseActivityData.setActivityCategory(ActivityCategoryCode.AC_3);
			baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_5);
			baseActivityData.setActivityType(ActivityTypeCode.AT_10);
			baseActivityData.setActivitySource(ActivitySourceCode.AS_161);
			baseActivityData.setActivityOwnership(true);
			baseActivityData.setUnit(kW);
			if (!toBoolean(questionA48Answer)) {
				baseActivityData.setValue(0.0);
			} else {
				baseActivityData.setValue(elecConsumption / toDouble(questionA49Answer, hour));
			}
			res.add(baseActivityData);
		}
		return res;
	}

}
