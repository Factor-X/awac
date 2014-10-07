package eu.factorx.awac.service;

import eu.factorx.awac.models.AbstractBaseModelTest;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.account.Person;
import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.models.invitation.Invitation;
import eu.factorx.awac.models.invitation.InvitationTest;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@ContextConfiguration(locations = {"classpath:/components-test.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class InvitationServiceTest extends AbstractBaseModelTest {

	@Autowired
	private InvitationService invitationService;


	@Autowired
	private OrganizationService organizationService;

	@Test
    public void _001_createInvitation() {

		Organization org = new Organization ("gaston", InterfaceTypeCode.ENTERPRISE);
		organizationService.saveOrUpdate(org);

		Invitation invitation = new Invitation (InvitationTest.INVITATION_EMAIL,InvitationTest.INVITATION_GENKEY,org);
		invitationService.saveOrUpdate(invitation);

    } // end of test method

	@Test
	public void _002_retrieveInvitationByEmail() {

		play.Logger.info("Spring @" + new Date(applicationContext.getStartupDate()));

		List<Invitation> invitationList = null;
		invitationList = invitationService.findByEmail(InvitationTest.INVITATION_EMAIL);
		assertNotNull(invitationList);
		assertTrue(invitationList.size() > 0 ? true : false);
	} // end of test method



	@Test
	public void _003_retrieveInvitationByGenkey() {

		Invitation invitation = null;
		invitation = invitationService.findByGenkey(InvitationTest.INVITATION_GENKEY);
		assertNotNull(invitation);
		assertEquals(invitation.getEmail(),InvitationTest.INVITATION_EMAIL);

	} // end of test


	// for DB cleanup
	@Test
	public void _004_deleteInvitation() {

		Invitation invitation = null;
		invitation = invitationService.findByGenkey(InvitationTest.INVITATION_GENKEY);
		assertNotNull(invitation);
		assertEquals(invitation.getEmail(),InvitationTest.INVITATION_EMAIL);

		// suppress
		//em.getTransaction().begin();
		invitationService.remove(invitation);
		organizationService.remove(invitation.getOrganization());
		//em.getTransaction().commit();

		List<Invitation> invitationReloadList = null;

		try {
			invitationReloadList = invitationService.findByEmail(InvitationTest.INVITATION_EMAIL);
		} catch (Exception empty) {}

		assertTrue(invitationReloadList.size()==0?true:false);
	} // end of test


} // end of class
