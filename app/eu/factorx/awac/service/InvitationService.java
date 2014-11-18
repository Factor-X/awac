package eu.factorx.awac.service;

import java.util.List;

import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.models.invitation.Invitation;

public interface InvitationService extends PersistenceService<Invitation> {

	public Invitation findByGenkey(String genkey);

	public List<Invitation> findByEmail(String email);

    public Invitation findByEmailAndInterface(String invitationEmail, InterfaceTypeCode interfaceCode);
}
