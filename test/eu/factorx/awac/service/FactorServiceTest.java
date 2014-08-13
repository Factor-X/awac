package eu.factorx.awac.service;

import eu.factorx.awac.models.AbstractBaseModelTest;
import eu.factorx.awac.models.code.type.ActivitySourceCode;
import eu.factorx.awac.models.code.type.ActivityTypeCode;
import eu.factorx.awac.models.code.type.IndicatorCategoryCode;
import eu.factorx.awac.models.knowledge.Factor;
import eu.factorx.awac.models.knowledge.Unit;
import eu.factorx.awac.service.impl.FactorSearchParameter;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import play.Logger;

import java.util.*;

import static org.junit.Assert.*;

@ContextConfiguration(locations = {"classpath:/components-test.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FactorServiceTest extends AbstractBaseModelTest {

	@Autowired
	private FactorService factorService;

	@Autowired
	private UnitService unitService;

    @Test
    public void _001_findAll() {

		List <Factor> lf = factorService.findAll();

		assertNotNull(lf);
		assertEquals(649,lf.size());

	} // end of test

	@Test
	public void _002_findByParameters() {

		// assume following search parameters
		FactorSearchParameter searchParameter = new FactorSearchParameter();
		searchParameter.setActivitySource(ActivitySourceCode.AS_1);
		searchParameter.setActivityType(ActivityTypeCode.AT_1);
		searchParameter.setIndicatorCategory(IndicatorCategoryCode.IC_1);
		searchParameter.setUnitIn(unitService.findBySymbol("GJ")); // GJ - id 214
		searchParameter.setUnitOut(unitService.findBySymbol("tCO2e")); // tCO2e - id 224

		Factor factor = factorService.findByParameters(searchParameter);
		assertNotNull(factor);
		//Logger.info("Institution :" + factor.getInstitution());
		assertEquals(factor.getInstitution(),"IPCC, lignes directrices 2006, volume:2, table 2.4");

	}

} // end of class
