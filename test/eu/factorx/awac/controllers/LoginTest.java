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
import play.Logger;
import play.libs.Json;
import play.mvc.Result;
import play.test.FakeRequest;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static play.test.Helpers.*;

//import play.api.mvc.AnyContent;
//import com.avaje.ebean.Ebean;

@ContextConfiguration(locations = {"classpath:/components-test.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LoginTest extends AbstractBaseModelTest {

	@Test
	public void _001_authenticateActionSuccess() {

		// ConnectionFormDTO
		ConnectionFormDTO cfDto = new ConnectionFormDTO("user1", "password", InterfaceTypeCode.ENTERPRISE.getKey(), "");

		//Json node
		Map<String, String> map = new HashMap<String, String>();
		map.put("login", cfDto.getLogin());
		map.put("password", cfDto.getPassword());
		map.put("interfaceName", cfDto.getInterfaceName());
		JsonNode node = Json.toJson(map);


		// Fake request
		FakeRequest fr = new FakeRequest();
		fr.withHeader("Content-type", "application/json");
		fr.withJsonBody(node);

		// Call controller action
		Result result = callAction(
				eu.factorx.awac.controllers.routes.ref.AuthenticationController.authenticate(),
				fr
		); // callAction

		// test results

		// expecting an HTTP 200 return code
		assertEquals(200, status(result));
		// verify user identifier in session is not null
		assertNotNull(session(result).get(SecuredController.SESSION_IDENTIFIER_STORE));
		// verify user identifier in session is the one expected
		assertEquals(session(result).get(SecuredController.SESSION_IDENTIFIER_STORE), cfDto.getLogin());

		// get LoginResultDTO
		//Logger.info("results: " + new String(contentAsBytes(result)));
		String content = new String(contentAsBytes(result));
		JsonNode jsonResponse = Json.parse(content);
		LoginResultDTO loginResult = Json.fromJson(jsonResponse, LoginResultDTO.class);

		//Logger.info("jsonNode: " + jsonResponse.toString());
		//Logger.info("findPath:" + jsonResponse.findPath("lastname").asText());
		//Logger.info("lastname:" + loginResult.getPerson().getLastName());

		// verify lastname of user1 is Dupont.
		assertEquals(loginResult.getPerson().getLastName(), "Dupont");

	} // end of authenticateSuccess test

	@Test
	public void _002_authenticateActionFailure() {

		// ConnectionFormDTO
		ConnectionFormDTO cfDto = new ConnectionFormDTO("unknown", "password", InterfaceTypeCode.ENTERPRISE.getKey(), "");

		//Json node
		Map<String, String> map = new HashMap<String, String>();
		map.put("login", cfDto.getLogin());
		map.put("password", cfDto.getPassword());
		map.put("interfaceName", cfDto.getInterfaceName());
		JsonNode node = Json.toJson(map);


		// Fake request
		FakeRequest fr = new FakeRequest();
		fr.withHeader("Content-type", "application/json");
		fr.withJsonBody(node);

		// Call controller action
		Result result = callAction(
				eu.factorx.awac.controllers.routes.ref.AuthenticationController.authenticate(),
				fr
		); // callAction

		// test results

		// expecting an HTTP 401 return code
		assertEquals(401, status(result));
		// verify user identifier in session is null
		assertNull(session(result).get(SecuredController.SESSION_IDENTIFIER_STORE));

		// should return a ExceptionDTO
		ExceptionsDTO loginResult = getDTO(result, ExceptionsDTO.class);

		// verify lastname of user1 is Dupont.
		assertEquals(loginResult.getMessage(), "The couple login / password was not found");
	} // end of authenticateSuccess test

	@Test
	public void _003_testAuthenticationActionSuccess() {

		// ConnectionFormDTO
		ConnectionFormDTO cfDto = new ConnectionFormDTO("user1", "password", InterfaceTypeCode.ENTERPRISE.getKey(), "");

		//Json node
		Map<String, String> map = new HashMap<String, String>();
		map.put("login", cfDto.getLogin());
		map.put("password", cfDto.getPassword());
		map.put("interfaceName", cfDto.getInterfaceName());
		JsonNode node = Json.toJson(map);


		// Fake request
		FakeRequest fr = new FakeRequest();
		fr.withHeader("Content-type", "application/json");
		fr.withJsonBody(node);
		fr.withSession(SecuredController.SESSION_IDENTIFIER_STORE, cfDto.getLogin());

		// Call controller action
		Result result = callAction(
				eu.factorx.awac.controllers.routes.ref.AuthenticationController.testAuthentication(),
				fr
		); // callAction

		// test results

		// expecting an HTTP 200 return code
		assertEquals(200, status(result));

	} // end of authenticateSuccess test

	@Test
	public void _004_testAuthenticationActionFailure() {

		// ConnectionFormDTO
		ConnectionFormDTO cfDto = new ConnectionFormDTO("user1", "password", InterfaceTypeCode.ENTERPRISE.getKey(), "");

		//Json node
		Map<String, String> map = new HashMap<String, String>();
		map.put("login", cfDto.getLogin());
		map.put("password", cfDto.getPassword());
		map.put("interfaceName", cfDto.getInterfaceName());
		JsonNode node = Json.toJson(map);


		// Fake request
		FakeRequest fr = new FakeRequest();
		fr.withHeader("Content-type", "application/json");
		fr.withJsonBody(node);
		//fr.withSession(SecuredController.SESSION_IDENTIFIER_STORE,cfDto.getLogin());

		// Call controller action
		Result result = callAction(
				eu.factorx.awac.controllers.routes.ref.AuthenticationController.testAuthentication(),
				fr
		); // callAction

		// test results

		// expecting an HTTP 200 return code
		assertEquals(401, status(result));

	} // end of authenticateSuccess test


	@Test
	public void _005_LogoutActionSuccess() {
		Result result = callAction(
				eu.factorx.awac.controllers.routes.ref.AuthenticationController.logout(),
				fakeRequest()
		);

		// expecting an HTTP 200 return code
		assertEquals(200, status(result));
	} // end of authenticated test

	@Test
	public void _006_authenticateWithBadInterfaceNameActionFailure() {

		// ConnectionFormDTO
		ConnectionFormDTO cfDto = new ConnectionFormDTO("user1", "password", InterfaceTypeCode.MUNICIPALITY.getKey(), "");

		//Json node
		Map<String, String> map = new HashMap<String, String>();
		map.put("login", cfDto.getLogin());
		map.put("password", cfDto.getPassword());
		map.put("interfaceName", cfDto.getInterfaceName());
		JsonNode node = Json.toJson(map);


		// Fake request
		FakeRequest fr = new FakeRequest();
		fr.withHeader("Content-type", "application/json");
		fr.withJsonBody(node);

		// Call controller action
		Result result = callAction(
				eu.factorx.awac.controllers.routes.ref.AuthenticationController.authenticate(),
				fr
		); // callAction

		// test results

		// expecting an HTTP 401 return code
		assertEquals(401, status(result));
		// verify user identifier in session is null
		assertNull(session(result).get(SecuredController.SESSION_IDENTIFIER_STORE));

		// should return a ExceptionDTO
		String content = new String(contentAsBytes(result));
		JsonNode jsonResponse = Json.parse(content);
		//Logger.info("jsonNode: " + jsonResponse.toString());

		ExceptionsDTO loginResult = Json.fromJson(jsonResponse, ExceptionsDTO.class);
		// verify lastname of user1 is Dupont.
		assertEquals(loginResult.getMessage(), "This account is not for municipality but for enterprise. Please switch calculator and retry.");
	} // end of authenticateSuccess test


}

