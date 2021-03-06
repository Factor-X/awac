package eu.factorx.awac.service.impl;

import java.util.List;

import org.springframework.expression.ExpressionException;
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
		if (resultList.size() == 0) {
			return null;
		}
		if (resultList.size() > 1) {
			String errorMsg = "More than one conversion formula for unit = '" + unit.getSymbol() + "' and year = " + year;
			throw new RuntimeException(errorMsg);
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
			throw new RuntimeException("Cannot convert a unit of category '" + unitCategory.getName() + "' ("
					+ currentUnit + ") to a unit of category '" + targetUnitCategory.getName() + "' (" + targetUnit
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
			throw new RuntimeException("Cannot find a conversion formula for unit = '" + currentUnit
					+ "' and year = " + year);
		}
		String unitToReferenceFormula = conversionFormula.getUnitToReference();
		return evaluateExpression(unitToReferenceFormula, value);
	}

	@Override
	public Double convertFromReferenceUnit(Double value, Unit targetUnit, Integer year) {
		UnitConversionFormula conversionFormula = findByUnitAndYear(targetUnit, year);
		if (conversionFormula == null) {
			throw new RuntimeException("Cannot find a conversion formula for unit = '" + targetUnit
					+ "' and year = " + year);
		}
		String referenceToUnitFormula = conversionFormula.getReferenceToUnit();
		return evaluateExpression(referenceToUnitFormula, value);
	}

	public Double evaluateExpression(String strExpression, Double variableValue) throws ExpressionException {
		return MyrmexExpressionParser.toDouble(strExpression, variableValue);
	}

}
