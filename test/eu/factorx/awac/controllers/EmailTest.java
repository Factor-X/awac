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

import com.fasterxml.jackson.databind.JsonNode;
import eu.factorx.awac.dto.awac.get.LoginResultDTO;
import eu.factorx.awac.dto.myrmex.get.ExceptionsDTO;
import eu.factorx.awac.dto.myrmex.post.ConnectionFormDTO;
import eu.factorx.awac.models.AbstractBaseModelTest;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import play.Configuration;
import play.Logger;
import play.libs.Json;
import play.mvc.Result;
import play.test.FakeRequest;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static play.test.Helpers.*;

//import play.api.mvc.AnyContent;
//import com.avaje.ebean.Ebean;

@ContextConfiguration(locations = {"classpath:/components-test.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EmailTest extends AbstractBaseModelTest {

	@Test
	public void _001_submitActionSuccess() {

		// try to get a bash env
		String smtpPassword = Configuration.root().getString("mail.smtp.password");
		assertNotNull("Password not in the bash",smtpPassword);

		// Fake request
		FakeRequest fr = new FakeRequest();
//	fr.withHeader("Content-type", "application/json");
//	fr.withJsonBody(node);

		// Call controller action
		Result result = callAction(
				eu.factorx.awac.controllers.routes.ref.EmailController.send("gaston.hollands@factorx.eu", "AWAC Registration Confirmation", "Need to http://localhost:9000/awac/confirm/" + "1"),
				fr
		); // callAction

		// wait some time to be sure AKKA actor has time to send the message

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// test results
		// expecting an HTTP 200 return code
		assertEquals("failed -> "+result,200, status(result));
	} // end of authenticateSuccess test

}

