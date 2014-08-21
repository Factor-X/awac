
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
public class BaseActivityDataAE_BAD24 extends ActivityResultContributor {

	@Override
	public List<BaseActivityData> getBaseActivityData(Map<QuestionCode, List<QuestionSetAnswer>> questionSetAnswers) {
				List<BaseActivityData> res = new ArrayList<>();

		// Get Target Unit (équivalent.habitant in this case)
		// Allow finding unit by a UnitCode: getUnitByCode(UnitCode.équivalent.habitant)
		Unit baseActivityDataUnit = getUnitByCode(UnitCode.U5330);

		// For each set of answers in A191, build an ActivityBaseData (see specifications)
		List<QuestionSetAnswer> questionSetAnswersA191 = questionSetAnswers.get(QuestionCode.A191);		if (questionSetAnswersA191 == null) {			return res;		}		for (QuestionSetAnswer questionSetAnswer : questionSetAnswersA191) {

			Map<QuestionCode, QuestionAnswer> answersByCode = byQuestionCode(questionSetAnswer.getQuestionAnswers());

			QuestionAnswer questionA192Answer = answersByCode.get(QuestionCode.A192);
			QuestionAnswer questionA193Answer = answersByCode.get(QuestionCode.A193);

			if (questionA192Answer == null ||
					questionA193Answer == null) {
				continue;
			}


			BaseActivityData baseActivityData = new BaseActivityData();

			baseActivityData.setKey(BaseActivityDataCode.AE_BAD24);
			baseActivityData.setRank(2);
			baseActivityData.setSpecificPurpose("-cantine");
			baseActivityData.setActivityCategory(ActivityCategoryCode.AC_7);
			baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_10);
			// TODO: FLORIAN
			// baseActivityData.setActivityType(ActivityTypeCode.EPURATION);
			// TODO: FLORIAN
			// baseActivityData.setActivitySource(ActivitySourceCode.EAU_USEE);
			baseActivityData.setActivityOwnership(null);
			baseActivityData.setUnit(baseActivityDataUnit);
			baseActivityData.setValue(toDouble(questionA192Answer, baseActivityDataUnit) / 4 * toDouble(questionA193Answer, baseActivityDataUnit) / 365);

			res.add(baseActivityData);
		}
		return res;
	}

}
