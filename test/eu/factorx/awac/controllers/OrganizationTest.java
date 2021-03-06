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
import static org.junit.Assert.assertTrue;
import static play.test.Helpers.callAction;
import static play.test.Helpers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import play.Logger;
import play.libs.Json;
import play.mvc.Result;
import play.test.FakeRequest;

import com.fasterxml.jackson.databind.JsonNode;

import eu.factorx.awac.dto.awac.get.AccountDTO;
import eu.factorx.awac.dto.awac.get.OrganizationDTO;
import eu.factorx.awac.dto.awac.get.SiteAddUsersResultDTO;
import eu.factorx.awac.dto.awac.get.SiteDTO;
import eu.factorx.awac.dto.awac.post.SiteAddUsersDTO;
import eu.factorx.awac.dto.myrmex.post.ConnectionFormDTO;
import eu.factorx.awac.models.association.AccountSiteAssociation;
import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.business.Site;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.service.AccountSiteAssociationService;
import eu.factorx.awac.service.OrganizationService;
import eu.factorx.awac.service.SiteService;

//import play.api.mvc.AnyContent;
//import com.avaje.ebean.Ebean;

@ContextConfiguration(locations = {"classpath:/components-test.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OrganizationTest extends AbstractBaseControllerTest {


	@Autowired
	ConversionService conversionService;

	@Autowired
	OrganizationService organisationService;

	@Autowired
	SiteService siteService;

	@Autowired
	private AccountSiteAssociationService accountSiteAssociationService;

	private final String USER1_IDENTIFIER = "user1";
	private final String USER2_IDENTIFIER = "user2";

  	@Test
	public void _001_getAllAccounts() {

	Organization org = organisationService.findById(FACTORX_ID);
	Site site = siteService.findById(1L);
	SiteAddUsersDTO dto = createDTO(org,site);

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
				eu.factorx.awac.controllers.routes.ref.OrganizationController.loadAssociatedAccounts(),
				fr
		); // callAction
	} catch (Exception e) {
		Logger.info("Action exception occured");
		e.printStackTrace();
		assertTrue(false);
	}

	// test results
	// expecting an HTTP 200 return code
    assertEquals(200, status(result));
	//analyse result
	SiteAddUsersResultDTO resultDTO = getDTO(result, SiteAddUsersResultDTO.class);

	List<AccountDTO> organizationAccountList = resultDTO.getOrganizationUserList();
	List<AccountDTO> siteAccountList = resultDTO.getSiteSelectedUserList();

	assertEquals(2,organizationAccountList.size());
	assertEquals(0,siteAccountList.size());




  } // end of authenticateSuccess test

	@Test
	public void _002_saveAssociatedAccounts() {

		List sites = new ArrayList<Site> ();

		Organization org = organisationService.findById(FACTORX_ID);
		Site site = siteService.findById(1L);
		SiteAddUsersDTO dto = createDTO(org,site);

		// add fake associated accounts
		AccountDTO account1 = new AccountDTO();
		account1.setIdentifier(USER1_IDENTIFIER);

		AccountDTO account2 = new AccountDTO();
		account2.setIdentifier(USER2_IDENTIFIER);

		List<AccountDTO> associatedAccountList= new ArrayList<AccountDTO>();
		associatedAccountList.add(account1);
		associatedAccountList.add(account2);

		dto.setSelectedAccounts(associatedAccountList);

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
					eu.factorx.awac.controllers.routes.ref.OrganizationController.saveAssociatedAccounts(),
					fr
			); // callAction
		} catch (Exception e) {
			Logger.info("Action exception occured...");
			e.printStackTrace();
			assertTrue(false);
		}

		// test results
		// expecting an HTTP 200 return code
		assertEquals(200, status(result));
		//analyse result

		SiteAddUsersResultDTO resultDTO = getDTO(result, SiteAddUsersResultDTO.class);
		List<AccountDTO> organizationAccountList = resultDTO.getOrganizationUserList();
		List<AccountDTO> siteAccountList = resultDTO.getSiteSelectedUserList();

		assertEquals(2,organizationAccountList.size());
		assertEquals(2,siteAccountList.size());

	} // end of test

	@Test
	public void _003_cleanTestData() {

		Site site = siteService.findById(1L);
		List<AccountSiteAssociation> listToRemove = accountSiteAssociationService.findBySite(site);

		// remove account for test DB sanity
		for (AccountSiteAssociation item : listToRemove) {
			accountSiteAssociationService.remove(item);
		}

		List<AccountSiteAssociation> reload = accountSiteAssociationService.findBySite(site);
		assertTrue(reload.isEmpty());
	}

	// class to handle EmailInvitationDTO

	private SiteAddUsersDTO createDTO(Organization org, Site site) {

		SiteAddUsersDTO dto = new SiteAddUsersDTO();
		OrganizationDTO orgDTO = conversionService.convert(org,OrganizationDTO.class);
		SiteDTO siteDTO = conversionService.convert(site,SiteDTO.class);
		//dto.setOrganization(orgDTO);
		dto.setSite(siteDTO);

		return dto;
	}


}

