
package eu.factorx.awac.service.knowledge.activity.contributor.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import eu.factorx.awac.models.code.type.*;
import eu.factorx.awac.models.data.answer.QuestionAnswer;
import eu.factorx.awac.models.data.answer.QuestionSetAnswer;
import eu.factorx.awac.models.knowledge.Unit;
import eu.factorx.awac.models.reporting.BaseActivityData;

/**
 * CHECKED BY FLO
 */
public class BaseActivityDataAE_BAD34C extends BaseActivityDataForProducts {

	public List<BaseActivityData> getBaseActivityData(QuestionSetAnswer questionSetAnswer, QuestionAnswer questionA245Answer) {
				List<BaseActivityData> res = new ArrayList<>();

		// Get Target Unit (kg in this case)
		// Allow finding unit by a UnitCode: getUnitByCode(UnitCode.kg)
		Unit baseActivityDataUnit = getUnitByCode(UnitCode.U5133);

        // For each set of answers in A288, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswersChild : questionSetAnswer.getChildren()) {
            if (questionSetAnswersChild.getQuestionSet().getCode().equals(QuestionCode.A288)) {

                Map<QuestionCode, QuestionAnswer> answersByCode = byQuestionCode(questionSetAnswersChild.getQuestionAnswers());

                QuestionAnswer questionA289Answer = answersByCode.get(QuestionCode.A289);
                QuestionAnswer questionA290Answer = answersByCode.get(QuestionCode.A290);


                if (questionA289Answer == null || questionA290Answer == null) {
                    continue;
                }

                BaseActivityData baseActivityData = new BaseActivityData();

                baseActivityData.setKey(BaseActivityDataCode.AE_BAD34C);
                baseActivityData.setRank(1);
                baseActivityData.setSpecificPurpose(toString(questionA245Answer));
                baseActivityData.setActivityCategory(ActivityCategoryCode.AC_11);
                baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_5);
                baseActivityData.setActivityType(ActivityTypeCode.AT_8);
                baseActivityData.setActivitySource(toActivitySourceCode(questionA289Answer));
                baseActivityData.setActivityOwnership(null);
                baseActivityData.setUnit(baseActivityDataUnit);
                baseActivityData.setValue(toDouble(questionA290Answer, baseActivityDataUnit));

                res.add(baseActivityData);
			}
		}
		return res;
	}
}
