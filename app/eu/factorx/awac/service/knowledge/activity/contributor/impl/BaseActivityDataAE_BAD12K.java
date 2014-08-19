
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
public class BaseActivityDataAE_BAD12K extends ActivityResultContributor {

	@Override
	public List<BaseActivityData> getBaseActivityData(Map<QuestionCode, List<QuestionSetAnswer>> questionSetAnswers) {
				List<BaseActivityData> res = new ArrayList<>();

		// Get Target Unit (véhicule.km in this case)
		// Allow finding unit by a UnitCode: getUnitByCode(UnitCode.véhicule.km)
		Unit baseActivityDataUnit = getUnitByCode(UnitCode.U5327);

		// For each set of answers in A94, build an ActivityBaseData (see specifications)
		List<QuestionSetAnswer> questionSetAnswersA94 = questionSetAnswers.get(QuestionCode.A94);		if (questionSetAnswersA94 == null) {			return res;		}		for (QuestionSetAnswer questionSetAnswer : questionSetAnswersA94) {

			Map<QuestionCode, QuestionAnswer> answersByCode = byQuestionCode(questionSetAnswer.getQuestionAnswers());

			QuestionAnswer questionA105Answer = answersByCode.get(QuestionCode.A105);

			if (questionA105Answer == null) {
				continue;
			}


			BaseActivityData baseActivityData = new BaseActivityData();

			baseActivityData.setKey(BaseActivityDataCode.AE_BAD12K);
			baseActivityData.setRank(2);
			baseActivityData.setSpecificPurpose(null);
			baseActivityData.setActivityCategory(ActivityCategoryCode.AC_5);
			baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_8);
			baseActivityData.setActivityType(ActivityTypeCode.AT_16);
			baseActivityData.setActivitySource(ActivitySourceCode.AS_164);
			baseActivityData.setActivityOwnership(false);
			baseActivityData.setUnit(baseActivityDataUnit);
			baseActivityData.setValue(toDouble(questionA105Answer, baseActivityDataUnit));

			res.add(baseActivityData);
		}
		return res;
	}

}
