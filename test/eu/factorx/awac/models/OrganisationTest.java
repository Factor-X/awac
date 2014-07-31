package eu.factorx.awac.models;

import eu.factorx.awac.models.business.Organization;
import org.hibernate.Session;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;
import play.api.Play;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by gaston on 7/31/14.
 */
public class OrganisationTest extends AbstractBaseModelTest {


    @Test
    public void createOrganisation() {

        em.getTransaction().begin();
        em.persist(new Organization("factorx"));
        em.getTransaction().commit();
    } // end of test method


    @Test
    public void retrieveOrganisationSuccess() {

        String query = "select o from Organization o where o.name = 'factorx'";
        Organization org = em.createQuery(query, Organization.class).getResultList().get(0);
        assertEquals(org.getName(), "factorx");
    } // end of test

    @Test
    public void retrieveOrganisationFailure() {

        Organization org = null;
        String query = "select o from Organization o where o.name = 'factor'";

        try {
            org = em.createQuery(query, Organization.class).getResultList().get(0);
        } catch (Exception empty) {}

        assertNull(org);
    } // end of test

    @Test
    public void deleteOrganisation() {

        Organization org = null;
        String query = "select o from Organization o where o.name = 'factorx'";

        try {
            org = em.createQuery(query, Organization.class).getResultList().get(0);
        } catch (Exception empty) {}

        assertEquals(org.getName(), "factorx");

        em.getTransaction().begin();
        em.remove(org);
        em.getTransaction().commit();

        Organization reload=null;

        try {
            reload = em.createQuery(query, Organization.class).getResultList().get(0);
        } catch (Exception empty) {}

        assertNull(reload);
    } // end of test


    @Test
    public void updateOrganization() {


        // Create
        em.getTransaction().begin();
        em.persist(new Organization("carrefour"));
        em.getTransaction().commit();

        // Check persistence
        Organization org = null;
        String query1 = "select o from Organization o where o.name = 'carrefour'";

        try {
            org = em.createQuery(query1, Organization.class).getResultList().get(0);
        } catch (Exception empty) {}

        assertEquals(org.getName(), "carrefour");

        // update org name
        org.setName("CarrefourCamelCase");
        em.getTransaction().begin();
        em.persist(org);
        em.getTransaction().commit();

        // Check update persistence
        Organization modifiedOrg = null;
        String query2 = "select o from Organization o where o.name = 'CarrefourCamelCase'";

        try {
            modifiedOrg = em.createQuery(query2, Organization.class).getResultList().get(0);
        } catch (Exception empty) {}

        assertEquals(modifiedOrg.getName(), "CarrefourCamelCase");

    } // end of test method


} // end of class
