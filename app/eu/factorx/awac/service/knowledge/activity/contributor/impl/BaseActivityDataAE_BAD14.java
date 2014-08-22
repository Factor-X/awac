
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
public class BaseActivityDataAE_BAD14 extends ActivityResultContributor {

	@Override
	public List<BaseActivityData> getBaseActivityData(Map<QuestionCode, List<QuestionSetAnswer>> questionSetAnswers) {
				List<BaseActivityData> res = new ArrayList<>();

		// Get Target Unit (passagers.km in this case)
		Unit baseActivityDataUnit = getUnitByCode(UnitCode.U5328);

		// For each set of answers in A115, build an ActivityBaseData (see specifications)
		List<QuestionSetAnswer> questionSetAnswersA115 = questionSetAnswers.get(QuestionCode.A115);
		if (questionSetAnswersA115 == null) {
			return res;
		}

		for (QuestionSetAnswer questionSetAnswer : questionSetAnswersA115) {

			Map<QuestionCode, QuestionAnswer> answersByCode = byQuestionCode(questionSetAnswer.getQuestionAnswers());

			QuestionAnswer questionA117Answer = answersByCode.get(QuestionCode.A117);
			QuestionAnswer questionA119Answer = answersByCode.get(QuestionCode.A119);
			QuestionAnswer questionA120Answer = answersByCode.get(QuestionCode.A120);
			QuestionAnswer questionA118Answer = answersByCode.get(QuestionCode.A118);
			QuestionAnswer questionA116Answer = answersByCode.get(QuestionCode.A116);
            if (questionA117Answer == null ||
					questionA119Answer == null ||
					questionA120Answer == null ||
					questionA118Answer == null ||
					questionA116Answer == null) {
				continue;
			}

			BaseActivityData baseActivityData = new BaseActivityData();

			baseActivityData.setKey(BaseActivityDataCode.AE_BAD14);
			baseActivityData.setRank(1);
			baseActivityData.setSpecificPurpose(toString(questionA116Answer));
			baseActivityData.setActivityCategory(ActivityCategoryCode.AC_5);
			baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_7);
			baseActivityData.setActivityType(toActivityTypeCode(questionA118Answer));
			baseActivityData.setActivitySource(toActivitySourceCode(questionA117Answer));
			baseActivityData.setActivityOwnership(false);
			baseActivityData.setUnit(baseActivityDataUnit);
			baseActivityData.setValue(toDouble(questionA119Answer, getUnitByCode(UnitCode.U5332)) * toDouble(questionA120Answer, getUnitByCode(UnitCode.U5101)));

			res.add(baseActivityData);
		}
		return res;
	}

}
