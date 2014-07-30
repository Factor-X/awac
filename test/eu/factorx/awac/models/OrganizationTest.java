package eu.factorx.awac.models;

import eu.factorx.awac.models.account.Administrator;
import eu.factorx.awac.models.business.Organization;
import org.hibernate.Session;
import org.junit.Test;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.libs.F;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class OrganizationTest extends BaseModelTest {

    /* ADMINISTRATOR TESTS */
//    @Transactional
    @Test
    public void createOrganization() {

        assertEquals(1, 1);
        JPA.withTransaction(new F.Callback0() {
            @Override
            public void invoke() throws Throwable {
                JPA.em().persist(new Organization("factorx"));
                JPA.em().unwrap(Session.class).flush();
            }
        });
//        new Administrator("admin001id", "admin001pwd", "admin001name","admin001lastname").save();
//        Administrator admin = Administrator.find.where().eq("identifier", "admin001id").findUnique();
//        assertNotNull(admin);
//        assertEquals(admin.lastname, "admin001name");
    } // end of test method


    @Test
    public void retrieveOrganization() {
        assertEquals(1, 1);
        JPA.withTransaction(new F.Callback0() {
            @Override
            public void invoke() throws Throwable {
                String query = "select o from Organization o where o.name = 'factorx'";
                System.out.print("getResultList().get(0)-->"+JPA.em().createQuery(query).getResultList().get(0));
                Organization org = JPA.em().createQuery(query,Organization.class).getResultList().get(0);
                System.out.print("organization name = " + org.getName());
            }
        });
    }

}
