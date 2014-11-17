package eu.factorx.awac.service;

import java.util.List;

import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.forms.VerificationRequestCanceled;

/**
 * Created by florian on 9/10/14.
 */
public interface VerificationRequestCanceledService extends PersistenceService<VerificationRequestCanceled> {

    public List<VerificationRequestCanceled> findByOrganizationVerifier(Organization organizationVerifier);
}
