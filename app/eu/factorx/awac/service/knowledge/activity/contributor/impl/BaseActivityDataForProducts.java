
package eu.factorx.awac.service.knowledge.activity.contributor.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import eu.factorx.awac.models.code.type.QuestionCode;
import eu.factorx.awac.models.data.answer.QuestionAnswer;
import eu.factorx.awac.models.data.answer.QuestionSetAnswer;
import eu.factorx.awac.models.reporting.BaseActivityData;
import eu.factorx.awac.service.knowledge.activity.contributor.ActivityResultContributor;


public class BaseActivityDataForProducts extends ActivityResultContributor {

	private BaseActivityDataAE_BAD31A baseActivityDataAE_BAD31A;
	private BaseActivityDataAE_BAD31B baseActivityDataAE_BAD31B;
	private BaseActivityDataAE_BAD31C baseActivityDataAE_BAD31C;
	private BaseActivityDataAE_BAD31D baseActivityDataAE_BAD31D;
	private BaseActivityDataAE_BAD31E baseActivityDataAE_BAD31E;
	private BaseActivityDataAE_BAD31F baseActivityDataAE_BAD31F;
	private BaseActivityDataAE_BAD31G baseActivityDataAE_BAD31G;
	private BaseActivityDataAE_BAD31H baseActivityDataAE_BAD31H;
	private BaseActivityDataAE_BAD31I baseActivityDataAE_BAD31I;
	
	private BaseActivityDataAE_BAD32A baseActivityDataAE_BAD32A;
	private BaseActivityDataAE_BAD32B baseActivityDataAE_BAD32B;
	private BaseActivityDataAE_BAD32C baseActivityDataAE_BAD32C;
	private BaseActivityDataAE_BAD32D baseActivityDataAE_BAD32D;

	private BaseActivityDataAE_BAD33A baseActivityDataAE_BAD33A;
	private BaseActivityDataAE_BAD33B baseActivityDataAE_BAD33B;
	private BaseActivityDataAE_BAD33C baseActivityDataAE_BAD33C;

	private BaseActivityDataAE_BAD34A baseActivityDataAE_BAD34A;
	private BaseActivityDataAE_BAD34B baseActivityDataAE_BAD34B;
	private BaseActivityDataAE_BAD34C baseActivityDataAE_BAD34C;

	private BaseActivityDataAE_BAD35A baseActivityDataAE_BAD35A;
	private BaseActivityDataAE_BAD35B baseActivityDataAE_BAD35B;
	private BaseActivityDataAE_BAD35C baseActivityDataAE_BAD35C;
	private BaseActivityDataAE_BAD35D baseActivityDataAE_BAD35D;

