package eu.factorx.awac.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import eu.factorx.awac.models.AbstractBaseModelTest;
import eu.factorx.awac.models.forms.Form;

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
		assertEquals(11, fl.size());

	} // end of test

	@Test
	public void _002_findByIdentifier() {

		Form form = formService.findByIdentifier("TAB2");

		assertNotNull(form);
		assertEquals("TAB2", form.getIdentifier());
	} // end of test

} // end of class
