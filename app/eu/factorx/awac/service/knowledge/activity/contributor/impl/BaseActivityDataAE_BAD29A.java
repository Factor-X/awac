
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


public class BaseActivityDataAE_BAD29A extends ActivityResultContributor {

	@Override
	public List<BaseActivityData> getBaseActivityData(Map<QuestionCode, List<QuestionSetAnswer>> questionSetAnswers) {
				List<BaseActivityData> res = new ArrayList<>();

		// Get Target Unit (m2 in this case)
		// Allow finding unit by a UnitCode: getUnitByCode(UnitCode.m2)
		Unit baseActivityDataUnit = getUnitByCode(UnitCode.U5115);

		// For each set of answers in A231, build an ActivityBaseData (see specifications)
		List<QuestionSetAnswer> questionSetAnswersA231 = questionSetAnswers.get(QuestionCode.A231);
		if (questionSetAnswersA231 == null) {
			return res;
		}

		for (QuestionSetAnswer questionSetAnswer : questionSetAnswersA231) {

			Map<QuestionCode, QuestionAnswer> answersByCode = byQuestionCode(questionSetAnswer.getQuestionAnswers());

			QuestionAnswer questionA233Answer = answersByCode.get(QuestionCode.A233);
			QuestionAnswer questionA234Answer = answersByCode.get(QuestionCode.A234);
			QuestionAnswer questionA232Answer = answersByCode.get(QuestionCode.A232);

			if (questionA233Answer == null ||
					questionA234Answer == null ||
					questionA232Answer == null) {
				continue;
			}

			BaseActivityData baseActivityData = new BaseActivityData();

			baseActivityData.setKey(BaseActivityDataCode.AE_BAD29A);
			baseActivityData.setRank(1);
			baseActivityData.setSpecificPurpose(toString(questionA232Answer));
			baseActivityData.setActivityCategory(ActivityCategoryCode.AC_9);
			baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_10);
			baseActivityData.setActivityType(toActivityTypeCode(questionA233Answer));
			baseActivityData.setActivitySource(toActivitySourceCode(questionA233Answer));
			baseActivityData.setActivityOwnership(null);
			baseActivityData.setUnit(baseActivityDataUnit);
			baseActivityData.setValue(toDouble(questionA234Answer, baseActivityDataUnit));

			res.add(baseActivityData);
		}
		return res;
	}

}
