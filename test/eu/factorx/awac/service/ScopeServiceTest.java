package eu.factorx.awac.service;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import eu.factorx.awac.models.AbstractBaseModelTest;
import eu.factorx.awac.models.business.Scope;

@ContextConfiguration(locations = {"classpath:/components-test.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ScopeServiceTest extends AbstractBaseModelTest {

	private final static String ORGANIZATION_NAME = "Factor-X";

	@Autowired
	private ScopeService scopeService;

	@Autowired
	private OrganizationService organizationService;

	@Autowired
	private ProductService productService;

	//@Test
	public void _001_findAll() {

		// Regarding scope refactoring, Scope is now an abstract class cand an not be instantiated anymore as itself
		// Test is obsolete.
		List<Scope> sf = scopeService.findAll();

		assertNotNull(sf);
		assertTrue(sf.size() > 0);

	} // end of test


	@Test
	public void _002_findByOrganization() {

		Scope scope = organizationService.findById(FACTORX_ID);

		assertNotNull("Scope not found for organization "+ORGANIZATION_NAME, scope);
		assertEquals("Organization expected "+ORGANIZATION_NAME+" but found : "+scope.getOrganization().getName(), ORGANIZATION_NAME, scope.getOrganization().getName());

	} // end of test

	@Test
	public void _003_findByProduct() {

		Scope scope = null;
		try {
			scope = productService.findAll().get(0); // No product for AWAC
			fail("Getting first product should throw an IndexOutOfBoundsException! (no product in AWAC)");
		} catch (IndexOutOfBoundsException noProductFound) {
			// no product is available for AWAC
			// assume test is OK if scope is null
		}
		assertNull(scope);

	} // end of test


} // end of class
