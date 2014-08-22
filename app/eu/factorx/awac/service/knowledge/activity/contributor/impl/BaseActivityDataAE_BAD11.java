
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
public class BaseActivityDataAE_BAD11 extends ActivityResultContributor {

	@Override
	public List<BaseActivityData> getBaseActivityData(Map<QuestionCode, List<QuestionSetAnswer>> questionSetAnswers) {
				List<BaseActivityData> res = new ArrayList<>();

		// Get Target Unit (l in this case)
		// Allow finding unit by a UnitCode: getUnitByCode(UnitCode.l)
		Unit baseActivityDataUnit = getUnitByCode(UnitCode.U5126);
        // For each set of answers in A78, build an ActivityBaseData (see specifications)
		List<QuestionSetAnswer> questionSetAnswersA78 = questionSetAnswers.get(QuestionCode.A78);
		if (questionSetAnswersA78 == null) {
			return res;
		}

        for (QuestionSetAnswer questionSetAnswer : questionSetAnswersA78) {

			Map<QuestionCode, QuestionAnswer> answersByCode = byQuestionCode(questionSetAnswer.getQuestionAnswers());

			QuestionAnswer questionA79Answer = answersByCode.get(QuestionCode.A79);
			QuestionAnswer questionA80Answer = answersByCode.get(QuestionCode.A80);
			QuestionAnswer questionA81Answer = answersByCode.get(QuestionCode.A81);
			QuestionAnswer questionA83Answer = answersByCode.get(QuestionCode.A83);
			QuestionAnswer questionA88Answer = answersByCode.get(QuestionCode.A88);
			QuestionAnswer questionA89Answer = answersByCode.get(QuestionCode.A89);
			QuestionAnswer questionA90Answer = answersByCode.get(QuestionCode.A90);
			QuestionAnswer questionA91Answer = answersByCode.get(QuestionCode.A91);
			QuestionAnswer questionA92Answer = answersByCode.get(QuestionCode.A92);
            if (questionA79Answer == null ||
                    questionA80Answer == null ||
                    (toBoolean(questionA80Answer) == Boolean.FALSE
							&& questionA81Answer == null) ||
                    questionA83Answer == null ||
					questionA88Answer == null ||
					(questionA89Answer == null &&
							questionA90Answer == null &&
							questionA91Answer == null &&
							questionA92Answer == null)) {
				continue;
			}

            BaseActivityData baseActivityData = new BaseActivityData();

			baseActivityData.setKey(BaseActivityDataCode.AE_BAD11);
			baseActivityData.setRank(2);
			baseActivityData.setSpecificPurpose(toString(questionA79Answer));
			baseActivityData.setActivityCategory(ActivityCategoryCode.AC_5);
            if (toBoolean(questionA80Answer) == Boolean.FALSE) {
                baseActivityData.setActivitySubCategory(toActivitySubCategoryCode(questionA81Answer));
            } else {
                baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_7);
            }

            baseActivityData.setActivityType(ActivityTypeCode.AT_1);
            baseActivityData.setActivitySource(toActivitySourceCode(questionA83Answer));
            baseActivityData.setActivityOwnership(toBoolean(questionA80Answer));
            baseActivityData.setUnit(baseActivityDataUnit);

            if (questionA89Answer != null) {
    			baseActivityData.setValue(toDouble(questionA88Answer, getUnitByCode(UnitCode.U5170)) / (toDouble(questionA89Answer, getUnitByCode(UnitCode.U5170))));
            } else if (questionA90Answer != null) {
	    		baseActivityData.setValue(toDouble(questionA88Answer, getUnitByCode(UnitCode.U5170)) / (toDouble(questionA90Answer, getUnitByCode(UnitCode.U5170))));
            } else if (questionA91Answer != null) {
		    	baseActivityData.setValue(toDouble(questionA88Answer, getUnitByCode(UnitCode.U5170)) / (toDouble(questionA91Answer, getUnitByCode(UnitCode.U5170))));
            } else {
			    baseActivityData.setValue(toDouble(questionA88Answer, getUnitByCode(UnitCode.U5170)) / (toDouble(questionA92Answer, getUnitByCode(UnitCode.U5170))));
            }

			res.add(baseActivityData);
		}
		return res;
	}

}
