
package eu.factorx.awac.service.knowledge.activity.contributor.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import eu.factorx.awac.models.code.type.QuestionCode;
import eu.factorx.awac.models.data.answer.QuestionAnswer;
import eu.factorx.awac.models.data.answer.QuestionSetAnswer;
import eu.factorx.awac.models.reporting.BaseActivityData;
import eu.factorx.awac.service.knowledge.activity.contributor.ActivityResultContributor;


public class BaseActivityDataForProducts extends ActivityResultContributor {

	@Autowired
	private BaseActivityDataAE_BAD31A baseActivityDataAE_BAD31A;
	@Autowired
	private BaseActivityDataAE_BAD31B baseActivityDataAE_BAD31B;
	@Autowired
	private BaseActivityDataAE_BAD31C baseActivityDataAE_BAD31C;
	@Autowired
	private BaseActivityDataAE_BAD31D baseActivityDataAE_BAD31D;
	@Autowired
	private BaseActivityDataAE_BAD31E baseActivityDataAE_BAD31E;
	@Autowired
	private BaseActivityDataAE_BAD31F baseActivityDataAE_BAD31F;
	@Autowired
	private BaseActivityDataAE_BAD31G baseActivityDataAE_BAD31G;
	@Autowired
	private BaseActivityDataAE_BAD31H baseActivityDataAE_BAD31H;
	@Autowired
	private BaseActivityDataAE_BAD31I baseActivityDataAE_BAD31I;
	
	@Autowired
	private BaseActivityDataAE_BAD32A baseActivityDataAE_BAD32A;
	@Autowired
	private BaseActivityDataAE_BAD32B baseActivityDataAE_BAD32B;
	@Autowired
	private BaseActivityDataAE_BAD32C baseActivityDataAE_BAD32C;
	@Autowired
	private BaseActivityDataAE_BAD32D baseActivityDataAE_BAD32D;

	@Autowired
	private BaseActivityDataAE_BAD33A baseActivityDataAE_BAD33A;
	@Autowired
	private BaseActivityDataAE_BAD33B baseActivityDataAE_BAD33B;
	@Autowired
	private BaseActivityDataAE_BAD33C baseActivityDataAE_BAD33C;

	@Autowired
	private BaseActivityDataAE_BAD34A baseActivityDataAE_BAD34A;
	@Autowired
	private BaseActivityDataAE_BAD34B baseActivityDataAE_BAD34B;
	@Autowired
	private BaseActivityDataAE_BAD34C baseActivityDataAE_BAD34C;

	@Autowired
	private BaseActivityDataAE_BAD35A baseActivityDataAE_BAD35A;
	@Autowired
	private BaseActivityDataAE_BAD35B baseActivityDataAE_BAD35B;
	@Autowired
	private BaseActivityDataAE_BAD35C baseActivityDataAE_BAD35C;
	@Autowired
	private BaseActivityDataAE_BAD35D baseActivityDataAE_BAD35D;

	@Autowired
	private BaseActivityDataAE_BAD36 baseActivityDataAE_BAD36;

	
	@Override
	public List<BaseActivityData> getBaseActivityData(Map<QuestionCode, List<QuestionSetAnswer>> questionSetAnswers) {
				List<BaseActivityData> res = new ArrayList<>();

		// Get Target Unit (tonne.km in this case)
		// Allow finding unit by a UnitCode: getUnitByCode(UnitCode.tonne.km)

		// For each set of answers in A238, build an ActivityBaseData (see specifications)
		for (QuestionSetAnswer questionSetAnswer : questionSetAnswers.get(QuestionCode.A238)) {

			Map<QuestionCode, QuestionAnswer> answersByCode = byQuestionCode(questionSetAnswer.getQuestionAnswers());

			QuestionAnswer questionA245Answer = answersByCode.get(QuestionCode.A245);
			QuestionAnswer questionA246Answer = answersByCode.get(QuestionCode.A246);

			if (questionA245Answer == null || questionA246Answer == null) {
				continue;
			}

			//resolve BAD31
			res.addAll(baseActivityDataAE_BAD31A.getBaseActivityData(questionSetAnswer, questionA245Answer));
			res.addAll(baseActivityDataAE_BAD31B.getBaseActivityData(questionSetAnswer, questionA245Answer));
			res.addAll(baseActivityDataAE_BAD31C.getBaseActivityData(questionSetAnswer, questionA245Answer));
			res.addAll(baseActivityDataAE_BAD31D.getBaseActivityData(questionSetAnswer, questionA245Answer));
			res.addAll(baseActivityDataAE_BAD31E.getBaseActivityData(questionSetAnswer, questionA245Answer));
			res.addAll(baseActivityDataAE_BAD31F.getBaseActivityData(questionSetAnswer, questionA245Answer));
			res.addAll(baseActivityDataAE_BAD31G.getBaseActivityData(questionSetAnswer, questionA245Answer));
			res.addAll(baseActivityDataAE_BAD31H.getBaseActivityData(questionSetAnswer, questionA245Answer));
			res.addAll(baseActivityDataAE_BAD31I.getBaseActivityData(questionSetAnswer, questionA245Answer));

			//resolve BAD32
			res.addAll(baseActivityDataAE_BAD32A.getBaseActivityData(questionSetAnswer, questionA245Answer));
			res.addAll(baseActivityDataAE_BAD32B.getBaseActivityData(questionSetAnswer, questionA245Answer));
			res.addAll(baseActivityDataAE_BAD32C.getBaseActivityData(questionSetAnswer, questionA245Answer));
			res.addAll(baseActivityDataAE_BAD32D.getBaseActivityData(questionSetAnswer, questionA245Answer));


			//resolve BAD33
			res.addAll(baseActivityDataAE_BAD33A.getBaseActivityData(questionSetAnswer, questionA245Answer));
			res.addAll(baseActivityDataAE_BAD33B.getBaseActivityData(questionSetAnswer, questionA245Answer));
			res.addAll(baseActivityDataAE_BAD33C.getBaseActivityData(questionSetAnswer, questionA245Answer));


			//resolve BAD34
			res.addAll(baseActivityDataAE_BAD34A.getBaseActivityData(questionSetAnswer, questionA245Answer));
			res.addAll(baseActivityDataAE_BAD34B.getBaseActivityData(questionSetAnswer, questionA245Answer));
			res.addAll(baseActivityDataAE_BAD34C.getBaseActivityData(questionSetAnswer, questionA245Answer));

			//resolve BAD35
			res.addAll(baseActivityDataAE_BAD35A.getBaseActivityData(questionSetAnswer, questionA245Answer, questionA246Answer));
			res.addAll(baseActivityDataAE_BAD35B.getBaseActivityData(questionSetAnswer, questionA245Answer, questionA246Answer));
			res.addAll(baseActivityDataAE_BAD35C.getBaseActivityData(questionSetAnswer, questionA245Answer, questionA246Answer));
			res.addAll(baseActivityDataAE_BAD35D.getBaseActivityData(questionSetAnswer, questionA245Answer, questionA246Answer));

			//resolve BAD36
			res.addAll(baseActivityDataAE_BAD36.getBaseActivityData(questionSetAnswer, questionA245Answer));
		}
		return res;
	}

}
