/*
 *
 * Instant Play Framework
 * AWAC
 *                       
 *
 * Copyright (c) 2014 Factor-X.
 * Author Gaston Hollands
 *
 */
 
package eu.factorx.awac.models;

import eu.factorx.awac.models.*;
import eu.factorx.awac.models.account.Address;
import eu.factorx.awac.models.account.Administrator;

import org.junit.*;

import static org.junit.Assert.*;
import static play.test.Helpers.*;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;


// for YAML tests
import com.avaje.ebean.*;

import play.*;
import play.libs.*;

import java.util.Map;

public class AdministratorTest extends BaseModelTest {

	/* ADMINISTRATOR TESTS */
//    @Test
//    public void createAndRetrieveAdministrator() {
//        new Administrator("admin001id", "admin001pwd", "admin001name","admin001lastname").save();
//        Administrator admin = Administrator.find.where().eq("identifier", "admin001id").findUnique();
//        assertNotNull(admin);
//        assertEquals(admin.lastname, "admin001name");
//    } // end of test method
//
//    @Test
//    public void createAndRetrieveAdministratorWithAddress() {
//        new Administrator("admin001id", "admin001pwd", "admin001name","admin001lastname").save();
//        Administrator admin = Administrator.find.where().eq("identifier", "admin001id").findUnique();
//        assertNotNull(admin);
//        assertEquals("admin001name",admin.lastname);
//
//		Address address = new Address ("Emile Delva","1020","Laeken","Belgium");
//		play.Logger.info ("address street : " + address.street);
//		play.Logger.info ("address postalcode : " + address.postalcode);
//		play.Logger.info ("address country: " + address.country);
//		play.Logger.info ("address city : " + address.city);
//
//		play.Logger.info("isEmbeddedNewOrDirty?" + admin._ebean_isEmbeddedNewOrDirty());
//		admin.setAddress(address);
//
		//play.Logger.info("isEmbeddedNewOrDirty?" + admin._ebean_isEmbeddedNewOrDirty());
		//((Person)admin)._ebean_setEmbeddedLoaded();
		//play.Logger.info("isEmbeddedNewOrDirty?" + admin._ebean_isEmbeddedNewOrDirty());
//		admin.update();
//
//        Administrator find = Administrator.find.where().eq("identifier", "admin001id").findUnique();
//        assertNotNull(find);
//        assertEquals("admin001name",find.lastname);
//        assertNotNull(find.address);
//		assertEquals("Emile Delva",find.address.street);
		
		//play.Logger.info ("address street : " + find.address.street);
		//play.Logger.info ("address postalcode : " + find.address.postalcode);
		//play.Logger.info ("address country: " + find.address.country);
		//play.Logger.info ("address city : " + find.address.city);
		

//    } // end of test method

} // end of class
