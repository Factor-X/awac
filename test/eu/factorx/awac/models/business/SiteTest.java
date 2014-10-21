package eu.factorx.awac.models.business;

import eu.factorx.awac.models.AbstractBaseModelTest;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SiteTest extends AbstractBaseModelTest {

	private static String SITE = "gastonsite";
	private static String ORGANISATION = "factorx";

    @Test
    public void _001_createSite() {

		Organization org = new Organization(ORGANISATION, InterfaceTypeCode.ENTERPRISE);
		em.persist(org);
		Site site = new Site (org,SITE);
		site.setOrganizationalStructure("ORGANIZATION_STRUCTURE_1");
		em.persist(site);
    } // end of test method


    @Test
    public void _002_retrieveSiteSuccess() {

        String query = "select s from Site s where s.name = '" + SITE + "'";
        Site site = em.createQuery(query, Site.class).getResultList().get(0);
        assertEquals(site.getName(), SITE);
    } // end of test

    @Test
    public void _003_retrieveOrganisationFailure() {

        Site site = null;
        String query = "select s from Site s where s.name = 'factor'";

        try {
            site = em.createQuery(query, Site.class).getResultList().get(0);
        } catch (Exception empty) {}

        assertNull(site);
    } // end of test

	@Test
	public void _004_deleteSite() {

		Site site= null;
		String query = "select s from Site s where s.name = '" + SITE +"'";

		try {
			site= em.createQuery(query, Site.class).getResultList().get(0);
		} catch (Exception empty) {}

		assertEquals(site.getName(), SITE);

		em.remove(site);

		Site reload=null;

		try {
			reload = em.createQuery(query, Site.class).getResultList().get(0);
		} catch (Exception empty) {}

		assertNull(reload);
	} // end of test


	@Test
    public void _005_deleteOrganisation() {

        Organization org = null;
        String query = "select o from Organization o where o.name = '" + ORGANISATION + "'";

        try {
            org = em.createQuery(query, Organization.class).getResultList().get(0);
        } catch (Exception empty) {}

        assertEquals(org.getName(), ORGANISATION);

        em.remove(org);

        Organization reload=null;

        try {
            reload = em.createQuery(query, Organization.class).getResultList().get(0);
        } catch (Exception empty) {}

        assertNull(reload);
    } // end of test




} // end of class
