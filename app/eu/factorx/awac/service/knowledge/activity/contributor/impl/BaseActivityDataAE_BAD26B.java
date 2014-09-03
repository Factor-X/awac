
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
public class BaseActivityDataAE_BAD26B extends ActivityResultContributor {

	@Override
	public List<BaseActivityData> getBaseActivityData(Map<QuestionCode, List<QuestionSetAnswer>> questionSetAnswers) {
				List<BaseActivityData> res = new ArrayList<>();

		// Get Target Unit (t in this case)
		// Allow finding unit by a UnitCode: getUnitByCode(UnitCode.t)
		Unit baseActivityDataUnit = getUnitByCode(UnitCode.U5135);

		// For each set of answers in A201, build an ActivityBaseData (see specifications)
		List<QuestionSetAnswer> questionSetAnswersA201 = questionSetAnswers.get(QuestionCode.A201);
		if (questionSetAnswersA201 == null) {
			return res;
		}

		for (QuestionSetAnswer questionSetAnswer : questionSetAnswersA201) {

			Map<QuestionCode, QuestionAnswer> answersByCode = byQuestionCode(questionSetAnswer.getQuestionAnswers());

			QuestionAnswer questionA203Answer = answersByCode.get(QuestionCode.A203);
			QuestionAnswer questionA204Answer = answersByCode.get(QuestionCode.A204);
			// TODO: question qui a été renumérotée car il y avait un doublon!
			QuestionAnswer questionA501Answer = answersByCode.get(QuestionCode.A501);

			if (questionA203Answer == null ||
					questionA204Answer == null ||
					questionA501Answer == null) {
				continue;
			}


			BaseActivityData baseActivityData = new BaseActivityData();

			baseActivityData.setKey(BaseActivityDataCode.AE_BAD26B);
			baseActivityData.setRank(2);
			baseActivityData.setAlternativeGroup("ALT_E_6_EAU");
			baseActivityData.setSpecificPurpose(null);
			baseActivityData.setActivityCategory(ActivityCategoryCode.AC_7);
			baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_10);
            baseActivityData.setActivityType(toActivityTypeCode(questionA204Answer));
            baseActivityData.setActivitySource(ActivitySourceCode.AS_208);
            if (getCode(questionA501Answer).getKey().equals("1"))
                baseActivityData.setActivityOwnership(Boolean.TRUE);
            else
                baseActivityData.setActivityOwnership(Boolean.FALSE);
			baseActivityData.setUnit(baseActivityDataUnit);
			baseActivityData.setValue(toDouble(questionA203Answer, baseActivityDataUnit));

			res.add(baseActivityData);
		}
		return res;
	}

}
