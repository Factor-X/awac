
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
public class BaseActivityDataAE_BAD25 extends ActivityResultContributor {

	@Override
	public List<BaseActivityData> getBaseActivityData(Map<QuestionCode, List<QuestionSetAnswer>> questionSetAnswers) {
				List<BaseActivityData> res = new ArrayList<>();

		// Get Target Unit (m3 in this case)
		// Allow finding unit by a UnitCode: getUnitByCode(UnitCode.m3)
		Unit baseActivityDataUnit = getUnitByCode(UnitCode.U5122);

		// For each set of answers in A197, build an ActivityBaseData (see specifications)
		List<QuestionSetAnswer> questionSetAnswersA197 = questionSetAnswers.get(QuestionCode.A197);
		if (questionSetAnswersA197 == null) {
			return res;
		}

		for (QuestionSetAnswer questionSetAnswer : questionSetAnswersA197) {

			Map<QuestionCode, QuestionAnswer> answersByCode = byQuestionCode(questionSetAnswer.getQuestionAnswers());

			QuestionAnswer questionA198Answer = answersByCode.get(QuestionCode.A198);
			QuestionAnswer questionA199Answer = answersByCode.get(QuestionCode.A199);
			QuestionAnswer questionA200Answer = answersByCode.get(QuestionCode.A200);
			QuestionAnswer questionA195Answer = answersByCode.get(QuestionCode.A195);
            if (questionA198Answer == null ||
					questionA199Answer == null ||
					questionA200Answer == null ||
					questionA195Answer == null) {
				continue;
			}

			BaseActivityData baseActivityData = new BaseActivityData();

			baseActivityData.setKey(BaseActivityDataCode.AE_BAD25);
			baseActivityData.setRank(1);
			baseActivityData.setSpecificPurpose(null);
			baseActivityData.setActivityCategory(ActivityCategoryCode.AC_7);
			baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_10);
			baseActivityData.setActivityType(toActivityTypeCode(questionA200Answer));
			baseActivityData.setActivitySource(toActivitySourceCode(questionA198Answer));
            if (getCode(questionA195Answer).getKey().equals("1"))
    			baseActivityData.setActivityOwnership(Boolean.TRUE);
            else
                baseActivityData.setActivityOwnership(Boolean.FALSE);
			baseActivityData.setUnit(baseActivityDataUnit);
			baseActivityData.setValue(toDouble(questionA199Answer, baseActivityDataUnit));

			res.add(baseActivityData);
		}
		return res;
	}

}
