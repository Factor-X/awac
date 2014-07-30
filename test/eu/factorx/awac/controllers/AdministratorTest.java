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
 
package eu.factorx.awac.controllers;

import org.junit.*;

import static org.junit.Assert.*;

import java.util.*;

import eu.factorx.awac.models.*;
import eu.factorx.awac.models.account.Administrator;
import play.mvc.*;
import play.libs.*;
import play.test.*;
import static play.test.Helpers.*;

import com.avaje.ebean.Ebean;
import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

public class AdministratorTest extends WithApplication {
//    @Before
//    public void setUp() {
//             start(fakeApplication(inMemoryDatabase(), fakeGlobal()));
//	Map<String,List<Object>> all = (Map<String,List<Object>>)Yaml.load("initial-data-awac.yml");
    // Insert users first
    
    //Ebean.save(all.get("addresses"));
//    Ebean.save(all.get("administrators"));
//    } // end of set-up
//
//    @Test
//    public void newAdministrator() {
//
//	Map<String,String> data = new HashMap<String,String>();
//    data.put("lastname", "banzaii");
//    data.put("firstname", "chinese");
//	data.put("identifier","admin");
//	data.put("password","secret");
//	data.put("email","titi@toto.com");
//	data.put("address.street","Fontaine");
//	data.put("address.postalcode","1360");
//	data.put("address.city","Perwez");
//	data.put("address.country","Belgium");
//
//
//     Result result = callAction(
//        eu.factorx.awac.controllers.routes.ref.Administrators.save(),
//        fakeRequest().withSession("identifier", "admin") //existing admin is the one who is logged
//            .withFormUrlEncodedBody(ImmutableMap.copyOf(data))
//     );
//    assertEquals(303, status(result)); // redirect is expected (cf
//	Administrator administrator = Administrator.find.where()
//        .eq("lastname", "banzaii").findUnique();
//    assertNotNull(administrator);
//    assertEquals(administrator.lastname, "banzaii");
//    }
//
//    @Test
//    public void updateAdministrator() {
//	long id = Administrator.find.where()
//        .eq("identifier", "admin").findUnique().personId;
//
//
//	Map<String,String> data = new HashMap<String,String>();
//    data.put("lastname", "banzaii");
//    data.put("firstname", "chinese");
//	data.put("identifier","admin");
//	data.put("password","secret");
//	data.put("email","titi@toto.com");
//	data.put("address.street","Fontaine");
//	data.put("address.postalcode","1360");
//	data.put("address.city","Perwez");
//	data.put("address.country","Belgium");
//
//     Result result = callAction(
//        eu.factorx.awac.controllers.routes.ref.Administrators.update(id),
//        fakeRequest().withSession("identifier", "admin") //existing admin is the one who is logged
//            .withFormUrlEncodedBody(ImmutableMap.copyOf(data))
//     );
//    assertEquals(303, status(result)); // redirect is expected
//    Administrator administrator = Administrator.find.where()
//        .eq("lastname", "banzaii").findUnique();
//    assertNotNull(administrator);
//    assertEquals(administrator.lastname, "banzaii");
//    }
//
//    @Test
//    public void updateAdministrateurForbidden() {
//	long id = Administrator.find.where()
//        .eq("identifier", "admin").findUnique().personId;
//
//	Map<String,String> data = new HashMap<String,String>();
//    data.put("lastname", "banzaii");
//    data.put("firstname", "chinese");
//	data.put("identifier","admin");
//	data.put("password","secret");
//	data.put("email","titi@toto.com");
//	data.put("address.street","Fontaine");
//	data.put("address.postalcode","1360");
//	data.put("address.city","Perwez");
//	data.put("address.country","Belgium");
//
//     Result result = callAction(
//        eu.factorx.awac.controllers.routes.ref.Administrators.update(id),
//        fakeRequest().withSession("identifier", "hollandg") //existing user without admin rights
//            .withFormUrlEncodedBody(ImmutableMap.copyOf(data))
//     );
//    assertEquals(403, status(result)); // access denied
//    Administrator administrator = Administrator.find.where()
//        .eq("lastname", "Hollands").findUnique();
//    assertNotNull(administrator);
//    assertEquals(administrator.lastname, "Hollands");
//    }
}// end of test class

