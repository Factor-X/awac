package eu.factorx.awac.service;

import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.invitation.Invitation;

import java.util.List;

public interface InvitationService extends PersistenceService<Invitation> {

	public Invitation findByGenkey(String genkey);

	public List<Invitation> findByEmail(String email);

}
