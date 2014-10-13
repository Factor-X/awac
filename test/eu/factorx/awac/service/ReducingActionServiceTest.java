package eu.factorx.awac.service;

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
import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.models.code.type.ReducingActionType;
import eu.factorx.awac.models.knowledge.ReducingAction;

@ContextConfiguration(locations = { "classpath:/components-test.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ReducingActionServiceTest extends AbstractBaseModelTest {

	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private ReducingActionService reducingActionService;

	@Test
	public void _001_testCRUD() throws Exception {
		Organization organization = createOrganization("testorg1");

		// create
		ReducingAction action = createReducingAction("Mobilité: Réduire les déplacements de 10%", organization, ReducingActionType.REDUCING_GES);
		Assert.assertNotNull("Failed to save a new ReducingAction", action);
		Long actionId = action.getId();

		// find by id
		ReducingAction foundAction = reducingActionService.findById(actionId);
		Assert.assertNotNull("Cannot find action with id = " + actionId, foundAction);

		// update
		foundAction.setTitle("updatedActionTitle");
		ReducingAction updatedAction = reducingActionService.saveOrUpdate(foundAction);
		Assert.assertEquals(actionId, updatedAction.getId());
		updatedAction = reducingActionService.findById(actionId);
		Assert.assertEquals("updatedActionTitle", updatedAction.getTitle());

		// remove
		reducingActionService.remove(updatedAction);
		Assert.assertNull("Failed to remove action " + updatedAction, reducingActionService.findById(actionId));
		
		// clean
		organizationService.remove(organization);
	}

//	@Test
//	public void _004_testOnOrganizationDeleteCascade() throws Exception {
//		Organization organization = createOrganization("testorg3");
//		Long organizationId = organization.getId();
//		Period p2006 = periodService.findByCode(PeriodCode.P2006);
//
//		OrganizationEvent createdEvent = createOrganizationEvent(organization, p2006, "event1", "eventDescription");
//		Long eventId = createdEvent.getId();
//		// explicitly attach event to its organization (to allow cascade detaching at organization removal)
//		// required due to the bidirectional character of the relation (and because persistence context will not be flushed before transaction commit)
//		organization.getEvents().add(createdEvent);
//		organizationService.saveOrUpdate(organization);
//
//		organizationService.remove(organizationService.findById(organizationId));
//
//		Assert.assertNull("Failed to remove organization " + organizationId, organizationService.findById(organizationId));
//		Assert.assertNull("Failed to cacade remove organization event " + eventId, organizationEventService.findById(eventId));
//	}

	private Organization createOrganization(String name) {
		return organizationService.saveOrUpdate(new Organization(name + "_" + System.currentTimeMillis(), InterfaceTypeCode.ENTERPRISE));
	}

	private ReducingAction createReducingAction(String name, Scope scope, ReducingActionType type) {
		return reducingActionService.saveOrUpdate(new ReducingAction(name, scope, type));
	}

}
