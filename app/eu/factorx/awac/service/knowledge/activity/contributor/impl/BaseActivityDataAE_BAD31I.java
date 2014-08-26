
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
public class BaseActivityDataAE_BAD31I extends BaseActivityDataForProducts {

	public List<BaseActivityData> getBaseActivityData(QuestionSetAnswer questionSetAnswer, QuestionAnswer questionA245Answer) {
		
		List<BaseActivityData> res = new ArrayList<>();

		Unit baseActivityDataUnit = getUnitByCode(UnitCode.U5329);

		for (QuestionSetAnswer questionSetAnswersChild : questionSetAnswer.getChildren()) {
			if (questionSetAnswersChild.getQuestionSet().getCode().equals(QuestionCode.A253)) {

				Map<QuestionCode, QuestionAnswer> answersByCode = byQuestionCode(questionSetAnswersChild.getQuestionAnswers());


				QuestionAnswer questionA254Answer = answersByCode.get(QuestionCode.A254);
				QuestionAnswer questionA255Answer = answersByCode.get(QuestionCode.A255);
				QuestionAnswer questionA264Answer = answersByCode.get(QuestionCode.A264);

				if (questionA254Answer == null ||
						questionA255Answer == null ||
						questionA264Answer == null) {
					continue;
				}


				BaseActivityData baseActivityData = new BaseActivityData();

				baseActivityData.setKey(BaseActivityDataCode.AE_BAD31I);
				baseActivityData.setRank(2);
				baseActivityData.setSpecificPurpose(toString(questionA245Answer));
				baseActivityData.setActivityCategory(ActivityCategoryCode.AC_4);
				baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_12);
				baseActivityData.setActivityType(ActivityTypeCode.AT_30);
				baseActivityData.setActivitySource(ActivitySourceCode.AS_177);
				baseActivityData.setActivityOwnership(false);
	    		baseActivityData.setUnit(baseActivityDataUnit);
                baseActivityData.setValue(toDouble(questionA254Answer, getUnitByCode(UnitCode.U5135)) * toDouble(questionA255Answer, getUnitByCode(UnitCode.U5106)) * toDouble(questionA264Answer));

				res.add(baseActivityData);
			}
		}
		return res;
	}

}
