
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
public class BaseActivityDataAE_BAD33C extends BaseActivityDataForProducts {

	public List<BaseActivityData> getBaseActivityData(QuestionSetAnswer questionSetAnswer, QuestionAnswer questionA245Answer) {
		
		List<BaseActivityData> res = new ArrayList<>();

		// Get Target Unit (t in this case)
		// Allow finding unit by a UnitCode: getUnitByCode(UnitCode.t)
		Unit baseActivityDataUnit = getUnitByCode(UnitCode.U5135);

		// For each set of answers in A273, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswersChild : questionSetAnswer.getChildren()) {
            if (questionSetAnswersChild.getQuestionSet().getCode().equals(QuestionCode.A272)) {

                QuestionSetAnswer questionSetA273Answer = getFirstChildQuestionSetAnswer(questionSetAnswersChild, QuestionCode.A273);
                if (questionSetA273Answer == null) {
                    return res;
                }

                Map<QuestionCode, QuestionAnswer> answersByCode = byQuestionCode(questionSetA273Answer.getQuestionAnswers());

                QuestionAnswer questionA274Answer = answersByCode.get(QuestionCode.A274);

                if (questionA274Answer == null) {
                    continue;
                }
                for (QuestionSetAnswer questionSetAnswersChild273 : questionSetA273Answer.getChildren()) {
                    if (questionSetAnswersChild273.getQuestionSet().getCode().equals(QuestionCode.A279)) {
                        Map<QuestionCode, QuestionAnswer> answersByCodeChild = byQuestionCode(questionSetAnswersChild273.getQuestionAnswers());

						QuestionAnswer questionA280Answer = answersByCodeChild.get(QuestionCode.A280);
						QuestionAnswer questionA281Answer = answersByCodeChild.get(QuestionCode.A281);

						if (questionA280Answer == null || questionA281Answer == null) {
							continue;
						}
                        BaseActivityData baseActivityData = new BaseActivityData();

						baseActivityData.setKey(BaseActivityDataCode.AE_BAD33C);
						baseActivityData.setRank(1);
						baseActivityData.setSpecificPurpose(toString(questionA245Answer) + "-" + toString(questionA274Answer));
						baseActivityData.setActivityCategory(ActivityCategoryCode.AC_10);
						baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_5);
						baseActivityData.setActivityType(ActivityTypeCode.AT_8);
						baseActivityData.setActivitySource(toActivitySourceCode(questionA280Answer));
						baseActivityData.setActivityOwnership(null);
	            		baseActivityData.setUnit(baseActivityDataUnit);
						baseActivityData.setValue(toDouble(questionA281Answer, baseActivityDataUnit));

						res.add(baseActivityData);
					}
				}
			}
		}
		return res;
	}

}
