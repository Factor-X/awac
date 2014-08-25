
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
 * CHECKED FJ
 */
public class BaseActivityDataAE_BAD27L extends ActivityResultContributor {

	@Override
	public List<BaseActivityData> getBaseActivityData(Map<QuestionCode, List<QuestionSetAnswer>> questionSetAnswers) {
				List<BaseActivityData> res = new ArrayList<>();

		// TODO In AWAC-enterprise-calcul_FE.xls, BAD27L is expressed in "euros", but there is no factors for this => must be replaced by "k€" 
		Unit baseActivityDataUnit = getUnitByCode(UnitCode.U5325); // k€

		// For each set of answers in A209, build an ActivityBaseData (see specifications)
		List<QuestionSetAnswer> questionSetAnswersA209 = questionSetAnswers.get(QuestionCode.A209);
		if (questionSetAnswersA209 == null) {
			return res;
		}

		for (QuestionSetAnswer questionSetAnswer : questionSetAnswersA209) {

			Map<QuestionCode, QuestionAnswer> answersByCode = byQuestionCode(questionSetAnswer.getQuestionAnswers());

			QuestionAnswer questionA219Answer = answersByCode.get(QuestionCode.A219);
			QuestionAnswer questionA222Answer = answersByCode.get(QuestionCode.A222);
			QuestionAnswer questionA210Answer = answersByCode.get(QuestionCode.A210);

			if (questionA219Answer == null ||
					questionA222Answer == null ||
					questionA210Answer == null) {
				continue;
			}


			BaseActivityData baseActivityData = new BaseActivityData();

			baseActivityData.setKey(BaseActivityDataCode.AE_BAD27L);
			baseActivityData.setRank(1);
			baseActivityData.setSpecificPurpose(toString(questionA210Answer));
			baseActivityData.setActivityCategory(ActivityCategoryCode.AC_8);
			baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_10);
			baseActivityData.setActivityType(ActivityTypeCode.AT_68);
			baseActivityData.setActivitySource(toActivitySourceCode(questionA219Answer));
			baseActivityData.setActivityOwnership(null);
			baseActivityData.setUnit(baseActivityDataUnit);
			baseActivityData.setValue(toDouble(questionA222Answer, baseActivityDataUnit));

			res.add(baseActivityData);
		}
		return res;
	}

}
