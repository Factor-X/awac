package eu.factorx.awac.models;

import eu.factorx.awac.models.account.Administrator;
import eu.factorx.awac.models.business.Organization;
import org.hibernate.Session;
import org.junit.Test;
import play.api.Play;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.libs.F;

import static org.junit.Assert.*;


public class OrganizationTest extends BaseModelTest {

    /* ORGANIZATION TESTS */

    //@Test
    public void createOrganization() {

        JPA.withTransaction(new F.Callback0() {
            @Override
            public void invoke() throws Throwable {
                JPA.em().persist(new Organization("factorx"));
                JPA.em().unwrap(Session.class).flush();
            }
        });
    } // end of test method


    //@Test
    public void retrieveOrganizationSuccess() {
        JPA.withTransaction(new F.Callback0() {
            @Override
            public void invoke() throws Throwable {
                String query = "select o from Organization o where o.name = 'factorx'";
//                System.out.print("getResultList().get(0)-->"+JPA.em().createQuery(query).getResultList().get(0));
                Organization org = JPA.em().createQuery(query,Organization.class).getResultList().get(0);
//                System.out.print("organization name = " + org.getName());
                assertEquals(org.getName(), "factorx");
            }
        });
    }

    //@Test
    public void retrieveOrganizationFailure() {
        JPA.withTransaction(new F.Callback0() {
            @Override
            public void invoke() throws Throwable {
                Organization org = null;
                String query = "select o from Organization o where o.name = 'factor'";
//                System.out.print("getResultList().get(0)-->"+JPA.em().createQuery(query).getResultList().get(0));
                try {
                    org = JPA.em().createQuery(query, Organization.class).getResultList().get(0);
                } catch (Exception empty) {}
              assertNull(org);
            }
        });
    }

    //@Test
    public void deleteOrganization() {
        JPA.withTransaction(new F.Callback0() {
            @Override
            public void invoke() throws Throwable {
                Organization org = null;
                String query = "select o from Organization o where o.name = 'factorx'";
//                System.out.print("getResultList().get(0)-->"+JPA.em().createQuery(query).getResultList().get(0));
                try {
                    org = JPA.em().createQuery(query, Organization.class).getResultList().get(0);
                } catch (Exception empty) {}
                assertNull(org);
                try {
                  JPA.em().remove(org);
                } catch (Exception empty) {}

            }
        });
    }


    //@Test
    public void updateOrganization() {


        // Create
        JPA.withTransaction(new F.Callback0() {
            @Override
            public void invoke() throws Throwable {
                JPA.em().persist(new Organization("carrefour"));
                JPA.em().unwrap(Session.class).flush();
            }
        });

        // Check persistence and update name
        JPA.withTransaction(new F.Callback0() {
            @Override
            public void invoke() throws Throwable {
                // Check persistence
                String query = "select o from Organization o where o.name = 'carrefour'";
                Organization org = JPA.em().createQuery(query,Organization.class).getResultList().get(0);
                assertEquals(org.getName(), "carrefour");
                org.setName("CarrefourWithUpperCase");
                JPA.em().persist(org);
                JPA.em().unwrap(Session.class).flush();
            }
        });

        // Check persistence and update name
        JPA.withTransaction(new F.Callback0() {
            @Override
            public void invoke() throws Throwable {
                // Check persistence
                String query = "select o from Organization o where o.name = 'CarrefourWithUpperCase'";
                Organization org = JPA.em().createQuery(query,Organization.class).getResultList().get(0);
                // name has changed
                assertEquals(org.getName(), "CarrefourWithUpperCase");
                // list only contains 1 organisation
                assertEquals(1,JPA.em().createQuery(query,Organization.class).getResultList().size());
            }
        });


    } // end of test method

}
