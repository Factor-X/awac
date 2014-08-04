
package eu.factorx.awac.service.knowledge.activity.contributor.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import eu.factorx.awac.models.code.type.ActivityCategoryCode;
import eu.factorx.awac.models.code.type.ActivitySubCategoryCode;
import eu.factorx.awac.models.code.type.ActivityTypeCode;
import eu.factorx.awac.models.code.type.BaseActivityDataCode;
import eu.factorx.awac.models.code.type.QuestionCode;
import eu.factorx.awac.models.data.answer.QuestionAnswer;
import eu.factorx.awac.models.data.answer.QuestionSetAnswer;
import eu.factorx.awac.models.knowledge.Unit;
import eu.factorx.awac.models.reporting.BaseActivityData;

/**
 * CHECKED BY FLO
 */
public class BaseActivityDataAE_BAD34A extends BaseActivityDataForProducts {

	public List<BaseActivityData> getBaseActivityData(QuestionSetAnswer questionSetAnswer, QuestionAnswer questionA245Answer) {
		
		List<BaseActivityData> res = new ArrayList<>();

		// Get Target Unit (GJ in this case)
		// Allow finding unit by a UnitCode: getUnitByCode(UnitCode.GJ)
		Unit baseActivityDataUnit = getUnitBySymbol("GJ");

		// For each set of answers in A273, build an ActivityBaseData (see specifications)
		for (QuestionSetAnswer questionSetAnswersChild : questionSetAnswer.getChildren()) {
			if (questionSetAnswersChild.getQuestionSet().getCode().equals(QuestionCode.A284)) {

				Map<QuestionCode, QuestionAnswer> answersByCode = byQuestionCode(questionSetAnswersChild.getQuestionAnswers());

				QuestionAnswer questionA285Answer = answersByCode.get(QuestionCode.A285);
				QuestionAnswer questionA286Answer = answersByCode.get(QuestionCode.A286);

				if (questionA285Answer == null ||
						questionA286Answer == null) {
					continue;
				}


				BaseActivityData baseActivityData = new BaseActivityData();

				baseActivityData.setKey(BaseActivityDataCode.AE_BAD34A);
				baseActivityData.setRank(1);
				baseActivityData.setSpecificPurpose(toString(questionA245Answer));
				baseActivityData.setActivityCategory(ActivityCategoryCode.AC_11);
				baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_1);
				baseActivityData.setActivityType(ActivityTypeCode.AT_1);
				baseActivityData.setActivitySource(toActivitySourceCode(questionA285Answer));
				baseActivityData.setActivityOwnership(null);
			baseActivityData.setUnit(baseActivityDataUnit);
				baseActivityData.setValue(toDouble(questionA286Answer, baseActivityDataUnit));

				res.add(baseActivityData);
			}
		}
		return res;
	}

}
