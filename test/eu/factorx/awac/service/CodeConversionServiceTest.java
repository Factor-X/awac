package eu.factorx.awac.service;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import eu.factorx.awac.models.AbstractBaseModelTest;
import eu.factorx.awac.models.code.CodeList;

@ContextConfiguration(locations = { "classpath:/components-test.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CodeConversionServiceTest extends AbstractBaseModelTest {

	@Autowired
	private CodeConversionService codeConversionService;

	@Test
	public void _001_isSublistOf() {

		Assert.assertTrue("The code list " + CodeList.FRIGORIGENE + " should be a sublist of " + CodeList.ActivitySource,
				codeConversionService.isSublistOf(CodeList.FRIGORIGENE, CodeList.ActivitySource));

		Assert.assertFalse("The code list " + CodeList.ActivitySource + " should not be a sublist of " + CodeList.FRIGORIGENE,
				codeConversionService.isSublistOf(CodeList.ActivitySource, CodeList.FRIGORIGENE));

		Assert.assertFalse("The code list " + CodeList.ActivitySource + " should not be a sublist of " + CodeList.FRIGORIGENE,
				codeConversionService.isSublistOf(CodeList.FRIGORIGENE, CodeList.ActivityType));
	}

}
