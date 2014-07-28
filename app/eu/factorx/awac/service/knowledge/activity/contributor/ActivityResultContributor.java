package eu.factorx.awac.service.knowledge.activity.contributor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.factorx.awac.models.code.type.ActivitySourceCode;
import eu.factorx.awac.models.code.type.ActivitySubCategoryCode;
import eu.factorx.awac.models.code.type.ActivityTypeCode;
import eu.factorx.awac.models.code.type.QuestionCode;
import eu.factorx.awac.models.data.answer.QuestionAnswer;
import eu.factorx.awac.models.data.answer.QuestionSetAnswer;
import eu.factorx.awac.models.data.answer.type.BooleanAnswerValue;
import eu.factorx.awac.models.data.answer.type.CodeAnswerValue;
import eu.factorx.awac.models.data.answer.type.NumericAnswerValue;
import eu.factorx.awac.models.data.answer.type.StringAnswerValue;
import eu.factorx.awac.models.knowledge.Unit;
import eu.factorx.awac.models.reporting.BaseActivityData;
import eu.factorx.awac.service.CodeConversionService;
import eu.factorx.awac.service.UnitConversionService;
import eu.factorx.awac.service.UnitService;

@Component
public abstract class ActivityResultContributor {

	@Autowired
	private UnitService unitService;

	@Autowired
	private UnitConversionService unitConversionService;

	@Autowired
	private CodeConversionService codeConversionService;

	private Map<String, Unit> unitsBySymbol = null;;

	public ActivityResultContributor() {
		super();
	}

	public abstract List<BaseActivityData> getBaseActivityData(Map<QuestionCode, List<QuestionSetAnswer>> questionSetAnswers);

	protected Double toDouble(QuestionAnswer questionAnswer, Unit toUnit) {
		NumericAnswerValue numericAnswerValue = (NumericAnswerValue) questionAnswer.getAnswerValues().get(0);
		Unit answerUnit = numericAnswerValue.getUnit();
		Double answerValue = numericAnswerValue.doubleValue();
		return convertNumericValue(answerValue, answerUnit, toUnit);
	}

	protected String toString(QuestionAnswer questionAnswer) {
		StringAnswerValue answerValue = (StringAnswerValue) questionAnswer.getAnswerValues().get(0);
		return answerValue.getValue();
	}

	protected Boolean toBoolean(QuestionAnswer questionAnswer) {
		BooleanAnswerValue answerValue = (BooleanAnswerValue) questionAnswer.getAnswerValues().get(0);
		return answerValue.getValue();
	}

	protected ActivitySourceCode toActivitySourceCode(QuestionAnswer questionAnswer) {
		CodeAnswerValue answerValue = (CodeAnswerValue) questionAnswer.getAnswerValues().get(0);
		return codeConversionService.toActivitySourceCode(answerValue.getValue());
	}

	protected ActivityTypeCode toActivityTypeCode(QuestionAnswer questionAnswer) {
		CodeAnswerValue answerValue = (CodeAnswerValue) questionAnswer.getAnswerValues().get(0);
		return codeConversionService.toActivityTypeCode(answerValue.getValue());
	}

	protected ActivitySubCategoryCode toActivitySubCategoryCode(QuestionAnswer questionAnswer) {
		CodeAnswerValue answerValue = (CodeAnswerValue) questionAnswer.getAnswerValues().get(0);
		throw new RuntimeException("NOT YET IMPLEMENTED");
//		return codeConversionService.toActivitySubCategoryCode(answerValue.getValue());
	}

	protected Double convertNumericValue(Double value, Unit unitFrom, Unit toUnit) {
		return unitConversionService.convert(value, unitFrom, toUnit, null);
	}

	protected Map<QuestionCode, QuestionAnswer> byQuestionCode(List<QuestionAnswer> questionAnswers) {
		Map<QuestionCode, QuestionAnswer> res = new HashMap<>();
		for (QuestionAnswer questionAnswer : questionAnswers) {
			res.put(questionAnswer.getQuestion().getCode(), questionAnswer);
		}
		return res;
	}

	protected Unit getUnitBySymbol(String symbol) {
		if (unitsBySymbol == null) {
			findAllUnits();
		}
		return unitsBySymbol.get(symbol);
	}

	private void findAllUnits() {
		List<Unit> units = unitService.findAll();
		for (Unit unit : units) {
			unitsBySymbol.put(unit.getSymbol(), unit);
		}
	}
}
