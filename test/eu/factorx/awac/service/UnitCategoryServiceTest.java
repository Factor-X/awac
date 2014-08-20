package eu.factorx.awac.service;

import eu.factorx.awac.dto.awac.get.UnitCategoryDTO;
import eu.factorx.awac.dto.awac.get.UnitDTO;
import eu.factorx.awac.models.AbstractBaseModelTest;
import eu.factorx.awac.models.knowledge.Unit;
import eu.factorx.awac.models.knowledge.UnitCategory;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import play.Logger;


import eu.factorx.awac.service.UnitCategoryService;

import java.util.*;

import static org.junit.Assert.*;

@ContextConfiguration(locations = {"classpath:/components-test.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UnitCategoryServiceTest extends AbstractBaseModelTest {



	@Autowired
	private UnitCategoryService unitCategoryService;

    @Test
    public void _001_getAllCategories() {

		// Data to consider @07/08/2014
		/*
				"Length",
				"Area",
				"Volume",
				"Mass",
				"Density",
				"Duration",
				"Frequency",
				"Speed",
				"Energy",
				"Power",
				"Voltage",
				"Temperature",
				"Currency",
				"Water Consumption",
				"Global Warming Potential",
				"Time"
		*/

		List<UnitCategory> luc = unitCategoryService.findAll();
		assertNotNull(luc);
		assertEquals(15,luc.size());

		Collection col = Collections.unmodifiableList(new ArrayList<String>(Arrays.asList(
				"Length",
				"Area",
				"Volume",
				"Mass",
				"Density",
				"Duration",
				"Frequency",
				"Speed",
				"Energy",
				"Power",
				"Voltage",
				"Temperature",
				"Currency",
				"Water Consumption",
				"Global Warming Potential")));

		for (UnitCategory unitCategory : luc) {
			assertTrue(col.contains(unitCategory.getName()));
		}

	} // end of test

} // end of class
