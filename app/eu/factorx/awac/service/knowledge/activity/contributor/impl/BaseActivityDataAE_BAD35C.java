
package eu.factorx.awac.service.knowledge.activity.contributor.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import eu.factorx.awac.models.code.type.ActivityCategoryCode;
import eu.factorx.awac.models.code.type.ActivitySourceCode;
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
public class BaseActivityDataAE_BAD35C extends BaseActivityDataForProducts {

	public List<BaseActivityData> getBaseActivityData(QuestionSetAnswer questionSetAnswer, QuestionAnswer questionA245Answer, QuestionAnswer questionA246Answer) {
				List<BaseActivityData> res = new ArrayList<>();

		// Get Target Unit (kWh in this case)
		// Allow finding unit by a UnitCode: getUnitByCode(UnitCode.kWh)
		Unit baseActivityDataUnit = getUnitBySymbol("kWh");

		// For each set of answers in A291, build an ActivityBaseData (see specifications)
		for (QuestionSetAnswer questionSetAnswersChild : questionSetAnswer.getChildren()) {
			if (questionSetAnswersChild.getQuestionSet().getCode().equals(QuestionCode.A291)) {

				Map<QuestionCode, QuestionAnswer> answersByCode = byQuestionCode(questionSetAnswersChild.getQuestionAnswers());

				QuestionAnswer questionA294Answer = answersByCode.get(QuestionCode.A294);
				QuestionAnswer questionA296Answer = answersByCode.get(QuestionCode.A296);
				QuestionAnswer questionA293Answer = answersByCode.get(QuestionCode.A293);

				if (questionA294Answer == null ||
						questionA296Answer == null ||
						questionA293Answer == null) {
					continue;
				}

				BaseActivityData baseActivityData = new BaseActivityData();

				baseActivityData.setKey(BaseActivityDataCode.AE_BAD35C);
				baseActivityData.setRank(1);
				baseActivityData.setSpecificPurpose(toString(questionA245Answer));
				baseActivityData.setActivityCategory(ActivityCategoryCode.AC_12);
				baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_2);
				baseActivityData.setActivityType(ActivityTypeCode.AT_3);
				baseActivityData.setActivitySource(ActivitySourceCode.AS_164);
				baseActivityData.setActivityOwnership(null);
			baseActivityData.setUnit(baseActivityDataUnit);
				baseActivityData.setValue(toDouble(questionA246Answer, baseActivityDataUnit) * toDouble(questionA296Answer, baseActivityDataUnit) * toDouble(questionA293Answer, baseActivityDataUnit));

				res.add(baseActivityData);
			}
		}
		return res;
	}

}
