
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
public class BaseActivityDataAE_BAD18C extends ActivityResultContributor {

	@Override
	public List<BaseActivityData> getBaseActivityData(Map<QuestionCode, List<QuestionSetAnswer>> questionSetAnswers) {
				List<BaseActivityData> res = new ArrayList<>();

		// Get Target Unit (tonne.km in this case)
		// Allow finding unit by a UnitCode: getUnitByCode(UnitCode.tonne.km)
		Unit baseActivityDataUnit = getUnitByCode(UnitCode.U5329);

		// For each set of answers in A157, build an ActivityBaseData (see specifications)
		List<QuestionSetAnswer> questionSetAnswersA157 = questionSetAnswers.get(QuestionCode.A157);
		if (questionSetAnswersA157 == null) {
			return res;
		}

		for (QuestionSetAnswer questionSetAnswer : questionSetAnswersA157) {

			Map<QuestionCode, QuestionAnswer> answersByCode = byQuestionCode(questionSetAnswer.getQuestionAnswers());

			QuestionAnswer questionA158Answer = answersByCode.get(QuestionCode.A158);
			QuestionAnswer questionA159Answer = answersByCode.get(QuestionCode.A159);
			QuestionAnswer questionA160Answer = answersByCode.get(QuestionCode.A160);
			QuestionAnswer questionA161Answer = answersByCode.get(QuestionCode.A161);
			QuestionAnswer questionA162Answer = answersByCode.get(QuestionCode.A162);

            if (questionA158Answer == null ||
                    questionA159Answer == null) {
                continue;
            }

            if (!getCode(questionA159Answer).getKey().equals("1")) { // NOT Belgium
                continue;
            }

            Double belgianTruckRatio = 0.854;
			Double internationalTruckRatio = 0.287;
			Double belgianDistance = 200.0;

			BaseActivityData baseActivityData = new BaseActivityData();

			baseActivityData.setKey(BaseActivityDataCode.AE_BAD18C);
			baseActivityData.setRank(3);
			baseActivityData.setSpecificPurpose("");
			baseActivityData.setActivityCategory(ActivityCategoryCode.AC_4);
			baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_6);
			baseActivityData.setActivityType(ActivityTypeCode.AT_34);
			baseActivityData.setActivitySource(ActivitySourceCode.AS_178);
			baseActivityData.setActivityOwnership(false);
			baseActivityData.setUnit(baseActivityDataUnit);
			baseActivityData.setValue(toDouble(questionA158Answer, getUnitByCode(UnitCode.U5135)) * belgianDistance);

			res.add(baseActivityData);
		}
		return res;
	}

}
