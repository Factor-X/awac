package eu.factorx.awac.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertFalse;

import java.util.List;

import javax.validation.constraints.AssertFalse;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import eu.factorx.awac.models.AbstractBaseModelTest;
import eu.factorx.awac.models.knowledge.BaseIndicator;

@ContextConfiguration(locations = { "classpath:/components-test.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class IndicatorServiceTest extends AbstractBaseModelTest {

	@Autowired
	private BaseIndicatorService indicatorService;

	@Test
	public void _001_findAll() {

		List<BaseIndicator> li = indicatorService.findAll();

		assertNotNull(li);
		assertFalse(li.isEmpty());
//		assertEquals(64, li.size());

	} // end of test

} // end of class