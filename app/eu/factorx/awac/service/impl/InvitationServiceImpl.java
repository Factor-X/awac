package eu.factorx.awac.service.impl;

import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.invitation.Invitation;
import eu.factorx.awac.service.AccountService;
import eu.factorx.awac.service.InvitationService;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Repository;
import play.Logger;
import play.db.jpa.JPA;

import java.util.List;

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
}
