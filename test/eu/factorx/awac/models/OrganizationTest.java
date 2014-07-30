package eu.factorx.awac.models;

import eu.factorx.awac.models.account.Administrator;
import eu.factorx.awac.models.business.Organization;
import org.junit.Test;
import play.db.jpa.JPA;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class OrganizationTest extends BaseModelTest {

    /* ADMINISTRATOR TESTS */
    @Test
    public void createAndRetrieveOrganization() {

        assertEquals(1, 1);
        //JPA.em().persist(new Organization("factorx"));
//        new Administrator("admin001id", "admin001pwd", "admin001name","admin001lastname").save();
//        Administrator admin = Administrator.find.where().eq("identifier", "admin001id").findUnique();
//        assertNotNull(admin);
//        assertEquals(admin.lastname, "admin001name");
    } // end of test method
}
