package eu.factorx.awac.service;

import static org.junit.Assert.assertNotNull;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import play.mvc.Result;
import eu.factorx.awac.models.AbstractBaseModelTest;

@ContextConfiguration(locations = {"classpath:/components-test.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class VelocityGeneratorServiceTest extends AbstractBaseModelTest {

	private final static String ORGANIZATION_NAME = "Factor-X";

	@Autowired
	private VelocityGeneratorService velocityGenerator;

	@Test
	public void _001_basic() throws FileNotFoundException {

		assertNotNull(velocityGenerator);

		Map values = new HashMap<String,Object>();
		values.put("name","My name is gaston");
		values.put("title","This is my titre");

		Result result = velocityGenerator.ok("index.vm",values);
		assertNotNull(result);

	} // end of test

} // end of class
