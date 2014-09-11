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
import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.awac.dto.SiteAddUsersDTO;
import eu.factorx.awac.dto.awac.dto.SiteAddUsersResultDTO;
import eu.factorx.awac.dto.awac.get.AccountDTO;
import eu.factorx.awac.dto.awac.get.InvitationResultDTO;
import eu.factorx.awac.dto.awac.get.OrganizationDTO;
import eu.factorx.awac.dto.myrmex.post.ConnectionFormDTO;
import eu.factorx.awac.models.AbstractBaseModelTest;
import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import play.Configuration;
import play.Logger;
import play.libs.Json;
import play.mvc.Result;
import play.test.FakeRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static play.test.Helpers.callAction;
import static play.test.Helpers.status;

//import play.api.mvc.AnyContent;
//import com.avaje.ebean.Ebean;

@ContextConfiguration(locations = {"classpath:/components-test.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OrganizationTest extends AbstractBaseModelTest {


	@Autowired
	ConversionService conversionService;

	private final String ORGANISATION_NAME = "Factor-X";

  	@Test
	public void _001_submitActionSuccess() {

	Organization org = new Organization(ORGANISATION_NAME);
	SiteAddUsersDTO dto = createDTO(org);

	// ConnectionFormDTO
	ConnectionFormDTO cfDto = new ConnectionFormDTO("user1", "password", InterfaceTypeCode.ENTERPRISE.getKey(),"");

	//Json Body node
	JsonNode node = Json.toJson(dto);

	// Fake request
	FakeRequest fr = new FakeRequest();
	fr.withJsonBody(node);
	fr.withSession(SecuredController.SESSION_IDENTIFIER_STORE,cfDto.getLogin());

	Result result = null;
	try {
		// Call controller action
		result = callAction(
				eu.factorx.awac.controllers.routes.ref.OrganizationController.retrieveAssociatedAccounts(),
				fr
		); // callAction
	} catch (Exception e) {
		Logger.info("User is not authorized");
	}

	// test results
	// expecting an HTTP 200 return code
    assertEquals(200, status(result));
	//analyse result
	SiteAddUsersResultDTO resultDTO = getDTO(result, SiteAddUsersResultDTO.class);

	List<AccountDTO> accountList = resultDTO.getUsers();
	assertEquals(accountList.size(),2);


  } // end of authenticateSuccess test

	// class to handle EmailInvitationDTO

	private SiteAddUsersDTO createDTO(Organization org) {

		SiteAddUsersDTO dto = new SiteAddUsersDTO();
		OrganizationDTO orgDTO = conversionService.convert(org,OrganizationDTO.class);
		dto.setOrganization(orgDTO);

		return dto;
	}


}

