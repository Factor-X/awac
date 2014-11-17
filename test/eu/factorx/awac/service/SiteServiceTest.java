package eu.factorx.awac.service;

import java.util.List;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import eu.factorx.awac.models.AbstractBaseModelTest;
import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.business.Site;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.models.code.type.PeriodCode;
import eu.factorx.awac.models.knowledge.Period;

@ContextConfiguration(locations = { "classpath:/components-test.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SiteServiceTest extends AbstractBaseModelTest {

	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private SiteService siteService;
	@Autowired
	private PeriodService periodService;
	@Autowired
	private ScopeService scopeService;

	private static Period period1;
	private static Period period2;
	private static Organization org;
	private static Site site;
	private static Long siteId;

	@Test
	public void _001_testCascadingPersist() {
		period1 = periodService.findByCode(PeriodCode.P2005);
		period2 = periodService.findByCode(PeriodCode.P2006);
		Assert.assertNotEquals("assertNotEquals(period1, period2)", period1, period2);
		org = organizationService.saveOrUpdate(new Organization("anOrg", InterfaceTypeCode.ENTERPRISE));
		site = new Site(org, "aSite");
		site.setOrganizationalStructure("ORGANIZATION_STRUCTURE_1");
		site.getListPeriodAvailable().add(period1);
		site = siteService.saveOrUpdate(site);
		siteId = site.getId();

		Site foundSite = siteService.findById(siteId);
		Assert.assertNotNull("Cannot retrieve site with id = " + siteId, foundSite);
		List<Period> sitePeriods = foundSite.getListPeriodAvailable();
		Assert.assertEquals("The site '" + foundSite.getName() + "' should be linked to period '" + period1.getLabel() + "'", period1, sitePeriods.get(0));
	}

	@Test
	public void _002_testCascadingMerge() {
		site.getListPeriodAvailable().clear();
		site.getListPeriodAvailable().add(period2);
		site = siteService.saveOrUpdate(site);

		Site foundSite = siteService.findById(siteId);
		List<Period> sitePeriods = foundSite.getListPeriodAvailable();
		Assert.assertEquals("The site '" + foundSite.getName() + "' should be linked to period '" + period2.getLabel() + "'", period2, sitePeriods.get(0));
		Assert.assertEquals("The site '" + foundSite.getName() + "' should be linked to one and only one period", 1, sitePeriods.size());
	}

	@Test
	public void _003_testCascadingDelete() {
		siteService.remove(site);

		Site foundSite = siteService.findById(siteId);
		Assert.assertNull("The site with id = " + siteId + " should have been deleted", foundSite);
	}

}
