
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
public class BaseActivityDataAE_BAD21 extends ActivityResultContributor {

	@Override
	public List<BaseActivityData> getBaseActivityData(Map<QuestionCode, List<QuestionSetAnswer>> questionSetAnswers) {
				List<BaseActivityData> res = new ArrayList<>();

		// Get Target Unit (équivalent.habitant in this case)
		// Allow finding unit by a UnitCode: getUnitByCode(UnitCode.équivalent.habitant)
		Unit baseActivityDataUnit = getUnitByCode(UnitCode.U5330);

		// For each set of answers in A182, build an ActivityBaseData (see specifications)
		List<QuestionSetAnswer> questionSetAnswersA182 = questionSetAnswers.get(QuestionCode.A182);
		if (questionSetAnswersA182 == null) {
			return res;
		}

		for (QuestionSetAnswer questionSetAnswer : questionSetAnswersA182) {

			Map<QuestionCode, QuestionAnswer> answersByCode = byQuestionCode(questionSetAnswer.getQuestionAnswers());

			QuestionAnswer questionA183Answer = answersByCode.get(QuestionCode.A183);
			QuestionAnswer questionA184Answer = answersByCode.get(QuestionCode.A184);

			if (questionA183Answer == null ||
					questionA184Answer == null) {
				continue;
			}


			BaseActivityData baseActivityData = new BaseActivityData();

			baseActivityData.setKey(BaseActivityDataCode.AE_BAD21);
			baseActivityData.setRank(2);
			baseActivityData.setSpecificPurpose("-atelier");
			baseActivityData.setActivityCategory(ActivityCategoryCode.AC_7);
			baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_10);
			baseActivityData.setActivityType(ActivityTypeCode.AT_55);
			baseActivityData.setActivitySource(ActivitySourceCode.AS_191);
			baseActivityData.setActivityOwnership(null);
			baseActivityData.setUnit(baseActivityDataUnit);
			baseActivityData.setValue(toDouble(questionA183Answer) / 2 * toDouble(questionA184Answer) / 365);

			res.add(baseActivityData);
		}
		return res;
	}

}
