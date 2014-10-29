package eu.factorx.awac.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import play.db.jpa.JPA;
import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.forms.VerificationRequestCanceled;
import eu.factorx.awac.service.VerificationRequestCanceledService;

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
