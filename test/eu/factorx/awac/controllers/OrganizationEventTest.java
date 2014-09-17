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
import eu.factorx.awac.dto.awac.dto.OrganizationEventDTO;
import eu.factorx.awac.dto.awac.dto.OrganizationEventResultDTO;
import eu.factorx.awac.dto.awac.dto.SiteAddUsersDTO;
import eu.factorx.awac.dto.awac.dto.SiteAddUsersResultDTO;
import eu.factorx.awac.dto.awac.get.AccountDTO;
import eu.factorx.awac.dto.awac.get.OrganizationDTO;
import eu.factorx.awac.dto.awac.get.PeriodDTO;
import eu.factorx.awac.dto.awac.get.SiteDTO;
import eu.factorx.awac.dto.myrmex.post.ConnectionFormDTO;
import eu.factorx.awac.models.association.AccountSiteAssociation;
import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.business.OrganizationEvent;
import eu.factorx.awac.models.business.Site;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.models.code.type.PeriodCode;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.service.*;
import org.junit.Assert;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static play.test.Helpers.callAction;
import static play.test.Helpers.status;

//import play.api.mvc.AnyContent;
//import com.avaje.ebean.Ebean;

@ContextConfiguration(locations = {"classpath:/components-test.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OrganizationEventTest extends AbstractNoDefaultTransactionBaseControllerTest {


	@Autowired
	ConversionService conversionService;

	@Autowired
	OrganizationService organisationService;

	@Autowired
	PeriodService periodService;

	@Autowired
	private OrganizationEventService organizationEventService;



	private final String ORGANISATION_NAME = "Factor-X";
	private final String EVENT_NAME = "CTRL_TEST_EVENT_NAME";
	private final String EVENT_DESCRIPTION = "CTRL_TEST_EVENT_DESCRIPTION";



  	@Test
	public void _001_getAllEvents() {

	Organization org = organisationService.findByName(ORGANISATION_NAME);
	Period period = periodService.findByCode(PeriodCode.P2013);
	OrganizationEventDTO dto = createDTO(org,period);

	// create event
	em.getTransaction().begin();
	OrganizationEvent createdEvent = createOrganizationEvent(org, period, "event1", "eventDescription");
	Assert.assertNotNull("Failed to save a new OrganizationEvent", createdEvent);
	em.getTransaction().commit();

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
				eu.factorx.awac.controllers.routes.ref.OrganizationEventController.loadEvents(),
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
	OrganizationEventResultDTO resultDTO = getDTO(result, OrganizationEventResultDTO.class);
	assertNotNull(resultDTO);

	assertEquals(1, resultDTO.getOrganizationEventList().size());

  } // end of authenticateSuccess test

	@Test
	public void _002_saveEvent() {

		Organization org = organisationService.findByName(ORGANISATION_NAME);
		Period period = periodService.findByCode(PeriodCode.P2013);
		OrganizationEventDTO dto = createDTO(org,period);
		dto.setName(EVENT_NAME);
		dto.setDescription(EVENT_DESCRIPTION);

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
					eu.factorx.awac.controllers.routes.ref.OrganizationEventController.saveEvent(),
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
		OrganizationEventDTO resultDTO = getDTO(result, OrganizationEventDTO.class);
		assertNotNull(resultDTO);

		assertEquals(EVENT_NAME, resultDTO.getName());

	}

	@Test
	public void _003_DeleteAllEvents() {
		em.getTransaction().begin();
		Organization org = organisationService.findByName(ORGANISATION_NAME);
		Period period = periodService.findByCode(PeriodCode.P2013);

		organizationEventService.remove(organizationEventService.findByOrganizationAndPeriod(org,period));
		em.getTransaction().commit();
	}


	private OrganizationEventDTO createDTO(Organization org, Period period) {

		OrganizationEventDTO dto = new OrganizationEventDTO();
		OrganizationDTO orgDTO = conversionService.convert(org,OrganizationDTO.class);
		PeriodDTO periodDTO = conversionService.convert(period,PeriodDTO.class);
		dto.setOrganization(orgDTO);
		dto.setPeriod(periodDTO);

		return dto;
	}

	private OrganizationEvent createOrganizationEvent(Organization organization, Period period, String name, String description) {
		return organizationEventService.saveOrUpdate(new OrganizationEvent(organization, period, name, description));
	}

}