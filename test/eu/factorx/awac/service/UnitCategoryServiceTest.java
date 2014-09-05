package eu.factorx.awac.service;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import eu.factorx.awac.models.AbstractBaseModelTest;
import eu.factorx.awac.models.knowledge.UnitCategory;

@ContextConfiguration(locations = {"classpath:/components-test.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UnitCategoryServiceTest extends AbstractBaseModelTest {

	@Autowired
	private UnitCategoryService unitCategoryService;

    @Test
    public void _001_getAllCategories() {

		// DataCell to consider @07/08/2014
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

		Collection<String> col = Collections.unmodifiableList(new ArrayList<String>(Arrays.asList(
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
