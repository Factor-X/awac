package eu.factorx.awac.service;

import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.forms.VerificationRequest;
import eu.factorx.awac.models.forms.VerificationRequestCanceled;
import eu.factorx.awac.models.knowledge.Period;

import java.util.List;

/**
 * Created by florian on 9/10/14.
 */
public interface VerificationRequestCanceledService extends PersistenceService<VerificationRequestCanceled> {

    public List<VerificationRequestCanceled> findByOrganizationVerifier(Organization organizationVerifier);
}
