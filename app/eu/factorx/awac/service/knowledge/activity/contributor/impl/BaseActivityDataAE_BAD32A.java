
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
 * CHECK XM
 */
public class BaseActivityDataAE_BAD32A extends BaseActivityDataForProducts {

	public List<BaseActivityData> getBaseActivityData(QuestionSetAnswer questionSetAnswer, QuestionAnswer questionA245Answer) {
		
		List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (l in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.l)
		Unit baseActivityDataUnit = getUnitByCode(UnitCode.U5126);

        for (QuestionSetAnswer questionSetAnswersChild : questionSetAnswer.getChildren()) {
            if (questionSetAnswersChild.getQuestionSet().getCode().equals(QuestionCode.A250)) {

            	QuestionSetAnswer questionSetA252Answer = getChildQuestionSetAnswer(questionSetAnswersChild, QuestionCode.A252);
            	if (questionSetA252Answer == null) {
            		return res;
            	}

            	QuestionSetAnswer questionSetA266Answer = getChildQuestionSetAnswer(questionSetA252Answer, QuestionCode.A266);
            	if (questionSetA266Answer == null) {
            		return res;
            	}            	

                Map<QuestionCode, QuestionAnswer> answersByCodeChild = byQuestionCode(questionSetA266Answer.getQuestionAnswers());

				QuestionAnswer questionA268Answer = answersByCodeChild.get(QuestionCode.A268);
				QuestionAnswer questionA267Answer = answersByCodeChild.get(QuestionCode.A267);

				if (questionA268Answer == null || questionA267Answer == null) {
					continue;
				}

                if (!getCode(questionA268Answer).getKey().equals("1")) { // NOT Belgium
                    continue;
                }

                Double belgianTruckRatio = 0.854;
                Double internationalTruckRatio = 0.287;
                Double belgianDistance = 200.0;

				BaseActivityData baseActivityData = new BaseActivityData();

				baseActivityData.setKey(BaseActivityDataCode.AE_BAD31A);
				baseActivityData.setRank(2);
				baseActivityData.setSpecificPurpose(toString(questionA245Answer));
				baseActivityData.setActivityCategory(ActivityCategoryCode.AC_4);
				baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_12);
                baseActivityData.setActivityType(ActivityTypeCode.AT_1);
				baseActivityData.setActivitySource(ActivitySourceCode.AS_162);
				baseActivityData.setActivityOwnership(false);
    			baseActivityData.setUnit(baseActivityDataUnit);
                baseActivityData.setValue(belgianTruckRatio * belgianDistance * toDouble(questionA267Answer, getUnitByCode(UnitCode.U5135)) / 11.4 / 0.4426 * 24.98 / 100);

				res.add(baseActivityData);
			}
		}
		return res;
	}

}