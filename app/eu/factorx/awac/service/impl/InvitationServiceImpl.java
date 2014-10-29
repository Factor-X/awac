package eu.factorx.awac.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import play.Logger;
import play.db.jpa.JPA;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.models.invitation.Invitation;
import eu.factorx.awac.service.InvitationService;

@Repository
public class InvitationServiceImpl extends AbstractJPAPersistenceServiceImpl<Invitation> implements InvitationService {

	@Override
	public Invitation findByGenkey (String genkey) {
		List<Invitation> resultList = JPA.em().createNamedQuery(Invitation.FIND_BY_KEY, Invitation.class)
				.setParameter("genkey", genkey).getResultList();
		if (resultList.size() > 1) {
			String errorMsg = "More than one genkey found for same invited user";
			Logger.error(errorMsg);
			throw new RuntimeException(errorMsg);
		}
		if (resultList.size() == 0) {
			return null;
		}
		return resultList.get(0);
	}

	@Override
	public List<Invitation> findByEmail(String email){
		List<Invitation> resultList = JPA.em().createNamedQuery(Invitation.FIND_BY_EMAIL, Invitation.class)
				.setParameter("email", email).getResultList();

		return resultList;
	}

    @Override
    public Invitation findByEmailAndInterface(String email, InterfaceTypeCode interfaceCode) {

        List<Invitation> resultList = JPA.em().createNamedQuery(Invitation.FIND_BY_EMAIL_AND_INTERFACE, Invitation.class)
                .setParameter("email", email)
                .setParameter("interface", interfaceCode)
                .getResultList();
        if (resultList.size() > 1) {
            String errorMsg = "More than one genkey found for same invited user";
            Logger.error(errorMsg);
            throw new RuntimeException(errorMsg);
        }
        if (resultList.size() == 0) {
            return null;
        }
        return resultList.get(0);
    }
}
