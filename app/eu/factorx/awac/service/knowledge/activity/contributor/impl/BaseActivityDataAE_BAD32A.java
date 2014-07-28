
package eu.factorx.awac.service.knowledge.activity.contributor.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import eu.factorx.awac.models.code.type.ActivityCategoryCode;
import eu.factorx.awac.models.code.type.ActivitySourceCode;
import eu.factorx.awac.models.code.type.ActivitySubCategoryCode;
import eu.factorx.awac.models.code.type.BaseActivityDataCode;
import eu.factorx.awac.models.code.type.QuestionCode;
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

		Unit baseActivityDataUnit = unitService.findBySymbol("l");

		for (QuestionSetAnswer questionSetAnswersChild : questionSetAnswer.getChildren()) {
			if (questionSetAnswersChild.getQuestionSet().getCode().equals(QuestionCode.A253)) {

				Map<QuestionCode, QuestionAnswer> answersByCode = byQuestionCode(questionSetAnswersChild.getQuestionAnswers());


				QuestionAnswer questionA268Answer = answersByCode.get(QuestionCode.A268);
				QuestionAnswer questionA267Answer = answersByCode.get(QuestionCode.A267);

				if (questionA268Answer == null || questionA267Answer == null) {
					continue;
				}

				Double belgianTruckRatio = 0.854;
				Double internationalTruckRatio = 0.287;
				Double belgianDistance = 0.0;
				Double internationalDistance = 0.0;
				// TODO: codes
            /*    if (getCode(questionA268Answer, CODE) == CODE("Belgique")) {
                    belgianDistance = 200.0;
                    internationalDistance = 0.0;
                } else if (getCode(questionA268Answer, CODE) == CODE("Europe")) {
                    belgianDistance = 0.0;
                    internationalDistance = 2500.0;
                } else {
                    belgianDistance = 0.0;
                    internationalDistance = 5000.0;
                }
*/
				BaseActivityData baseActivityData = new BaseActivityData();

				baseActivityData.setKey(BaseActivityDataCode.AE_BAD31A);
				baseActivityData.setRank(2);
				baseActivityData.setSpecificPurpose(toString(questionA245Answer));
				baseActivityData.setActivityCategory(ActivityCategoryCode.AC_4);
				baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_12);
				// TODO: FLORIAN
				// baseActivityData.setActivityType(ActivityTypeCode.CAMION_TRANSPORTEUR_EXT);
				baseActivityData.setActivitySource(ActivitySourceCode.AS_179);
				baseActivityData.setActivityOwnership(false);
			baseActivityData.setUnit(baseActivityDataUnit);
				baseActivityData.setValue(belgianTruckRatio *
						belgianDistance *
						toDouble(questionA267Answer, baseActivityDataUnit) / 11.4 / 0.4426 *
						24.98 / 100);

				res.add(baseActivityData);
			}
		}
		return res;
	}

}
