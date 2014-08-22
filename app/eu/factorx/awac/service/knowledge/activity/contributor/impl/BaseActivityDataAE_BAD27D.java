
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
public class BaseActivityDataAE_BAD27D extends ActivityResultContributor {

	@Override
	public List<BaseActivityData> getBaseActivityData(Map<QuestionCode, List<QuestionSetAnswer>> questionSetAnswers) {
				List<BaseActivityData> res = new ArrayList<>();

		// Get Target Unit (t in this case)
		// Allow finding unit by a UnitCode: getUnitByCode(UnitCode.t)
		Unit baseActivityDataUnit = getUnitByCode(UnitCode.U5135);

		// For each set of answers in A209, build an ActivityBaseData (see specifications)
		List<QuestionSetAnswer> questionSetAnswersA209 = questionSetAnswers.get(QuestionCode.A209);
		if (questionSetAnswersA209 == null) {
			return res;
		}

		for (QuestionSetAnswer questionSetAnswer : questionSetAnswersA209) {

			Map<QuestionCode, QuestionAnswer> answersByCode = byQuestionCode(questionSetAnswer.getQuestionAnswers());

			QuestionAnswer questionA210Answer = answersByCode.get(QuestionCode.A210);
			QuestionAnswer questionA211Answer = answersByCode.get(QuestionCode.A211);
			QuestionAnswer questionA213Answer = answersByCode.get(QuestionCode.A213);
			QuestionAnswer questionA220Answer = answersByCode.get(QuestionCode.A220);
			QuestionAnswer questionA221Answer = answersByCode.get(QuestionCode.A221);

			if (questionA210Answer == null ||
					questionA211Answer == null ||
					questionA213Answer == null ||
					questionA220Answer == null ||
					questionA221Answer == null) {
				continue;
			}


			BaseActivityData baseActivityData = new BaseActivityData();

			baseActivityData.setKey(BaseActivityDataCode.AE_BAD27D);
			baseActivityData.setRank(1);
			baseActivityData.setSpecificPurpose(toString(questionA210Answer));
			baseActivityData.setActivityCategory(ActivityCategoryCode.AC_8);
			baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_10);
			baseActivityData.setActivityType(toActivityTypeCode(questionA211Answer));

            if (ActivitySourceCode.AS_222.equals(toActivitySourceCode(questionA213Answer))) {
                baseActivityData.setActivitySource(ActivitySourceCode.AS_229);
            } else             if (ActivitySourceCode.AS_223.equals(toActivitySourceCode(questionA213Answer))) {
                baseActivityData.setActivitySource(ActivitySourceCode.AS_230);
            } else            if (ActivitySourceCode.AS_224.equals(toActivitySourceCode(questionA213Answer))) {
                baseActivityData.setActivitySource(ActivitySourceCode.AS_231);
            } else            if (ActivitySourceCode.AS_225.equals(toActivitySourceCode(questionA213Answer))) {
                baseActivityData.setActivitySource(ActivitySourceCode.AS_232);
            } else            if (ActivitySourceCode.AS_226.equals(toActivitySourceCode(questionA213Answer))) {
                baseActivityData.setActivitySource(ActivitySourceCode.AS_233);
            } else            if (ActivitySourceCode.AS_227.equals(toActivitySourceCode(questionA213Answer))) {
                baseActivityData.setActivitySource(ActivitySourceCode.AS_234);
            } else            if (ActivitySourceCode.AS_228.equals(toActivitySourceCode(questionA213Answer))) {
                baseActivityData.setActivitySource(ActivitySourceCode.AS_235);
            }

			baseActivityData.setActivityOwnership(null);
			baseActivityData.setUnit(baseActivityDataUnit);
			baseActivityData.setValue(toDouble(questionA221Answer, baseActivityDataUnit) * toDouble(questionA220Answer));

			res.add(baseActivityData);
		}
		return res;
	}

}
