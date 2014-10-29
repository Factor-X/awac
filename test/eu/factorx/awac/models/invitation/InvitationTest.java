package eu.factorx.awac.models.invitation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import play.Logger;
import eu.factorx.awac.models.AbstractBaseModelTest;
import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;

//import java.util.List;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class InvitationTest extends AbstractBaseModelTest {

	public static String INVITATION_EMAIL= "banzaiiii@factorx.eu";
	public static String INVITATION_GENKEY= "12345678901234567890";

    @Test
    public void _001_createInvitation() {

		Organization org = new Organization("gaston", InterfaceTypeCode.ENTERPRISE);
		Invitation invitation = new Invitation (INVITATION_EMAIL,INVITATION_GENKEY,org);


		em.persist(org);
		em.persist(invitation);

		//Logger.info("Identifier:" + ac.getIdentifier());
		//Logger.info("Id:" + ac.getId());

    } // end of test method


    @Test
    public void _002_retrieveInvitationByEmailSuccess() {

		Invitation invitation = null;

        String query = "select i from Invitation i where i.email = '" + INVITATION_EMAIL + "'";
		try {
			invitation = em.createQuery(query, Invitation.class).getResultList().get(0);
		} catch (Exception empty) {
			Logger.info("invitation is null");
		}
		assertEquals(invitation.getEmail(),INVITATION_EMAIL);
    } // end of test

	@Test
	public void _003_retrieveInvitationByGenKeySuccess() {

		Invitation invitation = null;

		String query = "select i from Invitation i where i.genkey = '" + INVITATION_GENKEY + "'";
		try {
			invitation = em.createQuery(query, Invitation.class).getResultList().get(0);
		} catch (Exception empty) {
			Logger.info("invitation is null");
		}
		assertEquals(invitation.getGenkey(),INVITATION_GENKEY);
	} // end of test



	// for DB cleanup
	@Test
	public void _004_deleteInvitation() {


		Invitation invitation = null;

		String query = "select i from Invitation i where i.email = '" + INVITATION_EMAIL + "'";
		try {
			invitation = em.createQuery(query, Invitation.class).getResultList().get(0);
		} catch (Exception empty) {
			Logger.info("invitation is null");
		}
		assertEquals(invitation.getEmail(),INVITATION_EMAIL);


		em.remove(invitation);
		em.remove(invitation.getOrganization());


		Invitation reload=null;

		try {
			reload = em.createQuery(query, Invitation.class).getResultList().get(0);
		} catch (Exception empty) {}

		assertNull(reload);
	} // end of test

} // end of class
