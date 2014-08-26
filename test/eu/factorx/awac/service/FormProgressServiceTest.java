package eu.factorx.awac.service;

import eu.factorx.awac.models.AbstractBaseModelTest;
import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.code.type.PeriodCode;
import eu.factorx.awac.models.data.FormProgress;
import eu.factorx.awac.models.forms.Form;
import eu.factorx.awac.models.knowledge.Period;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import play.Logger;
import play.api.Play;

import java.util.List;

import static org.junit.Assert.*;

@ContextConfiguration(locations = {"classpath:/components-test.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FormProgressServiceTest extends AbstractBaseModelTest {

	private final static PeriodCode  PERIOD_CODE= PeriodCode.P2013;
	private final static String ORGANIZATION_NAME = "Factor-X";
	private final static String FORM_IDENTIFIER = "TAB7";
	private final static int EXPECTED_PERCENT = 99;

	@Autowired
	private ScopeService scopeService;

	@Autowired
	private FormProgressService formProgressService;

	@Autowired
	private PeriodService periodService;

	@Autowired
	private OrganizationService organizationService;

	@Autowired
	private FormService formService;



	/* TODO : FormProgressService.remove dont work. Need @Transactional.  */

	@Test
	public void _000_prepareData () {

		Period period;
		Form form;
		Scope scope;
		Integer percentage = new Integer (EXPECTED_PERCENT);

		period = periodService.findByCode(PERIOD_CODE);
		form = formService.findByIdentifier(FORM_IDENTIFIER);
		scope = scopeService.findByOrganization(organizationService.findByName(ORGANIZATION_NAME));

		FormProgress fp = formProgressService.saveOrUpdate(new FormProgress(period, form, scope, percentage));

		assertNotNull(fp);
		assertEquals(new Integer(EXPECTED_PERCENT),fp.getPercentage());

	} // end of test

	@Test
    public void _001_findAll() {

		List <FormProgress> fp = formProgressService.findAll();

		assertNotNull(fp);
		assertTrue(fp.size()>=1?true:false);

	} // end of test


	@Test
	public void _002_findByPeriodAndByScope() {

		Period period = periodService.findByCode(PERIOD_CODE);
		Scope scope = scopeService.findByOrganization(organizationService.findByName(ORGANIZATION_NAME));

		assertNotNull("Period is null, founded by "+PERIOD_CODE, period);
		assertNotNull("Scope is null, founded by "+ORGANIZATION_NAME, scope);

		List <FormProgress> formProgress = formProgressService.findByPeriodAndByScope(period,scope);

		assertNotNull(formProgress);

	} // end of test

	@Test
	public void _003_findByPeriodAndByScopeAndForm() {

		Period period = periodService.findByCode(PERIOD_CODE);
		Scope scope = scopeService.findByOrganization(organizationService.findByName(ORGANIZATION_NAME));
		Form form = formService.findByIdentifier(FORM_IDENTIFIER);

		assertNotNull("Form is null, founded by "+FORM_IDENTIFIER, form);
		assertNotNull("Period is null, founded by "+PERIOD_CODE, period);
		assertNotNull("Scope is null, founded by "+ORGANIZATION_NAME, scope);

		FormProgress formProgress = formProgressService.findByPeriodAndByScopeAndForm(period,scope,form);

		assertNotNull("FormProgress not found for period/scope/form : "+period+"/"+scope+"/"+form, formProgress);
		assertEquals("Excepected pourcentage : "+EXPECTED_PERCENT+", but found : "+formProgress.getPercentage(),new Integer(EXPECTED_PERCENT),formProgress.getPercentage());

	} // end of test

	@Test
	public void _099_deleteData () {

		Period period;
		Form form;
		Scope scope;
		Integer percentage = new Integer (99);

		period = periodService.findByCode(PERIOD_CODE);
		form = formService.findByIdentifier(FORM_IDENTIFIER);
		scope = scopeService.findByOrganization(organizationService.findByName(ORGANIZATION_NAME));

		FormProgress formProgress = formProgressService.findByPeriodAndByScopeAndForm(period,scope,form);

		//Logger.info("id:" + formProgress.getId());
		em.getTransaction().begin();
		formProgressService.remove(formProgress);
		em.getTransaction().commit();
		assertTrue(true);

	} // end of test


} // end of class
