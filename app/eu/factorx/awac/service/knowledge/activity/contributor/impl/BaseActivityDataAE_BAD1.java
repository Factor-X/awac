
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
public class BaseActivityDataAE_BAD1 extends ActivityResultContributor {

	@Override
	public List<BaseActivityData> getBaseActivityData(Map<QuestionCode, List<QuestionSetAnswer>> questionSetAnswers) {
		List<BaseActivityData> res = new ArrayList<>();

		//1. build unit(s) needed for
		//  - a) the BAD.unit
		//  - b) conversion

		// Get Target Unit (GJ in this case)
		// Allow finding unit by a UnitCode: getUnitByCode(UnitCode.GJ)
		Unit baseActivityDataUnit = getUnitByCode(UnitCode.U5321);


		//2. build BAD
		//2.1 loop for each answer for each loop for each parents of one or more question needed by the BAD


		// For each set of answers in A15, build an ActivityBaseData (see specifications)
		List<QuestionSetAnswer> questionSetAnswersA15 = questionSetAnswers.get(QuestionCode.A15);
		//2.2 control if the list if different than null
		if (questionSetAnswersA15 == null) {
			return res;
		}

		for (QuestionSetAnswer questionSetAnswer : questionSetAnswersA15) {

			//create a map for each repetition level
			Map<QuestionCode, QuestionAnswer> answersByCode = byQuestionCode(questionSetAnswer.getQuestionAnswers());

			//load question for this level
			QuestionAnswer questionA16Answer = answersByCode.get(QuestionCode.A16);
			QuestionAnswer questionA17Answer = answersByCode.get(QuestionCode.A17);

			//control them
			if (questionA16Answer == null ||
					questionA17Answer == null) {
				continue;
			}

			//build the bad
			BaseActivityData baseActivityData = new BaseActivityData();

			baseActivityData.setKey(BaseActivityDataCode.AE_BAD1);
			baseActivityData.setRank(1);
			baseActivityData.setSpecificPurpose(null);
			baseActivityData.setActivityCategory(ActivityCategoryCode.AC_1);
			baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_1);
			baseActivityData.setActivityType(ActivityTypeCode.AT_1);

			baseActivityData.setActivitySource(toActivitySourceCode(questionA16Answer));

			baseActivityData.setActivityOwnership(true);
			baseActivityData.setUnit(baseActivityDataUnit);
			baseActivityData.setValue(toDouble(questionA17Answer, baseActivityDataUnit));
			res.add(baseActivityData);
		}
		return res;
	}

}
