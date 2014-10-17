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
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static play.test.Helpers.*;

//import play.api.mvc.AnyContent;
//import com.avaje.ebean.Ebean;

@ContextConfiguration(locations = {"classpath:/components-test.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EmailTest extends AbstractBaseControllerTest {

  	@Test
	public void _001_submitActionSuccess() {


	// try to get a bash env
	String smtpPassword = Configuration.root().getString("mail.smtp.password");
	assertNotNull("The password for the email server is not in the bash",smtpPassword);

	// ConnectionFormDTO
	ConnectionFormDTO cfDto = new ConnectionFormDTO("user1", "password", InterfaceTypeCode.ENTERPRISE.getKey(),"");

	//Json node
	Map<String, String> map = new HashMap<String, String>();
	map.put("login",cfDto.getLogin());
	map.put("password",cfDto.getPassword());
	map.put("interfaceName",cfDto.getInterfaceName());
	JsonNode node = Json.toJson(map);

	// Fake request
	FakeRequest fr = new FakeRequest();
//	fr.withHeader("Content-type", "application/json");
//	fr.withJsonBody(node);
	fr.withSession(SecuredController.SESSION_IDENTIFIER_STORE,cfDto.getLogin());

	Result result = null;
	try {
		// Call controller action
		result = callAction(
				eu.factorx.awac.controllers.routes.ref.EmailController.send("gaston.hollands@factorx.eu", "AWAC test des accents : [éèàç]", "[àéu!çè%] -> Need to http://localhost:9000/awac/confirm/" + "1"),
				fr
		); // callAction
	} catch (Exception e) {
		Logger.info("User is not authorized");
	}

	// wait some time to be sure AKKA actor has time to send the message

	try {
		Thread.sleep(10000);
	} catch (InterruptedException e) {
		e.printStackTrace();
	}
	// test results
	// expecting an HTTP 200 return code
    assertEquals(200, status(result));
  } // end of authenticateSuccess test

	@Test
	public void _002_submitWithAttachmentsActionSuccess() {


		// try to get a bash env
		String smtpPassword = Configuration.root().getString("mail.smtp.password");
		assertNotNull("The password for the email server is not in the bash",smtpPassword);

		// ConnectionFormDTO
		ConnectionFormDTO cfDto = new ConnectionFormDTO("user1", "password", InterfaceTypeCode.ENTERPRISE.getKey(),"");

		//Json node
		Map<String, String> map = new HashMap<String, String>();
		map.put("login",cfDto.getLogin());
		map.put("password",cfDto.getPassword());
		map.put("interfaceName",cfDto.getInterfaceName());
		JsonNode node = Json.toJson(map);

		// Fake request
		FakeRequest fr = new FakeRequest();
//	fr.withHeader("Content-type", "application/json");
//	fr.withJsonBody(node);
		fr.withSession(SecuredController.SESSION_IDENTIFIER_STORE,cfDto.getLogin());


		List<String> filenameList = new ArrayList<>();

		filenameList.add("public/vm/createVerificationRequest.vm");
		filenameList.add("public/images/ajax-loader.gif");

		Result result = null;
		try {
			// Call controller action
			result = callAction(
					eu.factorx.awac.controllers.routes.ref.EmailController.sendWithAttachments("gaston.hollands@factorx.eu", "AWAC test", "Need to http://localhost:9000/awac/confirm/" + "1",filenameList),
					fr
			); // callAction
		} catch (Exception e) {
			Logger.info("User is not authorized");
		}

		// wait some time to be sure AKKA actor has time to send the message

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// test results
		// expecting an HTTP 200 return code
		assertEquals(200, status(result));
	} // end of authenticateSuccess test

}