	private BaseActivityDataAE_BAD36 baseActivityDataAE_BAD36;

	
	@Override
	public List<BaseActivityData> getBaseActivityData(Map<QuestionCode, List<QuestionSetAnswer>> questionSetAnswers) {
				List<BaseActivityData> res = new ArrayList<>();

		// Get Target Unit (tonne.km in this case)
		// Allow finding unit by a UnitCode: getUnitByCode(UnitCode.tonne.km)

		// For each set of answers in A238, build an ActivityBaseData (see specifications)
		List<QuestionSetAnswer> questionSetAnswersA238 = questionSetAnswers.get(QuestionCode.A238);		if (questionSetAnswersA238 == null) {			return res;		}		for (QuestionSetAnswer questionSetAnswer : questionSetAnswersA238) {

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

	public void setBaseActivityDataAE_BAD31A(BaseActivityDataAE_BAD31A baseActivityDataAE_BAD31A) {
		this.baseActivityDataAE_BAD31A = baseActivityDataAE_BAD31A;
	}

	public void setBaseActivityDataAE_BAD31B(BaseActivityDataAE_BAD31B baseActivityDataAE_BAD31B) {
		this.baseActivityDataAE_BAD31B = baseActivityDataAE_BAD31B;
	}

	public void setBaseActivityDataAE_BAD31C(BaseActivityDataAE_BAD31C baseActivityDataAE_BAD31C) {
		this.baseActivityDataAE_BAD31C = baseActivityDataAE_BAD31C;
	}

	public void setBaseActivityDataAE_BAD31D(BaseActivityDataAE_BAD31D baseActivityDataAE_BAD31D) {
		this.baseActivityDataAE_BAD31D = baseActivityDataAE_BAD31D;
	}

	public void setBaseActivityDataAE_BAD31E(BaseActivityDataAE_BAD31E baseActivityDataAE_BAD31E) {
		this.baseActivityDataAE_BAD31E = baseActivityDataAE_BAD31E;
	}

	public void setBaseActivityDataAE_BAD31F(BaseActivityDataAE_BAD31F baseActivityDataAE_BAD31F) {
		this.baseActivityDataAE_BAD31F = baseActivityDataAE_BAD31F;
	}

	public void setBaseActivityDataAE_BAD31G(BaseActivityDataAE_BAD31G baseActivityDataAE_BAD31G) {
		this.baseActivityDataAE_BAD31G = baseActivityDataAE_BAD31G;
	}

	public void setBaseActivityDataAE_BAD31H(BaseActivityDataAE_BAD31H baseActivityDataAE_BAD31H) {
		this.baseActivityDataAE_BAD31H = baseActivityDataAE_BAD31H;
	}

	public void setBaseActivityDataAE_BAD31I(BaseActivityDataAE_BAD31I baseActivityDataAE_BAD31I) {
		this.baseActivityDataAE_BAD31I = baseActivityDataAE_BAD31I;
	}

	public void setBaseActivityDataAE_BAD32A(BaseActivityDataAE_BAD32A baseActivityDataAE_BAD32A) {
		this.baseActivityDataAE_BAD32A = baseActivityDataAE_BAD32A;
	}

	public void setBaseActivityDataAE_BAD32B(BaseActivityDataAE_BAD32B baseActivityDataAE_BAD32B) {
		this.baseActivityDataAE_BAD32B = baseActivityDataAE_BAD32B;
	}

	public void setBaseActivityDataAE_BAD32C(BaseActivityDataAE_BAD32C baseActivityDataAE_BAD32C) {
		this.baseActivityDataAE_BAD32C = baseActivityDataAE_BAD32C;
	}

	public void setBaseActivityDataAE_BAD32D(BaseActivityDataAE_BAD32D baseActivityDataAE_BAD32D) {
		this.baseActivityDataAE_BAD32D = baseActivityDataAE_BAD32D;
	}

	public void setBaseActivityDataAE_BAD33A(BaseActivityDataAE_BAD33A baseActivityDataAE_BAD33A) {
		this.baseActivityDataAE_BAD33A = baseActivityDataAE_BAD33A;
	}

	public void setBaseActivityDataAE_BAD33B(BaseActivityDataAE_BAD33B baseActivityDataAE_BAD33B) {
		this.baseActivityDataAE_BAD33B = baseActivityDataAE_BAD33B;
	}

	public void setBaseActivityDataAE_BAD33C(BaseActivityDataAE_BAD33C baseActivityDataAE_BAD33C) {
		this.baseActivityDataAE_BAD33C = baseActivityDataAE_BAD33C;
	}

	public void setBaseActivityDataAE_BAD34A(BaseActivityDataAE_BAD34A baseActivityDataAE_BAD34A) {
		this.baseActivityDataAE_BAD34A = baseActivityDataAE_BAD34A;
	}

	public void setBaseActivityDataAE_BAD34B(BaseActivityDataAE_BAD34B baseActivityDataAE_BAD34B) {
		this.baseActivityDataAE_BAD34B = baseActivityDataAE_BAD34B;
	}

	public void setBaseActivityDataAE_BAD34C(BaseActivityDataAE_BAD34C baseActivityDataAE_BAD34C) {
		this.baseActivityDataAE_BAD34C = baseActivityDataAE_BAD34C;
	}

	public void setBaseActivityDataAE_BAD35A(BaseActivityDataAE_BAD35A baseActivityDataAE_BAD35A) {
		this.baseActivityDataAE_BAD35A = baseActivityDataAE_BAD35A;
	}

	public void setBaseActivityDataAE_BAD35B(BaseActivityDataAE_BAD35B baseActivityDataAE_BAD35B) {
		this.baseActivityDataAE_BAD35B = baseActivityDataAE_BAD35B;
	}

	public void setBaseActivityDataAE_BAD35C(BaseActivityDataAE_BAD35C baseActivityDataAE_BAD35C) {
		this.baseActivityDataAE_BAD35C = baseActivityDataAE_BAD35C;
	}

	public void setBaseActivityDataAE_BAD35D(BaseActivityDataAE_BAD35D baseActivityDataAE_BAD35D) {
		this.baseActivityDataAE_BAD35D = baseActivityDataAE_BAD35D;
	}

	public void setBaseActivityDataAE_BAD36(BaseActivityDataAE_BAD36 baseActivityDataAE_BAD36) {
		this.baseActivityDataAE_BAD36 = baseActivityDataAE_BAD36;
	}

}
