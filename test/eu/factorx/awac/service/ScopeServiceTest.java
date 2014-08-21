package eu.factorx.awac.service;

import eu.factorx.awac.models.AbstractBaseModelTest;
import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.code.type.ActivitySourceCode;
import eu.factorx.awac.models.code.type.ActivityTypeCode;
import eu.factorx.awac.models.code.type.IndicatorCategoryCode;
import eu.factorx.awac.models.knowledge.Factor;
import eu.factorx.awac.service.impl.FactorSearchParameter;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@ContextConfiguration(locations = {"classpath:/components-test.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ScopeServiceTest extends AbstractBaseModelTest {

	@Autowired
	private ScopeService scopeService;

	@Autowired
	private OrganizationService organizationService;

	@Autowired
	private ProductService productService;



	@Test
    public void _001_findAll() {

		List <Scope> sf = scopeService.findAll();

		assertNotNull(sf);
		assertEquals(13,sf.size());

	} // end of test


	@Test
	public void _002_findByOrganization() {

		Scope scope = scopeService.findByOrganization(organizationService.findById(1L)); // id 1 = Factorx

		assertNotNull(scope);
		assertEquals("Factor-X",scope.getOrganization().getName());

	} // end of test

	@Test
	public void _003_findByProduct() {

		Scope scope = null;
		try {
			scope = scopeService.findByProduct(productService.findAll().get(0)); // No product for AWAC
		} catch (IndexOutOfBoundsException noProductFound) {
			// no product is available for AWAC
			// assume test is OK if scope is null
		}
		assertNull(scope);

	} // end of test


} // end of class
