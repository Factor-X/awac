package eu.factorx.awac.service.impl;

import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.forms.VerificationRequest;
import eu.factorx.awac.models.forms.VerificationRequestCanceled;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.service.VerificationRequestCanceledService;
import eu.factorx.awac.service.VerificationRequestService;
import org.springframework.stereotype.Repository;
import play.Logger;
import play.db.jpa.JPA;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by florian on 26/09/14.
 */
@Repository
public class VerificationRequestCanceledServiceImpl extends AbstractJPAPersistenceServiceImpl<VerificationRequestCanceled> implements VerificationRequestCanceledService{


    @Override
    public List<VerificationRequestCanceled> findByOrganizationVerifier(Organization organizationVerifier) {
        return JPA.em().createNamedQuery(VerificationRequestCanceled.FIND_BY_ORGANIZATION_VERIFIER, VerificationRequestCanceled.class)
                .setParameter("organizationVerifier", organizationVerifier).getResultList();

    }


}
