
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
 * CHECKED BY FLO
 */
public class BaseActivityDataAE_BAD31E extends BaseActivityDataForProducts {

	public List<BaseActivityData> getBaseActivityData(QuestionSetAnswer questionSetAnswer, QuestionAnswer questionA245Answer) {
		
		List<BaseActivityData> res = new ArrayList<>();

		Unit baseActivityDataUnit = getUnitByCode(UnitCode.U5329);

		for (QuestionSetAnswer questionSetAnswersChild : questionSetAnswer.getChildren()) {
			if (questionSetAnswersChild.getQuestionSet().getCode().equals(QuestionCode.A253)) {


				Map<QuestionCode, QuestionAnswer> answersByCodeChild = byQuestionCode(questionSetAnswersChild.getQuestionAnswers());


				QuestionAnswer questionA254Answer = answersByCodeChild.get(QuestionCode.A254);
				QuestionAnswer questionA255Answer = answersByCodeChild.get(QuestionCode.A255);
				QuestionAnswer questionA260Answer = answersByCodeChild.get(QuestionCode.A260);

				if (questionA254Answer == null ||
						questionA255Answer == null ||
						questionA260Answer == null) {
					continue;
				}


				BaseActivityData baseActivityData = new BaseActivityData();

				baseActivityData.setKey(BaseActivityDataCode.AE_BAD31E);
				baseActivityData.setRank(2);
				baseActivityData.setSpecificPurpose(toString(questionA245Answer));
				baseActivityData.setActivityCategory(ActivityCategoryCode.AC_4);
				baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_12);
				baseActivityData.setActivityType(ActivityTypeCode.AT_26);
				baseActivityData.setActivitySource(ActivitySourceCode.AS_177);
				baseActivityData.setActivityOwnership(false);
			baseActivityData.setUnit(baseActivityDataUnit);
				baseActivityData.setValue(toDouble(questionA254Answer, baseActivityDataUnit) * toDouble(questionA255Answer, baseActivityDataUnit) * toDouble(questionA260Answer, baseActivityDataUnit));

				res.add(baseActivityData);
			}
		}
		return res;
	}

}
