package eu.factorx.awac.service;

import java.util.ArrayList;
import java.util.List;

import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import eu.factorx.awac.models.AbstractBaseModelTest;
import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.business.OrganizationEvent;
import eu.factorx.awac.models.code.type.PeriodCode;
import eu.factorx.awac.models.knowledge.Period;

@ContextConfiguration(locations = { "classpath:/components-test.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OrganizationEventServiceTest extends AbstractBaseModelTest {

	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private PeriodService periodService;
	@Autowired
	private OrganizationEventService organizationEventService;

	@Test
	public void _001_testCRUD() throws Exception {
		Organization organization = createOrganization("testorg1");
		Period p2006 = periodService.findByCode(PeriodCode.P2006);

		// create
		OrganizationEvent createdEvent = createOrganizationEvent(organization, p2006, "event1", "eventDescription");
		Assert.assertNotNull("Failed to save a new OrganizationEvent", createdEvent);
		Long eventId = createdEvent.getId();

		// find by id
		OrganizationEvent foundEvent = organizationEventService.findById(eventId);
		Assert.assertNotNull("Cannot find event with id = " + eventId, foundEvent);

		// update
		foundEvent.setDescription("updatedEventDescription");
		OrganizationEvent updatedEvent = organizationEventService.saveOrUpdate(foundEvent);
		Assert.assertEquals(eventId, updatedEvent.getId());
		updatedEvent = organizationEventService.findById(eventId);
		Assert.assertEquals("updatedEventDescription", updatedEvent.getDescription());

		// remove
		organizationEventService.remove(updatedEvent);
		Assert.assertNull("Failed to remove event " + updatedEvent, organizationEventService.findById(eventId));
		
		// clean
		organizationService.remove(organization);
	}

	@Test
	public void _002_testFindByOrganizationAndPeriod() throws Exception {
		Organization organization = createOrganization("testorg2");
		Period p2006 = periodService.findByCode(PeriodCode.P2006);

		List<OrganizationEvent> createdEvents = new ArrayList<>();
		OrganizationEvent event1 = createOrganizationEvent(organization, p2006, "event1", "eventDescription");
		createdEvents.add(event1);
		OrganizationEvent event2 = createOrganizationEvent(organization, p2006, "event2", "event2Description");
		createdEvents.add(event2);

		Assert.assertEquals(createdEvents, organizationEventService.findByOrganizationAndPeriod(organization, p2006));

		// clean
		organizationEventService.remove(createdEvents);
		organizationService.remove(organization);
	}

	@Test
	public void _003_testFindByOrganization() throws Exception {
		Organization organization = createOrganization("testorg3");
		Period p2006 = periodService.findByCode(PeriodCode.P2006);
		Period p2007 = periodService.findByCode(PeriodCode.P2007);
		Period p2008 = periodService.findByCode(PeriodCode.P2008);

		List<OrganizationEvent> createdEvents = new ArrayList<>();
		OrganizationEvent event1 = createOrganizationEvent(organization, p2006, "event1", "eventDescription");
		createdEvents.add(event1);
		OrganizationEvent event2 = createOrganizationEvent(organization, p2007, "event2", "event2Description");
		createdEvents.add(event2);
		OrganizationEvent event3 = createOrganizationEvent(organization, p2008, "event3", "event3Description");
		createdEvents.add(event3);


		Assert.assertEquals(createdEvents, organizationEventService.findByOrganization(organization));

		// clean
		organizationEventService.remove(createdEvents);
		organizationService.remove(organization);
	}


	@Test
	public void _004_testOnOrganizationDeleteCascade() throws Exception {
		Organization organization = createOrganization("testorg3");
		Long organizationId = organization.getId();
		Period p2006 = periodService.findByCode(PeriodCode.P2006);

		OrganizationEvent createdEvent = createOrganizationEvent(organization, p2006, "event1", "eventDescription");
		Long eventId = createdEvent.getId();
		// explicitly attach event to its organization (to allow cascade detaching at organization removal)
		// required due to the bidirectional character of the relation (and because persistence context will not be flushed before transaction commit)
		organization.getEvents().add(createdEvent);
		organizationService.saveOrUpdate(organization);

		organizationService.remove(organizationService.findById(organizationId));

		Assert.assertNull("Failed to remove organization " + organizationId, organizationService.findById(organizationId));
		Assert.assertNull("Failed to cacade remove organization event " + eventId, organizationEventService.findById(eventId));
	}

	private Organization createOrganization(String name) {
		return organizationService.saveOrUpdate(new Organization(name + "_" + System.currentTimeMillis(), InterfaceTypeCode.ENTERPRISE));
	}

	private OrganizationEvent createOrganizationEvent(Organization organization, Period period, String name, String description) {
		return organizationEventService.saveOrUpdate(new OrganizationEvent(organization, period, name, description));
	}

}
