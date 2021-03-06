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

import static org.junit.Assert.*;
import static play.test.Helpers.callAction;
import static play.test.Helpers.status;

import java.util.List;

import eu.factorx.awac.dto.awac.get.*;
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

import com.fasterxml.jackson.databind.JsonNode;

import eu.factorx.awac.dto.myrmex.post.ConnectionFormDTO;
import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.business.OrganizationEvent;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.models.code.type.PeriodCode;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.service.OrganizationEventService;
import eu.factorx.awac.service.OrganizationService;
import eu.factorx.awac.service.PeriodService;

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

		LoginResultDTO loginResultDTO = createOrganization();

		Organization org = organisationService.findById(loginResultDTO.getOrganizationId());
		Period period = periodService.findByCode(PeriodCode.P2013);
		OrganizationEventDTO dto = createDTO(org, period);

		// create event
		em.getTransaction().begin();
		OrganizationEvent createdEvent = createOrganizationEvent(org, period, "event1", "eventDescription");
		Assert.assertNotNull("Failed to save a new OrganizationEvent", createdEvent);
		em.getTransaction().commit();

		// ConnectionFormDTO
		ConnectionFormDTO cfDto = new ConnectionFormDTO("user1", "password", InterfaceTypeCode.ENTERPRISE.getKey(), "");

		//Json Body node
		JsonNode node = Json.toJson(dto);

		// Fake request
		FakeRequest fr = new FakeRequest();
		fr.withJsonBody(node);
		fr.withSession(SecuredController.SESSION_IDENTIFIER_STORE, cfDto.getLogin());

		Result result = null;
		try {
			// Call controller action
			result = callAction(
				eu.factorx.awac.controllers.routes.ref.OrganizationEventController.load(),
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

		assertEquals(4, resultDTO.getOrganizationEventList().size());

		//save event
		dto.setName(EVENT_NAME);
		dto.setDescription(EVENT_DESCRIPTION);
		//dto.setId(14L);

		cfDto = new ConnectionFormDTO("user1", "password", InterfaceTypeCode.ENTERPRISE.getKey(), "");

		//Json Body node
		node = Json.toJson(dto);

		// Fake request
		fr = new FakeRequest();
		fr.withJsonBody(node);
		fr.withSession(SecuredController.SESSION_IDENTIFIER_STORE, cfDto.getLogin());

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
		OrganizationEventDTO resultOrgEvDTO = getDTO(result, OrganizationEventDTO.class);
		assertNotNull(resultOrgEvDTO);

		assertEquals(EVENT_NAME, resultOrgEvDTO.getName());
/*
	}

	@Test
	public void _003_updateEvent() {
*/

		//update event
		List<OrganizationEvent> list = organizationEventService.findByOrganizationAndPeriod(org, period);
		OrganizationEvent orgEvent = list.get(0);

		OrganizationEventDTO orgEventDTO = conversionService.convert(orgEvent, OrganizationEventDTO.class);


		orgEventDTO.setName("change");

		cfDto = new ConnectionFormDTO("user1", "password", InterfaceTypeCode.ENTERPRISE.getKey(), "");

		//Json Body node
		node = Json.toJson(orgEventDTO);

		// Fake request
		fr = new FakeRequest();
		fr.withJsonBody(node);
		fr.withSession(SecuredController.SESSION_IDENTIFIER_STORE, cfDto.getLogin());

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
		resultOrgEvDTO = getDTO(result, OrganizationEventDTO.class);
		assertNotNull(resultOrgEvDTO);

		assertEquals("change", resultOrgEvDTO.getName());

	}
/*
	@Test
	public void _004_DeleteAllEvents() {
		em.getTransaction().begin();
		Organization org = organisationService.findById(FACTORX_ID);
		Period period = periodService.findByCode(PeriodCode.P2013);

		organizationEventService.remove(organizationEventService.findByOrganizationAndPeriod(org, period));
		em.getTransaction().commit();
	}
*/

	private OrganizationEventDTO createDTO(Organization org, Period period) {

		OrganizationEventDTO dto = new OrganizationEventDTO();
		OrganizationDTO orgDTO = conversionService.convert(org, OrganizationDTO.class);
		PeriodDTO periodDTO = conversionService.convert(period, PeriodDTO.class);
		//dto.setOrganization(orgDTO);
		dto.setPeriod(periodDTO);

		return dto;
	}

	private OrganizationEvent createOrganizationEvent(Organization organization, Period period, String name, String description) {
		return organizationEventService.saveOrUpdate(new OrganizationEvent(organization, period, name, description));
	}

}
