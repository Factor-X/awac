
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
public class BaseActivityDataAE_BAD22 extends ActivityResultContributor {

	@Override
	public List<BaseActivityData> getBaseActivityData(Map<QuestionCode, List<QuestionSetAnswer>> questionSetAnswers) {
				List<BaseActivityData> res = new ArrayList<>();

		// Get Target Unit (équivalent.habitant in this case)
		// Allow finding unit by a UnitCode: getUnitByCode(UnitCode.équivalent.habitant)
		Unit baseActivityDataUnit = getUnitByCode(UnitCode.U5330);

		// For each set of answers in A185, build an ActivityBaseData (see specifications)
		List<QuestionSetAnswer> questionSetAnswersA185 = questionSetAnswers.get(QuestionCode.A185);		if (questionSetAnswersA185 == null) {			return res;		}		for (QuestionSetAnswer questionSetAnswer : questionSetAnswersA185) {

			Map<QuestionCode, QuestionAnswer> answersByCode = byQuestionCode(questionSetAnswer.getQuestionAnswers());

			QuestionAnswer questionA186Answer = answersByCode.get(QuestionCode.A186);
			QuestionAnswer questionA187Answer = answersByCode.get(QuestionCode.A187);

			if (questionA186Answer == null ||
					questionA187Answer == null) {
				continue;
			}


			BaseActivityData baseActivityData = new BaseActivityData();

			baseActivityData.setKey(BaseActivityDataCode.AE_BAD22);
			baseActivityData.setRank(2);
			baseActivityData.setSpecificPurpose("-bureau");
			baseActivityData.setActivityCategory(ActivityCategoryCode.AC_7);
			baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_10);
			// TODO: FLORIAN
			// baseActivityData.setActivityType(ActivityTypeCode.EPURATION);
			// TODO: FLORIAN
			// baseActivityData.setActivitySource(ActivitySourceCode.EAU_USEE);
			baseActivityData.setActivityOwnership(null);
			baseActivityData.setUnit(baseActivityDataUnit);
			baseActivityData.setValue(toDouble(questionA186Answer, baseActivityDataUnit) / 3 * toDouble(questionA187Answer, baseActivityDataUnit) / 365);

			res.add(baseActivityData);
		}
		return res;
	}

}
