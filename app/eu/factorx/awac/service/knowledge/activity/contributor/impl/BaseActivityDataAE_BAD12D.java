
package eu.factorx.awac.service.knowledge.activity.contributor.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import eu.factorx.awac.models.code.type.ActivityCategoryCode;
import eu.factorx.awac.models.code.type.ActivitySourceCode;
import eu.factorx.awac.models.code.type.ActivitySubCategoryCode;
import eu.factorx.awac.models.code.type.ActivityTypeCode;
import eu.factorx.awac.models.code.type.BaseActivityDataCode;
import eu.factorx.awac.models.code.type.QuestionCode;
import eu.factorx.awac.models.data.answer.QuestionAnswer;
import eu.factorx.awac.models.data.answer.QuestionSetAnswer;
import eu.factorx.awac.models.knowledge.Unit;
import eu.factorx.awac.models.reporting.BaseActivityData;
import eu.factorx.awac.service.knowledge.activity.contributor.ActivityResultContributor;

/**
 * CHECK XM
 */
public class BaseActivityDataAE_BAD12D extends ActivityResultContributor {

	@Override
	public List<BaseActivityData> getBaseActivityData(Map<QuestionCode, List<QuestionSetAnswer>> questionSetAnswers) {
				List<BaseActivityData> res = new ArrayList<>();

		// Get Target Unit (km.passager in this case)
		// Allow finding unit by a UnitCode: getUnitByCode(UnitCode.km.passager)
		Unit baseActivityDataUnit = getUnitBySymbol("km.passager");

		// For each set of answers in A94, build an ActivityBaseData (see specifications)
		List<QuestionSetAnswer> questionSetAnswersA94 = questionSetAnswers.get(QuestionCode.A94);		if (questionSetAnswersA94 == null) {			return res;		}		for (QuestionSetAnswer questionSetAnswer : questionSetAnswersA94) {

			Map<QuestionCode, QuestionAnswer> answersByCode = byQuestionCode(questionSetAnswer.getQuestionAnswers());

			QuestionAnswer questionA98Answer = answersByCode.get(QuestionCode.A98);

			if (questionA98Answer == null) {
				continue;
			}


			BaseActivityData baseActivityData = new BaseActivityData();

			baseActivityData.setKey(BaseActivityDataCode.AE_BAD12D);
			baseActivityData.setRank(2);
			baseActivityData.setSpecificPurpose(null);
			baseActivityData.setActivityCategory(ActivityCategoryCode.AC_5);
			baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_7);
			baseActivityData.setActivityType(ActivityTypeCode.AT_12);
			baseActivityData.setActivitySource(ActivitySourceCode.AS_164);
			baseActivityData.setActivityOwnership(false);
			baseActivityData.setUnit(baseActivityDataUnit);
			baseActivityData.setValue(toDouble(questionA98Answer, baseActivityDataUnit));

			res.add(baseActivityData);
		}
		return res;
	}

}
