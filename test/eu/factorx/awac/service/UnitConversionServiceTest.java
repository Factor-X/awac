package eu.factorx.awac.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import play.Logger;
import eu.factorx.awac.models.AbstractBaseModelTest;
import eu.factorx.awac.models.knowledge.Unit;
import eu.factorx.awac.models.knowledge.UnitConversionFormula;

@ContextConfiguration(locations = { "classpath:/components-test.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UnitConversionServiceTest extends AbstractBaseModelTest {

	@Autowired
	private UnitService unitService;

	@Autowired
	private UnitConversionService unitConversionService;

	/**
	 * Check existing data - Test expressions syntax (throwing an ExpressionException when parsing or evaluating them)
	 */
	@Test
	public void _001_testExpressionSyntax() {
		List<String> invalidFormulas = new ArrayList<>();
		Double aDouble = 4542348645581.25;
		for (UnitConversionFormula unitConversionFormula : unitConversionService.findAll()) {
			try {
				unitConversionService.evaluateExpression(unitConversionFormula.getReferenceToUnit(), aDouble);
				unitConversionService.evaluateExpression(unitConversionFormula.getUnitToReference(), aDouble);
			} catch (ExpressionException e) {
				invalidFormulas.add("\t" + unitConversionFormula + "\n\t  --> " + e.getLocalizedMessage());
			}
		}
		if (!invalidFormulas.isEmpty()) {
			Logger.error("Found " + invalidFormulas.size() + " invalid unit conversion formula(s)!\n" + StringUtils.join(invalidFormulas, '\n'));
		}
	}

	/**
	 * Check existing data - Test data 'consistency' (ie check if conversion expressions are symmetric)
	 */
	@Test
	public void _002_testDataConsistency() {
		List<String> invalidFormulas = new ArrayList<>();
		Double initialValue = 100d;
		for (UnitConversionFormula unitConversionFormula : unitConversionService.findAll()) {
			Unit unit = unitConversionFormula.getUnit();
			Unit refUnit = unit.getCategory().getMainUnit();
			try {
				Double convertedValue = unitConversionService.convertToReferenceUnit(initialValue, unit, unitConversionFormula.getYear());
				Double endValue = unitConversionService.convertFromReferenceUnit(convertedValue, unit, unitConversionFormula.getYear());
				if (Math.abs(endValue - initialValue) > 1) {
					invalidFormulas.add("\t" + unitConversionFormula + "\n\t  --> "
							+ initialValue + " " + unit.getSymbol() + " => " + convertedValue + " " + refUnit.getSymbol() + " => " + Math.round(endValue) + " " + unit.getSymbol());
				}
			} catch (ExpressionException e) {
			}
		}
		if (!invalidFormulas.isEmpty()) {
			Logger.error("Found " + invalidFormulas.size() + " inconsistent conversion formula(s)!\n" + StringUtils.join(invalidFormulas, '\n'));
		}
	}

}
