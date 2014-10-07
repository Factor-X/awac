package eu.factorx.awac.models.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import eu.factorx.awac.models.AbstractBaseModelTest;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OrganisationTest extends AbstractBaseModelTest {


    @Test
    public void _001_createOrganisation() {

        em.persist(new Organization("factorx", InterfaceTypeCode.ENTERPRISE));
    } // end of test method


    @Test
    public void _002_retrieveOrganisationSuccess() {

        String query = "select o from Organization o where o.name = 'factorx'";
        Organization org = em.createQuery(query, Organization.class).getResultList().get(0);
        assertEquals(org.getName(), "factorx");
    } // end of test

    @Test
    public void _003_retrieveOrganisationFailure() {

        Organization org = null;
        String query = "select o from Organization o where o.name = 'factor'";

        try {
            org = em.createQuery(query, Organization.class).getResultList().get(0);
        } catch (Exception empty) {}

        assertNull(org);
    } // end of test

    @Test
    public void _004_deleteOrganisation() {

        Organization org = null;
        String query = "select o from Organization o where o.name = 'factorx'";

        try {
            org = em.createQuery(query, Organization.class).getResultList().get(0);
        } catch (Exception empty) {}

        assertEquals(org.getName(), "factorx");

        em.remove(org);

        Organization reload=null;

        try {
            reload = em.createQuery(query, Organization.class).getResultList().get(0);
        } catch (Exception empty) {}

        assertNull(reload);
    } // end of test


    @Test
    public void _005_updateOrganization() {


        // Create
        em.persist(new Organization("carrefour", InterfaceTypeCode.ENTERPRISE));

        // Check persistence
        Organization org = null;
        String query1 = "select o from Organization o where o.name = 'carrefour'";

        try {
            org = em.createQuery(query1, Organization.class).getResultList().get(0);
        } catch (Exception empty) {}

        assertEquals(org.getName(), "carrefour");

        // update org name
        org.setName("CarrefourCamelCase");
        em.persist(org);

        // Check update persistence
        Organization modifiedOrg = null;
        String query2 = "select o from Organization o where o.name = 'CarrefourCamelCase'";

        try {
            modifiedOrg = em.createQuery(query2, Organization.class).getResultList().get(0);
        } catch (Exception empty) {}

        assertEquals(modifiedOrg.getName(), "CarrefourCamelCase");

    } // end of test method

	@Test
	public void _006_deleteOrganisation() {

		Organization org = null;
		String query = "select o from Organization o where o.name = 'CarrefourCamelCase'";

		try {
			org = em.createQuery(query, Organization.class).getResultList().get(0);
		} catch (Exception empty) {}

		assertEquals(org.getName(), "CarrefourCamelCase");

		em.remove(org);

		Organization reload=null;

		try {
			reload = em.createQuery(query, Organization.class).getResultList().get(0);
		} catch (Exception empty) {}

		assertNull(reload);
	} // end of test


} // end of class
