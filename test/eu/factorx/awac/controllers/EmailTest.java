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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static play.test.Helpers.callAction;
import static play.test.Helpers.status;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import eu.factorx.awac.util.email.messages.EmailMessage;
import eu.factorx.awac.util.email.service.EmailService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import play.Configuration;
import play.Logger;
import play.libs.Json;
import play.mvc.Result;
import play.test.FakeRequest;

import com.fasterxml.jackson.databind.JsonNode;

import eu.factorx.awac.dto.myrmex.post.ConnectionFormDTO;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;

//import play.api.mvc.AnyContent;
//import com.avaje.ebean.Ebean;

@ContextConfiguration(locations = {"classpath:/components-test.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EmailTest extends AbstractBaseControllerTest {

    @Autowired
    EmailService emailService;

	//@Test
	public void _001_submitActionSuccess() {


		// try to get a bash env
		String smtpPassword = Configuration.root().getString("mail.smtp.password");
		System.out.println("smtpPassword==" + smtpPassword);
		assertNotNull("The password for the email server is not in the bash", smtpPassword);

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
//	fr.withHeader("Content-type", "application/json");
//	fr.withJsonBody(node);
		fr.withSession(SecuredController.SESSION_IDENTIFIER_STORE, cfDto.getLogin());

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
		assertNotNull("The password for the email server is not in the bash", smtpPassword);

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
//	fr.withHeader("Content-type", "application/json");
//	fr.withJsonBody(node);
		fr.withSession(SecuredController.SESSION_IDENTIFIER_STORE, cfDto.getLogin());


		List<String> filenameList = new ArrayList<>();

		filenameList.add("public/vm/verification/startVerification.vm");
		filenameList.add("public/images/ajax-loader.gif");

        FileInputStream input = null;
        try {
            Logger.info("Creating Filer Input Stream");
            input = new FileInputStream(new File("/home/gaston/Downloads/export.xls"));
            Logger.info("Done...");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Logger.info("Creating String");
            String theString = IOUtils.toString(input, "UTF8");
            Logger.info("file content = " + theString);
            Logger.info("Done...");
        } catch (Exception e) {
            e.printStackTrace();
        }

        ByteArrayInputStream bais=null;
        try {
            bais =  new ByteArrayInputStream(FileUtils.readFileToByteArray(new File("/home/gaston/Downloads/export.xls")));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //send email
        HashMap<String, ByteArrayInputStream> attachementList = new HashMap<>();
        attachementList.put("gho.xls", bais);

        // send mail
        EmailMessage email = new EmailMessage("test","gaston.hollands@factorx.eu", "file attachment", "File attachment with InputStream",attachementList);
        //EmailMessage email = new EmailMessage(destinationEmail, subject, message);
        emailService.send(email);


        //send email
        //HashMap<String, ByteArrayInputStream> listAttachment = new HashMap<>();
        //listAttachment.put("average.xls", input);

		Result result = null;
//		try {
//			// Call controller action
//			result = callAction(
//				eu.factorx.awac.controllers.routes.ref.EmailController.sendWithAttachments("gaston.hollands@factorx.eu", "AWAC test", "Need to http://localhost:9000/awac/confirm/" + "1", filenameList),
//				fr
//			); // callAction
//		} catch (Exception e) {
//			Logger.info("User is not authorized");
//		}

		// wait some time to be sure AKKA actor has time to send the message

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// test results
		// expecting an HTTP 200 return code
		//assertEquals(200, status(result));
	} // end of authenticateSuccess test

}

