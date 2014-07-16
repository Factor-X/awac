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

public class AllModelUsingYamlTest extends BaseModelTest {

		/* USE YAML */
	
	    @Test
		public void createDataUsingYamlTest() {
		Map<String,List<Object>> all = (Map<String,List<Object>>)Yaml.load("initial-data-awac.yml"); 
		// Insert users first
		
		//Ebean.save(all.get("addresses"));
		Ebean.save(all.get("administrators"));
		
		//find admin
		Administrator admin = Administrator.find.where().eq("identifier","admin").findUnique();
		assertNotNull(admin);
		assertEquals(admin.lastname, "Hollands");
		assertNotNull(admin.getAddress());
		assertEquals(admin.getAddress().getStreet(), "Fontaine des Miracles");
	} // end of test method

} // end of class
