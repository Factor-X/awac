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

import play.mvc.*;
import play.libs.*;
import play.test.*;
import static play.test.Helpers.*;
import com.avaje.ebean.Ebean;
import com.google.common.collect.ImmutableMap;

public class LoginTest extends WithApplication {
    @Before
    public void setUp() {
        start(fakeApplication(inMemoryDatabase(), fakeGlobal()));
	Map<String,List<Object>> all = (Map<String,List<Object>>)Yaml.load("initial-data-awac.yml"); 
    // Insert users first
    
    //Ebean.save(all.get("addresses"));
    Ebean.save(all.get("administrators"));
    } // end of set up

  @Test
  public void authenticateSuccess() {
    Result result = callAction(
        eu.factorx.awac.controllers.routes.ref.Authentication.authenticate(),
        fakeRequest().withFormUrlEncodedBody(ImmutableMap.of(
            "identifier", "admin",
            "password", "password"))
    );
    assertEquals(303, status(result));
    assertEquals("admin", session(result).get("identifier"));
  } // end of authenticateSuccess test

  @Test
  public void authenticateFailure() {
    Result result = callAction(
        eu.factorx.awac.controllers.routes.ref.Authentication.authenticate(),
        fakeRequest().withFormUrlEncodedBody(ImmutableMap.of(
            "identifier", "admin",
            "password", "badpassword"))
    );
    assertEquals(400, status(result));
    assertNull(session(result).get("identifier"));
  } // end of authenticationFailure
  
  @Test
  public void authenticated() {
    Result result = callAction(
        eu.factorx.awac.controllers.routes.ref.Application.index(),
        fakeRequest().withSession("identifier", "admin")
    );
    assertEquals(200, status(result));
  } // end of authenticated test  

  @Test
  public void logout() {
    Result result = callAction(
        eu.factorx.awac.controllers.routes.ref.Authentication.logout(),
        fakeRequest()
    );
    assertEquals(303, status(result));
	assertEquals("/awac/login", header("Location", result));
  } // end of authenticated test    

  @Test
  public void notAuthenticated() {
    Result result = callAction(
        eu.factorx.awac.controllers.routes.ref.Application.index(),
        fakeRequest()
    );
    assertEquals(200, status(result));
	assertNull(session(result).get("identifier")); // back to index page for non authenticated users
  } // end of notAuthenticated test

}

