package eu.factorx.awac.service;

import eu.factorx.awac.models.AbstractBaseModelTest;
import eu.factorx.awac.models.data.FormProgress;
import eu.factorx.awac.models.forms.Form;
import eu.factorx.awac.models.knowledge.UnitCategory;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@ContextConfiguration(locations = {"classpath:/components-test.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FormServiceTest extends AbstractBaseModelTest {

	@Autowired
	private FormService formService;


	@Test
    public void _001_findAll() {

		List <Form> fl = formService.findAll();

		assertNotNull(fl);
		assertEquals(6, fl.size());

		Collection col = Collections.unmodifiableList(new ArrayList<String>(Arrays.asList(
				"TAB2",
				"TAB3",
				"TAB4",
				"TAB5",
				"TAB6",
				"TAB7")));

		for (Form form : fl) {
			assertTrue(col.contains(form.getIdentifier()));
		}
	} // end of test

	@Test
	public void _002_findByIdentifier() {

		Form form = formService.findByIdentifier("TAB2");

		assertNotNull(form);
		assertEquals("TAB2", form.getIdentifier());
	} // end of test

} // end of class
