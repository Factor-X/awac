package eu.factorx.awac.service.impl;

import java.util.List;

import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Component;

import play.db.jpa.JPA;
import eu.factorx.awac.models.knowledge.Unit;
import eu.factorx.awac.models.knowledge.UnitCategory;
import eu.factorx.awac.models.knowledge.UnitConversionFormula;
import eu.factorx.awac.service.UnitConversionService;

@Component
public class UnitConversionServiceImpl extends AbstractJPAPersistenceServiceImpl<UnitConversionFormula> implements
		UnitConversionService {

	@Override
	public UnitConversionFormula findByUnitAndYear(Unit unit, Integer year) {
		List<UnitConversionFormula> resultList = JPA.em()
				.createNamedQuery(UnitConversionFormula.FIND_BY_UNIT_AND_YEAR, UnitConversionFormula.class)
				.setParameter("unit", unit).setParameter("year", year).getResultList();
		if (resultList.size() > 1) {
			String errorMsg = "More than one conversion formula for unit = '" + unit.getSymbol() + "' and year = "
					+ year;
			throw new RuntimeException(errorMsg);
		}
		if (resultList.size() == 0) {
			return null;
		}
		return resultList.get(0);
	}

	@Override
	public Double convert(Double value, Unit currentUnit, Unit targetUnit, Integer year) {
		if (currentUnit.equals(targetUnit)) {
			return value;
		}

		UnitCategory unitCategory = currentUnit.getCategory();
		UnitCategory targetUnitCategory = targetUnit.getCategory();

		if (!unitCategory.equals(targetUnitCategory)) {
			throw new RuntimeException("Cannot convert a unit of category '" + unitCategory.getSymbol() + "' ("
					+ currentUnit + ") to a unit of category '" + targetUnitCategory.getSymbol() + "' (" + targetUnit
					+ ")");
		}

		value = convertToReferenceUnit(value, currentUnit, year);
		value = convertFromReferenceUnit(value, targetUnit, year);

		return value;
	}

	@Override
	public Double convertToReferenceUnit(Double value, Unit currentUnit, Integer year) {
		UnitConversionFormula conversionFormula = findByUnitAndYear(currentUnit, year);
		if (conversionFormula == null) {
			throw new RuntimeException("Cannot find a conversion formula for unit = '" + currentUnit.getSymbol()
					+ "' and year = " + year);
		}
		String unitToReferenceFormula = conversionFormula.getUnitToReference();
		return evaluateFormula(unitToReferenceFormula, value);
	}

	@Override
	public Double convertFromReferenceUnit(Double value, Unit targetUnit, Integer year) {
		UnitConversionFormula conversionFormula = findByUnitAndYear(targetUnit, year);
		if (conversionFormula == null) {
			throw new RuntimeException("Cannot find a conversion formula for unit = '" + targetUnit.getSymbol()
					+ "' and year = " + year);
		}
		String referenceToUnitFormula = conversionFormula.getReferenceToUnit();
		return evaluateFormula(referenceToUnitFormula, value);
	}

	private Double evaluateFormula(String formula, Double value) {
		ExpressionParser parser = new SpelExpressionParser();
		formula = formula.replaceAll(",", ".");
		formula = formula.replaceAll(UnitConversionFormula.VARIABLE_NAME, String.valueOf(value));
		Expression expression = parser.parseExpression(formula);
		return expression.getValue(Double.class);
	}

}
