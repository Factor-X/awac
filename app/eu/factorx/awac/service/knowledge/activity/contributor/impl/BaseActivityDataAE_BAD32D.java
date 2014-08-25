
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
public class BaseActivityDataAE_BAD32D extends BaseActivityDataForProducts {

	public List<BaseActivityData> getBaseActivityData(QuestionSetAnswer questionSetAnswer, QuestionAnswer questionA245Answer) {
		
		List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (tonne.km in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.tonne.km)
		Unit baseActivityDataUnit = getUnitByCode(UnitCode.U5329);

		for (QuestionSetAnswer questionSetAnswersChild : questionSetAnswer.getChildren()) {
			if (questionSetAnswersChild.getQuestionSet().getCode().equals(QuestionCode.A266)) {

				Map<QuestionCode, QuestionAnswer> answersByCode = byQuestionCode(questionSetAnswersChild.getQuestionAnswers());

				QuestionAnswer questionA268Answer = answersByCode.get(QuestionCode.A268);
				QuestionAnswer questionA267Answer = answersByCode.get(QuestionCode.A267);

				if (questionA268Answer == null || questionA267Answer == null) {
					continue;
				}

                Double belgianTruckRatio = 0.854;
                Double internationalTruckRatio = 0.287;
                Double internationalDistance = 0.0;

                if (getCode(questionA268Answer).getKey().equals("1")) { // Belgium
                    continue;
                } else if (getCode(questionA268Answer).getKey().equals("2")) { // Europe
                    internationalDistance = 2500.0;
                } else  { // World
                    internationalDistance = 5000.0;
                }

                BaseActivityData baseActivityData = new BaseActivityData();

				baseActivityData.setKey(BaseActivityDataCode.AE_BAD32D);
				baseActivityData.setRank(3);
				baseActivityData.setSpecificPurpose(toString(questionA245Answer));
				baseActivityData.setActivityCategory(ActivityCategoryCode.AC_4);
				baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_12);
				baseActivityData.setActivityType(ActivityTypeCode.AT_36);
				baseActivityData.setActivitySource(ActivitySourceCode.AS_178);
				baseActivityData.setActivityOwnership(false);
    			baseActivityData.setUnit(baseActivityDataUnit);
                baseActivityData.setValue(toDouble(questionA267Answer, getUnitByCode(UnitCode.U5135)) * internationalDistance);

				res.add(baseActivityData);
			}
		}
		return res;
	}
}
